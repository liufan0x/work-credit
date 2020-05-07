package com.anjbo.service;

import com.anjbo.bean.risk.ArchiveDto;
import com.anjbo.common.RespDataObject;

import java.util.List;
import java.util.Map;

public interface ArchiveService {

	
	public ArchiveDto detail(String archiveId);
	
	public List<ArchiveDto> listArchive(String orderNo);
	
	public int insert(ArchiveDto obj);
	
	public int insert(Map<String,Object> map);
	
	public int update(ArchiveDto obj);
	
	public int update(Map<String,Object> map);
	
	public int updateByArchiveId(ArchiveDto obj);
	
	public int getArchiveId(RespDataObject<Map<String,Object>> result,Map<String,Object> param) ;
	
	public int update(RespDataObject<Map<String,Object>> result,Map<String,Object> param);
	
	public int delete(ArchiveDto obj);
	
	public int updateByOrderNo(ArchiveDto obj);

	public List<ArchiveDto> detailByOrderNo(String orderNo);
}
