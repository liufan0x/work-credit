/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.AgencyAcceptDto;
import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.AgencyIncomeModeDto;
import com.anjbo.bean.AgencyProductDto;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.dao.AgencyMapper;
import com.anjbo.service.AgencyAcceptService;
import com.anjbo.service.AgencyIncomeModeService;
import com.anjbo.service.AgencyProductService;
import com.anjbo.service.AgencyService;
import com.anjbo.service.AgencyTypeService;
import com.anjbo.service.RoleService;
import com.anjbo.service.UserService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.SingleUtils;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:45
 * @version 1.0
 */
@Service
public class AgencyServiceImpl extends BaseServiceImpl<AgencyDto>  implements AgencyService {
	@Autowired private AgencyMapper agencyMapper;
	
	@Resource private AgencyTypeService agencyTypeService;
	
	@Resource private AgencyAcceptService agencyAcceptService;
	
	@Resource private AgencyIncomeModeService agencyIncomeModeService;
	
	@Resource private AgencyProductService agencyProductService;
	
	@Resource private DataApi dataApi;
	
	@Resource private UserService userService;
	
	@Resource private RoleService roleService;
	
	@Override
	public AgencyDto insert(AgencyDto agencyDto) {
		if(agencyDto.getAgencyCode() == null) {
			agencyDto.setAgencyCode(generateAgencyCode());
		}
		if(StringUtils.isEmpty(agencyDto.getAccountUid())) {
			agencyDto.setAccountUid(UUID.randomUUID().toString());
		}
		super.insert(agencyDto);
		for (AgencyProductDto agencyProductDto : agencyDto.getAgencyProductDtos()) {
			agencyProductDto.setAgencyId(agencyDto.getId());
			agencyProductService.delete(agencyProductDto);
			agencyProductService.insert(agencyProductDto);
		}
		for (AgencyIncomeModeDto agencyIncomeModeDto : agencyDto.getAgencyIncomeModeDtos()) {
			agencyIncomeModeDto.setAgencyId(agencyDto.getId());
			agencyIncomeModeService.delete(agencyIncomeModeDto);
			agencyIncomeModeService.insert(agencyIncomeModeDto);
		}
		//新增机构默认角色
		roleService.insertByAgency(agencyDto.getId(), agencyDto.getCreateUid());
		
		
		UserDto userDto = new UserDto();
		userDto.setAgencyId(agencyDto.getId());
		userDto.setIsEnable(0);
		userDto.setName(agencyDto.getContactMan());
		userDto.setMobile(agencyDto.getContactTel());
		userDto.setIndateStart(null);
		userDto.setIndateEnd(null);
		userDto.setPassword("88888888");
		userDto.setSourceFrom("system");
		userDto.setRoleId(-1);
		userDto.setDeptId(-1);
		userDto.setDeptIdArray("-1");
		userDto.setIdentity(1);
		userDto.setPosition(" ");
		
		return agencyDto;
	}
	
	@Override
	public List<AgencyDto> search(AgencyDto dto) {
		List<AgencyDto> agencyDtos = super.search(dto);
		if(dto != null && dto.getIsRelation() == 1) {
			return agencyDtos;
		}
		List<DictDto> agencyType = dataApi.getDictDtoListByType("agencyType");
		List<DictDto> cooperativeMode = dataApi.getDictDtoListByType("cooperativeMode");
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("agencyId", 1);
		params.put("name", "渠道经理");
		params.put("type", "role");
		List<UserDto> userDtos = userService.searchByType(params);
		
		for (AgencyDto agencyDto : agencyDtos) {
			for (DictDto dictDto : agencyType) {
				if(dictDto.getCode().equals(agencyDto.getAgencyType())) {
					agencyDto.setTypeName(dictDto.getName());
					break;
				}
			}
			
			for (DictDto dictDto : cooperativeMode) {
				if(dictDto.getCode().equals(agencyDto.getCooperativeModeId()+"")) {
					agencyDto.setCooperativeMode(dictDto.getName());
					break;
				}
			}
			
			for (UserDto userDto : userDtos) {
				if(userDto.getUid().equals(agencyDto.getChanlMan())) {
					agencyDto.setChanManName(userDto.getName());
					break;
				}
			}
		}
		
		return agencyDtos;
	}
	
	@Override
	public AgencyDto find(AgencyDto dto) {
		AgencyDto agencyDto = super.find(dto);
		if(dto != null && dto.getIsRelation() == 1) {
			return agencyDto;
		}
			
		AgencyAcceptDto agencyAcceptDto = new AgencyAcceptDto();
		agencyAcceptDto.setAgencyId(dto.getId());
		agencyDto.setAgencyAcceptDtos(agencyAcceptService.search(agencyAcceptDto));
		
		AgencyIncomeModeDto agencyIncomeModeDto = new AgencyIncomeModeDto();
		agencyIncomeModeDto.setAgencyId(dto.getId());
		agencyDto.setAgencyIncomeModeDtos(agencyIncomeModeService.search(agencyIncomeModeDto));
		
		AgencyProductDto agencyProductDto = new AgencyProductDto();
		agencyProductDto.setAgencyId(dto.getId());
		agencyDto.setAgencyProductDtos(agencyProductService.search(agencyProductDto));

		return agencyDto;
	}
	

	final Lock lock = new ReentrantLock();
	
	private int generateAgencyCode(){
		lock.lock();
		try{
			Integer agecnyCode = agencyMapper.selectMaxAgencyCode();
			agecnyCode = (null==agecnyCode||100000>agecnyCode)?100000:++agecnyCode;
			return agecnyCode;
		} finally {
			lock.unlock();
		}
	}
	
}
