package com.anjbo.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/6/11.
 */
public class SingleUtils {

	private static RestTemplate restTemplate;

	public static Object obj = new Object();

	public static RestTemplate getRestTemplate() {
		if (null == restTemplate) {
			synchronized (obj) {
				if (null == restTemplate) {
					restTemplate = new RestTemplate();
				}
			}
		}
		return restTemplate;
	}

	public static RestTemplate getRestTemplate(int m) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		// 60s
		requestFactory.setConnectTimeout(m * 1000);
		requestFactory.setReadTimeout(m * 1000);
		synchronized (obj) {
			restTemplate = new RestTemplate(requestFactory);
		}
		return restTemplate;
	}

	public static HttpHeaders getHttpHeaders(MediaType charset) {
		HttpHeaders headers = new HttpHeaders();
		if (null == charset) {
			headers.setContentType(charset);
		} else {
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}
		return headers;
	}

	public static <T> HttpEntity getHttpEntity(T param, HttpHeaders headers) {
		if (null == headers) {
			headers = getHttpHeaders(null);
		}
		HttpEntity<T> requestEntity = new HttpEntity<T>(param, headers);
		return requestEntity;
	}

}
