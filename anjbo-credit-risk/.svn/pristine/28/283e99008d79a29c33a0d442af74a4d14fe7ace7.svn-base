package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.FirstForeclosureAuditDto;
import com.anjbo.bean.risk.FirstPaymentAuditDto;
import com.anjbo.bean.risk.RiskModelConfigDto;
import com.anjbo.common.Enums;
import com.anjbo.dao.FirstAuditMapper;
import com.anjbo.service.CreditService;
import com.anjbo.service.FirstAuditService;
import com.anjbo.service.RiskModelConfigService;
import com.anjbo.utils.HttpUtil;

/**
 * 初审
 * @author huanglj
 *
 */
@Service
public class FirstAuditServiceImpl implements FirstAuditService{

	@Resource
	private FirstAuditMapper firstAuditMapper;
	
	public FirstAuditDto detail(String orderNo){
		FirstAuditDto obj = firstAuditMapper.detail(orderNo);
		return null==obj?new FirstAuditDto():obj;
	}
	@Resource
	private CreditService creditService;
	
	@Resource
	private RiskModelConfigService riskModelConfigService;
	
	@Transactional
	public int update(FirstAuditDto obj){
		return firstAuditMapper.update(obj);
	}
	@Transactional
	public int insert(FirstAuditDto obj){
		int success = 0;
		FirstAuditDto tmp = firstAuditMapper.detail(obj.getOrderNo());
		if(null==tmp){
			success = firstAuditMapper.insert(obj);
		} else {
			success = firstAuditMapper.update(obj);
		}
		return success;
	}
	
