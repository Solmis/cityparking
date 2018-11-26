package net.solmis.cityparking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Authenticator {

    private static String secretToken;

    @Value("${config.secretToken}")
    public void setSecretToken(String secret) {
        secretToken = secret;
    }

    public static boolean tokenIsValid(String token) {
        return token != null && token.equals(secretToken);
    }

}
