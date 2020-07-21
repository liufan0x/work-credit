/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.AgencyFeescaleDto;
import com.anjbo.bean.AgencyFeescaleRiskcontrolDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAgencyFeescaleRiskcontrolController;
import com.anjbo.service.AgencyFeescaleRiskcontrolService;
import com.anjbo.service.AgencyFeescaleService;
import com.anjbo.service.AgencyService;
import com.anjbo.utils.NumberUtils;
import com.esotericsoftware.minlog.Log;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:58
 * @version 1.0
 */
@RestController
public class AgencyFeescaleRiskcontrolController extends BaseController implements IAgencyFeescaleRiskcontrolController{

	@Resource private AgencyFeescaleRiskcontrolService agencyFeescaleRiskcontrolService;
	
	@Resource private AgencyFeescaleService agencyFeescaleService;
	
	@Resource private AgencyService agencyService;
	
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AgencyFeescaleRiskcontrolDto> page(@RequestBody AgencyFeescaleRiskcontrolDto dto){
		RespPageData<AgencyFeescaleRiskcontrolDto> resp = new RespPageData<AgencyFeescaleRiskcontrolDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(agencyFeescaleRiskcontrolService.search(dto));
			resp.setTotal(agencyFeescaleRiskcontrolService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	public RespDataObject<Map<String, Object>> findStageRate(@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Integer riskGradeId = MapUtils.getInteger(params, "riskGradeId",0);
			if(riskGradeId <= 0) {
				RespHelper.setFailDataObject(resp, new HashMap<String, Object>(), "收费类型为空，或为其它时不需要执行计算");
			}else if(params.containsKey("productId")) {
				if(params.containsKey("cityCode") && params.containsKey("productCode")){
					params.put("productId", MapUtils.getString(params, "cityCode") + MapUtils.getString(params, "productCode"));
				}
			}
			Integer agencyId = MapUtils.getInteger(params, "cooperativeAgencyId");
			AgencyDto agencyDto = new AgencyDto();
			agencyDto.setId(agencyId);
			agencyDto = agencyService.find(agencyDto);
			
			Integer productId = MapUtils.getInteger(params, "productId");
			
			AgencyFeescaleDto agencyFeescaleDto = new AgencyFeescaleDto();
			agencyFeescaleDto.setAgencyTypeId(agencyId);
			agencyFeescaleDto.setRiskGradeId(riskGradeId);
			agencyFeescaleDto.setProductionid(productId);
			agencyFeescaleDto = agencyFeescaleService.find(agencyFeescaleDto);
			
			
			Map<String, Object> map = agencyFeescaleRiskcontrolService.findStageRate(params);
			if(null == map){
				Log.info("未查到费率，逾期费率");
				map = new HashMap<String, Object>();
				map.put("rate", "");
				map.put("overdueRate", "");
			}
			
			map.put("rate", map.get("rate")==null?"":map.get("rate")+"");
			map.put("overdueRate", map.get("overdueRate")==null?"":map.get("overdueRate")+"");
			map.put("customsPoundage", agencyFeescaleDto.getCounterfee());  //关外手续费
			map.put("otherPoundage",   agencyFeescaleDto.getOtherfee());     //其它费用
			map.put("serviceFees",     agencyFeescaleDto.getServicefee());   //固定费用
			map.put("serviceCharge",   agencyFeescaleDto.getServicefee());   //固定费用，保留(兼容APP老数据)

			//最低收费标准
			double chargeStandard = agencyDto.getChargeStandard() == null ? 0 : agencyDto.getChargeStandard();  
			map.put("chargeStandard",  chargeStandard);
			// 收费金额构建
			double chargeMoneyBase = this.calcChargeMoney(MapUtils.getDoubleValue(params, "loanAmount", 0), MapUtils.getIntValue(params, "borrowingDays", 0), map);
			double chargeMoneyTotal = chargeMoneyBase + MapUtils.getDoubleValue(map, "customsPoundage",0) + MapUtils.getDoubleValue(map, "otherPoundage",0) + MapUtils.getDoubleValue(map, "serviceFees");	
			map.put("chargeMoneyBase", chargeMoneyBase); 
			map.put("chargeMoneyTotalInit", chargeMoneyTotal>chargeStandard ? chargeMoneyTotal : chargeStandard); 
			map.put("chargeMoney", MapUtils.getDouble(map, "chargeMoneyTotalInit")); //保留(兼容APP老数据)
			
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			logger.error("查询费率异常,参数："+params.toString(), e);
			RespHelper.setFailDataObject(resp, new HashMap<String, Object>(), RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<AgencyFeescaleRiskcontrolDto> search(@RequestBody AgencyFeescaleRiskcontrolDto dto){ 
		RespData<AgencyFeescaleRiskcontrolDto> resp = new RespData<AgencyFeescaleRiskcontrolDto>();
		try {
			return RespHelper.setSuccessData(resp, agencyFeescaleRiskcontrolService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AgencyFeescaleRiskcontrolDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleRiskcontrolDto> find(@RequestBody AgencyFeescaleRiskcontrolDto dto){ 
		RespDataObject<AgencyFeescaleRiskcontrolDto> resp = new RespDataObject<AgencyFeescaleRiskcontrolDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleRiskcontrolService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleRiskcontrolDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleRiskcontrolDto> add(@RequestBody AgencyFeescaleRiskcontrolDto dto){ 
		RespDataObject<AgencyFeescaleRiskcontrolDto> resp = new RespDataObject<AgencyFeescaleRiskcontrolDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleRiskcontrolService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleRiskcontrolDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AgencyFeescaleRiskcontrolDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleRiskcontrolService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody AgencyFeescaleRiskcontrolDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleRiskcontrolService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 计算收费金额 
	 *  1.计算公式：(modeid:0按天[费率*借款金额*借款期限],1按阶段[费率*借款金额 ]) + (关外手续费+其他金额+固定费用)
	 *  2.因UI模块可调整值(关外手续费+其他金额+固定费用)，故总额为UI计算，此处仅计算收费方式的值
	 * @Author KangLG<2017年12月18日>
	 * @param loanAmount 借款金额
	 * @param borrowingDays 借款期限
	 * @param params
	 * @return 收费金额，不含(关外手续费+其他金额+固定费用)
	 */
	private double calcChargeMoney(double loanAmount, int borrowingDay, Map<String,Object> params){
		int borrowingDays = 0;
		if(borrowingDay>5){
			borrowingDays = borrowingDay-1;
		}else{
			borrowingDays = borrowingDay;
		}
		double rate = MapUtils.getDoubleValue(params, "rate", 0);//费率
		int modeid = MapUtils.getIntValue(params, "modeid", 1);  //按段，按天		
		System.out.println("费率："+rate+"收费方式："+modeid+"借款金额："+loanAmount+"借款期限："+borrowingDays);
		//基础收费金额 (modeid:0按天[费率*借款金额*借款期限],1按阶段[费率*借款金额 ])
		return NumberUtils.formatDecimal(rate*loanAmount*100 * (1!=modeid ? borrowingDays : 1), 2);   			
	}
	
}