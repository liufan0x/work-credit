/**
 * 
 */
package com.anjbo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;




/**
 * @author Kevin Chang
 * 
 */
public class ParseXmlUtil {
	private static final Log log = LogFactory
			.getLog(ParseXmlUtil.class);

	/*********************************************************同致诚*******************************************************************/
	
	/**
	 * 解析同致诚物业名称
	 * @param xmlStr
	 * @return estateMaps
	 * @return key id 	物业名称Id
	 * @return key name 物业名称
	 */
	public static List<Map<String, String>> parseTZCEstateResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> estateMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			log.info("rootElement=" + rootElt.getName());
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element estatesElt = rootElt.element("Estates");
				if (estatesElt != null) {
					Iterator<Element> iterator = estatesElt.elementIterator("Estate");
					estateMaps = new ArrayList<Map<String,String>>();
					while (iterator.hasNext()) {
						Element estateItemElt = iterator.next();
						String estateId = estateItemElt.attributeValue("EstateId");
						String estateName = estateItemElt.attributeValue("EstateName");
						Map<String, String> estateMap = new HashMap<String, String>();
						estateMap.put("id", estateId);
						estateMap.put("name", estateName);
						estateMaps.add(estateMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析同致诚物业名称异常", e);
		}
		return estateMaps;
	}
	
	/**
	 * 解析同致诚楼栋
	 * 
	 * @param xmlStr
	 * @return buildingMaps
	 * @return key id 	楼栋名称Id
	 * @return key name 楼栋名称
	 */
	public static List<Map<String, Object>> parseTZCBuildingResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, Object>> buildingMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element buildingsElt = rootElt.element("Buildings");
				if (buildingsElt != null) {
					Iterator<Element> iterator = buildingsElt.elementIterator("Building");
					buildingMaps = new ArrayList<Map<String, Object>>();
					while (iterator.hasNext()) {
						Element buildingItemElt = iterator.next();
						String buildingId = buildingItemElt.attributeValue("BuildingId");
						String buildingName = buildingItemElt.attributeValue("BuildingName");
						Map<String, Object> buildingMap = new HashMap<String, Object>();
						buildingMap.put("id", buildingId);
						buildingMap.put("name", buildingName);
						buildingMaps.add(buildingMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析同致诚楼栋异常", e);
		}
		return buildingMaps;
	}
	

	/**
	 * 解析同致诚房号
	 * 
	 * @param xmlStr
	 * @return roomMaps
	 * @return key id 	房间名称Id
	 * @return key name 房间名称
	 */
	public static List<Map<String, String>> parseTZCRoomResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> roomMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			log.info("rootElement=" + rootElt.getName());
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element flagsElt = rootElt.element("Flags");
				if (flagsElt != null) {
					Iterator<Element> iterator = flagsElt.elementIterator("Flag");
					roomMaps = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {
						Element roomItemElt = iterator.next();
						String flagId = roomItemElt.attributeValue("FlagId");
						String flagName = roomItemElt
								.attributeValue("FlagName");
						String buildArea = roomItemElt
						.attributeValue("BuildArea");
						Map<String, String> roomMap = new HashMap<String, String>();
						roomMap.put("id", flagId);
						roomMap.put("name", flagName);
						roomMap.put("buildArea", buildArea);
						roomMaps.add(roomMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析同致诚房号异常", e);
		}
		return roomMaps;
	}
	

	/**
	 * 解析同致诚银行
	 * 
	 * @param xmlStr
	 * @return bankMaps
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	public static List<Map<String, String>> parseTZCBankResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> bankMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element banksElt = rootElt.element("Banks");
				if (banksElt != null) {
					Iterator<Element> iterator = banksElt
							.elementIterator("Bank");
					bankMaps = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {
						Element bankItemElt = iterator.next();
						String bankId = bankItemElt.attributeValue("Id");
						String bankName = bankItemElt.attributeValue("Name");
						Map<String, String> bankMap = new HashMap<String, String>();
						bankMap.put("id", bankId);
						bankMap.put("name", bankName);
						bankMaps.add(bankMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析同致诚银行异常", e);
		}
		return bankMaps;
	}


	/**
	 * 解析同致诚支行银行
	 * 
	 * @param xmlStr
	 * @return bankMaps
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	public static List<Map<String, String>> parseTZCSubBankResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> bankMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element banksElt = rootElt.element("SubBanks");
				if (banksElt != null) {
					Iterator<Element> iterator = banksElt
							.elementIterator("SubBank");
					bankMaps = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {
						Element bankItemElt = iterator.next();
						String bankId = bankItemElt.attributeValue("Id");
						String bankName = bankItemElt.attributeValue("Name");
						Map<String, String> bankMap = new HashMap<String, String>();
						bankMap.put("id", bankId);
						bankMap.put("name", bankName);
						bankMaps.add(bankMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析同致诚支行银行异常", e);
		}
		return bankMaps;
	}
	

	/**
	 * 解析银行客户经理
	 * 
	 * @param xmlStr
	 * @return managerMaps
	 * @return key bankId
	 * @return key bankName
	 * @return key subBankId
	 * @return key subBankName
	 * @return key managerId
	 * @return key managerName
	 */
	public static List<Map<String, String>> parseTZCManagerResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> managerMaps = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			log.info("rootElement=" + rootElt.getName());
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element managersElt = rootElt.element("Managers");
				if (managersElt != null) {
					Iterator<Element> iterator = managersElt
							.elementIterator("Manager");
					managerMaps = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {
						Element managerItemElt = iterator.next();
						String bankId = managerItemElt.attributeValue("BankId");
						String bankName = managerItemElt
								.attributeValue("BankName");
						String subBankId = managerItemElt
								.attributeValue("SubBankId");
						String subBankName = managerItemElt
								.attributeValue("SubBankName");
						String managerId = managerItemElt
								.attributeValue("ManagerId");
						String managerName = managerItemElt
								.attributeValue("ManagerName");
						Map<String, String> managerMap = new HashMap<String, String>();
						managerMap.put("bankId", bankId);
						managerMap.put("bankName", bankName);
						managerMap.put("subBankId", subBankId);
						managerMap.put("subBankName", subBankName);
						managerMap.put("managerId", managerId);
						managerMap.put("managerName", managerName);
						managerMaps.add(managerMap);
					}
				}
			}
		} catch (Exception e) {
			log.error("解析银行客户经理异常", e);
		}
		return managerMaps;
	}


