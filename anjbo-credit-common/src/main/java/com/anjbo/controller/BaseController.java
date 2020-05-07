package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.FacesignRecognitionDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;

public class BaseController {
	protected Log logger = LogFactory.getLog(this.getClass());

	HttpUtil httpUtil = null;

	public BaseController() {
		httpUtil = new HttpUtil();
	}

	/**
	 * 获取订单基本信息
	 * cityCode				城市代码
	 * cityName				城市名称
	 * productCode			产品代码
	 * productName			产品名称
	 * agencyId				机构Id
	 * state				状态
	 * processId			流程Id
	 * previousHandlerUid	上一节点操作人Uid
	 * previousHandler		上一节点操作人名称
	 * currentHandlerUid	当前处理人Uid
	 * currentHandler		当前处理人名称
	 * 
	 * @param orderNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderBaseInfo(String orderNo){
		Map<String, Object> orderBaseInfoMap = (Map<String, Object>) RedisOperator.get(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO+orderNo);

		//测试代码 start
		//		orderBaseInfoMap = new HashMap<String, Object>();
		//		orderBaseInfoMap.put("orderNo", orderNo);
		//		orderBaseInfoMap.put("agencyId", "1");
		//		orderBaseInfoMap.put("cityCode", "4403");
		//		orderBaseInfoMap.put("cityName", "深圳市");
		//		orderBaseInfoMap.put("productCode", "04");
		//		orderBaseInfoMap.put("productName", "云按揭");
		//		orderBaseInfoMap.put("state", "待提交评估");
		//		orderBaseInfoMap.put("processId", "forensicsMortgage");
		//		orderBaseInfoMap.put("previousHandlerUid", "123456");
		//		orderBaseInfoMap.put("previousHandler", "李灿");
		//		orderBaseInfoMap.put("currentHandlerUid", "123456");
		//		orderBaseInfoMap.put("currentHandler", "李灿");
		//测试代码 end

		if(orderBaseInfoMap != null && !orderBaseInfoMap.isEmpty()){
			return orderBaseInfoMap;
		}else{
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 设置订单基本信息
	 * orderNo				订单号
	 * map				        基本信息
	 */
	public void setOrderBaseInfo(String productCode,String orderNo){
		Map<String,Object> orderBaseInfoMap = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		if("10000".equals(productCode)){
			params.put("tblName", "tbl_cm_");
			RespDataObject<Map<String,Object>> respD = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/select", params, Map.class);
			orderBaseInfoMap = respD.getData();
		}
		if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)||"06".equals(productCode)||"07".equals(productCode)){
			RespDataObject<Map<String,Object>> respD = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", params, Map.class);
			orderBaseInfoMap = respD.getData();
		}
		RedisOperator.set(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO+orderNo,orderBaseInfoMap);
	}

	/**
	 * 从Map中提取订单基本信息
	 * cityCode				城市代码
	 * cityName				城市名称
	 * productCode			产品代码
	 * productName			产品名称
	 * agencyId				机构Id
	 * state				状态
	 * processId			流程Id
	 * previousHandlerUid	上一节点操作人Uid
	 * previousHandler		上一节点操作人名称
	 * currentHandlerUid	当前处理人Uid
	 * currentHandler		当前处理人名称
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> clearOrderBaseInfoMap(Map<String, Object> params){
		if(params != null && !params.isEmpty()){
			Map<String, Object> orderBaseInfoMap = new HashMap<String, Object>();
			orderBaseInfoMap.put("agencyId", MapUtils.getString(params, "agencyId"));
			orderBaseInfoMap.put("agencyId", "1");
			orderBaseInfoMap.put("cityCode", "4403");
			orderBaseInfoMap.put("cityName", "深圳市");
			orderBaseInfoMap.put("productCode", "10000");
			orderBaseInfoMap.put("productName", "云按揭");
			orderBaseInfoMap.put("state", "待提交评估");
			orderBaseInfoMap.put("processId", "roundTurn");
			orderBaseInfoMap.put("previousHandlerUid", "123456");
			orderBaseInfoMap.put("previousHandler", "李灿");
			orderBaseInfoMap.put("currentHandlerUid", "123456");
			orderBaseInfoMap.put("currentHandler", "李灿");
			return orderBaseInfoMap;
		}else{
			return params;
		}
	}

	/**
	 * 获取当前登录用户信息
	 * @param request
	 * @return
	 */

	public UserDto getUserDto(HttpServletRequest request){
		String uid = request.getHeader("uid") == null ? "" : (String) request.getHeader("uid");
		String deviceId = request.getHeader("deviceId") == null ? "" : (String) request.getHeader("deviceId");
		if(StringUtils.isNotEmpty(uid)){
			uid = RedisOperator.getString(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(uid) + ":" + MD5Utils.MD5Encode(deviceId));
			//信贷查询不到的用户，返回含有快鸽uid的用户对象
			return CommonDataUtil.getUserDtoByUidAndMobile(uid);
		}else{
			if(request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY) != null) {
				uid = request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY).toString();
				return CommonDataUtil.getUserDtoByUidAndMobile(uid);
			}
			return null;
		}

	}

	/**
	 * 获取当前登录数据库用户信息
	 * @param request
	 * @return
	 */

	public UserDto getUserDtoByMysql(HttpServletRequest request){
		String uid = request.getAttribute("uid") == null ? "" : (String) request.getAttribute("uid");
		String deviceId = request.getAttribute("deviceId") == null ? "" : (String) request.getAttribute("deviceId");
		if(StringUtils.isNotEmpty(uid)){
			uid = RedisOperator.getString(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(uid) + ":" + MD5Utils.MD5Encode(deviceId));
			UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			UserDto mysqlUser = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/user/base/v/get4Auth", map, UserDto.class);
			if(userDto != null && mysqlUser != null ){
				mysqlUser.setAgencyChanlManUid(userDto.getAgencyChanlManUid());
			}
			return null==userDto ? new UserDto() : ((mysqlUser!=null&&mysqlUser.getUid()!=null)?mysqlUser:userDto);
		}else{
			if(request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY) != null) {
				uid = request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY).toString();
				return CommonDataUtil.getUserDtoByUidAndMobile(uid);
			}
			return null;
		}

	}

	/**
	 * 获取当前登录用户redis中的用户信息
	 * @param request
	 * @return
	 */

	public UserDto getUserDtoByRedis(HttpServletRequest request){
		String uid = request.getAttribute("uid") == null ? "" : (String) request.getAttribute("uid");
		String deviceId = request.getAttribute("deviceId") == null ? "" : (String) request.getAttribute("deviceId");
		if(StringUtils.isNotEmpty(uid)){
			uid = RedisOperator.getString(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(uid) + ":" + MD5Utils.MD5Encode(deviceId));
			//信贷查询不到的用户，返回含有快鸽uid的用户对象
			return CommonDataUtil.getUserDtoByUidAndMobile(uid);
		}else{
			if(request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY) != null) {
				uid = request.getSession(false).getAttribute(Constants.LOGIN_USER_KEY).toString();
				return CommonDataUtil.getUserDtoByUidAndMobile(uid);
			}
			return null;
		}

	}



	@SuppressWarnings("unchecked")
	public List<UserDto> getAllUserDtoList(){
		List<UserDto> userList = (List<UserDto>) RedisOperator.get("userList");
		//		userList = httpUtil.getList(Enums.MODULAR_URL.CREDIT.toString(), "/credit/user/base/v/userList", null, UserDto.class);
		return userList;
	}

	/**
	 * 查询用户 
	 * 不传参则查所有，传机构则参机构用户(可传多个)
	 * @return
	 */
	//	public List<UserDto> getUserDtoList(int... agencyIds){
	//		List<UserDto> list = getAllUserDtoList();
	//		if(agencyIds.length == 0){
	//			return list;
	//		}
	//		List<UserDto> tempList = new ArrayList<UserDto>();
	//		for (UserDto userDto : list) {
	//			if(Arrays.binarySearch(agencyIds,userDto.getAgencyId()) >= 0){
	//				tempList.add(userDto);
	//			}
	//		}
	//		return tempList;
	//	}

	/**
	 * 根据Uid 查询用户 
	 * 不传参则查所有，传机构则参机构用户(可传多个)
	 * @param uid
	 * @return
	 */
	public UserDto getUserDtoByUid(String uid){
		//		List<UserDto>  userList = getUserDtoList(agencyIds);
		//		if(userList!=null){
		//			for (UserDto userDto : userList) {
		//				if(userDto.getUid().equals(uid)){
		//					return userDto;
		//				}
		//			}
		//		}
		return CommonDataUtil.getUserDtoByUidAndMobile(uid);
	}

	//	/**
	//	 * 根据Mobile 查询用户
	//	 * @Author KangLG<2017年11月15日>
	//	 * @param mobile
	//	 * @return
	//	 */
	//	public UserDto getUserDtoByMobile(String mobile){
	//		if(StringUtils.isNotEmpty(mobile)){
	//			List<UserDto>  userList = getAllUserDtoList();
	//			for (UserDto userDto : userList) {
	//				if(mobile.equals(userDto.getMobile())){
	//					return userDto;
	//				}
	//			}
	//		}		
	//		return null;
	//	}

	//	/**
	//	 * 根据CityCode 查询用户
	//	 * 不传参则查所有，传机构则参机构用户(可传多个)
	//	 * @param uid
	//	 * @return
	//	 */
	//	public List<UserDto> getUserDtoByCityCode(String cityCode,int agencyIds){
	//		List<UserDto> userList = getUserDtoList(agencyIds);
	//		if(StringUtils.isEmpty(cityCode)){
	//			return userList;
	//		}
	//		List<UserDto> tempList = new ArrayList<UserDto>();
	//		for (UserDto userDto : userList) {
	//			if(userDto.getCityCode().equals(cityCode)){
	//				tempList.add(userDto);
	//			}
	//		}
	//		return tempList;
	//	}



	/**
	 * 根据获取产品
	 * @param agencyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductDto> getProductDtos(){
		List<ProductDto> productList = (List<ProductDto>) RedisOperator.get("productList");
		if(productList == null){
			productList = (List<ProductDto>) RedisOperator.get("productList");
		}
		return productList;
	}

	/**
	 * 根据产品Code获取产品流程
	 * @param productCode
	 * @return
	 */
	public List<ProductProcessDto> getProductProcessDto(int productId){
		List<ProductDto> list = getProductDtos();
		for (ProductDto productDto : list) {
			if(productDto.getId() == productId){
				return productDto.getProductProcessDtos();
			}
		}
		return new ArrayList<ProductProcessDto>();
	}

	/**
	 * 比较两个流程的前后
	 * @param productId	产品Id
	 * @param startProcessId 开始流程
	 * @param endProcessId	结束流程
	 * @return
	 */
	public boolean compareProcessAround(int productId,String startProcessId,String endProcessId){
		List<ProductProcessDto> list = getProductProcessDto(productId);
		int startSort = 0;
		int endSort = 0;
		for (ProductProcessDto productProcessDto : list){
			if(productProcessDto.getProcessId().equals(startProcessId)){
				startSort = productProcessDto.getSort();
			}
			if(productProcessDto.getProcessId().equals(endProcessId)){
				endSort = productProcessDto.getSort();
			}
		}
		return startSort <= endSort;
	}

	/**
	 * 获取流程名称
	 * @param productId
	 * @param processId
	 * @return
	 */
	public String getProcessName(int productId,String processId){
		List<ProductProcessDto> list = getProductProcessDto(productId);
		for (ProductProcessDto productProcessDto : list) {
			if(productProcessDto.getProcessId().equals(processId)){
				return productProcessDto.getProcessName();
			}
		}
		return "";
	}

	/**
	 * 获取下一个流程
	 * @param productId
	 * @param processId
	 * @return
	 */
	public ProductProcessDto getNextProcess(int productId,String processId){
		List<ProductProcessDto> list = getProductProcessDto(productId);
		boolean tempfl = false;
		for (ProductProcessDto productProcessDto : list) {
			if(tempfl){
				return productProcessDto;
			}
			if(productProcessDto.getProcessId().equals(processId)){
				tempfl = true;
			}
		}
		return null;
	}

	private List<DictDto> dictDtoList = null;

	@SuppressWarnings("unchecked")
	public List<DictDto> getDictDto(String type){
//		if(dictDtoList == null){
//			dictDtoList = (List<DictDto>) RedisOperator.get("dictList");
//			if(dictDtoList == null){
//				httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/common/base/v/initData", null);
//				dictDtoList = (List<DictDto>) RedisOperator.get("dictList");
//			}
//		}
		DictDto dictDto = new DictDto();
		dictDto.setType(type);
		List<DictDto> dictDtoListTemp  = httpUtil.getList(Constants.LINK_CREDIT, "/credit/data/dict/v/search", dictDto,DictDto.class);
		
//		List<DictDto> dictDtoListTemp = new ArrayList<DictDto>();
//		for (DictDto dictDto : dictDtoList) {
//			if(type.equals(dictDto.getType())){
//				dictDtoListTemp.add(dictDto);
//			}
//		}
		return dictDtoListTemp;
	}

	/**
	 * 根据类型获取字典
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DictDto> getDictDtoByType(String type){
		List<DictDto> dictDtos = getDictDto(type);
		if(dictDtos == null){
			httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/common/base/v/initData", null);
			dictDtos = (List<DictDto>) RedisOperator.get(type);
		}
		return dictDtos;
	}

	//	/**
	//	 * 通过银行Id获取银行名称
	//	 * @param bankId
	//	 * @return
	//	 */
	//	public String getBankNameByBankId(int bankId){
	//		List<BankDto> bankDtos = getAllBankList();
	//		for (BankDto bankDto : bankDtos) {
	//			if(bankDto.getId() == bankId){
	//				return bankDto.getName();
	//			}
	//		}
	//		return "";
	//	}
	//	
	//	/**
	//	 * 通过支行银行Id获取支行银行名称
	//	 * @param bankId
	//	 * @return
	//	 */
	//	public String getSubBankNameBySubBankId(int subBankId){
	//		List<SubBankDto> subBankDtos = getAllSubBankList();
	//		for (SubBankDto subBankDto : subBankDtos) {
	//			if(subBankDto.getId() == subBankId){
	//				return subBankDto.getName();
	//			}
	//		}
	//		return "";
	//	}

	//	/**
	//	 * 国土局
	//	 * @param bureauId
	//	 * @return
	//	 */
	//	public String getBureauName(String type,String bureauId){
	//		List<DictDto> dictDtos = getDictDtoByType(type);
	//		for (DictDto dictDto : dictDtos) {
	//			if(dictDto.getCode().equals(bureauId)){
	//				return dictDto.getName();
	//			}
	//		}
	//		return "";
	//	}

	/**
	 * 国土局
	 * @param bureauId
	 * @return
	 */
	public String getBureauName(String code){
		List<DictDto> dictDtos = getDictDtoByType("landBureau");
		for (DictDto dictDto : dictDtos) {
			if(dictDto.getCode().equals(code)){
				return dictDto.getName();
			}
		}
		return "";
	}

	/**
	 * 判断是否已经提交
	 * @param orderNo
	 * @param processId
	 * @return
	 */
	public boolean isSubmit(String orderNo,String processId){
		OrderFlowDto orderFlowDto = new OrderFlowDto();
		orderFlowDto.setOrderNo(orderNo);
		orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
		if(orderFlowDto != null && orderFlowDto.getCurrentProcessId().equals(processId)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否已经撤回
	 * @param orderNo
	 * @param processId
	 * @return
	 */
	public boolean isWithdraw(String orderNo,String processId){
		OrderFlowDto orderFlowDto = new OrderFlowDto();
		orderFlowDto.setOrderNo(orderNo);
		orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
		if(orderFlowDto != null && orderFlowDto.getNextProcessId().equals(processId)){
			return false;
		}
		return true;
	}

	/**
	 * 判断是否已经退回
	 * @param orderNo
	 * @param processId
	 * @return
	 */
	public boolean isBack(String orderNo,String processId){
		OrderFlowDto orderFlowDto = new OrderFlowDto();
		orderFlowDto.setOrderNo(orderNo);
		orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
		if(orderFlowDto != null && StringUtils.isNotEmpty(orderFlowDto.getBackReason()) && orderFlowDto.getNextProcessId().equals(processId)){
			return true;
		}
		return false;
	}

	//	/**
	//	 * 获取All银行
	//	 * @return
	//	 */
	//	
	//	private List<BankDto> bankList = null;
	//	
	//	@SuppressWarnings("unchecked")
	//	public List<BankDto> getAllBankList(){
	//		if(bankList == null){
	//			bankList = (List<BankDto>) RedisOperator.get("allBankList");
	//			if(bankList == null){
	//				bankList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/common/base/v/selectBankAll", null, BankDto.class);
	//			}
	//		}
	//		return bankList;
	//	}
	//	
	//	/**
	//	 * 获取All支行
	//	 * @return
	//	 */
	//	private List<SubBankDto> subBankList = null;
	//	
	//	@SuppressWarnings("unchecked")
	//	public List<SubBankDto> getAllSubBankList(){
	//		if(subBankList == null ){
	//			subBankList = (List<SubBankDto>) RedisOperator.get("allSubBankList");
	//			if(subBankList == null){
	//				subBankList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/common/base/v/selectSubBankAll", null, SubBankDto.class);
	//			}
	//		}
	//		return subBankList;
	//	}

	/**
	 * 
	 * 
	 * 进入下一个节点
	 * @param orderFlowDto
	 * @param orderListDto
	 * @return
	 */
	public RespStatus goNextNode(OrderFlowDto orderFlowDto,OrderListDto orderListDto){
		if(orderListDto==null){
			orderListDto = new OrderListDto();
		}
		orderListDto.setOrderNo(orderFlowDto.getOrderNo());
		orderFlowDto.setUpdateUid(orderFlowDto.getHandleUid());
		int productId = 440301;
		OrderListDto tempOrderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto,OrderListDto.class);
		if(tempOrderListDto != null){
			productId = NumberUtils.toInt(tempOrderListDto.getCityCode()+tempOrderListDto.getProductCode());
		}

		if(StringUtils.isEmpty(orderFlowDto.getNextProcessId())){
			ProductProcessDto productProcessDto = getNextProcess(productId, orderFlowDto.getCurrentProcessId());
			orderFlowDto.setNextProcessId(productProcessDto.getProcessId());
			orderFlowDto.setNextProcessName(productProcessDto.getProcessName());
		}
		//上一个处理人
		orderListDto.setPreviousHandlerUid(orderFlowDto.getHandleUid());
		orderListDto.setPreviousHandler(orderFlowDto.getHandleName());

		if(StringUtils.isEmpty(orderListDto.getState())){
			//当前节点
			orderListDto.setProcessId(orderFlowDto.getNextProcessId());
			orderListDto.setState("待"+orderFlowDto.getNextProcessName());
		}

		//同步数据到智能要件
		try {
			logger.info("执行了baseControl同步信贷件订单到智能要件"+"state为"+orderListDto.getState());
			RespStatus elementResp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/list/saveCredit", orderListDto);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		//退回的单判断是否重新走流程
		OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
		if(flowDto!=null&&StringUtils.isNotBlank(flowDto.getBackReason())&&flowDto.getIsNewWalkProcess()==2){
			//不重新走流程
			String processName = getProcessName(productId,flowDto.getCurrentProcessId());
			orderFlowDto.setNextProcessId(flowDto.getCurrentProcessId());
			orderFlowDto.setNextProcessName(processName);
			orderListDto.setProcessId(flowDto.getCurrentProcessId());
			orderListDto.setState("待"+processName);
			orderListDto.setCurrentHandlerUid(flowDto.getHandleUid());
			orderListDto.setCurrentHandler(CommonDataUtil.getUserDtoByUidAndMobile(flowDto.getHandleUid()).getName());
		}

		RespStatus resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", orderListDto);
		if(!resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
			return resp;
		}
		//app再次编辑不插入流水
		if(flowDto!=null && flowDto.getCurrentProcessId().equals(orderFlowDto.getCurrentProcessId())){
			return resp;

		}else if("wanjie".equals(orderListDto.getProcessId())){
			String uid = orderFlowDto.getHandleUid();
			String name = orderFlowDto.getHandleName();
			httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/flow/v/addOrderFlow", orderFlowDto);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderListDto.getOrderNo());
			orderFlowDto.setUpdateUid(uid);
			orderFlowDto.setCurrentProcessId("wanjie");
			orderFlowDto.setCurrentProcessName("已完结");
			orderFlowDto.setNextProcessId("-");
			orderFlowDto.setNextProcessName("-");
			orderFlowDto.setHandleUid(uid);
			orderFlowDto.setHandleName(name);
			return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/flow/v/addOrderFlow", orderFlowDto);
		}else if("closeOrder".equals(orderListDto.getProcessId())){
			String uid = orderFlowDto.getHandleUid();
			String name = orderFlowDto.getHandleName();
			httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/flow/v/addOrderFlow", orderFlowDto);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderListDto.getOrderNo());
			orderFlowDto.setUpdateUid(uid);
			orderFlowDto.setCurrentProcessId("closeOrder");
			orderFlowDto.setCurrentProcessName("订单已停止");
			orderFlowDto.setNextProcessId("-");
			orderFlowDto.setNextProcessName("-");
			orderFlowDto.setHandleUid(uid);
			orderFlowDto.setHandleName(name);
			return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/flow/v/addOrderFlow", orderFlowDto);
		}else{
			return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/flow/v/addOrderFlow", orderFlowDto);
		}
	}

	/**
	 * 根据当前部门获取所有部门编号
	 * @param alldeps
	 * @param rootId
	 * @return
	 */
	public Set<Integer> getAllDeptSet(List<DeptDto> alldeps,int rootId){
		Set<Integer> sTemp = new HashSet<Integer>();
		Set<Integer> sTempChd = new HashSet<Integer>();
		sTempChd.add(rootId);
		while(sTempChd.size()>0){
			sTemp.addAll(sTempChd);
			Set<Integer> sTempChdTemp = new HashSet<Integer>();
			for (DeptDto deptDto : alldeps) {
				if(sTempChd.contains(deptDto.getPid())){
					sTempChdTemp.add(deptDto.getId());
				}
			}
			sTempChd = sTempChdTemp;
		}
		return sTemp;
	}

	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	//	public String getCreditOrderNo(String orderNo){
	//		OrderBaseBorrowRelationDto order = new OrderBaseBorrowRelationDto();
	//		order.setOrderNo(orderNo);
	//		order = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/relation/v/loanOrderNo",order, OrderBaseBorrowRelationDto.class);
	//		if(null==order){
	//			return orderNo;
	//		}
	//		return order.getRelationOrderNo();
	//	}

	/**
	 * 录入人脸识别初始化数据
	 * @param orderBaseCustomerDto
	 */
	public void insertFacesignRecognition(OrderBaseCustomerDto orderBaseCustomerDto){
		if(orderBaseCustomerDto!=null){
			String orderNo = orderBaseCustomerDto.getOrderNo();
			FacesignRecognitionDto dto=new FacesignRecognitionDto();
			dto.setOrderNo(orderNo);
			boolean f = false;
			List<FacesignRecognitionDto> respList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognitionDetail", dto, FacesignRecognitionDto.class);
			for (FacesignRecognitionDto facesignRecognitionDto : respList) {
				if(StringUtils.isBlank(orderBaseCustomerDto.getCustomerCardNumber())||(facesignRecognitionDto.getCustomerCardNumber().equals(orderBaseCustomerDto.getCustomerCardNumber())&&"借款人".equals(facesignRecognitionDto.getCustomerType()))){
					f = true;
				}
			}
			if(!f){
				//录入
				FacesignRecognitionDto facesignRecognitionDto = new FacesignRecognitionDto();
				facesignRecognitionDto.setCreateUid(orderBaseCustomerDto.getCreateUid());
				facesignRecognitionDto.setOrderNo(orderNo);
				facesignRecognitionDto.setCustomerName(orderBaseCustomerDto.getCustomerName());
				facesignRecognitionDto.setCustomerCardNumber(orderBaseCustomerDto.getCustomerCardNumber());
				facesignRecognitionDto.setCustomerType("借款人");
				httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognition", facesignRecognitionDto, FacesignRecognitionDto.class);
			}
			if(null!=orderBaseCustomerDto.getCustomerBorrowerDto()&&orderBaseCustomerDto.getCustomerBorrowerDto().size()>0){
				List<OrderBaseCustomerBorrowerDto> temp = new ArrayList<OrderBaseCustomerBorrowerDto>();
				List<OrderBaseCustomerBorrowerDto> customerBorrowDto =  orderBaseCustomerDto.getCustomerBorrowerDto();
				for (OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto : customerBorrowDto) {
					boolean flag = false;
					for (FacesignRecognitionDto facesignRecognitionDto : respList) {
						if(StringUtils.isBlank(orderBaseCustomerBorrowerDto.getBorrowerCardNumber())||facesignRecognitionDto.getCustomerCardNumber().equals(orderBaseCustomerBorrowerDto.getBorrowerCardNumber())&&"共同借款人".equals(facesignRecognitionDto.getCustomerType())){
							flag = true;
						}
					}
					if(!flag){
						temp.add(orderBaseCustomerBorrowerDto);
					}
				}
				if(temp.size()>0){
					//录入
					List<FacesignRecognitionDto> list = new ArrayList<FacesignRecognitionDto>();
					for (OrderBaseCustomerBorrowerDto obj : temp) {
						FacesignRecognitionDto facesignRecognitionDto = new FacesignRecognitionDto();
						facesignRecognitionDto.setCreateUid(orderBaseCustomerDto.getCreateUid());
						facesignRecognitionDto.setOrderNo(orderNo);
						facesignRecognitionDto.setCustomerName(obj.getBorrowerName());
						facesignRecognitionDto.setCustomerCardNumber(obj.getBorrowerCardNumber());
						facesignRecognitionDto.setCustomerType("共同借款人");
						list.add(facesignRecognitionDto);
					}
					httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/insertFacesignRecognitions", list);
				}
			}
			if(null!=orderBaseCustomerDto.getCustomerGuaranteeDto()&&orderBaseCustomerDto.getCustomerGuaranteeDto().size()>0){
				List<OrderBaseCustomerGuaranteeDto> temp = new ArrayList<OrderBaseCustomerGuaranteeDto>();
				List<OrderBaseCustomerGuaranteeDto>  customerGuaranteeDto = orderBaseCustomerDto.getCustomerGuaranteeDto();
				for (OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto : customerGuaranteeDto) {
					boolean flag = false;
					for (FacesignRecognitionDto facesignRecognitionDto : respList) {
						if(StringUtils.isBlank(orderBaseCustomerGuaranteeDto.getGuaranteeCardNumber())||(facesignRecognitionDto.getCustomerCardNumber().equals(orderBaseCustomerGuaranteeDto.getGuaranteeCardNumber())&&"担保人".equals(facesignRecognitionDto.getCustomerType()))){
							flag = true;
						}
					}
					if(!flag){
						temp.add(orderBaseCustomerGuaranteeDto);
					}
				}
				if(temp.size()>0){
					//录入
					List<FacesignRecognitionDto> list = new ArrayList<FacesignRecognitionDto>();
					for (OrderBaseCustomerGuaranteeDto obj : temp) {
						FacesignRecognitionDto facesignRecognitionDto = new FacesignRecognitionDto();
						facesignRecognitionDto.setCreateUid(orderBaseCustomerDto.getCreateUid());
						facesignRecognitionDto.setOrderNo(orderNo);
						facesignRecognitionDto.setCustomerName(obj.getGuaranteeName());
						facesignRecognitionDto.setCustomerCardNumber(obj.getGuaranteeCardNumber());
						facesignRecognitionDto.setCustomerType("担保人");
						list.add(facesignRecognitionDto);
					}
					httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/insertFacesignRecognitions", list);
				}
			}
		}
	}
}
