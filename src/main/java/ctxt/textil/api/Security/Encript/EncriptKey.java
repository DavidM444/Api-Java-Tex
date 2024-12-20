package ctxt.textil.api.Security.Encript;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class providing encryption and hashing functionality for secure password handling.
 * This class includes methods for HMAC-SHA256 encryption and BCrypt password hashing.
 */
public class EncriptKey {
    /**
     * Encrypts a key using HMAC-SHA256 algorithm with the provided API secret.
     *
     * @param key       The string to be encrypted
     * @param apiSecret The secret key used for encryption
     * @return The encrypted string in hexadecimal format
     * @throws NoSuchAlgorithmException If the HMAC-SHA256 algorithm is not available
     * @throws InvalidKeyException      If the provided API secret is invalid
     */
    public static String Encript(String key, String apiSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256.init(secretKeySpec);
        byte[] encriptedBytes = sha256.doFinal(key.getBytes(StandardCharsets.UTF_8));
        return bytesString(encriptedBytes);
    }

    /**
     * Converts a byte array to its hexadecimal string representation.
     *
     * @param encriptedBytes The byte array to convert
     * @return A hexadecimal string representation of the byte array
     */
    private static String bytesString(byte[] encriptedBytes) {
        StringBuilder result = new StringBuilder();
        for(byte b: encriptedBytes){
            result.append(String.format("%02x",b));
        }
        return  result.toString();
    }

    /**
     * Hashes a password using BCrypt with a randomly generated salt.
     * BCrypt is an adaptive hash function based on the Blowfish cipher,
     * specifically designed for password hashing.
     *
     * @param key The password to hash
     * @return A string containing the BCrypt hashed password with its salt
     *         Format: $2a$[work factor]$[22 character salt][31 character hash]
     */
    public static String BycriptKeydd(String key) {
        return BCrypt.hashpw(key, BCrypt.gensalt());
    }
}
