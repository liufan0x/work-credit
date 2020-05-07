package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Configuration
public class ThirdApiConstants {
	/** =======================电子签章start========================== */

	// 登录账号
	public static String SIGNATURE_ACCOUNT;

	// 登录密码
	public static String SIGNATURE_PASSWORD;

	// 签署密码
	public static String SIGNATURE_SIGN_PASSWORD;

	// e签宝url
	public static String SIGNATURE_URL;

	// 项目id
	public static String SIGNATURE_PROJECTID;

	// 项目密钥
	public static String SIGNATURE_PROJECT_SECRET;

	@Value("${signature.account}")
	public void setSignatureAccount(String signatureAccount) {
		SIGNATURE_ACCOUNT = signatureAccount;
	}

	@Value("${signature.password}")
	public void setSignaturePassword(String signaturePassword) {
		SIGNATURE_PASSWORD = signaturePassword;
	}

	@Value("${signature.sign.password}")
	public void setSignatureSignPassword(String signatureSignPassword) {
		SIGNATURE_SIGN_PASSWORD = signatureSignPassword;
	}

	@Value("${signature.url}")
	public void setSignatureUrl(String signatureUrl) {
		SIGNATURE_URL = signatureUrl;
	}

	@Value("${signature.projectid}")
	public void setSignatureProjectid(String signatureProjectid) {
		SIGNATURE_PROJECTID = signatureProjectid;
	}

	@Value("${signature.project.secret}")
	public void setSignatureProjectSecret(String signatureProjectSecret) {
		SIGNATURE_PROJECT_SECRET = signatureProjectSecret;
	}

	/** =======================电子签章end========================== */

	/** =======================友盟start========================== */
	public static String UMENG_IOS_APPKEY;
	public static String UMENG_IOS_SECRET;

	public static String UMENG_ANDROID_APPKEY;
	public static String UMENG_ANDROID_SECRET;

	@Value("${umeng.ios.appkey}")
	public void setUmengIosAppkey(String umengIosAppkey) {
		UMENG_IOS_APPKEY = umengIosAppkey;
	}

	@Value("${umeng.ios.secret}")
	public void setUmengIosSecret(String umengIosSecret) {
		UMENG_IOS_SECRET = umengIosSecret;
	}

	@Value("${umeng.android.appkey}")
	public void setUmengAndroidAppkey(String umengAndroidAppkey) {
		UMENG_ANDROID_APPKEY = umengAndroidAppkey;
	}

	@Value("${umeng.android.secret}")
	public void setUmengAndroidSecret(String umengAndroidSecret) {
		UMENG_ANDROID_SECRET = umengAndroidSecret;
	}

	/** =======================友盟end========================== */

	/** =======================百度start========================== */
	public static String BAIDU_FACE_URL;
	public static String BAIDU_QUERYRISKLIST_SP_NO;
	public static String BAIDU_QUERYRISKLIST_KEY;
	public static String BAIDU_QUERYRISKLIST_URL;

	@Value("${baidu.face.url}")
	public void setBaiduFaceUrl(String baiduFaceUrl) {
		BAIDU_FACE_URL = baiduFaceUrl;
	}

	@Value("${baidu.queryRiskList.sp_no}")
	public void setBaiduQueryrisklistSpNo(String baiduQueryrisklistSpNo) {
		BAIDU_QUERYRISKLIST_SP_NO = baiduQueryrisklistSpNo;
	}

	@Value("${baidu.queryRiskList.key}")
	public void setBaiduQueryrisklistKey(String baiduQueryrisklistKey) {
		BAIDU_QUERYRISKLIST_KEY = baiduQueryrisklistKey;
	}

	@Value("${baidu.queryRiskList.url}")
	public void setBaiduQueryrisklistUrl(String baiduQueryrisklistUrl) {
		BAIDU_QUERYRISKLIST_URL = baiduQueryrisklistUrl;
	}

	/** =======================百度end============================ */

	/** =======================建行start========================== */

	// 快鸽对外接口加密密钥
	public static String CCB_SECRET_KEY;

