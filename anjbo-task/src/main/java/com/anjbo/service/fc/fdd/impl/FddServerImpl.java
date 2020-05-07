package com.anjbo.service.fc.fdd.impl;

import com.anjbo.common.AnjboException;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.service.fc.fdd.FddServer;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("fddServer")
public class FddServerImpl implements FddServer{
	private static Map<String,String> chiefinspector;
	public static final String AMS_CODE = "hyt";
	private Logger log = Logger.getLogger(getClass());
	static {
		if(null==chiefinspector){
			chiefinspector = new HashMap<String,String>();
			chiefinspector.put("深圳银行渠道部","刘黎明,13825285206");
			//chiefinspector.put("深圳银行渠道部","刘黎明,18039237829");
			chiefinspector.put("深圳贷款渠道部","李坚华,13501593393");
			chiefinspector.put("深圳地产渠道部","刘俊龙,13510243739");
			chiefinspector.put("深圳大客户渠道部","陈敏,18676668599");
			chiefinspector.put("广州分公司","徐良,13560797797");
			chiefinspector.put("东莞分公司","汤琦,15818760711");
			chiefinspector.put("厦门分公司","杨俊,18923885427");
			chiefinspector.put("郑州分公司","李浩,18530807888");
			chiefinspector.put("杭州分公司","彭磊,13631573925");
			chiefinspector.put("长沙分公司","李恒建,13428709262");
			chiefinspector.put("南京分公司","庄伟思,18805154314");
			chiefinspector.put("武汉分公司","徐亚南,13631236585");
			chiefinspector.put("上海分公司","郑旭锋,15602966079");
			chiefinspector.put("重庆办事处","胡湘,18129863201");
			chiefinspector.put("南宁办事处","黎志聪,15815561809");
		}
	}
	
	public static void main(String[] args) throws AnjboException {
		//FddServerImpl fddServerImpl = new FddServerImpl();
		//fddServerImpl.run();
	}
	
