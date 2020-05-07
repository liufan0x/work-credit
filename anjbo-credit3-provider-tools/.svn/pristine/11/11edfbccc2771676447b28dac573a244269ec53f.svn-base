/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.TblFinanceAfterloanEqualInterestDto;
import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.bean.TblFinanceAfterloanLinePaymentDto;
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.bean.TblOrderBaseHouseLendingDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.Arith;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.ITblFinanceAfterloanLinePaymentController;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.TblFinanceAfterloanEqualInterestService;
import com.anjbo.service.TblFinanceAfterloanFirstInterestService;
import com.anjbo.service.TblFinanceAfterloanLinePaymentService;
import com.anjbo.service.TblFinanceAfterloanListService;
import com.anjbo.service.TblOrderBaseHouseLendingService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-29 09:37:16
 * @version 1.0
 */
@RestController
public class TblFinanceAfterloanLinePaymentController extends BaseController implements ITblFinanceAfterloanLinePaymentController{

	@Resource private TblFinanceAfterloanLinePaymentService tblFinanceAfterloanLinePaymentService;
	
	@Resource private UserApi userApi;
	@Resource private ThirdApi thirdApi;
	 @Resource
    private TblFinanceAfterloanEqualInterestService  afterloanEqualInterestService;
 
    @Resource
    private TblFinanceAfterloanFirstInterestService afterloanFirstInterestService;
    
    @Resource
    private TblOrderBaseHouseLendingService tblOrderBaseHouseLendingService;
    
    @Resource
    private TblFinanceAfterloanListService tblFinanceAfterloanListService;
    
