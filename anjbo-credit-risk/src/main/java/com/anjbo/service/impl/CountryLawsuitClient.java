package com.anjbo.service.impl;

import com.anjbo.common.Constants;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.JsonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** 
 * 全国诉讼数据抓取
 * @ClassName: CountryLawsuitClient 
 * @author liuw 
 * @date 2016-10-8 下午02:43:33 
 */ 
public class CountryLawsuitClient {
	private static Logger log = Logger.getLogger(CountryLawsuitClient.class);
	
	static CloseableHttpClient client = HttpClients.createDefault();
	private static final String IMG_DEFAULT_PATH = "/credit/risk/upload";
	private static final String IMG_NAME = "QGAuthcode.jpg";
	
	/**
	 * 判断网站是否限制访问流量
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean isVisitLimit() throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet("http://zhixing.court.gov.cn/search");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.addHeader("Host", "zhixing.court.gov.cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        httpGet.addHeader("Referer", "http://zhixing.court.gov.cn/search/");
        CloseableHttpResponse response = client.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			String htmlStr = EntityUtils.toString(entity, "UTF-8");
			if(htmlStr.indexOf("网站当前访问量较大，请输入验证码后继续访问")==-1){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			response.close();
			httpGet.abort();
		}
        return true;
	}
	
	/**
	 * 获取限制访问流量的页面验证码
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getLimitAuthCode(HttpServletRequest request) throws IOException{
		HttpGet httpGet = new HttpGet("http://zhixing.court.gov.cn/waf_captcha/");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.addHeader("Host", "zhixing.court.gov.cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        httpGet.addHeader("Referer", "http://zhixing.court.gov.cn/search/");
        
        //获取当前项目图片存储路径,不存在则创建
        String savePicDicUrl = request.getSession().getServletContext().getRealPath(IMG_DEFAULT_PATH);
		File savePicDic = new File(savePicDicUrl);
        if (!savePicDic.exists()) {
        	savePicDic.mkdir();  
        }
		String domain=ConfigUtil.getStringValue(Constants.PROJECT_DOMAIN,ConfigUtil.CONFIG_LINK);
		String authCodeUrl = null;
		FileOutputStream fos = null;
		String picAllName = savePicDicUrl+File.separator+IMG_NAME;
		File storeFile = new File(picAllName);
		CloseableHttpResponse response = client.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			fos = new FileOutputStream(storeFile);
			entity.writeTo(fos);
			authCodeUrl=domain
			+ request.getContextPath()+IMG_DEFAULT_PATH+"/"+IMG_NAME;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			fos.close();
			response.close();
			httpGet.abort();
		}
		return authCodeUrl;
	}
	
	public static boolean isPassLimit(Map<String, Object> params){
		return true;
	}
	public static String getAuthCodeAndCookiesNew(HttpServletRequest request) throws IOException{
	 	Document parse = Jsoup.parse(new URL("http://zhixing.court.gov.cn/search/index_form.do"),3000);
        Elements select = parse.select("#captchaImg");
        String src = select.get(0).attr("src");
        src = "http://zhixing.court.gov.cn/search/"+src;
	    return src;
	}
	public static String getAuthCodeAndCookies(HttpServletRequest request) throws IOException{
		HttpGet httpGet = new HttpGet("http://zhixing.court.gov.cn/search/security/jcaptcha.jpg");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.addHeader("Host", "zhixing.court.gov.cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        httpGet.addHeader("Referer", "http://zhixing.court.gov.cn/search/");
        
        //获取当前项目图片存储路径,不存在则创建
        String savePicDicUrl = request.getSession().getServletContext().getRealPath(IMG_DEFAULT_PATH);
		File savePicDic = new File(savePicDicUrl);
        if (!savePicDic.exists()) {
        	savePicDic.mkdir();  
        }
		String domain=ConfigUtil.getStringValue(Constants.PROJECT_DOMAIN,ConfigUtil.CONFIG_LINK);
		String authCodeUrl = null;
		FileOutputStream fos = null;
		String picAllName = savePicDicUrl+File.separator+IMG_NAME;
		File storeFile = new File(picAllName);
		CloseableHttpResponse response = client.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			fos = new FileOutputStream(storeFile);
			entity.writeTo(fos);
			authCodeUrl=domain
			+ request.getContextPath()+IMG_DEFAULT_PATH+"/"+IMG_NAME;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			fos.close();
			response.close();
			httpGet.abort();
		}
		return authCodeUrl;
	}
	
	public static Map<String, Object> getResult(String pname,String cardNum,String code,String captchaId) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost("http://zhixing.court.gov.cn/search/newsearch");
		
		httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Host", "zhixing.court.gov.cn");
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		httpPost.addHeader("Referer", "http://zhixing.court.gov.cn/search/index_form.do");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("searchCourtName", "全国法院（包含地方各级法院）"));
		formparams.add(new BasicNameValuePair("currentPage", "1"));
		formparams.add(new BasicNameValuePair("selectCourtId", "1"));
		formparams.add(new BasicNameValuePair("selectCourtArrange", "1"));
		formparams.add(new BasicNameValuePair("pname", pname));
		formparams.add(new BasicNameValuePair("cardNum", cardNum));
		formparams.add(new BasicNameValuePair("j_captcha", code));
		formparams.add(new BasicNameValuePair("captchaId", captchaId));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		
		httpPost.setEntity(entity);
		CloseableHttpResponse response = client.execute(httpPost);
		
		Map<String, Object> map = null;
		try{
			HttpEntity httpEntity = response.getEntity();
			String htmlStr = EntityUtils.toString(httpEntity);
			if(htmlStr.indexOf("验证码错误")!=-1){
				log.error("全国查询返回html包含验证码错误");
				return map;
			}
			map = parseToMap(htmlStr);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			response.close();
			httpPost.abort();
		}
		return map;
	}
    
    /**
     * 将html里的,tr,td标签内容解析出来
     * @param htmlStr
     * @return
     * @throws ParserException
     */
    public static Map<String, Object> parseToMap(String htmlStr) throws ParserException{
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
        Parser parser = new Parser(htmlStr);  
        NodeFilter trFilter = new TagNameFilter("tr");
        NodeFilter cssFilter = new HasAttributeFilter("style");
        NodeFilter filter = new AndFilter(trFilter, cssFilter);
        NodeList trNodeList =  parser.extractAllNodesThatMatch(filter);
    	for(int i=0;i<trNodeList.size();i++){
    		Parser trParser = new Parser(trNodeList.elementAt(i).toHtml());
    		NodeFilter tdfilter = new TagNameFilter("td");
    		NodeList tdNodeList = trParser.extractAllNodesThatMatch(tdfilter);
    		Map<String, Object> rowMap = new HashMap<String, Object>();
    		rowMap.put("name", tdNodeList.elementAt(1).toPlainTextString());
    		rowMap.put("date", tdNodeList.elementAt(2).toPlainTextString());
    		rowMap.put("caseNo", tdNodeList.elementAt(3).toPlainTextString());
    		TagNode tagNode = new TagNode();
    		String detailHtml = tdNodeList.elementAt(4).toHtml();
    		String regEx = "id=\"([\\s\\S]*?)\">";
    		Pattern pattern = Pattern.compile(regEx);
    		Matcher matcher = pattern.matcher(detailHtml);
    		matcher.find();
    		String detailId = matcher.group(1);
    		rowMap.put("detailId", detailId);
    		listMap.add(rowMap);
    	}
    	map.put("listMap", listMap);
    	return map;
    }
    
