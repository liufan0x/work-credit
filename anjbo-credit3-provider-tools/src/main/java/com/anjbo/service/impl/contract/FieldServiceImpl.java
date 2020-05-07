/*
 ****************************************************************
 *Project: ANJBO Generator
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.contract.FieldDto;
import com.anjbo.bean.contract.FieldInputDto;
import com.anjbo.dao.contract.FieldMapper;
import com.anjbo.service.contract.FieldInputService;
import com.anjbo.service.contract.FieldService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:20
 * @version 1.0
 */
@Service
public class FieldServiceImpl extends BaseServiceImpl<FieldDto>  implements FieldService {
	@Autowired private FieldMapper fieldMapper;
	
	@Resource private FieldInputService fieldInputService;
	
	@Override
	public FieldDto insert(FieldDto dto) {
		dto = super.insert(dto);
		for (FieldInputDto fieldInputDto : dto.getInputs()) {
			fieldInputDto.setFieldId(dto.getId());
			fieldInputService.insert(fieldInputDto);
		}
		return dto;
	}
	
	@Override
	public int delete(FieldDto dto) {
		if(dto.getId()!= null) {
			FieldInputDto fieldInputDto = new FieldInputDto();
			fieldInputDto.setFieldId(dto.getId());
			fieldInputService.delete(fieldInputDto);
		}
		return super.delete(dto);
	}
	
	@Override
	public List<FieldDto> search(FieldDto dto) {
		List<FieldDto> list = super.search(dto);
		List<FieldInputDto> fieldInputDtos = fieldInputService.search(null);
		List<FieldInputDto> tempList = null;
		for (FieldDto fieldDto : list) {
			tempList = new ArrayList<FieldInputDto>();
			for (FieldInputDto fieldInputDto : fieldInputDtos) {
				if(fieldInputDto.getFieldId().equals(fieldDto.getId())) {
					tempList.add(fieldInputDto);
				}
			}
			fieldDto.setInputs(tempList);
		}
		return list;
	}
	
}
