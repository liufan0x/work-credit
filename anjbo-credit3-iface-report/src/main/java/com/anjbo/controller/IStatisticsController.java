package com.anjbo.controller;

import com.anjbo.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 报表
 * @author yis
 *
 */
@Api(value = "订单风控统计")
@RequestMapping("/statistics/v")
public interface IStatisticsController{

	/**
	 * 回款报备统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@ApiOperation(value = "回款报备统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("selectInPayment")
	public RespPageData<Map<String,Object>> selectInPayment(@RequestBody Map<String,Object> map);

	/**
	 * 出款报备统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@ApiOperation(value = "出款报备统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("/selectOutPayment")
	public RespPageData<Map<String,Object>> selectOutPayment(@RequestBody Map<String,Object> map);

	/**
	 * 查询今日审批统计
	 * @param request
	 * @param map key=cityCode(城市code),productCode(产品code)
	 * @return
	 */
	@ApiOperation(value = "查询今日审批统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("selectToDayOrder")
	public RespDataObject<Map<String,Object>> selectToDayOrder(@RequestBody Map<String,Object> map);

	/**
	 * 创收目标报表
	 * @param request
	 * @param map key=cityCode(城市code),effective(1:上传,2:未上传)
	 * @return
	 */
	@ApiOperation(value = "创收目标报表", httpMethod = "POST",response = Map.class)
	@RequestMapping("/createIncome")
	public  RespPageData<Map<String,Object>> createIncome(@RequestBody Map<String,Object> map);

	/**
	 * 上传目标报表
	 * @param request
	 * @param map key=cityCode(城市code),deptId(部门id),file(上传的Excel文件)
	 * @return
	 */
	@ApiOperation(value = "上传目标报表", httpMethod = "POST",response = Map.class)
	@RequestMapping("/uploadIncome")
	public RespStatus uploadIncome(MultipartFile file,@RequestParam Map<String,Object> map);

	/**
	 * 删除创收目标报表
	 * @param request
	 * @param map key=deptId(部门id),cityCode(城市code)
	 * @return
	 */
	@ApiOperation(value = "删除创收目标报表", httpMethod = "POST",response = Map.class)
	@RequestMapping("/deleteIncome")
	public RespStatus deleteIncome(@RequestBody Map<String,Object> map);
	/**
	 * 下载个人创收模板
	 * @param request
	 * @param map key=deptId(部门id)
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "下载个人创收模板", httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "/downloadIncome",method = RequestMethod.GET)
	public void downloadIncome(@RequestParam Map<String,Object> map,HttpServletResponse response);
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
	@ApiOperation(value = "查询机构放回款统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("/agency")
	public RespPageData<Map<String,Object>> agency(@RequestBody Map<String,Object> map);

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
	@ApiOperation(value = "查询资方放回款统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("/fund")
	public RespPageData<Map<String,Object>> fund(@RequestBody Map<String,Object> map);

	/**
	 * 个人创收概览
	 * @param request
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "个人创收概览", httpMethod = "POST",response = Map.class)
	@RequestMapping("/selectPersonalView")
	public RespPageData<Map<String,Object>> selectPersonalView(@RequestBody Map<String,Object> map);
}
