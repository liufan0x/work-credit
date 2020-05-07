package com.anjbo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuditBaseService;
import com.anjbo.service.ElementSystemMessageService;


/**
  * 系统消息表 [Controller]
  * @ClassName: ElementSystemMessageController
  * @Description: 
  * @date 2017-12-21 15:01:44
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/element/system/message/v")
public class ElementSystemMessageController extends BaseController{

	private Log log = LogFactory.getLog(ElementSystemMessageController.class);
	
	@Resource 
	private ElementSystemMessageService elementSystemMessageService;
	@Resource
	private AuditBaseService auditBaseService;
	/**
	 * 
	 * @Title: count 
	 * @Description: 消息数接口
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/count")
	@ResponseBody
	public RespDataObject<Object> count(HttpServletRequest request){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			UserDto userDto = getUserDto(request);
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("unreadAuditCount", auditBaseService.selectToAuditCount(userDto.getUid()));
			result.put("unreadCopyCount", auditBaseService.selectUnreadCopyAuditCount(userDto.getUid()));
			result.put("unreadApplayCount", elementSystemMessageService.unreadApplayCount(userDto.getUid()));
			result.put("unreadMsgCount", elementSystemMessageService.selectUnreadCount(userDto.getUid()));//审批消息分离到审批下,查询时过滤掉审批相关消息
			resp.setData(result);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return resp;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: list
	 * @Description: 消息列表
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/list")
	@ResponseBody
	public RespDataObject<Object> list(@RequestBody Map<String,Object> params,HttpServletRequest request){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			//start,pageSize不传时查全部
			UserDto userDto = getUserDto(request);
			params.put("uid", userDto.getUid());
			if(MapUtils.getInteger(params, "start")==null){
				params.put("start",0);
			}
			return RespHelper.setSuccessDataObject(resp, elementSystemMessageService.listByUid(params));//审批消息分离到审批下,查询时过滤掉审批相关消息
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}

}
