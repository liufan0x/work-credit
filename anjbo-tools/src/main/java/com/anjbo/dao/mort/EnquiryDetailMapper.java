package com.anjbo.dao.mort;

import java.util.List;
import java.util.Map;


/**
 * 网络报盘与市场成交查询
 * @author Administrator
 *
 * @date 2017-6-1 上午11:15:52
 */
public interface EnquiryDetailMapper {
	/**
	 * 市场成交数量
	 * 
	 * @user Administrator
	 * @date 2017-6-1 上午11:19:59 
	 * @param enquiryDto
	 * @return
	 */
	Integer selectCountByMarketPropertyName(Map<String,Object> paramMap);
	
	/**
	 * 网络报盘数量
	 * 
	 * @user Administrator
	 * @date 2017-6-1 上午11:20:10 
	 * @param enquiryDto
	 * @return
	 */
	Integer selectCountByNetworkPropertyName(Map<String,Object> paramMap);
	
	/**
	 * 根据条件取市场成交平均价格
	 * 
	 * @user Administrator
	 * @date 2017-6-1 下午02:05:43 
	 * @param paramMap
	 * @return
	 */
	Double selectAvgByMarket(Map<String,Object> paramMap);
	
	/**
	 * 根据条件取网络报盘平均价格
	 * 
	 * @user Administrator
	 * @date 2017-6-1 上午11:20:10 
	 * @param enquiryDto
	 * @return
	 */
	Double selectAvgByNetwork(Map<String,Object> paramMap);
	
	String selectPhoneByUid(String uid);
}
