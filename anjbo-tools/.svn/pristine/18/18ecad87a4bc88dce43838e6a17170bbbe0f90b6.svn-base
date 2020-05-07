package com.anjbo.bean.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.anjbo.utils.DateUtil;

/**
 * 房产评估（查询过户价）
 * @author limh
 *
 */
public class HouseAssess {
	
	private int id;
	/**权利人类别（个人/单位）**/
	private String radiobutton;
	/**身份证号（个人）**/
	private String id_no;
	/**证书类别（房地产权证书/不动产权证书）**/
	private String zslx;
	/**房地产证号（个人）**/
	private String cert_no; 
	/**单位名称（单位）**/
	private String owner_name; 
	/**房地产证号（单位）**/
	private String cert_no2; 
	/**验证码**/
	private String CERTCODE;
	
	private Date createTime;
	private String createTimeStr;
	private String cookieValue;
	
	/**不动产权证书粤号**/
	private String selectbdc=DateUtil.getDateByFmt(new Date(),"yyyy");
	
	public String getSelectbdc() {
		return selectbdc;
	}
	public void setSelectbdc(String selectbdc) {
		this.selectbdc = selectbdc;
	}
	public String getZslx() {
		return zslx;
	}
	public void setZslx(String zslx) {
		this.zslx = zslx;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
	public String getParams(){
		try {
			if(StringUtils.isNotEmpty(id_no)){
				id_no = URLEncoder.encode(id_no,"utf-8");
			}
			if(StringUtils.isNotEmpty(radiobutton)){
				radiobutton = URLEncoder.encode(radiobutton,"utf-8");
			}
			if(StringUtils.isNotEmpty(owner_name)){
				owner_name = URLEncoder.encode(owner_name,"utf-8");
			}
			if(StringUtils.isNotEmpty(zslx)){
				zslx = URLEncoder.encode(zslx,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new StringBuffer().append("?radiobutton=").append(radiobutton)
		.append("&id_no=").append(id_no)
		.append("&cert_no=").append(cert_no)
		.append("&zslx=").append(zslx)
		.append("&owner_name=").append(owner_name)
		.append("&cert_no2=").append(cert_no2)
		.append("&CERTCODE=").append(CERTCODE)
		.append("&selectbdc=").append(selectbdc).toString();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRadiobutton() {
		return radiobutton;
	}
	public void setRadiobutton(String radiobutton) {
		this.radiobutton = radiobutton;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getCert_no2() {
		return cert_no2;
	}
	public void setCert_no2(String cert_no2) {
		this.cert_no2 = cert_no2;
	}
	public String getCERTCODE() {
		return CERTCODE;
	}
	public void setCERTCODE(String cERTCODE) {
		CERTCODE = cERTCODE;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
