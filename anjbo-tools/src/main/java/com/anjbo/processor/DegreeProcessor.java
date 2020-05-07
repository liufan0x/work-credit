package com.anjbo.processor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.mozilla.javascript.NativeObject;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.XXTEA;

/**
 * 学位查询工具类
 * @author limh limh@anjbo.com   
 * @date 2017-4-15 下午03:59:18
 */
public class DegreeProcessor {
	private BasicCookieStore cookieStore = new BasicCookieStore();
	private String baseUrl;
	private String houseNo;
	private String code;
	public DegreeProcessor(String baseUrl,String houseNo,String code){
		this.baseUrl = baseUrl;
		this.houseNo = houseNo;
		this.code = code;
	}
	public static void main(String[] args) {
		/*Map<String,String> param = new HashMap<String,String>();
		
		param.put("http://nsjyk.sz.edu.cn:8008","4403050040010200127000018");//南山
		param.put("http://lhjcjy.sz.edu.cn:8008","4403060080010300049000001");//龙华
		param.put("http://ftjyk.sz.edu.cn:8008","4403040010020500001000485");//福田
		param.put("http://lhjjk.sz.edu.cn:8008","4403030040060200037000145");//罗湖
		param.put("http://121.34.248.233:8008","4403070020171700128000001");//龙岗
		param.put("http://gmjjk.sz.edu.cn:8008","4403060050090200030000036");//光明
		for (String key : param.keySet()) {
			RespDataObject<String> status = new DegreeProcessor(key,param.get(key)).getDegree();
			System.out.println(status.getMsg()+" "+status.getData());
		}*/
		String url = "http://lhjjk.sz.edu.cn/visitlhgbxyxqfcx";
		//getToken(url);
		String fwtype="0";
		String token="45a02a89e67640b88753a823861de608";
		String fwbm="4403030070070900016000002";
		try {
			String str= XXTEA.encryptWithBase64(fwtype.getBytes(), token.getBytes());
            str = str.replace("+", "_abc123");
            str = str.replace("-", "_def456");
            str = str.replace("=", "_ghi789");
            str = str.replace("/", "_jkl098");
            str = str.replace("*", "_mno765");
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public RespDataObject<String> getDegree(){
		String token = getToken(baseUrl,code);
		String result = getStatus(baseUrl,houseNo,token);
		return degreeDesc(result);
		
	}
	public static RespDataObject<String> degreeDesc(String statusString) {
		RespDataObject<String> status = new RespDataObject<String>();
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.THIRD_FAIL.getMsg());
		if(StringUtils.isEmpty(statusString)){
			return status;
		}
		try {
			statusString = StringEscapeUtils.unescapeHtml(statusString);
			JSONObject jsonObject = JSONObject.fromObject(statusString);
			if (jsonObject.getString("statusCode").equals("200")) {
				JSONObject datas = jsonObject.getJSONObject("datas");
				JSONArray fwlist = datas.getJSONArray("fwlist");
				if (fwlist==null) {
					return status;
				}
				if (fwlist.size() == 0) {
					status.setData("查询结果:无学区");
				} else {
					StringBuffer sb = new StringBuffer();
					for (int i=0;i<fwlist.size();i++) {
						JSONObject json = (JSONObject) fwlist.get(i);
						sb.append(",").append(json.getString("STATUS_N"));
						if(StringUtil.isNotBlank(json.getString("XXNAME"))&&!"null".equals(json.getString("XXNAME"))) {
							sb.append("【").append(json.getString("XXNAME")).append("】");
						}
					}
					if(sb.length()>0){
						status.setData("查询结果："+sb.substring(1));
					}
				}
			}else{
				String msg = jsonObject.getString("msg");
				if(StringUtils.isNotEmpty(msg)){
					status.setMsg(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotEmpty(status.getData())){
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		}
		return status;
	}
	private String getToken(String baseUrl,String code){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            //执行请求
           /* HttpPost post = new HttpPost(baseUrl+"/visitor/open/toIndex/00000000000000000000000000000000");
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    
                    entity = httpResponse.getEntity();
                    String result=EntityUtils.toString(entity,"UTF-8");
                    HttpGet get = new HttpGet(baseUrl+"/zsLogin/getLoginData");  
                    httpResponse = httpClient.execute(get);
                    entity = httpResponse.getEntity();
                    result=EntityUtils.toString(entity,"UTF-8");
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    String re = jsonObject.getString("token");
                    return re;
                }
            } finally{
                httpResponse.close();
            }*/
        	String url = baseUrl;
        	if(code.equals("XWCXLH")) {//罗湖
        		url+="/visitlhgbxyxqfcx";
        	}else if(code.equals("XWCXNS")) {//南山
        		url+="/visitnsgbxyxqfcx";
        	}else if(code.equals("XWCXLG")) {//龙岗
        		url+="/visitlggbxyxqfcx";
        	}else if(code.equals("XWCXLHA")) {//龙华
        		url+="/visitlhuagbxyxqfcx";
        	}else if(code.equals("XWCXFT")) {//福田
        		url+="/visitftgbxyxqfcx";
        	}else if(code.equals("XWCXGM")) {//光明
        		url+="/visitgmgbxyxqfcx";
        	}
        	HttpGet get = new HttpGet(url);  
        	CloseableHttpResponse httpResponse = httpClient.execute(get);
        	HttpEntity entity = httpResponse.getEntity();
        	String result=EntityUtils.toString(entity,"UTF-8");
        	String re = result.substring(result.indexOf("KWZ.token ='")+12, result.indexOf("KWZ.token ='")+44);
        	System.out.println("re:"+re);
        	return re;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
	}
	private String getStatus(String url,String fwbm,String token){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		httpClient = HttpClients.custom()
		.setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost post = new HttpPost(url+"/zs_xqfdzlocksearch/open/doXqfDzLockSearch");          
            //创建参数列表
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            /*Map<String,Object> map=getParam(fwbm,token);
            String FWTYPE="";
            for(Object m:map.keySet()){
            	if(m.equals("FWTYPE")){
            		FWTYPE=(String) map.get(m);
            	}
            	if(m.equals("fwxx")){
            		fwbm=(String) map.get(m);
            	}
			}
            list.add(new BasicNameValuePair("FWTYPE", FWTYPE));
            list.add(new BasicNameValuePair("fwxx", fwbm));*/
            fwbm = XXTEA.encryptWithBase64(fwbm.getBytes(), token.getBytes());
            fwbm = XXTEA.kwencrypt(fwbm);
            String fwType = XXTEA.encryptWithBase64("0".getBytes(), token.getBytes());
            fwType= XXTEA.kwencrypt(fwType);
            list.add(new BasicNameValuePair("FWTYPE", fwType));
            list.add(new BasicNameValuePair("FWBM", fwbm));
            list.add(new BasicNameValuePair("token", token));
            //url格式编码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
            post.setEntity(uefEntity);
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(addHeader(post));
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    String result=EntityUtils.toString(entity,"UTF-8");
                    return result;
                }
            } finally{
                httpResponse.close();
            }
             
        } catch( UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	private Map<String,Object> getParam(String fwbm,String token){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String basePath = System.getProperty("web.root");
			//basePath="D:\\workspace\\anjbo-tools\\src\\main\\webapp";
			FileReader reader = new FileReader(new File(basePath+"/js/encrypt.js")); // 执行指定脚本
			engine.eval(reader);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数
				NativeObject c=(NativeObject)invoke.invokeFunction("merge", fwbm,token);
				map = obj2map(engine, c);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	private Map<String, Object> obj2map(ScriptEngine engine,  
            Object nativeObject) throws ScriptException, NoSuchMethodException {  
        Map<String, Object> map = new HashMap<String, Object>();  
  
        engine.put("map", map);  
        engine.put("obj", nativeObject);  
        String script = "   function dosomething(){                                         "  
                + "                         for (i in obj){                                         "  
                + "                             map.put(i,obj[i]);                                  "  
                + "                         }                                                       " + "                   }                                                               ";  
        engine.eval(script);  
  
        Invocable inv = (Invocable) engine;  
        inv.invokeFunction("dosomething");  
        return map;  
  
    } 
	private HttpRequestBase addHeader(HttpRequestBase httpRequestBase) {
    	httpRequestBase.setHeader("Accept", "*/*");
    	httpRequestBase.setHeader("Accept-Encoding", "gzip, deflate");
    	httpRequestBase.setHeader("Accept-Language","zh-CN,zh;q=0.9");
    	httpRequestBase.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequestBase.setHeader("Host", "lhjcjy.sz.edu.cn");
		httpRequestBase.setHeader("Referer","http://lhjjk.sz.edu.cn/visitlhgbxyxqfcx");
		httpRequestBase.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
		httpRequestBase.setHeader("X-Requested-With", "XMLHttpRequest");
		return httpRequestBase;
	}
}
