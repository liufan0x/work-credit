package com.anjbo.controller.tools;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.DegreeBADto;
import com.anjbo.bean.tools.DegreeDto;
import com.anjbo.bean.tools.DegreeLockRecordDto;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.processor.DegreeProcessor;
import com.anjbo.service.tools.DegreeBAService;
import com.anjbo.service.tools.DegreeLockRecordService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;

/**
 * 学位查询
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/tools/degree/v")
public class DegreeController{
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private DegreeLockRecordService degreeLockRecordService;
	@Resource
	private DegreeBAService degreeBAService;

	/**
	 * 学位锁定查询
	 * 
	 * @param request
	 * @param response
	 * @param area
	 * @param fwbm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeQuery")
	public RespDataObject<String> checkDegree(HttpServletRequest request,
			HttpServletResponse response, @RequestBody DegreeDto degreeDto) {
//		log.info("查询学位锁定状态");
//		log.info("区:" + degreeDto.getRegion() + "房屋编码:" + degreeDto.getFwbm()+
//				"房屋地址："+degreeDto.getHouseAddress());
		RespDataObject<String> status = new RespDataObject<String>();
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.ERRORSIX.getMsg());
		try {
			String code = Enums.DegreeEnums.getCodeByContainsName(degreeDto.getRegion());
			if(StringUtil.isNotEmpty(code)){
				String url = ConfigUtil.getStringValue(code);
				status = new DegreeProcessor(url, degreeDto.getFwbm(),code).getDegree();
			} else {
				status.setData("该区域暂未开放，敬请期待");
				return status;
			}
			// 录入查询记录表
			/*if (StringUtil.isNotEmpty(status.getData())&&!status.getData().equals(RespStatusEnum.THIRD_FAIL.getMsg())) {
				DegreeLockRecordDto degreeLockRecordDto = new DegreeLockRecordDto();
				degreeLockRecordDto.setUid(degreeDto.getUid());
				degreeLockRecordDto.setHouseAddress(degreeDto.getHouseAddress());
				degreeLockRecordDto.setDevice(degreeDto.getDevice());
				degreeLockRecordDto.setAreaName(degreeDto.getRegion());
				degreeLockRecordDto.setHouseNo(degreeDto.getFwbm());
				degreeLockRecordDto.setContent(status.getData());
				degreeLockRecordDto.setVersion(degreeDto.getVersion());
				degreeLockRecordService.insertLockRecord(degreeLockRecordDto);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			status.setMsg("教育局系统升级维护中！");
		}
		return status;
	}

	/**
	 * 宝安物业搜索
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeBASearch")
	public RespData<DegreeBADto> degreeBASearch(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody DegreeBADto degreeBADto) {
		RespData<DegreeBADto> status = new RespData<DegreeBADto>();
		if(StringUtil.isEmpty(degreeBADto.getHouseAddress())){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			log.info("关键字不能为空");
			return status;
		}
		try {
			List<DegreeBADto> degreeBADtoList = degreeBAService
					.queryPropertyRoomList(degreeBADto);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setData(degreeBADtoList);
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
			status.setMsg(RespStatusEnum.THIRD_FAIL.getMsg());
		}
		return status;
	}

	/**
	 * 宝安学位查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeBAQuery")
	public RespDataObject<String> degreeBAQuery(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody DegreeBADto degreeBADto) {
		RespDataObject<String> status = new RespDataObject<String>();
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.ERRORSIX.getMsg());
		try {
			List<DegreeBADto> degreeBADtoList = degreeBAService
					.queryBaoAnDegree(degreeBADto);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			if (degreeBADtoList.size() != 0) {
				String data = "查询结果:该学区房已在";
				for (DegreeBADto dba : degreeBADtoList) {
					data += dba.getUserYear() + "被【"
							+ dba.getSchoolName() + "】";
				}
				data += "绑定";
				//log.info(data);
				status.setData(data);
			}
			// 录入查询记录表
			/*if (StringUtil.isNotEmpty(status.getData())) {
				DegreeLockRecordDto degreeLockRecordDto = new DegreeLockRecordDto();
				degreeLockRecordDto.setUid(degreeBADto.getUid());
				degreeLockRecordDto.setHouseAddress(degreeBADto.getHouseAddress());
				degreeLockRecordDto.setDevice(degreeBADto.getDevice());
				degreeLockRecordDto.setAreaName(Enums.DegreeEnums.ORGION4.getName()+"区");
				degreeLockRecordDto.setContent(status.getData());
				degreeLockRecordDto.setVersion(degreeBADto.getVersion());
				degreeLockRecordService.insertLockRecord(degreeLockRecordDto);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 学位锁定查询记录
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeLockRecordList")
	public RespData<DegreeLockRecordDto> degreeLockRecordList(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody DegreeLockRecordDto degreeLockRecordDto) {
		RespData<DegreeLockRecordDto> status = new RespData<DegreeLockRecordDto>();
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.ERRORSIX.getMsg());
		try {
			List<DegreeLockRecordDto> list = degreeLockRecordService
					.findLockRecord(degreeLockRecordDto);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 学位锁定查询记录总数
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeLockRecordCount")
	public RespDataObject<Integer> degreeLockRecordCount(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody DegreeLockRecordDto degreeLockRecordDto) {
		RespDataObject<Integer> status = new RespDataObject<Integer>();
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.ERRORSIX.getMsg());
		try {
			int count = degreeLockRecordService
					.findLockRecordCount(degreeLockRecordDto);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setData(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
