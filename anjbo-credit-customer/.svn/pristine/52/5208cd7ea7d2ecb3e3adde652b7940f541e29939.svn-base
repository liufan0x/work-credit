package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.customer.CustomerAgencyFeescaleRiskcontrolDto;
import org.apache.ibatis.annotations.Param;

/**
  * 风控配置 [Dao接口类]
  * @ClassName: CustomerAgencyFeescaleRiskcontrolMapper
  * @Description: 
  * @date 2017-07-06 14:48:35
  * @version V3.0
 */
public interface CustomerAgencyFeescaleRiskcontrolMapper{
	/**
	 * 获取风控配置列表
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return list
	 */
	List<CustomerAgencyFeescaleRiskcontrolDto> selectCustomerAgencyFeescaleRiskcontrolList(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto);

	/**
	 * 获取风控配置列表总数
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
	 * @description:根据收费标准ID删除 风控配置
	 * @param int
	 * @return Integer
	 */
	int deleteCustomerAgencyFeescaleRiskcontrolByFeescaleId(int feescaleid);
	
	/**
	 * @description:根据ID查询 风控配置
	 * @param int
	 * @return CustomerAgencyFeescaleRiskcontrolDto
	 */
	CustomerAgencyFeescaleRiskcontrolDto selectCustomerAgencyFeescaleRiskcontrolById(int id);

	int deleteCustomerAgencyFeescaleRiskcontrolByFeescaleIds(@Param("feescaleids") String feescaleids);
}