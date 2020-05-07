package com.anjbo.utils;

import com.anjbo.bean.BaseDto;
import com.anjbo.common.*;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

public class HttpUtil {
	private static int CONNTIMEOUT = 10000;
	/**
	 * 新增本地接口互调模式
	 * @Author KangLG<2017年11月1日>
	 */
	private boolean isLocalApi = false;
	
	public HttpUtil(){}	
	public HttpUtil(boolean isLocalApi){
		this.isLocalApi = isLocalApi;
	}
		
	public RespStatus getRespStatus(String modular,String requestMapping,Object object){
		JSONObject json = getData(modular, requestMapping, object);
		RespStatus resp = (RespStatus)JSONObject.toBean(json,RespStatus.class);
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getObject(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespDataObject<T> resp = new RespDataObject<T>();
		resp = (RespDataObject<T>)JSONObject.toBean(json,resp.getClass());
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			if(!isWrapClass(clazz)){
				JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher()); 
				return (T)JSONObject.toBean(JSONObject.fromObject(resp.getData()),clazz);
			}else{
				return resp.getData();
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> RespDataObject<T> getRespDataObject(String modular,String requestMapping,Object object,Class clazz){
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
	public <T> List<T> getList(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespData<T> resp = (RespData<T>)JSONObject.toBean(json,RespData.class);
		TimestampToDateMorpher timestampToDateMorpher = new TimestampToDateMorpher();
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			List<T> list =  new ArrayList<T>(); 
			for (T t : resp.getData()) {
				if(!isWrapClass(clazz)){
					JSONUtils.getMorpherRegistry().registerMorpher(timestampToDateMorpher); 
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
	public <T> RespPageData<T> getRespPageData(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespPageData<T> resp = (RespPageData<T>)JSONObject.toBean(json,RespPageData.class);
		TimestampToDateMorpher timestampToDateMorpher = new TimestampToDateMorpher();
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			List<T> list =  new ArrayList<T>(); 
			for (T t : resp.getRows()) {
				if(!isWrapClass(clazz)){
					JSONUtils.getMorpherRegistry().registerMorpher(timestampToDateMorpher); 
					list.add((T)JSONObject.toBean(JSONObject.fromObject(t),clazz));
				}else{
					list.add(t);
				}
			}
			resp.setRows(list);
		}
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> RespData<T> getRespData(String modular,String requestMapping,Object object,Class clazz){
		JSONObject json = getData(modular, requestMapping, object);
		RespData<T> resp = (RespData<T>)JSONObject.toBean(json,RespData.class);
		TimestampToDateMorpher timestampToDateMorpher = new TimestampToDateMorpher();
		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			List<T> list =  new ArrayList<T>(); 
			for (T t : resp.getData()) {
				if(!isWrapClass(clazz)){
					JSONUtils.getMorpherRegistry().registerMorpher(timestampToDateMorpher); 
					list.add((T)JSONObject.toBean(JSONObject.fromObject(t),clazz));
				}else{
					list.add(t);
				}
			}
			resp.setData(list);
		}
		return resp;
	}
	
	public <T> JSONObject getData(String modular,String requestMapping,Object object){
		if(object == null) object = new Object();
		String url = (isLocalApi?modular:ConfigUtil.getStringValue(modular,ConfigUtil.CONFIG_LINK)) +"/"+ requestMapping;
		System.out.println("请求："+url);
		return jsonPost(url,object);
	}
	
    @SuppressWarnings("rawtypes")
	private static boolean isWrapClass(Class clz) { 
        try { 
        	if("java.lang.String".equals(clz.getName())){
        		return true;
        	}
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
	private <T> JSONObject jsonPost(String url, Object object) {
		JSONObject json = null;
		RespStatus resp = new RespStatus();
		RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(150000).setConnectionRequestTimeout(150000)
        .setSocketTimeout(150000).build();
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			StringEntity entity = new StringEntity(JSONSerializer.toJSON(object).toString(),"utf-8");
			entity.setContentEncoding("UTF-8");    
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpPost.addHeader("key", (new BaseDto()).getKey());
			
			//登陆信息
			ServletRequestAttributes servletRequest = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
			HttpServletRequest request = null==servletRequest?null:servletRequest.getRequest(); 
			if(null!=request){
				String  session = CookieUtil.getValueFromCookieName(request, "SESSION");
				httpPost.addHeader("uid",request.getHeader("uid"));
				httpPost.addHeader("deviceId",request.getHeader("deviceId"));
				httpPost.addHeader("Cookie", "SESSION="+session);
			}
			HttpResponse response = client.execute(httpPost);
			//登陆信息
			if(response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity he = response.getEntity();
			    json = JSONObject.fromObject(EntityUtils.toString(he,"UTF-8"));
				return json;
			}
		}catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,"请求接口异常");
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		json = JSONObject.fromObject(resp);
		return json;
	}
	
	public static String get(String url) throws Exception {
		return get(url, "UTF-8", 20000,null,null);
	}
	
	/**
	 * 通过map参数集，转换GET拼接参数
	 * 如 a=111,b=222,返回   a=111&b=222
	 * @author Jerry
	 * @version v1.0 2013-10-15 上午08:53:19 
	 * @param params map参数集
	 * @return 拼接后的连接
	 */
	public static String getParam4Map2String(Map<String, String> params){
		StringBuilder builder = new StringBuilder();
		if (params == null) {
			return null;
		}
		int i = 1;
		int size = params.entrySet().size();
		for (Entry<String, String> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append("=");
			builder.append(StringUtil.trimToEmpty(entry.getValue()));
			if (i!=size){
				builder.append("&");
			} 
			i++;
		}
		return builder.toString();
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
	
	//写文件
	 public static String filePost(String strURL, Map<String, Object> map,InputStream fis) {
		 JSONObject json = filePostJSONObject(strURL,map,fis);
		 if(null==json){
			 return ""; // 自定义错误信息
		 } else {
			 return json.getString("url");
		 }
	 }
	public static Map<String,Object> filePostMap(String strURL, Map<String, Object> map,InputStream fis) throws Exception{
		JSONObject json = filePostJSONObject(strURL,map,fis);
		if(null==json){
			return Collections.EMPTY_MAP;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> data = mapper.readValue(json.toString(), Map.class);
			return data;
		}
	}
	public static JSONObject filePostJSONObject(String strURL, Map<String, Object> map,InputStream fis){
		JSONObject json = null;
		try {
			String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; //Could be any string
			String Enter = "\r\n";
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
			conn.connect();
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			//part 1
			String part1 =  "--" + boundary + Enter
					+ "Content-Disposition: form-data; filename=19.jpg; name=\"file\"" + Enter
					+ "Content-Type: image/jpeg" + Enter + Enter  ;
			//part 2
			String part2 = Enter
					+ "--" + boundary + Enter
					+ "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"path\"" + Enter + Enter
					+ map.get("path") + Enter
					+ "--" + boundary + "--";

			byte[] xmlBytes = new byte[fis.available()];
			fis.read(xmlBytes);
			dos.writeBytes(part1);
			dos.write(xmlBytes);
			dos.writeBytes(part2);
			dos.flush();
			dos.close();
			fis.close();
			conn.disconnect();
			DataInputStream in=new DataInputStream(conn.getInputStream());
			BufferedReader br=new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line=null;
			StringBuilder sb=new StringBuilder();
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			br.close();
			String result = sb.toString();
			json = JSONObject.fromObject(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	 /**
	     * post请求，格式为json，接收格式为json，@RequestBody
	     * @param strURL
	     * @param map 参数
	     * @return
	     */
	    public static String jsonPost(String strURL, Map map) {  
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
	            String params=JsonUtil.MapToJson(map);
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
	    
	    public static String jsonPost(String strURL, Map map,int timeout) {  
	        try {  
	            URL url = new URL(strURL);// 创建连接  
	            HttpURLConnection connection = (HttpURLConnection) url  
	                    .openConnection();  
	            connection.setDoOutput(true);  
	            connection.setDoInput(true);  
	            connection.setUseCaches(false);  
	            connection.setInstanceFollowRedirects(true); 
	            connection.setConnectTimeout(timeout);
	            connection.setRequestMethod("POST"); // 设置请求方式  
	            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
	            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
	            connection.connect();  
	            OutputStreamWriter out = new OutputStreamWriter(  
	                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
	            //对象转json字符串
	            String params=JsonUtil.MapToJson(map);
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
			return post(url,"UTF-8", "UTF-8", map, 10000);
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
		
		  public static Map transStringToMap(String mapString){  
		    	mapString=mapString.replace("{", "").replace("}", "").trim();
		    	  Map map = new HashMap();  
		    	  String array[]=mapString.trim().split(",");
		    	  
		    	  for(String o:array) {
		    		  String array_key_value[]=o.split("=");
		    		  map.put(array_key_value[0].replace("{", "").replace("}", "").trim(),array_key_value[1].replace("{", "").replace("}", ""));
		    	  }
		    	
		    	  return map;  
		    	} 
		  
		  /**
			 * 创建HttpClient连接(包含Cookie、SSL信息)
			 * @Author KangLG<2017年10月20日>
			 * @param isSSL
			 * @return
			 */
		public static CloseableHttpClient createSSLClientDefault(boolean isSSL){
			if(isSSL){
				try {
					SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {					
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {//信任所有
							return true;
						}
					}).build();
					SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					return HttpClients.custom().setSSLSocketFactory(sslSF).build();
				} catch (KeyManagementException e) { 
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (KeyStoreException e) {
					e.printStackTrace();
				}
				return null;
			}
			return HttpClients.createDefault();
		}

	/**
	 * 上传到服务器
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static  RespDataObject<Map<String,Object>> upload(byte[] bytes,String fileName,String contentType) throws IOException {
		if(StringUtil.isBlank(contentType))
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),null,"ContentType参数不能空");
		InputStream inputStream = getInputStream(bytes);
		if(StringUtil.isBlank(fileName)) {
			return upload(inputStream);
		}
		JSONObject jsonObject = filePostJSONObject(inputStream,fileName,contentType);
		RespDataObject<Map<String,Object>> respDataObject = null;
		if(null!=jsonObject){
			respDataObject  = new Gson().fromJson(jsonObject.toString(), RespDataObject.class);
		} else {
			respDataObject = new RespDataObject<Map<String,Object>>();
			RespHelper.setFailDataObject(respDataObject,null,"上传失败");
		}
		return respDataObject;
	}

	public static InputStream getInputStream(byte[] bytes)throws IOException{
		ByteArrayInputStream by = null;
		try {
			by = new ByteArrayInputStream(bytes);
			return by;
		} finally {
			if(null!=by){
				by.close();
				by = null;
			}

		}
	}
	/**
	 * 上传到服务器
	 * @param in
	 * @return
	 */
	public static RespDataObject<Map<String,Object>> upload(InputStream in){
		Map<String,Object> map = new HashMap<String,Object>() ;
		String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK);
		JSONObject jsonObject = filePostJSONObject(FS_URL+"/fs/file/upload", map, in);
		RespDataObject<Map<String,Object>> respDataObject = null;
		if(null!=jsonObject){
			respDataObject  = new Gson().fromJson(jsonObject.toString(), RespDataObject.class);
		} else {
			respDataObject = new RespDataObject<Map<String,Object>>();
			RespHelper.setFailDataObject(respDataObject,null,"上传失败");
		}
		return respDataObject;
	}
	public static JSONObject filePostJSONObject(InputStream fis, String fileName,String ContentType) {
		String tmp = fileName;
		tmp = tmp.replace("/", File.separator);
		int index = tmp.lastIndexOf(File.separator);
		if (index != -1) {
			tmp = tmp.substring(index + 1, tmp.length());
		}
		JSONObject json = null;
		String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL, ConfigUtil.CONFIG_LINK);
		Map<String, Object> map = new HashMap<String, Object>();
		DataOutputStream dos = null;
		DataInputStream in = null;
		BufferedReader br = null;
		HttpURLConnection conn = null;
		try {
			String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113";
			String Enter = "\r\n";
			URL url = new URL(FS_URL + "/fs/file/upload");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.connect();
			dos = new DataOutputStream(conn.getOutputStream());
			String part1 = "--" + boundary + Enter + "Content-Disposition: form-data; filename=" + tmp + "; name=\"file\"" + Enter + "Content-Type: "+ContentType + Enter + Enter;
			String part2 = Enter + "--" + boundary + Enter + "Content-Type: text/plain" + Enter + "Content-Disposition: form-data; name=\"path\"" + Enter + Enter + MapUtils.getString(map, "path") + Enter + "--" + boundary + "--";
			byte[] xmlBytes = new byte[fis.available()];
			fis.read(xmlBytes);
			dos.writeBytes(part1);
			dos.write(xmlBytes);
			dos.writeBytes(part2);
			dos.flush();

			in = new DataInputStream(conn.getInputStream());
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String result = sb.toString();
			json = JSONObject.fromObject(result);
		} catch (IOException var17) {
			var17.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
					br = null;
				}
				if (null != in) {
					in.close();
					in = null;
				}
				if (null != conn) {
					conn.disconnect();
				}
				if (null != dos) {
					dos.close();
					dos = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
