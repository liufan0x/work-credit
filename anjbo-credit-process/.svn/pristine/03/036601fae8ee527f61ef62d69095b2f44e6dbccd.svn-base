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
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FddMortgageStorageService;
import com.anjbo.service.ForensicsService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 房抵贷-取证
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/fddForensics/v")
public class FddForensicsController extends BaseController{

@Resource ForensicsService forensicsService;
@Resource FddMortgageStorageService fddMortgageStorageService;	

	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<ForensicsDto> detail(HttpServletRequest request,@RequestBody  ForensicsDto forensicsDto){
		 RespDataObject<ForensicsDto> resp=new RespDataObject<ForensicsDto>();
		 ForensicsDto fdto=forensicsService.selectForensics(forensicsDto);
		 if(fdto==null){
			 fdto = new ForensicsDto();
			 fdto.setOrderNo(forensicsDto.getOrderNo());
		 }else{
			 //取证银行-支行
			 if(fdto.getLicenseRevBank()!=null){
				BankDto bankDto =CommonDataUtil.getBankNameById(fdto.getLicenseRevBank());
				fdto.setLicenseRevBankName(bankDto==null ? "":bankDto.getName());
			}
			if(fdto.getLicenseRevBankSub()!=null){
				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(fdto.getLicenseRevBankSub());
				fdto.setLicenseRevBankSubName(subBankDto == null ?"":subBankDto.getName());
			}
			if(fdto.getFlandBureau()!=null && fdto.getFlandBureau()!=""){
			     String name= getBureauName(fdto.getFlandBureau());
			     fdto.setFlandBureauName(name);
		    }
		 }
		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
		 borrowDto.setOrderNo(fdto.getOrderNo());
		 //取证员名称
		 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(fdto.getLicenseReverUid());
		 if(userDto!=null){
			 fdto.setLicenseRever(userDto.getName());
		 }
		 FddMortgageStorageDto mdto=fddMortgageStorageService.findByStorage(forensicsDto.getOrderNo());
		 if(mdto!=null&&null!=mdto.getCollateralUid()){
			 fdto.setFddMortgageStorageUid(mdto.getCollateralUid());
			 UserDto fddDto=CommonDataUtil.getUserDtoByUidAndMobile(fdto.getCollateralUid());
			 if(fddDto!=null){
				 fdto.setFddMortgageStorageName(fddDto.getName());
			 }
			 if(mdto.getCollateralTime()!=null){
				 fdto.setFddMortgageStorageTime(mdto.getCollateralTime());
			 }
		 }
		 try {
			 if(fdto.getLicenseRevTime()!=null && fdto.getLicenseRevEndTime()!=null)
				 fdto.setLicenseRevTime(DateUtil.dateTodate(fdto.getLicenseRevTime(), fdto.getLicenseRevEndTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 resp.setData(fdto);
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
				HttpServletResponse response,@RequestBody ForensicsDto forensicsDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
				if(StringUtils.isBlank(forensicsDto.getOrderNo())){
					rd.setMsg("保存审核信息失败,订单编号不能为空!");
					return rd;
				}
				boolean isSubmit = isSubmit(forensicsDto.getOrderNo(),"fddForensics");
//				if(isSubmit){
//					rd.setMsg("该订单已经被提交");
//					return rd;
//				}
				boolean isWithdraw = isWithdraw(forensicsDto.getOrderNo(),"fddForensics");
				if(isWithdraw &&!isSubmit){
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("该订单已经被撤回");
					return rd;
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				forensicsDto.setCreateUid(dto.getUid());
				forensicsDto.setUpdateUid(dto.getUid());
				forensicsService.addFddForensics(forensicsDto);
				//添加抵押品入库初始信息
				forensicsDto.setRemark("");
				fddMortgageStorageService.addStorage(forensicsDto);
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(forensicsDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("fddForensics");
				orderFlowDto.setCurrentProcessName("取证");
				
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());

				FinalAuditDto finalAuditDto=new FinalAuditDto();
				finalAuditDto.setOrderNo(forensicsDto.getOrderNo());
				RespDataObject<FinalAuditDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
				String paymentType=Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName(); //凭他项权利证放款
				String paymentTypeName="";
				if(null!=auditDto.getData()){ 
					paymentTypeName=auditDto.getData().getPaymentType();
				}
				UserDto dto2= null;
				OrderListDto listDto = new OrderListDto();
				if(paymentType.equals(paymentTypeName)){ //凭他项
					OrderBaseBorrowDto baseDto=new OrderBaseBorrowDto();
					baseDto.setOrderNo(forensicsDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> baseobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", baseDto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto=  baseobj.getData();
					orderFlowDto.setNextProcessId("applyLoan");
					orderFlowDto.setNextProcessName("申请放款");
					UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
					listDto.setCurrentHandlerUid(baseBorrowDto.getAcceptMemberUid());
					listDto.setCurrentHandler(nextUser.getName());
				}else{
					orderFlowDto.setNextProcessId("fddMortgageStorage");
					orderFlowDto.setNextProcessName("抵押品入库");
					listDto.setCurrentHandlerUid(forensicsDto.getCollateralUid());//下一处理人抵押品入库
					dto2= CommonDataUtil.getUserDtoByUidAndMobile(forensicsDto.getCollateralUid());
					if(dto2!=null)
					   listDto.setCurrentHandler(dto2.getName());
				}
				
				//更新流程方法
				 if(!isSubmit(forensicsDto.getOrderNo(), "fddForensics")){		
					 goNextNode(orderFlowDto, listDto);
				 }else{
					 listDto.setOrderNo(forensicsDto.getOrderNo());
					 httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
				 }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
}
