package com.anjbo.utils.yntrust;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Enumeration;

/**
 * Created by liuyb on 8/11/2017.
 */

public class RsaSignUtils {
    private String privateKey ;
    private String password = "123456";
    // private String alias = "1";
    private static String algorithm = "MD5withRSA";
    //private String algorithm = "SHA256withRSA";
    public RsaSignUtils(String privateKeyPath, String password){
        privateKey = privateKeyPath;
        this.password = password;
    }
    public String generate(String body) {
        byte[] signedInfo = null;
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(privateKey);
            char[] nPassword = null;
            if ((password == null) || password.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = password.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
            Enumeration enuml = ks.aliases();
            String keyAlias = null;
            if (enuml.hasMoreElements()) {
                keyAlias = (String) enuml.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
            }
            System.out.println("is key entry = " + ks.isKeyEntry(keyAlias));
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
            System.out.println("keystore type = " + ks.getType());
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(prikey);

            //Read the string into a buffer
            byte[] dataInBytes = body.getBytes("UTF-8");

            //update signature with data to be signed
            signature.update(dataInBytes);

            //sign the data
            signedInfo = signature.sign();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return Base64.encode(signedInfo);
    }
}