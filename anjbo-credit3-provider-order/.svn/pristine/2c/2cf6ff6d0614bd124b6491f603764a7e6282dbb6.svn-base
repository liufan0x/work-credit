/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.AuditFundDockingDto;
import com.anjbo.dao.risk.AuditFundDockingMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.risk.AuditFundDockingService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@Service
public class AuditFundDockingServiceImpl extends BaseServiceImpl<AuditFundDockingDto>  implements AuditFundDockingService {
	@Autowired private AuditFundDockingMapper auditFundDockingMapper;

	@Override
	public AuditFundDockingDto detail(String orderNo) {
		return auditFundDockingMapper.detail(orderNo);
	}

}
