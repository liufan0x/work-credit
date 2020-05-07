package com.anjbo.processor;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.security.cert.X509Certificate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.anjbo.bean.tools.DegreeBuildingDto;
import com.anjbo.bean.tools.DegreeHouseNumDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.tools.DegreeHouseNumberService;
import com.anjbo.utils.HttpUtil;
@Component
public class DegreeHouseNumberProcessor {
    private static final Log log = LogFactory.getLog(DegreeHouseNumberProcessor.class);

    static int pagesize = 10000;
    private CloseableHttpClient httpClient = createSSLClientDefault();
    private static Map<String,String> threadMap = new HashMap<String,String>();
    //private static String URL="https://szjzz.szga.gov.cn/";
    private static String URL="https://szjzz.ga.sz.gov.cn/";
    @Resource
    private DegreeHouseNumberService degreeHouseNumberService;

    /**
     * @param houseNumDto
     * @return 根据条件查询楼栋信息
     * @throws IOException
     * @throws JSONException
     */
    public RespData<DegreeBuildingDto> findBuilding(final DegreeHouseNumDto houseNumDto) throws IOException, JSONException {

    	if(!threadMap.containsKey("buildingThread")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	threadMap.put("buildingThread",Thread.currentThread().getName());
                        //fetchBuildingByName(houseNumDto.getRegion(),houseNumDto.getStreet(),houseNumDto.getPropertyName(),houseNumDto.getCookieValue(),houseNumDto.getValidateCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }  finally{
                    	threadMap.remove("buildingThread");
                    }
                }
            }).start();
    	}

        RespData<DegreeBuildingDto> respData= new RespData<DegreeBuildingDto>();;
		try {
			String keyWord = URLEncoder.encode(houseNumDto.getPropertyName(), "utf-8");
			respData.setCode(RespStatusEnum.SUCCESS.getCode());
			respData.setMsg(RespStatusEnum.SUCCESS.getMsg());
			String url = URL+"s/common/searchBuilding?province=44&city=03&pagesize=" + pagesize + "&pagenum=&region=" + houseNumDto.getRegion() + "&street=4403" + houseNumDto.getStreet() + "&keyword=" + keyWord+"&validateCode="+houseNumDto.getValidateCode();
			System.out.println("url:"+url);
			HttpUriRequest request =  new HttpGet(url);
			request.setHeader("Cookie", houseNumDto.getCookieValue());
//			request.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
//			request.setHeader("Referer","https://szjzz.ga.sz.gov.cn/index/queryHouseCode");
			//request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
//			request.setHeader("X-Requested-With", "XMLHttpRequest");
			System.out.println("Cookie:"+houseNumDto.getCookieValue());
			CloseableHttpResponse execute = httpClient.execute(request);
			System.out.println(execute);
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println("根据条件查询楼栋信息结果："+result);
			//String url = "http://mszjzz.szga.gov.cn/jzzNew/commonNew/lggajSearchBuildingNew";
      /* String url = "http://mszjzz.ga.sz.gov.cn/jzzNew/commonNew/lggajSearchBuildingNew";
			String region = StringUtils.isBlank(houseNumDto.getRegion())?"":houseNumDto.getRegion().replace("4403", "");
			String street = StringUtils.isBlank(houseNumDto.getStreet())?"":houseNumDto.getStreet().replace(houseNumDto.getRegion(), "");
			
			String requestBody = "regionID=" + region + "&streetID=" + street + "&keyword="+ keyWord +"&ispaging=1&pagenum=1&pagesize=" + pagesize + "&USER_ID=1&USER_NAME=undefined";
			String result = "";
			try {
				result = HttpUtil.housePost(url, "utf-8", "utf-8", 60000, requestBody);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			//log.info("根据条件查询楼栋信息结果："+result);
			JSONObject object = new JSONObject(result);
            if(object.getInt("status")==2){
            	respData.setCode(RespStatusEnum.FAIL.getCode());
            	respData.setMsg("验证码不匹配");
            }else if(object.getInt("status")==0) {
            	respData.setCode(RespStatusEnum.FAIL.getCode());
            	respData.setMsg("未搜索到结果");
            }
			List<DegreeBuildingDto> houseNumDtos = parseForFindBuildingNew(result);
			if(houseNumDtos==null){
			    respData.setData(new ArrayList<DegreeBuildingDto>());
			}else {
			    respData.setData(houseNumDtos);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return respData;
    }
    
    public static void main(String[] args) {
    	/*String url = "https://szjzz.ga.sz.gov.cn/s/common/searchBuilding?province=44&city=03&pagesize=10000&pagenum=&region=440303&street=4403440303001&keyword=1&validateCode=t3ax";
		HttpUriRequest request =  new HttpGet(url);
		request.setHeader("Cookie", "szjzz.session.id=2872ab92c97d4f168f4f9d281e8cba92; Path=/; HttpOnly");
		CloseableHttpClient httpClient = createSSLClientDefault();
		CloseableHttpResponse execute;
		try {
			System.out.println("url:"+url);
			execute = httpClient.execute(request);
			System.out.println(execute);
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    	String url = "https://szjzz.ga.sz.gov.cn/s/common/searchHouse?province=44&city=03&region=440303&street=4403440303007&building=4403030070070900016&pagesize=6&pagenum=&keyword=1&validateCode=c7k9";
		HttpUriRequest request =  new HttpGet(url);
		request.setHeader("Cookie", "Hm_lvt_82116c626a8d504a5c0675073362ef6f=1561367312; szjzz.session.id=f46b507cdd34429fa702d22b32e5659e; Hm_lpvt_82116c626a8d504a5c0675073362ef6f=1561713441");
		CloseableHttpClient httpClient = createSSLClientDefault();
		CloseableHttpResponse execute;
		try {
			System.out.println("url:"+url);
			execute = httpClient.execute(request);
			System.out.println(execute);
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * @param houseNumDto     区域编码
     * @return 根据条件查询房屋编码
     * @throws IOException
     * @throws JSONException
     */
    public RespData<DegreeHouseNumDto> findHouseNumber(final DegreeHouseNumDto houseNumDto) throws IOException, JSONException {

    	if(!threadMap.containsKey("houseNumberThread")){
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	threadMap.put("houseNumberThread",Thread.currentThread().getName());
	                    //fatchHouseNumberByRomnum(houseNumDto.getRegion(),houseNumDto.getStreet(),houseNumDto.getBuildingId(),houseNumDto.getRoomNum(),houseNumDto.getCookieValue(),houseNumDto.getValidateCode());
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally{
	                	threadMap.remove("houseNumberThread");
	                }
	            }
	        }).start();
    	}

        RespData<DegreeHouseNumDto> respData = new RespData<DegreeHouseNumDto>();
        respData.setCode(RespStatusEnum.SUCCESS.getCode());
        respData.setMsg(RespStatusEnum.SUCCESS.getMsg());
        String keyWord = URLEncoder.encode(houseNumDto.getRoomNum(), "utf-8");
        String url = URL+"s/common/searchHouse?province=44&city=03&region=" + houseNumDto.getRegion() + "&street=4403" + houseNumDto.getStreet() + "&building=" + houseNumDto.getBuildingId() + "&pagesize=" + pagesize + "&pagenum=&keyword=" + keyWord+"&validateCode="+houseNumDto.getValidateCode();
        System.out.println("url:"+url);
        System.out.println("Cookie:"+houseNumDto.getCookieValue());
        HttpUriRequest request =  new HttpGet(url);
		request.setHeader("Cookie", houseNumDto.getCookieValue());
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        //String url = "http://mszjzz.szga.gov.cn/jzzNew/common/lggajSearchHouse";
//        String url = "http://mszjzz.ga.sz.gov.cn/jzzNew/common/lggajSearchHouse";
//        String region = StringUtils.isBlank(houseNumDto.getRegion())?"":houseNumDto.getRegion().replace("4403", "");
//        String street = StringUtils.isBlank(houseNumDto.getStreet())?"":houseNumDto.getStreet().replace(houseNumDto.getRegion(), "");
        //regionID=03&streetID=001&buildingID=4403030010060100060&ispaging=1&pagenum=1&pagesize=30&keyword=1&USER_ID=1&USER_NAME=undefined
