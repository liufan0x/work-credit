package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.MortgageService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 抵押
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/process/mortgage/v")
public class MortgageController extends BaseController{

	@Resource MortgageService mortgageService;
	

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
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
//			    if(isWithdraw(mortgageDto.getOrderNo(), "mortgage")){		
					UserDto dto=getUserDto(request);  //获取用户信息
					mortgageDto.setCreateUid(dto.getUid());
					mortgageDto.setUpdateUid(dto.getUid());
					MortgageDto dto2=mortgageService.selectMortgage(mortgageDto);
					if(dto2==null){
						mortgageService.addMortgage(mortgageDto);
					}else{
						mortgageService.updateMortgage(mortgageDto);
					}
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
					orderFlowDto.setCurrentProcessId("mortgage");
					orderFlowDto.setCurrentProcessName("抵押");
					if("02".equals(productCode) || "02"==productCode){ //非交易
						orderFlowDto.setNextProcessId("receivableFor");
						orderFlowDto.setNextProcessName("回款");
					}else if("05".equals(productCode)|| "05"==productCode) {//提放
						orderFlowDto.setNextProcessId("elementReturn");
						orderFlowDto.setNextProcessName("要件退还");
					}else{ 
						ReceivableForDto forDto=new ReceivableForDto();
						forDto.setOrderNo(mortgageDto.getOrderNo());
						List<ReceivableForDto> forlist=httpUtil.getList(Constants.LINK_CREDIT, "/credit/finance/receivableFor/v/detail", forDto,ReceivableForDto.class);
						if(forlist!=null && forlist.size()>0){
							orderFlowDto.setNextProcessId("receivableForEnd");
							orderFlowDto.setNextProcessName("回款（尾期）");
						}else{
							orderFlowDto.setNextProcessId("receivableFor");
							orderFlowDto.setNextProcessName("回款");
						}
//						int isOnePay=baseBorrowDto.getIsOnePay()==null?1:baseBorrowDto.getIsOnePay();  //1:是,2:否(非交易类没有是否一次性回款)
//						if(isOnePay == 1|| "1".equals(isOnePay+"")){  //一次性回款到抵押 -V3.3
//						}else{
//						}
						//回款首期金额和时间
//						ReceivableForDto receivableForDto=new ReceivableForDto();
//						receivableForDto.setIsFrist(1);
//						receivableForDto.setOrderNo(mortgageDto.getOrderNo());
//						List<ReceivableForDto> list=httpUtil.getList(Enums.MODULAR_URL.CREDIT.toString(), "/credit/finance/lendingHarvest/v/detail", receivableForDto,ReceivableForDto.class);
//						if(list!=null && list.size()>0){
//							ReceivableForDto forDto=list.get(0);
//							listDto.setAppShowValue1(forDto.getPayMentAmountDate());
//							listDto.setAppShowValue2(forDto.getPayMentPic()+"");
//						}
					}
					
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					
					//财务管理-会计
					LendingDto lendingDto=new LendingDto();
					lendingDto.setOrderNo(mortgageDto.getOrderNo());
					RespDataObject<LendingDto> lend=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/selectReForUid", lendingDto,LendingDto.class);
					lendingDto =  lend.getData();
					if(lend!=null && lendingDto!=null && lendingDto.getReceivableForUid() != null && !"".equals(lendingDto.getReceivableForUid())){
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(lendingDto.getReceivableForUid());//根据uid获取名称
						listDto.setCurrentHandlerUid(lendingDto.getReceivableForUid());//下一处理人回款
						listDto.setCurrentHandler(userDto.getName());
					}else{ //默认财务
						String uid="1490000220355";  //默认姚彦如
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(uid);//根据uid获取名称
						listDto.setCurrentHandlerUid(uid);//下一处理人回款
						listDto.setCurrentHandler(userDto.getName());
					}
					if("05".equals(productCode)|| "05"==productCode) {//提放
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getElementUid());//根据uid获取名称
						listDto.setCurrentHandlerUid(userDto.getUid());
						listDto.setCurrentHandler(userDto.getName());
					}
					//更新流程方法
				   if(!isSubmit(mortgageDto.getOrderNo(), "mortgage")){		
					 goNextNode(orderFlowDto, listDto);
				   }else{
					    listDto.setOrderNo(mortgageDto.getOrderNo());
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
