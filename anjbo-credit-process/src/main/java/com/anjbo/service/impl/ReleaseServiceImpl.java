package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.FddReleaseDto;
import com.anjbo.dao.ReleaseMapper;
import com.anjbo.service.ReleaseService;

@Service
public class ReleaseServiceImpl implements ReleaseService {

	@Resource ReleaseMapper releaseMapper;
	@Override
	public FddReleaseDto findByRelease(String OrderNo) {
		// TODO Auto-generated method stub
		return releaseMapper.findByRelease(OrderNo);
	}

	@Override
	public int addRelease(FddReleaseDto releaseDto) {
		// TODO Auto-generated method stub
		FddReleaseDto dto=releaseMapper.findByRelease(releaseDto.getOrderNo());
		if(dto==null){
			return releaseMapper.addRelease(releaseDto);
		}else{
			return releaseMapper.updRelease(releaseDto);
		}
	}

}
