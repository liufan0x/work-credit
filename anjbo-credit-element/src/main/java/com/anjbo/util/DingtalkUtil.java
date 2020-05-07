package com.anjbo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;

public class DingtalkUtil {
	private DingtalkUtil(){throw new UnsupportedOperationException();};
	private static final Logger log = Logger.getLogger(DingtalkUtil.class);
	
	public static final String PUSH_MSG_URL = "http://edu.anjbo.com/service/dingtalk.asmx/SendPorcessMessage";//审批消息推送接口
	public static final String PUSH_RESULT_URL = "http://edu.anjbo.com/service/dingtalk.asmx/SendProcessResult";//审批结果消息推送接口
	public static final String USER_LIST = "http://edu.anjbo.com/service/dingtalk.asmx/GetCorpUserList";//获取员工的接口
	public static final String AUTHORIZE_URL="http://edu.anjbo.com/api/dingtalk/authorize.aspx?appid=2&response_type=code&redirect_uri=";//客户端获取登录信息接口
	//需要域名前缀
	public static final String DOC_AUDIT_DETAIL_URL = "/elementH5/template/approval_doc_detail.html";//钉钉消息要件审批详情跳转url
	public static final String SEAL_AUDIT_DETAIL_URL = "/elementH5/template/approval_seal_detail.html";//钉钉消息公章审批详情跳转url
	private static Map<String,String> useridMap = new ConcurrentHashMap<String, String>();
	public static final String CREDIT_URL = "http://sl2.anjbo.com";
	/**
	 * 
	 * @Title: refreshUseridMap 
	 * @Description: 刷新uierId映射 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws Exception 
	 */
	@SuppressWarnings({"rawtypes"})
	public static void refreshUseridMap() throws Exception{
		List<Map> userList = getUserList();
		for (Map map : userList) {
			String mobile = MapUtils.getString(map, "mobile");
			String userId = MapUtils.getString(map, "userid");
			useridMap.put(mobile, userId);
		}
	}
	/**
	 * 
	 * @Title: pushMsg 
	 * @Description: 借要件审批推送钉钉消息
	 * @param @param contentMap
	 * @param @param title
	 * @param @param mobileList
	 * @param @param auditId 审批id
	 * @param @param msgid 消息id
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static void pushMsg(Map<String,String> contentMap,String title,List<String> mobileList,Integer auditId,Integer msgid){
		String url = DOC_AUDIT_DETAIL_URL;
		if(title.indexOf("借公章审批")>-1){
			url = SEAL_AUDIT_DETAIL_URL;
		}
		url = CREDIT_URL+url;
//		url = "http://192.168.1.96"+url;
		if(auditId!=null){
			url = url + "?id="+auditId;
			if(msgid!=null){
				url = url + "&msgId="+msgid;
			}
		}
		try {
			url = AUTHORIZE_URL+URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		pushDingtalkMsg(contentMap,title,url,mobileList,0);
	}
	/**
	 * 
	 * @Title: pushResult 
	 * @Description: 推送钉钉借要件审批结果消息
	 * @param @param content
	 * @param @param title
	 * @param @param mobileList    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void pushResult(String content,String title,List<String> mobileList,Integer auditId){
		String url = DOC_AUDIT_DETAIL_URL;
		if(title.indexOf("借公章审批")>-1){
			url = SEAL_AUDIT_DETAIL_URL;
		}
		url = CREDIT_URL+url;
//		url = "http://192.168.1.96"+url;
		if(auditId!=null){
			url = url + "?id="+auditId;
		}
		try {
			url = AUTHORIZE_URL+URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,String> contentMap = new HashMap<String,String>();
		contentMap.put("content",content);
		pushDingtalkMsg(contentMap,title,url,mobileList,1);
	}
	/**
	 * 
	 * @Title: getUserList 
	 * @Description: 获取钉钉用户列表
	 * @param @return    设定文件 
	 * @return List<Map>    返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> getUserList(){
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("accountId", "2");
			return JSONArray.parseArray(HttpUtil.post(USER_LIST, params), Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Title: getUserListByDeptId 
	 * @Description: 获取钉钉部门编号下的用户列表
	 * @param @return    设定文件 
	 * @return List<Map>    返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> getUserListByDeptId(String deptid){
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("accountId", deptid);
			return JSONArray.parseArray(HttpUtil.post(USER_LIST, params), Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Title: pushDingtalkMsg 
	 * @Description: 调用接口推送钉钉消息
	 * @param @param contentMap
	 * @param @param title
	 * @param @param toUrl
	 * @param @param mobileList
	 * @param @param type    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private static void pushDingtalkMsg(Map<String,String> contentMap,String title,String toUrl,List<String> mobileList,int type){
		try{
			String url = PUSH_MSG_URL;//推送提示消息接口
			if(type==1){
				url = PUSH_RESULT_URL;//推送结果消息接口
			}
			if(mobileList!=null&&mobileList.size()>0){
				Map<String,String> params = new HashMap<String,String>();
				params.put("accountId", "2");
				params.put("agentId", "75130110");
				params.put("title", title);
				if(type==1){
					params.put("content", contentMap.get("content"));
				}else{
					params.put("formComponent", JsonUtil.MapToJson(contentMap));
				}
				for (String mobile : mobileList) {
					String userId = useridMap.get(mobile);
					if(userId==null){
						refreshUseridMap();
						userId = useridMap.get(mobile);
					}
					if (userId != null) {
						params.put("messageUrl",toUrl);
						params.put("useridList", userId);
						String result = HttpUtil.post(url, "UTF-8", "UTF-8", params, 1000);
						Map<String, Object> jsonResult = JsonUtil.jsonToMap(result);
						if ("1".equals(jsonResult.get("status"))) {
							log.info("手机号" + mobile + "钉钉消息推送成功!");
						} else {
							log.info("手机号" + mobile + "钉钉消息推送失败:" + jsonResult.get("msg"));
						}
					} else {
						log.info("手机号"+mobile+"钉钉消息推送失败:找不到对应用户!");
					}
				}
			}
		}catch(Exception e){
			log.info("钉钉消息推送失败!",e);
		}
	}
	public static void main(String[] args) throws Exception {
		refreshUseridMap();
		System.out.println(useridMap.get("18929383301"));
		System.out.println(useridMap.get("18030016225"));
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("content", "测试");
//		pushDingtalkMsg(map, "标题", "http://www.baidu.com", Arrays.asList("18030016225"),1);
	}
}
