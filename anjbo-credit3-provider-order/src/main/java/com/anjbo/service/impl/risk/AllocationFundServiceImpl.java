/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.dao.risk.AllocationFundMapper;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@Service
public class AllocationFundServiceImpl extends BaseServiceImpl<AllocationFundDto>  implements AllocationFundService {
	@Autowired private AllocationFundMapper allocationFundMapper;

}
