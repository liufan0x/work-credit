package com.anjbo.config;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.order.BaseListService;
import com.anjbo.stream.sender.OrderAmsSender;
import com.anjbo.utils.BeanToMapUtil;

@Aspect
@Configuration
public class ProcessAOPConfigure {

	protected Log logger = LogFactory.getLog(this.getClass());	
	
	@Resource private UserApi userApi;
	
	//@Resource private FlowableApi flowableApi;
	
	@Resource private OrderAmsSender orderAmsSender;
	
	@Resource private BaseListService baseListService;
	
	@Pointcut("execution(* com.anjbo.controller.impl.*.*.processSubmit(..))")
    public void excudeService(){}
	
	@Around("excudeService()")
	public Object excudeService(ProceedingJoinPoint pjp) {
		long startTime = System.currentTimeMillis();
		RespStatus result = new RespStatus();
		Object[] args =  pjp.getArgs();
		try {
			Object obj = args[0];
			if(obj instanceof List){
				List<Object> list = (List)obj;
				obj = list.get(0);
			}
			Map<String, Object> params = BeanToMapUtil.beanToMap(obj);
			String orderNo = MapUtils.getString(params, "orderNo");
			if(StringUtils.isEmpty(orderNo)) {
				return RespHelper.setFailRespStatus(result, "缺少订单号");
			}
			UserDto userDto = userApi.getUserDto();
			
			//判断订单是否为当前处理人处理。
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(orderNo);
			baseListDto = baseListService.find(baseListDto);
			if(baseListDto == null) {
				return RespHelper.setFailRespStatus(result, "此订单不存在");
			}else if(!baseListDto.getCurrentHandlerUid().equals(userDto.getUid())) {
				return RespHelper.setFailRespStatus(result, "当前处理人不是你，请勿处理");
			}
			
			result = (RespStatus) pjp.proceed();
			
//			//不成功直接返回错误信息
//			if(!RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
//				return result;
//			}
//			params.put("processId", baseListDto.getProcessId());
//			if(!(params.containsKey("cityCode") && params.containsKey("productCode"))) {
//				params.put("cityCode", baseListDto.getCityCode());
//				params.put("productCode", baseListDto.getProductCode());
//			}
//			//调用流程处理。进入下一个流程
//			Map<String, Object> processMap = new HashMap<String,Object>(); //flowableApi.complete(params);
//			if(!(processMap != null && processMap.isEmpty())) {
//				throw new Exception();
//			}
//			
//
//			//更新列表状态
//			BaseListDto newBaseListDto = new BaseListDto();
//			newBaseListDto.setOrderNo(orderNo);
//			newBaseListDto.setPreviousHandlerUid(userDto.getUid());
//			newBaseListDto.setPreviousHandler(userDto.getName());
//			if("wanjie".equals(MapUtils.getString(processMap, "processId"))) {
//				newBaseListDto.setProcessId("wanjie");
//				newBaseListDto.setState("已完结");
//				newBaseListDto.setCurrentHandlerUid("-");
//				newBaseListDto.setCurrentHandler("-");
//			}else {
//				newBaseListDto.setProcessId(MapUtils.getString(processMap, "processId"));
//				newBaseListDto.setState("待"+MapUtils.getString(processMap, "processName"));
//				newBaseListDto.setCurrentHandlerUid(MapUtils.getString(processMap, "currentHandlerUid"));
//				newBaseListDto.setCurrentHandler(MapUtils.getString(processMap, "currentHandler"));
//			}
//			baseListService.update(newBaseListDto);
//			
//			//对应流程发短信
//			Map<String, Object> amsMap = new HashMap<String, Object>();
//			amsMap.put("previousProcessId", baseListDto.getProcessId());		//上一个节点
//			amsMap.put("currentProcessId", newBaseListDto.getProcessId());		//当前节点
//			amsMap.put("acceptMemberUid", baseListDto.getAcceptMemberUid());	//受理员
//			amsMap.put("channelManagerUid", baseListDto.getChannelManagerUid());//渠道经理
//			amsMap.put("currentHandlerUid", baseListDto.getCurrentHandlerUid());//当前处理人
//			orderAmsSender.sendOrderAms(amsMap);
			
		} catch (Throwable e) {
			logger.error(pjp.getSignature() + "异常,参数：" + args, e);
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
		}
		logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
		return result;
	}


}

