
package com.anjbo.utils.huarong;

import java.io.Serializable;

/**
 * @Description: 接口，业务逻辑响应类
 * @see: ReturnParam 此处填写需要参考的类
 * @version 2017年8月22日 下午15:19:06
 * @author chenzm
 */
public class ReturnParam<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 响应状态 成功-SUCCESS */
	public static String STS_SUCCESS = "SUCCESS";

	/** 响应状态 失败-FAIL */
	public static String STS_FAIL = "FAIL";

	/**
	 * 成功编码
	 */
	public static final String SUCCESSCODE = "00000";

	/**
	 * 失败编码
	 */
	public static final String FAILCODE = "11111";

	/**
	 * 业务异常编码
	 */
	public static final String FAILBUSCODE = "22222";

	/** 响应状态 */
	private String retSts;
	/** 响应编码 */
	private String retCode;
	/** 响应信息 */
	private String retInfo;

	/** 响应数据 */
	private T retData;

	public static String getSTS_SUCCESS() {
		return STS_SUCCESS;
	}

	public static void setSTS_SUCCESS(String sTS_SUCCESS) {
		STS_SUCCESS = sTS_SUCCESS;
	}

	public static String getSTS_FAIL() {
		return STS_FAIL;
	}

	public static void setSTS_FAIL(String sTS_FAIL) {
		STS_FAIL = sTS_FAIL;
	}

	public String getRetSts() {
		return retSts;
	}

	public void setRetSts(String retSts) {
		this.retSts = retSts;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public T getRetData() {
		return retData;
	}

	public void setRetData(T retData) {
		this.retData = retData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReturnParam [retSts=");
		builder.append(retSts);
		builder.append(", retCode=");
		builder.append(retCode);
		builder.append(", retInfo=");
		builder.append(retInfo);
		builder.append(", returnData=");
		builder.append(retData);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @Description 返回结果赋值
	 * @param retSts 响应状态
	 * @param retCode 响应编码
	 * @param retInfo 响应信息
	 * @param retData 响应数据
	 * @see 需要参考的类或方法
	 */
	public void setReturnParam(String retSts, String retCode, String retInfo, T retData) {
		this.retSts = retSts;
		this.retCode = retCode;
		this.retInfo = retInfo;
		this.retData = retData;
	}

}
