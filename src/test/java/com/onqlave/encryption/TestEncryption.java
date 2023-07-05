package com.onqlave.encryption;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import org.junit.Test;

import java.time.Duration;


//{
//        "access_key": "onq.QO2RW7gd55ktGUcCiZKPoKTSORxyubUl",
//        "arx_url": "https://dp0.onqlave.io/cluster--lfGOnCmR9-niDEN5aAiBG",
//        "server_signing_key": "onq.gAYG1wqKOtwcwHtQ76lOf2NBglxLCM3c",
//        "server_secret_key": "onq.2PFqFaRQrLoSzQ6y5oVE6AOwAhZdzb8XKQbnzq2iXTEGMLcsvQEPiygfzvgndMvLFOSN9XA26ZTkXj4MCQtbFv0MKfj8sqtrADEMaPth1FH6VihQAZ9NlmOwyjilDBah",
//        "client_key": ""
//        }



//{
//        "access_key": "onq.w7wVODbp7PZm3yygIFQ32GTi5A7mmQxm",
//        "arx_url": "http://localhost:8090/cluster--6dtMjth2bTlFhemsZ-mHv",
//        "server_signing_key": "onq.DKlEchZOlz1RMpUoMIXF8lznqaO2UwSo",
//        "server_secret_key": "onq.r2usR90n0YbLyXzMXEdUWBWQ8k4km8O3riMzqoOl143JAwJuNVjuMWiwGKCNJqQYkbtO0CQZHoJYpJg3KRSanJbN1SJTGJ9ulmGTvigEP819hjnDDi2gI4PpNlBFWrQC",
//        "client_key": ""
//        }


//{
//        "access_key": "onq.PUaOUCJ2NfuM7CuIvBw0RIqWek4mRC89",
//        "arx_url": "https://dp0.onqlave.io/cluster--0YXcpkP0vTNFuzwQXYnST",
//        "server_signing_key": "onq.XacZ7FyCkgQN5qsOuFe33rGVWvz6igZc",
//        "server_secret_key": "onq.qACMyOPUjADyHhXkn03FR8r7FOVThORISdqahBWSxSxm7Gta0NNnZukJHKsTI2qmHKcIuQ2hkSJuH1FpJlGTuhQdzCvq79lJ5uEFK5zxePcGcSQABvNy1MVCX4VNqP9J",
//        "client_key": ""
//        }
public class TestEncryption {

    @Test
    public void TestEncryption() throws Exception {
        Credential credential = new Credential("onq.PUaOUCJ2NfuM7CuIvBw0RIqWek4mRC89",
                "onq.XacZ7FyCkgQN5qsOuFe33rGVWvz6igZc",
                "onq.qACMyOPUjADyHhXkn03FR8r7FOVThORISdqahBWSxSxm7Gta0NNnZukJHKsTI2qmHKcIuQ2hkSJuH1FpJlGTuhQdzCvq79lJ5uEFK5zxePcGcSQABvNy1MVCX4VNqP9J");

        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(30), Duration.ofSeconds(30));
        Encryption enc = new Encryption(credential, retry, "https://dp0.onqlave.io/cluster--0YXcpkP0vTNFuzwQXYnST", true);

        String plainText = "This is a plain text string";
        byte[] encrypted = new byte[0];
        try {

            encrypted = enc.Encrypt(plainText.getBytes(), null);
        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            enc.Decrypt(encrypted, null);
        } catch (Exception e) {

        }
    }


}
