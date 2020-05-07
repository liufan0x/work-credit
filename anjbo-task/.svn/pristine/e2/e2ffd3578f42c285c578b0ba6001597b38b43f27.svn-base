package com.anjbo.controller.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.SZDealDto;
import com.anjbo.common.HttpUtil;
import com.anjbo.controller.BaseController;
import com.anjbo.task.estatedeal.SZDataUtil;

/**
 * 示例信息
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:14:41
 */
@Controller
@RequestMapping("/task/system/eg/v")
public class EgController extends BaseController{
	HttpUtil httpUtil = new HttpUtil();
	@RequestMapping(value="/secondHandTrendUrl",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getdealchartdata(@RequestBody Map<String, String> hashMap){
		//新增Cookie设置
    	String cookie = httpUtil.getCookie(SZDataUtil.secondHandTrendUrl);
    	System.out.println("Cookie="+cookie);
    	hashMap.put("Cookie", cookie);
    	String result=httpUtil.get(SZDataUtil.secondHandTrendUrl,hashMap);
    	return result;
	}
	@RequestMapping(value="/getSzData",produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> getSzData(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SZDealDto> shenZhenSecondHandData = SZDataUtil.getSZSecondHandData();
			map.put("shenZhenSecondHandData", shenZhenSecondHandData);
			List<SZDealDto> shenZhenSecondHandDetail = SZDataUtil.getSZSecondHandDetail();
			map.put("shenZhenSecondHandDetail", shenZhenSecondHandDetail);
			List<SZDealDto> shenZhenOneHandData = SZDataUtil.getSZOneHandData();
			map.put("shenZhenOneHandData", shenZhenOneHandData);
			/*try {
				List<DealdataTrend> shenZhenSecondHandDataTrend = SZDataUtil.getSZSecondHandTrend();
				map.put("shenZhenSecondHandDataTrend", shenZhenSecondHandDataTrend);
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("获取深圳二手房均价趋势图数据失败，错误信息："+e.getMessage());*/
				map.put("shenZhenSecondHandDataTrend",new ArrayList<DealdataTrend>());
			//}
			DealdataAvgPricce avgPrcie=SZDataUtil.getSZOneHandAvgPrice();
			map.put("avgPrcie", avgPrcie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
