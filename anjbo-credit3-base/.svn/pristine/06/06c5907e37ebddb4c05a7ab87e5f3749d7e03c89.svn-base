package com.anjbo.common;




/**
 * 项目中定义的枚举
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:12:05
 */
public class Enums {
	
	/**
	 * 文件系统服务接口
	 * @ClassName: FS_SERVICES 
	 * @author limh limh@zxsf360.com
	 * @date 2014-12-9 下午02:19:47
	 */
	public enum FS_SERVICES {
		base64Decode,
		appPupload,
		abbreviations
	}
	/**
	 * 特殊权限
	 * @author lic
	 */
	public enum AuthNameEnum{
		LOOK_ORDER("查看订单权限","2042","2043"),
		LOOK_PICTURE("查看图片权限","2044","2045"),
		LOOK_BUSINFO("APP-订单节点全部资料","2110","2111");
		
		public static String getAuthIdByAuthName(String authName) {
			for (AuthNameEnum authNameEnum : AuthNameEnum.values()) {
				if(authNameEnum.getAuthName().equals(authName)){
					return authNameEnum.getAuthIdA() + authNameEnum.getAuthIdB();
				}
			}
			return "";
		}
		
		private AuthNameEnum(String authName,String authIdA,String authIdB) {
			this.authName = authName;
			this.authIdA = authIdA;
			this.authIdB = authIdB;
		}
		
		private String authName;
		private String authIdA;
		private String authIdB;
		public String getAuthName() {
			return authName;
		}

		public void setAuthName(String authName) {
			this.authName = authName;
		}

		public String getAuthIdA() {
			return authIdA;
		}

		public void setAuthIdA(String authIdA) {
			this.authIdA = authIdA;
		}

		public String getAuthIdB() {
			return authIdB;
		}

		public void setAuthIdB(String authIdB) {
			this.authIdB = authIdB;
		}
		
	}

	/**
	 * 交易类与非交易类枚举
	*
	 */
	public enum PropertyStatusEnum{
		L1("抵押查封"),
		L2("抵押"),
		L3("查封"),
		L4("有效房产详细资料"),
		L5("该权利人没有房产"),
		L6("没有找到匹配的房产记录"),
		NULL("");
		
