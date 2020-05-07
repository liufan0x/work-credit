package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ForensicsService;
import com.anjbo.service.MortgageService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 房抵贷-抵押
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/process/fddMortgage/v")
public class FddMortgageController extends BaseController{
	
	@Resource MortgageService mortgageService;
	@Resource ForensicsService forensicsService;
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<MortgageDto> detail(HttpServletRequest request,@RequestBody  MortgageDto mortgageDto){
		 RespDataObject<MortgageDto> resp=new RespDataObject<MortgageDto>();
		 MortgageDto dto=mortgageService.selectMortgage(mortgageDto);
		 if(dto==null){
			 dto=new MortgageDto();
			 dto.setOrderNo(mortgageDto.getOrderNo());
		 }else{
			 if(dto.getMlandBureau()!=null && dto.getMlandBureau()!=""){
				     String name= getBureauName(dto.getMlandBureau());
				     dto.setMlandBureauName(name);
			 } 
			 UserDto cland=CommonDataUtil.getUserDtoByUidAndMobile(dto.getMlandBureauUid());
			 if(cland!=null){
				 dto.setMlandBureauUserName(cland.getName());
			 }
		 }
		 try {
			 if(dto.getMortgageTime()!=null && dto.getMortgageEndTime()!=null)
				 dto.setMortgageTime(DateUtil.dateTodate(dto.getMortgageTime(), dto.getMortgageEndTime()));
			 
			 //取证信息
			 ForensicsDto fdto=forensicsService.selectForensics(mortgageDto);
			 if(fdto!=null){
				if(dto.getFlandBureau()!=null && dto.getFlandBureau()!=""){
				     String name= getBureauName(dto.getFlandBureau());
				     dto.setFlandBureauName(name);
			    }
				if(fdto.getLicenseRevBank()!=null){
					BankDto bankDto =CommonDataUtil.getBankNameById(fdto.getLicenseRevBank());
					dto.setLicenseRevBankName(bankDto==null ? "":bankDto.getName());
					dto.setLicenseRevBank(fdto.getLicenseRevBank());
				}
				if(fdto.getLicenseRevBankSub()!=null){
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(fdto.getLicenseRevBankSub());
					dto.setLicenseRevBankSubName(subBankDto == null ?"":subBankDto.getName());
					dto.setLicenseRevBankSub(fdto.getLicenseRevBankSub());
				}
				 //取证员名称
				 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(fdto.getLicenseReverUid());
				 dto.setLicenseReverUid(fdto.getLicenseReverUid());
				 if(userDto!=null){
					 dto.setLicenseRever(userDto.getName());
				 }
				 if(fdto.getLicenseRevTime()!=null){
					 dto.setLicenseRevTime(fdto.getLicenseRevTime());
				 }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 resp.setData(dto);
		 resp.setCode(RespStatusEnum.SUCCESS.getCode());
		 resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		 return resp;
	}
	
	/**
	 * 添加
	 * @param request
	 * @param response
	 * @return
	 */
	   @RequestMapping(value = "/add")
		public @ResponseBody
		RespStatus add(HttpServletRequest request,
				HttpServletResponse response,@RequestBody MortgageDto mortgageDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
			try {
					if(StringUtils.isBlank(mortgageDto.getOrderNo())){
						rd.setMsg("保存审核信息失败,订单编号不能为空!");
						return rd;
					}
					boolean isSubmit = isSubmit(mortgageDto.getOrderNo(),"fddMortgage");
//					if(isSubmit){
//						rd.setMsg("该订单已经被提交");
//						return rd;
//					}
					boolean isWithdraw = isWithdraw(mortgageDto.getOrderNo(),"fddMortgage");
					if(isWithdraw &&!isSubmit){
						rd.setMsg("该订单已经被撤回");
						return rd;
					}
					UserDto dto=getUserDto(request);  //获取用户信息
					mortgageDto.setCreateUid(dto.getUid());
					mortgageDto.setUpdateUid(dto.getUid());
					MortgageDto dto2=mortgageService.selectMortgage(mortgageDto);
					if(dto2==null){
						mortgageService.addMortgage(mortgageDto);
					}else{
						mortgageService.updateMortgage(mortgageDto);
					}
					//添加取证初始数据
					mortgageDto.setRemark("");
					forensicsService.addFddForensics(mortgageDto);
					
					//查询订单信息   
					OrderBaseBorrowDto baseDto=new OrderBaseBorrowDto();
					baseDto.setOrderNo(mortgageDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> baseobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", baseDto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto=  baseobj.getData();
					String productCode="01"; //交易类
					if(baseBorrowDto!=null){
						productCode=baseBorrowDto.getProductCode();
					}
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					//订单列表
					OrderListDto listDto = new OrderListDto();
					orderFlowDto.setOrderNo(mortgageDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("fddMortgage");
					orderFlowDto.setCurrentProcessName("抵押");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					
					FinalAuditDto finalAuditDto=new FinalAuditDto();
					finalAuditDto.setOrderNo(mortgageDto.getOrderNo());
					RespDataObject<FinalAuditDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
					String paymentType=Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName(); //凭他项权利证放款
					String paymentTypeName="";
					if(null!=auditDto.getData()){
						paymentTypeName=auditDto.getData().getPaymentType();
					}
					if(paymentType.equals(paymentTypeName)){ 
						orderFlowDto.setNextProcessId("fddForensics");
						orderFlowDto.setNextProcessName("取证");
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(mortgageDto.getLicenseReverUid());//根据uid获取名称
						listDto.setCurrentHandlerUid(userDto.getUid());
						listDto.setCurrentHandler(userDto.getName());
					}else{
//						DistributionMemberDto memberDto = new DistributionMemberDto();//还款专员
//						memberDto.setOrderNo(mortgageDto.getOrderNo());
//						RespDataObject<DistributionMemberDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", memberDto,DistributionMemberDto.class);
//						DistributionMemberDto member=  mobj.getData();
//						UserDto nextUser= null;
//						if(member!=null && member.getForeclosureMemberUid()!=null){
//							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(member.getForeclosureMemberUid());
//						}
						orderFlowDto.setNextProcessId("applyLoan");
						orderFlowDto.setNextProcessName("申请放款");
						UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
						listDto.setCurrentHandlerUid(baseBorrowDto.getAcceptMemberUid());
						listDto.setCurrentHandler(nextUser.getName());
					}
					//更新流程方法
				   if(!isSubmit(mortgageDto.getOrderNo(), "fddMortgage")){		
					 goNextNode(orderFlowDto, listDto);
				   }else{
					    listDto.setOrderNo(mortgageDto.getOrderNo());
						httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
					}
			} catch (Exception e) {
				e.printStackTrace();
				return rd;
			}
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return rd;
		}
	
}
