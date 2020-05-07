package com.anjbo.dao.ccb;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.ccb.BusInfoTask;

public interface BusInfoTaskMapper {
	int insert(BusInfoTask orderTask);

	BusInfoTask getByOrderNo(@Param("orderNo") String orderNo);
	
	BusInfoTask getByOrderNoAndCode(@Param("orderNo") String orderNo,@Param("code") String code);
	
	int updateStatus(@Param("id")int id,@Param("status")String status,@Param("result") String result);
	
	int update(BusInfoTask orderTask);

	int hasN(@Param("orderNo") String orderNo,@Param("code") String code);

	List<BusInfoTask> listN(String orderNo);
}
