package com.anjbo.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anjbo.bean.tools.DocSearch;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;

/**
 * 办文查询处理类
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:16:29
 */
public class DocSearchProcessor {
	private Logger log = Logger.getLogger(getClass());
	public final static String  url = "http://search.szpl.gov.cn:8080/was5/web/search?channelid=228114&type=detail&searchword=DOC_NO=";
	
	public DocSearch getDocSearchResult(String docNo){
		try {
			//log.info("办文查询docNo="+docNo);
			if(StringUtils.isEmpty(docNo)){
				return null;
			}
			String srcDocNo = docNo;
			if(docNo.contains("-")){
				docNo = docNo.split("-")[1];
			}
			String result = HttpUtil.get(url+docNo);
//			log.info("办文查询结果："+result);
			if(StringUtils.isEmpty(result)){
				return null;
			}
			result = result.replace(" ","");
			result = result.replace("\n","");
			result = result.replace("\t","");
			result = result.toLowerCase();
			Map<Integer,String> map = StringUtil.processLocationMap(result, "bgcolor=#ffffffclass=css_td_b>(.*?)</td>");
			if(map.size()==6){
				DocSearch doc = new DocSearch();
				doc.setDocNo(srcDocNo);
				doc.setSerialNo(map.get(1));
				doc.setDocItem(map.get(2));
				doc.setAcceptTime(map.get(3));
				doc.setReplyDate(map.get(4));
				doc.setStatus(map.get(5));
				return doc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Map<String,String> getDocSearchResultByBookingCode(String bookingCode){
		Map<String,String> map = new HashMap<String,String>();
		String curl = "http://www.szpl.gov.cn:8080/was5/web/search?fapplyno=&fsblsh="+bookingCode+"&fapplytm=&fendoptm=&Submit2=%E6%9F%A5%E8%AF%A2&channelid=228114&searchword=sblsh%3D%27"+bookingCode+"%27+&Relation=AND";
		try {
			String result = HttpUtil.get(curl, 5000);
			if(StringUtil.isNotEmpty(result)){
				Document doc = Jsoup.parseBodyFragment(result);
				Element body = doc.body();
				Elements forms = body.getElementsByTag("form");
				Element form = forms.get(1);
				Elements trs = form.getElementsByTag("tr");
				if(trs.size()>1){
					Element tr = trs.get(1);
					Elements tds = tr.getElementsByTag("td");
					map.put("acceptTime", tds.get(3).text());//受理时间
					map.put("result", tds.get(4).getElementsByTag("div").get(0).text());//办理结果
					map.put("completedTime", tds.get(5).getElementsByTag("div").get(0).text());//办结时间
					map.put("acceptNumber", tds.get(6).getElementsByTag("div").get(0).text());//受理编号
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String[] args) {
		//new DocSearchProcessor().getDocSearchResult("9C-415128258");
		String bookingCode="30041100769555062211610140043";
		getDocSearchResultByBookingCode(bookingCode);
	}
}
