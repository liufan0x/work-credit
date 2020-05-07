package com.anjbo.utils.ccb;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayInputStream;
import java.security.Key;

/**
 * des加密解密-建行
 */
public class DESPlus {
    private static String strDefaultKey; //默认密钥

    private static final byte[] iv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef};//des 向量

	private static BASE64Encoder enc = new BASE64Encoder();//将byte[]转换成String

	private static BASE64Decoder dec = new BASE64Decoder(); //将String转换成byte[]


    static {           
//        try{
//        	   strDefaultKey = Util.getParameter("DESPLUS", "defaultKey", "WDTENKEY");
//        }catch(Throwable t){
//            logger.warn("从配置初始化密钥失败");
            strDefaultKey = "FDTENKEY";
       // }
    }

    /**
     * 加密字节数组
     *
     * @param arrB
     *            需加密的字节数组
     * @param key
     *            密钥
     * @return 加密后的字节数组
     * @throws Exception
     */
    public static byte[] encrypt(byte[] arrB, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec ivp = new IvParameterSpec(DESPlus.iv);

        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivp);

        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param xml
     *            需加密的字符串
     * @param key
     *            密钥
     * @return 加密后的字符串
     * @throws Exception
     */
	public static String encrypt(String xml, String key) throws Exception {
        return DESPlus.enc.encode(encrypt(xml.getBytes("utf-8"), key));
    }

    /**
     * 使用默认公钥加密字符串
     * @param xml 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String xml) throws Exception {
        return encrypt(xml, strDefaultKey);
    }

    /**
     * 解密字节数组
     *
     * @param arrB
     *            需解密的字节数组
     * @param key
     *            密钥
     * @return 解密后的字节数组
     * @throws Exception
     */
    public static byte[] decrypt(byte[] arrB, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec ivp = new IvParameterSpec(DESPlus.iv);

        Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivp);

        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param xml
     *            需解密的字符串
     * @param key
     *            密钥
     * @return 解密后的字符串
     * @throws Exception
     */
	public static String decrypt(String xml, String key) throws Exception {
        return new String(decrypt(DESPlus.dec.decodeBuffer(xml), key));
    }

    /**
     * 使用默认公钥解密字符串
     * @param xml 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String xml) throws Exception {
        return decrypt(xml, strDefaultKey);
    }
    public static String decryptForCharset(String xml, String charset) throws Exception {
    	return new String(decrypt(DESPlus.dec.decodeBuffer(xml), strDefaultKey),charset);
    }

    /**
     * 使用默认密钥解密字符串
     * @param xml 加密后经过BASE64Decoder编码的字节数组
     * @param key 密钥字节数组
     * @return 解密后的字符串
     * @throws Exception
     */
	public static String decryptByte(byte[] xml, String key, String charset) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(xml);
        String buf = new String(decrypt(DESPlus.dec.decodeBuffer(bis), key),charset);
        bis.close();
        return buf;
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp
     *            构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    @SuppressWarnings("unused")
	private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * 获取默认密钥
     * @return
     */
    public static String getDesKey() {
        return DESPlus.strDefaultKey;
    }

   /* public static void main(String[] args) {
        try {
            //测试密匙
            String key = "WDTENKEY";
            //004交易案例
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><HEADER><TRANNO>004</TRANNO><BNKNO>17850</BNKNO><COMNO>COM01</COMNO><SN>1109141222222660161</SN></HEADER><BODY><TRANLOG><APPNO>0000000123</APPNO><NOTATION>客户信息不完整，请补充资料。</NOTATION></TRANLOG></BODY></ROOT>";
            System.out.println("密钥：" + key);
            System.out.println("加密前的字符串：" + xml);
            System.out.println("加密前的字符串长度：" + xml.getBytes().length);

            //加密
            xml = DESPlus.encrypt("ftpuser", key);
            System.out.println("加密后的字符串：" + xml);
            System.out.println("加密后的字符串长度：" + xml.getBytes().length);

            //解密
            xml = DESPlus.decrypt("/seX2d/Xn3+Nn0aHhQdHoQ==", key);
            System.out.println("解密后的字符串：" + xml);
            System.out.println("解密后的字符串长度：" + xml.getBytes().length);

        } catch (Exception e) {
        	e.printStackTrace();
        }
    }*/
}
