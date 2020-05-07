package com.anjbo.utils.huarong;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http客户端工具类
 * @author czm
 * @Description 用于发送http请求
 * @since 2017年8月22日
 */
public final class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int DEFAULT_SO_TIMEOUT = 30000;
	private static final String HTTPS_PROTOCOL = "https";
	private static CloseableHttpClient httpClient;
	private static CloseableHttpClient httpsClient;

	static {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
		try {
			SSLContextBuilder builder = SSLContexts.custom();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			SSLContext sslContext = builder.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				public void verify(String host, SSLSocket ssl) throws IOException {}

				public void verify(String host, X509Certificate cert) throws SSLException {}

				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {}

				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});

			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).build();
		} catch (Exception e) {
			logger.error("", e);
		}

		PoolingHttpClientConnectionManager httpsConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		httpsConnectionManager.setMaxTotal(200);
		httpsConnectionManager.setDefaultMaxPerRoute(30);
		SocketConfig httpsConfig = SocketConfig.custom().setSoTimeout(DEFAULT_SO_TIMEOUT).build();
		httpsConnectionManager.setDefaultSocketConfig(httpsConfig);

		httpsClient = HttpClients.custom().setConnectionManager(httpsConnectionManager).build();

		PoolingHttpClientConnectionManager httpConnectionManager = new PoolingHttpClientConnectionManager();
		httpConnectionManager.setMaxTotal(200);
		httpConnectionManager.setDefaultMaxPerRoute(30);
		SocketConfig httpConfig = SocketConfig.custom().setSoTimeout(DEFAULT_SO_TIMEOUT).build();
		httpConnectionManager.setDefaultSocketConfig(httpConfig);

		httpClient = HttpClients.custom().setConnectionManager(httpConnectionManager).build();
	}

	private HttpClientUtils() {}

	private static ResponseHandler<String> stringResponseHandler = new ResponseHandler<String>() {
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			}
		}
	};

	public static enum Method {
		POST, GET, OPTION, CREATE
	}

	public static String get(final String uri) throws IOException {
		return send(Method.GET, uri, null);
	}

	public static String send(final Method method, final String uri, final Map<String, String> params) throws IOException {
		return send(method, uri, params, false, DEFAULT_CHARSET);
	}

	public static String send(final Method method, final String uri, final String params, final boolean useURLEncoder, final String encodeCharset)
			throws IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		StringEntity entity = null;
		if (null != params) {
			if (params.contains("&")) {
				for (String str : params.split("&")) {
					nameValuePairs.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1)));
				}
			} else {
				if (StringUtils.isBlank(encodeCharset)) entity = new StringEntity(params);
				else if (useURLEncoder) entity = new StringEntity(params, encodeCharset);
			}
		}
		return send(method, uri, null, entity, nameValuePairs, useURLEncoder, encodeCharset, stringResponseHandler, new BasicHttpContext());
	}

	public static String
			send(final Method method, final String uri, final String params, final boolean useURLEncoder, final String encodeCharset, final int timeout)
					throws IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		StringEntity entity = null;
		if (null != params) {
			if (params.contains("&")) {
				for (String str : params.split("&")) {
					nameValuePairs.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1)));
				}
			} else {
				if (StringUtils.isBlank(encodeCharset)) entity = new StringEntity(params);
				else if (useURLEncoder) entity = new StringEntity(params, encodeCharset);
			}
		}
		return send(method, uri, null, entity, nameValuePairs, useURLEncoder, encodeCharset, stringResponseHandler, new BasicHttpContext(), RequestConfig.custom()
				.setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build());
	}

	public static String send(final Method method, final String uri, final Map<String, String> params, final boolean useURLEncoder, final String encodeCharset)
			throws IOException {
		return send(method, uri, params, useURLEncoder, encodeCharset, null);
	}

	public static String send(final Method method, final String uri, final Map<String, String> params, final boolean useURLEncoder, final String encodeCharset,
			final Integer timeout) throws IOException {
		RequestConfig config = null;
		if (timeout == null) config = RequestConfig.DEFAULT;
		else RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (String name : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(name, params.get(name)));
			}
		}
		return send(method, uri, null, null, nameValuePairs, useURLEncoder, encodeCharset, stringResponseHandler, null, config);
	}

	public static <T> T send(final Method method, final String uri, final Map<String, String> headers, final Map<String, String> params,
			final boolean useURLEncoder, final String encodeCharset, final ResponseHandler<T> handler, final HttpContext context) throws IOException {
		UrlEncodedFormEntity entity = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (String name : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(name, params.get(name)));
			}
			if (useURLEncoder) {
				entity = new UrlEncodedFormEntity(nameValuePairs, encodeCharset);
			}
		}
		if (entity == null) return send(method, uri, headers, null, nameValuePairs, useURLEncoder, encodeCharset, handler, context);
		else return send(method, uri, headers, entity, null, useURLEncoder, encodeCharset, handler, context);

	}

	/**
	 * 发起POST请求
	 * @param uri 请求地址
	 * @param headers 请求头
	 * @param handler 响应回调处理
	 * @param context 请求上下文
	 * @param
	 * @return 响应
	 * @throws IOException IO异常
	 */
	public static <T> T send(final Method method, final String uri, final Map<String, String> headers, final HttpEntity entity,
			final List<NameValuePair> nameValuePairs, final boolean useURLEncode, final String encodeCharset, final ResponseHandler<T> handler,
			final HttpContext context) throws IOException {
		return send(method, uri, headers, entity, nameValuePairs, useURLEncode, encodeCharset, handler, context, null);
	}

	/**
	 * 发起http请求
	 * @param method 请求方式
	 * @param uri 请求地址
	 * @param headers 请求头
	 * @param entity 请求体
	 * @param nameValuePairs 键值对参数
	 * @param useURLEncode 使用URL编码
	 * @param encodeCharset 编码字符集
	 * @param handler 响应处理
	 * @param context 请求上下文
	 * @param config 请求配置
	 * @return 请求处理后的结果
	 * @throws IOException
	 */
	public static <T> T send(final Method method, final String uri, final Map<String, String> headers, final HttpEntity entity,
			final List<NameValuePair> nameValuePairs, final boolean useURLEncode, final String encodeCharset, final ResponseHandler<T> handler,
			final HttpContext context, final RequestConfig config) throws IOException {
		RequestBuilder builder = null;
		if (Method.GET.equals(method)) builder = RequestBuilder.get();
		else if (Method.POST.equals(method)) builder = RequestBuilder.post();
		if (headers != null && headers.size() > 0) {
			for (String name : headers.keySet()) {
				builder.addHeader(name, headers.get(name));
			}
		}
		builder.setUri(uri);
		builder.addParameters(nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]));
		builder.setEntity(entity);
		builder.setConfig(config == null ? RequestConfig.DEFAULT : config);
		HttpUriRequest request = builder.build();
		if (HTTPS_PROTOCOL.equals(new URL(uri).getProtocol())) return httpsClient.execute(request, handler, context == null ? new BasicHttpContext() : context);
		return httpClient.execute(request, handler, context == null ? new BasicHttpContext() : context);

	}
}
