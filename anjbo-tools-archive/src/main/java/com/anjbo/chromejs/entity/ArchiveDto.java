package com.anjbo.chromejs.entity;

/**
 * 查档
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:09:43
 */
public class ArchiveDto{
	
	private String id;//主键
	private int type;// 查档类型(1分户，2分栋)
	private int estateType;// 产权证书类型（0房地产权证书 1不动产权证书）
	private String estate;// 房产证号
	private String identity;// 身份证号
	private String obligee;//权利人
	private String yearNo;// 粤（不动产权证书要用）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEstateType() {
		return estateType;
	}
	public void setEstateType(int estateType) {
		this.estateType = estateType;
	}
	public String getEstate() {
		return estate;
	}
	public void setEstate(String estate) {
		this.estate = estate;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getObligee() {
		return obligee;
	}
	public void setObligee(String obligee) {
		this.obligee = obligee;
	}
	public String getYearNo() {
		return yearNo;
	}
	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}
	
}
