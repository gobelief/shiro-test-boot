package com.module1;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.module1.project.pojo.User;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

public class MyJWTest {

    @Test
    public void jwtTest() {
        String token = "";
        long start = System.currentTimeMillis();
        try {
            long now = new Date().getTime();
            System.out.println();
            long now2 = now + 1000;
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withIssuer("auth0").withExpiresAt(new Date(now2)).withSubject("jwt test")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start) / 1000D);
    }
    @Test
    public void gsonTest() {
        Gson g = new Gson();
        User user = new User();
        user.setUsername("asfasdf");
        user.setPassword("sdfasf");
        UUID u = UUID.randomUUID();
        String uid = u.toString().replace("-","").toUpperCase();
        System.out.println(g.toJson(user));

    }
}
