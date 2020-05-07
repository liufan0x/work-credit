package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.AuditDto;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuditService;
import com.anjbo.utils.CommonDataUtil;
/**
 * 财务审核 -云南
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/finance/audit/v")
public class AuditController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	@Resource
	AuditService auditService;
	
	/**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<AuditDto> detail(HttpServletRequest request,@RequestBody AuditDto auditDto){
		RespDataObject<AuditDto> resp=new RespDataObject<AuditDto>();
		try {
			resp.setData(auditService.selectAudit(auditDto.getOrderNo()));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
     * 添加信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespStatus add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody AuditDto auditDto) {
    	RespStatus rd = new RespStatus();
		rd.setCode(RespStatusEnum.SUCCESS.getCode());
		rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		try {
			 //判断当前节点
		    if(!isSubmit(auditDto.getOrderNo(), "financialAudit")){	
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", auditDto.getOrderNo());
				OrderListDto  orderList  = new OrderListDto();
				orderList.setOrderNo(auditDto.getOrderNo());
				orderList = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderList, OrderListDto.class);
				if(!"04".equals(orderList.getProductCode())) {
					RespStatus respStatus=httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/third/api/yntrust/v/confirmPayment", map);
					if("FAIL".equals(respStatus.getCode()) && !respStatus.getMsg().contains("已经发送过放款指令不能重复发送")){
							rd.setCode(RespStatusEnum.FAIL.getCode());
							rd.setMsg(respStatus.getMsg());
							return rd;
					}
				}
				UserDto dto=getUserDto(request);  //获取用户信息
		    	auditDto.setCreateUid(dto.getUid());
		    	auditDto.setUpdateUid(dto.getUid());
				auditService.addAudit(auditDto);
				
				//订单列表
				OrderListDto orderListDto=new OrderListDto();
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(auditDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("financialAudit");
				orderFlowDto.setCurrentProcessName("财务审核");
				
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				
				orderFlowDto.setNextProcessId("lending");
				orderFlowDto.setNextProcessName("放款");
				
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(auditDto.getNextHandleUid());
				orderListDto.setCurrentHandlerUid(auditDto.getNextHandleUid());//
				orderListDto.setCurrentHandler(nextUser.getName());
				//更新流程方法
				goNextNode(orderFlowDto, orderListDto);  
		    }else{
		    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
				rd.setMsg("该订单已被处理，请刷新列表查看");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			log.info("lendingHarvest-add Exception ==>", e);
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
}
