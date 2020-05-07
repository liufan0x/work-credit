/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderUpDto;
import com.anjbo.dao.OrderUpMapper;
import com.anjbo.service.OrderUpService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-03-07 14:16:11
 * @version 1.0
 */
@Service
public class OrderUpServiceImpl extends BaseServiceImpl<OrderUpDto, Long>  implements OrderUpService {
	@Autowired private OrderUpMapper orderUpMapper;

}
