package com.anjbo.service.baidu.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.baidu.BaiduFaceMapper;
import com.anjbo.service.baidu.BaiduFaceService;

@Service
public class BaiduFaceServiceImpl implements BaiduFaceService {
	
	@Resource private BaiduFaceMapper baiduFaceMapper;

	@Override
	public int addBaiduFaceData(Map<String, Object> param) {
		return baiduFaceMapper.addBaiduFaceData(param);
	}

}
