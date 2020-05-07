package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.anjbo.bean.contract.ContractCustomerBorrowerDto;
import com.anjbo.bean.contract.LoanContractDto;
import com.anjbo.bean.contract.OrderContractCustomerBorrowerDto;
import com.anjbo.bean.contract.OrderContractDataDto;
import com.anjbo.bean.contract.OrderContractPropertyPeopleDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.OrderContractDataMapper;
import com.anjbo.service.OrderContractDataService;
import com.anjbo.utils.BeanToMapUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.PinYin4JUtil;
import com.anjbo.utils.StringUtil;

@Service
public class OrderContractDataServiceImpl implements OrderContractDataService {

	@Resource
	private OrderContractDataMapper orderContractDataMapper;
	
	HttpUtil httpUtil = new HttpUtil();
	
	@Override
	public int insertOrderContractData(OrderContractDataDto orderContractDataDto) {
		OrderBaseBorrowDto borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", orderContractDataDto, OrderBaseBorrowDto.class);
		if(null==borrow||null==borrow.getBorrowerName()){
			//重置清空合同内容
			orderContractDataMapper.delete(orderContractDataDto);
			return 0;
		}
		OrderBaseCustomerDto customer = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", orderContractDataDto, OrderBaseCustomerDto.class);
		OrderBaseHouseDto house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", orderContractDataDto, OrderBaseHouseDto.class);
		DocumentsDto documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", orderContractDataDto,DocumentsDto.class);
		contract1(orderContractDataDto,borrow,customer,house,documents);
		contract2(orderContractDataDto,borrow,customer,house,documents);
		return 1;
	}

	@Override
	public int updateOrderContractData(OrderContractDataDto orderContractDataDto) {
		Map<String,Object> data = orderContractDataDto.getData();
		if(StringUtil.isNotBlank(MapUtils.getString(data, "customerName",""))
				&&StringUtil.isNotBlank(MapUtils.getString(data, "dateOfSigning",""))){
			//设置合同编号
			String py = PinYin4JUtil.getFirstSpell(MapUtils.getString(data, "customerName"));
			py = py.toUpperCase();
			String date = MapUtils.getString(data, "dateOfSigning");
			date = date.substring(date.length()-10, date.length());
			String dateDay = date.substring(date.length()-2, date.length());
			String dateMonth = date.substring(date.length()-5, date.length()-3);
			data.put("contractNo", py+dateMonth+dateDay);
		}
		//如果更新信用信息查询授权书的合同编号
		if("tbl_contract_credit_authorization".equals(orderContractDataDto.getTblName())
				&&StringUtil.isNotBlank(MapUtils.getString(data, "creditBorrowerName",""))
				&&StringUtil.isNotBlank(MapUtils.getString(data, "dateOfSigning",""))){
			String py = PinYin4JUtil.getFirstSpell(MapUtils.getString(data, "creditBorrowerName"));
			py = py.toUpperCase();
			String date = MapUtils.getString(data, "dateOfSigning");
			date = date.substring(date.length()-10, date.length());
			String dateDay = date.substring(date.length()-2, date.length());
			String dateMonth = date.substring(date.length()-5, date.length()-3);
			data.put("creditContractNo", py+dateMonth+dateDay);
		}
		if(!"tbl_contract_trust_loan".equals(orderContractDataDto.getTblName())){
			orderContractDataDto.setTblName("tbl_contract_bussiness_application");
		}
		String dataStr = JSONObject.fromObject(data).toString();
		if(MapUtils.isEmpty(data)){
			dataStr = null;
		}
		orderContractDataDto.setDataStr(dataStr);
		OrderContractDataDto orderContractData = selectOrderContractDataDto(orderContractDataDto);
		if(null!=orderContractData){
			orderContractDataMapper.updateOrderContractData(orderContractDataDto);
		}else{
			orderContractDataMapper.insertOrderContractData(orderContractDataDto);
		}
		return 1;
	}

