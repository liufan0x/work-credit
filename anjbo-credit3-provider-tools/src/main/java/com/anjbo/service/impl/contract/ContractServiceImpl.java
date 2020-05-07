/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.contract.ContractDto;
import com.anjbo.dao.contract.ContractMapper;
import com.anjbo.service.contract.ContractService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@Service
public class ContractServiceImpl extends BaseServiceImpl<ContractDto>  implements ContractService {
	@Autowired private ContractMapper contractMapper;
	
	@Override
	public ContractDto insert(ContractDto dto) {
		dto.setSort(contractMapper.selectMaxSort()+1);
		return super.insert(dto);
	}
	
}
