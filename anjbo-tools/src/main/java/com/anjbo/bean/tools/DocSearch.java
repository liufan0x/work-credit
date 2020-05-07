package com.anjbo.bean.tools;

import java.util.Date;
import java.util.List;
import com.anjbo.bean.BaseDto;

/**
 * 办文查询
 * @author limh limh@zxsf360.com
 * @date 2015-10-9 下午03:36:54
 */
public class DocSearch extends BaseDto{
	private int id;
	private String uid;
	private int searchId;
	/**文号**/
	private String docNo;
	/**统一申办流水号**/
	private String serialNo;
	/**来文事项**/
	private String docItem;
	/**受理时间**/
	private String acceptTime;
	/**答复日期**/
	private String replyDate; 
	/**办理状态**/
	private String status; 
	/**查询时间**/
	private Date createTime;

	private boolean isSearch;
	private List<DocSearch> doclist;
	private String createTimeStr;
	
	public String getTitle(){
		return "办文查询提醒";
	}
	public String getReTitle(){
		return "办文查询[再次查询]提醒";
	}
	
	public boolean getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getDocItem() {
		return docItem;
	}
	public void setDocItem(String docItem) {
		this.docItem = docItem;
	}
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isSearch() {
		return isSearch;
	}
	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public List<DocSearch> getDoclist() {
		return doclist;
	}
	public void setDoclist(List<DocSearch> doclist) {
		this.doclist = doclist;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public int getSearchId() {
		return searchId;
	}
	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}
}
