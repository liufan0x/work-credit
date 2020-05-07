package com.anjbo.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.RiskGradeService;
import com.anjbo.utils.NumberUtil;

@Controller
@RequestMapping("/credit/customer/risk/v")
public class RiskGradeController extends BaseController{
	
	private static final Log log = LogFactory.getLog(RiskGradeController.class);
	@Resource
	private RiskGradeService riskGradeService;
	
	/**
	 * 根据机构类型ID查询风控等级
	 * @param request
	 * @param agencyTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findRiskGradeList")
	public RespDataObject<List<Map<String,Object>>> findRiskGradeList(HttpServletRequest request,@RequestBody Map<String, Object> params){	
		RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{			
			List<Map<String,Object>> riskGradeLists = new ArrayList<Map<String,Object>>();
			Map<String,Object> maps = new HashMap<String,Object>();
			maps.put("id", 0);
			maps.put("riskGradeId","0");
			maps.put("name","其他");
			riskGradeLists.add(maps);
			int cooperativeAgencyId = MapUtils.getIntValue(params, "cooperativeAgencyId");
			// 机构收费类型		
			if(0!=cooperativeAgencyId&&params.get("cityCode")!=null&&params.get("productCode")!=null){
				int productId = Integer.parseInt(MapUtils.getString(params, "cityCode")+MapUtils.getString(params, "productCode"));
				List<Map<String,Object>> riskGradeList = riskGradeService.searchRiskGradeListByAgency(cooperativeAgencyId, productId);
				if(null!=riskGradeList && !riskGradeList.isEmpty()){
					List<DictDto> dictDtoList = getDictDtoByType("riskControl");
					for (DictDto dictDto : dictDtoList) {
						for(Map<String,Object> risk : riskGradeList){
							if(dictDto.getCode().equals(String.valueOf(risk.get("riskGradeId")))){
								maps = new HashMap<String, Object>();							
								maps.put("id", dictDto.getCode());
								maps.put("name", dictDto.getName());
								maps.put("riskGradeId", dictDto.getCode());
								riskGradeLists.add(maps);
							}
						}
					}
				}			
				result.setMsg(RespStatusEnum.SUCCESS.getMsg());
				result.setCode(RespStatusEnum.SUCCESS.getCode());
				result.setData(riskGradeLists);
			}
			else{
				if(cooperativeAgencyId==0){
					result.setMsg("请选择债务置换贷款合作机构");
				}else{
					result.setMsg("参数异常");
				}
				result.setData(null);
				return result;
			}
		} catch (Exception e){
			log.error("根据机构码查询风控等级失败==>", e);
		}
		return result;
	}
		
	/**
	 * 获取机构类型固定/关外/其他等费用 
	 * @param request
	 * @param productId 产品ID
	 * @param agencyTypeId 机构类型ID
	 * @param orderDto
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAgencyPoundage")
	public RespDataObject<Map<String,Object>> findAgencyPoundage(HttpServletRequest request,@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			//畅贷查询其他金额
			String orderNo=MapUtils.getString(map, "orderNo");
			if(MapUtils.getString(map, "cityCode")!=null&&MapUtils.getString(map, "productCode")!=null){
				map.put("productId", MapUtils.getInteger(map, "cityCode")+MapUtils.getInteger(map, "productCode"));
			}else if(orderNo!=null&&!orderNo.equals("")&&!orderNo.equals("0")){
				OrderListDto orderList = new OrderListDto();
				orderList.setOrderNo(orderNo);
				orderList  = httpUtil.getObject(
						Constants.LINK_CREDIT,
						"/credit/order/base/v/selectDetailByOrderNo", orderList,
						OrderListDto.class);
				if(orderList!=null){
					int cooperativeAgencyId = orderList.getCooperativeAgencyId();
					int productId = Integer.parseInt(orderList.getCityCode()+"03");
					map.put("productId", productId);
					map.put("cooperativeAgencyId", cooperativeAgencyId);
				}
			}
			if(MapUtils.getInteger(map, "productId") == null ||MapUtils.getInteger(map, "cooperativeAgencyId")==null){
				status.setData(null);
				status.setMsg("获取固定/关外/其他等费用 等必选参数不全");
				return status;
			}  
			Map<String,Object> poundage = riskGradeService.selectPoundageByProductAndAgencyTypeId(map);
			if(poundage!=null){
				double customsPoundage = Double.parseDouble(poundage.get("poundageFees")==null?"0":poundage.get("poundageFees").toString());
				double otherPoundage = Double.parseDouble(poundage.get("otherFees")==null?"0":poundage.get("otherFees").toString());  
				poundage.put("customsPoundage", customsPoundage);
				poundage.put("otherPoundage", otherPoundage);
			}
			status.setData(poundage);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("获取机构费用失败-->", e);
		}
		return status;
	}
	
	/**
	 * 获取机构类型的费率
	 * @Title: findRate 
	 * @param request
	 * @param productId 业务产品ID
	 * @param agencyTypeId 机构类型ID
	 * @param orderDto 订单对象
	 * @return void
	 */
	@ResponseBody
	@RequestMapping("/findStageRate")
	public RespDataObject<Map<String,Object>> findRate(HttpServletRequest request,@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			if(map.containsKey("riskGradeId") && MapUtils.getIntValue(map, "riskGradeId", 0)<1){
				status.setMsg("收费类型为空，或为其它时不需要执行计算");
				return status;
			}else if(!map.containsKey("productId")){
				if(map.containsKey("cityCode") && map.containsKey("productCode")){
					map.put("productId", MapUtils.getString(map, "cityCode") + MapUtils.getString(map, "productCode"));
				}
			}
			Map<String,Object> poundage = riskGradeService.selectPoundageByProductAndAgencyTypeId(map);
			Map<String,Object> fund = riskGradeService.findStageRate(map);
			if(null == fund){
				fund = new HashMap<String, Object>();
				fund.put("rate", "");
				fund.put("overdueRate", "");
			}
			fund.put("rate", fund.get("rate")==null?"":fund.get("rate")+"");
			fund.put("overdueRate", fund.get("overdueRate")==null?"":fund.get("overdueRate")+"");
			fund.put("customsPoundage", MapUtils.getString(poundage, "poundageFees"));  //关外手续费
			fund.put("otherPoundage",   MapUtils.getString(poundage, "otherFees"));     //其它费用
			fund.put("serviceFees",     MapUtils.getString(poundage, "serviceFees"));   //固定费用
			fund.put("serviceCharge",   MapUtils.getString(poundage, "serviceFees"));   //固定费用，保留(兼容APP老数据)

			//最低收费标准
			double chargeStandard = MapUtils.getDoubleValue(poundage, "chargeStandard", 0);  
			fund.put("chargeStandard",  chargeStandard);
			// 收费金额构建
			double chargeMoneyBase = this.calcChargeMoney(MapUtils.getDoubleValue(map, "loanAmount", 0), MapUtils.getIntValue(map, "borrowingDays", 0), fund);
			double chargeMoneyTotal = chargeMoneyBase + MapUtils.getDoubleValue(fund, "customsPoundage",0) + MapUtils.getDoubleValue(fund, "otherPoundage",0) + MapUtils.getDoubleValue(fund, "serviceFees");	
			fund.put("chargeMoneyBase", chargeMoneyBase); 
			fund.put("chargeMoneyTotalInit", chargeMoneyTotal>chargeStandard ? chargeMoneyTotal : chargeStandard); 
			fund.put("chargeMoney", MapUtils.getDouble(fund, "chargeMoneyTotalInit")); //保留(兼容APP老数据)
			
			status.setData(fund);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("获取机构类型费率失败-->", e);
		}
		return status;
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
	private double calcChargeMoney(double loanAmount, int borrowingDays, Map<String,Object> params){
		double rate = MapUtils.getDoubleValue(params, "rate", 0);//费率
		int modeid = MapUtils.getIntValue(params, "modeid", 1);  //按段，按天		
		
		//基础收费金额 (modeid:0按天[费率*借款金额*借款期限],1按阶段[费率*借款金额 ])
		return NumberUtil.formatDecimal(rate*loanAmount*100 * (1!=modeid ? borrowingDays : 1), 2);   			
	}
	
