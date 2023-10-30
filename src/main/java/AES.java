import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;


public class AES {
    private static final String SEC_KEY="CS2212MAPSYSTEM";
    private static SecretKeySpec secrekey;
    private static byte[] key;

    private static void convToByte(String newKey) throws Exception {
        try{
            key=newKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key=sha.digest(key);
            key=Arrays.copyOf(key, 16);
            secrekey= new SecretKeySpec(key, "AES");
        }catch(Exception e) {
            throw new Exception();
        }


    }
    public static String encryptPW(String encPW) throws Exception {

            convToByte(SEC_KEY);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secrekey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(encPW.getBytes("UTF-8")));


    }
    public static String decryptPW(String decPW) throws Exception {

            convToByte(SEC_KEY);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secrekey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(decPW)));

    }
}
