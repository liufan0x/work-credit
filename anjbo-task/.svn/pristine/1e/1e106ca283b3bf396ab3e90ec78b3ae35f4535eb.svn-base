package com.anjbo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class HttpUtil {

    private Boolean https;
    private File keyStore;
    private Charset charset = Consts.UTF_8;
    private CloseableHttpClient httpClient;

    public HttpUtil() {
        this(false);
    }

    public HttpUtil(Boolean https) {
        this(https, null, null);
    }

    public HttpUtil(Boolean https, File keyStore, String password) {
        try {
            if (https) {
                if (keyStore == null || password == null) {//信任所有
                    TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                public X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }

                                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                                    // don't check
                                }

                                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                                    // don't check
                                }
                            }
                    };
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, trustAllCerts, null);
                    LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
                    httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
                } else {
                    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                    FileInputStream in = new FileInputStream(keyStore);
                    ks.load(in, password.toCharArray());
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init(ks);
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, tmf.getTrustManagers(), null);
                    LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
                    httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
                }
            } else {
                httpClient = HttpClients.createDefault();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String get(String url, Map<String, String> params) {
        HttpGet httpGet = new HttpGet();
        CloseableHttpResponse response=null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null && params.size() > 0) {
                if(params.containsKey("Cookie")){
                	httpGet.addHeader("Cookie",params.get("Cookie"));
                	params.remove("Cookie");
                }
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                builder.setParameters(qparams);
            }
            //增加超时时间设置
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)  
                    .setSocketTimeout(60000).build();  
            httpGet.setConfig(requestConfig);
            httpGet.setURI(builder.build());
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(httpGet!=null){
                httpGet.releaseConnection();
            }
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String post(String url, Map<String, String> params) {
        HttpPost httpPost=null;
        CloseableHttpResponse response=null;
        try {
            httpPost = new HttpPost(url);
            if (params != null && params.size() > 0) {
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(qparams, charset));
            }
            //增加超时时间设置
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)  
                    .setSocketTimeout(60000).build();  
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String postJson(String url, Object jsonParam) {
        HttpPost httpPost=null;
        CloseableHttpResponse response=null;
        try {
            httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(new Gson().toJson(jsonParam), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //增加超时时间设置
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)  
                    .setSocketTimeout(60000).build();  
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public String getCookie(String url) {
    	PostMethod m = new PostMethod(url);
    	HttpClient httpClient = new HttpClient();
        try {
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(10000);
			httpClient.executeMethod(m);
			Header[] h = m.getResponseHeaders();
			StringBuffer sb = new StringBuffer();
			for (Header header : h) {
				if("Set-Cookie".equals(header.getName())){
					sb.append(header.getValue()).append(";");
				}
			}
			if(sb.length()>0){
				return sb.substring(0, sb.length()-1);
			}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
        return null;
    }
    
}