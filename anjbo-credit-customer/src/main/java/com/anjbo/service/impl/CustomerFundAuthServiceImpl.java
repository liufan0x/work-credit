/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.CustomerFundAuthDto;
import com.anjbo.dao.CustomerFundAuthMapper;
import com.anjbo.service.CustomerFundAuthService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-03-01 16:12:53
 * @version 1.0
 */
@Service
public class CustomerFundAuthServiceImpl extends BaseServiceImpl<CustomerFundAuthDto, Long>  implements CustomerFundAuthService {
	@Autowired private CustomerFundAuthMapper customerFundAuthMapper;

}
