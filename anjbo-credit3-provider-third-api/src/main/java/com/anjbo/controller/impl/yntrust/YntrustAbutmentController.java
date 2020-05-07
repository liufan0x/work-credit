package com.anjbo.controller.impl.yntrust;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.yntrust.*;
import com.anjbo.common.*;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.yntrust.IYntrustAbutmentController;
import com.anjbo.dao.yntrust.YntrustBorrowMapper;
import com.anjbo.service.yntrust.*;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.SingleUtils;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtils;
import com.anjbo.utils.yntrust.YntrustAbutment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/3/8. 云南信托控制器
 */
@RestController
public class YntrustAbutmentController extends BaseController implements IYntrustAbutmentController {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private YntrustBorrowService yntrustBorrowService;
	@Resource
	private YntrustContractService yntrustContractService;
	@Resource
	private YntrustMappingService yntrustMappingService;
	@Resource
	private YntrustRepaymentInfoService yntrustRepaymentInfoService;
	@Resource
	private YntrustRepaymentPayService yntrustRepaymentPayService;
	@Resource
	private YntrustRepaymentPlanService yntrustRepaymentPlanService;
	@Resource
	private YntrustRequestFlowService yntrustRequestFlowService;
	@Resource
	private YntrustImageService yntrustImageService;
	@Resource
	private YntrustLoanService yntrustLoanService;
	@Resource
	private UserApi userApi;
	@Resource
	private OrderApi orderApi;
	@Resource
	private DataApi dataApi;
	@Resource
    private YntrustBorrowMapper yntrustBorrowMapper;
	/**
	 * 保存云南信托借款与合同信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RespDataObject<String> addBorrowAndContract(@RequestBody Map<String, Object> map) {
		RespDataObject<String> respStatus = new RespDataObject<String>();

		Object borrowObj = MapUtils.getObject(map, "borrow");
		Object contractObj = MapUtils.getObject(map, "contract");
		int choice = (int) MapUtils.getObject(map, "type");
		if (null == borrowObj || null == contractObj) {
			RespHelper.setFailRespStatus(respStatus, RespStatusEnum.PARAMETER_ERROR.getMsg());
			return respStatus;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		UserDto user = null;
		String orderNo = "";
		String ynProductCode = "";
		String ynProductName = "";

		Map<String, Object> borrow = gson.fromJson(gson.toJson(borrowObj), Map.class);
		Map<String, Object> contract = gson.fromJson(gson.toJson(contractObj), Map.class);
		try {
			user = userApi.getUserDto();
			if (StringUtils.isBlank(MapUtils.getString(borrow, "orderNo"))) {
				RespHelper.setFailRespStatus(respStatus, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return respStatus;
			}
			ynProductCode = MapUtils.getString(borrow, "ynProductCode");
			ynProductName = MapUtils.getString(borrow, "ynProductName");
			map.put("ynProductCode", ynProductCode);
			map.put("ynProductName", ynProductName);
			orderNo = MapUtils.getString(borrow, "orderNo");
			borrow.put("createUid", user.getUid());
			borrow.put("updateUid", user.getUid());
			yntrustBorrowService.insertMap(borrow); // 有相同订单就修改，否则就是新增订单
		} catch (Exception e) {
			log.error("保存云南信托借款人信息异常:", e);
			RespHelper.setFailRespStatus(respStatus, "保存借款人信息失败");
			return respStatus;
		}
		try {
			if (StringUtils.isBlank(MapUtils.getString(contract, "orderNo"))) {
				RespHelper.setFailRespStatus(respStatus, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return respStatus;
			}
			contract.put("createUid", user.getUid());
			contract.put("updateUid", user.getUid());
			contract.put("ynProductCode", ynProductCode);
			contract.put("ynProductName", ynProductName);
			yntrustContractService.insertMap(contract);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存云南信托合同信息异常:", e);
			RespHelper.setFailRespStatus(respStatus, "保存合同信息失败");
			return respStatus;
		}
		if (choice==2) {
			// 推送借款信息，合同信息
			try {
				map.put("orderNo", orderNo);
				YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
						yntrustRequestFlowService);
				yntrustAbutment.setDataApi(dataApi);
				yntrustAbutment.plusYnTrustInfo(respStatus, map);

				YntrustContractDto contractDto = new YntrustContractDto();
				YntrustBorrowDto borrowDto = new YntrustBorrowDto();
				borrowDto.setOrderNo(orderNo);
				borrowDto.setYnProductCode(ynProductCode);
				borrowDto.setYnProductName(ynProductName);
				contractDto.setOrderNo(orderNo);
				contractDto.setYnProductCode(ynProductCode);
				contractDto.setYnProductName(ynProductName);

				// 成功发送短信给借款人
				if (RespStatusEnum.SUCCESS.getCode().equals(respStatus.getCode())) {
					Map<String, Object> imgMap = new HashMap<String, Object>();
					List<Map<String, Object>> list = yntrustImageService.list(map);
					List<Map<String,Object>> cun = new ArrayList<Map<String,Object>>();     //记录只有一张的影像资料
					List<String> arr = new ArrayList<String>();
				
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> maps = list.get(i);
						String type = MapUtils.getString(maps, "type");
						arr.add(type);
					}
					if (!arr.contains("A") || !arr.contains("B") || !arr.contains("T") || !arr.contains("V")
							|| !arr.contains("W")) {
						RespHelper.setFailRespStatus(respStatus, "请先上传必须的影像资料");
						return respStatus;
					}
					RespDataObject<Object> ds= coupling(list);
					cun = (List<Map<String, Object>>) ds.getData();
					if (null!=ds.getMsg()) {
						RespHelper.setFailRespStatus(respStatus,ds.getMsg());
						return respStatus;
					}
					
					imgMap.put("orderNo", orderNo);
					imgMap.put("imgList", cun);
					imgMap.put("iDCardNo", MapUtils.getString(borrow, "iDCardNo"));
					if (list.size() > 0) {
						yntrustAbutment.pushImg(respStatus, imgMap);
						if (!RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())) {
							imgMap.put("isPlus", 1);
							yntrustImageService.updateIsPush(imgMap);
						}
					}
					RespDataObject<Map<String, Object>> qrCode = getQRCode(map);
					if (RespStatusEnum.SUCCESS.getCode().equals(qrCode.getCode())) {
						String key = SMSConstants.SMS_YNTRUST_QR_CODE;
						String borrowerName = MapUtils.getString(borrow, "shortName");
						String telephoneNo = MapUtils.getString(borrow, "telephoneNo");
						if (StringUtil.isNotBlank(telephoneNo)) {
							String ipWhite = Constants.BASE_AMS_IPWHITE;
							AmsUtil.smsSend2(telephoneNo, ipWhite, key, "hyt",
									"客户名: " + borrowerName + " " + MapUtils.getString(qrCode.getData(), "url","").replace("#","%23"));
						} else {
							log.info("没有订单为:" + orderNo + "电话号码信息,没有发送电子合同链接地址短信");
						}
						log.info("获取成功:" + qrCode.getData());
					} else {
						log.info("云南信息推送信息成功获取合同电子签证链接状态为" + qrCode.getMsg()
								+ ",请通过/credit/third/api/yntrust/v/getQRCode获取,");
					}
					borrowDto.setPushStatus(1);
					contractDto.setPushStatus(1);
				} else {
					borrowDto.setPushStatus(2);
					contractDto.setPushStatus(2);
				}
				yntrustBorrowService.update(borrowDto);
				yntrustContractService.update(contractDto);

			} catch (Exception e) {
				RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
				log.error("推送数据给云南信托异常:", e);
			}
		}

		return respStatus;
	}

	
	// 单推影像资料
	@Override
	public RespStatus pushImgs(@RequestBody Map<String, Object> map) {
		RespStatus respStatus = new RespStatus();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			Map<String, Object> imgMap = new HashMap<String, Object>();
			imgMap.put("orderNo", orderNo);
			List<Map<String, Object>> lists = yntrustImageService.list(imgMap);
			List<Map<String, Object>> cun = new ArrayList<Map<String,Object>>();
			List<String> myList = new ArrayList<String>();
			Map<String, Object> ssMap = new HashMap<String, Object>();
			ssMap.put("orderNo", orderNo);
			for (int i = 0; i < lists.size(); i++) {
				myList.add(MapUtils.getString(lists.get(i),"type"));  
			   
			}
			 HashSet hset = new HashSet(myList);        //区分两条数据都是同一类型的
			 myList.clear();    
			 myList.addAll(hset);   
			 ssMap.put("lists",myList);
					
			 List<Map<String, Object>> cuns = yntrustImageService.lisMas(ssMap);
				
			
			RespDataObject<Object> ds= coupling(cuns);
			cun = (List<Map<String, Object>>) ds.getData();
			if (null!=ds.getMsg()) {
				respStatus.setMsg(ds.getMsg());
				return respStatus;
			}
			
			imgMap.put("imgList", cun);
			imgMap.put("iDCardNo", MapUtils.getString(map, "iDCardNo"));
			
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.pushImg(respStatus, imgMap);
			if (!RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())) {
				imgMap.put("isPlus", 1);
				yntrustImageService.updateIsPush(imgMap);
				RespHelper.setSuccessRespStatus(respStatus);
			}
		} catch (Exception e) {
			log.error("推送云南信托影像资料异常:", e);
			RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}

	

public RespDataObject<Object> coupling(List<Map<String, Object>> list){
     int num1=0;
	 int num2=0;
	 int num3=0;
	 int num4=0;
	 int num5=0;
	 int num6=0;
	 int num7=0;
	 int num8=0;
	 RespDataObject<Object> respStatus = new RespDataObject<Object>();
     List<Map<String,Object>> cun = new ArrayList<Map<String,Object>>();     //记录只有一张的影像资料
	 List<Map<String, Object>> url3 = new ArrayList<Map<String, Object>>();
	 List<Map<String, Object>> url4 = new ArrayList<Map<String, Object>>();
	 List<Map<String, Object>> url5 = new ArrayList<Map<String, Object>>();
	 List<Map<String, Object>> url6 = new ArrayList<Map<String, Object>>();
	 List<Map<String, Object>> url7 = new ArrayList<Map<String, Object>>();
	 List<Map<String, Object>> url8 = new ArrayList<Map<String, Object>>();      //后补资料
         
        for (int i = 0; i < list.size(); i++) {
						Map<String, Object> maps = list.get(i);
						String type = MapUtils.getString(maps, "type");
						if ("A".equals(type) ){
							cun.add(maps);
							num1++;
						}
						if ("B".equals(type) ){
							cun.add(maps);
							num2++;
						}
						if ("D".equals(type) ){
							url3.add(maps);
							num3++;
						}
						if ("T".equals(type) ){
							url4.add(maps);
							num4++;
						}
						if ("V".equals(type) ){
							url5.add(maps);
							num5++;
						}
						if ("W".equals(type) ){
							url6.add(maps);
							num6++;
						}
						if ("X".equals(type) ){
							url7.add(maps);
							num7++;
						}
						if ("N".equals(type) ){
							url8.add(maps);
							num8++;
						}
					}
                     if (num1>1||num2>1) {
						respStatus.setMsg("借款人正反面证件只能上传一张!");
						return respStatus; 
					}
                if (num3>1) {
					String name3 =getBusinfoToPdf(url3);
					Map<String,Object> ss  = new HashMap<>();
					for (int i = 0; i < url3.size(); i++) {
						ss.putAll(url3.get(i));
					}
					String url = MapUtils.getString(ss,"url");
					url = name3;
					ss.put("url",url);
					cun.add(ss);
					}else if(num3==1){
					cun.add(url3.get(0));
					}
				     if (num4>1) {
				    String name4 =getBusinfoToPdf(url4);
				    Map<String,Object> ss  = new HashMap<>();
					for (int i = 0; i < url4.size(); i++) {
						ss.putAll(url4.get(i));
					}
					String url = MapUtils.getString(ss,"url");
					url = name4;
					ss.put("url",url);
					cun.add(ss);
				     }else if(num4==1){
						cun.add(url4.get(0));
					}
				    if (num5>1) {
				    String name5 =	getBusinfoToPdf(url5);
				    Map<String,Object> ss  = new HashMap<>();
					for (int i = 0; i < url5.size(); i++) {
						ss.putAll(url5.get(i));
					}
					String url = MapUtils.getString(ss,"url");
					url = name5;
					ss.put("url",url);
					cun.add(ss);
					}else if(num5==1){
						cun.add(url5.get(0));
					}
				    if (num6>1) {
				    String name6 =getBusinfoToPdf(url6);
				    Map<String,Object> ss  = new HashMap<>();
					for (int i = 0; i < url6.size(); i++) {
						ss.putAll(url6.get(i));
					}
					String url = MapUtils.getString(ss,"url");
					url = name6;
					ss.put("url",url);
					cun.add(ss);
					}else if(num6==1){
						cun.add(url6.get(0));
					}
				    if (num7>1) {
				    String name7 =getBusinfoToPdf(url7);
				    Map<String,Object> ss  = new HashMap<>();
					for (int i = 0; i < url7.size(); i++) {
						ss.putAll(url7.get(i));
					}
					String url = MapUtils.getString(ss,"url");
					url = name7;
					ss.put("url",url);
					cun.add(ss);
					}else if(num7==1){
						cun.add(url7.get(0));
					}
				    if (num8>1) {
					    String name8 =getBusinfoToPdf(url8);
					    Map<String,Object> ss  = new HashMap<>();
						for (int i = 0; i < url8.size(); i++) {
							ss.putAll(url8.get(i));
						}
						String url = MapUtils.getString(ss,"url");
						url = name8;
						ss.put("url",url);
						cun.add(ss);
						}else if(num8==1){
							cun.add(url8.get(0));
						}
				    respStatus.setData(cun);
				    return respStatus;
       }
	/**
	 * 保存云南信托影像资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespStatus addImage(@RequestBody Map<String, Object> map) {
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = userApi.getUserDto();
			map.put("createUid", user.getUid());
			yntrustImageService.insert(map);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存云南信托影像资料异常:", e);
			RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}

	/**
	 * 批量保存云南信托影像资料
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public RespStatus batchAddImage(@RequestBody List<Map<String, Object>> list) {
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = userApi.getUserDto();
			for (Map<String, Object> m : list) {
				m.put("createUid", user.getUid());
			}
			yntrustImageService.batchInsert(list);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("批量保存云南信托影像资料异常:", e);
			RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}

	/**
	 * 查询信托专户余额
	 * 
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryTrustAccount(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			String ynProductCode = MapUtils.getString(map, "ynProductCode");
			if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(ynProductCode)) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.queryTrustAccount1(result, ynProductCode, orderNo);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询信托专户余额异常:", e);
		}
		return result;
	}

	/**
	 * 发送放款指令
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespStatus confirmPayment(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			if (!map.containsKey("orderNo")) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(),
					MapUtils.getString(map, "orderNo"), yntrustMappingService, yntrustRequestFlowService);
			yntrustAbutment.confirmPayment(result);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("云南信托发送放款指令异常:", e);
		}
		return result;
	}

	/**
	 * 放款状态查询 处理状态：放款中=0,成功=1,失败=2,业务不执行=3,异常 =4，放款指令发送失败=9
	 * 只有在查获“放款指令发送失败”状态，才能调用“补单放款接口”；
	 * 只有在放款查询中，查获“失败”状态，贷款服务机构才可考虑是否转其他通道放款。为避免重复放款，建议不要做自动切换通道放款。发生失败后，请先调用取消放款接口，再转其他通道放款。
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public RespDataObject<YntrustLoanDto> queryTradingStatus(@RequestBody YntrustLoanDto obj) {
		RespDataObject<YntrustLoanDto> result = new RespDataObject<YntrustLoanDto>();
		try {
			String orderNo = obj.getOrderNo();
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustLoanDto tmp = new YntrustLoanDto();
			tmp.setOrderNo(obj.getOrderNo());
			tmp.setCancelStatus(1);
			List<YntrustLoanDto> list = yntrustLoanService.list(tmp);
			YntrustLoanDto loan = null;
			if (null != list && list.size() > 0) {
				for (YntrustLoanDto yntrustLoanDto : list) {
					if ("2".equals(yntrustLoanDto.getAuditStatus())) {
						loan = yntrustLoanDto;
						break;
					}
				}
			}
			if (null != loan && "2".equals(loan.getAuditStatus()) && 1 == loan.getProcessStatus()) { // 放款状态为1成功时
				RespHelper.setSuccessDataObject(result, loan);
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.queryTradingStatus(result);

			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				loan = result.getData();
				YntrustContractDto contract = new YntrustContractDto();
				contract.setOrderNo(orderNo);
				contract.setLoanContractNumber(loan.getYnxtLoanContractNumber());
				yntrustContractService.update(contract);
				yntrustLoanService.delete(loan);
				yntrustLoanService.insert(loan);
			}
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询云南信托放款状态信息异常", e);
		}
		return result;
	}

	/**
	 * 获取合同信息电子签证的链接与二维码图片
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> getQRCode(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.getQRCode(result);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("获取云南信托合同信息电子签证的链接与二维码图片异常:", e);
		}
		return result;
	}

	/**
	 * 获取云信签章合同文件
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> getContractFile(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.getContractFile(result, map);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("获取云信签章合同文件异常:", e);
		}
		return result;
	}

	/**
	 * 下载云信签章合同文件
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public String downloadContractFile(String orderNo, HttpServletResponse res) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			map.put("isGetFileContent", "1");
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.getContractFile(result, map);
			Map<String, Object> responseMap = result.getData();
			if(!MapUtils.getString(responseMap, "SignStatus","").equals("4")) {
				return "合同未签约成功";
			}
			String base64 = MapUtils.getString(responseMap, "ContractFile","");
			res.setHeader("content-type", "application/octet-stream");
			res.setContentType("application/octet-stream");
			res.setHeader("Content-Disposition", "attachment;filename=" + orderNo + ".pdf");
			byte[] bytes = Base64.getDecoder().decode(base64);
			BufferedInputStream bis = null;
			OutputStream os = null;
			try {
				os = res.getOutputStream();
				os.write(bytes);
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("获取云信签章合同文件异常:", e);
		}
		return "";
	}

	/**
	 * 保存应还款计划并推送
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public RespStatus addRepaymentPlan(@RequestBody YntrustRepaymentPlanDto obj) {
		RespStatus result = new RespStatus();
		try {
			if (StringUtils.isBlank(obj.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			obj.setCreateUid(user.getUid()); // 创建人uid
			obj.setUpdateUid(user.getUid()); // 更新人uid
			if (yntrustRepaymentPlanService.select(obj) == null) {
				obj.setPushStatus(0); // 初始化应还款计划状态
				yntrustRepaymentPlanService.insert(obj);
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), obj.getOrderNo(),
					yntrustMappingService, yntrustRequestFlowService);
			yntrustAbutment.createRepaySchedule(result, obj);
			yntrustRepaymentPlanService.update(obj);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("推送云南信托应还款计划异常:", e);
		}
		return result;
	}

	/**
	 * 保存回款计划的还款信息
	 * 
	 * @param info
	 * @return
	 */
	@Override
	public RespStatus addRepaymentInfoAndPayInfo(@RequestBody YntrustRepaymentInfoDto info) {
		RespStatus result = new RespStatus();
		if (null == info || StringUtils.isBlank(info.getOrderNo())) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
			return result;
		}
		try {

			// 预计还款时间与实际还款时间不一致。则需要变更计划
			if (DateUtils.dateDiff(info.getRepayDate(), info.getRealityPayDate()) < 0) {
				YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
				plan.setOrderNo(info.getOrderNo());
				plan.setRepayPrincipal(info.getRepayPrincipal());
				plan.setRepayProfit(info.getGivePayProfit());
				Integer borrowingDays = DateUtils.getDiffDays(info.getRepayDate(), info.getRealityPayDate())
						+ info.getBorrowingDays();
				plan.setBorrowingDays(borrowingDays);
				plan.setRepayDate(info.getRealityPayDate());
				plan.setChangeReason("2");
				result = updateRepaySchedule(plan);
				if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
					return result;
				}
			}

			YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
			plan.setOrderNo(info.getOrderNo());
			plan = yntrustRepaymentPlanService.select(plan);
			if (null != plan && StringUtils.isNotBlank(plan.getPartnerScheduleNo())) {
				info.setPartnerScheduleNo(plan.getPartnerScheduleNo());
			} else {
				info.setPartnerScheduleNo(UidUtils.generateOrderId());
			}

			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), info.getOrderNo(),
					yntrustMappingService, yntrustRequestFlowService);
			yntrustAbutment.repaymentOrderInfo(result, info);
			if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
				info.setPushStatus(2);
				return result;
			} else {
				info.setPushStatus(1);
			}
			yntrustRepaymentInfoService.insert(info);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, "保存还款信息失败");
			log.error("保存回款计划还款信息异常:", e);
		}
		return result;
	}

	/**
	 * 还款状态查询
	 * 
	 * @param info
	 * @return
	 */
	@Override
	public RespDataObject<YntrustRepaymentInfoDto> queryRepayOrder(@RequestBody YntrustRepaymentInfoDto info) {
		RespDataObject<YntrustRepaymentInfoDto> result = new RespDataObject<YntrustRepaymentInfoDto>();
		try {
			String orderNo = info.getOrderNo();
			if (StringUtils.isBlank(info.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			info = yntrustRepaymentInfoService.select(info);
			if (info == null) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
				return result;
			} else if (null != info && null != info.getStatus() && (7 == info.getStatus() || 6 == info.getStatus())) {
				RespHelper.setSuccessDataObject(result, info);
				return result;
			}
			RespStatus tempResp = new RespStatus();
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.queryRepayOrder(tempResp, info, yntrustRepaymentInfoService, null);
			if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
				RespHelper.setFailRespStatus(result, result.getMsg());
				return result;
			}
			info = yntrustRepaymentInfoService.select(info);
			result.setData(info);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("还款订单状态查询异常:", e);
		}
		return result;
	}

	/**
	 * 订单支付
	 * 
	 * @param pay
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> paymentOrder(@RequestBody YntrustRepaymentPayDto pay) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = pay.getOrderNo();
			if (StringUtils.isBlank(pay.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			pay.setCreateUid(user.getUid());
			pay.setUpdateUid(user.getUid());

			YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
			info.setOrderNo(orderNo);
			info = yntrustRepaymentInfoService.select(info);
			if (null == info || StringUtils.isBlank(info.getTransactionNo())) {
				RespHelper.setFailDataObject(result, null, "没有查询到该订单的交易编号transactionNo");
				return result;
			} else {
				pay.setTransactionNo(info.getTransactionNo());
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(user, orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.paymentOrder(result, pay);
			if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
				pay.setPushStatus(2);
			} else {
				pay.setPushStatus(1);
				result.setCode(RespStatusEnum.SUCCESS.getCode());
				result.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			yntrustRepaymentPayService.insert(pay);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("云南信托订单支付异常:", e);
		}
		return result;
	}

	/**
	 * 支付状态查询
	 * 
	 * @param pay
	 * @return
	 */
	@Override
	public RespDataObject<YntrustRepaymentPayDto> queryPaymentOrder(@RequestBody YntrustRepaymentPayDto pay) {
		RespDataObject<YntrustRepaymentPayDto> result = new RespDataObject<YntrustRepaymentPayDto>();
		try {
			if (StringUtils.isBlank(pay.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustRepaymentPayDto tmp = yntrustRepaymentPayService.select(pay);
			if (null == tmp) {
				RespHelper.setFailDataObject(result, pay, RespStatusEnum.FAIL.getMsg());
				return result;
			}
			if ("1".equals(tmp.getStatus()) || "7".equals(tmp.getStatus())) {
				RespHelper.setSuccessDataObject(result, tmp);
				return result;
			}
			YntrustRepaymentInfoDto tmps = new YntrustRepaymentInfoDto();
			tmps.setOrderNo(pay.getOrderNo());
			tmps = yntrustRepaymentInfoService.select(tmps);
			RespStatus tempResp = new RespStatus();
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), pay.getOrderNo(),
					yntrustMappingService, yntrustRequestFlowService);
			yntrustAbutment.queryRepayOrder(tempResp, tmps, yntrustRepaymentInfoService, yntrustRepaymentPayService);
			if (!RespStatusEnum.FAIL.getCode().equals(tempResp.getCode())) {
				RespHelper.setSuccessDataObject(result, tmp);
			} else {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			}
			System.out.println(result);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("订单支付状态查询异常:", e);
		}
		return result;
	}

	/**
	 * 取消贷款
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespStatus cancelLoan(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), orderNo, yntrustMappingService,
					yntrustRequestFlowService);
			yntrustAbutment.cancelLoan(result, orderNo);
			if (!RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
				YntrustBorrowDto borrowDto = new YntrustBorrowDto();
				borrowDto.setOrderNo(orderNo);
				borrowDto.setPushStatus(0);
				yntrustBorrowService.update(borrowDto);
				YntrustContractDto contractDto = new YntrustContractDto();
				contractDto.setOrderNo(orderNo);
				contractDto.setPushStatus(0);
				yntrustContractService.update(contractDto);
				YntrustLoanDto loan = new YntrustLoanDto();
				loan.setOrderNo(orderNo);
				loan.setAuditStatus("1");
				loan.setCancelStatus(-1);
				loan.setProcessStatus(0);
				yntrustLoanService.updateByOrderNo(loan);
				YntrustMappingDto obj = new YntrustMappingDto();
				obj.setOrderNo(orderNo);
				obj.setStatus(-1);
				yntrustMappingService.update(obj);
				map.put("isPlus", 0);
				yntrustImageService.updateIsPush(map);
				RespHelper.setSuccessRespStatus(result);
			}
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("取消贷款异常:", e);
		}
		return result;
	}

	/**
	 * 更新还款计划
	 * 
	 * @param plan
	 * @return
	 */
	@Override
	public RespStatus updateRepaySchedule(@RequestBody YntrustRepaymentPlanDto plan) {
		RespStatus result = new RespStatus();
		try {
			if (StringUtils.isBlank(plan.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustRepaymentPlanDto tmp = new YntrustRepaymentPlanDto();
			tmp.setOrderNo(plan.getOrderNo());
			tmp = yntrustRepaymentPlanService.select(plan);
			if (null == tmp) {
				RespHelper.setFailRespStatus(result, "没有获取到与该订单号应还款计划信息");
				return result;
			} else {
				plan.setPartnerScheduleNo(tmp.getPartnerScheduleNo());
			}
			if (null == plan.getRepayDate()) {
				RespHelper.setFailRespStatus(result, "还款计划时间不能为空");
				return result;
			}
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), plan.getOrderNo(),
					yntrustMappingService, yntrustRequestFlowService);
			/**
			 * map包含两个key changeReason:还款计划变更原因(0=项目结清,1=提前部分还款,2=错误更正),
			 * scheduleType:还款计划类型(如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型)
			 */
			yntrustAbutment.updateRepaySchedule(result, plan.getOrderNo(), plan);
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				yntrustRepaymentPlanService.update(plan);
			}
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("更新还款计划异常:", e);
		}
		return result;
	}

	/**
	 * 更新逾期费用
	 * 
	 * @param info
	 * @return
	 */
	@Override
	public RespStatus updateOverDueFee(@RequestBody YntrustRepaymentInfoDto info) {
		RespStatus result = new RespStatus();
		try {
			if (StringUtils.isBlank(info.getOrderNo())) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			yntrustRepaymentInfoService.insert(info);
			YntrustAbutment yntrustAbutment = new YntrustAbutment(userApi.getUserDto(), info.getOrderNo(),
					yntrustMappingService, yntrustRequestFlowService);
			YntrustRepaymentPlanDto yntrustRepaymentPlanDto = new YntrustRepaymentPlanDto();
			yntrustRepaymentPlanDto.setOrderNo(info.getOrderNo());
			yntrustRepaymentPlanDto = yntrustRepaymentPlanService.select(yntrustRepaymentPlanDto);
			yntrustAbutment.updateOverDueFee(result, info, yntrustRepaymentPlanDto.getPartnerScheduleNo());
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("更新云南信托逾期费用异常:", e);
		}
		return result;
	}

	/**
	 * 查询云南信托推送的借款人与合同信息与影像资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> selectBorrowContract(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustBorrowDto borrow = new YntrustBorrowDto();
			borrow.setOrderNo(orderNo);
			//borrow.setPushStatus(1);
			borrow = yntrustBorrowService.select(borrow);
			YntrustContractDto contract = new YntrustContractDto();
			contract.setOrderNo(orderNo);
			//contract.setPushStatus(1);
			contract = yntrustContractService.select(contract);
			Map<String, Object> img = yntrustImageService.listByMap(map);
			Map<String, Object> returnParam = new HashMap<String, Object>();
			YntrustMappingDto mapping = new YntrustMappingDto();
			mapping.setOrderNo(orderNo);
			mapping = yntrustMappingService.select(mapping);
			if (null != mapping) {
				if (StringUtils.isBlank(mapping.getYnProductName())) {
					borrow.setYnProductName("一期");
				} else {
					borrow.setYnProductName(mapping.getYnProductName());
					borrow.setYnProductCode(mapping.getYnProductCode());
				}
				borrow.setUniqueId(mapping.getUniqueId());
			}
			returnParam.put("borrow", borrow);
			returnParam.put("contract", contract);
			returnParam.put("img", img);
			RespHelper.setSuccessDataObject(result, returnParam);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询云南信托推送的借款人与合同信息异常:", e);
		}
		return result;
	}

	/**
	 * 查询云南信托推送的影像资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> selectBusinfo(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			Map<String, Object> img = yntrustImageService.listByMap(map);
			RespHelper.setSuccessDataObject(result, img);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询云南信托推送的影像资料:", e);
		}
		return result;
	}

	/**
	 * 查询应还款计划
	 * 
	 * @param plan
	 * @return
	 */
	@Override
	public RespDataObject<YntrustRepaymentPlanDto> selectRepaymentPlan(@RequestBody YntrustRepaymentPlanDto plan) {
		RespDataObject<YntrustRepaymentPlanDto> result = new RespDataObject<YntrustRepaymentPlanDto>();
		try {
			if (StringUtils.isBlank(plan.getOrderNo())) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			plan = yntrustRepaymentPlanService.select(plan);
			if (plan != null && 1 == plan.getPushStatus()) { // 推送成功才会返回查询数据
				RespHelper.setSuccessDataObject(result, plan);
				return result;
			}
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询应还款计划异常:", e);
		}
		return result;
	}

	@Override
	public RespDataObject<Map<String, Object>> selectRepaymentPlanMap(@RequestBody YntrustRepaymentPlanDto plan) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			if (StringUtils.isBlank(plan.getOrderNo())) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustContractDto contract = new YntrustContractDto();
			contract.setOrderNo(plan.getOrderNo());
			contract = yntrustContractService.select(contract);
			plan = yntrustRepaymentPlanService.select(plan);
			if (1 == plan.getPushStatus()) { // 推送成功才会返回查询数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("plan", plan);
				map.put("contract", contract);
				RespHelper.setSuccessDataObject(result, map);
			}
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询应还款计划异常:", e);
		}
		return result;
	}

	/**
	 * 查询回款计划还款信息与支付信息
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> selectRepaymentInfoAndPay(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			if (StringUtils.isBlank(orderNo)) {
				RespHelper.setFailDataObject(result, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
			info.setOrderNo(orderNo);
			info = yntrustRepaymentInfoService.select(info);
			YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
			pay.setOrderNo(orderNo);
			pay = yntrustRepaymentPayService.select(pay);
			map.put("info", info);
			map.put("pay", pay);
			RespHelper.setSuccessDataObject(result, map);
		} catch (Exception e) {
			RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
			log.error("查询回款计划还款信息与支付信息异常:", e);
		}
		return result;
	}

	/**
	 * 删除影像资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespStatus deleteImg(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			Integer id = MapUtils.getInteger(map, "id");
			if (null == id) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			map.put("updateUid", user.getUid());
			map.put("ids", id);
			yntrustImageService.delete(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("删除云南信托影像资料异常:", e);
		}
		return result;
	}

	/**
	 * 批量删除影像资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public RespStatus batchDeleteImg(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			if (!map.containsKey("ids") || null == MapUtils.getObject(map, "ids")) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			map.put("updateUid", user.getUid());
			yntrustImageService.batchDelete(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			log.error("批量删除云南信托影像资料异常:", e);
		}
		return result;
	}

	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Override
	public RespStatus test() {
		RespStatus result = new RespStatus();
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		return result;
	}

	@Override // 新增云南节点状态返回
	public RespDataObject<Map<String, Object>> ynStuats(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> stuatsMap = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			String ynProductCode = MapUtils.getString(map, "ynProductCode"); // 云南产品编号

			YntrustBorrowDto borrowDto = new YntrustBorrowDto(); // 信息推送
			borrowDto.setOrderNo(orderNo);
			borrowDto.setYnProductCode(ynProductCode);
			borrowDto = yntrustBorrowService.select(borrowDto);

			Map<String, Object> maps = new HashMap<String, Object>();
			Map<String, Object> ma = null;
			if (borrowDto == null || borrowDto.getPushStatus() == null) { // 资料从未推送过
				ma = new HashMap<String, Object>();
				ma.put("code", "4");
				ma.put("msg", "待推送资料");
				maps.put("pushStatus", ma);
				stuatsMap.setData(maps);
				stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
				stuatsMap.setMsg("此订单号还未提交审批");
			} else if (0 == borrowDto.getPushStatus()) { // 此订单号正在初始化
				ma = new HashMap<String, Object>();
				ma.put("code", borrowDto.getPushStatus());
				ma.put("msg", "待推送资料");
				maps.put("pushStatus", ma);
				stuatsMap.setData(maps);
				stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
				stuatsMap.setMsg("待推送资料");
			} else if (1 != borrowDto.getPushStatus()) {
				ma = new HashMap<String, Object>();
				ma.put("code", borrowDto.getPushStatus());
				ma.put("msg", "推送失败");
				maps.put("pushStatus", ma);
				stuatsMap.setData(maps);
				stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
				stuatsMap.setMsg("推送失败");
			} else if (1 == borrowDto.getPushStatus()) {
				// 放款状态查询
				YntrustLoanDto loan = new YntrustLoanDto();
				loan.setOrderNo(orderNo);
				loan.setYnProductCode(ynProductCode);
				queryTradingStatus(loan);

				YntrustLoanDto loanDto = new YntrustLoanDto(); // 放款信息
				loanDto.setOrderNo(orderNo);
				loanDto.setYnProductCode(ynProductCode);
				List<YntrustLoanDto> lists = yntrustLoanService.list(loanDto);

				if (0 >= lists.size()) { // 还未到放款节点
					ma = new HashMap<String, Object>();
					ma.put("code", borrowDto.getPushStatus());
					ma.put("msg", "待资方放款");
					maps.put("pushStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("待资方放款");

					ma = new HashMap<String, Object>();
					ma.put("code", borrowDto.getPushStatus());
					ma.put("msg", "待资方放款");
					maps.put("processStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("待资方放款");
					return stuatsMap;
				}
				loanDto = lists.get(0); // 已到放款节点
				if (0 == loanDto.getProcessStatus()) {
					ma = new HashMap<String, Object>();
					ma.put("code", loanDto.getProcessStatus());
					ma.put("actExcutedTime", loanDto.getActExcutedTime());
					ma.put("msg", "放款中");
					maps.put("processStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("放款中");
				} else if (1 == loanDto.getProcessStatus()) { // 放款成功

					ma = new HashMap<String, Object>();
					ma.put("code", borrowDto.getPushStatus());
					ma.put("msg", "待推送应还款计划");
					maps.put("pushStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("待推送应还款计划");

					ma = new HashMap<String, Object>();
					ma.put("actExcutedTime", loanDto.getActExcutedTime());
					ma.put("msg", "放款成功");
					maps.put("processStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("放款成功");

					YntrustRepaymentPlanDto planDao = new YntrustRepaymentPlanDto();// 查询应还款计划表
					planDao.setOrderNo(orderNo);
					planDao = yntrustRepaymentPlanService.select(planDao);
					if (planDao == null) {
						return stuatsMap;
					}
					if (0 == planDao.getPushStatus()) { // 初始化
						ma = new HashMap<String, Object>();
						ma.put("msg", "待推送应还款");
						maps.put("planStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("待推送应还款");
					} else if (2 == planDao.getPushStatus()) { // 推送失败
						ma = new HashMap<String, Object>();
						ma.put("msg", "待推送应还款计划");
						maps.put("planStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("待推送应还款计划");

						ma = new HashMap<String, Object>();
						ma.put("msg", "待推送应还款计划");
						maps.put("pushStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("待推送应还款计划");
					} else if (1 == planDao.getPushStatus()) { // 推送成功
						ma = new HashMap<String, Object>();
						ma.put("msg", "推送应还款成功");
						maps.put("planStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("推送应还款成功");

						ma = new HashMap<String, Object>();
						ma.put("msg", "待推送回款计划");
						maps.put("pushStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("待推送回款计划");

						ma = new HashMap<String, Object>();
						ma.put("msg", "待推送回款计划");
						maps.put("repaymentStatus", ma);
						stuatsMap.setData(maps);
						stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
						stuatsMap.setMsg("待推送回款计划");

						YntrustRepaymentInfoDto repaymentInfoDto = new YntrustRepaymentInfoDto();
						repaymentInfoDto.setOrderNo(orderNo);
						repaymentInfoDto.setYnProductCode(ynProductCode);

						// 回款状态
						if (RespStatusEnum.FAIL.getCode().equals(queryRepayOrder(repaymentInfoDto).getCode())) {
							return stuatsMap;
						} else {
							repaymentInfoDto = yntrustRepaymentInfoService.select(repaymentInfoDto);
						}

						if (null == repaymentInfoDto.getStatus() || 0 == repaymentInfoDto.getStatus()
								|| repaymentInfoDto.getStatus().equals(" ")) { // 支付状态等于0 已创建
							ma = new HashMap<String, Object>();
							ma.put("code", borrowDto.getPushStatus());
							ma.put("msg", "待资方校验");
							maps.put("pushStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("待资方校验");

							ma = new HashMap<String, Object>();
							ma.put("code", repaymentInfoDto.getStatus());
							ma.put("msg", "已创建");
							maps.put("repaymentStatus", ma);

							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("已创建");
						} else if (1 == repaymentInfoDto.getStatus()) { // 支付状态等于1 检验中
							ma = new HashMap<String, Object>();
							ma.put("code", borrowDto.getPushStatus());
							ma.put("msg", "待资方校验");
							maps.put("pushStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("待资方校验");

							ma = new HashMap<String, Object>();
							ma.put("code", repaymentInfoDto.getStatus());
							ma.put("msg", "检验中");
							maps.put("repaymentStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("检验中");

						} else if (2 == repaymentInfoDto.getStatus()) { // 支付状态等于2 检验失败
							ma = new HashMap<String, Object>();
							ma.put("code", repaymentInfoDto.getPushStatus());
							ma.put("msg", "检验失败");
							maps.put("repaymentStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("检验失败");

						} else if (3 <= repaymentInfoDto.getStatus()) { // 支付状态等于3 检验成功
							ma = new HashMap<String, Object>();
							ma.put("code", borrowDto.getPushStatus());
							ma.put("msg", "待推送支付信息");
							maps.put("pushStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("待推送支付信息");

							ma = new HashMap<String, Object>();
							ma.put("code", repaymentInfoDto.getStatus());
							ma.put("msg", "检验成功");
							maps.put("repaymentStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("检验成功");
							YntrustRepaymentPayDto repaymentPayDto = new YntrustRepaymentPayDto();
							repaymentPayDto.setOrderNo(orderNo);
							repaymentPayDto.setYnProductCode(ynProductCode);

							ma = new HashMap<String, Object>();
							ma.put("msg", "待支付");
							maps.put("payStatus", ma);
							stuatsMap.setData(maps);
							stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
							stuatsMap.setMsg("待支付");

							// 支付状态
							if (RespStatusEnum.FAIL.getCode().equals(queryPaymentOrder(repaymentPayDto).getCode())) {
								return stuatsMap;
							} else {
								repaymentPayDto = yntrustRepaymentPayService.select(repaymentPayDto);
							}

							if ("0".equals(repaymentPayDto.getStatus())) {
								ma = new HashMap<String, Object>();
								ma.put("code", borrowDto.getPushStatus());
								ma.put("msg", "待资方核验");
								maps.put("pushStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("待资方核验");

								ma = new HashMap<String, Object>();
								ma.put("code", repaymentPayDto.getStatus());
								ma.put("msg", "支付中");
								maps.put("payStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("支付中");
							} else if ("1".equals(repaymentPayDto.getStatus())) {

								ma = new HashMap<String, Object>();
								ma.put("code", borrowDto.getPushStatus());
								ma.put("msg", "核销成功");
								maps.put("pushStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("核销成功");

								ma = new HashMap<String, Object>();
								ma.put("msg", "核销成功");
								maps.put("payStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("核销成功");

							} else {
								ma = new HashMap<String, Object>();
								ma.put("msg", "核销失败");
								maps.put("payStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("核销失败");

								ma = new HashMap<String, Object>();
								ma.put("msg", "核销失败");
								maps.put("pushStatus", ma);
								stuatsMap.setData(maps);
								stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
								stuatsMap.setMsg("核销失败");
							}
						}
					}

				} else if (2 == loanDto.getProcessStatus() || 3 == loanDto.getProcessStatus()
						|| 4 == loanDto.getProcessStatus() || 9 == loanDto.getProcessStatus()) {
					ma = new HashMap<String, Object>();
					ma.put("msg", "放款失败");
					maps.put("processStatus", ma);
					stuatsMap.setData(maps);
					stuatsMap.setCode(RespStatusEnum.SUCCESS.getCode());
					stuatsMap.setMsg("放款失败");
				}

			}

		} catch (Exception e) {
			log.error("ynStuats返回状态接口失败", e);
			RespHelper.setFailDataObject(stuatsMap, null, "系统异常，返回失败");
		}

		return stuatsMap;
	}

	
	     @SuppressWarnings("unchecked")
		public String getBusinfoToPdf(@RequestBody  List<Map<String,Object>> list) {	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			System.err.println("跳转路径打印"+Constants.LINK_ANJBO_FS_URL + "/fs/pdf/creatPDF");
			//return SingleUtils.getRestTemplate(120).postForObject("http://localhost:8082/" + "anjbo-fs/fs/pdf/creatPDF", SingleUtils.getHttpEntity(list,headers), String.class);
			return SingleUtils.getRestTemplate(120).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/pdf/creatPDF", SingleUtils.getHttpEntity(list,headers), String.class);
			
		}


	   
	    
	 
}
