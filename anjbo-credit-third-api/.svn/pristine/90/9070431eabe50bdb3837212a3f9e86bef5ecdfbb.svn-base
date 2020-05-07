package com.anjbo.dao.huarong;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 获取batchNo和 获取borrowId
 * @author Administrator
 *
 */
public interface UtilBorrowIdMapper {
	
	long getBorrowId();
	int updateBorrowId(@Param("bid") long bid);
	
	long getBatchNo();
	int updateBatchNo(@Param("bno") long bno);
	
	long getApplSeq();
	int updateApplSeq(@Param("aeq") long aeq);
	
	
	String findBatchNoByOrderNo(@Param("orderNo") String  orderNo);
	
	String findApplSeqByOrderNo(@Param("orderNo") String  orderNo);
	String findBorrowIdByOrderNo(@Param("orderNo") String  orderNo);
}
