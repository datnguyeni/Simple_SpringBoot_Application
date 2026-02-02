package com.example.revise.service;

import com.example.revise.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
public class JwtService {

   @Value("${jwt.secret}")
   private String SIGNER_KEY;

    public String generateToken(User user) throws JOSEException {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("example.com")
                .issueTime(new Date())
                .expirationTime(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
                )
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return jwsObject.serialize();
    }

    public boolean validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);

            // 1. Verify Signature
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            if (!jwsObject.verify(verifier)) {
                return false;
            }

            // 2. Parse Claims
            JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            Date expirationTime = claims.getExpirationTime();

            // 3. Check if 'exp' exists and is in the future
            return expirationTime != null && expirationTime.after(new Date());

        } catch (Exception e) {
            // Log the exception here if you need to debug why tokens are failing
            return false;
        }
    }



    private String buildScope(User user) {
        return user.getRole(); // xong
    }


//    private String buildScope(User user){
//        StringJoiner stringJoiner = new StringJoiner(" ");
//
//        if(!CollectionUtils.isEmpty(user.getRole())){
//            user.getRole().forEach(stringJoiner::add);
//
//            return stringJoiner.toString();
//        }
//    }

}


