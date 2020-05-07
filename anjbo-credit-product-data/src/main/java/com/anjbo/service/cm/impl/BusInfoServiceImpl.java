package com.anjbo.service.cm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.cm.CMBusInfoDto;
import com.anjbo.dao.cm.BusInfoMapper;
import com.anjbo.service.cm.BusInfoService;

/**
 * 影像资料
 * @author limh limh@anjbo.com   
 * @date 2016-12-28 下午05:29:08
 */

@Service
public class BusInfoServiceImpl implements BusInfoService {
	
	@Resource private BusInfoMapper busInfoMapper;



	@Override
	public List<CMBusInfoDto> getByOrderAndCode(String orderNo, String code) {
		return busInfoMapper.getByOrderAndCode(orderNo, code);
	}

	
}
