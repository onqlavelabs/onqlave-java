package com.onqlave.example;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import com.onqlave.encryption.Encryption;
import com.onqlave.types.OnqlaveStructure;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Example {
    // Reset
    public static final String RESET = "\u001B[0m";

    // Bold Colors
    public static final String RED_BOLD = "\u001B[31;1m";
    public static final String GREEN_BOLD = "\u001B[32;1m";
    public static final String YELLOW_BOLD = "\u001B[33;1m";
    public static final String FILE_CONFIGURATION = "YOUR_FILE_CONFIGURATION.json";

    public static void main(String args[]) {
        ObjectMapper configMapper = new ObjectMapper();
        Configuration configuration;

        Path path = Paths.get(FILE_CONFIGURATION);
        try {
            configuration = configMapper.readValue(new File(path.toAbsolutePath().toString()), Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Credential credential = new Credential(configuration.accessKey, configuration.signingKey, configuration.secretKey);

        RetrySettings retry = new RetrySettings(configuration.maxRetries,
                Duration.ofSeconds(configuration.waitTime),
                Duration.ofSeconds(configuration.maxWaitTime));

        Encryption enc = new Encryption(credential, retry, configuration.arxURL, configuration.debug);

        String plainText = "This is a plain text string";
        String associatedData = "this data needs to be authenticated, but not encrypted";

        System.out.println(GREEN_BOLD + "=======================START ENCRYPTION ==========================" + RESET);
        byte[] cipherData = null;
        try {
            cipherData = enc.encrypt(plainText.getBytes(), associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        byte[] decrypted = null;
        try {
            decrypted = enc.decrypt(cipherData, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.printf(YELLOW_BOLD + "Decrypted result: %s\n" + RESET, new String(decrypted));
        System.out.println(GREEN_BOLD + "======================= END ENCRYPTION ==========================\n\n\n" + RESET);

        System.out.println(GREEN_BOLD + "=======================START ENCRYPTION STREAM ==========================" + RESET);
        InputStream encryptPlainStream = new ByteArrayInputStream(plainText.getBytes());
        ByteArrayOutputStream encryptCipherStream = new ByteArrayOutputStream();
        try {
            enc.encryptStream(encryptPlainStream, encryptCipherStream, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(RED_BOLD + "Encrypted Stream EXCEPTION: " + e.getMessage() + RESET);
        }

        ByteArrayInputStream dataEncrypted = new ByteArrayInputStream(encryptCipherStream.toByteArray());
        ByteArrayOutputStream decryptPlainStream = new ByteArrayOutputStream();

        try {
            enc.decryptStream(dataEncrypted, decryptPlainStream, associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(RED_BOLD + "Decrypted Stream EXCEPTION: " + e.getMessage() + RESET);
        }
        System.out.printf(YELLOW_BOLD + "Decrypt stream result: %s\n", new String(decryptPlainStream.toByteArray()) + RESET);
        System.out.println(GREEN_BOLD + "=======================END ENCRYPTION A STREAM ==========================\n\n\n");
    }
}