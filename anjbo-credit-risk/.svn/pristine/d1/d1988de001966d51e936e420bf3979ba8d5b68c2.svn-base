package com.anjbo.service.impl;

import com.anjbo.common.Constants;
import com.anjbo.utils.ConfigUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
 * 深圳诉讼数据抓取
 * @ClassName: SZLawsuitClient 
 * @author liuw 
 * @date 2016-10-8 下午02:43:55 
 */ 
public class SZLawsuitClient {
	static CloseableHttpClient client = HttpClients.createDefault();
	private static final String IMG_DEFAULT_PATH = "/credit/risk/upload";
	private static final String IMG_NAME = "SZAuthcode.jpg";
	
	/**
	 * 访问查询页获取cookies
	 * @throws IOException
	 */
	public static void getSessionCookies() throws IOException{
		HttpGet httpGet = new HttpGet("http://ssfw.szcourt.gov.cn/frontend/anjiangongkai/caseOpen");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.addHeader("Host", "ssfw.szcourt.gov.cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        CloseableHttpResponse response = client.execute(httpGet);
        response.close();
        httpGet.abort();
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getAuthCode(HttpServletRequest request) throws IOException{
		HttpGet httpGet = new HttpGet("http://ssfw.szcourt.gov.cn/yzm.jsp");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        //httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Host", "ssfw.szcourt.gov.cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        //httpGet.addHeader("Referer", "http://zhixing.court.gov.cn/search/");
        
        //获取当前项目图片存储路径,不存在则创建
        String savePicDicUrl = request.getSession().getServletContext().getRealPath(IMG_DEFAULT_PATH);
		File savePicDic = new File(savePicDicUrl);
        if (!savePicDic.exists()) {
        	savePicDic.mkdir();  
        }
		String domain = ConfigUtil.getStringValue(Constants.PROJECT_DOMAIN,ConfigUtil.CONFIG_VERSION);
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
	
	/**
	 * 根据验证码和名称查询结果
	 * @param map
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Map<String, Object> getResult(Map<String, Object> map) throws ClientProtocolException, IOException{
		String code = MapUtils.getString(map, "code");
		String appliers=MapUtils.getString(map, "appliers");
		String param = "?appliers="+URLEncoder.encode(appliers, "utf-8")+"&code="+code;
		HttpGet httpGet = new HttpGet("http://ssfw.szcourt.gov.cn/frontend/anjiangongkai/caseOpen/query"+param);
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("Host", "ssfw.szcourt.gov.cn");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		CloseableHttpResponse response = client.execute(httpGet);
		Map<String, Object> data = null;
		try{
			HttpEntity httpEntity = response.getEntity();
			String htmlStr = EntityUtils.toString(httpEntity);
			data = parseToMap(htmlStr);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			response.close();
		}
		return data;
	}
	
    /**
     * 解析获取到的html
     * @param htmlStr
     * @return
     * @throws ParserException
     */
    public static Map<String, Object> parseToMap(String htmlStr) throws ParserException{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Parser parser = new Parser(htmlStr);
    	NodeFilter coleeFilter = new HasAttributeFilter( "id", "colee" );
    	NodeList tbodyNodeList =  parser.parse(coleeFilter);
    	String tbodyHtmlStr = tbodyNodeList.elementAt(0).toHtml();
    	String testStr = tbodyNodeList.toHtml();
    	Parser trparser = new Parser(tbodyHtmlStr);
        NodeFilter trFilter = new TagNameFilter("tr");
        NodeList trNodeList =  trparser.extractAllNodesThatMatch(trFilter);
        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();;
        if(trNodeList.size()>0){
            for(int i=0;i<trNodeList.size();i++){
        		Parser tdParser = new Parser(trNodeList.elementAt(i).toHtml());
        		NodeFilter tdfilter = new TagNameFilter("td");
        		NodeList tdNodeList = tdParser.extractAllNodesThatMatch(tdfilter);
        		Map<String, Object> rowMap = new HashMap<String, Object>();
        		if(tdNodeList.elementAt(0).toPlainTextString().trim().indexOf("nbsp")!=-1){
        			continue;
        		}
        		rowMap.put("caseNo", tdNodeList.elementAt(0).toPlainTextString().trim());
        		rowMap.put("judge", tdNodeList.elementAt(1).toPlainTextString().trim());
        		rowMap.put("litigant", tdNodeList.elementAt(2).toPlainTextString().trim().replace("\t", "").replace("\n", "").replace("\\s","").replace("\r", ""));
        		rowMap.put("registerDate", tdNodeList.elementAt(3).toPlainTextString().trim());
        		rowMap.put("openCourtDate", tdNodeList.elementAt(4).toPlainTextString().trim());
        		rowMap.put("closeCourtDate", tdNodeList.elementAt(5).toPlainTextString().trim());
        		rowMap.put("status", tdNodeList.elementAt(6).toPlainTextString().trim());
        		listMap.add(rowMap);
            }	
        }
        map.put("listMap", listMap);
    	return map;
    }
}
