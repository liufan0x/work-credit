package com.anjbo.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.dao.FundDockingMapper;
import com.anjbo.service.FundDockingService;

@Service
public class FundDockingServiceImpl implements FundDockingService {

	@Resource 
	private FundDockingMapper dockingMapper;
	@Override
	public DataAuditDto fund(String orderNo) {
		// TODO Auto-generated method stub
		return dockingMapper.fund(orderNo);
	}

	@Override
	public int add(DataAuditDto auditDto) {
		// TODO Auto-generated method stub
		dockingMapper.delete(auditDto.getOrderNo());
		return dockingMapper.add(auditDto);
	}

	@Override
	public int delete(String orderNo) {
		// TODO Auto-generated method stub
		return dockingMapper.delete(orderNo);
	}

	@Override
	public int addOrdinary(Map<String, Object> parament) {
		// TODO Auto-generated method stub
		String orderNo=parament.get("orderNo")+"";
		if(!"null".equals(orderNo) && !"".equals(orderNo)){
			dockingMapper.deleteOrdinary(orderNo);
		}
		return dockingMapper.addOrdinary(parament);
	}

	@Override
	public int addOrdinaryAudit(Map<String, Object> parament) {
		// TODO Auto-generated method stub
		String orderNo=parament.get("orderNo")+"";
		if(!"null".equals(orderNo) && !"".equals(orderNo)){
			dockingMapper.deleteOrdinaryAudit(orderNo);
		}
		return dockingMapper.addOrdinaryAudit(parament);
	}

	@Override
	public OrderBaseBorrowDto findByOrdinary(String orderNo) {
		// TODO Auto-generated method stub
		return dockingMapper.findByOrdinary(orderNo);
	}

	@Override
	public FirstAuditDto findByOrdinaryAudit(String orderNo) {
		// TODO Auto-generated method stub
		return dockingMapper.findByOrdinaryAudit(orderNo);
	}

}
