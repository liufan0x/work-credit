package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleSectionDto;
import org.apache.ibatis.annotations.Param;

/**
  * 收费设置 [Dao接口类]
  * @ClassName: CustomerAgencyFeescaleSectionMapper
  * @Description: 
  * @date 2017-07-06 14:48:35
  * @version V3.0
 */
public interface CustomerAgencyFeescaleSectionMapper{
	/**
	 * 获取收费设置列表
	 * @param customerAgencyFeescaleSectionDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleSectionDto> selectCustomerAgencyFeescaleSectionList(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto);

	/**
	 * 获取收费设置列表总数
	 * @param customerAgencyFeescaleSectionDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleSectionCount(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto);
	
	/**
	 * @description:添加 收费设置
	 * @param CustomerAgencyFeescaleSectionDto
	 * @return int
	 */
	int addCustomerAgencyFeescaleSection(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto);
	
	/**
	 * @description:修改 收费设置
	 * @param CustomerAgencyFeescaleSectionDto
	 * @return int
	 */
	int updateCustomerAgencyFeescaleSection(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto);
	/**
	 * @description:根据ID删除 收费设置
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleSectionById(int id);	
	/**
	 * @description:根据收费标准ID删除 收费设置
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleSectionByFeescaleId(int feescaleid);
	/**
	 * @description:根据ID查询 收费设置
	 * @param int
	 * @return CustomerAgencyFeescaleSectionDto
	 */
	CustomerAgencyFeescaleSectionDto selectCustomerAgencyFeescaleSectionById(int id);

	int deleteCustomerAgencyFeescaleSectionByFeescaleIds(@Param("feescaleids") String feescaleids);
}