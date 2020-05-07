package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * Copy Right Information : SINOSAFE <br>
 * Description : 加密类 <br>
 * Author : ChenLiang <br>
 * Version : 1.0.0 <br>
 * Since : 1.0 <br>
 * Date : 2016年10月27日<br>
 */
public class DesEncrypterUtil {
	Cipher ecipher;
	Cipher dcipher;
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	/**
	 * 构造方法
	 * 
	 * @param passPhrase
	 *            将用户的apikey作为密钥传入
	 * @throws Exception
	 */
	public DesEncrypterUtil(String passPhrase) throws Exception {
		int iterationCount = 2;
		KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		ecipher = Cipher.getInstance(key.getAlgorithm());
		dcipher = Cipher.getInstance(key.getAlgorithm());
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,iterationCount);
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String str) throws Exception {
//		str = new String(str.getBytes(), "UTF-8");
		return Base64.encodeBase64String(ecipher.doFinal(str.getBytes("UTF-8")));
	}

	/**
	 * 解密
	 * 
	 * @param str
	 *            要解密的字符串
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String str) throws Exception {
		return new String(dcipher.doFinal(Base64.decodeBase64(str)), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		DesEncrypterUtil desEncrypter = new DesEncrypterUtil("3409d0e771dc7cf7707ff6fd0518a030");
//		// str为加密前的参数字符串
//		String str = "idCardName=马战&idCardCode=130622198104023650";
//		System.out.println(desEncrypter.encrypt(str));
//		
//		String s = "Fb4YER5HoXNofhc6qAxa7fCJt+TYt6BW1+HcSVeVuwP4VFDXWGcvzp4CnkD40Bd9";
//		System.out.println(desEncrypter.decrypt(s));
		
		
		
		String url="http://agenttest.sinosafe.com.cn/xinbaostg/SULOUApply";
		String params=readFileByLines("F:\\workspace\\anjbo-fc\\src\\main\\resources\\ha-param.txt");
		Map<String,String> param = new HashMap<String,String>();
		param.put("accid","KG2017");
		param.put("type","HAXB0001");
		param.put("params",desEncrypter.encrypt(params));
		//System.out.println(HttpUtil.jsonPost(url, param));
	}
	
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
    	StringBuffer sb = new StringBuffer();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                sb.append(tempString.replace(" ",""));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Base64加密 (sun.misc包)
     * @Author KangLG<2017年10月20日>
     * @param s
     * @return
     */
    @SuppressWarnings("restriction")
	public static String encoderBase64Sun(byte[] sources) {
		if (sources == null)
			return null;
		try {
			return new sun.misc.BASE64Encoder().encode(sources);
		} catch (Exception e) {
			return null;
		}
	}
    /**
     * Base64解码 (sun.misc包)
     * @Author KangLG<2017年10月20日>
     * @param encode
     * @return
     */
	@SuppressWarnings("restriction")
	public static String decodeBase64Sun(String encode){
		if (null == encode)
			return null;		
		try {
			return new String(new sun.misc.BASE64Decoder().decodeBuffer(encode));
		} catch (IOException e) {    
		      e.printStackTrace(); 
		}
		return null;
	}    
}