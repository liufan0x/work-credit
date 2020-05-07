package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;

public class FileUtil {
	public static String ReadFile(String fileName){
		BufferedReader reader = null;
		String laststr = "";
		try{
			InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
			laststr += tempString;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
	public static void main(String[] args) {
		String JsonContext = FileUtil.ReadFile("degree.json");
		JSONArray jsonArray = JSONArray.fromObject(JsonContext);
		System.out.println(jsonArray.toString());
	}
}
