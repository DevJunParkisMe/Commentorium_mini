package project.pnu.config;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class JwtUtil {
   // HMACSHA256 알고리즘에 사용할 안전한(secret) 키
    private static final SecretKey SECRET_KEY = generateSecretKey();

    public static SecretKey getSecretKey() {
        // HMACSHA256 알고리즘에 사용할 고정된(secret) 키를 반환합니다.
        return SECRET_KEY;
    }
    
    private static SecretKey generateSecretKey() {
        try {
            // HMACSHA256 알고리즘에 사용할 랜덤한(secret) 키를 생성합니다.
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate secret key", e);
        }
    }
}
