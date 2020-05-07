package com.anjbo.service.impl;

import com.anjbo.bean.risk.AllocationFundAduitDto;
import com.anjbo.dao.AlloctionFundAduitMapper;
import com.anjbo.service.AlloctionFundAduitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service
public class AlloctionFundAduitServiceImpl implements AlloctionFundAduitService {

	@Resource
	private AlloctionFundAduitMapper alloctionFundAduitMapper;
	@Override
	public AllocationFundAduitDto selectFundAudit(String orderNo) {
		return alloctionFundAduitMapper.selectFundAudit(orderNo);
	}

	@Override
	public int addFundAuidt(AllocationFundAduitDto aduitDto) {
		return alloctionFundAduitMapper.addFundAuidt(aduitDto);
	}

	@Override
	public int updateFundAuidt(AllocationFundAduitDto aduitDto) {
		return alloctionFundAduitMapper.updateFundAuidt(aduitDto);
	}

	@Override
	public int updataStatus(AllocationFundAduitDto aduitDto) {
		return alloctionFundAduitMapper.updataStatus(aduitDto);
	}

	@Override
	public List<AllocationFundAduitDto> selectAll() {
		return alloctionFundAduitMapper.selectAll();
	}

	@Override
	public int deleteByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return alloctionFundAduitMapper.deleteByOrderNo(orderNo);
	}

	
}
