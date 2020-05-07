/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.dao.BankSubMapper;
import com.anjbo.service.BankService;
import com.anjbo.service.BankSubService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-02 18:19:28
 * @version 1.0
 */
@Service
public class BankSubServiceImpl extends BaseServiceImpl<BankSubDto>  implements BankSubService {
	@Autowired private BankSubMapper bankSubMapper;
	
	@Autowired private BankService bankService;

	@Override
	public List<BankSubDto> search(BankSubDto dto) {
		List<BankSubDto> bankSubDtos = super.search(dto);
		if(dto != null && dto.getIsRelation() == 1) {
			return bankSubDtos;
		}
		BankDto bank = new BankDto();
		bank.setIsRelation(1);
		List<BankDto> bankDtos = bankService.search(bank);
		for (BankSubDto bankSubDto : bankSubDtos) {
			
			for (BankDto bankDto : bankDtos) {
				if(bankDto.getId().equals(bankSubDto.getPid())) {
					bankSubDto.setBankName(bankDto.getName());
					break;
				}
			}
		}
		return bankSubDtos;
	}
	
}
