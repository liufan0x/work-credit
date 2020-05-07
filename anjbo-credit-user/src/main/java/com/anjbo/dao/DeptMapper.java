package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.user.DeptDto;

/**
 * 部门Dto
 * @author lic
 */
public interface DeptMapper extends BaseMapper<DeptDto, Integer> {

	DeptDto findDeptByDeptId(int deptId);

	List<DeptDto> selectDeptList(DeptDto deptDto);

	int selectDeptCount(DeptDto deptDto);
	
	List<Integer> searchIdsByAgency(int agencyId);
	
}