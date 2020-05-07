package com.anjbo.controller;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.LendingHarvestService;
import com.anjbo.service.RebateService;
import com.anjbo.service.ReceivablePayService;

/**
 * 返佣
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/finance/rebate/v")
public class RebateController extends BaseController{

	@Resource
	RebateService rebateService;
	@Resource 
	LendingHarvestService lendingHarvestService;
	@Resource ReceivablePayService receivablePayService;
	/**
	 * 初始化
	 * @param request
	 * @param rebateDto
	 * @return
	 */
	@RequestMapping(value = "/init")
	public @ResponseBody
	RespDataObject<RebateDto> init(HttpServletRequest request,@RequestBody RebateDto rebateDto) {
		RespDataObject<RebateDto> rd = new RespDataObject<RebateDto>();
		try {
//			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
//			orderBaseBorrowDto.setOrderNo(rebateDto.getOrderNo());
//			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
//			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			//获取费用支付方式
			double rebateMoney = 0.0;
			LendingHarvestDto harvestDto =new LendingHarvestDto();
			harvestDto.setOrderNo(rebateDto.getOrderNo());
//			if(baseBorrowDto==null){
//				harvestDto.setType(1);
//			}else{
//				harvestDto.setType(baseBorrowDto.getPaymentMethod());
//			}
			harvestDto = lendingHarvestService.findByHarvest(harvestDto);
			rebateMoney = harvestDto.getReturnMoney();
			//收罚息节点的返佣
			ReceivablePayDto pay = new ReceivablePayDto();
			pay.setOrderNo(rebateDto.getOrderNo());
			pay = receivablePayService.findByReceivablePay(pay);
			BigDecimal rebateMoneyP=new BigDecimal("0.00");
			if(pay!=null) {
				rebateMoneyP = pay.getRebateMoney();
			}
			if(rebateMoneyP!=null&&rebateMoneyP.doubleValue()>0){
				rebateMoney += rebateMoneyP.doubleValue();
			}
			rebateDto.setRebateMoney(BigDecimal.valueOf(rebateMoney));
			rd.setData(rebateDto);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	/**
	 * 详情
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public @ResponseBody
	RespDataObject<RebateDto> detail(HttpServletRequest request,@RequestBody RebateDto rebateDto) {
		RespDataObject<RebateDto> rd = new RespDataObject<RebateDto>();
		try {
			rd.setData(rebateService.findByAll(rebateDto.getOrderNo()));
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	
	@RequestMapping(value = "/add")
	public @ResponseBody
	RespDataObject<RebateDto> add(HttpServletRequest request,@RequestBody RebateDto rebateDto) {
		RespDataObject<RebateDto> rd = new RespDataObject<RebateDto>();
		rd.setCode(RespStatusEnum.SUCCESS.getCode());
		rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		 try {
			//判断当前节点
			if(!isSubmit(rebateDto.getOrderNo(), "rebate")){	
				UserDto dto=getUserDto(request);  //获取用户信息
				rebateDto.setCreateUid(dto.getUid());
				rebateDto.setUpdateUid(dto.getUid());
				rebateService.insert(rebateDto);
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(rebateDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("rebate");
				orderFlowDto.setCurrentProcessName("返佣");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				OrderListDto listDto = new OrderListDto();
				orderFlowDto.setNextProcessId("wanjie");
				orderFlowDto.setNextProcessName("已完结");
				listDto.setOrderNo(rebateDto.getOrderNo());
				listDto.setCurrentHandlerUid("-");
				listDto.setCurrentHandler("-");
				listDto.setProcessId("wanjie");
				listDto.setState("已完结");
				goNextNode(orderFlowDto, listDto);  //流程方法
			}else{
				rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
				rd.setMsg("该订单已被处理，请刷新列表查看");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}

}