	/**
	 * 解析税费数据
	 * 
	 * @param xmlStr
	 * @return taxMap
	 * @return key totalPrice	评估总值，单位万元
	 * @return key netPrice		评估净值，单位万元
	 * @return key tax			税费合计，单位元
	 * @return key salesTax		营业税，单位元
	 * @return key urbanTax		城建税，单位元
	 * @return key eduAttached	教育费附件，单位元
	 * @return key stamp		印花税，单位元
	 * @return key landTax		土地增值税，单位元
	 * @return key income		所得税，单位元
	 * @return key tranFees		交易手续费，单位元
	 * @return key deed			契税，单位元
	 * @return key embankFees	堤围费，单位元
	 * @return key auctionFees	拍卖处理费，单位元
	 * @return key costs		诉讼费，单位元
	 * @return key regFees		登记费，单位元
	 * @return key notaryFees	公证费，单位元
	 * @return key serFees		交易服务费，单位元
	 */
	public static Map<String, Object> parseTaxResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		Map<String, Object> taxMap = null;
		// 将字符串转为XML
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			String errorCode = rootElt.element("Error").attributeValue(
					"ErrCode");
			if ("0".equals(errorCode)) {
				Element taxElt = rootElt.element("Tax");
				if (taxElt != null) {
					taxMap = new HashMap<String, Object>();
					// 评估总值
					Element totalPriceElt = taxElt.element("totalPrice");
					double totalPrice = 0;
					if (totalPriceElt != null) {
						totalPrice = NumberUtils.toDouble(
								totalPriceElt.attributeValue("value"), 0);
					}
					// 评估净值
					double netPrice = 0;
					Element netPriceElt = taxElt.element("netPrice");
					if (netPriceElt != null) {
						netPrice = NumberUtils.toDouble(netPriceElt
								.attributeValue("value"));
					}
					// 税费合计
					double tax = 0;
					Element taxAmountElt = taxElt.element("Tax");
					if (taxAmountElt != null) {
						tax = NumberUtils.toDouble(
								taxAmountElt.attributeValue("value"), 0);
					}
					taxMap.put("totalPrice", totalPrice);
					taxMap.put("netPrice", netPrice);
					taxMap.put("tax", tax);
					// 营业税，单位元
					double salesTax = 0;
					Element salesTaxElt = taxElt.element("SalesTax");
					if (salesTaxElt != null) {
						salesTax = NumberUtils.toDouble(salesTaxElt
								.attributeValue("value"));
					}
					taxMap.put("salesTax", salesTax);
					// 城建税，单位元
					double urbanTax = 0;
					Element urbanTaxElt = taxElt.element("UrbanTax");
					if (urbanTaxElt != null) {
						urbanTax = NumberUtils.toDouble(urbanTaxElt
								.attributeValue("value"));
					}
					taxMap.put("urbanTax", urbanTax);
					// 教育费附件，单位元
					double eduAttached = 0;
					Element eduAttachedElt = taxElt.element("EduAttached");
					if (eduAttachedElt != null) {
						eduAttached = NumberUtils.toDouble(eduAttachedElt
								.attributeValue("value"));
					}
					taxMap.put("eduAttached", eduAttached);
					// 印花税，单位元
					double stamp = 0;
					Element stampElt = taxElt.element("Stamp");
					if (stampElt != null) {
						stamp = NumberUtils.toDouble(stampElt
								.attributeValue("value"));
					}
					taxMap.put("stamp", stamp);
					// 土地增值税，单位元
					double landTax = 0;
					Element landTaxElt = taxElt.element("LandTax");
					if (landTaxElt != null) {
						landTax = NumberUtils.toDouble(
								landTaxElt.attributeValue("value"), 0);
					}
					taxMap.put("landTax", landTax);
					// 所得税，单位元
					double income = 0;
					Element incomeElt = taxElt.element("Income");
					if (incomeElt != null) {
						income = NumberUtils.toDouble(incomeElt
								.attributeValue("value"));
					}
					taxMap.put("income", income);
					// 交易手续费，单位元
					double tranFees = 0;
					Element tranFeesElt = taxElt.element("TranFees");
					if (tranFeesElt != null) {
						tranFees = NumberUtils.toDouble(tranFeesElt
								.attributeValue("value"));
					}
					taxMap.put("tranFees", tranFees);
					// 契税，单位元
					double deed = 0;
					Element deedElt = taxElt.element("Deed");
					if (deedElt != null) {
						deed = NumberUtils.toDouble(deedElt
								.attributeValue("value"));
					}
					taxMap.put("deed", deed);
					// 拍卖处理费，单位元
					double auctionFees = 0;
					Element auctionFeesElt = taxElt.element("AuctionFees");
					if (auctionFeesElt != null) {
						auctionFees = NumberUtils.toDouble(auctionFeesElt
								.attributeValue("value"));
					}
					taxMap.put("auctionFees", auctionFees);
					// 诉讼费，单位元
					double costs = 0;
					Element costsElt = taxElt.element("Costs");
					if (costsElt != null) {
						costs = NumberUtils.toDouble(costsElt
								.attributeValue("value"));
					}
					taxMap.put("costs", costs);
					// 诉讼费，单位元
					double regFees = 0;
					Element regFeesElt = taxElt.element("RegFees");
					if (regFeesElt != null) {
						regFees = NumberUtils.toDouble(regFeesElt
								.attributeValue("value"));
					}
					taxMap.put("regFees", regFees);
					// 公证费，单位元
					double notaryFees = 0;
					Element notaryFeesElt = taxElt.element("NotaryFees");
					if (notaryFeesElt != null) {
						notaryFees = NumberUtils.toDouble(notaryFeesElt
								.attributeValue("value"));
					}
					taxMap.put("notaryFees", notaryFees);
					// 交易服务费，单位元
					double serFees = 0;
					Element serFeesElt = taxElt.element("SerFees");
					if (serFeesElt != null) {
						serFees = NumberUtils.toDouble(serFeesElt
								.attributeValue("value"));
					}
					taxMap.put("serFees", serFees);
					// 堤围费，单位元
					double embankFees = 0;
					Element embankFeesElt = taxElt.element("EmbankFees");
					if (embankFeesElt != null) {
						embankFees = NumberUtils.toDouble(embankFeesElt
								.attributeValue("value"));
					}
					taxMap.put("embankFees", embankFees);
				}
			}
		} catch (Exception e) {
			log.error("parseTaxResp Exception.", e);
		}
		return taxMap;
	}
	
	
	/**
	 * 解析同致诚询价结果
	 * @user lic
	 * @date 2016-6-3 上午11:08:12 
	 * @param xmlStr
	 * @return enquiryMapResp
	 * @return key status			状态 1：正常；2：异常
	 * @return key errorCode		异常代码
	 * @return key errorMessage		异常描述
	 * @return key returnValue		备注
	 */
	public static Map<String, String> parseTZCEnquiryResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		Map<String, String> enquiryMapResp = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 拿到根节点的名称
			log.info("rootElement=" + rootElt.getName());
			// 获取根节点下的子节点信息
			String status = rootElt.elementTextTrim("status");
			String errorCode = rootElt.elementTextTrim("errorCode");
			String errorMessage = rootElt.elementTextTrim("errorMessage");
			String returnValue = rootElt.elementTextTrim("returnValue");
			enquiryMapResp = new HashMap<String, String>();
			enquiryMapResp.put("status", status);
			enquiryMapResp.put("errorCode", errorCode);
			enquiryMapResp.put("errorMessage", errorMessage);
			enquiryMapResp.put("returnValue", returnValue);
		}catch (Exception e) {
			log.error("解析同致诚询价结果异常", e);
		}
		return enquiryMapResp;
	}
	

	/**
	 * 查询同致诚询价结果
	 * @user lic
	 * @date 2016-6-3 下午02:09:58 
	 * @param xmlStr
	 * @return	List<Map> poolingEnquiryReportMaps
	 * @return key referID 		询价单流水号
	 * @return key referTime 	询价时间
	 * @return key replyTime	回复时间
	 * @return key pgsqID		询价公司内部流水号
	 * @return key propertyName	物业名称
	 * @return key buildingName	楼栋
	 * @return key houseName	房号
	 * @return key buildingArea	建筑面积
	 * @return key unitPrice	评估单价
	 * @return key totalPrice	评估总价
	 * @return key tax			预计税费
	 * @return key netPrice		评估净值
	 * @return key status		评估单状态（0 已回复 1 无法评估）
	 * @return key specialMessage特别提示信息
	 */
	public static List<Map<String, String>> parsePoolingTZCEnquiryResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> poolingEnquiryReportMaps = null;
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
				poolingEnquiryReportMaps = new ArrayList<Map<String, String>>();
				Iterator<Element> iterator = rootElt.elementIterator("cisReport");
				while (iterator.hasNext()) {
					Element element = iterator.next();
					Map<String, String> enquiryReportMap = new HashMap<String, String>();
					// 询价单流水号
					String referID = element.attributeValue("referID");
					enquiryReportMap.put("referID", referID);
					// 询价时间
					String referTime = element.attributeValue("referTime");
					enquiryReportMap.put("referTime", referTime);
					// 回复时间
					String replyTime = element.attributeValue("replyTime");
					enquiryReportMap.put("replyTime", replyTime);
					Element resultElt = element.element("result");
					if (resultElt != null) {
						Element itemElt = resultElt.element("item");
						if (itemElt != null) {
							// 询价公司内部流水号
							String pgsqID = itemElt.elementTextTrim("pgsqID");
							enquiryReportMap.put("pgsqID", pgsqID);
							// 物业名称(必填项，字符型长度100)
							String projectName = itemElt
									.elementTextTrim("projectName");
							enquiryReportMap.put("propertyName", projectName);
							// 栋号（必填项，字符型长度50）
							String buildingName = itemElt
									.elementTextTrim("buildingName");
							enquiryReportMap.put("buildingName", buildingName);
							// 房号（必填项，字符型长度50）
							String houseName = itemElt
									.elementTextTrim("houseName");
							enquiryReportMap.put("houseName", houseName);
							// 建筑面积（必填项，2为小数）
							double buildingArea = NumberUtils.toDouble(
									itemElt.elementTextTrim("buildingArea"), 0);
							enquiryReportMap.put("buildingArea", buildingArea+"");
							// 评估单价
							double unitPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("unitPrice"), 0);
							enquiryReportMap.put("unitPrice", unitPrice+"");
							// 评估总价
							double totalPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("totalPrice"), 0);
							enquiryReportMap.put("totalPrice", totalPrice+"");
							// 预计税费
							double tax = NumberUtils.toDouble(
									itemElt.elementTextTrim("tax"), 0);
							enquiryReportMap.put("tax", tax+"");
							// 评估净值
							double netPrice = NumberUtils.toDouble(
									itemElt.elementTextTrim("netPrice"), 0);
							enquiryReportMap.put("netPrice", netPrice+"");
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
							enquiryReportMap.put("status", status);
							enquiryReportMap.put("specialMessage", specialMessage);
						}
					}
					poolingEnquiryReportMaps.add(enquiryReportMap);
				}
			}
		} catch (Exception e) {
			log.error("查询同致诚询价结果异常", e);
		}
		return poolingEnquiryReportMaps;
	}
	

	/**
	 * 解析申请评估报文
	 * @param xmlStr
	 * @return assessMap
	 * @return key errCode
	 * @return key errMsg
	 */
	public static Map<String, String> parseTZCEnquiryAssessResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		Map<String, String> assessMap = null;
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
			assessMap = new HashMap<String, String>();
			assessMap.put("errCode", errCode);
			assessMap.put("errMsg", errMsg);
		} catch (Exception e) {
			log.error("解析申请评估报文异常", e);
		}
		return assessMap;
	}

	/*********************************************************世联*******************************************************************/
	
	/**
	 * 解析世联物业名称
	 * 
	 * @param xmlStr
	 * @return List<Map>
	 * @return Map key id   物业名称Id 
	 * @return Map key name 物业名称
	 */
	public static List<Map<String, String>> parseSLConstructionResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> constructionMaps = new ArrayList<Map<String,String>>();
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 获取根节点下的子节点信息
			Iterator<Element> tableIter = rootElt.elementIterator("Table");
			while (tableIter.hasNext()) {
				Element tableElt = tableIter.next();
				String constructionInfo = tableElt
						.elementText("ConstructionId");
				if (StringUtils.isNotEmpty(constructionInfo)) {
					Map<String, String> constructionMap = new HashMap<String, String>();
					String[] item = constructionInfo.split(":");
					String part1 = item[0];
					String part2 = item[1];
					String constructionName = part1.split("\\^")[0];
					String constructionId = part2.split("\\^")[1];
					constructionMap.put("id", constructionId);
					constructionMap.put("name", constructionName);
					constructionMaps.add(constructionMap);
				}
			}
		} catch (Exception e) {
			log.error("解析世联物业名称异常", e);
		}
		return constructionMaps;
	}

	/**
	 * 解析楼栋
	 * 
	 * @param xmlStr
	 * @return Map
	 * @return key id 物业名称Id
	 * @return key rp 
	 * @return key buildings List<Map> 楼栋信息
	 * @return key buildings key id 楼栋Id
	 * @return key buildings key name 楼栋名称 
	 */
	public static Map<String, Object> parseSLBuildingResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Map<String, Object> constructionMap =  new HashMap<String, Object>();
		Document doc = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 楼盘详情
			Element tableElt = rootElt.element("Table");
			if (tableElt == null) {
				return constructionMap;
			}
			constructionMap.put("id", tableElt.elementText("ConstructionId"));
			constructionMap.put("rp", tableElt.elementText("rp"));
			// 楼盘下的楼栋信息
			Iterator<Element> tableIter = rootElt.elementIterator("Table1");
			List<Map<String, String>> buildingMaps = new ArrayList<Map<String, String>>();
			while (tableIter.hasNext()) {
				Element tableEltItem = tableIter.next();
				String buildingid = tableEltItem.elementText("buildingid");
				String buildingname = tableEltItem.elementText("buildingname");
				Map<String, String> buildingMap = new HashMap<String, String>();
				buildingMap.put("id", buildingid);
				buildingMap.put("name", buildingname);
				buildingMaps.add(buildingMap);
			}
			constructionMap.put("buildings",buildingMaps);
		} catch (Exception e) {
			log.error("解析世联楼栋异常", e);
		}
		return constructionMap;
	}

	/**
	 * 解析房号
	 * 
	 * @param xmlStr
	 * @return List<Map>
	 * @return key id 房号Id
	 * @return key name 房号名称
	 */
	public static List<Map<String, String>> parseSLHourseResp(String xmlStr) {
		
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		List<Map<String, String>> houseMaps = new ArrayList<Map<String,String>>();
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 获取根节点下的子节点信息
			Iterator<Element> tableIter = rootElt.elementIterator("Table");
			while (tableIter.hasNext()) {
				Element tableElt = tableIter.next();
				String houseId = tableElt.elementText("houseId");
				String houseName = tableElt.elementText("houseName");
				Map<String, String> houseMap = new HashMap<String, String>();
				houseMap.put("id", houseId);
				houseMap.put("name", houseName);
				houseMaps.add(houseMap);
			}
		} catch (Exception e) {
			log.error("解析世联房号异常", e);
		}
		return houseMaps;
	}

	/**
	 * 解析房号详情
	 * 
	 * @param xmlStr
	 * @return	Map
	 * @return key houseId
	 * @return key buildingtype
	 * @return key purposename
	 * @return key structure
	 * @return key builddate
	 * @return key rp
	 * @return key buildarea
	 * @return key structureid
	 * @return key isautovalue
	 * @return key useryear
	 * @return key buildingid
	 */
	public static Map<String, String> parseSLHouseInfoResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 获取根节点下的子节点信息
			Element tableElt = rootElt.element("Table");
			String houseId = tableElt.elementText("houseId");
			String buildingtype = tableElt.elementText("buildingtype");
			String purposename = tableElt.elementText("purposename");
			String structure = tableElt.elementText("structure");
			String builddate = tableElt.elementText("builddate");
			String rp = tableElt.elementText("rp");
			String buildarea = tableElt.elementText("buildarea");
			String structureid = tableElt.elementText("structureid");
			String isautovalue = tableElt.elementText("isautovalue");
			String useryear = tableElt.elementText("useryear");
			String buildingid = tableElt.elementText("buildingid");
			Map<String, String> roomMap = new HashMap<String, String>();
			roomMap.put("houseId", houseId);
			roomMap.put("buildingtype", buildingtype);
			roomMap.put("purposename", purposename);
			roomMap.put("structure", structure);
			roomMap.put("builddate", builddate);
			roomMap.put("rp", rp);
			roomMap.put("buildarea", buildarea);
			roomMap.put("structureid", structureid);
			roomMap.put("isautovalue", isautovalue);
			roomMap.put("useryear", useryear);
			roomMap.put("buildingid", buildingid);
			return roomMap;
		} catch (Exception e) {
			log.error("解析房号详情", e);
		}
		return null;
	}

	/**
	 * 自动估价
	 * 
	 * @param xmlStr
	 * @return	Map
	 * @return key pricecount
	 * @return key pricemax
	 * @return key pricemin
	 * @return key priceavg
	 */
	public static Map<String, Object> parseSLAutoPriceResp(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Document doc = null;
		Map<String, Object> autoPriceInfoMap = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			// 获取根节点下的子节点信息
			Element tableElt = rootElt.element("Table");
			if (tableElt != null) {
				autoPriceInfoMap = new HashMap<String, Object>();
				int pricecount = NumberUtils.toInt(
						tableElt.elementText("pricecount"), 0);
				double pricemax = NumberUtils.toDouble(
						tableElt.elementText("pricemax"), 0);
				double pricemin = NumberUtils.toDouble(
						tableElt.elementText("pricemin"), 0);
				double priceavg = NumberUtils.toDouble(
						tableElt.elementText("priceavg"), 0);
				autoPriceInfoMap.put("pricecount", pricecount);
				autoPriceInfoMap.put("pricemax", pricemax);
				autoPriceInfoMap.put("pricemin", pricemin);
				autoPriceInfoMap.put("priceavg", priceavg);
				return autoPriceInfoMap;
			}

		} catch (Exception e) {
			log.error("parseAutoPriceResp Exception.", e);
		}
		return null;

	}
}