//        String requestBody = "regionID=" + region + "&streetID=" + street + "&buildingID="+ houseNumDto.getBuildingId() +"&ispaging=1&pagenum=1&pagesize=" + pagesize + "&keyword="+ keyWord +"&USER_ID=1&USER_NAME=undefined";
//        String result = "";
//		try {
//			result = HttpUtil.housePost(url, "utf-8", "utf-8", 60000, requestBody);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        log.info("根据条件查询房屋编码结果："+result);
        JSONObject object = new JSONObject(result);
        if(object.getInt("status")==2){
        	respData.setCode(RespStatusEnum.FAIL.getCode());
        	respData.setMsg("验证码不匹配");
        }else if(object.getInt("status")==0) {
        	respData.setCode(RespStatusEnum.FAIL.getCode());
        	respData.setMsg("未搜索到结果");
        }
        List<DegreeHouseNumDto> houseNumDtos = parseForFindHouseNumberNew(result);
        if(houseNumDtos==null){
            respData.setData(new ArrayList<DegreeHouseNumDto>());
        }else {
            respData.setData(houseNumDtos);
        }
        return respData;
    }

    private List<DegreeHouseNumDto> parseForFindHouseNumber(String s) throws JSONException {
        ArrayList<DegreeHouseNumDto> houseNumDtos = new ArrayList<DegreeHouseNumDto>();
        if (StringUtils.isNotBlank(s)) {
            JSONObject object = new JSONObject(s);
            if(object.getInt("status")!=1){
                return null;
            }
            JSONArray res = object.getJSONArray("res");
            for (int i = 0; i < res.length(); i++) {
                JSONObject jsonObject = res.getJSONObject(i);
                DegreeHouseNumDto numDto = new DegreeHouseNumDto();
                numDto.setAddress(jsonObject.getString("address"));
                numDto.setBuildingId(jsonObject.getString("buildingID"));
                numDto.setHouseNum(jsonObject.getString("houseCode"));
                numDto.setRoomNum(jsonObject.getString("roomNum"));
                houseNumDtos.add(numDto);
            }
        }
        return houseNumDtos;
    }
    
    private List<DegreeHouseNumDto> parseForFindHouseNumberNew(String s) throws JSONException {
        ArrayList<DegreeHouseNumDto> houseNumDtos = new ArrayList<DegreeHouseNumDto>();
        if (StringUtils.isNotBlank(s)) {
            JSONObject object = new JSONObject(s);
            //log.info("根据条件查询房屋编码结果：status="+object.getInt("status"));
            if(object.getInt("status")!=1){
                return null;
            }
            JSONArray res = object.getJSONArray("res");
            for (int i = 0; i < res.length(); i++) {
                JSONObject jsonObject = res.getJSONObject(i);
                DegreeHouseNumDto numDto = new DegreeHouseNumDto();
                numDto.setAddress(jsonObject.getString("address"));
                numDto.setBuildingId(jsonObject.getString("buildingID"));
                numDto.setHouseNum(jsonObject.getString("houseCode"));
                numDto.setRoomNum(jsonObject.getString("roomNum"));
                houseNumDtos.add(numDto);
            }
        }
        return houseNumDtos;
    }

    private List<DegreeBuildingDto> parseForFindBuilding(String s) throws JSONException {
        ArrayList<DegreeBuildingDto> dtos = new ArrayList<DegreeBuildingDto>();
        if (StringUtils.isNotBlank(s)) {
            JSONObject object = new JSONObject(s);
            if(object.getInt("status")!=1){
                return null;
            }
            JSONArray res = object.getJSONArray("res");
            for (int i = 0; i < res.length(); i++) {
                JSONObject jsonObject = res.getJSONObject(i);
                DegreeBuildingDto buildingDto = new DegreeBuildingDto();
                buildingDto.setAreaId(jsonObject.getString("areaid"));
                buildingDto.setBuildingID(jsonObject.getString("buildingID"));
                buildingDto.setBuildingName(jsonObject.getString("buildingName"));
                dtos.add(buildingDto);
            }
        }
        return dtos;
    }
    
    private List<DegreeBuildingDto> parseForFindBuildingNew(String s) throws JSONException {
        ArrayList<DegreeBuildingDto> dtos = new ArrayList<DegreeBuildingDto>();
        if (StringUtils.isNotBlank(s)) {
            JSONObject object = new JSONObject(s);
            //log.info("根据条件查询楼栋信息结果：status="+object.getInt("status"));
            if(object.getInt("status")!=1){
                return null;
            }
            JSONArray res = object.getJSONArray("res");
            for (int i = 0; i < res.length(); i++) {
                JSONObject jsonObject = res.getJSONObject(i);
                DegreeBuildingDto buildingDto = new DegreeBuildingDto();
                buildingDto.setAreaId(jsonObject.getString("areaid"));
                buildingDto.setBuildingID(jsonObject.getString("buildingID"));
                buildingDto.setBuildingName(jsonObject.getString("buildingName"));
                dtos.add(buildingDto);
            }
        }
        return dtos;
    }

    private static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                        // 信任所有
                        @SuppressWarnings("unused")
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    private void fetchBuildingByName(String region, String street, String propertyName,String cookieValue,String validateCode) throws IOException, JSONException {

        String keyWord = URLEncoder.encode(propertyName, "utf-8");
        String url = URL+"s/common/searchBuilding?province=44&city=03&pagesize=" + pagesize + "&pagenum=&region=" + region + "&street=4403" + street + "&keyword=" + keyWord+"&validateCode="+validateCode;
        HttpUriRequest request =  new HttpGet(url);
        request.setHeader("Cookie", cookieValue);
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());

        JSONObject object = new JSONObject(result);
        if(object.getInt("status")!=1){
            return;
        }
        String page = object.getString("page");
        Pattern p = Pattern.compile("条，共 (\\d+) 条");
        Matcher m = p.matcher(page);
        if(m.find()){
            String size = m.group(1);
            if(Integer.valueOf(size)>0){
                CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
                String result2 = EntityUtils.toString(response.getEntity());
                List<DegreeBuildingDto> dtos = parseForFindBuilding(result2);
                if(dtos!=null&&dtos.size()>0){
                    for (DegreeBuildingDto dto:dtos){
                        dto.setKeyWord(propertyName);
                        dto.setRegion(region);
                        dto.setStreet(street);
                        List<DegreeBuildingDto> buildingDtos=degreeHouseNumberService.queryByBuildingId(dto.getBuildingID());
                        if(buildingDtos==null||buildingDtos.size()<=0){
                        	degreeHouseNumberService.insertBuilding(dto);
                        }
                    }
                }
            }
        }
    }

    private void fatchHouseNumberByRomnum(String region, String street, String buildingId, String romnum,String cookieValue,String validateCode) throws IOException, JSONException {
        String keyWord = URLEncoder.encode(romnum, "utf-8");
        String url = URL+"s/common/searchHouse?province=44&city=03&region=" + region + "&street=4403" + street + "&building=" + buildingId + "&pagesize=" + pagesize + "&pagenum=&keyword=" + keyWord+"&validateCode="+validateCode;
        HttpUriRequest request =  new HttpGet(url);
        request.setHeader("Cookie", cookieValue);
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        JSONObject object = new JSONObject(result);
        if(object.getInt("status")!=1){
            return;
        }
        String page = object.getString("page");
        Pattern p = Pattern.compile("条，共 (\\d+) 条");
        Matcher m = p.matcher(page);
        if(m.find()){
            String size = m.group(1);
            if(Integer.valueOf(size)>0){
                CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
                String result2 = EntityUtils.toString(response.getEntity());
                List<DegreeHouseNumDto> houseNumDtos = parseForFindHouseNumber(result2);
                if(houseNumDtos!=null&&houseNumDtos.size()>0){
                    for (DegreeHouseNumDto dto : houseNumDtos) {
                        dto.setRegion(region);
                        dto.setStreet(street);
                        if (!degreeHouseNumberService.isIncludedHouseNumber(dto.getHouseNum())) {
                        	degreeHouseNumberService.insert(dto);
                        }
                    }
                }
            }
        }
    }
}
