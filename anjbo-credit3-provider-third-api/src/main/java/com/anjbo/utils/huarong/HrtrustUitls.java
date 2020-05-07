package com.anjbo.utils.huarong;

import com.anjbo.common.ThirdApiConstants;
import com.anjbo.utils.SingleUtils;
import com.anjbo.utils.UidUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 加密发送，加密类
 * @see: SendHrHaEnCrypt 此处填写需要参考的类
 * @version 2017年8月22日 下午3:02:55
 * @author chenzm
 */
public class HrtrustUitls {

	private static Logger logger = LoggerFactory.getLogger(HrtrustUitls.class);

	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * 项目加密发送(RSA)
	 * 
	 * @Description 一句话描述方法用法
	 * @param message
	 *            需要发送的数据
	 * @param timeout
	 *            超时时间
	 * @param url
	 *            调用地址
	 * @param loanCooprCode
	 *            助贷商代码编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ReturnParam<Object> sendHrHaEnCryptMap(String loanCooprCode, String message, String url,
			String timeout) {
		ReturnParam<Object> param = new ReturnParam<Object>();
		try {
			if (StringUtils.isEmpty(url)) {
				throw new RuntimeException("url不能为空");
			}
			String messageEnCrypt = encrypt(message, ThirdApiConstants.GD_PUBLICKEY2);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("loanCooprCode", loanCooprCode);
			paramMap.put("data", messageEnCrypt);
			String sendMessage = encrypt(gson.toJson(paramMap), ThirdApiConstants.GD_PUBLICKEY1);
//			logger.info("项目加密发送(RSA)--加密后数据:{}", messageEnCrypt);
			String returnDataCryp = SingleUtils.getRestTemplate()
					.postForEntity(ThirdApiConstants.HR_PARENT_URL + url, sendMessage, String.class).getBody();
			returnDataCryp = new String(returnDataCryp.getBytes("ISO8859-1"), "UTF-8");
//			logger.info("项目加密发送(RSA)--返回内容:{}", returnDataCryp);
			String returnData = decrypt(returnDataCryp, ThirdApiConstants.HRGD_PRIVATEKEY); // 替换成自己的
			param = gson.fromJson(returnData, ReturnParam.class);
		} catch (Exception e) {
			e.printStackTrace();
			param.setReturnParam(ReturnParam.STS_FAIL, ReturnParam.FAILCODE, e.getMessage(), null);
			logger.error("项目加密发送(RSA)发送失败", e);
		}
		return param;
	}

	public static String encrypt(String str, String huarongPublicKey) throws Exception {
		/** 生成密文报文 */
		String hrdAesKey = UidUtils.generateNo(16);
		String sectdata = HrAES.encryptToBase64(str, hrdAesKey);
		String enkey = HrRSA.encrypt(hrdAesKey, huarongPublicKey);
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("key", enkey);
		params.put("Str", sectdata);
		return gson.toJson(params);
	}

	@SuppressWarnings("unchecked")
	public static String decrypt(String str, String hrdPrivateKey) throws Exception {
		/** 解密报文明文 */
		Map<String, String> params = new HashMap<String, String>(2);
		params = gson.fromJson(str, Map.class);
		String enkey = params.get("key");
		String sectdata = params.get("Str");
		String hrdAesKey = HrRSA.decrypt(enkey, hrdPrivateKey);
		String realdata = HrAES.decryptFromBase64(sectdata, hrdAesKey);
		return realdata;
	}
	
}
