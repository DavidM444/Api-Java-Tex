package ctxt.textil.api.Security.Encript;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class EncriptKey {


    public static String Encript(String key,String apiSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256.init(secretKeySpec);
        byte[] encriptedBytes = sha256.doFinal(key.getBytes(StandardCharsets.UTF_8));
        return bytesString(encriptedBytes);
    }

    private static String bytesString(byte[] encriptedBytes) {
        StringBuilder result = new StringBuilder();
        for(byte b: encriptedBytes){
            result.append(String.format("%02x",b));
        }
        return  result.toString();

    }
    public static String BycriptKeydd(String key){
        return BCrypt.hashpw(key, BCrypt.gensalt());
        // Generar el hash de la clave
    }
}
