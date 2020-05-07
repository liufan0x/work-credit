package com.anjbo.controller;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.CustomerAgencyAcceptDto;
import com.anjbo.bean.customer.CustomerAgencyDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.*;
import com.anjbo.utils.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
  * 机构 [Controller]
  * @ClassName: CustomerAgencyController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/new/v")
public class CustomerAgencyController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyController.class);
	
	@Resource 
	private CustomerAgencyService customerAgencyService;

	@Resource
	private CustomerAgencyAcceptService agencyAcceptService;
	@Resource 
	private CustomerAgencyTypeService customerAgencyTypeService;
	@Resource
	private AgencyProductService agencyProductService;
	@Resource
	private AgencyIncomeModeService agencyIncomeModeService;
	@Resource
	private CustomerAgencyAcceptService customerAgencyAcceptService;
	/**
	 * 机构分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyDto> page(HttpServletRequest request,@RequestBody CustomerAgencyDto customerAgencyDto){
		RespPageData<CustomerAgencyDto> resp = new RespPageData<CustomerAgencyDto>();
		if(StringUtils.isNotBlank(customerAgencyDto.getName())&&customerAgencyDto.getName().contains("%")){
			String name = customerAgencyDto.getName().replaceAll("%","''");
			customerAgencyDto.setName(name);
		}
		UserDto user = getUserDto(request);
		Map<String,Object> selectMap = getSelectUid(user);
		customerAgencyDto.setUids(MapUtils.getString(selectMap,"uids",""));
		customerAgencyDto.setIds(MapUtils.getString(selectMap,"ids",""));
		int count = customerAgencyService.selectCustomerAgencyCount(customerAgencyDto);
		resp.setTotal(count);
		if(count>0){
			List<CustomerAgencyDto> clist = customerAgencyService.selectCustomerAgencyList(customerAgencyDto);

			//渠道经理
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "roleName");
			params.put("choicePersonnel",
					Enums.RoleEnum.CHANNEL_MANAGER.getName());
			List<UserDto> list = new HttpUtil().getList(Constants.LINK_CREDIT,
					"/credit/user/base/v/choicePersonnel", params,UserDto.class);
			Map<String, String> map = new HashMap<String, String>();
			for (UserDto userDto : list) {
				map.put(userDto.getUid(), userDto.getName());
			}
			//机构类型
			List<DictDto> agencyType = getDictDtoByType("agencyType");
			for (DictDto d:agencyType){
				map.put(d.getCode(),d.getName());
			}
			//合作模式
			List<DictDto> cooperativeMode = getDictDtoByType("cooperativeMode");
			for (DictDto d:cooperativeMode){
				map.put(d.getCode(),d.getName());
			}
			for (CustomerAgencyDto c : clist) {
				c.setTypeName(MapUtils.getString(map,c.getAgencyType()));
				c.setChanManName(MapUtils.getString(map,c.getChanlMan()));
				c.setCooperativeMode(MapUtils.getString(map,String.valueOf(c.getCooperativeModeId())));
			}

			resp.setRows(clist);
		}else{
			resp.setRows(new ArrayList<CustomerAgencyDto>());
		}
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	/**
	 * 机构列表
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param request
	 * @return customerAgencyTypeDto
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespData<CustomerAgencyDto> list(HttpServletRequest request,@RequestBody CustomerAgencyDto customerAgencyDto){
		RespData<CustomerAgencyDto> resp = new RespData<CustomerAgencyDto>();
		resp.setData(customerAgencyService.selectCustomerAgencyList(customerAgencyDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}

	/**
	 * 机构编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyDto customerAgencyDto){
		RespStatus result = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			CustomerAgencyDto old = customerAgencyService.selectCustomerAgencyById(customerAgencyDto.getId());
			if(null!=old
					&&old.getSurplusQuota()!=customerAgencyDto.getSurplusQuota()
					&&1==customerAgencyDto.getCooperativeModeId()){
				Double oldSurplusQuota = old.getSurplusQuota();
				Double riskBearMultiple = old.getRiskBearMultiple();
				Double bond = old.getBond();
				if(2==old.getCooperativeModeId()&&1==customerAgencyDto.getCooperativeModeId()){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("cooperativeAgencyId",old.getId());
					RespDataObject<OrderListDto> respDataObject =  httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/order/base/v/getOrderLoan",map, OrderListDto.class);
					if(RespStatusEnum.FAIL.getCode().equals(respDataObject.getCode())){
						RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
						return result;
					}
					if(null!=respDataObject.getData()){
						Double tmp = null==respDataObject.getData().getBorrowingAmount()?0:respDataObject.getData().getBorrowingAmount();
						Double creditLimit = NumberUtil.multiply(customerAgencyDto.getRiskBearMultiple(),customerAgencyDto.getBond());
						customerAgencyDto.setSurplusQuota(creditLimit-tmp);
					} else {
						Double tmp = NumberUtil.multiply(customerAgencyDto.getRiskBearMultiple(),customerAgencyDto.getBond());
						customerAgencyDto.setSurplusQuota(tmp);
					}

				} else {
					if(null!=riskBearMultiple&&null!=bond&&null!=oldSurplusQuota){
						//旧的剩余额度
						Double tmp = NumberUtil.multiply(riskBearMultiple,bond);
						//使用了多少额度
						Double tmpSurplusQuota =  tmp - oldSurplusQuota;
						//新的剩余额度
						tmp = NumberUtil.multiply(customerAgencyDto.getRiskBearMultiple(),customerAgencyDto.getBond());
						Double newSurplusQuota = tmp-tmpSurplusQuota;
						newSurplusQuota = newSurplusQuota>tmp?tmp:newSurplusQuota;
						customerAgencyDto.setSurplusQuota(newSurplusQuota);
					} else {
						Double tmp = NumberUtil.multiply(customerAgencyDto.getRiskBearMultiple(),customerAgencyDto.getBond());
						customerAgencyDto.setSurplusQuota(tmp);
					}
				}
			} else if(2==customerAgencyDto.getCooperativeModeId()){
				customerAgencyDto.setSurplusQuota(null);
			}

			Map<String,Object> dataObject = null;
			Map<String,Object> param = new HashMap<String,Object>();
			boolean isEditContactTel = false;
			if(StringUtils.isNotBlank(customerAgencyDto.getContactTel())
				&&!customerAgencyDto.getContactTel().equals(old.getContactTel())){
					isEditContactTel = true;
					param.put("contactsPhone",customerAgencyDto.getContactTel());
					result = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/product/data/sm/agency/v/checkMobile",param);
					if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
						return result;
					} else {
						customerAgencyDto.setManageAccount(customerAgencyDto.getContactTel());
					}
			}
			if(customerAgencyDto.getId()>0){
				customerAgencyDto.setUpdateUid(user.getUid());
				customerAgencyService.updateCustomerAgency(customerAgencyDto);
				agencyAcceptService.deleteCustomerAgencyAcceptByAgencyId(customerAgencyDto.getId());
				for (CustomerAgencyAcceptDto customerAgencyAcceptDto : customerAgencyDto.getCustomerAgencyAcceptDtos()) {
					customerAgencyAcceptDto.setCreateUid(user.getUid());
					agencyAcceptService.addCustomerAgencyAccept(customerAgencyAcceptDto);
				}
			}else{
				customerAgencyDto.setCreateUid(user.getUid());
				customerAgencyService.addCustomerAgency(customerAgencyDto);
			}
			/**修改机构手机号同时修改机构管理员账号*/
			if(isEditContactTel){
				param.put("orderNo",MapUtils.getString(dataObject,"orderNo"));
				param.put("contactsPhone",customerAgencyDto.getContactTel());

				Map<String,Object> tmpAccount = new HashMap<String,Object>();
				tmpAccount.put("agencyId",customerAgencyDto.getId());
				tmpAccount.put("isEnable",0);
				tmpAccount.put("name",customerAgencyDto.getContactMan());
				tmpAccount.put("mobile",customerAgencyDto.getContactTel());
				tmpAccount.put("indateStart",null);
				tmpAccount.put("indateEnd",null);

				if(StringUtils.isNotBlank(old.getAccountUid())){
					tmpAccount.put("uid",old.getAccountUid());
				} else {
					tmpAccount.put("password", ConfigUtil.getStringValue(Constants.BASE_AGENCY_DEFAULT_PASSWORLD,ConfigUtil.CONFIG_BASE));
				}
				result = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/editAgentAdmin", tmpAccount);

				if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
					customerAgencyDto.setAccountUid(result.getMsg());
					customerAgencyService.updateCustomerAgency(customerAgencyDto);
				} else {
					log.info("维护修改联系人手机号生成管理员账号失败:==="+result.getMsg()+"===");
					log.info("=================数据开始回滚================");
					customerAgencyService.updateCustomerAgency(old);
					log.info("=================数据回滚结束================");
					return result;
				}

			}

			/**发送短信给渠道经理*/
			if(null!=old
					&&StringUtils.isNotBlank(customerAgencyDto.getChanlMan())
					&&!customerAgencyDto.getChanlMan().equals(old.getChanlMan())){
				sendMessage(customerAgencyDto);
			} else if(null==old
					&&StringUtils.isNotBlank(customerAgencyDto.getChanlMan())){
				sendMessage(customerAgencyDto);
			}

			return RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			log.error("机构编辑失败，异常信息：", e);
			if(StringUtil.isNotEmpty(e.getMessage()) && 
					e.getMessage().contains("MySQLIntegrityConstraintViolationException")){
				String msg = "该机构名称或简称已存在，请重新填写";
				if(e.getMessage().contains("agencyNameUnique")){
					msg = "该机构名称已存在，请重新填写";
				}else if(e.getMessage().contains("agencySimNameUnique")){
					msg = "该机构简称已存在，请重新填写";
				}
				return RespHelper.setFailRespStatus(result,msg);
			}
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除机构
	 * @Title: deleteCustomerAgencyById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyById")
	public RespStatus deleteCustomerAgencyById(HttpServletRequest request, @RequestBody CustomerAgencyDto customerAgencyDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyService.deleteCustomerAgencyById(customerAgencyDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("机构删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询机构
	 * @Title: selectCustomerAgencyById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyById")
	public RespDataObject<CustomerAgencyDto> selectCustomerAgencyById(HttpServletRequest request, @RequestBody CustomerAgencyDto customerAgencyDto){
		 RespDataObject<CustomerAgencyDto> status = new  RespDataObject<CustomerAgencyDto>();
		if(customerAgencyDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		CustomerAgencyDto dto = customerAgencyService.selectCustomerAgencyById(customerAgencyDto.getId());
		UserDto expandUser = CommonDataUtil.getUserDtoByUidAndMobile(dto.getExpandManagerUid());
		if(null!=expandUser){
			dto.setExpandManagerName(expandUser.getName());
		}
		UserDto chanlMan = CommonDataUtil.getUserDtoByUidAndMobile(dto.getChanlMan());
		if(null!=chanlMan){
			dto.setChanManName(chanlMan.getName());
		}
		status.setData(dto);
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}

	/**
	 * 初始化数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectEditAgencyData")
	public RespDataObject<Map<String,Object>> selectEditAgencyData(HttpServletRequest request){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			//费用支付方式初始化值
			List<DictDto> incomeModeList = getDictDtoByType("incomeMode");
			map.put("incomeModeList",incomeModeList);
			//机构类型
			List<DictDto> agencyType = getDictDtoByType("agencyType");
			map.put("agencyType",agencyType);
			//合作模式
			List<DictDto> cooperativeMode = getDictDtoByType("cooperativeMode");
			map.put("cooperativeMode",cooperativeMode);
			//受理经理
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "roleName");
			params.put("choicePersonnel",
					Enums.RoleEnum.ACCEPT_MANAGER.getName());
			List<UserDto> acceptManagerList = new HttpUtil().getList(Constants.LINK_CREDIT,
					"/credit/user/base/v/choicePersonnel", params,UserDto.class);
			map.put("acceptManagerList",acceptManagerList);
			//城市
			List<DictDto> cityList = getDictDtoByType("cityList");
			map.put("cityList",cityList);

			RespHelper.setSuccessDataObject(result,map);
		} catch (Exception e){
			RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
			log.error("机构维护获取机构所初始化数据异常:",e);
		}
		return result;
	}
//	public UserDto getUser(String uid){
//		if (StringUtils.isNotBlank(uid)){
//			List<UserDto> userList = getUserDtoList();
//			for (UserDto user:userList){
//				if(user.getUid().equals(uid)){
//					return user;
//				}
//			}
//		}
//		return null;
//	}

	/**
	 * 分配渠道经理发送短信给渠道经理
	 * @param dto
	 */
	public void sendMessage(CustomerAgencyDto dto){
		UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(dto.getChanlMan());
		if(null!=user){
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
			String key = Constants.SMS_AGENCY_DISTRIBUTION_CHANNELMANAGER;
			AmsUtil.smsSend(user.getMobile(), ipWhite, key, dto.getName(), dto.getContactMan(),dto.getContactTel());
		} else {
			log.info("发送短信,没有获取到渠道经理信息,未发送短信");
		}
	}

	/**
	 * 获取用户查询权限uid
	 * @param userDto
	 * @return java.util.Map key=uids(关联的uid),key=ids(如果是受理员则会返回该字段)
	 */
	public Map<String,Object> getSelectUid(UserDto userDto){
		Map<String,Object> selectMap = new HashMap<String,Object>();
		String deptAllUid = "";
		//查看全部订单
		if(userDto.getAuthIds().contains("102")){
			//查看部门订单
		}else if(userDto.getAuthIds().contains("103")){
			userDto.setCreateTime(null);
			userDto.setUpdateTime(null);
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId",userDto , Map.class);
			deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
			//查看自己订单
		}else{
			deptAllUid = userDto.getUid();
		}
		selectMap.put("uids",deptAllUid);
		/**如果角色是受理员则查询是否有关联机构*/
		if(Enums.RoleEnum.ACCEPT.getName().equals(userDto.getRoleName())){
			CustomerAgencyAcceptDto customerAgencyAcceptDto  = new CustomerAgencyAcceptDto();
			customerAgencyAcceptDto.setAcceptUid(userDto.getUid());
			List<CustomerAgencyAcceptDto> list = customerAgencyAcceptService.selectCustomerAgencyAcceptList(customerAgencyAcceptDto);
			if(null!=list&&list.size()>0){
				String ids = "";
				for (CustomerAgencyAcceptDto d:list){
					ids += d.getAgencyId()+",";
				}
				if(StringUtils.isNotBlank(ids)){
					ids = ids.substring(0,ids.length()-1);
				}
				selectMap.put("ids",ids);
			}
		}
		return  selectMap;
	}
}
