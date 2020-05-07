package com.anjbo.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.ReceivableHasDto;
import com.anjbo.bean.vo.ChartVo;
import com.anjbo.dao.ReceivableForMapper;
import com.anjbo.dao.ReceivableHasMapper;
import com.anjbo.service.ReceivableForService;
import com.anjbo.utils.DateUtils;

@Transactional
@Service
public class ReceivableForServiceImpl implements ReceivableForService {

	@Resource  ReceivableForMapper receivableForMapper;
	@Resource  ReceivableHasMapper receivableHasMapper;
	/**
	 * 详情
	 */
	@Override
	public List<ReceivableForDto>  findByReceivableFor(ReceivableForDto dto) {
		// TODO Auto-generated method stub
		return receivableForMapper.findByReceivableFor(dto);
	}
	
	
	/**
	 * 一次回款
	 */
	@Override
	public int addReceivableFor(ReceivableForDto receivableFor) {
		int i=0;
		try {
			ReceivableForDto forDto =receivableFor.getForList().get(0);  
			if(forDto==null){
				return -1;   //参数不合格
			}
			receivableFor.setPayMentAmount(forDto.getPayMentAmount());  //回款金额
			receivableFor.setPayMentPic(forDto.getPayMentPic());     //回款图片
			receivableFor.setPayMentAmountDate(forDto.getPayMentAmountDate());   //回款时间
			receivableFor.setCreateTime(new Date());
			ReceivableForDto fdto=receivableForMapper.selectByStatus(receivableFor.getOrderNo());
			if(fdto!=null){
				receivableForMapper.updateReceivableFor(receivableFor);
			}else{
				receivableForMapper.addReceivableFor(receivableFor);
			}
			//计算回款天数（提前/逾期/准时）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date data1=sdf.parse(receivableFor.getPayMentAmountDate());
			Date time1=sdf.parse(receivableFor.getCustomerPaymentTimeStr());
			int datediff = getdatediff(data1,time1).intValue();
			receivableFor.setDatediff(datediff);
			
			/** 类型（0准时回款 1提前回款 2逾期回款  ） */
			Integer type;
			if (datediff == 0) {
				type = 0;
			} else if (datediff > 0) {
				type = 1;
			} else {
				type = 2;
			}
			receivableFor.setIsFrist(1);
			receivableFor.setType(type);
			ReceivableHasDto dto=receivableHasMapper.findByReceivableHas(receivableFor);
			if(dto!=null){
				receivableHasMapper.updateReceivableHas(receivableFor);
			}else{
				receivableHasMapper.addReceivableHas(receivableFor);
			}
			if (datediff == 0) {//准时回款
				i=3;
			} else if(datediff>0){//提前
//				if(receivableFor.getRefund()==0) //是否退费
				  i=3;	
//				else
//				  i=4;
			}else{  //逾期
				i=4;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return i;
	}

	
	/***
	 * 多次回款
	 */
	@Override
	public int addReceivableForToMany(ReceivableForDto receivableFor) {
		int j=0;
		List<ReceivableForDto> forDto =receivableFor.getNewForList();  
		if(forDto.size()==0){
			return -10;   //参数不合格
		}
		// 如果还款金额已经达标
		BigDecimal amoutCount=receivableForMapper.findByCountPay(receivableFor.getOrderNo());   //数据库已存 总金额
		Double loanAmount = new BigDecimal(receivableFor.getLoanAmount()).doubleValue();  // 借款总额 （万）//new BigDecimal(receivableFor.getLoanAmount()).multiply(new BigDecimal(10000)).doubleValue(); /转（元）单位
		BigDecimal total =new BigDecimal(0) ;   //页面总额
		for (int k = 0; k < forDto.size();k++) {
			BigDecimal amount = forDto.get(k).getPayMentAmount();
			total=total.add(amount);   
		}
		if(amoutCount!=null){
			total=total.add(amoutCount);  //所有回款金额
		}
		
		
		//如果总额大于借款金额,这返回提示
		if(loanAmount<total.doubleValue()){
			return -1;
		}
		//多次回款不能一次回完
		if(loanAmount==total.doubleValue() &&  null==amoutCount){
			return -2;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<ReceivableForDto> forList = receivableForMapper.findByReceivableFor(receivableFor);
		for (int i = 0; i < forDto.size(); i++) {
			String payMentAmountDate = forDto.get(i).getPayMentAmountDate();
			BigDecimal payMentAmount = forDto.get(i).getPayMentAmount();
			String payMentPic = forDto.get(i).getPayMentPic();
			receivableFor.setPayMentAmountDate(payMentAmountDate);
			receivableFor.setPayMentAmount(payMentAmount);
			receivableFor.setPayMentPic(payMentPic);
			receivableFor.setCreateTime(new Date());
			//数据库已存 总金额
			BigDecimal payMentAmountSum=receivableForMapper.findByCountPay(receivableFor.getOrderNo()); 
			if(payMentAmountSum!=null)
			payMentAmountSum=payMentAmountSum.add(payMentAmount);
			if (payMentAmountSum==null || loanAmount > payMentAmountSum.doubleValue()) {  // 总额不达标，继续累加并插入
				//看是否是首次回款
				if(forList.size()==0){//首次回款
					j = 3;
					receivableFor.setIsFrist(1);
				}else{
					receivableFor.setIsFrist(2);
				}
				receivableForMapper.addReceivableFor(receivableFor);   //添加未回款表
				ReceivableHasDto hasDto=receivableHasMapper.findByReceivableHas(receivableFor);
				if(hasDto==null){
					receivableHasMapper.addReceivableHas(receivableFor);
				}else{
					receivableHasMapper.updateReceivableHas(receivableFor);
				}
			}else if (loanAmount == payMentAmountSum.doubleValue()) {  // 总额达标，进行插入下一步
				//是否已抵押，已抵押则插入
				if(receivableFor.getIsMortgage()==1){
					return -1;   //抵押未完成
				}
				receivableFor.setIsFrist(2);
				receivableForMapper.addReceivableFor(receivableFor);  //添加未回款表
				//计算回款天数（提前/逾期/准时）
				int datediff = 0;
				try {
					Date data1=sdf.parse(receivableFor.getPayMentAmountDate());
					Date time1=sdf.parse(receivableFor.getCustomerPaymentTimeStr());
					datediff = getdatediff(data1,time1).intValue();
					receivableFor.setDatediff(datediff);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				/** 类型（0准时回款 1提前回款 2逾期回款  ） */
				Integer type;
				if (datediff == 0) {
					type = 0;
					j=5;
				} else if (datediff > 0) {  //提前
					type = 1;
//					if(receivableFor.getRefund()==0){ //是否需要退费
					j=5;	
//					}else{
//						  j=4;
//					}
				} else {  //逾期付罚息
					type = 2;
					j=4;
				}
				receivableFor.setType(type);
				// 插入回款基本表
				ReceivableHasDto hasDto=receivableHasMapper.findByReceivableHas(receivableFor);
				if(hasDto==null){
					receivableHasMapper.addReceivableHas(receivableFor);
				}else{
					receivableHasMapper.updateReceivableHas(receivableFor);
				}
				return j;
			} else {
				return -1;
			}

		}
		return j;
	}
	
	public Long getdatediff(Date submitTime,Date customerPaymentTime) {
		Long datediff;
		datediff = DateUtils.betDatePart(submitTime, customerPaymentTime,
				Calendar.DATE);
		return datediff;
	}

	/**
	  * 删除数据
	  */
	@Override
	public int updwithdraw(ReceivableForDto receivableFor) {
		// TODO Auto-generated method stub
		return receivableForMapper.delectFor(receivableFor);
	}


	@Override
	public List<String> search4AgencyOrderTime(ReceivableForDto dto) {
		return receivableForMapper.search4AgencyOrderTime(dto);
	}
	
	@Override
	public ChartVo search4reportChart(ReceivableForDto dto, int lastMonth) {
		ChartVo vo = new ChartVo(lastMonth);
		try {
			dto.setPayMentAmountDateStart(DateUtils.dateToString(org.apache.commons.lang.time.DateUtils.addMonths(new Date(), (1-lastMonth)), "yyyy-MM"));
			List<ReceivableForDto> list = receivableForMapper.search4reportChart(dto);
			// 构建报表数据	
			if(null!=list && !list.isEmpty()){		
				for (int i = 0; i < lastMonth; i++) {
					for (ReceivableForDto bean : list) {
						if(vo.getNames()[i].equals(bean.getPayMentAmountDateFmt().split("年")[1])){
							vo.getValues()[i] = bean.getReportSum();
							vo.getValues2()[i] = bean.getReportCount();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	@Override
	public List<ReceivableForDto> search4report(ReceivableForDto dto) {
		return receivableForMapper.search4report(dto);
	}

	@Override
	public List<Map<String, Object>> findPaymentStatisticMonth(String agencyId,
			String orderNos) {
		return receivableForMapper.findPaymentStatisticMonth(agencyId, orderNos);
	}

	@Override
	public Map<String, Object> findPaymentStatisticTotal(String agencyId,
			String orderNos) {
		return receivableForMapper.findPaymentStatisticTotal(agencyId, orderNos);
	}

	@Override
	public List<Map<String, Object>> findPaymentStatisticDetail(
			String agencyId, String orderNos, int year, int month) {
		return receivableForMapper.findPaymentStatisticDetail(agencyId, orderNos, year, month);
	}


	@Override
	public List<Map<String, Object>> findPaymentByLoan(String orderNos) {
		return receivableForMapper.findPaymentByLoan(orderNos);
	}


	@Override
	public Map<String, Object> findPaymentStatisticByMonthAndOrderNos(
			String orderNos, int year, int month) {
		return receivableForMapper.findPaymentStatisticByMonthAndOrderNos(orderNos, year, month);
	}

}
