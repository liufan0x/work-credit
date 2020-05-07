package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.DocumentsMapper;
import com.anjbo.service.DocumentsService;
import com.anjbo.utils.CommonDataUtil;

@Transactional
@Service
public class DocumentsServiceImpl implements DocumentsService{
	
	Logger log = Logger.getLogger(DocumentsServiceImpl.class);
	@Resource
	private DocumentsMapper documentsMapper;

	public Map<String,Object> detailElementAll(String orderNo){
		Map<String,Object> map = new HashMap<String,Object>();
		DocumentsDto documents = documentsMapper.detail(orderNo);
		ForeclosureTypeDto foreclosureType = documentsMapper.detailForeclosureType(orderNo);
		PaymentTypeDto paymentType = documentsMapper.detailPaymentType(orderNo);
		map.put("documents", documents);
		map.put("foreclosureType", foreclosureType);
		map.put("paymentType", paymentType);
		return map;
	}

	public int insert(DocumentsDto obj){
		int success = documentsMapper.insert(obj);
		// 回款方式
		PaymentTypeDto orderPaymentModeDto = new PaymentTypeDto();
		orderPaymentModeDto.setOrderNo(obj.getOrderNo());
		orderPaymentModeDto.setPaymentMode("网银转账");
		orderPaymentModeDto.setPaymentaccountType("产权人");
		documentsMapper.insertPaymentType(orderPaymentModeDto);

		// 赎楼方式
		ForeclosureTypeDto orderForeclosureTypeDto = new ForeclosureTypeDto();
		orderForeclosureTypeDto.setOrderNo(obj.getOrderNo());
		orderForeclosureTypeDto.setForeclosureType("银行自动扣款");
		orderForeclosureTypeDto.setAccountType("产权人");
		documentsMapper.insertForeclosureType(orderForeclosureTypeDto);
		return success;
	}

	public int insert(Map<String,Object> map){
		return update(map);
	}

	public int update(Map<String,Object> map){
		Map<String,Object> dMap = MapUtils.getMap(map, "documents");
		String orderNo = MapUtils.getString(dMap, "orderNo", "");
		Map<String,Object> fMap = MapUtils.getMap(map, "foreclosureType");
		Map<String,Object> pMap = MapUtils.getMap(map, "paymentType");
		fMap.put("orderNo", orderNo);
		pMap.put("orderNo", orderNo);
		int success = 0;
		DocumentsDto tmp = documentsMapper.detail(orderNo);
		if(null==tmp){
			documentsMapper.insertForeclosureTypeByMap(fMap);
			documentsMapper.insertPaymentTypeByMap(pMap);
			success = documentsMapper.insertByMap(dMap);
		} else {
			documentsMapper.updateForeclosureTypeByMap(fMap);
			documentsMapper.updatePaymentTypeByMap(pMap);
			success = documentsMapper.updateByMap(dMap);
		}
		return success;
	}

	public DocumentsDto detail(String orderNo){
		DocumentsDto obj = documentsMapper.detail(orderNo);
		if(null==obj){
			obj = new DocumentsDto();
		}

		Map<String,Object> tmp = null;
		ForeclosureTypeDto foreclosureType = documentsMapper.detailForeclosureType(orderNo);
		if(null!=foreclosureType){
			tmp = getBank(foreclosureType.getBankNameId(),foreclosureType.getBankSubNameId());
			String bank = MapUtils.getString(tmp,"bankId","");
			String subBank = MapUtils.getString(tmp,"subBankId","");
			foreclosureType.setBankName(bank);
			foreclosureType.setBankSubName(subBank);
			obj.setForeclosureType(foreclosureType);
		}
		PaymentTypeDto paymentType = documentsMapper.detailPaymentType(orderNo);
		if(null!=paymentType){
			tmp = getBank(paymentType.getPaymentBankNameId(),paymentType.getPaymentBankSubNameId());
			String bank = MapUtils.getString(tmp,"bankId","");
			String subBank = MapUtils.getString(tmp,"subBankId","");
			paymentType.setPaymentBankName(bank);
			paymentType.setPaymentBankSubName(subBank);
			obj.setPaymentType(paymentType);
		}
		return obj;
	}

