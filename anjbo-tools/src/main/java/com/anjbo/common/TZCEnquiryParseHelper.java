/**
 * 
 */
package com.anjbo.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.anjbo.bean.enquiry.EnquiryAssessResp;
import com.anjbo.bean.enquiry.EnquiryReport;


/**
 * @author Kevin Chang
 * 
 */
public class TZCEnquiryParseHelper {
	private static final Log log = LogFactory
			.getLog(TZCEnquiryParseHelper.class);

	/**
	 * 解析申请评估报文
	 * @param xmlStr
	 * @return
	 */
	public static EnquiryAssessResp parseEnquiryAssessResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		EnquiryAssessResp assess = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			log.info("rootElement=" + rootElt.getName());
			// 获取根节点下的子节点信息
			String errCode = rootElt.elementTextTrim("errCode");
			String errMsg = rootElt.elementTextTrim("errMsg");
			assess = new EnquiryAssessResp();
			assess.setErrCode(Integer.parseInt(errCode));
			assess.setErrMsg(errMsg);
		} catch (DocumentException e) {
			log.error("parseEnquiryResp DocumentException.", e);
		} catch (Exception e) {
			log.error("parseEnquiryResp Exception.", e);
		}
		return assess;
	}
	
	public static List<EnquiryReport> parsePoolingEnquiryResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<EnquiryReport> poolingEnquiryReports = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			/**
			 * 获取根节点下的子节点信息
			 */
			// 结果统计信息
			int count = 0;
			Element costCountElt = rootElt.element("costCount");
			if (costCountElt != null) {
				Element itemElt = costCountElt.element("item");
				if (itemElt != null) {
					// 返回询价结果条数
					count = NumberUtils.toInt(itemElt.elementTextTrim("count"));
				}
			}
			// 有返回最新询价结果
			if (count > 0) {
				poolingEnquiryReports = new ArrayList<EnquiryReport>();
				Iterator<Element> iterator = rootElt
						.elementIterator("cisReport");
				while (iterator.hasNext()) {
					Element element = iterator.next();
					EnquiryReport enquiryReport = new EnquiryReport();
					// 询价单流水号
					String referID = element.attributeValue("referID");
					enquiryReport.setReferID(referID);
					// 询价时间
					String referTime = element.attributeValue("referTime");
					enquiryReport.setReferTime(referTime);
					// 回复时间
					String replyTime = element.attributeValue("replyTime");
					enquiryReport.setReplyTime(replyTime);
					Element resultElt = element.element("result");
					if (resultElt != null) {
						Element itemElt = resultElt.element("item");
						if (itemElt != null) {
							// 询价公司内部流水号
							String pgsqID = itemElt.elementTextTrim("pgsqID");
							enquiryReport.setPgsqID(pgsqID);
							// 物业名称(必填项，字符型长度100)
							String projectName = itemElt
									.elementTextTrim("projectName");
							enquiryReport.setProjectName(projectName);
							// 栋号（必填项，字符型长度50）
							String buildingName = itemElt
									.elementTextTrim("buildingName");
							enquiryReport.setBuildingName(buildingName);
							// 房号（必填项，字符型长度50）
							String houseName = itemElt
									.elementTextTrim("houseName");
							enquiryReport.setHouseName(houseName);
							// 建筑面积（必填项，2为小数）
							double buildingArea = NumberUtils.toDouble(
									itemElt.elementTextTrim("buildingArea"), 0);
							enquiryReport.setBuildingArea(buildingArea);
							// 评估单价
							double unitPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("unitPrice"), 0);
							enquiryReport.setUnitPrice(unitPrice);
							// 评估总价
							double totalPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("totalPrice"), 0);
							enquiryReport.setTotalPrice(totalPrice);
							// 预计税费
							double tax = NumberUtils.toDouble(
									itemElt.elementTextTrim("tax"), 0);
							enquiryReport.setTax(tax);
							// 评估净值
							double netPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("netPrice"), 0);
							enquiryReport.setNetPrice(netPrice);
							// 评估单状态（0 已回复 1 无法评估）
							String status = itemElt.elementTextTrim("status");
							// 特别提示信息
							String specialMessage = itemElt
									.elementTextTrim("specialMessage");
							// 无法评估
							if ("1".equals(status)
									|| ("0".equals(status) && "0"
											.equals(totalPrice))) {
								status = "-2";
								specialMessage = "对不起，系统暂时无法评估此物业";
							} else {
								status = "1";
								specialMessage = "";
							}
							enquiryReport.setStatus(status);
							enquiryReport.setSpecialMessage(specialMessage);
						}
					}
					poolingEnquiryReports.add(enquiryReport);
				}
			}
		} catch (DocumentException e) {
			log.error("parsePoolingEnquiryResp DocumentException.", e);
		} catch (Exception e) {
			log.error("parsePoolingEnquiryResp Exception.", e);
		}
		return poolingEnquiryReports;
	}
}
