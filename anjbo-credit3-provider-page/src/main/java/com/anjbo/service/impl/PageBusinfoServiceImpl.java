package com.anjbo.service.impl;

import com.anjbo.bean.data.PageBusinfoDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;
import com.anjbo.dao.PageBusinfoMapper;
import com.anjbo.service.PageBusinfoService;
import com.anjbo.service.PageConfigService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PageBusinfoServiceImpl implements PageBusinfoService {

	@Resource private PageBusinfoMapper pageBusinfoMapper;
	
	@Resource private PageConfigService pageConfigService;
	
	public List<PageBusinfoTypeDto> pageBusinfoConfig(Map<String, Object> params){
		PageBusinfoTypeDto pageBusinfoTypeDto = new PageBusinfoTypeDto();
		pageBusinfoTypeDto.setProductCode(MapUtils.getString(params, "productCode"));
		List<PageBusinfoTypeDto> businfoTypeDtos = pageConfigService.selectPageBusinfoConfig(pageBusinfoTypeDto);
		String orderNo = MapUtils.getString(params, "orderNo","");
		List<PageBusinfoDto> businfoDtos = null;
		if(StringUtils.isNotEmpty(orderNo)) {
			PageBusinfoDto pageBusinfoDto = new PageBusinfoDto();
			pageBusinfoDto.setOrderNo(MapUtils.getString(params, "orderNo"));
			businfoDtos = pageBusinfoMapper.selectPageBusinfo(pageBusinfoDto);
		}
		List<PageBusinfoTypeDto> tempList = new ArrayList<PageBusinfoTypeDto>();
		for (PageBusinfoTypeDto temp1 : businfoTypeDtos) {
			if(0 == temp1.getPid()) {
				List<PageBusinfoTypeDto> sonTypes = new ArrayList<PageBusinfoTypeDto>();
				for (PageBusinfoTypeDto temp2 : businfoTypeDtos) {
					if(temp1.getId().equals(temp2.getPid())) {
						if(StringUtils.isNotEmpty(orderNo)) {
							List<PageBusinfoDto> tempBusinfo = new ArrayList<PageBusinfoDto>();
							for (PageBusinfoDto pageBusinfoDto : businfoDtos) {
								if(pageBusinfoDto.getTypeId().equals(temp2.getId())) {
									tempBusinfo.add(pageBusinfoDto);
								}
							}
							temp2.setListMap(tempBusinfo);
						}
						sonTypes.add(temp2);
					}
				}
				temp1.setSonTypes(sonTypes);
				tempList.add(temp1);
			}
		}
		return tempList;
	}
	
	@Override
	public List<PageBusinfoDto> selectPageBusinfo(PageBusinfoDto pageBusinfoDto) {
		return pageBusinfoMapper.selectPageBusinfo(pageBusinfoDto);
	}
	
	@Override
	public int pageBusinfoSave(List<PageBusinfoDto> pageBusinfoDtos) {
//		List<PageBusinfoDto> businfoDtos = new ArrayList<PageBusinfoDto>();
//		for (String str : pageBusinfoDto.getur.split(",")) {
//			PageBusinfoDto businfoDto = new PageBusinfoDto();
//			businfoDto.setUid(MapUtils.getString(params, "uid"));
//			businfoDto.setOrderNo(MapUtils.getString(params, "orderNo"));
//			businfoDto.setTypeId(MapUtils.getInteger(params, "typeId"));
//			businfoDto.setIndex(MapUtils.getInteger(params, "index"));
//			businfoDto.setIsOrder(MapUtils.getInteger(params, "isOrder"));
//			businfoDto.setIsPs(MapUtils.getInteger(params, "isPs"));
//			businfoDto.setUrl(str);
//			businfoDtos.add(businfoDto);
//		}
		return pageBusinfoMapper.batchInsert(pageBusinfoDtos);
	}
	
	@Override
	public int pageBusinfoDelete(PageBusinfoDto pageBusinfoDto) {
		return pageBusinfoMapper.batchdelete(pageBusinfoDto);
	}
	
	@Override
	public int pageBusinfoUpdate(PageBusinfoDto pageBusinfoDto) {
		return pageBusinfoMapper.batchUpdate(pageBusinfoDto);
	}
	
}
