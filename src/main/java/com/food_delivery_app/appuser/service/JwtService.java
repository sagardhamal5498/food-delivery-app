package com.food_delivery_app.appuser.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food_delivery_app.appuser.entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.expire}")
    private int expiry;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.key}")
    private String key;

    Algorithm algorithm;

    @PostConstruct
    public void generateAlgo(){
        algorithm = Algorithm.HMAC256(key);
    }


    private static final  String USER_NAME = "username";

    public String generateToken(AppUser user){
        return JWT.create().withClaim(USER_NAME,user.getUsername())
                          .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                          .withIssuer(issuer)
                          .sign(algorithm);

    }

    public String getUserName(String token) {

       return JWT.require(algorithm).build().verify(token).getClaim(USER_NAME).asString();
    }
}
