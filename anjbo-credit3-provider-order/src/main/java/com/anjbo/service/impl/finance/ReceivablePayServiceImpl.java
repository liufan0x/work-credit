/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.dao.finance.ReceivablePayMapper;
import com.anjbo.service.finance.ReceivablePayService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@Service
public class ReceivablePayServiceImpl extends BaseServiceImpl<ReceivablePayDto>  implements ReceivablePayService {
	@Autowired private ReceivablePayMapper receivablePayMapper;

}
