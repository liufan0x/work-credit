package com.anjbo.service.impl;

import com.alibaba.fastjson.JSON;
import com.anjbo.bean.customer.FundAdminDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.order.*;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.HuaanDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.AllocationFundController;
import com.anjbo.dao.AllocationFundMapper;
import com.anjbo.dao.HuaanMapper;
import com.anjbo.service.AllocationFundService;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.NumberUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 资金方
 * @author huanglj
 *
 */
@Transactional
@Service
public class AllocationFundServiceImpl implements AllocationFundService{

	@Resource
	private AllocationFundMapper allocationFundMapper;
	@Resource
	private HuaanMapper huaanMapper;
	
	public List<AllocationFundDto> listDetail(String orderNo){
		List<AllocationFundDto> list = allocationFundMapper.listDetail(orderNo);
		list = getFundList(list);
		return list;
	}
	
	public AllocationFundDto detail(int id){
		return allocationFundMapper.detail(id);
	}
	
	public int update(AllocationFundDto obj){
		return allocationFundMapper.update(obj);
	}
	
	public int insert(AllocationFundDto obj){
		return allocationFundMapper.insert(obj);
	}
	public int insert(List<AllocationFundDto> list){
		if(null==list||list.size()<=0){
			return 0;
		}
		allocationFundMapper.deleteByOrderNo(list.get(0).getOrderNo());
		return allocationFundMapper.insertBatch(list);
	}
	
	public List<AllocationFundDto> getFundList(List<AllocationFundDto> list){
		HttpUtil http = new HttpUtil();
		FundAdminDto tmpFund = new FundAdminDto();
		tmpFund.setStatus(100);
		List<FundAdminDto> result = http.getList(Constants.LINK_CREDIT, "/credit/customer/fund/v/list",tmpFund, FundAdminDto.class);
		if(null==result){
			return list;
		}
		List<AllocationFundDto> fTmp = new ArrayList<AllocationFundDto>();
		
		Iterator<FundAdminDto> admin = result.iterator();
		while(admin.hasNext()){
			FundAdminDto obj = admin.next();
			Iterator<AllocationFundDto> it = list.iterator();
			while(it.hasNext()){
				AllocationFundDto tmp = it.next();
				if(tmp.getFundId()==obj.getId()){
					tmp.setFundCode(obj.getFundCode());
					tmp.setFundDesc(obj.getFundDesc());
					fTmp.add(tmp);
					it.remove();
					continue;
				}
			}
			if(null==list||list.size()<=0){
				break;
			}
		}
		
		for(AllocationFundDto obj:list){
			if(!fTmp.contains(obj)){
				fTmp.add(obj);
			}
		}
		if(null==fTmp||fTmp.size()<=0){
			fTmp = list;
		}
		return fTmp;
	}
	
