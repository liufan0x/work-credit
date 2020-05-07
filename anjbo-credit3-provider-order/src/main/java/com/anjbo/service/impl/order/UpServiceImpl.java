/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.UpDto;
import com.anjbo.dao.order.UpMapper;
import com.anjbo.service.order.UpService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:49
 * @version 1.0
 */
@Service
public class UpServiceImpl extends BaseServiceImpl<UpDto>  implements UpService {
	@Autowired private UpMapper upMapper;

}
