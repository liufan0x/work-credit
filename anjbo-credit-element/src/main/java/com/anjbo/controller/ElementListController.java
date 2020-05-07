package com.anjbo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.BoxBaseService;
import com.anjbo.service.BoxBaseWebService;
import com.anjbo.service.ElementService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

/**
 * 智能要件要件管理列表
 * @author xufx
 *
 */
@Controller
@RequestMapping("/credit/element/list/")
public class ElementListController extends BaseController{

	private static final Log log = LogFactory.getLog(ElementListController.class);
	@Resource
	private ElementService elementService;
	@Resource
	private BoxBaseWebService boxBaseWebService;
	
	@Resource
	private BoxBaseService boxBaseService;
 	
	/**
	 * 保存要件订单
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveElementOrder")
	public RespDataObject<Map<String,Object>> saveElementOrder(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
	
			UserDto user=getUserDtoByMysql(request);
			params.put("subsidiary", "总部要件柜");//通过登录人的权限，确定所属的子公司，确定箱子
	
			params.put("operationAuthority", user.getAuthIds());//通过登录人的权限，确定所属的子公司，确定箱子
			params.put("createUid", user.getUid());//通过登录人的权限，确定所属的子公司，确定箱子

			log.info("登录人权限："+user.getAuthIds());
			//String boxNo=MapUtils.getString(params, "boxNo");
			//订单号查询boxNo
			Map<String,Object> m = boxBaseService.selectBoxBaseByOrderNo(params);
			String orderType=MapUtils.getString(params, "orderType");
			String boxNo="";
			if(m!=null&&m.get("boxNo")!=null) {
				boxNo = MapUtils.getString(m, "boxNo");
			}
			if(boxNo==null||boxNo.equals("")) {
				//暂未给您配置要件柜的使用权限，请联系管理员。
				if(user.getAuthIds()==null||user.getAuthIds().size()==0) {
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg("暂未给您配置要件柜的使用权限，请联系管理员");
					return resp;
				}else {
					Map<String,Object>	boxBaseMap=boxBaseService.selectBoxByOperationAuthority(params);
					if(boxBaseMap==null) {
						resp.setCode(RespStatusEnum.FAIL.getCode());
						resp.setMsg("暂未给您配置要件柜的使用权限，请联系管理员");
						return resp;
					}
				}
				if(orderType.equals("3")) {//若为存公章，判断是否存在该部门的公章订单
					
					List<ElementListDto>  orderList=elementService.selectElementBydepartId(params);
					if(orderList!=null&&orderList.size()>0) {
						boxNo=orderList.get(0).getBoxNo();
						params.put("boxNo", orderList.get(0).getBoxNo());
						params.put("orderNo", orderList.get(0).getOrderNo());//避免重新生成订单,共各分子公司的公章放一个订单和一个箱子
					}else {
						Map<String,Object>	boxBaseMap=boxBaseService.randomBox(params);
						if(boxBaseMap==null) {
							resp.setCode(RespStatusEnum.FAIL.getCode());
							resp.setMsg("当前要件箱已存满,暂时不能存入要件");
							return resp;
						}
						boxNo=MapUtils.getString(boxBaseMap, "boxNo");
						log.info("分配的箱号boxNo:"+boxNo);
						params.put("boxNo", boxNo);
					}
				}else {//若不为公章
					Map<String,Object>	boxBaseMap=boxBaseService.randomBox(params);
					if(boxBaseMap==null) {
						resp.setCode(RespStatusEnum.FAIL.getCode());
						resp.setMsg("当前要件箱已存满,暂时不能存入要件");
						return resp;
					}
					boxNo=MapUtils.getString(boxBaseMap, "boxNo");
					log.info("分配的箱号boxNo:"+boxNo);
					params.put("boxNo", boxNo);
				}		
			}

			
			//先判断该箱子是否离线
			int deviceStatus=0;
			
			List<Map<String,Object>> boxList=boxBaseService.selectBoxBaseByBoxNo(params);
			
			if(boxList!=null&&boxList.size()>0) {
				deviceStatus=MapUtils.getInteger(boxList.get(0), "deviceStatus");
			}
			
			if(deviceStatus==0) {
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("箱子已离线,请重新存入或联系管理员");
				return resp;
			}
			
			RespDataObject<Map<String, Object>> boxBaseResp = new RespDataObject<Map<String, Object>>();
			if(MapUtils.getIntValue(params, "isOpen")==1) {
				log.info("箱门已打开"+boxNo);
				boxBaseResp.setCode(RespStatusEnum.SUCCESS.getCode());
			}else {
				//调用设备开箱接口
				boxBaseResp = (RespDataObject<Map<String, Object>>) boxBaseWebService.openElementBox(boxNo);
				log.info("开箱回调接口返回的开箱信息:"+boxBaseResp);
			}
			if(boxBaseResp.getCode().equals("FAIL")) {
				resp.setCode(boxBaseResp.getCode());
				resp.setMsg(boxBaseResp.getMsg());
				if(boxBaseResp.getMsg().equals("开箱超时")) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("boxNo", boxNo);
					resp.setData(map);
				}
				return resp;
			}else {

//				String operationTime=MapUtils.getString(boxBaseResp.getData(), "operationTime");
//				if(operationTime!=null&&!operationTime.equals("")) {
//					params.put("operationTime",stampToDate(operationTime));
//				}else {
					params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				}
				
				params.put("currentHandlerUid",MapUtils.getString(params, "uid"));
				params.put("currentHandler",user.getName());

				elementService.saveElementOrder(params);
				
				
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}	
			//boxBaseWebService.openElementBox(boxNo);

		} catch (Exception e){
			log.error("保存要件订单异常,方法名saveElementOrder,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	/**
	 * 保存同步信贷系统要件订单
	 * @param request
	 * @param obj
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("/saveCredit")
	public RespStatus saveCredit(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			//UserDto user=getUserDtoByMysql(request);
			
			//params.put("currentHandlerUid", "1234");//操作人id，user对象中拿
			//params.put("currentHandlerUid", request.getParameter("uid"));
			//params.put("subsidiary", "总公司");//通过登录人的权限，确定所属的子公司，确定箱子
			elementService.saveCredit(params);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("保存信贷关联订单异常,方法名saveNotCredit,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}*/
	
