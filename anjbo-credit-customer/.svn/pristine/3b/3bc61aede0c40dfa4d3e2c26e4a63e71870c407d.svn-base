package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyDto;

 /**
  * 机构 [Service接口类]
  * @ClassName: CustomerAgencyService
  * @Description: 机构业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
public interface CustomerAgencyService{
	/**
	 * 获取机构列表
	 * @param customerAgencyDto
	 * @return list
	 */
	List<CustomerAgencyDto> selectCustomerAgencyList(CustomerAgencyDto customerAgencyDto);

	/**
	 * 获取机构总数
	 * @param customerAgencyDto
	 * @return int
	 */
	int selectCustomerAgencyCount(CustomerAgencyDto customerAgencyDto);
	
	/**
	 * @description:添加 机构
	 * @param CustomerAgencyDto
	 * @return int
	 */
	int addCustomerAgency(CustomerAgencyDto customerAgencyDto);
	
	/**
	 * @description:修改 机构
	 * @param CustomerAgencyDto
	 * @return int
	 */
	int updateCustomerAgency(CustomerAgencyDto customerAgencyDto);
	/**
	 * @description:根据ID删除 机构
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyById(int id);	
	
	/**
	 * @description:根据ID查询 机构
	 * @param int
	 * @return CustomerAgencyDto
	 */
	CustomerAgencyDto selectCustomerAgencyById(int id);
}