
package com.anjbo.utils.huarong;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @Description: 加密解密
 * @see: ReturnEntry 此处填写需要参考的类
 * @version 2015年8月18日 下午7:53:53
 * @author czm
 */
public class Endecrypt {
	
	/** 用华融公钥加密 */
	public static String encrypt(String str,String huarongPublicKey) throws Exception {
		/** 生成密文报文 */
//		String sectdata = RSA.encrypt(str, huarongPublicKey);
//		return sectdata;
//		Properties pro = Endecrypt.proper();
//		String hrdPublicKey = (String) pro.get("hrd_publickey");
		/** 生成密文报文 */
		String hrdAesKey = GetStringRandom.getStringRandom(16);
		String sectdata =AES.encryptToBase64(str, hrdAesKey);
		String enkey = RSA.encrypt(hrdAesKey, huarongPublicKey);
		Map<String,String> params=new HashMap<String,String>(2);
		params.put("key", enkey);
		params.put("Str", sectdata);
		String sectjson=JsonUtils.toJsonString(params);
		return sectjson;
	}
	
	/** 用惠人贷私钥解密 */
	public static String decrypt(String str,String hrdPrivateKey) throws Exception {
		// String hrdPrivateKey = (String) pro.get("hrd_privatekey");
		/** 解密报文明文 */
//		String realdata = RSA.decrypt(str, hrdPrivateKey);
//		return realdata;
//		Properties pro = Endecrypt.proper();
//		String huarongPrivateKey = (String) pro.get("huarong_privatekey");
		// String hrdPrivateKey = (String) pro.get("hrd_privatekey");
		/** 解密报文明文 */
		Map<String,String> params=new HashMap<String,String>(2);
		params=JsonUtils.toObject(str, java.util.Map.class);	
		String enkey=params.get("key");
		String sectdata=params.get("Str");
		String hrdAesKey = RSA.decrypt(enkey, hrdPrivateKey);
		String realdata=AES.decryptFromBase64(sectdata, hrdAesKey);
//		String realdata = RSA.decrypt(str, huarongPrivateKey);
		return realdata;
	}
}
