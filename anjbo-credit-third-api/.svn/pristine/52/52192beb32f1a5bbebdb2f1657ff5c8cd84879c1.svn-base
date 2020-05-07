/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.utils.icbc.ClientUtil.java  <2017年10月19日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.utils.icbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import cn.com.icbc.CMS.commontools.TranslationTool;
import cn.com.infosec.icbc.ReturnValue;

import com.anjbo.bean.icbc.ThirdIcbcQpdDto;
import com.anjbo.bean.icbc.bo.ResQPD;
import com.anjbo.lang.ObjectBase;
import com.anjbo.service.icbc.ThirdIcbcQpdService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.DesEncrypterUtil;
import com.anjbo.utils.FileUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.UidUtil;

/**
 * 工行银企互联，客户端工具类(数据请求发起)
 * @Author KangLG 
 * @Date 2017年10月19日 下午6:28:54
 * @version 1.0
 */
@Component
public class IcbcClientUtil extends ObjectBase {
	@Autowired ThirdIcbcQpdService thirdIcbcQpdService;
	private static ParamUtil paramXML = ParamUtil.getInstance();
	private final boolean signflag = true;// 交易是否需要签名，根据实际交易修改或者配置
	
	/**
	 * 工行数据传输接口
	 * @Author KangLG<2017年10月23日>
	 * @param tranCode 交易代码
	 */
	public void send(TranCodeEnum tranCode) {
		try {
			byte[] bodyData = TranslationTool.readFile(FileUtil.getFilePath("icbc/send/"+tranCode+".xml"));
			this.send(tranCode, bodyData, "");
		} catch (IOException e) {
			logger.error("无法读取指定数据");
		}
	}
	