	/**
	 * app获取费率，收费金额
	 * @Title: findRate 
	 * @param request
	 * @param productId 业务产品ID
	 * @param agencyTypeId 机构类型ID
	 * @param orderDto 订单对象
	 * @return void
	 */
	@ResponseBody
	@RequestMapping("/appFindStageRate")
	public RespDataObject<Map<String,Object>> appFindStageRate(HttpServletRequest request,@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			int isAdd = MapUtils.getIntValue(map, "isAdd");//2:合作机构1：关外，其他
			//风控等级为其他，置空三项
			if(MapUtils.getInteger(map, "riskGradeId")!=null&&MapUtils.getInteger(map, "riskGradeId")==0&&isAdd!=1){
				Map<String,Object> other = new HashMap<String, Object>();
				other.put("chargeMoney", null);
				other.put("rate", null);
				other.put("overdueRate", null);
				other.put("unit", "%/天");
				other.put("customsPoundage", MapUtils.getString(map, "customsPoundage"));
				other.put("otherPoundage", MapUtils.getString(map, "otherPoundage"));
				status.setData(other);
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
				return status;
			}
			//其他，并且更新关外，其他，原样返回
			if(MapUtils.getInteger(map, "riskGradeId")!=null&&MapUtils.getInteger(map, "riskGradeId")==0&&isAdd==1){
				map.put("unit", "%/天");
				status.setData(map);
				map.remove("riskGradeId");
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
				return status;
			}
			String orderNo = MapUtils.getString(map, "orderNo");
			Integer cooperativeAgencyId = MapUtils.getInteger(map, "cooperativeAgencyId");
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			borrow  = httpUtil.getObject(
					Constants.LINK_CREDIT,
					"/credit/order/borrowother/v/queryBorrow", borrow,
					OrderBaseBorrowDto.class);
			if(borrow!=null&&cooperativeAgencyId==null){//畅贷
				map.put("productId", borrow.getCityCode()+"03");
				map.put("cooperativeAgencyId", borrow.getCooperativeAgencyId());
			}else{
				map.put("productId", borrow.getCityCode()+borrow.getProductCode());
			}
			double loanAmount = MapUtils.getDoubleValue(map, "loanAmount");
			Integer borrowingDays = MapUtils.getInteger(map, "borrowingDays")==null?0:MapUtils.getInteger(map, "borrowingDays");
			//关外手续费
			double customsPoundage = MapUtils.getDoubleValue(map, "customsPoundage");
			double otherPoundage = MapUtils.getDoubleValue(map, "otherPoundage");
			//获取机构固定服务费
			Map<String,Object> poundage = riskGradeService.selectPoundageByProductAndAgencyTypeId(map);
			//固定服务费
			double serviceFees = MapUtils.getDoubleValue(poundage, "serviceFees");
			//机构最低标准收费
			double chargeStandard = MapUtils.getDoubleValue(poundage, "chargeStandard");
			Map<String,Object> fund = riskGradeService.findStageRate(map);
			
			//判断按天/按段0:按天(费率*借款金额*借款期限),1:按阶段(费率*借款金额 )精确到2位 +(关外手续费+其他金额+固定费用2017.2.20)
			double rate = MapUtils.getDoubleValue(fund, "rate");
			double overdueRate = MapUtils.getDoubleValue(fund, "overdueRate");
			double chargeMoney=0;
			
			if(fund==null){
				fund = new HashMap<String, Object>();
				fund.put("rate", rate+"");
				fund.put("overdueRate", overdueRate+"");
			}
			//关外手续费
			if(borrow!=null&&isAdd==2){
				customsPoundage = MapUtils.getDoubleValue(poundage, "poundageFees");
				otherPoundage = MapUtils.getDoubleValue(poundage, "otherFees");
			}else{
				customsPoundage = customsPoundage!=0?customsPoundage:MapUtils.getDoubleValue(poundage, "poundageFees");
				otherPoundage = otherPoundage!=0?otherPoundage:MapUtils.getDoubleValue(poundage, "otherFees");
			}
			//畅贷不加关外手续费
			if(cooperativeAgencyId==null){
				customsPoundage=0;
			}
			if(MapUtils.getInteger(fund, "modeid")!=null&&MapUtils.getInteger(fund, "modeid")==1){
				chargeMoney = rate*loanAmount*100+customsPoundage+otherPoundage+serviceFees;
				fund.put("unit", "%");
			}else{
				chargeMoney = rate*loanAmount*borrowingDays*100+customsPoundage+otherPoundage+serviceFees;
				fund.put("unit", "%/天");
			}
			chargeMoney = chargeMoney>chargeStandard?chargeMoney:chargeStandard;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			fund.put("chargeMoney", nf.format(NumberUtil.formatDecimal(chargeMoney, 2))+"");
			fund.put("customsPoundage", nf.format(NumberUtil.formatDecimal(customsPoundage, 2))+"");
			//其他费用
			fund.put("otherPoundage", nf.format(NumberUtil.formatDecimal(otherPoundage, 2))+"");
			fund.put("rate", rate+"");
			fund.put("overdueRate", overdueRate+"");
			status.setData(fund);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取机构类型费率失败-->", e);
		}
		return status;
	}

	/**
	 * 计算收费金额
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/calculationChargeMoney")
	public RespDataObject<Map<String, Object>> calculationChargeMoney(@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			int modeid = MapUtils.getIntValue(params, "modeid",1);//按段，按天
			double rate = MapUtils.getDoubleValue(params, "rate",0);//费率
			double loanAmount = MapUtils.getDoubleValue(params, "loanAmount",0);//金额
			int borrowingDays = MapUtils.getIntValue(params, "borrowingDays",0);//期限
			double customsPoundage = MapUtils.getDoubleValue(params, "customsPoundage",0);//关外手续费
			double otherPoundage = MapUtils.getDoubleValue(params, "otherPoundage",0);//其他费用
			double chargeStandard = MapUtils.getDoubleValue(params, "chargeStandard",0);//最低收费标准
			double chargeMoney=0;//收费金额
			if(modeid == 1){
				chargeMoney = rate*loanAmount*100+customsPoundage+otherPoundage;
			}else{
				chargeMoney = rate*loanAmount*borrowingDays*100+customsPoundage+otherPoundage;
			}
			chargeMoney = chargeMoney>chargeStandard?chargeMoney:chargeStandard;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("chargeMoney", chargeMoney);
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("计算收费金额失败-->", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 根据机构码获取(风控等级)收费类型
	 * @Author KangLG<2017年12月11日>
	 * @param request
	 * @param agencyId
	 * @return
	 */
	@SuppressWarnings("serial")
	@ResponseBody
	@RequestMapping(value="/searchRiskGradeListByAgency_{agencyId}_{productId}")
	public RespDataObject<List<Map<String,Object>>> searchRiskGradeListByAgency(HttpServletRequest request, @PathVariable("agencyId")int agencyId, @PathVariable("productId")int productId){
		RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{			
			List<Map<String,Object>> riskGradeLists = new ArrayList<Map<String,Object>>();
			Map<String,Object> maps = new HashMap<String,Object>();
			// 机构收费类型			
			List<Map<String,Object>> riskGradeList = riskGradeService.searchRiskGradeListByAgency(agencyId>0?agencyId:this.getUserDto(request).getAgencyId(), productId);
			if(null!=riskGradeList && !riskGradeList.isEmpty()){
				List<DictDto> dictDtoList = getDictDtoByType("riskControl");
				for (DictDto dictDto : dictDtoList) {
					for(Map<String,Object> risk : riskGradeList){
						if(dictDto.getCode().equals(String.valueOf(risk.get("riskGradeId")))){
							maps = new HashMap<String, Object>();							
							maps.put("id", dictDto.getCode());
							maps.put("name", dictDto.getName());
							maps.put("riskGradeId", dictDto.getCode());
							riskGradeLists.add(maps);
						}
					}
				}
			}	
			maps = new HashMap<String,Object>(){{
				put("id", 0);
				put("riskGradeId","0");
				put("name","其他");
			}};				
			riskGradeLists.add(maps);
			
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setData(riskGradeLists);
		} catch (Exception e){
			log.error("根据机构码查询风控等级失败==>", e);
		}
		return result;
	}
}
