package com.anjbo.service.fc.order.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.fc.order.OrderLendingStatistics;
import com.anjbo.dao.fc.order.LendingStatisticsMapper;
import com.anjbo.service.fc.order.LendingStatisticsService;
import com.anjbo.utils.StringUtil;

/**
 * 放款统计
 * @author admin
 *
 */
@Service("lendingStatisticsService")
public class LendingStatisticsServiceImpl implements LendingStatisticsService {

	@Resource
	private LendingStatisticsMapper lendingStatisticsMapper;
	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lendingStartTime", date+" 00:00:00");
		map.put("lendingEndTime", date+" 23:59:59");
		List<OrderLendingStatistics> lendingList = lendingStatisticsMapper.selectLendingOrders(map);
		if(lendingList!=null&&lendingList.size()>0){
			for (OrderLendingStatistics orderLendingStatistics : lendingList) {
				double income = 0;
				if(orderLendingStatistics.getInterest()!=null){
					income+=orderLendingStatistics.getInterest();
				}
				if(orderLendingStatistics.getFine()!=null){
					income+=orderLendingStatistics.getFine();
				}
				if(orderLendingStatistics.getServiceCharge()!=null){
					income+=orderLendingStatistics.getServiceCharge();
				}
				if(orderLendingStatistics.getCustomsPoundage()!=null){
					income+=orderLendingStatistics.getCustomsPoundage();
				}
				if(orderLendingStatistics.getOtherPoundage()!=null){
					income+=orderLendingStatistics.getOtherPoundage();
				}
				orderLendingStatistics.setIncome(income);
				//设置原贷款银行，支行
				if(StringUtil.isNotBlank(orderLendingStatistics.getOldLoanBankName())&&StringUtil.isNotBlank(orderLendingStatistics.getOldLoanBankSubName())){
					orderLendingStatistics.setOldLoanBankAndSub(orderLendingStatistics.getOldLoanBankName()+"-"+orderLendingStatistics.getOldLoanBankSubName());
				}
				//设置新贷款银行，支行
				if(StringUtil.isNotBlank(orderLendingStatistics.getLoanBankName())&&StringUtil.isNotBlank(orderLendingStatistics.getLoanBankSubName())){
					orderLendingStatistics.setNewLoanBankAndSub(orderLendingStatistics.getLoanBankName()+"-"+orderLendingStatistics.getLoanBankSubName());
				}
			}
			lendingStatisticsMapper.batchInsertLendingOrders(lendingList);
		}
	}
	
	@Override
	public void init(Map<String,Object> map) {
		List<OrderLendingStatistics> lendingList = lendingStatisticsMapper.selectLendingOrders(map);
		if(lendingList!=null&&lendingList.size()>0){
			for (OrderLendingStatistics orderLendingStatistics : lendingList) {
				//计算创收
				double income = 0;
				if(orderLendingStatistics.getInterest()!=null){
					income+=orderLendingStatistics.getInterest();
				}
				if(orderLendingStatistics.getFine()!=null){
					income+=orderLendingStatistics.getFine();
				}
				if(orderLendingStatistics.getServiceCharge()!=null){
					income+=orderLendingStatistics.getServiceCharge();
				}
				if(orderLendingStatistics.getCustomsPoundage()!=null){
					income+=orderLendingStatistics.getCustomsPoundage();
				}
				if(orderLendingStatistics.getOtherPoundage()!=null){
					income+=orderLendingStatistics.getOtherPoundage();
				}
				orderLendingStatistics.setIncome(income);
				//设置原贷款银行，支行
				if(StringUtil.isNotBlank(orderLendingStatistics.getOldLoanBankName())&&StringUtil.isNotBlank(orderLendingStatistics.getOldLoanBankSubName())){
					orderLendingStatistics.setOldLoanBankAndSub(orderLendingStatistics.getOldLoanBankName()+"-"+orderLendingStatistics.getOldLoanBankSubName());
				}
				//设置新贷款银行，支行
				if(StringUtil.isNotBlank(orderLendingStatistics.getLoanBankName())&&StringUtil.isNotBlank(orderLendingStatistics.getLoanBankSubName())){
					orderLendingStatistics.setNewLoanBankAndSub(orderLendingStatistics.getLoanBankName()+"-"+orderLendingStatistics.getLoanBankSubName());
				}
			}
			lendingStatisticsMapper.batchInsertLendingOrders(lendingList);
		}
	}

}
