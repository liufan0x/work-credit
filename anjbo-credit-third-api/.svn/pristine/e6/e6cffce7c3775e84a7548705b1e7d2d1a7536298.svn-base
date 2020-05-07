/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.service.dingtalk.impl.DingtalkServiceImpl.java  <2017年10月13日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.dingtalk.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anjbo.bean.dingtalk.DingtalkUser;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Enums.DingtalkServiceMethod;
import com.anjbo.service.dingtalk.DingtalkService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.common.ThirdConfigUtil;

/**
 * @Author KangLG 
 * @Date 2017年10月13日 下午3:44:32
 * @version 1.0
 */
@Service
public class DingtalkServiceImpl implements DingtalkService {
	static Log logger = LogFactory.getLog(DingtalkServiceImpl.class);
	static String dingUrl = null;
	static {
        try {
        	dingUrl = ThirdConfigUtil.getProperty("DingTalk_WS_URL");
        } catch (Exception e) {
        	logger.error("钉钉接口参数初始化失败:DingTalk_WS_URL...");
        }
    }
		
	/* (non-Javadoc)
	 * @see com.anjbo.service.dingtalk.impl.DingtalkService#getUserByName(java.lang.String)
	 */
	@Override
	public DingtalkUser getUserByName(String userName) {
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", userName);
			// status(1成功0失败), msg, userid, deptid
			JSONObject json = JSONObject.parseObject(HttpUtil.post(String.format("%s/%s", dingUrl, DingtalkServiceMethod.USER_INFO.getValue()), map));
			if(null!=json && 1==json.getIntValue("status")){
				DingtalkUser vo = new DingtalkUser(json.getString("deptid"), json.getString("userid"));
				vo.setIsEnable(0);
				return vo;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}	
	
	/* (non-Javadoc)
	 * @see com.anjbo.service.dingtalk.impl.DingtalkService#bpmsCreate(com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto)
	 */
	@Override
	public String bpmsCreate(ThirdDingtalkBpmsDto dto) {
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountId", "2");                   //账户ID固定值
			map.put("agentId", StringUtils.isNotEmpty(dto.getAgentId()) ? dto.getAgentId() : "41605932");        //企业微应用标识
			map.put("processCode", StringUtils.isNotEmpty(dto.getProcessCode()) ? dto.getProcessCode() : "PROC-424LSGUV-RYRO67BYQBJCT3M5LGDV3-42NSQ68J-6");//审批流/审批表单码
			map.put("originatorUserId", dto.getOriginatorUserId());//审批实例发起人的userid
			map.put("originatorDeptId", dto.getOriginatorDeptId());//发起人所在的部门ID
			map.put("approvers", dto.getApprovers());              //审批人userid列表
			map.put("ccList", dto.getCcList()); 				   //抄送人userid列表
			map.put("formComponent", dto.getFormComponent());      //审批流表单参数
			
			JSONObject json = JSONObject.parseObject(HttpUtil.post(String.format("%s/%s", dingUrl, DingtalkServiceMethod.SEND_PROCESS_INSTANCE.getValue()), map));
			return null!=json && json.containsKey("processInstanceId") ? json.getString("processInstanceId") : "ERROR";
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	@SuppressWarnings({ "deprecation", "serial" })
	@Override
	public List<DeptDto> getDeptList() {
		List<DeptDto> list = new ArrayList<DeptDto>();
		try{	
			final Date curDate = new Date();
			curDate.setDate(curDate.getDate()-1);
			Map<String, String> map = new HashMap<String, String>(){{
				put("updateTime", DateUtils.dateToString(curDate, null));   //更新时间截
				put("accountId", "2");   //对应接口公司ID，不传值获取所有公司
			}};
			JSONArray jsonAry = JSONArray.parseArray(HttpUtil.post(String.format("%s/%s", dingUrl, DingtalkServiceMethod.DEPT_LIST.getValue()), map));
			
			JSONObject json;
			DeptDto dto;
			for (int i=0; i<jsonAry.size(); i++) {
				json = jsonAry.getJSONObject(i);
				dto = new DeptDto();
				dto.setAgencyId(1);
				dto.setPid(json.getIntValue("parent_id"));
				dto.setId(json.getIntValue("id"));
				dto.setName(json.getString("name"));
				dto.setRemark("钉钉");	
				list.add(dto);
			}
		} catch (Exception e){
			e.printStackTrace();
		}	
		return list;
	}

	@SuppressWarnings({ "deprecation", "serial" })
	@Override
	public List<UserDto> getUserList() {
		List<UserDto> list = new ArrayList<UserDto>();
		try{			
			final Date curDate = new Date();
			curDate.setDate(curDate.getDate()-1);
			Map<String, String> map = new HashMap<String, String>(){{
				put("updateTime", DateUtils.dateToString(curDate, null));   //更新时间截		
				put("accountId", "2");   //对应接口公司ID，不传值获取所有公司
			}};
			JSONArray jsonAry = JSONArray.parseArray(HttpUtil.post(String.format("%s/%s", dingUrl, DingtalkServiceMethod.USER_LIST.getValue()), map));
			
			JSONObject json;
			UserDto dto;
			for (int i=0; i<jsonAry.size(); i++) {
				json = jsonAry.getJSONObject(i);
				dto = new UserDto();
				dto.setAgencyId(1);				
				dto.setDeptIdArray(json.getString("deptid"));
				dto.setDingtalkUid(json.getString("userid"));
				dto.setJobNumber(json.getString("jobnumber"));//工号
				dto.setPosition(json.getString("position"));//职务
				dto.setName(json.getString("user_name"));
				dto.setTelphone(json.getString("telphone"));
				dto.setMobile(json.getString("mobile"));
				dto.setEmail(json.getString("email"));
				dto.setIsEnable(0==json.getIntValue("status") ? 0 : 1);				
				list.add(dto);
			}
		} catch (Exception e){
			e.printStackTrace();
		}	
		return list;
	}	
	
}