	// 对接公司编号
	public static String CCB_COMNO;

	// 机构号
	public static String CCB_BANKNO;

	// 渠道号
	public static String CCB_CHANNELNO;

	// 接口地址
	public static String CCB_WS_URL;

	public static String CCB_BUS_INFO_DIR_BASE;
	public static String CCB_BUS_INFO_DIR;
	public static String CCB_BUS_INFO_ZIPDIR;
	public static String CCB_RSYNC;

	public static String getCcbSecretKey() {
		return CCB_SECRET_KEY;
	}

	@Value("${CCB_SECRET_KEY}")
	public void setCcbSecretKey(String ccbSecretKey) {
		CCB_SECRET_KEY = ccbSecretKey;
	}

	public static String getCcbComno() {
		return CCB_COMNO;
	}

	@Value("${CCB_COMNO}")
	public void setCcbComno(String ccbComno) {
		CCB_COMNO = ccbComno;
	}

	public static String getCcbBankno() {
		return CCB_BANKNO;
	}

	@Value("${CCB_BANKNO}")
	public void setCcbBankno(String ccbBankno) {
		CCB_BANKNO = ccbBankno;
	}

	public static String getCcbChannelno() {
		return CCB_CHANNELNO;
	}

	@Value("${CCB_CHANNELNO}")
	public void setCcbChannelno(String ccbChannelno) {
		CCB_CHANNELNO = ccbChannelno;
	}

	public static String getCcbWsUrl() {
		return CCB_WS_URL;
	}

	@Value("${CCB_WS_URL}")
	public void setCcbWsUrl(String ccbWsUrl) {
		CCB_WS_URL = ccbWsUrl;
	}

	public static String getCcbBusInfoDirBase() {
		return CCB_BUS_INFO_DIR_BASE;
	}

	@Value("${CCB_BUS_INFO_DIR_BASE}")
	public void setCcbBusInfoDirBase(String ccbBusInfoDirBase) {
		CCB_BUS_INFO_DIR_BASE = ccbBusInfoDirBase;
	}

	public static String getCcbBusInfoDir() {
		return CCB_BUS_INFO_DIR;
	}

	@Value("${CCB_BUS_INFO_DIR}")
	public void setCcbBusInfoDir(String ccbBusInfoDir) {
		CCB_BUS_INFO_DIR = ccbBusInfoDir;
	}

	public static String getCcbBusInfoZipdir() {
		return CCB_BUS_INFO_ZIPDIR;
	}

	@Value("${CCB_BUS_INFO_ZIPDIR}")
	public void setCcbBusInfoZipdir(String ccbBusInfoZipdir) {
		CCB_BUS_INFO_ZIPDIR = ccbBusInfoZipdir;
	}

	public static String getCcbRsync() {
		return CCB_RSYNC;
	}

	@Value("${CCB_RSYNC}")
	public void setCcbRsync(String ccbRsync) {
		CCB_RSYNC = ccbRsync;
	}

	/** =======================建行end============================ */
	

	/** =======================钉钉start========================== */
	// 钉钉URL
	public static String DINGTALK_WS_URL;

	public static String getDingtalkWsUrl() {
		return DINGTALK_WS_URL;
	}

	@Value("${DINGTALK_WS_URL}")
	public void setDingtalkWsUrl(String dingtalkWsUrl) {
		DINGTALK_WS_URL = dingtalkWsUrl;
	}

	/** =======================钉钉end============================ */

	/** =======================云南信托start======================= */

	// 私钥名称
	public static String YNTRUST_PRIVATE_KEY;

	// 公钥名称
	public static String YNTRUST_PUBLIC_KEY;

	// 商户号
	public static String YNTRUST_MERID;

	// 商户密钥
	public static String YNTRUST_SECRETKEY;

	// 私钥密码
	public static String YNTRUST_PRIVATECERT_PASSWORD;

	// 云南信托URL
	public static String YNTRUST_URL;

	// 云南信托产品编号一期
	public static String YNTRUST_PRODUCT_CODE_ONE;

