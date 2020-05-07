package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.CustomerFundAuthDto;
import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.dao.CustomerFundAuthMapper;
import com.anjbo.dao.CustomerFundMapper;
import com.anjbo.service.CustomerFundService;

/**
  * 合作资金方 [Service实现类]
  * @ClassName: CustomerFundServiceImpl
  * @Description: 合作资金方业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerFundServiceImpl  implements CustomerFundService{	
	@Resource private CustomerFundMapper customerFundMapper;
	@Resource private CustomerFundAuthMapper customerFundAuthMapper;

	@Override
	public List<CustomerFundDto> selectCustomerFundList(CustomerFundDto customerFundDto){
		return customerFundMapper.selectCustomerFundList(customerFundDto);
	}

	@Override
	public int selectCustomerFundCount(CustomerFundDto customerFundDto) {
		return customerFundMapper.selectCustomerFundCount(customerFundDto);
	}
	
	@Override
	public int addCustomerFund(CustomerFundDto customerFundDto) {
		return customerFundMapper.addCustomerFund(customerFundDto);
	}
	@Override
	public long insert(CustomerFundDto vo) {
		customerFundMapper.addCustomerFund(vo);
		return customerFundAuthMapper.insert(new CustomerFundAuthDto(vo.getId(), vo.getFundCode(), vo.getAuths()));
	}
	
	@Override
	public int updateCustomerFund(CustomerFundDto customerFundDto) {
		return customerFundMapper.updateCustomerFund(customerFundDto);
	}
	@Override
	public int update(CustomerFundDto vo) {
		customerFundMapper.updateCustomerFund(vo);
		return customerFundAuthMapper.update(new CustomerFundAuthDto(vo.getId(), vo.getFundCode(), vo.getAuths()));
	}
	
	@Override
	public int deleteCustomerFundById(int id){
		return customerFundMapper.deleteCustomerFundById(id);
	}
	
	@Override
	public CustomerFundDto selectCustomerFundById(int id){
		return customerFundMapper.selectCustomerFundById(id);
	}
	
	@Override
	public CustomerFundDto selectCustomerFundByFundCode(String fundCode) {
		return customerFundMapper.selectCustomerFundByFundCode(fundCode);
	}
}