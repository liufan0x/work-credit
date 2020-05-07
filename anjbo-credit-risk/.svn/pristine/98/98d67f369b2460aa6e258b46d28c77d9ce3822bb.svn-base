package com.anjbo.service.impl;

import com.anjbo.common.Constants;
import com.anjbo.dao.RiskBaseMapper;
import com.anjbo.service.RiskBaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskBaseServiceImpl implements RiskBaseService {
	
	@Resource
	private RiskBaseMapper riskBaseMapper;
	
	public List<Map<String,Object>> listImg(Map<String,Object> map){
		return riskBaseMapper.listImg(map);
	}
	
	public List<Map<String,Object>> deleteImgById(Map<String,Object> map){
		riskBaseMapper.deleteImgById(map);

		String imgType = MapUtils.getString(map, "imgType");
		String orderNo = MapUtils.getString(map, "orderNo");
		Map<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("imgType",imgType);
		tmp.put("orderNo",orderNo);
		List<Map<String,Object>> imsg = riskBaseMapper.listImg(tmp);
		return imsg;
	}
	
	public List<Map<String,Object>> insertImg(Map<String,Object> map){
		String imgType = MapUtils.getString(map, "imgType");
		String orderNo = MapUtils.getString(map, "orderNo");
		String imgList = MapUtils.getString(map, "imgUrl");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp = null;
		if(StringUtils.isNotBlank(imgList)){
			imgList = imgList.replaceAll(";",",");
			String[] arr = imgList.split(Constants.IMG_SEPARATE);
			for(String img:arr){
				tmp = new HashMap<String,Object>();
				tmp.put("imgType", imgType);
				tmp.put("orderNo", orderNo);
				tmp.put("imgUrl", img);
				list.add(tmp);
			}
		}
		
		if(null!=list&&list.size()>0){
			riskBaseMapper.insertImg(list);
		}

		tmp = new HashMap<String,Object>();
		tmp.put("imgType",imgType);
		tmp.put("orderNo",orderNo);
		List<Map<String,Object>> imsg = riskBaseMapper.listImg(tmp);
		return imsg;
	}
	
}
