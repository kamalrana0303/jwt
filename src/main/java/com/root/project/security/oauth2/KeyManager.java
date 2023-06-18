package com.root.project.security.oauth2;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class KeyManager {
    public RSAKey rsaKey(){
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
            g.initialize(2048);
            KeyPair keyPair = g.generateKeyPair();
            RSAPublicKey aPublic =(RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
            return new RSAKey.Builder(aPublic).privateKey(aPrivate).keyID(UUID.randomUUID().toString()).build();
        } catch (NoSuchAlgorithmException e) {
            new RuntimeException("RSA key not generated");
        }
        return null;
    }
}
