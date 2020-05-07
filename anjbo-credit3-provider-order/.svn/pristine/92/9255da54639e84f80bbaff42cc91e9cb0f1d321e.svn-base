package com.anjbo.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespStatus;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.FlowService;
import com.anjbo.utils.SingleUtils;

public class OrderBaseController extends BaseController {	
	
	@Resource private BaseListService baseListService;
	
	@Resource private FlowService flowService;
	
	@Resource private DataApi dataApi;
	
	@Resource private UserApi userApi;
	
	/**
	 * 进入下一个节点
	 */
	public void goNextNode(FlowDto flowDto,BaseListDto baseListDto) {
		if(baseListDto==null){
			baseListDto = new BaseListDto();
		}
		baseListDto.setOrderNo(flowDto.getOrderNo());
		flowDto.setUpdateUid(flowDto.getHandleUid());
		Integer productId = 440301;
		BaseListDto tempbaseListDto = baseListService.find(baseListDto);
		
		if(tempbaseListDto != null && StringUtils.isNotEmpty(tempbaseListDto.getCityCode()) && StringUtils.isNotEmpty(tempbaseListDto.getProductCode())){
			productId = Integer.valueOf(tempbaseListDto.getCityCode()+tempbaseListDto.getProductCode());
		}

		//上一个处理人
		baseListDto.setPreviousHandlerUid(flowDto.getHandleUid());
		baseListDto.setPreviousHandler(flowDto.getHandleName());
		baseListDto.setPreviousHandleTime(new Date());

		if(StringUtils.isEmpty(baseListDto.getState())){
			//当前节点
			baseListDto.setProcessId(flowDto.getNextProcessId());
			if("已完结".equals(flowDto.getNextProcessName())) {
				baseListDto.setState(flowDto.getNextProcessName());
				baseListDto.setCurrentHandler("-");
				baseListDto.setCurrentHandlerUid("-");
			}else {
				baseListDto.setState("待"+flowDto.getNextProcessName());
			}
		}

//		//同步数据到智能要件
		try {
			HttpHeaders headers = new HttpHeaders();
			logger.info("执行了baseControl同步信贷件订单到智能要件"+"state为"+baseListDto.getState());
			SingleUtils.getRestTemplate().postForEntity(Constants.LINK_ANJBO_CREDIT_URL + "/credit/element/list/saveCredit", SingleUtils.getHttpEntity(baseListDto, headers), RespStatus.class);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		//退回的单判断是否重新走流程
		FlowDto tempFlowDto = flowService.selectEndOrderFlow(flowDto);
		if(tempFlowDto!=null&&StringUtils.isNotBlank(tempFlowDto.getBackReason())&&tempFlowDto.getIsNewWalkProcess()==2){
			//不重新走流程
			List<ProcessDto> processDtos = dataApi.findProcessDto(productId);
			for (ProcessDto processDto : processDtos) {
				if(processDto.getProcessId().equals(tempFlowDto.getCurrentProcessId())) {
					baseListDto.setState("待"+processDto.getProcessName());
					flowDto.setNextProcessName(processDto.getProcessName());
				}
			}
			flowDto.setNextProcessId(tempFlowDto.getCurrentProcessId());
			baseListDto.setProcessId(tempFlowDto.getCurrentProcessId());
			baseListDto.setCurrentHandlerUid(tempFlowDto.getHandleUid());
			baseListDto.setCurrentHandler(userApi.findUserDtoByUid(tempFlowDto.getHandleUid()).getName());
		}
		baseListService.update(baseListDto);

		flowDto.setIsNewWalkProcess(1);
		
		//app再次编辑不插入流水
		if(flowDto!=null && tempFlowDto != null && flowDto.getCurrentProcessId().equals(tempFlowDto.getCurrentProcessId())){
			return;

		}else if("wanjie".equals(baseListDto.getProcessId())){
			String uid = flowDto.getHandleUid();
			String name = flowDto.getHandleName();
			flowService.insert(flowDto);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			flowDto = new FlowDto();
			flowDto.setOrderNo(baseListDto.getOrderNo());
			flowDto.setUpdateUid(uid);
			flowDto.setCurrentProcessId("wanjie");
			flowDto.setNextProcessId("-");
			flowDto.setNextProcessName("-");
			flowDto.setHandleUid(uid);
			flowDto.setHandleName(name);
			flowDto.setIsNewWalkProcess(1);
			flowService.insert(flowDto);
		}else if("closeOrder".equals(baseListDto.getProcessId())){
			String uid = flowDto.getHandleUid();
			String name = flowDto.getHandleName();
			flowService.insert(flowDto);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			flowDto = new FlowDto();
			flowDto.setOrderNo(baseListDto.getOrderNo());
			flowDto.setUpdateUid(uid);
			flowDto.setCurrentProcessId("closeOrder");
			flowDto.setNextProcessId("-");
			flowDto.setNextProcessName("-");
			flowDto.setHandleUid(uid);
			flowDto.setHandleName(name);
		}else {
			flowService.insert(flowDto);
		}
		
	}
	
}