	/**
	 * 保存同步信贷系统要件订单
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCredit")
	public RespStatus saveCredit(HttpServletRequest request,@RequestBody OrderListDto orderListDto){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			log.info("执行保存同步信贷系统要件订单saveCredit");
			elementService.saveCreditOrderListDto(orderListDto);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("执行保存同步信贷系统要件订单异常,方法名saveCredit,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 查询要件订单列表
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/selectElementList") 
	public RespDataObject<Map<String, Object>> selectElementList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		try {
			UserDto userDto=getUserDtoByMysql(request);
			//根据权限判断展示
			
			//params.put("start", 1);
			params.put("pageSize", MapUtils.getInteger(params, "start")+MapUtils.getInteger(params, "pageSize"));
			

			//HttpUtil httpUtil=new HttpUtil();
			String deptAllUid="";
			if(userDto.getAuthIds()==null){
				deptAllUid = userDto.getUid();
			}else{
				//log.info("权限组authIds："+userDto.getAuthIds());
				//查看全部订单
				if(userDto.getAuthIds().contains("1")&&userDto.getIsEnable()==0&&1==userDto.getAgencyId()){
				//查看部门订单
				}else if(userDto.getAuthIds().contains("2")&&userDto.getIsEnable()==0&&1==userDto.getAgencyId()){
					//日期格式 json转换出错
					userDto.setCreateTime(null);
					userDto.setUpdateTime(null);
					RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
					deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
				//查看自己订单
				}else{
					deptAllUid = userDto.getUid();
				}
			}
			
		
			
			Map<String,Object> respMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectCreditOrderNos", params,Map.class);
			params.put("orderNos", MapUtils.getString(respMap, "orderNos"));
			params.put("updateUid", userDto.getUid());
			params.put("currentHandlerUid", deptAllUid);
			params.put("createUid", userDto.getUid());
			
			params.put("notCreditCurrentHandlerUid", userDto.getUid());//无关联订单,查询受理员和渠道经理看单权限
			
			log.info("当前用户看单参数params"+params);
			retMap.put("brush", assembleBrush(userDto));
			retMap.put("orderList", elementService.selectElementList(params,userDto));
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			log.error("查询要件订单列表数据异常,方法名为selectElementList,异常信息为:", e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 返回app要件列表页检索条件
	 * @param user
	 * @return
	 */
	private Map<String, Object> assembleBrush(UserDto userDto){
		Map<String, Object> map = new HashMap<>();
		//获取是展示添加按钮
		if(userDto.getAuthIds().contains("57")||userDto.getAuthIds().contains("58")) {//判断是否显示存按钮/右上角添加按钮
			map.put("canAdd", 1);
		}else {
			map.put("canAdd", 0);
		}
		//获取城市列表
		List<DictDto> cityListTemp = getDictDtoByType("bookingSzAreaOid");
		List<Map<String, Object>> cityList = new ArrayList<Map<String,Object>>();
		Map<String,Object> cityMap = new HashMap<String,Object>();
		cityMap.put("cityName", "");
		cityMap.put("cityNameValue", "不限");
		cityList.add(cityMap);
		for (DictDto dictDto : cityListTemp) {
			if(StringUtil.isBlank(dictDto.getPcode())) {
				cityMap = new HashMap<String,Object>();
				cityMap.put("cityName", dictDto.getName());
				cityMap.put("cityNameValue", dictDto.getName());
				cityList.add(cityMap);
			}
		}
		map.put("cityList", cityList);
		//获取状态列表
		List<Map<String, Object>> orderStatusList = new ArrayList<Map<String,Object>>();
		Map<String,Object> orderStatusMap = new HashMap<String,Object>();
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "");
		orderStatusMap.put("orderStatusValue", "不限");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "1");
		orderStatusMap.put("orderStatusValue", "未存入");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "2");
		orderStatusMap.put("orderStatusValue", "已存入");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "3");
		orderStatusMap.put("orderStatusValue", "已借出");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "4");
		orderStatusMap.put("orderStatusValue", "已归还");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "5");
		orderStatusMap.put("orderStatusValue", "超时未还");
		orderStatusList.add(orderStatusMap);
		orderStatusMap = new HashMap<String,Object>();
		orderStatusMap.put("orderStatus", "6");
		orderStatusMap.put("orderStatusValue", "已退要件");
		orderStatusList.add(orderStatusMap);
		map.put("orderStatusList", orderStatusList);
		return map;
	}
	
	/**
	 * 要件列表点击进入的要件详情信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/selectElementDetailInfo")
	public RespDataObject<Map<String, Object>> selectElementDetailInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			Map<String, Object> map=elementService.selectElementDetailInfo(params);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载要件详情信息异常,方法名为selectElementDetailInfo,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 获取存取记录列表
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/selectAccessFlowList")
	public RespDataObject<Map<String, Object>> selectAccessFlowList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			List<Map<String, Object>> list=elementService.selectAccessFlowList(params);
			//基本信息(客户姓名，渠道经理，受理员)
			List<ElementListDto> elementList = elementService.selectElementByOrderNo(params);
			map.put("list", list);
			map.put("customerName", elementList.get(0).getCustomerName());
			map.put("channelManagerAcceptMemberName", elementList.get(0).getChannelManagerName()+"/"+elementList.get(0).getAcceptMemberName());
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载获取存取记录列表信息异常,方法名为selectAccessFlowList,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 获取存取记录详情信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/selectAccessFlowDetailInfo")
	public RespDataObject<Map<String, Object>> selectAccessFlowDetailInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto userDto=getUserDtoByMysql(request);
			System.out.println(userDto);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			Map<String, Object> map=elementService.selectAccessFlowDetailInfo(params);
			
			//获取是展示修改按钮权限
			if((userDto.getAuthIds().contains("57")||userDto.getAuthIds().contains("58"))
					&&MapUtils.getIntValue(map, "operationType")!=5) {//5是退详情
				map.put("canAdd", 1);
				if(MapUtils.getBooleanValue(map, "canAddCustomer")) {
					map.put("canAddCustomer", 1);
				}else {
					map.put("canAddCustomer", 0);
				}
			}else {
				map.put("canAdd", 0);
				map.put("canAddCustomer", 0);
			}
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载存取记录详情信息异常,方法名为selectAccessFlowDetailInfo,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 保存暂存信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveSuspendedStoreButtonInfo")
	public RespStatus saveSuspendedStoreButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			//params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			//params.put("orderNo", "No123456");
			//3.暂存或暂退
			//params.put("operationType", 1);
			
			//params.put("currentHandlerUid", "1234");//操作人id，user对象中拿
			//params.put("currentHandlerUid", request.getParameter("uid"));
			params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
			params.put("currentHandler",user.getName());
			elementService.saveSuspendedStoreButtonInfo(params);
		
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e){
			log.error("保存暂存信息,方法名saveSuspendedStoreButtonInfo,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	/**
	 * 点击存按钮返回信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunStoreButtonInfo")
	public RespDataObject<Map<String, Object>> retrunStoreButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			//Map<String, Object> map=elementService.selectAccessFlowTemp(params);
			
			//params.put("currentHandlerUid", "123456");//操作人id，user对象中拿
			//params.put("currentHandlerUid", request.getParameter("uid"));
			params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
			Map<String, Object> map=elementService.retrunStoreButtonInfo(params);
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载暂存信息异常,方法名为retrunStoreButtonInfo,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 保存取要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveTakeElementInfo")
	public RespDataObject<Map<String,Object>> saveTakeElementInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		UserDto user=getUserDtoByMysql(request);
		
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			String elementSet="";
			String elementSetIds[]= {};
			
			String CurrentSet="";
			String CurrentSetIds[]= {};
			
			//判断该批次要件是否已被取走
			List<ElementListDto> listDto=elementService.selectElementByOrderNo(params);
			if(listDto!=null&&listDto.size()>0){
				CurrentSet=listDto.get(0).getCurrentBoxElementSet();
			}
			
			if(CurrentSet!=null) {
				CurrentSetIds=CurrentSet.split(",");
			}else {
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("当前要取出的要件，已经被取出");
				return resp;
			}
			
			
			elementSet=MapUtils.getString(params, "elementSet");
			
			if(elementSet!=null) {
				elementSetIds=elementSet.split(",");
			}
			System.out.println("要件箱中的要件:"+CurrentSet);
			System.out.println(elementSet);
			if(!useLoop(CurrentSetIds, elementSetIds)) {
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("当前要取出的要件，已经被取出");
				return resp;
			}

			params.put("currentHandler",user.getName());
			params.put("currentHandlerUid",user.getUid());
			String boxNo=MapUtils.getString(params, "boxNo");

			RespDataObject<Map<String, Object>> boxBaseResp = new RespDataObject<Map<String, Object>>();
			if(MapUtils.getIntValue(params, "isOpen")==1) {
				log.info("箱门已打开"+boxNo);
				boxBaseResp.setCode(RespStatusEnum.SUCCESS.getCode());
			}else {
				//调用设备开箱接口
				boxBaseResp = (RespDataObject<Map<String, Object>>) boxBaseWebService.openElementBox(boxNo);
				log.info("开箱回调接口返回的开箱信息:"+boxBaseResp);
			}
			
			if(boxBaseResp.getCode().equals("FAIL")) {
				resp.setCode(boxBaseResp.getCode());
				resp.setMsg(boxBaseResp.getMsg());
				if(boxBaseResp.getMsg().equals("开箱超时")) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("boxNo", boxNo);
					resp.setData(map);
				}
				return resp;
			}else {
				
//				String operationTime=MapUtils.getString(boxBaseResp.getData(), "operationTime");
//				if(operationTime!=null&&!operationTime.equals("")) {
//					params.put("operationTime",stampToDate(operationTime));
//				}else {
					params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				}
				
				elementService.saveTakeElementInfo(params);
				
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}

		} catch (Exception e){
			log.error("保存取要件信息,方法名saveTakeElementInfo,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 点击取按钮返回取要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunTakeButtonInfo")
	public RespDataObject<Map<String, Object>> retrunTakeButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

			UserDto user=getUserDtoByMysql(request);

			Map<String, Object> map=elementService.retrunTakeButtonInfo(params);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载取要件界面信息异常,方法名为retrunTakeButtonInfo,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 点击取按钮返回超时未还信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunTimeOutInfo")
	public RespDataObject<Map<String, Object>> retrunTimeOutInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);

			params.put("id", MapUtils.getString(params, "dbId"));
			Map<String, Object> map=elementService.retrunTimeOutInfo(params);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载超时未还面信息异常,方法名为retrunTimeOutInfo,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 点击取按钮返回超时未还信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/retrunRiskOpinion")
	public RespDataObject<Map<String, Object>> retrunRiskOpinion(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);

			Map<String, Object> map=elementService.retrunRiskOpinion(params);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载风控意见信息异常,方法名为retrunRiskOpinion,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 点击还按钮返回还要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunButtonInfo")
	public RespDataObject<Map<String, Object>> retrunButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			//params.put("currentHandlerUid", "1234");
			params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
			Map<String, Object> map=elementService.retrunButtonInfo(params);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载还要件界面信息异常,方法名为retrunButtonInfo,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 保存退要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveRefundElementInfo")
	public RespDataObject<Map<String,Object>> saveRefundElementInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			UserDto user=getUserDtoByMysql(request);
			params.put("currentHandler",user.getName());
			params.put("currentHandlerUid", user.getUid());
			//params.put("boxNo", "深圳/福田1111");//防止一次退还完毕，用于修改箱子使用状态
			String boxNo=MapUtils.getString(params, "boxNo");

			RespDataObject<Map<String, Object>> boxBaseResp = new RespDataObject<Map<String, Object>>();
			if(MapUtils.getIntValue(params, "isOpen")==1) {
				log.info("箱门已打开"+boxNo);
				boxBaseResp.setCode(RespStatusEnum.SUCCESS.getCode());
			}else {
				//调用设备开箱接口
				boxBaseResp = (RespDataObject<Map<String, Object>>) boxBaseWebService.openElementBox(boxNo);
				log.info("开箱回调接口返回的开箱信息:"+boxBaseResp);
			}
			
			if(boxBaseResp.getCode().equals("FAIL")) {
				resp.setCode(boxBaseResp.getCode());
				resp.setMsg(boxBaseResp.getMsg());
				if(boxBaseResp.getMsg().equals("开箱超时")) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("boxNo", boxNo);
					resp.setData(map);
				}
				return resp;
			}else {
				
//				String operationTime=MapUtils.getString(boxBaseResp.getData(), "operationTime");
//				if(operationTime!=null&&!operationTime.equals("")) {
//					params.put("operationTime",stampToDate(operationTime));
//				}else {
					params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				}
				
				
				
				elementService.saveRefundElementInfo(params);
				
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}

		} catch (Exception e){
			log.error("保存退要件信息,方法名saveRefundElementInfo,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 *点击退按钮返回信息要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunRefundButtonInfo")
	public RespDataObject<Map<String, Object>> retrunRefundButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);

			//params.put("orderNo", "No123456");
			
			//params.put("boxNo", "深圳/福田1111");

			Map<String, Object> map=elementService.retrunRefundButtonInfo(params);
			
		
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载退要件界面信息异常,方法名为retrunRefundButtonInfo,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 *点击开箱按钮返回信息要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/retrunOpenButtonInfo")
	public RespDataObject<Map<String, Object>> retrunOpenButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			//params.put("orderNo", "No123456");
			
			//params.put("boxNo", "深圳/福田1111");
			params.put("currentHandlerUid",MapUtils.getString(params, "uid"));
			Map<String, Object> map=elementService.retrunOpenButtonInfo(params);
			
		
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载开箱按钮加载界面信息异常,方法名为retrunOpenButtonInfo,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 保存开箱信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveOpenElementInfo")
	public RespDataObject<Map<String,Object>>  saveOpenElementInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>>  resp = new RespDataObject<Map<String,Object>> ();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			String remark = MapUtils.getString(params, "remark");//借用原因
			if(remark.length()>500) {
				RespHelper.setFailRespStatus(resp, "提取原因不能超过500个字");
				return resp;
			}
			UserDto user=getUserDtoByMysql(request);
			params.put("currentHandler",user.getName());
			params.put("currentHandlerUid",user.getUid());
			
			//params.put("boxNo", "深圳/福田1111");//防止一次退还完毕，用于修改箱子使用状态
			
			String boxNo=MapUtils.getString(params, "boxNo");
			
			RespDataObject<Map<String, Object>> boxBaseResp = new RespDataObject<Map<String, Object>>();
			if(MapUtils.getIntValue(params, "isOpen")==1) {
				log.info("箱门已打开"+boxNo);
				boxBaseResp.setCode(RespStatusEnum.SUCCESS.getCode());
			}else {
				//调用设备开箱接口
				boxBaseResp = (RespDataObject<Map<String, Object>>) boxBaseWebService.openElementBox(boxNo);
				log.info("开箱回调接口返回的开箱信息:"+boxBaseResp);
			}
			
			if(boxBaseResp.getCode().equals("FAIL")) {
				resp.setCode(boxBaseResp.getCode());
				resp.setMsg(boxBaseResp.getMsg());
				if(boxBaseResp.getMsg().equals("开箱超时")) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("boxNo", boxNo);
					resp.setData(map);
				}
				return resp;
			}else {
//				String operationTime=MapUtils.getString(boxBaseResp.getData(), "operationTime");
//				if(operationTime!=null&&!operationTime.equals("")) {
//					params.put("operationTime",stampToDate(operationTime));
//				}else {
					params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				}
				
				
				elementService.saveOpenElementInfo(params);
				
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}

		} catch (Exception e){
			log.error("保存开箱信息,方法名saveOpenElementInfo,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
		
	/**
	 * 保存暂还信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveSuspendedRetrunButtonInfo")
	public RespStatus saveSuspendedRetrunButtonInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			UserDto user=getUserDtoByMysql(request);
			params.put("currentHandler",user.getName());
			
			//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			//params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			//params.put("orderNo", "No123456");
			//3.暂存或暂退
			//params.put("operationType", 1);
			//params.put("currentHandlerUid", "1234");
			params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
			elementService.saveSuspendedRetrunButtonInfo(params);
		
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e){
			log.error("保存暂还信息,方法名saveSuspendedRetrunButtonInfo,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 保存还要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/saveReturnElementOrder")
	public RespDataObject<Map<String,Object>> saveReturnElementOrder(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

			//params.put("boxNo", "深圳/福田1111");//防止一次退还完毕，用于修改箱子使用状态
			//params.put("currentHandlerUid", "1234");//操作人id，user对象中拿
			
			UserDto user=getUserDtoByMysql(request);
			params.put("currentHandler",user.getName());
			String boxNo=MapUtils.getString(params, "boxNo");

			RespDataObject<Map<String, Object>> boxBaseResp = new RespDataObject<Map<String, Object>>();
			if(MapUtils.getIntValue(params, "isOpen")==1) {
				log.info("箱门已打开"+boxNo);
				boxBaseResp.setCode(RespStatusEnum.SUCCESS.getCode());
			}else {
				//调用设备开箱接口
				boxBaseResp = (RespDataObject<Map<String, Object>>) boxBaseWebService.openElementBox(boxNo);
				log.info("开箱回调接口返回的开箱信息:"+boxBaseResp);
			}
			
			if(boxBaseResp.getCode().equals("FAIL")) {
				resp.setCode(boxBaseResp.getCode());
				resp.setMsg(boxBaseResp.getMsg());
				if(boxBaseResp.getMsg().equals("开箱超时")) {
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("boxNo", boxNo);
					resp.setData(map);
				}
				return resp;
			}else {
//				String operationTime=MapUtils.getString(boxBaseResp.getData(), "operationTime");
//				if(operationTime!=null&&!operationTime.equals("")) {
//					params.put("operationTime",stampToDate(operationTime));
//				}else {
					params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				}
				
				params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
				elementService.saveReturnElementOrder(params);
				
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}

		} catch (Exception e){
			log.error("保存还要件信息,方法名saveReturnElementOrder,异常信息为:",e);
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 获取公章部门接口
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/selectSealDepartmentList")
	public RespDataObject<List<Map<String, Object>>> selectSealDepartmentList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<List<Map<String, Object>>> result = new RespDataObject<List<Map<String, Object>>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

			UserDto user=getUserDtoByMysql(request);
			System.out.println(user);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			List<Map<String, Object>> list=elementService.selectSealDepartmentList(params);
			
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载获公章所属部门信息异常,方法名为selectsealDepartmentList,异常信息为:", e);
		}
		return result;
	}
	
	
	/**
	 * 获取公章已存要件界面信息，刷新界面
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/selectSealElementInfo")
	public RespDataObject<Map<String, Object>> selectSealElementInfo(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			UserDto user=getUserDtoByMysql(request);
			//System.out.println(user);
			/*//1.操作人Uid  UserDto user=getUserDtoByMysql(request);
			params.put("currentHandlerUid", 123);//user中获取当前登录人的Uid
			//2.订单orderNo
			params.put("orderNo", "No123456");
			//3.暂存或暂退
			params.put("operationType", 1);*/

			Map<String, Object> sealElement=elementService.selectSealElementInfo(params);
			
			result.setData(sealElement);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载公章已存要件界面信息异常,方法名为selectSealElementInfo,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 修改部门已存公章信息
	 *1. 已借出（包括超时未还）的公章不支持修改；
	 *2.用户已申请借用的公章暂时不能修改
	 *3.目前只支持少选，不支持多选，即用户可以将已存入或已归还的公章数量减少，不支持添加，之前漏存或漏还的要件，可通过【存】、【还】等操作来存进去或还回去。
	 * @param request
	 * @param params
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping("/v/updateSealElementInfo")
	public RespStatus updateSealElementInfo(HttpServletRequest request,@RequestBody Map<String, Object> params) {
		RespStatus resp =new RespStatus();
		try {
			UserDto user = getUserDto(request);
			//存取记录表数据
			params.put("currentHandlerUid",user.getUid());
			params.put("currentHandler",user.getName());
			elementService.updateSealElementInfo(params);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, "更新失败");
		}
		return resp;
	}*/
	
	
	/**
	 * 信贷是否收到要件,要件详情接口
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/receiveElementInfo")
	public RespDataObject<Map<String,Object>> receiveElementInfo(HttpServletRequest request,@RequestBody Map<String,Object> orderNo){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			//UserDto user=getUserDtoByMysql(request);

			Map<String,Object>  map=elementService.receiveElementInfo(orderNo);
			
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("获取信贷系统要件详情入口信息:", e);
		}
		return result;
	}
	
	
	/* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(s!=null&&!s.equals("")) {
        	long lt = new Long(s);
 	        Date date = new Date(lt);
 	        res = simpleDateFormat.format(date);
        }
       
        return res;
    }
    
	public static boolean useLoop(String[] a, String[] b) {  
	       boolean flag = false;    
	       if(a==null||b==null) {
				 return flag;
			}
	       
	       for(String i:b) {
	    	   boolean  f=Arrays.asList(a).contains(i);
	    	   if(f) {
	    		   flag=true;
	    	   }else {
	    		   return false;
	    	   }
	       }
	       return flag;    
	   }  
	
	/**
	 * 更新要件信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updateElementOrder")
	public RespStatus updateElementOrder(HttpServletRequest request,@RequestBody Map<String, Object> map){
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			map.put("createUid", user.getUid());
			map.put("createName", user.getName());
			//存取记录表数据
			map.put("currentHandlerUid",user.getUid());
			map.put("currentHandler",user.getName());
			int i = elementService.updateElementFile(map);
			//已申请借出或者已借出或者超时未还不允许修改
			if(i==1) {
				resp.setCode("-1");
				resp.setMsg("部分要件已被客户申请借用，暂时不能修改，请重新确认信息后再试!");
				return resp;
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
		return resp;
	}
	
	/**
	 * 更新客户基本信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updateElementCustomerInfo")
	public RespStatus updateElementCustomerInfo(HttpServletRequest request,@RequestBody Map<String, Object> map){
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			map.put("createUid", user.getUid());
			map.put("createName", user.getName());
			//存取记录表数据
			map.put("currentHandlerUid",user.getUid());
			map.put("currentHandler",user.getName());
			elementService.updateElementCustomerInfo(map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
		return resp;
	}
	
	/**
	 * 修改要件基本信息详情
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updateElementBaseInfoDetail")
	public RespDataObject<Map<String,Object>> updateElementBaseInfoDetail(HttpServletRequest request,@RequestBody Map<String, Object> map){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> m = elementService.updateElementBaseInfoDetail(map);
			RespHelper.setSuccessDataObject(resp, m);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, "获取修改要件详情失败");
		}
		return resp;
	}
	
	/**
	 * 修改要件详情
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updateElementDetail")
	public RespDataObject<Map<String,Object>> editElementDetail(HttpServletRequest request,@RequestBody Map<String, Object> map){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> m = elementService.updateElementDetail(map);
			RespHelper.setSuccessDataObject(resp, m);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, "获取修改要件详情失败");
		}
		return resp;
	}
	
	/**
	 * 要件检索添加
	 */
	@ResponseBody
	@RequestMapping("v/searchElement")
	public RespData<Map<String,Object>> searchElement(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespData<Map<String,Object>> resp = new RespData<Map<String,Object>>();
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//获取自定义名称的要件，没有具体内容
			Map<String,Object> pageParamMap = new HashMap<String,Object>();
			pageParamMap.put("pageClass", "tbl_element_elementFile_page");
			pageParamMap.put("regionClass", MapUtils.getString(params, "regionClass"));//eleFilePayRadio
			RespData<PageTabRegionFormConfigDto> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist = respData.getData();
			if("eleFilePayRadio".equals(MapUtils.getString(params, "regionClass"))) {
				for(PageTabRegionFormConfigDto dto:configlist) {
					Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
					List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
					dtoList.add(dto);
					if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("银行卡")) {
						dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
						dtoMap.put("hasAdd", "2");//是否可添加
						dtoMap.put("title",dto.getTitle());
						dtoMap.put("data", dtoList);
						dtoMap.put("type", 7);
						list.add(dtoMap);
					}
				}
			}else {
				for(PageTabRegionFormConfigDto dto:configlist) {
					Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
					List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
					dtoList.add(dto);
					if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("结婚证")&&!dto.getTitle().equals("离婚证")&&!dto.getTitle().equals("银行卡")
							&&!dto.getTitle().equals("户口本")&&!dto.getTitle().equals("房产证")) {
						dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
						dtoMap.put("hasAdd", "2");//是否可添加
						dtoMap.put("title",dto.getTitle());
						dtoMap.put("data", dtoList);
						dtoMap.put("type", 7);
						list.add(dtoMap);
					}
				}
			}
			
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, "查询失败");
		}
		return resp;
	}
    
    public static void main(String[] args) {
		//System.out.println(stampToDate("1516354933000"));
    	String a[]= {"1331","1332","1336","1337"};
		String b[]= {"1336","1337","1331"};
		System.out.println(useLoop(a,b));
	}
}
