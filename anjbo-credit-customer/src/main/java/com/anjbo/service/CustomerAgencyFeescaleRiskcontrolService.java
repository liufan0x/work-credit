package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleRiskcontrolDto;

 /**
  * 风控配置 [Service接口类]
  * @ClassName: CustomerAgencyFeescaleRiskcontrolService
  * @Description: 风控配置业务服务
  * @author 
  * @date 2017-07-06 15:03:11
  * @version V3.0
 */
public interface CustomerAgencyFeescaleRiskcontrolService{
	/**
	 * 获取风控配置列表
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleRiskcontrolDto> selectCustomerAgencyFeescaleRiskcontrolList(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto);

	/**
	 * 获取风控配置总数
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleRiskcontrolCount(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto);
	
	/**
	 * @description:添加 风控配置
	 * @param CustomerAgencyFeescaleRiskcontrolDto
	 * @return int
	 */
	int addCustomerAgencyFeescaleRiskcontrol(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto);
	
	/**
	 * @description:修改 风控配置
	 * @param CustomerAgencyFeescaleRiskcontrolDto
	 * @return int
	 */
	int updateCustomerAgencyFeescaleRiskcontrol(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto);
	/**
	 * @description:根据ID删除 风控配置
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleRiskcontrolById(int id);	
	
	/**
	 * @description:根据ID查询 风控配置
	 * @param int
	 * @return CustomerAgencyFeescaleRiskcontrolDto
	 */
	CustomerAgencyFeescaleRiskcontrolDto selectCustomerAgencyFeescaleRiskcontrolById(int id);
}