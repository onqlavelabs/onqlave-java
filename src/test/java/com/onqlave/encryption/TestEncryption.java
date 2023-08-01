//package com.onqlave.encryption;
//
//import com.onqlave.contract.Credential;
//import com.onqlave.contract.RetrySettings;
//import org.junit.Test;
//
//import java.time.Duration;
//
//public class TestEncryption {
//
//    @Test
//    public void TestEncryption() throws Exception {
//        Credential credential = new Credential("",
//                "",
//                "");
//
//        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(30), Duration.ofSeconds(30));
//        Encryption enc = new Encryption(credential, retry, "", true);
//
//        String plainText = "This is a plain text string";
//        byte[] encrypted = new byte[0];
//        try {
//
//            encrypted = enc.Encrypt(plainText.getBytes(), new byte[0]);
//        } catch (Exception e) {
//            throw e;
//        }
//
//
//        try {
//            byte[] got = enc.Decrypt(encrypted, new byte[0]);
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//
//}
