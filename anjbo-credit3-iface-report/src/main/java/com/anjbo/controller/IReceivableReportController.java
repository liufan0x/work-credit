package com.anjbo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.bean.OrderReceivablleReportVo;
import com.anjbo.common.RespPageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "订单总体概况统计")
@RequestMapping("/receivable/v")
public interface IReceivableReportController {

	@ApiOperation(value = "查询初审人员效率统计", httpMethod = "POST",response = Map.class)
	@RequestMapping("query")
	public   RespPageData<OrderReceivablleReportVo> query(@RequestBody Map<String,Object> paramMap);
}
