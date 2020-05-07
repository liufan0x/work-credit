package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.NewlicenseService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 领新证
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/newlicense/v")
public class NewlicenseController extends BaseController{

	
	@Resource NewlicenseService newlicenseService;
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<NewlicenseDto> detail(HttpServletRequest request,@RequestBody  NewlicenseDto newlicenseDto){
		 RespDataObject<NewlicenseDto> resp=new RespDataObject<NewlicenseDto>();
		 NewlicenseDto dto=newlicenseService.selectNewlicense(newlicenseDto);
		 if(dto==null){
			 dto = new NewlicenseDto(); 
			 dto.setOrderNo(newlicenseDto.getOrderNo());
		 }else{
			//国土局（过户）
			 if(dto.getNlandBureau()!=null && dto.getNlandBureau()!=""){
				     String name= getBureauName(dto.getNlandBureau());
				     dto.setNlandBureauName(name);
			 } 
			 UserDto cuser=CommonDataUtil.getUserDtoByUidAndMobile(dto.getNlandBureauUid());
			 if(cuser!=null){
				 dto.setNlandBureauUserName(cuser.getName());
			 }
			 //国土局驻点（抵押）
			 UserDto cland=CommonDataUtil.getUserDtoByUidAndMobile(dto.getMlandBureauUid());
			 if(cland!=null){
				 dto.setMlandBureauUserName(cland.getName());
			 }
			 //国土局（抵押）
			 if(dto.getMlandBureau()!=null && dto.getMlandBureau()!=""){
				     String name= getBureauName(dto.getMlandBureau());
				     dto.setMlandBureauName(name);
			 } 
		 }
		 try {
			 if(dto.getNewlicenseTime()!=null && dto.getNewlicenseEndTime()!=null)
				 dto.setNewlicenseTime(DateUtil.dateTodate(dto.getNewlicenseTime(), dto.getNewlicenseEndTime()));
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
				HttpServletResponse response,@RequestBody NewlicenseDto newlicenseDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
//			    if(isWithdraw(newlicenseDto.getOrderNo(), "newlicense")){	
					UserDto dto=getUserDto(request);  //获取用户信息
					newlicenseDto.setCreateUid(dto.getUid());
					newlicenseDto.setUpdateUid(dto.getUid());
					newlicenseService.addNewlicense(newlicenseDto);
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(newlicenseDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("newlicense");
					orderFlowDto.setCurrentProcessName("领新证");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					//回款首期
					OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
					borrowDto.setOrderNo(newlicenseDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto =  obj.getData();
					String code=baseBorrowDto.getProductCode();  //01交易类  02：非交易
					int isOnePay=baseBorrowDto.getIsOnePay()==null?1:baseBorrowDto.getIsOnePay();  //1:是,2:否(非交易类没有是否一次性回款)
					logger.info("----领新证：是否一次性回款"+baseBorrowDto.getIsOnePay());
			    	System.out.println("----领新证：是否一次性回款"+baseBorrowDto.getIsOnePay());
				    if("02"==code || "02".equals(code)){
				    	orderFlowDto.setNextProcessId("receivableFor");
						orderFlowDto.setNextProcessName("回款");
				    }else{
				    	if(isOnePay == 1 || "1".equals(isOnePay+"")){
				    		orderFlowDto.setNextProcessId("mortgage");
							orderFlowDto.setNextProcessName("抵押");
				    	}else{
					    	orderFlowDto.setNextProcessId("receivableForFirst");
							orderFlowDto.setNextProcessName("回款（首期）");
				    	}
				    }
					//订单列表
					OrderListDto listDto = new OrderListDto();
					//财务管理-会计
					LendingDto lendingDto=new LendingDto();
					lendingDto.setOrderNo(newlicenseDto.getOrderNo());
					
					//填充列表预计回款，预计回款金额
					try {
						if(borrowDto.getOrderReceivableForDto()!=null && borrowDto.getOrderReceivableForDto().size()>0){
							listDto.setAppShowValue1(DateUtil.getDateByFmt(borrowDto.getOrderReceivableForDto().get(0).getPayMentAmountDate(), DateUtil.FMT_TYPE2));
							listDto.setAppShowValue2(borrowDto.getOrderReceivableForDto().get(0).getPayMentAmount()+"");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(baseBorrowDto.getIsOnePay() == 1 || "1".equals(baseBorrowDto.getIsOnePay().toString())){
						listDto.setCurrentHandlerUid(newlicenseDto.getMlandBureauUid());//下一处理人抵押
						listDto.setCurrentHandler(newlicenseDto.getMlandBureauUserName());
					}else{	
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
					}
					//更新流程方法
					if(!isSubmit(newlicenseDto.getOrderNo(), "newlicense")){	
						goNextNode(orderFlowDto, listDto);  
					}else{
						listDto.setOrderNo(newlicenseDto.getOrderNo());
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
