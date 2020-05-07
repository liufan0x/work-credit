/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.order;

import com.anjbo.bean.order.FlowDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-10 16:09:44
 * @version 1.0
 */
public interface FlowService extends BaseService<FlowDto>{
	
	FlowDto selectEndOrderFlow(FlowDto orderFlowDto);
	
	String selectOrderNoByUid(String uid);
	
}
