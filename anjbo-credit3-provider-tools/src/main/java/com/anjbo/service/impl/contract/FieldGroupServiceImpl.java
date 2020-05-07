/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.contract.FieldDto;
import com.anjbo.bean.contract.FieldGroupDto;
import com.anjbo.bean.contract.FieldInputDto;
import com.anjbo.dao.contract.FieldGroupMapper;
import com.anjbo.service.contract.FieldGroupService;
import com.anjbo.service.contract.FieldInputService;
import com.anjbo.service.contract.FieldService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 12:22:21
 * @version 1.0
 */
@Service
public class FieldGroupServiceImpl extends BaseServiceImpl<FieldGroupDto>  implements FieldGroupService {
	@Autowired private FieldGroupMapper fieldGroupMapper;
	
	@Resource private FieldService fieldService;
	
	@Resource private FieldInputService fieldInputService;
	
	@Override
	public FieldGroupDto insert(FieldGroupDto dto) {
		dto.setSort(fieldGroupMapper.selectMaxSort()+1);
		return super.insert(dto);
	}
	
	@Override
	public int delete(FieldGroupDto dto) {
		FieldDto fieldDto = new FieldDto();
		fieldDto.setGroupId(dto.getId());
		fieldService.delete(fieldDto);
		FieldInputDto fieldInputDto = new FieldInputDto();
		fieldInputDto.setGroupId(dto.getId());
		fieldInputService.delete(fieldInputDto);
		return super.delete(dto);
	}
	
	@Override
	public List<FieldGroupDto> search(FieldGroupDto dto) {
		List<FieldGroupDto> list = super.search(dto);
		List<FieldDto> fieldDtos = fieldService.search(null);
		List<FieldDto> tempList = null;
		for (FieldGroupDto fieldGroupDto : list) {
			tempList = new ArrayList<FieldDto>();
			for (FieldDto fieldDto : fieldDtos) {
				if(fieldDto.getGroupId().equals(fieldGroupDto.getId())) {
					tempList.add(fieldDto);
				}
			}
			fieldGroupDto.setFileList(tempList);
		}
		return list;
	}
	
}
