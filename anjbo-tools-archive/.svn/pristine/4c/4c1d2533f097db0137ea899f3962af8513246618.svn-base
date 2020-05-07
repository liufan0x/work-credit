package com.anjbo.common;
/**
 * 返回状态枚举类
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:14:01
 */
public enum RespStatusEnum {
	SUCCESS("SUCCESS","操作成功"),
	FAIL("FAIL","操作失败，请稍后再试"),
	THIRD_FAIL("FAIL","哎呀，服务器繁忙中！请稍后再试。"),
	PARAMETER_ERROR("PARAMETER_ERROR","参数异常"),
	FILE_ERROR("FILE_ERROR","未选择文件"),
	ACCOUNT_PWD_ERROR("ACCOUNT_PWD_ERROR","账号或密码错误"),
	ACCOUT_ROLE_ERROR("ACCOUT_ROLE_ERROR","选择正确的角色"),
	ACCOUNT_REGISTERED("ACCOUNT_REGISTERED","此手机号码已被占用"),
	ACCOUNT_UNREGISTER("ACCOUNT_UNREGISTER","账号未注册"),
	SMS_DAY_THREE("SMS_DAY_THREE","一天只能发送3次短信"),
	SMS_MONTH_FIVE("SMS_MONTH_FIVE","一个月只能发送5次短信"),
	SYSTEM_ERROR("SYSTEM_ERROR","系统内部错误，请重试!"),
	REQUEST_TIMEOUT("REQUEST_TIMEOUT","请求超时,请稍后重试"),
	REQUEST_TIMEOUT_THIRD("REQUEST_TIMEOUT_THIRD","访问国土局取号系统超时，请稍后重试"),
	REQUEST_FALL_THIRD("REQUEST_FALL_THIRD","访问国土局取号系统异常，请稍后重试"),
	VERIFYCODE_ERROR("VERIFYCODE_ERROR","验证码不正确"),
	USERTYPE_ERROR("USERTYPE_ERROR","未知的用户类型"),
	OLD_PWD_ERROR("OLD_PWD_ERROR","原密码不正确"),
	ACCOUNT_BINDED("ACCOUNT_BINDED","账号已绑定"),
	ACCOUNT_BINDFAIL("ACCOUNT_BINDFAIL","不能添加自己为联系人"),
	PARAMETER_FORMAT_ERROR("PARAMETER_FORMAT_ERROR","参数格式错误"),
	MOBILE_FORMAT_ERROR("MOBILE_FORMAT_ERROR","手机号码格式有误"),
	PWD_FORMAT_ERROR("PWD_FORMAT_ERROR","密码不符合要求"), //密码6到20位只能包含字母,数字和下划线
	REG_ROLE_ERROR("REG_ROLE_ERROR","注册用户类型有误"),
	HEADIMG_UPLOAD_ERROR("HEADIMG_UPLOAD_ERROR","头像上传失败,请重试"),
	IMG_UPLOAD_ERROR("IMG_UPLOAD_ERROR","图片上传失败,请重试"),
	CREDIT_PERSONIMG_EMPTY("CREDIT_PERSONIMG_EMPTY","左手身份证,右手征信授权书照片不能为空"),
	CREDIT_CREDITIMG_EMPTY("CREDIT_CREDITIMG_EMPTY","授权书照片不能为空"),
	SESSION_INVALID("SESSION_INVALID","会话过期,请重新登录"),
	ENQUIRY_RATE_LIMIT_OVER("ENQUIRY_RATE_LIMIT_OVER","一个月只能提交%s次询价"),
	ENQUIRY_MAXCOUNT_TODAY("ENQUIRY_MAXCOUNT_TODAY","每天最多询价%s次哦！"),
	PROJECT_CODE_NAME_EMPTY_ERROR("PROJECT_CODE_NAME_EMPTY_ERROR","新项目上传，项目编号/名称不能为空"),
	PROJECT_CODE_FORMAT_ERROR("PROJECT_CODE_FORMAT_ERROR","项目编号非法，只能包含数字、字母、_和-"),
	FILE_TYPE_ERROR("FILE_TYPE_ERROR","文件格式不正确"),
	HAD_VERSION_ERROR("HAD_VERSION_ERROR","当前版本已上传"),
	VERSION_HAD_NEW("VERSION_HAD_NEW","已是最新版本"),
	LOW_VERSION_ERROR("LOW_VERSION_ERROR","上传的版本不能比已上传的低"),
	ENQUIRY_SAME_QUERY("ENQUIRY_SAME_QUERY","相同房号一天查询不能超过%s次"),
	COUPONS_USE_UP("COUPONS_USE_UP","优惠券已用完"),
	COUPONS_NUMBER_ERROR("COUPONS_NUMBER_ERROR","优惠券数量不够，只能选择一种询价类型"),
	VALIDATE_ERROR("VALIDATE_ERROR","数据校验失败"),
	REPEAT_PAY("REPEAT_PAY","请不要重复支付"),
	GOOD_NOT_FOUND("GOOD_NOT_FOUND","未查询到商品%s相关信息"),
	EVALUATED("EVALUATED","已评估过啦"),
	SIGNERROR("SIGNERROR","签名失败"),
	CREDIT_REPLAY_STATUS_ERROR("CREDIT_REPLAY_STATUS_ERROR","征询回复状态值设置错误"),
	COUPONS_RATE_ERROR("COUPONS_RATE_ERROR","您应用优惠码的次数已达到上限"),
	COUPONS_UNEW_ERROR("COUPONS_UNEW_ERROR","注册赠送优惠码仅限新用户"),
	COUPONS_INVALID("COUPONS_INVALID","优惠码无效"),
	COUPONS_USE_TYPE_ERROR("COUPONS_USE_TYPE_ERROR","优惠码使用类型错误"),
	COUPONS_USED("COUPONS_USED","优惠码已使用"),
	COUPONS_OVERDUE("COUPONS_OVERDUE","优惠码已过期"),
	COUPONS_NOT_USE("COUPONS_NOT_USE","自己无法使用此优惠码"),
	COUPONS_50_RECEIVED("COUPONS_50_RECEIVED","您已领取50元现金券"),
	BOOKING_EXIT("BOOKING_EXIT","此申办已存在"),
	BOOKING_CODE_EXIT("BOOKING_CODE_EXIT","您已预约"),
	REQDATAERROR("REQDATAERROR","请求数据异常"),
	SAME_ROOM_NUMBER("SAME_ROOM_NUMBER","相同房号请在记录详情再次查询"),
	SYSTEM_UPDATE("SYSTEM_UPDATE","房产信息系统升级,稍后短信通知"),
	SYSTEM_NO_RESULT("SYSTEM_NO_RESULT","无询价结果"),
	ERRORONE("ERRORONE","数据跑火星去了，请稍后再试。"),
	ERRORTWO("ERRORTWO","系统开小差了，请稍后再试。"),
	ERRORTHREE("ERRORTHREE","哎呀，没有识别成功，建议更换上传的照片。"),
	ERRORFOUR("ERRORFOUR","数据读取失败了，请稍后再试。"),
	ERRORFIVE("ERRORFIVE","记录被人偷走了，我们正在努力找回，请稍后再试。"),
	ERRORSIX("ERRORSIX","天啦，服务器被挤爆了，请稍后再试 。"),
	ERRORSEVEN("ERRORSEVEN","哇哦，计算器罢工了，请稍后再试。");
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private RespStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
