package com.onqlave.keymanager;

import com.google.gson.Gson;
import com.onqlave.connection.Client;
import com.onqlave.connection.ClientImpl;
import com.onqlave.connection.Connection;
import com.onqlave.connection.ConnectionImpl;
import com.onqlave.contract.Configuration;
import com.onqlave.contract.request.DecryptionOpenRequest;
import com.onqlave.contract.request.EncryptionOpenRequest;
import com.onqlave.contract.response.DecryptionOpenResponse;
import com.onqlave.contract.response.EncryptionOpenResponse;
import com.onqlave.error.ErrorCodes;
import com.onqlave.error.OnqlaveError;
import com.onqlave.keymanager.factories.RsaSsaPkcs1ShaFactory;
import com.onqlave.keymanager.operations.RsaSsaPkcs1ShaOperation;
import com.onqlave.service.CPRNGService;
import com.onqlave.types.AlgorithmTypeValue;
import com.onqlave.types.Unwrapping;
import com.onqlave.types.WrappingKeyFactory;
import com.onqlave.types.WrappingKeyOperation;
import com.onqlave.utils.HashImpl;
import com.onqlave.utils.Hasher;
import org.javatuples.Triplet;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.onqlave.error.ErrorCodes.SdkErrorCode;

public class KeyManagerImpl implements KeyManager {
    public Connection keyManager;
    public Configuration configuration;

    public Map<String, WrappingKeyOperation> operation;

    private static final String ENCRYPT_RESOURCE_URL = "oe2/keymanager/encrypt";
    private static final String DECRYPT_RESOURCE_URL = "oe2/keymanager/decrypt";

    public KeyManagerImpl(Configuration config, CPRNGService randomService) {
        Hasher hasher = new HashImpl();
        Client client = new ClientImpl(HttpClient.newHttpClient(), config.getRetry());
        Connection httpClient = new ConnectionImpl(config, hasher, client);
        WrappingKeyFactory rsaSSAPKCS1KeyFactory = new RsaSsaPkcs1ShaFactory(randomService);

        Map<String, WrappingKeyOperation> operations = new HashMap();
        operations.put(AlgorithmTypeValue.RSA_SSA_PKCS1_2048_SHA256_F4, new RsaSsaPkcs1ShaOperation(rsaSSAPKCS1KeyFactory));
        this.keyManager = httpClient;
        this.configuration = config;
        this.operation = operations;
    }

    @Override
    public Triplet<byte[], byte[], String> FetchEncryptionKey() throws Exception {
        String operation = "FetchEncryptionKey";
        EncryptionOpenRequest request = new EncryptionOpenRequest();
        Triplet<byte[], byte[], String> trip;

        try {
            byte[] data = this.keyManager.Post(ENCRYPT_RESOURCE_URL, request);
            System.out.println(new String(data, StandardCharsets.UTF_8));
            EncryptionOpenResponse response = new Gson().fromJson(new String(data, StandardCharsets.UTF_8), EncryptionOpenResponse.class);

            byte[] edk = response.getDK().getEncryptedDataKey();
            byte[] wdk = response.getDK().getWrappedDataKey();
            byte[] epk = response.getWK().getEncryptedPrivateKey();
            byte[] fp = response.getWK().getKeyFingerprint();
            String wrappingAlgo = response.getSecurityModel().getWrappingAlgorithm();
            String algo = response.getSecurityModel().getAlgorithm();
            byte[] dk = this.unwrapKey(wrappingAlgo, operation, wdk, epk, fp, this.configuration.getCredential().getSecretKey().getBytes());
            trip = Triplet.with(edk, dk, algo);
        } catch (Exception e) {
            throw new OnqlaveError(SdkErrorCode, e.getMessage(), null);
        }

        return trip;
    }

    @Override
    public byte[] FetchDecryptionKey(byte[]edk) throws Exception {
        String operation = "FetchDecryptionKey";
        DecryptionOpenRequest request = new DecryptionOpenRequest(edk.toString());
        byte[] dk;
        try {
            byte[] data = this.keyManager.Post(DECRYPT_RESOURCE_URL, request);
            DecryptionOpenResponse response = new Gson().fromJson(data.toString(), DecryptionOpenResponse.class);
            byte[] wdk = response.getDK().getWrappedDataKey();
            byte[] epk = response.getWK().getEncryptedPrivateKey();
            byte[] fp = response.getWK().getKeyFingerprint();
            String wrappingAlgo = response.getSecurityModel().getWrappingAlgorithm();
            dk = this.unwrapKey(wrappingAlgo, operation, wdk, epk, fp, this.configuration.getCredential().getSecretKey().getBytes());
        } catch (Exception e) {
            throw new OnqlaveError(ErrorCodes.SdkErrorCode, e.getMessage(), null);
        }
        return dk;
    }

    private byte[] unwrapKey(String algo, String operation, byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception {
        if (!this.operation.containsKey(AlgorithmTypeValue.RSA_SSA_PKCS1_2048_SHA256_F4)) {
            throw new OnqlaveError(SdkErrorCode, "Error Unwrapkey", null);
        }
        WrappingKeyOperation wrappingOp = this.operation.get(AlgorithmTypeValue.RSA_SSA_PKCS1_2048_SHA256_F4);
        WrappingKeyFactory factory = wrappingOp.GetFactory();
        byte[] dk;
        try {
            Unwrapping primitive = factory.Primitive(wrappingOp);
            dk = primitive.UnwrapKey(wdk, epk, fp, password);
        } catch (Exception e) {
            throw e;
        }
        return dk;
    }
}
