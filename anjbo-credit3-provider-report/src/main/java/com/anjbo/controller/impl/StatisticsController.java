package com.anjbo.controller.impl;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.*;
import com.anjbo.controller.IStatisticsController;
import com.anjbo.controller.ReportBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.ExcelService;
import com.anjbo.service.StatisticsService;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表
 * @author yis
 *
 */
@RestController
public class StatisticsController extends ReportBaseController implements IStatisticsController{
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private ExcelService excelService;
	@Resource 
	private UserApi userApi;
	@Resource
	private DataApi dataApi;
	
	/**
	 * 回款报备统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> selectInPayment(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try {
			map.put("type","cityList");
			Integer count = statisticsService.selectInPaymentCount(map);
			List<Map<String,Object>> list = statisticsService.selectInPayment(map);
			result.setTotal(count);
			result.setRows(list);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("回款报备统计异常:",e);
		}
		return result;
	}

	/**
	 * 出款报备统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> selectOutPayment(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try {
			map.put("type","cityList");
			Integer count = statisticsService.selectOutPaymentCount(map);
			List<Map<String,Object>> list = statisticsService.selectOutPayment(map);
			result.setRows(list);
			result.setTotal(count);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("出款报备统计异常:",e);
		}
		return result;
	}

	/**
	 * 查询今日审批统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@Override
	public RespDataObject<Map<String,Object>> selectToDayOrder(@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		try{
			Map<String,Object> m = statisticsService.selectToDayOrder(map);
			RespHelper.setSuccessDataObject(result,m);
		} catch (Exception e){
			RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
			log.error("查询今日审批统计异常:",e);
		}
		return result;
	}

	/**
	 * 创收目标报表
	 * @param request
	 * @param map key=cityCode(城市code),effective(1:上传,2:未上传)
	 * @return
	 */
	@Override
	public  RespPageData<Map<String,Object>> createIncome(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try{
			//分公司
			List<DictDto> branchCompany = dataApi.getDictDtoListByType("branchCompany");
			UserDto userDto = userApi.getUserDto();
			map.put("agencyId",userDto.getAgencyId());
			map.put("roleName", Enums.RoleEnum.CHANNEL_MANAGER.getName());
			String type = "cityList";
			if(StringUtil.isBlank(MapUtils.getString(map,"type")))
				map.put("type",type);
			List<Map<String,Object>> count = statisticsService.selectCityCount(map);
			List<Map<String,Object>> list = statisticsService.createIncome(map,branchCompany);
			result.setTotal(count.size());
			result.setRows(list);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			logger.error("查询创收目标报表异常",e);
		}
		return result;
	}

