package com.anjbo.bean.order;

import com.anjbo.bean.BaseDto;

public class OrderBusinfoTypeDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	/**业务资料*/
	private Integer id;
	/** 资料名称 */
	private String name;
	/** 上级分类ID */
	private Integer pid;
	/** 是否是面签资料(面签节点的业务资料上传)。1是，2不是(每个节点的照片) */
	private Integer isFaceInfo;
	/** 是否pc的业务类型(1是,2否) */
	private Integer pcPid;
	/** 备注说明 */
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getIsFaceInfo() {
		return isFaceInfo;
	}
	public void setIsFaceInfo(Integer isFaceInfo) {
		this.isFaceInfo = isFaceInfo;
	}
	public Integer getPcPid() {
		return pcPid;
	}
	public void setPcPid(Integer pcPid) {
		this.pcPid = pcPid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBusinfoTypeDto() {
		super();
	}

}
