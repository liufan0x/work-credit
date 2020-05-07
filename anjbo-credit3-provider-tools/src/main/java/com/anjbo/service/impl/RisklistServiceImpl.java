/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.RisklistDto;
import com.anjbo.dao.RisklistMapper;
import com.anjbo.service.RisklistService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:21:45
 * @version 1.0
 */
@Service
public class RisklistServiceImpl extends BaseServiceImpl<RisklistDto>  implements RisklistService {
	@Autowired private RisklistMapper risklistMapper;

}
