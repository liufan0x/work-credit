/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.ProcessDto;
import com.anjbo.dao.ProcessMapper;
import com.anjbo.service.ProcessService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 09:49:33
 * @version 1.0
 */
@Service
public class ProcessServiceImpl extends BaseServiceImpl<ProcessDto>  implements ProcessService {
	
	@Autowired private ProcessMapper processMapper;

	@Override
	public ProcessDto getNextProcess(Map<String, Object> params) {
		int productId = Integer.parseInt(MapUtils.getString(params, "cityCode","") + MapUtils.getString(params, "productCode",""));
		String processId = MapUtils.getString(params, "processId","");
		ProcessDto dto = new ProcessDto();
		dto.setProductId(productId);
		List<ProcessDto> processDtos = search(dto);
		
		Iterator<ProcessDto> iterator = processDtos.iterator();
		while (iterator.hasNext()) {
			ProcessDto processDto = iterator.next();
			if(processDto.getProcessId().equals(processId)) {
				return iterator.next();
			}
		}
		return null;
	}
	
}
