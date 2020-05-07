package com.anjbo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Rc4Util {
	
	/**
	 * 加密密匙
	 */
	public static final String RC4_AMS_KEY = "1bb762f7ce24ceee";

	/**
	 * 加密密匙
	 */
	public static final byte[] RC4_AMS_KEY_BYTE = RC4_AMS_KEY.getBytes();
	
	/**
	 * 报文密码加密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Encrypt(String src) {
		byte[] dest;
		try {
			dest = encrypt(src.getBytes(), RC4_AMS_KEY_BYTE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(Hex.encodeHex(dest));
	}

	/**
	 * 报文密码加密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Encrypt(String src, String key) {
		byte[] dest;
		try {
			dest = encrypt(src.getBytes(), key.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(Hex.encodeHex(dest));
	}

	/**
	 * 报文密码解密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Decrypt(String src) {
		byte[] src1;
		try {
			src1 = Hex.decodeHex(src.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
		byte[] dest;
		try {
			dest = decrypt(src1, RC4_AMS_KEY_BYTE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(dest);
	}

	/**
	 * 报文密码解密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Decrypt(String src, String key) {
		byte[] src1;
		try {
			src1 = Hex.decodeHex(src.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
		byte[] dest;
		try {
			dest = decrypt(src1, key.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(dest);
	}
	
	private static final String ALGORITHM = "RC4";

	public static final byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(src);
	}

	public static final byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(src);
	}
}
