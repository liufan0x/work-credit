/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.AuditReviewDto;
import com.anjbo.dao.risk.AuditReviewMapper;
import com.anjbo.service.risk.AuditReviewService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@Service
public class AuditReviewServiceImpl extends BaseServiceImpl<AuditReviewDto>  implements AuditReviewService {
	@Autowired private AuditReviewMapper auditReviewMapper;

}
