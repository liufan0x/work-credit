/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseReceivableForDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.order.BaseBorrowMapper;
import com.anjbo.dao.order.BaseReceivableForMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.order.BaseBorrowService;

import net.sf.json.JSONObject;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:44
 * @version 1.0
 */
@Service
public class BaseBorrowServiceImpl extends BaseServiceImpl<BaseBorrowDto>  implements BaseBorrowService {
	@Autowired private BaseBorrowMapper baseBorrowMapper;
	@Autowired private BaseReceivableForMapper baseReceivableForMapper;
	@Override
	public BaseBorrowDto find(BaseBorrowDto dto) {
		String orderNo = dto.getOrderNo();
		BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
		baseBorrowDto = baseBorrowMapper.find(dto);		
		// 计划回款时间
		BaseReceivableForDto baseReceivableFor = new BaseReceivableForDto();
		List<BaseReceivableForDto> baseReceivableForList = baseReceivableForMapper
				.search(baseReceivableFor);
		if (baseBorrowDto == null) {
			baseBorrowDto = new BaseBorrowDto();
		}
		baseBorrowDto.setBaseReceivableForDto(baseReceivableForList);
		//查询债务置换贷款按天按段
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("riskGradeId", baseBorrowDto.getRiskGradeId());
//		map.put("cooperativeAgencyId", baseBorrowDto.getCooperativeAgencyId());
//		map.put("borrowingDays", baseBorrowDto.getBorrowingDays());
//		map.put("loanAmount", baseBorrowDto.getLoanAmount());
//		map.put("productId", baseBorrowDto.getCityCode()+baseBorrowDto.getProductCode());
//		JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "/credit/customer/risk/v/findStageRate", map);
//		if(json!=null){
//			JSONObject data = json.getJSONObject("data");
//			if(data!=null&&!data.isNullObject()){
//				Object obj=data.get("modeid");
//				if((data!=null)&&obj instanceof  Integer){
//					int modeid = data.getInt("modeid");
//					baseBorrowDto.setModeid(modeid);
//				}
//			}
//		}
		return baseBorrowDto;
	}
	
