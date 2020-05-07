/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseHousePropertypeopleDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.DistributionMemberDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.finance.IApplyLoanController;
import com.anjbo.service.element.DocumentsService;
import com.anjbo.service.element.ForeclosureTypeService;
import com.anjbo.service.finance.ApplyLoanService;
import com.anjbo.service.finance.FinanceLogService;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseHousePropertypeopleService;
import com.anjbo.service.order.BaseHouseService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.DistributionMemberService;
import com.anjbo.service.risk.AllocationFundService;
import com.esotericsoftware.minlog.Log;

/**
 * 申请放款
 * @Author ANJBO Generator
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class ApplyLoanController extends OrderBaseController implements IApplyLoanController {

	@Resource
	private ApplyLoanService applyLoanService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;

	@Resource
	private BaseBorrowService baseBorrowService;
	
	@Resource
	private BaseListService baseListService;

	@Resource
	private FinanceLogService financeLogService;

	@Resource
	private AllocationFundService allocationFundService;
	
	@Resource
	private DocumentsService documentsService;
	
	@Resource
	private DistributionMemberService distributionMemberService;
	
	@Resource
	private BaseHouseService baseHouseService;
	
	@Resource
	private BaseHousePropertypeopleService baseHousePropertypeopleService;
	
	@Resource
	private ForeclosureTypeService foreclosureTypeService;
	
	Logger log = Logger.getLogger(ApplyLoanController.class);
	
	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody ApplyLoanDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			ApplyLoanDto temp = new ApplyLoanDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = applyLoanService.find(temp);
			if (temp == null) {
				applyLoanService.insert(dto);
			} else {
				FinanceLogDto financeLogDto = new FinanceLogDto();
				financeLogDto.setCreateUid(userDto.getUid());
				financeLogDto.setUpdateUid(userDto.getUid());
				financeLogDto.setType(1);
				if (!temp.getBorrowerName().equals(dto.getBorrowerName())) {
					financeLogDto.setBeanColumn("borrowerName");
					financeLogDto.setColName("客户姓名");
					financeLogDto.setStartVal(dto.getBorrowerName());
					financeLogDto.setEndVal(temp.getBorrowerName());
					financeLogService.insert(financeLogDto);
				}
				if (temp.getBorrowingDays() != dto.getBorrowingDays()) {
					financeLogDto.setBeanColumn("borrowingDays");
					financeLogDto.setColName("借款期限");
					financeLogDto.setStartVal(dto.getBorrowingDays() + "天");
					financeLogDto.setEndVal(temp.getBorrowingDays() + "天");
					financeLogService.insert(financeLogDto);
				}
				if (StringUtils.isNotEmpty(temp.getBankName()) && !temp.getBankName().equals(dto.getBankName())) {
					financeLogDto.setBeanColumn("bankName");
					financeLogDto.setColName("银行卡户名");
					financeLogDto.setStartVal(dto.getBankName());
					financeLogDto.setEndVal(temp.getBankName());
					financeLogService.insert(financeLogDto);
				}
				if (null != temp.getBankAccount() && !temp.getBankAccount().equals(dto.getBankAccount())) {
					financeLogDto.setBeanColumn("bankAccount");
					financeLogDto.setColName("银行卡账号");
					financeLogDto.setStartVal(dto.getBankAccount());
					financeLogDto.setEndVal(temp.getBankAccount());
					financeLogService.insert(financeLogDto);
				}
				if (temp.getLendingBankId() != dto.getLendingBankId()
						&& !(dto.getLendingBankId()).equals(temp.getLendingBankId())) {
					financeLogDto.setBeanColumn("lendingBankId");
					financeLogDto.setColName("放款银行");
					if (StringUtils.isNotEmpty(temp.getLendingBankId())) {
						BankDto bankDto = new BankDto();
						bankDto.setId(Integer.valueOf(temp.getLendingBankId()));
						bankDto = dataApi.getBankNameById(bankDto).getData();
						financeLogDto.setStartVal(bankDto.getName());
					}
					if (StringUtils.isNotEmpty(dto.getLendingBankId())) {
						BankDto bankDto = new BankDto();
						bankDto.setId(Integer.valueOf(dto.getLendingBankId()));
						bankDto = dataApi.getBankNameById(bankDto).getData();
						financeLogDto.setEndVal(bankDto.getName());
					}
					financeLogService.insert(financeLogDto);
				}
				if (dto.getLendingBankSubId() != temp.getLendingBankSubId()
						&& !dto.getLendingBankSubId().equals(temp.getLendingBankSubId())) {
					financeLogDto.setBeanColumn("lendingBankSubId");
					financeLogDto.setColName("放款支行");
					if (StringUtils.isNotEmpty(temp.getLendingBankSubId())) {
						BankSubDto bankSubDto = new BankSubDto();
						bankSubDto.setId(Integer.valueOf(temp.getLendingBankSubId()));
						bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
						financeLogDto.setStartVal(bankSubDto.getName());
					}
					if (StringUtils.isNotEmpty(dto.getLendingBankSubId())) {
						BankSubDto bankSubDto = new BankSubDto();
						bankSubDto.setId(Integer.valueOf(dto.getLendingBankSubId()));
						bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
						financeLogDto.setEndVal(bankSubDto.getName());
					}
					financeLogService.insert(financeLogDto);
				}
				applyLoanService.update(dto);
			}

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("applyLoan");
			flowDto.setCurrentProcessName("申请放款");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());

			boolean fl = false;
			boolean isSGT = false;
			String handlerUid = "";
			
			AllocationFundDto allocationFundDto = new AllocationFundDto();
			allocationFundDto.setOrderNo(dto.getOrderNo());
			List<AllocationFundDto> allocationFundDtos = allocationFundService.search(allocationFundDto);
			for (AllocationFundDto fundDto : allocationFundDtos) {
				if(StringUtils.isNotEmpty(fundDto.getFinanceUid())) {
					handlerUid = fundDto.getFinanceUid();
				}
				if (37 == fundDto.getFundId() || 38 == fundDto.getFundId() ) {
					fl = true;
				}
				if(36 == fundDto.getFundId()) {
					isSGT = true;
					log.info("陕国投申请放款");
				}
			}

			BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
			baseBorrowDto.setOrderNo(dto.getOrderNo());
			baseBorrowDto = baseBorrowService.find(baseBorrowDto);
			if("04".equals(baseBorrowDto.getProductCode())) {//申请放款后收费，陕国投核实收费后财务制单
				//非陕国投不走财务制单
//				if(isSGT) {
//					flowDto.setNextProcessId("financialStatement");
//					flowDto.setNextProcessName("财务制单");
//					log.info("下一节点：财务制单");
//				}else {
					flowDto.setNextProcessId("charge");
					flowDto.setNextProcessName("收费");
					log.info("下一节点：收费");
//				}
			}else if("03".equals(baseBorrowDto.getProductCode())) {
				flowDto.setNextProcessId("isLendingHarvest");
				flowDto.setNextProcessName("收利息"); 
			}else if("06".equals(baseBorrowDto.getProductCode())||"07".equals(baseBorrowDto.getProductCode())) {
				flowDto.setNextProcessId("lendingHarvest");
				flowDto.setNextProcessName("核实利息"); 
			}else{
				if(baseBorrowDto.getPaymentMethod()==1){
					flowDto.setNextProcessId("isLendingHarvest");
					flowDto.setNextProcessName("收利息"); 
				}else {
					if(fl) {
						flowDto.setNextProcessId("financialStatement");
						flowDto.setNextProcessName("财务制单");
					}else {
						flowDto.setNextProcessId("lending");
						flowDto.setNextProcessName("放款");
					}
				}
			}
			
			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
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
	 * @author lic
	 */
	@Override
	public RespDataObject<Map<String, Object>> processDetails(@RequestBody ApplyLoanDto dto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dto = applyLoanService.find(dto);
			if(dto != null) {
				if(StringUtils.isNotEmpty(dto.getLendingBankId())) {
					BankDto bankDto = new BankDto();
					bankDto.setId(Integer.valueOf(dto.getLendingBankId()));
					bankDto = dataApi.getBankNameById(bankDto).getData();
					dto.setLendingBank(bankDto.getName());
				}
				if(StringUtils.isNotEmpty(dto.getLendingBankSubId())) {
					BankSubDto bankSubDto = new BankSubDto();
					bankSubDto.setId(Integer.valueOf(dto.getLendingBankSubId()));
					bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
					dto.setLendingBankSub(bankSubDto.getName());
				}
				BaseListDto baseListDto = new BaseListDto();
				baseListDto.setOrderNo(dto.getOrderNo());
				baseListDto = baseListService.find(baseListDto);
				map.put("logDtos", new ArrayList<Map<String, Object>>());
				if(baseListDto != null && "04".equals(baseListDto.getProductCode())) {
					FinanceLogDto financeLogDto = new FinanceLogDto();
					financeLogDto.setOrderNo(dto.getOrderNo());
					financeLogDto.setType(1);
					map.put("logDtos", financeLogService.search(financeLogDto));
				}
			}
			map.put("loanDto", dto);
			
			return RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	/**
	 * 校验银行卡户名
	 * @param request
	 * @param response
	 * @param loanDto
	 * @return
	 */
	@Override
	public RespStatus processValidBankCardName(@RequestBody ApplyLoanDto loanDto) {
 		RespStatus status=new RespStatus();
 		try {
			status.setCode(RespStatusEnum.SYSTEM_ERROR.getCode());
			status.setMsg("你修改的银行卡户名异常，确定修改吗？");
			boolean isName=false;
			String name=loanDto.getBankName();
			//对比要件校验
			ForeclosureTypeDto fcdto = new ForeclosureTypeDto();
			fcdto.setOrderNo(loanDto.getOrderNo());
			fcdto = foreclosureTypeService.find(fcdto);
			logger.info("银行卡户名："+name+"---要件信息"+fcdto);
			if(name.equals(fcdto.getBankCardMaster())){
				isName=true;
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
				return status;
			}
			
			//对比借款人
			BaseBorrowDto orderBaseBorrowDto = new BaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(loanDto.getOrderNo());
			BaseBorrowDto baseBorrowDto = baseBorrowService.find(orderBaseBorrowDto);
			if(baseBorrowDto!=null){
				 if(name.equals(baseBorrowDto.getBorrowerName())){
					isName=true;
					status.setCode(RespStatusEnum.SUCCESS.getCode());
					status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return status;
				 }
			}
			//对比还款专员
			DistributionMemberDto memberDto=new DistributionMemberDto();
			memberDto.setOrderNo(loanDto.getOrderNo());
			memberDto = distributionMemberService.find(memberDto);
			if(memberDto!=null){
				 String uid=memberDto.getForeclosureMemberUid();
				 UserDto userDto = userApi.findUserDtoByUid(uid);
				 if(userDto!=null && name.equals(userDto.getName())){
					isName=true;
					status.setCode(RespStatusEnum.SUCCESS.getCode());
					status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return status;
				 }
			}
			//对比产权人
			BaseHousePropertypeopleDto hpDto = new BaseHousePropertypeopleDto();
			hpDto.setOrderNo(loanDto.getOrderNo());
			List<BaseHousePropertypeopleDto> hpList = baseHousePropertypeopleService.search(hpDto);
			for (BaseHousePropertypeopleDto hp : hpList) {
				if(name.equals(hp.getPropertyName())){
					isName=true;
					break;
				}
			}
			if(isName){
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setCode(RespStatusEnum.FAIL.getMsg());
		}
 		return status;
 	}
}