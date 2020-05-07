/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.finance;

import com.anjbo.bean.finance.ReceivableHasDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
public interface ReceivableHasMapper extends BaseMapper<ReceivableHasDto>{
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public ReceivableHasDto findByReceivableHas(ReceivableHasDto dto);
	/**
	 * 添加
	 * @param orderNo
	 * @return
	 */
	public int addReceivableHas(ReceivableHasDto dto);
	/**
	 * 完善
	 * @param dto
	 * @return
	 */
	public int updateReceivableHas(ReceivableHasDto dto);
	
	/**
	 * 撤回
	 * @param receivableHas
	 * @return
	 */
	public int updwithdraw(ReceivableHasDto receivableHas);
	
	/**
	 * 删除
	 * @param orderNo
	 * @return
	 */
	public int delectReceivableHas(String orderNo);
}
