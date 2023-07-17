package com.onqlave.example;

import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import com.onqlave.encryption.Encryption;
import com.onqlave.types.OnqlaveStructure;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

    public static void main(String args[]) {
        Dotenv dotenv = Dotenv.load();
        Credential credential = new Credential(dotenv.get("ACCESS_KEY"), dotenv.get("SIGNING_KEY"), dotenv.get("SECRET_KEY"));

        RetrySettings retry = new RetrySettings(Integer.parseInt(dotenv.get("MAX_RETRIES")),
                Duration.ofSeconds(Integer.parseInt(dotenv.get("WAIT_TIME"))),
                Duration.ofSeconds(Integer.parseInt(dotenv.get("MAX_WAIT_TIME"))));

        Encryption enc = new Encryption(credential, retry, dotenv.get("ARX_URL"),Boolean.parseBoolean(dotenv.get("DEBUG")) );

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
            enc.decryptStream(dataEncrypted,decryptPlainStream,associatedData.getBytes());
        } catch (Exception e) {
            System.out.println(RED_BOLD + "Decrypted Stream EXCEPTION: " + e.getMessage() + RESET);
        }
        System.out.printf(YELLOW_BOLD + "Decrypt stream result: %s\n", new String(decryptPlainStream.toByteArray()) + RESET);
        System.out.println(GREEN_BOLD + "=======================END ENCRYPTION A STREAM ==========================\n\n\n");
    }
}