/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.AgencyFeescaleDetailDto;
import com.anjbo.bean.AgencyFeescaleSectionDto;
import com.anjbo.dao.AgencyFeescaleSectionMapper;
import com.anjbo.service.AgencyFeescaleDetailService;
import com.anjbo.service.AgencyFeescaleSectionService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
@Service
public class AgencyFeescaleSectionServiceImpl extends BaseServiceImpl<AgencyFeescaleSectionDto>  implements AgencyFeescaleSectionService {
	@Autowired private AgencyFeescaleSectionMapper agencyFeescaleSectionMapper;
	
	@Resource private AgencyFeescaleDetailService agencyFeescaleDetailService;
	
	@Override
	public List<AgencyFeescaleSectionDto> search(AgencyFeescaleSectionDto dto) {
		List<AgencyFeescaleSectionDto> list = super.search(dto);
		for (AgencyFeescaleSectionDto agencyFeescaleSectionDto : list) {
			if(agencyFeescaleSectionDto.getId() != null) {
				AgencyFeescaleDetailDto agencyFeescaleDetailDto = new AgencyFeescaleDetailDto();
				agencyFeescaleDetailDto.setSectionid(agencyFeescaleSectionDto.getId());
				agencyFeescaleSectionDto.setAgencyFeescaleDetailDtos(agencyFeescaleDetailService.search(agencyFeescaleDetailDto));
			}
		}
		return list;
	}
	
	@Override
	public AgencyFeescaleSectionDto insert(AgencyFeescaleSectionDto dto) {
		super.insert(dto);
		for (AgencyFeescaleDetailDto agencyFeescaleDetailDto : dto.getAgencyFeescaleDetailDtos()) {
			agencyFeescaleDetailDto.setSectionid(dto.getId());
			agencyFeescaleDetailService.insert(agencyFeescaleDetailDto);
		}
		return dto;
	}
	
	
	@Override
	public int delete(AgencyFeescaleSectionDto dto) {
		for (AgencyFeescaleDetailDto agencyFeescaleDetailDto : dto.getAgencyFeescaleDetailDtos()) {
			agencyFeescaleDetailDto.setSectionid(dto.getId());
			agencyFeescaleDetailService.delete(agencyFeescaleDetailDto);
		}
		return super.delete(dto);
	}
	
	
}
