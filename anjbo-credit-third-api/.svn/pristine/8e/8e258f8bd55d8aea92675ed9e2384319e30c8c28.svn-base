package com.anjbo.service.huarong.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.dao.huarong.UtilBorrowIdMapper;
/**
 * 
* @ClassName: QueryLoanStatusServiceImpl 
* @Description: TODO(获取 borrowId和批次号) 
* @author czm
* @date 2017年8月18日 下午2:12:57 
*
 */
@Service
public class UtilBorrowIdService  {
	Logger log = Logger.getLogger(UtilBorrowIdService.class);
	
	@Resource
	private UtilBorrowIdMapper utilBorrowIdMapper;
	/**
	 *  获取BorrowId()
	 * @date 2017年8月18日 下午2:12:57
	 * @author czm
	 */
	public   long  getBorrowId(){	 
		long borrowId=utilBorrowIdMapper.getBorrowId();
		log.info("------------------------------------------getBorrowId"+borrowId);
		utilBorrowIdMapper.updateBorrowId(borrowId+1);
		return borrowId;
	}

	
	public   long  getBatchNo(){	
		long batchNo=utilBorrowIdMapper.getBatchNo();
		utilBorrowIdMapper.updateBatchNo(batchNo+1);
		return batchNo;
	}
	
	public  long getApplSeq(){
		
		long ApplSeq=utilBorrowIdMapper.getApplSeq();
		utilBorrowIdMapper.updateApplSeq(ApplSeq+1);
		return ApplSeq;
	}
	public String findBatchNoByOrderNo(String orderNo){
		return utilBorrowIdMapper.findBatchNoByOrderNo(orderNo);
	}
	
	public String findApplSeqByOrderNo(String orderNo){
		return utilBorrowIdMapper.findApplSeqByOrderNo(orderNo);
	}
	public String findBorrowIdByOrderNo(String orderNo){
		return utilBorrowIdMapper.findBorrowIdByOrderNo(orderNo);
	}
	
}
