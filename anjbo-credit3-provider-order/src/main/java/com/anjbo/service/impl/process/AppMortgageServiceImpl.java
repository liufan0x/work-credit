/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.process.AppMortgageDto;
import com.anjbo.dao.process.AppMortgageMapper;
import com.anjbo.service.process.AppMortgageService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@Service
public class AppMortgageServiceImpl extends BaseServiceImpl<AppMortgageDto>  implements AppMortgageService {
	@Autowired private AppMortgageMapper appMortgageMapper;

	@Override
	public void add(AppMortgageDto dto) {
		AppMortgageDto temp = new AppMortgageDto();
		temp.setOrderNo(dto.getOrderNo());
		temp = find(temp);
		if(temp == null) {
			insert(dto);
		}else {
			update(dto);
		}
	}
	
}