    /**
     * 根据id获取诉讼详情
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Map<String, Object> getDetail(Map<String, Object> params) throws ClientProtocolException, IOException{
    	Map<String, Object> result = new HashMap<String, Object>();
		String id = MapUtils.getString(params, "id");
		String j_captcha = MapUtils.getString(params, "j_captcha");
		String captchaId = MapUtils.getString(params, "captchaId");
		String param = "?id="+id+"&j_captcha="+j_captcha+"&captchaId="+captchaId;
		HttpGet httpGet = new HttpGet("http://zhixing.court.gov.cn/search/newdetail"+param);
		
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("Host", "zhixing.court.gov.cn");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
		CloseableHttpResponse response = client.execute(httpGet);
		try{
			HttpEntity httpEntity = response.getEntity();
			String htmlStr = EntityUtils.toString(httpEntity);
			result = JsonUtil.parseJsonToMap(htmlStr);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			response.close();
		}
		
    	return result;
    }
    
    public static void main(String[] args) throws Exception {  
        String str = "<tr class='a'><td>张三</td><td>20</td></tr><tr><td>李四</td><td>30</td></tr>"; 
          Parser parser = new Parser(str);  
          NodeFilter trFilter = new TagNameFilter("tr");
          NodeFilter cssFilter = new HasAttributeFilter("class");
          NodeFilter filter = new AndFilter(trFilter, cssFilter);
          NodeList trNodeList =  parser.extractAllNodesThatMatch(filter);
          	for(int i=0;i<trNodeList.size();i++){
          		Parser trParser = new Parser(trNodeList.elementAt(i).toHtml());
          		NodeFilter tdfilter = new TagNameFilter("td");
          		NodeList tdNodeList = trParser.extractAllNodesThatMatch(tdfilter);
          		for(int j=0;j<tdNodeList.size();j++){
          			System.out.println(tdNodeList.elementAt(j).toPlainTextString());
          		}
          	}
    }
    
    
}
