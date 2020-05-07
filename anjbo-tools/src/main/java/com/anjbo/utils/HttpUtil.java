package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.codec.Charsets;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.SecureRandom;
import java.security.cert.CertificateException;

/**
 * 
 * HttpUtil 访问http请求
 * @author Jerry
 * @version v1.0 HttpUtil.java 2013-9-25 上午10:58:36
 */
public class HttpUtil {

	private static int CONNTIMEOUT = 10000;
	private static final Log log = LogFactory.getLog(HttpUtil.class);
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
            String params=JSONObject.fromObject(obj).toString();
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
	@SuppressWarnings("deprecation")
	public static String postForHeader(String url,Map<String, String> map,int timeOut) throws Exception {
		PostMethod m = new PostMethod(url);
		StringBuilder rsp = new StringBuilder();
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Connection", "close");
			String contentType = map.get("contentType");
			if(StringUtils.isNotEmpty(contentType)){
				m.setRequestHeader("Content-Type",contentType);
				map.remove("contentType");
			}
			String deviceId = map.get("deviceId");
			if(StringUtils.isNotEmpty(deviceId)){
				m.setRequestHeader("deviceId",deviceId);
			}
			String sid = map.get("sid");
			if(StringUtils.isNotEmpty(sid)){
				m.setRequestHeader("sid",sid);
			}
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNTIMEOUT);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(timeOut);
			m.setRequestBody(JsonUtil.parserObjToString(map));
			if (httpClient.executeMethod(m) == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						m.getResponseBodyAsStream(), "UTF-8"));
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
	 * 不需要商户证书，发送https请求
	 * 
	 * @param httpsUrl
	 * @param xmlObj
	 * @return
	 * @throws IOException 
	 */
	public static String sendPost(String httpsUrl, String xmlObj) throws IOException {
		HttpsURLConnection urlCon = null;
		StringBuffer temp = new StringBuffer();
		urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
		urlCon.setDoInput(true);
		urlCon.setDoOutput(true);
		urlCon.setRequestMethod("POST");
		urlCon.setRequestProperty("Content-Type", "text/xml");
		urlCon.setConnectTimeout(10000);
		urlCon.setReadTimeout(20000);
		urlCon.setUseCaches(false);
		urlCon.getOutputStream().write(xmlObj.getBytes("UTF-8"));
		urlCon.getOutputStream().flush();
		urlCon.getOutputStream().close();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlCon.getInputStream(),"utf-8"));
		String line;
		while ((line = in.readLine()) != null) {
			temp.append(line);
		}
		String result = temp.toString();
		return result;
	}
	
	
	/**
	 * post提交得到cookie
	 * @Title: getCookieForPost 
	 * @param @param url
	 * @param @param map
	 * @param @param reGetCookie 是否重新获取，重新获取直接跳过读取缓存的cookie，不重新获取如果缓存没有也会自动重新获取
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getCookieForPost(String url,Map<String, String> map,boolean reGetCookie) throws Exception {
		if(!reGetCookie){
			System.out.println("no regetcookie");
			String tempCookie = RedisOperator.getString(RedisKey.PREFIX.MORTGAGE_COOKIE);
			if(StringUtils.isNotEmpty(tempCookie)){
				System.out.println("no regetcookie success");
				return tempCookie;
			}
			System.out.println("no regetcookie fail");
		}
		System.out.println("regetcookie");
		PostMethod m = new PostMethod(url);
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Connection", "close");
			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNTIMEOUT);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(10000);

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
			httpClient.executeMethod(m);
			Header[] h = m.getResponseHeaders();
			StringBuffer sb = new StringBuffer();
			for (Header header : h) {
				if("Set-Cookie".equals(header.getName())){
					sb.append(header.getValue()).append(";");
				}
			}
			System.out.println("getCookieForPost===>"+sb.toString());
			if(sb.length()>0){
				System.out.println("regetcookie success");
				String tempCookie = sb.substring(0, sb.length()-1);
				RedisOperator.setString(RedisKey.PREFIX.MORTGAGE_COOKIE, tempCookie);
				return tempCookie;
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
		System.out.println("regetcookie fail");
		return null;
	}
	
	
    /**
     * HTTPS 
     * @user Object
     * @date 2016-11-23 上午11:37:07 
     * @param uri
     * @param data
     * @return
     * @throws Exception
     */
    public static String post(String uri, String data) throws Exception {
        String result = "";
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = getSSLHttpClient();
            HttpPost httpPost = new HttpPost(uri);
            if (StringUtils.isNotBlank(data)) httpPost.setEntity(new StringEntity(data, Charsets.UTF_8));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("charset", Charsets.UTF_8.displayName());
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) result = EntityUtils.toString(httpEntity);
        } finally {
            if (response != null) response.close();
            if (httpclient != null) httpclient.close();
        }
        return result;
    }
    
    private static CloseableHttpClient getSSLHttpClient() throws Exception {
        CloseableHttpClient httpclient;
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        SSLContext sc = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
        sc.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sc, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        httpclient = HttpClients.custom().setSSLSocketFactory(factory).build();
        return httpclient;
    }
    
    /**
	 * 
	 * housePost提交
	 * @author Jerry
	 * @version v1.0 2013-9-25 上午10:55:35 
	 * @param url 服务连接 
	 * @param charset 编码方式
	 * @param map 参数集
	 * @param readTimeOut 超时时间
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String housePost(String url, String hcharset, String charset, int readTimeOut, String requestBody) 
			throws Exception {
		PostMethod m = new PostMethod(url);
		StringBuilder rsp = new StringBuilder();
		HttpClient httpClient = null;
		try {
			m.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			if(StringUtils.isNotEmpty(hcharset)){
				m.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, hcharset);
			}

			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNTIMEOUT);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(readTimeOut);
			m.setRequestBody(requestBody);
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
	 * 请求鲁克评估系统
	 * @param strURL url
	 * @param obj 对象
	 * @return
	 */
    public static String jsonPostLKPG(String strURL, Object obj, String token) {  
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
            connection.setRequestProperty("token", token); // 
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            //对象转json字符串
            String params=JSONObject.fromObject(obj).toString();
            log.info("请求url："+strURL+"\n参数："+params);
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
}
