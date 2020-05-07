/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AgencyProductDto;
import com.anjbo.dao.AgencyProductMapper;
import com.anjbo.service.AgencyProductService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@Service
public class AgencyProductServiceImpl extends BaseServiceImpl<AgencyProductDto>  implements AgencyProductService {
	@Autowired private AgencyProductMapper agencyProductMapper;

}
