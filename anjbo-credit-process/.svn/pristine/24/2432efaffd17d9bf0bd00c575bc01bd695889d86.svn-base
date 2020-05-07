package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.dao.NotarizationMapper;
import com.anjbo.service.NotarizationService;

@Service
public class NotarizationServiceImpl implements NotarizationService {

	@Resource NotarizationMapper notarizationMapper;
	
	@Override
	public NotarizationDto selectNotarization(NotarizationDto dto) {
		// TODO Auto-generated method stub
		return notarizationMapper.selectNotarization(dto);
	}

	@Override
	public int addNotarizetion(NotarizationDto dto) {
		// TODO Auto-generated method stub
		return notarizationMapper.addNotarizetion(dto);
	}

	@Override
	public int updateNotarizetion(NotarizationDto dto) {
		// TODO Auto-generated method stub
		return notarizationMapper.updateNotarizetion(dto);
	}

}
