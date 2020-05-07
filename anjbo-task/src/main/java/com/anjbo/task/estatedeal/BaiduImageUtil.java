package com.anjbo.task.estatedeal;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 百度识别工具类
 */
public class BaiduImageUtil {

    private static final String apikey="500410b2e5f8aa554cf41dc552c1d19c";

    public static String imageRequest(File file) throws Exception {
        if(file==null){
            return null;
        }
        return imageRequest(FileUtils.readFileToByteArray(file));
    }

    public static String imageRequest(InputStream inputStream) throws Exception {
        if(inputStream==null){
            return null;
        }
        return imageRequest(IOUtils.toByteArray(inputStream));
    }


    public static String imageRequest(byte[] bytes) throws Exception {
        String data= URLEncoder.encode(Base64.encodeBase64String(bytes), "utf-8");
        String httpUrl = "http://apis.baidu.com/idl_baidu/baiduocrpay/idlocrpaid";
        String httpArg = "sizetype=big&fromdevice=pc&clientip=10.10.10.0&detecttype=LocateRecognize&languagetype=CHN_ENG&imagetype=1&image="+data;
        String jsonResult = request(httpUrl, httpArg);

        JSONObject jsonObject = new JSONObject(jsonResult);
        int errNum = jsonObject.getInt("errNum");
        if(errNum!=0){
            return null;
        }
        JSONArray retData = jsonObject.getJSONArray("retData");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < retData.length(); i++) {
            JSONObject object = retData.getJSONObject(i);
            String word = object.getString("word");
            if(StringUtils.isNotBlank(word)){
                sb.append(word);
            }
        }
        return sb.toString();
    }

    private static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  apikey);
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String s = imageRequest(new File("d:/d.jpg"));
        System.out.println(s);
    }

}
