package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CancellationService;
import com.anjbo.utils.CommonDataUtil;
/**
 * 注销
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/cancellation/v")
public class CancellationController extends BaseController{
	
	 	@Resource CancellationService cancellationService;
	 	
	 	/**
		 * 详情
		 * @param request
		 * @param notarizationDto
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/detail") 
		public RespDataObject<CancellationDto> detail(HttpServletRequest request,@RequestBody  CancellationDto cancellationDto){
			 RespDataObject<CancellationDto> resp=new RespDataObject<CancellationDto>();
			 CancellationDto dto=new CancellationDto();
			 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
			 borrowDto.setOrderNo(cancellationDto.getOrderNo());
			 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
			 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			 if(baseBorrowDto!=null){
				 String serviceType=baseBorrowDto.getProductCode();  //判断交易非交易
				 if("02".equals(serviceType)||"05".equals(serviceType)){  //非交易类
					 dto=cancellationService.selectCancellationByMortgage(cancellationDto);	 //查询抵押
					 if(dto!=null)
					 dto.setType(2);
					 else
						 dto=new CancellationDto(); dto.setOrderNo(cancellationDto.getOrderNo());
				 }else{ //交易
					 dto=cancellationService.selectCancellation(cancellationDto); //查询过户
					 if(dto!=null)
					 dto.setType(1);
					 else
						 dto=new CancellationDto();dto.setOrderNo(cancellationDto.getOrderNo());
				 }
				//国土局（注销）
				 if(dto.getClandBureau()!=null && dto.getClandBureau()!=""){
					     String name= getBureauName(dto.getClandBureau());
					     dto.setClandBureauName(name);
				 } 
				 UserDto cuser=CommonDataUtil.getUserDtoByUidAndMobile(dto.getClandBureauUid());
				 if(cuser!=null){
					 dto.setClandBureauUserName(cuser.getName());
				 }
				 //国土局驻点（过户-抵押）
				 UserDto cland=CommonDataUtil.getUserDtoByUidAndMobile(dto.getTlandBureauUid());
				 if(cland!=null){
					 dto.setTlandBureauUserName(cland.getName());
				 }
				 //国土局（过户-抵押）
				 if(dto.getClandBureau()!=null && dto.getTlandBureau()!=""){
					     String name= getBureauName(dto.getTlandBureau());
					     dto.setTlandBureauName(name);
				 } 
			 }
			try {
				 if(dto.getCancelTime()!=null && dto.getCancelEndTime()!=null)
					 dto.setCancelTime(DateUtil.dateTodate(dto.getCancelTime(), dto.getCancelEndTime()));
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
					HttpServletResponse response,@RequestBody CancellationDto cancellationDto) {
			    RespStatus rd = new RespStatus();
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
				try {
					 //判断当前节点
//				    if(!isWithdraw(cancellationDto.getOrderNo(), "cancellation")){		
						 OrderFlowDto orderFlowDto=new OrderFlowDto();  
						
						 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
						 borrowDto.setOrderNo(cancellationDto.getOrderNo());
						 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
						 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
						 if(baseBorrowDto!=null){
							String serviceType=baseBorrowDto.getProductCode();  //判断交易非交易
							if("02".equals(serviceType)||"05".equals(serviceType)){ //非交易类
								//抵押
								cancellationDto.setType(2);
								orderFlowDto.setNextProcessId("mortgage");
								orderFlowDto.setNextProcessName("抵押");
							}else{
								cancellationDto.setType(1);
								orderFlowDto.setNextProcessId("transfer");
								orderFlowDto.setNextProcessName("过户");
							}
						}
						UserDto dto=getUserDto(request);  //获取用户信息
						cancellationDto.setCreateUid(dto.getUid());
						cancellationDto.setUpdateUid(dto.getUid());
						cancellationService.addCancellation(cancellationDto);
						//流程
						orderFlowDto.setOrderNo(cancellationDto.getOrderNo());
						orderFlowDto.setCurrentProcessId("cancellation");
						orderFlowDto.setCurrentProcessName("注销");
						orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
						orderFlowDto.setHandleName(dto.getName());
						//订单列表
						OrderListDto listDto = new OrderListDto();
						//填充列表预计过户，国土局
						listDto.setAppShowValue1(DateUtil.getDateByFmt(cancellationDto.getTransferTime(), DateUtil.FMT_TYPE2));
						listDto.setAppShowValue2(getBureauName(cancellationDto.getTlandBureau()));
						
						listDto.setCurrentHandlerUid(cancellationDto.getTlandBureauUid());//下一处理人
						UserDto dto2= CommonDataUtil.getUserDtoByUidAndMobile(cancellationDto.getTlandBureauUid());
						if(dto2!=null){
						   listDto.setCurrentHandler(dto2.getName());
						}
						if(!isSubmit(cancellationDto.getOrderNo(), "cancellation")){
							goNextNode(orderFlowDto, listDto); //流程表
						}else{
							listDto.setOrderNo(cancellationDto.getOrderNo());
							httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
						}
//				    }else{
//				    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
//						rd.setMsg(RespStatusEnum.NOADOPT_ERROR.getMsg());
//				    }
				} catch (Exception e) {
					e.printStackTrace();
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg(RespStatusEnum.FAIL.getMsg());
				}
				return rd;
			}
		
		
	 
}