	/**
	 * 上传目标报表
	 * @param request
	 * @param map key=cityCode(城市code),deptId(部门id),file(上传的Excel文件)
	 * @return
	 */
	@Override
	public RespStatus uploadIncome( MultipartFile file,@RequestParam Map<String,Object> map){
		RespStatus result = new RespStatus();
		try {
			if(null==file)
				return RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			String fileName = file.getOriginalFilename();
			boolean flg = excelService.isExcel(fileName);
			if(!flg)
				return RespHelper.setFailRespStatus(result,"请上传Excel文件");
			UserDto user = userApi.getUserDto();
			String cityCode = MapUtils.getString(map,"cityCode");
			List<DictDto> dics = dataApi.getDictDtoListByType("cityList");
			for (DictDto d:dics){
				if(d.getCode().equals(cityCode)){
					map.put("cityName",d.getName());
					break;
				}
			}
			statisticsService.uploadIncome(file,map,user,excelService,result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("上传目标报表异常：",e);
		}
		return result;
	}

	/**
	 * 删除创收目标报表
	 * @param request
	 * @param map key=deptId(部门id),cityCode(城市code)
	 * @return
	 */
	@Override
	public RespStatus deleteIncome(@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		try{
			if(StringUtil.isBlank(MapUtils.getString(map,"cityCode"))
					||"undefined".equals(MapUtils.getString(map,"cityCode"))) {
				RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			map.put("updateUid",user.getUid());
			map.put("createUid",user.getUid());
			statisticsService.cancelIncomeFileByDeptId(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("删除个人创收目标报表异常:",e);
		}
		return result;
	}
	/**
	 * 下载个人创收模板
	 * @param request
	 * @param map key=deptId(部门id)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void downloadIncome(@RequestParam Map<String,Object> map,HttpServletResponse response){
		try{
			String cityCode = MapUtils.getString(map,"cityCode");
			if(StringUtil.isBlank(cityCode)
					||"undefined".equals(cityCode))
				return;
			List<DictDto> dics = dataApi.getDictDtoListByType("cityList");
			for (DictDto d:dics){
				if(d.getCode().equals(cityCode)){
					map.put("cityName",d.getName());
					break;
				}
			}
			UserDto user = userApi.getUserDto();
			map.put("agencyId",user.getAgencyId());
			statisticsService.downloadIncome(map,user,excelService,response);
		} catch (Exception e){
			logger.error("下载个人创收模板异常：",e);
		}
	}
	/**
	 * 查询机构放回款统计
	 * @param request
	 * @param map
	 * productCode(产品code),cooperativeModeId(1兜底,2非兜底)
	 * timeWhere=lastWeek(检索条件：上周)
	 * timeWhere=lastMonth(检索条件：上月)
	 * timeWhere=yesterday(检索条件：昨日)
	 * timeWhere=thisMonth(检索条件：当月)
	 * startTime(开始时间)
	 * endTime(结束时间)
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> agency(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try{
			String startTime = MapUtils.getString(map,"startTime");
			String endTime = MapUtils.getString(map,"startTime");
			if(StringUtil.isNotBlank(startTime)
					||StringUtil.isNotBlank(endTime)){
				map.put("timeWhere","");
			}
			if(map.containsKey("fundLoanAmountSort")){
				map.put("loanAmountSort",MapUtils.getString(map,"fundLoanAmountSort"));
			}
			Integer count = statisticsService.statisticsAgencyCount(map);
			List<Map<String,Object>> list = statisticsService.statisticsAgency(map);
			result.setRows(list);
			result.setTotal(count);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("查询机构放回款统计:",e);
		}
		return result;
	}

	/**
	 * 查询资方放回款统计
	 * @param request
	 * @param map
	 * productCode(产品code),cooperativeModeId(1兜底,2非兜底)
	 * timeWhere=lastWeek(检索条件：上周)
	 * timeWhere=lastMonth(检索条件：上月)
	 * timeWhere=yesterday(检索条件：昨日)
	 * timeWhere=thisMonth(检索条件：当月)
	 * startTime(开始时间)
	 * endTime(结束时间)
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> fund(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try{
			String orderCountSort = MapUtils.getString(map,"orderNoCountSort");
			if(StringUtil.isNotBlank(orderCountSort)){
				map.put("orderCountSort",orderCountSort);
			}
			List<Map<String,Object>> list = statisticsService.statisticsFund(map);
			Integer count = statisticsService.statisticsFundCount(map);
			result.setTotal(count);
			result.setRows(list);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("查询资方放回款统计异常",e);
		}
		return result;
	}

	/**
	 * 个人创收概览
	 * @param request
	 * @param map
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> selectPersonalView(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try{
			String month = "";
			Object startTime = MapUtils.getString(map,"startTime");
			Object endTime = MapUtils.getString(map,"endTime");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar instance = Calendar.getInstance();
			try {
				if (null!=startTime) {
					String st = String.valueOf(startTime);
					if(startTime instanceof Date) {
						Date d1 = format.parse(st);
						instance.setTime(d1);
						month = "" + (instance.get(Calendar.MONTH) + 1);
					} else if(st.length()>6){
						st = st.replaceAll("/","").replaceAll("-","");
						String year = st.substring(0,4);
						month = st.substring(4,6).replace("0","");
						map.put("year", year);
					}
				} else if (null!=endTime) {
					String se = String.valueOf(endTime);
					if(endTime instanceof Date) {
						Date d1 = format.parse(se);
						instance.setTime(d1);
						month = ""+(instance.get(Calendar.MONTH)+1);
					} else if(se.length()>6){
						se = se.replaceAll("/","").replaceAll("-","");
						String year = se.substring(0,4);
						month = se.substring(4,6).replace("0","");
						map.put("year", year);
					}
				} else if("lastMonth".equals(MapUtils.getString(map,"timeWhere"))) {
					if (0 == instance.get(Calendar.MONTH)) {
						month = "1";
						map.put("year", instance.get(Calendar.YEAR) - 1);
					} else {
						month = "" + instance.get(Calendar.MONTH);
						map.put("year", instance.get(Calendar.YEAR));
					}
				}else if("lastYear".equals(MapUtils.getString(map,"timeWhere"))){
					map.put("year", instance.get(Calendar.YEAR) - 1);
					month = ""+(instance.get(Calendar.MONTH)+1);
				} else {
					month = ""+(instance.get(Calendar.MONTH)+1);
					map.put("year", instance.get(Calendar.YEAR));
				}
			} catch (Exception e){
				logger.error("个人创收概览设置查询时间条件异常:",e);
				month = ""+(instance.get(Calendar.MONTH)+1);
				map.put("year", instance.get(Calendar.YEAR));
			}
			UserDto userDto  = userApi.getUserDto();
			map.put("agencyId",userDto.getAgencyId());
			map.put("roleName", Enums.RoleEnum.CHANNEL_MANAGER.getName());
			List<UserDto> allUser = userApi.selectAllUserDto();
			List<DictDto> listCity = dataApi.getDictDtoListByType("cityList");
			map.put("month",month);
			Integer count = statisticsService.selectPersonalViewCount(map);
			List<Map<String,Object>> list = statisticsService.selectPersonalView(map,allUser,listCity);
			result.setRows(list);
			result.setTotal(count);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			log.error("查询个人创收概览异常:",e);
		}
		return result;
	}

}
