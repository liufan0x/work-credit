package com.anjbo.bean.dingtalk;

import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;


/**
  *  [实体类]
  * @Description: tbl_third_dingtalk_bpms_temp
  * @author 
  * @date 2017-10-16 15:46:54
  * @version V3.0
 */
public class ThirdDingtalkBpmsTempDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -8695489855434180479L;
	/**
	*字段名：id:
	*名称：id
	*/
	private int id;
	
	/**
	*字段名：code:
	*名称：模板编码
	*/
	@ApiModelProperty("模板编码")
	private String code;
	
	/**
	*字段名：name:
	*名称：模板名称
	*/
	@ApiModelProperty("模板名称")
	private String name;
	
	/**
	*字段名：agentId:
	*名称：企业微应用标识
	*/
	@ApiModelProperty("企业微应用标识")
	private String agentId;
	
	/**
	*字段名：processCode:
	*名称：审批表单码
	*/
	@ApiModelProperty("审批表单码")
	private String processCode;
	
	/**
	*字段名：approvers:
	*名称：审批人userid列表
	*/
	@ApiModelProperty("审批人userid列表")
	private String approvers;
	@ApiModelProperty("审批人userid列表名称")
	private String approversName;
	
	/**
	*字段名：ccList:
	*名称：抄送人userid列表
	*/
	@ApiModelProperty("抄送人userid列表")
	private String ccList;
	@ApiModelProperty("抄送人userid列表名称")
	private String ccListName;
	
	/**
	*字段名：formComponent:
	*名称：审批流表单参数
	*/
	@ApiModelProperty("审批流表单参数")
	private String formComponent;
		
	/**id: id*/
	public int getId() {
		return id;
	}

	/**id: id*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**code: 模板编码*/
	public String getCode() {
		return code;
	}

	/**code: 模板编码*/
	public void setCode(String code) {
		this.code = code;
	}
	
	/**name: 模板名称*/
	public String getName() {
		return name;
	}

	/**name: 模板名称*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**agentId: 企业微应用标识*/
	public String getAgentId() {
		return agentId;
	}

	/**agentId: 企业微应用标识*/
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	/**processCode: 审批表单码*/
	public String getProcessCode() {
		return processCode;
	}

	/**processCode: 审批表单码*/
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
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
	
	public ThirdDingtalkBpmsTempDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
	
}