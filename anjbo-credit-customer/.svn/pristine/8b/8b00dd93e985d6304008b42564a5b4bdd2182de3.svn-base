package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.customer.AgencyDto;

public interface ChannelManagerMapper {

	/**
	 * 查询受理员关联机构的渠道经理集合
	 * @param acceptUid
	 * @return
	 */
	List<AgencyDto> listRoleByRolrNameAndAgencyIds(String acceptUid);
	
	/**
	 * 查询机构
	 * @param agencyId
	 * @return
	 */
	List<AgencyDto> findChanlMan(int agencyId);
	
	List<Map<String,Object>> findAgencyByChanlAndAccept(AgencyDto agencyDto);
	
	List<Map<String,Object>> findAgencyByChanlMan(String chanlMan);
}
