/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.process.AppCancellationDto;
import com.anjbo.dao.process.AppCancellationMapper;
import com.anjbo.service.process.AppCancellationService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:32
 * @version 1.0
 */
@Service
public class AppCancellationServiceImpl extends BaseServiceImpl<AppCancellationDto>  implements AppCancellationService {
	@Autowired private AppCancellationMapper appCancellationMapper;
	
	@Override
	public void add(AppCancellationDto dto) {
		AppCancellationDto temp = new AppCancellationDto();
		temp.setOrderNo(dto.getOrderNo());
		temp = find(temp);
		if(temp == null) {
			insert(dto);
		}else {
			update(dto);
		}
	}
	
}
