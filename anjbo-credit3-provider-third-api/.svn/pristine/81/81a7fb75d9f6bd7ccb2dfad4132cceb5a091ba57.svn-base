package com.anjbo.controller.impl.dingtalk;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.common.*;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.dingtalk.IBpmsTempController;
import com.anjbo.service.dingtalk.BpmsTempService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
  *  [Controller]
  * @ClassName: ThirdDingtalkBpmsTempController
  * @Description: 
  * @date 2017-10-16 15:46:54
  * @version V3.0
 */
@RestController
public class BpmsTempController extends BaseController implements IBpmsTempController {
	private Log log = LogFactory.getLog(BpmsTempController.class);
	@Resource
    private BpmsTempService bpmsTempService;
	@Resource
	private UserApi userApi;
	/**
	 * 分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-10-16 15:46:54
	 * @return thirdDingtalkBpmsTempDto
	 */
	@Override
	public RespPageData<ThirdDingtalkBpmsTempDto> page(@RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto){
		RespPageData<ThirdDingtalkBpmsTempDto> resp = new RespPageData<ThirdDingtalkBpmsTempDto>();
		resp.setRows(bpmsTempService.search(thirdDingtalkBpmsTempDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	@Override
	public RespStatus get(long id){
		try {
			return RespHelper.setSuccessDataObject(new RespDataObject<ThirdDingtalkBpmsTempDto>(), bpmsTempService.getEntity(id));
		} catch (Exception e) {
			log.error("加载出错...：", e);		
			return RespHelper.failRespStatus();
		}
		
	}
	
	/**
	 * 编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-10-16 15:46:54
	 * @param thirdDingtalkBpmsTempDto
	 * @return
	 */
	@Override
	public RespStatus edit(@RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto){
		try {
			UserDto userDto = userApi.getUserDto();
			if(thirdDingtalkBpmsTempDto.getId()>0){
				thirdDingtalkBpmsTempDto.setUpdateUid(userDto.getAccount());
				bpmsTempService.edit(thirdDingtalkBpmsTempDto);
				return RespHelper.setSuccessRespStatus(new RespStatus());
			}			
		} catch (Exception e) {
			log.error("编辑失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
	
}
