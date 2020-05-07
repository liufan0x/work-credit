package com.anjbo.dao.huarong;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 
* @ClassName: LoanStatusMapper 
* @Description: TODO(华融返回放款结果dao) 
* @author xufx
* @date 2017年9月14日 下午12:34:37 
*
 */
public interface LoanStatusMapper {
	
	int saveLoanStatus(Map<String,Object> map);
	Map getLoanStatus(@Param("orderNo") String  orderNo);
}
