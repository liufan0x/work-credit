/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.process.AppNotarizationDto;
import com.anjbo.dao.process.AppNotarizationMapper;
import com.anjbo.service.process.AppNotarizationService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@Service
public class AppNotarizationServiceImpl extends BaseServiceImpl<AppNotarizationDto>  implements AppNotarizationService {
	@Autowired private AppNotarizationMapper appNotarizationMapper;

}
