package com.anjbo.controller.tools;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.Booking;
import com.anjbo.bean.tools.BookingDetail;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.processor.BookingProcessor;
import com.anjbo.service.mort.DictService;
import com.anjbo.service.tools.BookingService;
import com.anjbo.utils.DateUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.ImageUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.MapAndBean;
import com.anjbo.utils.StringUtil;

/**
 * 预约取号
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:14:56
 */
@Controller
@RequestMapping("/tools/booking")
public class BookingController {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private BookingService bookingService;
	@Resource
	private ThreadPoolTaskExecutor poolTaskExecutor;
	@Resource
	private DictService dictService;
	

	/**
	 * 预约取号详情
	 * @user Object
	 * @date 2016-11-28 下午03:18:54 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/getDetail")
	public BookingDetail getDetail(@RequestBody Map<String, Object> params) {
		return bookingService.selectBookingDetail(MapUtils.getIntValue(params, "id"));
	}
	
	
	/**
	 * 查询预约单详情集合
	 * 
	 * @param request
	 * @param pageSize
	 *            每页记录条数
	 * @param currPage
	 *            当前页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/v/bookingDetails")
	public RespData<BookingDetail> getValidBookingDetails(@RequestBody Map<String, Object> params) {
		RespData<BookingDetail> respData = new RespData<BookingDetail>();
		try {
			BookingDetail detail = new BookingDetail();
			detail.setUid(MapUtils.getString(params, "uid"));
			detail.setStart(MapUtils.getIntValue(params, "start"));
			detail.setPagesize(MapUtils.getIntValue(params, "pageSize"));
			List<BookingDetail> alist = bookingService.selectBookingDetailList(detail);
			if (alist != null) {
				Map<String, String> dictMap = MapUtils.getMap(params, "dictMap");
				for (BookingDetail bookingDetail : alist) {
					String type = bookingDetail.getBookingType();
					String no = bookingDetail.getSzItemNo();
					String[] tnVal = dictMap.get(
							"bookingType|szItemNo" + type + "|" + no).split(
							"\\|");
					bookingDetail.setBookingType(tnVal[0]);
					bookingDetail.setSzItemNo(tnVal[1]);
					String regval = dictMap.get("registrationAreaOid"
							+ bookingDetail.getRegistrationAreaOid());
					bookingDetail.setRegistrationAreaOid(regval.substring(0,
							regval.indexOf("（")));
					String code = bookingDetail.getBookingCode();
					if (StringUtils.isNotEmpty(code)) {
						bookingDetail.setBookingCode(code.substring(code.length() - 6, code.length()));
					}
					if (StringUtils.isNotEmpty(bookingDetail.getBookingCode())&&bookingDetail.getStatus()==1) {
						String bookingDate = bookingDetail.getBookingDate();
						String workTimeSolt = bookingDetail.getWorkTimeSolt();
						workTimeSolt = workTimeSolt.split("-")[1];
						Date dt = DateUtil.parse(bookingDate + " " + workTimeSolt
								+ ":00", DateUtil.FMT_TYPE1);
						if (dt.getTime() < new Date().getTime()) {// 是否过期
							bookingDetail.setStatus(5); // 5是过期
						}
					}
				}
			}
			respData.setCode(RespStatusEnum.SUCCESS.getCode());
			respData.setMsg(RespStatusEnum.SUCCESS.getMsg());
			respData.setData(alist);
		} catch (Exception e) {
			e.printStackTrace();
			respData.setCode(RespStatusEnum.FAIL.getCode());
			respData.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return respData;
	}
	
	/**
	 * 保存取号信息
	 * 注意 1传入uid
	 *     2传入cookieValue
	 * @Title: save 
	 * @param request
	 * @param bookingDetail
	 * @param isTake
	 * @return
	 * RespDataObject<Integer>
	 * @throws
	 */
	@RequestMapping(value = "/v/save")
	@ResponseBody
	public RespDataObject<Integer> save(HttpServletRequest request,
			@RequestBody BookingDetail bookingDetail) {
		RespDataObject<Integer> resp = new RespDataObject<Integer>();
		try {
			bookingDetail.setBookingDate(bookingDetail.getWorkDay());
			bookingDetail.setWorkTimeSolt(bookingDetail.getWorkTimeSoltName());
//			int bookingId = addBooking(bookingDetail);// 添加申办信息
//			BookingDetail detail = null;
//			if (bookingDetail.getId() > 0) {
//				detail = bookingService.selectBookingDetail(bookingDetail
//						.getId());
//			}
//			if (bookingDetail.getId() > 0
//					&& detail != null
//					&& detail.getBookingType().equals(
//							bookingDetail.getBookingType())
//					&& detail.getSzItemNo().equals(bookingDetail.getSzItemNo())) {// 防止修改申办类型
//				bookingDetail.setBookingId(bookingId);
//				bookingService.updateBookingDetail(bookingDetail);
//			} else {
//				BookingDetail bd = bookingService
//						.bookingDetailExit(bookingDetail);
//				if (bd != null) {
//					resp.setCode(RespStatusEnum.BOOKING_EXIT.getCode());
//					resp.setMsg(RespStatusEnum.BOOKING_EXIT.getMsg());
//					return resp;
//				} else {
//					bookingDetail.setBookingId(bookingId);
					bookingDetail.setType(1);// 类型（1真实申办数据 2批量刷申办数据）
//					bookingService.addBookingDetail(bookingDetail);// 添加申办详情
//				}
//			}
//			resp.setData(bookingDetail.getId());
//			if (bookingDetail.getIsTake() == 1) {// 立即预约
				RespStatus rs = submit(request, bookingDetail.getId(),
						bookingDetail.getWorkDay(),
						bookingDetail.getWorkDayLabel(),
						bookingDetail.getWorkTimeSoltOid(),
						bookingDetail.getWorkTimeSoltName(),
						bookingDetail.getVerificationcodereg(),
						bookingDetail.getCookieValue(),
						bookingDetail.getDictMap(),
						bookingDetail);
				resp.setCode(rs.getCode());
				resp.setMsg(rs.getMsg());
//			} else {
//				resp.setCode(RespStatusEnum.SUCCESS.getCode());
//				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
//			}
			return resp;
		} catch (Exception e) {
			log.error("申办出错：" + e.getMessage());
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}

	/**
	 * 刷新取号
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v/refreshTake")
	@ResponseBody
	public RespDataObject<String> refreshTake(
			@RequestBody Map<String, String> params) {
		RespDataObject<String> status = new RespDataObject<String>();
		try {
			String workDay = BookingProcessor.workDay(params
					.get("registrationAreaOid"));
			String workTime = BookingProcessor.workTime(
					params.get("registrationAreaOid"),
					params.get("bookingType"));
			String workCount = BookingProcessor.workCount(
					params.get("registrationAreaOid"),
					params.get("bookingType"));
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("workDay", JSONArray.fromObject(workDay));
			respMap.put("workTime", JSONArray.fromObject(workTime));
			respMap.put("workCount", JSONObject.fromObject(workCount));
			status.setData(JsonUtil.MapToJson(respMap));
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (MortgageException e) {
			status.setCode(e.getCode());
			status.setMsg(e.getMsg());
			return status;
		}
		return status;
	}

	/**
	 * 预约取消
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/v/cancelBooking")
	public @ResponseBody
	RespStatus cancelBooking(@RequestBody Map<String, Object> params) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		
		BookingDetail bookingDetail = (BookingDetail) MapAndBean.MapToBean(BookingDetail.class, params);
		
		BookingDetail detail = bookingService.selectBookingDetail(bookingDetail.getId());
		String bookingCode = detail.getBookingCode();
		String phoneNumber = detail.getPhoneNumber();
		String certificateNo = detail.getCertificateNo();
		if (bookingDetail.getId() == 0
				|| StringUtils.isEmpty(bookingCode)
				|| (StringUtils.isEmpty(phoneNumber) && StringUtils
						.isEmpty(certificateNo))) {
			return new RespStatus(RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		StringBuffer sb = new StringBuffer(BookingProcessor.goCancelBookWeb);
		sb.append("&bookingCode=").append(bookingCode);
		sb.append("&certificateNo=").append(certificateNo);
		sb.append("&phoneNumber=").append(phoneNumber);
		try {
			String result = HttpUtil.get(sb.toString(), "GBK", 10000, null,
					null);
			String msg = StringUtil.processLocation(result,
					"<td id\\=\"sturtsName\">&nbsp;(.*?)</td>");
			if ("已取消".equals(msg)) {
				bookingDetail.setStatus(2);// 取消
				bookingService.updateBookingDetailStatus(bookingDetail);
				return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
						RespStatusEnum.SUCCESS.getMsg());
			}
			result = StringUtil.processLocation(result,
					"bookWebCancel\\('(.*?)'\\)");
			if (StringUtils.isNotEmpty(result)) {
				sb = new StringBuffer(BookingProcessor.cancelBookUrl);
				sb.append("&bookingInformationOid=").append(result);
				result = HttpUtil.get(sb.toString(), "GBK", 10000, null, null);
				if (StringUtils.isEmpty(result)) {
					bookingDetail.setStatus(2);// 取消
					bookingService.updateBookingDetailStatus(bookingDetail);
					return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
							RespStatusEnum.SUCCESS.getMsg());
				} else {
					return new RespStatus(
							RespStatusEnum.SYSTEM_ERROR.getCode(), result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RespStatus(RespStatusEnum.FAIL.getCode(),
				RespStatusEnum.FAIL.getMsg());
	}

	/**
	 * 生成验证码并获取第三方服务器的cookie
	 * remark:由于图片保存的位置，暂时不用
	 * @Title: authCode 
	 * @param request
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/v/authCode")
	public @ResponseBody
	RespDataObject<Map<String, Object>> authCode(HttpServletRequest request) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getCode());
		try {
			String url = "/code/booking/" + UUID.randomUUID().toString()
					+ ".jpg";
			String path = request.getSession().getServletContext()
					.getRealPath(url);
			String cookieValue = ImageUtil.saveToFile(
					BookingProcessor.authCodeUrl
							+ URLEncoder.encode(DateUtil.getGMT(new Date()),
									"utf-8"), path);

			// request.getSession().setAttribute("MORTGAGECOOKIEVALUE_BOOKING",
			// cookieValue);
			//log.info("生成验证码返回cookieValue=" + cookieValue);
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("cookieValue", cookieValue);
			respMap.put("url", url + "?" + Math.random());
			status.setData(respMap);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("生成验证码异常");
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 新增申办
	 * @return int 返回申办id，已存在申办信息直接返回id
	 */
	private int addBooking(BookingDetail bookingDetail) {
		Booking booking = new Booking();
		booking.setBookingType(bookingDetail.getBookingType());
		booking.setSzItemNo(bookingDetail.getSzItemNo());
		booking.setBookingSzAreaOid(bookingDetail.getBookingSzAreaOid());
		booking.setRegistrationAreaOid(bookingDetail.getRegistrationAreaOid());
		Integer boookingId = bookingService.bookingExit(booking);
		if (boookingId != null) {
			return boookingId;
		}
		bookingService.addBooking(booking);
		return booking.getId();
	}

	/**
	 * 立即预约
	 * @param map 
	 * @param bookingDetail
	 * @return
	 */
	@ResponseBody
	private RespStatus submit(HttpServletRequest request, int id,
			String workDay, String workDayLabel, String workTimeSoltOid,
			String workTimeSoltName, String verificationcodereg,
			String cookieValue, Map<String, String> dictMap, BookingDetail bookingDetail) {
		RespStatus status = new RespStatus();
		try {
			if (/*id <= 0 || */StringUtils.isEmpty(workDay)
					|| StringUtils.isEmpty(workDayLabel)
					|| StringUtils.isEmpty(workTimeSoltOid)
					|| StringUtils.isEmpty(workTimeSoltName)) {
				status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return status;
			}
//			BookingDetail bookingDetail = bookingService
//					.selectBookingDetail(id);
			bookingDetail.setVerificationcodereg(verificationcodereg);
			if (bookingDetail.getStatus() == 1) {
				String bookingDate = bookingDetail.getBookingDate();
				String workTimeSolt = bookingDetail.getWorkTimeSolt();
				workTimeSolt = workTimeSolt.split("-")[1];
				Date dt = DateUtil.parse(bookingDate + " " + workTimeSolt
						+ ":00", DateUtil.FMT_TYPE1);
				if (dt.getTime() >= new Date().getTime()) {// 未过期的预约不能重新预约
					status.setCode(RespStatusEnum.BOOKING_CODE_EXIT.getCode());
					status.setMsg(RespStatusEnum.BOOKING_CODE_EXIT.getMsg());
					return status;
				}
			}
			bookingDetail.setWorkDay(workDay);
			bookingDetail.setWorkDayLabel(workDayLabel);
			bookingDetail.setWorkTimeSolt(workTimeSoltOid);
			bookingDetail.setWorkTimeSoltName(workTimeSoltName);
			bookingDetail.setCookieValue(cookieValue);
			bookingDetail.setDictMap(dictMap);
			//log.info("预约取号获取的cookieValue=" + cookieValue);
			return BookingProcessor.getInstance().submitBooking(bookingDetail,
					bookingService, poolTaskExecutor);
		} catch (Exception e) {
			log.debug(e.getMessage());
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return status;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param detailsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/deleteDetails")
	public RespStatus deleteDetails(@RequestBody Map<String, Object> params) {
		try {
			String detailIdsStr = MapUtils.getString(params, "detailIds");
			detailIdsStr = detailIdsStr.replace("{", "");
			detailIdsStr = detailIdsStr.replace("}", "");
			String[] idss = detailIdsStr.split(",");
			int detailIds[] = new int[idss.length];
			for (int i = 0; i < idss.length; i++) {
				detailIds[i] = Integer.parseInt(idss[i]);
			}
			bookingService.deleteBookingDetails(MapUtils.getString(params, "uid"), detailIds);
			log.info("deleteDetails success : ");
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
					RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("deleteDetails Exception ->", e);
			return new RespStatus(RespStatusEnum.FAIL.getCode(),
					RespStatusEnum.FAIL.getMsg());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/searchWorkInfo")
	public RespDataObject<List<Map<String,String>>> searchWorkInfo(@RequestBody Map<String, String> params){
		RespDataObject<List<Map<String,String>>> resp =new RespDataObject<List<Map<String, String>>>();
		List<Map<String,String>> bookingProcessorList=BookingProcessor.searchWork(params);
		if(!CollectionUtils.isEmpty(bookingProcessorList)){
			resp.setData(bookingProcessorList);
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
		}else{
			resp.setData(null);
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping(value = "/initSzItem")
	public RespStatus initSzItem(String k){
		RespStatus resp = new RespStatus();
		if(!"local20171031".equals(k)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return resp;
		}
		try {
			Map<String,String> szItemMap = getSzItemMap();
			Map<String,String> szItemOldMap =dictService.getDictMap("bookingType|szItemNo");
			for (String key : szItemOldMap.keySet()) {
				String val = szItemOldMap.get(key);
				if(val.equals("预售房地产抵押业务类|预售房地产抵押登记（楼花抵押）")){
					val = "预售房地产抵押业务类|一般抵押权首次登记-预售（楼花抵押）";
				}else if(val.equals("初始、变更及其它业务类|安居房换证登记")){
					val="初始、变更及其它业务类|政策性住房上市换证登记（原安居房换证登记）";
				}else if(val.equals("初始、变更及其它业务类|地役权登记")){
					val="初始、变更及其它业务类|地役权首次登记";
				}else if(val.equals("初始、变更及其它业务类|注销地役权登记")){
					val="初始、变更及其它业务类|地役权注销登记";
				}else if(val.equals("初始、变更及其它业务类|不动产证书登记证明补发")){
					val="初始、变更及其它业务类|不动产权证书、登记证明补发";
				}else if(val.equals("初始、变更及其它业务类|不动产证书登记证明换发")){
					val="初始、变更及其它业务类|不动产权证书、登记证明换发";
				}else if(val.equals("初始、变更及其它业务类|海上构筑物初始登记")){
					val="初始、变更及其它业务类|海上构筑物首次登记";
				}
				String szItemNo = szItemMap.get(val);
				if(StringUtil.isNotEmpty(szItemNo)){
					szItemMap.remove(val);
					if(!key.equals(szItemNo)){
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("type", "bookingType|szItemNo");
						param.put("code",key);
						param.put("name",szItemOldMap.get(key));
						param.put("newCode",szItemNo);
						param.put("newName",val);
						int r = dictService.updateDict(param);
						if(r>0){
							param.put("bookingType",key.split("\\|")[0]);
							param.put("szItemNo",key.split("\\|")[1]);
							param.put("newBookingType",szItemNo.split("\\|")[0]);
							param.put("newSzItemNo",szItemNo.split("\\|")[1]);
							r = bookingService.updateBookingBase(param);
							log.info(param.toString()+"更新数据条数"+r);
						}
					}
				}
			}
			log.info("********************新增数据****************************");
			int maxSort = dictService.getMaxSort("bookingType|szItemNo");
			for (String key : szItemMap.keySet()) {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("type", "bookingType|szItemNo");
				param.put("code",szItemMap.get(key));
				param.put("name",key);
				param.put("sort",++maxSort);
				dictService.addDict(param);
				log.info(szItemMap.get(key)+"    "+key);
			}
			log.info("********************更新prove数据****************************");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", "prove");
			param.put("code","00");
			param.put("newCode","00");
			param.put("newName","房地产证");
			log.info(dictService.updateDict(param));
			if(!dictService.getDictMap("prove").containsKey("02")){
				maxSort = dictService.getMaxSort("prove");
				param = new HashMap<String,Object>();
				param.put("type", "prove");
				param.put("code","02");
				param.put("name","不动产权证书");
				param.put("sort",++maxSort);
				log.info(dictService.addDict(param));
			}
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}
	private Map<String,String> getSzItemMap() throws Exception{
		Map<String,String> map = new LinkedHashMap<String,String>();
		String result = HttpUtil.get(BookingProcessor.szItemUrl, "GBK", 10000, null,null);
		JSONArray szItemList = JSONArray.fromObject(result);
		for (Object obj : szItemList) {
			JSONObject szItem = JSONObject.fromObject(obj);
			result = HttpUtil.get(String.format(BookingProcessor.szItemNoUrl,szItem.getString("bookingTypeOid")), "GBK", 10000, null,null);
			JSONArray szItemNoList = JSONArray.fromObject(result);
			for (Object objNo : szItemNoList) {
				JSONObject szItemNo = JSONObject.fromObject(objNo);
				map.put(szItem.getString("bookingTypeName")+"|"+szItemNo.getString("sz2ndItemName"),
						szItem.getString("bookingTypeOid")+"|"+szItemNo.getString("szItemNo"));
			}
		}
		return map;
	}
}