package com.anjbo.service.tools.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.dao.tools.CrawlerMapper;
import com.anjbo.service.tools.CrawlerService;

/**
 * 抓取物业数据
 * @author limh
 *
 * @date 2017-4-21 下午12:55:29
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {
	private Logger log = Logger.getLogger(CrawlerServiceImpl.class);
	@Resource
	private CrawlerMapper crawlerMapper;
	@Override
	public int addCrawler(Map<String, Object> param) {
		log.info("Crawler物业信息："+param.toString());
		try {
			if(StringUtils.isNotEmpty(MapUtils.getString(param, "roomId"))){
				return crawlerMapper.updateCrawlerRoomInfo(param);//修改房号信息
			}else if(StringUtils.isNotEmpty(MapUtils.getString(param, "buildingId"))){
				return crawlerMapper.addCrawlerRoom(param);//添加房号
			}else if(StringUtils.isNotEmpty(MapUtils.getString(param, "propertyId"))){
				return crawlerMapper.addCrawlerBuilding(param);//添加楼栋
			}else{
				return crawlerMapper.addCrawlerProperty(param);//添加物业信息
			}
		} catch (Exception e) {
			log.info("Crawler物业信息失败：",e);
			//e.printStackTrace();
		}
		return 0;
	}
}
