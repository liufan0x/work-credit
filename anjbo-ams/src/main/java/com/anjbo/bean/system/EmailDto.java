package com.anjbo.bean.system;

import com.anjbo.utils.StringUtil;

public class EmailDto extends EmailOldDto{
	private String title;
	private String content;
	private String email;
	
	public String getTitle() {
		return StringUtil.isEmpty(title)?getUid():title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return StringUtil.isEmpty(content)?getM():content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
