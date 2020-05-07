package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleModeDto;
 
 /**
  * 收费方式 [Dao接口类]
  * @ClassName: CustomerAgencyFeescaleModeMapper
  * @Description: 
  * @date 2017-07-06 14:48:35
  * @version V3.0
 */
public interface CustomerAgencyFeescaleModeMapper{
	/**
	 * 获取收费方式列表
	 * @param customerAgencyFeescaleModeDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleModeDto> selectCustomerAgencyFeescaleModeList(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto);

	/**
	 * 获取收费方式列表总数
	 * @param customerAgencyFeescaleModeDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleModeCount(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto);
	
	/**
	 * @description:添加 收费方式
	 * @param CustomerAgencyFeescaleModeDto
	 * @return int
	 */
	int addCustomerAgencyFeescaleMode(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto);
	
	/**
	 * @description:修改 收费方式
	 * @param CustomerAgencyFeescaleModeDto
	 * @return int
	 */
	int updateCustomerAgencyFeescaleMode(CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto);
	/**
	 * @description:根据ID删除 收费方式
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleModeById(int id);	
	
	/**
	 * @description:根据ID查询 收费方式
	 * @param int
	 * @return CustomerAgencyFeescaleModeDto
	 */
	CustomerAgencyFeescaleModeDto selectCustomerAgencyFeescaleModeById(int id);

}