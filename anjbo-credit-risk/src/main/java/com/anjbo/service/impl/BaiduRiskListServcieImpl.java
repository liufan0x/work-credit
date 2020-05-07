package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.BaiduRiskListDto;
import com.anjbo.dao.BaiduRiskListMapper;
import com.anjbo.service.BaiduRiskListService;
@Service
public class BaiduRiskListServcieImpl implements BaiduRiskListService {

	@Resource
	private BaiduRiskListMapper baiduRiskListMapper;
	@Override
	public List<BaiduRiskListDto> selectBaiduRiskList(
			BaiduRiskListDto baiduRiskListDto) {
		return baiduRiskListMapper.selectBaiduRiskList(baiduRiskListDto);
	}

	@Override
	public int selectBaiduRiskListCount(BaiduRiskListDto baiduRiskListDto) {
		return baiduRiskListMapper.selectBaiduRiskListCount(baiduRiskListDto);
	}

	@Override
	public int insertBaiduRiskList(BaiduRiskListDto baiduRiskListDto) {
		return baiduRiskListMapper.insertBaiduRiskList(baiduRiskListDto);
	}

	@Override
	public BaiduRiskListDto getBaiduRiskById(int id) {
		return baiduRiskListMapper.getBaiduRiskById(id);
	}

	@Override
	public int deleteBaiduRiskById(int id) {
		return baiduRiskListMapper.deleteBaiduRiskById(id);
	}

}
