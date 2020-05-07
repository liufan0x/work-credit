
package com.anjbo.controller.tools;
import java.util.HashMap;
import java.util.Map;

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

import com.anjbo.bean.tools.CalcApr;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.Arith;
import com.anjbo.utils.MapAndBean;

/**
 * 计算器
 * @author jiangyq
 *
 * @date 2017年11月13日 上午11:45:55
 */
@Controller
@RequestMapping("/tools/calculate")
public class CalculateController {
	
	private static final Log log = LogFactory.getLog(CalculateController.class);

	
	/**
	 * 年化利率计算
	 * 
	 * @user jiangyq
	 * @date 2017年11月13日
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/aprCalculate")
	@ResponseBody
	public RespDataObject<Map<String, Object>> aprCalculate(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody CalcApr calcApr) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		
		//校验参数
		RespStatus rs = validateParams(calcApr);
		if (RespStatusEnum.FAIL.getCode().equals(rs.getCode())) {
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(rs.getMsg());
			return status;
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			
			double apr = getApr(calcApr);
			
			retMap.put("apr", apr);
			status.setData(retMap);
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("年利率计算异常", e);
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			status.setCode(RespStatusEnum.FAIL.getCode());
		}
		return status;
	}

	/**
	 * 计算年利率
	 * 
	 * @user jiangyq
	 * @date 2017年11月13日
	 * @param calcApr
	 * @return 单位（%）
	 */
	private double getApr(CalcApr calcApr) {
		double apr = 0D;
		
		/** 计算类型1：银行贷款，2：固定费率 */
		int type = calcApr.getType();
		/** 贷款期限（分期数） */
		int period = calcApr.getPeriod();
		
		if (type == 1) {
			/** 贷款总额 */
			double loanAmount = Arith.mul(calcApr.getLoanAmount(), 10000);
			/** 贷款年利率 */
			double loanApr = Arith.mul(calcApr.getLoanApr(), 0.01);
			/** 其他费用 */
			double otherFee = calcApr.getOtherFee();
			/** 是否回存 */
			int isBack = calcApr.getIsBack();
			/** 回存总额 */
			double backAmount = Arith.mul(calcApr.getBackAmount(), 10000);
			/** 回存利率 */
			double backApr = Arith.mul(calcApr.getBackApr(), 0.01);
			/** 回存期限 */
			int backPeriod = calcApr.getBackPeriod();
			
			if (isBack == 0) {
				//年利率=[（贷款总额*贷款年利率*贷款期限/12+其他费用）/贷款总额]*(12/贷款期限)
				apr = Arith.div(Arith.add(Arith.mul(Arith.mul(loanAmount, loanApr), period), Arith.mul(otherFee, 12)), Arith.mul(loanAmount, period), 4);
			} else {
				//年利率={[贷款总额*贷款年利率*贷款期限/12+其他费用-（回存总额*存款年利率*存款期限/12）]/(贷款总额-回存总额）}*(12/贷款期限)
				apr = Arith.div(Arith.add(Arith.sub(Arith.mul(Arith.mul(loanAmount, loanApr), period), Arith.mul(Arith.mul(backAmount, backApr), backPeriod)), Arith.mul(otherFee, 12)), Arith.mul(Arith.sub(loanAmount, backAmount), period), 4);
			}
			
		} else if (type == 2) {
			/** 单期手续费（%） */
			double singleFee = Arith.mul(calcApr.getSingleFee(), 0.01);
			//年利率 = 单期手续费率*分期数/（分期数+1）*24
			apr = Arith.div(Arith.mul(Arith.mul(singleFee, period), 24), Arith.add(period, 1), 4);
		}
		
		apr = Arith.mul(apr, 100);
		
		return apr;
	}

	/**
	 * 校验提交参数
	 * 
	 * @user Administrator
	 * @date 2017年11月13日 下午1:47:14 
	 * @param calcApr
	 * @return
	 */
	private RespStatus validateParams(CalcApr calcApr) {
		RespStatus rs = new RespStatus();
		rs.setCode(RespStatusEnum.FAIL.getCode());
		
		if (calcApr == null || (calcApr.getType() != 1 && calcApr.getType() != 2)) {
			rs.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return rs;
		}
		//银行贷款利率计算
		if (calcApr.getType() == 1) {
			
			if (calcApr.getLoanAmount() <= 0) {
				rs.setMsg("请输入贷款总额");
				return rs;
			}
			
			if (calcApr.getLoanApr() <= 0) {
				rs.setMsg("请输入贷款年利率");
				return rs;
			}
			
			if (calcApr.getPeriod() <= 0) {
				rs.setMsg("请选择贷款期限");
				return rs;
			}
			
			if (calcApr.getIsBack() == 1) {
				
				if (calcApr.getBackAmount() <= 0 || calcApr.getBackAmount() >= calcApr.getLoanAmount()) {
					rs.setMsg("回存总额有误");
					return rs;
				}
				
				if (calcApr.getBackApr() <= 0) {
					rs.setMsg("请输入回存年利率");
					return rs;
				}
				
				if (calcApr.getBackPeriod() <= 0) {
					rs.setMsg("请选择回存期限");
					return rs;
				}
			}
			
		} else if (calcApr.getType() == 2) {
			//固定费率计算
			if (calcApr.getSingleFee() <= 0) {
				rs.setMsg("请输入单期手续费率");
				return rs;
			}
			
			if (calcApr.getPeriod() <= 0) {
				rs.setMsg("请选择分期数");
				return rs;
			}
			
		} else {
			
			rs.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return rs;
		}
		
		rs.setCode(RespStatusEnum.SUCCESS.getCode());
		return rs;
	}
	
	
}
