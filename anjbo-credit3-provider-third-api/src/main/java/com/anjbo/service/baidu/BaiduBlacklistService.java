package com.anjbo.service.baidu;

import java.util.Map;

import com.anjbo.bean.baidu.BaiduRiskVo;

public interface BaiduBlacklistService {
	
	public Map<String, Object> blacklist(BaiduRiskVo baiduRiskVo);
	
}
