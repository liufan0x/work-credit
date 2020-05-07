package com.anjbo.utils.yntrust;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.anjbo.bean.yntrust.YntrustRequestFlowDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.service.yntrust.YntrustRequestFlowService;
import com.anjbo.utils.SingleUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Created by Administrator on 2018/3/5. 对接云南信托
 */

public class YntrustUtils {
	private Logger log = Logger.getLogger(getClass());
	
	private YntrustRequestFlowService yntrustRequestFlowService;

	public YntrustUtils(YntrustRequestFlowService yntrustRequestFlowService) {
		this.yntrustRequestFlowService = yntrustRequestFlowService;
	}
	
	private static String algorithm = "MD5withRSA";

	private ObjectMapper mapper;

	private static String keyPath = "key" + File.separator + ThirdApiConstants.YNTRUST_PRIVATE_KEY;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	// 对接第三方
	
	/**
	 * 发送请求给第三方
	 * 
	 * @param param
	 *            请求参数
	 * @param clazz
	 *            返回类型
	 * @param url
	 *            请求路径
	 * @param requestInterfaceName
	 *            接口名称
	 * @param orderNo
	 *            订单号
	 * @param <T>
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public <T> T postForObject(Map<String, Object> param, Class<T> clazz, String url, String requestInterfaceName,
			String orderNo, RespStatus result,String uid) {
		String requestId = MapUtils.getString(param, "requestId");
		log.info("==========================请求接口名称:" + requestInterfaceName + ":" + requestId+ "=============================");
		T t = null;
		//影像资料不需要headers
		if(param.containsKey("imageContent")) {
			t = postForObjectFile(param, clazz, url);
		}else {
			t = postForObject(param, clazz, url);
		}
		String json = gson.toJson(t);
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		boolean isSuccess = analyzeResponseMsg(gson.fromJson(json, Map.class), result, responseMsg);
		String responseStatusMsg = MapUtils.getString(responseMsg, "responseStatusMsg");
		String requestMsg = "";
		if(param != null && !param.containsKey("imageContent")) {
			requestMsg = gson.toJson(param);
		}
		insertFlow(orderNo, requestId, json, url, requestInterfaceName, responseStatusMsg, requestMsg,uid);
		if (!isSuccess) {
	        log.info("===================requestId:"+requestId+",请求云南信托返回:"+responseStatusMsg+"========================");
		}
		return t;
	}
    
    /**
     * 保存请求流水
     * @param orderNo 订单编号
     * @param requestId 本次请求的唯一标示
     * @param responseMsg 返回信息
     * @param requestInterface 请求接口
     * @param requestInterfaceName 请求接口名称
     * @param responseStatusMsg 返回的状态名称
     */
    private void insertFlow(String orderNo,
                           String requestId,
                           String responseMsg,
                           String requestInterface,
                           String requestInterfaceName,
                           String responseStatusMsg,
                           String requestMsg,
                           String uid){
        YntrustRequestFlowDto flow = new YntrustRequestFlowDto();
        flow.setCreateUid(uid);
        flow.setOrderNo(orderNo);
        flow.setRequestId(requestId);
        flow.setResponseMsg(responseMsg);
        flow.setRequestInterface(requestInterface);
        flow.setRequestInterfaceName(requestInterfaceName);
        flow.setResponseStatusMsg(responseStatusMsg);
        flow.setRequestMsg(requestMsg);
        yntrustRequestFlowService.insert(flow);
    }
    
    @SuppressWarnings("unchecked")
    private boolean analyzeResponseMsg(Map<String,Object> responseMsg, RespStatus result, Map<String,Object> tmp){
        Map<String,Object> statusMap = MapUtils.getMap(responseMsg,"Status");
        String responseStatusMsg = MapUtils.getString(statusMap,"ResponseMessage");
        String ResponseCode = MapUtils.getString(statusMap,"ResponseCode");
        tmp.put("responseStatusMsg",responseStatusMsg);
        tmp.put("responseStatusCode",ResponseCode);
        if(null==statusMap||statusMap.size()<=0){
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
            return false;
        }
        if(StringUtils.isBlank(ResponseCode)
                ||!ResponseCode.equals("0000")){
            result.setCode(RespStatusEnum.FAIL.getCode());
            if("0040".equals(ResponseCode)){
                result.setMsg("第三方返回:"+responseStatusMsg);
            } else {
                result.setMsg(responseStatusMsg);
            }
            return false;
        }
        return true;
    }

	/**
	 * 发送请求给云南
	 * 
	 * @param param
	 *            请求第三方接口参数
	 * @param clazz
	 *            返回类型
	 * @param url
	 *            请求路径
	 * @return
	 */
	private <T> T postForObject(Map<String, Object> param, Class<T> clazz, String url) {
		url = ThirdApiConstants.YNTRUST_URL + url;
		HttpHeaders headers = getHeader(param);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(param, headers);
		String json = SingleUtils.getRestTemplate().postForObject(url, requestEntity, String.class);
		return gson.fromJson(json, clazz);
	}

	/**
	 * 发送云南影像资料地址
	 * 
	 * @param param
	 *            请求第三方接口参数
	 * @param clazz
	 *            返回类型
	 * @param url
	 *            请求路径
	 * @return
	 */
	private <T> T postForObjectFile(Map<String, Object> param, Class<T> clazz, String url) {
		url = ThirdApiConstants.YNTRUST_FILE_URL + url;
		HttpHeaders headers = getHeader("");
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(param, headers);
		return SingleUtils.getRestTemplate().postForObject(url, requestEntity, clazz);
	}

	/**
	 * 云南设置请求头
	 * 
	 * @param obj
	 * @return
	 */
	private HttpHeaders getHeader(Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("MerId", ThirdApiConstants.YNTRUST_MERID);
		headers.add("SecretKey", ThirdApiConstants.YNTRUST_SECRETKEY);
		try {
			mapper = new ObjectMapper();
			headers.add("SignedMsg", generate(mapper.writeValueAsString(obj)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return headers;
	}

	/**
	 * 云南请求参数加密
	 * 
	 * @param body
	 * @return
	 */
	@SuppressWarnings("restriction")
	private String generate(String body) {
		byte[] signedInfo = null;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream is = getClass().getClassLoader()
					.getResourceAsStream(ResourceUtils.CLASSPATH_URL_PREFIX + keyPath);
			if (is == null) {
				is = new FileInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + keyPath));
			}
			char[] nPassword = null;
			if ((ThirdApiConstants.YNTRUST_PRIVATECERT_PASSWORD == null)
					|| ThirdApiConstants.YNTRUST_PRIVATECERT_PASSWORD.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = ThirdApiConstants.YNTRUST_PRIVATECERT_PASSWORD.toCharArray();
			}
			ks.load(is, nPassword);
			is.close();
			Enumeration<String> enuml = ks.aliases();
			String keyAlias = null;
			if (enuml.hasMoreElements()) {
				keyAlias = (String) enuml.nextElement();
			}
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(prikey);
			byte[] dataInBytes = body.getBytes("UTF-8");
			signature.update(dataInBytes);
			signedInfo = signature.sign();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("云南加密失败:" + body, e);
		}
		return Base64.encode(signedInfo);
	}
	
	
	
}
