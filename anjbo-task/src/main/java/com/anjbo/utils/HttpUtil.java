package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.anjbo.bean.BaseDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

public class HttpUtil {
	/* 
     * 随机生成国内IP地址 
     */  
    public static String getRandomIp() {  
  
        // ip范围  
        int[][] range = { { 607649792, 608174079 },// 36.56.0.0-36.63.255.255  
                { 1038614528, 1039007743 },// 61.232.0.0-61.237.255.255  
                { 1783627776, 1784676351 },// 106.80.0.0-106.95.255.255  
                { 2035023872, 2035154943 },// 121.76.0.0-121.77.255.255  
                { 2078801920, 2079064063 },// 123.232.0.0-123.235.255.255  
                { -1950089216, -1948778497 },// 139.196.0.0-139.215.255.255  
                { -1425539072, -1425014785 },// 171.8.0.0-171.15.255.255  
                { -1236271104, -1235419137 },// 182.80.0.0-182.92.255.255  
                { -770113536, -768606209 },// 210.25.0.0-210.47.255.255  
                { -569376768, -564133889 }, // 222.16.0.0-222.95.255.255  
        };  
  
        Random rdint = new Random();  
        int index = rdint.nextInt(10);  
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));  
        return ip;  
    }  
  
    /* 
     * 将十进制转换成ip地址 
     */  
    public static String num2ip(int ip) {  
        int[] b = new int[4];  
        String x = "";  
  
        b[0] = (int) ((ip >> 24) & 0xff);  
        b[1] = (int) ((ip >> 16) & 0xff);  
        b[2] = (int) ((ip >> 8) & 0xff);  
        b[3] = (int) (ip & 0xff);  
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);  
  
        return x;  
    }  
	private static int CONNTIMEOUT = 10000;
	/**
	 * 
	 * get方式提交
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:55:58 
	 * @param url 服务连接
	 * @param charset 编码方式
	 * @param readTimeOut 超时时间
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url, String charset, int readTimeOut,String cookieValue,String Referer)
			throws Exception {
		GetMethod m = new GetMethod(url);
		StringBuilder rsp = new StringBuilder();
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Connection", "close");
			if(StringUtils.isNotEmpty(cookieValue)){
				m.setRequestHeader("Cookie", cookieValue);
			}
			if(StringUtils.isNotEmpty(Referer)){
				m.setRequestHeader("Referer", Referer);
			}
			String ip = getRandomIp();
			m.addRequestHeader("X-Forwarded-For",ip);
			m.addRequestHeader("Proxy-Client-IP",ip);
			m.addRequestHeader("WL-Proxy-Client-IP",ip);
			m.addRequestHeader("HTTP_CLIENT_IP",ip);
			m.addRequestHeader("X-Real-IP",ip);
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNTIMEOUT);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(readTimeOut);
			if (httpClient.executeMethod(m) == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						m.getResponseBodyAsStream(), charset));
				boolean first = true;
				String line;
				try {
					while ((line = br.readLine()) != null) {
						if (first) {
							first = false;
						} else {
							rsp.append('\n');
						}
						rsp.append(line);
					}
				} catch (Exception e) {
					throw e;
				} finally {
					try {
						br.close();
					} catch (Exception e) {
					}
				}
				return rsp.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				m.releaseConnection();
				SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (simpleHttpConnectionManager != null) {
					simpleHttpConnectionManager.shutdown();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * post提交
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:55:35 
	 * @param url 服务连接 
	 * @param charset 编码方式
	 * @param map 参数集
	 * @param readTimeOut 超时时间
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String post(String url, String hcharset, String charset,
			Map<String, String> map, int readTimeOut) throws Exception {
		PostMethod m = new PostMethod(url);
		StringBuilder rsp = new StringBuilder();
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Connection", "close");
			String cookieValue = map.get("cookieValue");
			if(StringUtils.isNotEmpty(cookieValue)){
				m.setRequestHeader("Cookie", cookieValue);
				map.remove("cookieValue");
			}
			String requested = map.get("requested");
			if(StringUtils.isNotEmpty(requested)){
				m.setRequestHeader("X-Requested-With", requested);
				map.remove("requested");
			}
			String referer = map.get("referer");
			if(StringUtils.isNotEmpty(referer)){
				m.setRequestHeader("Referer", referer);
				map.remove("referer");
			}
			if(StringUtils.isNotEmpty(hcharset)){
				m.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, hcharset);
			}

			String ip = getRandomIp();
			m.addRequestHeader("X-Forwarded-For",ip);
			m.addRequestHeader("Proxy-Client-IP",ip);
			m.addRequestHeader("WL-Proxy-Client-IP",ip);
			m.addRequestHeader("HTTP_CLIENT_IP",ip);
			m.addRequestHeader("X-Real-IP",ip);
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNTIMEOUT);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(readTimeOut);

			if (map != null && !map.isEmpty()) {
				NameValuePair[] nameVP = new NameValuePair[map.size()];
				int ii = 0;
				for (Entry<String, String> entry : map.entrySet()) {
					nameVP[ii] = new NameValuePair(entry.getKey(),
							entry.getValue());
					ii++;
				}
				m.setRequestBody(nameVP);
			}
			if (httpClient.executeMethod(m) == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						m.getResponseBodyAsStream(), charset));
				boolean first = true;
				String line;
				try {
					while ((line = br.readLine()) != null) {
						if (first) {
							first = false;
						} else {
							rsp.append('\n');
						}
						rsp.append(line);
					}
				} catch (Exception e) {
					throw e;
				} finally {
					try {
						br.close();
					} catch (Exception e) {
					}
				}
				return rsp.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				m.releaseConnection();
				SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (simpleHttpConnectionManager != null) {
					simpleHttpConnectionManager.shutdown();
				}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 
	 * UTF-8 方式提交请求，如果timeout为0，则timeout为10000
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:51:15 
	 * @param url 服务连接 
	 * @param map 参数集 
	 * @param timeout 超时时间
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> map, int timeout)
			throws Exception {
		if (timeout == 0) {
			timeout = 10000;
		}
		return post(url,"", "UTF-8", map, timeout);
	}
	
	/**
	 * 
	 * post请求，编码为 UTF-8 ，timeout 10000
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:48:09 
	 * @param url 服务连接
	 * @param map 参数集
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> map)
			throws Exception {
		return post(url,"", "UTF-8", map, 10000);
	}
	
	/**
	 * post请求
	 * @param url
	 * @param map
	 * @param hcharset 请求之前设置参数编码格式
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> map,String hcharset)
			throws Exception {
		return post(url,hcharset, "UTF-8", map, 10000);
	}
	
	/**
	 * post请求
	 * @param url
	 * @param map
	 * @param hcharset 请求之前设置参数编码格式
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> map,String hcharset,String charset)
			throws Exception {
		return post(url,hcharset, charset, map, 10000);
	}

	/**
	 * 
	 * get方式提交，编码为 UTF-8，如果timeout为0，则timeout为10000
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:53:05 
	 * @param url 服务连接
	 * @param timeout
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url, int timeout) throws Exception {
		if (timeout == 0) {
			timeout = 10000;
		}
		return get(url, "UTF-8", timeout,null,null);
	}

	/**
	 * 
	 * get方式提交
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:54:20 
	 * @param url 服务连接
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		return get(url, "UTF-8", 10000,null,null);
	}
	public static String get(String url,String cookieValue,String Referer) throws Exception {
		return get(url, "UTF-8", 10000,cookieValue,Referer);
	}
	public static boolean httpClientGet(String uri) throws Exception {
		try{
			HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		HttpMethod method = new GetMethod(uri);
		// 使用POST方法
		// HttpMethod method = new PostMethod("http://java.sun.com");
			int result=client.executeMethod(method);
			if(result==200){
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}
	/**
     * post请求，格式为json，接收格式为json，@RequestBody
     * @param strURL
     * @param map 参数
     * @return
     */
    public static String jsonPost(String strURL, Map<String,Object> map) {  
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true); 
            connection.setConnectTimeout(CONNTIMEOUT);
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            //对象转json字符串
            String params=JSONSerializer.toJSON(map).toString();
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            InputStream is = connection.getInputStream(); 
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line=null;
            StringBuilder sb=new StringBuilder();
            while((line=br.readLine())!=null){
            	sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return "error"; // 自定义错误信息  
    }
    
	public static RespStatus getRespStatus(String modular,String requestMapping,Object object){
		JSONObject json = getData(modular, requestMapping, object);
		RespStatus resp = (RespStatus)JSONObject.toBean(json,RespStatus.class);
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getObject(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespDataObject<T> resp = new RespDataObject<T>();
//		String[] dateFormats = new String[] {"yyyy/MM/dd"};
//		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
		resp = (RespDataObject<T>)JSONObject.toBean(json,resp.getClass());
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			if(!isWrapClass(clazz)){
				JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher()); 
//				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
				return (T)JSONObject.toBean(JSONObject.fromObject(resp.getData()),clazz);
			}else{
				return resp.getData();
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> RespDataObject<T> getRespDataObject(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespDataObject<T> resp = new RespDataObject<T>();
		resp = (RespDataObject<T>)JSONObject.toBean(json,resp.getClass());
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			if(!isWrapClass(clazz)){
				resp.setData((T)JSONObject.toBean(JSONObject.fromObject(resp.getData()),clazz));
			}else{
				return resp;
			}
		}
		
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  static <T> List<T> getList(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespData<T> resp = (RespData<T>)JSONObject.toBean(json,RespData.class);
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			List<T> list =  new ArrayList<T>(); 
			for (T t : resp.getData()) {
				if(!isWrapClass(clazz)){
					JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher()); 
					list.add((T)JSONObject.toBean(JSONObject.fromObject(t),clazz));
				}else{
					list.add(t);
				}
			}
			return list;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> RespData<T> getRespData(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespData<T> resp = (RespData<T>)JSONObject.toBean(json,RespData.class);
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			List<T> list =  new ArrayList<T>(); 
			for (T t : resp.getData()) {
				if(!isWrapClass(clazz)){
					JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher()); 
					list.add((T)JSONObject.toBean(JSONObject.fromObject(t),clazz));
				}else{
					list.add(t);
				}
			}
			resp.setData(list);
		}
		return resp;
	}
	
	public static <T> JSONObject getData(String modular,String requestMapping,Object object){
		if(object == null) object = new Object();
		String url = modular+"/"+requestMapping;
		return jsonPost(url,object);
	}
	
    @SuppressWarnings("rawtypes")
	private static boolean isWrapClass(Class clz) { 
        try { 
           return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) { 
            return false; 
        } 
    } 
    /**
	 * post请求
	 * @param url
	 * @param params 参数
	 * @return
	 */
	private static <T> JSONObject jsonPost(String url, Object object) {
		JSONObject json = null;
		RespStatus resp = new RespStatus();
		RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		try {
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpClient client = HttpClients.createDefault();
			StringEntity entity = new StringEntity(JSONSerializer.toJSON(object).toString(),"utf-8");
			entity.setContentEncoding("UTF-8");    
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpPost.addHeader("key", (new BaseDto()).getKey());
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity he = response.getEntity();
			    json = JSONObject.fromObject(EntityUtils.toString(he,"UTF-8"));
				return json;
			}
		}catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,"请求接口异常");
		}
		json = JSONObject.fromObject(resp);
		return json;
	}
	
	
}
