package com.anjbo.service;

import com.anjbo.bean.customer.CustomerAgencyFeescaleDto;
import com.anjbo.common.RespStatus;

import java.util.List;

 /**
  * 收费标准 [Service接口类]
  * @ClassName: CustomerAgencyFeescaleService
  * @Description: 收费标准业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
public interface CustomerAgencyFeescaleService{
	/**
	 * 获取收费标准列表
	 * @param customerAgencyFeescaleDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleList(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);

	/**
	 * 获取收费标准总数
	 * @param customerAgencyFeescaleDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleCount(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	
	/**
	 * @description:添加 收费标准
	 * @param customerAgencyFeescaleDto
	 * @return int
	 */
	int addCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	
	/**
	 * @description:修改 收费标准
	 * @param customerAgencyFeescaleDto
	 * @return int
	 */
	int updateCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	/**
	 * @description:根据ID删除 收费标准
	 * @param id
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleById(int id);	
	
	/**
	 * @description:根据ID查询 收费标准
	 * @param id
	 * @return CustomerAgencyFeescaleDto
	 */
	CustomerAgencyFeescaleDto selectCustomerAgencyFeescaleById(int id);
	
	/**
	 * @description:修改 收费信息（包括收费标准、风控等级、收费设置、收费详细）
	 * @param customerAgencyFeescaleDto
	 * @return int
	 */
	RespStatus updateFeescaleInfo(List<CustomerAgencyFeescaleDto> customerAgencyFeescaleDto);

	 /**
	  * 根据机构id与产品查询收费标准
	  * @param obj
	  * @return
	  */
	 List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleByAgencyIdAndProductionid(CustomerAgencyFeescaleDto obj);
}