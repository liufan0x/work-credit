package com.anjbo.task.estatedeal;

import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.GZDealDto;
import com.anjbo.bean.estatedeal.assist.JsonRootBean;
import com.anjbo.common.DateUtil;
import com.anjbo.common.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public class GZDataUtil {

    private static final String PATTERN = "yyyy.MM.dd";
    private static final String PATTERN2 = "yyyy-MM-dd";
    private static final String PATTERN3 = "yyyy-M-d";
    /*广州二手房均价走势图详情*/
    private static final 	String secondHandTrendUrl="http://gz.centanet.com/page/v1/ajax/getdealchartdata.ashx";

    public static final Date DEFAULT_DATE= DateUtil.parse("2007-01-01", PATTERN2);

    public static List<GZDealDto> getGZOneHandData(String startDate, String endDate) throws Exception {
        ArrayList<GZDealDto> list = new ArrayList<GZDealDto>();
        HttpUtil httpUtil = new HttpUtil();
        ArrayList<String> districtnames = new ArrayList<String>();
        districtnames.add("天河");
        districtnames.add("海珠");
        districtnames.add("越秀");
        districtnames.add("荔湾");
        districtnames.add("白云");
        districtnames.add("黄埔");
        districtnames.add("番禺");
        districtnames.add("花都");
        districtnames.add("南沙");
        districtnames.add("增城");
        districtnames.add("从化");
        for(String name:districtnames){
            String districtname = URLEncoder.encode(name, "utf-8");
            int days = DateUtil.betDays(DateUtil.parse(startDate, PATTERN2), DateUtil.parse(endDate, PATTERN2));
            int n = days % 30 == 0 ? days / 30 : days / 30 + 1;
            for (int i = 1; i <= n; i++) {
                String url = "http://data.house.163.com/gzxf/district/trend.html?datenum=0&startDate=" + startDate + "&endDate=" + endDate + "&district=" + districtname + "&trend=1&page="+i;
                String s = httpUtil.get(url, null);
                Document doc = Jsoup.parse(s);
                Elements tables = doc.select(".table_area");
                Element table = tables.get(0);
                Elements trs = table.select("tbody tr");
                for (int j = 0; j <trs.size() ; j++) {
                    Elements tds = trs.get(j).select("td");
                    GZDealDto dealDto = new GZDealDto();
                    dealDto.setType(1);
                    dealDto.setAreaName(name);
                    dealDto.setDate(DateUtil.parse(tds.get(0).text(),PATTERN2));
                    dealDto.setTotalNum(NumberUtils.toInt(tds.get(1).text(), 0));
//                    dealDto.setAvgPrice(NumberUtils.toDouble(tds.get(4).text().replaceAll(",",""), 0));
                    list.add(dealDto);
                }
            }
        }
        return list;
    }

    public static List<GZDealDto> getGZOneHandDataOld(String startDate, String endDate) throws Exception {
        ArrayList<GZDealDto> list = new ArrayList<GZDealDto>();
        HttpUtil httpUtil = new HttpUtil();
        startDate = DateUtil.getDateByFmt(DateUtil.parse(startDate,PATTERN2),PATTERN);
        endDate = DateUtil.getDateByFmt(DateUtil.parse(endDate,PATTERN2),PATTERN);

        ArrayList<String> districtnames = new ArrayList<String>();
        districtnames.add("天河");
        districtnames.add("海珠");
        districtnames.add("越秀");
        districtnames.add("荔湾");
        districtnames.add("白云");
        districtnames.add("黄埔");
        districtnames.add("番禺");
        districtnames.add("花都");
        districtnames.add("南沙");
        districtnames.add("增城");
        districtnames.add("从化");
        for(String name:districtnames){
            String districtname = URLEncoder.encode(name, "utf-8");
            int days = DateUtil.betDays(DateUtil.parse(startDate, PATTERN), DateUtil.parse(endDate, PATTERN));
            int n = days % 16 == 0 ? days / 16 : days / 16 + 1;
            for (int i = 1; i <= n; i++) {
                String url = "http://data.house.163.com/gz/housing/trend/district/todayprice/" + startDate + "-" + endDate + "/day/allDistrict/"+i+".html?districtname=" + districtname + "#stoppoint";
                String s = httpUtil.get(url, null);
                Document doc = Jsoup.parse(s);
                Elements tables = doc.select(".daTb");
                Element table = tables.get(0);
                Elements trs = table.select("tbody tr");
                for (int j = 3; j <trs.size() ; j++) {
                    Elements tds = trs.get(j).select("td");
                    GZDealDto dealDto = new GZDealDto();
                    dealDto.setType(1);
                    dealDto.setAreaName(name);
                    dealDto.setDate(DateUtil.parse(tds.get(1).text(),PATTERN2));
                    dealDto.setTotalNum(NumberUtils.toInt(tds.get(2).text(), 0));
                    dealDto.setAvgPrice(NumberUtils.toDouble(tds.get(4).text().replaceAll(",",""), 0));
                    list.add(dealDto);
                }
            }
        }
        return list;
    }

    public static List<GZDealDto> getGZSecondHandData(String startDate, String endDate) throws Exception {
        ArrayList<GZDealDto> list = new ArrayList<GZDealDto>();
        HttpUtil httpUtil = new HttpUtil();
        startDate = DateUtil.getDateByFmt(DateUtil.parse(startDate,PATTERN2),PATTERN3);
        endDate = DateUtil.getDateByFmt(DateUtil.parse(endDate,PATTERN2),PATTERN3);

        ArrayList<String> districtnames = new ArrayList<String>();
        districtnames.add("天河");
        districtnames.add("海珠");
        districtnames.add("越秀");
        districtnames.add("荔湾");
        districtnames.add("白云");
        districtnames.add("黄埔");
        districtnames.add("番禺");
        districtnames.add("花都");
        districtnames.add("南沙");
        districtnames.add("增城");
        districtnames.add("从化");
        for(String name:districtnames){
            String districtname = URLEncoder.encode(name, "utf-8");
            int days = DateUtil.betDays(DateUtil.parse(startDate, PATTERN3), DateUtil.parse(endDate, PATTERN3));
            int n = days % 30 == 0 ? days / 30 : days / 30 + 1;
            for (int i = 1; i <= n; i++) {
                String url = "http://data.house.163.com/gzesf/district/trend.html?datenum=0&startDate="+startDate+"&endDate="+endDate+"&district="+districtname+"&page="+i;
                String s = httpUtil.get(url, null);
                Document doc = Jsoup.parse(s);
                Elements trs = doc.select(".table_area tbody tr");
                for (int j = 0; j < trs.size(); j++) {
                    Elements tds = trs.get(j).select("td");
                    GZDealDto dealDto = new GZDealDto();
                    dealDto.setType(2);
                    dealDto.setAreaName(name);
                    dealDto.setDate(DateUtil.parse(tds.get(0).text(),PATTERN2));
                    dealDto.setTotalNum(NumberUtils.toInt(tds.get(1).text(), 0));
                    dealDto.setAvgPrice(NumberUtils.toDouble(tds.get(3).text().replaceAll(",",""), 0));
                    list.add(dealDto);
                }
            }
        }
        return list;
    }
    
    /*广州二手房均价走势数据*/
    public static List<DealdataTrend> getGZSecondHandTrend() throws Exception {
    	List<DealdataTrend> last_Trend=new ArrayList<DealdataTrend>();
    	
    	HttpUtil httpUtil = new HttpUtil();
    	HashMap<String, String> hashMap = new HashMap<String, String>();
    	
    	
    	List<DealdataTrend> list_Trend=new ArrayList<DealdataTrend>();
    	
    	String endDate=DateUtil.getMonthBeforeDate(1,"yyyy/MM/dd HH:mm:ss");
    	
    	System.out.println("endDate"+endDate);
    	
    	String beginDate=DateUtil.getMonthBeforeDate(-13,"yyyy/MM/dd HH:mm:ss");
    	
    	System.out.println("beginDate"+beginDate);
    	
    	String nowDate=DateUtil.getMonthBeforeDate(0,"yyyy/MM");
    	
    	double now_avgPrice=1;
    	
    	System.out.println("nowDate"+nowDate);
    	
    	String yesterYearDate=DateUtil.getMonthBeforeDate(-12,"yyyy/MM");
    	
    	double yesterYear_avgPrice=1;
    	
    	System.out.println("yesterYearDate"+yesterYearDate);

    	hashMap.put("rad", String.valueOf(Math.random()));
    	hashMap.put("compare", "");

    	hashMap.put("begin", beginDate);
    	hashMap.put("end", endDate);
    	
    	hashMap.put("gscopeid", "0");
    	hashMap.put("regionid", "0");
    	
    	String result=httpUtil.get(secondHandTrendUrl,hashMap);
    	
		System.out.println(result);
    	
		Gson gson=new Gson();
		
		JsonRootBean jrbean=gson.fromJson(result, JsonRootBean.class);
		
		String areaName=jrbean.getData().get(0).getName();
		
		List<List<String>> List_byPrice_data=jrbean.getData().get(0).getByPrice().getData();
		int i=0;
		for(List<String> price_data :List_byPrice_data) {
			
			List<String> ByNum_data=jrbean.getData().get(0).getByNum().getData().get(i);
			
			DealdataTrend dt=new DealdataTrend();
			dt.setCityName(areaName.trim());
			dt.setDataType("M");
			dt.setHouseType(2);
			
			if(price_data!=null&&price_data.size()>0) {
				
				String tempDate=DateUtil.longToDate(price_data.get(0).toString(),"yyyy/MM/dd");
				System.out.println("tempDate"+tempDate);
				
				String avgPrice=price_data.get(1).toString();
				
				if(tempDate.contains(nowDate)) {
					now_avgPrice=Double.parseDouble(avgPrice);
				}
				
				if(tempDate.contains(yesterYearDate)) {
					yesterYear_avgPrice=Double.parseDouble(avgPrice);
				}
				
				dt.setDate(DateUtil.parse(tempDate,"yyyy/MM/dd"));
				
				dt.setAvgPrice(avgPrice);
				
				dt.setLastMonthRate(price_data.get(2).toString()+"%");

			}
			
			if(now_avgPrice!=1&&yesterYear_avgPrice!=1) {
				dt.setLastYearRate(multiplication2(String.valueOf((now_avgPrice-yesterYear_avgPrice)/yesterYear_avgPrice))+"%");
			}
			
			if(ByNum_data!=null) {
				
				dt.setTotalNum(multiplicationToInt(ByNum_data.get(1)));
				
				System.out.println(ByNum_data.get(1));
			}
			
			list_Trend.add(dt);
			i++;
			if(i==List_byPrice_data.size()) {
				last_Trend.add(dt);
			}
		}	
		
	/*	for(DealdataTrend o:list_Trend) {
			System.out.println(o.getAvgPrice()+o.getCityName()+o.getLastYearRate()+o.getDate());
		}*/
		return last_Trend;	
    }
    
    //BigDecimal舍弃小数据点后面的数据，取整
	 public static int multiplicationToInt(String str) {
		 if(StringUtil.isEmpty(str)) {
			 return 0;
		 }
		 BigDecimal bd = new BigDecimal(str);  
		 bd = bd.setScale(0,BigDecimal.ROUND_DOWN);
		return Integer.parseInt(bd.toPlainString()); 
	 }    
	     //四舍五入取2位小数点,浮点转百分比
		 public static String  multiplication2(String str) {
			 if(StringUtil.isEmpty(str)) {
				 return str;
			 }
			 BigDecimal bd = new BigDecimal(str);  
			 BigDecimal b2 = new BigDecimal(100);
			 bd =bd.multiply(b2);
			 bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			return bd.toPlainString(); 
		 }    

    

    public static void main(String[] args) throws Exception {
       /* List<GZDealDto> guangZhouOneHandData = getGZOneHandData("2017-03-01", "2017-03-07");
        List<GZDealDto> guangZhouSecondHandData = getGZSecondHandData("2017-03-01", "2017-03-07");
        System.out.println();*/
    	getGZSecondHandTrend();
    }
}
