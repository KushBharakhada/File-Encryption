import javax.crypto.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * EncryptionDecryption.java
 *
 * @author Kush Bharakhada
 */

public class EncryptionDecryption {

    private final static String ENCRYPTION_TYPE = "AES";
    private final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static SecretKey createKey() {
        SecretKey key = null;

        try {
            // Generate a strong random number
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
            // Create a 128 bit key using the random number
            keyGenerator.init(128, secureRandom);
            key = keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return key;
    }

    public String encrypt(String data, SecretKey key) {
        try {
            if (!FormattingAndConversion.isDataEncrypted(data)) {
                // Using AES encryption in Electronic Code Book mode with padding scheme PKCS5
                // Initialising encryption cipher
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, key);

                // Retrieve the data in bytes
                byte[] dataInBytes = data.getBytes();
                byte[] encryptedDataBytes = cipher.doFinal(dataInBytes);

                String encodeData = FormattingAndConversion.encodeData(encryptedDataBytes);
                // Add a tag to show the data is encrypted
                String dataWithTag = FormattingAndConversion.addEncryptionTag(encodeData);

                return dataWithTag;
            }
            else {
                System.out.println("Data is already encrypted!");
                return data;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public String decrypt(String encryptedData, SecretKey key) {
        try {
            if (FormattingAndConversion.isDataEncrypted(encryptedData)) {
                // Remove the tag so the message can be decrypted
                String tagRemovedData = FormattingAndConversion.removeEncryptionTag(encryptedData);

                // Initialising decryption cipher
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.DECRYPT_MODE, key);

                // Decode and decrypt the data
                byte[] encryptedDataBytes = FormattingAndConversion.decodeData(tagRemovedData);
                byte[] decryptedDataBytes = cipher.doFinal(encryptedDataBytes);

                // Decrypted message
                return new String(decryptedDataBytes);
            }
            else {
                System.out.println("Data is already decrypted!");
                return encryptedData;
            }
        }
        catch (BadPaddingException keyException) {
            System.out.println("Not a valid Key!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Return the input data if data could not be decrypted
        return encryptedData;
    }

    public static void main(String[] args) {

        // Does not use IV and uses ECB -> less security

        EncryptionDecryption test = new EncryptionDecryption();
        System.out.println("----- ORIGINAL MESSAGE -----");
        String text = "This  is a secret message!";
        System.out.println(text);

        SecretKey key = createKey();

        System.out.println("----- SECRET KEY -----");
        String testKey = "7500C921EF830D9F22AEEF2BB0603F9E";
        System.out.println("The Symmetric Key is: " + testKey);

        System.out.println("----- ENCRYPTED -----");
        String encrypted = test.encrypt(text, key);
        System.out.println(encrypted);

        System.out.println("----- DECRYPTED -----");
        System.out.println(test.decrypt(encrypted, key));

    }


}
