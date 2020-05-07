package com.anjbo.bean.estatedeal.assist;

import java.math.BigDecimal;

import com.google.gson.Gson;

public class Test {
public static void main(String[] args) {
/*	String json="{\"resultNo\":0,\"isSuccess\":true,\"message\":null,\"data\":[{\"name\":\"深圳\",\"level\":1,\"byPrice\":{\"data\":[[1469980800000.0,48039.85,-0.95],[1472659200000.0,49542.4,3.13],[1475251200000.0,46656.78,-5.82],[1477929600000.0,46962.68,0.66],[1480521600000.0,47097.33,0.29],[1483200000000.0,48415.29,2.8],[1485878400000.0,47487.87,-1.92],[1488297600000.0,55763.27,17.43],[1490976000000.0,51795.35,-7.12],[1493568000000.0,50546.02,-2.41],[1496246400000.0,54790.52,8.4],[1498838400000.0,51935.24,-5.21],[1501516800000.0,51257.2,-1.31],[1504195200000.0,51972.56,1.4]]},\"byNum\":{\"data\":[[1469980800000.0,1136.0],[1472659200000.0,1297.0],[1475251200000.0,353.0],[1477929600000.0,398.0],[1480521600000.0,617.0],[1483200000000.0,286.0],[1485878400000.0,532.0],[1488297600000.0,1540.0],[1490976000000.0,949.0],[1493568000000.0,971.0],[1496246400000.0,733.0],[1498838400000.0,909.0],[1501516800000.0,741.0],[1504195200000.0,89.0]]}}]}";
	Gson gson=new Gson();
	
	JsonRootBean jr=gson.fromJson(json, JsonRootBean.class);
	
	System.out.println(jr.getData().get(0).getByNum().getData().get(0).get(0));*/
	/*String longTime="1469980800000";
	BigDecimal bd = new BigDecimal(longTime); 
	
	bd = bd.setScale(0,BigDecimal.ROUND_DOWN);

	long lSysTime1=Long.parseLong(bd.toPlainString());
	
	System.out.println(lSysTime1);*/
	
	
}
}
