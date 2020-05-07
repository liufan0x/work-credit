package com.anjbo.controller.tools;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.TransferPrice;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;

/**
 * 过户价/税费
 */
@Controller
@RequestMapping("/tools/transferPrice/v/hz")
public class TransferPriceHzController {
	private static final Log log = LogFactory.getLog(TransferPriceController.class);
	
	/**
	 * 惠州地区税费计算
	 * 
	 * @user hex
	 * @date 2016-11-30 下午01:56:38 
	 * @param request
	 * @param transferPrice
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public RespDataObject<TransferPrice> save(HttpServletRequest request,
			@RequestBody TransferPrice transferPrice) {
		double transactionPrice = transferPrice.getTransactionPrice();
		double area = transferPrice.getArea();
		RespDataObject<TransferPrice> status = new RespDataObject<TransferPrice>();
		if (transactionPrice <= 0 || area <= 0) {
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status = calculation(transferPrice);
		return status;
	}
	
	/**
	 * 税费计算
	 * 
	 * @user hex
	 * @date 2016-11-30 下午01:59:48 
	 * @param transferPrice
	 * @return
	 */
	private RespDataObject<TransferPrice> calculation(
			TransferPrice transferPrice) {
		RespDataObject<TransferPrice> status = new RespDataObject<TransferPrice>();
		int range = transferPrice.getRange();
		double transferPrices = transferPrice.getTransactionPrice(); // 含税成交价格
		double totalTax = 0D;	//税费合计
		if (range == 0) { // 购房期限不满两年
			double valueAddedTax = transferPrices / (1 + 0.05) * 0.05; // 增值税
			double cityBuildTax = valueAddedTax * 0.07; // 城建税
			double educationFees = valueAddedTax * 0.03; // 教育附加税
			double placeEducationFees = valueAddedTax * 0.02; // 地方教育附加税
			transferPrice.setValueAddedTax(valueAddedTax);
			transferPrice.setCityBuildTax(cityBuildTax);
			transferPrice.setEducationFees(educationFees);
			transferPrice.setPlaceEducationFees(placeEducationFees);
			totalTax = valueAddedTax + cityBuildTax + educationFees + placeEducationFees;
		} else {
			transferPrice.setValueAddedTax(0);
			transferPrice.setCityBuildTax(0);
			transferPrice.setEducationFees(0);
			transferPrice.setPlaceEducationFees(0);
		}

		int onlyHouse = transferPrice.getOnlyHouse(); // 是否唯一住房
		if (range == 2 && onlyHouse == 1) { // 购房期限满五年且是唯一住房
			transferPrice.setIncomeTax(0);
		} else {
			double incomeTax = (transferPrices / (1 + 0.05)) * 0.02; // 个税
			transferPrice.setIncomeTax(incomeTax);
			totalTax += incomeTax;
		}

		double area = transferPrice.getArea(); // 房屋面积
		double deedTax = 0D;
		if (transferPrice.getIsFirstHouse() == 0) { // 买方首套
			if (area > 90) { // 90平米以上
				deedTax = (transferPrices / (1 + 0.05)) * 0.015;
			} else { // 90平米以下
				deedTax = (transferPrices / (1 + 0.05)) * 0.01;
			}
		} else if (transferPrice.getIsFirstHouse() == 1) { // 买方两套及以下
			if (area > 90) {
				deedTax = (transferPrices / (1 + 0.05)) * 0.02;
			} else {
				deedTax = (transferPrices / (1 + 0.05)) * 0.01;
			}
		} else { // 买方三套以上
			deedTax = (transferPrices / (1 + 0.05)) * 0.03;
		}
		transferPrice.setDeedTax(deedTax); // 契税
		totalTax += deedTax;
		transferPrice.setTranFees(area * 4); // 交易手续费
		totalTax += (area * 4);
		transferPrice.setTotalTax(totalTax+80+5);
		status.setData(transferPrice);
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
