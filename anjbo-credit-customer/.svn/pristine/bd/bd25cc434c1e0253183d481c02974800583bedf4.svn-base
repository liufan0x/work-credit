package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleDto;
import org.apache.ibatis.annotations.Param;

/**
  * 收费标准 [Dao接口类]
  * @ClassName: CustomerAgencyFeescaleMapper
  * @Description: 
  * @date 2017-07-06 14:48:34
  * @version V3.0
 */
public interface CustomerAgencyFeescaleMapper{
	/**
	 * 获取收费标准列表
	 * @param customerAgencyFeescaleDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleList(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);

	/**
	 * 获取收费标准列表总数
	 * @param customerAgencyFeescaleDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleCount(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	
	/**
	 * @description:添加 收费标准
	 * @param CustomerAgencyFeescaleDto
	 * @return int
	 */
	int addCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	
	/**
	 * @description:修改 收费标准
	 * @param CustomerAgencyFeescaleDto
	 * @return int
	 */
	int updateCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto);
	/**
	 * @description:根据ID删除 收费标准
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleById(int id);	
	
	/**
	 * @description:根据ID查询 收费标准
	 * @param int
	 * @return CustomerAgencyFeescaleDto
	 */
	CustomerAgencyFeescaleDto selectCustomerAgencyFeescaleById(int id);

	 /**
	  * 根据机构id与产品查询收费标准
	  * @param obj
	  * @return
	  */
	List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleByAgencyIdAndProductionid(CustomerAgencyFeescaleDto obj);

	 /**
	  * 根据机构id与产品删除收费标准
	  * @param obj
	  * @return
	  */
	int deleteCustomerAgencyFeescaleByAgencyIdAndProductionid(CustomerAgencyFeescaleDto obj);

	 /**
	  * 批量新增收费标准
	  * @param list
	  */
	void batchAddCustomerAgencyFeescale(List<CustomerAgencyFeescaleDto> list);

}