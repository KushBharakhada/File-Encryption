import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * EncryptionDecryption.java
 *
 * - Uses the Advanced Encryption Standard (AES) algorithm as a symmetric key encryption algorithm is required.
 * - Uses a 256 bit key size.
 * - Uses PKCS#5 padding to meet the 128 bit block size requirement.
 * - Uses Cipher Block Chaining (CBC) mode with a random 16 byte Initialisation Vector.
 * - Uses the MIME encoding/decoding scheme.
 *
 * Encryption
 * Retrieves the bytes from the original data and encrypts this data using an IV and a Key.
 * The encrypted bytes is concatenated with the IV with the IV bytes at the start. The full message
 * is encoded with the MIME encoding scheme. An encryption tag is then appended to the end of this
 * encoded message to show the message is encrypted.
 *
 * Decryption
 * Removes the encryption tag from the end of the string if the data is encrypted.
 * The message is decoded using the MIME encoding scheme. The message now consists of
 * two parts, the IV (128 bits/16 bytes) and the encrypted data. First 16 bytes is extracted
 * to retrieve the original IV and the rest becomes the encrypted data. Data is decrypted with
 * the IV and the Key (retrieved from user input). Original data is retrieved by converting the
 * decrypted data to a string.
 *
 * @author Kush Bharakhada
 */

public class EncryptionDecryption {

    // Instance variables
    private final static String ENCRYPTION_TYPE = "AES";
    private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static SecretKey createKey() {
        SecretKey key = null;
        final int KEY_SIZE = 256;

        try {
            // Generate a strong random number
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
            // Create a 256 bit key using the random number
            keyGenerator.init(KEY_SIZE, secureRandom);
            key = keyGenerator.generateKey();

            GUIPanel.appendInformationMessage("A key has been generated. If you use this key to encrypt, ONLY this" +
                    " key again can be used to decrypt the message. Losing this key can lead to losing access to" +
                    " the message.");
        }
        catch (NoSuchAlgorithmException e) {
            GUIPanel.appendInformationMessage("An error has occurred in key creation. Try generating" +
                    " a new key or restart the application.");
        }
        return key;
    }

    public byte[] createIV() {
        // Initialize the byte array
        final int DEFAULT_BLOCK_SIZE = 128;
        final int BITS_TO_BYTES = 8;
        byte[] iv = new byte[DEFAULT_BLOCK_SIZE / BITS_TO_BYTES];

        SecureRandom secureRandom = new SecureRandom();
        // Fill it with random bytes
        secureRandom.nextBytes(iv);
        return iv;
    }

    public String encrypt(String data, SecretKey key) {
        try {
            if (!FormattingAndConversion.isDataEncrypted(data)) {
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);

                // Creating an initialisation vector
                byte[] iv = createIV();
                // Specifying the initialisation vector
                IvParameterSpec ivPSpec = new IvParameterSpec(iv);
                // Initialising encryption cipher
                cipher.init(Cipher.ENCRYPT_MODE, key, ivPSpec);

                // Retrieve the data in bytes and encrypt the data
                byte[] dataInBytes = data.getBytes();
                byte[] encryptedDataBytes = cipher.doFinal(dataInBytes);

                // Append the IV bytes to the start of the encrypted data bytes array
                byte[] encryptedDataAndIVBytes = FormattingAndConversion.concatBytes(cipher.getIV(),encryptedDataBytes);
                String encodeAll = FormattingAndConversion.encodeData(encryptedDataAndIVBytes);
                // Add a tag to show the data is encrypted
                String dataWithTag = FormattingAndConversion.addEncryptionTag(encodeAll);

                GUIPanel.appendInformationMessage("Data has been encrypted.");
                return dataWithTag;
            }
            else {
                GUIPanel.appendInformationMessage("This data is already encrypted and cannot be encrypted again.");
            }
        }
        catch (Exception e) {
            GUIPanel.appendInformationMessage("An error has occurred during encryption." +
                    " Retry encryption or restart the application.");
        }
        // Keeps the data unchanged otherwise if encryption process failed
        return data;
    }

    public String decrypt(String encryptedData, SecretKey key) {
        try {
            if (FormattingAndConversion.isDataEncrypted(encryptedData)) {
                // Remove the tag so the message can be decrypted
                String tagRemovedData = FormattingAndConversion.removeEncryptionTag(encryptedData);

                Cipher cipher = Cipher.getInstance(TRANSFORMATION);

                // Decode and decrypt the data
                byte[] encryptedMessageBytes = FormattingAndConversion.decodeData(tagRemovedData);

                // Split the bytes into the IV and Data
                byte[] iv = FormattingAndConversion.getIVBytes(encryptedMessageBytes);
                byte[] encryptedDataBytes = FormattingAndConversion.getDataBytes(encryptedMessageBytes);

                // Specifying the initialisation vector
                IvParameterSpec ivPSpec = new IvParameterSpec(iv);
                // Initialising decryption cipher
                cipher.init(Cipher.DECRYPT_MODE, key, ivPSpec);

                byte[] decryptedDataBytes = cipher.doFinal(encryptedDataBytes);

                GUIPanel.appendInformationMessage("Data has been decrypted.");
                // Retrieve the original message and return it
                return new String(decryptedDataBytes);
            }
            else {
                GUIPanel.appendInformationMessage("This data is already decrypted and cannot be decrypted again.");
            }
        }
        catch (BadPaddingException keyException) {
            GUIPanel.appendInformationMessage("Unable to decrypt the data with the given key.");
        }
        catch (Exception e) {
            GUIPanel.appendInformationMessage("An error has occurred during decryption." +
                    " Retry decryption or restart the application.");
        }
        // Return the input data if data could not be decrypted
        return encryptedData;
    }

    public static SecretKey stringToKey(String key) {
        byte[] keyInByteFormat = FormattingAndConversion.hexToBytes(key);
        // Converting the key to a key object
        SecretKey keyObj = new SecretKeySpec(keyInByteFormat, 0, keyInByteFormat.length, ENCRYPTION_TYPE);
        return keyObj;
    }

}
