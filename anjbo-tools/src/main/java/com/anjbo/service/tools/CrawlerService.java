package com.anjbo.service.tools;

import java.util.Map;

/**
 * 抓取物业数据
 * @author limh
 *
 * @date 2017-4-21 下午12:34:05
 */
public interface CrawlerService {
	/**
	 * 抓取物业
	 * @user limh
	 * @date 2017-4-21 下午12:33:05 
	 * @param param
	 * @return
	 */
	int addCrawler(Map<String,Object> param);
}
