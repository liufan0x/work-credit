package com.anjbo.chromejs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * HttpUtil 访问http请求
 * @author Jerry
 * @version v1.0 HttpUtil.java 2013-9-25 上午10:58:36
 */
public class HttpUtil {

	private static int CONNTIMEOUT = 10000;
	/**
	 * post请求，格式为json，接收格式为json，@RequestBody
	 * @param strURL url
	 * @param obj 对象
	 * @return
	 */
    public static String jsonPost(String strURL, Object obj) {  
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
            String params=JSONObject.toJSONString(obj);
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
}