	@SuppressWarnings("unchecked")
	public HuaanDto loadPushOrder(HuaanDto huaanDto){
		
		HuaanDto obj = huaanMapper.detail(huaanDto.getOrderNo());
		if(null!=obj){
			Map<String,Object> bankMap = AllocationFundController.getBank(obj.getHouseLoanBankId(),obj.getHouseLoanSubBankId());
			obj.setHouseLoanBankName(MapUtils.getString(bankMap, "bankId", ""));
			obj.setHouseLoanSubBankName(MapUtils.getString(bankMap, "subBankId", ""));
			return obj;
		}
		String orderNo = AllocationFundController.getOrderNo(huaanDto.getOrderNo());//如果是畅贷的订单号是查不到订单信息,因为是关联的所以需要以债务置换的订单号
		
		obj = new HuaanDto();
		obj.setOrderNo(orderNo);
		obj.setCreateUid(huaanDto.getCreateUid());
		HttpUtil http = new HttpUtil();
		JSONObject jsons = http.getData(Constants.LINK_CREDIT, "/credit/order/borrow/v/allDetail", obj);
		Map<String,Object> allData = JSON.parseObject(jsons.toString(), Map.class);
		DocumentsDto doc = http.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", obj,DocumentsDto.class);
		
		obj.setOrderNo(huaanDto.getOrderNo());//恢复为原来的订单编号
		
		if(RespStatusEnum.SUCCESS.getCode().equals(MapUtils.getString(allData, "code", ""))){
			Object allObj = MapUtils.getObject(allData, "data");
			
			if(null!=allObj&&allObj instanceof Map){
				Map<String,Object> allMap = (Map<String,Object>)allObj;
				
				if(null==allMap||MapUtils.isEmpty(allMap)){
					return obj;
				}
				
				Object borrowObj = MapUtils.getObject(allMap, "borrow");
				if(null!=borrowObj){
					OrderBaseBorrowDto borrow = JSON.parseObject(borrowObj.toString(),OrderBaseBorrowDto.class);
					obj.setPhoneNumber(borrow.getPhoneNumber());
					obj.setServiceType(borrow.getProductCode());
					obj.setLoanAmount(borrow.getLoanAmount());
					obj.setHouseLoanBankId(null==borrow.getLoanBankNameId()?0:borrow.getLoanBankNameId());
					obj.setHouseLoanSubBankId(null==borrow.getLoanSubBankNameId()?0:borrow.getLoanSubBankNameId());
				}
				
				Object customerObj = MapUtils.getMap(allMap, "customer");
				if(null!=customerObj){
					OrderBaseCustomerDto customer = JSON.parseObject(customerObj.toString(),OrderBaseCustomerDto.class);
					obj.setCustomerName(customer.getCustomerName());
					obj.setIdCardNo(customer.getCustomerCardNumber());
					obj.setCustomerMarriageState(customer.getCustomerMarriageState());
					obj.setCustomerWifeName(customer.getCustomerWifeName());
					
					List<OrderBaseCustomerGuaranteeDto> guaranteeList = customer.getCustomerGuaranteeDto();
					if(null!=guaranteeList&&guaranteeList.size()>0){
						OrderBaseCustomerGuaranteeDto guarantee = guaranteeList.get(0);
						obj.setGuaranteeCardNumber(guarantee.getGuaranteeCardNumber());
						obj.setGuaranteeName(guarantee.getGuaranteeName());
						obj.setGuaranteeRelationship(guarantee.getGuaranteeRelationship());
					}
				}
				
				Object houseObj = MapUtils.getMap(allMap, "house");
				if(null!=houseObj){
					OrderBaseHouseDto house = JSON.parseObject(houseObj.toString(),OrderBaseHouseDto.class);
					obj.setOldHouseLoanBalance(house.getOldHouseLoanBalance());
					obj.setHouseSuperviseAmount(house.getHouseSuperviseAmount());
					obj.setHouseLoanAmount(house.getHouseLoanAmount());
					
					List<OrderBaseHousePropertyDto> list = house.getOrderBaseHousePropertyDto();
					if(null!=list&&list.size()>0){
						OrderBaseHousePropertyDto property = list.get(0);
						obj.setHouseName(property.getHouseName());
						obj.setHousePropertyNumber(property.getHousePropertyNumber());
					}
					List<OrderBaseHousePropertyPeopleDto> propertyList = house.getOrderBaseHousePropertyPeopleDto();
					if(null!=propertyList&&propertyList.size()>0){
						OrderBaseHousePropertyPeopleDto property = propertyList.get(0);
						obj.setPropertyName(property.getPropertyName());
						obj.setCusName(property.getPropertyName());
						obj.setPropertyCardNumber(property.getPropertyCardNumber());
						obj.setCertCode(property.getPropertyCardNumber());
						
					}
					List<OrderBaseHousePurchaserDto> purchaserList = house.getOrderBaseHousePurchaserDto();
					if(null!=purchaserList&&purchaserList.size()>0){
						OrderBaseHousePurchaserDto purchaser =  purchaserList.get(0);
						obj.setBuyName(purchaser.getBuyName());
						obj.setBuyCardNumber(purchaser.getBuyCardNumber());
					}
				}
				if(null!=doc&&null!=doc.getPaymentType()){
					PaymentTypeDto type =  doc.getPaymentType();
					obj.setPaymentBankCardName(type.getPaymentBankCardName());
					obj.setPaymentBankName(type.getPaymentBankName());
					obj.setPaymentBankNumber(type.getPaymentBankNumber());
				}
				huaanMapper.insert(obj);
			}
		}
		
		return obj;
	}

	/**
	 * 拼接已经选择的资金方代号
	 */
	public Map<String,Object> listFundByOrderNos(Map<String,Object> map){
		String orderNos = MapUtils.getString(map, "orderNo", "");

		List<AllocationFundDto> list = allocationFundMapper.listFundByOrderNos(orderNos);
		list = getFundList(list);
		
		Map<String,Object> tmp = new HashMap<String,Object>();
		Iterator<AllocationFundDto> it = list.iterator();
		String fundCode = "";
		String fundDesc = "";
		while(it.hasNext()){
			AllocationFundDto obj = it.next();
			if("".equals(MapUtils.getString(tmp, obj.getOrderNo(), ""))){
				fundCode = obj.getFundCode();
				fundDesc = obj.getFundDesc();
				tmp.put(obj.getOrderNo(), fundCode);
				tmp.put(obj.getOrderNo()+"desc", fundDesc);
			} else {
				fundCode = MapUtils.getString(tmp, obj.getOrderNo());
				fundDesc = MapUtils.getString(tmp, obj.getOrderNo()+"desc");
				fundCode = fundCode+","+obj.getFundCode();
				fundDesc = fundDesc+","+obj.getFundDesc();
				tmp.put(obj.getOrderNo(), fundCode);
				tmp.put(obj.getOrderNo()+"desc", fundDesc);
			}
		}
		return tmp;
	}
	/**
	 * 经理审批订单总计
	 * 已确认放款总计
	 * @return map key{auditCount=经理审批订单总计,lendingTotal=已确认放款总计}
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectLendingTotalAndAuditCount(){
		Map<String,Object> count = new HashMap<String,Object>();
		count.put("auditCount", 0);
		count.put("lendingTotal", 0);
		HttpUtil http = new HttpUtil();
		JSONObject json = http.getData(Constants.LINK_CREDIT, "/credit/finance/lending/v/orderNoList", null);
		RespData<String> resultList = JSON.parseObject(json.toString(), RespData.class);
		if(RespStatusEnum.SUCCESS.getCode().equals(resultList.getCode())
				&&null!=resultList.getData()){
			double total = allocationFundMapper.selectSuccessLendingTotal(resultList.getData());
			total = NumberUtil.divide(total, 10000d, 1);
			count.put("lendingTotal", total+"");
		}
		RespDataObject<Integer> resultInt = http.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/managerCount", null,Integer.class);
		if (RespStatusEnum.SUCCESS.getCode().equals(resultInt.getCode())
				&&null!=resultInt.getData()){
			count.put("auditCount",resultInt.getData()+"");
		}
		return count;
	}

	public int updateByOrderNo(AllocationFundDto obj){
		return allocationFundMapper.updateByOrderNo(obj);
	}
}