	/**
	 * 查询合同内容,返回data对象
	 */
	@Override
	public OrderContractDataDto selectOrderContractDataDto(
			OrderContractDataDto orderContractDataDto) {
		orderContractDataDto = orderContractDataMapper.selectOrderContractDataDto(orderContractDataDto);
		if(orderContractDataDto!=null){
			String dataStr = orderContractDataDto.getDataStr();
			JSONObject json=JSONObject.fromObject(dataStr);
			Map<String, Object> data = (Map)json;
			orderContractDataDto.setData(data);
		}
		return orderContractDataDto;
	}

	/**
	 * 光大信托贷款合同
	 * @return
	 */
	public int contract1(OrderContractDataDto orderContractDataDto,OrderBaseBorrowDto borrow,OrderBaseCustomerDto customer,OrderBaseHouseDto house,DocumentsDto documents){
		Map<String,Object> data = new HashMap<String, Object>();
		LoanContractDto contract = new LoanContractDto();
		contract.setCustomerName(customer.getCustomerName());
		contract.setCustomerCardNumber(customer.getCustomerCardNumber());
		contract.setCustomerWifeName(customer.getCustomerWifeName());
		contract.setCustomerWifeCardNumber(customer.getCustomerWifeCardNumber());
		contract.setCustomerSex(customer.getCustomerSex());
		
	
		List<OrderContractCustomerBorrowerDto> customerBorrowerDto = new ArrayList<OrderContractCustomerBorrowerDto>();
		OrderContractCustomerBorrowerDto customerBorrower = new OrderContractCustomerBorrowerDto();
		//借款人
		customerBorrower.setBorrowerName(customer.getCustomerName());
		customerBorrower.setBorrowerCardNumber(customer.getCustomerCardNumber());
		customerBorrower.setBorrowerPhone(borrow.getPhoneNumber());
		customerBorrowerDto.add(customerBorrower);
		//配偶
		if(StringUtil.isNotBlank(customer.getCustomerWifeName())){
			customerBorrower = new OrderContractCustomerBorrowerDto();
			customerBorrower.setBorrowerName(customer.getCustomerWifeName());
			customerBorrower.setBorrowerCardNumber(customer.getCustomerWifeCardNumber());
			customerBorrower.setBorrowerPhone(customer.getCustomerWifePhone());
			customerBorrowerDto.add(customerBorrower);
		}
		//产权人
		if(house.getOrderBaseHousePropertyPeopleDto()!=null&&house.getOrderBaseHousePropertyPeopleDto().size()>0){
			for (int i = 0; i < house.getOrderBaseHousePropertyPeopleDto().size(); i++) {
				String json = JsonUtil.BeanToJson(house.getOrderBaseHousePropertyPeopleDto().get(i));
				Map<String, Object> map;
				try {
					map = JsonUtil.jsonToMap(json);
					customerBorrower = new OrderContractCustomerBorrowerDto();
					//名称去重
					if(!MapUtils.getString(map, "propertyName","").equals(customer.getCustomerName())
							&&!MapUtils.getString(map, "propertyName","").equals(customer.getCustomerWifeName())){
						customerBorrower.setBorrowerName(MapUtils.getString(map, "propertyName",""));
						customerBorrower.setBorrowerCardNumber(MapUtils.getString(map, "propertyCardNumber",""));
						customerBorrower.setBorrowerPhone(MapUtils.getString(map, "propertyPhoneNumber",""));
						customerBorrowerDto.add(customerBorrower);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		//共同借款人
		if(customer.getCustomerBorrowerDto()!=null&&customer.getCustomerBorrowerDto().size()>0){
			for (int i = 0; i < customer.getCustomerBorrowerDto().size(); i++) {
				String json = JsonUtil.BeanToJson(customer.getCustomerBorrowerDto().get(i));
				Map<String, Object> map;
				try {
					map = JsonUtil.jsonToMap(json);
					boolean flag = true;
					for(OrderContractCustomerBorrowerDto cus : customerBorrowerDto){
						if(cus.getBorrowerName().equals(MapUtils.getString(map, "borrowerName",""))){
							flag = false;
						}
					}
					if(flag){
						customerBorrower = new OrderContractCustomerBorrowerDto();
						customerBorrower.setBorrowerName(MapUtils.getString(map, "borrowerName",""));
						customerBorrower.setBorrowerCardNumber(MapUtils.getString(map, "borrowerCardNumber",""));
						customerBorrower.setBorrowerPhone(MapUtils.getString(map, "borrowerPhone",""));
						customerBorrowerDto.add(customerBorrower);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		contract.setCustomerBorrowerDto(customerBorrowerDto);
		contract.setPhoneNumber(borrow.getPhoneNumber());
		contract.setBorrowingAmount(borrow.getLoanAmount()*10000);
		contract.setBorrowingDays(borrow.getBorrowingDays());
		if(null != documents){
			//出款卡
			ForeclosureTypeDto foreclosureTypeDto = documents.getForeclosureType();
			if(null != foreclosureTypeDto){
				contract.setBankCardMaster(foreclosureTypeDto.getBankCardMaster());
				contract.setBankNo(foreclosureTypeDto.getBankNo());
				contract.setBankNameId(foreclosureTypeDto.getBankNameId());
				contract.setBankSubNameId(foreclosureTypeDto.getBankSubNameId());
				contract.setBankName(foreclosureTypeDto.getBankName());
				contract.setBankSubName(foreclosureTypeDto.getBankSubName());
			}
			//回款卡
			PaymentTypeDto paymentTypeDto = documents.getPaymentType();
			if(null != paymentTypeDto){
				contract.setPaymentBankCardName(paymentTypeDto.getPaymentBankCardName());
				contract.setPaymentBankNumber(paymentTypeDto.getPaymentBankNumber());
				contract.setPaymentBankNameId(paymentTypeDto.getPaymentBankNameId());
				contract.setPaymentBankName(paymentTypeDto.getPaymentBankName());
				contract.setPaymentBankSubNameId(paymentTypeDto.getPaymentBankSubNameId());
				contract.setPaymentBankSubName(paymentTypeDto.getPaymentBankSubName());
			}
		}
		data = BeanToMapUtil.beanToMap(contract);
		orderContractDataDto.setData(data);
		orderContractDataDto.setTblName("tbl_contract_trust_loan");
		updateOrderContractData(orderContractDataDto);
		return 1;
	}
	
	/**
	 * 通用合同
	 * @return
	 */
	public int contract2(OrderContractDataDto orderContractDataDto,OrderBaseBorrowDto borrow,OrderBaseCustomerDto customer,OrderBaseHouseDto house,DocumentsDto documents){
		//借款合同(合同2)
		Map<String,Object> data = new HashMap<String, Object>();
		LoanContractDto contract = new LoanContractDto();
		contract.setCustomerName(customer.getCustomerName());
		contract.setCustomerCardNumber(customer.getCustomerCardNumber());
		contract.setCustomerWifeName(customer.getCustomerWifeName());
		contract.setCustomerWifeCardNumber(customer.getCustomerWifeCardNumber());
		contract.setCustomerWifePhone(customer.getCustomerWifePhone());
		contract.setCustomerSex(customer.getCustomerSex());
		List<OrderContractPropertyPeopleDto> propertyPeopleDtoList = new ArrayList<OrderContractPropertyPeopleDto>();
		if(house.getOrderBaseHousePropertyPeopleDto()!=null&&house.getOrderBaseHousePropertyPeopleDto().size()>0){
			OrderContractPropertyPeopleDto propertyPeopleDto = new OrderContractPropertyPeopleDto();
			for (int i = 0; i < house.getOrderBaseHousePropertyPeopleDto().size(); i++) {
				String json = JsonUtil.BeanToJson(house.getOrderBaseHousePropertyPeopleDto().get(i));
				Map<String, Object> map;
				try {
					map = JsonUtil.jsonToMap(json);
					propertyPeopleDto = new OrderContractPropertyPeopleDto();
					propertyPeopleDto.setPropertyName(MapUtils.getString(map, "propertyName",""));
					propertyPeopleDto.setPropertyPhoneNumber(MapUtils.getString(map, "propertyPhoneNumber",""));
					propertyPeopleDto.setPropertyCardNumber(MapUtils.getString(map, "propertyCardNumber",""));
					propertyPeopleDtoList.add(propertyPeopleDto);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		contract.setPropertyPeopleDto(propertyPeopleDtoList);
		List<OrderContractCustomerBorrowerDto> customerBorrowerDto = new ArrayList<OrderContractCustomerBorrowerDto>();
		if(customer.getCustomerBorrowerDto()!=null&&customer.getCustomerBorrowerDto().size()>0){
			OrderContractCustomerBorrowerDto customerBorrower = new OrderContractCustomerBorrowerDto();
			for (int i = 0; i < customer.getCustomerBorrowerDto().size(); i++) {
				String json = JsonUtil.BeanToJson(customer.getCustomerBorrowerDto().get(i));
				Map<String, Object> map;
				try {
					map = JsonUtil.jsonToMap(json);
					customerBorrower = new OrderContractCustomerBorrowerDto();
					customerBorrower.setBorrowerName(MapUtils.getString(map, "borrowerName",""));
					customerBorrower.setBorrowerCardNumber(MapUtils.getString(map, "borrowerCardNumber",""));
					customerBorrower.setBorrowerPhone(MapUtils.getString(map, "borrowerPhone",""));
					customerBorrowerDto.add(customerBorrower);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		contract.setCustomerBorrowerDto(customerBorrowerDto);
		contract.setPhoneNumber(borrow.getPhoneNumber());
		contract.setBorrowingAmount(borrow.getLoanAmount()*10000);
		if(null != documents){
			//出款卡
			ForeclosureTypeDto foreclosureTypeDto = documents.getForeclosureType();
			if(null != foreclosureTypeDto){
				contract.setBankCardMaster(foreclosureTypeDto.getBankCardMaster());
				contract.setBankNo(foreclosureTypeDto.getBankNo());
				contract.setBankNameId(foreclosureTypeDto.getBankNameId());
				contract.setBankSubNameId(foreclosureTypeDto.getBankSubNameId());
				contract.setBankName(foreclosureTypeDto.getBankName());
				contract.setBankSubName(foreclosureTypeDto.getBankSubName());
			}
			//回款卡
			PaymentTypeDto paymentTypeDto = documents.getPaymentType();
			if(null != paymentTypeDto){
				contract.setPaymentBankCardName(paymentTypeDto.getPaymentBankCardName());
				contract.setPaymentBankNumber(paymentTypeDto.getPaymentBankNumber());
				contract.setPaymentBankNameId(paymentTypeDto.getPaymentBankNameId());
				contract.setPaymentBankName(paymentTypeDto.getPaymentBankName());
				contract.setPaymentBankSubNameId(paymentTypeDto.getPaymentBankSubNameId());
				contract.setPaymentBankSubName(paymentTypeDto.getPaymentBankSubName());
			}
		}
		OrderListDto orderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderContractDataDto, OrderListDto.class);
		List<ReceivableForDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/finance/receivableFor/v/detail", orderContractDataDto, ReceivableForDto.class);
		//放款时间
		contract.setLendingTime(orderListDto.getLendingTime());
		//实际回款日期
		if(list!=null&&list.size()==1){
			contract.setPayMentAmountDate(list.get(0).getPayMentAmountDate());
		}else if(list!=null&&list.size()>1){
			contract.setPayMentAmountDate(list.get(list.size()-1).getPayMentAmountDate());
		}
		
		data = BeanToMapUtil.beanToMap(contract);
		orderContractDataDto.setData(data);
		orderContractDataDto.setTblName("tbl_contract_bussiness_application");
		updateOrderContractData(orderContractDataDto);
		return 1;
	}
	
	@Override
	public int delete(OrderContractDataDto orderContractDataDto) {
		return orderContractDataMapper.delete(orderContractDataDto);
	}
	
	/**
	 * 共同借款人集合
	 * @return
	 */
	@Override
	public List<OrderContractCustomerBorrowerDto> queryContractCustomerBorrow(OrderContractDataDto orderContractDataDto){
		orderContractDataDto.setTblName("tbl_contract_bussiness_application");
		OrderContractDataDto contractDataDto = selectOrderContractDataDto(orderContractDataDto);
		Map<String,Object> map = contractDataDto.getData();
		List<OrderContractCustomerBorrowerDto> customerBorrowerDto = new ArrayList<OrderContractCustomerBorrowerDto>();
		OrderContractCustomerBorrowerDto customerBorrower = new OrderContractCustomerBorrowerDto();
		//借款人
		customerBorrower.setBorrowerName(MapUtils.getString(map, "customerName"));
		customerBorrower.setBorrowerCardNumber(MapUtils.getString(map, "customerCardNumber"));
		customerBorrowerDto.add(customerBorrower);
		//配偶
		if(StringUtil.isNotBlank(MapUtils.getString(map, "customerWifeName"))&&StringUtil.isNotBlank(MapUtils.getString(map, "customerWifeCardNumber"))){  
			customerBorrower = new OrderContractCustomerBorrowerDto();
			customerBorrower.setBorrowerName(MapUtils.getString(map, "customerWifeName"));
			customerBorrower.setBorrowerCardNumber(MapUtils.getString(map, "customerWifeCardNumber"));
			customerBorrowerDto.add(customerBorrower);
		}
		//产权人
		List<OrderContractPropertyPeopleDto> propertyPeopleDto = (List<OrderContractPropertyPeopleDto>) MapUtils.getObject(map, "propertyPeopleDto");
		if(propertyPeopleDto!=null&&propertyPeopleDto.size()>0){
			for (int i = 0 ; i<propertyPeopleDto.size() ;i++) {
				String json = JsonUtil.BeanToJson(propertyPeopleDto.get(i));
				Map<String, Object> map2;
				try {
					map2 = JsonUtil.jsonToMap(json);
					customerBorrower = new OrderContractCustomerBorrowerDto();
					//名称去重
					if(StringUtil.isNotBlank(MapUtils.getString(map2, "propertyName",""))&&!MapUtils.getString(map2, "propertyName","").equals(MapUtils.getString(map, "customerName"))
							&&!MapUtils.getString(map2, "propertyName","").equals(MapUtils.getString(map, "customerWifeName"))){
						customerBorrower.setBorrowerName(MapUtils.getString(map2, "propertyName",""));
						customerBorrower.setBorrowerCardNumber(MapUtils.getString(map2, "propertyCardNumber",""));
						customerBorrowerDto.add(customerBorrower);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		//共同借款人
		List<ContractCustomerBorrowerDto> customerBorrowerDtoT = (List<ContractCustomerBorrowerDto>) MapUtils.getObject(map, "customerBorrowerDto"); 
		if(customerBorrowerDtoT!=null&&customerBorrowerDtoT.size()>0){
			for (int i = 0; i < customerBorrowerDtoT.size(); i++) {
				String json = JsonUtil.BeanToJson(customerBorrowerDtoT.get(i));
				Map<String, Object> map2;
				try {
					map2 = JsonUtil.jsonToMap(json);
					boolean flag = true;
					for(OrderContractCustomerBorrowerDto cus : customerBorrowerDto){
						if(cus.getBorrowerName().equals(MapUtils.getString(map, "borrowerName",""))){
							flag = false;
						}
					}
					if(flag&&StringUtil.isNotBlank(MapUtils.getString(map2, "borrowerName",""))){
						customerBorrower = new OrderContractCustomerBorrowerDto();
						customerBorrower.setBorrowerName(MapUtils.getString(map2, "borrowerName",""));
						customerBorrower.setBorrowerCardNumber(MapUtils.getString(map2, "borrowerCardNumber",""));
						customerBorrowerDto.add(customerBorrower);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		return customerBorrowerDto;
	}
}
