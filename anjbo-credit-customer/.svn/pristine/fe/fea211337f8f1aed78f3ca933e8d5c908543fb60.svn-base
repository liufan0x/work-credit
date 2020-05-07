package com.anjbo.controller;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.customer.AgencyIncomeModeDto;
import com.anjbo.bean.customer.vo.AgencyFull;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.RoleDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.AgencyIncomeModeService;
import com.anjbo.service.AgencyService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/credit/customer/agency")
public class AgencyController extends BaseController{	
	@Resource
	private AgencyService agencyService;

	@Resource
	private AgencyIncomeModeService agencyIncomeModeService;

	Logger log = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/v/agencyList")
	public RespData<AgencyDto> agencyList(HttpServletRequest request){
		return new RespHelper().setSuccessData(new RespData<AgencyDto>(), agencyService.agencyList());
	}


	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/v/getAgencyDto")
	public RespDataObject<AgencyDto> getAgencyDto(@RequestBody Map<String, Object> params){
		if(null==params || !params.containsKey("agencyId") || MapUtils.getIntValue(params, "agencyId", 0)<1){
			RespHelper.setFailDataObject(new RespDataObject<AgencyDto>(), null, RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		AgencyDto dtoAgency = agencyService.getAgencyDto(MapUtils.getIntValue(params, "agencyId"));
		if(null!=dtoAgency && StringUtils.isNotEmpty(dtoAgency.getChanlMan())){
			UserDto dtoUser = CommonDataUtil.getUserDtoByUidAndMobile(dtoAgency.getChanlMan());
			if(null!=dtoUser){
				dtoAgency.setChanManName(dtoUser.getName());
				dtoAgency.setChanManPhone(dtoUser.getMobile());
			}
		}		
		return new RespHelper().setSuccessDataObject(new RespDataObject<AgencyDto>(), dtoAgency);
	}	

	@ResponseBody
	@RequestMapping("/v/insertAgency")
	public RespDataObject<AgencyDto> insertAgency(HttpServletRequest request, @RequestBody Map<String,Object> map){
		RespDataObject<AgencyDto> result = new RespDataObject<AgencyDto>();
		try {
			log.info("新增机构接收参数:"+map.toString());
			result = agencyService.insert(map);
		} catch (Exception e){
			log.error("新增机构异常",e);
			if(StringUtil.isNotEmpty(e.getMessage()) &&
					e.getMessage().contains("MySQLIntegrityConstraintViolationException")){
				String msg = "该机构名称或简称已存在，请重新填写";
				if(e.getMessage().contains("agencyNameUnique")){
					msg = "该机构名称已存在，请重新填写";
				}else if(e.getMessage().contains("agencySimNameUnique")){
					msg = "该机构简称已存在，请重新填写";
				}
				return RespHelper.setFailDataObject(result,null,msg);
			}
			return RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
		}
		return result;
	}
	/**
	 * 修改剩余额度
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updSurplusQuota")
	public RespDataObject<Integer> updSurplusQuota(HttpServletRequest request, @RequestBody Map<String,Object> map){
		RespDataObject<Integer> result = new RespDataObject<Integer>();
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		try {
			System.out.println("修改机构剩余额度接收参数:"+map.toString());
			agencyService.updSurplusQuota(map);
			RespHelper.setSuccessDataObject(result,null);
		} catch (Exception e){
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
			RespHelper.setSuccessDataObject(result,null);
			log.error("修改机构剩余额度异常",e);
		}
		return result;
	}
	

	@ResponseBody
	@RequestMapping(value = "/{id}")
	public RespDataObject<AgencyDto> getEntityById(@PathVariable("id")int id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agencyId", id);
		return this.getAgencyDto(params);
	}
	@ResponseBody
	@RequestMapping(value = "/v/get")
	public RespDataObject<AgencyDto> getEntity(HttpServletRequest request){
		return this.getEntityById(this.getUserDto(request).getAgencyId());
	}
	
	/**
	 * 获取机构完整关联信息(机构基本信息|角色|部门)
	 * @Author KangLG<2017年11月15日>
	 * @param request
	 * @param agencyCode 机构代码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFullByAgency_{agencyCode}")
	public RespDataObject<AgencyFull> getFullByAgency(HttpServletRequest request, @PathVariable("agencyCode")int agencyCode){
		AgencyFull agencyFullVo = new AgencyFull(agencyService.getEntityByAgencyCode(agencyCode));
		if(agencyFullVo.isNull()){
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, "机构不存在，请重新输入机构码");
		}else if(!agencyFullVo.getStatus().equals(1)){	//使用状态，0禁用1可用
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, RespStatusEnum.AGENCY_UNENABLED.getMsg());
		}else if(agencyFullVo.getSignStatus() != 2){	//签约状态，1未签约2已签约3已解约
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, RespStatusEnum.AGENCY_UNSIGN.getMsg());
		}
		// 构建角色及部门列表
		RespPageData<RoleDto> roles = new HttpUtil().getRespPageData(Constants.LINK_CREDIT/*Constants.LINK_CREDIT http://127.0.0.1:9911*/, "/credit/user/role/list", new RoleDto(agencyFullVo.getId()), RoleDto.class);
		if(null!=roles && null!=roles.getRows() && !roles.getRows().isEmpty()){
			agencyFullVo.setListRole(roles.getRows());
		}
		RespPageData<DeptDto> depts = new HttpUtil().getRespPageData(Constants.LINK_CREDIT/*Constants.LINK_CREDIT http://127.0.0.1:9911*/, "/credit/user/dept/list", new DeptDto(agencyFullVo.getId()), DeptDto.class);
		if(null!=depts && null!=depts.getRows() && !depts.getRows().isEmpty()){
			agencyFullVo.setListDept(depts.getRows());
		}
		return RespHelper.setSuccessDataObject(new RespDataObject<AgencyFull>(), agencyFullVo);
	}

	
	/**
	 * 机构费用支付方式列表
	 * @Author KangLG<2017年11月14日>
	 * @param request
	 * @param agency 机构ID
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/incomeModeList_{agencyId}")
	public RespData<AgencyIncomeModeDto> incomeModeList4Agency(HttpServletRequest request, @PathVariable("agencyId")int agency){
		return new RespHelper().setSuccessData(
				   new RespData<AgencyIncomeModeDto>(),
				   agencyIncomeModeService.search(new AgencyIncomeModeDto(agency)));
	}

	/**
	 * App机构费用支付方式列表
	 * @Author KangLG<2017年11月14日>
	 * @param request
	 * @param params 机构ID
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/incomeModeList")
	public RespData<Map<String, Object>> incomeModeList(HttpServletRequest request, @RequestBody Map<String, Object> params){
		int agencyCode = MapUtils.getIntValue(params, "cooperativeAgencyId",0);
		List<AgencyIncomeModeDto> tempList = agencyIncomeModeService.search(new AgencyIncomeModeDto(agencyCode));
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (AgencyIncomeModeDto agencyIncomeModeDto : tempList) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("id", agencyIncomeModeDto.getIncomeMode());
			tempMap.put("name", agencyIncomeModeDto.getName());
			list.add(tempMap);
		}
		return new RespHelper().setSuccessData(new RespData<Map<String, Object>>(),list);
	}
	
	/**
	 * 更新
	 * @param request
	 * @param agencyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/update")
	public RespStatus update(HttpServletRequest request,@RequestBody AgencyDto agencyDto){
		RespStatus result = new RespStatus();
		try{
			if(null==agencyDto.getId()){
				RespHelper.setFailRespStatus(result,"更新机构信息缺少主键");
				return result;
			}
			AgencyDto tmp = agencyService.getAgencyDto(agencyDto.getId());
			if(3==agencyDto.getSignStatus()){
				if(1==tmp.getStatus()){
					RespHelper.setFailRespStatus(result,"该机构的后台账号为解冻的状态，解除合作之前请先冻结该后台账号!");
					return result;
				}
				if(Enums.AgencyEnum.KUAIGE.getAgencyId()==agencyDto.getId()){
					RespHelper.setFailRespStatus(result,"抱歉该机构不能解除合作");
					return result;
				}
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("agencyId",tmp.getId());
				param.put("agencyName",tmp.getName());
				param.put("agencyCode",tmp.getAgencyCode());
				param.put("agencyStatus",3);

				result = httpUtil.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/updateAgencyNotify",param);
				log.info("解除合作"+tmp.getName()+"更新快鸽APP用户机构状态==="+result+"====");
				result = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/unbind4Agency_"+agencyDto.getId(),null);
				log.info("解除合作"+tmp.getName()+"同步用户更新机构状态:==="+request+"===");
				clearUsers();
			}
			if(null!=agencyDto.getStatus()
					&&(0==agencyDto.getStatus()
					||1==agencyDto.getStatus())){
				if(Enums.AgencyEnum.KUAIGE.getAgencyId()==agencyDto.getId()
						&&0==agencyDto.getStatus()){
					RespHelper.setFailRespStatus(result,"抱歉该机构不能被冻结");
					return result;
				}
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("agencyId",tmp.getId());
				param.put("agencyName",tmp.getName());
				param.put("agencyCode",tmp.getAgencyCode());
				param.put("agencyStatus",0==agencyDto.getStatus()?1:0);
				int isEnable = 0;
				if(0==agencyDto.getStatus()){
					isEnable = 1;
				}
				result = httpUtil.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/updateAgencyNotify",param);
				log.info("冻结"+tmp.getName()+"更新快鸽APP用户机构状态==="+result+"====");
				Map<String,Object> tmpAccount = new HashMap<String,Object>();
				tmpAccount.put("agencyId",tmp.getId());
				tmpAccount.put("isEnable",isEnable);
				tmpAccount.put("name",tmp.getContactMan());
				tmpAccount.put("mobile",tmp.getContactTel());
				tmpAccount.put("indateStart",null);
				tmpAccount.put("indateEnd",null);
				tmpAccount.put("uid",tmp.getAccountUid());
				result = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/editAgentAdmin", tmpAccount);
				log.info("冻结"+tmp.getName()+"同步用户更新机构状态:==="+request+"===");
				clearUsers();
			}
			agencyService.updateById(agencyDto);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			log.error("更新机构信息异常:",e);
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
		return result;
	}

	/**
	 * 重置机构密码
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/restartPassworld")
	public RespStatus restartPassworld(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		try {
			result = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/resetPwd",map);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("重置机构密码异常:",e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/v/delete")
	public RespStatus delete(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		try{
			if(null==MapUtils.getString(map,"agencyId")){
				return RespHelper.setFailRespStatus(result,"主键不能为空");
			}
			AgencyDto a = new AgencyDto();
			a.setId(MapUtils.getInteger(map,"id"));
			agencyService.delete(a);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("删除机构异常:",e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/v/selectAgencyByMobile")
	public RespDataObject<AgencyDto> checkMobile(HttpServletRequest request,@RequestBody AgencyDto dto){
		RespDataObject<AgencyDto> result = new RespDataObject<AgencyDto>();
		try{
			if(StringUtils.isBlank(dto.getContactTel())){
				RespHelper.setFailDataObject(result,null,"手机号码不能为空");
				return result;
			}
			dto = agencyService.selectAgencyByMobile(dto);
			RespHelper.setSuccessDataObject(result,dto);
		} catch (Exception e){
			RespHelper.setFailDataObject(result,null,"请求异常,请联系开发部!");
			log.error("校验手机号是否存在异常",e);
		}
		return result;
	}

	public void clearUsers(){
		RespStatus respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/clearUserList",null);
		logger.info("刷新用户缓存==="+respStatus+"===");
	}

	/**
	 * 更新机构信息
	 * @param request
	 * @param map key=id(机构id),key=name(机构名称),key=contactTel(机构联系人手机号),key=contactMan(机构联系人),
	 *            key=accountUid(管理员账号uid),key=indateStart(账号有效开始时间),key=indateEnd(账号有效结束时间)
	 *            key=chanlMan(渠道经理)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/updateAgencyInfo")
	public RespStatus updateAgencyInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		AgencyDto agencyDto = null;
		try {
			if(null==MapUtils.getInteger(map,"id")||0==MapUtils.getInteger(map,"id")){
				RespHelper.setFailRespStatus(respStatus,"更新缺少机构主键");
				return respStatus;
			}
			agencyDto = agencyService.selectAgencyById(map);
			if(null!=agencyDto){
				String name = MapUtils.getString(map,"name");
				//String contactTel = MapUtils.getString(map,"contactTel");
				String contactMan = MapUtils.getString(map,"contactMan");
				String chanlMan = MapUtils.getString(map,"chanlMan");
				//String accountUid = MapUtils.getString(map,"accountUid");
				//String indateStart = MapUtils.getString(map,"indateStart");
				//String indateEnd = MapUtils.getString(map,"indateEnd");
				/*
				if(!contactTel.equals(agencyDto.getContactTel())){
					Map<String,Object> tmp = new HashMap<String,Object>();
					tmp.put("uid",accountUid);
					tmp.put("contactsPhone",contactTel);
					respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/product/data/sm/agency/v/checkMobile",tmp);
				}
				if(RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())){
					return respStatus;
				}*/
				if(!name.equals(agencyDto.getName())){
					List<AgencyDto> tmp = agencyService.selectAgencyByName(name);
					String agencyIdsStr = "";
					if(null!=tmp||tmp.size()>0){
						for (AgencyDto a:tmp){
							if (!a.getId().equals(agencyDto.getId())&&2 == a.getSignStatus()) {
								RespHelper.setFailRespStatus(respStatus,  "已存在相同的机构名称!");
								return respStatus;
							} else {
								agencyIdsStr += ","+a.getId()+"-"+a.getSignStatus();
							}
						}
					}
					if(StringUtils.isNotBlank(agencyIdsStr)){
						agencyIdsStr = agencyIdsStr.substring(1,agencyIdsStr.length());
						map.put("agencyId",agencyIdsStr);
						respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/sm/agency/v/isListByAgencyName", map);
						if (RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())) {
							return respStatus;
						}
					}
				}
				AgencyDto now = new AgencyDto();
				now.setId(agencyDto.getId());
				now.setName(name);
				now.setSimName(name);
				now.setChanlMan(chanlMan);
				//now.setContactTel(contactTel);
				now.setContactMan(contactMan);
				//now.setManageAccount(contactTel);
				agencyService.updateById(now);
				/*
				Map<String,Object> accountMap = null;
				if(StringUtils.isBlank(indateStart)){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					indateStart = format.format(new Date());
				}
				if(StringUtils.isNotBlank(accountUid)){
					accountMap = new HashMap<String,Object>();
					accountMap.put("uid",accountUid);
					accountMap.put("agencyId",agencyDto.getId());
					accountMap.put("mobile",contactTel);
					accountMap.put("name",contactMan);
					accountMap.put("indateStart",indateStart);
				}
				if(MapUtils.isNotEmpty(accountMap)
						&&StringUtils.isNotBlank(indateEnd)){
					accountMap.put("indateEnd",indateEnd);
					respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/editAgentAdmin", accountMap);
				}*/
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e){
			agencyService.updateById(agencyDto);
			RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
			logger.error("修改机构信息异常:",e);
		}
		return respStatus;
	}

	@ResponseBody
	@RequestMapping("/v/initAgencyToRedis")
	public RespStatus initAgencyToRedis(){
		RespStatus respStatus = agencyService.initAgencyToRedis();
		return respStatus;
	}
}