	/**
	 * 工行数据传输接口
	 * @Author KangLG<2017年10月23日>
	 * @param tranCode 交易代码
	 * @param bodyData 数据传输信息
	 * @param nextTag 下一页(首次/无下一页留空)
	 */
	private void send(TranCodeEnum tranCode, byte[] bodyData, String nextTag) {
		byte[] signature = this.sign(bodyData);
		if(null == signature){
			return ;
		}
		
		// 加载指令参数信息
		FileInputStream fis = null;
		byte[] certificate = null;		
		try {
			fis = new FileInputStream(new File(FileUtil.getFilePath(paramXML.getCerfile())));
			certificate = new byte[fis.available()];
			fis.read(certificate);// 私钥			
		} catch (IOException e4) {
			logger.error("无法读取配置企业数据层公钥文件");
			return;
		} finally {
			if(null != fis){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 构建接口请求		
		try {
			HttpPost httpPost = new HttpPost(String.format("https://%s:%s", paramXML.getIcbcIp(), paramXML.getIcbcPort()));
			httpPost.setEntity(new UrlEncodedFormEntity(this.buildPair(tranCode, bodyData, nextTag, signature, certificate),"GBK"));
			
			CloseableHttpClient httpclient = HttpUtil.createSSLClientDefault(true);			
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			String repMsg = null;//EntityUtils.toString(httpResponse.getEntity());			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                if(httpEntity.getContentEncoding()!=null){  
	                if("gzip".equalsIgnoreCase(httpEntity.getContentEncoding().getValue())){  
	                    httpEntity = new GzipDecompressingEntity(httpEntity);                 
	                } else if("deflate".equalsIgnoreCase(httpEntity.getContentEncoding().getValue())){  
	                    httpEntity = new DeflateDecompressingEntity(httpEntity);              
	                }
	            }  
                repMsg = EntityUtils.toString(httpEntity, "GBK");// 取出应答字符串  
			}
            
            boolean isSuccess = repMsg.startsWith("reqData=");//reqData=,errorCode=			
            logger.info(String.format("已接收到(%s)返回信息:%s", tranCode, isSuccess));
            if(isSuccess){
    			this.addAndNext(tranCode, bodyData, nextTag, DesEncrypterUtil.decodeBase64Sun(repMsg.substring(8)));
            }else{
            	logger.error(DesEncrypterUtil.decodeBase64Sun(repMsg.substring(10)));
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 构建接口请求参数
	 * @Author KangLG<2017年10月23日>
	 * @param tran_code
	 * @param bodyData
	 * @param nextTag
	 * @param signature
	 * @param certificate
	 * @return
	 */
	@SuppressWarnings("static-access")
	private List<NameValuePair> buildPair(TranCodeEnum tranCode, byte[] bodyData, String nextTag, byte[] signature, byte[] certificate){
		String packageID = UidUtil.generateSerialId();
		Date curDate = new Date();
		// 接口参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();	
		params.add(new BasicNameValuePair("Version", paramXML.getAnjboVersion()));  
		params.add(new BasicNameValuePair("GroupCIS", paramXML.getCis()));  
		params.add(new BasicNameValuePair("BankCode", paramXML.getBankcode()));  
		params.add(new BasicNameValuePair("TransCode", tranCode.name()));  
		params.add(new BasicNameValuePair("ID", paramXML.getAnjboId()));  
		params.add(new BasicNameValuePair("Cert", DesEncrypterUtil.encoderBase64Sun(certificate)));  
		params.add(new BasicNameValuePair("PackageID", packageID));  
		// 动态组装reqData参数
		String bodyXml = new String(bodyData);
		switch (tranCode) {
			case QPD:
				// TranDate, TranTime, fSeqno(packageID), NextTag
				bodyXml = bodyXml.format(bodyXml, DateUtils.dateToString(curDate, "yyyyMMdd"), DateUtils.dateToString(curDate, "HHmmssSSS"), packageID, nextTag); 
				break;	
			default:
				break;
		}
		if (signflag) {// 需要签名做以下操作，否则放明文
			String length = String.valueOf(bodyXml.length());
			if (length.length() <= 10)
				length = ("0000000000").substring(0, 10 - length.length())
						+ length;// 长度补齐10位，左补0
			bodyXml = length + bodyXml + "ICBCCMP" + DesEncrypterUtil.encoderBase64Sun(signature);

		}		
		params.add(new BasicNameValuePair("reqData", DesEncrypterUtil.encoderBase64Sun(bodyXml.getBytes())));  // 请根据实际修改上送包xml数据
		
		return params;
	}	
	
	/**
	 * 数据(按分页)同步到本地数据库
	 * @Author KangLG<2017年10月23日>
	 * @param tran_code
	 * @param bodyData
	 * @param nextTag
	 * @param resXml
	 */
	private void addAndNext(TranCodeEnum tranCode, byte[] bodyData, String nextTag, String resXml){
		Matcher m = Pattern.compile("(<[/]?[A-Z])").matcher(resXml);
		while (m.find()) {
			resXml = resXml.replace(m.group(), m.group().toLowerCase());
		}
		logger.debug(resXml);
		
		boolean isNext = false;
		try{
			JSONObject json = XML.toJSONObject(resXml);		
			JSONObject jsonOut = json.getJSONObject("cMS").getJSONObject("eb").getJSONObject("out");
			if(StringUtils.isEmpty(jsonOut.getString("totalNum")) || jsonOut.getInt("totalNum")<1){
				return;
			}
			if(StringUtils.isNotEmpty(jsonOut.getString("nextTag"))){   //是否有下一页
				nextTag = jsonOut.getString("nextTag");
				isNext = true;
			}
			ResQPD resBean = (ResQPD)JsonUtil.parseJsonToObj(jsonOut.toString(), ResQPD.class);
			if(resBean.getTotalNum()>0 && null!=resBean.getRd() && resBean.getRd().size()>0){
				for (ThirdIcbcQpdDto dto : resBean.getRd()) {
					dto.setParentNode(resBean);
					try {
						dto.setCreateUid("schedule");
						thirdIcbcQpdService.insert(dto);				
					} catch (Exception e) {	
						if(e instanceof DuplicateKeyException && e.toString().contains("idx_")){
							logger.info("当前记录已录入(中断)..."); //暂定之前的数据都录入了
							isNext = false;
							break;
						}			
						e.printStackTrace();
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}				

		if(isNext){			
			try {
				logger.debug("下一页...");
				Thread.sleep(5*1000);
				this.send(tranCode, bodyData, nextTag);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}			
		}
	} 
		
	/**
	 * 签名
	 * @Author KangLG<2017年10月20日>
	 * @param bodyData
	 * @return 签名成功返回签名码，否则返回空
	 */
	private byte[] sign(byte[] bodyData){
		byte[] signature = null;
		try {
			byte[] key = TranslationTool.readFile(FileUtil.getFilePath(paramXML.getKey()));
			signature = ReturnValue.sign(bodyData, bodyData.length, key, paramXML.getKeypass().toCharArray());
		} catch (IOException e2) {
			logger.error("无法读取企业数据层私钥文件");
			return null;		
		} catch (Exception e3) {
			logger.error("无法生成签名");
			return null;
		}		
		return signature;
	}

}
