package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerFundCostDto;

 /**
  * 合作资金方业务产品 [Service接口类]
  * @ClassName: CustomerFundCostService
  * @Description: 合作资金方业务产品业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
public interface CustomerFundCostService{
	/**
	 * 获取合作资金方业务产品列表
	 * @param customerFundCostDto
	 * @return list
	 */
	List<CustomerFundCostDto> selectCustomerFundCostList(CustomerFundCostDto customerFundCostDto);

	/**
	 * 获取合作资金方业务产品总数
	 * @param customerFundCostDto
	 * @return int
	 */
	int selectCustomerFundCostCount(CustomerFundCostDto customerFundCostDto);
	
	/**
	 * @description:添加 合作资金方业务产品
	 * @param CustomerFundCostDto
	 * @return int
	 */
	int addCustomerFundCost(CustomerFundCostDto customerFundCostDto);
	
	/**
	 * @description:修改 合作资金方业务产品
	 * @param CustomerFundCostDto
	 * @return int
	 */
	int updateCustomerFundCost(CustomerFundCostDto customerFundCostDto);
	/**
	 * @description:根据ID删除 合作资金方业务产品
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerFundCostById(int id);	
	
	/**
	 * @description:根据ID查询 合作资金方业务产品
	 * @param int
	 * @return CustomerFundCostDto
	 */
	CustomerFundCostDto selectCustomerFundCostById(int id);
	
	CustomerFundCostDto selectCustomerFundCostByFundId(CustomerFundCostDto customerFundCostDto);
}