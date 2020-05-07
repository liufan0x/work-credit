package com.anjbo.service.tools.impl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.anjbo.bean.enquiry.EnquiryAssessResp;
import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.bean.tools.EnquiryReportDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.FreemarkerHelper;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.TZCEnquiryParseHelper;
import com.anjbo.dao.mort.EnquiryDetailMapper;
import com.anjbo.dao.tools.EnquiryMapper;
import com.anjbo.service.tools.EnquiryService;
import com.anjbo.thirdInterface.tzc.ServiceSoap;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DateUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.NumberUtil;
import com.anjbo.utils.ParseXmlUtil;
import com.anjbo.utils.StringComparator;
import com.anjbo.utils.StringUtil;

/**
 * 询价
 * @author lic
 *
 * @date 2016-6-2 上午10:47:28
 */
@Service
public class EnquiryServiceImpl implements EnquiryService{

	private static final Log log = LogFactory.getLog(EnquiryServiceImpl.class);

	@Resource
	private EnquiryMapper enquiryMapper;
	
	@Resource
	private EnquiryDetailMapper enquiryDetailMapper;
	
	/** 鲁克评估获取tokenUrl */
	private static final String tokenLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api//lkpg/data-api/enquiry/getToken?clientId="
			+ ConfigUtil.getStringValue("LKPG_ID") + "&clientSecret=" + ConfigUtil.getStringValue("LKPG_SECERT");
	
	/** 鲁克评估获取物业Url */
	private static final String propertyLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api//lkpg/data-api/enquiry/selectPropertyList";
	/** 鲁克评估获取楼栋Url */
	private static final String buildingLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/selectBuildingList";
	/** 鲁克评估获取房间Url */
	private static final String housesLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/selectRoomList";
	/** 鲁克评估自动询价Url */
	private static final String autoPriceLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/addAutoEnquiry";
	/** 鲁克评估手动询价Url */
	private static final String handPriceLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/addHandEnquiry";

	/** 鲁克评估获取小区网络报盘Url */
	private static final String networkOfferLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/propertyInfo/networkOffer";
	/** 鲁克评估获取小区市场成交Url */
	private static final String marketBargainLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/propertyInfo/marketBargain";
	/** 鲁克评估获取小区照片Url */
	private static final String propertyImgsLKPGUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/propertyInfo/propertyImgs";
	/** 鲁克评估获取小区数量信息Url */
	private static final String propertyImgAndBargainAndNetworkCountUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/propertyInfo/selectPropertyImgAndBargainAndNetworkCount";
	/** 鲁克评估物业信息反馈Url */
	private static final String propertyFeedBackUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/propertyFeedBack";
	/** 鲁克评估询价反馈Url */
	private static final String enquiryFeedBackUrl = ConfigUtil.getStringValue("LKPG_URL")+"/api/lkpg/data-api/enquiry/enquiryFeedBack";
	
	/**
	 * 查询相同物业，相同楼栋，相同房号同一天查询次数
	 * @user lic
	 * @date 2016-6-3 下午04:15:11 
	 * @param param
	 * @return
	 */
	@Override
	public int selectCountByCondition(EnquiryDto enquiryDto) {
		return enquiryMapper.selectCountByCondition(enquiryDto);
	}

	/**
	 * 增加询价记录
	 * @user lic
	 * @date 2016-6-3 下午05:06:12 
	 * @param param
	 * @throws MortgageException 
	 */
	private void addEnquiry(EnquiryDto enquiryDto) throws MortgageException {
		enquiryMapper.insertEnquiry(enquiryDto);
	}

	/**
	 * 查询询价结果记录Id
	 * @user lic
	 * @date 2016-6-6 下午02:30:25 
	 * @return
	 */
	private Integer reportExit(EnquiryReportDto enquiryReportDto){
		return enquiryMapper.reportExit(enquiryReportDto);
	}

	/**
	 * 新增询价结果记录
	 * @user lic
	 * @date 2016-6-6 下午02:31:41 
	 * @param enquiryReportDto
	 */
	public void insertReport(EnquiryReportDto enquiryReportDto){
		enquiryMapper.insertReport(enquiryReportDto);
	}

	/**
	 * 查询税费记录Id
	 * @user lic
	 * @date 2016-6-6 下午02:51:32 
	 * @param param
	 * @return
	 */
	@Override
	public Integer taxExit(Map<String, Object> param){
		return enquiryMapper.taxExit(param);
	}

	/**
	 * 新增税费记录
	 * @user lic
	 * @date 2016-6-6 下午02:51:56 
	 * @param param
	 */
	@Override
	public void insertTax(Map<String, Object> param){
		enquiryMapper.insertTax(param);
	}

	/**
	 * 根据内部流水查询询价Id
	 * @user lic
	 * @date 2016-11-14 下午04:31:33 
	 */
	@Override
	public Integer selectEnquiryBySerialid(String serialid) {
		return enquiryMapper.selectEnquiryBySerialid(serialid);
	}
	
	/**
	 * 修改询价结果(如果无，则新增询价结果)
	 * @user lic
	 * @date 2016-11-14 下午04:51:27 
	 * @param enquiryReportDto
	 */
	@Override
	public void updateEnquiryReport(EnquiryReportDto enquiryReportDto) {
		Integer reportId = reportExit(enquiryReportDto);
		if(reportId == null){
			insertReport(enquiryReportDto);
		}else{
			enquiryMapper.updateEnquiryReport(enquiryReportDto);
		}
	}

	/**
	 * 询价
	 * @user lic
	 * @date 2016-6-3 下午05:15:31 
	 * @param param
	 * @throws MortgageException 
	 */
	@Override
	public void createEnquiry(EnquiryDto enquiryDto) throws MortgageException {
		if (enquiryDto == null || StringUtils.isEmpty(enquiryDto.getPropertyId())
				|| StringUtils.isEmpty(enquiryDto.getBuildingId()) || StringUtils.isEmpty(enquiryDto.getRoomId())) {
			throw new MortgageException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			
			
			String assessType = enquiryDto.getType();
			EnquiryReportDto enquiryReportDto = new EnquiryReportDto();
			enquiryReportDto.setPropertyName(enquiryDto.getPropertyName());
			enquiryReportDto.setBuildingName(enquiryDto.getBuildingName());
			enquiryReportDto.setRoomName(enquiryDto.getRoomName());
			enquiryReportDto.setArea(enquiryDto.getArea());
			
			String retValue = "";
			if(Constants.TZC_ID.equals(assessType)){
				//同致诚询价
				addEnquiry(enquiryDto);
				retValue = autoPriceTZC(enquiryDto);
				enquiryReportDto.setCompany("同致诚");
			}
			
			if(Constants.GJ_ID.equals(assessType)){
				//国策询价
				retValue = autoPriceGJ(enquiryDto,enquiryReportDto);
				enquiryReportDto.setCompany("国策");
				if(retValue.equals(RespStatusEnum.FAIL.getCode())){
					String msg = "非常抱歉！系统发生了错误，请稍后重试。";
					if(StringUtils.isNotEmpty(enquiryReportDto.getMsg())){
						msg = enquiryReportDto.getMsg();
					}
					throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),msg);
				}
			}
			if(Constants.YPG_ID.equals(assessType)){
				//云评估询价
				retValue = autoPriceYPG(enquiryDto, enquiryReportDto);
				enquiryReportDto.setCompany("云评估");
			}
			
			if(Constants.LKPG_ID.equals(assessType)){
				//鲁克评估询价
				retValue = autoPriceLKPG(enquiryDto, enquiryReportDto);
				enquiryReportDto.setCompany("鲁克评估");
			}

//			if(assessType.indexOf(Constants.SL_ID) > -1){
//				slEnquiry(param);
//			}
			
			//新增询价记录
			if(enquiryDto.getId()==0 && !Constants.TZC_ID.equals(assessType)){
				addEnquiry(enquiryDto);
			}
			enquiryReportDto.setEnquiryId(enquiryDto.getId());
			
			Integer reportId = reportExit(enquiryReportDto);
			if(reportId == null){
				insertReport(enquiryReportDto);
			}
			
