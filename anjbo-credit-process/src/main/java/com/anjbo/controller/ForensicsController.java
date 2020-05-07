package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ForensicsService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 取证
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/forensics/v")
public class ForensicsController extends BaseController{

	@Resource ForensicsService forensicsService;
	

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
		 }
		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
		 borrowDto.setOrderNo(fdto.getOrderNo());
		 //取证员名称
		 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(fdto.getLicenseReverUid());
		 if(userDto!=null){
			 fdto.setLicenseRever(userDto.getName());
		 }
		//国土局驻点（注销）
		 UserDto cland=CommonDataUtil.getUserDtoByUidAndMobile(fdto.getClandBureauUid());
		 if(cland!=null){
			 fdto.setClandBureauUserName(cland.getName());
		 }
		 //国土局（注销）
		 if(fdto.getClandBureau()!=null && fdto.getClandBureau()!=""){
			     String name= getBureauName(fdto.getClandBureau());
			     fdto.setClandBureauName(name);
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
//			    if(isWithdraw(forensicsDto.getOrderNo(), "forensics")){		
					UserDto dto=getUserDto(request);  //获取用户信息
					forensicsDto.setCreateUid(dto.getUid());
					forensicsDto.setUpdateUid(dto.getUid());
					forensicsService.addForensics(forensicsDto);
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(forensicsDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("forensics");
					orderFlowDto.setCurrentProcessName("取证");
					orderFlowDto.setNextProcessId("cancellation");
					orderFlowDto.setNextProcessName("注销");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					//订单列表
					OrderListDto listDto = new OrderListDto();
					
					//填充列表预计注销，国土局
					listDto.setAppShowValue1(DateUtil.getDateByFmt(forensicsDto.getCancelTime(), DateUtil.FMT_TYPE2));
					listDto.setAppShowValue2(getBureauName(forensicsDto.getClandBureau()));
					
					listDto.setCurrentHandlerUid(forensicsDto.getClandBureauUid());//下一处理人
					UserDto dto2= CommonDataUtil.getUserDtoByUidAndMobile(forensicsDto.getClandBureauUid());
					if(dto2!=null)
					   listDto.setCurrentHandler(dto2.getName());
					//更新流程方法
					 if(!isSubmit(forensicsDto.getOrderNo(), "forensics")){		
						 goNextNode(orderFlowDto, listDto);
					 }else{
						 listDto.setOrderNo(forensicsDto.getOrderNo());
						 httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
					 }
//			    }else{
//			    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
//					rd.setMsg(RespStatusEnum.NOADOPT_ERROR.getMsg());
//			    }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
}
