package com.anjbo.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.FundCostDto;
import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v")
@Api(value = "用户对外查询api")
public interface IUserApiController {
	
	@ApiOperation(value = "获取当前登陆信息", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "/getUserDto", method=RequestMethod.POST)
	UserDto getUserDto();
	
	@ApiOperation(value = "获取机构信息", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "/getAgencyDto", method=RequestMethod.POST)
	AgencyDto getAgencyDto(@RequestBody AgencyDto agencyDto);
	
	@ApiOperation(value = "根据部门id获取部门下的uids", httpMethod = "POST" ,response = String.class)
	@RequestMapping(value = "/selectUidsByDeptId", method=RequestMethod.POST)
	String selectUidsByDeptId(@RequestBody UserDto userDto);
	
	@ApiOperation(value = "根据uid获取用户", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "/findByUid_{uid}", method=RequestMethod.POST)
	UserDto findUserDtoByUid(@PathVariable("uid") String uid);

	@ApiOperation(value = "根据user获取用户", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "/findUserDtoByUser", method=RequestMethod.POST)
	UserDto findUserDtoByUser(@RequestBody UserDto userDto);
	
	@ApiOperation(value = "获取所有用户集合", httpMethod = "POST" ,response = UserDto.class)
	@RequestMapping(value = "/selectAllUserDto", method=RequestMethod.POST)
	List<UserDto> selectAllUserDto();
	
	@ApiOperation(value = "查找", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "/findByFundId", method= RequestMethod.POST)
	FundDto findByFundId(@RequestBody FundDto dto);
	
	@ApiOperation(value = "新增机构", httpMethod = "POST" ,response = AgencyDto.class)
	@RequestMapping(value = "/insertAgency", method= RequestMethod.POST)
	AgencyDto insertAgency(@RequestBody AgencyDto agencyDto);
	
	@ApiOperation(value = "查找资方业务产品", httpMethod = "POST" ,response = FundCostDto.class)
	@RequestMapping(value = "/findFundCostByFindId", method= {RequestMethod.POST})
	FundCostDto findFundCostByFindId(@RequestBody FundCostDto dto);
	
	@ApiOperation(value = "根据ID查询 合作资金方", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "/selectCustomerFundById", method= {RequestMethod.POST})
	FundDto selectCustomerFundById(@RequestBody int id);
	
	@ApiOperation(value = "根据状态查询资方列表", httpMethod = "POST" ,response = FundDto.class)
	@RequestMapping(value = "/selectFundListByStatus", method= {RequestMethod.POST})
	List<FundDto> selectFundListByStatus(@RequestBody FundDto fundDto);
//	
//	@ApiOperation(value = "根据城市code和agencyId获取用户集合", notes = "可以传Null", httpMethod = "POST" ,response = UserDto.class)
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "cityCode", value = "城市名称", required = false),
//        @ApiImplicitParam(name = "agencyId", value = "机构Id", required = false)
//	})
//	@RequestMapping(value = "/getUserListByCityCodeOrAgencyId", method=RequestMethod.POST)
//	RespData<UserDto> getUserListByCityCodeOrAgencyId(Map<String, Object> params);
//	
//	@ApiOperation(value = "根据uid获取用户",  httpMethod = "POST" ,response = UserDto.class)
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "uid", value = "用户uid", required = true),
//	})
//	@RequestMapping(value = "/getUserDtoByUid", method=RequestMethod.POST)
//	RespDataObject<UserDto> getUserDtoByUid(UserDto userDto);
//	
//	@ApiOperation(value = "根据mobile获取用户",  httpMethod = "POST" ,response = UserDto.class)
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "mobile", value = "用户mobile", required = true),
//	})
//	@RequestMapping(value = "/getUserDtoByMobile", method=RequestMethod.POST)
//	RespDataObject<UserDto> getUserDtoByMobile(UserDto userDto);
//
//	@ApiOperation(value = "修改人员状态",  httpMethod = "POST" ,response = UserDto.class)
//	@RequestMapping(value = "/updateStatus")
//	public RespStatus updateStatus(UserDto userDto);
//	
//	@ApiOperation(value = "编辑机构测试账号(即机构管理员)",  httpMethod = "POST" ,response = UserDto.class)
//		@ApiImplicitParams({
//        @ApiImplicitParam(name = "agencyId", value = "机构ID(10资方机构)", required = true),
//        @ApiImplicitParam(name = "isEnable", value = "状态 0启用1禁用", required = true),
//        @ApiImplicitParam(name = "name", value = "姓名", required = true),
//        @ApiImplicitParam(name = "mobile", value = "电话", required = true),
//        @ApiImplicitParam(name = "indateStart", value = "有效期开始", required = true),
//        @ApiImplicitParam(name = "indateEnd", value = "有效期结束", required = true),
//	})
//	@RequestMapping(value = "/editAgentAdmin", method=RequestMethod.POST)
//	public abstract RespStatus editAgentAdmin(UserDto userDto);
	
//	@ApiOperation(value = "新增用户",  httpMethod = "POST" ,response = UserDto.class)
//	@RequestMapping(value = "/insertUser", method=RequestMethod.POST)
//	public abstract RespStatus insertUser(UserDto userDto, boolean isAdmin4Org, String _uid, boolean isAllPermi);
	
}
