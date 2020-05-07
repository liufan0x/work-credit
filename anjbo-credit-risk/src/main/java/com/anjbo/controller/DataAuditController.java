package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AllocationFundService;
import com.anjbo.service.DataAuditService;
import com.anjbo.service.FundDockingService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 资料审核
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/risk/dataAudit/v")
public class DataAuditController extends BaseController{
	private static final Log log = LogFactory.getLog(CreditController.class);
	
	@Resource
	private DataAuditService auditService;
	@Resource 
	private AllocationFundService fundService;
	@Resource
	private FundDockingService dockingService;
	/**
	 * 保存审核信息
	 * @param request
	 * @param auditDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertaudit")
	public RespStatus insertaudit(HttpServletRequest request,@RequestBody DataAuditDto auditDto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(auditDto.getOrderNo())){
				result.setMsg("保存审核信息失败,订单编号不能为空!");
				return result;
			}
			boolean isSubmit = isSubmit(auditDto.getOrderNo(),"dataAudit");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(auditDto.getOrderNo(),"dataAudit");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			UserDto user = getUserDto(request);
			auditDto.setCreateUid(user.getUid());
			auditDto.setUpdateUid(user.getUid());
			auditService.insert(auditDto);
			
			OrderFlowDto next = new OrderFlowDto();
			OrderListDto orderListDto = new OrderListDto();
			next.setOrderNo(auditDto.getOrderNo());
			next.setCurrentProcessId("dataAudit");
			next.setCurrentProcessName("资料审核");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
//			//V3.0版本
//			boolean fundCode=false;  //是否有110，105资金
//			List<AllocationFundDto> flist = fundService.listDetail(auditDto.getOrderNo());
//			if(flist!=null){
//				for (int i = 0; i < flist.size(); i++) {
//					if("110".equals(flist.get(i).getFundCode()) || "105".equals(flist.get(i).getFundCode())){
//						fundCode=true;
//						break;
//					}
//				}
//			}
//			if(fundCode){
//				next.setNextProcessId("fundDocking");
//				next.setNextProcessName("资料推送");
//				String nextUid=flist.get(0).getCreateUid();
//				UserDto dto=CommonDataUtil.getUserDtoByUidAndMobile(nextUid);
//				orderListDto.setCurrentHandlerUid(dto.getUid()); //下一处理人分配资金
//				orderListDto.setCurrentHandler(dto.getName());
//			}else{
//				//获取当前受理员uid
				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(auditDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
//				if(null!=baseBorrowDto.getAgencyId()&&baseBorrowDto.getAgencyId()>1){
//					next.setNextProcessId("element");
//					next.setNextProcessName("要件校验");
//					orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
//					orderListDto.setCurrentHandler(baseBorrowDto.getElementName());
//				}else{
//					next.setNextProcessId("repaymentMember");
//					next.setNextProcessName("指派还款专员");
//					//查询受理经理uid
//					OrderListDto listDto=new OrderListDto();
//					listDto.setOrderNo(auditDto.getOrderNo());
//					listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
//					if(listDto!=null && StringUtil.isNotBlank(listDto.getReceptionManagerUid())){
//						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(listDto.getReceptionManagerUid());
//						orderListDto.setCurrentHandlerUid(nextUser.getUid());
//						orderListDto.setCurrentHandler(nextUser.getName());
//					}else{ //老数据
//						ManagerAuditDto dis = new ManagerAuditDto();
//						dis.setOrderNo(auditDto.getOrderNo());
//						dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
//						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
//						orderListDto.setCurrentHandlerUid(nextUser.getUid());
//						orderListDto.setCurrentHandler(nextUser.getName());
//					}
//				}
//			}
			if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷 V3.4.0
				next.setNextProcessId("fddMortgage");
				next.setNextProcessName("抵押");
				UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}else{
				//获取下一处理人 还款专员  ---V3.0.1版本
				DistributionMemberDto memberDto = new DistributionMemberDto();
				memberDto.setOrderNo(auditDto.getOrderNo());
				RespDataObject<DistributionMemberDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", memberDto,DistributionMemberDto.class);
				DistributionMemberDto member=  mobj.getData();
				UserDto nextUser = null;
				if(member!=null && member.getForeclosureMemberUid()!=null){
					log.info("还款专员uid:"+member.getForeclosureMemberUid());
					nextUser=CommonDataUtil.getUserDtoByUidAndMobile(member.getForeclosureMemberUid());
				}
				next.setNextProcessId("applyLoan");
				next.setNextProcessName("申请放款");
				if(nextUser==null){
					nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
				}
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}
			
			orderListDto.setOrderNo(auditDto.getOrderNo());
			result = goNextNode(next,orderListDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("审核信息新增数据异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/detail")
	public RespDataObject<DataAuditDto> detail(HttpServletRequest request,@RequestBody DataAuditDto auditDto){
		RespDataObject<DataAuditDto> result = new RespDataObject<DataAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(auditDto.getOrderNo())){
				result.setMsg("加载审核信息异常,缺少订单编号");
				return result;
			}
			String orderNo = AllocationFundController.getOrderNo(auditDto.getOrderNo());
			auditDto=auditService.detail(orderNo);
			if(auditDto!=null){
				UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(auditDto.getCreateUid());
				auditDto.setName(userDto.getName());
			}
			result.setData(auditDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信详情数据异常,异常信息为:",e);
		}
		return result;
	}
	
	
}
