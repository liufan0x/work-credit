package com.anjbo.bean.finance;

import java.util.Date;

import com.anjbo.bean.BaseDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;


/**
 * 待核实利息/扣回后置的订单
 * @author YS
 *
 */
public class LendingHarvestDto extends OrderBaseBorrowDto{
	private static final long serialVersionUID = 1L;
	
	  private String createUid;// 提交人uid
	  private String updateUid; //'修改人uid'
	  private String orderNo;//订单编号
	  private Date createTime;// 创建时间
	  private Date updateTime ;// 最后更新时间
	  private Date interestTime ;//收取利息时间
	  private String interestImg ;//上传截屏
	  private double collectInterestMoney ;//实收利息金额
	  private double payInterestMoney;// 应付利息金额
	  private double returnMoney;// 返佣金额
	  private double receivableInterestMoney ;// 应收利息金额
	  private String remark; //备注
	  private String rateImg; //费率凭证
	  private int type;
	  private int isUpdata;//是否修改费率
	  private double backExpensesMoney;  //扣回后置费用
	  private String rebateUid; //返佣处理人
	  
	  
	  public String getRebateUid() {
		return rebateUid;
	}
	public void setRebateUid(String rebateUid) {
		this.rebateUid = rebateUid;
	}
	private String interestTimeStr;
	  public double getBackExpensesMoney() {
		return backExpensesMoney;
	}
	public void setBackExpensesMoney(double backExpensesMoney) {
		this.backExpensesMoney = backExpensesMoney;
	}
	
	
	public String getInterestTimeStr() {
		return interestTimeStr;
	}
	public void setInterestTimeStr(String interestTimeStr) {
		this.interestTimeStr = interestTimeStr;
	}
	public int getIsUpdata() {
		return isUpdata;
	}
	public void setIsUpdata(int isUpdata) {
		this.isUpdata = isUpdata;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRateImg() {
		return rateImg;
	}
	public void setRateImg(String rateImg) {
		this.rateImg = rateImg;
	}
	private String uid;

	  public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getInterestTime() {
		return interestTime;
	}
	public void setInterestTime(Date interestTime) {
		this.interestTime = interestTime;
	}
	public String getInterestImg() {
		return interestImg;
	}
	public void setInterestImg(String interestImg) {
		this.interestImg = interestImg;
	}
	public double getCollectInterestMoney() {
		return collectInterestMoney;
	}
	public void setCollectInterestMoney(double collectInterestMoney) {
		this.collectInterestMoney = collectInterestMoney;
	}
	public double getPayInterestMoney() {
		return payInterestMoney;
	}
	public void setPayInterestMoney(double payInterestMoney) {
		this.payInterestMoney = payInterestMoney;
	}
	public double getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(double returnMoney) {
		this.returnMoney = returnMoney;
	}
	public double getReceivableInterestMoney() {
		return receivableInterestMoney;
	}
	public void setReceivableInterestMoney(double receivableInterestMoney) {
		this.receivableInterestMoney = receivableInterestMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	  
	    
}
