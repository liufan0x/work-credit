package com.anjbo.bean.finance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 待付费
 * @author yis
 *
 */
public class ReceivablePayDto extends ReceivableHasDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String payImg;  //退费时间/付罚息时间
	private Date payTime;
	private String payTimeStr;

	private BigDecimal penaltyReal;  //返佣
	private BigDecimal rebateMoney;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public String getPayImg() {
		return payImg;
	}

	public void setPayImg(String payImg) {
		this.payImg = payImg;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

	

	public BigDecimal getPenaltyReal() {
		return penaltyReal;
	}

	public void setPenaltyReal(BigDecimal penaltyReal) {
		this.penaltyReal = penaltyReal;
	}

	public BigDecimal getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(BigDecimal rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	

}
