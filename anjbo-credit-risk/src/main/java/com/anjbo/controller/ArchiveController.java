package com.anjbo.controller;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.ArchiveDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.core.RMqClientConnect;
import com.anjbo.execute.ExecuteData;
import com.anjbo.service.ArchiveService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.StringUtil;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.execute.ExecuteQueue;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查档
 * @author haunglj
 *
 */
@Controller
@RequestMapping("/credit/risk/archive")
public class ArchiveController extends BaseController{

	private static final Log log = LogFactory.getLog(ArchiveController.class);
	@Resource
	private ArchiveService archiveService;
	/**
	 * 查档
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody 
	@RequestMapping(value="v/getArchive")
	public RespDataObject<Map<String, Object>> getArchive(HttpServletRequest request,@RequestBody Map<String,Object> param){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
//			if(StringUtils.isBlank(MapUtils.getString(param, "orderNo", ""))){
//				result.setMsg("查询失败,缺少订单编号!");	
//				return result;
//			} else 
			if(StringUtils.isBlank(MapUtils.getString(param, "estateNo", ""))){
				result.setMsg("查询失败,房产证号不能为空!");	
				return result;
			} else if(StringUtils.isBlank(MapUtils.getString(param, "estateType", ""))){
				result.setMsg("查询失败,产权证类型不能为空!");	
				return result;
			} else if(StringUtils.isBlank(MapUtils.getString(param, "identityNo", ""))){
				result.setMsg("查询失败,姓名/身份证号不能为空!");	
				return result;
			}
			
			//查询业务类型
			if(StringUtils.isNotEmpty(MapUtils.getString(param, "orderNo", ""))){
				OrderListDto listDto=new OrderListDto();
				listDto.setOrderNo(MapUtils.getString(param, "orderNo", ""));
				listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
				if(listDto!=null){
					param.put("productCode", listDto.getProductCode());
				}
			}
			UserDto user = getUserDto(request);
			//取登录人信息
			String id = MD5Utils.MD5Encode(MapUtils.getString(param, "estateNo") + MapUtils.getString(param, "estateType") + MapUtils.getString(param, "identityNo", "") +  "1");
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("id",id);
			map.put("estate", MapUtils.getString(param, "estateNo", ""));
			map.put("estateType", MapUtils.getString(param, "estateType", ""));
			String mqChannel=ConfigUtil.getStringValue(Constants.MQ_CHANNEL,ConfigUtil.CONFIG_BASE);
			map.put("channel", mqChannel);
			map.put("type", MapUtils.getString(param, "type", ""));
			map.put("yearNo",MapUtils.getString(param, "yearNo", ""));
			String identityNo = MapUtils.getString(param, "identityNo", "");
			String obligee=null;
			if (!StringUtil.isChineseChar(identityNo)) {
			    identityNo = identityNo.toUpperCase();
			    identityNo=identityNo.replace("(","（").replace(")","）");
			}else{
				obligee=identityNo;
			} 
			map.put("identity",identityNo);
			map.put("obligee",obligee);
			/*
			param.put("noLimitCountHour",true);
			param.put("noLimitCount", true);
			param.put("estateNo", "0116810");
			param.put("yearNo", "2017");
			param.put("identityNo", "上官燕");
			param.put("orderNo", "2016102015535400002");
			param.put("type", 1);
			param.put("uid","789456");
			param.put("uMobile","13813811889");
			param.put("agencyId",1);
			*/
			ExecuteData<Map<String, Object>> data = new ExecuteData<Map<String, Object>>(); 
			data.setId(id);
			data.setData(map);
			RMqClientConnect.sendMsg("queue_archive", map);
			String msg = ExecuteQueue.getInstance().getMsgById(data, 30);
			//HttpUtil http = new HttpUtil();
			//result = http.getRespDataObject(Enums.MODULAR_URL.TOOl.toString(), "/tools/archive/v/addArchive", param, Map.class);
			//String toolsUrl = UrlUtil.getStringValue(Enums.MODULAR_URL.TOOl.toString())+"/tools/archive/v/addArchive";
			//			String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL, ConfigUtil.CONFIG_LINK)+"/tools/archive/v/addArchive";
//			String resultStr = HttpUtil.jsonPost(toolsUrl, param);
//			result = JSON.parseObject(resultStr, RespDataObject.class);
//			if(null!=result&&"SUCCESS".equals(result.getCode())
//					&&" 没有找到任何匹配的数据".equals(MapUtils.getString(result.getData(), "msg", ""))){
//				resultStr = HttpUtil.jsonPost(toolsUrl, param);
//				result = JSON.parseObject(resultStr, RespDataObject.class);
//			}
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("message", msg);
			returnMap.put("createTime", DateUtil.getDateByFmt(new Date(), DateUtil.FMT_TYPE11));
			returnMap.put("archiveId", id);
			result.setData(returnMap);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			param.put("createUid", user.getUid());
			param.put("uid", user.getUid());
			param.put("uMobile",user.getMobile());
			param.put("agencyId",user.getAgencyId());
			param.put("id",id);
			archiveService.getArchiveId(result,param);
		} catch (Exception e){
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
			log.error("查档异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 其他地方调用查档
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody 
	@RequestMapping(value="getArchive")
	public RespDataObject<Map<String, Object>> getArchiveTools(HttpServletRequest request,@RequestBody Map<String,Object> param){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(param, "estateNo", ""))){
				result.setMsg("查询失败,房产证号不能为空!");	
				return result;
			} else if(StringUtils.isBlank(MapUtils.getString(param, "estateType", ""))){
				result.setMsg("查询失败,产权证类型不能为空!");	
				return result;
			} else if(StringUtils.isBlank(MapUtils.getString(param, "identityNo", ""))){
				result.setMsg("查询失败,姓名/身份证号不能为空!");	
				return result;
			}
			
			//取登录人信息
			String id = MD5Utils.MD5Encode(MapUtils.getString(param, "estateNo") + MapUtils.getString(param, "estateType") + MapUtils.getString(param, "identityNo", "") +  "1");
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("id",id);
			map.put("estate", MapUtils.getString(param, "estateNo", ""));
			map.put("estateType", MapUtils.getString(param, "estateType", ""));
			String mqChannel=ConfigUtil.getStringValue(Constants.MQ_CHANNEL,ConfigUtil.CONFIG_BASE);
			map.put("channel", mqChannel);
			map.put("type", MapUtils.getString(param, "type", ""));
			map.put("yearNo",MapUtils.getString(param, "yearNo", ""));
			String identityNo = MapUtils.getString(param, "identityNo", "");
			String obligee=null;
			if (!StringUtil.isChineseChar(identityNo)) {
			    identityNo = identityNo.toUpperCase();
			    identityNo=identityNo.replace("(","（").replace(")","）");
			}else{
				obligee=identityNo;
			} 
			map.put("identity",identityNo);
			map.put("obligee",obligee);
			/*
			param.put("noLimitCountHour",true);
			param.put("noLimitCount", true);
			param.put("estateNo", "0116810");
			param.put("yearNo", "2017");
			param.put("identityNo", "上官燕");
			param.put("orderNo", "2016102015535400002");
			param.put("type", 1);
			param.put("uid","789456");
			param.put("uMobile","13813811889");
			param.put("agencyId",1);
			*/
			ExecuteData<Map<String, Object>> data = new ExecuteData<Map<String, Object>>(); 
			data.setId(id);
			data.setData(map);
			RMqClientConnect.sendMsg("queue_archive", map);
			String msg = ExecuteQueue.getInstance().getMsgById(data, 30);
			//HttpUtil http = new HttpUtil();
			//result = http.getRespDataObject(Enums.MODULAR_URL.TOOl.toString(), "/tools/archive/v/addArchive", param, Map.class);
			//String toolsUrl = UrlUtil.getStringValue(Enums.MODULAR_URL.TOOl.toString())+"/tools/archive/v/addArchive";
			//			String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL, ConfigUtil.CONFIG_LINK)+"/tools/archive/v/addArchive";
//			String resultStr = HttpUtil.jsonPost(toolsUrl, param);
//			result = JSON.parseObject(resultStr, RespDataObject.class);
//			if(null!=result&&"SUCCESS".equals(result.getCode())
//					&&" 没有找到任何匹配的数据".equals(MapUtils.getString(result.getData(), "msg", ""))){
//				resultStr = HttpUtil.jsonPost(toolsUrl, param);
//				result = JSON.parseObject(resultStr, RespDataObject.class);
//			}
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("message", msg);
			returnMap.put("createTime", DateUtil.getDateByFmt(new Date(), DateUtil.FMT_TYPE11));
			returnMap.put("archiveId", id);
			result.setData(returnMap);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e){
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
			log.error("查档异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 根据查档id获取查档
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="v/getArchiveById")
	public RespDataObject<Map<String,Object>> getArchiveById(HttpServletRequest request,@RequestBody Map<String,Object> param){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>(); 
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(param, "archiveId"))){
				result.setMsg("查档id缺失");
				return result;
			}
			param.put("id", MapUtils.getString(param, "archiveId"));
			HttpUtil http = new HttpUtil();
			result = http.getRespDataObject(Constants.LINK_ANJBO_TOOl_URL, "/tools/archive/v/addArchiveAgain", param, Map.class);
			UserDto user = getUserDto(request);
			param.put("uMobile",user.getMobile());
			param.put("createUid", user.getUid());
			param.put("agencyId", user.getAgencyId());
			archiveService.update(result,param);
		} catch (Exception e){
			log.error("查档id获取查档异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("v/delete")
	public RespStatus delete(HttpServletRequest request,@RequestBody ArchiveDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())||StringUtils.isBlank(obj.getArchiveId())){
				result.setMsg("删除失败,删除条件不能为空!");
				return result;
			}
			archiveService.delete(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("删除异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("v/update")
	public RespStatus update(HttpServletRequest request,@RequestBody  ArchiveDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("更新失败,更新条件不能为空!");
				return result;
			}
			archiveService.updateByOrderNo(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("更新异常,异常信息为:",e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("v/detail")
	public RespDataObject<List<ArchiveDto>> detail(HttpServletRequest request,@RequestBody ArchiveDto obj){
		RespDataObject<List<ArchiveDto>> result = new RespDataObject<List<ArchiveDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("获取查档信息参数不能为空");
				return result;
			}
			List<ArchiveDto> list = archiveService.detailByOrderNo(obj.getOrderNo());
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询查档详情异常:",e);
		}
		return result;
	}
	public static void main(String[] args) {
		HttpUtil http = new HttpUtil();
		Map<String,Object> param = new HashMap<String,Object>();
		
		param.put("estateNo", "0116880");
		param.put("yearNo", "2017");
		param.put("identityNo", "习主席");
		param.put("orderNo", "2016102015535400008");
		param.put("type", 1);
		param.put("uid","789456");
		param.put("uMobile","13813811889");
		param.put("agencyId",1);
		//param.put("id", "85ce3deffa04454b86d0e6b043381e7a");94c32986b4d945af82a11bd2abb894aa
		/*
		String str = "{uid=123456, pagesize=0, createTime=1490081937000," +
				" propertyStatus=0, location=null, estateType=0, " +
				"noLimitCount=false, add=false, estateNo=0116800, " +
				"type=1, version=tools-1.0, " +
				"id=85ce3deffa04454b86d0e6b043381e7a, " +
				"time=0, archiveFlowList=[], lazyLoadingTime=30," +
				" yearNo=2015, identityNo=李宝玲, deviceId=null, sid=null, noLimitInfo=false, " +
				"status=2, isQuery=1, obligee=null, ip=null, oldStatus=0, message=该权利人没有房产," +
				" isHidden=0, uMobile=null, createTimeStr=2017-03-21 15:38, houseImgUrl=, " +
				"start=0, device=null, clientIp=null, isRead=1}";*/
		//RespDataObject<Map<String,Object>> result = http.getRespDataObject(Enums.MODULAR_URL.TOOl.toString(), "/tools/archive/v/addArchive", param, Map.class);
		//System.out.println(result.getData());
		String str = " 没有找到任何匹配的数据";
		System.out.println("-"+str.replace("\\s", ""));
	}
}
