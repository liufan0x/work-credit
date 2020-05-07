package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
* @ClassName: LoanStatusMapper 
* @Description: TODO(华融返回放款结果dao) 
* @author xufx
* @date 2017年9月14日 下午12:34:37 
*
 */
public interface HrtrustLoanStatusMapper {
	
	int saveLoanStatus(Map<String, Object> map);
	
	Map<String,Object> getLoanStatus(@Param("orderNo") String orderNo);
}
