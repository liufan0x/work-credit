/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseCustomerBorrowerDto;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.bean.order.BaseCustomerShareholderDto;
import com.anjbo.dao.order.BaseCustomerBorrowerMapper;
import com.anjbo.dao.order.BaseCustomerGuaranteeMapper;
import com.anjbo.dao.order.BaseCustomerMapper;
import com.anjbo.dao.order.BaseCustomerShareholderMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.order.BaseCustomerService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@Service
public class BaseCustomerServiceImpl extends BaseServiceImpl<BaseCustomerDto>  implements BaseCustomerService {
	@Autowired private BaseCustomerMapper baseCustomerMapper;
	@Autowired private BaseCustomerGuaranteeMapper baseCustomerGuaranteeMapper;
	@Autowired private BaseCustomerBorrowerMapper baseCustomerBorrowerMapper;
	@Autowired private BaseCustomerShareholderMapper baseCustomerShareholderMapper;
	@Override
	public BaseCustomerDto find(BaseCustomerDto dto) {
		String orderNo = dto.getOrderNo();
		BaseCustomerDto baseCustomerDto = baseCustomerMapper.find(dto);
		if(null != baseCustomerDto){
			BaseCustomerGuaranteeDto baseCustomerGuaranteeDto = new BaseCustomerGuaranteeDto();
			baseCustomerGuaranteeDto.setOrderNo(orderNo);
			baseCustomerDto.setCustomerGuaranteeDto(baseCustomerGuaranteeMapper.search(baseCustomerGuaranteeDto));  	//担保人
			BaseCustomerBorrowerDto baseCustomerBorrowerDto = new BaseCustomerBorrowerDto();
			baseCustomerBorrowerDto.setOrderNo(orderNo);
			baseCustomerDto.setCustomerBorrowerDto(baseCustomerBorrowerMapper.search(baseCustomerBorrowerDto));			//共同借款人
			BaseCustomerShareholderDto baseCustomerShareholderDto = new BaseCustomerShareholderDto();
			baseCustomerShareholderDto.setOrderNo(orderNo);
			baseCustomerDto.setCustomerShareholderDto(baseCustomerShareholderMapper.search(baseCustomerShareholderDto));	//企业股东
		}
		return baseCustomerDto;
	}
	@Override
	public Map<Object, String> allCustomerNos(String orderNo) {
		// TODO Auto-generated method stub
		return baseCustomerMapper.allCustomerNos(orderNo);
	}
}
