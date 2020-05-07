/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseReceivableForDto;
import com.anjbo.dao.order.BaseReceivableForMapper;
import com.anjbo.service.order.BaseReceivableForService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@Service
public class BaseReceivableForServiceImpl extends BaseServiceImpl<BaseReceivableForDto>  implements BaseReceivableForService {
	@Autowired private BaseReceivableForMapper baseReceivableForMapper;

	@Override
	public List<BaseReceivableForDto> selectOrderReceivableForByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return baseReceivableForMapper.selectOrderReceivableForByOrderNo(orderNo);
	}

}
