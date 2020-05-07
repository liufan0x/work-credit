package com.anjbo.service.tools.impl;


import com.anjbo.bean.tools.CalcRate;
import com.anjbo.dao.tools.CalcRateMapper;
import com.anjbo.service.tools.CalcRateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CalcRateServiceImpl implements CalcRateService {

	@Resource
	public CalcRateMapper calcRateMapper;

	@Override
	public List<CalcRate> selectCalcRateList() {
		return calcRateMapper.selectCalcRateList();
	}

	@Override
	public CalcRate selectCalcRate(int id) {
		return calcRateMapper.selectCalcRate(id);
	}

	@Override
	public CalcRate selectCalcStandardRate() {
		return calcRateMapper.selectCalcStandardRate();
	}

	@Override
	public int addCalcRate(CalcRate calcRate) {
		return calcRateMapper.addCalcRate(calcRate);
	}

	@Override
	public int updateCalcRate(CalcRate calcRate) {
		return calcRateMapper.updateCalcRate(calcRate);
	}

	@Override
	public int updateCalcStandardRate(CalcRate calcRate) {
		return calcRateMapper.updateCalcStandardRate(calcRate);
	}

	@Override
	public int updateCalcRateSort(int id, int sort) {
		return calcRateMapper.updateCalcRateSort(id, sort);
	}

	@Override
	public int deleteCalcRate(int id) {
		return calcRateMapper.deleteCalcRate(id);
	}
}
