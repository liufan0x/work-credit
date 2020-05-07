package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.dao.LendingHarvestMapper;
import com.anjbo.dao.LendingPayMapper;
import com.anjbo.service.LendingHarvestService;

@Service
public class LendingHarvestServiceImpl implements LendingHarvestService {

	@Resource LendingHarvestMapper lendingHarvestMapper;
	@Resource LendingPayMapper lendingPayMapper;
	
	/**
	 *查询详情 
	 */
	@Override
	public LendingHarvestDto findByHarvest(LendingHarvestDto harvestDto) {
		// TODO Auto-generated method stub
		return lendingHarvestMapper.findByHarvest(harvestDto);
	}
	
	
	/**
	 * 添加信息
	 */
	@Override
	public int addLendingHarvest(LendingHarvestDto harvestDto) {
		return lendingHarvestMapper.addLendingHarvest(harvestDto);
	}

	/**
	 * 更新信息
	 */
	@Override
	public int updateHarves(LendingHarvestDto harvestDto) {
		return lendingHarvestMapper.updateHarves(harvestDto);
	}

	
	/**
	 * 删除
	 */
	@Override
	public int delectHarves(LendingHarvestDto harvestDto) {
		// TODO Auto-generated method stub
		return lendingHarvestMapper.delectHarves(harvestDto);
	}

}
