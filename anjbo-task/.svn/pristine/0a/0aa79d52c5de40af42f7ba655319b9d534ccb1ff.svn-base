package com.anjbo.task.estatedeal;

import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.assist.JsonRootBean;
import com.anjbo.common.DateUtil;
import com.anjbo.common.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DGDataUtil {

    /*东莞一手房*/
    private static final String oneHandUrl="http://dgfc.dg.gov.cn/dgwebsite_v2/Vendition/Index.aspx";
    /*东莞二手房*/
    private static final String secondHandUrl="http://dgfc.dg.gov.cn/dgwebsite_v2/Secondhand/default.aspx";

    /*东莞二手房均价走势图详情*/
    private static final 	String secondHandTrendUrl="http://dg.centanet.com/page/v1/ajax/getdealchartdata.ashx";

    /**
     * 东莞一手房数据
     * @return
     * @throws Exception
     */
    public static List<DGDealDto> getDGOneHandData() throws Exception {
        return dongGuanDealDataOne(oneHandUrl,1);
    }

    /**
     * 东莞一手房详情
     * @return
     * @throws Exception
     */
    public static List<DGDealDto> getDGOneHandDetail() throws Exception {
        return dongGuanDetailOne(oneHandUrl,1);
    }

    /**
     * 东莞二手房数据
     * @return
     * @throws Exception
     */
    public static List<DGDealDto> getDGSecondHandData() throws Exception {
        return dongGuanDealData(secondHandUrl,2);
    }

    /**
     * 东莞二手房详情
     * @return
     * @throws Exception
     */
    public static List<DGDealDto> getDGSecondHandDetail() throws Exception {
        return dongGuanDetail(secondHandUrl,2);
    }

    //一手房
    private static List<DGDealDto> dongGuanDealDataOne(String url,Integer type) throws Exception {
        ArrayList<DGDealDto> list = new ArrayList<DGDealDto>();
        Document doc = Jsoup.parse(new URL(url), 30*1000);
        Elements tables = doc.select("table .resultTable3");
        Element table = tables.get(0);
        Elements trs = table.select("tr");
        Elements tds = trs.get(2).select("td");
        Integer houseNum = Integer.valueOf(tds.get(1).text());//住宅
        Integer shangpu = Integer.valueOf(tds.get(3).text());//商铺
        Integer bangong = Integer.valueOf(tds.get(5).text());//办公
        Integer cheku = Integer.valueOf(tds.get(7).text());//车库
        DGDealDto dealDto = new DGDealDto();
        dealDto.setDate(new Date());
        dealDto.setHouseNum(houseNum);
        dealDto.setTotalNum(houseNum+shangpu+bangong+cheku);
        dealDto.setType(type);
        list.add(dealDto);
        return list;
    }

    private static List<DGDealDto> dongGuanDetailOne(String url,Integer type) throws Exception {
        ArrayList<DGDealDto> list = new ArrayList<DGDealDto>();
        Document doc = Jsoup.parse(new URL(url), 30*1000);
        Elements select = doc.select("table .resultTable3");
        Element element = select.get(1);
        Elements trs = element.select("tr");
        for (int i = 1; i < trs.size(); i++) {
            DGDealDto dealDto = new DGDealDto();
            Elements tds = trs.get(i).select("td");
            dealDto.setName(tds.get(0).text());
            Integer houseNum = Integer.valueOf(tds.get(1).text());//住宅
            Integer shangpu = Integer.valueOf(tds.get(3).text());//商铺
            Integer bangong = Integer.valueOf(tds.get(5).text());//办公
            Integer cheku = Integer.valueOf(tds.get(7).text());//车库
            dealDto.setHouseNum(houseNum);
            dealDto.setTotalNum(houseNum+shangpu+bangong+cheku);
            dealDto.setDate(new Date());
            dealDto.setType(type);
            list.add(dealDto);
        }
        return list;
    }
    //二手房
    private static List<DGDealDto> dongGuanDealData(String url,Integer type) throws Exception {
        ArrayList<DGDealDto> list = new ArrayList<DGDealDto>();
        Document doc = Jsoup.parse(new URL(url), 30*1000);
        Elements tables = doc.select("table .resultTable3");
        Element table = tables.get(0);
        Elements trs = table.select("tr");
        Elements tds = trs.get(2).select("td");
        Integer houseNum = Integer.valueOf(tds.get(1).text());
        Integer notHouseNum = Integer.valueOf(tds.get(2).text());
        DGDealDto dealDto = new DGDealDto();
        dealDto.setDate(new Date());
        dealDto.setHouseNum(houseNum);
        dealDto.setTotalNum(houseNum+notHouseNum);
        dealDto.setType(type);
        list.add(dealDto);
        return list;
    }

    private static List<DGDealDto> dongGuanDetail(String url,Integer type) throws Exception {
        ArrayList<DGDealDto> list = new ArrayList<DGDealDto>();
        Document doc = Jsoup.parse(new URL(url), 30*1000);
        Elements select = doc.select("table .resultTable3");
        Element element = select.get(1);
        Elements trs = element.select("tr");
        for (int i = 1; i < trs.size(); i++) {
            DGDealDto dealDto = new DGDealDto();
            Elements tds = trs.get(i).select("td");
            dealDto.setName(tds.get(0).text());
            dealDto.setTotalNum(Integer.valueOf(tds.get(1).text()));
            dealDto.setMoney(Double.valueOf(tds.get(3).text()));
            dealDto.setDate(new Date());
            dealDto.setType(type);
            list.add(dealDto);
        }
        return list;
    }
    
    /*东莞二手房均价走势数据*/
    public static List<DealdataTrend> getDGSecondHandTrend() throws Exception {
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
    	List<DGDealDto> dongGuanSecondHandData=DGDataUtil.getDGSecondHandData();
        System.out.println(JSONArray.fromObject(dongGuanSecondHandData).toString());
    	List<DGDealDto> dongGuanSecondHandDetail=DGDataUtil.getDGSecondHandDetail();
        System.out.println(JSONArray.fromObject(dongGuanSecondHandDetail).toString());
        List<DGDealDto> dongGuanOneHandData = DGDataUtil.getDGOneHandData();
        System.out.println(JSONArray.fromObject(dongGuanOneHandData).toString());
        List<DGDealDto> dongGuanOneHandDetail = DGDataUtil.getDGOneHandDetail();
        System.out.println(JSONArray.fromObject(dongGuanOneHandDetail).toString());
    }
}
