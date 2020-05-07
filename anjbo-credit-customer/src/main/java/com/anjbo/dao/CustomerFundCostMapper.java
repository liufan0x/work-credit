package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerFundCostDto;
 
 /**
  * 合作资金方业务产品 [Dao接口类]
  * @ClassName: CustomerFundCostMapper
  * @Description: 
  * @date 2017-07-06 14:48:34
  * @version V3.0
 */
public interface CustomerFundCostMapper{
	/**
	 * 获取合作资金方业务产品列表
	 * @param customerFundCostDto
	 * @return list
	 */
	List<CustomerFundCostDto> selectCustomerFundCostList(CustomerFundCostDto customerFundCostDto);

	/**
	 * 获取合作资金方业务产品列表总数
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
	/**
	 * @description:根据资金方ID跟业务品种查询 合作资金方业务产品
	 * @param int
	 * @return CustomerFundCostDto
	 */
	CustomerFundCostDto selectCustomerFundCostByFundId(CustomerFundCostDto customerFundCostDto);

}