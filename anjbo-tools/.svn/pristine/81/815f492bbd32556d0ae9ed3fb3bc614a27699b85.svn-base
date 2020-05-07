package com.anjbo.bean.mort;

import com.anjbo.bean.BaseDto;

/**
 * 用户基本信息
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:09:23
 */
public class UserDto extends BaseDto {
	// 主键ID
	private int id;
	// 用户ID
	private String uid;
	// 名称
	private String name;
	// 所属组织或机构
	private String team = "";
	// 手机号
	private String mobile;
	// 密码
	private String pwd;
	//	邀请码
	private String invitationCode;
	// 用户类型
	private String userType;
	// 图片内容
	private String base64img;
	// 验证码
	private String verifyCode;
	// 图像路径
	private String headimg = "";
	/**
	 * 客户经理
	 */
	// 银行id
	private String bankId = "";
	// 分行id
	private String subBankId = "";
	// 是否审核通过。0未通过，1已通过[针对银行客户经理]
	private int valid;
	private String ip;
	private int source;// 来源（0系统注册 1手工注册 2微信注册）
	private String openId;// 微信公众号openId
	private int isMortHelper;// 是否小鸽助手指定回复机构(0:否,1是)
	private String channel;//渠道
	
	private int agencyId;//合作机构id
	private String agencyName;//合作机构名称
	
	private int isAgency;//是否为合作机构(0否 1是)
	private int isAgencyManager;//对应是否为管理者（0否 1是）
	private String createTimeStr;
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public int getIsAgency() {
		return isAgency;
	}

	public void setIsAgency(int isAgency) {
		this.isAgency = isAgency;
	}

	public int getIsAgencyManager() {
		return isAgencyManager;
	}

	public void setIsAgencyManager(int isAgencyManager) {
		this.isAgencyManager = isAgencyManager;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 */
	public UserDto() {
	}

	/**
	 * @param uid
	 * @param name
	 * @param team
	 * @param mobile
	 * @param pwd
	 * @param userType
	 * @param base64img
	 * @param verifyCode
	 * @param loginType
	 * @param headimg
	 */
	public UserDto(String uid, String name, String team, String mobile,
			String userType, String loginType, String headimg, String bankId,int valid,int agencyId) {
		super();
		this.uid = uid;
		this.name = name;
		this.team = team;
		this.mobile = mobile;
		this.userType = userType;
		this.headimg = headimg;
		this.bankId = bankId;
		this.valid = valid;
		this.agencyId = agencyId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBase64img() {
		return base64img;
	}

	public void setBase64img(String base64img) {
		this.base64img = base64img;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		if (bankId == null) {
			bankId = "";
		}
		this.bankId = bankId;
	}

	public String getSubBankId() {
		return subBankId;
	}

	public void setSubBankId(String subBankId) {
		if (subBankId == null) {
			subBankId = "";
		}
		this.subBankId = subBankId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String toString() {
		StringBuffer msg = new StringBuffer();
		msg.append("============注册信息===============<br>");
		msg.append("姓名:").append(name).append("<br>");
		msg.append("所属组织:").append(team).append("<br>");
		msg.append("手机号:").append(mobile);
		return msg.toString();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getIsMortHelper() {
		return isMortHelper;
	}

	public void setIsMortHelper(int isMortHelper) {
		this.isMortHelper = isMortHelper;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
