package com.sir.todorestservicewithdynamodb.security;

import com.sir.todorestservicewithdynamodb.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class CustomEncoder implements PasswordEncoder {

    @Value("${ssecurity.jjwt.password.encoder.secret}")
    private String secret;

    @Value("${ssecurity.jjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${ssecurity.jjwt.password.encoder.keylength}")
    private Integer keylength;

    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
