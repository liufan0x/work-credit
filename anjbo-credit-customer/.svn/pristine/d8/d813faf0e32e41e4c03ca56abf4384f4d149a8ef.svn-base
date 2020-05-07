package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerAgencyFeescaleDetailMapper;
import com.anjbo.service.CustomerAgencyFeescaleDetailService;
import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;

/**
  * 收费标准详细 [Service实现类]
  * @ClassName: CustomerAgencyFeescaleDetailServiceImpl
  * @Description: 收费标准详细业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyFeescaleDetailServiceImpl  implements CustomerAgencyFeescaleDetailService
{
	
	@Resource
	private CustomerAgencyFeescaleDetailMapper customerAgencyFeescaleDetailMapper;

	@Override
	public List<CustomerAgencyFeescaleDetailDto> selectCustomerAgencyFeescaleDetailList(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto){
		return customerAgencyFeescaleDetailMapper.selectCustomerAgencyFeescaleDetailList(customerAgencyFeescaleDetailDto);
	}

	@Override
	public int selectCustomerAgencyFeescaleDetailCount(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto) {
		return customerAgencyFeescaleDetailMapper.selectCustomerAgencyFeescaleDetailCount(customerAgencyFeescaleDetailDto);
	}
	
	@Override
	public int addCustomerAgencyFeescaleDetail(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto) {
		return customerAgencyFeescaleDetailMapper.addCustomerAgencyFeescaleDetail(customerAgencyFeescaleDetailDto);
	}
	
	@Override
	public int updateCustomerAgencyFeescaleDetail(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto) {
		return customerAgencyFeescaleDetailMapper.updateCustomerAgencyFeescaleDetail(customerAgencyFeescaleDetailDto);
	}
	
	@Override
	public int deleteCustomerAgencyFeescaleDetailById(int id){
		return customerAgencyFeescaleDetailMapper.deleteCustomerAgencyFeescaleDetailById(id);
	}
	
	@Override
	public CustomerAgencyFeescaleDetailDto selectCustomerAgencyFeescaleDetailById(int id){
		return customerAgencyFeescaleDetailMapper.selectCustomerAgencyFeescaleDetailById(id);
	}
	
}