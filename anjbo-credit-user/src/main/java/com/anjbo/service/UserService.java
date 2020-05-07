package com.anjbo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Enums.UserUpdateFrom;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

public interface UserService {

	/**
	 * 登陆
	 * @param request
	 * @param params
	 * @return
	 */
	public RespDataObject<UserDto> login(HttpServletRequest request, Map<String, String> params);
	
	/**
	 * 获取实体.UID
	 * @Author KangLG<2017年11月16日>
	 * @param uid
	 * @return
	 */
	UserDto getEntityByUid(@Param(value="uid")String uid);

	/**
	 * 修改密码
	 * @param userDto
	 * @param params
	 * @return
	 */
	RespStatus updataPwd(UserDto userDto, Map<String, Object> params);
	int updatePwd(UserDto userDto);

	/**
	 * app登陆
	 * @param request
	 * @param params
	 * @return
	 */
	public RespDataObject<UserDto> appLogin(HttpServletRequest request,
			Map<String, String> params);

	public List<UserDto> selectUserList(UserDto userDto);
	public List<UserDto> search(UserDto dto);
	
	/**
	 * 查询钉钉用户
	 * @Author KangLG<2017年10月17日>
	 * @param userDto
	 * @return
	 */
	List<UserDto> searchByDingtalk(UserDto userDto);

	public int selectUserCount(UserDto userDto);

	public UserDto findUserDto(UserDto userDto);
	
	public RespStatus updateUser(UserUpdateFrom from, UserDto userDto, boolean isApp);
	int updateStatus(UserDto userDto);
	
	/**
	 * 用户解绑
	 * @Author KangLG<2017年11月28日>
	 * @param userDto
	 * @return
	 */
	int update4Unbind(UserDto userDto);
	int update4UnbindAgency(UserDto userDto);

	public UserDto selectUserByAccount(String account);

	public RespStatus insertUser(UserDto userDto, boolean isApp);
	/**
	 * 根据部门分组统计用户数量
	 * @param userDto
	 * @return
	 */
	List<Map<String,Object>> selectUserCountGroupByDeptId(UserDto userDto);

	public String selectUidByDeptIds(String deptIds);
	/**
	 * 
	 * @Author KangLG<2018年1月15日>
	 * @param listDeptIds 部门ID集合
	 * @return
	 */
	public String selectUidByDeptList(List<Integer> listDeptIds);
	
	/**
	 * 自动同步用户信息
	 * @Author KangLG<2017年11月1日>
	 */
	void autoSyncDingtalkUser();
	void autoSyncOldUser();
	
	/**
	 * 查询机构下所有的用户id
	 * @param agencyId
	 * @return
	 */
	public String selectUidByAgencyId(int agencyId);
	
	/**
	 * 获取对象By手机号
	 * @Author KangLG<2017年12月7日>
	 * @param mobile
	 * @return
	 */
	UserDto getEntityByMobile(String mobile);
	
	CustomerFundDto selectFundByUid(String uid);

}
