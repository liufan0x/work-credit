package com.anjbo.utils;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class PayUtil {

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
     * 获取Wx签名
     * @user Object
     * @date 2016-11-24 上午10:29:30 
     * @param params
     * @return
     */
    public static String getWxSign(Map<String, Object> params){
    	String sign = "";
		Map<String, Object> sortMap = new TreeMap<String, Object>();
		sortMap.putAll(params);
		for (Map.Entry<String, Object> s : sortMap.entrySet()) {
		    String k = s.getKey();
		    Object v = s.getValue();
		    if (v == null || k.equals("sign")) {// 过滤空值 和签名
		        continue;
		    }
			sign += k+"="+params.get(k)+"&";
		}
		sign = sign + "key="+ConfigUtil.getStringValue("WX_KEY");
		sign = MD5Utils.MD5EncodeUTF(sign).toUpperCase();
		return sign;
    }
}
