/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.contract.ContractDto;
import com.anjbo.bean.contract.ContractGroupDto;
import com.anjbo.dao.contract.ContractGroupMapper;
import com.anjbo.service.contract.ContractGroupService;
import com.anjbo.service.contract.ContractService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@Service
public class ContractGroupServiceImpl extends BaseServiceImpl<ContractGroupDto>  implements ContractGroupService {
	@Autowired private ContractGroupMapper contractGroupMapper;
	
	@Resource private ContractService contractService;
	
	@Override
	public List<ContractGroupDto> search(ContractGroupDto dto) {
		List<ContractGroupDto> list = super.search(dto);
		List<ContractDto> contractDtos = null;
		if(dto != null && dto.getIsEnable() != null && dto.getIsEnable() == 1) {
			ContractDto contractDto = new ContractDto();
			contractDto.setIsEnable(dto.getIsEnable());
			contractDtos = contractService.search(contractDto);
		}else {
			contractDtos = contractService.search(null);
		}
		
		List<ContractDto> tempList = null;
		for (ContractGroupDto contractGroupDto : list) {
			tempList = new ArrayList<ContractDto>();
			for (ContractDto contractDto : contractDtos) {
				if(contractGroupDto.getId().equals(contractDto.getGroupId())) {
					tempList.add(contractDto);
				}
			}
			contractGroupDto.setContractList(tempList);
		}
		return list;
	}
	
}
