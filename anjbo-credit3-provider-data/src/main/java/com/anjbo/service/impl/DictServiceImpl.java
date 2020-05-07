/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.DictDto;
import com.anjbo.dao.DictMapper;
import com.anjbo.service.DictService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-02 18:19:28
 * @version 1.0
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictDto>  implements DictService {
	@Autowired private DictMapper dictMapper;

	@Override
	public DictDto findDto(DictDto dto) {
		// TODO Auto-generated method stub
		return dictMapper.findDto(dto);
	}

}
