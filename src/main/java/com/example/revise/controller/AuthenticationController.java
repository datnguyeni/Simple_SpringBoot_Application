package com.example.revise.controller;

import com.example.revise.entity.User;
import com.example.revise.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws JOSEException {
        String generatedToken =  authenticationService.authenticate(user.getUsername(), user.getPassword());
        return generatedToken;
    }

    @PostMapping("/introspect")
    public String introspect(@RequestBody String generatedToken) throws JOSEException {
        if (!authenticationService.validateToken(generatedToken)) {
            return "invalid token";
        }
        return "success";
    }



}
