/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.data.PageFlowDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.PageFlowMapper;
import com.anjbo.service.PageFlowService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:06
 * @version 1.0
 */
@Service
public class PageFlowServiceImpl extends BaseServiceImpl<PageFlowDto>  implements PageFlowService {
	
	@Autowired private PageFlowMapper pageFlowMapper;
	
	@Resource private UserApi userApi;
	
	@Resource private DataApi dataApi;
	
	@Override
	public List<PageFlowDto> search(PageFlowDto dto) {
		List<PageFlowDto> list =super.search(dto);
		String productId = "104003100";
		List<ProcessDto> processDtoList = dataApi.findProcessDto(Integer.parseInt(productId));
		for (PageFlowDto pageFlowDto : list) {
			for (ProcessDto processDto : processDtoList) {
				if(processDto.getProcessId().equals(pageFlowDto.getCurrentProcessId())) {
					pageFlowDto.setCurrentProcessName(processDto.getProcessName());
				}
				if(processDto.getProcessId().equals(pageFlowDto.getNextProcessId())) {
					pageFlowDto.setNextProcessName(processDto.getProcessName());
				}
			}
			pageFlowDto.setHandleName(userApi.findUserDtoByUid(pageFlowDto.getHandleUid()).getName());
		}
		return list;
	}
	
}
