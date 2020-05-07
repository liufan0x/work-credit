/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.dao.element.DocumentsMapper;
import com.anjbo.dao.element.ForeclosureTypeMapper;
import com.anjbo.dao.element.PaymentTypeMapper;
import com.anjbo.dao.order.BaseListMapper;
import com.anjbo.service.element.DocumentsService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@Service
public class DocumentsServiceImpl extends BaseServiceImpl<DocumentsDto>  implements DocumentsService {
	@Autowired private DocumentsMapper documentsMapper;
	@Autowired private BaseListMapper baseListMapper;
	@Autowired private ForeclosureTypeMapper foreclosureTypeMapper;
	@Autowired private PaymentTypeMapper paymentTypeMapper;
	@Autowired private DataApi dataApi;
	@Override
	public DocumentsDto find(DocumentsDto dto) {
		String orderNo = dto.getOrderNo();
		BaseListDto baseListDto = new BaseListDto();
		baseListDto.setOrderNo(orderNo);
		baseListDto = baseListMapper.find(baseListDto);
		if("03".equals(baseListDto.getProductCode())&&StringUtil.isNotBlank(baseListDto.getRelationOrderNo())){
			orderNo = baseListDto.getRelationOrderNo();
		}
		DocumentsDto documentsDto = new DocumentsDto();
		documentsDto.setOrderNo(orderNo);
		DocumentsDto obj = documentsMapper.find(documentsDto);
		if(null==obj){
			obj = new DocumentsDto();
		}

		ForeclosureTypeDto foreclosureTypeDto = new ForeclosureTypeDto();
		foreclosureTypeDto.setOrderNo(orderNo);
		foreclosureTypeDto = foreclosureTypeMapper.find(foreclosureTypeDto);
		if(null!=foreclosureTypeDto){
			BankDto bankDto = new BankDto();
			bankDto.setId(foreclosureTypeDto.getBankNameId());
			bankDto = dataApi.getBankNameById(bankDto).getData();
			BankSubDto bankSubDto = new BankSubDto();
			bankSubDto.setId(foreclosureTypeDto.getBankSubNameId());
			bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
			foreclosureTypeDto.setBankName(bankDto.getName());
			foreclosureTypeDto.setBankSubName(bankSubDto.getName());
			obj.setForeclosureType(foreclosureTypeDto);
		}
		PaymentTypeDto paymentType = new PaymentTypeDto();
		paymentType.setOrderNo(orderNo);
		paymentType = paymentTypeMapper.find(paymentType);
		if(null!=paymentType){
			BankDto bankDto = new BankDto();
			bankDto.setId(paymentType.getPaymentBankNameId());
			bankDto = dataApi.getBankNameById(bankDto).getData();
			BankSubDto bankSubDto = new BankSubDto();
			bankSubDto.setId(paymentType.getPaymentBankSubNameId());
			bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
			paymentType.setPaymentBankName(bankDto.getName());
			paymentType.setPaymentBankSubName(bankSubDto.getName());
			obj.setPaymentType(paymentType);
		}
		return obj;
	}
}
