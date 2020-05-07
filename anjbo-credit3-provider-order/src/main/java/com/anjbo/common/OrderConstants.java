package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Configuration
public class OrderConstants {
	
	public static String BASE_FINANCE_LOAN_PROCESS;
	public static String BASE_FINANCE_OUTLOANREPORT_PROCESS;
	public static String BASE_PLACE_ORDER;

	@Value("${base.place.order}")
	public void setBase_place_order(String base_place_order) {
		BASE_PLACE_ORDER = base_place_order;
	}

	@Value("${base.finance.outloanreport.process}")
	public void setBase_finance_outloanreport_process(String base_finance_outloanreport_process) {
		BASE_FINANCE_OUTLOANREPORT_PROCESS = base_finance_outloanreport_process;
	}
	
	@Value("${base.finance.loan.process}")
	public void setBase_finance_loan_process(String base_finance_loan_process) {
		BASE_FINANCE_LOAN_PROCESS = base_finance_loan_process;
	}
	
	
	
	
}
