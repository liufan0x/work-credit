/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseHouseDto;
import com.anjbo.bean.order.BaseHousePropertyDto;
import com.anjbo.bean.order.BaseHousePropertypeopleDto;
import com.anjbo.bean.order.BaseHousePurchaserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.dao.order.BaseBorrowMapper;
import com.anjbo.dao.order.BaseHouseMapper;
import com.anjbo.dao.order.BaseHousePropertyMapper;
import com.anjbo.dao.order.BaseHousePropertypeopleMapper;
import com.anjbo.dao.order.BaseHousePurchaserMapper;
import com.anjbo.dao.order.BaseListMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.order.BaseHouseService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@Service
public class BaseHouseServiceImpl extends BaseServiceImpl<BaseHouseDto>  implements BaseHouseService {
	@Autowired private BaseHouseMapper baseHouseMapper;
	@Autowired private BaseListMapper baseListMapper;
	@Autowired private BaseBorrowMapper baseBorrowMapper;
	@Autowired private BaseHousePropertyMapper baseHousePropertyMapper;
	@Autowired private BaseHousePurchaserMapper baseHousePurchaserMapper;
	@Autowired private BaseHousePropertypeopleMapper baseHousePropertypeopleMapper;
	@Override
	public BaseHouseDto find(BaseHouseDto dto) {
		String orderNo = dto.getOrderNo();
		BaseListDto baseListDto = new BaseListDto();
		baseListDto.setOrderNo(dto.getOrderNo());
		baseListDto = baseListMapper.find(baseListDto);
		BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
		if("03".equals(baseListDto.getProductCode())&&StringUtil.isNotBlank(baseListDto.getRelationOrderNo())){
			orderNo = baseListDto.getRelationOrderNo();
		}
		BaseHouseDto baseHouseDto = new BaseHouseDto();
		baseHouseDto.setOrderNo(orderNo);
		baseHouseDto = baseHouseMapper.find(baseHouseDto);
	    if(baseHouseDto!=null){
	    	//房产信息
			BaseHousePropertyDto baseHousePropertyDto = new BaseHousePropertyDto();
			baseHousePropertyDto.setOrderNo(orderNo);
			baseHouseDto.setBaseHousePropertyDto(baseHousePropertyMapper.search(baseHousePropertyDto));
		    //买房人信息
			BaseHousePurchaserDto baseHousePurchaserDto = new BaseHousePurchaserDto();
			baseHousePurchaserDto.setOrderNo(orderNo);
			baseHouseDto.setBaseHousePurchaserDto(baseHousePurchaserMapper.search(baseHousePurchaserDto));
		    //产权人信息
			BaseHousePropertypeopleDto baseHousePropertypeopleDto = new BaseHousePropertypeopleDto();
			baseHousePropertypeopleDto.setOrderNo(orderNo);
		    baseHouseDto.setBaseHousePropertypeopleDto(baseHousePropertypeopleMapper.search(baseHousePropertypeopleDto));
	    }
		//查询借款信息中的附加信息
	    baseBorrowDto.setOrderNo(orderNo);
	    baseBorrowDto = baseBorrowMapper.find(baseBorrowDto);
	    baseHouseDto = getOrderBaseHouseDto(baseHouseDto,baseBorrowDto);
	    return baseHouseDto;
	}
	
	/**
	 * 附加借款信息
	 * @param orderBaseHouseDto
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public BaseHouseDto getOrderBaseHouseDto(BaseHouseDto orderBaseHouseDto,BaseBorrowDto orderBaseBorrowDto){
		/*if(orderBaseHouseDto!=null){
			orderBaseHouseDto.setIsOldLoanBank(orderBaseBorrowDto.getIsOldLoanBank());
			orderBaseHouseDto.setIsLoanBank(orderBaseBorrowDto.getIsLoanBank());
		}
		if((orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1)||(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1)){
			//查询银行名称
				if(orderBaseBorrowDto.getOldLoanBankNameId()!=null){
					orderBaseHouseDto.setOldLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanBankNameId()!=null){
					orderBaseHouseDto.setLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getOldLoanBankSubNameId()!=null){
					orderBaseHouseDto.setOldLoanBankSubName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getOldLoanBankSubNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanSubBankNameId()!=null){
					orderBaseHouseDto.setLoanSubBankName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getLoanSubBankNameId()).getName());
				}
			orderBaseHouseDto.setOldLoanBankManager(orderBaseBorrowDto.getOldLoanBankManager());
			orderBaseHouseDto.setOldLoanBankManagerPhone(orderBaseBorrowDto.getOldLoanBankManagerPhone());
			orderBaseHouseDto.setLoanBankNameManager(orderBaseBorrowDto.getLoanBankNameManager());
			orderBaseHouseDto.setLoanBankNameManagerPhone(orderBaseBorrowDto.getLoanBankNameManagerPhone());
		}
		if(orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==2){
			orderBaseHouseDto.setOldLoanBankName(orderBaseBorrowDto.getOldLoanBankName());
		}
		if(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==2){
			orderBaseHouseDto.setLoanBankName(orderBaseBorrowDto.getLoanBankName());
		}
		//获取所有城市
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		if(orderBaseHouseDto!=null&&orderBaseHouseDto.getOrderBaseHousePropertyDto()!=null){
			List<OrderBaseHousePropertyDto> housePropertyList = orderBaseHouseDto.getOrderBaseHousePropertyDto();
			List<OrderBaseHousePropertyDto> housePropertyListNew = new ArrayList<OrderBaseHousePropertyDto>();
			for (OrderBaseHousePropertyDto orderBaseHousePropertyDto : housePropertyList) {
				for (DictDto dictDto : dictList) {
					if(dictDto.getCode().equals(orderBaseHousePropertyDto.getCity())){
						orderBaseHouseDto.setCityName(dictDto.getName());
						orderBaseHousePropertyDto.setCityName(dictDto.getName());
					}
					if(dictDto.getCode().equals(orderBaseHousePropertyDto.getHouseRegion())&&dictDto.getPcode().equals(orderBaseHousePropertyDto.getCity())){
						orderBaseHousePropertyDto.setHouseRegionName(dictDto.getName());
					}
				}
				housePropertyListNew.add(orderBaseHousePropertyDto);
			}
			orderBaseHouseDto.setOrderBaseHousePropertyDto(housePropertyListNew);
		}*/
		
		return orderBaseHouseDto;
	}
}
