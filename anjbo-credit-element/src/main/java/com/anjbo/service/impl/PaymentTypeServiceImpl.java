package com.anjbo.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.PaymentTypeMapper;
import com.anjbo.service.PaymentTypeService;
@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

	@Resource
	private PaymentTypeMapper paymentTypeMapper;
	@Override
	public Map<String, Object> selectReceivableElement(Map<String, Object> param) {
		return paymentTypeMapper.selectReceivableElement(param);
	}

}
