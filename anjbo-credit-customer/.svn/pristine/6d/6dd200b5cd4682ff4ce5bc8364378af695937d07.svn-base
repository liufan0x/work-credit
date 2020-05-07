package com.anjbo.service;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import java.util.List;
import java.util.Map;

public interface AgencyService {

	List<AgencyDto> agencyList();

	AgencyDto getAgencyDto(int id);
	
	/**
	 * 根据机构码查询
	 * @Author KangLG<2017年11月15日>
	 * @param agencyCode 机构代码
	 * @return
	 */
	AgencyDto getEntityByAgencyCode(int agencyCode);

	RespDataObject<AgencyDto> insert(Map<String,Object> map)throws Exception;

	Integer selectMaxAgencyCode();

	List<AgencyDto> selectAgencyByName(String name);
	
	int updSurplusQuota(Map<String, Object> map);

	int updateById(AgencyDto agencyDto);

	int delete(AgencyDto agencyDto);

	AgencyDto detail(AgencyDto agencyDto);

	AgencyDto selectAgencyByMobile(AgencyDto agencyDto);

	AgencyDto selectAgencyById(Map<String,Object> map);
	/**
	 * 根据简称查询机构
	 * @param simName
	 * @return
	 */
	List<AgencyDto> selectAgencyBySimName(String simName);

	public RespStatus initAgencyToRedis();
}
