package com.anjbo.service.dingtalk.impl;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.dao.dingtalk.ThirdDingtalkBpmsMapper;
import com.anjbo.service.dingtalk.BpmsService;
import com.anjbo.service.dingtalk.DingtalkService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
  *  [Service实现类]
  * @ClassName: ThirdDingtalkBpmsServiceImpl
  * @Description: 业务服务
  * @author 
  * @date 2017-10-13 11:37:59
  * @version V3.0
 */
@Service
public class BpmsServiceImpl  implements BpmsService {
	@Autowired
    private DingtalkService dingtalkService;
	@Resource
    private ThirdDingtalkBpmsMapper thirdDingtalkBpmsMapper;

	@Override
	public List<ThirdDingtalkBpmsDto> search(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto){
		return thirdDingtalkBpmsMapper.search(thirdDingtalkBpmsDto);
	}
	@Override
	public int searchCount(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto){
		return thirdDingtalkBpmsMapper.searchCount(thirdDingtalkBpmsDto);
	}
	
	@Override
	public int add(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto) {
		if(!thirdDingtalkBpmsDto.validDingBpms()){
			return -10;
		}
		
		// 同步审批流程至钉钉
		String result = dingtalkService.bpmsCreate(thirdDingtalkBpmsDto);
		if(StringUtils.isEmpty(result) || result.equals("ERROR")){
			return -1;
		}
		
		// 业务系统记录
		thirdDingtalkBpmsDto.setProcessInstanceId(result);
		return thirdDingtalkBpmsMapper.add(thirdDingtalkBpmsDto);
	}
		
}