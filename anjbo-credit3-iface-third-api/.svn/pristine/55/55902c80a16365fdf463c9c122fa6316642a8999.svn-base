package com.anjbo.controller.ordinary;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value="普通资方")
@RequestMapping(value="/ordinary")
public interface IOrdinaryCapitalController {
	
	
	@ApiOperation(value = "普通资方获取影像资料分类",httpMethod = "POST",response = RespStatus.class)
	@ApiImplicitParam(value = "orderNo",name = "订单号",required=false)
	@RequestMapping("/v/loadOrdinaryBusInfo")
	public RespDataObject<Map<String,Object>> loadOrdinaryBusInfo(@RequestBody Map<String,Object> map);
	
	//查询信息。 借款信息，审批意见（注意要判断是否有开放）
	@ApiOperation(value = "普通资方推送信息查询",httpMethod = "POST",response = RespStatus.class)
	@ApiImplicitParam(value = "orderNo",name = "订单号",required=false)
	@RequestMapping(value = "/v/ordinaryDetail")
	public RespDataObject<Map<String,Object>> ordinaryDetail(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "普通资方保存信息",httpMethod = "POST",response = RespStatus.class)
	@ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
	@RequestMapping("/v/ordinaryApply")
	public RespStatus ordinaryApply(@RequestBody Map<String,Object> map);
	
	@ApiOperation(value = "普通资方上传影像资料删除",httpMethod = "POST",response = RespStatus.class)
	@ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
	@RequestMapping("/v/deleteOrdinaryImg")
	public RespStatus deleteOrdinaryImg(@RequestBody Map<String,Object> map);
   
	@ApiOperation(value = "普通资方上传影像资料添加",httpMethod = "POST",response = RespStatus.class)
	@ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
	@RequestMapping("/v/addOrdinaryImg")
	public RespStatus addOrdinaryImg(@RequestBody Map<String,Object> map);
}
