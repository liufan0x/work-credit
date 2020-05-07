package com.anjbo.service.fc.order.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.DictDto;
import com.anjbo.dao.fc.order.ReceivableReportMapper;
import com.anjbo.service.fc.order.ReceivableReportService;

@Service
public class ReceivableReportServiceImpl implements ReceivableReportService {

	@Resource ReceivableReportMapper receivableReportMapper;
	
	/**
	 * 计算城市概况
	 */
	@Override
	public void runJob() {
		List<DictDto> cityList=receivableReportMapper.findByCity("cityList"); //所以城市
		List<DictDto> productList=receivableReportMapper.findByCity("product"); //所以产品
		for(DictDto city:cityList){
			for(DictDto product:productList){
					Map<String, Object> reportVo=new HashMap<String, Object>();
					reportVo.put("createTime", new Date());
					reportVo.put("cityCode", city.getCode());
					reportVo.put("cityName", city.getName());
					reportVo.put("productCode", product.getCode());
					reportVo.put("productName", product.getName());
					//条件查询
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("cityCode", city.getCode());
					map.put("productCode", product.getCode());
					
					//正常回款订单笔数与金额
					Map<String, Object> has= receivableReportMapper.findByreceivableHas(map);
					reportVo.put("receivableCount",has.get("orderCount"));
					reportVo.put("receivableAmount", has.get("orderAmountSum"));
					
					int overdueCount=0;  //逾期回款订单笔数
					int notOverdueCount=0; //逾期未回款订单笔数
					int overDay =0; //总逾期天数
					int overdueBigDay =0; //逾期最大天数
					//逾期回款订单笔数与金额
					Map<String, Object> overdue=receivableReportMapper.findByOverHas(map);
					reportVo.put("overdueCount", overdue.get("orderCount"));
					overdueCount=Integer.parseInt(overdue.get("orderCount").toString());
					reportVo.put("overdueAmount", overdue.get("orderAmountSum"));
					int overDays=Integer.parseInt(overdue.get("overDay").toString());
					overDay = Math.abs(overDays);
					reportVo.put("overDayCount", overDay);  //逾期总天数
					int minDays=Integer.parseInt(overdue.get("minDay").toString());
					overdueBigDay =Math.abs(minDays);
					
					//未出款订单笔数
					Map<String, Object> notLending=receivableReportMapper.findByNotLending(map);
					reportVo.put("notLendingCount", notLending.get("orderCount"));
					reportVo.put("notLendingAmount", notLending.get("orderAmountSum"));
					
					//已放款订单笔数
					Map<String, Object> lending=receivableReportMapper.findByLending(map);
					reportVo.put("lendingCount", lending.get("orderCount"));
					reportVo.put("lendingAmount", lending.get("orderAmountSum"));
					
					int notReceivableSum=0;//总未回款订单
					BigDecimal notReceivableAmountSum=new BigDecimal(0);//总未回款金额
					//正常未回款
					map.put("forType", "1");
					Map<String, Object> notReceivable=receivableReportMapper.findByNotReceivable(map);
					reportVo.put("notReceivableCount", notReceivable.get("orderCount"));
					notReceivableSum+=Integer.parseInt(notReceivable.get("orderCount").toString());
					reportVo.put("notReceivableAmount", notReceivable.get("orderAmountSum"));
					double rAmount=Double.parseDouble(notReceivable.get("orderAmountSum").toString());
					notReceivableAmountSum=notReceivableAmountSum.add(BigDecimal.valueOf(rAmount)); //总未回款金额 = 正常未回款+逾期未回款
					
					double amount=0; //逾期未回款金额
					//逾期未回款
					map.put("forType", "2");
					Map<String, Object> overdueReceivable=receivableReportMapper.findByNotReceivable(map);
					reportVo.put("notOverdueCount", overdueReceivable.get("orderCount"));
					notReceivableSum+=Integer.parseInt(overdueReceivable.get("orderCount").toString());
					reportVo.put("notOverdueAmount", overdueReceivable.get("orderAmountSum"));
					amount=Double.parseDouble(overdueReceivable.get("orderAmountSum").toString());
					notReceivableAmountSum=notReceivableAmountSum.add(BigDecimal.valueOf(amount)); //分公司总未回款金额 = 正常未回款+逾期未回款 （分产品）
					notOverdueCount=Integer.parseInt(overdueReceivable.get("orderCount").toString());//逾期未回款订单笔数  = 逾期未回款总数
					
					//总未回款订单（正常未回款+逾期未回款）
					reportVo.put("notReceivableSum", notReceivableSum);
					reportVo.put("notReceivableAmountSum", notReceivableAmountSum);
					
					//逾期未回款占比（城市）（分公司逾期未回款金额/分公司总未回款金额）
					BigDecimal overAmount=BigDecimal.valueOf(amount); //逾期未回款金额
					BigDecimal overdueAmountRatio=new BigDecimal(0);
					try {
						map.put("forType", "3");
						map.put("productCode", "null");
						Map<String, Object> notReceivableCount=receivableReportMapper.findByNotReceivable(map);
						double sum=Double.parseDouble(notReceivableCount.get("orderAmountSum").toString());
						BigDecimal bigDecimal=BigDecimal.valueOf(sum); //分公司总为回款金额 （不分产品）
						if(overAmount!=BigDecimal.valueOf(0.0)){
							overdueAmountRatio=overAmount.divide(bigDecimal,3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
						}
						System.out.println("城市"+city.getName()+",产品："+product.getName()+",逾期未回款占比: 逾期未回款金额："+overAmount +",未回款总金额："+bigDecimal+"等于："+overdueAmountRatio);
					} catch (Exception e) {
						overdueAmountRatio=BigDecimal.valueOf(0);
					}
					reportVo.put("overdueAmountRatio", overdueAmountRatio);
					
					map.put("productCode", product.getCode());
					//逾期占总未回款比(全国)(分公司逾期未回款金额/全国未回款总金额)
					BigDecimal overdueAmountRatioByCount=new BigDecimal(0);
					Map<String, Object> overSumMap=receivableReportMapper.findByNotReceivableAll(map);
					if(overdueReceivable!=null && overdueReceivable.size()>0){
						String orderAmountSum=overSumMap.get("orderAmountSum")+"";
						if(orderAmountSum==null || "null".equals(orderAmountSum)){
							orderAmountSum="0.0";
						}
						double overSum=Double.parseDouble(orderAmountSum);
						BigDecimal sum=BigDecimal.valueOf(overSum);
						try {
							overdueAmountRatioByCount=overAmount.divide(sum,3,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
						} catch (Exception e) {
							overdueAmountRatioByCount=BigDecimal.valueOf(0);
						}
						reportVo.put("overdueAmountRatioByCount", overdueAmountRatioByCount);
						System.out.println("城市"+city.getName()+",产品："+product.getName()+",逾期占总未回款比(全国): 逾期未回款金额："+overAmount +",未回款总（全国）金额："+sum+"等于："+overdueAmountRatioByCount);
					}else{
						reportVo.put("overdueAmountRatioByCount", 0);
					}
					
					//逾期未回款额占总逾期未回款额比（全国）
					BigDecimal overdueAmountRatioBySum=new BigDecimal(0);
					Map<String, Object> notOverAllMap=receivableReportMapper.findByNotOverAll(map);
					if(notOverAllMap!=null && notOverAllMap.size()>0){
						String orderAmountSum=notOverAllMap.get("orderAmountSum")+"";
						if(orderAmountSum==null || "null".equals(orderAmountSum)){
							orderAmountSum="0.0";
						}
						double notOverSum=Double.parseDouble(orderAmountSum);
						try {
							overdueAmountRatioBySum=overAmount.divide(BigDecimal.valueOf(notOverSum)).multiply(BigDecimal.valueOf(100)).setScale(3,BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							overdueAmountRatioBySum=BigDecimal.valueOf(0);
						}
						reportVo.put("overdueAmountRatioBySum", overdueAmountRatioBySum);
					}else{
						reportVo.put("overdueAmountRatioBySum", 0);
					}
					
					//逾期数量 （逾期已回款订单+逾期未回款）
					int overdueNumber =overdueCount+notOverdueCount;
					reportVo.put("overdueNumber", overdueNumber);
					//平均逾期天数（总订单逾期天数/总逾期订单量）
					try {
						DecimalFormat  df = new DecimalFormat("######0.0"); 
						if(overDay==0 || overdueNumber==0){
							reportVo.put("overdueDay", 0);
						}else{
							reportVo.put("overdueDay", df.format((float)overDay/overdueNumber));
						}
						
					} catch (Exception e) {
						reportVo.put("overdueDay", 0);
					}
					//逾期最大天数
					reportVo.put("overdueBigDay", overdueBigDay);
					//保存数据
					int count=receivableReportMapper.findByReport(map);
					if(count>0){
						receivableReportMapper.deleteReport(map);
					}
					receivableReportMapper.addReport(reportVo);
			}
		}
	}

}
