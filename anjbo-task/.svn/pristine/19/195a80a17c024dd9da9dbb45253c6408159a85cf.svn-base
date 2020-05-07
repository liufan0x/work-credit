package com.anjbo.task.estatedeal;

import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.HZDealDto;
import com.anjbo.bean.estatedeal.assist.JsonRootBean;
import com.anjbo.common.DateUtil;
import com.anjbo.common.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;

import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

public class HZDataUtil {

    /*惠州一手房*/
    private static final String oneHandUrl="http://data.fz0752.com/chart/housedeal.shtml";
    /*惠州二手房*/
    private static final String secondHandUrl="http://data.fz0752.com/chart/index.shtml";
    
    /*惠州二手房均价走势图详情*/
    private static final 	String secondHandTrendUrl="http://hz.centanet.com/page/v1/ajax/getdealchartdata.ashx";

    private static final String PATTERN="yyyy-MM-dd";

    public static final Date DEFAULT_DATE= DateUtil.parse("2007-01-01", PATTERN);

    /*惠州一手房数据*/
    public static List<HZDealDto> getHZOneHandData(String startDate, String endDate) throws ParseException {
        ArrayList<HZDealDto> list = new ArrayList<HZDealDto>();
        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.get(oneHandUrl, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("0", "惠城区");
        map.put("1", "惠东县");
        map.put("2", "博罗县");
        map.put("4", "龙门县");
//        map.put("5", "仲恺区");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            int days = DateUtil.betDays(DateUtil.parse(startDate, PATTERN), DateUtil.parse(endDate, PATTERN));
            int n = days % 20 == 0 ? days / 20 : days / 20 + 1;
            for (int i = 1; i <= n; i++) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("code", entry.getKey());
                hashMap.put("date2", startDate);
                hashMap.put("date1", endDate);
                hashMap.put("pageNO", String.valueOf(i));
                String post = httpUtil.post(oneHandUrl, hashMap);
                Document doc = Jsoup.parse(post);
                Elements tables = doc.select("table");
                Element table = tables.get(0);
                Elements trs = table.select("tr");
                for (int j = 2; j < trs.size() - 1; j++) {
                    HZDealDto dealDto = new HZDealDto();
                    dealDto.setType(1);
                    Elements tds = trs.get(j).select("td");
                    dealDto.setDate(DateUtil.parse(tds.get(0).text(), PATTERN));
                    dealDto.setTotalNum(Integer.valueOf(tds.get(1).text()));
                    dealDto.setHouseNum(Integer.valueOf(tds.get(3).text()));
                    dealDto.setAreaName(entry.getValue());
                    list.add(dealDto);
                }
                System.out.println(entry.getValue()+":"+i);
            }
        }
        return list;
    }

    /*惠州二手房数据*/
    public static List<HZDealDto> getHZSecondHandData(String startDate, String endDate) throws ParseException {
        ArrayList<HZDealDto> list = new ArrayList<HZDealDto>();
        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.get(secondHandUrl, null);
        int days = DateUtil.betDays(DateUtil.parse(startDate, PATTERN), DateUtil.parse(endDate, PATTERN));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("0", "惠城区");
        map.put("1", "惠东县");
        map.put("2", "博罗县");
        map.put("4", "龙门县");
//        map.put("5", "仲恺区");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Date date1 = DateUtil.parse(startDate,PATTERN);
            Date date2 = DateUtil.parse(endDate, PATTERN);
            while (date1.before(date2)){
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("code", entry.getKey());
                hashMap.put("YMD", DateUtil.getDateByFmt(date2,PATTERN));
                String post = httpUtil.post(secondHandUrl, hashMap);
                Document doc = Jsoup.parse(post);
                Elements tables = doc.select("table");
                Elements trs = tables.get(1).select("tbody tr");
                for (int i = 1; i < trs.size()-1; i++) {
                    HZDealDto dealDto = new HZDealDto();
                    dealDto.setType(2);
                    dealDto.setAreaName(entry.getValue());
                    Elements tds = trs.get(i).select("td");
                    dealDto.setDate(DateUtil.parse(tds.get(0).text(), PATTERN));
                    dealDto.setTotalNum(Integer.valueOf(tds.get(1).text()));
                    Date date = dealDto.getDate();
                    if(date1.before(date)){
                        list.add(dealDto);
                    }
                }
                date2=get7daysBeforeDate(date2);
            }
        }
        return list;
    }

    
    private static Date get7daysBeforeDate(Date date){
        return DateUtils.addDays(date, -7);
    }
    
    /*惠州二手房均价走势数据*/
    public static List<DealdataTrend> getHZSecondHandTrend() throws Exception {
    	
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
				System.out.println(dt.getDate());
			}
		}	
		
		/*for(DealdataTrend o:list_Trend) {
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
			 //getHZSecondHandTrend();
			 //getHZOneHandData();
		}

}
