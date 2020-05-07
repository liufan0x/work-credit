package com.anjbo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.vo.AuditInfoVo;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuditBaseService;
import com.anjbo.service.ElementSystemMessageService;


/**
  * 审批信息表 [Controller]
  * @ClassName: ElementAuditBaseController
  * @Description: 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/element/audit/base")
public class AuditBaseController extends BaseController{

	private Log log = LogFactory.getLog(AuditBaseController.class);
	
	@Resource 
	private AuditBaseService auditBaseService;
	@Resource 
	private ElementSystemMessageService msgService;
	/**
	 * 
	 * @Title: pickPerson 
	 * @Description: 获取审批候选人
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping(value={"/v/pickPerson","/pickPerson"})
	@ResponseBody
	public RespDataObject<Object> pickPerson(@RequestBody Map<String,Object> params,HttpServletRequest request){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			Integer id = MapUtils.getInteger(params, "id");
			Integer level = MapUtils.getInteger(params, "level");//审批层级
			Integer type = MapUtils.getInteger(params, "type");//审批类型
			String city = MapUtils.getString(params, "city");
			if(level==null){
				level = 1;
			}
			if(type==null){
				type = 1;
			}
			if(city==null){
				city = "深圳";
			}
			if(id!=null){
				level = MapUtils.getInteger(params, "level",0);
				AuditBaseDto auditBaseDto = auditBaseService.selectAuditBaseDtoById(id);
				type = auditBaseDto.getType();
//				String uid = MapUtils.getString(params, "uid");
//				UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
//				if(auditBaseDto.getType()<3){//要件
//					if("会计".equals(userDto.getRoleName())||"出纳".equals(userDto.getRoleName())){
//						type = 2;
//					}
//				}else{//公章
//					type = 3;
//				}
				city = auditBaseService.selectBoxcityByOrderNo(auditBaseDto.getOrderNo());
			}
			if(type<0||type>4||level<0||level>5){
				return RespHelper.setFailDataObject(resp, null, "参数错误!");
			}
			params.put("level", level);
			params.put("city", city);
			params.put("type", type);
			Map<String,Object> configMap = auditBaseService.selectCandidates(params);
			String auditorUidListStr = MapUtils.getString(configMap, "degree"+level);
			return RespHelper.setSuccessDataObject(resp, JSONArray.parseArray(auditorUidListStr, Map.class));
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
 		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: copyToList 
	 * @Description: 抄送人选择
	 * @param @param request
	 * @param @param response
	 * @param @return    设定文件 
	 * @return RespDataObject<Map<String,Object>>    返回类型 
	 * @throws
	 */
	@RequestMapping("/v/copyToList")
	@ResponseBody
	public RespDataObject<Object> copyToList(HttpServletRequest request,HttpServletResponse response){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try {
			List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
			List<UserDto> userDtoList = getAllUserDtoList();
			for (UserDto userDto : userDtoList) {
				if(userDto.getIsEnable()==0&&StringUtils.isNotBlank(userDto.getDingtalkUid())){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("name", userDto.getName());
					map.put("id", userDto.getUid());
					userList.add(map);
				}
			}
			return RespHelper.setSuccessDataObject(resp, userList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}

	/**
	 * 
	 * @Title: list 
	 * @Description: 审批列表
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/v/list")
	@ResponseBody
	public RespDataObject<Map<String,Object>> list(@RequestBody Map<String,Object> params, HttpServletRequest request){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try{
			params.put("uid", getUserDto(request).getUid());
			if(MapUtils.getInteger(params, "start")==null){
				params.put("start",0);
			}
			if(MapUtils.getInteger(params, "listType")==null){
				params.put("listType", 1);
			}
			Integer listType = MapUtils.getInteger(params, "listType");
			List<AuditInfoVo> list = null;
			int count = 0;//待我审批未处理消息数
			switch (listType) {
				case 1: list = auditBaseService.selectApplyList(params);break;//审批申请列表
				case 2: count = auditBaseService.selectToAuditCount(MapUtils.getString(params, "uid"));
						if(count==0){
							list = Collections.emptyList();
						}else{
							list = auditBaseService.selectToAuditList(params);
						}
						break;//待我审批列表
				case 3: list = auditBaseService.selectAuditedList(params);break;//我已审批列表
				case 4: list = auditBaseService.selectCopyList(params);break;//抄送给我列表
				default: auditBaseService.selectApplyList(params);break;//审批申请列表
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("list", list);
			resultMap.put("count", count);
			return RespHelper.setSuccessDataObject(resp, resultMap);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}
	
}
