package com.anjbo.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ProductSubmitBaseService;
import com.anjbo.utils.CommonDataUtil;

@RequestMapping("/credit/product/data/submit/base/v")
@Controller
public class ProductSubmitBaseController extends BaseController{
	
	private static final Logger log = Logger.getLogger(ProductSubmitBaseController.class);
	
	@Resource
	private ProductSubmitBaseService productSubmitBaseService;
	
	@RequestMapping("check")
	@ResponseBody
	public RespDataObject<Integer> check(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Integer> respStatus = new RespDataObject<Integer>();
		productSubmitBaseService.check(map);
		return respStatus;
	}
	
	@RequestMapping("submit")
	@ResponseBody
	public RespStatus submit(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		//处理业务逻辑
		RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		//录入流水,修改订单状态
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
				map.put("mobile", user.getMobile());
			}
			map.put("createUid", MapUtils.getString(map, "updateUid"));
			UserDto u = CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(map, "updateUid"));
			map.put("userName", u.getName());
			String packageClassMethodName= MapUtils.getString(map, "packageClassMethodName");
			if(null==packageClassMethodName){
				RespHelper.setFailRespStatus(respStatus, "packageClassMethodName不能为空");
				return respStatus;
			}
			respStatus = productSubmitBaseService.submit(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respStatus;
	}
	
	@RequestMapping("submitFailDelete")
	@ResponseBody
	public RespStatus submitFailDelete(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		//删除提交失败的订单
		try {
			productSubmitBaseService.submitFailDelete(map);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respStatus;
	}
	
}
