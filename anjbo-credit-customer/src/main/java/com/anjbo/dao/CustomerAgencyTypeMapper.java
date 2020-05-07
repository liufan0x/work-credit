package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyTypeDto;
 
 /**
  * 机构类型 [Dao接口类]
  * @ClassName: CustomerAgencyTypeMapper
  * @Description: 
  * @date 2017-07-06 14:48:34
  * @version V3.0
 */
public interface CustomerAgencyTypeMapper{
	/**
	 * 获取机构类型列表
	 * @param customerAgencyTypeDto
	 * @return list
	 */
	List<CustomerAgencyTypeDto> selectCustomerAgencyTypeList(CustomerAgencyTypeDto customerAgencyTypeDto);

	/**
	 * 获取机构类型列表总数
	 * @param customerAgencyTypeDto
	 * @return int
	 */
	int selectCustomerAgencyTypeCount(CustomerAgencyTypeDto customerAgencyTypeDto);
	
	/**
	 * @description:添加 机构类型
	 * @param CustomerAgencyTypeDto
	 * @return int
	 */
	int addCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto);
	
	/**
	 * @description:修改 机构类型
	 * @param CustomerAgencyTypeDto
	 * @return int
	 */
	int updateCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto);
	/**
	 * @description:根据ID删除 机构类型
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyTypeById(int id);	
	
	/**
	 * @description:根据ID查询 机构类型
	 * @param int
	 * @return CustomerAgencyTypeDto
	 */
	CustomerAgencyTypeDto selectCustomerAgencyTypeById(int id);

	/**
	 * @description:根据名称查询 机构类型
	 * @param customerAgencyTypeDto
	 * @return CustomerAgencyTypeDto
	 */
	CustomerAgencyTypeDto selectCustomerAgencyTypeByName(CustomerAgencyTypeDto customerAgencyTypeDto);

}