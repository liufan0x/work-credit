package com.anjbo.service.dingtalk.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDetailsDto;
import com.anjbo.dao.dingtalk.ThirdDingtalkBpmsDetailsMapper;
import com.anjbo.service.dingtalk.BpmsDetailsService;

/**
  *  [Service实现类]
  * @ClassName: ThirdDingtalkBpmsDetailsServiceImpl
  * @Description: 业务服务
  * @author 
  * @date 2017-10-13 11:37:59
  * @version V3.0
 */
@Service
public class BpmsDetailsServiceImpl  implements BpmsDetailsService
{
	
	@Resource
	private ThirdDingtalkBpmsDetailsMapper thirdDingtalkBpmsDetailsMapper;

	@Override
	public List<ThirdDingtalkBpmsDetailsDto> search(ThirdDingtalkBpmsDetailsDto thirdDingtalkBpmsDetailsDto){
		return thirdDingtalkBpmsDetailsMapper.search(thirdDingtalkBpmsDetailsDto);
	}
	
	@Override
	public int add(ThirdDingtalkBpmsDetailsDto thirdDingtalkBpmsDetailsDto) {
		return thirdDingtalkBpmsDetailsMapper.add(thirdDingtalkBpmsDetailsDto);
	}
		
}