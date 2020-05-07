/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AgencyIncomeModeDto;
import com.anjbo.dao.AgencyIncomeModeMapper;
import com.anjbo.service.AgencyIncomeModeService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@Service
public class AgencyIncomeModeServiceImpl extends BaseServiceImpl<AgencyIncomeModeDto>  implements AgencyIncomeModeService {
	@Autowired private AgencyIncomeModeMapper agencyIncomeModeMapper;

}
