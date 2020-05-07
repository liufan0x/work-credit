package com.anjbo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.ReceivableHasDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.statistics.OrderLendingStatistics;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ReceivableHasService;
import com.anjbo.service.ReceivablePayService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.StringUtil;

/**
 * 待付费
 * @author yis
 *
 */

@Controller
@RequestMapping("/credit/finance/receivablePay/v")
public class ReceivablePayController extends BaseController{

	
	@Resource ReceivablePayService receivablePayService;
	@Resource ReceivableHasService receivableHasService;
	/**
	 * 详情
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public @ResponseBody
	RespDataObject<ReceivablePayDto> detail(HttpServletRequest request,@RequestBody ReceivablePayDto receivablePayDto) {
		RespDataObject<ReceivablePayDto> rd = new RespDataObject<ReceivablePayDto>();
		try {
			ReceivablePayDto dto=receivablePayService.findByReceivablePay(receivablePayDto);
			if(dto==null){
				dto=new ReceivablePayDto();
			}
			dto.setOrderNo(receivablePayDto.getOrderNo());
			ReceivableHasDto hasDto=receivableHasService.findByReceivableHas(receivablePayDto);
			if(hasDto!=null){
				dto.setType(hasDto.getType());
				dto.setRefund(hasDto.getRefund());
				//dto.setPenaltyPayable(hasDto.getPenaltyPayable());
				//逾期天数
				dto.setDatediff(hasDto.getDatediff());
			}
			rd.setData(dto);
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
     * 添加信息
     * @param request
     * @param lendingPayDto
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespData<ReceivablePayDto> add(HttpServletRequest request,@RequestBody ReceivablePayDto receivablePayDto) {
		RespData<ReceivablePayDto> rd = new RespData<ReceivablePayDto>();
		try {
			 //判断当前节点
			String createUid=receivablePayDto.getCreateUid();
		    if(!isSubmit(receivablePayDto.getOrderNo(), "pay")){		
				UserDto dto=getUserDto(request);  //获取用户信息
				receivablePayDto.setCreateUid(dto.getUid());
				receivablePayDto.setUpdateUid(dto.getUid());
				ReceivablePayDto hasDto=receivablePayService.findByReceivablePay(receivablePayDto);
				if(hasDto!=null){
				    receivablePayService.updateReceivablePay(receivablePayDto);
				}else{
					receivablePayService.addReceivablePay(receivablePayDto);
				}
				boolean isRebate=false; //是否返佣
				if(StringUtil.isNotBlank(createUid)){
					isRebate=true;
				}
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(receivablePayDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("pay");
				orderFlowDto.setCurrentProcessName("收罚息");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				OrderListDto listDto = new OrderListDto();
				//查询要件管理员信息
				OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
				borrowDto.setOrderNo(receivablePayDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if((baseBorrowDto!=null && "03".equals(baseBorrowDto.getProductCode()) && !"0".equals(receivablePayDto.getRelationOrderNo()))){ //畅待关联置换贷
					if(isRebate){
						orderFlowDto.setNextProcessId("rebate");
						orderFlowDto.setNextProcessName("返佣");
						listDto.setCurrentHandlerUid(createUid);
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(createUid);
						listDto.setCurrentHandler(userDto.getName());
					}else{
						orderFlowDto.setNextProcessId("wanjie");
						orderFlowDto.setNextProcessName("已完结");
						
						listDto.setOrderNo(receivablePayDto.getOrderNo());
						listDto.setCurrentHandlerUid("-");
						listDto.setCurrentHandler("-");
						listDto.setProcessId("wanjie");
						listDto.setState("已完结");
					}
				}else{
					orderFlowDto.setNextProcessId("elementReturn");  //要件退还
					orderFlowDto.setNextProcessName("要件退还");
					listDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());//下一处理人要件管理员
					listDto.setCurrentHandler(baseBorrowDto.getElementName());
				}
				goNextNode(orderFlowDto, listDto);  //流程方法
				
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
				try {
					//更新统计表罚息字段和计算创收
					OrderLendingStatistics lendingStatistics = new OrderLendingStatistics();
					lendingStatistics.setOrderNo(receivablePayDto.getOrderNo());
					lendingStatistics = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/report/achievementStatistics/v/query", lendingStatistics, OrderLendingStatistics.class);
					if(lendingStatistics!=null){
						double income=receivablePayDto.getPenaltyPayable().doubleValue();
						if(lendingStatistics.getServiceCharge()!=null){
							income +=lendingStatistics.getServiceCharge();
						}
						if(lendingStatistics.getInterest()!=null){
							income +=lendingStatistics.getInterest();
						}
						lendingStatistics.setIncome(income);
						lendingStatistics.setFine(receivablePayDto.getPenaltyPayable().doubleValue());
					}
					lendingStatistics.setCreateTime(null);
					lendingStatistics.setLendingTime(null);
					lendingStatistics.setUpdateTime(null);
					RespStatus resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/report/achievementStatistics/v/update", lendingStatistics);
					logger.info("更新放款量统计表"+resp.getCode());
				} catch (Exception e) {
					logger.error("更新放款量统计表失败");
					e.printStackTrace();
				}
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
