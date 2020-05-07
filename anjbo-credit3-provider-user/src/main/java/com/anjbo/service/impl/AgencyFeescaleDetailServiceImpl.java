/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AgencyFeescaleDetailDto;
import com.anjbo.dao.AgencyFeescaleDetailMapper;
import com.anjbo.service.AgencyFeescaleDetailService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
@Service
public class AgencyFeescaleDetailServiceImpl extends BaseServiceImpl<AgencyFeescaleDetailDto>  implements AgencyFeescaleDetailService {
	@Autowired private AgencyFeescaleDetailMapper agencyFeescaleDetailMapper;

}
