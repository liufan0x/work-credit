/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.process.AppTransferDto;
import com.anjbo.dao.process.AppTransferMapper;
import com.anjbo.service.process.AppTransferService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@Service
public class AppTransferServiceImpl extends BaseServiceImpl<AppTransferDto>  implements AppTransferService {
	@Autowired private AppTransferMapper appTransferMapper;

	@Override
	public void add(AppTransferDto dto) {
		AppTransferDto temp = new AppTransferDto();
		temp.setOrderNo(dto.getOrderNo());
		temp = find(temp);
		if(temp == null) {
			insert(dto);
		}else {
			update(dto);
		}
	}
	
}
