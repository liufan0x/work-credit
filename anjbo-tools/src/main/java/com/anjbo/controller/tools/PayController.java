package com.anjbo.controller.tools;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.MapUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.anjbo.common.FreemarkerHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DateUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.PayUtil;
import com.anjbo.utils.XmlAndMap;


/**
 * 支付  调用：app
 * @author lic
 *
 * @date 2016-11-23 上午11:25:21
 */
@Component
@Controller
@RequestMapping("/tools/pay/v")
public class PayController {
	
	/**
	 * 微信查询订单
	 * @user Object
	 * @date 2016-11-24 上午11:29:13 
	 * @param params 需传入(微信订单号：transaction_id 或 商户订单号：out_trade_no  )
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@ResponseBody
	@RequestMapping(value = "/wxOrderquery")
	public RespDataObject<Map<String, Object>> wxOrderquery(@RequestBody Map<String, Object> params) throws IOException, DocumentException{
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		params.put("appid", ConfigUtil.getStringValue("WX_APPID"));
		params.put("mch_id", ConfigUtil.getStringValue("WX_MCH_ID"));
		params.put("nonce_str",PayUtil.getRandomStringByLength(32));
		params.put("sign", PayUtil.getWxSign(params));
		String xmlData = FreemarkerHelper.getIns().processTemplate("wxUnifiedOrder.ftl", params);
		String resultXml =  HttpUtil.sendPost(ConfigUtil.getStringValue("WX_ORDERQUERY_URL"), xmlData);
		Map<String, Object> resultAll = new HashMap<String, Object>();
		XmlAndMap.xml2map(resultXml,null,resultAll);
		if(MapUtils.getString(resultAll, "result_code").equals("SUCCESS")){
			System.out.println(MapUtils.getString(resultAll, "trade_state"));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		}
		return resp;
	}
	
	/**
	 * 微信结果回调
	 * @throws DocumentException 
	 * @user lic
	 * @date 2016-11-23 上午11:29:53 
	 */
	@ResponseBody
	@RequestMapping(value = "/wxNotify")
	public String wxNotify(@RequestBody String notifyReceiveDataXml) throws DocumentException{
		Map<String, String> params = new HashMap<String, String>();
		System.out.println(notifyReceiveDataXml);
		XmlAndMap.xml2map(notifyReceiveDataXml,null,params);
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
	
	/**
	 * 微信统一下单
	 * @user lic
	 * @date 2016-11-24 上午11:41:53 
	 * @param params  需传入   商户订单号：out_trade_no ， 金额：total_fee(单位：分,必须为整数) ，终端Ip：spbill_create_ip ，支付类型 ：trade_type ，回调Url:notify_url ，商品类型：good_type
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws DocumentException
	 */
	@ResponseBody
	@RequestMapping(value = "/wxPlaceOrder")
	public RespDataObject<Map<String, Object>> wxPlaceOrder(@RequestBody Map<String, Object> params) throws ClientProtocolException, IOException, ParserConfigurationException, SAXException, DocumentException{
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		Map<String, Object> xmlParams = new HashMap<String, Object>();
		xmlParams.put("appid", ConfigUtil.getStringValue("WX_APPID"));
		xmlParams.put("mch_id", ConfigUtil.getStringValue("WX_MCH_ID"));
		xmlParams.put("device_info", "WEB");
		xmlParams.put("nonce_str",PayUtil.getRandomStringByLength(32));
		xmlParams.put("time_start", DateUtil.getNowyyyyMMddHHmmss(new Date()));
		xmlParams.put("time_expire", DateUtil.getNowyyyyMMddHHmmss(DateUtil.nextHour(new Date(), 1)));
		xmlParams.put("notify_url", ConfigUtil.getStringValue("WX_NOTIFY_URL"));
		xmlParams.put("body", "快鸽按揭-订单号:"+params.get("out_trade_no"));
		xmlParams.put("out_trade_no", params.get("out_trade_no")+PayUtil.getRandomStringByLength(4));
		xmlParams.put("total_fee", params.get("total_fee"));
		xmlParams.put("Ip：spbill_create_ip", params.get("Ip：spbill_create_ip"));
		xmlParams.put("trade_type", params.get("trade_type"));
		xmlParams.put("openid", params.get("openid"));
		xmlParams.put("transaction_id", params.get("transaction_id"));
		xmlParams.put("attach", params.get("notify_url")+"-"+params.get("out_trade_no")+"-"+params.get("good_type"));
		xmlParams.put("sign", PayUtil.getWxSign(xmlParams));
		String xmlData = FreemarkerHelper.getIns().processTemplate("wxUnifiedOrder.ftl", xmlParams);
		String resultXml =  HttpUtil.sendPost(ConfigUtil.getStringValue("WX_UNIFIEDORDER_URL"), xmlData);
		
		Map<String, Object> resultAll = new HashMap<String, Object>();
		XmlAndMap.xml2map(resultXml,null,resultAll);
		
		if(!MapUtils.getString(resultAll, "result_code").equals("SUCCESS")){
			resp.setMsg(RespStatusEnum.SYSTEM_ERROR.getMsg());
			return resp;
		}
		
		//防止数据被篡改
		if(!PayUtil.getWxSign(resultAll).equals(MapUtils.getString(resultAll, "sign"))){
			resp.setMsg(RespStatusEnum.SIGNERROR.getMsg());
			return resp;
		}
		
		//APP生成签名，返回参数和签名
		if(MapUtils.getString(params, "trade_type").equals("APP")){
			Map<String, Object> tempRetMap = new HashMap<String, Object>();
			tempRetMap.put("appid", xmlParams.get("appid"));
			tempRetMap.put("partnerid", xmlParams.get("mch_id"));
			tempRetMap.put("prepayid", resultAll.get("prepay_id"));
			tempRetMap.put("package", "Sign=WXPay");
			tempRetMap.put("noncestr", PayUtil.getRandomStringByLength(32));
			tempRetMap.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
			tempRetMap.put("sign", PayUtil.getWxSign(tempRetMap));
			resultAll.putAll(tempRetMap);
		}
		
		resp.setData(resultAll);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	

	
	
	
}
