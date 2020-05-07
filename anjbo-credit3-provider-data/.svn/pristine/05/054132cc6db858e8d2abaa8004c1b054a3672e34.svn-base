/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.dao.BankMapper;
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
public class BankServiceImpl extends BaseServiceImpl<BankDto>  implements BankService {
	@Autowired private BankMapper bankMapper;
	
	@Resource private BankSubService bankSubService;
	
	@Override
	public List<BankDto> search(BankDto dto) {
		List<BankDto> bankDtos = super.search(dto);
		if(dto != null && 1 == dto.getIsRelation()) {
			return bankDtos;
		}
		BankSubDto bankSub = new BankSubDto();
		bankSub.setIsRelation(1);
		List<BankSubDto> bankSubDtos = bankSubService.search(bankSub);
		for (BankDto bankDto : bankDtos) {
			List<BankSubDto> tempList = new ArrayList<BankSubDto>();
			for (BankSubDto bankSubDto : bankSubDtos) {
				if(bankDto.getId().equals(bankSubDto.getPid())) {
					tempList.add(bankSubDto);
				}
			}
			bankDto.setSubBankDtos(tempList);
		}
		return bankDtos;
	}
	
}