	/**
	 * 风控模型
	 * @param orderNo
	 * @return
	 */
	public Map<String, Boolean> getCreditFlagMap(String orderNo) {
		CreditDto creditdto = creditService.detail(orderNo);
		
		// 查询风控模型，传入x的值，计算是否显示初审，终审按钮
		CreditDto creditDto = new CreditDto();
		creditDto.setStart(0);		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("start", 0);
		param.put("pageSize",1000);
		List<RiskModelConfigDto> creditDtos = new ArrayList<RiskModelConfigDto>();
		List<RiskModelConfigDto> list = riskModelConfigService.selectCreditList(param);
		
		for (RiskModelConfigDto credit : list) {// 暂时12个没有机构评级
			if (Enums.WindControlEnum.LOAN_AMOUNT.getName().equals(
					credit.getProject())) {
				credit.setX(String.valueOf(creditdto.getLoanAmount()));
			} else if (Enums.WindControlEnum.NUMBER_OF_BORROWINGS.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getLoanPercentage()));
			} else if (Enums.WindControlEnum.MONEY_LATE_TIMES.getName().equals(
					credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getLatelyTwoYearMoneyOverdueNumber()));
			} else if (Enums.WindControlEnum.CREDIT_LATE_TIMES.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getCreditOverdueNumber()));
			} else if (Enums.WindControlEnum.CARD_OVERDRAFT_AMOUNT.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getCreditCardOverdraft()));
			} else if (Enums.WindControlEnum.CREDIT_QUERY_TIMES.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getLatelyHalfYearSelectNumber()));
			} else if (Enums.WindControlEnum.ORIGINAL_LOAN_BANK.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getOldLoanIsBank()));
			} else if (Enums.WindControlEnum.NEW_LOAN_BANK.getName().equals(
					credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getNewLoanIsBank()));
			} else if (Enums.WindControlEnum.IS_LITIGATION.getName().equals(
					credit.getProject())) {
				if(creditdto.getIsLitigation()==null){
					credit.setX("否");
				}else{
					if(creditdto.getIsLitigation()==1){
						credit.setX("否");
					}else{
						credit.setX("是");
					}
				}
			} else if (Enums.WindControlEnum.PROPERTY_MORTGAGE.getName()
					.equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getPropertyMortgage()));
			}  else if (Enums.WindControlEnum.IS_COMPANY_PROPERTY_RIGHT
					.getName().equals(credit.getProject())) {
				credit.setX(String.valueOf(creditdto
						.getIsCompanyProperty()));
			}
			if(credit.getX()!=null&&!credit.getX().equals("null")){
				creditDtos.add(credit);
			}
		}
		Map<String, Boolean> creditFlagMap = calculationCredit(creditDtos);
		return creditFlagMap;
	}
	
	public Map<String, Boolean> calculationCredit(List<RiskModelConfigDto> creditDtos) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("auditFirst", true);
		map.put("auditFinal", true);
		if(creditDtos != null && creditDtos.size() > 0){
			
			for (RiskModelConfigDto creditDto : creditDtos) {
				String x = creditDto.getX();
				String auditFirstExpression = creditDto.getAuditFirstExpression();
				String auditFinalExpression = creditDto.getAuditFinalExpression();
				String auditFirstResult = creditDto.getAuditFirstResult();
				String auditFinalResult = creditDto.getAuditFinalResult();
				int expression = creditDto.getExpression();
				if(expression == 1){
					
					if(!calculationExpression(auditFirstExpression,x,auditFirstResult)){
						map.put("auditFirst", false);
					}
					
					if(!calculationExpression(auditFinalExpression,x,auditFinalResult)){
						map.put("auditFinal", false);
					}
					
				}else if(expression == 2){
				
					if(!auditFirstResult.equals("不限")){
						if(!auditFirstResult.equals(x)){
							map.put("auditFirst", false);
						}
					}
					
 
					if(!"不限".equals(auditFinalResult)){
						if(auditFinalResult !=null &&!auditFinalResult.equals(x)){
							map.put("auditFinal", false);
						}
					}
				}else if(expression == 3){

					if((auditFirstResult.toCharArray()[0] < x.toCharArray()[0])){
							map.put("auditFirst", false);
					}
					
					if((auditFinalResult.toCharArray()[0] < x.toCharArray()[0])){
							map.put("auditFinal", false);
					}
				}
			}
			
		}else{
			map.put("auditFirst", false);
			map.put("auditFinal", false);
		}
		return map;
	}
	
	/**
	 * 计算表达式
	 * @param Expression
	 * @param x
	 * @param result
	 * @return
	 */
	private boolean calculationExpression(String expression ,String x ,String result){
		String[] results = result.split(",");
		String[] expressions = expression.split("X");
		
		boolean fl = true;
		
		
		//x处于中间
		if(expressions.length > 1){
			
			if(expressions[0].contains("＜")){
				
				fl = Integer.valueOf(results[0]) < Double.valueOf(x) ;
				
			}
			
			if(expressions[0].contains("≤")){

				fl = Integer.valueOf(results[0]) <= Double.valueOf(x) ;
				
			}
			
			if(expressions[1].contains("＜")){
				
				fl = Double.valueOf(x) < Integer.valueOf(results[1])  ;
				
			}
			
			if(expressions[1].contains("≤")){

				fl = Double.valueOf(x) <= Integer.valueOf(results[1])   ;
				
			}
			
		}else{
			
			if(expression.contains("＜")){
				
				fl = Integer.valueOf(result) < Double.valueOf(x) ;
				
			}
			
			if(expression.contains("≤")){

				fl = Integer.valueOf(result) <= Double.valueOf(x) ;
				
			}
			
			if(expression.contains("=")){

				fl = Double.valueOf(result) == Double.valueOf(x) ;
				
			}
			
		}
		
		return fl;
	}
	
	public List<StatisticsDto> listByTime(Map<String,Object> map){
		return firstAuditMapper.listByTime(map);
	}
	
	public int listByTimeCount(Map<String,Object> map){
		return firstAuditMapper.listByTimeCount(map);
	}
	@Override
	public List<FirstForeclosureAuditDto> findforeclosureList(String orderNo) {
		// TODO Auto-generated method stub
		return firstAuditMapper.findforeclosureList(orderNo);
	}
	@Override
	public int addFirstForeclosure(FirstForeclosureAuditDto foreclosureAuditDto) {
		// TODO Auto-generated method stub
		return firstAuditMapper.addFirstForeclosure(foreclosureAuditDto);
	}
	@Override
	public int delFirstForeclosure(String orderNo) {
		// TODO Auto-generated method stub
		return firstAuditMapper.delFirstForeclosure(orderNo);
	}
	@Override
	public List<FirstPaymentAuditDto> findPaymentList(String orderNo) {
		// TODO Auto-generated method stub
		return firstAuditMapper.findPaymentList(orderNo);
	}
	@Override
	public int addFirstPayment(FirstPaymentAuditDto firstPaymentAuditDto) {
		// TODO Auto-generated method stub
		return firstAuditMapper.addFirstPayment(firstPaymentAuditDto);
	}
	@Override
	public int delFirstPayment(String orderNo) {
		// TODO Auto-generated method stub
		return firstAuditMapper.delFirstPayment(orderNo);
	}
}
