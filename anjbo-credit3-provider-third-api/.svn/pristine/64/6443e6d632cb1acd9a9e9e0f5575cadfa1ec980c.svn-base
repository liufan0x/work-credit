package com.anjbo.service.dingtalk.impl;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.dao.dingtalk.ThirdDingtalkBpmsTempMapper;
import com.anjbo.service.dingtalk.BpmsTempService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
  *  [Service实现类]
  * @ClassName: ThirdDingtalkBpmsTempServiceImpl
  * @Description: 业务服务
  * @author 
  * @date 2017-10-16 15:46:54
  * @version V3.0
 */
@Service
public class BpmsTempServiceImpl implements BpmsTempService {
	@Resource
    private ThirdDingtalkBpmsTempMapper thirdDingtalkBpmsTempMapper;

	/* (non-Javadoc)
	 * @see com.anjbo.service.dingtalk.impl.BpmsTempService#search(com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto)
	 */
	@Override
	public List<ThirdDingtalkBpmsTempDto> search(ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto){
		return thirdDingtalkBpmsTempMapper.search(thirdDingtalkBpmsTempDto);
	}
	
	@Override
	public ThirdDingtalkBpmsTempDto getEntity(long id){
		return thirdDingtalkBpmsTempMapper.getEntity(id);
	}
	
	@Override
	public ThirdDingtalkBpmsTempDto getEntityByCode(String code){
		return thirdDingtalkBpmsTempMapper.getEntityByCode(code);
	}
	
	/* (non-Javadoc)
	 * @see com.anjbo.service.dingtalk.impl.BpmsTempService#edit(com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto)
	 */
	@Override
	public int edit(ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto) {
		return thirdDingtalkBpmsTempMapper.edit(thirdDingtalkBpmsTempDto);
	}
	
}