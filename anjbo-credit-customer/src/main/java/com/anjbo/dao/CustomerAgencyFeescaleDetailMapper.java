package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;
import org.apache.ibatis.annotations.Param;

/**
  * 收费标准详细 [Dao接口类]
  * @ClassName: CustomerAgencyFeescaleDetailMapper
  * @Description: 
  * @date 2017-07-06 14:48:35
  * @version V3.0
 */
public interface CustomerAgencyFeescaleDetailMapper{
	/**
	 * 获取收费标准详细列表
	 * @param customerAgencyFeescaleDetailDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleDetailDto> selectCustomerAgencyFeescaleDetailList(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto);

	/**
	 * 获取收费标准详细列表总数
	 * @param customerAgencyFeescaleDetailDto
	 * @return int
	 */
	int selectCustomerAgencyFeescaleDetailCount(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto);
	
	/**
	 * @description:添加 收费标准详细
	 * @param CustomerAgencyFeescaleDetailDto
	 * @return int
	 */
	int addCustomerAgencyFeescaleDetail(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto);
	
	/**
	 * @description:修改 收费标准详细
	 * @param CustomerAgencyFeescaleDetailDto
	 * @return int
	 */
	int updateCustomerAgencyFeescaleDetail(CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto);
	/**
	 * @description:根据ID删除 收费标准详细
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleDetailById(int id);	
	/**
	 * @description:根据收费标准ID删除 收费标准详细
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleDetailByFeescaleId(int feescaleid);
	/**
	 * @description:根据ID查询 收费标准详细
	 * @param int
	 * @return CustomerAgencyFeescaleDetailDto
	 */
	CustomerAgencyFeescaleDetailDto selectCustomerAgencyFeescaleDetailById(int id);

	int deleteCustomerAgencyFeescaleDetailByFeescaleIds(@Param("feescaleids") String feescaleids);
}