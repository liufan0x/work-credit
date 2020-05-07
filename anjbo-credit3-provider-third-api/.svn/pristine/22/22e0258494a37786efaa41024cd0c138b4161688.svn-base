package com.anjbo.service.hrtrust;

import java.util.List;
import java.util.Map;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

public interface HrtrustService {
	
	public void setOrderNo(String orderNo);
	
	/**
	 * 申请数据接口
	 * @param parmMap
	 * @return
	 */
	public RespStatus applySend(Map<String, Object> parmMap);
	
	
	
	/**
	 * 放款接口
	 * @param parmMap
	 * @return
	 */
	public RespStatus lendSend(Map<String, Object> parmMap);
	
	/**
	 * 附件接口
	 * @param list
	 * @return
	 */
	public RespStatus fileApplySend(List<Map<String, Object>> list);
	
	/**
	 * 应还款计划接口
	 * @param parmMap
	 * @return
	 */
	public RespStatus borrowRepaymentSend(Map<String, Object> parmMap);
	
	/**
	 * 回款接口
	 * @param parmMap
	 * @return
	 */
	public RespStatus repaymentRegisterSend(Map<String, Object> parmMap);
	
	/**
	 * 放款状态查询
	 * @param parmMap
	 * @return
	 */
	public RespDataObject<Map<String, Object>> queryLoanStatusSend(Map<String, Object> parmMap);
	
}
