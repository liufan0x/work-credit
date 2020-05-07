package com.anjbo.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.baidu.BaiduRiskVo;
import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v")
@Api(value = "third对外查询api")
public interface IThirdApiController {
	
    @ApiOperation(value = "查询风险黑名单",httpMethod = "POST",response = Map.class)
    @RequestMapping("/searchBlacklist")
    public Map<String, Object> searchBlacklist(@RequestBody BaiduRiskVo baiduRiskVo);
	
	@ApiOperation(value = "推送文本", httpMethod = "POST", response = Map.class)
	@ApiImplicitParams({ 
		@ApiImplicitParam(value = "uid", name = "用户uid", required = true),
		@ApiImplicitParam(value = "message", name = "推送的消息", required = true) })
	@RequestMapping("/pushText")
	public RespStatus pushText(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "陕国投对接接口", httpMethod = "POST", response = Map.class)
	@RequestMapping("/interfaceCall")
	public RespDataObject<Map<String,Object>> interfaceCall(@RequestBody Map<String, Object> params);
    
	@ApiOperation(value = "陕国影像资料查询接口", httpMethod = "POST", response = Map.class)
	@RequestMapping("/searchSgtBusinfoStatus")
	public RespDataObject<Map<String,Object>> searchSgtBusinfoStatus(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "调进件批量申请接口（异步）【2101】", httpMethod = "POST" )
	@RequestMapping(value = "sgtLending", method= {RequestMethod.POST})
	public abstract RespStatus sgtLending(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "调进件批量申请接口放款结果【2102】", httpMethod = "POST" ,response = SgtongBorrowerInformationDto.class)
	@RequestMapping(value = "sgtLendingResult", method= {RequestMethod.POST})
	public abstract RespStatus sgtLendingResult(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "发送放款指令",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/confirmPayment")
    public RespStatus confirmPayment(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "添加信息",httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "/v/getInsertFile")
	public RespStatus getInsertFile(@RequestBody Map<String,Object> map);
	
}