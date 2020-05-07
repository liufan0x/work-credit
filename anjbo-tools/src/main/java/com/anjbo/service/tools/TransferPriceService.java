package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.tools.TransferPrice;

/**
 * 过户价/税费
 * @author limh limh@zxsf360.com
 * @date 2015-9-8 下午05:14:57
 */
public interface TransferPriceService {
	/**
	 * 新增过户价/税费
	 * @Title: addTransferPrice 
	 * @param transferPrice
	 * @return
	 * int
	 * @throws
	 */
	int addTransferPrice(TransferPrice transferPrice);
	/**
	 * 更新税费
	 * @Title: updateTax 
	 * @param transferPrice
	 * @return
	 * int
	 * @throws
	 */
	int updateTax(TransferPrice transferPrice);
	/**
	 * 分页查询过户价/税费
	 * @Title: selectTransferPricePage 
	 * @param transferPrice
	 * @return
	 * List<TransferPrice>
	 * @throws
	 */
	List<TransferPrice> selectTransferPricePage(TransferPrice transferPrice);
	/**
	 * 查询过户价/税费详情
	 * @Title: selectTransferPrice 
	 * @param id
	 * @return
	 * TransferPrice
	 * @throws
	 */
	TransferPrice selectTransferPrice(int id);
	/**
	 * 查询过户价总记录数
	 * 
	 * @user Administrator
	 * @date 2018年3月23日 上午10:45:47 
	 * @param transferPrice
	 * @return
	 */
	int selectTransferPricePageCount(TransferPrice transferPrice);
	
	/**
	 * 保存三价合一计算
	 * @user Administrator
	 * @date 2018年4月11日 下午6:34:08 
	 * @param transferPrice
	 * @return
	 */
	int addTransferPriceBank(TransferPrice transferPrice);
}