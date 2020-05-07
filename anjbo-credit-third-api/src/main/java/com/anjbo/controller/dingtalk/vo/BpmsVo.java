/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.controller.dingtalk.vo.BpmsVo.java  <2017年10月17日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.dingtalk.vo;

import org.apache.commons.lang.StringUtils;

/**
 * @Author KangLG 
 * @Date 2017年10月17日 下午5:26:45
 * @version 1.0
 */
public class BpmsVo {
	/**来源(审批流程模板编码)*/
	private String bpmsFrom;
	/**来源对象关联ID(如订单ID)*/
	private long bpmsObjectId = 0l;
	/**表单描述参数*/
	private String[] formComponentParam;
	
	/**
	 * 校验传入参数
	 * @Author KangLG<2017年10月17日>
	 * @return
	 */
	public boolean validated(){
		if(StringUtils.isEmpty(bpmsFrom) || null==formComponentParam || formComponentParam.length<2){
			return false;
		}
		return true;
	}
	
	public String getBpmsFrom() {
		return bpmsFrom;
	}
	public void setBpmsFrom(String bpmsFrom) {
		this.bpmsFrom = bpmsFrom;
	}
	public long getBpmsObjectId() {
		return bpmsObjectId;
	}
	public void setBpmsObjectId(long bpmsObjectId) {
		this.bpmsObjectId = bpmsObjectId;
	}
	public String[] getFormComponentParam() {
		return formComponentParam;
	}
	public void setFormComponentParam(String[] formComponentParam) {
		this.formComponentParam = formComponentParam;
	}
	

}
