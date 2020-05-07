package com.anjbo.service.impl;

import com.anjbo.bean.risk.EnquiryDto;
import com.anjbo.dao.EnquiryMapper;
import com.anjbo.dao.RiskBaseMapper;
import com.anjbo.service.EnquiryService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 询价
 * @author huanglj
 *
 */
@Transactional
@Service
public class EnquiryServiceImpl implements EnquiryService{

	@Resource
	private EnquiryMapper enquiryMapper;
	@Resource
	private RiskBaseMapper riskBaseMapper;
	
	public int insert(EnquiryDto dto){
		return enquiryMapper.insert(dto);
	}
	
	public int insertByMap(Map<String,Object> map){
		if(StringUtils.isNotBlank(MapUtils.getString(map, "oldEnquiryId", ""))){
			String orderNo = MapUtils.getString(map, "orderNo");
			String enquiryId = MapUtils.getString(map, "oldEnquiryId");
			Map<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("enquiryId", enquiryId);
			tmp.put("orderNo", orderNo);
			EnquiryDto dto = enquiryMapper.detail(tmp);
			if(null!=dto){
				return enquiryMapper.updateByOldEnquiryId(map);
			}
		}
		return enquiryMapper.insertByMap(map);
	}
	
	public List<EnquiryDto> listEnquiry(String orderNo){
		
		List<EnquiryDto> list = enquiryMapper.listEnquiry(orderNo);
		if(null==list||list.size()<=0){
			list = new ArrayList<EnquiryDto>();
			EnquiryDto tmp = new EnquiryDto();
			tmp.setOrderNo(orderNo);
			list.add(tmp);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("imgType", "enquiry");
		List<Map<String,Object>> imgs = riskBaseMapper.listImg(param);

		for(EnquiryDto obj:list){
			obj.setEnquiryImgs(imgs);
		}
		return list;
	}
	
	public int update(Map<String,Object> map){
		int success = enquiryMapper.updateByMap(map);
		return success;
	}
	
	public int update(EnquiryDto dto){
		return enquiryMapper.update(dto);
	}
	
	public int delete(EnquiryDto obj){
		return enquiryMapper.delete(obj);
	}
	
	public int updateByOrderNo(EnquiryDto obj){
		List<EnquiryDto> list = enquiryMapper.detailByOrderNo(obj.getOrderNo());
		if(null==list||list.size()<=0){
			return enquiryMapper.insert(obj);
		}
		return enquiryMapper.updateByOrderNo(obj);
	}
	public List<EnquiryDto> detailByOrderNo(String orderNo){
		return enquiryMapper.detailByOrderNo(orderNo);
	}
}
