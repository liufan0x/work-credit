package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerFundDto;

 /**
  * 合作资金方 [Service接口类]
  * @ClassName: CustomerFundService
  * @Description: 合作资金方业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
public interface CustomerFundService{
	/**
	 * 获取合作资金方列表
	 * @param customerFundDto
	 * @return list
	 */
	List<CustomerFundDto> selectCustomerFundList(CustomerFundDto customerFundDto);

	/**
	 * 获取合作资金方总数
	 * @param customerFundDto
	 * @return int
	 */
	int selectCustomerFundCount(CustomerFundDto customerFundDto);
	
	/**
	 * @description:添加 合作资金方
	 * @param CustomerFundDto
	 * @return int
	 */
	int addCustomerFund(CustomerFundDto customerFundDto);
	long insert(CustomerFundDto vo);
	
	
	/**
	 * @description:修改 合作资金方
	 * @param CustomerFundDto
	 * @return int
	 */
	int updateCustomerFund(CustomerFundDto customerFundDto);
	int update(CustomerFundDto vo);
	/**
	 * @description:根据ID删除 合作资金方
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerFundById(int id);	
	
	/**
	 * @description:根据ID查询 合作资金方
	 * @param int
	 * @return CustomerFundDto
	 */
	CustomerFundDto selectCustomerFundById(int id);

	/**
	 * @description:根据代码查询 合作资金方
	 * @param String
	 * @return CustomerFundDto
	 */
	CustomerFundDto selectCustomerFundByFundCode(String fundCode);
}