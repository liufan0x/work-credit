/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.dao.element.PaymentTypeMapper;
import com.anjbo.service.element.PaymentTypeService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:31
 * @version 1.0
 */
@Service
public class PaymentTypeServiceImpl extends BaseServiceImpl<PaymentTypeDto>  implements PaymentTypeService {
	@Autowired private PaymentTypeMapper paymentTypeMapper;

}
