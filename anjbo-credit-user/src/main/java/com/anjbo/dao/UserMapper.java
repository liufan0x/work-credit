package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.user.UserDto;

/**
 * 用户Dto
 * @author lic
 */
public interface UserMapper extends BaseMapper<UserDto, Integer> {

	UserDto findUserByAccountMobile(String account);
	UserDto loginByAccountMobile(@Param(value="account")String account, @Param(value="mobile")String mobile);
	UserDto getEntityByUid(@Param(value="uid")String uid);
	UserDto getEntityByMobile(@Param(value="mobile")String mobile);

	int updatePwd(UserDto userDto);
	
	List<UserDto> selectUserList(UserDto userDto);
	
	/**
	 * 查询钉钉用户
	 * @Author KangLG<2017年10月17日>
	 * @param userDto
	 * @return
	 */
	List<UserDto> searchByDingtalk(UserDto userDto);

	int selectUserCount(UserDto userDto);
	
	UserDto findUserDto(UserDto userDto);
	
	int updateUser(UserDto userDto);
	int updateStatus(UserDto userDto);
	
	/**
	 * 用户解绑
	 * @Author KangLG<2017年11月28日>
	 * @param userDto
	 * @return
	 */
	int update4Unbind(UserDto userDto);
	int update4UnbindAgency(UserDto userDto);
	
	int update4SyncOldUser(UserDto userDto);
	
	int batchMoveMobile(List<UserDto> list);

	int insertUser(UserDto userDto);
	/**
	 * 根据部门分组统计用户数量
	 * @param userDto
	 * @return
	 */
	List<Map<String,Object>> selectUserCountGroupByDeptId(UserDto userDto);

	List<String> selectUidByDeptIds(@Param(value="deptIds")String deptIds);
	List<String> selectUidByDeptList(List<Integer> list);
	
	/**
	 * 查询机构下所有的用户id
	 * @param agencyId
	 * @return
	 */
	List<String> selectUidByAgencyId(int agencyId);
	
	CustomerFundDto selectFundByUid(@RequestParam("uid") String uid);
	
}