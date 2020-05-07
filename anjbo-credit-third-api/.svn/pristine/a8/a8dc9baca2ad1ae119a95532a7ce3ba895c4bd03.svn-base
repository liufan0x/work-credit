/*
 *Project: anjbo-credit-third-api
 *File: test.third.api.DingtalkTest.java  <2017年10月13日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package test.third.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.HttpUtil;

/**
 * @Author KangLG 
 * @Date 2017年10月13日 上午9:20:30
 * @version 1.0
 */
public class DingtalkTest {
	String dingtalkURL = "http://edu.anjbo.com/service/dingtalk.asmx";

	@Before
	public void setUp() throws Exception {
	}
	
//	@Ignore
	@Test
	public void userTest() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Date curDate = new Date();
		curDate.setDate(curDate.getDate()-1);
		System.out.println(DateUtils.dateToString(curDate, null));
		
//		map.put("updateTime", DateUtils.dateToString(curDate, null));				
//		JSONArray json = JSONArray.parseArray(HttpUtil.post(dingtalkURL+"/GetDeptList", map));
//		System.out.println(json);
		
//		map.put("updateTime", "2017-10-31 12:00:00");				
//		JSONArray json = JSONArray.parseArray(HttpUtil.post(dingtalkURL+"/GetUserList", map));
//		System.out.println(json);
		
//		map.put("userName", "康良刚");				
//		JSONObject json = JSONObject.parseObject(HttpUtil.post(dingtalkURL+"/GetUserInfo", map));
//		System.out.println(json);
//		System.out.println(json.get("userid"));
		
	}
	
	@Ignore
	@Test
	public void bpmsTest() throws Exception {		
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", "2");//账户ID固定值
		map.put("agentId", "41605932");
		map.put("processCode", "PROC-424LSGUV-RYRO67BYQBJCT3M5LGDV3-42NSQ68J-6");
		map.put("originatorUserId", "104317204024357506");
		map.put("originatorDeptId", "28740302");
		map.put("approvers", "0827643407944864");
		map.put("ccList", "");
		map.put("formComponent", "{\"标题\":\"爱好value\",\"描述\":\"描述value\"}");
		System.out.println(HttpUtil.post(dingtalkURL+"/SendProcess", map));
	}
	
	@Ignore
	@Test
	public void test() throws Exception{
		System.out.println(HttpUtil.get("http://127.0.0.1:9910/credit/third/api/dingtalk/user/get?userName=aaaa"));
	}

}
