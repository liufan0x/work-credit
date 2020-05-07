package com.anjbo.dao;

import com.anjbo.bean.customer.AgencyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgencyMapper {

	List<AgencyDto> agencyList();

	AgencyDto getAgencyDto(int id);
	
	/**
	 * 根据机构码查询
	 * @Author KangLG<2017年11月15日>
	 * @param agencyId
	 * @return
	 */
	AgencyDto getEntityByAgencyCode(int agencyCode);

	/**
	 * 新增机构
	 * @param agencyDto
	 * @return
	 */
	int insert(AgencyDto agencyDto);

	/**
	 * 查询机构最大的机构码
	 * @return
	 */
	Integer selectMaxAgencyCode();

	/**
	 * 根据名称查询机构
	 * @param name
	 * @return
	 */
	List<AgencyDto> selectAgencyByName(@Param("name") String name);

	/**
	 * 根据id更新机构
	 * @param agencyDto
	 * @return
	 */
	int updateById(AgencyDto agencyDto);
	
	int updSurplusQuota(Map<String, Object> map);

	int delete(AgencyDto agencyDto);

	AgencyDto detail(AgencyDto agencyDto);

	AgencyDto selectAgencyByMobile(AgencyDto agencyDto);

	AgencyDto selectAgencyById(Map<String,Object> map);

	/**
	 * 根据简称查询机构
	 * @param simName
	 * @return
	 */
	List<AgencyDto> selectAgencyBySimName(@Param("simName") String simName);
}