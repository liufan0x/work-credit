/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.dao.order.BaseCustomerGuaranteeMapper;
import com.anjbo.service.order.BaseCustomerGuaranteeService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@Service
public class BaseCustomerGuaranteeServiceImpl extends BaseServiceImpl<BaseCustomerGuaranteeDto>  implements BaseCustomerGuaranteeService {
	@Autowired private BaseCustomerGuaranteeMapper baseCustomerGuaranteeMapper;

}
