package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerFundCostDiscountDto;

 /**
  * 合作资金方业务产品优惠 [Service接口类]
  * @ClassName: CustomerFundCostDiscountService
  * @Description: 合作资金方业务产品优惠业务服务
  * @author 
  * @date 2017-07-06 15:53:15
  * @version V3.0
 */
public interface CustomerFundCostDiscountService{
	/**
	 * 获取合作资金方业务产品优惠列表
	 * @param customerFundCostDiscountDto
	 * @return list
	 */
	List<CustomerFundCostDiscountDto> selectCustomerFundCostDiscountList(CustomerFundCostDiscountDto customerFundCostDiscountDto);

	/**
	 * 获取合作资金方业务产品优惠总数
	 * @param customerFundCostDiscountDto
	 * @return int
	 */
	int selectCustomerFundCostDiscountCount(CustomerFundCostDiscountDto customerFundCostDiscountDto);
	
	/**
	 * @description:添加 合作资金方业务产品优惠
	 * @param CustomerFundCostDiscountDto
	 * @return int
	 */
	int addCustomerFundCostDiscount(CustomerFundCostDiscountDto customerFundCostDiscountDto);
	
	/**
	 * @description:修改 合作资金方业务产品优惠
	 * @param CustomerFundCostDiscountDto
	 * @return int
	 */
	int updateCustomerFundCostDiscount(CustomerFundCostDiscountDto customerFundCostDiscountDto);
	/**
	 * @description:根据ID删除 合作资金方业务产品优惠
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerFundCostDiscountById(int id);	
	
	/**
	 * @description:根据ID查询 合作资金方业务产品优惠
	 * @param int
	 * @return CustomerFundCostDiscountDto
	 */
	CustomerFundCostDiscountDto selectCustomerFundCostDiscountById(int id);

	void deleteCustomerFundCostDiscountByFundCostId(int id);
}