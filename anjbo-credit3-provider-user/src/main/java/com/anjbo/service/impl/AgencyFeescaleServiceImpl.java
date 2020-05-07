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
import com.anjbo.bean.AgencyFeescaleDto;
import com.anjbo.bean.AgencyFeescaleRiskcontrolDto;
import com.anjbo.bean.DictDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.dao.AgencyFeescaleMapper;
import com.anjbo.service.AgencyFeescaleRiskcontrolService;
import com.anjbo.service.AgencyFeescaleService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:46
 * @version 1.0
 */
@Service
public class AgencyFeescaleServiceImpl extends BaseServiceImpl<AgencyFeescaleDto>  implements AgencyFeescaleService {
	@Autowired private AgencyFeescaleMapper agencyFeescaleMapper;
	
	@Resource private DataApi dataApi;
	
	@Resource private AgencyFeescaleRiskcontrolService agencyFeescaleRiskcontrolService;
	
	@Override
	public List<AgencyFeescaleDto> search(AgencyFeescaleDto dto) {
		List<AgencyFeescaleDto> list = super.search(dto);
		List<DictDto> dictDtoList = dataApi.getDictDtoListByType("riskControl");
		for (AgencyFeescaleDto agencyFeescaleDto : list) {
			if(agencyFeescaleDto.getId() != null) {
				AgencyFeescaleRiskcontrolDto agencyFeescaleRiskcontrolDto = new AgencyFeescaleRiskcontrolDto();
				agencyFeescaleRiskcontrolDto.setFeescaleid(agencyFeescaleDto.getId());
				if(dto != null && dto.getIsRelation() == 1) {
					agencyFeescaleRiskcontrolDto.setIsRelation(1);
				}
				agencyFeescaleDto.setAgencyFeescaleRiskcontrolDtos(agencyFeescaleRiskcontrolService.search(agencyFeescaleRiskcontrolDto));
			}
			if(dto != null && dto.getIsRelation() == 1) {
				for (DictDto dictDto : dictDtoList) {
					if(dictDto.getCode().equals(agencyFeescaleDto.getRiskGradeId()+"")) {
						agencyFeescaleDto.setRiskGradeName(dictDto.getName());
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public AgencyFeescaleDto insert(AgencyFeescaleDto dto) {
		super.insert(dto);
		for (AgencyFeescaleRiskcontrolDto agencyFeescaleRiskcontrolDto : dto.getAgencyFeescaleRiskcontrolDtos()) {
			agencyFeescaleRiskcontrolDto.setFeescaleid(dto.getId());
			agencyFeescaleRiskcontrolService.insert(agencyFeescaleRiskcontrolDto);
		}
		return dto;
	}
	
	@Override
	public int delete(AgencyFeescaleDto dto) {
		for (AgencyFeescaleRiskcontrolDto agencyFeescaleRiskcontrolDto : dto.getAgencyFeescaleRiskcontrolDtos()) {
			agencyFeescaleRiskcontrolDto.setFeescaleid(dto.getId());
			agencyFeescaleRiskcontrolService.delete(agencyFeescaleRiskcontrolDto);
		}
		return super.delete(dto);
	}
	
}
