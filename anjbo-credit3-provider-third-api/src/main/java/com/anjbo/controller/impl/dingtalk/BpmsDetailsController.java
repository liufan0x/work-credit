package com.anjbo.controller.impl.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDetailsDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.dingtalk.IBpmsDetailsController;
import com.anjbo.service.dingtalk.BpmsDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 
 * @Author KangLG 2017年10月13日 下午4:01:41
 */
@RestController
public class BpmsDetailsController extends BaseController implements IBpmsDetailsController {
	@Resource
    private BpmsDetailsService thirdDingtalkBpmsDetailsService;
	@Resource
	private UserApi userApi;
	/**
	 * 目前没场景，供demo操作
	 * @Author KangLG<2017年10月13日>
	 * @param thirdDingtalkBpmsDetailsDto
	 * @return
	 */
	@Override
	public RespPageData<ThirdDingtalkBpmsDetailsDto> page(@RequestBody ThirdDingtalkBpmsDetailsDto thirdDingtalkBpmsDetailsDto){
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
	 * @param msg{processInstanceId, processInstanceStatus}
	 * @return
	 */
	@Override
	public RespStatus notify(String msg){
		try {
			UserDto user = userApi.getUserDto();
			JSONObject json = JSONObject.parseObject(msg);
			ThirdDingtalkBpmsDetailsDto record = new ThirdDingtalkBpmsDetailsDto(json.getString("process_instance_id"), json.getString("process_instance_result"));
			record.setCreateUid(user.getAccount());
			thirdDingtalkBpmsDetailsService.add(record);
		
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failRespStatus();
		}
	}
}