	public int update(DocumentsDto obj){
		int success = 0;
		DocumentsDto tmp = documentsMapper.detail(obj.getOrderNo());
		if(null==tmp){
			if(obj.getForeclosureType()!=null){
				obj.getForeclosureType().setOrderNo(obj.getOrderNo());
				documentsMapper.insertForeclosureType(obj.getForeclosureType());
			}
			if(obj.getPaymentType()!=null){
				obj.getPaymentType().setOrderNo(obj.getOrderNo());
				documentsMapper.insertPaymentType(obj.getPaymentType());
			}
			if(null==obj.getStatus()){
				obj.setStatus(1);
			}
			success = documentsMapper.insert(obj);
		} else {
			ForeclosureTypeDto ftmp = documentsMapper.detailForeclosureType(obj.getOrderNo());
			if(null!=obj.getForeclosureType()){
				if(null==ftmp){
					obj.getForeclosureType().setOrderNo(obj.getOrderNo());
					documentsMapper.insertForeclosureType(obj.getForeclosureType());
				} else{
					documentsMapper.updateForeclosureTypeNotValidate(obj.getForeclosureType());
				}
			}
			PaymentTypeDto ptmp = documentsMapper.detailPaymentType(obj.getOrderNo());
			if(null !=obj.getPaymentType()){
				obj.getPaymentType().setOrderNo(obj.getOrderNo());
				if(null==ptmp){
					documentsMapper.insertPaymentType(obj.getPaymentType());
				} else{
					documentsMapper.updatePaymentTypeNotValidate(obj.getPaymentType());
				}
			}
			if("APP".equals(obj.getNextHandleUid())) {
				success = documentsMapper.update(obj);
			}else {
				success = documentsMapper.updateNotValidate(obj);
			}
		}
		return success;
	}

	public DocumentsDto addImg(DocumentsDto obj){
		DocumentsDto tmp = documentsMapper.detail(obj.getOrderNo());
		if(null==tmp){
			documentsMapper.insert(obj);
		} else {
			String imgUrl = tmp.getGreenStatusImgUrl();
			if(StringUtils.isNotBlank(imgUrl)&&imgUrl.endsWith(Constants.IMG_SEPARATE)){
				imgUrl += obj.getGreenStatusImgUrl();
				obj.setGreenStatusImgUrl(imgUrl);
			} else{
				imgUrl = obj.getGreenStatusImgUrl()+imgUrl;
				obj.setGreenStatusImgUrl(imgUrl);
			}
			documentsMapper.updateDocumentImg(obj);

		}
		return obj;
	}
	public DocumentsDto delImg(DocumentsDto obj){
		documentsMapper.updateDocumentImg(obj);
		DocumentsDto tmp = documentsMapper.detail(obj.getOrderNo());
		return tmp;
	}

	public int updateForeclosureType(DocumentsDto doc){
		int success = 0;
		DocumentsDto tmpDoc =  documentsMapper.detail(doc.getOrderNo());
		if(null==tmpDoc){
			doc.setStatus(1);
			documentsMapper.insert(doc);
		}
		ForeclosureTypeDto fdto = documentsMapper.detailForeclosureType(doc.getOrderNo());
		if(null==fdto){
			success = documentsMapper.insertForeclosureType(doc.getForeclosureType());
		} else {
			success = documentsMapper.updateForeclosureType(doc.getForeclosureType());
		}
		return success;
	}

	public int updatePaymentType(DocumentsDto doc){
		int success = 0;
		DocumentsDto tmpDoc =  documentsMapper.detail(doc.getOrderNo());
		if(null==tmpDoc){
			doc.setStatus(1);
			documentsMapper.insert(doc);
		}
		PaymentTypeDto pdto = documentsMapper.detailPaymentType(doc.getOrderNo());
		if(null==pdto){
			success = documentsMapper.insertPaymentType(doc.getPaymentType());
		} else {
			success = documentsMapper.updatePaymentType(doc.getPaymentType());
		}
		return success;
	}

	/**
	 * 
	 * @param bankId 银行ID
	 * @param subBankId 支行ID
	 * @return java.util.Map 银行bankId,subBankId为key,银行名称为value
	 */
	public Map<String,Object> getBank(Integer bankId,Integer subBankId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bankId", "");
		map.put("subBankId","");
		if(null==bankId)return map;
			BankDto bankDto =CommonDataUtil.getBankNameById(bankId);
			map.put("bankId", bankDto==null?"":bankDto.getName());
		if(null==subBankId)return map;
		SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(subBankId);
		map.put("subBankId", subBankDto==null?"":subBankDto.getName());
		return map;
	}

}
