package com.anjbo.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.user.DeptDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespPageData;
import com.anjbo.dao.DeptMapper;
import com.anjbo.service.DeptService;
import com.anjbo.utils.HttpUtil;

@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptDto, Integer> implements DeptService{
	@Resource private DeptMapper deptMapper;
	
	@Override
	public DeptDto findDeptByDeptId(int deptId) {
		return deptMapper.findDeptByDeptId(deptId);
	}
	
	@Override
	public List<DeptDto> selectDeptList(DeptDto deptDto) {
		return deptMapper.selectDeptList(deptDto);
	}
	
	@Override
	public int selectDeptCount(DeptDto deptDto) {
		return deptMapper.selectDeptCount(deptDto);
	}
	
	@Override
	public void autoSyncDingtalkDept(){
		// 业务系统部门列表
		List<Integer> lstDbIds = deptMapper.searchIdsByAgency(1);
						
		// 钉钉部门增量列表
		RespPageData<DeptDto> resp = new HttpUtil().getRespPageData(
				Constants.LINK_CREDIT/*Constants.LINK_CREDIT http://127.0.0.1:9910*/, 
				"/credit/third/api/dingtalk/dept/list", 
				null, 
				DeptDto.class);
		List<DeptDto> lstInsert = new LinkedList<DeptDto>();
		List<DeptDto> lstUpdate = new LinkedList<DeptDto>();
		if(null!=resp && null!=resp.getRows() && !resp.getRows().isEmpty()){
			for (DeptDto dto : resp.getRows()) {
				if(lstDbIds.contains(dto.getId())){
					lstUpdate.add(dto);
				}else{
					lstInsert.add(dto);
				}
			}
		}
		
		// 增量更新或新增
		if(!lstInsert.isEmpty()){
			deptMapper.batchInsert(lstInsert);
		}
		if(!lstUpdate.isEmpty()){
			deptMapper.batchUpdate(lstUpdate);
		}
	}
	
}