		public static Enum<?> getEnumByName(String name) {
			Enum<?> e = PropertyStatusEnum.NULL;
			for (PropertyStatusEnum propertyStatusEnum : PropertyStatusEnum.values()) {
				if (propertyStatusEnum.getName().equals(name)) {
					e = propertyStatusEnum;
				}
			}
			return e;
		}
		private PropertyStatusEnum(String name) {
			this.name = name;
		}
		private String name;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name;
		}

	}
	public enum FundEnums{
		HABX("华安保险","105");
		private FundEnums(String name,String fundCode){
			this.name = name;
			this.fundCode=fundCode;
		}
		private String name;
		private String fundCode;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFundCode() {
			return fundCode;
		}

		public void setFundCode(String fundCode) {
			this.fundCode = fundCode;
		}
		
	}
	
	public enum AgencyEnum {
		KUAIGE("快鸽按揭",1);
		private AgencyEnum(String name,int agencyId){
			this.name=name;
			this.agencyId=agencyId;
		}
		private String name;
		private int agencyId;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAgencyId() {
			return agencyId;
		}

		public void setAgencyId(int agencyId) {
			this.agencyId = agencyId;
		}
		
	}
	
	/**
	 * 角色枚举
	 * @author Mark
	 *
	 */
	public enum RoleEnum {
		ACCEPT("受理员"),
		ACCEPT_MANAGER("受理经理"),
		MAJORDOMO("总监"),
		RISK_MANAGEMENT("风控人员"),
		RISK_MANAGER("风控经理"),
		RISK_OFFICER("首席风险官"),
		ACCOUNTANT("会计"),
		TELLER("出纳"),
		FUND_MEMBER("资金分配员"),
		FC_MEMBER("赎楼员"),
		ELEMENT_ADMINISTRATOR("要件管理员"),
		CHANNEL_MANAGER("渠道经理"),
		CHANNEL_CHIEF_MANAGER("渠道总监"),
		CEO("CEO"),
		LANDBUREAU_UID("国土局驻点"),
		FACESIGNER("面签员"),
		FIRSTAUDIT("风控初审员"),
		NOTARIAL("公证员"),
		RECORDORDER("录单员"),
		EXPANDMANAGER("拓展经理"),
		INVESTIGATIONMANAGER("尽调经理"),
		NULL("");
		
		public static Enum<?> getEnumByName(String name) {
			Enum<?> e = RoleEnum.NULL;
			for (RoleEnum roleEnum : RoleEnum.values()) {
				if (roleEnum.getName().equals(name)) {
					e = roleEnum;
				}
			}
			return e;
		}
		private RoleEnum(String name) {
			this.name = name;
		}
		private String name;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name;
		}
	}
	
	/**
	 * 风控项目枚举
	 * @author liuf
	 *
	 */
	public enum WindControlEnum {
		/**借款金额(万）*/
		LOAN_AMOUNT("借款金额(万)"),
		/**借款成数（房产总值-征信总负债）*/
		NUMBER_OF_BORROWINGS("借款成数（房产总值-征信总负债）"),
		/**2年内金额2000以上有逾期次数*/
		MONEY_LATE_TIMES("2年内金额2000以上有逾期次数"),
		/**征信报告逾期次数*/
		CREDIT_LATE_TIMES("征信报告逾期次数"),
		/**信用卡半年平均透支余额*/
		CARD_OVERDRAFT_AMOUNT("信用卡半年平均透支余额"),
		/**近半年征信查询次数（非贷后管理）*/
		CREDIT_QUERY_TIMES("近半年征信查询次数（非贷后管理）"),
		/**原贷款是否银行）*/
		ORIGINAL_LOAN_BANK("原贷款是否银行"),
		/**新贷款是否银行*/
		NEW_LOAN_BANK("新贷款是否银行"),
		/**是否有诉讼*/
		IS_LITIGATION("是否有诉讼"),
		/**产权抵押情况*/
		PROPERTY_MORTGAGE("产权抵押情况"),
		/**个人客户评级*/
		CUSTOMER_RATING("个人客户评级"),
		/**是否公司产权*/
		IS_COMPANY_PROPERTY_RIGHT("是否公司产权"),
		NULL("");
		public static Enum<?> getEnumByName(String name) {
			Enum<?> e = WindControlEnum.NULL;
			for (WindControlEnum windControlEnum : WindControlEnum.values()) {
				if (windControlEnum.getName().equals(name)) {
					e = windControlEnum;
				}
			}
			return e;
		}
		private WindControlEnum(String name) {
			this.name = name;
		}
		private String name;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name;
		}
	}
	
	/**
	 * 资方业务模型枚举
	 * @author liuf
	 *
	 */
	public enum FundModelEnum {
		/**城市*/
		CITY("城市"),
		/**业务类型*/
		PRODUCT_NAME("业务类型"),
		/**借款金额*/
		LOAN_AMOUNT("借款金额"),
		/**原贷款是否银行）*/
		ORIGINAL_LOAN_BANK("原贷款是否银行"),
		/**新贷款是否银行*/
		NEW_LOAN_BANK("新贷款是否银行"),
		/**客户年龄*/
		CUSTOMER_AGE("客户年龄"),
		/**客户类型*/
		CUSTOMER_TYPE("客户类型"),
		/**近半年征信查询次数*/
		LATELY_HALF_YEAR_SELECT_NUMBER("近半年征信查询次数"),
		/**房抵贷 - 抵押类型*/
		MORTGAGE_TYPE("房抵贷 - 抵押类型"),
		/**房抵贷 - 一抵是否银行*/
		MORTGAGE_ISBANK("房抵贷 - 一抵是否银行"),
		NULL("");
		public static Enum<?> getEnumByName(String name) {
			Enum<?> e = WindControlEnum.NULL;
			for (WindControlEnum windControlEnum : WindControlEnum.values()) {
				if (windControlEnum.getName().equals(name)) {
					e = windControlEnum;
				}
			}
			return e;
		}
		private FundModelEnum(String name) {
			this.name = name;
		}
		private String name;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name;
		}
	}
	
	public enum RISKLIST_ENUMS{
		C06BT020("C06BT020","法院失信"),
		C06BT021("C06BT021","工商偷税漏税"),
		C06BT022("C06BT022","股权冻结"),
		C06BT023("C06BT023","无照经营"),
		C06BT024("C06BT024","法院被执行人"),
		C06BT025("C06BT025","行政处罚");
		public static String getNameByCode(String code) {
			String name = "";
			for (RISKLIST_ENUMS roleEnum : RISKLIST_ENUMS.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
				}
			}
			return name;
		}
		private RISKLIST_ENUMS(String code,String name){
			this.code = code;
			this.name = name;
		}
		private String code;
		private String name;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	/**
	 * 建行流水
	 * 
	 * @author chenzm
	 * @date 2017-08-28 下午17:48:43
	 */	
	public enum CM_FLOW_PROCESS{
		ASSESS("assess","提交评估"),
		ASSESSFAIL("assessFail","评估失败"),
		ASSESSSUCCESS("assessSuccess","评估已完成"),
		ROUNDTURN("roundTurn","买卖双方信息"),
		SUBMORTGAGE("subMortgage","提交申请按揭"),
		MANAGERAUDIT("managerAudit","客户经理审核"),
		STORESRESERVE("storesReserve","审批前材料准备"),
		AUDIT("audit","待审批"),
		AUDITFAIL("auditFail","审批未通过"),
		AUDITSUCCESS("auditSuccess","审批已通过"),
		TRANSFER("transfer","房产过户和抵押"), 
		RESERVEMORTGAGE("reserveMortgage","预约抵押登记"),
		FORENSICSMORTGAGE("forensicsMortgage","取证抵押"),
		LOAN("loan","待放款"),
		LOANFAIL("loanFail","放款失败"),
		LOANSUCCESS("loanSuccess","贷款已发放"),
		WANJIE("wanjie","完结"),
		CLOSEORDER("closeOrder","关闭订单");
		public static String getNameByCode(String code) {
			String name = "";
			for (RISKLIST_ENUMS roleEnum : RISKLIST_ENUMS.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
				}
			}
			return name;
		}
		private CM_FLOW_PROCESS(String code,String name){
			this.code = code;
			this.name = name;
		}
		private String code;
		private String name;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	/**
	 * 建行流水匹配订单流程
	 * @author yis
	 *
	 */
	public enum CM_FLOW_PROCESS_CONTRAST{
		MANAGERAUDIT("managerAudit","0301"),  //客户经理审核
		STORESRESERVE("storesReserve","0302"), //-审批前材料准备
		AUDIT("audit","0303"),   //正在审批
		AUDITFAIL("auditFail","审批未通过"),
		AUDITSUCCESS("auditSuccess","0304"),  //审批已通过
		TRANSFER("transfer","0305"),  //房产过户和抵押
		RESERVEMORTGAGE("reserveMortgage","预约抵押"), //预约抵押
		FORENSICSMORTGAGE("forensicsMortgage","取证抵押"), //取证抵押
		LOAN("loan","放款"),
		LOANSUCCESS("loanSuccess","0399"),  //贷款已发放
		LOANFAIL("loanFail","03ZZ");  //贷款终止
		public static String getNameByCode(String code) {
			String name = "";
			for (CM_FLOW_PROCESS_CONTRAST roleEnum : CM_FLOW_PROCESS_CONTRAST.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
				}
			}
			return name;
		}
		private CM_FLOW_PROCESS_CONTRAST(String code,String name){
			this.code = code;
			this.name = name;
		}
		private String code;
		private String name;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 建行-表单标签
	 * @author yis
	 *
	 */
	public enum CM_CONFIG_PAGE_TAB{
		TBL_CM_ASSESS("tbl_cm_assess","评估对象"),  
		TBL_CM_ASSESSSUCCESS("tbl_cm_assessSuccess","评估成功"),
		TBL_CM_ASSESSFAIL("tbl_cm_assessFail","评估失败"),  
		TBL_CM_ROUNDTURN("tbl_cm_roundTurn","买卖双方"),
		TBL_CM_CUSTOMER("tbl_cm_customer","客户信息"),  
		TBL_CM_LOAN("tbl_cm_loan","贷款信息"),
		TBL_CM_MANAGERAUDIT ("tbl_cm_managerAudit","客户经理审核"),
		TBL_CM_STORESRESERVE("tbl_cm_storesReserve","审批前材料准备"),
		TBL_CM_AUDITFAIL("tbl_cm_auditFail","审批失败"),
		TBL_CM_AUDITSUCCESS("tbl_cm_auditSuccess","审批已通过"),  
		TBL_CM_TRANSFER("tbl_cm_transfer","房产过户和抵押"),
		TBL_CM_RESERVEMORTGAGE("tbl_cm_reserveMortgage","预约抵押"),
		TBL_CM_FORENSICSMORTGAGE("tbl_cm_forensicsMortgage","取证抵押"),  
		TBL_CM_LOANFAIL("tbl_cm_loanFail","放款失败"), 
		TBL_CM_LOANSUCCESS("tbl_cm_loanSuccess","贷款已发放"),
		TBL_CM_CLOSEORDER("tbl_cm_closeOrder","关闭订单");
		public static String getNameByCode(String code) {
			String name = "";
			for (CM_CONFIG_PAGE_TAB roleEnum : CM_CONFIG_PAGE_TAB.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
				}
			}
			return name;
		}
		private CM_CONFIG_PAGE_TAB(String code,String name){
			this.code = code;
			this.name = name;
		}
		private String code;
		private String name;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 评估状态
	 * 
	 * @author limh limh@anjbo.com
	 * @date 2016-12-29 下午09:48:43
	 */
	public enum AssessStatusEnum {
		ASSESSING(1, "评估中"), 
		ASSESSSUCCESS(2, "评估成功"), 
		ASSESSFAIL(3, "评估失败");

		private int code;
		private String name;

		private AssessStatusEnum(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String getNameByCode(int code) {
			String name = "";
			for (AssessStatusEnum roleEnum : AssessStatusEnum.values()) {
				if (roleEnum.getCode() == code) {
					name = roleEnum.getName();
					break;
				}
			}
			return name;
		}
	}
	/**
	 * 订单流程
	 * 
	 * @author limh limh@anjbo.com
	 * @date 2017-3-14 上午09:58:49
	 */
	public enum CMOrderProgressEnum {
		CLOSED(-1, "已关闭"), 
		ASSESSNOLIMIT(0, "订单已提交，等待评估"), 
		ASSESSING(1, "评估中"), 
		ASSESSSUCCESS(2, "评估成功"), 
		ASSESSFAIL(3, "评估失败"), 
		AUDITNOSUBMIT(5, "贷款信息完善中"), 
		AUDITING(10, "贷款正在审批中"), 
		AUDITSUCCESS(15, "审批通过，银行确认已出批复"), 
		AUDITFAIL(20, "很遗憾，审批未通过"), 
		LENDING(30, "等待放款"), 
		LENDFAIL(40, "放款失败"), 
		LENDED(50, "放款成功");

		private int code;
		private String name;

		private CMOrderProgressEnum(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String getNameByCode(int code) {
			String name = "";
			for (CMOrderProgressEnum roleEnum : CMOrderProgressEnum.values()) {
				if (roleEnum.getCode() == code) {
					name = roleEnum.getName();
					break;
				}
			}
			return name;
		}

		/**
		 * 流程是否完成
		 * 
		 * @param code
		 * @return
		 */
		public static boolean OrderProgressFinish(int code) {
			return ASSESSFAIL.getCode() == code || AUDITFAIL.getCode() == code || LENDFAIL.getCode() == code
					|| LENDED.getCode() == code;
		}
	}
	public enum BankEnum{
		CCB(10000021,"建设银行"),
		GZCB(10000022,"广州银行");
		
		
		private BankEnum(int code, String name) {
			this.code = code;
			this.name = name;
		}
		private int code;
		private String name;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	/**
	 * 贷款流程枚举
	 * 
	 * @author limh limh@anjbo.com
	 * @date 2016-12-30 下午04:57:07
	 */
	public enum LoanProgressEnum {
		AUDIT_CUST_MANAGER("0301", "客户经理审核"), 
		AUDIT_PRE_INFO_INIT("0302", "审批前材料准备"), 
		AUDITING("0303","正在审批"), 
		AUDIT_SUCCESS("0304", "审批通过，银行确认已出预批复"), 
		AUDIT_FAIL("03AF","很遗憾，审批未通过"), 
		LENDING_MORTGAGE("0305", "房产过户和抵押"), 
		LENDING_WAIT("03LW","等待放款"), 
		LENDING_SUCCESS("0399", "放款成功，请尽快查询是否到账"), 
		LENDING_FAIL("03ZZ", "很遗憾，放款失败");

		private String code;
		private String name;

		private LoanProgressEnum(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String getNameByCode(String code) {
			String name = "";
			for (LoanProgressEnum roleEnum : LoanProgressEnum.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
					break;
				}
			}
			return name;
		}
	}
	/**
	 * 建行交易号枚举
	 * 
	 * @author limh limh@anjbo.com
	 * @date 2016-12-30 下午04:57:07
	 */
	public enum CCBTranNoEnum {
		C008("C008", "评估申请"), 
		C005("C005", "存量客户查询交易"), 
		C009("C009", "评估结果反馈"), 
		C001("C001", "房贷通客户信息新增"), 
		C002("C002","房贷通贷款信息新增"), 
		C015("C015", "审批结果反馈"), 
		C013("C013", "放款反馈"), 
		C012("C012", "意见反馈"), 
		C006("C006", "经办人信息查询交易"),
		C004("C004", "个人贷款合约状态查询"), 
		C015_NEW("C015", "审批结果查询"), 
		C011("C011", "查询放款信息"), 
		C007("C007", "上传影像通知"), 
		C019("C019", "出具征信电子授权书"), 
		C020("C020", "征信影像上传"), 
		C014("C014", "征信影像下传"), 
		C024("C024", "房产信息交易申请"), 
		C025("C025", "抵押登记预约"), 
		C026("C026", "权证登记信息新增"), 
		C027("C027", "影像信息交易新增");

		private String code;
		private String name;

		private CCBTranNoEnum(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String getNameByCode(String code) {
			String name = "";
			for (CCBTranNoEnum roleEnum : CCBTranNoEnum.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
					break;
				}
			}
			return name;
		}
	}
	
	/**
	 * 用户修改模块，请求来源
	 * @Author KangLG 2017年12月26日 下午5:09:48
	 */
	public enum UserUpdateFrom{
		/**默认普通操作*/DEFAULT,
		/**机构维护发起*/AGENCY
	}
	
	/**
	 * 钉钉接口方法
	 * @Author KangLG 2017年12月28日 上午9:27:35  更新接口方法（GetCorpDeptList、GetCorpUserList、GetCorpUserInfo、SendProcessInstance）
	 */
	public enum DingtalkServiceMethod{
		/**获取部门列表*/DEPT_LIST("GetCorpDeptList"),
		/**获取用户列表*/USER_LIST("GetCorpUserList"),
		/**获取用户信息*/USER_INFO("GetCorpUserInfo"),
		/**发起审批实例*/SEND_PROCESS_INSTANCE("SendProcessInstance");
		
		private String value;
		DingtalkServiceMethod(String value){
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
				
	}
	
	/**
	 * 客户类型
	 *  0未知1个人2小微企业
	 * @Author KangLG 2018年1月18日 下午3:12:08
	 */
	public enum CustomerType{
		/**默认*/DEFAULT,
		/**个人*/PERSON,
		/**小微企业*/COMPANY_SMALL
	}
	
	/*
	 * 陕国投数据枚举
	 * */
	
    public enum SGTWsNoEnum{
    	S2011("S2011","进件建立签约关系"),
    	S2012("S2012","确认签约关系"),
    	S2001("S2001","预审批批量申请"),
    	S2002("S2002","预审批结果查询"),
    	S2106("S2106","影像文件上传"),
    	S2107("S2107","查询影像文件上传"),
    	S5204("S5204","查询专户余额"),
    	S2101("S2101","进件批量申请"),
    	S2102("S2102","进件处理结果查询"),
    	S2201("S2201","还款计划上传"),
    	S2204("S2204","还款计划上传结果查询 "),
    	S2311("S2311","线上还款"),
    	S2312("S2312","线下还款"),
    	S2313("S2313","扣款查询接口");
    	
    	private String name;
    	private String code;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		private SGTWsNoEnum(String code ,String name) {
			this.code=code;
			this.name=name;
		}
		
		public static String getNameAndCode(String code) {
			String name="";
			for(SGTWsNoEnum eo:SGTWsNoEnum.values()) {
				if(eo.getCode().equals(code)) {
					name = eo.getName();
					break;
				}
			}
		    return name;
		  }
    }
    
	/**
	 * 分控终审还款方式
	 * @author yis
	 */
	public enum AuditFinalPaymentType {
		AUDIT_Final_PAYMENT_TYPE_ONE("1", "凭抵押回执放款"), 
		AUDIT_Final_PAYMENT_TYPE_TWO("2", "凭抵押状态放款"), 
		AUDIT_Final_PAYMENT_TYPE_THREE("3","凭他项权证放款");

		private String code;
		private String name;

		private AuditFinalPaymentType(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String getNameByCode(String code) {
			String name = "";
			for (LoanProgressEnum roleEnum : LoanProgressEnum.values()) {
				if (roleEnum.getCode().equals(code)) {
					name = roleEnum.getName();
					break;
				}
			}
			return name;
		}
	}
	
}
