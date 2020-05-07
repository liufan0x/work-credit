package com.anjbo.service.tools;

import java.util.Map;

import com.anjbo.bean.tools.AssessReportDto;
import com.anjbo.common.RespDataObject;


public interface AssessReportService {


	/**
	 * 新增出评估报告信息
	 * @param assessReportDto
	 * @param uid
	 */
	RespDataObject<AssessReportDto> insertAssessReport(Map<String,Object> param);
	
	/**
	 * 查找评估报告信息
	 * @param id
	 * @return
	 */
	AssessReportDto findAssessReportDto(String orderNo);
	
	/**
	 * 修改出评估报告流程
	 * @param orderNo
	 */
	int updateAssessReportProgressId(String orderNo, int progressId);
}
