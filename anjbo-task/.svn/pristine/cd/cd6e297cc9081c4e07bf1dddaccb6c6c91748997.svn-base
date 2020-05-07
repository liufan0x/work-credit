package com.anjbo.service.msloan.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.msloan.MSCustomerDto;
import com.anjbo.dao.mort.msloan.MSLoanMapper;
import com.anjbo.service.msloan.MSLoanService;

/**
 * Created by Administrator on 2017/3/31.
 */
@Service("msLoanService")
public class MSLoanServiceImpl implements MSLoanService {

    @Resource
    private MSLoanMapper msLoanMapper;

	@Override
	public List<MSCustomerDto> selectMSDetailByQuota() {
		return msLoanMapper.selectMSDetailByQuota();
	}

	@Override
	public int updateQuotaById(MSCustomerDto msCustomerDto) {
		return msLoanMapper.updateQuotaById(msCustomerDto);
	}

	@Override
	public int updateMSSmsStatebyId(MSCustomerDto msCustomerDto) {
		return msLoanMapper.updateMSSmsStatebyId(msCustomerDto);
	}

}
