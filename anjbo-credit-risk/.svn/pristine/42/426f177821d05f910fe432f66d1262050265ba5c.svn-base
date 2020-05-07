package com.anjbo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CreditService;
import com.anjbo.service.FirstAuditService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 征信
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/ordercredit/v")
public class CreditController extends BaseController{
	
	private static final Log log = LogFactory.getLog(CreditController.class);
	
	@Resource
	private CreditService creditService;
	@Resource
	private FirstAuditService firstAuditService;
	/**
	 * 新增征信信息
	 * @param request
	 * @param credit
	 * @return RespStatus
	 */
	@ResponseBody
	@RequestMapping(value="/insertCredit")
	public RespStatus insertCredit(HttpServletRequest request,@RequestBody CreditDto credit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(credit.getOrderNo())){
				result.setMsg("保存征信信息失败,订单编号不能为空!");
				return result;
			}
			UserDto user = getUserDto(request);
			credit.setCreateUid(user.getUid());
			creditService.insertCredit(credit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信新增数据异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 更新征信信息
	 * @param request
	 * @param credit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateCredit")
	public RespStatus updateCredit(HttpServletRequest request,@RequestBody CreditDto credit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(credit.getOrderNo())){
				result.setMsg("更新征信信息异常,缺少订单编号");
				return result;
			}
			UserDto user = getUserDto(request);
			credit.setCreateUid(user.getUid());
			credit.setUpdateUid(user.getUid());	
			creditService.updateCredit(credit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信更新数据异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 更新征信信息
	 * @param request
	 * @param credit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateNull")
	public RespStatus updateNull(HttpServletRequest request,@RequestBody CreditDto credit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(credit.getOrderNo())){
				result.setMsg("更新征信信息异常,缺少订单编号");
				return result;
			}
			UserDto user = getUserDto(request);
			credit.setCreateUid(user.getUid());
			credit.setUpdateUid(user.getUid());
			creditService.updateNull(credit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信更新数据异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 查询征信更新流水日志
	 * @param request
	 * @param credit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectCreditLog")
	public RespDataObject<List<Map<String,Object>>> selectCreditLog(HttpServletRequest request,@RequestBody CreditDto credit){
		RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(credit.getOrderNo())){
				result.setMsg("查询征信修改日志异常,缺少订单编号");
				return result;
			}
			List<Map<String,Object>> list = creditService.selectCreditLog(credit.getOrderNo());
			FirstAuditDto first = firstAuditService.detail(credit.getOrderNo());
			UserDto tmp = null;
			if(null!=first){
				tmp = CommonDataUtil.getUserDtoByUidAndMobile(first.getHandleUid());
			} else if(null!=list&&list.size()>0){
				String uid = MapUtils.getString(list.get(0), "createUid","");
				tmp = CommonDataUtil.getUserDtoByUidAndMobile(uid);
			}
			
			if(null!=list&&list.size()>0&&null!=tmp){
				Map<String,Object> m = list.get(0);
				m.put("handleName", tmp.getName());
			}
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信查询操作日志数据异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 查询征信详情
	 * @param request
	 * @param credit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/detail")
	public RespDataObject<CreditDto> detail(HttpServletRequest request,@RequestBody CreditDto credit){
		RespDataObject<CreditDto> result = new RespDataObject<CreditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(credit.getOrderNo())){
				result.setMsg("加载征信信息异常,缺少订单编号");
				return result;
			}
//			String orderNo = AllocationFundController.getOrderNo(credit.getOrderNo());
			
			credit = creditService.detail(credit.getOrderNo());
			result.setData(credit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("风控征信详情数据异常,异常信息为:",e);
		}
		return result;
	}
}
