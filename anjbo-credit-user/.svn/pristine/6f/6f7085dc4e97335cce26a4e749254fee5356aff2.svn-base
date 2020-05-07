package com.anjbo.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.DeptService;
import com.anjbo.service.UserService;


@Controller
@RequestMapping("/credit/user/dept")
public class DeptController extends BaseController{	
	@Resource DeptService deptService;
	@Resource UserController userController;
	@Resource UserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/list") 
	public RespPageData<DeptDto> ignList(HttpServletRequest request,@RequestBody DeptDto deptDto){
		if(null==deptDto || deptDto.getAgencyId()<1){
			RespPageData<DeptDto> resp = new RespPageData<DeptDto>();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return resp;
		}
		return this.list(request, deptDto);
	}
	@ResponseBody
	@RequestMapping(value = "/v/list") 
	public RespPageData<DeptDto> list(HttpServletRequest request,@RequestBody DeptDto deptDto){
		RespPageData<DeptDto> resp = new RespPageData<DeptDto>();
		try {			
			deptDto.setAgencyId(deptDto.getAgencyId()>0 ? deptDto.getAgencyId() : getUserDto(request).getAgencyId());
			List<DeptDto> list = deptService.selectDeptList(deptDto);
			if(null != request.getSession().getAttribute(Constants.LOGIN_USER_KEY)){ //后台系统调用，统计部门人员数量
				UserDto ud = getUserDto(request);
				UserDto userDto = new UserDto();
				userDto.setAgencyId(deptDto.getAgencyId());
				Map<String, Map<String, Integer>> userCountMap = userController.mapUserCountGroupByDeptId(request, userDto).getData();
				Map<String, Integer> map0 = userCountMap.get("map0");//普通
				Map<String, Integer> map1 = userCountMap.get("map1");//上级
				for (DeptDto dept : list) {
					int count = 0;
					Set<Integer> set = getAllDeptSet(list, dept.getId());
					for (Integer deptId : set) {
						if(deptId == ud.getDeptId()){//当前部门
							if(!"admin".equals(ud.getAccount())){//排除管理员自己
								count+=1;//加上自己
							}
							if(ud.getIdentity()==1){//如果是上级加上同级的普通成员
								count+=MapUtils.getIntValue(map0,String.valueOf(deptId));
							}
						}else{//子部门
							count+=MapUtils.getIntValue(map0,String.valueOf(deptId));
							count+=MapUtils.getIntValue(map1,String.valueOf(deptId));
						}
					}
					dept.setUserCount(count);
				}
			}			
			resp.setRows(list);
			resp.setTotal(deptService.selectDeptCount(deptDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	/**
	 * 根据当前部门获取所有部门编号
	 * @param request
	 * @param deptDto
	 * <br>         id 当前部门ID
	 * <br>         agencyId 机构编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/getAllDepts") 
	public RespDataObject<String>  getAllDepts(HttpServletRequest request,@RequestBody DeptDto deptDto){
		RespDataObject<String> resp = new RespDataObject<String>();
		List<DeptDto> list = deptService.selectDeptList(deptDto);
		Set<Integer> set = getAllDeptSet(list,deptDto.getId());
		String deptIds = set.toString();
		deptIds = deptIds.replace("[","").replace("]","");
		resp.setData(deptIds);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 同步钉钉部门
	 * @Author KangLG<2017年11月1日>
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/autoSyncDingtalkDept") 
	public void autoSyncDingtalkDept(HttpServletRequest request){
		deptService.autoSyncDingtalkDept();		
	}
	
	/**
	 * 添加/编辑
	 * @Author KangLG<2017年11月6日>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody DeptDto dto){ 
		try {
			dto.setAgencyId(this.getUserDto(request).getAgencyId());
			if(dto.getId()>0){
				dto.setUpdateUid(getUserDto(request).getAccount());
				deptService.update(dto);
				return RespHelper.setSuccessRespStatus(new RespStatus());
			} else {
				dto.setCreateUid(getUserDto(request).getAccount());
				deptService.insert(dto);
				return RespHelper.setSuccessRespStatus(new RespStatus());
			}
		} catch (Exception e) {
			logger.error("操作失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
	@ResponseBody
	@RequestMapping(value = "/v/delete")
	public RespStatus delete(HttpServletRequest request, @RequestBody DeptDto dto){ 
		try {
			dto.setAgencyId(this.getUserDto(request).getAgencyId());
			dto.setPid(dto.getId());			
			UserDto uDto = new UserDto();
			uDto.setAgencyId(dto.getAgencyId());
			uDto.setDeptId(dto.getId());
			
			if(deptService.search(dto).size() > 0){
				return RespHelper.setFailRespStatus(new RespStatus(), "请删除此部门下的子部门后，再删除此部门");
			}else if(userService.search(uDto).size() > 0){
				return RespHelper.setFailRespStatus(new RespStatus(), "请删除此部门下的成员后，再删除此部门");
			}			
			deptService.delete(dto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			logger.error("操作失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
}
