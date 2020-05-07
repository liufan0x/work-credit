package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyAcceptDto;
 
 /**
  * 机构受理员 [Dao接口类]
  * @ClassName: CustomerAgencyAcceptMapper
  * @Description: 
  * @date 2017-07-06 14:48:34
  * @version V3.0
 */
public interface CustomerAgencyAcceptMapper{
	/**
	 * 获取机构受理员列表
	 * @param customerAgencyAcceptDto
	 * @return list
	 */
	List<CustomerAgencyAcceptDto> selectCustomerAgencyAcceptList(CustomerAgencyAcceptDto customerAgencyAcceptDto);

	/**
	 * 获取机构受理员列表总数
	 * @param customerAgencyAcceptDto
	 * @return int
	 */
	int selectCustomerAgencyAcceptCount(CustomerAgencyAcceptDto customerAgencyAcceptDto);
	
	/**
	 * @description:添加 机构受理员
	 * @param CustomerAgencyAcceptDto
	 * @return int
	 */
	int addCustomerAgencyAccept(CustomerAgencyAcceptDto customerAgencyAcceptDto);
	
	/**
	 * @description:修改 机构受理员
	 * @param CustomerAgencyAcceptDto
	 * @return int
	 */
	int updateCustomerAgencyAccept(CustomerAgencyAcceptDto customerAgencyAcceptDto);
	/**
	 * @description:根据ID删除 机构受理员
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyAcceptByAgencyId(int id);	
	
	/**
	 * @description:根据ID查询 机构受理员
	 * @param int
	 * @return CustomerAgencyAcceptDto
	 */
	CustomerAgencyAcceptDto selectCustomerAgencyAcceptById(int id);

}