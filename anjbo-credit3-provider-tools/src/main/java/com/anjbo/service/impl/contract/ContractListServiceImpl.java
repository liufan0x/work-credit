/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.contract;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.contract.ContractListDto;
import com.anjbo.bean.contract.FieldInputDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.service.contract.ContractListService;
import com.anjbo.service.contract.FieldInputService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.NumberToCN;

import net.sf.json.JSONObject;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-09-20 18:11:02
 * @version 1.0
 */
@Service
public class ContractListServiceImpl extends BaseServiceImpl<ContractListDto> implements ContractListService {

	@Resource
	private FieldInputService fieldInputService;

	@Resource
	private OrderApi orderApi;
	
	@Resource
	private DataApi dataApi;

	@Override
	public ContractListDto insert(ContractListDto dto) {
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			BaseListDto baseListDto = orderApi.findByListOrderNo(dto.getOrderNo());
			dto.setAgencyId(baseListDto.getAgencyId());
			dto.setCityCode(baseListDto.getCityCode());
			dto.setCityName(baseListDto.getCityName());
			dto.setProductCode(baseListDto.getProductCode());
			dto.setProductName(baseListDto.getProductName());
			dto.setBorrowingAmount(baseListDto.getBorrowingAmount());
			dto.setBorrowingDay(baseListDto.getBorrowingDay());
			dto.setOrderNo(baseListDto.getOrderNo());
			dto.setRelationOrderNo(dto.getRelationOrderNo());
			dto.setData(getDataBySource(dto.getOrderNo()));
		} else {
			dto.setData("{\"borrowerName\":\"" + dto.getCustomerName() + "\"}");
		}
		return super.insert(dto);
	}

	@Override
	public int update(ContractListDto dto) {
		// if(StringUtils.isNotEmpty(MapUtils.getString(dto.getJsonOject(),
		// "borrowerName"))) {
		// dto.setCustomerName(MapUtils.getString(dto.getJsonOject(),
		// "borrowerName",""));
		// }
		return super.update(dto);
	}

	public String getDataBySource(String orderNo) {
		Map<String, Object> jsonData = configuration(orderNo); // TODO 李岳处理 设置jsonData 的值
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		List<FieldInputDto> fieldInputDtos = fieldInputService.search(null);
		for (FieldInputDto fieldInputDto : fieldInputDtos) {
			if (StringUtils.isNotEmpty(fieldInputDto.getSource())) {
				String value = MapUtils.getString(jsonData, fieldInputDto.getSource());
				jsonData.put(fieldInputDto.getValue(), value);
			}
		}
		return jsonObject.toString();
	}

	@Override
	public Map<String, Object> configuration(String orderNo) {
		Map<String, Object> ladingMap = new HashMap<String, Object>();

		BaseBorrowDto baseBorrowDto = orderApi.findBorrowByOrderNo(orderNo);
		String borrowerName = null;
		String customerWifeName = null;
		if (baseBorrowDto==null) {
			ladingMap.put("borrowerName", "");
			ladingMap.put("phoneNumber", "");
			ladingMap.put("loanAmount","");
			ladingMap.put("bigAmount","");
		}else {
	    borrowerName = baseBorrowDto.getBorrowerName();
		String phoneNumber = baseBorrowDto.getPhoneNumber();
		Double loanAmount = baseBorrowDto.getLoanAmount() * 10000;
		String bigAmount = NumberToCN.number2CNMontrayUnit(new BigDecimal(loanAmount)); // 借款金额 大写
		bigAmount = bigAmount.substring(0, bigAmount.indexOf("元"));
		ladingMap = NumberToCN.Separate(loanAmount);
		if (StringUtils.isNotEmpty(borrowerName)) {
			ladingMap.put("borrowerName", borrowerName);
		}else {
			ladingMap.put("borrowerName", "");
		}
		if (StringUtils.isNotEmpty(phoneNumber)) {
			ladingMap.put("phoneNumber", phoneNumber);
		}else {
			ladingMap.put("phoneNumber", "");
		}if (StringUtils.isNotEmpty(bigAmount)) {
			ladingMap.put("bigAmount", bigAmount);
		}else {
			ladingMap.put("bigAmount","");
		}
		ladingMap.put("loanAmount", loanAmount);
		}
		
		BaseCustomerDto baseCustomerDto = orderApi.findCustomerByOrderNo(orderNo);
		if (baseCustomerDto==null) {
			ladingMap.put("customerSex", "");
			ladingMap.put("customerCardNumber", "");
			ladingMap.put("customerWifeName", "");
			ladingMap.put("customerWifeCardNumber","");
			ladingMap.put("customerWifePhone", "");
		}else {
		customerWifeName = baseCustomerDto.getCustomerWifeName();
		if (StringUtils.isNotEmpty(baseCustomerDto.getCustomerSex())) {
			ladingMap.put("customerSex", baseCustomerDto.getCustomerSex());
		}else {
			ladingMap.put("customerSex", "");
		}
		if (StringUtils.isNotEmpty(baseCustomerDto.getCustomerCardNumber())) {
			ladingMap.put("customerCardNumber",  baseCustomerDto.getCustomerCardNumber());
		}else {
			ladingMap.put("customerCardNumber", "");
		}
		if (StringUtils.isNotEmpty(customerWifeName)) {
			ladingMap.put("customerWifeName", customerWifeName);
		}else {
			ladingMap.put("customerWifeName", "");
		}
		if (StringUtils.isNotEmpty(baseCustomerDto.getCustomerWifeCardNumber())) {
			ladingMap.put("customerWifeCardNumber", baseCustomerDto.getCustomerWifeCardNumber());
		}else {
			ladingMap.put("customerWifeCardNumber", "");
		}
		if (StringUtils.isNotEmpty(baseCustomerDto.getCustomerWifePhone())) {
			ladingMap.put("customerWifePhone",baseCustomerDto.getCustomerWifePhone());	
		}else {
			ladingMap.put("customerWifePhone", "");
		}
	}

		List<Map<String, Object>> propertyMap = orderApi.findHousePropertyOrderNo(orderNo);
		DictDto dictDto = new DictDto();
		dictDto.setType("bookingSzAreaOid"); // 字典类型
		for (int i = 0; i < propertyMap.size(); i++) {
			Map<String, Object> map2 = propertyMap.get(i);
			String city = MapUtils.getString(map2, "city");

			if (city != null) { // 市名
				dictDto.setCode(city);
				dictDto = dataApi.getCityById(dictDto);
				ladingMap.put("cityName" + (i + 1), dictDto.getName());
			}
			ladingMap.put("houseRegionName" + (i + 1), MapUtils.getString(map2, "houseRegion")); // 区名
			ladingMap.put("houseName" + (i + 1), MapUtils.getString(map2, "houseName")); // 房产名称
		}

		List<Map<String, Object>> propertyPeopleMap = orderApi.findHousePropertyPeopleOrderNo(orderNo);

		Map<String, String> names = new HashMap<String, String>();

		int num = 0;
		for (int i = 0; i < propertyPeopleMap.size(); i++) { 
			num++;
			Map<String, Object> map3 = propertyPeopleMap.get(i);
			if (StringUtils.isNotEmpty(MapUtils.getString(map3, "propertyName"))) {
				if (!borrowerName.equals(MapUtils.getString(map3, "propertyName"))) {
					if (map3.get("propertyName")!=null&&!MapUtils.getString(map3, "propertyName").equals(customerWifeName)) {
						System.out.println("进来了这个判断，妻子值打印"+customerWifeName);
						names.put(MapUtils.getString(map3, "propertyCardNumber"),
								MapUtils.getString(map3, "propertyName"));
					} else {
						ladingMap.put("propertyName" + num, "");
						ladingMap.put("propertyCardNumber" + num, "");
					}

				} else {
					ladingMap.put("propertyName" + num, "");
					ladingMap.put("propertyCardNumber" + num, "");
				}
			} else {
				ladingMap.put("propertyName" + num, "");
				ladingMap.put("propertyCardNumber" + num, "");
			}
		}
		num -= names.size();
		Set<String> keys = names.keySet();
		for (String name : keys) {

			num++;
			ladingMap.put("propertyName" + num, names.get(name));
			ladingMap.put("propertyCardNumber" + num, name);
		}

		ForeclosureTypeDto foreclosureTypeDto = orderApi.findForeclosureByOrderNo(orderNo);
		if (foreclosureTypeDto==null) {
			  ladingMap.put("bankName","");
			  ladingMap.put("bankSubName","");
			  ladingMap.put("bankCardMaster", "");
			  ladingMap.put("bankNo",""); 
		}else {
		if(StringUtils.isNotEmpty(foreclosureTypeDto.getBankName())) {
			ladingMap.put("bankName", foreclosureTypeDto.getBankName()); // 开户银行
		   }else {
			   ladingMap.put("bankName",""); 
		}
        if(StringUtils.isNotEmpty(foreclosureTypeDto.getBankSubName())) {
        	ladingMap.put("bankSubName", foreclosureTypeDto.getBankSubName()); // 开户支行
		   }else {
			   ladingMap.put("bankSubName","");
		}
       if(StringUtils.isNotEmpty(foreclosureTypeDto.getBankCardMaster())) {
    	   ladingMap.put("bankCardMaster", foreclosureTypeDto.getBankCardMaster()); // 银行卡户名
           }else {
        	   ladingMap.put("bankCardMaster", "");
          }
        if(StringUtils.isNotEmpty(foreclosureTypeDto.getBankNo())) {
        	ladingMap.put("bankNo", foreclosureTypeDto.getBankNo()); // 银行卡号
       }else {
    	   ladingMap.put("bankNo",""); 
        }
     }
		PaymentTypeDto paymentTypeDto = orderApi.findPaymentByOrderNo(orderNo);
		if (paymentTypeDto==null) {
			ladingMap.put("paymentBankName","");
			ladingMap.put("paymentBankSubName", "");
			ladingMap.put("paymentBankCardName", "");
			ladingMap.put("paymentBankNumber", "");
		}else {
		if (StringUtils.isNotEmpty(paymentTypeDto.getPaymentBankName())) {
			ladingMap.put("paymentBankName", paymentTypeDto.getPaymentBankName()); // 回款银行
		} else {
			ladingMap.put("paymentBankName","");
		}
		if (StringUtils.isNotEmpty(paymentTypeDto.getPaymentBankSubName())) {
			ladingMap.put("paymentBankSubName", paymentTypeDto.getPaymentBankSubName()); // 回款支行
		} else {
			ladingMap.put("paymentBankSubName", "");
		}
		if (StringUtils.isNotEmpty(paymentTypeDto.getPaymentBankCardName())) {
			ladingMap.put("paymentBankCardName", paymentTypeDto.getPaymentBankCardName()); // 回款户名
		} else {
			ladingMap.put("paymentBankCardName", "");
		}
		if (StringUtils.isNotEmpty(paymentTypeDto.getPaymentBankNumber())) {
			ladingMap.put("paymentBankNumber", paymentTypeDto.getPaymentBankNumber()); // 回款卡号
		} else {
			ladingMap.put("paymentBankNumber", "");
		}
	}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		BaseListDto baseList = orderApi.findByListOrderNo(orderNo);
		Date strings = baseList.getCreateTime();
		String date = sdf.format(strings);
		String year = date.substring(3, date.lastIndexOf("年")); // 2017年09月29日 == 7
		ladingMap.put("date", date); // 提单日期
		ladingMap.put("year", year); // 提单日期 年份截取
		String contractNo = baseList.getContractNo();
		if (null != contractNo) {
			if (!contractNo.contains("第") || !contractNo.contains("号")) {
				contractNo = "";
			} else {
				contractNo = contractNo.substring(contractNo.indexOf("第") + 1, contractNo.lastIndexOf("号")); // 合同编号
			}
		} else {
			contractNo = "";
		}
		ladingMap.put("contractNo", contractNo);
		List<ReceivableForDto> baseReceivableFors = orderApi.findReceivableForByOrderNo(orderNo);
		for (int i = 0; i < baseReceivableFors.size(); i++) {
			ReceivableForDto dto = baseReceivableFors.get(i);
			String payMentAmountDate = dto.getPayMentAmountDate();
			if (null != payMentAmountDate) {
				try {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					Date dString = sdf1.parse( payMentAmountDate);
					payMentAmountDate = sdf.format(dString); // 实际回款日期
					ladingMap.put("payMentAmountDate", payMentAmountDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		return ladingMap;
	}


}
