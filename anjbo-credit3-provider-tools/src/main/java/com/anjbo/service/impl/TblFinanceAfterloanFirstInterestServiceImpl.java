/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.dao.TblFinanceAfterloanFirstInterestMapper;
import com.anjbo.service.TblFinanceAfterloanFirstInterestService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:30
 * @version 1.0
 */
@Service
public class TblFinanceAfterloanFirstInterestServiceImpl extends BaseServiceImpl<TblFinanceAfterloanFirstInterestDto>  implements TblFinanceAfterloanFirstInterestService {
	@Autowired private TblFinanceAfterloanFirstInterestMapper tblFinanceAfterloanFirstInterestMapper;

}
