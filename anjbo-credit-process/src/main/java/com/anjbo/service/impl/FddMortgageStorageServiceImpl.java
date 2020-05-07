package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.dao.FddMortgageStorageMapper;
import com.anjbo.service.FddMortgageStorageService;

@Service
public class FddMortgageStorageServiceImpl implements FddMortgageStorageService {

	@Resource FddMortgageStorageMapper fddMortgageStorageMapper;
	@Override
	public FddMortgageStorageDto findByStorage(String orderNo) {
		// TODO Auto-generated method stub
		return fddMortgageStorageMapper.findByStorage(orderNo);
	}

	@Override
	public int addStorage(FddMortgageStorageDto storageDto) {
		FddMortgageStorageDto dto=fddMortgageStorageMapper.findByStorage(storageDto.getOrderNo());
		if(dto==null){
			return fddMortgageStorageMapper.addStorage(storageDto);
		}else {
			return fddMortgageStorageMapper.updStorage(storageDto);
		}
	}

}
