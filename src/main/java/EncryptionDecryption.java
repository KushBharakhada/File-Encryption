import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
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
            GUI.appendInformationMessage("A key has been generated. If you use this key to encrypt, ONLY this" +
                    " key again can be used to decrypt the message. Losing this key can lead to losing access to" +
                    " the message.");
        }
        catch (NoSuchAlgorithmException e) {
            GUI.appendInformationMessage("An error has occurred in key creation. Try generating" +
                    " a new key or restart the application.");
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

                GUI.appendInformationMessage("Data has been encrypted.");
                return dataWithTag;
            }
            else {
                GUI.appendInformationMessage("This data is already encrypted and cannot be encrypted again.");
                //return data;
            }
        }
        catch (Exception e) {
            GUI.appendInformationMessage("An error has occurred during encryption." +
                    " Retry encryption or restart the application.");
            //return data;
        }
        return data;
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

                GUI.appendInformationMessage("Data has been decrypted.");
                // Decrypted message
                return new String(decryptedDataBytes);
            }
            else {
                GUI.appendInformationMessage("This data is already decrypted and cannot be decrypted again.");
            }
        }
        catch (BadPaddingException keyException) {
            GUI.appendInformationMessage("Unable to decrypt the data with the given key.");
        }
        catch (Exception e) {
            e.printStackTrace(); // REMOVE THIS STATEMENT
            GUI.appendInformationMessage("An error has occurred during decryption." +
                    " Retry decryption or restart the application.");
        }
        // Return the input data if data could not be decrypted
        return encryptedData;
    }

    public static SecretKey stringToKey(String key) {
        // Converting a key in text to a key object
        byte[] decodedKey = FormattingAndConversion.decodeData(key);
        SecretKey keyObj = new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYPTION_TYPE);
        return keyObj;
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
        String encrypted = test.encrypt(text, stringToKey(testKey));
        System.out.println(encrypted);

        System.out.println("----- DECRYPTED -----");
        System.out.println(test.decrypt(encrypted, stringToKey(testKey)));

    }

}

// Decryption when opening file not working
