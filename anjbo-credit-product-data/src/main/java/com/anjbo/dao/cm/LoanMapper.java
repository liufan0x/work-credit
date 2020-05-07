package com.anjbo.dao.cm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.cm.LoanDto;

public interface LoanMapper {
	/**
	 * 新增贷款
	 * @param loanDto
	 * @return
	 */
    int addLoan(LoanDto loanDto);
    /**
     * 查询贷款列表
     * @param loanDto
     * @return
     */
    List<LoanDto> selectLoanList(Map<String, Object> params);
    /**
     * 查询贷款数量
     * @param loanDto
     * @return
     */
    int selectLoanCount(Map<String, Object> params);
    /**
     * 修改贷款信息
     * @param loanDto
     * @return
     */
    int updateLoan(LoanDto loanDto);
    /**
     * 修改贷款状态
     * @param loanDto
     * @return
     */
    int updateLoanStatus(LoanDto loanDto);
    /**
     * 根据订单编号查询贷款信息
     * @param orderNo
     * @return
     */
    LoanDto selectLoanByOrderNo(String orderNo);
    /**
     * 根据贷款申请编号查询订单编号，用于校验申请编号是否有效
     * @param appNo
     * @return
     */
    LoanDto selectOrderNoByLoanAppNo(String appNo);
    /**
     * 查询贷款流程流水
     * @param orderNo
     * @return
     */
    List<Map<String,Object>> selectLoanProgressFlow(String orderNo);
    /**
     * 根据状态查询贷款列表
     * @param status 多个用,分割
     * @return
     */
    List<LoanDto> selectLoanListByStatus(@Param("status")String status);
    /**
     * 修改贷款流程
     * @param param 
     * orderNo 订单编号<br>
     * progressNo 流程编号<br>
     * status 状态（可选）
     * @return
     */
    int updateLoanProgressNo(Map<String,Object> param);
    /**
     * 新增贷款流程流水
     * @param param
     * @return
     */
    int addLoanProgressFlow(Map<String,Object> param);
    /**
     * 关闭订单
     * @param loanDto
     * @return
     */
    int addOrderClose(LoanDto loanDto);
    /**
     * 查询关闭原因
     * @param orderNo
     * @return
     */
    Map<String,Object> selectOrderCloseReason(String orderNo);
}