package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerAgencyAcceptMapper;
import com.anjbo.service.CustomerAgencyAcceptService;
import com.anjbo.bean.customer.CustomerAgencyAcceptDto;

/**
  * 机构受理员 [Service实现类]
  * @ClassName: CustomerAgencyAcceptServiceImpl
  * @Description: 机构受理员业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyAcceptServiceImpl  implements CustomerAgencyAcceptService
{
	
	@Resource
	private CustomerAgencyAcceptMapper customerAgencyAcceptMapper;

	@Override
	public List<CustomerAgencyAcceptDto> selectCustomerAgencyAcceptList(CustomerAgencyAcceptDto customerAgencyAcceptDto){
		return customerAgencyAcceptMapper.selectCustomerAgencyAcceptList(customerAgencyAcceptDto);
	}

	@Override
	public int selectCustomerAgencyAcceptCount(CustomerAgencyAcceptDto customerAgencyAcceptDto) {
		return customerAgencyAcceptMapper.selectCustomerAgencyAcceptCount(customerAgencyAcceptDto);
	}
	
	@Override
	public int addCustomerAgencyAccept(CustomerAgencyAcceptDto customerAgencyAcceptDto) {
		return customerAgencyAcceptMapper.addCustomerAgencyAccept(customerAgencyAcceptDto);
	}
	
	@Override
	public int updateCustomerAgencyAccept(CustomerAgencyAcceptDto customerAgencyAcceptDto) {
		return customerAgencyAcceptMapper.updateCustomerAgencyAccept(customerAgencyAcceptDto);
	}
	
	@Override
	public int deleteCustomerAgencyAcceptByAgencyId(int id){
		return customerAgencyAcceptMapper.deleteCustomerAgencyAcceptByAgencyId(id);
	}
	
	@Override
	public CustomerAgencyAcceptDto selectCustomerAgencyAcceptById(int id){
		return customerAgencyAcceptMapper.selectCustomerAgencyAcceptById(id);
	}
	
}