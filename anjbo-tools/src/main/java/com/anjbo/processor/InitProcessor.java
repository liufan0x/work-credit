package com.anjbo.processor;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

import com.anjbo.bean.tools.CalcRate;
import com.anjbo.service.tools.CalcRateService;
import com.anjbo.service.tools.impl.LoanRateCalcHelper;

/**
 * 应用启动初始化
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午02:26:14
 */
@Controller
public class InitProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = Logger.getLogger(InitProcessor.class);

	@Resource
	private CalcRateService calcRateService;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
		if (event.getApplicationContext().getParent() == null) {
			logger.info("应用启动初始化...");
			calcRateInit();
		}
	}


	private void calcRateInit(){
		List<CalcRate> calcRateList = calcRateService.selectCalcRateList();
		int len = calcRateList.size();
		String[] rates = new String[len];
		Object[] lilv_array = new Object[len];
		for (int i = 0;i < len; i++) {
			CalcRate calcRate = calcRateList.get(i);
			rates[i]=calcRate.getTitle();
			Double[] sdArr = new Double[4];
			sdArr[0] = calcRate.getSd1();// 商贷1年
			sdArr[1] = calcRate.getSd1_3();// 商贷1～3年
			sdArr[2] = calcRate.getSd3_5();// 商贷 3～5年
			sdArr[3] = calcRate.getSd5_30();// 商贷 5-30年
			Double[] gjjArr = new Double[2];
			gjjArr[0] = calcRate.getGjj1_5();// 公积金 1～5年
			gjjArr[1] = calcRate.getGjj5_30();// 公积金 5-30年
			lilv_array[i] = new Object[]{sdArr,gjjArr};
		}
		LoanRateCalcHelper.lilv_array=lilv_array;
		LoanRateCalcHelper.rates = rates;
	}

}
