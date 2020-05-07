/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.risk;

import com.anjbo.bean.risk.AuditFundDockingDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
public interface AuditFundDockingService extends BaseService<AuditFundDockingDto>{
	AuditFundDockingDto detail(String orderNo);
}
