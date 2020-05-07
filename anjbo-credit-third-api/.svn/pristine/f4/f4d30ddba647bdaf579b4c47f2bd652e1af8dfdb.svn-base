package com.anjbo.service.ccb.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.ccb.BusInfoTask;
import com.anjbo.dao.ccb.BusInfoTaskMapper;
import com.anjbo.service.ccb.BusInfoTaskService;
import com.anjbo.utils.ccb.CCBEnums.Order_Task_Status;

/**
 * 任务实现
 * @author limh limh@anjbo.com   
 * @date 2016-12-28 下午05:29:08
 */

@Service
public class BusInfoTaskServiceImpl implements BusInfoTaskService {
	
	@Resource private BusInfoTaskMapper busInfoTaskMapper;

	@Override
	public int insert(BusInfoTask orderTask) {
		return busInfoTaskMapper.insert(orderTask);
	}

	@Override
	public BusInfoTask getByOrderNo(String orderNo) {
		return busInfoTaskMapper.getByOrderNo(orderNo);
	}

	@Override
	public BusInfoTask getByOrderNoAndCode(String orderNo, String code) {
		return busInfoTaskMapper.getByOrderNoAndCode(orderNo, code);
	}

	@Override
	public int update(BusInfoTask orderTask) {
		return busInfoTaskMapper.update(orderTask);
	}

	@Override
	public boolean hasN(String orderNo, String code) {
		return busInfoTaskMapper.hasN(orderNo, code)>0?true:false;
	}

	@Override
	public int updateStatus(int id, Order_Task_Status status) {
		return busInfoTaskMapper.updateStatus(id, status.getStatus(), status==Order_Task_Status.FAILE?"N":null);
	}

	@Override
	public List<BusInfoTask> listN(String orderNo) {
		return busInfoTaskMapper.listN(orderNo);
	}
	
}