	// 云南信托专户账号一期
	public static String YNTRUST_ACCOUNT_NO_ONE;

	// 云南信托影像资料一期
	public static String YNTRUST_IMG_ONE;

	// 云南信托影像资料一期名称
	public static String YNTRUST_IMG_NAME_ONE;

	// 云南信托产品编号二期
	public static String YNTRUST_PRODUCT_CODE_TWO;

	// 云南信托专户账号二期
	public static String YNTRUST_ACCOUNT_NO_TWO;

	// 云南信托影像资料二期
	public static String YNTRUST_IMG_TWO;

	// 云南信托影像资料二期名称
	public static String YNTRUST_IMG_NAME_TWO;

	// 云南信托上传文件
	public static String YNTRUST_FILE_URL;

	@Value("${base.yntrust.private.key}")
	public void setYntrustPrivateKey(String yntrustPrivateKey) {
		YNTRUST_PRIVATE_KEY = yntrustPrivateKey;
	}

	@Value("${base.yntrust.public.key}")
	public void setYntrustPublicKey(String yntrustPublicKey) {
		YNTRUST_PUBLIC_KEY = yntrustPublicKey;
	}

	@Value("${base.yntrust.mer.id}")
	public void setYntrustMerid(String yntrustMerid) {
		YNTRUST_MERID = yntrustMerid;
	}

	@Value("${base.yntrust.secret.key}")
	public void setYntrustSecretkey(String yntrustSecretkey) {
		YNTRUST_SECRETKEY = yntrustSecretkey;
	}

	@Value("${base.yntrust.privateCert.password}")
	public void setYntrustPrivatecertPassword(String yntrustPrivatecertPassword) {
		YNTRUST_PRIVATECERT_PASSWORD = yntrustPrivatecertPassword;
	}

	@Value("${link.yntrust.url}")
	public void setYntrustUrl(String yntrustUrl) {
		YNTRUST_URL = yntrustUrl;
	}

	@Value("${base.yntrust.product.code.one}")
	public void setYNTRUST_PRODUCT_CODE_ONE(String yNTRUST_PRODUCT_CODE_ONE) {
		YNTRUST_PRODUCT_CODE_ONE = yNTRUST_PRODUCT_CODE_ONE;
	}

	@Value("${base.yntrust.account.no.one}")
	public void setYNTRUST_ACCOUNT_NO_ONE(String yNTRUST_ACCOUNT_NO_ONE) {
		YNTRUST_ACCOUNT_NO_ONE = yNTRUST_ACCOUNT_NO_ONE;
	}

	@Value("${base.yntrust.product.code.two}")
	public void setYNTRUST_PRODUCT_CODE_TWO(String yNTRUST_PRODUCT_CODE_TWO) {
		YNTRUST_PRODUCT_CODE_TWO = yNTRUST_PRODUCT_CODE_TWO;
	}

	@Value("${base.yntrust.account.no.two}")
	public void setYNTRUST_ACCOUNT_NO_TWO(String yNTRUST_ACCOUNT_NO_TWO) {
		YNTRUST_ACCOUNT_NO_TWO = yNTRUST_ACCOUNT_NO_TWO;
	}

	@Value("${link.yntrust.file.url}")
	public void setYntrustFileUrl(String yntrustFileUrl) {
		YNTRUST_FILE_URL = yntrustFileUrl;
	}

	@Value("${link.yntrust.img.one}")
	public void setYntrust_img_one(String yntrust_img_one) {
		YNTRUST_IMG_ONE = yntrust_img_one;
	}

	@Value("${link.yntrust.img.two}")
	public void setYntrust_img_two(String yntrust_img_two) {
		YNTRUST_IMG_TWO = yntrust_img_two;
	}

	@Value("${link.yntrust.img.name.one}")
	public void setYntrust_img_name_one(String yntrust_img_name_one) {
		YNTRUST_IMG_NAME_ONE = yntrust_img_name_one;
	}

