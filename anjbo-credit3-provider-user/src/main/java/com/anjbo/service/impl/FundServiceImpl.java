/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.FundAuthDto;
import com.anjbo.bean.FundDto;
import com.anjbo.dao.FundMapper;
import com.anjbo.service.FundAuthService;
import com.anjbo.service.FundService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@Service
public class FundServiceImpl extends BaseServiceImpl<FundDto>  implements FundService {
	@Autowired private FundMapper fundMapper;
	
	@Resource private FundAuthService fundAuthService;
	
	@Override
	public FundDto find(FundDto dto) {
		FundDto fundDto = super.find(dto);
		FundAuthDto fundAuthDto = new FundAuthDto();
		fundAuthDto.setId(fundDto.getId());
		fundAuthDto = fundAuthService.find(fundAuthDto);
		fundDto.setAuths(fundAuthDto.getAuths());
		return fundDto;
	}
	
	@Override
	public FundDto insert(FundDto dto) {
		FundDto fundDto = super.insert(dto);
		FundAuthDto fundAuthDto = new FundAuthDto();
		fundAuthDto.setId(fundDto.getId());
		fundAuthDto.setFundCode(fundDto.getFundCode());
		fundAuthDto.setAuths(fundDto.getAuths());
		fundAuthService.insert(fundAuthDto);
		return fundDto;
	}
	
	@Override
	public int update(FundDto dto) {
		if(StringUtils.isEmpty(dto.getAuths())) {
			return super.update(dto);
		}else {
			super.update(dto);
			FundAuthDto fundAuthDto = new FundAuthDto();
			fundAuthDto.setId(dto.getId());
			fundAuthDto.setFundCode(dto.getFundCode());
			fundAuthDto.setAuths(dto.getAuths());
			return fundAuthService.update(fundAuthDto);
		}
	}

	@Override
	public FundDto selectCustomerFundById(int id) {
		// TODO Auto-generated method stub
		return fundMapper.selectCustomerFundById(id);
	}

	@Override
	public List<FundDto> selectFundListByStatus(FundDto fundDto) {
		return fundMapper.selectFundListByStatus(fundDto);
	}
}
