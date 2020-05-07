/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.BaseUserController;
import com.anjbo.bean.DeptDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IDeptController;
import com.anjbo.controller.impl.api.UserApiController;
import com.anjbo.service.DeptService;
import com.anjbo.service.UserService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
@RestController
public class DeptController extends BaseUserController implements IDeptController{

	@Resource private DeptService deptService;

	@Resource private UserService userService;
	
	@Resource private UserApiController userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<DeptDto> page(@RequestBody DeptDto dto){
		RespPageData<DeptDto> resp = new RespPageData<DeptDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(deptService.search(dto));
			resp.setTotal(deptService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<DeptDto> search(@RequestBody DeptDto dto){ 
		RespData<DeptDto> resp = new RespData<DeptDto>();
		try {
			List<DeptDto> list = deptService.search(dto);
			UserDto ud = userApi.getUserDto();
			UserDto userDto = new UserDto();
			userDto.setAgencyId(ud.getAgencyId());
			Map<String, Map<String, Integer>> userCountMap = userService.selectUserCountGroupByDeptId(userDto);
			Map<String, Integer> map0 = userCountMap.get("map0");//普通
			Map<String, Integer> map1 = userCountMap.get("map1");//上级
			for (DeptDto dept : list) {
				int count = 0;
				Set<Integer> set = getAllDeptSet(list, dept.getId());
				for (Integer deptId : set) {
					if(deptId.equals(ud.getDeptId())){//当前部门
						if(!"admin".equals(ud.getAccount())){//排除管理员自己
							count+=1;//加上自己
						}
						if("1".equals(ud.getIdentity()+"")){//如果是上级加上同级的普通成员
							count+=MapUtils.getIntValue(map0,String.valueOf(deptId));
						}
					}else{//子部门
						count+=MapUtils.getIntValue(map0,String.valueOf(deptId));
						count+=MapUtils.getIntValue(map1,String.valueOf(deptId));
					}
				}
				dept.setUserCount(count);
			}
			return RespHelper.setSuccessData(resp, list);
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<DeptDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<DeptDto> find(@RequestBody DeptDto dto){ 
		RespDataObject<DeptDto> resp = new RespDataObject<DeptDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, deptService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new DeptDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<DeptDto> add(@RequestBody DeptDto dto){ 
		RespDataObject<DeptDto> resp = new RespDataObject<DeptDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, deptService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new DeptDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody DeptDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			deptService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

}