	/**
	 * 给订单列表字段赋值
	 * 
	 * @param BaseBorrowDto
	 * @return
	 */
	public BaseBorrowDto getBaseBorrowDto(BaseBorrowDto baseBorrowDto){
		
//		if(null != baseBorrowDto.getChannelManagerUid()){
//			UserDto channelManagerDto = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid());
//			baseBorrowDto.setChannelManagerName(channelManagerDto.getName());
//			baseBorrowDto.setCurrentHandler(channelManagerDto.getName());
//		}
//		baseBorrowDto.setAcceptMemberName(CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid()).getName());
//		if(StringUtil.isNotEmpty(baseBorrowDto.getForeclosureMemberUid())){
//			baseBorrowDto.setForeclosureMemberName(CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getForeclosureMemberUid()).getName());
//		}
//		baseBorrowDto.setNotarialName(CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getNotarialUid()).getName());
//		baseBorrowDto.setFacesignName(CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getFacesignUid()).getName());
//		baseBorrowDto.setElementName(CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getElementUid()).getName());
//		
//		//获取所有城市
//		baseBorrowDto.setCityName(CommonDataUtil.getDictDtoByTypeAndCode("bookingSzAreaOid", baseBorrowDto.getCityCode()).getName());
//		
//		if(StringUtil.isBlank(baseBorrowDto.getProductName()) && StringUtil.isNotEmpty(baseBorrowDto.getCityCode()) && StringUtil.isNotEmpty(baseBorrowDto.getProductCode())){
//			baseBorrowDto.setProductName(CommonDataUtil.getProductDtoByProductId(baseBorrowDto.getCityCode() + baseBorrowDto.getProductCode()).getProductName());
//		}
//		
//		//获取合作机构名称
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
//		HttpUtil httpUtil = new HttpUtil();
//		AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
//		if(agencyDto!=null){
//			baseBorrowDto.setCooperativeAgencyName(agencyDto.getName());
//		}
//		//受理员机构名称
//		map.put("agencyId", baseBorrowDto.getAgencyId());
//		agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
//		if(agencyDto!=null){
//			baseBorrowDto.setAgencyName(agencyDto.getName());
//		}
//		//收费类型名称
//		List<DictDto> dicts = CommonDataUtil.getDictDtoByType("riskControl");
//		if(dicts!=null&&dicts.size()>0){
//			for (DictDto dictDto : dicts) {
//				if(dictDto.getCode().equals(String.valueOf(baseBorrowDto.getRiskGradeId()))){
//					baseBorrowDto.setRiskGrade(dictDto.getName());
//				}
//				if(baseBorrowDto.getRiskGradeId()!=null&&baseBorrowDto.getRiskGradeId()==0){
//					baseBorrowDto.setRiskGrade("其他");
//				}
//			}
//		}
//		if(baseBorrowDto.getIsOldLoanBank() != null && baseBorrowDto.getIsOldLoanBank()==1){
//			baseBorrowDto.setOldLoanBankName(CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId()).getName());
//			baseBorrowDto.setOldLoanBankSubName(CommonDataUtil.getSubBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName());
//		}
//		if(baseBorrowDto.getIsLoanBank() != null && baseBorrowDto.getIsLoanBank()==1){
//			baseBorrowDto.setLoanBankName(CommonDataUtil.getBankNameById(baseBorrowDto.getLoanBankNameId()).getName());
//			baseBorrowDto.setLoanSubBankName(CommonDataUtil.getSubBankNameById(baseBorrowDto.getLoanSubBankNameId()).getName());
//		}
//		if(baseBorrowDto.getIsRebate() != null && baseBorrowDto.getIsRebate()==1){
//			baseBorrowDto.setRebateBank(CommonDataUtil.getBankNameById(baseBorrowDto.getRebateBankId()).getName());
//			baseBorrowDto.setRebateSubBank(CommonDataUtil.getSubBankNameById(baseBorrowDto.getRebateBankSubId()).getName());
//		}
		//1：费用前置  2：费用后置
		if(baseBorrowDto.getPaymentMethod()==1){
			baseBorrowDto.setPaymentMethodName("费用前置");
		}else if(baseBorrowDto.getPaymentMethod()==2){
			baseBorrowDto.setPaymentMethodName("费用后置");
		}
		return baseBorrowDto;
	}

	/**
	 * 查询出债务置换贷款订单以及关联业务订单借款信息
	 */ 
	/*@Override
	public BaseBorrowDto selectOrderBorrowByOrderNo(String orderNo) {
		BaseBorrowDto orderBaseBorrowDto = new BaseBorrowDto();
		orderBaseBorrowDto = baseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);		
		// 计划回款时间
		List<BaseReceivableForDto> orderBaseReceivableForList = baseReceivableForMapper
				.selectOrderReceivableForByOrderNo(orderNo);
		if (orderBaseBorrowDto == null) {
			orderBaseBorrowDto = new BaseBorrowDto();
		}
		orderBaseBorrowDto.setBaseReceivableForDto(orderBaseReceivableForList);
		//查询债务置换贷款按天按段
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("riskGradeId", orderBaseBorrowDto.getRiskGradeId());
		map.put("cooperativeAgencyId", orderBaseBorrowDto.getCooperativeAgencyId());
		map.put("borrowingDays", orderBaseBorrowDto.getBorrowingDays());
		map.put("loanAmount", orderBaseBorrowDto.getLoanAmount());
		map.put("productId", orderBaseBorrowDto.getCityCode()+orderBaseBorrowDto.getProductCode());
		JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "/credit/customer/risk/v/findStageRate", map);
		if(json!=null){
			JSONObject data = json.getJSONObject("data");
			if(data!=null&&!data.isNullObject()){
				Object obj=data.get("modeid");
				if((data!=null)&&obj instanceof  Integer){
					int modeid = data.getInt("modeid");
					orderBaseBorrowDto.setModeid(modeid);
				}
			}
		}
		return orderBaseBorrowDto;
	}*/
}
