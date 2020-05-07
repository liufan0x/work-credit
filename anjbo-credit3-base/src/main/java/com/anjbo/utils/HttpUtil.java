package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class HttpUtil {
	private boolean isLocalApi = false;
	
	public HttpUtil(){}	
	public HttpUtil(boolean isLocalApi){
		this.isLocalApi = isLocalApi;
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
	 * 上传到服务器
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static  RespDataObject<Map<String,Object>> upload(byte[] bytes,String fileName,String contentType) throws IOException {
		if(StringUtil.isBlank(contentType))
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),null,"ContentType参数不能空");
		InputStream inputStream = getInputStream(bytes);
		JSONObject jsonObject = filePostJSONObject(inputStream,fileName,contentType,null);
		RespDataObject<Map<String,Object>> respDataObject = null;
		if(null!=jsonObject){
			respDataObject  = new Gson().fromJson(jsonObject.toString(), RespDataObject.class);
		} else {
			respDataObject = new RespDataObject<Map<String,Object>>();
			RespHelper.setFailDataObject(respDataObject,null,"上传失败");
		}
		return respDataObject;
	}
	public static  RespDataObject<Map<String,Object>> upload(byte[] bytes,String fileName,String contentType,String returnFileName) throws IOException {
		if(StringUtil.isBlank(contentType))
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),null,"ContentType参数不能空");
		InputStream inputStream = getInputStream(bytes);
		JSONObject jsonObject = filePostJSONObject(inputStream,fileName,contentType,returnFileName);
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
	public static JSONObject filePostJSONObject(InputStream fis, String fileName,String ContentType,String returnFileName) {
		String tmp = fileName;
		tmp = tmp.replace("/", File.separator);
		int index = tmp.lastIndexOf(File.separator);
		if (index != -1) {
			tmp = tmp.substring(index + 1, tmp.length());
		}
		JSONObject json = null;
		String FS_URL = Constants.LINK_ANJBO_FS_URL;
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
			if(returnFileName!=null) {
				String part3 = Enter + "--" + boundary + Enter+  "Content-Disposition: form-data; name=\"returnFileName\"" + Enter + Enter + URLEncoder.encode(returnFileName, "utf-8") + Enter + Enter;
				dos.writeBytes(part3);
			}
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
