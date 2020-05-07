package com.anjbo.dao;

import com.anjbo.bean.risk.ArchiveDto;

import java.util.List;
import java.util.Map;



public interface ArchiveMapper {

	public ArchiveDto detail(String archiveId);
	
	public List<ArchiveDto> listArchive(String orderNo);
	
	public int insert(ArchiveDto obj);
	
	public int insertByMap(Map<String,Object> map);
	
	public int update(ArchiveDto obj);
	
	public int updateByMap(Map<String,Object> map);
	
	public int updateByArchiveId(ArchiveDto obj);
	
	public ArchiveDto selectArchiveByOrderNoAndArchiveId(Map<String,Object> map);
	
	public ArchiveDto selectByOrderNoAndArchiveId(ArchiveDto dto);
	
	public int delete(ArchiveDto obj);
	
	public int updateByOrderNo(ArchiveDto obj);
	
	public List<ArchiveDto>detailByOrderNo(String orderNo);
}