    @Resource
    private OrderApi orderApi;
  
    
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<TblFinanceAfterloanLinePaymentDto> page(@RequestBody TblFinanceAfterloanLinePaymentDto dto){
		RespPageData<TblFinanceAfterloanLinePaymentDto> resp = new RespPageData<TblFinanceAfterloanLinePaymentDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(tblFinanceAfterloanLinePaymentService.search(dto));
			resp.setTotal(tblFinanceAfterloanLinePaymentService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<TblFinanceAfterloanLinePaymentDto> search(@RequestBody TblFinanceAfterloanLinePaymentDto dto){ 
		RespData<TblFinanceAfterloanLinePaymentDto> resp = new RespData<TblFinanceAfterloanLinePaymentDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanLinePaymentService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanLinePaymentDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanLinePaymentDto> find(@RequestBody TblFinanceAfterloanLinePaymentDto dto){ 
		RespDataObject<TblFinanceAfterloanLinePaymentDto> resp = new RespDataObject<TblFinanceAfterloanLinePaymentDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanLinePaymentService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanLinePaymentDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanLinePaymentDto> add(@RequestBody TblFinanceAfterloanLinePaymentDto dto){ 
		RespDataObject<TblFinanceAfterloanLinePaymentDto> resp = new RespDataObject<TblFinanceAfterloanLinePaymentDto>();
		try {
			String orderNo = dto.getOrderNo();
			dto.setCreateUid(userApi.getUserDto().getUid());
			RespDataObject ss=new RespDataObject<>() ;
			RespDataObject<TblFinanceAfterloanLinePaymentDto> s=null;

			TblFinanceAfterloanListDto tblFinanceAfterloanListDto=new TblFinanceAfterloanListDto();
			tblFinanceAfterloanListDto.setOrderNo(dto.getOrderNo());
			TblFinanceAfterloanListDto tblFinanceAfterloanListDto2= tblFinanceAfterloanListService.find(tblFinanceAfterloanListDto);
			
		//更新还款列表中的数据
		/*
		 * 1正常还款
		 */
		if ("1".equals(dto.getDeductionsType())) {
			Double repayPrincipalTotal=Double.valueOf(0) ;//'实收本金',
			Double repayInterestTotal=Double.valueOf(0) ;//'实收利息',
			Double repayOverdueTotal=Double.valueOf(0) ;//'实收利息',

		if (1==dto.getRepaymentMode() ) {//先息后本
			
			TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
			afterloanFirstInterestDto.setOrderNo(dto.getOrderNo());
			afterloanFirstInterestDto.setRepaymentPeriods(dto.getCurrentPeriod());
			List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
			
			
			
			if (list.size()>0) {
				TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto2= new TblFinanceAfterloanFirstInterestDto();
				afterloanFirstInterestDto2=list.get(0);
				Double givePayPrincipal=	afterloanFirstInterestDto2.getGivePayPrincipal() ;//
				Double givePayInterest=	afterloanFirstInterestDto2.getGivePayInterest() ;//实收总额
				Double givePayOverdue=	afterloanFirstInterestDto2.getGivePayOverdue() ;//实收总额
				
				
				
				Double total=	Double.valueOf(0) ;//应还总额
				Double total2=	Double.valueOf(0) ;//实收总额
						Double	 t1=	Double.valueOf(list.get(0).getRepayPrincipal()!=null?list.get(0).getRepayPrincipal():0);
						Double	 t2	=Double.valueOf((list.get(0).getRepayInterest()!=null?list.get(0).getRepayInterest():0));
						Double	 t3	=new Double(Double.valueOf((list.get(0).getRepayOverdue()!=null?list.get(0).getRepayOverdue():0)));
						
						total=Arith.add(Arith.add(t1, t2), t3);		
								
						Double	 t4=Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0));
						Double	 t5=Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0));
						Double	 t6=Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0));
						Double	 t7=Double.valueOf((dto.getReceivedPrincipal() !=null ? dto.getReceivedPrincipal():0));
						Double	 t8=Double.valueOf((dto.getReceivedInterest()!=null ? dto.getReceivedInterest():0));
						Double	 t9=Double.valueOf((dto.getReceivedDefaultInterest()!=null ? dto.getReceivedDefaultInterest():0));
				total2=Arith.add(Arith.add(Arith.add(Arith.add(Arith.add(t4, t5), t6),t7),t8),t9);	
						
			
				afterloanFirstInterestDto2.setGivePayPrincipal(Arith.add((dto.getReceivedPrincipal()!=null?dto.getReceivedPrincipal():0),(list.get(0).getGivePayPrincipal()!=null?list.get(0).getGivePayPrincipal():0)));//实收本金
				afterloanFirstInterestDto2.setGivePayInterest(Arith.add((dto.getReceivedInterest()!=null?dto.getReceivedInterest():0),(list.get(0).getGivePayInterest()!=null?list.get(0).getGivePayInterest():0)));//'实收利息',
				afterloanFirstInterestDto2.setGivePayOverdue(Arith.add((dto.getReceivedDefaultInterest()!=null?dto.getReceivedDefaultInterest():0),(list.get(0).getGivePayOverdue()!=null?list.get(0).getGivePayOverdue():0)));//'实收罚息',
			
		
			
			 
			    repayPrincipalTotal=Double.valueOf(dto.getReceivedPrincipal());
				repayInterestTotal=Double.valueOf(dto.getReceivedInterest());
				repayOverdueTotal=Double.valueOf(dto.getReceivedDefaultInterest());
			
				ss=    pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
				// ss.setCode("SUCCESS");
				 //ss.setMsg("处理成功");
				
			 
			 //提交成功后更新状态
				//提交成功后查询状态
				 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
					 
					 Map<String, Object> map=new HashMap<String, Object>();
					 
					 map.put("orderNo", dto.getOrderNo());
					 map.put("currentPeriod", dto.getCurrentPeriod());
					 
					 RespDataObject resps = searchSgtLinePaymentStatus( map);
					 
					 logger.error("查询结果返回msg:"+resps);
					 if (total.compareTo(total2) ==0 && "处理成功".equals( resps.getMsg()) ) {
						 afterloanFirstInterestDto2.setStatus(3);
					 }
					 if (total.compareTo(total2) ==1 && "处理成功".equals( resps.getMsg()) ) { 
						 afterloanFirstInterestDto2.setStatus(2);
					 }
					 
					 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
						 ss.setCode("FAIL");
						 ss.setMsg("资方服务异常");
						 afterloanFirstInterestDto2.setStatus(1);
						 afterloanFirstInterestDto2.setGivePayPrincipal(givePayPrincipal);//实收本金
							afterloanFirstInterestDto2.setGivePayInterest(givePayInterest);//'实收利息',
							afterloanFirstInterestDto2.setGivePayOverdue(givePayOverdue);//'实收罚息',
						 
			          } 
					 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
						 afterloanFirstInterestDto2.setStatus(9);
						 afterloanFirstInterestDto2.setGivePayPrincipal(givePayPrincipal);//实收本金
							afterloanFirstInterestDto2.setGivePayInterest(givePayInterest);//'实收利息',
							afterloanFirstInterestDto2.setGivePayOverdue(givePayOverdue);//'实收罚息',
			          } 
					 
					
							afterloanFirstInterestService.update(afterloanFirstInterestDto2);
							
							if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==afterloanFirstInterestDto2.getRepaymentPeriods()
									&&afterloanFirstInterestDto2.getStatus()==3) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(3);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
								nextFlow(dto.getOrderNo());
							}else if(afterloanFirstInterestDto2.getStatus()==3) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(3);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
								
							}else if(afterloanFirstInterestDto2.getStatus()==2) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(2);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
								
							}
							tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
					 
				 }
		
					
	
			 
			
			}
			
		}
       if (2==dto.getRepaymentMode()) {//等额本息
			
			TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
			afterloanEqualInterestDto.setOrderNo(dto.getOrderNo());
			afterloanEqualInterestDto.setRepaymentPeriods(dto.getCurrentPeriod());
			
			List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
			Double total=	Double.valueOf(0) ;//应还总额
			Double total2=	Double.valueOf(0) ;//实收总额
			if (list.size()>0) {
				TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto2=list.get(0);
				
				Double givePayPrincipal=	afterloanEqualInterestDto2.getGivePayPrincipal() ;//
				Double givePayInterest=	afterloanEqualInterestDto2.getGivePayInterest() ;//实收总额
				Double givePayOverdue=	afterloanEqualInterestDto2.getGivePayOverdue() ;//实收总额
				
				
			
				Double t1=Double.valueOf((list.get(0).getRepayPrincipal()!=null ? list.get(0).getRepayPrincipal()  :0));
				Double t2=Double.valueOf((list.get(0).getRepayInterest()!=null ? list.get(0).getRepayInterest():0));
				Double t3=Double.valueOf((list.get(0).getRepayOverdue()!=null? list.get(0).getRepayOverdue() :0));
				
				total=Arith.add(Arith.add(t1, t2), t3);		
						
				
				Double t4=Double.valueOf((list.get(0).getGivePayPrincipal()!=null ? list.get(0).getGivePayPrincipal():0));
				Double t5=Double.valueOf((list.get(0).getGivePayInterest()!=null ? list.get(0).getGivePayInterest():0));
				Double t6=Double.valueOf((list.get(0).getGivePayOverdue()!=null ? list.get(0).getGivePayOverdue():0));
				Double t7=Double.valueOf((dto.getReceivedPrincipal()!=null ? dto.getReceivedPrincipal():0));
				Double t8=Double.valueOf((dto.getReceivedInterest()!=null ? dto.getReceivedInterest():0));
				Double t9=Double.valueOf((dto.getReceivedDefaultInterest()!=null ?dto.getReceivedDefaultInterest():0));
				
				total2=Arith.add(Arith.add(Arith.add(Arith.add(Arith.add(t4, t5), t6),t7),t8),t9);	
			
				afterloanEqualInterestDto2.setGivePayPrincipal(Arith.add((dto.getReceivedPrincipal()!=null ?  dto.getReceivedPrincipal():0)
						                                            ,(list.get(0).getGivePayPrincipal()!=null ? list.get(0).getGivePayPrincipal() :0)));//'实收本金',
				afterloanEqualInterestDto2.setGivePayInterest(Arith.add((dto.getReceivedInterest()!=null ?  dto.getReceivedInterest():0)
						                                      ,(list.get(0).getGivePayInterest()!=null ? list.get(0).getGivePayInterest() :0)));//'实收利息',
				afterloanEqualInterestDto2.setGivePayOverdue(Arith.add((dto.getReceivedDefaultInterest()!=null ?  dto.getReceivedDefaultInterest():0)
						                                 ,(list.get(0).getGivePayOverdue()!=null ? list.get(0).getGivePayOverdue() :0)));//'实收罚息',
				
				    repayPrincipalTotal=Double.valueOf(dto.getReceivedPrincipal());
					repayInterestTotal=Double.valueOf(dto.getReceivedInterest());
					repayOverdueTotal=Double.valueOf(dto.getReceivedDefaultInterest());
					
				
			
					 
					
					
					 ss=    pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
		
//					 ss.setCode("SUCCESS");
//					 ss.setMsg("处理中");
					
					//提交成功后查询状态
					 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
						 
						 Map<String, Object> map=new HashMap<String, Object>();
						 
						 map.put("orderNo", dto.getOrderNo());
						 map.put("currentPeriod", dto.getCurrentPeriod());
						 
						 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
						 logger.error("查询结果返回msg:"+resps);
						 if (total.compareTo(total2) ==0 && "处理成功".equals( resps.getMsg()) ) {
								afterloanEqualInterestDto2.setStatus(3);
						 }
						 if (total.compareTo(total2) ==1 && "处理成功".equals( resps.getMsg()) ) { 
										afterloanEqualInterestDto2.setStatus(2);
						 }
						 
						 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
							 ss.setCode("FAIL");
							 ss.setMsg("资方服务异常");
								afterloanEqualInterestDto2.setStatus(1);

								afterloanEqualInterestDto2.setGivePayPrincipal(givePayPrincipal != null ? givePayPrincipal: 0);// '实收本金',
								afterloanEqualInterestDto2.setGivePayInterest(givePayInterest != null ? givePayInterest: 0);// '实收利息',
								afterloanEqualInterestDto2.setGivePayOverdue(givePayOverdue != null ? givePayOverdue: 0);// '实收罚息',
								
				          } 
						 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
								afterloanEqualInterestDto2.setStatus(9);
								afterloanEqualInterestDto2.setGivePayPrincipal(givePayPrincipal != null ? givePayPrincipal: 0);// '实收本金',
								afterloanEqualInterestDto2.setGivePayInterest(givePayInterest != null ? givePayInterest: 0);// '实收利息',
								afterloanEqualInterestDto2.setGivePayOverdue(givePayOverdue != null ? givePayOverdue: 0);// '实收罚息',
								
								
				          } 
							afterloanEqualInterestService.update(afterloanEqualInterestDto2);
							
							if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==afterloanEqualInterestDto2.getRepaymentPeriods()
									&&afterloanEqualInterestDto2.getStatus()==3) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(3);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
								nextFlow(dto.getOrderNo());
							}else if(afterloanEqualInterestDto2.getStatus()==3) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(3);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
								
							}else if(afterloanEqualInterestDto2.getStatus()==2) {
								
								tblFinanceAfterloanListDto2.setRepaymentStatus(2);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
								
							}
							
							tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
					 }
		
			}
		}
       
       
		}
       /*
		 * 2。提前清贷
		 */
		if ("2".equals(dto.getDeductionsType())) {
			Double repayPrincipalTotal= Double.valueOf(0)  ;//剩余全部'应收本金',
			Double repayInterestTotal=Double.valueOf(0) ;//剩余全部'应收利息',
			Double repayOverdueTotal=Double.valueOf(0) ;//剩余全部'应收利息',

			
			
			
			
			if (1==dto.getRepaymentMode() ) {//先息后本
				
				TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
				afterloanFirstInterestDto.setOrderNo(dto.getOrderNo());
				
				//查询剩余全部期数
			List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
				//跟新剩余期数为提前清贷
			for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto : list) {
				
				if (tblFinanceAfterloanFirstInterestDto.getStatus()==2 ) {
					repayPrincipalTotal= Arith.add(repayPrincipalTotal,
							         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0),
									Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal():0)) );
							
					repayInterestTotal= Arith.add(repayInterestTotal,
					         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0),
							Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayInterest():0)) );
						
					repayOverdueTotal= Arith.add(repayOverdueTotal,
					         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0),
							Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayOverdue():0)) );
				}
				
				if (tblFinanceAfterloanFirstInterestDto.getStatus()==1 ) {

					
					repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0));
					repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0));
					repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0));

					
				}
				
			}
			        dto.setReceivablePrincipal(repayPrincipalTotal);//
			        dto.setReceivableInterest(repayInterestTotal);
			        dto.setReceivableDefaultInterest(repayOverdueTotal);
			        
			        
			        
				//提交陕国投
			 ss=       pushTblFinanceAfterloanLinePaymentDto(dto,dto.getReceivedPrincipalTotal(),dto.getReceivedInterestTotal(),dto.getReceivedDefaultInterestTotal());
			 //提交成功后更新状态
			 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
				 
				 
				 Map<String, Object> map=new HashMap<String, Object>();
				 
				 map.put("orderNo", dto.getOrderNo());
				 map.put("currentPeriod", dto.getCurrentPeriod());
				 
				 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
				 logger.error("查询结果返回msg:"+resps);
				 
				 for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto : list) {
						
						if (tblFinanceAfterloanFirstInterestDto.getStatus()==1 ||tblFinanceAfterloanFirstInterestDto.getStatus()==2) {
						
							
//							tblFinanceAfterloanFirstInterestDto.setStatus(4);
//							afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto);
							
							
					
							 if ( "处理成功".equals( resps.getMsg()) ) {
									tblFinanceAfterloanFirstInterestDto.setGivePayPrincipal(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0);//'实收本金',
									tblFinanceAfterloanFirstInterestDto.setGivePayInterest(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0);//'实收利息',
									tblFinanceAfterloanFirstInterestDto.setGivePayOverdue(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0);//'实收利息',
								 
								 tblFinanceAfterloanFirstInterestDto.setStatus(4);
							 }
						
							 
							 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
								 ss.setCode("FAIL");
								 ss.setMsg("资方服务异常");
								 tblFinanceAfterloanFirstInterestDto.setStatus(10);
					          } 
							 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
								 tblFinanceAfterloanFirstInterestDto.setStatus(11);
					          } 
							 afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto);
							
						}
					}
				 
				 if ( "处理成功".equals( resps.getMsg()) ) {
			
						
						tblFinanceAfterloanListDto2.setRepaymentStatus(4);
						tblFinanceAfterloanListDto2.setRepaymentStatusName("提前结清");
						tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
						
						nextFlow(dto.getOrderNo());
					
				 
				 }
			 }
				
			}
	       if (2==dto.getRepaymentMode()) {//等额本息
				
				TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
				
				afterloanEqualInterestDto.setOrderNo(dto.getOrderNo());
				
				//查询剩余全部期数
				List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
				//跟新剩余期数为提前清贷
				for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto : list) {
					
					if (tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
						repayPrincipalTotal= Arith.add(repayPrincipalTotal,
								         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0),
										Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal():0)) );
								
						repayInterestTotal= Arith.add(repayInterestTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0),
								Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayInterest():0)) );
							
						repayOverdueTotal= Arith.add(repayOverdueTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0),
								Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayOverdue():0)) );
				}
				
				if (tblFinanceAfterloanEqualInterestDto.getStatus()==1 ) {
					
				
					repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0));
					repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0));
					repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0));

				}
				}
				
				   dto.setReceivablePrincipal(repayPrincipalTotal);//
			        dto.setReceivableInterest(repayInterestTotal);
			        dto.setReceivableDefaultInterest(repayOverdueTotal);
				
				
				 ss=       pushTblFinanceAfterloanLinePaymentDto(dto,dto.getReceivedPrincipalTotal(),dto.getReceivedInterestTotal(),dto.getReceivedDefaultInterestTotal());
				
				 //提交成功后更新状态
				 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
					 
					 
			         Map<String, Object> map=new HashMap<String, Object>();
					 
										 map.put("orderNo", dto.getOrderNo());
										 map.put("currentPeriod", dto.getCurrentPeriod());
										 
										 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
										 
										 logger.error("查询结果返回msg:"+resps);
					 for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto : list) {
							
							if (tblFinanceAfterloanEqualInterestDto.getStatus()==1 ||tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
              
							 if ( "处理成功".equals( resps.getMsg()) ) {
								 tblFinanceAfterloanEqualInterestDto.setStatus(4);
									tblFinanceAfterloanEqualInterestDto.setGivePayPrincipal(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0);//'实收本金',
									tblFinanceAfterloanEqualInterestDto.setGivePayInterest(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0);//'实收利息',
									tblFinanceAfterloanEqualInterestDto.setGivePayOverdue(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0);//'实收罚息',
							 }
						
							 
							 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
								 ss.setCode("FAIL");
								 ss.setMsg("资方服务异常");
								 tblFinanceAfterloanEqualInterestDto.setStatus(10);
					          } 
							 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
								 tblFinanceAfterloanEqualInterestDto.setStatus(11);
					          } 
							 afterloanEqualInterestService.update(tblFinanceAfterloanEqualInterestDto);
							 
							
								
								
							
							}
						}
					 
					 
					 if ( "处理成功".equals( resps.getMsg()) ) {
							
							
							tblFinanceAfterloanListDto2.setRepaymentStatus(4);
							tblFinanceAfterloanListDto2.setRepaymentStatusName("提前结清");
							tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
							
							nextFlow(dto.getOrderNo());
						
					 
					 }
					 
				 }
			  }
	        
	        
			}
	
	
		
		return ss;
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanLinePaymentDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	
	@SuppressWarnings("rawtypes")
	public RespDataObject pushTblFinanceAfterloanLinePaymentDto(TblFinanceAfterloanLinePaymentDto dto, Double repayPrincipalTotal, Double repayInterestTotal, Double repayOverdueTotal) {
		RespDataObject resp = new RespDataObject<>();
		try {
			TblOrderBaseHouseLendingDto tblOrderBaseHouseLendingDto= new TblOrderBaseHouseLendingDto();
			tblOrderBaseHouseLendingDto.setOrderNo(dto.getOrderNo());
			tblOrderBaseHouseLendingDto.setRepaymentType(dto.getRepaymentMode());
		List<TblOrderBaseHouseLendingDto>	tblOrderBaseHouseLendingDtoli=	 tblOrderBaseHouseLendingService.search(tblOrderBaseHouseLendingDto);
            if (tblOrderBaseHouseLendingDtoli.size()>0) {
            	dto.setDebitAccount(tblOrderBaseHouseLendingDtoli.get(0).getPaymentBankAccount());
				
			}
			//线上还款
			if ("1".equals(dto.getLineflag())) {
			String timestamp=String.valueOf(System.currentTimeMillis());
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("brNo", Constants.SGT_WS_BRNO);
			maps.put("batNo", "P"+timestamp);
			maps.put("dataCnt", 1);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> firstmap = new HashMap<String, Object>();
				firstmap.put("pactNo", "KG"+ dto.getOrderNo());
				firstmap.put("serialNo", "D"+timestamp);
				firstmap.put("acNo", dto.getDebitAccount());
				//firstmap.put("cardChn", "CL0001");
				firstmap.put("repayType", "0"+dto.getDeductionsType());
				firstmap.put("repayAmt", Arith.add( Arith.add(repayPrincipalTotal, repayInterestTotal) ,repayOverdueTotal));
			
		
					
				
				List<Map<String, Object>> listSubj = new ArrayList<Map<String, Object>>();
				for (int i = 0; i <9; i++) {
					Map<String, Object> subMap = new HashMap<String, Object>();
					subMap.put("cnt", dto.getCurrentPeriod());
					if (i==0) {//应收本金
						subMap.put("subjType", "1001");
						subMap.put("subjAmt",dto.getReceivablePrincipal() !=null?dto.getReceivablePrincipal() :0);
					}
					if (i==1) {//应收利息
						subMap.put("subjType", "1002");
						subMap.put("subjAmt",dto.getReceivableInterest()!=null?dto.getReceivableInterest():0);
					}
				
					if (i==2) {//应收罚息
						subMap.put("subjType", "1003");
						subMap.put("subjAmt",dto.getReceivableDefaultInterest()!=null?dto.getReceivableDefaultInterest():0 );
					}
				
					if (i==3) {// 实收本金
						subMap.put("subjType", "2001");
						subMap.put("subjAmt",repayPrincipalTotal!=null?repayPrincipalTotal:0) ;
					}
				
					if (i==4) {// 实收利息
						subMap.put("subjType", "2002");
						subMap.put("subjAmt",repayInterestTotal !=null?repayInterestTotal:0);
					}
				
					if (i==5) {//实收罚息
						subMap.put("subjType", "2003");
						subMap.put("subjAmt",repayOverdueTotal!=null?repayOverdueTotal:0 );
					}
					if (i==6) {//减免本金
						subMap.put("subjType", "3001");
						subMap.put("subjAmt",dto.getRemissionPrincipal()!=null?dto.getRemissionPrincipal():0 );
					}
					if (i==7) {//减免利息
						subMap.put("subjType", "3002");
						subMap.put("subjAmt",dto.getRemissionInterest()!=null?dto.getRemissionInterest():0 );
					}
					if (i==8) {//减免罚息
						subMap.put("subjType", "3003");
						subMap.put("subjAmt",dto.getRemissionDefaultInterest()!=null?dto.getRemissionDefaultInterest():0 );
					}
				
				
					listSubj.add(subMap);
				}
				
				firstmap.put("listSubj", listSubj);
				list.add(firstmap);
			maps.put("list", list);
			maps.put("type", 1);
			maps.put("txCode","2311");
			String jsonObject = JSONObject.fromObject(maps).toString();  
			logger.error("线上还款请求参数："+jsonObject);
			RespDataObject<Map<String, Object>> s= thirdApi.interfaceCall(maps);
			resp.setCode(s.getCode());
			resp.setMsg(s.getMsg());
			logger.error("线上还款请求返回结果："+s);
			
			if ("SUCCESS".equals(s.getCode())) {
				
				  tblFinanceAfterloanLinePaymentService.insert(dto);
					TblFinanceAfterloanLinePaymentDto afterloanLinePaymentDto=new TblFinanceAfterloanLinePaymentDto();
					afterloanLinePaymentDto.setOrderNo(dto.getOrderNo());
					afterloanLinePaymentDto.setCurrentPeriod(dto.getCurrentPeriod());
					
					List<TblFinanceAfterloanLinePaymentDto> afterloanLinePaymentDtos=	tblFinanceAfterloanLinePaymentService.search(afterloanLinePaymentDto);
					afterloanLinePaymentDto=	afterloanLinePaymentDtos.get(0);
					afterloanLinePaymentDto.setExtendField3("P"+timestamp);
					afterloanLinePaymentDto.setExtendField4("KG"+ dto.getOrderNo());
					
					tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDto);
				
			}
			
			
			
			}else {//线下还款
				Map<String, Object> maps = new HashMap<String, Object>();
				String timestamp=String.valueOf(System.currentTimeMillis());

				maps.put("brNo", Constants.SGT_WS_BRNO);
				maps.put("batNo", "P"+timestamp);
				maps.put("bankNo",dto.getSpecialAccountBankSerialNumber());//专户银行流水号
				maps.put("dataCnt", 1);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
					Map<String, Object> firstmap = new HashMap<String, Object>();
					firstmap.put("pactNo", "KG"+ dto.getOrderNo());
					firstmap.put("repayType", "0"+dto.getDeductionsType());
					firstmap.put("repayAmt", Arith.add(Arith.add(repayPrincipalTotal, repayInterestTotal) ,repayOverdueTotal));

					firstmap.put("signOffline", "0"+dto.getOfflineReceivingTag());
					
					String bankArrDate=	DateUtils.dateToString(dto.getBankActualDate(), "YYYYMMDD");
					firstmap.put("bankArrDate",bankArrDate);
					
				
					
					
					
					List<Map<String, Object>> listSubj = new ArrayList<Map<String, Object>>();
					for (int i = 0; i <9; i++) {
						Map<String, Object> subMap = new HashMap<String, Object>();
						subMap.put("cnt", dto.getCurrentPeriod());
						if (i==0) {//应收本金
							subMap.put("subjType", "1001");
							subMap.put("subjAmt", dto.getReceivablePrincipal()!=null? dto.getReceivablePrincipal():0);
						}
						if (i==1) {//应收利息
							subMap.put("subjType", "1002");
							subMap.put("subjAmt", dto.getReceivableInterest()!=null? dto.getReceivableInterest():0);
						}
					
						if (i==2) {//应收罚息
							subMap.put("subjType", "1003");
							subMap.put("subjAmt", dto.getReceivableDefaultInterest()!=null? dto.getReceivableDefaultInterest():0);
						}
					
						if (i==3) {// 实收本金
							subMap.put("subjType", "2001");
							subMap.put("subjAmt", repayPrincipalTotal!=null? repayPrincipalTotal:0);
						}
					
						if (i==4) {// 实收利息
							subMap.put("subjType", "2002");
							subMap.put("subjAmt", repayInterestTotal!=null? repayInterestTotal:0);
						}
					
						if (i==5) {//实收罚息
							subMap.put("subjType", "2003");
							subMap.put("subjAmt", repayOverdueTotal!=null? repayOverdueTotal:0);
						}
						if (i==6) {//减免本金
							subMap.put("subjType", "3001");
							subMap.put("subjAmt",dto.getRemissionPrincipal()!=null? dto.getRemissionPrincipal():0 );
						}
						if (i==7) {//减免利息
							subMap.put("subjType", "3002");
							subMap.put("subjAmt",dto.getRemissionInterest() !=null? dto.getRemissionInterest():0);
						}
						if (i==8) {//减免罚息
							subMap.put("subjType", "3003");
							subMap.put("subjAmt",dto.getRemissionDefaultInterest() !=null? dto.getRemissionDefaultInterest():0);
						}
					
						listSubj.add(subMap);
					}
						
					firstmap.put("listSubj", listSubj);
					list.add(firstmap);
				maps.put("list", list);
				maps.put("type", 1);
				maps.put("txCode","2312");
				String jsonObject = JSONObject.fromObject(maps).toString();  
				logger.error("线下还款请求参数："+jsonObject);
					RespDataObject<Map<String, Object>> s= thirdApi.interfaceCall(maps);
					resp.setCode(s.getCode());
					resp.setMsg(s.getMsg());
					logger.error("线下还款请求返回结果："+s);
					
					if ("SUCCESS".equals(s.getCode())) {
						  tblFinanceAfterloanLinePaymentService.insert(dto);
						TblFinanceAfterloanLinePaymentDto afterloanLinePaymentDto=new TblFinanceAfterloanLinePaymentDto();
						afterloanLinePaymentDto.setOrderNo(dto.getOrderNo());
						afterloanLinePaymentDto.setCurrentPeriod(dto.getCurrentPeriod());
						
						List<TblFinanceAfterloanLinePaymentDto> afterloanLinePaymentDtos=	tblFinanceAfterloanLinePaymentService.search(afterloanLinePaymentDto);
						afterloanLinePaymentDto=	afterloanLinePaymentDtos.get(0);
						afterloanLinePaymentDto.setExtendField3("P"+timestamp);
						afterloanLinePaymentDto.setExtendField4("KG"+ dto.getOrderNo());
						
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDto);
					}
				
			}

		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("上传还款信息失败");

		}

		return resp;
	}
	
	
	
	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody TblFinanceAfterloanLinePaymentDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			tblFinanceAfterloanLinePaymentService.update(dto);
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
	public RespStatus delete(@RequestBody TblFinanceAfterloanLinePaymentDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			tblFinanceAfterloanLinePaymentService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}


	@Override
	public RespDataObject<Map<String, Object>> searchSgtLinePaymentStatus(@RequestBody Map<String, Object> dto) {
		RespDataObject resp = new RespDataObject<>();
		try {
			
			TblFinanceAfterloanLinePaymentDto afterloanLinePaymentDto=new TblFinanceAfterloanLinePaymentDto();
			afterloanLinePaymentDto.setOrderNo(String.valueOf(dto.get("orderNo")));
			

			
			if ("2".equals(String.valueOf( dto.get("deductionsType")))) {//提前清贷查询
				afterloanLinePaymentDto.setDeductionsType("2");
				
			}else {
				afterloanLinePaymentDto.setCurrentPeriod(Integer.valueOf(String.valueOf( dto.get("currentPeriod"))));//正常还款查询
			}
			List<TblFinanceAfterloanLinePaymentDto> afterloanLinePaymentDtos=	tblFinanceAfterloanLinePaymentService.search(afterloanLinePaymentDto);
			
			

			TblFinanceAfterloanListDto tblFinanceAfterloanListDto=new TblFinanceAfterloanListDto();
			tblFinanceAfterloanListDto.setOrderNo(String.valueOf(dto.get("orderNo")));
			TblFinanceAfterloanListDto tblFinanceAfterloanListDto2= tblFinanceAfterloanListService.find(tblFinanceAfterloanListDto);
			
			
			if (afterloanLinePaymentDtos.size()>0) {
				afterloanLinePaymentDto=afterloanLinePaymentDtos.get(0);
			}
			
			if ("1".equals(afterloanLinePaymentDto.getDeductionsType())) {
				
			
			
			
			  TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto=null;
			  TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto=null;
			  int status=0;
				Double total=	Double.valueOf(0) ;//应还总额
				Double total2=	Double.valueOf(0) ;//实收总额
				
				Double givePayPrincipal=	Double.valueOf(0) ;//实收本金
				Double givePayInterest=	Double.valueOf(0) ;//实收利息
				Double givePayOverdue=	Double.valueOf(0) ;//实收罚息
			if (1==afterloanLinePaymentDto.getRepaymentMode() ) {//先息后本
				
				TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
				afterloanFirstInterestDto.setOrderNo(afterloanLinePaymentDto.getOrderNo());
				afterloanFirstInterestDto.setRepaymentPeriods(afterloanLinePaymentDto.getCurrentPeriod());
			
			    List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
			    
			    if (list.size()>0) {
			    	tblFinanceAfterloanFirstInterestDto=list.get(0);
				}
			    
			    
				
			
						Double	 t1=	Double.valueOf(list.get(0).getRepayPrincipal()!=null?list.get(0).getRepayPrincipal():0);
						Double	 t2	=Double.valueOf((list.get(0).getRepayInterest()!=null?list.get(0).getRepayInterest():0));
						Double	 t3	= Double.valueOf((list.get(0).getRepayOverdue()!=null?list.get(0).getRepayOverdue():0));
						
						total=	Arith.add(Arith.add(t1,t2),t3);
						Double	 t4=0.0;
						Double	 t5=0.0;
						Double	 t6=0.0;
						if ("处理成功".equals(afterloanLinePaymentDto.getExtendField5())) {
							 t4=Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0));//实收= 已还的+再还款
							 t5=Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0));
							 t6=Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0));
	
						}else {
								 t4=Arith.add(Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0)),afterloanLinePaymentDto.getReceivedPrincipal());//实收= 已还的+再还款
								 t5=Arith.add(Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0)),afterloanLinePaymentDto.getReceivedInterest());
								 t6=Arith.add(Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0)),afterloanLinePaymentDto.getReceivedDefaultInterest());
		
							
						}
						total2=Arith.add(Arith.add(t4,t5),t6);
			    
			}
			
		    if (2==afterloanLinePaymentDto.getRepaymentMode()) {//等额本息
					
					TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
					
					afterloanEqualInterestDto.setOrderNo(afterloanLinePaymentDto.getOrderNo());
					afterloanEqualInterestDto.setRepaymentPeriods(afterloanLinePaymentDto.getCurrentPeriod());
					
					List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
					
					  if (list.size()>0) {
						  tblFinanceAfterloanEqualInterestDto=list.get(0);
						}
					  

						Double	 t1=	Double.valueOf(list.get(0).getRepayPrincipal()!=null?list.get(0).getRepayPrincipal():0);
						Double	 t2	=Double.valueOf((list.get(0).getRepayInterest()!=null?list.get(0).getRepayInterest():0));
						Double	 t3	= Double.valueOf((list.get(0).getRepayOverdue()!=null?list.get(0).getRepayOverdue():0));
						
						total=	Arith.add(Arith.add(t1,t2),t3);
						Double	 t4=0.0;
						Double	 t5=0.0;
						Double	 t6=0.0;
						if ("处理成功".equals(afterloanLinePaymentDto.getExtendField5())) {
							 t4=Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0));//实收= 已还的+再还款
							 t5=Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0));
							 t6=Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0));
	
						}else {
								 t4=Arith.add(Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0)),afterloanLinePaymentDto.getReceivedPrincipal());//实收= 已还的+再还款
								 t5=Arith.add(Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0)),afterloanLinePaymentDto.getReceivedInterest());
								 t6=Arith.add(Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0)),afterloanLinePaymentDto.getReceivedDefaultInterest());
		
							
						}
						
						
						total2=Arith.add(Arith.add(t4,t5),t6);
					  
					  
			   }
			
			
			Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("type", 2);
				maps.put("txCode","2313");
				maps.put("brNo", Constants.SGT_WS_BRNO);
				maps.put("batNo", afterloanLinePaymentDtos.get(0).getExtendField3());
				maps.put("onLine", "0"+afterloanLinePaymentDtos.get(0).getLineflag());
				maps.put("pactNo", "KG"+ afterloanLinePaymentDtos.get(0).getOrderNo());
