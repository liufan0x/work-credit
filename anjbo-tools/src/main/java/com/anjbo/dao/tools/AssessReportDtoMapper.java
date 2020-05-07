package com.anjbo.dao.tools;

import com.anjbo.bean.tools.AssessReportDto;


public interface AssessReportDtoMapper {

	/**
	 * 新增出评估报告信息
	 * @param assessReportDto
	 */
	void insertAssessReport(AssessReportDto assessReportDto);
	
	/**
	 * 查找评估报告信息
	 * @param id
	 * @return
	 */
	AssessReportDto findAssessReportDto(String orderNo);

	/**
	 * 修改出评估报告信息
	 * @param assessReportDto
	 */
	int updateAssessReport(AssessReportDto assessReportDto);
	
	/**
	 * 修改出评估报告流程
	 * @param orderNo
	 */
	int updateAssessReportProgressId(String orderNo, int progressId);
	
}