	@Value("${link.yntrust.img.name.two}")
	public void setYntrust_img_name_two(String yntrust_img_name_two) {
		YNTRUST_IMG_NAME_TWO = yntrust_img_name_two;
	}

	/** =======================云南end============================ */

	/** =======================华融start========================== */
	// 华融域名
	public static String HR_PARENT_URL;

	// 申请数据发送URL
	public static String HR_APPLY_URL;
	// 申请数据发送超时时间
	public static String HR_APPLY_URL_TIMEOUT;

	// 附件URL
	public static String HR_FILE_APPLY_URL;
	// 附件超时时间
	public static String HR_FILE_APPLY_URL_TIMEOUT;

	// 放款URL
	public static String HR_LEND_URL;
	// 放款超时时间
	public static String HR_LEND_URL_TIMEOUT;

	// 应还款URL
	public static String HR_REPAYMENT_URL;
	// 应还款超时时间
	public static String HR_REPAYMENT_URL_TIMEOUT;

	// 回款URL
	public static String HR_PAY_MENT_PLAN_URL;
	// 超时时间
	public static String HR_PAY_MENT_PLAN_URL_TIMEOUT;

	// 状态查询URL
	public static String HR_QUERY_LOAN_STATUS_URL;
	// 状态查询超时时间
	public static String HR_QUERY_LOAN_STATUS_URL_TIMEOUT;

	// 公钥
	public static String PUBLICKEY;

	// 私钥
	public static String HRGD_PRIVATEKEY;

	// 公钥1
	public static String GD_PUBLICKEY1;

	// 公钥2
	public static String GD_PUBLICKEY2;

	public String getPUBLICKEY() {
		return PUBLICKEY;
	}

	@Value("${base.hr.public.key}")
	public void setPUBLICKEY(String pUBLICKEY) {
		PUBLICKEY = pUBLICKEY;
	}

	public String getHRGD_PRIVATEKEY() {
		return HRGD_PRIVATEKEY;
	}

	@Value("${base.hr.private.key}")
	public void setHRGD_PRIVATEKEY(String hRGD_PRIVATEKEY) {
		HRGD_PRIVATEKEY = hRGD_PRIVATEKEY;
	}

	public String getGD_PUBLICKEY1() {
		return GD_PUBLICKEY1;
	}

	@Value("${gdPublicKey1}")
	public void setGdPublicKey1(String gdPublicKey1) {
		GD_PUBLICKEY1 = gdPublicKey1;
	}

	public String getGD_PUBLICKEY2() {
		return GD_PUBLICKEY2;
	}

	@Value("${gdPublicKey2}")
	public void setGdPublicKey2(String gdPublicKey2) {
		GD_PUBLICKEY2 = gdPublicKey2;
	}

	public static String getHrParentUrl() {
		return HR_PARENT_URL;
	}

	@Value("${HR_PARENT_URL}")
	public void setHrParentUrl(String hrParentUrl) {
		HR_PARENT_URL = hrParentUrl;
	}

	public static String getHrApplyUrl() {
		return HR_APPLY_URL;
	}

	@Value("${HR_APPLY_URL}")
	public void setHrApplyUrl(String hrApplyUrl) {
		HR_APPLY_URL = hrApplyUrl;
	}

	public static String getHrApplyUrlTimeout() {
		return HR_APPLY_URL_TIMEOUT;
	}

	@Value("${HR_APPLY_URL_TIMEOUT}")
	public void setHrApplyUrlTimeout(String hrApplyUrlTimeout) {
		HR_APPLY_URL_TIMEOUT = hrApplyUrlTimeout;
	}

	public static String getHrFileApplyUrl() {
		return HR_FILE_APPLY_URL;
	}

	@Value("${HR_FILE_APPLY_URL}")
	public void setHrFileApplyUrl(String hrFileApplyUrl) {
		HR_FILE_APPLY_URL = hrFileApplyUrl;
	}

	public static String getHrFileApplyUrlTimeout() {
		return HR_FILE_APPLY_URL_TIMEOUT;
	}