	@Override
	public void run() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list =HttpUtil.getList(ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString()), "/credit/finance/afterLoanList/allLoan",null,Map.class);
			SimpleDateFormat format = new SimpleDateFormat("HH");
			Integer closeMsg = 0;
			for (Map<String, Object> params : list) {
				System.out.println(params);
				int overdueDay = MapUtils.getIntValue(params, "overdueDay", 0);
				int last = MapUtils.getIntValue(params, "last");//1是2否(最后一期)
				Integer repaymentType = MapUtils.getIntValue(params, "repaymentType");//1先息后本,2等额本息
				String branchCompany = MapUtils.getString(params,"branchCompany");
				closeMsg = MapUtils.getInteger(params,"closeMsg",0);

				if(9==closeMsg)continue;

				Set<String> keys = chiefinspector.keySet();
				if(StringUtil.isNotBlank(branchCompany)) {
					for (String key : keys) {
						if (key.equals(branchCompany)) {
							String val = MapUtils.getString(chiefinspector, key);
							String CHIEF_INSPECTOR_NAME = val.split(",")[0];
							String CHIEF_INSPECTOR_PHONE = val.split(",")[1];
							params.put("CHIEF_INSPECTOR_NAME", CHIEF_INSPECTOR_NAME);
							params.put("CHIEF_INSPECTOR_PHONE", CHIEF_INSPECTOR_PHONE);
							break;
						}
					}
				}
				/**
				 * 还款日前5天/前1天 上午10点(客户与渠道经理)
				 * 还款日当天 上午7点(客户与渠道经理)
				 */
				if (overdueDay ==0
						&&"07".equals(format.format(System.currentTimeMillis()))){
					repaymentDate(params,AMS_CODE,closeMsg);
				} else if((overdueDay==5||overdueDay==1)
						&&"10".equals(format.format(System.currentTimeMillis()))){
					repaymentManagerAgoX(params,overdueDay,AMS_CODE);
					if(1!=closeMsg) {
						repaymentConsumerAgoX(params, overdueDay, AMS_CODE);
					}
				}
				if(overdueDay>-1){
					continue;
				}
				overdueDay = Math.abs(overdueDay);
				//先息后本
				if(1==repaymentType){
					//逾期第1天 上午七点 (客户与渠道经理)
					if(1==overdueDay
							&&"07".equals(format.format(System.currentTimeMillis()))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);
						if(last==1){
							overdueXLoaning(params,last,repaymentType,overdueDay,AMS_CODE);
						}
					//逾期第2天 上午七点 (客户与渠道经理和贷中管理员)
					} else if(2==overdueDay
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))) {
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params, last, repaymentType, overdueDay,AMS_CODE);
						overdueXLoaning(params, last, repaymentType, overdueDay,AMS_CODE);
						if (last == 1) {
							overdueXInspector(params, last, repaymentType, overdueDay,AMS_CODE);
						}
					//逾期第4,5,6天 上午七点下午两点 (客户与渠道经理和贷中管理员)
					} else if(overdueDay>=3&&overdueDay<=5
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);
						if(last==2) {
							overdueXLoaning(params, last, repaymentType, overdueDay,AMS_CODE);
						} else if(last==1){
							overdueXAfter(params, last, repaymentType, overdueDay,AMS_CODE);
							overdueXInspector(params, last, repaymentType, overdueDay,AMS_CODE);
						}
					//逾期第7-30天  上午七点下午两点 (客户与渠道经理和业务总监和贷后管理员)
					} else if(overdueDay>=6&&overdueDay<=30
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);
						overdueXInspector(params,last,repaymentType,overdueDay,AMS_CODE);
						overdueXAfter(params,last,repaymentType,overdueDay,AMS_CODE);
						if(overdueDay>=15) {
							overdueXJustice(params, last, repaymentType, overdueDay, AMS_CODE);
						}
					}

				//等额本息
				} else if(2==repaymentType){
					//逾期第2天 上午七点 客户,渠道经理
					if(1==overdueDay
							&&"07".equals(format.format(System.currentTimeMillis()))) {
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);

					//逾期第3天 上午七点下午两点 客户,渠道经理
					} else if(2==overdueDay
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);

					//逾期第4,5 上午七点下午两点 客户,渠道经理,贷中管理员
					} else if(overdueDay>=3&&overdueDay<=4
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);
						overdueXLoaning(params,last,repaymentType,overdueDay,AMS_CODE);

					//逾期第6-30 上午七点下午两点 客户,渠道经理,业务总监,贷后管理
					} else if(overdueDay>=5&&overdueDay<=30
							&&("07".equals(format.format(System.currentTimeMillis()))
							||"14".equals(format.format(System.currentTimeMillis())))){
						if(1!=closeMsg) {
							overdueXConsumer(params, last, repaymentType, overdueDay, AMS_CODE);
						}
						overdueXManager(params,last,repaymentType,overdueDay,AMS_CODE);
						overdueXInspector(params,last,repaymentType,overdueDay,AMS_CODE);
						overdueXAfter(params,last,repaymentType,overdueDay,AMS_CODE);
						if(overdueDay>=15) {
							overdueXJustice(params, last, repaymentType, overdueDay, AMS_CODE);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("房抵贷短信通知异常啦");
//			try {
//				AmsUtil.smsSend("18926473051", ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()), "房抵贷短信通知异常啦", Constants.SMSCOMEFROM);
//			} catch (AnjboException e1) {
//				e1.printStackTrace();
//			}
		}
	}

	/**
	 * 还款日期前3天
	 * @param params
	 * @throws AnjboException
	 */
	private void repaymentDateFront3(Map<String, Object> params,String amsCode) throws AnjboException{
		params.put("borrowContent", "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司办理的业务还有3天到期，应还金额"+MapUtils.getString(params, "repayAmount")+"元，请及时将应还款项存入约定账户，如有特殊状况，请及时联系我司相关人员，谢谢。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。");
		sendBorrow(params,amsCode);
	}
	/**
	 * 还款日前X天客户短信模板
	 * @param params
	 * @param day 提前的天数
	 * @throws AnjboException
	 */
	private void repaymentConsumerAgoX(Map<String, Object> params,Integer day,String amsCode) throws AnjboException{
		String templet = "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司贷款将于本月"+MapUtils.getString(params,"repaymentDateToDay")+"日还款，本期应还金额"+MapUtils.getString(params, "repayAmount")+"元，为维护您良好的信用记录，请提前做好还款安排，谢谢。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。";
		params.put("borrowContent",templet);
		sendBorrow(params,amsCode);
		//System.out.println("还款前客户通知:"+templet);
	}
	/**
	 * 还款日前X天渠道经理短信模板
	 * @param params
	 * @param day 提前的天数
	 * @throws AnjboException
	 */
	private void repaymentManagerAgoX(Map<String, Object> params,Integer day,String amsCode) throws AnjboException{
		String templet = "还款提醒：您名下客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，在我司办理的房抵贷业务将于本月"+MapUtils.getString(params,"repaymentDateToDay")+"日还款，请做好还款提醒工作。";
		params.put("channelManagerContent",templet);
		sendChannelManager(params,amsCode);
		//System.out.println("还款前渠道经理通知:"+templet);
	}
	/**
	 * 本金到期日前5天
	 * @param params
	 * @throws AnjboException
	 */
	private void capitalExpireDateFront5(Map<String, Object> params,String amsCode) throws AnjboException{
		String templet = "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司办理的房抵贷业务还有5天到期，应还金额"+MapUtils.getString(params, "repayAmount")+"元，请及时将应还款项存入约定账户，如有特殊状况，请及时联系我司相关人员，谢谢。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。";
		params.put("borrowContent",templet );
		sendBorrow(params,amsCode);
		//System.out.println(templet);
	}
	
	/**
	 * 还款日当天
	 * @param params
	 * @throws AnjboException
	 */
	private void repaymentDate(Map<String, Object> params,String amsCode,Integer closeMsg) throws AnjboException{
		String templet = "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司贷款将于今日还款，本期应还金额"+MapUtils.getString(params, "repayAmount")+"元。如已还款请忽略本条提醒。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。";
		params.put("borrowContent",templet);
		if(1!=closeMsg) {
			sendBorrow(params, amsCode);
		}
		//System.out.println("还款当日客户通知:"+templet);
		templet = "还款提醒：您名下客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务将于今天还款，请及时做好还款提醒。";
		params.put("channelManagerContent",templet);
		sendChannelManager(params,amsCode);
		//System.out.println("还款当日渠道经理通知:"+templet);
	}
	
	/**
	 * 还款日次日
	 * @param params
	 * @throws AnjboException
	 */
	private void repaymentDateTomorrow(Map<String, Object> params,String amsCode) throws AnjboException{
		int periods = MapUtils.getIntValue(params, "repaymentPeriods",0);
		params.put("borrowContent", "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司办理的房抵贷业务，已逾期1天，应还金额"+MapUtils.getString(params, "repayAmount")+"元，并已开始计收罚息，请尽快联系我司相关人员，谢谢。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。");
		params.put("channelManagerContent", "客户逾期通知："+MapUtils.getString(params, "channelManagerName")+"同事您好，您名下客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，在我司办理的房抵贷业务第"+periods+"期，已逾期1天未还款，请及时处理。");
		sendBorrow(params,amsCode);
	}

	/**
	 * 逾期天数两天以及以上发送客户短信模板
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXConsumer(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String templet = "尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您由我司协助办理的贷款已逾期"+lateDay+"天，请及时处理或联系我司相关人员。如已还款请忽略本条提醒。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。";
		params.put("borrowContent",templet);
		sendBorrow(params,amsCode);
		//System.out.println("逾期客户通知:"+templet);
	}

	/**
	 * 逾期天数两天以及以上发送渠道经理短信模板
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXManager(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String templet = "客户逾期通知：您名下客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务第"+MapUtils.getIntValue(params, "repaymentPeriods",0)+"期，已逾期"+lateDay+"天，请及时处理。";
		params.put("channelManagerContent",templet);
		sendChannelManager(params,amsCode);
		//System.out.println("逾期渠道经理通知:"+templet);
	}

	/**
	 * 逾期天数两天以及以上发送贷中短信模板
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXLoaning(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String templet = "客户逾期通知：客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务第"+MapUtils.getIntValue(params, "repaymentPeriods",0)+"期，已逾期"+lateDay+"天，请及时处理";
		params.put("loanContent",templet);
		sendLoan(params,amsCode);
		//System.out.println("逾期贷中通知:"+templet);
	}

	/**
	 * 逾期天数两天以及以上发送贷后管理人员
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXAfter(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String templet = "客户逾期通知：客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务第"+MapUtils.getIntValue(params, "repaymentPeriods",0)+"期，已逾期"+lateDay+"天，请及时处理";
		params.put("loanContent",templet);
		sendLoanAfter(params,amsCode);
		//System.out.println("逾期贷后通知："+templet);
	}
	/**
	 * 逾期天数两天以及以上发送业务总监
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXInspector(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String chief_inspector_name = MapUtils.getString(params,"CHIEF_INSPECTOR_NAME");
		if(StringUtil.isBlank(chief_inspector_name)){
			log.info("逾期业务总监通知：没有获取到该订单所属于的总监信息");
			return;
		}
		String templet = "客户逾期通知：客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务第"+MapUtils.getIntValue(params, "repaymentPeriods",0)+"期，已逾期"+lateDay+"天，请及时处理";
		params.put("loanContent",templet);
		sendChiefInspector(params,amsCode);
		//System.out.println("逾期业务总监通知："+templet);
	}
	/**
	 * 逾期天数两天以及以上发送法务
	 * @param params
	 * @param last 是否是最后一期(1是2否)
	 * @param repaymentType 1:先息后本,2:等额本息
	 * @param lateDay 逾期天数
	 */
	public void overdueXJustice(Map<String, Object> params,Integer last,Integer repaymentType,Integer lateDay,String amsCode) throws AnjboException {
		String justice_name = ConfigUtil.getStringValue(null,"JUSTICE_NAME",true);
		String templet = "客户逾期通知：客户"+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，房抵贷业务第"+MapUtils.getIntValue(params, "repaymentPeriods",0)+"期，已逾期"+lateDay+"天，请及时处理";
		params.put("loanContent",templet);
		sendJustice(params,amsCode);
		//System.out.println("逾期法务通知："+templet);
	}

	/**
	 * 逾期
	 * @param params
	 * @throws AnjboException
	 */
	private void beOverdue(Map<String, Object> params) throws AnjboException{
		int overdueDay = MapUtils.getIntValue(params, "overdueDay",0);
		int periods = MapUtils.getIntValue(params, "repaymentPeriods",0);
		params.put("borrowContent","尊敬的"+MapUtils.getString(params, "borrowerName")+"先生/女士：您在我司办理的房抵贷业务，已逾期"+Math.abs(overdueDay)+"天未还款，应还金额"+MapUtils.getString(params, "repayAmount")+"元，请尽快联系我司相关人员，谢谢。客户经理："+MapUtils.getString(params, "channelManagerName")+"，"+MapUtils.getString(params, "channelManagerPhone")+"。");
		params.put("channelManagerContent","客户逾期通知："+MapUtils.getString(params, "channelManagerName")+"同事您好，您名下客户："+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，在我司办理的房抵贷业务第"+periods+"期，已逾期"+Math.abs(overdueDay)+"天未还款，请及时处理。");
		params.put("loanContent","客户逾期通知："+MapUtils.getString(params, "channelManagerName")+"同事您好，客户："+MapUtils.getString(params, "borrowerName")+"，电话"+MapUtils.getString(params, "phoneNumber")+"，在我司办理的房抵贷业务第"+periods+"期，已逾期"+Math.abs(overdueDay)+"天未还款，请及时处理。");
		String amsCode = "hyt";
		sendBorrow(params,amsCode);
		sendChannelManager(params,amsCode);
		if(overdueDay == -2){
			//贷中
			sendLoan(params,amsCode);
		}else if(overdueDay == -5){
			//贷后
			sendLoanAfter(params,amsCode);
		}else if(overdueDay == -10){
			//贷后 + 业务总监
			sendLoanAfter(params,amsCode);
			sendChiefInspector(params,amsCode);
		}else if(overdueDay == -15 || overdueDay == -20){
			//贷后 + 业务总监 + 法务
			sendLoanAfter(params,amsCode);
			sendChiefInspector(params,amsCode);
			sendJustice(params,amsCode);
		}
	}
	
	
	/**
	 * 发给客户
	 * @param params
	 * @throws AnjboException
	 */
	private void sendBorrow(Map<String, Object> params,String amsCode) throws AnjboException{
		AmsUtil.smsSend2(MapUtils.getString(params, "phoneNumber"), ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "borrowContent"), Constants.SMSCOMEFROM);
	}
	
	/**
	 * 发给渠道经理
	 * @param params
	 * @throws AnjboException
	 */
	private void sendChannelManager(Map<String, Object> params,String amsCode) throws AnjboException{
		AmsUtil.smsSend2(MapUtils.getString(params, "channelManagerPhone"), ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "channelManagerContent"), Constants.SMSCOMEFROM);
	}
	
	/**
	 * 发给贷中管理人员
	 * @param params
	 * @throws AnjboException
	 */
	private void sendLoan(Map<String, Object> params,String amsCode) throws AnjboException{
		AmsUtil.smsSend2(ConfigUtil.getStringValue("LOAN_PHONE"), ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "loanContent"), Constants.SMSCOMEFROM);
	}
	
	/**
	 * 发给贷后管理人员
	 * @param params
	 * @throws AnjboException
	 */
	private void sendLoanAfter(Map<String, Object> params,String amsCode) throws AnjboException{
		AmsUtil.smsSend2(ConfigUtil.getStringValue("LOAN_AFTER_PHONE"), ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "loanContent"), Constants.SMSCOMEFROM);
	}
	
	/**
	 * 发给业务总监
	 * @param params
	 * @throws AnjboException
	 */
	private void sendChiefInspector(Map<String, Object> params,String amsCode) throws AnjboException{
		String phone = MapUtils.getString(params,"CHIEF_INSPECTOR_PHONE");
		if(StringUtil.isBlank(phone)){
			return;
		}
		AmsUtil.smsSend2(phone, ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "loanContent"), Constants.SMSCOMEFROM);
	}
	
	/**
	 * 发给法务
	 * @param params
	 * @throws AnjboException
	 */
	private void sendJustice(Map<String, Object> params,String amsCode) throws AnjboException{
		AmsUtil.smsSend2(ConfigUtil.getStringValue("JUSTICE_PHONE"), ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString()),amsCode, MapUtils.getString(params, "loanContent"), Constants.SMSCOMEFROM);
	}


}
