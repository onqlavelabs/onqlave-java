//package com.onqlave.encryption;
//
//import com.onqlave.contract.Credential;
//import com.onqlave.contract.RetrySettings;
//import com.onqlave.types.OnqlaveStructure;
//import org.junit.Test;
//
//import java.io.*;
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestEncryptionStream {
//    @Test
//    public void TestEncryptionStream() throws Exception {
//        Credential credential = new Credential("", "", "");
//
//        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(30), Duration.ofSeconds(30));
//        Encryption enc = new Encryption(credential, retry, "", true);
//
//        String plaintext = "This is plaintext";
//        InputStream input = new ByteArrayInputStream(plaintext.getBytes());
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        try {
//
//            enc.EncryptStream(input, output, new byte[0]);
//            System.out.println(output.size());
//            //System.out.println("Encrypted DataStructure here: " + encodeString);
//        } catch (Exception e) {
//            System.out.println("Encrypted DataStructure EXCEPTION: " + e);
//        }
//
//        ByteArrayInputStream dataEncrypted = new ByteArrayInputStream(output.toByteArray());
//        ByteArrayOutputStream plainStream = new ByteArrayOutputStream();
//
//        try {
//            enc.DecryptStream(dataEncrypted,plainStream, new byte[0]);
//            String finalString = new String(plainStream.toByteArray());
//            System.out.println(finalString);
//        } catch (Exception e) {
//            System.out.println("Decrypted DataStructure EXCEPTION: " + e);
//        }
//
//    }
//
//}
