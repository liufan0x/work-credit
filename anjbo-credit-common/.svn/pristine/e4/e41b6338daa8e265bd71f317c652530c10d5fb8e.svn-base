package com.anjbo.bean.dingtalk;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.anjbo.bean.BaseDto;


/**
  *  [实体类]
  * @Description: tbl_third_dingtalk_bpms
  * @author 
  * @date 2017-10-13 11:37:59
  * @version V3.0
 */
public class ThirdDingtalkBpmsDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -1846995480433352804L;
	/**
	*字段名：id:
	*名称：id
	*/
	private long id;
	
	/**
	*字段名：bpmsFrom:
	*名称：审批流来源：order订单、orderDoc订单要件特批
	*/
	private String bpmsFrom;
	
	/**
	*字段名：bpmsFromId:
	*名称：审批流来源ID：如订单id、
	*/
	private long bpmsFromId = 0l;
	
	/**
	*字段名：agentId:
	*名称：企业微应用标识
	*/
	private String agentId;
	
	/**
	*字段名：processCode:
	*名称：审批流/审批表单码
	*/
	private String processCode;
	
	/**
	*字段名：originatorUserId:
	*名称：审批实例发起人的userid
	*/
	private String originatorUserId;
	private String originatorUserName;
	
	/**
	*字段名：originatorDeptId:
	*名称：发起人所在的部门ID
	*/
	private String originatorDeptId;
	
	/**
	*字段名：approvers:
	*名称：审批人userid列表
	*/
	private String approvers;
	private String approversName;
	
	/**
	*字段名：ccList:
	*名称：抄送人userid列表
	*/
	private String ccList;
	private String ccListName;
	
	/**
	*字段名：formComponent:
	*名称：审批流表单参数
	*/
	private String formComponent;
	private String[] formComponentParam;
	
	/**
	*字段名：processInstanceId:
	*名称：processInstanceId
	*/
	private String processInstanceId;
		
	/**id: id*/
	public long getId() {
		return id;
	}

	/**id: id*/
	public void setId(long id) {
		this.id = id;
	}
	
	/**bpmsFrom: 审批流来源：order订单*/
	public String getBpmsFrom() {
		return bpmsFrom;
	}

	/**bpmsFrom: 审批流来源：order订单*/
	public void setBpmsFrom(String bpmsFrom) {
		this.bpmsFrom = bpmsFrom;
	}
	
	/**bpmsFromId: 审批流来源ID：如订单id、*/
	public long getBpmsFromId() {
		return bpmsFromId;
	}

	/**bpmsFromId: 审批流来源ID：如订单id、*/
	public void setBpmsFromId(long bpmsFromId) {
		this.bpmsFromId = bpmsFromId;
	}
	
	/**agentId: 企业微应用标识*/
	public String getAgentId() {
		return agentId;
	}

	/**agentId: 企业微应用标识*/
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	/**processCode: 审批流/审批表单码*/
	public String getProcessCode() {
		return processCode;
	}

	/**processCode: 审批流/审批表单码*/
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	
	/**originatorUserId: 审批实例发起人的userid*/
	public String getOriginatorUserId() {
		return originatorUserId;
	}

	/**originatorUserId: 审批实例发起人的userid*/
	public void setOriginatorUserId(String originatorUserId) {
		this.originatorUserId = originatorUserId;
	}
	
	/**originatorDeptId: 发起人所在的部门ID*/
	public String getOriginatorDeptId() {
		return originatorDeptId;
	}

	/**originatorDeptId: 发起人所在的部门ID*/
	public void setOriginatorDeptId(String originatorDeptId) {
		this.originatorDeptId = originatorDeptId;
	}
	
	/**approvers: 审批人userid列表*/
	public String getApprovers() {
		return approvers;
	}

	/**approvers: 审批人userid列表*/
	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}
	
	/**ccList: 抄送人userid列表*/
	public String getCcList() {
		return ccList;
	}

	/**ccList: 抄送人userid列表*/
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	
	/**formComponent: 审批流表单参数*/
	public String getFormComponent() {
		return formComponent;
	}

	/**formComponent: 审批流表单参数*/
	public void setFormComponent(String formComponent) {
		this.formComponent = formComponent;
	}
	
	/**processInstanceId: processInstanceId*/
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**processInstanceId: processInstanceId*/
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public ThirdDingtalkBpmsDto(){	
	}
	/**
	 * 校验提交审批表单格式是否正确
	 * @Author KangLG<2017年10月13日>
	 * @return
	 */
	public boolean validDingBpms(){	
		if(StringUtils.isEmpty(bpmsFrom)
			|| StringUtils.isEmpty(originatorUserId) || StringUtils.isEmpty(originatorDeptId) || StringUtils.isEmpty(approvers)
			|| StringUtils.isEmpty(formComponent)){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getOriginatorUserName() {
		return originatorUserName;
	}

	public void setOriginatorUserName(String originatorUserName) {
		this.originatorUserName = originatorUserName;
	}

	public String getApproversName() {
		return approversName;
	}

	public void setApproversName(String approversName) {
		this.approversName = approversName;
	}

	public String getCcListName() {
		return ccListName;
	}

	public void setCcListName(String ccListName) {
		this.ccListName = ccListName;
	}

	public String[] getFormComponentParam() {
		return formComponentParam;
	}

	public void setFormComponentParam(String[] formComponentParam) {
		this.formComponentParam = formComponentParam;
	}
	
}