package com.katalon.platform.internal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

public final class CryptoUtil {
    private static final String DF_ALGORITHM = "PBEwithSHA1AndDESede";

    private static final byte[] DF_SALT = { 75, 64, 116, 97, 108, 48, 110, 32, 83, 84, 117, 100, 108, 79 };

    private static final byte[] DF_SECRET_KEY = { 83, 51, 99, 82, 101, 84, 32, 75, 51, 105 };

    private static final int DF_ITERATION = 20;

    private static final String DF_ENCODING = "UTF-8";

    private CryptoUtil() {
        // Disable default constructor
    }

    public static class CryptoInfo {
        private String data;

        private String algorithm;

        private byte[] salt; // public key

        private byte[] privateKey;

        private int iteration = DF_ITERATION;

        private String encode = DF_ENCODING;
    }

    public static CryptoInfo getDefault(String data) {
        return create(DF_ALGORITHM, data, DF_SALT, DF_SECRET_KEY);
    }

    public static CryptoInfo getDefault(byte[] salt, String data) {
        return create(DF_ALGORITHM, data, salt, DF_SECRET_KEY);
    }

    public static CryptoInfo create(String algorithm, String data, byte[] salt, byte[] privateKey) {
        CryptoInfo cryptoInfo = new CryptoInfo();
        cryptoInfo.algorithm = algorithm;
        cryptoInfo.data = data;
        cryptoInfo.salt = salt;
        cryptoInfo.privateKey = privateKey;
        return cryptoInfo;
    }

    public static CryptoInfo create(String algorithmn, String data, byte[] salt, byte[] privateKey, int iteration) {
        CryptoInfo cryptoInfo = create(algorithmn, data, salt, privateKey);
        cryptoInfo.iteration = iteration;
        return cryptoInfo;
    }

    public static CryptoInfo create(String algorithmn, String data, byte[] salt, byte[] privateKey, int iteration,
            String encode) {
        CryptoInfo cryptoInfo = create(algorithmn, data, salt, privateKey, iteration);
        cryptoInfo.encode = encode;
        return cryptoInfo;
    }

    public static String encode(CryptoInfo cryptoInfo) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptoInfo.algorithm);
        String privateKeyString = new String(cryptoInfo.privateKey, cryptoInfo.encode);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(privateKeyString.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance(cryptoInfo.algorithm);
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(cryptoInfo.salt, cryptoInfo.iteration));
        return DatatypeConverter.printBase64Binary(pbeCipher.doFinal(cryptoInfo.data.getBytes(cryptoInfo.encode)));
    }

    public static String decode(CryptoInfo cryptoInfo) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptoInfo.algorithm);
        String privateKeyString = new String(cryptoInfo.privateKey, cryptoInfo.encode);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(privateKeyString.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance(cryptoInfo.algorithm);
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(cryptoInfo.salt, cryptoInfo.iteration));
        return new String(pbeCipher.doFinal(DatatypeConverter.parseBase64Binary((cryptoInfo.data))), cryptoInfo.encode);
    }
}
