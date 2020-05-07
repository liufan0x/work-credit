package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.tools.DegreeBADto;


public interface DegreeBAService {
	/**
	 * 查询是否是已锁定的学位房
	 * 
	 * @user Administrator
	 * @date 2016-11-14 上午10:18:27 
	 * @param baoAnDegreeDto
	 * @return
	 */
	List<DegreeBADto> queryBaoAnDegree(DegreeBADto begreeBADto);
	/**
	 * 模糊查询是否已锁定的房屋地址列表
	 * 
	 * @user Administrator
	 * @date 2016-11-14 上午10:20:45 
	 * @param baoAnDegreeDto
	 * @return
	 */
	List<DegreeBADto> queryPropertyRoomList(DegreeBADto begreeBADto);
}
