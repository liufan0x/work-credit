package com.anjbo.bean.tools;

import org.springframework.web.multipart.MultipartFile;

/**
 * 版本信息
 * 
 * @ClassName: VersionDto
 * @author limh limh@zxsf360.com
 * @date 2015-2-12 下午03:50:04
 */
public class VersionDto {
	private int id;
	private String code;
	private String name;
	private String pack;
	private String url;
	/** 类型（apk,ipa...） **/
	private String type;
	private int forceUpdate;// 是否强制更新

	private int versionId;
	private String path;
	private String instructions;
	private String uploadTime;
	private String versionName;
	private int version;
	private String author;
	private int audit; //审核状态 0未审核 1已审核未通过 2已通过

	private MultipartFile file;

	private int iOSReminder;//是否提示IOS更新(0:否,1:是)
	private String iOSReminderTitle;//iOS提示标题
	private int iOSReminderForce;//iOS是否强制更新(0:否,1:是)
	
	private int isNeedUpdate;//是否需要更新 (0:否,1:是)
	
	public int getIsNeedUpdate() {
		return isNeedUpdate;
	}

	public void setIsNeedUpdate(int isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}

	public int getiOSReminder() {
		return iOSReminder;
	}

	public void setiOSReminder(int iOSReminder) {
		this.iOSReminder = iOSReminder;
	}

	public String getiOSReminderTitle() {
		return iOSReminderTitle;
	}

	public void setiOSReminderTitle(String iOSReminderTitle) {
		this.iOSReminderTitle = iOSReminderTitle;
	}

	public int getiOSReminderForce() {
		return iOSReminderForce;
	}

	public void setiOSReminderForce(int iOSReminderForce) {
		this.iOSReminderForce = iOSReminderForce;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(int forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

}
