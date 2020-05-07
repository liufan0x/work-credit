package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerAgencyFeescaleModeMapper;
import com.anjbo.service.CustomerAgencyFeescaleModeService;
import com.anjbo.bean.customer.CustomerAgencyFeescaleModeDto;

/**
  * 收费方式 [Service实现类]
  * @ClassName: CustomerAgencyFeescaleModeServiceImpl
  * @Description: 收费方式业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyFeescaleModeServiceImpl  implements CustomerAgencyFeescaleModeService
{
	
	@Resource
	private CustomerAgencyFeescaleModeMapper customerAgencyFeescaleModeMapper;

	@Override
	public List<CustomerAgencyFeescaleModeDto> selectCustomerAgencyFeescaleModeList(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto){
		return customerAgencyFeescaleModeMapper.selectCustomerAgencyFeescaleModeList(customerAgencyFeescaleModeDto);
	}

	@Override
	public int selectCustomerAgencyFeescaleModeCount(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto) {
		return customerAgencyFeescaleModeMapper.selectCustomerAgencyFeescaleModeCount(customerAgencyFeescaleModeDto);
	}
	
	@Override
	public int addCustomerAgencyFeescaleMode(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto) {
		return customerAgencyFeescaleModeMapper.addCustomerAgencyFeescaleMode(customerAgencyFeescaleModeDto);
	}
	
	@Override
	public int updateCustomerAgencyFeescaleMode(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto) {
		return customerAgencyFeescaleModeMapper.updateCustomerAgencyFeescaleMode(customerAgencyFeescaleModeDto);
	}
	
	@Override
	public int deleteCustomerAgencyFeescaleModeById(int id){
		return customerAgencyFeescaleModeMapper.deleteCustomerAgencyFeescaleModeById(id);
	}
	
	@Override
	public CustomerAgencyFeescaleModeDto selectCustomerAgencyFeescaleModeById(int id){
		return customerAgencyFeescaleModeMapper.selectCustomerAgencyFeescaleModeById(id);
	}
	
}