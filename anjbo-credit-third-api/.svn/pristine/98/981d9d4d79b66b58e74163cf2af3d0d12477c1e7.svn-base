package com.anjbo.controller.dingtalk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.dingtalk.BpmsTempService;


/**
  *  [Controller]
  * @ClassName: ThirdDingtalkBpmsTempController
  * @Description: 
  * @date 2017-10-16 15:46:54
  * @version V3.0
 */
@RestController
@RequestMapping("/credit/third/api/dingtalk/bpmsTemp")
public class BpmsTempController extends BaseController{
	private Log log = LogFactory.getLog(BpmsTempController.class);	
	@Resource private BpmsTempService bpmsTempService;

	/**
	 * 分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-10-16 15:46:54
	 * @param request
	 * @return thirdDingtalkBpmsTempDto
	 */
	@RequestMapping(value = "/page")
	public RespPageData<ThirdDingtalkBpmsTempDto> page(HttpServletRequest request,@RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto){
		RespPageData<ThirdDingtalkBpmsTempDto> resp = new RespPageData<ThirdDingtalkBpmsTempDto>();		
		resp.setRows(bpmsTempService.search(thirdDingtalkBpmsTempDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	@RequestMapping(value = "/get")
	public RespStatus get(HttpServletRequest request, long id){ 
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
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto){ 
		try {
			if(thirdDingtalkBpmsTempDto.getId()>0){
				thirdDingtalkBpmsTempDto.setUpdateUid(getUserDto(request).getAccount());
				bpmsTempService.edit(thirdDingtalkBpmsTempDto);
				return RespHelper.setSuccessRespStatus(new RespStatus());
			}			
		} catch (Exception e) {
			log.error("编辑失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
	
}
