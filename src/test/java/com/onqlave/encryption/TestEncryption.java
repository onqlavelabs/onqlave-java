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


//TODO: AES-128
//{
//        "access_key": "onq.Q9sWg4jaH3R2VEqDYgvpEERuRMVRrQTH",
//        "arx_url": "https://dp0.onqlave.com/cluster--n4urZ76lCDRk2_f2w0jmq",
//        "server_signing_key": "onq.vbFmb3OzVQcF30jfm8HcpwGdrcl0C7mW",
//        "server_secret_key": "onq.Iia6CEUobDf5KN07b3K1Sg50VAtoSoFW2gtmVOAibK7XgXZozzS7pRXdWr9fbF4LXrK0RfXwSDME279pa0nCuvBekWkyD8FnOQFyXwaTm8qUk4UtrKWrZsYcoTqQveIW",
//        "client_key": ""
//        }
public class TestEncryption {

    @Test
    public void TestEncryption() throws Exception {
        Credential credential = new Credential("onq.Q9sWg4jaH3R2VEqDYgvpEERuRMVRrQTH",
                "onq.vbFmb3OzVQcF30jfm8HcpwGdrcl0C7mW",
                "onq.Iia6CEUobDf5KN07b3K1Sg50VAtoSoFW2gtmVOAibK7XgXZozzS7pRXdWr9fbF4LXrK0RfXwSDME279pa0nCuvBekWkyD8FnOQFyXwaTm8qUk4UtrKWrZsYcoTqQveIW");

        RetrySettings retry = new RetrySettings(1, Duration.ofSeconds(30), Duration.ofSeconds(30));
        Encryption enc = new Encryption(credential, retry, "https://dp0.onqlave.com/cluster--n4urZ76lCDRk2_f2w0jmq", true);

        String plainText = "This is a plain text string";
        byte[] encrypted = new byte[0];
        try {

            encrypted = enc.Encrypt(plainText.getBytes(), new byte[0]);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }


        try {
            byte[] got = enc.Decrypt(encrypted, new byte[0]);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }


}
