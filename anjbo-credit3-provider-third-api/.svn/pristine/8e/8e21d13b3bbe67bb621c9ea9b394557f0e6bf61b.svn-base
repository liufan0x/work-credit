package com.anjbo.service.baidu.impl;

import com.anjbo.dao.baidu.BaiduFaceMapper;
import com.anjbo.service.baidu.BaiduFaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class BaiduFaceServiceImpl implements BaiduFaceService {
	
	@Resource
    private BaiduFaceMapper baiduFaceMapper;

	@Override
	public int addBaiduFaceData(Map<String, Object> param) {
		return baiduFaceMapper.addBaiduFaceData(param);
	}

}
