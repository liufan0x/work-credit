/*
 *Project: anjbo-credit-customer
 *File: com.anjbo.service.impl.AgencyIncomeModeServiceImpl.java  <2017年11月14日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.AgencyIncomeModeDto;
import com.anjbo.dao.AgencyIncomeModeMapper;
import com.anjbo.service.AgencyIncomeModeService;

/**
 * @Author KangLG
 * @Date 2017年11月14日 下午5:03:39
 * @version 1.0
 */
@Service
public class AgencyIncomeModeServiceImpl extends BaseServiceImpl<AgencyIncomeModeDto, Integer> implements AgencyIncomeModeService {
	@Autowired private AgencyIncomeModeMapper agencyIncomeModeMapper;

}