			//深圳额外查询同致诚
//			if (Constants.LKPG_ID.equals(assessType) && "深圳".equals(enquiryDto.getCityName())) {
//				try {
//					enquiryDto.setCityId(Enums.CityNameByType.getCodeBygNameAndType(enquiryDto.getCityName(),"2"));
//					String res = autoPriceTZC(enquiryDto);
//					if (RespStatusEnum.SUCCESS.getCode().equals(res)) {
//						EnquiryReportDto newDto = new EnquiryReportDto();
//						newDto.setCompany("同致诚");
//						newDto.setPropertyName(enquiryDto.getPropertyName());
//						newDto.setBuildingName(enquiryDto.getBuildingName());
//						newDto.setRoomName(enquiryDto.getRoomName());
//						newDto.setArea(enquiryDto.getArea());
//						newDto.setEnquiryId(enquiryDto.getId());
//						if(reportExit(newDto) == null){
//							insertReport(newDto);
//						}
//					}
//				} catch (Exception e) {
//					log.info("深圳同致诚询价异常",e);
//				}
//			}
			
		}catch (Exception e) {
			log.info("创建询价异常",e);
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
			"非常抱歉！系统发生了错误，请稍后重试。");
		}
	}
	
	/**
	 * 查询询价列表
	 * @user lic
	 * @date 2016-11-14 下午06:28:56 
	 * @param uid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> param) {
		return enquiryMapper.getList(param);
	}

	/*********************************************************同致诚*******************************************************************/

	@Resource(name = "serviceSoapProxy")
	private ServiceSoap serviceSoap;

	/**
	 * 获取同致诚物业名称
	 * @user lic
	 * @date 2016-6-2 下午04:45:02 
	 * @param keyWord
	 * @return Map
	 * @return key id 物业名称Id
	 * @return key name 物业名称
	 */
	@Override
	public List<Map<String, String>>  getTZCPropertyName(String keyWord,String cityId){
		try {
			String result = serviceSoap.tzcQueryEstate2(keyWord,cityId);
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTZCEstateResp(result);
		} catch (Exception e) {
			log.error("获取同致诚物业名称异常", e);
		}
		return null;
	}

	
	/**
	 * 获取同致诚楼栋名称
	 * @user lic
	 * @date 2016-6-2 下午04:53:20 
	 * @param propertyId
	 * @param buildingKeyWord
	 * @return Map
	 * @return key id 	楼栋名称Id
	 * @return key name 楼栋名称
	 */
	@Override
	public List<Map<String, Object>> getTZCBuilding(String propertyId , String buildingKeyWord, String cityId){
		try {
			String result = serviceSoap.tzcQueryBuilding2(propertyId, buildingKeyWord,cityId);
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTZCBuildingResp(result);
		} catch (RemoteException e) {
			log.error("获取同致诚楼栋名称异常", e);
		}
		return null;
	}

	/**
	 * 获取同致诚房间名称
	 * @user lic
	 * @date 2016-6-2 下午05:06:35 
	 * @param buildingId
	 * @param housesKeyWord
	 * @return Map
	 * @return key id 	房间名称Id
	 * @return key name 房间名称
	 */
	@Override
	public List<Map<String, String>> getTZCHouses(String buildingId, String housesKeyWord, String cityId){
		try {
			String result = serviceSoap.tzcQueryFlag2(buildingId, housesKeyWord, cityId);
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTZCRoomResp(result);
		} catch (RemoteException e) {
			log.error("获取同致诚房间名称异常", e);
		}
		return null;
	}

	/**
	 * 获取同致诚银行
	 * @user lic
	 * @date 2016-6-2 下午05:23:51 
	 * @param bankId
	 * @return Map
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	@Override
	public List<Map<String, String>> getTZCBank(String bankId){
		try {
			String result = serviceSoap.tzcQueryBank(Constants.TZC_USERID,Constants.TZC_PASSWORD, bankId);
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTZCBankResp(result);
		} catch (RemoteException e) {
			log.error("获取同致诚银行异常", e);
		}
		return null;
	}

	/**
	 * 获取同致诚支行银行
	 * @user lic
	 * @date 2016-6-2 下午05:23:51 
	 * @param bankId
	 * @param subBankId
	 * @return Map
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	@Override
	public List<Map<String, String>> getTZCSubBank(String bankId , String subBankId){
		try {
			String result = serviceSoap.tzcQuerySubBank(Constants.TZC_USERID,Constants.TZC_PASSWORD, bankId , subBankId);
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTZCSubBankResp(result);
		} catch (RemoteException e) {
			log.error("获取同致诚支行银行异常", e);
		}
		return null;
	}

	/**
	 * 获取银行客户经理信息
	 * @user lic
	 * @date 2016-6-2 下午05:40:38 
	 * @param managerName		
	 * @param phone
	 * @return Map
	 * @return key bankId        银行id	
	 * @return key bankName      银行名称 
	 * @return key subBankId     支行id 
	 * @return key subBankName   分行名称 
	 * @return key managerId     经理id 
	 * @return key managerName   经理名称 
	 */
	public Map<String, String> getTZCBankManager(String managerName, String phone){
		try {
			String result = serviceSoap.tzcQueryBankManager(
					Constants.TZC_USERID, Constants.TZC_PASSWORD, managerName,
					phone);
			result = StringUtil.getFromBASE64(result, "GBK");
			log.debug("tzcQueryBankManager response=" + result);
			List<Map<String, String>> managerMaps = ParseXmlUtil.parseTZCManagerResp(result);
			if (managerMaps != null && managerMaps.size() > 0) {
				return managerMaps.get(0);
			}
		} catch (Exception e) {
			log.error("获取银行客户经理信息异常", e);
		}
		return null;
	}

	/**
	 * 注册同致诚银行客户经理
	 * @user lic
	 * @date 2016-6-2 下午07:04:46 
	 * @param bankId
	 * @param subBankId
	 * @param managerName
	 * @param phone
	 * @return Map
	 * @return key bankId		银行id	
	 * @return key bankName		银行名称
	 * @return key subBankId	支行id
	 * @return key subBankName	分行名称
	 * @return key managerId	经理id
	 * @return key managerName	经理名称
	 */
	public Map<String, String> regTZCManager(String bankId, String subBankId,String managerName, String phone){
		try {
			String result = serviceSoap.tzcManagerReg(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, bankId, subBankId, managerName,
					phone);
			result = StringUtil.getFromBASE64(result, "GBK");
			List<Map<String, String>> managerMaps = ParseXmlUtil.parseTZCManagerResp(result);
			if (managerMaps != null && managerMaps.size() > 0) {
				return managerMaps.get(0);
			}
		} catch (RemoteException e) {
			log.error("注册同致诚银行客户经理异常", e);
		}
		return null;
	}

	/**
	 * 查询计算同致诚物业税费
	 * @user lic
	 * @date 2016-6-2 下午07:13:43 
	 * @param params
	 * @return Map
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
	public Map<String, Object> getTZCTax(Map<String, String> params){
		try {
			String queryCondition = FreemarkerHelper.getIns().processTemplate(
					"tzcreqTax.ftl", params);
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			String result = serviceSoap.tzcQueryTax(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, queryCondition, "XML");
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parseTaxResp(result);
		} catch (Exception e) {
			log.error("查询计算同致诚物业税费异常", e);
		}
		return null;
	}

	/**
	 * 同致诚自动询价
	 * @user lic
	 * @date 2016-6-3 上午11:31:09 
	 * @param enquiryDto
	 * @return
	 * @throws MortgageException
	 */
	private String autoPriceTZC(EnquiryDto enquiryDto) throws MortgageException{
		String result = "";
		try {
			if(enquiryDto.getRegisterPrice() != 0){
				enquiryDto.setRegisterPrice(enquiryDto.getRegisterPrice()/10000);
			}
			String queryCondition = FreemarkerHelper.getIns().processTemplate("tzcreqEnquiry.ftl", enquiryDto);
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			result = serviceSoap.tzcQueryReport(Constants.TZC_USERID,Constants.TZC_PASSWORD, queryCondition, "XML");
		} catch (Exception e) {
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		Map<String, String> enquiryMap = ParseXmlUtil.parseTZCEnquiryResp(result);
		if(MapUtils.isNotEmpty(enquiryMap)){
			String status = MapUtils.getString(enquiryMap, "status");
			if("1".equals(status)){
				return RespStatusEnum.SUCCESS.getCode();
			}
		}
		return RespStatusEnum.FAIL.getCode();
	}


	/**
	 * 查询同致诚询价结果(同致诚已废弃)
	 * @user lic
	 * @date 2016-6-3 下午02:20:18 
	 * @param params
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
	@Override
	public List<Map<String, String>> getTZCEnquiryResult(Map<String, String> params){
		try {
			String resultCondition = FreemarkerHelper.getIns().processTemplate("tzcreqReport.ftl", params);
			String result = serviceSoap.tzcResultReport(Constants.TZC_USERID,Constants.TZC_PASSWORD, resultCondition, "XML");
			result = StringUtil.getFromBASE64(result, "GBK");
			return ParseXmlUtil.parsePoolingTZCEnquiryResp(result);
		} catch (Exception e) {
			log.error("查询同致诚询价结果异常", e);
		}
		return null;
	}

	/**
	 * 同致诚申请评估
	 * @user lic
	 * @date 2016-6-3 下午02:40:58 
	 * @param params
	 * @return
	 * @throws MortgageException 
	 */
	@Override
	public RespStatus applyAssessTZC(EnquiryAssessDto assess) throws MortgageException{
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		String result = "";
		assess.setUnitName("快鸽按揭");
		assess.setOrganUnitName("深圳公司");
		try {
			String queryCondition = FreemarkerHelper.getIns().processTemplate(
					"tzcLimitApply.ftl", assess);
			log.info("tzcLimitApply request params=" + queryCondition);
			// base64编码
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			result = serviceSoap.tzcLimitApply(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, queryCondition, "XML");
			log.info("tzcLimitApply response=" + result);
		} catch (RemoteException e) {
			// 远程请求异常
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		EnquiryAssessResp assessResp = TZCEnquiryParseHelper
				.parseEnquiryAssessResp(result);
		if (assessResp != null) {
			int status = assessResp.getErrCode();
			if (status == 0) {//正常
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(status+assessResp.getErrMsg());
			} else if(status == 3008){
				resp.setMsg(status+assessResp.getErrMsg());
			}else{
				resp.setMsg(status+assessResp.getErrMsg());
			}
		}
		return resp;
	}

	/**
	 * 同致诚申请评估报告
	 * @user lic
	 * @date 2016-6-3 下午02:55:20 
	 * @param params
	 * @return
	 * @throws MortgageException
	 */
	@Override
	public RespStatus applyAssessReportTZC(Map<String, String> params) throws MortgageException{
		RespStatus resp =  new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg("接口异常");
		String result = "";
		params.put("unitName", "快鸽按揭");
		params.put("organUnitName", "深圳公司");
		try {
			String queryCondition = FreemarkerHelper.getIns().processTemplate("tzcReportApply.ftl", params);
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			result = serviceSoap.tzcWebReportApply(Constants.TZC_USERID,Constants.TZC_PASSWORD, queryCondition, "XML");
		} catch (Exception e) {
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		Map<String , String> assessRespMap = ParseXmlUtil.parseTZCEnquiryAssessResp(result);
		if(MapUtils.isNotEmpty(assessRespMap)){
			String status = MapUtils.getString(assessRespMap, "errCode");
			String errMsg = MapUtils.getString(assessRespMap, "errMsg");
			if("0".equals(status)){
				//成功
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
			}
			resp.setMsg(status+errMsg);
		}
		return resp;
	}

	/*********************************************************世联*******************************************************************/

	/**
	 * 世联询价
	 * @user lic
	 * @date 2016-6-3 下午05:48:31 
	 * @param params
	 */
	public Map<String, Object> slEnquiry(Map<String, Object> params){
		String bankId = MapUtils.getString(params, "bankid");
		if(StringUtils.isEmpty(bankId)){//设置默认银行
			bankId = Constants.BANK_MORTGAGE;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> houseInfoMap = getSLHouseInfo(MapUtils.getString(params, "roomid"), StringUtil.isEmpty(MapUtils.getString(params, "cityid")) ? Constants.DEFAULT_CITYID : MapUtils.getString(params, "cityid"));	//获取房间信息
		params.put("cons_rp", "1");
		params.put("house_rp", MapUtils.getString(houseInfoMap, "rp"));
		Map<String, Object> autoPriceInfoMap = autoPriceSL(params);		//世联自动询价

		// 若返回自动估价结果为0，取楼盘均价对应字段AvagePrice
		double unitPrice = MapUtils.getDoubleValue(autoPriceInfoMap, "priceunit") == 0 ? MapUtils.getDoubleValue(autoPriceInfoMap, "priceavg") : MapUtils.getDoubleValue(autoPriceInfoMap, "priceunit");
		// 总额(元)
		double priceTotal = MapUtils.getDoubleValue(autoPriceInfoMap, "priceTotal");

		//记录世联评估报告
		Map<String, Object> enquiryReportMap = new HashMap<String, Object>();
		enquiryReportMap.put("company", Constants.SL_ID);
		enquiryReportMap.put("enquiryid", MapUtils.getString(params, "id"));
		enquiryReportMap.put("projectName", MapUtils.getString(params, "propertyname"));
		enquiryReportMap.put("buildingName", MapUtils.getString(params, "buildingnum"));
		enquiryReportMap.put("houseName", MapUtils.getString(params, "roomnum"));
		enquiryReportMap.put("buildingArea", MapUtils.getString(params, "area"));
		if(unitPrice == 0 || MapUtils.getDoubleValue(autoPriceInfoMap, "pricecount") == 0){
			//评估失败
			resultMap.put("success", false);
			enquiryReportMap.put("specialMessage", "对不起，系统暂时无法评估此物业");
			enquiryReportMap.put("status", "-2");
			//记录询价结果表
//			Integer reportId = reportExit(enquiryReportMap);
//			if(reportId == null){
//				insertReport(enquiryReportMap);
//			}

		}else{
			//评估成功
			resultMap.put("success", false);
			//单价
			enquiryReportMap.put("unitPrice", NumberUtil.formatDecimal(unitPrice));
			//总价
			enquiryReportMap.put("priceTotal", NumberUtil.formatDecimal(priceTotal));

			Integer reportId = 0;

			if(MapUtils.getIntValue(params, "isGetNetPriceTax") == 1){

				/**
				 * 不计算税费
				 */

//				reportId = reportExit(enquiryReportMap);
//				if(reportId == null){
//					insertReport(enquiryReportMap);
//					reportId = MapUtils.getInteger(enquiryReportMap, "id");
//				}

			}else{

				/**
				 * 计算税费
				 */

				Map<String, String> taxConditionMap = new HashMap<String, String>();
				taxConditionMap.put("buildSize", MapUtils.getString(params, "area"));

				Calendar calendar = Calendar.getInstance();
				if("0".equals(MapUtils.getString(params, "range"))){
					//未满两年
					calendar.add(Calendar.YEAR, -1);
				}else{
					//已满两年
					calendar.add(Calendar.YEAR, -3);
				}
				String d = DateUtil.getNowyyyyMMdd(calendar.getTime());
				taxConditionMap.put("buyDate", d);

				// 评估目的：按揭类、个人企业信贷、消费贷、小微抵押授信(是否消费贷)
				taxConditionMap.put("judgeFor", MapUtils.getIntValue(params, "consumerloans") == 1 ? "2" : "0");
				// 物业类型 住宅、非住宅、土地
				taxConditionMap.put("property", "住宅");
				// 登记价：浮点数 单位 万元
				taxConditionMap.put("regPrice", MapUtils.getString(params, "price"));
				// 权属类型：个人、企业
				taxConditionMap.put("rightType", StringUtils.defaultString(MapUtils.getString(params, "obligee"),"个人"));
				// 评估总值：浮点数 单位 万元
				// 总额(万元)
				taxConditionMap.put("totalPrice", (priceTotal / 10000.0) + "");
				enquiryReportMap.put("status", "1");

				Double tax=null;// 税费(单位:元)
				Double netprice=null;// 净值(单位:万元)
				taxConditionMap.put("bankId", bankId);

				Map<String, Object> taxMap = getTZCTax(taxConditionMap);
				if(MapUtils.isNotEmpty(taxMap)){
					tax = MapUtils.getDouble(taxMap, "tax");
					netprice = MapUtils.getDouble(taxMap, "netprice");
				}else if("2".equals(MapUtils.getString(taxConditionMap, "judgeFor"))){
					taxConditionMap.put("judgeFor", "0");
					taxMap = getTZCTax(taxConditionMap);
					if(MapUtils.isNotEmpty(taxMap)){
						tax = MapUtils.getDouble(taxMap, "tax");
						netprice = MapUtils.getDouble(taxMap, "netprice");
					}
				}

				if(MapUtils.isNotEmpty(taxMap)){
					if(tax != null){
						enquiryReportMap.put("tax", tax);
					}
					if(netprice != null){
						double d_netPrice = netprice * 10000;
						double sffive = d_netPrice * 1.05 * 0.7;// 净值上浮5%
						double sften = d_netPrice * 1.1 * 0.7;// 净值上浮10%
						enquiryReportMap.put("netPrice", d_netPrice);
						enquiryReportMap.put("sffive", sffive);
						enquiryReportMap.put("sften", sften);
					}

					//记录询价结果表
//					reportId = reportExit(enquiryReportMap);
//					if(reportId == null){
//						insertReport(enquiryReportMap);
//						reportId = MapUtils.getInteger(enquiryReportMap, "id");
//					}

					//插入税费详情
					taxMap.put("bankId", bankId);
					taxMap.put("reportId", reportId);
					Integer taxId = taxExit(taxMap);
					if(taxId == null){
						insertTax(taxMap);
					}

				}
			}

			try {
				//更新世联评估总价
				Map<String, Object> enquiryMap = new HashMap<String, Object>();
				enquiryMap.put("id", reportId);
				enquiryMap.put("sltotalprice", priceTotal);
				enquiryMapper.updateSlTotalPrice(enquiryMap);
			} catch (Exception e) {
				log.error("更新世联评估总价异常",e);
			}

		}
		resultMap.put("priceTotal", priceTotal);
		return resultMap;
	}

	// 世联登陆地址
	private static String loginUrl = "http://evs.worldunion.cn/Login.aspx";

	// 世联小区,楼栋,房号服务地址
	private static String autoPriceSvr = "http://evs.worldunion.cn/QueryPriceManagement/AutoPrice.ashx";

	/**
	 * 获取世联物业信息
	 * @user lic
	 * @date 2016-6-2 上午10:54:00 
	 * @param keyWord
	 * @param cityId
	 * @return List<Map>            
	 * @return Map key id   物业名称Id  
	 * @return Map key name 物业名称    
	 */
	@Override
	public List<Map<String, String>> getSLPropertyName(String keyWord, String cityId){
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keyWord.length(); i++) {
			int code = keyWord.codePointAt(i);			//返回Unicode代码点
			sb.append(code).append(",");
		}
		params.put("CityId", cityId);
		params.put("type", "auto_searchCon");
		params.put("contxt", sb.toString());
		try {
			String result = httpPostForLogin(params);
			if(StringUtil.isNotEmpty(result)){
				return ParseXmlUtil.parseSLConstructionResp(result);	//解析XML返回物业信息
			}
		} catch (Exception e) {
			log.error("获取世联物业信息异常", e);
		}
		return null;
	}


	/**
	 * 获取世联楼栋信息
	 * @user lic
	 * @date 2016-6-2 下午02:52:10 
	 * @param propertyId
	 * @param cityId
	 * @return Map                           
	 * @return key id 物业名称Id                 
	 * @return key rp                        
	 * @return key buildings List<Map> 楼栋信息  
	 * @return key buildings key id 楼栋Id     
	 * @return key buildings key name 楼栋名称   
	 */
	@Override
	public Map<String, Object> getSLBuilding(String propertyId , String cityId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("CityId", cityId);
		params.put("type", "auto_getConInfo");
		params.put("conid", propertyId);
		try {
			String result = httpPostForLogin(params);
			if (StringUtils.isNotEmpty(result)) {
				return ParseXmlUtil.parseSLBuildingResp(result);		//解析XML返回楼栋信息
			}
		} catch (Exception e) {
			log.error("获取世联楼栋信息异常", e);
		}
		return null;
	}

	/**
	 * 获取世联房号信息
	 * @user lic
	 * @date 2016-6-2 下午02:55:32 
	 * @param buildingId
	 * @param cityId
	 * @return Map
	 * @return key id 房号Id  
	 * @return key name 房号名称
	 */
	@Override
	public List<Map<String, String>> getSLHouses(String buildingId , String cityId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("CityId", cityId);
		params.put("type", "auto_getHouseList");
		params.put("BuildingId", buildingId);
		try {
			String result = httpPostForLogin(params);
			if (StringUtils.isNotEmpty(result)) {
				return ParseXmlUtil.parseSLHourseResp(result);		//解析XML返回房间信息
			}
		} catch (Exception e) {
			log.error("获取世联房号信息", e);
		}
		return null;
	}

	/**
	 * 获取世联房间详细信息
	 * @user lic
	 * @date 2016-6-2 下午03:05:10 
	 * @param houseId
	 * @param cityId
	 * @return Map
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
	@Override
	public Map<String, String> getSLHouseInfo(String houseId , String cityId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("CityId", cityId);
		params.put("type", "auto_getHouseInfo");
		params.put("HouseId", houseId);
		String result = "";
		try {
			result = httpPostForLogin(params);
			if (StringUtils.isNotEmpty(result)) {
				return ParseXmlUtil.parseSLHouseInfoResp(result);	//解析XML返回房间详细信息	
			}
		} catch (Exception e) {
			log.error("获取世联房间详细信息异常", e);
		}
		return null;
	}

	/**
	 * 世联自动询价
	 * @user lic
	 * @date 2016-6-2 下午03:16:40 
	 * @param params
	 * @param key cityId 		城市Id
	 * @param key propertyId	物业Id
	 * @param key buildingArea	面积
	 * @param key buildingId	楼栋Id
	 * @param key cons_rp		物业rp值
	 * @param key room_rp		房号rp值
	 * @param key propertyuse	物业用途
	 * @return autoPriceInfoMap
	 * @return key pricecount	案例数量
	 * @return key pricemax  	案例最大值
	 * @return key pricemin  	案例最小值
	 * @return key priceavg  	案列平均值
	 * @return key priceunit	单价
	 * @return key pricetotal	总价
	 */
	@Override
	public Map<String, Object> autoPriceSL(Map<String, Object> params){
		Map<String, String> param = new HashMap<String, String>();
		// 城市id
		param.put("CityId", MapUtils.getString(params, "cityId"));
		// 楼盘id
		param.put("conid", MapUtils.getString(params, "propertyId"));
		// 楼栋id
		param.put("BuildingId", MapUtils.getString(params, "buildingId"));
		// 住宅类型(普通住宅:1,2,22,26,27,38,非普通住宅:3,4,28,29,30,33,34,35,40)
		String purposeId = "1,2,22,26,27,38";
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "propertyuse")) && !"0".equals(MapUtils.getString(params, "propertyuse"))) {
			purposeId = "3,4,28,29,30,33,34,35,40";
		}
		param.put("PurposeId", purposeId);
		//
		param.put("PriceSourceId", "1,2,5,6,11,13,14,15,16");
		// 面积
		param.put("BuildingArea", MapUtils.getString(params, "buildingArea"));
		// 案例开始时间
		param.put("SDate", DateUtil.getDayBeforeDate(-30, DateUtil.FMT_TYPE2));
		// 案例结束时间
		param.put("EDate",
				DateUtil.getDateByFmt(new Date(), DateUtil.FMT_TYPE2));
		// 请求类型
		param.put("type", "auto_getPrice");
		try {
			String result = httpPostForLogin(param);
			// 解析xml
			if (StringUtils.isNotEmpty(result)) {
				Map<String, Object> autoPriceInfoMap = ParseXmlUtil.parseSLAutoPriceResp(result);
				if (autoPriceInfoMap != null) {
					// 单价=楼盘RP值*房号RP值*案例平均值
					// 楼盘RP值
					double d_cons_rp = NumberUtils.toDouble(MapUtils.getString(params, "cons_rp"), 0);
					if (d_cons_rp < 0.01) {
						d_cons_rp = 1.0;
					}
					// 房号RP值
					double d_room_rp = NumberUtils.toDouble(MapUtils.getString(params, "room_rp"), 1);
					// 案例平均值
					double avgprice =  NumberUtils.toDouble(MapUtils.getString(autoPriceInfoMap, "priceavg"));
					// 单价
					double unitPrice = Math.ceil(d_cons_rp * d_room_rp * avgprice);
					// 总价=单价*建筑面积
					double d_buildingArea = NumberUtils.toDouble(MapUtils.getString(params, "buildingArea"),0);
					// 总价
					double totalPrice = NumberUtil.formatDecimal(unitPrice * d_buildingArea);
					autoPriceInfoMap.put("priceunit", (NumberUtil.formatDecimal(unitPrice)));
					autoPriceInfoMap.put("pricetotal", totalPrice);
					return autoPriceInfoMap;
				}
			}
		} catch (Exception e) {
			log.error("世联自动询价异常", e);
		}
		return null;
	}



	/**
	 * 登录并查询数据
	 * @user lic
	 * @date 2016-6-2 上午11:05:04 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String httpPostForLogin(Map<String, String> params) throws Exception{
		params.put("requested", "XMLHttpRequest");
		params.put("referer", "http://evs.worldunion.cn/QueryPriceManagement/AutoPriceMain.aspx");
		params.put("cookieValue", login(false));//获取缓存的cookie信息
		String result = HttpUtil.post(autoPriceSvr, params);
		if(StringUtils.isEmpty(result)){
			params.put("cookieValue", login(true));//重新获取登录信息
			result = HttpUtil.post(autoPriceSvr, params);
		}
		return result;
	}

	/**
	 * 登录到世联网站
	 * @user lic
	 * @date 2016-6-2 上午11:02:50 
	 * @param reLogin
	 * @return
	 */
	private static String login(boolean reLogin){
		Map<String, String> params = new HashMap<String, String>();
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", "/wEPDwULLTEzMzcxOTEyMDgPZBYCAgEPZBYCAgkPDxYEHgRUZXh0BTo8c3BhbiBzdHlsZT0nY29sb3I6UmVkJz7nlKjmiLflkI3miJblr4bnoIHplJnor6/vvIE8L3NwYW4+HgdWaXNpYmxlZ2RkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQxJbWFnZUJ1dHRvbjIc8Engv536Qf0dlkmR9YESgWYC+00VAUYHkJdnvQ1JmA==");
		params.put("__VIEWSTATEGENERATOR", "C2EE9ABB");
		params.put("__EVENTVALIDATION", "/wEdAAQz4rvVSup2JsEOBi7Z8u2DDlm4ViRSj8IOmM+vzfHmfXY2+Mc6SrnAqio3oCKbxYZizVftpmYJzgJTRuQ1U4Nbg/rMR8W+8gRxSOODr2bX3TR5HhwsuXjUdjpJWPSSvYo=");
		params.put("txtUserId", "szwxgw001");
		params.put("txtPassword", "234362597Jamin");
		params.put("ImageButton2.x", "54");
		params.put("ImageButton2.y", "24");
		try {
			return HttpUtil.getCookieForPost(loginUrl, params, reLogin);
		} catch (Exception e) {
			log.error("getConstruction Exception.", e);
		}
		return null;
	}

	@Override
	public EnquiryDto selectEnquiry(Integer id) {
		return enquiryMapper.selectEnquiry(id);
	}
	
	@Override
	public List<Map<String, Object>> selectEnquiryReportList(int id) {
		return enquiryMapper.selectEnquiryReportList(id);
	}

	/*********************************************************云评估*******************************************************************/

    /**
     * 发送请求云评估返回数据
     * @user Object
     * @date 2016-11-11 下午03:50:58 
     * @param obj
     * @param url
     * @return
     */
    private String sendRequest(Object obj,String url) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String timestamp = System.currentTimeMillis()+"";
    	String uuid = UUID.randomUUID()+"";
    	String appId = ConfigUtil.getStringValue("YPG_APPID");
    	String appKey = ConfigUtil.getStringValue("YPG_APPKEY");
        String[] arr = new String[]{appId, uuid, timestamp + "", appKey};
        Arrays.sort(arr, new StringComparator());
        String codeStr = "";
        for (int i = 0; i < arr.length; i++) {
            codeStr += arr[i];
        }
        String signature = MD5Utils.MD5EncodeUTF(codeStr);
    	params.put("body", obj);
    	params.put("appId",appId);
    	params.put("appKey", appKey);
    	params.put("timestamp",timestamp);
    	params.put("uuid",uuid);
    	params.put("signature",signature);
    	 try {
 			return HttpUtil.post(url, JSONObject.fromObject(params)+"");
 		} catch (Exception e) {
 			log.info("发送云评估失败",e);
 			return null;
 		}
    }
	
	/**
	 * 获取云评估物业名称
	 * @user Object
	 * @date 2016-11-10 下午03:47:38 
	 * @param keyWord
	 * @param cityId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getYPGPropertyName(String keyWord,String cityId) throws ClientProtocolException, IOException{
		String url = ConfigUtil.getStringValue("YPG_URL")+"/property/project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", 0);
		params.put("items", 20);
		params.put("key", keyWord);
		params.put("cityId", cityId);
		JSONObject result = JSONObject.fromObject(sendRequest(params,url).replace("projectId", "id").replace("projectName", "name"));
		if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
			return JSONArray.toList(JSONArray.fromObject(result.get("body")),new HashMap<String, String>(),new JsonConfig());
		}else{
			return null;
		}
	}
	
	/**
	 * 获取云评估楼栋
	 * @user Object
	 * @date 2016-11-10 下午03:47:27 
	 * @param houseDataId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getYPGBuilding(String propertyId,String cityId) throws ClientProtocolException, IOException{
		String url = ConfigUtil.getStringValue("YPG_URL")+"/property/building";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId",propertyId);
		params.put("cityId", cityId);
		JSONObject result = JSONObject.fromObject(sendRequest(params,url).replace("", ""));
		if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
			return JSONArray.toList(JSONArray.fromObject(result.get("body")),new HashMap<String, String>(),new JsonConfig());
		}else{
			return null;
		}
	}
	
	/**
	 * 获取云评估房号
	 * @user Object
	 * @date 2016-11-10 下午03:47:19 
	 * @param houseBuildingId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getYPGHouses(String houseBuildingId,String cityId) throws ClientProtocolException, IOException{
		String url = ConfigUtil.getStringValue("YPG_URL")+"/property/house";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buildingId",houseBuildingId);
		params.put("cityId", cityId);
		JSONObject result = JSONObject.fromObject(sendRequest(params,url).replace("", ""));
		if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
			return JSONArray.toList(JSONArray.fromObject(result.get("body")),new HashMap<String, String>(),new JsonConfig());
		}else{
			return null;
		}
	}
	
	/**
	 * 云评估自动询价
	 * @user Object
	 * @date 2016-11-10 下午03:46:58 
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String autoPriceYPG(EnquiryDto enquiryDto,EnquiryReportDto enquiryReportDto) throws ClientProtocolException, IOException{
    	String url = ConfigUtil.getStringValue("YPG_URL")+"/enquiry/auto_evaluate";
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("serialNo", enquiryDto.getSerialid());
    	params.put("areaId", 0);
    	params.put("projectId", enquiryDto.getPropertyId());
    	params.put("projectName", enquiryDto.getPropertyName());
    	params.put("buildingId", enquiryDto.getBuildingId());
    	params.put("buildingName", enquiryDto.getBuildingName());
    	params.put("houseId", enquiryDto.getRoomId());
    	params.put("houseName", enquiryDto.getRoomName());
    	params.put("buildArea", enquiryDto.getArea());
    	params.put("cityId", enquiryDto.getCityId());
    	JSONObject result = JSONObject.fromObject(sendRequest(params,url).replace("", ""));
    	if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
    		result = JSONObject.fromObject(result.get("body"));
    		enquiryReportDto.setUnitPrice(result.getDouble("unitPrice"));
    		enquiryReportDto.setTotalPrice(result.getDouble("totalPrice"));
			enquiryReportDto.setStart(1);
    		return RespStatusEnum.SUCCESS.getCode();
    	}else{
			enquiryReportDto.setStart(2);
    		return RespStatusEnum.FAIL.getCode();
    	}
	}


	/*********************************************************国策********************************************************************/

	public static void main(String[] args) throws Exception {
		
		RedisOperator.delete("lkpg_token");
//		
//		EnquiryDto enquiryDto = new EnquiryDto();
//		enquiryDto.setPropertyId("2016050800001");
//		enquiryDto.setPropertyName("桃源盛世园");
//		enquiryDto.setBuildingId("2016050800335");
//		enquiryDto.setBuildingName("10#楼");
//		enquiryDto.setRoomId("20160500653148");
//		enquiryDto.setRoomName("1单元11A");
//		enquiryDto.setArea(137.71);
//		enquiryDto.setIsGetNetPriceTax(0);
//		enquiryDto.setCityId("1");
//		enquiryDto.setCityName("深圳");
//		enquiryDto.setRange("0");
//		enquiryDto.setObligee("个人");
//		enquiryDto.setSerialid(UUID.randomUUID()+"");
//		enquiryDto.setCurrentFloor("11");
//		EnquiryReportDto enquiryReportDto = new EnquiryReportDto();
//		EnquiryServiceImpl a  = new EnquiryServiceImpl();

//		System.out.println(a.getTZCPropertyName("s", "1"));
//		String url = ConfigUtil.getStringValue("YPG_URL")+"/district/province";
//		Map<String, Object> params = new HashMap<String, Object>();
//		System.out.println(a.sendRequest(null,url));
//		JSONObject result = JSONObject.fromObject(a.sendRequest(null,url).replace("", ""));
//		if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
//			List<Map<String, Object>> array = JSONArray.toList(JSONArray.fromObject(result.get("body")),new HashMap<String, Object>(),new JsonConfig());
//			for (Map<String, Object> map : array) {
//				url = ConfigUtil.getStringValue("YPG_URL")+"/district/city";
//				params.put("provinceId",map.get("provinceId"));
//				result = JSONObject.fromObject(a.sendRequest(params,url).replace("", ""));
//				if(result != null && NumberUtils.toInt(result.get("code")+"") == 0){
//					array = JSONArray.toList(JSONArray.fromObject(result.get("body")),new HashMap<String, Object>(),new JsonConfig());
//					for (Map<String, Object> map1 : array) {
//						if(a.getYPGPropertyName("",map1.get("cityId").toString()).size()>0){
//							System.out.println(map1.get("cityId").toString()+"|"+map1.get("cityName").toString());
//						}
//					}
//				}
//			}
//		}
//		云评估测试
//		System.out.println(a.getYPGPropertyName("中铁花园","110").size());
//		System.out.println(a.getYPGBuilding("1F004D07732091D2","8"));
//		System.out.println(a.getYPGHouses("35217DE274E87FBB","110"));
//		a.autoPriceYPG(enquiryDto, enquiryReportDto);
		
		
//		国策测试
//		System.out.println(a.getGJPropertyName("ty",enquiryDto.getCityId()));
//		System.out.println(a.getGJBuilding("2016050800001",enquiryDto.getCityId()));
//		System.out.println(a.getGJHouses("2016050800335",enquiryDto.getCityId()));
//		System.out.println(a.getGJHousesData("20160500653148",enquiryDto.getCityId()));
//		a.autoPriceGJ(enquiryDto, enquiryReportDto);
		
		
	}


	/**
	 * 国策切换城市
	 * @user Object
	 * @date 2016-11-10 下午01:52:49 
	 * @param cityId
	 * @param cookieStore
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static void changeCity(String cityId,BasicCookieStore cookieStore) throws ClientProtocolException, IOException{
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost city = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/home/index.shtml");
		city.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		city.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		city.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		city.setHeader("Cache-Control", "max-age=0");
		city.setHeader("Connection", "keep-alive");
		city.setHeader("Host", "gj.guocedc.cn");
		city.setHeader("Upgrade-Insecure-Requests", "1");
		city.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36");
		ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
		postData.add(new BasicNameValuePair("cityid", cityId));  
		city.setEntity(new UrlEncodedFormEntity(postData));
		client.execute(city);
	}

	/**
	 * 国策登录
	 * @user Object
	 * @date 2016-11-10 下午01:53:13 
	 * @param cookieStore
	 * @return
	 */
	@SuppressWarnings("restriction")
	private static Cookie gjLogin(){
		BasicCookieStore cookieStore = new BasicCookieStore();
		try {
			Cookie cookie = (Cookie) RedisOperator.get("gjCookie");
			if(cookie != null){
				System.out.println("已登录：无需再次登录");
				return cookie;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cookie cookieValue = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		BufferedInputStream is = null;
		try {
			HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			//访问登录页
			HttpGet getLoginPage = new HttpGet(ConfigUtil.getStringValue("GJ_URL")+"/home/login.shtml");
			client.execute(getLoginPage);

			byte[] data = null;
			String code = "";
			//获取验证码
			try {
				HttpGet getVerifyCodeImg = new HttpGet(ConfigUtil.getStringValue("GJ_URL")+"/backendhome/authenticationcode.shtml?timestamp="+new Date().getTime());
				response = client.execute(getVerifyCodeImg);
				entity = response.getEntity();
				is=new BufferedInputStream(new DataInputStream(entity.getContent()));
				data = new byte[102400];
				is.read(data);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				is.close();
			}
			//自动识别验证码
			HttpPost getVerifyCode = new HttpPost("http://twx.anjbo.com/mortgage/ocr/start");
			String base64EncodingImg=new BASE64Encoder().encode(data); 
			ArrayList<NameValuePair> verifyCodeData = new ArrayList<NameValuePair>();  
			verifyCodeData.add(new BasicNameValuePair("imageByte", base64EncodingImg));  
			getVerifyCode.setEntity(new UrlEncodedFormEntity(verifyCodeData));
			response = client.execute(getVerifyCode);
			result = EntityUtils.toString(response.getEntity(), "gbk");
			code = JSONObject.fromObject(result).get("data")+"";
			System.out.println("验证码："+result);
			//登录
			HttpPost login = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Home/DoLogin");
			login.setHeader("ajax", "true");

			ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
			postData.add(new BasicNameValuePair("VipCode", ConfigUtil.getStringValue("GJ_CODE")));  
			postData.add(new BasicNameValuePair("VipPwd",  ConfigUtil.getStringValue("GJ_PWD")));
			postData.add(new BasicNameValuePair("VerifyCode", code));
			login.setEntity(new UrlEncodedFormEntity(postData));
			response = client.execute(login);
			result = EntityUtils.toString(response.getEntity(), "gbk");
			System.out.println("登录结果:"+result);
			for (Cookie cookie : cookieStore.getCookies()) {
				cookieValue = cookie;
			}
			if(NumberUtils.toInt(JSONObject.fromObject(result).get("status")+"") == 1){
				changeCity("1", cookieStore);
			}else{
//				cookieValue = null;
//				while (cookieValue==null) {
//					cookieValue = gjLogin();
//				}
			}
			RedisOperator.set("gjCookie", cookieValue,60*60*24);	//一天失效时间
			return cookieValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取国策物业名称
	 * @user Object
	 * @date 2016-11-10 下午03:09:56 
	 * @param keyWord
	 * @param cityId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getGJPropertyName(String keyWord,String cityId) throws ClientProtocolException, IOException{
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(gjLogin());
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet propertyName = new HttpGet(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/GetHouseData?q="+keyWord+"&cityId="+cityId);
		HttpResponse response = client.execute(propertyName);
		String result = EntityUtils.toString(response.getEntity(), "gbk");
		result = result.replace("\"Id\"", "\"id\"").replace("EstateName", "name");
		List<Map<String, String>> retList = new ArrayList<Map<String,String>>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(result);
			retList = JSONArray.toList(jsonArray,new HashMap<String, String>(),new JsonConfig());
		} catch (Exception e) {
			RedisOperator.delete("gjCookie");
//			retList = getGJPropertyName(keyWord, cityId);
		}
		return retList;
	}


	/**
	 * 获取国策楼栋
	 * @user Object
	 * @date 2016-11-10 下午03:13:55 
	 * @param houseDataId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGJBuilding(String houseDataId,String cityId) throws ClientProtocolException, IOException{
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(gjLogin());
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost building = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/GetBuildingNo");
		ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
		postData.add(new BasicNameValuePair("houseDataId", houseDataId));  
		building.setEntity(new UrlEncodedFormEntity(postData));
		HttpResponse response = client.execute(building);
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		try {
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(response.getEntity(), "gbk").replace("\"Id\"", "\"id\"").replace("BuildingNo", "name"));
			if(NumberUtils.toInt(result.get("status")+"")==1){
				retList = JSONArray.toList(result.getJSONArray("data"),new HashMap<String, String>(),new JsonConfig());
			}else{
				RedisOperator.delete("gjCookie");
				retList = getGJBuilding(houseDataId, cityId);
			}
		} catch (Exception e) {
			RedisOperator.delete("gjCookie");
//			retList = getGJBuilding(houseDataId, cityId);
		}
		return retList;
	}

	/**
	 * 获取国策房号
	 * @user Object
	 * @date 2016-11-10 下午03:36:35 
	 * @param houseBuildingId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getGJHouses(String houseBuildingId,String cityId) throws ClientProtocolException, IOException{
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(gjLogin());
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost houses = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/GetRoomNo");
		ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
		postData.add(new BasicNameValuePair("houseBuildingId", houseBuildingId));  
		houses.setEntity(new UrlEncodedFormEntity(postData));
		HttpResponse response = client.execute(houses);
		List<Map<String, String>> retList = new ArrayList<Map<String,String>>();
		try {
			JSONObject result = JSONObject.fromObject(EntityUtils.toString(response.getEntity(),"gbk").replace("\"Id\"", "\"id\"").replace("RoomNo", "name").replace("Gfa", "BuildArea"));
			if(NumberUtils.toInt(result.get("status")+"")==1){
				retList = JSONArray.toList(result.getJSONArray("data"),new HashMap<String, String>(),new JsonConfig());
			}else{
				RedisOperator.delete("gjCookie");
				retList = getGJHouses(houseBuildingId, cityId);
			}
		} catch (Exception e) {
			RedisOperator.delete("gjCookie");
//			retList = getGJHouses(houseBuildingId, cityId);
		}
		return retList;
	}

	/**
	 * 获取国策房间信息
	 * @user Object
	 * @date 2016-11-10 下午03:36:53 
	 * @param houseRoomId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Map<String, String> getGJHousesData(String houseRoomId,String cityId) throws ClientProtocolException, IOException{
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(gjLogin());
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost houses = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/GetRoomData");
		ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
		postData.add(new BasicNameValuePair("houseRoomId", houseRoomId));  
		houses.setEntity(new UrlEncodedFormEntity(postData));
		HttpResponse response = client.execute(houses);
		JSONObject result = JSONObject.fromObject(EntityUtils.toString(response.getEntity(),"gbk").replace("\"Id\"", "\"id\"").replace("RoomNo", "name"));
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			if(NumberUtils.toInt(result.get("status")+"")==1){
				JSONObject json = JSONObject.fromObject(result).getJSONObject("data");
				retMap.put("BuildArea", json.getString("Gfa")==null ? "" : json.getString("Gfa"));
				retMap.put("currentFloor", json.getString("CurrentFloor")==null ? "" : json.getString("CurrentFloor"));
			}else{
				RedisOperator.delete("gjCookie");
				getGJHousesData(houseRoomId, cityId);
			}
		} catch (Exception e) {
			RedisOperator.delete("gjCookie");
//			getGJHousesData(houseRoomId, cityId);
		}
		return retMap;
	}
	
	/**
	 * 国策询价
	 * @user Object
	 * @date 2016-11-11 下午02:42:39 
	 * @param enquiryDto
	 * @param enquiryReportDto
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String autoPriceGJ(EnquiryDto enquiryDto, EnquiryReportDto enquiryReportDto) throws ClientProtocolException, IOException{
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(gjLogin());
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost houses = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/DoAuto");
		ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();  
		postData.add(new BasicNameValuePair("EstateName", enquiryDto.getPropertyName())); 
		postData.add(new BasicNameValuePair("HouseDataId",  enquiryDto.getPropertyId()));    
		postData.add(new BasicNameValuePair("DistrictName",  ""));
		postData.add(new BasicNameValuePair("BuildingNo",  enquiryDto.getBuildingName()));  
		postData.add(new BasicNameValuePair("HouseBuildingId",  enquiryDto.getBuildingId()));  
		postData.add(new BasicNameValuePair("CurrentFloor",  enquiryDto.getCurrentFloor()));  
		postData.add(new BasicNameValuePair("RoomName",  enquiryDto.getRoomName()));  
		postData.add(new BasicNameValuePair("HouseRoomId",  enquiryDto.getRoomId()));  
		postData.add(new BasicNameValuePair("Gfa",  enquiryDto.getArea()+"")); 
		postData.add(new BasicNameValuePair("IsComputeTax", enquiryDto.getIsGetNetPriceTax() == 1 ? "0" : "1")); 
		if(enquiryDto.getRange()!=null){
			postData.add(new BasicNameValuePair("BuyYears", enquiryDto.getRange().equals("1") ? "6" : "4" )); 
		}else{
			postData.add(new BasicNameValuePair("BuyYears", "4" )); 
		}
		postData.add(new BasicNameValuePair("ResidentialType", "普通")); 
		postData.add(new BasicNameValuePair("Purpose", "抵押贷款")); 
		postData.add(new BasicNameValuePair("CityId",  enquiryDto.getCityId())); 
		postData.add(new BasicNameValuePair("CityName",  enquiryDto.getCityName())); 
		postData.add(new BasicNameValuePair("OriginalSum",  Double.toString(enquiryDto.getRegisterPrice())));
		if(enquiryDto.getObligee()!=null){
			postData.add(new BasicNameValuePair("EquityKind", enquiryDto.getObligee().equals("个人") ? "1" : "2" )); 
		}else{
			postData.add(new BasicNameValuePair("EquityKind", "1")); 
		}

//		国策测试数据
//		HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");  
//	    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();  
//	    houses.setConfig(config);
//		postData.add(new BasicNameValuePair("EstateName", "阳光华艺大厦"));  
//		postData.add(new BasicNameValuePair("HouseDataId", "2016050800010"));  
//		postData.add(new BasicNameValuePair("DistrictName",  "")); 
//		postData.add(new BasicNameValuePair("BuildingNo", "2栋"));  
//		postData.add(new BasicNameValuePair("HouseBuildingId", "2016050800039")); 
//		postData.add(new BasicNameValuePair("CurrentFloor","10"));
//		postData.add(new BasicNameValuePair("RoomName",  "1009"));  
//		postData.add(new BasicNameValuePair("HouseRoomId",  "20160500630134"));  
//		postData.add(new BasicNameValuePair("Gfa",  "37.58")); 
//		postData.add(new BasicNameValuePair("IsComputeTax", "1")); 
//		postData.add(new BasicNameValuePair("BuyYears", "4")); 
//		postData.add(new BasicNameValuePair("ResidentialType", "普通")); 
//		postData.add(new BasicNameValuePair("Purpose", "抵押贷款"));  
//		postData.add(new BasicNameValuePair("CityId",  "1")); 
//		postData.add(new BasicNameValuePair("CityName",  "深圳")); 
//		postData.add(new BasicNameValuePair("OriginalSum",  ""));
//		postData.add(new BasicNameValuePair("EquityKind", "1"));   
		
		houses.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));
		houses.setHeader("ajax", "true");
		houses.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		houses.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		houses.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		houses.setHeader("Cache-Control", "max-age=0");
		houses.setHeader("Connection", "keep-alive");
		houses.setHeader("Host", "gj.guocedc.cn");
		houses.setHeader("Upgrade-Insecure-Requests", "1");
		houses.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36");
		HttpResponse response = client.execute(houses);
		JSONObject json = JSONObject.fromObject(EntityUtils.toString(response.getEntity(), "gbk"));
		System.out.println(json);
		if(NumberUtils.toInt(json.get("status")+"") == 1){
			String data = json.get("data")+"";
			HttpPost housesPrice = new HttpPost(ConfigUtil.getStringValue("GJ_URL")+"/Appraisal/AutoResult/"+data+".shtml");
			String result = EntityUtils.toString(client.execute(housesPrice).getEntity(), "gbk");
			Document html =Jsoup.parse(result);
			Elements el = html.getElementsByClass("div_input").get(1).getElementsByTag("span");
			String unitPrice = el.get(0).text().replace(",", "");
			String totalPrice = el.get(1).text().replace(",", "");
			String tax = el.get(2).text().replace(",", "");
			String netPrice = el.get(3).text().replace(",", "");
			enquiryReportDto.setUnitPrice(Double.parseDouble(unitPrice));
			enquiryReportDto.setTotalPrice(Double.parseDouble(totalPrice));
			if(StringUtil.isNotEmpty(tax)){
				enquiryReportDto.setTax(Double.parseDouble(tax));
			}if(StringUtil.isNotEmpty(netPrice)){
				enquiryReportDto.setNetPrice(Double.parseDouble(netPrice));
			}
    		return RespStatusEnum.SUCCESS.getCode();
		}else{
			enquiryReportDto.setStart(2);
			Object obj = json.get("data");
			enquiryReportDto.setMsg(obj==null?"":obj.toString());
			return RespStatusEnum.FAIL.getCode();
		}
	}

	@Override
	public void createEnquiryDongGuan(EnquiryDto enquiryDto) throws MortgageException {
		if (enquiryDto == null || StringUtils.isEmpty(enquiryDto.getPropertyId())
				|| StringUtils.isEmpty(enquiryDto.getBuildingId()) || StringUtils.isEmpty(enquiryDto.getRoomId())) {
			throw new MortgageException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			String assessType = enquiryDto.getType();
			EnquiryReportDto enquiryReportDto = new EnquiryReportDto();
			enquiryReportDto.setPropertyName(enquiryDto.getPropertyName());
			enquiryReportDto.setBuildingName(enquiryDto.getBuildingName());
			enquiryReportDto.setRoomName(enquiryDto.getRoomName());
			enquiryReportDto.setArea(enquiryDto.getArea());
			
			String minValue = "",maxValue = "";
			double area = enquiryDto.getArea();
			if (0<= area && area <70) {
				minValue = "";
				maxValue = "70";
			} else if (70<= area && area <90) {
				minValue = "70";
				maxValue = "90";
			} else if (90<= area && area <110) {
				minValue = "90";
				maxValue = "110";
			} else if (110<= area && area <120) {
				minValue = "110";
				maxValue = "120";
			} else if (120<= area && area <140) {
				minValue = "120";
				maxValue = "140";
			} else if (140<= area && area <180) {
				minValue = "140";
				maxValue = "180";
			} else {
				minValue = "180";
				maxValue = "";
			}
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("propertyName", enquiryDto.getPropertyName());

			int allMarketCount = enquiryDetailMapper.selectCountByMarketPropertyName(paramMap);
			int allNetworkCount = enquiryDetailMapper.selectCountByNetworkPropertyName(paramMap);
			//全部里没有该物业的数据，那找云估价（物业）
			if (allMarketCount <= 0 && allNetworkCount <= 0) {
				if(Constants.YPG_ID.equals(assessType)){
					System.out.println("=============================执行云估价");
					//云评估询价
					String retValue = autoPriceYPG(enquiryDto, enquiryReportDto);
					enquiryReportDto.setCompany("云评估");
				}
			} else {
				enquiryReportDto.setCompany("云评估");
				System.out.println("=============================执行取网络报盘与市场成交");
				//查询两个的数量（物业&面积）
				paramMap.put("minArea", minValue);
				paramMap.put("maxArea", maxValue);
				int marketCount = enquiryDetailMapper.selectCountByMarketPropertyName(paramMap);
				int networkCount = enquiryDetailMapper.selectCountByNetworkPropertyName(paramMap);
				double marketAvg = 0,networkAvg = 0;
				if (marketCount > 0 && networkCount > 0) {
					//两个都有数据取两个
					marketAvg = enquiryDetailMapper.selectAvgByMarket(paramMap);
					networkAvg  = enquiryDetailMapper.selectAvgByNetwork(paramMap);
				} else if (marketCount > 0 && networkCount <= 0) {
					//只有一个有数据，取一个
					marketAvg = enquiryDetailMapper.selectAvgByMarket(paramMap);
				} else if (marketCount <= 0 && networkCount > 0) {
					//只有一个有数据，取一个
					networkAvg  = enquiryDetailMapper.selectAvgByNetwork(paramMap);
				} else {
					//都没有取全部
					paramMap.remove("minArea");
					paramMap.remove("maxArea");
					if (allMarketCount > 0) {
						marketAvg = enquiryDetailMapper.selectAvgByMarket(paramMap);
					}
					if (allNetworkCount > 0) {
						networkAvg  = enquiryDetailMapper.selectAvgByNetwork(paramMap);
					}
				}
				if (marketAvg > 0 && networkAvg > 0) {
					enquiryReportDto.setUnitPrice(((marketAvg+networkAvg)/2)/area*10000);
		    		enquiryReportDto.setTotalPrice((marketAvg+networkAvg)/2*10000);
				} else {
					enquiryReportDto.setUnitPrice((marketAvg+networkAvg)/area*10000);
		    		enquiryReportDto.setTotalPrice((marketAvg+networkAvg)*10000);
				}
					
			}
			
			//新增询价记录
			if(enquiryDto.getId()==0 && !Constants.TZC_ID.equals(assessType)){
				addEnquiry(enquiryDto);
			}
			enquiryReportDto.setEnquiryId(enquiryDto.getId());
			
			Integer reportId = reportExit(enquiryReportDto);
			if(reportId == null){
				insertReport(enquiryReportDto);
			}
		}catch (Exception e) {
			log.info("创建询价异常",e);
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
			"非常抱歉！系统发生了错误，请稍后重试。");
		}
		
	}

	@Override
	public int deleteEnquiry(int eid) {
		
		int row = enquiryMapper.deleteEnquiryById(eid);
		
		if (row > 0) {
			enquiryMapper.deleteToolsReportByEnquiryId(eid);
		}
		
		return row;
	}

	@Override
	public RespPageData<Map<String, Object>> enqueryReportListByProperty(Map<String, Object> params) {
		RespPageData<Map<String, Object>> page = new RespPageData<Map<String, Object>>();
		page.setCode(RespStatusEnum.SUCCESS.getCode());
		page.setMsg(RespStatusEnum.SUCCESS.getMsg());
		int count = enquiryMapper.enqueryReportCountByProperty(params);
		page.setMyAllCount(count);
		if(count == 0){
			page.setData(Collections.<Map<String, Object>>emptyList());
			return page;
		}
		List<Map<String,Object>> list = enquiryMapper.enqueryReportListByProperty(params);
		page.setData(list);
		return page;
	}

	
	/*********************************************************鲁克评估*******************************************************************/

	/**
	 * 获取token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getLKPGToken() throws Exception{
		Map<String, String> resMap = new HashMap<String, String>();
		String token = (String) RedisOperator.get("lkpg_token");
		String md5Key = (String) RedisOperator.get("lkpg_md5_key");
		if(StringUtil.isEmpty(token) || StringUtil.isEmpty(md5Key)){
			String result = HttpUtil.get(tokenLKPGUrl);
			log.info("getToken-result="+result);
			JSONObject res = JSONObject.fromObject(result);
			Map<String, Object> map = JsonUtil.jsonToMap(res.getString("data"));
			token = MapUtils.getString(map, "token");
			md5Key = MapUtils.getString(map, "md5Key");
			RedisOperator.set("lkpg_token",token , NumberUtils.toInt(MapUtils.getString(map, "expiresTime")));
			RedisOperator.set("lkpg_md5_key", md5Key, NumberUtils.toInt(MapUtils.getString(map, "expiresTime")));
		}
		
		resMap.put("token", token);
		resMap.put("md5Key", md5Key);
		return resMap;
	}
	
	 /**
	 * 生成服务的签名
     */
	private static String createApiSign(Map<?,?> param, String md5Key) throws Exception {
		Set<?> keysSet = param.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		//MD5key作为前缀,各参数按字典排序拼接
		StringBuffer temp = new StringBuffer(md5Key);
		boolean first = true;
		for (Object key : keys) {
		    if (first) {
		        first = false;
		    } else {
		        temp.append("&");
		    }
		    temp.append(key).append("=");
		    Object value = param.get(key);
		    String valueString = "";
		    if (null != value) {
		        valueString = String.valueOf(value);
		    }
		    temp.append(valueString);
		}
		//MD5加密后转为大写
		return MD5Utils.MD5Encode(temp.toString()).toUpperCase();
	}
	
	/**
	 * 获取鲁克评估物业名称
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getLKPGPropertyName(String keyWord, String cityName) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("city", cityName);
			params.put("propertyName", keyWord);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			log.info("POST--------tools 访问lkpg的请求参数"+params);
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(propertyLKPGUrl, params, keys.get("token")));
			log.info("Return------------tools 访问lkpg的 返回结果"+result);
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				List<Map<String, String>> list = JSONArray.toList((JSONArray) result.get("data"),new HashMap<String, String>(),new JsonConfig());
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取鲁克评估楼栋
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getLKPGBuilding(String propertyId, String cityName) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("city", cityName);
			params.put("propertyId", propertyId);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(buildingLKPGUrl, params, keys.get("token")));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				List<Map<String, Object>> list = JSONArray.toList((JSONArray) result.get("data"),new HashMap<String, Object>(),new JsonConfig());
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取鲁克评估房号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getLKPGHouses(String propertyId, String buildingId, String cityName) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("city", cityName);
			params.put("propertyId", propertyId);
			params.put("buildingId", buildingId);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(housesLKPGUrl, params, keys.get("token")).replaceAll("area", "buildArea"));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				List<Map<String, String>> list = JSONArray.toList((JSONArray) result.get("data"),new HashMap<String, String>(),new JsonConfig());
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 鲁克评估自动询价
	 * @user Administrator
	 * @date 2018年6月20日 下午5:35:35 
	 * @param enquiryDto
	 * @param enquiryReportDto
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String autoPriceLKPG(EnquiryDto enquiryDto, EnquiryReportDto enquiryReportDto) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	DecimalFormat decimalFormat = new DecimalFormat("###################.###########");  
	    	params.put("city", enquiryDto.getCityName());
	    	params.put("propertyName", enquiryDto.getPropertyName());
			params.put("buildingName", enquiryDto.getBuildingName());
			params.put("roomName", enquiryDto.getRoomName());
			params.put("area", decimalFormat.format(enquiryDto.getArea()));
			params.put("housingType", "1");
			params.put("isTax", enquiryDto.getIsGetNetPriceTax());
			
			params.put("obligeeNature", "个人".equals(enquiryDto.getObligee())?"1":"2");
			params.put("registerPrice", decimalFormat.format(enquiryDto.getRegisterPrice()));
			params.put("registrationTimeStr", enquiryDto.getRegisterDate());
			
			params.put("propertyId", enquiryDto.getPropertyId());
			params.put("buildingId", enquiryDto.getBuildingId());
			params.put("roomId", enquiryDto.getRoomId());
			
			params.put("totalFloor", enquiryDto.getTotalFloor());
			params.put("inTheFloor", enquiryDto.getInTheFloor());
			params.put("orientation", enquiryDto.getOrientation());
			params.put("decorate", enquiryDto.getDecorate());
			params.put("ladder", enquiryDto.getLadder());
			
	    	params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(autoPriceLKPGUrl, params, keys.get("token")));
			log.info("响应：" + result);
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
	    		result = JSONObject.fromObject(result.get("data"));
	    		enquiryReportDto.setUnitPrice(result.getDouble("enquiryPrice"));
	    		enquiryReportDto.setTotalPrice(result.getDouble("sumPrice"));
	    		//如果计算税费
	    		if (enquiryDto.getIsGetNetPriceTax() == 0) {
	    			if (result.containsKey("taxation1")) {
	    				enquiryReportDto.setTax(result.getDouble("taxation1"));
	    				enquiryReportDto.setNetPrice(result.containsKey("netWorth1")?result.getDouble("netWorth1"):0);
	    			}else if (result.containsKey("taxation2")) {
	    				enquiryReportDto.setTax(result.getDouble("taxation2"));
	    				enquiryReportDto.setNetPrice(result.containsKey("netWorth2")?result.getDouble("netWorth2"):0);
	    			}else if (result.containsKey("taxation3")) {
	    				enquiryReportDto.setTax(result.getDouble("taxation3"));
	    				enquiryReportDto.setNetPrice(result.containsKey("netWorth3")?result.getDouble("netWorth3"):0);
	    			}
	    		}
				enquiryReportDto.setStart(1);
	    		return RespStatusEnum.SUCCESS.getCode();
	    	}else{
				enquiryReportDto.setStart(2);
	    		return RespStatusEnum.FAIL.getCode();
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 鲁克评估手动询价
	 * @user Administrator
	 * @date 2018年6月20日 下午5:35:44 
	 * @param enquiryDto
	 * @param enquiryReportDto
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Deprecated
	private String handPriceLKPG(EnquiryDto enquiryDto, EnquiryReportDto enquiryReportDto) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("city", enquiryDto.getCityName());
	    	params.put("province", "");
	    	params.put("propertyName", enquiryDto.getPropertyName());
			params.put("buildingName", enquiryDto.getBuildingName());
			params.put("roomName", enquiryDto.getRoomName());
			params.put("area", enquiryDto.getArea());
			params.put("housingType", 1);
			params.put("isTax", 1);
			params.put("obligeeNature", 1);
			params.put("objective", 4);
			params.put("imgUrl", "");
			params.put("pickPhone", "");
	    	params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(handPriceLKPGUrl, params, keys.get("token")));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
	    		result = JSONObject.fromObject(result.get("data"));
	    		enquiryReportDto.setUnitPrice(result.getDouble("enquiryPrice"));
	    		enquiryReportDto.setTotalPrice(result.getDouble("sumPrice"));
				enquiryReportDto.setStart(1);
	    		return RespStatusEnum.SUCCESS.getCode();
	    	}else{
				enquiryReportDto.setStart(2);
	    		return RespStatusEnum.FAIL.getCode();
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 获取鲁克评估小区网络报盘
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getLKPGNetworkOffer(int start, int pageSize, int orderMode,
			String propertyId, String propertyName, String cityName) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", start);
			params.put("pageSize", pageSize);
			params.put("city", cityName);
			params.put("orderMode", orderMode);
			params.put("propertyId", propertyId);
			params.put("propertyName", propertyName);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(networkOfferLKPGUrl, params, keys.get("token")));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				List<Map<String, String>> list = JSONArray.toList((JSONArray) result.get("data"),new HashMap<String, String>(),new JsonConfig());
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取鲁克评估小区市场成交
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getLKPGMarketBargain(int start, int pageSize, int orderMode,
			String propertyId, String propertyName, String cityName) throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", start);
			params.put("pageSize", pageSize);
			params.put("city", cityName);
			params.put("orderMode", orderMode);
			params.put("propertyId", propertyId);
			params.put("propertyName", propertyName);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(marketBargainLKPGUrl, params, keys.get("token")));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				List<Map<String, Object>> list = JSONArray.toList((JSONArray) result.get("data"),new HashMap<String, Object>(),new JsonConfig());
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取鲁克评估小区照片
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getLKPGPropertyImgs(String propertyId, String propertyName, String cityName, int type) 
			throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("city", cityName);
			params.put("propertyId", propertyId);
			params.put("propertyName", propertyName);
			params.put("type", type);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			String result = HttpUtil.jsonPostLKPG(propertyImgsLKPGUrl, params, keys.get("token"));
			RespDataObject<List<Map<String, Object>>> res = (RespDataObject<List<Map<String, Object>>>) JsonUtil.parseJsonToObj(result, RespDataObject.class);
			if(res.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
				return res.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取鲁克评估小区数量信息
	 */
	@Override
	public Map<String, Object> selectLKPGPropertyImgAndBargainAndNetworkCount(String propertyId, String cityName) 
			throws ClientProtocolException, IOException{
		try {
			Map<String, String> keys = getLKPGToken();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("city", cityName);
			params.put("propertyId", propertyId);
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			JSONObject result = JSONObject.fromObject(HttpUtil.jsonPostLKPG(propertyImgAndBargainAndNetworkCountUrl, params, keys.get("token")));
			if(result.getString("code").equals(RespStatusEnum.SUCCESS.getCode())){
				Map<String, Object> res = JsonUtil.jsonToMap(result.getString("data"));
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 鲁克评估询价反馈
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RespDataObject<Map<String, Object>> lKPGEnquiryFeedback(Map<String, Object> params) 
			throws ClientProtocolException, IOException{
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		String result = "";
		try {
			Map<String, String> keys = getLKPGToken();
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			result = HttpUtil.jsonPostLKPG(enquiryFeedBackUrl, params, keys.get("token"));
			RespDataObject<Map<String, Object>> res = (RespDataObject<Map<String, Object>>) JsonUtil.parseJsonToObj(result, RespDataObject.class);
			return res;
		} catch (Exception e) {
			log.info("result：" + result);
			e.printStackTrace();
			resp.setCode(RespStatusEnum.ERRORONE.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
		}
		return resp;
	}
	
	/**
	 * 鲁克评估物业信息反馈
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RespDataObject<Map<String, Object>> lKPGPropertyFeedback(Map<String, Object> params) 
			throws ClientProtocolException, IOException{
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		String result = "";
		try {
			Map<String, String> keys = getLKPGToken();
			params.put("sign", createApiSign(params, keys.get("md5Key")));
			result = HttpUtil.jsonPostLKPG(propertyFeedBackUrl, params, keys.get("token"));
			RespDataObject<Map<String, Object>> res = (RespDataObject<Map<String, Object>>) JsonUtil.parseJsonToObj(result, RespDataObject.class);
			return res;
		} catch (Exception e) {
			log.info("result：" + result);
			e.printStackTrace();
			resp.setCode(RespStatusEnum.ERRORONE.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
		}
		return resp;
	}

	@Override
	public RespDataObject<Map<String, Object>> resetLkpgToken(
			Map<String, Object> param) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		String result = "";
		try {
			result = HttpUtil.get(tokenLKPGUrl);
			log.info("getToken-result="+result);
			JSONObject res = JSONObject.fromObject(result);
			Map<String, Object> map = JsonUtil.jsonToMap(res.getString("data"));
			RedisOperator.set("lkpg_token",MapUtils.getString(map, "token") , NumberUtils.toInt(MapUtils.getString(map, "expiresTime")));
			RedisOperator.set("lkpg_md5_key", MapUtils.getString(map, "md5Key"), NumberUtils.toInt(MapUtils.getString(map, "expiresTime")));
		} catch (Exception e) {
			log.info("result：" + result);
			e.printStackTrace();
			resp.setCode(RespStatusEnum.ERRORONE.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
		}
		
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
}
