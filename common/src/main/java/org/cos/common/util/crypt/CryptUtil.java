package org.cos.common.util.crypt;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;

public class CryptUtil {

    static String key = "BLOCKCHAINAESKEY";
    static String iv = "AESKEYBLOCKCHAIN";
    private CryptUtil(){

    }

    public static String[] genRsaKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024,new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        return new String[]{publicKeyString,privateKeyString};
    }

    public static String encryptRsaWithPublicKey(String str, String publicKey) throws Exception{
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static String decryptRsaWithPrivateKey(String str, String privateKey) throws Exception{
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
    // 加密:
    public static byte[] encrypt(byte[] secret, byte[] input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] byteKey = sha.digest(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(byteKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // 解密:
    public static byte[] decrypt(byte[] secret, byte[] input) throws GeneralSecurityException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] byteKey = sha.digest(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(byteKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // 加密:
    public static String encryptToString(byte[] secret, byte[] input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] byteKey = sha.digest(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(byteKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64Utils.encodeToString(cipher.doFinal(input));
    }

    public static String encryptAES(String message) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return toHex(encryptedBytes).toUpperCase();

    }
    public static String toHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
