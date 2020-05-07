package com.anjbo.controller.dingtalk;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.dingtalk.vo.BpmsVo;
import com.anjbo.service.dingtalk.BpmsService;
import com.anjbo.service.dingtalk.BpmsTempService;


/**
 * 
 * @Author KangLG 2017年10月13日 下午4:01:41
 */
@RestController
@RequestMapping("/credit/third/api/dingtalk/bpms")
public class BpmsController extends BaseController{
	private Log log = LogFactory.getLog(BpmsController.class);	
	@Autowired private BpmsTempService bpmsTempService;
	@Autowired private BpmsService bpmsService;
	
	/**
	 * 订单要件审批表单
	 * @Author KangLG<2017年10月17日>
	 * @param request
	 * @param BpmsVo
	 * @return
	 */
	@RequestMapping(value = "/createOrderDoc")
	public RespStatus createOrderDoc(HttpServletRequest request, @RequestBody BpmsVo record){ 
		try {	
			ThirdDingtalkBpmsTempDto temp = bpmsTempService.getEntityByCode(record.getBpmsFrom());
			if(null == temp){
				return RespHelper.setFailRespStatus(new RespStatus(), "流程审批模板不存在！");
			}
			// 构建表单参数
			ThirdDingtalkBpmsDto bpms = new ThirdDingtalkBpmsDto();
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);  
			BeanUtils.copyProperties(bpms, temp);
			bpms.setBpmsFrom(record.getBpmsFrom());
			bpms.setBpmsFromId(record.getBpmsObjectId());	
			bpms.setFormComponent(String.format(temp.getFormComponent(), record.getFormComponentParam()[0], record.getFormComponentParam()[1]));
			// 操作人信息			
			bpms.setOriginatorDeptId(this.getUserDto(request).getDingtalkDepId());
			bpms.setOriginatorUserId(this.getUserDto(request).getDingtalkUid());
			bpms.setOriginatorUserName(this.getUserDto(request).getName());			
			bpms.setCreateUid(getUserDto(request).getAccount());
			if(bpmsService.add(bpms) < 1){
				return RespHelper.failRespStatus();
			}
		} catch (Exception e) {
			log.error("创建失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return RespHelper.setSuccessRespStatus(new RespStatus());
	}
	

	/**
	 * 目前没场景，供demo操作
	 * @Author KangLG<2017年10月13日>
	 * @param request
	 * @param thirdDingtalkBpmsDto
	 * @return
	 */
	@RequestMapping(value = "/page")
	public RespPageData<ThirdDingtalkBpmsDto> page(HttpServletRequest request,@RequestBody ThirdDingtalkBpmsDto thirdDingtalkBpmsDto){
		RespPageData<ThirdDingtalkBpmsDto> resp = new RespPageData<ThirdDingtalkBpmsDto>();
		resp.setTotal(bpmsService.searchCount(thirdDingtalkBpmsDto));
		resp.setRows(bpmsService.search(thirdDingtalkBpmsDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
}