	@Value("${HR_FILE_APPLY_URL_TIMEOUT}")
	public void setHrFileApplyUrlTimeout(String hrFileApplyUrlTimeout) {
		HR_FILE_APPLY_URL_TIMEOUT = hrFileApplyUrlTimeout;
	}

	public static String getHrLendUrl() {
		return HR_LEND_URL;
	}

	@Value("${HR_LEND_URL}")
	public void setHrLendUrl(String hrLendUrl) {
		HR_LEND_URL = hrLendUrl;
	}

	public static String getHrLendUrlTimeout() {
		return HR_LEND_URL_TIMEOUT;
	}

	@Value("${HR_LEND_URL_TIMEOUT}")
	public void setHrLendUrlTimeout(String hrLendUrlTimeout) {
		HR_LEND_URL_TIMEOUT = hrLendUrlTimeout;
	}

	public static String getHrRepaymentUrl() {
		return HR_REPAYMENT_URL;
	}

	@Value("${HR_REPAYMENT_URL}")
	public void setHrRepaymentUrl(String hrRepaymentUrl) {
		HR_REPAYMENT_URL = hrRepaymentUrl;
	}

	public static String getHrRepaymentUrlTimeout() {
		return HR_REPAYMENT_URL_TIMEOUT;
	}

	@Value("${HR_REPAYMENT_URL_TIMEOUT}")
	public void setHrRepaymentUrlTimeout(String hrRepaymentUrlTimeout) {
		HR_REPAYMENT_URL_TIMEOUT = hrRepaymentUrlTimeout;
	}

	public static String getHrPayMentPlanUrl() {
		return HR_PAY_MENT_PLAN_URL;
	}

	@Value("${HR_PAY_MENT_PLAN_URL}")
	public void setHrPayMentPlanUrl(String hrPayMentPlanUrl) {
		HR_PAY_MENT_PLAN_URL = hrPayMentPlanUrl;
	}

	public static String getHrPayMentPlanUrlTimeout() {
		return HR_PAY_MENT_PLAN_URL_TIMEOUT;
	}

	@Value("${HR_PAY_MENT_PLAN_URL_TIMEOUT}")
	public void setHrPayMentPlanUrlTimeout(String hrPayMentPlanUrlTimeout) {
		HR_PAY_MENT_PLAN_URL_TIMEOUT = hrPayMentPlanUrlTimeout;
	}

	public static String getHrQueryLoanStatusUrl() {
		return HR_QUERY_LOAN_STATUS_URL;
	}

	@Value("${HR_QUERY_LOAN_STATUS_URL}")
	public void setHrQueryLoanStatusUrl(String hrQueryLoanStatusUrl) {
		HR_QUERY_LOAN_STATUS_URL = hrQueryLoanStatusUrl;
	}

	public static String getHrQueryLoanStatusUrlTimeout() {
		return HR_QUERY_LOAN_STATUS_URL_TIMEOUT;
	}

	@Value("${HR_QUERY_LOAN_STATUS_URL_TIMEOUT}")
	public void setHrQueryLoanStatusUrlTimeout(String hrQueryLoanStatusUrlTimeout) {
		HR_QUERY_LOAN_STATUS_URL_TIMEOUT = hrQueryLoanStatusUrlTimeout;
	}

	/** =======================华融end============================ */
	
	/** =======================平台start========================== */
	

	// 公钥
	public static String PUBLIC_KEYS;

	// 私钥
	public static String PRIVATE_KEYS;

	public  String getPUBLIC_KEYS() {
		return PUBLIC_KEYS;
	}
	@Value("${base.pt.public.key}")
	public  void setPUBLIC_KEYS(String pUBLIC_KEYS) {
		PUBLIC_KEYS = pUBLIC_KEYS;
	}

	public  String getPRIVATE_KEYS() {
		return PRIVATE_KEYS;
	}
	@Value("${base.pt.private.key}")
	public  void setPRIVATE_KEYS(String pRIVATE_KEYS) {
		PRIVATE_KEYS = pRIVATE_KEYS;
	}

	/** =======================平台end============================ */
}
