/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.process.AppFacesignDto;
import com.anjbo.dao.process.AppFacesignMapper;
import com.anjbo.service.process.AppFacesignService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:33
 * @version 1.0
 */
@Service
public class AppFacesignServiceImpl extends BaseServiceImpl<AppFacesignDto>  implements AppFacesignService {
	@Autowired private AppFacesignMapper appFacesignMapper;

	@Override
	public int updateByOrderNo(AppFacesignDto appFacesignDto) {
		return appFacesignMapper.updateByOrderNo(appFacesignDto);
	}

}
