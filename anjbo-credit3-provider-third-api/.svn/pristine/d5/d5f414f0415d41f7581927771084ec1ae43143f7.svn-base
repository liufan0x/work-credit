package com.anjbo.utils.yntrust;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.yntrust.YntrustLoanDto;
import com.anjbo.bean.yntrust.YntrustMappingDto;
import com.anjbo.bean.yntrust.YntrustRepaymentInfoDto;
import com.anjbo.bean.yntrust.YntrustRepaymentPayDto;
import com.anjbo.bean.yntrust.YntrustRepaymentPlanDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.controller.api.DataApi;
import com.anjbo.service.yntrust.YntrustMappingService;
import com.anjbo.service.yntrust.YntrustRepaymentInfoService;
import com.anjbo.service.yntrust.YntrustRepaymentPayService;
import com.anjbo.service.yntrust.YntrustRequestFlowService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.SingleUtils;
import com.anjbo.utils.UidUtil;
import com.anjbo.utils.UidUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YntrustAbutment {

	private Logger log = Logger.getLogger(getClass());

	private YntrustUtils yntrustUtils = null;

	private YntrustMappingService yntrustMappingService;

	private String orderNo;

	private UserDto userDto;

	private DataApi dataApi;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public YntrustAbutment(UserDto userDto, String orderNo, YntrustMappingService yntrustMappingService,
			YntrustRequestFlowService yntrustRequestFlowService) {
		this.userDto = userDto;
		this.orderNo = orderNo;
		this.yntrustMappingService = yntrustMappingService;
		yntrustUtils = new YntrustUtils(yntrustRequestFlowService);
	}

	public void setDataApi(DataApi dataApi) {
		this.dataApi = dataApi;
	}

	/**
	 * 创建借款人
	 */
	private static String CBACTION = "/Loan/CreateBorrower";
	/**
	 * 创建订单：借款人与合同一体化接口
	 */
	private static String COACTION = "/Loan/Borrower/Createorder";
	/**
	 * 创建借款合同
	 */
	private static String CLACTION = "/Loan/CreateLoan";
	/**
	 * 创建还款计划
	 */
	private static String CRSACTION = "/Loan/CreateRepaySchedule";
	/**
	 * 更新还款计划
	 */
	private static String UPDATE_REPAY_SCHEDULE = "/Loan/UpdateRepaySchedule";
	/**
	 * 上传文件
	 */
	private static String FUACTION = "/Loan/FileUpload";
	/**
	 * 上传身份证
	 */
	private static String IUACTION = "/Loan/ImageUpload";
	/**
	 * 发送放款指令
	 */
	private static String CPACTION = "/Loan/ConfirmPayment";
	/**
	 * 补单放款指令
	 */
	private static String RPACTION = "/Loan/RemedyPayment";
	/**
	 * 放款状态查询
	 */
	private static String QTSACTION = "/Loan/QueryTradingStatus";
	/**
	 * 取消贷款
	 */
	private static String CCLACTION = "/Loan/CancelLoan";
	/**
	 * 查询流水记录
	 */
	private static String QBSACTION = "/Loan/QueryTrustAccountStatement";

	/**
	 * 信托专户查询 查询余额
	 */
	private static String QUERY_TRUST_ACCOUNT = "/Loan/QueryTrustAccount";

	/**
	 * 查询可放余额接口
	 */
	private static String QUERY_TRUST_ACCOUNT1 = "/pms/QueryingBalance";
	
	/**
	 * 获取二维码接口
	 */
	private static String GET_QRCODE = "/Loan/GetQRCode";
	/**
	 * 获取云信签章合同文件
	 */
	private static String GET_CONTRACT_FILE = "/Loan/GetContractFile";
	/**
	 * 还款订单信息接口
	 */
	private static String REPAYMENT_ORDER_INFO = "/Loan/RepaymentOrderInfo";
	/**
	 * 还款订单查询接口
	 */
	private static String QUERY_REPAY_ORDER = "/Loan/QueryRepayOrder";
	/**
	 * 订单支付接口
	 */
	private static String PAYMENTORDER = "/Loan/PaymentOrder";
	/**
	 * 订单支付状态查询接口
	 */
	private static String QUERY_PAYMENT_ORDER = "/Loan/QueryPaymentOrder";
	/**
	 * 更新逾期费用
	 */
	private static String UPDATE_OVERDUEFEE = "/Loan/UpdateOverDueFee";
	/**
	 * 修改还款信息
	 */
	private static String MODIFY_REPAY = "/Loan/ModifyRepay";
	/**
	 * 放款对账查询
	 */
	private static String LOAN_INFO = "/Loan/LoanInfo";
	/**
	 * 查询最新的还款计划
	 */
	private static String QUERY_REPAY_SCHEDULE = "/Loan/QueryRepaySchedule";

	final Lock lock = new ReentrantLock();

	/**
	 * 信托专户查询 查询余额
	 * 
	 * @param result
	 *            返回信息
	 * @param orderNo
	 *            订单号
	 * @param ynProductCode
	 *            产品编号
	 */
	@SuppressWarnings("unchecked")
	public void queryTrustAccount(RespDataObject<Map<String, Object>> result, String ynProductCode, String orderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		String ynProductName = "";
		if (ynProductCode.equals(ThirdApiConstants.YNTRUST_PRODUCT_CODE_ONE)) {
			param.put("productCode", ThirdApiConstants.YNTRUST_PRODUCT_CODE_ONE);
			param.put("accountNo", ThirdApiConstants.YNTRUST_ACCOUNT_NO_ONE);// 信托专户账号
			ynProductName = "一期";
		} else {
			param.put("productCode", ThirdApiConstants.YNTRUST_PRODUCT_CODE_TWO);
			param.put("accountNo", ThirdApiConstants.YNTRUST_ACCOUNT_NO_TWO);// 信托专户账号
			ynProductName = "二期";
		}
		Map<String, Object> responseMsg = yntrustUtils.postForObject(param, Map.class, QUERY_TRUST_ACCOUNT,
				"信托专户查询 查询余额", orderNo, result, userDto.getUid());
		Map<String, Object> dataMaptmp = null;
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
			dataMaptmp = new HashMap<String, Object>();
			dataMaptmp.put("Balance", "查询异常");
			dataMaptmp.put("ynProductName", ynProductName);
		} else {
			dataMaptmp = MapUtils.getMap(responseMsg, "Data");
			if (MapUtils.isNotEmpty(dataMaptmp)) {
				Double balance = MapUtils.getDouble(dataMaptmp, "Balance");
				BigDecimal bigDecimal = new BigDecimal(Double.toString(null == balance ? 0d : balance));
				BigDecimal divide = bigDecimal.divide(new BigDecimal("10000"));
				dataMaptmp.put("Balance", divide);
				dataMaptmp.put("ynProductName", ynProductName);
			}
		}
		RespHelper.setSuccessDataObject(result, dataMaptmp);
	}
	
	/**
	 * 查询可放余额接口
	 * 
	 * @param result
	 *            返回信息
	 * @param orderNo
	 *            订单号
	 * @param ynProductCode
	 *            产品编号
	 */
	@SuppressWarnings("unchecked")
	public void queryTrustAccount1(RespDataObject<Map<String, Object>> result, String ynProductCode, String orderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		String ynProductName = "";
		if (ynProductCode.equals(ThirdApiConstants.YNTRUST_PRODUCT_CODE_ONE)) {
			param.put("productCode", ThirdApiConstants.YNTRUST_PRODUCT_CODE_ONE);
			param.put("accountNo", ThirdApiConstants.YNTRUST_ACCOUNT_NO_ONE);// 信托专户账号
			ynProductName = "一期";
		} else {
			param.put("productCode", ThirdApiConstants.YNTRUST_PRODUCT_CODE_TWO);
			param.put("accountNo", ThirdApiConstants.YNTRUST_ACCOUNT_NO_TWO);// 信托专户账号
			ynProductName = "二期";
		}
		Map<String, Object> responseMsg = yntrustUtils.postForObject(param, Map.class, QUERY_TRUST_ACCOUNT1,
				"信托专户查询 查询余额", orderNo, result, userDto.getUid());
		Map<String, Object> dataMaptmp = null;
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
			dataMaptmp = new HashMap<String, Object>();
			dataMaptmp.put("Balance", "查询异常");
			dataMaptmp.put("ynProductName", ynProductName);
		} else {
			dataMaptmp = MapUtils.getMap(responseMsg, "Data");
			if (MapUtils.isNotEmpty(dataMaptmp)) {
				Double balance = MapUtils.getDouble(dataMaptmp, "ReleaseBalance");
				BigDecimal bigDecimal = new BigDecimal(Double.toString(null == balance ? 0d : balance));
				BigDecimal divide = bigDecimal.divide(new BigDecimal("10000"));
				dataMaptmp.put("Balance", divide);
				dataMaptmp.put("ynProductName", ynProductName);
			}
		}
		RespHelper.setSuccessDataObject(result, dataMaptmp);
	}

	/**
	 * 推送借款信息，合同
	 * 
	 * @param result
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void plusYnTrustInfo(RespDataObject<String> result, Map<String, Object> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		String uniqueId = UidUtils.generateUUID();
		param.put("requestId", UidUtils.generateUUID()); // 请求唯一标示
		param.put("uniqueId", uniqueId); // 快鸽与云南信托全局唯一标示
		Object borrowObj = MapUtils.getObject(map, "borrow");
		Object contractObj = MapUtils.getObject(map, "contract");
		Map<String, Object> borrow = gson.fromJson(gson.toJson(borrowObj), Map.class);
		Map<String, Object> contract = gson.fromJson(gson.toJson(contractObj), Map.class);
		packagBorrowAndContract(param, borrow, contract);
		yntrustUtils.postForObject(param, Map.class, COACTION, "创建借款与合同信息", orderNo, result, userDto.getUid());
		if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
			YntrustMappingDto obj = new YntrustMappingDto();
			obj.setOrderNo(orderNo);
			obj.setStatus(1);
			obj.setUniqueId(uniqueId);
			obj.setYnProductCode(MapUtils.getString(borrow, "ynProductCode"));
			obj.setYnProductName(MapUtils.getString(borrow, "ynProductName"));
			yntrustMappingService.insert(obj);
			result.setData(uniqueId);
		}
	}

	/**
	 * 推送影像资料
	 * 
	 * @param result
	 * @param params
	 */
	public void pushImg(RespStatus result, Map<String, Object> params) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		if(yntrustMappingDto == null) {
			RespHelper.setFailRespStatus(result, "请先推借款信息");
			return;
		}
		Map<String, Object> imgparam = new HashMap<String, Object>();
		imgparam.put("imageContent", analyzeImg(MapUtils.getObject(params, "imgList"), result));
		imgparam.put("requestId", UidUtils.generateUUID());
		imgparam.put("uniqueId", yntrustMappingDto.getUniqueId());
		imgparam.put("iDCardNo", MapUtils.getString(params, "iDCardNo"));
		yntrustUtils.postForObject(imgparam, Map.class, FUACTION, "上传影像资料", orderNo, result, userDto.getUid());
	}

	/**
	 * 获取合同信息电子签证的链接与二维码图片
	 * 
	 * @param result
	 * @param orderNo
	 */
	public void getQRCode(RespDataObject<Map<String, Object>> result) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		Map<String, Object> responseMap = yntrustUtils.postForObject(param, Map.class, GET_QRCODE, "获取二维码", orderNo,
				result, userDto.getUid());
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("url", MapUtils.getString(responseMap, "QRUrl"));
		result.setData(tmp);
		RespHelper.setSuccessDataObject(result, tmp);
	}

	/**
	 * 获取云信签章合同文件
	 * 
	 * @param result
	 * @param orderNo
	 */
	public void getContractFile(RespDataObject<Map<String, Object>> result, Map<String, Object> map) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		param.put("isGetFileContent", MapUtils.getString(map, "isGetFileContent", "0"));
		Map<String, Object> responseMap = yntrustUtils.postForObject(param, Map.class, GET_CONTRACT_FILE, "获取云信签章合同文件",
				orderNo, result, userDto.getUid());
		String signStatus = MapUtils.getString(responseMap, "SignStatus");
		String signStatusName = mappingSignStatus(signStatus);
		responseMap.put("signStatusName", signStatusName);
		RespHelper.setSuccessDataObject(result, responseMap);
	}

	/**
	 * 放松放款指令
	 * 
	 * @param result
	 * @param orderNo
	 */
	public void confirmPayment(RespStatus result) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add(yntrustMappingDto.getUniqueId());
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueIdList", list);
		yntrustUtils.postForObject(param, Map.class, CPACTION, "发送放款指令", orderNo, result, userDto.getUid());
	}

	/**
	 * 放款查询
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public void queryTradingStatus(RespDataObject<YntrustLoanDto> result) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		Map<String, Object> responseMsg = yntrustUtils.postForObject(param, Map.class, QTSACTION, "放款查询", orderNo,
				result, userDto.getUid());
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode()))
			return;

		String ynxtLoanContactNumber = MapUtils.getString(responseMsg, "YnxtLoanContactNumber");

		String auditMessage = MapUtils.getString(responseMsg, "AuditMessage");
		String auditStatus = MapUtils.getString(responseMsg, "AuditStatus");
		String actExcutedTime;
		Object tmp = MapUtils.getObject(responseMsg, "PaymentDetails");
		YntrustLoanDto loan = null;
		if (null != tmp) {
			String json = gson.toJson(tmp);
			List<Map<String, Object>> tmpList = gson.fromJson(json, List.class);
			for (Map<String, Object> m : tmpList) {
				loan = new YntrustLoanDto();
				loan.setYnProductCode(yntrustMappingDto.getYnProductCode());
				loan.setYnProductName(yntrustMappingDto.getYnProductName());
				loan.setYnxtLoanContractNumber(ynxtLoanContactNumber);
				loan.setOrderNo(orderNo);
				loan.setCreateUid(userDto.getUid());
				loan.setAccountNo(MapUtils.getString(m, "AccountNo"));
				loan.setAmount(MapUtils.getDouble(m, "Amount"));
				loan.setBankSerialNo(MapUtils.getString(m, "BankSerialNo"));
				actExcutedTime = MapUtils.getString(m, "ActExcutedTime");
				if (StringUtils.isNotBlank(actExcutedTime)) {
					loan.setActExcutedTime(DateUtils.StringToDate(actExcutedTime, DateUtils.FMT_TYPE1));
				}
				loan.setAuditStatus(auditStatus);
				if (StringUtils.isNotBlank(auditStatus)) {
					String auditStatusName = "2".equals(auditStatus) ? "审核成功" : "3".equals(auditStatus) ? "作废" : "待审核";
					loan.setAuditStatusName(auditStatusName);
				}
				loan.setAuditMessage(auditMessage);
				loan.setName(MapUtils.getString(m, "Name"));
				loan.setProcessStatus(MapUtils.getInteger(m, "ProcessStatus"));
				String processStatusName = getProcessStatusName(loan.getProcessStatus());
				loan.setProcessStatusName(processStatusName);
				loan.setResult(MapUtils.getString(m, "Result"));
				result.setData(loan);
			}
			RespHelper.setSuccessRespStatus(result);
		}
	}

	/**
	 * 映射放款状态
	 * 
	 * @param processStatus
	 * @return
	 */
	public String getProcessStatusName(Integer processStatus) {
		String processStatusName = "";
		if (null == processStatus)
			return processStatusName;

		switch (processStatus) {
		case 0:
			processStatusName = "放款中";
			break;
		case 1:
			processStatusName = "成功";
			break;
		case 2:
			processStatusName = "失败";
			break;
		case 3:
			processStatusName = "业务不执行";
			break;
		case 4:
			processStatusName = "异常";
			break;
		case 9:
			processStatusName = "放款指令发送失败";
			break;
		}
		return processStatusName;
	}

	/**
	 * 创建应还款计划
	 * 
	 * @param result
	 */
	public void createRepaySchedule(RespStatus result, YntrustRepaymentPlanDto plan) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		packagRepaySchedule(param, plan, yntrustMappingDto.getUniqueId());
		yntrustUtils.postForObject(param, Map.class, CRSACTION, "创建还款计划", orderNo, result, userDto.getUid());
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
			plan.setPushStatus(2);
			RespHelper.setFailRespStatus(result, result.getMsg());
		} else {
			plan.setPushStatus(1);
			RespHelper.setSuccessRespStatus(result);
		}
	}

	/**
	 * 变更应还款计划
	 * 
	 * @param result
	 * @param plan
	 */
	public void updateRepaySchedule(RespStatus result, YntrustRepaymentPlanDto plan) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		String requestId = UidUtils.generateUUID();
		param.put("requestId", requestId);
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		param.put("changeReason", plan.getChangeReason());
		List<Map<String, Object>> repaySchedules = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String, Object> t1 = new HashMap<String, Object>();
		t1.put("repayDate", format.format(plan.getRepayDate()));// 还款时间
		String partnerScheduleNo = "";
		if (StringUtils.isBlank(plan.getPartnerScheduleNo())) {
			partnerScheduleNo = UidUtils.generateOrderId();
			t1.put("partnerScheduleNo", partnerScheduleNo);// 还款计划编号 每期还款计划的唯一标识
		} else {
			t1.put("partnerScheduleNo", plan.getPartnerScheduleNo());// 还款计划编号 每期还款计划的唯一标识
		}
		t1.put("repayPrincipal",
				new BigDecimal(new BigDecimal(Double.toString(plan.getRepayPrincipal())).toPlainString()));// 还款本金
		t1.put("repayProfit", new BigDecimal(new BigDecimal(Double.toString(plan.getRepayProfit())).toPlainString()));// 还款利息
		/**
		 * 还款计划类型 如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型
		 */
		t1.put("scheduleType", plan.getScheduleType());
		repaySchedules.add(t1);
		param.put("repaySchedules", repaySchedules);
		yntrustUtils.postForObject(param, Map.class, UPDATE_REPAY_SCHEDULE, "更新还款计划还款计划", orderNo, result,
				userDto.getUid());
		RespHelper.setSuccessRespStatus(result);
	}

	/**
	 * 还款订单信息接口
	 * 
	 * @param result
	 * @param info
	 */
	public void repaymentOrderInfo(RespStatus result, YntrustRepaymentInfoDto info) {
		try {
			if (null == info.getRepayDate() || null == info.getRealityPayDate()) {
				RespHelper.setFailRespStatus(result, "预计回款时间与实际回款时间不能为空");
				return;
			}
			/**
			 * 用实际还款时间对比预计还款时间，更新逾期费用
			 */
			if (DateUtils.dateDiff(info.getRepayDate(), info.getRealityPayDate()) > 0) {
				updateOverDueFee(result, info, info.getPartnerScheduleNo());
				if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
					return;
				}
			}

			YntrustMappingDto yntrustMappingDto = getYntrustMapping();
			Map<String, Object> param = new HashMap<String, Object>();
			String requestId = UidUtils.generateUUID();
			param.put("requestId", requestId);
			param.put("productCode", yntrustMappingDto.getYnProductCode());
			param.put("transactionNo", UidUtil.generateOrderId());
			param.put("transactionType", 1);
			List<Map<String, Object>> orderdetails = new ArrayList<Map<String, Object>>();
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("paymentMethod",
					StringUtils.isEmpty(info.getPaymentMethod() + "") ? "2001" : info.getPaymentMethod());
			orderMap.put("uniqueId", yntrustMappingDto.getUniqueId());
			List<Map<String, Object>> businessDetails = new ArrayList<Map<String, Object>>();
			BigDecimal detailAmount = mappingAmount(businessDetails, info);
			orderMap.put("businessDetails", businessDetails);
			orderMap.put("detailAmount", new BigDecimal(detailAmount.toPlainString()));
			param.put("totalAmount", new BigDecimal(detailAmount.toPlainString()));
			orderMap.put("partnerScheduleNo", info.getPartnerScheduleNo());
			orderMap.put("transactionTime", DateUtils.dateToString(info.getRealityPayDate(), DateUtils.FMT_TYPE1));
			orderdetails.add(orderMap);
			param.put("orderdetails", orderdetails);
			yntrustUtils.postForObject(param, Map.class, REPAYMENT_ORDER_INFO, "还款订单信息接口", orderNo, result,
					userDto.getUid());
			info.setTransactionNo(MapUtils.getString(param, "transactionNo"));
			info.setUpdateUid(userDto.getUid());
			if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
				info.setPushStatus(2);
				return;
			}
			info.setPushStatus(1);
			//
			// if (StringUtils.isNotEmpty(info.getPostLoanUrl())) {
			// List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			// for (String str : info.getPostLoanUrl().split(",")) {
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("url", str);
			// map.put("type", "X");
			// tempList.add(map);
			// }
			// List<Map<String, Object>> tmp1 = analyzeImg(tempList, tmpResult);
			// Map<String, Object> imgparam = new HashMap<String, Object>();
			// imgparam.put("imageContent", tmp1);
			// imgparam.put("requestId", UidUtils.generateUUID());
			// imgparam.put("uniqueId", obj.getUniqueId());
			// imgparam.put("iDCardNo", yntrustBorrowDto.getiDCardNo());
			// yntrustUtils.postForObject(imgparam, Map.class, FUACTION, "上传影像资料", orderNo,
			// result, userDto.getUid());
			// }
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			log.error("影像上传失败", e);
		}
	}

	/**
	 * 计算订单总金额
	 * 
	 * @param businessDetails
	 * @param info
	 * @return
	 */
	public BigDecimal mappingAmount(List<Map<String, Object>> businessDetails, YntrustRepaymentInfoDto info) {
		BigDecimal totalAmount = new BigDecimal(Double.toString(info.getRepayPrincipal()));
		Map<String, Object> businessMap = new HashMap<String, Object>();
		/**
		 * 费用类型 这里是多项,如果有逾期违约的 1000 计划本金, 1001 计划利息 1002 贷款服务费,1003 技术维护费 1004 其他费用,
		 * 1005 逾期罚息 1006 逾期违约金,1007 逾期服务费 1008 逾期其他费用
		 * 
		 * 2000 回购本金 2001 回购利息 2002 回购罚息 2003 回购其他费用
		 */
		businessMap.put("feeType", 1000);
		/**
		 * 金额 更新值必须小于等于（当前应收未收-支付中金额）
		 */
		businessMap.put("amount", new BigDecimal(totalAmount.toPlainString()));
		businessDetails.add(businessMap);
		if (null != info.getGivePayProfit()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1001);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getGivePayProfit())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getGivePayProfit())));
			businessDetails.add(businessMap);
		}
		if (null != info.getOtherFee()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1004);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getOtherFee())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getOtherFee())));
			businessDetails.add(businessMap);
		}
		if (null != info.getPenaltyFee()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1005);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getPenaltyFee())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getPenaltyFee())));
			businessDetails.add(businessMap);
		}
		if (null != info.getLatePenalty()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1006);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getLatePenalty())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLatePenalty())));
			businessDetails.add(businessMap);
		}
		if (null != info.getLateFee()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1007);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getLateFee())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLateFee())));
			businessDetails.add(businessMap);
		}
		if (null != info.getLateOtherCost()) {
			businessMap = new HashMap<String, Object>();
			businessMap.put("feeType", 1008);
			businessMap.put("amount",
					new BigDecimal(new BigDecimal(Double.toString(info.getLateOtherCost())).toPlainString()));
			totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLateOtherCost())));
			businessDetails.add(businessMap);
		}
		return totalAmount;
	}

	/**
	 * 提前或者逾期才调用 更新还款计划 需要注意还款计划集合需要按照执行时间顺序传递；覆盖旧的未执行的还款计划； 每期还款计划本金总和应等于贷款总金额；
	 * 每期还款日应早于等于合同到期日； 还款计划状态为『成功』、『扣款中』、『处理异常』或该条还款计划已发生『逾期』的情况下，无法进行变更；
	 * 调用本接口时，更新的还款计划相较原还款计划未有任何变动（还款日期、还款本金、利息及其他费用金额），则将被拒绝；
	 * 若有单条或多条还款计划需变更，则必须将所有未执行的还款计划都通过本接口传递至云信。
	 */
	public void updateRepaySchedule(RespStatus result, String orderNo, YntrustRepaymentPlanDto plan) {
		YntrustMappingDto obj = getYntrustMapping(result, orderNo);
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
			return;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", obj.getUniqueId());

		param.put("changeReason", plan.getChangeReason());
		List<Map<String, Object>> repaySchedules = new ArrayList<Map<String, Object>>();
		Map<String, Object> t1 = new HashMap<String, Object>();
		t1.put("repayDate", DateUtils.dateToString(plan.getRepayDate(), DateUtils.FMT_TYPE1));// 还款时间
		if (StringUtils.isBlank(plan.getPartnerScheduleNo())) {
			String partnerScheduleNo = UidUtils.generateOrderId();
			t1.put("partnerScheduleNo", partnerScheduleNo);// 还款计划编号 每期还款计划的唯一标识
			plan.setPartnerScheduleNo(partnerScheduleNo);
		} else {
			t1.put("partnerScheduleNo", plan.getPartnerScheduleNo());// 还款计划编号 每期还款计划的唯一标识
		}
		t1.put("repayPrincipal",
				new BigDecimal(new BigDecimal(Double.toString(plan.getRepayPrincipal())).toPlainString()));// 还款本金
		t1.put("repayProfit", new BigDecimal(new BigDecimal(Double.toString(plan.getRepayProfit())).toPlainString()));// 还款利息
		/**
		 * 还款计划类型 如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型
		 */
		t1.put("scheduleType", plan.getScheduleType());
		repaySchedules.add(t1);
		param.put("repaySchedules", repaySchedules);

		yntrustUtils.postForObject(param, Map.class, UPDATE_REPAY_SCHEDULE, "更新还款计划还款计划", orderNo, result,
				userDto.getUid());
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode()))
			return;
		plan.setUpdateUid(userDto.getUid());
		RespHelper.setSuccessRespStatus(result);

	}

	/**
	 * 获取快鸽与云南信托推送映射的中间表
	 * 
	 * @param result
	 * @param orderNo
	 * @return
	 */
	public YntrustMappingDto getYntrustMapping(RespStatus result, String orderNo) {
		YntrustMappingDto obj = new YntrustMappingDto();
		obj.setOrderNo(orderNo);
		obj.setStatus(1);
		obj = yntrustMappingService.select(obj);
		if (null == obj || StringUtils.isBlank(obj.getUniqueId())) {
			RespHelper.setFailRespStatus(result, "没有获取到与该订单号关联的uniqueId信息");
		}
		return obj;
	}

	/**
	 * 更新逾期费用
	 * 
	 * @param result
	 * @param info
	 */
	public void updateOverDueFee(RespStatus result, YntrustRepaymentInfoDto info, String partnerScheduleNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		packagOverDueFee(param, info, partnerScheduleNo);
		yntrustUtils.postForObject(param, Map.class, UPDATE_OVERDUEFEE, "还款订单状态查询", orderNo, result, userDto.getUid());
	}

	/**
	 * 还款状态查询
	 * 
	 * @param resultInfo
	 * @param orderNo
	 * @param isPay
	 */
	@SuppressWarnings("unchecked")
	public void queryRepayOrder(RespStatus result, YntrustRepaymentInfoDto info,YntrustRepaymentInfoService yntrustRepaymentInfoService,YntrustRepaymentPayService yntrustRepaymentPayService) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		param.put("productCode", yntrustMappingDto.getYnProductCode());
		List<String> transactionNos = new ArrayList<String>();
		transactionNos.add(info.getTransactionNo());
		param.put("transactionNos", transactionNos);
		Map<String, Object> responseMsg = yntrustUtils.postForObject(param, Map.class, QUERY_REPAY_ORDER, "还款订单状态查询",
				orderNo, result, userDto.getUid());
		
		if(yntrustRepaymentPayService == null) {
			mappingInfo(responseMsg, info,yntrustRepaymentInfoService);
		}else {
			mappingPay(responseMsg, info.getTransactionNo(), yntrustRepaymentInfoService, yntrustRepaymentPayService);
		}
		if (RespStatusEnum.FAIL.getCode().equals(result.getCode()))
			return;
		RespHelper.setSuccessRespStatus(result);
	}

	/**
	 * 订单支付接口
	 * 
	 * @param result
	 * @param orderNo
	 * @param pay
	 * @throws Exception
	 */
	public void paymentOrder(RespDataObject<Map<String, Object>> result, YntrustRepaymentPayDto pay) throws Exception {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		param.put("productCode", yntrustMappingDto.getYnProductCode());
		packagPayment(param, pay);
		yntrustUtils.postForObject(param, Map.class, PAYMENTORDER, "订单支付", orderNo, result, userDto.getUid());
	}

	/**
	 * 订单支付状态查询
	 * 
	 * @param result
	 */
	public void queryPaymentOrder(RespDataObject<YntrustRepaymentPayDto> result, String transactionNo) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		param.put("productCode", yntrustMappingDto.getYnProductCode());
		List<String> transactionNoList = new ArrayList<String>();
		transactionNoList.add(transactionNo);
		param.put("transactionNoList", transactionNoList);
		yntrustUtils.postForObject(param, Map.class, QUERY_PAYMENT_ORDER, "订单支付状态查询", orderNo, result,
				userDto.getUid());
	}

	/**
	 * 取消贷款
	 * 
	 * @param result
	 * @param orderNo
	 */
	public void cancelLoan(RespStatus result, String orderNo) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestId", UidUtils.generateUUID());
		param.put("uniqueId", yntrustMappingDto.getUniqueId());
		yntrustUtils.postForObject(param, Map.class, CCLACTION, "取消贷款", orderNo, result, userDto.getUid());
	}

	/**
	 * 获取快鸽与云南信托推送映射的中间表
	 * 
	 * @param result
	 * @param orderNo
	 * @return
	 */
	public YntrustMappingDto getYntrustMapping() { // orderNo可能取值不到
		YntrustMappingDto yntrustMappingDto = new YntrustMappingDto();
		yntrustMappingDto.setOrderNo(orderNo);
		yntrustMappingDto.setStatus(1);
		yntrustMappingDto = yntrustMappingService.select(yntrustMappingDto);
		return yntrustMappingDto;
	}

	/**
	 * 组装借款人与合同信息
	 * 
	 * @param param
	 */
	public void packagBorrowAndContract(Map<String, Object> param, Map<String, Object> borrow,
			Map<String, Object> contract) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shortName", MapUtils.getString(borrow, "shortName")); // 对手简称
		map.put("bankReservedPhoneNo", MapUtils.getString(borrow, "telephoneNo")); // 银行开户预留手机号 默认手机号
		map.put("iDCardNo", MapUtils.getString(borrow, "iDCardNo")); // 身份证号码
		map.put("maritalStatus", MapUtils.getString(borrow, "maritalStatus")); // 婚姻状况 10未婚, 20已婚, 21初婚, 22再婚, 23复婚,
																				// 30丧偶, 40离婚, 90 // 未说明的婚姻状况
		map.put("telephoneNo", MapUtils.getString(borrow, "telephoneNo")); // 手机号码

		if (StringUtils.isNotBlank(MapUtils.getString(borrow, "cityName"))) {
			AdministrationDivideDto r = new AdministrationDivideDto();
			r.setName(MapUtils.getString(borrow, "cityName"));
			r = dataApi.findAdministrationDivideDto(r);
			map.put("city", r.getCode()); // 居住城市
		} else {
			map.put("city", MapUtils.getString(borrow, "city")); // 居住城市
		}
		// 居住地址 取合同归属地
		map.put("address", StringUtils.isBlank(MapUtils.getString(contract, "address")) ? "999999"
				: MapUtils.getString(contract, "address"));
		map.put("zipCode", "999999");// 邮编
		map.put("jobType", MapUtils.getString(borrow, "jobType")); // 职业分类 1=政府部门，2=教科文，3=金融，4=商贸，5=房地产，6=制造业，7=自由职业，
																	// 8=其他
		map.put("roleType", MapUtils.getString(borrow, "roleType")); // 角色分类 //
																		// 贷款借款人=1；其他投融资交易对手=2；抵质押人担保人贷款类=3；抵质押人担保人非贷款类=4；委托方=5；其他对手方等付费对象=6
		map.put("accountNo", MapUtils.getString(contract, "accountno")); // 还款银行卡账号
		map.put("bankCode", MapUtils.getString(contract, "bankcode")); // 借款人开户银行名称
		map.put("branchBankName", MapUtils.getString(contract, "branchbankname")); // 借款人开户支行名称
		// 银行卡归属地:回款卡
		String bankCardAttribution = StringUtils
				.isBlank(MapUtils.getString(contract, "paymentsBankAttributionCountyCode"))
						? MapUtils.getString(contract, "paymentsBankAttributionCityCode")
						: MapUtils.getString(contract, "paymentsBankAttributionCountyCode");
		map.put("bankCardAttribution", bankCardAttribution);
		map.put("productCode", MapUtils.getString(borrow, "ynProductCode")); // 产品代码

		Double overdueRate = new BigDecimal(MapUtils.getDoubleValue(contract, "overdueDayRate"))
				.multiply(new BigDecimal("100")).doubleValue();
		map.put("communicationAddress", overdueRate); // 逾期费率(合同签署地已经放在居住地址,该字段现在第三方映射为逾期费率)
		param.put("borrower", map);

		map = new HashMap<String, Object>();
		// 合同信息
		map.put("contractAmount", new BigDecimal(
				new BigDecimal(Double.toString(MapUtils.getDoubleValue(contract, "amount"))).toPlainString())); // 合同金额
		// 非授信模式该字段值与放款金额一致
		map.put("signDate", MapUtils.getString(contract, "signDate")); // 签约日期
		map.put("beginDate", MapUtils.getString(contract, "beginDate")); // 开始日期
		 if (MapUtils.getString(borrow, "ynProductCode").equals("I16800")) {
			 map.put("EndDate", "2019-06-03");
		}else {
			 map.put("EndDate", "2019-05-28");    //二期
		}
		 System.out.println("EndDate值打印++++++++++++++++++++"+MapUtils.getString(map, "EndDate"));
		BigDecimal b1 = new BigDecimal(MapUtils.getDoubleValue(contract, "signDayRate"));
		BigDecimal b2 = new BigDecimal("360");
		Double signRate = b1.multiply(b2).doubleValue();
		map.put("signRate", signRate); // 签约利率

		// 付息周期 默认0 0=到期结算 1=按月结算 2=按二个月结算 3=按季结算 4=按四个月结算 5=按五个月结算 6=每半年结算 7=按七个月结算
		// 8=按八个月结算 9=按九个月结算
		// 10=按十个月结算 11=按十一个月结算 12=按年结算 24=每两年结算 36=每三年结算 48=每四年结算 60=每五年结算
		map.put("repaymentCycle", MapUtils.getString(contract, "repaymentCycle"));

		// 还款方式 默认3 等额本息=1,等额本金=2,其他方式=9
		map.put("repaymentMode", MapUtils.getString(contract, "repaymentMode"));
		// 还款期数 默认1期
		map.put("repaymentPeriod", MapUtils.getString(contract, "repaymentPeriod"));
		// 借款期限
		map.put("PartnerProductName", MapUtils.getString(contract, "borrowingDays"));
		Map<String, Object> tmp = new HashMap<String, Object>();

		// 放款银行账号
		tmp.put("accountno", MapUtils.getString(contract, "accountno"));
		// 银行编码
		tmp.put("bankcode", MapUtils.getString(contract, "bankcode"));
		// 支行名称
		tmp.put("branchbankname", MapUtils.getString(contract, "branchbankname"));
		// 银行卡归属地
		String bankcardattribution = StringUtils.isBlank(MapUtils.getString(contract, "bankcardAttributionCountyCode"))
				? MapUtils.getString(contract, "bankcardAttributionCityCode")
				: MapUtils.getString(contract, "bankcardAttributionCountyCode");
		tmp.put("bankcardattribution", bankcardattribution);
		// 账户开户名
		tmp.put("accountname", MapUtils.getString(contract, "accountname"));
		// 放款金额 非授信模式该字段值与合同金额一致
		tmp.put("amount", new BigDecimal(
				new BigDecimal(Double.toString(MapUtils.getDoubleValue(contract, "amount"))).toPlainString()));

		// 放款对象类型: 默认0 0：借款人 1：受托支付-个人 2：受托支付-金融机构 3：受托支付-非金融机构
		tmp.put("type", MapUtils.getString(contract, "type"));
		// 放款信息集合
		map.put("paymentBankCards", tmp);
		param.put("loan", map);
	}

	/**
	 * 组装应还款计划
	 * 
	 * @param param
	 * @param plan
	 */
	private void packagRepaySchedule(Map<String, Object> param, YntrustRepaymentPlanDto plan, String uniqueId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("requestId", UidUtils.generateUUID());
		Map<String, Object> t = new HashMap<String, Object>();
		List<Map<String, Object>> repaySchedules = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> loanRepaySchedules = new ArrayList<Map<String, Object>>();
		t.put("uniqueId", uniqueId);
		Map<String, Object> t1 = new HashMap<String, Object>();
		t1.put("repayDate", format.format(plan.getRepayDate()));// 还款时间
		t1.put("repayPrincipal",
				new BigDecimal(new BigDecimal(Double.toString(plan.getRepayPrincipal())).toPlainString()));// 还款本金
		t1.put("repayProfit", new BigDecimal(new BigDecimal(Double.toString(plan.getRepayProfit())).toPlainString()));// 还款利息
		t1.put("rechMaintenanceFee", 0d);// 技术服务费 非必传
		t1.put("otherFee", 0d);// 其他费用 非必传
		t1.put("loanServiceFee", 0d);// 贷款服务费 非必传

		String partnerScheduleNo = "";
		if (StringUtils.isNotBlank(plan.getPartnerScheduleNo())) {
			t1.put("partnerScheduleNo", plan.getPartnerScheduleNo());// 还款计划编号 每期还款计划的唯一标识
		} else {
			partnerScheduleNo = UidUtils.generateOrderId();
			t1.put("partnerScheduleNo", partnerScheduleNo);// 还款计划编号 每期还款计划的唯一标识
		}
		repaySchedules.add(t1);
		t.put("repaySchedules", repaySchedules);// 还款计划集合
		loanRepaySchedules.add(t);
		param.put("loanRepaySchedules", loanRepaySchedules);// 还款计划所属合同集
		if (StringUtils.isNotBlank(partnerScheduleNo)) {
			plan.setPartnerScheduleNo(partnerScheduleNo);
		}
	}

	/**
	 * 组装逾期费用
	 * 
	 * @param param
	 * @param info
	 * @param partnerScheduleNo
	 */
	private void packagOverDueFee(Map<String, Object> param, YntrustRepaymentInfoDto info, String partnerScheduleNo) {
		YntrustMappingDto yntrustMappingDto = getYntrustMapping();
		param.put("requestId", UidUtils.generateUUID());
		List<Map<String, Object>> overDueFeeDetails = new ArrayList<Map<String, Object>>();
		Map<String, Object> overDueFee = new HashMap<String, Object>();
		overDueFee.put("partnerScheduleNo", partnerScheduleNo);
		overDueFee.put("uniqueId", yntrustMappingDto.getUniqueId());
		overDueFee.put("productCode", yntrustMappingDto.getYnProductCode());
		overDueFee.put("penaltyFee", null == info.getPenaltyFee() ? 0d
				: new BigDecimal(new BigDecimal(Double.toString(info.getPenaltyFee())).toPlainString()));
		overDueFee.put("latePenalty", null == info.getLatePenalty() ? 0d
				: new BigDecimal(new BigDecimal(Double.toString(info.getLatePenalty())).toPlainString()));
		overDueFee.put("lateFee", null == info.getLateFee() ? 0d
				: new BigDecimal(new BigDecimal(Double.toString(info.getLateFee())).toPlainString()));
		overDueFee.put("lateOtherCost", null == info.getLateOtherCost() ? 0d
				: new BigDecimal(new BigDecimal(Double.toString(info.getLateOtherCost())).toPlainString()));
		overDueFee.put("totalOverdueFee", null == info.getOverDueFee() ? 0d
				: new BigDecimal(new BigDecimal(Double.toString(info.getOverDueFee())).toPlainString()));
		overDueFeeDetails.add(overDueFee);

		param.put("overDueFeeDetails", overDueFeeDetails);
	}

	/**
	 * 组装支付接口
	 * 
	 * @param param
	 * @param plan
	 */
	private void packagPayment(Map<String, Object> param, YntrustRepaymentPayDto pay) {
		param.put("transactionNo", pay.getTransactionNo());
		param.put("voucherType", pay.getVoucherType());
		param.put("accountNo", pay.getAccountNo());
		param.put("accountName", pay.getAccountName());
		param.put("bankTransactionNo", pay.getBankTransactionNo());
		param.put("amount", new BigDecimal(new BigDecimal(Double.toString(pay.getAmount())).toPlainString()));
		param.put("trustAccountNo", pay.getTrustAccountNo());
		param.put("trustAccountName", pay.getTrustAccountName());
		param.put("trustBankCode", pay.getTrustBankCode());
		pay.setUpdateUid(userDto.getUid());
	}

	/**
	 * 获取影像资料的BASE64数据
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> analyzeImg(Object object, RespStatus result) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(object, headers);
		RespDataObject<List<Map<String, Object>>> tmp = SingleUtils.getRestTemplate().postForObject(
				Constants.LINK_ANJBO_FS_URL + "/fs/yntrust/getImgBaseCode", requestEntity, RespDataObject.class);
		result.setMsg(tmp.getMsg());
		result.setCode(tmp.getCode());
		return tmp.getData();
	}

	@SuppressWarnings("unchecked")
	public void mappingInfo(Map<String, Object> responseMap, YntrustRepaymentInfoDto info,YntrustRepaymentInfoService yntrustRepaymentInfoService) {
		Object data = MapUtils.getObject(responseMap, "Data");

		if (null != data) {
			Map<String, Object> dataMap = gson.fromJson(gson.toJson(data), Map.class);
			Object obj2 = MapUtils.getObject(dataMap, "RepaymentOrderList");
			if (null != obj2) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<Map<String, Object>> l1 = gson.fromJson(gson.toJson(obj2), List.class);
				if (null != l1 && l1.size() > 0) {
					Map<String, Object> m1 = l1.get(0);
					Integer orderStatus = 0;
					if (m1.containsKey("OrderStatus")) {
						orderStatus = MapUtils.getInteger(m1, "OrderStatus");
					} else {
						orderStatus = MapUtils.getInteger(m1, "orderStatus");
					}

					String statusName = getOrderStatus(orderStatus);
					String occurTimeStr = "";
					if (m1.containsKey("OccurTime")) {
						occurTimeStr = MapUtils.getString(m1, "OccurTime");
					} else {
						occurTimeStr = MapUtils.getString(m1, "occurTime");
					}

					Date occurTime = null;
					if (StringUtils.isNotBlank(occurTimeStr)) {
						try {
							occurTime = format.parse(occurTimeStr);
						} catch (Exception e) {
							log.error("还款订单状态查询转换云南信托返回occurTime处理时间异常,云南信托返回的时间为" + occurTimeStr + ",本次请求的orderNo为:"
									+ orderNo, e);
						}
					}
					info.setStatus(orderStatus);
					info.setStatusName(statusName);
					info.setOccurTime(occurTime);
					info.setUpdateUid(userDto.getUid());
					info.setUpdateTime(new Date());
					Object obj3 = null;
					if (m1.containsKey("RepaymentDetailList")) {
						obj3 = MapUtils.getObject(m1, "RepaymentDetailList");
					} else {
						obj3 = MapUtils.getObject(m1, "repaymentDetailList");
					}
					List<Map<String, Object>> l2 = gson.fromJson(gson.toJson(obj3), List.class);
					if (null != l2 && l2.size() > 0) {
						Map<String, Object> m = l2.get(0);
						String remark = "";
						if (m.containsKey("Remark")) {
							remark = MapUtils.getString(m, "Remark");
						} else {
							remark = MapUtils.getString(m, "remark");
						}
						info.setFailMsg(remark);
					} else {
						info.setFailMsg("-");
					}
                    yntrustRepaymentInfoService.insert(info);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public YntrustRepaymentPayDto mappingPay(Map<String, Object> responseMap, String transactionNo,YntrustRepaymentInfoService yntrustRepaymentInfoService,YntrustRepaymentPayService yntrustRepaymentPayService) {
		Object data = MapUtils.getObject(responseMap, "Data");
		YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
		if (null != data) {
			Map<String, Object> dataMap = gson.fromJson(gson.toJson(data), Map.class);
			Object obj2 = MapUtils.getObject(dataMap, "RepaymentOrderList");
			if (null != obj2) {
				List<Map<String, Object>> l1 = gson.fromJson(gson.toJson(obj2), List.class);
				if (null != l1 && l1.size() > 0) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Map<String, Object> m1 = l1.get(0);
					Integer orderStatus = 0;
					if (m1.containsKey("orderStatus")) {
						orderStatus = MapUtils.getInteger(m1, "orderStatus", 0);
					} else {
						orderStatus = MapUtils.getInteger(m1, "OrderStatus", 0);
					}
					String statusName = getOrderStatus(orderStatus);
					String occurTimeStr = "";
					if (m1.containsKey("occurTime")) {
						occurTimeStr = MapUtils.getString(m1, "occurTime");
					} else {
						occurTimeStr = MapUtils.getString(m1, "OccurTime");
					}
					Date occurTime = null;
					if (StringUtils.isNotBlank(occurTimeStr)) {
						try {
							occurTime = format.parse(occurTimeStr);
						} catch (Exception e) {
							log.error("还款订单状态查询转换云南信托返回occurTime处理时间异常,云南信托返回的时间为" + occurTimeStr + ",本次请求的orderNo为"+ orderNo + ":", e);
						}
					}
					Object obj3 = null;
					if (m1.containsKey("repaymentDetailList")) {
						obj3 = MapUtils.getObject(m1, "repaymentDetailList");
					} else {
						obj3 = MapUtils.getObject(m1, "RepaymentDetailList");
					}
					List<Map<String, Object>> l2 = gson.fromJson(gson.toJson(obj3), List.class);
					String remark = "-";
					if (null != l2 && l2.size() > 0) {
						Map<String, Object> deletMap = new HashMap<String, Object>();
						deletMap.put("transactionNo", transactionNo);
						Map<String, Object> m = l2.get(0);
						m.put("createUid", userDto.getUid());
						m.put("orderNo", orderNo);
						m.put("transactionNo", transactionNo);
						m.put("checkStatusName", 1 == MapUtils.getInteger(m, "checkStatus") ? "检验成功" : "校验失败");
						m.put("recoverStatusName", 1 == MapUtils.getInteger(m, "recoverStatus") ? "已核销" : "未核销");
						if (m.containsKey("remark")) {
							remark = MapUtils.getString(m, "remark");
						} else {
							remark = MapUtils.getString(m, "Remark");
						}
						yntrustRepaymentInfoService.deleteRepaymentDetail(deletMap);
						yntrustRepaymentInfoService.insertRepaymentDetail(m);
					}

					pay.setOrderNo(orderNo);
					pay.setUpdateUid(userDto.getUid());
					pay.setStatusName(statusName);
					if (null != orderStatus && 4 == orderStatus) {
						pay.setStatus("" + 0);
					} else if (null != orderStatus && 6 == orderStatus) {
						pay.setStatus("" + 1);
					} else if (null != orderStatus && (5 == orderStatus || 9 == orderStatus)) {
						pay.setStatus("" + 2);
					} else if (null != orderStatus && 9 == orderStatus) {
						pay.setStatus("" + orderStatus);
					} else {
						pay.setStatusName("");
					}

					pay.setFailMsg(remark);
					pay.setCompleteTime(occurTime);
					yntrustRepaymentPayService.update(pay);

					if (6 == orderStatus || 7 == orderStatus) {
						YntrustMappingDto obj = getYntrustMapping();
						obj.setStatus(2);
						obj.setStatusName("完成");
						obj.setUpdateUid(userDto.getUid());
						yntrustMappingService.update(obj);
					}

				}
			}
		}
		return pay;
	}

	/**
	 * 映射合同状态
	 * 
	 * @param signStatus
	 * @return
	 */
	public String mappingSignStatus(String signStatus) {
		String signStatusName = "";
		if ("1".equals(signStatus)) {
			signStatusName = "未生成合同";
		} else if ("2".equals(signStatus)) {
			signStatusName = "生成合同成功";
		} else if ("3".equals(signStatus)) {
			signStatusName = "生成合同失败";
		} else if ("4".equals(signStatus)) {
			signStatusName = "合同签章成功";
		} else if ("5".equals(signStatus)) {
			signStatusName = "合同签章失败";
		} else if ("6".equals(signStatus)) {
			signStatusName = "发送放款指令成功";
		} else if ("7".equals(signStatus)) {
			signStatusName = "发送放款指令失败";
		} else if ("8".equals(signStatus)) {
			signStatusName = "签章处理中";
		} else if ("9".equals(signStatus)) {
			signStatusName = "签章人工处理";
		}
		return signStatusName;
	}

	public String getOrderStatus(Integer orderStatus) {
		/**
		 * 5=还款方式为1000线上代扣代扣失败后对应状态 9=还款方式为5001快捷支付代扣失败后对应状态
		 */
		String statusName = "";
		if (null == orderStatus)
			return statusName;
		switch (orderStatus) {
		case 0:
			statusName = "已创建";
			break;
		case 1:
			statusName = "检验中";
			break;
		case 2:
			statusName = "检验失败";
			break;
		case 3:
			statusName = "检验成功";
			break;
		case 4:
			statusName = "支付中";
			break;
		case 5:
			statusName = "支付异常";
			break;
		case 6:
			statusName = "支付成功";
			break;
		case 7:
			statusName = "已完成";
			break;
		case 8:
			statusName = "撤销";
			break;
		case 9:
			statusName = "支付失败";
			break;
		}
		return statusName;
	}
	

}
