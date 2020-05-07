package com.anjbo.dao.cm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.cm.AssessResultDto;


public interface AssessResultMapper {
	/**
	 * 新增评估反馈结果
	 * @param assessResultDto
	 * @return
	 */
    int addAssessResult(AssessResultDto assessResultDto);
    /**
     * 查询评估结果列表
     * @param orderNo
     * @return
     */
    List<AssessResultDto> selectAssessResultList(@Param("orderNo")String orderNo);
}