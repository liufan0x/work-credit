package com.anjbo.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.RiskModelConfigDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.RiskModelConfigService;
/**
 * 风控模型配置控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/credit/risk/riskmodel/v")
public class RiskModelConfigController extends BaseController{

	
	
	@Resource
	private RiskModelConfigService riskModelConfigService;
	
	/**
	 * 列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespPageData<RiskModelConfigDto> list(@RequestBody Map<String, Object> params){
		RespPageData<RiskModelConfigDto> resp = new RespPageData<RiskModelConfigDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			resp.setTotal(riskModelConfigService.selectCreditCount(params));
			resp.setRows(riskModelConfigService.selectCreditList(params));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 修改风控模型
	 * @param request
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/updateCredit")
	public RespStatus updateCredit(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!params.containsKey("id")){
				result.setMsg("修改风控模型异常,缺少主键");
				return result;
			}
			params.put("uid", getUserDto(request).getUid());
			riskModelConfigService.updateCredit(params);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
