package com.anjbo.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.customer.FundAdminDto;
import com.anjbo.bean.customer.FundCostDiscountDto;
import com.anjbo.bean.customer.FundCostDto;
import com.anjbo.dao.FundAdminMapper;
import com.anjbo.dao.FundCostDiscountMapper;
import com.anjbo.dao.FundCostMapper;
import com.anjbo.service.FundAdminService;

@Transactional
@Service
public class FundAdminServiceImpl implements FundAdminService{

	@Resource
	private FundAdminMapper FundAdminMapper;
	@Resource
	private FundCostDiscountMapper fundCostDiscountMapper;
	@Resource
	private FundCostMapper fundCostMapper;
	
	@Override
	public List<FundAdminDto> list(FundAdminDto obj){
		return FundAdminMapper.list(obj);
	}
	
	@Override
	public FundAdminDto detail(FundAdminDto obj){
		return FundAdminMapper.detail(obj);
	}

	@Override
	public List<FundAdminDto> listRelation(FundAdminDto obj) {
		List<FundAdminDto> adminList =  FundAdminMapper.list(obj);
		List<FundCostDiscountDto> discountList = fundCostDiscountMapper.list();
		List<FundCostDto> costList = fundCostMapper.list();
		for(FundCostDto cost:costList){
			Iterator<FundCostDiscountDto> it = discountList.iterator();
			while(it.hasNext()){
				FundCostDiscountDto tmp = it.next();
				if(cost.getId()==tmp.getFundCostId()){
					cost.setFundCostDiscountDto(tmp);
					it.remove();
					continue;
				}
			}
			if(null==discountList||discountList.size()<=0){
				break;
			}
		}
		for(FundAdminDto admin:adminList){
			Iterator<FundCostDto> costIt = costList.iterator();
			while(costIt.hasNext()){
				FundCostDto tmp = costIt.next();
				if(admin.getId()==tmp.getFundId()){
					admin.setFundCostDto(tmp);
					costIt.remove();
					continue;
				}
			}
			if(null==costList||costList.size()<=0){
				break;
			}
		}
		
		return adminList;
	}
	
	public List<Map<String,Object>> selectFundsByProductId(Map<String,Object> map){
		return FundAdminMapper.selectFundsByProductId(map);
	}
}
