package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyTypeDto;
import com.anjbo.common.RespStatus;

 /**
  * 机构类型 [Service接口类]
  * @ClassName: CustomerAgencyTypeService
  * @Description: 机构类型业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
public interface CustomerAgencyTypeService{
	/**
	 * 获取机构类型列表
	 * @param customerAgencyTypeDto
	 * @return list
	 */
	List<CustomerAgencyTypeDto> selectCustomerAgencyTypeList(CustomerAgencyTypeDto customerAgencyTypeDto);

	/**
	 * 获取机构类型总数
	 * @param customerAgencyTypeDto
	 * @return int
	 */
	int selectCustomerAgencyTypeCount(CustomerAgencyTypeDto customerAgencyTypeDto);
	
	/**
	 * @description:添加 机构类型
	 * @param CustomerAgencyTypeDto
	 * @return RespStatus
	 */
	RespStatus addCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto);
	
	/**
	 * @description:修改 机构类型
	 * @param CustomerAgencyTypeDto
	 * @return RespStatus
	 */
	RespStatus updateCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto);
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
}