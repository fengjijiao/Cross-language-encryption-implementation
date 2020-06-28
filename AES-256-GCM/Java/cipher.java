import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES_GCM_Example
{
    static String plainText = "hello aes-256-gcm !";
    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public static void main(String[] args) throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE);
       
        // Generate Key
        // SecretKey key = keyGenerator.generateKey();
        // byte[] IV = new byte[GCM_IV_LENGTH];
        // SecureRandom random = new SecureRandom();
        // random.nextBytes(IV);
        
        SecretKey key = getSecretKey("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4");
        byte[] IV = hexStringToByteArray("000000000000000000000000");
        System.out.println("Original Text : " + plainText);
        
        byte[] cipherText = encrypt(plainText.getBytes(), key, IV);
        System.out.println("Encrypted Text : " + bytesToHexString(cipherText));
        
        String decryptedText = decrypt(cipherText, key, IV);
        System.out.println("DeCrypted Text : " + decryptedText);
    }
    
    public static String SecretKeyToString(SecretKey key) {
        // create new key
        //SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        // get base64 encoded version of the key
        return bytesToHexString(key.getEncoded());
    }
    
    public static SecretKey getSecretKey(String encodedKey) {
        // decode the base64 encoded string
        byte[] decodedKey = hexStringToByteArray(encodedKey);
        // rebuild key using SecretKeySpec
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
    }
    
      /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }
    
    /**
     * byte数组转16进制字符串
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception
    {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        
        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        
        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        
        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);
        
        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) throws Exception
    {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        
        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        
        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        
        // Perform Decryption
        byte[] decryptedText = cipher.doFinal(cipherText);
        
        return new String(decryptedText);
    }
}
