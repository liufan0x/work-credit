package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.ReceivableHasDto;
import com.anjbo.dao.ReceivableHasMapper;
import com.anjbo.service.ReceivableHasService;

@Service
public class ReceivableHasServiceImpl implements ReceivableHasService {
	
	@Resource ReceivableHasMapper receivableHasMapper;
	
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	@Override
	public ReceivableHasDto findByReceivableHas(ReceivableHasDto dto) {
		// TODO Auto-generated method stub
		return receivableHasMapper.findByReceivableHas(dto);
	}

	/**
	 * 完善
	 */
	@Override
	public int updateReceivableHas(ReceivableHasDto dto) {
		// TODO Auto-generated method stub
		return receivableHasMapper.updateReceivableHas(dto);
	}

	/**
	 * 撤回
	 */
	@Override
	public int updwithdraw(ReceivableHasDto receivableHas) {
		// TODO Auto-generated method stub
		return receivableHasMapper.updwithdraw(receivableHas);
	}

	@Override
	public int addReceivableHas(ReceivableHasDto dto) {
		// TODO Auto-generated method stub
		return receivableHasMapper.addReceivableHas(dto);
	}

}
