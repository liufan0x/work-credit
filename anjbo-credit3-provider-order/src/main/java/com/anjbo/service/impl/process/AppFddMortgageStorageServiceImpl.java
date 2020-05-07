/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.process.AppFddMortgageStorageDto;
import com.anjbo.dao.process.AppFddMortgageStorageMapper;
import com.anjbo.service.process.AppFddMortgageStorageService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@Service
public class AppFddMortgageStorageServiceImpl extends BaseServiceImpl<AppFddMortgageStorageDto>  implements AppFddMortgageStorageService {
	@Autowired private AppFddMortgageStorageMapper appFddMortgageStorageMapper;

}
