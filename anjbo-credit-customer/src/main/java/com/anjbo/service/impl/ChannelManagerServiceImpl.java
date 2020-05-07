package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.dao.ChannelManagerMapper;
import com.anjbo.service.ChannelManagerService;
@Service
public class ChannelManagerServiceImpl implements ChannelManagerService{
	
	@Resource
	private ChannelManagerMapper channelManagerMapper;

	@Override
	public List<AgencyDto> listRoleByRolrNameAndAgencyIds(
			String acceptUid) {
		return channelManagerMapper.listRoleByRolrNameAndAgencyIds(acceptUid);
	}

	@Override
	public List<AgencyDto> findChanlMan(int agencyId) {
		return channelManagerMapper.findChanlMan(agencyId);
	}

	@Override
	public List<Map<String, Object>> findAgencyByChanlAndAccept(
			AgencyDto agencyDto) {
		return channelManagerMapper.findAgencyByChanlAndAccept(agencyDto);
	}

	@Override
	public List<Map<String, Object>> findAgencyByChanlMan(String chanlMan) {
		return channelManagerMapper.findAgencyByChanlMan(chanlMan);
	}


}
