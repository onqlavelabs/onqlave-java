package com.onqlave.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import com.onqlave.encryption.Encryption;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class Example {
    // Reset
    public static final String RESET = "\u001B[0m";

    // Bold Colors
    public static final String RED_BOLD = "\u001B[31;1m";
    public static final String GREEN_BOLD = "\u001B[32;1m";
    public static final String YELLOW_BOLD = "\u001B[33;1m";
    public static final String FILE_CONFIGURATION = "YOUR_FILE_CONFIGURATION.json";
    public static final String FILE_INPUT_STREAM = "src/main/java/com/onqlave/example/data.in";
    public static final String FILE_OUTPUT_STREAM = "src/main/java/com/onqlave/example/data.o";
    public static final String FILE_DECRYPTED_STREAM = "src/main/java/com/onqlave/example/dataout.in";
    public static Encryption encryption;

    public static void main(String args[]) {
        InitialEncryption();
        EncryptionPlainTextExample();
        EncryptionStreamExample();
    }

    public static void InitialEncryption() {
        ObjectMapper configMapper = new ObjectMapper();
        Configuration configuration;

        Path path = Paths.get(FILE_CONFIGURATION);
        try {
            configuration = configMapper.readValue(new File(path.toAbsolutePath().toString()), Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Credential credential = new Credential(configuration.accessKey, configuration.signingKey, configuration.secretKey);

        RetrySettings retry = new RetrySettings(configuration.maxRetries, Duration.ofSeconds(configuration.waitTime), Duration.ofSeconds(configuration.maxWaitTime));

        encryption = new Encryption(credential, retry, configuration.arxURL, configuration.debug);
    }

    public static void EncryptionPlainTextExample() {
        String plainText = "This is a plain text string";
        String associatedData = "this data needs to be authenticated, but not encrypted";

        System.out.println(GREEN_BOLD + "=======================START ENCRYPTION ==========================" + RESET);
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

        System.out.printf(YELLOW_BOLD + "Decrypted result: %s\n" + RESET, new String(decrypted));
        System.out.println(GREEN_BOLD + "======================= END ENCRYPTION ==========================\n\n\n" + RESET);
    }

    public static void EncryptionStreamExample() {
        String associatedData = "this data needs to be authenticated, but not encrypted";
        System.out.println(GREEN_BOLD + "=======================START ENCRYPTION STREAM ==========================" + RESET);
        InputStream encryptPlainStream = readInputStream(FILE_INPUT_STREAM);
        ByteArrayOutputStream encryptCipherStream = new ByteArrayOutputStream();
        try {
            encryption.encryptStream(encryptPlainStream, encryptCipherStream, associatedData.getBytes());
            writeOutputStream(FILE_OUTPUT_STREAM, encryptCipherStream);
        } catch (Exception e) {
            System.out.println(RED_BOLD + "Encrypted Stream EXCEPTION: " + e.getMessage() + RESET);
        } finally {
            try {
                encryptPlainStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream dataEncrypted = readInputStream(FILE_OUTPUT_STREAM);
        ByteArrayOutputStream decryptPlainStream = new ByteArrayOutputStream();

        try {
            encryption.decryptStream(dataEncrypted, decryptPlainStream, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(RED_BOLD + "Decrypted Stream EXCEPTION: " + e.getMessage() + RESET);
        } finally {
            try {
                dataEncrypted.close();
                encryptCipherStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeOutputStream(FILE_DECRYPTED_STREAM, decryptPlainStream);
        System.out.println(GREEN_BOLD + "=======================END ENCRYPTION A STREAM ==========================\n\n\n");
    }

    public static InputStream readInputStream(String fileName) {
        InputStream encryptPlainStream = null;
        Path pathFileInputStream = Paths.get(fileName);
        try {
            encryptPlainStream = new FileInputStream(pathFileInputStream.toAbsolutePath().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptPlainStream;
    }

    public static void writeOutputStream(String fileName, ByteArrayOutputStream fileOutputStream) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            fileOutputStream.writeTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}