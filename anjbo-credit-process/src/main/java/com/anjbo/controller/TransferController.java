package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.TransferService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 过户
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/transfer/v")
public class TransferController extends BaseController{
	
	@Resource TransferService transferService;
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<TransferDto> detail(HttpServletRequest request,@RequestBody  TransferDto transferDto){
		 RespDataObject<TransferDto> resp=new RespDataObject<TransferDto>();
		 TransferDto dto=transferService.selectTransfer(transferDto);
		 if(dto==null){
			 dto=new TransferDto();
			 dto.setOrderNo(transferDto.getOrderNo());
		 }else{
			//国土局（过户）
			 if(dto.getTlandBureau()!=null && dto.getTlandBureau()!=""){
				     String name= getBureauName(dto.getTlandBureau());
				     dto.setTlandBureauName(name);
			 } 
			 UserDto cuser=CommonDataUtil.getUserDtoByUidAndMobile(dto.getTlandBureauUid());
			 if(cuser!=null){
				 dto.setTlandBureauUserName(cuser.getName());
			 }
			 //国土局驻点（领新证）
			 UserDto cland=CommonDataUtil.getUserDtoByUidAndMobile(dto.getNlandBureauUid());
			 if(cland!=null){
				 dto.setNlandBureauUserName(cland.getName());
			 }
			 //国土局（领新证）
			 if(dto.getNlandBureau()!=null && dto.getNlandBureau()!=""){
				     String name= getBureauName(dto.getNlandBureau());
				     dto.setNlandBureauName(name);
			 } 
		 }
		 try {
			 if(dto.getTransferTime()!=null && dto.getTransferEndTime()!=null)
				 dto.setTransferTime(DateUtil.dateTodate(dto.getTransferTime(), dto.getTransferEndTime()));
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
				HttpServletResponse response,@RequestBody TransferDto transferDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
//			    if(isWithdraw(transferDto.getOrderNo(), "transfer")){	
					UserDto dto=getUserDto(request);  //获取用户信息
					transferDto.setCreateUid(dto.getUid());
					transferDto.setUpdateUid(dto.getUid());
					transferService.addTransfer(transferDto);
					
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(transferDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("transfer");
					orderFlowDto.setCurrentProcessName("过户");
					orderFlowDto.setNextProcessId("newlicense");
					orderFlowDto.setNextProcessName("领新证");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					//订单列表
					OrderListDto listDto = new OrderListDto();
					//填充列表预计领新证，国土局
					listDto.setAppShowValue1(DateUtil.getDateByFmt(transferDto.getNewlicenseTime(), DateUtil.FMT_TYPE2));
					listDto.setAppShowValue2(getBureauName(transferDto.getNlandBureau()));
					
					listDto.setCurrentHandlerUid(transferDto.getNlandBureauUid());//下一领新证处理人
					UserDto dto2= CommonDataUtil.getUserDtoByUidAndMobile(transferDto.getNlandBureauUid());
					if(dto2!=null)
					   listDto.setCurrentHandler(dto2.getName());
					//更新流程方法
					 if(!isSubmit(transferDto.getOrderNo(), "transfer")){	
						 goNextNode(orderFlowDto, listDto); 
					 }else{
						 listDto.setOrderNo(transferDto.getOrderNo());
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
