package com.anjbo.task.fc.order;

import javax.annotation.Resource;
import org.apache.log4j.Logger;

import com.anjbo.common.Enums;
import com.anjbo.common.RespStatus;
import com.anjbo.service.fc.order.ReceivableReportService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;

/**
 * 订单总体概况定时
 * @author yis
 *
 */
public class ReceivableReportTask {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private ReceivableReportService reportService;
   
  /**
	* @Title: run 
	* @param 
	* @return void
	*/
	public void run(){
		try{
			String toolsUrl = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString());
			RespStatus respStatus=HttpUtil.getRespStatus(toolsUrl, "/credit/order/huanTemp/updataFlowTimeByAll",null);
			if("FAIL".equals(respStatus.getCode())){
				log.error("定时更新及时流水操作时间失败 ，请手动更新...");
			}
			//更新报表
			reportService.runJob();
		} catch (Exception e){
			log.error("更新报表订单总体概况失败", e);
		}
	}
}
