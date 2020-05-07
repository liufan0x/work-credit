package com.anjbo.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerFundService;


/**
  * 合作资金方 [Controller]
  * @ClassName: CustomerFundController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/fund/new/v")
public class CustomerFundController extends BaseController{

	private Log log = LogFactory.getLog(CustomerFundController.class);
	
	@Resource 
	private CustomerFundService customerFundService;

	/**
	 * 合作资金方分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerFundDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerFundDto> page(HttpServletRequest request,@RequestBody CustomerFundDto customerFundDto){
		RespPageData<CustomerFundDto> resp = new RespPageData<CustomerFundDto>();
		resp.setTotal(customerFundService.selectCustomerFundCount(customerFundDto));
		resp.setRows(customerFundService.selectCustomerFundList(customerFundDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 合作资金方编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerFundDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerFundDto vo){ 
		try {
			CustomerFundDto temp = customerFundService.selectCustomerFundByFundCode(vo.getFundCode());
			vo.setAuths(dealAuths(vo.getAuths()));
			if(vo.getId() > 0){
				if(temp != null && temp.getId() != vo.getId()){
					return RespHelper.setFailRespStatus(new RespStatus(), "代号重复");
				}
				vo.setUpdateUid(getUserDto(request).getUid());
				// 手机号变更了才需要重新同步
				if(null!=temp && !vo.getContactTel().equals(temp.getContactTel())){
					vo.setManagerUid(temp.getManagerUid());
					vo.setManagerStatus(temp.getManagerStatus());
					RespStatus resp = this.syncUser(false, vo);
					if(null==resp || !RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
						return resp;
					}else if(StringUtils.isBlank(temp.getManagerUid())){
						vo.setManagerUid(resp.getMsg());
					}
				}				
				customerFundService.update(vo);
			}else{
				if(temp != null){
					return RespHelper.setFailRespStatus(new RespStatus(), "代号重复");
				}
				vo.setManagerStatus(1);
				vo.setCreateUid(getUserDto(request).getUid());
				
				RespStatus resp = this.syncUser(true, vo);
				if(null==resp || !RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
					return resp;
				}
				vo.setManagerUid(resp.getMsg());
				customerFundService.insert(vo);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("合作资金方编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 
	 * @Author KangLG<2018年3月12日>
	 * @param authsIn 权限串
	 * @return auths 正规权限串
	 */
	private String dealAuths(String authsIn) {
		if(StringUtils.isBlank(authsIn)){
			return "";
		}
					
//		// 数据清理
//		String[] arys = authsIn.split(",");
//		Arrays.sort(arys);
//		String old = null;
//		for (int i=0; i<arys.length; i++) {
//			if(i>0 && arys[i].equals(old)){
//				arys[i] = "";
//			}else{
//				old = arys[i];
//			}
//		}
//		
//		// 格式处理
//		String res = Arrays.toString(arys);
//		res = res.substring(1, res.length()-1).replaceAll(" ", "").replaceAll("(,{2,5})", ",");
		
		String res = authsIn.replaceAll(" ", "").replaceAll("(,{2,5})", ",");
		if(res.startsWith(",")){
			res = res.substring(1, res.length());
		}
		if(res.endsWith(",")){
			res = res.substring(0, res.length()-1);
		}
						
		return res;
	}	
	/**
	 * 用户数据同步
	 * @Author KangLG<2018年3月2日>
	 * @param isAdd
	 * @param vo
	 * @return
	 */
	public RespStatus syncUser(boolean isAdd, CustomerFundDto vo){
		Map<String,Object> paramAccount = new HashMap<String,Object>();
		paramAccount.put("uid", isAdd ? null : vo.getManagerUid());
		paramAccount.put("agencyId", 10);
		paramAccount.put("isEnable", vo.getManagerStatus());
		paramAccount.put("name", vo.getFundName());
		paramAccount.put("mobile", vo.getContactTel());
		paramAccount.put("indateStart", null);
		paramAccount.put("indateEnd", null);
		
		RespStatus resp = httpUtil.getRespStatus(
				Constants.LINK_CREDIT/*Constants.LINK_CREDIT http://127.0.0.1:9911*/, 
				"/credit/user/base/v/editAgentAdmin", 
				paramAccount);
		if(null==resp || !RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
			resp.setCodeMsg(RespStatusEnum.FAIL, String.format("同步用户数据失败(%s/%s)：%s", null!=vo.getManagerUid()?vo.getManagerUid():"--", vo.getContactTel(), resp.getMsg()));
		}
		return resp;
	}
	
	/**
	 * 根据ID删除合作资金方
	 * @Title: deleteCustomerFundById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerFundDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerFundById")
	public RespStatus deleteCustomerFundById(HttpServletRequest request, @RequestBody CustomerFundDto customerFundDto){ 
		RespStatus status = new  RespStatus();
		if(customerFundDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerFundService.deleteCustomerFundById(customerFundDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("合作资金方删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询合作资金方
	 * @Title: selectCustomerFundById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerFundDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerFundById")
	public RespDataObject<CustomerFundDto> selectCustomerFundById(HttpServletRequest request, @RequestBody CustomerFundDto customerFundDto){ 
		 RespDataObject<CustomerFundDto> status = new  RespDataObject<CustomerFundDto>();
		if(customerFundDto.getId() <= 0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}		
		status.setData(customerFundService.selectCustomerFundById(customerFundDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value = "/changeStatus")
	public RespStatus changeStatus(@RequestBody CustomerFundDto vo){ 
		try {			
			customerFundService.update(vo);			
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failRespStatus();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/changeManagerStatus")
	public RespStatus changeManagerStatus(@RequestBody CustomerFundDto vo){ 
		try {
			if(StringUtils.isBlank(vo.getManagerUid())){
				return RespHelper.setFailRespStatus(new RespStatus(), "资方不存在登录账号信息。");
			}
			
			Map<String,Object> paramAccount = new HashMap<String,Object>();
			paramAccount.put("uid", vo.getManagerUid());
			paramAccount.put("mobile", vo.getContactTel());
			paramAccount.put("isEnable", vo.getManagerStatus());
			
			RespStatus resp = httpUtil.getRespStatus(
					Constants.LINK_CREDIT/*Constants.LINK_CREDIT http://127.0.0.1:9911*/, 
					"/credit/user/base/v/updateStatus", 
					paramAccount);
			if(null==resp || !RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				resp.setCodeMsg(RespStatusEnum.FAIL, String.format("同步用户状态数据失败(%s-%s)：%s", vo.getManagerUid(), vo.getContactTel(), resp.getMsg()));
				return resp;
			}
			
			customerFundService.update(vo);			
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failRespStatus();
		}
	}
	
}
