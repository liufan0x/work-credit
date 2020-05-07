/**
 * 
 */
package com.anjbo.controller.tools;


import com.anjbo.bean.tools.LoanConditionDto;
import com.anjbo.bean.tools.LoanInfo;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.tools.LoanService;
import com.anjbo.service.tools.impl.LoanRateCalcHelper;
import com.anjbo.service.tools.impl.SampleBjLoanFactory;
import com.anjbo.service.tools.impl.SampleBxLoanFactory;
import com.anjbo.utils.NumberUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin Chang
 * 
 */
@Controller
@RequestMapping("/mortgage/loan")
public class LoanCalcController {
	private static final Log log = LogFactory.getLog(LoanCalcController.class);

	/**
	 * 等额本息
	 * 
	 * @param request
	 * @param response
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value = "/samebx")
	public @ResponseBody
	RespDataObject<LoanInfo> samebx(HttpServletRequest request,
									HttpServletResponse response,
									@RequestBody LoanConditionDto loanConditionDto) {
		RespDataObject<LoanInfo> rdObj = new RespDataObject<LoanInfo>();
		if (loanConditionDto == null) {
			rdObj.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			rdObj.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return rdObj;
		}
		log.info("等额本息:"+loanConditionDto.toString());
		LoanService loanService = SampleBxLoanFactory
				.getLoanCalc(loanConditionDto);
		try {
			LoanInfo loanInfo = loanService.calcLoanInfo();
			//万元化
			Double total = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotal()),10000d,2));
			loanInfo.setTotal(total.toString());
			Double totalRefund = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotalRefund()),10000d,2));
			loanInfo.setTotalRefund(totalRefund.toString());
			//Double totalInterest = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotalRefund()),10000d,2));
			Double totalInterest =  NumberUtil.formatDecimal(totalRefund-total,2);
			loanInfo.setTotalInterest(totalInterest.toString());
			loanInfo.setSyRates(loanConditionDto.getSyRates());
			loanInfo.setGjjRates(loanConditionDto.getGjjRates());
			loanInfo.setMonths(loanConditionDto.getMonths());
			rdObj.setData(loanInfo);
			rdObj.setCode(RespStatusEnum.SUCCESS.getCode());
			rdObj.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rdObj.setCode(RespStatusEnum.SYSTEM_ERROR.getCode());
			rdObj.setMsg(RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		return rdObj;
	}
	


	/**
	 * 等额本金
	 * 
	 * @param request
	 * @param response
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value = "/samebj")
	public @ResponseBody
	RespDataObject<LoanInfo> samebj(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody LoanConditionDto loanConditionDto) {
		RespDataObject<LoanInfo> rdObj = new RespDataObject<LoanInfo>();
		if (loanConditionDto == null) {
			rdObj.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			rdObj.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return rdObj;
		}
		log.info("等额本金:"+loanConditionDto.toString());
		LoanService loanService = SampleBjLoanFactory
				.getLoanCalc(loanConditionDto);
		try {
			LoanInfo loanInfo = loanService.calcLoanInfo();
			//万元化
			Double total = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotal()),10000d,2));
			loanInfo.setTotal(total.toString());
			Double totalRefund = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotalRefund()),10000d,2));
			loanInfo.setTotalRefund(totalRefund.toString());
			//Double totalInterest = NumberUtil.formatDecimal(NumberUtil.divide(Double.parseDouble(loanInfo.getTotalRefund()),10000d,2));
			Double totalInterest =  NumberUtil.formatDecimal(totalRefund-total,2);
			loanInfo.setTotalInterest(totalInterest.toString());
			loanInfo.setSyRates(loanConditionDto.getSyRates());
			loanInfo.setGjjRates(loanConditionDto.getGjjRates());
			loanInfo.setMonths(loanConditionDto.getMonths());
			rdObj.setData(loanInfo);
			rdObj.setCode(RespStatusEnum.SUCCESS.getCode());
			rdObj.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			rdObj.setCode(RespStatusEnum.SYSTEM_ERROR.getCode());
			rdObj.setMsg(RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		return rdObj;
	}

	@RequestMapping(value = "/baseData")
	public @ResponseBody
	RespDataObject<Map<String, Object>> baseData(HttpServletRequest request,
			HttpServletResponse response,@RequestBody Map<String,Integer> param) {
		int loanType = MapUtils.getIntValue(param,"loanType");
		RespDataObject<Map<String, Object>> respDataObject = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String[] rates = LoanRateCalcHelper.getRates();
		map.put("rates", rates);
		int year = 30;
		map.put("defYear", year);
		int rateIndex = 2;
		map.put("defRateIndex", rateIndex);
		Double[] values = LoanRateCalcHelper.calcLoanRate(loanType, year,
				rateIndex);
		if (loanType == 1) {// 商贷
			map.put("sd", values[0]);
		} else if (loanType == 2) {// 公积金
			map.put("gjj", values[0]);
		} else if (loanType == 3) {// 综合贷
			map.put("sd", values[0]);
			map.put("gjj", values[1]);
		}
		respDataObject.setData(map);
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}

	@RequestMapping(value = "/calcRate")
	public @ResponseBody
	RespDataObject<Map<String, Double>> calcRate(HttpServletRequest request,
			HttpServletResponse response,@RequestBody Map<String,Integer> param) {
		int loanType = MapUtils.getIntValue(param,"loanType");
		int year = MapUtils.getIntValue(param,"year");
		int rateIndex = MapUtils.getIntValue(param,"rateIndex");
		RespDataObject<Map<String, Double>> respDataObject = new RespDataObject<Map<String, Double>>();
		Map<String,Double> map = new HashMap<String, Double>();
		Double[] values = LoanRateCalcHelper.calcLoanRate(loanType, year,
				rateIndex);
		if (loanType == 1) {// 商贷
			map.put("sd", values[0]);
		} else if (loanType == 2) {// 公积金
			map.put("gjj", values[0]);
		} else if (loanType == 3) {// 综合贷
			map.put("sd", values[0]);
			map.put("gjj", values[1]);
		}
		respDataObject.setData(map);
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}
	
	/***
	 * 贷款成数接口
	 * @return
	 */
	@RequestMapping(value="getLoanPercent")
	@ResponseBody
	public RespDataObject<List<String>> getLoanPercent(HttpServletRequest request,
			HttpServletResponse response,@RequestBody Map<String,Integer> param){
		RespDataObject<List<String>> resp = new RespDataObject<List<String>>();
		//int loanType = param.get("loanType");//贷款类型 若有扩展可用此参数
		List<String> list = new ArrayList<String>();
		list.add("7成");
		list.add("6成");
		list.add("5成");
		list.add("4成");
		list.add("3成");
		list.add("2成");
		list.add("1成");
		resp.setData(list);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/***
	 * 贷款年数接口
	 * @return
	 */
	@RequestMapping(value="getLoanYear")
	@ResponseBody
	public RespDataObject<List<String>> getLoanYear(HttpServletRequest request,
			HttpServletResponse response,@RequestBody Map<String,Integer> param){
		RespDataObject<List<String>> resp = new RespDataObject<List<String>>();
		//int loanType = param.get("loanType");//贷款类型 若有扩展可用此参数
		List<String> list = new ArrayList<String>();
		list.add("30年(360期)");
		list.add("29年(348期)");
		list.add("28年(336期)");
		list.add("27年(324期)");
		list.add("26年(312期)");
		list.add("25年(300期)");
		list.add("24年(288期)");
		list.add("23年(276期)");
		list.add("22年(264期)");
		list.add("21年(252期)");
		list.add("20年(240期)");
		list.add("19年(228期)");
		list.add("18年(216期)");
		list.add("17年(204期)");
		list.add("16年(192期)");
		list.add("15年(180期)");
		list.add("14年(168期)");
		list.add("13年(156期)");
		list.add("12年(144期)");
		list.add("11年(132期)");
		list.add("10年(120期)");
		list.add("9年(108期)");
		list.add("8年(96期)");
		list.add("7年(84期)");
		list.add("6年(72期)");
		list.add("5年(60期)");
		list.add("4年(48期)");
		list.add("3年(36期)");
		list.add("2年(24期)");
		list.add("1年(12期)");
		resp.setData(list);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}



	/**
	 * 获取默认的贷款利率
	 * @Title: defaultRate 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 * RespDataObject<Map<String,Object>>
	 * @throws
	 */
	@RequestMapping(value = "/defaultRate")
	public @ResponseBody
	RespDataObject<Map<String, Object>> defaultRate(HttpServletRequest request,
			HttpServletResponse response,@RequestBody Map<String,Integer> param) {
		int loanType = MapUtils.getIntValue(param,"loanType");
		String yearStr =getLoanYear(request, response, param).getData().get(0);
		int year = NumberUtils.toInt(yearStr.substring(0,yearStr.indexOf("年")),30);
		RespDataObject<Map<String, Object>> respDataObject = new RespDataObject<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("year",yearStr);
		map.put("percentage",getLoanPercent(request, response, param).getData().get(0));
		map.put("rateContent",LoanRateCalcHelper.getRates()[0]);
		Double[] values = LoanRateCalcHelper.calcLoanRate(loanType, year,0);
		if (loanType == 1) {// 商贷
			map.put("sd", values[0]);
		} else if (loanType == 2) {// 公积金
			map.put("gjj", values[0]);
		} else if (loanType == 3) {// 综合贷
			map.put("sd", values[0]);
			map.put("gjj", values[1]);
		}
		respDataObject.setData(map);
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}

	public static void main(String[] args) {
		System.out.println(NumberUtil.divide(10200d,111d,2));
	}
}
