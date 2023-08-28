package com.onqlave.encryption;

import java.io.*;

import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import org.junit.Test;
import java.lang.String;
import java.time.Duration;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assert.assertEquals;

class TestEncryption {

    Encryption encryption;
    // Constructor
    TestEncryption(String accessKey, String arxURL, String signingKey, String secretKey) {
        encryption = createEncryption(accessKey, signingKey, secretKey, arxURL);
        compareEncryptedDecryptedText();
        compareEncryptedDecreptedStream();
    }

    private Encryption createEncryption(String accessKey, String signingKey, String secretKey, String arxURL) {
        // if null then skip test
        assumeFalse(nullOrEmpty(accessKey, signingKey, secretKey, arxURL));
        Credential credential = new Credential(accessKey, signingKey, secretKey);
        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(1), Duration.ofSeconds(1));
        return new Encryption(credential, retry, arxURL, Boolean.FALSE);
    }
    private void compareEncryptedDecryptedText() {
        String plainText = "This is a plain text string";
        String associatedData = "this data needs to be authenticated, but not encrypted";

        byte[] cipherData = null;
        try {
            cipherData = encryption.encrypt(plainText.getBytes(), associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        byte[] decrypted = null;
        try {
            decrypted = encryption.decrypt(cipherData, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(plainText, new String(decrypted));
    }
    private void compareEncryptedDecreptedStream() {
        // Prepare sample data
        String associatedData = "this data needs to be authenticated, but not encrypted";
        String plainText = "This is a plain text string";
        writeToFile("plaintext.txt", plainText);

        // Read file and encrypt
        InputStream encryptPlainStream = readInputStream("plaintext.txt");
        ByteArrayOutputStream encryptedCipherStream = new ByteArrayOutputStream();
        try {
            encryption.encryptStream(encryptPlainStream, encryptedCipherStream, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                encryptPlainStream.close();
                encryptedCipherStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Decrypt cipher stream
        InputStream dataEncrypted = toInputStream(encryptedCipherStream);
        ByteArrayOutputStream decryptPlainStream = new ByteArrayOutputStream();
        try {
            encryption.decryptStream(dataEncrypted, decryptPlainStream, associatedData.getBytes());
            writeToFile("decryptedstream.txt", decryptPlainStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dataEncrypted.close();
                decryptPlainStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // compare 2 files content plaintext.txt and decryptedstream.txt
        assertEquals( readFile("plaintext.txt"), readFile("decryptedstream.txt"));

        // remove file
        removeFile(new String[]{"plaintext.txt", "decryptedstream.txt"});
    }
    // ByteArrayOutputStream to InputStream
    private InputStream toInputStream(ByteArrayOutputStream baos) {
        return new ByteArrayInputStream(baos.toByteArray());
    }
    // Read file
    private String readFile(String fileName) {
        String content = "";
        try {
            content = new String(readInputStream(fileName).readAllBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }
    private void writeToFile(String fileName, String content) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void writeToFile(String fileName, ByteArrayOutputStream content) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(content.toByteArray());
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static InputStream readInputStream(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return inputStream;
    }
    private void removeFile(String[] fileNames) {
        for (String fileName : fileNames) {
            removeFile(fileName);
        }
    }
    private void removeFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private static boolean nullOrEmpty(String ...values) {
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
