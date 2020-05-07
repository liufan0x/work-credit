package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 短信模板配置类
 * Created by Administrator on 2018/6/14.
 */
@Component
@RefreshScope
@Configuration
public class SMSConstants {
	
	
	
	/** 流程节点短信模板  start**/
	
	//审批通过
	public static String SMS_PROCESS_PASS = "你的%s订单(%s %s万)已通过%s；审批人：%s，具体详情请到快马金服APP或信贷系统节点详情里面查看。";
	
	//待处理
	public static String SMS_PROCESS_HANDLE = "您的%s订单（%s %s万）待%s,请登录快马金服APP或信贷系统处理";
	

	/** 流程节点短信模板  end**/
	
	
    /**云南信托电子合同签证成功*/
    public static  String SMS_YNTRUST_QR_CODE_SUCCESS;
    
    /**云南信托生成电子合同签证*/
    public static  String SMS_YNTRUST_QR_CODE;
    
    
    @Value("${SMS_YNTRUST_QR_CODE}")
    public  void setSmsYntrustQrCode(String smsYntrustQrCode) {
        SMS_YNTRUST_QR_CODE = smsYntrustQrCode;
    }

    @Value("${SMS_YNTRUST_QR_CODE_SUCCESS}")
    public  void setSmsYntrustQrCodeSuccess(String smsYntrustQrCodeSuccess) {
        SMS_YNTRUST_QR_CODE_SUCCESS = smsYntrustQrCodeSuccess;
    }
	
	
    //短信模版配置	sms.template开头
    /**短信验证码*/
    public static final String SMS_CODE = "sms.template.code";
    /**新用户密码*/
    public static final String SMS_NEW_USER = "sms.template.password";
    /**重置密码*/
    public static final String SMS_RESETPWD = "sms.template.resetpwd";
    
    /**发送短信来源**/
    public static final String SMS_MORTGAGE_ARCHIVE = "mortgage-archive";
    public static final String SMS_FC_ORDER = "fc-order";

    /**发送短信来源**/
    public static final String SMSCOMEFROM = "ANJBO-CREDIT3-";
    public static final String SMSCOMEFROM_TEST="test";//测试

}
