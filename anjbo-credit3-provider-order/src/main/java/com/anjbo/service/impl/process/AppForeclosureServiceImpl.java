/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.process.AppForeclosureDto;
import com.anjbo.dao.process.AppForeclosureMapper;
import com.anjbo.service.process.AppForeclosureService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@Service
public class AppForeclosureServiceImpl extends BaseServiceImpl<AppForeclosureDto>  implements AppForeclosureService {
	@Autowired private AppForeclosureMapper appForeclosureMapper;

}
