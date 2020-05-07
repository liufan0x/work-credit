package com.anjbo.bean.dingtalk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.anjbo.bean.BaseDto;


/**
  *  [实体类]
  * @Description: tbl_third_dingtalk_bpms_details
  * @author 
  * @date 2017-10-13 11:37:59
  * @version V3.0
 */
public class ThirdDingtalkBpmsDetailsDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 8124804881741582440L;
	private long id;	
	/**审批实例id*/
	private String processInstanceId;	
	/**审批实例状态enum：RUNNING待审批  COMPLETED审批通过  CANCELED已取消  TERMINATED被拒绝 REDIRECT被转交*/
	private BpmsDetailsStatus status;
		
	/**
	 * 审批回调状态
	 * @Author KangLG 2017年10月16日 下午2:12:23
	 */
	public enum BpmsDetailsStatus{
		/**待   审  批*/RUNNING,
		/**审批通过*/COMPLETED,
		/**已   取  消*/CANCELED,
		/**被   拒  绝*/TERMINATED,
		/**被   转  交*/REDIRECT
	}
	/** 钉钉状态 */
	@SuppressWarnings("serial")
	public Map<String, BpmsDetailsStatus> mapDingtalkStatus = new HashMap<String, BpmsDetailsStatus>(){{
		put("Agree", BpmsDetailsStatus.COMPLETED);
		put("Refuse", BpmsDetailsStatus.TERMINATED);
	}};
		
	/*
	 * getter - setter
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public BpmsDetailsStatus getStatus() {
		return status;
	}
	public void setStatus(BpmsDetailsStatus status) {
		this.status = status;
	}
	
	public ThirdDingtalkBpmsDetailsDto(){}	
	public ThirdDingtalkBpmsDetailsDto(String processInstanceId, String status){
		this.processInstanceId = processInstanceId;
		this.status = (BpmsDetailsStatus)MapUtils.getObject(mapDingtalkStatus, status, BpmsDetailsStatus.CANCELED);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
		
}