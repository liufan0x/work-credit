package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.product.ManagerAuditDto;



public interface ManagerAuditService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public ManagerAuditDto selectManagerAudit(ManagerAuditDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addManagerAudit(ManagerAuditDto dto);
	
	public int updateManagerAudit(ManagerAuditDto dto);
	
	public int updateStatus(ManagerAuditDto dto);
	
	public List<ManagerAuditDto> selectManagerAll(ManagerAuditDto dto);
	
	public int selectManagerCount();

}
