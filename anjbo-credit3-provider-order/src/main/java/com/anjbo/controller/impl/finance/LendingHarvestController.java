/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.DistributionMemberDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.ILendingHarvestController;
import com.anjbo.service.finance.FinanceLogService;
import com.anjbo.service.finance.LendingHarvestService;
import com.anjbo.service.finance.LendingService;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.process.DistributionMemberService;
import com.anjbo.service.risk.AllocationFundService;

/**
 * 核实利息/扣回后置的订单
 * @Author ANJBO Generator
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class LendingHarvestController extends OrderBaseController implements ILendingHarvestController {

	@Resource
	private LendingHarvestService lendingHarvestService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;

	@Resource
	private BaseBorrowService baseBorrowService;
	
	@Resource
	private FinanceLogService financeLogService;

	@Resource
	private AllocationFundService allocationFundService;
	
	@Resource
	private LendingService lendingService;
	
	@Resource
	private DistributionMemberService distributionMemberService;
	
	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody LendingHarvestDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			LendingHarvestDto temp = new LendingHarvestDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = lendingHarvestService.find(temp);
			if (temp == null) {
				lendingHarvestService.insert(dto);
			} else {
				lendingHarvestService.update(dto);
			}

			BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
			baseBorrowDto.setOrderNo(dto.getOrderNo());
			baseBorrowDto = baseBorrowService.find(baseBorrowDto);

			FinanceLogDto logDto = new FinanceLogDto();
			logDto.setCreateUid(userDto.getUid());
			logDto.setUpdateUid(userDto.getUid());
			logDto.setOrderNo(dto.getOrderNo());
			if (dto.getType() == 1) {
				logDto.setType(2);
			} else {
				logDto.setType(3);
			}
			BaseBorrowDto baseBorrowTemp = new BaseBorrowDto();
			if (baseBorrowDto.getRiskGradeId() != null && !(dto.getRiskGradeId() + "").equals(baseBorrowDto.getRiskGradeId() + "")) {
				logDto.setBeanColumn("riskGradeId");
				logDto.setColName("收费类型");
				List<DictDto> dictDtos = dataApi.getDictDtoListByType("riskControl");
				for (DictDto dictDto : dictDtos) {
					if (dictDto.getCode().equals(String.valueOf(baseBorrowDto.getRiskGradeId()))) {
						logDto.setStartVal(dictDto.getName());
					}
					if (baseBorrowDto.getRiskGradeId() != null && baseBorrowDto.getRiskGradeId() == 0) {
						logDto.setStartVal("其他");
					}
					if (dictDto.getCode().equals(String.valueOf(dto.getRiskGradeId()))) {
						logDto.setEndVal(dictDto.getName());
					}
					if (dto.getRiskGradeId() != null && dto.getRiskGradeId() == 0) {
						logDto.setEndVal("其他");
					}
				}
				baseBorrowTemp.setRiskGradeId(dto.getRiskGradeId());
				financeLogService.insert(logDto);
			}
			if(baseBorrowDto.getChargeMoney()!=null && !equal(dto.getChargeMoney(),baseBorrowDto.getChargeMoney())){
				logDto.setBeanColumn("chargeMoney");
				logDto.setColName("收费金额");
				logDto.setStartVal(baseBorrowDto.getChargeMoney()+"元");
				logDto.setEndVal(dto.getChargeMoney()+"元");
				baseBorrowTemp.setChargeMoney(dto.getChargeMoney());
				financeLogService.insert(logDto);
			}
			
			if(baseBorrowDto.getServiceCharge()!=null && !equal(dto.getServiceCharge(),baseBorrowDto.getServiceCharge())){
				logDto.setBeanColumn("serviceCharge");
				logDto.setColName("服务费");
				logDto.setStartVal(baseBorrowDto.getServiceCharge()+"元");
				logDto.setEndVal(dto.getServiceCharge()+"元");
				baseBorrowTemp.setServiceCharge(dto.getServiceCharge());
				financeLogService.insert(logDto);
			}
			if(!"03".equals(baseBorrowDto.getProductCode()) && baseBorrowDto.getCustomsPoundage()!=null && !equal(dto.getCustomsPoundage(),baseBorrowDto.getCustomsPoundage())){
				logDto.setBeanColumn("customsPoundage");
				logDto.setColName("关外手续费");
				logDto.setStartVal(baseBorrowDto.getCustomsPoundage()+"元");
				logDto.setEndVal(dto.getCustomsPoundage()+"元");
				baseBorrowTemp.setCustomsPoundage(dto.getCustomsPoundage());
				financeLogService.insert(logDto);
			}
			if(baseBorrowDto.getOtherPoundage()!=null && !equal(dto.getOtherPoundage(),baseBorrowDto.getOtherPoundage())){
				logDto.setBeanColumn("otherPoundage");
				logDto.setColName("其他费用");
				logDto.setStartVal(baseBorrowDto.getOtherPoundage()+"元");
				logDto.setEndVal(dto.getOtherPoundage()+"元");
				baseBorrowTemp.setOtherPoundage(dto.getOtherPoundage());  //其他费用
				financeLogService.insert(logDto);
			}
			if(!equal(dto.getRate(),baseBorrowDto.getRate())){
				logDto.setBeanColumn("rate");
				logDto.setColName("费率");
				if(0!=dto.getRiskGradeId()){
					logDto.setStartVal(baseBorrowDto.getRate()+"%");
					logDto.setEndVal(dto.getRate()+"%");
				}else{  //按天
					logDto.setStartVal(baseBorrowDto.getRate()+"%/天");
					logDto.setEndVal(dto.getRate()+"%/天");
				}
				baseBorrowTemp.setRate(dto.getRate());
				financeLogService.insert(logDto);
			}
			if(!equal(dto.getOverdueRate(),baseBorrowDto.getOverdueRate())){
				logDto.setBeanColumn("overdueRate");
				logDto.setColName("逾期费率");
				if(0!=dto.getRiskGradeId()){
					logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%");
					logDto.setEndVal(dto.getOverdueRate()+"%");
				}else{  //按天
					logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%/天");
					logDto.setEndVal(dto.getOverdueRate()+"%/天");
				}
				baseBorrowTemp.setOverdueRate(dto.getOverdueRate());
				financeLogService.insert(logDto);
			}
			baseBorrowTemp.setOrderNo(dto.getOrderNo());
			baseBorrowService.update(baseBorrowTemp);

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());
			String handlerUid = "";
			if(dto.getType()==2){
				flowDto.setCurrentProcessId("backExpenses");
				flowDto.setCurrentProcessName("扣回后置费用");
				flowDto.setNextProcessId("foreclosure");
				flowDto.setNextProcessName("结清原贷款");
				DistributionMemberDto distributionMemberDto = new DistributionMemberDto();
				distributionMemberDto.setOrderNo(dto.getOrderNo());
				distributionMemberDto = distributionMemberService.find(distributionMemberDto);
				if(distributionMemberDto != null && StringUtils.isNotEmpty(distributionMemberDto.getForeclosureMemberUid())) {
					handlerUid = distributionMemberDto.getForeclosureMemberUid();
				}
				
				//更新放款表 回款处理人
				LendingDto lendingDto = new LendingDto();
				lendingDto.setOrderNo(dto.getOrderNo());
				lendingDto.setReceivableForUid(dto.getUid());
				lendingService.update(lendingDto);
			}else {
				AllocationFundDto allocationFundDto = new AllocationFundDto();
				allocationFundDto.setOrderNo(dto.getOrderNo());
				List<AllocationFundDto> allocationFundDtos = allocationFundService.search(allocationFundDto);
				flowDto.setCurrentProcessId("lendingHarvest");
				flowDto.setCurrentProcessName("核实利息");
				if("05".equals(baseBorrowDto.getProductCode())) {//提放
					flowDto.setNextProcessId("uploadInsurancePolicy");
					flowDto.setNextProcessName("上传电子保单");
					for (AllocationFundDto fundDto : allocationFundDtos) {
						handlerUid = fundDto.getCreateUid();
					}
				}else {
					flowDto.setNextProcessId("lending");
					flowDto.setNextProcessName("放款");
					
					for (AllocationFundDto fundDto : allocationFundDtos) {
						if (37 == fundDto.getFundId() || 38 == fundDto.getFundId() ) {
							flowDto.setNextProcessId("financialStatement");
							flowDto.setNextProcessName("财务制单");
						}
					}
					if(StringUtils.isNotBlank(dto.getUid())) {
						handlerUid = dto.getUid();
					}else {
						for (AllocationFundDto fundDto : allocationFundDtos) {
							handlerUid = fundDto.getCreateUid();
						}
					}
				}
			}
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			goNextNode(flowDto, baseListDto);
			
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("提交异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 详情
	 * 
	 * @author lic
	 */
	@Override
	public RespDataObject<Map<String, Object>> processDetails(@RequestBody LendingHarvestDto harvestDto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			//借款信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
			Map<String, Object> map=new HashMap<String, Object>();
			LendingHarvestDto lendingHarvestDto =new LendingHarvestDto();
			int type = 0;
			try {
				BaseBorrowDto orderBaseBorrowDto = new BaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(harvestDto.getOrderNo());
				BaseBorrowDto baseBorrowDto = baseBorrowService.find(orderBaseBorrowDto);
				//获取费用支付方式
				harvestDto.setType(baseBorrowDto.getPaymentMethod());
				lendingHarvestDto.setOrderNo(harvestDto.getOrderNo());
				lendingHarvestDto = lendingHarvestService.find(lendingHarvestDto);	
				if(lendingHarvestDto==null){
					lendingHarvestDto=new LendingHarvestDto();
					lendingHarvestDto.setOrderNo(harvestDto.getOrderNo());
				}else{
					type = lendingHarvestDto.getType();
				}
				if(baseBorrowDto!=null){
					lendingHarvestDto.setIsOnePay(baseBorrowDto.getIsOnePay());  //是否一次性付款
				    lendingHarvestDto.setProductCode(baseBorrowDto.getProductCode());  //产品编码
				    lendingHarvestDto.setCityCode(baseBorrowDto.getCityCode());
				    lendingHarvestDto.setCooperativeAgencyId(baseBorrowDto.getCooperativeAgencyId());  //合作机构Id
				    lendingHarvestDto.setServiceCharge(baseBorrowDto.getServiceCharge());//服务费
				    lendingHarvestDto.setCustomsPoundage(baseBorrowDto.getCustomsPoundage()); //关外手续费
				    lendingHarvestDto.setOtherPoundage(baseBorrowDto.getOtherPoundage());  //其他费用
					lendingHarvestDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
					lendingHarvestDto.setBorrowingDays(baseBorrowDto.getBorrowingDays());  //借款期限
					lendingHarvestDto.setRate(baseBorrowDto.getRate());					//费率
				    lendingHarvestDto.setOverdueRate(baseBorrowDto.getOverdueRate());		//逾期费率
				    lendingHarvestDto.setChargeMoney(baseBorrowDto.getChargeMoney());		//收费金额
				    lendingHarvestDto.setRiskGradeId(baseBorrowDto.getRiskGradeId());  	//风控等级
				    if(baseBorrowDto.getRiskGradeId()!=null&&baseBorrowDto.getRiskGradeId()>0){
				    	//风控等级名称
						List<DictDto> dicts = dataApi.getDictDtoListByType("riskControl");
						if(dicts!=null&&dicts.size()>0){
							for (DictDto dictDto : dicts) {
								if(dictDto.getCode().equals(String.valueOf(baseBorrowDto.getRiskGradeId()))){
									lendingHarvestDto.setRiskGrade(dictDto.getName());
								}
								if(baseBorrowDto.getRiskGradeId()!=null&&baseBorrowDto.getRiskGradeId()==0){
									lendingHarvestDto.setRiskGrade("其他");
								}
							}
						}
				    	
				    }
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			map.put("harvest", lendingHarvestDto);
			FinanceLogDto logDto=new FinanceLogDto();
			logDto.setOrderNo(harvestDto.getOrderNo());
			logDto.setType(type);
			List<FinanceLogDto> logDtos=financeLogService.search(logDto);
			map.put("logDtos", logDtos);
			return RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + harvestDto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
    public boolean equal(double a, double b) {
        if ((a- b> -0.000001) && (a- b) < 0.000001)
            return true;
        else
            return false;
    }

}