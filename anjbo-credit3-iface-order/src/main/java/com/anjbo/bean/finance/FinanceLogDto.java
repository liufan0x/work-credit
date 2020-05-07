/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@ApiModel(value = "申请放款日志")
public class FinanceLogDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 申请放款日志 */
	@ApiModelProperty(value = "申请放款日志")
	private java.lang.Integer id;
	
	/** 对应实体类属性 */
	@ApiModelProperty(value = "对应实体类属性")
	private java.lang.String beanColumn;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 字段名称 */
	@ApiModelProperty(value = "字段名称")
	private java.lang.String colName;
	
	/** 初始值 */
	@ApiModelProperty(value = "初始值")
	private java.lang.String startVal;
	
	/** 结束值 */
	@ApiModelProperty(value = "结束值")
	private java.lang.String endVal;
	
	/** 0:不展示1：展示 */
	@ApiModelProperty(value = "0:不展示1：展示")
	private java.lang.Integer isShow;
	
	/** 1:申请放款修改，2：收利息修改  3扣回后置费用修改4：核实收费 */
	@ApiModelProperty(value = "1:申请放款修改，2：收利息修改  3扣回后置费用修改4：核实收费")
	private java.lang.Integer type;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setBeanColumn(java.lang.String value) {
		this.beanColumn = value;
	}	
	public java.lang.String getBeanColumn() {
		return this.beanColumn;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setColName(java.lang.String value) {
		this.colName = value;
	}	
	public java.lang.String getColName() {
		return this.colName;
	}
	public void setStartVal(java.lang.String value) {
		this.startVal = value;
	}	
	public java.lang.String getStartVal() {
		return this.startVal;
	}
	public void setEndVal(java.lang.String value) {
		this.endVal = value;
	}	
	public java.lang.String getEndVal() {
		return this.endVal;
	}
	public void setIsShow(java.lang.Integer value) {
		this.isShow = value;
	}	
	public java.lang.Integer getIsShow() {
		return this.isShow;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}	
	public java.lang.Integer getType() {
		return this.type;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("beanColumn",getBeanColumn())
			.append("orderNo",getOrderNo())
			.append("colName",getColName())
			.append("startVal",getStartVal())
			.append("endVal",getEndVal())
			.append("isShow",getIsShow())
			.append("type",getType())
			.toString();
	}
}

