package com.anjbo.dao.cm;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.cm.AssessDto;
import com.anjbo.bean.cm.AssessExtendDto;
import com.anjbo.bean.cm.AssessResultDto;
import com.anjbo.bean.cm.LoanAuditResultDto;

public interface AssessMapper {
	/**
	 * 新增评估
	 * @param assessDto
	 * @return
	 */
    int addAssess(Map<String,Object> map);
    /**
     * 查询评估列表
     * @param assessDto
     * @return
     */
    List<AssessResultDto> selectAssessList(AssessDto assessDto);
    /**
     * 查询评估数量
     * @param assessDto
     * @return
     */
    int selectAssessCount(AssessDto assessDto);
    /**
     * 根据评估申请编号查询订单编号，用于校验申请编号是否有效
     * @param appNo
     * @return
     */
    AssessDto selectOrderNoByAssessAppNo(String appNo);
    
    AssessDto selectOrderNoByAssessOrderNo(String orderNo);
    /**
     * 修改评估状态
     * @param assessDto
     * @return
     */
    int updateAssessStatus(AssessDto assessDto);
    /**
     * 根据订单编号查询评估信息
     * @param orderNo
     * @return
     */
    AssessResultDto selectAssessByOrderNo(String orderNo);
    
    /**
     * 根据订单编号查询app申请评估的一些数据
     * @param orderNo
     * @return
     */
    AssessExtendDto queryAssessExtendByOrderNo(String orderNo);
    
    /**
     * 修改appNo
     * @param assessDto
     * @return
     */
    void updateAssessAppNo(AssessDto assessDto);
    
    /**
     * 修改评估订单信息
     * @param assessDto
     * @return
     */
    void updateAssess(Map<String,Object> map);
    
    /**
     * 修改评估订单扩展信息
     * @param assessDto
     * @return
     */
    void updateAssessExtend(Map<String,Object> map);
    
    
    
    /**
     * 添加意见反馈
     * @return
     */
    int addResult(LoanAuditResultDto auditResultDto);
}