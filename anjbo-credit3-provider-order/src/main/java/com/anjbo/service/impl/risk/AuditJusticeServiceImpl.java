/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.AuditJusticeDto;
import com.anjbo.dao.risk.AuditJusticeMapper;
import com.anjbo.service.risk.AuditJusticeService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@Service
public class AuditJusticeServiceImpl extends BaseServiceImpl<AuditJusticeDto>  implements AuditJusticeService {
	@Autowired private AuditJusticeMapper auditJusticeMapper;

}
