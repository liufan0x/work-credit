package com.anjbo.utils.erongsuo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.google.gson.Gson;
import com.netflix.client.http.HttpResponse;





public class ErongSuoUtils {
	private Logger log = Logger.getLogger(getClass());
	public  static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	private static final String URLS= "http://218.17.46.176:7020";
	public static final String DEFAULT_PRIVATE_KEY =
	            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLzcCAoQaBK3X0cPbsu1xKUsuwQ\n" +
	                    "vtVz12t16ARd2fza5H6A3aekI1DZV/+U6IT7LQm/75uOlTpek7W8nRZJsrxqK523RILCds5UEkGE\n" +
	                    "yM87RulTtL+acMBUg3nPxUQiBKj7W79VNDmxX5pO55d/tlIXl9tldQahykkLNSS8HTLzAgMBAAEC\n" +
	                    "gYAhqBOAmNGu+iWqMDOUDv04a2szZvrdXoo3lddratNi8TBkcow9yWsy+43HbhRFXpBb8xN3qFt8\n" +
	                    "vOj/F1hcJsRMxikzSnnDPtri48mx6esMG2v/Jt+eb5tfk4fTmboXXWRMiSjFG8B6cAaCxEwaykSV\n" +
	                    "CSI4S23aWrnJ9I6tezNDQQJBAPiQ7OqNb1/J7nWsKeWZCLLWYwZK2wCmb+IQZ8WUi4mIUlj6tkBX\n" +
	                    "rD+XAmdK1OXgjLHUlMAM0GZ0NQJfD++FdvECQQC4TYYliyJTjXoWDEBWWaVASRfK7RXBq8JTeXGg\n" +
	                    "5bjQZzJsmfqS5aU8Qz1AZyc9gjXuMJMqszv+WuyQ5vyLHPAjAkEAwvC2PcWqoU83KyZYvW5lugwV\n" +
	                    "IWw3kaz2di8zk2tKfBRjsND/ejrIJh8CjYvMqHSRIy57cpsaHh/pKvDvCIR9oQJBAKGceVFanBMg\n" +
	                    "MDo9K/2MRngEoDR1iWp2rsR77cPlLRayJ2lL7In7jdU2MPPUgHhTQe9H8QS0fpsgJ+k4Y6OpEHkC\n" +
	                    "QBvpHMdDZG9e7+eTeHTss5eZ6MzEA0umVZYrk+9egqDD5bVl11E/A6xoAMzRPsfsBxxaltnXwCyr\n" +
	                    "HjjWbeNVKso=";
	public static final String DEFAULT_PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIBDGXKV781mjh2/nL0EexmBBijbtZ8aaOIDHWfURhbQWckU2QamzR+"
            + "KpQAGKMDzxGa47vu6a3w5aWB3YWLBW1Y+KOE1siNpr4"
            + "+zATRysUrDBJvb7Ga5psG3jbyq1"
            + "+Eb16BenN6snBahmiXesMaH2m5jrHYyXowYWRCwK7OWPA3QIDAQAB";
	public static String eRongToken() {
		String responseStr = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url="http://218.17.46.176:7020/auth/oauth/token?grant_type=client_credentials";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Authorization","Basic a3VhaWdlX2NsaWVudDoyOTkxYTE5MTJhY2M0YWQ3YTY1MzAwNTlkNzQwOWQzMw==");
		try {
			 CloseableHttpResponse response =  httpclient.execute(httpGet);
			 if (null!=response.getEntity()) {
				 responseStr =EntityUtils.toString(response.getEntity());
				 Gson gson = new Gson();
				 Map<String,Object> map = new HashMap<String,Object>();
				 map=gson.fromJson(responseStr,map.getClass());
				 return "bearer "+MapUtils.getString(map,"access_token");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//开户测试
	public static void openAccount(String url){
	 HttpPost httpPost = null;
	 HttpClient httpClient = new DefaultHttpClient();
	 url = URLS+"/erong-cfss-fes/p2p/spring/account/register";
	 JSONObject jsonObject;
	 httpPost = new HttpPost(url);
	 httpPost.setHeader("Authorization",eRongToken());
	 httpPost.setHeader("Content-Type","application/json");
	 Map<String,Object> map = new HashMap<String,Object>();
	 map.put("orderNo","2019011013581100299");
	 map.put("channelType","ANDROID");
	 map.put("assetChannel","KG5438");
	 map.put("idCard","43062119940319271X");
	 map.put("realName","李岳");
	 map.put("mobile","15018531908");
	 map.put("bankcardNo","6228481742434482713");
	 map.put("retUrl","https://www.baidu.com/");
	 map.put("notifyUrl","https://www.baidu.com/");
     try {
    	 
    	/*JSONObject jsonObject  = new JSONObject(map);
     	StringEntity stringEntity =  new StringEntity(jsonObject.toString(),"UTF-8");
     	httpPost.setEntity(stringEntity);
 		String slong = EntityUtils.toString(httpClient.execute(httpPost).getEntity());*/
    	 
    	jsonObject  = new JSONObject(map);
    	PublicKey publicKey =RSAUtils.loadPublicKey(DEFAULT_PUBLIC_KEY);
    	PrivateKey privateKey =RSAUtils.loadPrivateKey(DEFAULT_PRIVATE_KEY);
 		String randomKey = String.valueOf(System.currentTimeMillis());                               //获取随机KEY randomKey
 		String cipherText = AESUtils.encrypt(jsonObject.toString(),randomKey);                       //AES加密请求内容cipherText
 		String cipherKey = Base64.encodeBase64String(RSAUtils.RSAEncode(publicKey, randomKey.getBytes()));
 		Map<String,Object> str = new HashMap<String,Object>();
 		str.put("cipher_text",cipherText);
 		str.put("cipher_key",cipherKey);
 		String strings = new JSONObject(str).toString();
    	StringEntity stringEntity =  new StringEntity(new JSONObject(str).toString(),"UTF-8");
    	httpPost.setEntity(stringEntity);
		String slong = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
		System.err.println(slong+"打印中......");
	} catch (Exception e) {
		e.printStackTrace();
	}
     
	}
	
	
	
	public static RespDataObject<Map<String, Object>> interfacEntry(Map<String,Object> map) {
		 RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		 String url = MapUtils.getString(map,"url"); 
		 if (StringUtils.isBlank(url)) {
			 resp.setCode("FAIL");
			 resp.setMsg("请输入请求路径");
			 return resp;
			}
		 map.remove("url");
		 HttpPost httpPost = null;
	     HttpClient httpClient = new DefaultHttpClient();
	     url = URLS+url;
	     JSONObject jsonObject;
		 httpPost = new HttpPost(url);
		 httpPost.setHeader("Authorization",eRongToken());
		 httpPost.setHeader("Content-Type","application/json");
		 try {
		    	jsonObject  = new JSONObject(map);
		    	PublicKey publicKey =RSAUtils.loadPublicKey(DEFAULT_PUBLIC_KEY);
		 		String randomKey = String.valueOf(System.currentTimeMillis());                               //获取随机KEY randomKey
		 		String cipherText = AESUtils.encrypt(jsonObject.toString(),randomKey);                       //AES加密请求内容cipherText
		 		String cipherKey = Base64.encodeBase64String(RSAUtils.RSAEncode(publicKey, randomKey.getBytes()));
		 		Map<String,Object> str = new HashMap<String,Object>();
		 		str.put("cipher_text",cipherText);
		 		str.put("cipher_key",cipherKey);
		    	StringEntity stringEntity =  new StringEntity(new JSONObject(str).toString(),"UTF-8");
		    	httpPost.setEntity(stringEntity);
				String slong = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
				System.err.println(slong+"打印中......");
				Map<String,Object> contents = JSON.parseObject(slong);
				if (StringUtils.isNotBlank(MapUtils.getString(contents,"errorDesc"))) {
					resp.setCode("FAIL");
					resp.setMsg(MapUtils.getString(contents,"errorDesc"));
					return resp;
				}
				resp.setCode("SUCCESS");
				resp.setData(JSON.parseObject(MapUtils.getString(contents,"data")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		 return resp;
	}
	//不加密参数  影像资料上传
	public static RespDataObject<Map<String, Object>> imgUpload(Map<String,Object> map) {
		 RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		 String url = MapUtils.getString(map,"url"); 
		 if (StringUtils.isBlank(url)) {
			 resp.setCode("FAIL");
			 resp.setMsg("请输入请求路径");
			 return resp;
			} 
		 map.remove("url");
	     String [] arr = new String[3];
	     /*arr[0]="C:\\Users\\Administrator\\Desktop\\textImage\\aa.jpg";
	     arr[1]="C:\\Users\\Administrator\\Desktop\\textImage\\caohuzi.jpg";
	     arr[2]="C:\\Users\\Administrator\\Desktop\\textImage\\jing.jpg";*/
	     arr[0]="http://fs.anjbo.com/img/fc-img/9fda7c290a3a46ad9580ef4b17b56925_48.jpg";
	     arr[1]="http://fs.anjbo.com/img/fc-img/d975770d12ff477794e4c5d4a36a9f39_48.png";
	     arr[2]="http://fs.anjbo.com/img/fc-img/2930061953014f69a788deceabeb2086_48.jpg";
	     MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		 try {
			 for (int i = 0; i < arr.length; i++) {
	    	     File f = new File(arr[i]);
	    		 builder.addBinaryBody("files",new FileInputStream(f), ContentType.APPLICATION_OCTET_STREAM, f.getName());      //  addPart("files",multipartFiles);
	    	 }
		 
		 builder.addTextBody("typeIdList","910101-910-0,910102-910-0,910103-910-0");
		 builder.addTextBody("barcode","web-22111453383-1");
		 builder.addTextBody("applyNo","2019011013581100232");
		 HttpPost httpPost = null;
	     HttpClient httpClient = new DefaultHttpClient();
	     url = URLS+url;
	     JSONObject jsonObject;
		 httpPost = new HttpPost(url);
		 httpPost.setEntity(builder.build());
		 String slong = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
		 System.err.println(slong+"打印中......");
		 Map<String,Object> contents = JSON.parseObject(slong);
		 if (StringUtils.isNotBlank(MapUtils.getString(contents,"errorDesc"))) {
					resp.setCode("FAIL");
					resp.setMsg(MapUtils.getString(contents,"errorDesc"));
					return resp;
				}
				resp.setCode("SUCCESS");
				resp.setMsg(MapUtils.getString(contents,"resultDesc"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		 return resp;
	}
	
	
	public static void main(String[] args) {	 	
		
		/*List<Map<String,Object>> contacts = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("contactName","张三");
		map1.put("contactPhone","15002179817");
		map1.put("contactRelation","配偶");
		contacts.add(map1);
		
		Map<String,Object> riskReport = new HashMap<String,Object>();
		riskReport.put("platformGradeRes","Y");
		riskReport.put("incomeLiabilityRes","Y");
		riskReport.put("negativeMessageRes","Y");

		Map<String,Object> map = new HashMap<String,Object>();
		 map.put("source","jy");
		 map.put("paytype","0");
		 map.put("accounttype","B");
		 map.put("applyNo","2019011013581100309");
		 map.put("prdId","0020006");
		 map.put("prdName","快鸽分期");
		 map.put("loanAmt","3000");
		 map.put("loanTerm","2");
		 map.put("applyLoanPurpose","商用");
		 map.put("borrowerMobile","15018531908");
		 map.put("borrowerName","李健豪");
		 map.put("borrowerIdType","001");
		 map.put("borrowerIdCard","430621199403192817");
		 
		 map.put("businessName","深圳市跑得快运输有限公司");
		 map.put("businessIdType","股份有限责任公司");
		 map.put("businessId","340191000026884");
		 map.put("corporateName","马大哈");
		 map.put("corporateIdType","001");
		 map.put("corporateId","430621199403192817");
		 map.put("corporatePhone","15002177066");
		 
		 map.put("payeeAccount","132456");
		 map.put("payeeMobile","15018531908");
		 map.put("payeeName","JKRQYZH1101");
		 map.put("payeeIdCard","430621199403192817");
		 map.put("repaymentType","1");
		 map.put("loanRate","0.65");
		 map.put("email","992753452@qq.com");
		 map.put("bankName","1");
		 map.put("bankCardNo","6212260508002821553 ");
		 map.put("accountBankId","123");
		 map.put("accountOwnerName","张三");
		 map.put("accountOwnerPhone","15002179817");
		 map.put("accountOwnerId","430621199403192715");
		 map.put("education","BS");
		 map.put("maritalStatus","0");
		 map.put("workCondition","003");
		 map.put("incomeRange","3");
		 map.put("affProvinceName","广东省");
		 map.put("affCityName","深圳市");
		 map.put("affAreaName","南山区锦衣大道");
		 map.put("houseProperty","001");
		 map.put("address","馨月府邸");
		 map.put("contacts",contacts);
		 map.put("riskReport",riskReport);
		 map.put("url","/erong-cfss-fes/pas/consume/addLoanApply");
		 RespDataObject<Map<String, Object>> sDataObject = interfacEntry(map);
		 System.err.println(sDataObject+"打印中......"+sDataObject.getData());*/
		//openAccount("465");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("url","/erong-cfss-fes/img/upload");
		imgUpload(map);
	}
	
}
