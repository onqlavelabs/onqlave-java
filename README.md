# onqlave-java

# Description
This Java SDK is designed to help developers easily integrate Onqlave `Encryption As A Service` into their Java backend.

[![CI](https://img.shields.io/static/v1?label=CI&message=passing&color=green?style=plastic&logo=github)](https://github.com/onqlavelabs/onqlave-java/actions)
# Table of Contents
- [Description](#description)
- [Table of Contents](#table-of-contents)
    - [Features](#features)
    - [Installation](#installation)
        - [Requirements](#requirements)
        - [Configuration](#configuration)
        - [Usage](#usage)
        - [Encrypt](#encrypt)
        - [Decrypt](#decrypt)
        - [Encrypt Stream](#encrypt-stream)
        - [Decrypt Stream](#decrypt-stream)
    - [Reporting a Vulnerability](#reporting-a-vulnerability)

## Features

- Encrypt/Decrypt piece of information
- Encrypt/Decrypt stream of data

## Installation

### Requirements
- OpenJDK 17 or higher
- Maven >= 3.x.x

### Configuration
Make sure your project is using Maven Modules (it will have a `pom.xml` file in its root if it already is):

```java
mvn install
```
Then, reference onqlave-java in a Java program with import:
```java
import com.onqlave.encryption.Encryption;
```

### Usage

To use this SDK, you firstly need to obtain credential to access an Onqlave Arx by signing up to [Onqlave](https://onqlave.com) and following instruction to create your 1st Onqlave Arx. Documentation can be found at [Onqlave Technical Documentation](https://docs.onqlave.com).

The [Onqlave Java](https://github.com/onqlavelabs/onqlave-java) module is used to perform operations on the configured ARX such as encrypting, and decrypting for an Onqlave_ARX. [example](https://github.com/onqlavelabs/onqlave-java/src/main/java/com.onqlave/example):

To use this module, the Onqlave client must first be initialized as follows.

```java
package com.example;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import com.onqlave.encryption.Encryption;

public class Main {
    public static void main(String[] agrs) {
        Credential credential = new Credential(
                "<api_access_key>",           //This is the API Access Key returned of the API Key created during setup. Keep in a safe place.
                "<api_signing_key>",          //This is the API Signing Key retruned of the API Key created during setup. Keep in a safe place.
                "<api_secret_key>");          //This is the API Secret Key retruned of the API Key created during setup. Keep in a safe place.
        RetrySettings retry = new RetrySettings(
                "<count>",                //Number of times to retry calling server endpoints in case of connection issue
                "<wait_time>",            //How long to wait between each retry
                "<max_wait_time>");        //How long to wait in total for operation to finis
        // Initial
        Encryption encryption = new Encryption(credential, retry, "<arx_url>", true);
    }
}
```
All Onqlave APIs must be invoked using a `Encryption` instance.
### Encrypt
To encrypt data, use the **Encrypt(byte[] planData, byte[] associatedData)** method of the Encryption service. The plainText parameter is the `byte[]` representation of data you are wishing to encrypt. The associatedData parameter the `byte[]` representation of associated data which can be used to improve the authenticity of the data (it is not mandatory), as shown below.

Encryption call:
```java
    Encryption serivce = new Encryption(credential, retry, "<arx_url>", true);
    String plainText = "This is a plain text string";
    String associatedData = "this data needs to be authenticated, but not encrypted";
    byte[] cipherData = null;
    try {
        cipherData = serivce.Encrypt(plainText.getBytes(), associatedData.getBytes());
    } catch (Exception e) {
        //TODO: handle exception here.
        System.out.println(e.getMessage());
    }
```
### Decrypt
To decrypt data, use the **Decrypt(byte[] cipherText, byte[] associatedData)** method of the `Encryption` service. The **cipherData** parameter is the `byte[]` representation of data you are wishing to decrypt (previously encrypted). The **associatedData** parameter the `byte[]` representation of associated data which can be used to improve the authenticity of the data (it is not mandatory), as shown below.

Decryption call:
```java
    Encryption serivce = new Encryption(credential, retry, "<arx_url>", true);
    String plainText = "This is cipher data is already encrypted using `Encrypt` method";
    String associatedData = "this data needs to be authenticated, but not encrypted";
    byte[] cipherData = null;
    try {
        cipherData = serivce.Decrypt(plainText.getBytes(),associatedData.getBytes());
    } catch (Exception e) {
        //TODO: handle exception here.
        System.out.println(e.getMessage());
    }
```

### Encrypt Stream
To encrypt stream of data, use the **EncryptStream(InputStream plainStream, OutputStream cipherStream, byte[] associatedData)** method of the `Encryption` service. The **plainStream** parameter is the `InputStream` stream of data you are wishing to encrypt. The **cipherStream** parameter is the `OutputStream` stream you are wishing to write the cipher data to. The **associatedData** parameter the `byte[]` representation of associated data which can be used to improve the authenticity of the data (it is not mandatory), as shown below.
```java
    Encryption serivce = new Encryption(credential, retry, "<arx_url>", true);
    String plainText = "This is cipher data is already encrypted using `Encrypt` method";
    String associatedData = "this data needs to be authenticated, but not encrypted";
    InputStream encryptPlainStream = new ByteArrayInputStream(plainText.getBytes());
    ByteArrayOutputStream encryptCipherStream = new ByteArrayOutputStream();
    try {
        service.EncryptStream(encryptPlainStream, encryptCipherStream, associatedData.getBytes());
    } catch (Exception e) {
        //TODO: handle exception here.
        System.out.println(e.getMessage());
    }
```
### Decrypt Stream
To decrypt stream of data, use the **DecryptStream(InputStream cipherStream,OutputStream plainStream, byte[] associatedData)** method of the `Encryption` service. The **cipherStream** parameter is the `InputStream` stream of data you are wishing to decrypt and it was originally encrypted using [EncryptStream](#encrypt-stream). The **plainStream** parameter is the `OutputStream` stream you are wishing to write the plain data back to. The **associatedData** parameter the `byte[]` representation of associated data which can be used to improve the authenticity of the data (it is not mandatory), as shown below.
```java
    String plainText = "This is cipher data is already encrypted using `Encrypt` method";
    Encryption serivce = new Encryption(credential, retry, "<arx_url>", true);
    ByteArrayInputStream dataEncrypted = new ByteArrayInputStream(encryptCipherStream.toByteArray());
    ByteArrayOutputStream decryptPlainStream = new ByteArrayOutputStream();

    try {
        serivce.DecryptStream(dataEncrypted,decryptPlainStream, associatedData.getBytes());
    } catch (Exception e) {
        //TODO: handle exception here.
        System.out.println(e.getMessage());
    }
```
## Reporting a Vulnerability
If you discover a potential security issue in this project, please reach out to us at security@onqlave.com. Please do not create public GitHub issues or Pull Requests, as malicious actors could potentially view them.




