package com.anjbo.utils.huarong;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anjbo.utils.common.ThirdConfigUtil;
import com.anjbo.utils.huarong.HttpClientUtils.Method;

/**
 * @Description: 加密发送，加密类
 * @see: SendHrHaEnCrypt 此处填写需要参考的类
 * @version 2017年8月22日 下午3:02:55
 * @author chenzm
 */
public class SendHrHaEnCrypt {

	private static Logger logger = LoggerFactory.getLogger(SendHrHaEnCrypt.class);
	/**
	 * 项目加密发送(RSA)
	 * @Description 一句话描述方法用法
	 * @param message 需要发送的数据
	 * @param timeout 超时时间
	 * @param uri 调用地址
	 * @param loanCooprCode 助贷商代码编号
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static ReturnParam<Object> sendHrHaEnCryptMap(String loanCooprCode, String message, String url) {
		ReturnParam<Object> param = new ReturnParam<Object>();
		try {
			if (!StringUtils.notEmpty(ThirdConfigUtil.getProperty(url))) {
				throw new RuntimeException("url不能为空");
			}
			String time = ThirdConfigUtil.getProperty(url + "Timeout");
			int tmout = Integer.parseInt(time);
			
			String uri = ThirdConfigUtil.getProperty("parentUrl") + ThirdConfigUtil.getProperty(url);
			//logger.info("项目加密发送(RSA)--请求:{}，参数{}", uri, message);
				
			String messageEnCrypt = Endecrypt.encrypt(message, SecretConstant.GD_PUBLICKEY2);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("loanCooprCode", loanCooprCode);
			paramMap.put("data", messageEnCrypt);
			String sendMessage = Endecrypt.encrypt(JsonUtils.toJsonString(paramMap), SecretConstant.GD_PUBLICKEY1);
			//logger.info("项目加密发送(RSA)--加密后数据:{}", messageEnCrypt);
			String returnDataCryp = HttpClientUtils.send(Method.POST, uri, sendMessage, true, ThirdConfigUtil.ENCODING_UTF8, tmout);			
			//returnDataCryp = new String(returnDataCryp.getBytes(HrHaConstant.ENCODING_ISO88591), HrConstant.ENCODING_UTF8);
			returnDataCryp = new String(returnDataCryp.getBytes(ThirdConfigUtil.ENCODING_ISO88591), ThirdConfigUtil.ENCODING_UTF8);
			//logger.info("项目加密发送(RSA)--返回内容:{}", returnDataCryp);
			String returnData = Endecrypt.decrypt(returnDataCryp, SecretConstant.HRGD_PRIVATEKEY);
			param = JsonUtils.toObject(returnData, ReturnParam.class);
			//logger.info("项目加密发送(RSA)--返回内容解密:{}", returnData);
			// param.setRetData(returnData);
		} catch (Exception e) {
			e.printStackTrace();
			param.setReturnParam(ReturnParam.STS_FAIL, ReturnParam.FAILCODE, e.getMessage(), null);
			logger.error("项目加密发送(RSA)发送失败", e);
		}
		return param;
	}

}
