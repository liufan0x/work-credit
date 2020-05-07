/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2019 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.RiskEnquiryDto;
import com.anjbo.dao.risk.RiskEnquiryMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.risk.RiskEnquiryService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2019-01-11 18:08:27
 * @version 1.0
 */
@Service
public class RiskEnquiryServiceImpl extends BaseServiceImpl<RiskEnquiryDto>  implements RiskEnquiryService {
	@Autowired private RiskEnquiryMapper tblRiskEnquiryMapper;

}
