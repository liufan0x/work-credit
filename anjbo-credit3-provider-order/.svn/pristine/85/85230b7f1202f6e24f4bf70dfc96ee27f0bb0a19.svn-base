/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AuditManagerDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.bean.risk.AuditFundDockingDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditFundDockingController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.AuditManagerService;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.service.risk.AuditFirstService;
import com.anjbo.service.risk.AuditFundDockingService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@RestController
public class AuditFundDockingController extends OrderBaseController implements IAuditFundDockingController{

	@Resource private AuditFundDockingService auditFundDockingService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private AuditFirstService auditFirstService;
	
	@Resource private BaseListService baseListService;
	
	@Resource private AuditManagerService auditManagerService;
	
	@Resource private AllocationFundService allocationFundService;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditFundDockingDto auditDto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto user = userApi.getUserDto();
			auditDto.setUpdateUid(user.getUid());
			auditDto.setCreateUid(user.getUid());
			
			//添加
			AuditFundDockingDto temp = new AuditFundDockingDto();
			temp.setOrderNo(auditDto.getOrderNo());
			temp = auditFundDockingService.find(temp);
			if(temp==null){
				auditFundDockingService.insert(auditDto);
			}else{
				auditFundDockingService.update(auditDto);
			}
			FlowDto next = new FlowDto();
			BaseListDto orderListDto = new BaseListDto();
			next.setOrderNo(auditDto.getOrderNo());
			next.setCurrentProcessId("fundDocking");
			next.setCurrentProcessName("资料推送");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			
			//获取当前受理员uid
			BaseBorrowDto orderBaseBorrowDto=new BaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(auditDto.getOrderNo());
			BaseBorrowDto baseBorrowDto=  baseBorrowService.find(orderBaseBorrowDto);
			logger.info("机构id:"+baseBorrowDto.getAgencyId()+"订单类型："+baseBorrowDto.getProductCode());
			if("03".equals(baseBorrowDto.getProductCode())){ //畅贷
				//查询是否是关联的畅贷，关联畅贷默认债务置换贷款的要件管理员
				BaseListDto olDto = new BaseListDto();
				olDto.setOrderNo(auditDto.getOrderNo());
				olDto = baseListService.find(olDto);
				if(StringUtils.isNotBlank(olDto.getRelationOrderNo())){
					BaseBorrowDto orderBaseBorrow=new BaseBorrowDto();
					orderBaseBorrow.setOrderNo(olDto.getRelationOrderNo());
					BaseBorrowDto borrow=  baseBorrowService.find(orderBaseBorrow);
					orderListDto.setCurrentHandlerUid(borrow.getElementUid());// 下一处理人要件管理员
					UserDto ud = userApi.findUserDtoByUid(borrow.getElementUid());
					orderListDto.setCurrentHandler(ud.getName());
				}else{
					orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
					UserDto ud = userApi.findUserDtoByUid(baseBorrowDto.getElementUid());
					orderListDto.setCurrentHandler(ud.getName());
				}
				next.setNextProcessId("element");
				next.setNextProcessName("要件校验");
			}else if("04".equals(baseBorrowDto.getProductCode()) || "04"== baseBorrowDto.getProductCode()){ //房抵贷
				next.setNextProcessId("dataAudit");
				next.setNextProcessName("资料审核");
				AuditFirstDto first=new AuditFirstDto();
				first.setOrderNo(auditDto.getOrderNo());
				first = auditFirstService.find(first);
				orderListDto.setCurrentHandlerUid(first.getHandleUid());//风控初审UId
				orderListDto.setCurrentHandler(first.getHandleName());
			}else if("07".equals(baseBorrowDto.getProductCode())) {//到申请放款
				next.setNextProcessId("applyLoan");
				next.setNextProcessName("申请放款");
				orderListDto.setCurrentHandlerUid(baseBorrowDto.getAcceptMemberUid());//受理员
				UserDto ud = userApi.findUserDtoByUid(baseBorrowDto.getAcceptMemberUid());
				orderListDto.setCurrentHandler(ud.getName());
			}else{  
				if(null!=baseBorrowDto.getAgencyId()&&baseBorrowDto.getAgencyId()>1){  //非快鸽
					next.setNextProcessId("element");
					next.setNextProcessName("要件校验");
					orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
					UserDto ud = userApi.findUserDtoByUid(baseBorrowDto.getElementUid());
					orderListDto.setCurrentHandler(ud.getName());
				}else{
					next.setNextProcessId("repaymentMember");
					next.setNextProcessName("指派还款专员");
					//查询受理经理uid
					BaseListDto listDto=new BaseListDto();
					listDto.setOrderNo(auditDto.getOrderNo());
					listDto=baseListService.find(listDto);
					if(listDto!=null && StringUtils.isNotBlank(listDto.getReceptionManagerUid())){
						UserDto nextUser = userApi.findUserDtoByUid(listDto.getReceptionManagerUid());
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}else{ //老数据
						AuditManagerDto dis = new AuditManagerDto();
						dis.setOrderNo(auditDto.getOrderNo());
						dis = auditManagerService.find(dis);
						UserDto nextUser = userApi.findUserDtoByUid(dis.getCreateUid());
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}
				}
			}
			orderListDto.setOrderNo(auditDto.getOrderNo());
			goNextNode(next,orderListDto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+auditDto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespData<AllocationFundDto> processDetails(@RequestBody AllocationFundDto dto) {
		RespData<AllocationFundDto> resp = new RespData<AllocationFundDto>();
		try {
			
			AllocationFundDto allocationFund = new AllocationFundDto();
			allocationFund.setOrderNo(dto.getOrderNo());
			List<AllocationFundDto> list = allocationFundService.search(allocationFund);
			AuditFundDockingDto auditDto=auditFundDockingService.detail(dto.getOrderNo());
			FundDto fundDto = new FundDto();
			for (AllocationFundDto allocationFundDto : list) {
				if(fundDto.getId()==null){
					allocationFundDto.setRemark(auditDto.getRemark());
				}
				fundDto.setId(allocationFundDto.getFundId());
				FundDto findFundDto = userApi.findByFundId(fundDto);
				allocationFundDto.setFundCode(findFundDto.getFundCode());
				allocationFundDto.setFundDesc(findFundDto.getFundDesc());
			}
			return RespHelper.setSuccessData(resp, list);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
		
}