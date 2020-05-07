package com.anjbo.utils.huarong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 华融http类
 * @ClassName:  
 * @author xufx
 * @date 2017-8-22 下午14:52:22
 */
public class httpUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	 public static String getRspContent(String http_requrl){
			StringBuffer resultJsonStr = new StringBuffer();
			try {
				URL url = new URL(http_requrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
				conn.setRequestProperty("Charsert", "UTF-8");
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				conn.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					resultJsonStr.append(line);
				}
				return resultJsonStr.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
