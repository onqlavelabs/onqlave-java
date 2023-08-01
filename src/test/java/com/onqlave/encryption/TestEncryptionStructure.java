//package com.onqlave.encryption;
//
//import com.onqlave.contract.Credential;
//import com.onqlave.contract.RetrySettings;
//import com.onqlave.types.OnqlaveStructure;
//import org.junit.Test;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestEncryptionStructure {
//
//
//    @Test
//    public  void TestEncryptionStructure() throws Exception {
//        Credential credential = new Credential("",
//                "",
//                "");
//
//        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(30), Duration.ofSeconds(30));
//        Encryption enc = new Encryption(credential, retry, "", true);
//
//       // String plainText = "This is a plain text string";
//
//        Map<String, Object> plainStructure = GetPlaintextStructTest();
//
//
//
//        byte[] encryptedDataBytes = new byte[0];
//        OnqlaveStructure encryptedData = null;
//        try {
//             encryptedData  = enc.EncryptStructure(plainStructure,  new byte[0]);
//            String encodeString =  Base64.getEncoder().encodeToString(encryptedData.getEdk());
//            System.out.println(
//                    "Encrypted DataStructure here: " + encodeString
//            );
//        }catch (Exception e){
//            System.out.println(
//                    "Encrypted DataStructure EXCEPTION: " + e
//            );
//        }
//        // Decryption Data
//        try {
//            Map<String, Object> decryptedData = enc.DecryptStructure(encryptedData,  new byte[0]);
//            System.out.println(Arrays.asList(decryptedData));
//        }
//        catch (Exception e){
//            System.out.println(
//                    "Decrypted DataStructure EXCEPTION: " + e
//            );
//        }
//
//    }
//     private Map<String, Object> GetPlaintextStructTest(){
//        Map<String, Object > plaintextStruct = new HashMap<>();
//        plaintextStruct.put("id", 1);
//        plaintextStruct.put("name", "C");
//        plaintextStruct.put("major", "Software Developer");
//
//        return plaintextStruct;
//    }
//}
