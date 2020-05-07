/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppFddMortgageStorageDto;
import com.anjbo.bean.process.AppForensicsDto;
import com.anjbo.bean.process.DistributionMemberDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.ILendingController;
import com.anjbo.service.finance.ApplyLoanService;
import com.anjbo.service.finance.LendingService;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.AppFddMortgageStorageService;
import com.anjbo.service.process.AppForensicsService;
import com.anjbo.service.process.DistributionMemberService;
import com.anjbo.service.risk.AuditFinalService;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class LendingController extends OrderBaseController implements ILendingController {

	@Resource
	private LendingService lendingService;

	@Resource
	private UserApi userApi;

	@Resource
	private BaseListService baseListService;

	@Resource
	private BaseBorrowService baseBorrowService;

	@Resource
	private AuditFinalService auditFinalService;

	@Resource
	private AppFddMortgageStorageService appFddMortgageStorageService;

	@Resource
	private AppForensicsService appForensicsService;
	
	@Resource
	private DistributionMemberService distributionMemberService;
	
	@Resource
	private ApplyLoanService applyLoanService;
	
	@Resource
	private DataApi dataApi;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody LendingDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			LendingDto temp = new LendingDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = lendingService.find(temp);
			if (temp == null) {
				lendingService.insert(dto);
			} else {
				lendingService.update(dto);
			}

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("lending");
			flowDto.setCurrentProcessName("放款");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());

			String handlerUid = "";
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);

			if ("03".equals(baseListDto.getProductCode())) {
				if (StringUtils.isNotEmpty(baseListDto.getRelationOrderNo())) {
					handlerUid = dto.getReceivableForUid();
					flowDto.setNextProcessId("receivableFor");
					flowDto.setNextProcessName("回款");
				} else {
					handlerUid = baseListDto.getAcceptMemberUid();
					flowDto.setNextProcessId("mortgage");
					flowDto.setNextProcessName("抵押");
				}
			} else if("06".equals(baseListDto.getProductCode())||"07".equals(baseListDto.getProductCode())){
				handlerUid = dto.getReceivableForUid();
				flowDto.setNextProcessId("receivableFor");
				flowDto.setNextProcessName("回款");
			} else if ("04".equals(baseListDto.getProductCode())) {
				
				//==============房抵押转贷后系统start==============
				
				//TODO 
				
				//==============房抵押转贷后系统end==============
				
				AuditFinalDto auditFinalDto = new AuditFinalDto();
				auditFinalDto.setOrderNo(dto.getOrderNo());
				auditFinalDto = auditFinalService.find(auditFinalDto);
				String paymentType = Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName();// 凭他项权利证放款
				if (paymentType.equals(auditFinalDto.getPaymentType())) { // 凭他项
					AppFddMortgageStorageDto appFddMortgageStorageDto = new AppFddMortgageStorageDto();
					appFddMortgageStorageDto.setOrderNo(dto.getOrderNo());
					appFddMortgageStorageDto = appFddMortgageStorageService.find(appFddMortgageStorageDto);
					if (appFddMortgageStorageDto != null
							&& StringUtils.isNotEmpty(appFddMortgageStorageDto.getCollateralUid())) {
						handlerUid = appFddMortgageStorageDto.getCollateralUid();
					} else {
						handlerUid = baseListDto.getAcceptMemberUid();
					}
					flowDto.setNextProcessId("fddMortgageStorage");
					flowDto.setNextProcessName("抵押品入库");
				} else {
					AppForensicsDto appForensicsDto = new AppForensicsDto();
					appForensicsDto.setOrderNo(dto.getOrderNo());
					appForensicsDto = appForensicsService.find(appForensicsDto);
					if (appForensicsDto != null && StringUtils.isNotEmpty(appForensicsDto.getLicenseReverUid())) {
						handlerUid = appForensicsDto.getLicenseReverUid();
					}
					flowDto.setNextProcessId("fddForensics");
					flowDto.setNextProcessName("取证");

				}
			} else {
				BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
				baseBorrowDto.setOrderNo(dto.getOrderNo());
				baseBorrowDto = baseBorrowService.find(baseBorrowDto);
				if (baseBorrowDto.getPaymentMethod() == 1) {
					DistributionMemberDto distributionMemberDto = new DistributionMemberDto();
					distributionMemberDto.setOrderNo(dto.getOrderNo());
					distributionMemberDto = distributionMemberService.find(distributionMemberDto);
					if(distributionMemberDto != null && StringUtils.isNotEmpty(distributionMemberDto.getForeclosureMemberUid())) {
						handlerUid = distributionMemberDto.getForeclosureMemberUid();
					}
					flowDto.setNextProcessId("foreclosure");
					flowDto.setNextProcessName("结清原贷款");
				} else {
					handlerUid = dto.getReceivableForUid();
					flowDto.setNextProcessId("isBackExpenses");
					flowDto.setNextProcessName("扣回后置费用");
				}
			}
			
			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			
			goNextNode(flowDto, baseListDto);
			
			//TODO 更新出款报表状态。  并下载还款计划

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
	public RespDataObject<LendingDto> processDetails(@RequestBody LendingDto lendingDto) {
		RespDataObject<LendingDto> resp = new RespDataObject<LendingDto>();
		try {
			String orderNo=lendingDto.getOrderNo();
			LendingDto leDto = new LendingDto();
			leDto.setOrderNo(orderNo);
			leDto = lendingService.find(leDto);
			if(leDto==null){
				leDto=new LendingDto();
				leDto.setOrderNo(lendingDto.getOrderNo());
			}
			try {
				ApplyLoanDto applyLoanDto = new ApplyLoanDto();
				applyLoanDto.setOrderNo(orderNo);
				applyLoanDto = applyLoanService.find(applyLoanDto);
				if(applyLoanDto!=null){
					leDto.setLendingBankId(applyLoanDto.getLendingBankId());
					leDto.setOpeningBankId(applyLoanDto.getLendingBankSubId());
					leDto.setBankName(applyLoanDto.getBankName());
					leDto.setBankAccount(applyLoanDto.getBankAccount());
					leDto.setLoanAmount(applyLoanDto.getLoanAmount());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//获取银行-支行名称
			if(leDto.getLendingBankId()!=null){
				BankDto bdto = new BankDto();
				bdto.setId(NumberUtils.toInt(leDto.getLendingBankId()));
				BankDto bankDto =dataApi.getBankNameById(bdto).getData();
				leDto.setLendingBank(bankDto==null ? "":bankDto.getName());
			}
			if(leDto.getOpeningBankId()!=null){
				BankSubDto bsDto = new BankSubDto();
				bsDto.setId(NumberUtils.toInt(leDto.getOpeningBankId()));
				BankSubDto subBankDto =dataApi.getSubBankNameById(bsDto).getData();
				leDto.setOpeningBank(subBankDto == null ?"":subBankDto.getName());
			}
			//借款信息（借款金额）
			try {
				BaseBorrowDto orderBaseBorrowDto = new BaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(lendingDto.getOrderNo());
				BaseBorrowDto baseBorrowDto=  baseBorrowService.find(orderBaseBorrowDto);
				if(baseBorrowDto!=null){
					leDto.setIsPaymentMethod(baseBorrowDto.getPaymentMethod());  //费用支付方式
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return RespHelper.setSuccessDataObject(resp, leDto);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + lendingDto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}