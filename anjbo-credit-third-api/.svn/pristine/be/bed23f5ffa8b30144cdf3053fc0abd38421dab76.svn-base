package com.anjbo.controller.ccb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.ccb.BusInfoTask;
import com.anjbo.common.Enums;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ccb.BusInfoTaskService;
import com.anjbo.utils.SpringContextUtil;
import com.anjbo.utils.ccb.BusInfoTaskRunner;
import com.anjbo.utils.ccb.CCBEnums;
import com.anjbo.utils.ccb.MD5;
import com.anjbo.utils.common.ThirdConfigUtil;
import com.anjbo.ws.ccb.CCBWsHelper;
/**
 * 建行辅助控制层
 * @author limh limh@anjbo.com   
 * @date 2017-08-22 下午07:45:42
 *
 */
@Controller
@RequestMapping("/credit/third/api/ccb/wshelper/v")
public class CCBWsHelperController {
	@Resource
	BusInfoTaskService busInfoTaskService;
	/**
	 * 请求建行接口
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody 
	@RequestMapping(value = "/post")
	public RespDataObject<Map<String,Object>> post(@RequestBody Map<String,Object> param){
		String tranNo = MapUtils.getString(param,"tranNo");
		Map<String,Object> obj = MapUtils.getMap(param, "obj");
		String subBankId = MapUtils.getString(obj,"subBankId");
		if(StringUtils.isEmpty(tranNo)||obj==null){
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),null,RespStatusEnum.PARAMETER_ERROR.getMsg());
		}else if(Enums.CCBTranNoEnum.C006.getCode().equals(tranNo)){
			subBankId = ThirdConfigUtil.getProperty(CCBEnums.GLOBAL_CONFIG_KEY.CCB_BANKNO.toString());
		}else if(StringUtils.isEmpty(subBankId)){
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),null,"此接口要求客户经理的机构号必传");
		}
		return CCBWsHelper.postOp(tranNo, obj, subBankId);
	}
	/**
	 * 影像资料任务
	 * @param param
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/busInfoTask")
	public RespStatus busInfoTask(@RequestBody Map<String,Object> param){
		String code = MapUtils.getString(param,"code");
		String orderNo = MapUtils.getString(param,"orderNo");
		String appNo = MapUtils.getString(param,"appNo");
		String subBankId = MapUtils.getString(param,"subBankId");
		if(StringUtils.isEmpty(code)||StringUtils.isEmpty(orderNo)||
				StringUtils.isEmpty(appNo)||StringUtils.isEmpty(subBankId)){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		BusInfoTaskRunner busInfoTaskRunner = (BusInfoTaskRunner) SpringContextUtil.getBean("busInfoTaskRunner");
		busInfoTaskRunner.init(code, orderNo, appNo, subBankId);
		new Thread(busInfoTaskRunner).start();
		return RespHelper.setSuccessRespStatus(new RespStatus());
	}
	/**
	 * 影像资料同步
	 * @param param
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/busInfoSync")
	public RespDataObject<Map<String, Object>> busInfoSync(@RequestBody Map<String,Object> param){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		String orderNo = MapUtils.getString(param,"orderNo");
		String url = MapUtils.getString(param,"url");
		String subBankId = MapUtils.getString(param,"subBankId");
		if(StringUtils.isEmpty(url)||StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(subBankId)){
			return RespHelper.setFailDataObject(resp,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		String path = new BusInfoTaskRunner().syncCCBBusInfo(orderNo, url);
		if(StringUtils.isEmpty(path)){
			return RespHelper.setFailDataObject(resp,null,"同步建行影像资料失败，请稍后再试");
		}
		try {
			String mdfstr = MD5.fileMD5(path).toUpperCase();
			Map<String,Object> objMap = new HashMap<String,Object>();
			objMap.put("name", new File(path).getName());
			objMap.put("mdfstr", mdfstr);
			String BusInfoDirBase = ThirdConfigUtil.getProperty("BusInfoDirBase");
			objMap.put("path", BusInfoDirBase+path);
			RespDataObject<Map<String, Object>> respCCB = CCBWsHelper.postOp(CCBTranNoEnum.C027.getCode(), objMap, subBankId);
			return respCCB;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RespHelper.setFailDataObject(resp,null,RespStatusEnum.FAIL.getMsg());
	}
	/**
	 *影像资料任务提交（未成功的数据）
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/busInfoTaskSubmit")
	public RespStatus busInfoTaskSubmit(HttpServletRequest request,@RequestBody Map<String,Object> param) {
		String orderNo = MapUtils.getString(param,"orderNo");
		String appNo = MapUtils.getString(param,"appNo");
		String subBankId = MapUtils.getString(param,"subBankId");
		if(StringUtils.isEmpty(orderNo)||
				StringUtils.isEmpty(appNo)||StringUtils.isEmpty(subBankId)){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		List<BusInfoTask> orderTasks = busInfoTaskService.listN(orderNo);
		for (Iterator<BusInfoTask> iterator = orderTasks.iterator(); iterator.hasNext();) {
			BusInfoTask busInfoTask = iterator.next();
			BusInfoTaskRunner busInfoTaskRunner = (BusInfoTaskRunner) SpringContextUtil.getBean("busInfoTaskRunner");
			busInfoTaskRunner.init(busInfoTask.getCode(), orderNo, appNo, subBankId);
			new Thread(busInfoTaskRunner).start();
		}
		return RespHelper.setSuccessRespStatus(new RespStatus());
	}
	/**
	 * 影像资料任务是否有未成功的数据
	 * @param request
	 * @param orderNo
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/busInfoTaskHasN")
	public RespDataObject<Boolean> busInfoTaskHasN(HttpServletRequest request,@RequestBody Map<String,Object> param) {
		RespDataObject<Boolean> resp = new RespDataObject<Boolean>();
		String code = MapUtils.getString(param,"code");
		String orderNo = MapUtils.getString(param,"orderNo");
		if(StringUtils.isEmpty(code)||StringUtils.isEmpty(orderNo)){
			return RespHelper.setFailDataObject(resp,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		boolean hasN = busInfoTaskService.hasN(orderNo, code);
		return RespHelper.setSuccessDataObject(resp,hasN);
	}
}
