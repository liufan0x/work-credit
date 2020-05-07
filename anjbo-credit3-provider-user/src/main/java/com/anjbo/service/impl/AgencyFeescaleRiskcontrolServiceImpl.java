/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AgencyFeescaleRiskcontrolDto;
import com.anjbo.bean.AgencyFeescaleSectionDto;
import com.anjbo.dao.AgencyFeescaleRiskcontrolMapper;
import com.anjbo.service.AgencyFeescaleRiskcontrolService;
import com.anjbo.service.AgencyFeescaleSectionService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
@Service
public class AgencyFeescaleRiskcontrolServiceImpl extends BaseServiceImpl<AgencyFeescaleRiskcontrolDto>  implements AgencyFeescaleRiskcontrolService {
	@Autowired private AgencyFeescaleRiskcontrolMapper agencyFeescaleRiskcontrolMapper;
	
	@Resource private AgencyFeescaleSectionService agencyFeescaleSectionService;
	
	
	@Override
	public List<AgencyFeescaleRiskcontrolDto> search(AgencyFeescaleRiskcontrolDto dto) {
		List<AgencyFeescaleRiskcontrolDto> list = super.search(dto);
		for (AgencyFeescaleRiskcontrolDto agencyFeescaleRiskcontrolDto : list) {
			if(agencyFeescaleRiskcontrolDto.getId() != null) {
				AgencyFeescaleSectionDto agencyFeescaleSectionDto = new AgencyFeescaleSectionDto();
				agencyFeescaleSectionDto.setRaskcontrolid(agencyFeescaleRiskcontrolDto.getId());
				agencyFeescaleRiskcontrolDto.setAgencyFeescaleSectionDtos(agencyFeescaleSectionService.search(agencyFeescaleSectionDto));
			}
		}
		return list;
	}
	
	@Override
	public AgencyFeescaleRiskcontrolDto insert(AgencyFeescaleRiskcontrolDto dto) {
		super.insert(dto);
		for (AgencyFeescaleSectionDto agencyFeescaleSectionDto : dto.getAgencyFeescaleSectionDtos()) {
			agencyFeescaleSectionDto.setRaskcontrolid(dto.getId());
			agencyFeescaleSectionService.insert(agencyFeescaleSectionDto);
		}
		return dto;
	}
	
	@Override
	public int delete(AgencyFeescaleRiskcontrolDto dto) {
		for (AgencyFeescaleSectionDto agencyFeescaleSectionDto : dto.getAgencyFeescaleSectionDtos()) {
			agencyFeescaleSectionDto.setRaskcontrolid(dto.getId());
			agencyFeescaleSectionService.delete(agencyFeescaleSectionDto);
		}
		return super.delete(dto);
	}
	
	@Override
	public Map<String, Object> findStageRate(Map<String, Object> map) {
		double loanAmount = MapUtils.getDoubleValue(map, "loanAmount");
		List<Map<String, Object>> list = agencyFeescaleRiskcontrolMapper.findStageRate(map);
		Map<String, Object> map3 = null;
		boolean flag = false;
		for (Map<String, Object> map2 : list) {
			map3 = new HashMap<String, Object>();
			if (MapUtils.getDouble(map2, "section") >= loanAmount) {
				map3 = map2;
				flag = true;
			}
		}
		if(flag&&(map3==null||map3.isEmpty())){
			for (Map<String, Object> map2 : list) {
				map3 = new HashMap<String, Object>();
				if (MapUtils.getDouble(map2, "section") < loanAmount) {
					map3 = map2;
					flag = true;
					break;
				}
			}
		}
		return flag ? map3 : ((list==null||list.size()<1)?null:list.get(0));
	}
	
}
