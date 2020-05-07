package com.anjbo.dao;


import com.anjbo.bean.finance.ReceivableHasDto;

public interface ReceivableHasMapper {
	
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