//				maps.put("pageNo", 1);
//				maps.put("pageSize", 20);
				
				String jsonObject = JSONObject.fromObject(maps).toString();  
				logger.error("还款状态查询请求参数："+jsonObject);
					RespDataObject<Map<String, Object>> ss=thirdApi.interfaceCall(maps);
				logger.error("还款状态请求返回结果："+ss);
				// ss.setCode("SUCCESS");
				
				if ("SUCCESS".equals(ss.getCode())) {
					
				
					JSONArray jsonArray=JSONArray.fromObject(ss.getData().get("list"));
					for (Object object : jsonArray) {
						
						  JSONObject json=  JSONObject.fromObject(object);// "{\"dealSts\":\"03\",\"dealDesc\":\"处理成功\"}");
						   logger.error("json："+json);
							   
							   if ("01".equals(json.get("dealSts"))) {
								   resp.setCode("SUCCESS");
								   resp.setMsg("未处理");
								   resp.setData(String.valueOf(json.get("dealDesc")));
							    	afterloanLinePaymentDtos.get(0).setExtendField5("未处理");
							    	tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));	
							    	
							    	status=9;
							    	
								}
							   if ("02".equals(json.get("dealSts"))) {
								   resp.setCode("SUCCESS");
								   resp.setMsg("处理中");
								   resp.setData(String.valueOf(json.get("dealDesc")));
							    	afterloanLinePaymentDtos.get(0).setExtendField5("处理中");
							    	tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));	
							    	status=9;
								}
							   if ("03".equals(json.get("dealSts"))) {
								   resp.setCode("SUCCESS");
								   resp.setMsg("处理成功");
								   resp.setData(String.valueOf(json.get("dealDesc")));
							    	
								   logger.error("total======"+total);	
								   logger.error("total2========："+total2);	
							   	 if (total.compareTo(total2) ==0 ) {
										status=3;
								 }
							   	 logger.error("total比较total2"+total.compareTo(total2));	
								 if (total.compareTo(total2) ==1  ) { 
												status=2;
								 }
								    	
								 if (!"处理成功".equals(afterloanLinePaymentDto.getExtendField5())) {
									
									 if (1==afterloanLinePaymentDto.getRepaymentMode() ) {//先息后本
											 givePayPrincipal=Arith.add((tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal()!=null? tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal():0)
												        , afterloanLinePaymentDto.getReceivedPrincipal());
										  
										  givePayInterest=Arith.add((tblFinanceAfterloanFirstInterestDto.getGivePayInterest()!=null? tblFinanceAfterloanFirstInterestDto.getGivePayInterest():0)
											        ,  afterloanLinePaymentDto.getReceivedInterest());
										
										  givePayOverdue=Arith.add((tblFinanceAfterloanFirstInterestDto.getGivePayOverdue()!=null? tblFinanceAfterloanFirstInterestDto.getGivePayOverdue():0)
											        , afterloanLinePaymentDto.getReceivedDefaultInterest());
										  
										  tblFinanceAfterloanFirstInterestDto.setGivePayPrincipal(givePayPrincipal);
										  tblFinanceAfterloanFirstInterestDto.setGivePayInterest(givePayInterest);
										  tblFinanceAfterloanFirstInterestDto.setGivePayOverdue(givePayOverdue);
									 }
									 
									 if (2==afterloanLinePaymentDto.getRepaymentMode() ) {//等额本息
												 givePayPrincipal=Arith.add((tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal()!=null? tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal():0)
													        , afterloanLinePaymentDto.getReceivedPrincipal());
											  
											  givePayInterest=Arith.add((tblFinanceAfterloanEqualInterestDto.getGivePayInterest()!=null? tblFinanceAfterloanEqualInterestDto.getGivePayInterest():0)
												        ,afterloanLinePaymentDto.getReceivedInterest());
											
											  givePayOverdue=Arith.add((tblFinanceAfterloanEqualInterestDto.getGivePayOverdue()!=null? tblFinanceAfterloanEqualInterestDto.getGivePayOverdue():0)
												        ,   afterloanLinePaymentDto.getReceivedDefaultInterest());
											  
											  tblFinanceAfterloanEqualInterestDto.setGivePayPrincipal(givePayPrincipal);
											  tblFinanceAfterloanEqualInterestDto.setGivePayInterest(givePayInterest);
											  tblFinanceAfterloanEqualInterestDto.setGivePayOverdue(givePayOverdue);
										 } 
								 }
								 afterloanLinePaymentDtos.get(0).setExtendField5("处理成功");
								 tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));	
								}
							   if ("06".equals(json.get("dealSts"))) {
								   resp.setCode("SUCCESS");
								   resp.setMsg("处理失败");
								   resp.setData(String.valueOf(json.get("dealDesc")));
							    	afterloanLinePaymentDtos.get(0).setExtendField5("处理失败");
							    	tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));	
							    	
							    	if ( "1".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							    		status=8;
									}
							    	if ( "2".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							    		status=10;
									}
								}
							    if ("05".equals(json.get("dealSts"))) {
							    	resp.setCode("SUCCESS");
							    	resp.setMsg("服务异常");
									   resp.setData(String.valueOf(json.get("dealDesc")));
							    	afterloanLinePaymentDtos.get(0).setExtendField5("服务异常");
							    	tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));	
							    	
							    	if ( "1".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							    		status=8;
									}
							    	if ( "2".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							    		status=10;
									}
								}
							    
							  
							
						}
				
				

						if (1==afterloanLinePaymentDto.getRepaymentMode() ) {//先息后本
							  tblFinanceAfterloanFirstInterestDto.setStatus(status);
							afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto);
							
							
							
							 //更新订单列表的还款状态
							 if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==tblFinanceAfterloanFirstInterestDto.getRepaymentPeriods()
										&&tblFinanceAfterloanFirstInterestDto.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
									nextFlow(String.valueOf(dto.get("orderNo")));
								}else if(tblFinanceAfterloanFirstInterestDto.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
									
								}else if(tblFinanceAfterloanFirstInterestDto.getStatus()==2) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(2);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
									
								}
								tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
							 
							
						}
				
						if (2==afterloanLinePaymentDto.getRepaymentMode() ) {//等额本息
							tblFinanceAfterloanEqualInterestDto.setStatus(status);
							afterloanEqualInterestService.update(tblFinanceAfterloanEqualInterestDto);
							
							
							 //更新订单列表的还款状态
							 if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==tblFinanceAfterloanEqualInterestDto.getRepaymentPeriods()
										&&tblFinanceAfterloanEqualInterestDto.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
									nextFlow(String.valueOf(dto.get("orderNo")));
								}else if(tblFinanceAfterloanEqualInterestDto.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
									
								}else if(tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(2);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
									
								}
								tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
							
							
							
					    }
				}else {
					
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg(ss.getMsg());
					resp.setData(ss.getMsg());
				}
			
			}else { //提前清贷
				
				
				TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto = new TblFinanceAfterloanFirstInterestDto();
				TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto = new TblFinanceAfterloanEqualInterestDto();
				int status = 0;
	
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("type", 2);
				maps.put("txCode", "2313");
				maps.put("brNo", Constants.SGT_WS_BRNO);
				maps.put("batNo", afterloanLinePaymentDtos.get(0).getExtendField3());
				maps.put("onLine", "0" + afterloanLinePaymentDtos.get(0).getLineflag());
				maps.put("pactNo", "KG" + afterloanLinePaymentDtos.get(0).getOrderNo());
				// maps.put("pageNo", 1);
				// maps.put("pageSize", 20);

				String jsonObject = JSONObject.fromObject(maps).toString();
				logger.error("还款状态查询请求参数：" + jsonObject);
				RespDataObject<Map<String, Object>> ss =  thirdApi.interfaceCall(maps);
				logger.error("还款状态请求返回结果：" + ss);
				//ss.setCode("SUCCESS");
				if ("SUCCESS".equals(ss.getCode())) {
					 JSONArray jsonArray=JSONArray.fromObject(ss.getData().get("list"));
					 for (Object object : jsonArray) {

					JSONObject json = JSONObject.fromObject(object);//"{\"dealSts\":\"03\",\"dealDesc\":\"处理成功\"}");
					logger.error("json：" + json);

					if ("01".equals(json.get("dealSts"))) {
						resp.setCode("SUCCESS");
						resp.setMsg("未处理");
						resp.setData(String.valueOf(json.get("dealDesc")));
						afterloanLinePaymentDtos.get(0).setExtendField5("未处理");
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));
						status = 9;
					}
					if ("02".equals(json.get("dealSts"))) {
						resp.setCode("SUCCESS");
						resp.setMsg("处理中");
						resp.setData(String.valueOf(json.get("dealDesc")));
						afterloanLinePaymentDtos.get(0).setExtendField5("处理中");
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));
						status = 9;
					}
					if ("03".equals(json.get("dealSts"))) {
						resp.setCode("SUCCESS");
						resp.setMsg("处理成功");
						resp.setData(String.valueOf(json.get("dealDesc")));
						status = 4;
						if (!"处理成功".equals(afterloanLinePaymentDto.getExtendField5())) {

							if (1 == afterloanLinePaymentDto.getRepaymentMode()) {// 先息后本

								TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto = new TblFinanceAfterloanFirstInterestDto();
								afterloanFirstInterestDto.setOrderNo(afterloanLinePaymentDto.getOrderNo());

								List<TblFinanceAfterloanFirstInterestDto> list = afterloanFirstInterestService
										.search(afterloanFirstInterestDto);

								for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto2 : list) {

									if (tblFinanceAfterloanFirstInterestDto2.getStatus() == 4
											|| tblFinanceAfterloanFirstInterestDto2.getStatus() == 10 || tblFinanceAfterloanFirstInterestDto2.getStatus() == 11 ) {

										tblFinanceAfterloanFirstInterestDto2.setGivePayPrincipal(tblFinanceAfterloanFirstInterestDto2.getRepayPrincipal());
										tblFinanceAfterloanFirstInterestDto2.setGivePayInterest(tblFinanceAfterloanFirstInterestDto2.getRepayInterest());
										tblFinanceAfterloanFirstInterestDto2.setGivePayOverdue(tblFinanceAfterloanFirstInterestDto2.getRepayOverdue());
										
										
										tblFinanceAfterloanFirstInterestDto2.setStatus(status);

										afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto2);
									}

								}

							}

							if (2 == afterloanLinePaymentDto.getRepaymentMode()) {// 等额本息

								TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto = new TblFinanceAfterloanEqualInterestDto();

								afterloanEqualInterestDto.setOrderNo(afterloanLinePaymentDto.getOrderNo());

								List<TblFinanceAfterloanEqualInterestDto> list = afterloanEqualInterestService
										.search(afterloanEqualInterestDto);

								for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto2 : list) {
									if (tblFinanceAfterloanEqualInterestDto2.getStatus() == 4
											|| tblFinanceAfterloanEqualInterestDto2.getStatus() == 10 || tblFinanceAfterloanEqualInterestDto2.getStatus() == 11) {
										tblFinanceAfterloanEqualInterestDto2.setGivePayPrincipal(tblFinanceAfterloanEqualInterestDto2.getRepayPrincipal());
										tblFinanceAfterloanEqualInterestDto2.setGivePayInterest(tblFinanceAfterloanEqualInterestDto2.getRepayInterest());
										tblFinanceAfterloanEqualInterestDto2.setGivePayOverdue(tblFinanceAfterloanEqualInterestDto2.getRepayOverdue());
										
										tblFinanceAfterloanEqualInterestDto2.setStatus(status);
										afterloanEqualInterestService.update(tblFinanceAfterloanEqualInterestDto2);

									}
								}

							}
							
							
							
							
					
						}
						afterloanLinePaymentDtos.get(0).setExtendField5("处理成功");
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));
						
								tblFinanceAfterloanListDto2.setRepaymentStatus(4);
								tblFinanceAfterloanListDto2.setRepaymentStatusName("提前结清");
								tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
								nextFlow(String.valueOf(dto.get("orderNo")));
						
						
					}
					if ("06".equals(json.get("dealSts"))) {
						resp.setCode("SUCCESS");
						resp.setMsg("处理失败");
						resp.setData(String.valueOf(json.get("dealDesc")));
						afterloanLinePaymentDtos.get(0).setExtendField5("处理失败");
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));

						if ("1".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							status = 8;
						}
						if ("2".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							status = 10;
						}
					}
					if ("05".equals(json.get("dealSts"))) {
						resp.setCode("SUCCESS");
						resp.setMsg("服务异常");
						resp.setData(String.valueOf(json.get("dealDesc")));
						afterloanLinePaymentDtos.get(0).setExtendField5("服务异常");
						tblFinanceAfterloanLinePaymentService.update(afterloanLinePaymentDtos.get(0));

						if ("1".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							status = 8;
						}
						if ("2".equals(afterloanLinePaymentDtos.get(0).getDeductionsType())) {
							status = 10;
						}
					}

			     }

				} else {

					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg(ss.getMsg());
					resp.setData(ss.getMsg());
				}
				
			}		

		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("还款状态信息查询失败");

		}

		return resp;
	}
		

		@Override
		public RespDataObject<TblFinanceAfterloanLinePaymentDto> repushPayment(@RequestBody TblFinanceAfterloanLinePaymentDto pdto) {
			RespDataObject<TblFinanceAfterloanLinePaymentDto> s = new RespDataObject<TblFinanceAfterloanLinePaymentDto>();
			RespDataObject ss = new RespDataObject<>();
			
			try {
				TblFinanceAfterloanLinePaymentDto dto=tblFinanceAfterloanLinePaymentService.search(pdto).get(0);
				
				TblFinanceAfterloanListDto tblFinanceAfterloanListDto=new TblFinanceAfterloanListDto();
				tblFinanceAfterloanListDto.setOrderNo(dto.getOrderNo());
				TblFinanceAfterloanListDto tblFinanceAfterloanListDto2= tblFinanceAfterloanListService.find(tblFinanceAfterloanListDto);
				
			//更新还款列表中的数据
			/*
			 * 1正常还款
			 */
			if ("1".equals(dto.getDeductionsType())) {
				Double repayPrincipalTotal=Double.valueOf(0) ;//'实收本金',
				Double repayInterestTotal=Double.valueOf(0) ;//'实收利息',
				Double repayOverdueTotal=Double.valueOf(0) ;//'实收利息',

			if (1==dto.getRepaymentMode() ) {//先息后本
				
				TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
				afterloanFirstInterestDto.setOrderNo(dto.getOrderNo());
				afterloanFirstInterestDto.setRepaymentPeriods(dto.getCurrentPeriod());
				List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
				
				if (list.size()>0) {
					TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto2=list.get(0);
					
					
					
					Double total=	0.0;//应还总额
					Double total2=	0.0 ;//实收总额
							Double	 t1=	Double.valueOf(list.get(0).getRepayPrincipal()!=null?list.get(0).getRepayPrincipal():0);
							Double	 t2	=Double.valueOf((list.get(0).getRepayInterest()!=null?list.get(0).getRepayInterest():0));
							Double	 t3	=new Double(Double.valueOf((list.get(0).getRepayOverdue()!=null?list.get(0).getRepayOverdue():0)));
							
							total=	Arith.add(Arith.add(	t1,t2),t3);
									
							Double	 t4=Double.valueOf((list.get(0).getGivePayPrincipal() !=null ? list.get(0).getGivePayPrincipal():0));
							Double	 t5=Double.valueOf((list.get(0).getGivePayInterest() !=null ? list.get(0).getGivePayInterest():0));
							Double	 t6=Double.valueOf((list.get(0).getGivePayOverdue() !=null ? list.get(0).getGivePayOverdue():0));
							Double	 t7=Double.valueOf((dto.getReceivedPrincipal() !=null ? dto.getReceivedPrincipal():0));
							Double	 t8=Double.valueOf((dto.getReceivedInterest()!=null ? dto.getReceivedInterest():0));
							Double	 t9=Double.valueOf((dto.getReceivedDefaultInterest()!=null ? dto.getReceivedDefaultInterest():0));
							total2=Arith.add(Arith.add(Arith.add(Arith.add(Arith.add(t4, t5), t6),t7),t8),t9);	
							
				
					
					afterloanFirstInterestDto2.setGivePayInterest(dto.getReceivedInterest()+list.get(0).getGivePayInterest());//'实收利息',
					afterloanFirstInterestDto2.setGivePayOverdue(dto.getReceivedDefaultInterest()+list.get(0).getGivePayOverdue());//'实收罚息',
				
			
				
				 
				    repayPrincipalTotal=Double.valueOf(dto.getReceivedPrincipal());
					repayInterestTotal=Double.valueOf(dto.getReceivedInterest());
					repayOverdueTotal=Double.valueOf(dto.getReceivedDefaultInterest());
				
					ss=    pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
					
				 
				 //提交成功后更新状态
					//提交成功后查询状态
					 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
						 
						 
						 Map<String, Object> map=new HashMap<String, Object>();
						 
						 map.put("orderNo", dto.getOrderNo());
						 map.put("currentPeriod", dto.getCurrentPeriod());
						 
						 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
						 logger.error("查询结果返回msg:"+resps);
						 if (total.compareTo(total2) ==0 && "处理成功".equals( resps.getMsg()) ) {
							 afterloanFirstInterestDto2.setStatus(3);
						 }
						 if (total.compareTo(total2) ==1 && "处理成功".equals( resps.getMsg()) ) { 
							 afterloanFirstInterestDto2.setStatus(2);
						 }
						 
						 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
							 afterloanFirstInterestDto2.setStatus(1);
				          } 
						 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
							 afterloanFirstInterestDto2.setStatus(9);
				          } 
						 
				
								afterloanFirstInterestService.update(afterloanFirstInterestDto2);
								
								
								
								if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==afterloanFirstInterestDto2.getRepaymentPeriods()
										&&afterloanFirstInterestDto2.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
									nextFlow(dto.getOrderNo());
								}else if(afterloanFirstInterestDto2.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
									
								}else if(afterloanFirstInterestDto2.getStatus()==2) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(2);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
									
								}
								tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
								
						 
					 }
			
						
		
				 
				
				}
				
			}
	       if (2==dto.getRepaymentMode()) {//等额本息
				
				TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
				afterloanEqualInterestDto.setOrderNo(dto.getOrderNo());
				afterloanEqualInterestDto.setRepaymentPeriods(dto.getCurrentPeriod());
				
				List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
				Double total=	Double.valueOf(0) ;//应还总额
				Double total2=	Double.valueOf(0) ;//实收总额
				if (list.size()>0) {
					TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto2=list.get(0);
				
					Double t1=Double.valueOf((list.get(0).getRepayPrincipal()!=null ? list.get(0).getRepayPrincipal()  :0));
					Double t2=Double.valueOf((list.get(0).getRepayInterest()!=null ? list.get(0).getRepayInterest():0));
					Double t3=Double.valueOf((list.get(0).getRepayOverdue()!=null? list.get(0).getRepayOverdue() :0));
					
					total=	Arith.add(Arith.add(	t1,t2),t3);
							
					
					Double t4=Double.valueOf((list.get(0).getGivePayPrincipal()!=null ? list.get(0).getGivePayPrincipal():0));
					Double t5=Double.valueOf((list.get(0).getGivePayInterest()!=null ? list.get(0).getGivePayInterest():0));
					Double t6=Double.valueOf((list.get(0).getGivePayOverdue()!=null ? list.get(0).getGivePayOverdue():0));
					Double t7=Double.valueOf((dto.getReceivedPrincipal()!=null ? dto.getReceivedPrincipal():0));
					Double t8=Double.valueOf((dto.getReceivedInterest()!=null ? dto.getReceivedInterest():0));
					Double t9=Double.valueOf((dto.getReceivedDefaultInterest()!=null ?dto.getReceivedDefaultInterest():0));
					
					total2=Arith.add(Arith.add(Arith.add(Arith.add(Arith.add(t4, t5), t6),t7),t8),t9);	
				
					afterloanEqualInterestDto2.setGivePayPrincipal(Arith.add((dto.getReceivedPrincipal()!=null ?  dto.getReceivedPrincipal():0)
							                                            ,(list.get(0).getGivePayPrincipal()!=null ? list.get(0).getGivePayPrincipal() :0)));//'实收本金',
					afterloanEqualInterestDto2.setGivePayInterest(Arith.add((dto.getReceivedInterest()!=null ?  dto.getReceivedInterest():0)
							                                      ,(list.get(0).getGivePayInterest()!=null ? list.get(0).getGivePayInterest() :0)));//'实收利息',
					afterloanEqualInterestDto2.setGivePayOverdue(Arith.add((dto.getReceivedDefaultInterest()!=null ?  dto.getReceivedDefaultInterest():0)
							                                 ,(list.get(0).getGivePayOverdue()!=null ? list.get(0).getGivePayOverdue() :0)));//'实收罚息',
					
					    repayPrincipalTotal=Double.valueOf(dto.getReceivedPrincipal());
						repayInterestTotal=Double.valueOf(dto.getReceivedInterest());
						repayOverdueTotal=Double.valueOf(dto.getReceivedDefaultInterest());
						
					
				
						 
						
						
						 ss=    pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
			
						//提交成功后查询状态
						 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
							 
							 
							 Map<String, Object> map=new HashMap<String, Object>();
							 
							 map.put("orderNo", dto.getOrderNo());
							 map.put("currentPeriod", dto.getCurrentPeriod());
							 
							 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
							 logger.error("查询结果返回msg:"+resps);
							 if (total.compareTo(total2) ==0 && "处理成功".equals( resps.getMsg()) ) {
									afterloanEqualInterestDto2.setStatus(3);
							 }
							 if (total.compareTo(total2) ==1 && "处理成功".equals( resps.getMsg()) ) { 
											afterloanEqualInterestDto2.setStatus(2);
							 }
							 
							 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
									afterloanEqualInterestDto2.setStatus(1);
					          } 
							 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
									afterloanEqualInterestDto2.setStatus(9);
					          } 
								afterloanEqualInterestService.update(afterloanEqualInterestDto2);
								
								
								if (Integer.valueOf(tblFinanceAfterloanListDto2.getBorrowingPeriods())==afterloanEqualInterestDto2.getRepaymentPeriods()
										&&afterloanEqualInterestDto2.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已结清");
									nextFlow(dto.getOrderNo());
								}else if(afterloanEqualInterestDto2.getStatus()==3) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(3);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("已还款");
									
								}else if(afterloanEqualInterestDto2.getStatus()==2) {
									
									tblFinanceAfterloanListDto2.setRepaymentStatus(2);
									tblFinanceAfterloanListDto2.setRepaymentStatusName("部分还款");
									
								}
								tblFinanceAfterloanListService.update(tblFinanceAfterloanListDto2);
								
								
								
						 }
			
				}
			}
	       
	       
			}
	       /*
			 * 2。提前清贷
			 */
			if ("2".equals(dto.getDeductionsType())) {
				Double repayPrincipalTotal= Double.valueOf(0)  ;//剩余全部'实收本金',
				Double repayInterestTotal=Double.valueOf(0) ;//剩余全部'实收利息',
				Double repayOverdueTotal=Double.valueOf(0) ;//剩余全部'实收利息',

				if (1==dto.getRepaymentMode() ) {//先息后本
					
					TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
					afterloanFirstInterestDto.setOrderNo(dto.getOrderNo());
					
					//查询剩余全部期数
				List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
					//跟新剩余期数为提前清贷
				for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto : list) {
					
					if (tblFinanceAfterloanFirstInterestDto.getStatus()==2 ) {
						repayPrincipalTotal= Arith.add(repayPrincipalTotal,
								         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0),
										Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal():0)) );
								
						repayInterestTotal= Arith.add(repayInterestTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0),
								Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayInterest():0)) );
							
						repayOverdueTotal= Arith.add(repayOverdueTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0),
								Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayOverdue():0)) );
					}
					
					if (tblFinanceAfterloanFirstInterestDto.getStatus()==1 ) {

						
						repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0));
						repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0));
						repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0));

						
					}
				}
					//提交陕国投
				 ss=       pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
				 //提交成功后更新状态
				 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
					 
					 
					 
					 for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto : list) {
							
							if (tblFinanceAfterloanFirstInterestDto.getStatus()==1 ||tblFinanceAfterloanFirstInterestDto.getStatus()==2) {
								tblFinanceAfterloanFirstInterestDto.setGivePayPrincipal(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal());//'实收本金',
								tblFinanceAfterloanFirstInterestDto.setGivePayInterest(tblFinanceAfterloanFirstInterestDto.getRepayInterest());//'实收利息',
								tblFinanceAfterloanFirstInterestDto.setGivePayOverdue(tblFinanceAfterloanFirstInterestDto.getRepayOverdue());//'实收利息',
								
//								tblFinanceAfterloanFirstInterestDto.setStatus(4);
//								afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto);
								
								
								 Map<String, Object> map=new HashMap<String, Object>();
								 
								 map.put("orderNo", dto.getOrderNo());
								 map.put("currentPeriod", dto.getCurrentPeriod());
								 
								 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
								 logger.error("查询结果返回msg:"+resps);
								 if ( "处理成功".equals( resps.getMsg()) ) {
									 tblFinanceAfterloanFirstInterestDto.setStatus(3);
								 }
							
								 
								 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
									 tblFinanceAfterloanFirstInterestDto.setStatus(8);
						          } 
								 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
									 tblFinanceAfterloanFirstInterestDto.setStatus(9);
						          } 
								 afterloanFirstInterestService.update(tblFinanceAfterloanFirstInterestDto);
								 
								 
								
								
							}
						}
					 
					 
					
					 
					 
					
				 }
					
				}
		       if (2==dto.getRepaymentMode()) {//等额本息
					
					TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
					
					afterloanEqualInterestDto.setOrderNo(dto.getOrderNo());
					
					//查询剩余全部期数
					List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
					//跟新剩余期数为提前清贷
					for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto : list) {
						
						if (tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
							repayPrincipalTotal= Arith.add(repayPrincipalTotal,
									         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0),
											Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal():0)) );
									
							repayInterestTotal= Arith.add(repayInterestTotal,
							         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0),
									Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayInterest():0)) );
								
							repayOverdueTotal= Arith.add(repayOverdueTotal,
							         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0),
									Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayOverdue():0)) );
					}
					
					if (tblFinanceAfterloanEqualInterestDto.getStatus()==1 ) {
						
					
						repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0));
						repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0));
						repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0));

					}
					}
					
					 ss=       pushTblFinanceAfterloanLinePaymentDto(dto,repayPrincipalTotal,repayInterestTotal,repayOverdueTotal);
					
					 //提交成功后更新状态
					 if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
						
						 for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto : list) {
								
								if (tblFinanceAfterloanEqualInterestDto.getStatus()==1 ||tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
									
								
									tblFinanceAfterloanEqualInterestDto.setGivePayPrincipal(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal());//'实收本金',
									tblFinanceAfterloanEqualInterestDto.setGivePayInterest(tblFinanceAfterloanEqualInterestDto.getRepayInterest());//'实收利息',
									tblFinanceAfterloanEqualInterestDto.setGivePayOverdue(tblFinanceAfterloanEqualInterestDto.getRepayOverdue());//'实收罚息',
									//tblFinanceAfterloanEqualInterestDto.setStatus(4);
								
							//	afterloanEqualInterestService.update(tblFinanceAfterloanEqualInterestDto);
								
								
	                       Map<String, Object> map=new HashMap<String, Object>();
								 
								 map.put("orderNo", dto.getOrderNo());
								 map.put("currentPeriod", dto.getCurrentPeriod());
								 
								 RespDataObject resps = 	 searchSgtLinePaymentStatus( map);
								 
								 logger.error("查询结果返回msg:"+resps);
								 if ( "处理成功".equals( resps.getMsg()) ) {
									 tblFinanceAfterloanEqualInterestDto.setStatus(3);
								 }
							
								 
								 if ( "处理失败".equals( resps.getMsg()) || "服务异常".equals( resps.getMsg())) { 
									 tblFinanceAfterloanEqualInterestDto.setStatus(8);
						          } 
								 if ( "未处理".equals( resps.getMsg()) || "处理中".equals( resps.getMsg())) { 
									 tblFinanceAfterloanEqualInterestDto.setStatus(9);
						          } 
								 afterloanEqualInterestService.update(tblFinanceAfterloanEqualInterestDto);
								 
								
								
								}
							}
					 }
				  }
		        
		        
				}
	       
	       s.setCode(ss.getCode());
	       s.setMsg(ss.getMsg());
			
			return s;
			}catch (Exception e) {
				logger.error("新增异常,参数："+pdto.toString(), e);
				return RespHelper.setFailDataObject(s, new TblFinanceAfterloanLinePaymentDto(), RespStatusEnum.FAIL.getMsg());
			}
		}


		@Override
		public RespDataObject<Map<String, Object>> getPrepaymentBalance(@RequestBody Map<String, Object> dto) {
			RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();

			
			Map<String, Object> retMap=new HashMap<String, Object>();
			try {
				
				Double repayPrincipalTotal= Double.valueOf(0)  ;//剩余全部'应收本金',
				Double repayInterestTotal=Double.valueOf(0) ;//剩余全部'应收利息',
				Double repayOverdueTotal=Double.valueOf(0) ;//剩余全部'应收利息',

				
				
				
				
				if (1==Integer.parseInt(String.valueOf(dto.get("repaymentType")) )) {//先息后本
					
					TblFinanceAfterloanFirstInterestDto afterloanFirstInterestDto=new TblFinanceAfterloanFirstInterestDto();
					afterloanFirstInterestDto.setOrderNo(String.valueOf(dto.get("orderNo")));
					
					//查询剩余全部期数
				List<TblFinanceAfterloanFirstInterestDto>	list=	afterloanFirstInterestService.search(afterloanFirstInterestDto);
					//跟新剩余期数为提前清贷
				for (TblFinanceAfterloanFirstInterestDto tblFinanceAfterloanFirstInterestDto : list) {
					
					if (tblFinanceAfterloanFirstInterestDto.getStatus()==2 ) {
						repayPrincipalTotal= Arith.add(repayPrincipalTotal,
								         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0),
										Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayPrincipal():0)) );
								
						repayInterestTotal= Arith.add(repayInterestTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0),
								Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayInterest():0)) );
							
						repayOverdueTotal= Arith.add(repayOverdueTotal,
						         Arith.sub(Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0),
								Double.valueOf(tblFinanceAfterloanFirstInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getGivePayOverdue():0)) );
					}
					
					if (tblFinanceAfterloanFirstInterestDto.getStatus()==1 ) {

						
						repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanFirstInterestDto.getRepayPrincipal():0));
						repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayInterest()!=null?tblFinanceAfterloanFirstInterestDto.getRepayInterest():0));
						repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanFirstInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanFirstInterestDto.getRepayOverdue():0));

						
					}
					
				}
				     
				}
				if (2==Integer.parseInt(String.valueOf(dto.get("repaymentType")) )) {//等额本息
						
						TblFinanceAfterloanEqualInterestDto afterloanEqualInterestDto=new TblFinanceAfterloanEqualInterestDto();
						
						afterloanEqualInterestDto.setOrderNo(String.valueOf(dto.get("orderNo")));
						
						//查询剩余全部期数
						List<TblFinanceAfterloanEqualInterestDto> list	=afterloanEqualInterestService.search(afterloanEqualInterestDto);
						//跟新剩余期数为提前清贷
						for (TblFinanceAfterloanEqualInterestDto tblFinanceAfterloanEqualInterestDto : list) {
							
							
							if (tblFinanceAfterloanEqualInterestDto.getStatus()==2) {
									repayPrincipalTotal= Arith.add(repayPrincipalTotal,
											         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0),
													Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayPrincipal():0)) );
											
									repayInterestTotal= Arith.add(repayInterestTotal,
									         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0),
											Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayInterest():0)) );
										
									repayOverdueTotal= Arith.add(repayOverdueTotal,
									         Arith.sub(Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0),
											Double.valueOf(tblFinanceAfterloanEqualInterestDto.getGivePayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getGivePayOverdue():0)) );
							}
							
							if (tblFinanceAfterloanEqualInterestDto.getStatus()==1 ) {
								
							
								repayPrincipalTotal=Arith.add(repayPrincipalTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayPrincipal()!=null?tblFinanceAfterloanEqualInterestDto.getRepayPrincipal():0));
								repayInterestTotal=Arith.add(repayInterestTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayInterest()!=null?tblFinanceAfterloanEqualInterestDto.getRepayInterest():0));
								repayOverdueTotal=Arith.add(repayOverdueTotal,Double.valueOf(tblFinanceAfterloanEqualInterestDto.getRepayOverdue()!=null?tblFinanceAfterloanEqualInterestDto.getRepayOverdue():0));

							}
						}
						
						  
				}
				
				retMap.put("repayPrincipalTotal", repayPrincipalTotal);//
				retMap.put("repayInterestTotal", repayInterestTotal);//
				retMap.put("repayOverdueTotal", repayOverdueTotal);//
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setData(retMap); 
			      
			} catch (Exception e) {
				
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("查选清贷余额失败！"); 
				
                  e.printStackTrace();
			
			}
			
			
			return resp;
			
			
			
			
			
			
		}
		
		/**
		 * 走流程
		 * @param orderNo
		 */
		public void nextFlow(String orderNo) {
			BaseListDto orderDto = new BaseListDto();
			FlowDto flowDto = new FlowDto();
			orderDto.setOrderNo(orderNo);
			orderDto.setState("抵押品出库");
			orderDto.setProcessId("fddMortgageRelease");
			FlowDto flow = new FlowDto();
			flow.setOrderNo(orderNo);
			flow.setCurrentProcessId("fddMortgageStorage");
			RespData<FlowDto> re = orderApi.selectOrderFlowList(flow);
			String currentHandlerUid ="";
			String currentHandler ="";
			if(RespStatusEnum.SUCCESS.getCode().equals(re.getCode())&&re.getData()!=null&&re.getData().size()>0) {
				List<FlowDto> flowList = re.getData();
				for (FlowDto temp : flowList) {
					currentHandlerUid = temp.getHandleUid();
					currentHandler = temp.getHandleName();
				}
			}
			if(StringUtil.isBlank(currentHandlerUid)) {
				BaseListDto baseListDto = orderApi.findByListOrderNo(orderNo);
				currentHandlerUid = baseListDto.getAcceptMemberUid();
				currentHandler = baseListDto.getAcceptMemberName();
			}
			orderDto.setCurrentHandlerUid(currentHandlerUid);
			orderDto.setCurrentHandler(currentHandler);
			flowDto.setOrderNo(orderNo);
			flowDto.setCurrentProcessId("fddRepayment");
			flowDto.setCurrentProcessName("还款");
			UserDto user = userApi.getUserDto();
			flowDto.setHandleUid(user.getUid());  //当前处理人
			flowDto.setHandleName(user.getName());
			flowDto.setNextProcessId("fddMortgageRelease");
			flowDto.setNextProcessName("抵押品出库");
			flowDto.setCreateUid(user.getUid());
			orderApi.editOrderList(orderDto);
			orderApi.addOrderFlow(flowDto);
		}
}