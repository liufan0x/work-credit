package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerFundCostMapper;
import com.anjbo.service.CustomerFundCostService;
import com.anjbo.bean.customer.CustomerFundCostDto;

/**
  * 合作资金方业务产品 [Service实现类]
  * @ClassName: CustomerFundCostServiceImpl
  * @Description: 合作资金方业务产品业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerFundCostServiceImpl  implements CustomerFundCostService
{
	
	@Resource
	private CustomerFundCostMapper customerFundCostMapper;

	@Override
	public List<CustomerFundCostDto> selectCustomerFundCostList(CustomerFundCostDto customerFundCostDto){
		return customerFundCostMapper.selectCustomerFundCostList(customerFundCostDto);
	}

	@Override
	public int selectCustomerFundCostCount(CustomerFundCostDto customerFundCostDto) {
		return customerFundCostMapper.selectCustomerFundCostCount(customerFundCostDto);
	}
	
	@Override
	public int addCustomerFundCost(CustomerFundCostDto customerFundCostDto) {
		return customerFundCostMapper.addCustomerFundCost(customerFundCostDto);
	}
	
	@Override
	public int updateCustomerFundCost(CustomerFundCostDto customerFundCostDto) {
		return customerFundCostMapper.updateCustomerFundCost(customerFundCostDto);
	}
	
	@Override
	public int deleteCustomerFundCostById(int id){
		return customerFundCostMapper.deleteCustomerFundCostById(id);
	}
	
	@Override
	public CustomerFundCostDto selectCustomerFundCostById(int id){
		return customerFundCostMapper.selectCustomerFundCostById(id);
	}

	@Override
	public CustomerFundCostDto selectCustomerFundCostByFundId(
			CustomerFundCostDto customerFundCostDto) {
		// TODO Auto-generated method stub
		return customerFundCostMapper.selectCustomerFundCostByFundId(customerFundCostDto);
	}
	
}