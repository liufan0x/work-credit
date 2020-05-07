package com.anjbo.controller.dingtalk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDetailsDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.dingtalk.BpmsDetailsService;


/**
 * 
 * @Author KangLG 2017年10月13日 下午4:01:41
 */
@RestController
@RequestMapping("/credit/third/api/dingtalk/bpmsDetails")
public class BpmsDetailsController extends BaseController{
	@Resource private BpmsDetailsService thirdDingtalkBpmsDetailsService;
	
	/**
	 * 目前没场景，供demo操作
	 * @Author KangLG<2017年10月13日>
	 * @param request
	 * @param thirdDingtalkBpmsDetailsDto
	 * @return
	 */
	@RequestMapping(value = "/page")
	public RespPageData<ThirdDingtalkBpmsDetailsDto> page(HttpServletRequest request,@RequestBody ThirdDingtalkBpmsDetailsDto thirdDingtalkBpmsDetailsDto){
		RespPageData<ThirdDingtalkBpmsDetailsDto> resp = new RespPageData<ThirdDingtalkBpmsDetailsDto>();
		resp.setTotal(10);
		resp.setRows(thirdDingtalkBpmsDetailsService.search(thirdDingtalkBpmsDetailsDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 审批流程回调
	 * @Author KangLG<2017年10月16日>
	 * @param request
	 * @param msg{processInstanceId, processInstanceStatus}
	 * @return
	 */
	@RequestMapping(value = "/notify")
	public RespStatus notify(HttpServletRequest request, String msg){ 
		try {
			JSONObject json = JSONObject.parseObject(msg);
			ThirdDingtalkBpmsDetailsDto record = new ThirdDingtalkBpmsDetailsDto(json.getString("process_instance_id"), json.getString("process_instance_result"));
			record.setCreateUid(getUserDto(request).getAccount());
			thirdDingtalkBpmsDetailsService.add(record);
		
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failRespStatus();
		}
	}
}
