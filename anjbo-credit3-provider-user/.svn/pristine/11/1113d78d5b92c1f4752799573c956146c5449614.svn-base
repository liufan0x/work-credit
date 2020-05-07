package com.anjbo.controller.impl.api;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.DeptDto;
import com.anjbo.bean.FundCostDto;
import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;
import com.anjbo.controller.BaseUserController;
import com.anjbo.controller.api.IUserApiController;
import com.anjbo.service.AgencyService;
import com.anjbo.service.DeptService;
import com.anjbo.service.FundCostService;
import com.anjbo.service.FundService;
import com.anjbo.service.UserService;

@RestController
public class UserApiController extends BaseUserController implements IUserApiController {
	
	@Resource private UserService userService;
	
	@Resource private DeptService deptService;
	
	@Resource private FundService fundService;
	
	@Resource private AgencyService agencyService;
	
	@Resource private FundCostService fundCostService;
	
	@Override
	public AgencyDto getAgencyDto(AgencyDto agencyDto) {
		return agencyService.find(agencyDto);
	}
	
	@Override
	public UserDto getUserDto() {
		String uid = super.getUid();
		UserDto user = new UserDto();
		user.setUid(uid);
		return userService.findByUid(user.getUid());
	}
	
	@Override
	public UserDto findUserDtoByUid(@PathVariable("uid") String uid) {
		if(StringUtils.isEmpty(uid)) {
			return new UserDto();
		}
		UserDto user = new UserDto();
		user.setUid(uid);
		return userService.findByUid(user.getUid());
	}
	
	@Override
	public UserDto findUserDtoByUser(@RequestBody UserDto userDto) {
		return userService.find(userDto);
	}

	@Override
	public String selectUidsByDeptId(@RequestBody UserDto userDto) {
		try {
			DeptDto deptDto = new DeptDto();
			deptDto.setAgencyId(userDto.getAgencyId());
			List<DeptDto> list = deptService.search(deptDto);
			// 新增多部门情况分支			
			List<Integer> lstDeptIds = new LinkedList<Integer>();
			if(StringUtils.isNotEmpty(userDto.getDeptIdArray()) && userDto.getDeptIdArray().contains(",")){
				String[] aryDeptIds = userDto.getDeptIdArray().split(",");				
				for(String deptId: aryDeptIds){
					lstDeptIds.addAll(getAllDeptSet(list, Integer.valueOf(deptId)));
				}
				this.logger.info("所属部门集合(多部门)："+ lstDeptIds.toString());
			}else{
				this.logger.info("所属部门集合(单部门)："+ userDto.getDeptId());
				lstDeptIds.addAll(getAllDeptSet(list, userDto.getDeptId()));				
			}
			String uids = userService.selectUidByDeptList(lstDeptIds);
			this.logger.info("所属部门人员UID："+ uids);
			return uids;
		} catch (Exception e) {
			logger.error("获取部门Uids异常:"+userDto.getDeptId(), e);
			return "-1";
		}
	}
	
	@Override
	public List<UserDto> selectAllUserDto() {
		return userService.selectAllUserDto();
	}

	@Override
	public FundDto findByFundId(@RequestBody FundDto dto) {
		return fundService.find(dto);
	}
	
	@Override
	public AgencyDto insertAgency(@RequestBody AgencyDto agencyDto) {
		return agencyService.insert(agencyDto);
	}

	@Override
	public FundCostDto findFundCostByFindId(@RequestBody FundCostDto dto) {
		return fundCostService.find(dto);
	}

	@Override
	public FundDto selectCustomerFundById(@RequestBody int id) {
		// TODO Auto-generated method stub
		return fundService.selectCustomerFundById(id);
	}

	@Override
	public List<FundDto> selectFundListByStatus(FundDto fundDto) {
		return fundService.selectFundListByStatus(fundDto);
	}
	
}
