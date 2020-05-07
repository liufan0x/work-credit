package com.anjbo.controller.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.OrderReceivablleReportVo;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IReceivableReportController;
import com.anjbo.controller.ReportBaseController;
import com.anjbo.service.ReceivableReportService;

/**
 * 订单总体概况
 * @author yis
 *
 */
@RestController
public class ReceivableReportController extends ReportBaseController implements IReceivableReportController{

	@Resource ReceivableReportService receivableReportService;
	
	@Override
	public   RespPageData<OrderReceivablleReportVo> query(@RequestBody Map<String,Object> paramMap){
		RespPageData<OrderReceivablleReportVo> resp = new RespPageData<OrderReceivablleReportVo>();
		try {
			List<OrderReceivablleReportVo> list=receivableReportService.findByAll(paramMap);
			List<OrderReceivablleReportVo> list2=new ArrayList<OrderReceivablleReportVo>();
			if(list !=null && list.size()>0){
				OrderReceivablleReportVo reportVo=new OrderReceivablleReportVo();
				reportVo.setCityName("总计");
				int receivableCount =0;//正常回款订单（笔）
				int overdueCount =0;//逾期回款订单
				int lendingCount =0;//已放款订单
				int notLendingCount =0;//未出款订单
				int notOverdueCount =0;//逾期未回款笔数
				int notReceivableCount=0;//正常未回款订单
				BigDecimal overdueAmount =new BigDecimal(0);;//逾期回款金额
				BigDecimal receivableAmount=new BigDecimal(0);//正常回款金额（万）
				BigDecimal lendingAmount=new BigDecimal(0);;//已放款金额
				BigDecimal notLendingAmount=new BigDecimal(0);;//未出款金额
				BigDecimal notReceivableAmount=new BigDecimal(0);;//正常未回款金额
				BigDecimal notOverdueAmount=new BigDecimal(0);;//逾期未回款金额
				BigDecimal notReceivableAmountSum=new BigDecimal(0);;//总未回款金额
				BigDecimal overdueAmountRatio=new BigDecimal(0);;//逾期未回款金额占比
				BigDecimal overdueAmountRatioByCount=new BigDecimal(0);;//逾期占总未回款比
//				BigDecimal overdueAmountRatioBySum=new BigDecimal(0);;//逾期占总逾期比
				int notReceivableSum =0;//总未回款订单
				int overdueNumber =0;//逾期数量
				BigDecimal overdueDay=new BigDecimal(0);//平均逾期天数
				int overdueBigDay =0;//逾期最大天数
				int overDayCount=0;
//				DecimalFormat decimalFormat=new DecimalFormat("0.00");
				String cityCode=paramMap.get("cityCode")+"";
				for(OrderReceivablleReportVo r:list){
					if(null!=cityCode && !"".equals(cityCode) && !"null".equals(cityCode)){  //重新计算逾期未回款额占比/逾期占总未回款比 /逾期未回款额占总逾期未回款额比 
						try {
							r.setOverdueAmountRatio(r.getNotOverdueAmount().divide(r.getNotReceivableAmountSum(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e2) {
							r.setOverdueAmountRatio(BigDecimal.valueOf(0));
						}
						try {
							r.setOverdueAmountRatioByCount(r.getNotOverdueAmount().divide(r.getNotReceivableAmountSum(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e1) {
							r.setOverdueAmountRatioByCount(BigDecimal.valueOf(0));
						}
						try {
							r.setOverdueAmountRatioBySum(r.getNotOverdueAmount().divide(r.getNotOverdueAmount(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e) {
							r.setOverdueAmountRatioBySum(BigDecimal.valueOf(0));
						}
					}
					receivableCount+=r.getReceivableCount();
					overdueCount+=r.getOverdueCount();
					lendingCount+=r.getLendingCount();
					notLendingCount+=r.getNotLendingCount();
					notOverdueCount+=r.getNotOverdueCount();
					notReceivableCount+=r.getNotReceivableCount();
//					overdueAmount+=decimalFormat.format(r.getOverdueAmount());
					overdueAmount=overdueAmount.add(r.getOverdueAmount());
					receivableAmount=receivableAmount.add(r.getReceivableAmount());
					lendingAmount=lendingAmount.add(r.getLendingAmount());
					notLendingAmount=notLendingAmount.add(r.getNotLendingAmount());
					notReceivableAmount=notReceivableAmount.add(r.getNotReceivableAmount());
					notOverdueAmount=notOverdueAmount.add(r.getNotOverdueAmount());
					notReceivableAmountSum=notReceivableAmountSum.add(r.getNotReceivableAmountSum());
//					overdueAmountRatio=overdueAmountRatio.add(r.getOverdueAmountRatio());  //逾期未回款额占比
//					overdueAmountRatioByCount=overdueAmountRatioByCount.add(r.getOverdueAmountRatioByCount());   //逾期占总未回款比
//					overdueAmountRatioBySum=overdueAmountRatioBySum.add(r.getOverdueAmountRatioBySum());  //逾期未回款额占总逾期未回款额比 
					notReceivableSum+=r.getNotReceivableSum();
					overdueNumber+=r.getOverdueNumber();
					overDayCount+=r.getOverDayCount();
					if(r.getOverdueBigDay()>overdueBigDay){
						overdueBigDay=r.getOverdueBigDay();
					}
					overdueDay = overdueDay.add(r.getOverdueDay());
				}
				reportVo.setReceivableCount(receivableCount);
				reportVo.setOverdueCount(overdueCount);
			    reportVo.setLendingCount(lendingCount);
		        reportVo.setNotLendingCount(notLendingCount);
		        reportVo.setNotOverdueCount(notOverdueCount);
		        reportVo.setNotReceivableCount(notReceivableCount);
		        reportVo.setOverdueAmount(overdueAmount);
		        reportVo.setReceivableAmount(receivableAmount);
		        reportVo.setLendingAmount(lendingAmount);
		        reportVo.setNotLendingAmount(notLendingAmount);
		        reportVo.setNotReceivableAmount(notReceivableAmount);
		        reportVo.setNotOverdueAmount(notOverdueAmount);
		        reportVo.setNotReceivableAmountSum(notReceivableAmountSum);
		        reportVo.setOverdueAmountRatio(notOverdueAmount.divide(notReceivableAmountSum,3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));//逾期未回款额占比
//		        reportVo.setOverdueAmountRatioByCount(overdueAmountRatioByCount);
		        reportVo.setOverdueAmountRatioByCount(notOverdueAmount.divide(notReceivableAmountSum,3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		        reportVo.setOverdueAmountRatioBySum(notOverdueAmount.divide(notOverdueAmount,3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			    reportVo.setNotReceivableSum(notReceivableSum);
			    reportVo.setOverdueNumber(overdueNumber);
			    if(null!=cityCode && !"".equals(cityCode) && !"null".equals(cityCode)){  //重新计算逾期未回款额占比/逾期占总未回款比 /逾期未回款额占总逾期未回款额比
			    	reportVo.setOverdueDay(overdueDay);
			    }else{
			    	DecimalFormat  df = new DecimalFormat("######0.0"); 
				    if(overDayCount==0 || overdueNumber==0){
				    	reportVo.setOverdueDay(new BigDecimal(0));
				    }else{
					    String day=df.format((float)overDayCount/overdueNumber);
						reportVo.setOverdueDay(BigDecimal.valueOf(Double.parseDouble(day)));
				    }
			    }
				reportVo.setOverdueBigDay(overdueBigDay);
				if(null==cityCode || "".equals(cityCode) || "null".equals(cityCode)){ //计算逾期未回款额占比/逾期占总未回款比 /逾期未回款额占总逾期未回款额比 
					for(OrderReceivablleReportVo r:list){
						try {
							r.setOverdueAmountRatio(r.getNotOverdueAmount().divide(r.getNotReceivableAmountSum(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e) {
							r.setOverdueAmountRatio(BigDecimal.valueOf(0));
						}
						try {
							r.setOverdueAmountRatioByCount(r.getNotOverdueAmount().divide(reportVo.getNotReceivableAmountSum(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e) {
							r.setOverdueAmountRatioByCount(BigDecimal.valueOf(0));
						}
						try {
							r.setOverdueAmountRatioBySum(r.getNotOverdueAmount().divide(reportVo.getNotOverdueAmount(),3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
						} catch (Exception e) {
							r.setOverdueAmountRatioBySum(BigDecimal.valueOf(0));
						}
					}
				}
				list2.add(reportVo);
				list2.addAll(list);
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(list2);
			resp.setTotal(list.size());
		} catch (Exception e){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			logger.error("订单风控统计异常:",e);
		}
		return resp;
	}
}
