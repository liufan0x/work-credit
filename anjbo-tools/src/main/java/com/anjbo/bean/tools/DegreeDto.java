package com.anjbo.bean.tools;


public class DegreeDto extends DegreeLockRecordDto{
	/**区域名称**/
	private String region; 
	/**房屋编码**/
	private String fwbm;
	/**房屋地址**/
	private String houseAddress;
	public DegreeDto() {
		super();
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFwbm() {
		return fwbm;
	}
	public void setFwbm(String fwbm) {
		this.fwbm = fwbm;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	
}
