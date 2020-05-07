package com.anjbo.utils.erongsuo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
 
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anjbo.controller.impl.lineparty.PlatformController;

import sun.misc.BASE64Decoder;
 
public class RSAUtils {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 1024;
    public static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";
    public static final String DEFAULT_PRIVATE_KEY =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLzcCAoQaBK3X0cPbsu1xKUsuwQ\n" +
                    "vtVz12t16ARd2fza5H6A3aekI1DZV/+U6IT7LQm/75uOlTpek7W8nRZJsrxqK523RILCds5UEkGE\n" +
                    "yM87RulTtL+acMBUg3nPxUQiBKj7W79VNDmxX5pO55d/tlIXl9tldQahykkLNSS8HTLzAgMBAAEC\n" +
                    "gYAhqBOAmNGu+iWqMDOUDv04a2szZvrdXoo3lddratNi8TBkcow9yWsy+43HbhRFXpBb8xN3qFt8\n" +
                    "vOj/F1hcJsRMxikzSnnDPtri48mx6esMG2v/Jt+eb5tfk4fTmboXXWRMiSjFG8B6cAaCxEwaykSV\n" +
                    "CSI4S23aWrnJ9I6tezNDQQJBAPiQ7OqNb1/J7nWsKeWZCLLWYwZK2wCmb+IQZ8WUi4mIUlj6tkBX\n" +
                    "rD+XAmdK1OXgjLHUlMAM0GZ0NQJfD++FdvECQQC4TYYliyJTjXoWDEBWWaVASRfK7RXBq8JTeXGg\n" +
                    "5bjQZzJsmfqS5aU8Qz1AZyc9gjXuMJMqszv+WuyQ5vyLHPAjAkEAwvC2PcWqoU83KyZYvW5lugwV\n" +
                    "IWw3kaz2di8zk2tKfBRjsND/ejrIJh8CjYvMqHSRIy57cpsaHh/pKvDvCIR9oQJBAKGceVFanBMg\n" +
                    "MDo9K/2MRngEoDR1iWp2rsR77cPlLRayJ2lL7In7jdU2MPPUgHhTQe9H8QS0fpsgJ+k4Y6OpEHkC\n" +
                    "QBvpHMdDZG9e7+eTeHTss5eZ6MzEA0umVZYrk+9egqDD5bVl11E/A6xoAMzRPsfsBxxaltnXwCyr\n" +
                    "HjjWbeNVKso=";
    public static final String DEFAULT_PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIBDGXKV781mjh2/nL0EexmBBijbtZ8aaOIDHW fURhbQWckU2QamzR+KpQAGKMDzxGa47vu6a3w5aWB3YWLBW1Y+KOE1siNpr4+zATRysUrDBJvb7G a5psG3jbyq1+Eb16BenN6snBahmiXesMaH2m5jrHYyXowYWRCwK7OWPA3QIDAQAB";
 
    /**
     * 获取公钥
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return restorePublicKey(publicKey.getEncoded());
 
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }
 
    /**
          * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
          *
          * @param keyBytes
          * @return
          */
    public static PublicKey restorePublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            throw new Exception("");
        }
    }
 
    /**
     * 通过私钥KEY获取解密私钥
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return restorePrivateKey(privateKey.getEncoded());
 
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }
 
    /**
     * 获取私钥
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            LOGGER.error("获取私钥失败", e);
        }
        return null;
    }
 
    /**
          * 加密，三步走。
          *
          * @param key
          * @param plainText
          * @return
          */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {
 
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            LOGGER.error("加密失败", e);
        }
        return null;
 
    }
 
    /**
    * 解密，三步走。
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static byte[] RSADecode(PrivateKey key, byte[] encodedText) {
 
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encodedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            LOGGER.error("解密失败", e);
        }
        return null;
 
    }
 
    public static void main(String[] ar) throws Exception {
        String str = "{\"a\":\"欢迎来到chacuo\"}";
        System.out.println("待加密报文：" + str);
        PublicKey publicKey = loadPublicKey(DEFAULT_PUBLIC_KEY);
        PrivateKey privateKey = loadPrivateKey(DEFAULT_PRIVATE_KEY);
//        String encodeStr = Base64.encodeBase64String(RSAEncode(publicKey, str.getBytes()));
//        System.out.println("加密后密文:" + encodeStr);
//        String decodeStr = new String(RSADecode(privateKey, Base64.decodeBase64(encodeStr)));
//        System.out.println("解密后密文:" + decodeStr);
 
 
        String randomKey = String.valueOf(System.currentTimeMillis());
        System.out.println("随机KEY randomKey:"+randomKey);
 
        String cipherText = AESUtils.encrypt(str,randomKey);
        System.out.println("AES加密请求内容cipherText:"+cipherText);
 
        String cipherKey = Base64.encodeBase64String(RSAEncode(publicKey, randomKey.getBytes()));
        System.out.println("RSA加密后的key:"+cipherKey);
 
        System.out.println("请求内容:{\"encryptStr\":\"" + cipherText+"\",\"aesKey\":\""+cipherKey+"\"}");
 
        String key = new String(RSADecode(privateKey, Base64.decodeBase64(cipherKey)));
        System.out.println("RSA私钥解密后的key:"+key);
 
        String decodeData = AESUtils.decrypt(cipherText,key);
        System.out.println("AES解密后:"+decodeData);
    }
}