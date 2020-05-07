package com.anjbo.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
/**
 * 根据url地址保存图片，获取服务器cookie
 * @author limh limh@zxsf360.com
 * @date 2015-9-9 下午02:21:44
 */
public class ImageUtil {
	public static String saveToFile(String destUrl,String savePath) {  
		String cookieValue = null; 
		FileOutputStream fos = null;  
		 BufferedInputStream bis = null;  
		 HttpURLConnection httpUrl = null;  
		 URL url = null;  
		 int BUFFER_SIZE = 1024;  
		 byte[] buf = new byte[BUFFER_SIZE];  
		 int size = 0;  
		 try {  
		 url = new URL(destUrl);  
		 httpUrl = (HttpURLConnection) url.openConnection();  
		 httpUrl.connect();  
		 bis = new BufferedInputStream(httpUrl.getInputStream());  
		 File file = new File(savePath);
		 if(!file.getParentFile().exists()){
			 file.getParentFile().mkdirs();
		 }
		 fos = new FileOutputStream(file);  
		 while ((size = bis.read(buf)) != -1) {  
		 fos.write(buf, 0, size);  
		 }  
		 fos.flush();  
		 } catch (Exception e) {  
			 e.printStackTrace();
		 }  finally {  
		 try {  
		 fos.close();  
		 bis.close();  
		 cookieValue=httpUrl.getHeaderField("Set-Cookie");
		 httpUrl.disconnect();  
		 } catch (IOException e) {  
		 } catch (NullPointerException e) {  
		 }  
		 }  
		 return cookieValue;
	}  
	
	public static String saveToFile(String destUrl, String savePath, String cookies) {
		String cookieValue = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			if (StringUtils.isNotBlank(cookies)) {
				httpUrl.setRequestProperty("Cookie", cookies);
			}
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			File file = new File(savePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
//				cookieValue = httpUrl.getHeaderField("Set-Cookie");
				Map<String, List<String>> map = httpUrl.getHeaderFields();
				List<String> list = map.get("Set-Cookie");
				StringBuffer sb = new StringBuffer();
				for (String s : list) {
					sb.append(s).append(";");
				}
				cookieValue = sb.toString();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return cookieValue;
	}
	
	public static void main(String[] args) {
		try {
			String cookieValue = ImageUtil.saveToFile("http://fj.szpgzx.com:8010/jgcx/login/CertPicture.jpg","C:\\authCode.jpg");
			System.out.println(cookieValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
