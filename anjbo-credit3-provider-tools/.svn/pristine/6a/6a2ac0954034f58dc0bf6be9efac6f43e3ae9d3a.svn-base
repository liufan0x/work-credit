/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.contract.ContractListRecordDto;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.contract.ContractListRecordMapper;
import com.anjbo.service.contract.ContractListRecordService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 20:08:34
 * @version 1.0
 */
@Service
public class ContractListRecordServiceImpl extends BaseServiceImpl<ContractListRecordDto>  implements ContractListRecordService {
	@Autowired private ContractListRecordMapper contractListRecordMapper;
	
	@Resource private UserApi userApi;
	
	@Override
	public List<ContractListRecordDto> search(ContractListRecordDto dto) {
		List<ContractListRecordDto> list = super.search(dto);
		List<UserDto> userDtos = userApi.selectAllUserDto();
		for (ContractListRecordDto contractListRecordDto : list) {
			for (UserDto userDto : userDtos) {
				if(contractListRecordDto.getCreateUid().equals(userDto.getUid())) {
					contractListRecordDto.setCreateName(userDto.getName());
				}
			}
		}
		return list;
	}
	
	
}
