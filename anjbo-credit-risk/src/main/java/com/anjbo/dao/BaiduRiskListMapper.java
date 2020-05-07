package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.risk.BaiduRiskListDto;

public interface BaiduRiskListMapper {
	
	public List<BaiduRiskListDto> selectBaiduRiskList(BaiduRiskListDto baiduRiskListDto);
	
	public int selectBaiduRiskListCount(BaiduRiskListDto baiduRiskListDto);
	
	public int insertBaiduRiskList(BaiduRiskListDto baiduRiskListDto);
	
	public BaiduRiskListDto getBaiduRiskById(int id);
	
	public int deleteBaiduRiskById(int id);
}
