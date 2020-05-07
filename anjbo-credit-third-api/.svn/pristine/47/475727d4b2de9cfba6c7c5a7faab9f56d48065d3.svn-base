package com.anjbo.service.ccb;

import java.util.List;

import com.anjbo.bean.ccb.BusInfoTask;
import com.anjbo.utils.ccb.CCBEnums.Order_Task_Status;

public interface BusInfoTaskService {
	
	int insert(BusInfoTask orderTask);

	BusInfoTask getByOrderNo( String orderNo);
	
	BusInfoTask getByOrderNoAndCode(String orderNo,String code);
	
	int updateStatus(int id,Order_Task_Status status);
	
	int update(BusInfoTask orderTask);
	
	boolean hasN( String orderNo,String code);

	List<BusInfoTask> listN(String orderNo);
}