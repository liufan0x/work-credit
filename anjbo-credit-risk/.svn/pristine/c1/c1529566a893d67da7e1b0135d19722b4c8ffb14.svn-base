package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.JusticeAuditDto;
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.dao.JusticeAuditMapper;
import com.anjbo.dao.OfficerAuditMapper;
import com.anjbo.service.JusticeAuditService;
import com.anjbo.service.OfficerAuditService;
/**
 * 首席风险官
 * @author huanglj
 *
 */
@Transactional
@Service
public class JusticeAuditServiceImpl implements JusticeAuditService{

	@Resource
	private JusticeAuditMapper justiAuditMapper;
	
	public JusticeAuditDto detail(String orderNo){
		JusticeAuditDto obj = justiAuditMapper.detail(orderNo);
		return  null==obj?new JusticeAuditDto():obj;
	}
	
	public int update(JusticeAuditDto obj){
		return justiAuditMapper.update(obj);
	}
	
	public int insert(JusticeAuditDto obj){
		int success = 0;
		JusticeAuditDto tmp = justiAuditMapper.detail(obj.getOrderNo());
		if(null==tmp){
			success = justiAuditMapper.insert(obj);
		} else {
			success = justiAuditMapper.update(obj);
		}
		return success;
	}
}
