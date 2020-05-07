package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.user.DeptDto;

public interface DeptService extends BaseService<DeptDto, Integer> {

	DeptDto findDeptByDeptId(int deptId);

	List<DeptDto> selectDeptList(DeptDto deptDto);

	int selectDeptCount(DeptDto deptDto);
	
	/**
	 * 自动同步钉钉部门信息
	 * @Author KangLG<2017年11月1日>
	 */
	void autoSyncDingtalkDept();
	
}
