package com.anjbo.task.estatedeal;


import com.anjbo.bean.estatedeal.XMDealDto;
import com.anjbo.common.DateUtil;
import com.anjbo.common.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/6.
 */
public class XMDataUtil {

    /*厦门一手房*/
    private static final String oneHandUrl="http://cloud.xm.gov.cn/xmzf/zf/newspfl.jsp";
    /*厦门二手房*/
    private static final String secondHandUrl="http://cloud.xm.gov.cn/xmzf/zf/newclf.jsp";
    /**
     * 厦门房产数据
     * @return
     * @throws Exception
     */
    public static List<XMDealDto> getXMData() throws Exception {
        ArrayList<XMDealDto> list = new ArrayList<XMDealDto>();
        XMDealDto onehandData = getOnehandData();
        XMDealDto scndhandData = getScndhandData();
        list.add(onehandData);
        list.add(scndhandData);
        return list;
    }


    private static XMDealDto getOnehandData(){
        HttpUtil httpUtil = new HttpUtil();
        String onehandHtml = httpUtil.get(oneHandUrl, null);
        Document oneDoc = Jsoup.parse(onehandHtml);
        Elements tds = oneDoc.select("td");
        String date = getByRegex("数据截止至(\\d{4}-\\d{1,2}-\\d{2})", tds.get(0).text());
        String totalNum = getByRegex("认购住宅套数：(\\d+)",tds.get(1).text());
        XMDealDto dto = new XMDealDto();
        dto.setDate(DateUtil.parse(date,"yyyy-MM-dd"));
        dto.setTotalNum(Integer.valueOf(totalNum));
        dto.setType(1);
        return dto;
    }

    private static XMDealDto getScndhandData(){
        HttpUtil httpUtil = new HttpUtil();
        String onehandHtml = httpUtil.get(secondHandUrl, null);
        Document oneDoc = Jsoup.parse(onehandHtml);
        Elements tds = oneDoc.select("td");
        String totalNum = getByRegex("今日成交套数：(\\d+)",tds.get(0).text());
        XMDealDto dto = new XMDealDto();
        dto.setDate(new Date());
        dto.setTotalNum(Integer.valueOf(totalNum));
        dto.setType(2);
        return dto;
    }

    private static String getByRegex(String reg,String input){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
    	getOnehandData();
    }
}
