/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.MonitorArchiveDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.dao.MonitorArchiveMapper;
import com.anjbo.service.MonitorArchiveService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:40:22
 * @version 1.0
 */
@Service
public class MonitorArchiveServiceImpl extends BaseServiceImpl<MonitorArchiveDto>  implements MonitorArchiveService {
	@Autowired private MonitorArchiveMapper monitorArchiveMapper;

	@Resource private DataApi dataApi;

	@Override
	public List<MonitorArchiveDto> search(MonitorArchiveDto dto) {
		List<MonitorArchiveDto> list = super.search(dto);
		List<DictDto> propertyTypes= dataApi.getDictDtoListByType("propertyType");
		List<ProductDto> productDtos = dataApi.getProductList();
		for (MonitorArchiveDto monitorArchiveDto : list) {
			for (DictDto propertyType : propertyTypes) {
				if(propertyType.getCode().equals(monitorArchiveDto.getEstateType()+"")) {
					monitorArchiveDto.setEstateTypeStr(propertyType.getName());
					break;
				}	
			}

			for (ProductDto productDto : productDtos) {
				if(productDto.getProductCode().equals(monitorArchiveDto.getQueryUsage())) {
					monitorArchiveDto.setQueryUsageStr(productDto.getProductName());
					break;
				}	
			}
		}

		return list;
	}


	@Override
	public MonitorArchiveDto find(MonitorArchiveDto dto) {
		MonitorArchiveDto monitorArchiveDto = super.find(dto);
		List<ProductDto> productDtos = dataApi.getProductList();
		for (ProductDto productDto: productDtos) {
			if(monitorArchiveDto.getQueryUsage().equals(productDto.getProductCode())){
				monitorArchiveDto.setQueryUsageStr(productDto.getProductName());
			}
		}
		
		List<Map<String, Object>> messages = new ArrayList<Map<String,Object>>();
		if(StringUtil.isNotEmpty(monitorArchiveDto.getMessage())){
			String cont[]=monitorArchiveDto.getMessage().split(";");
    		for (int i = 0; i < cont.length; i++) {
    			Map<String, Object> paramt=new HashMap<String, Object>();
    			String conts[]=cont[i].split("&");
    			//如果只有一条记录，那么复制一条
    			if(cont.length==1){
    				paramt.put("time", conts[0]);
    				paramt.put("cont", conts[1]);
    				messages.add(paramt);
    			}
    			//取数组中的第一条和最后一条记录
    			if(i==0||i==cont.length-1){
       				if(conts.length>1){
       					 paramt=new HashMap<String, Object>();
       					 if((i+1)==cont.length){
       						 paramt.put("time", DateUtils.dateToString(monitorArchiveDto.getUpdateTime(), DateUtils.FMT_TYPE1));
       					 }else{
       						 paramt.put("time", conts[0]);
       					 }
           				 paramt.put("cont", conts[1]);
           				messages.add(paramt);
       				 }
    			}
			}
		}
		
		monitorArchiveDto.setMessages(messages);
		
		return monitorArchiveDto;
	}

}
