package com.example.revise.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

   @Value("${jwt.secret}")
   private String SIGNER_KEY;

    public String generateToken(String username) throws JOSEException {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("example.com")
                .issueTime(new Date())
                .expirationTime(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
                )
                .claim("customerClaim", "Customer")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return jwsObject.serialize();
    }

    public boolean validateToken(String token) {
        try {
            // Parse token
            JWSObject jwsObject = JWSObject.parse(token);

            // Verify chữ ký
            boolean isValidSignature =
                    jwsObject.verify(new MACVerifier(SIGNER_KEY.getBytes()));

            if (!isValidSignature) {
                return false;
            }

            // Lấy claims
            JWTClaimsSet claims =
                    JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());

            // Check hết hạn
            return claims.getExpirationTime().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }



}
