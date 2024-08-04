package com.food_delivery_app.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.AlgorithmConstraints;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.key}")
    private String key;

    private String USERNAME="username";

    private Algorithm algorithm;

    @PostConstruct
    public void construct(){
        algorithm =Algorithm.HMAC256(key);
    }

    public String generateToken(String username){

        return JWT.create()
                .withClaim(USERNAME, username)
                .withExpiresAt(new Date(System.currentTimeMillis()+ expiry))
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public String findusername(String token){
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);

        String username = verify.getClaim(USERNAME).asString();
        return username;
    }

}
