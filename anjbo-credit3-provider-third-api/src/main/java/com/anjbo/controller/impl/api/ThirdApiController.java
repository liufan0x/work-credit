package com.anjbo.controller.impl.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.baidu.BaiduRiskVo;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.lineparty.PlatformDto;
import com.anjbo.bean.sgtong.DictionariesDto;
import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.bean.sgtong.SgtongContractInformationDto;
import com.anjbo.bean.sgtong.SgtongMortgagorInformationDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ThirdApiBaseController;
import com.anjbo.controller.api.IThirdApiController;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.sgtong.DictionariesMapper;
import com.anjbo.service.baidu.BaiduBlacklistService;
import com.anjbo.service.lineparty.PlatformService;
import com.anjbo.service.sgtong.SgtongBorrowerInformationService;
import com.anjbo.service.sgtong.SgtongBusinfoService;
import com.anjbo.service.sgtong.SgtongContractInformationService;
import com.anjbo.service.sgtong.SgtongMortgagorInformationService;
import com.anjbo.service.umeng.UmengService;
import com.anjbo.service.yntrust.YntrustMappingService;
import com.anjbo.service.yntrust.YntrustRequestFlowService;
import com.anjbo.utils.yntrust.YntrustAbutment;
import com.anjbo.ws.sgt.SGTWsHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class ThirdApiController extends ThirdApiBaseController implements IThirdApiController{	

	@Resource private UmengService umengService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaiduBlacklistService baiduBlacklistService;
	@Resource
	private SgtongBusinfoService sgtongBusinfoService;
	@Resource 
	private OrderApi orderApi;
	@Resource
	private SgtongBorrowerInformationService sgtongBorrowerInformationService;
	@Resource
	private SgtongContractInformationService sgtongContractInformationService;
	@Resource
	private SgtongMortgagorInformationService sgtongMortgagorInformationService;
	@Resource DictionariesMapper dictionariesMapper;
	@Resource
	private YntrustMappingService yntrustMappingService;
	@Resource
	private YntrustRequestFlowService yntrustRequestFlowService;
	@Resource
	private PlatformService platformService;
	
	Logger log = Logger.getLogger(ThirdApiController.class);
	
	@Override
	public Map<String, Object> searchBlacklist(@RequestBody BaiduRiskVo baiduRiskVo) {
		return baiduBlacklistService.blacklist(baiduRiskVo);
	}
	
	
	public RespStatus pushText(@RequestBody Map<String, Object> params) {
		RespStatus resp = new RespStatus();
		try {
			String uid = MapUtils.getString(params, "uid","");
			String message = MapUtils.getString(params, "message","");
			if(StringUtils.isEmpty(uid) || StringUtils.isEmpty(message)) {
				resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			String token = userApi.findUserDtoByUid(uid).getToken();
			if(StringUtils.isNotEmpty(token)) {
				umengService.pushText(token, message);
			}
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("推送异常,参数："+params, e);
			return RespHelper.failRespStatus();
		}
	}


	@Override
	public RespDataObject<Map<String,Object>> interfaceCall(@RequestBody Map<String, Object> params) {
		// TODO Auto-generated method stub
		return SGTWsHelper.interfaceCall(params);
	}


	@Override
	public RespDataObject<Map<String, Object>> searchSgtBusinfoStatus(@RequestBody Map<String, Object> params) {
		RespDataObject<Map<String, Object>> ss=null;
		try {
		
		SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
		dto4.setOrderNo(String.valueOf(params.get("orderNo")));
		dto4.setIsDelete(2);

		List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);
		
		if (sgtongBusinfoDtos.size()>0) {
			
		
			String retStr=null;
			
			
			
			for (SgtongBusinfoDto sgtongBusinfoDto2 : sgtongBusinfoDtos) {//更新影像资料审核状态
						SgtongBusinfoDto dd=new SgtongBusinfoDto();
					    dd.setId(sgtongBusinfoDto2.getId());
					    
					    Map<String, Object> maps2 = new HashMap<String, Object>();
						maps2.put("type",2);
						maps2.put("txCode","2107");
						maps2.put("brNo", "0004");
						maps2.put("batNo",sgtongBusinfoDto2.getBatchNo());
						
						String jsonObject2 = JSONObject.fromObject(maps2).toString();  
						logger.error("查询影像资料推送状态请求参数："+jsonObject2);
					   ss=   SGTWsHelper.interfaceCall(maps2);//查询影像资料推送状态
						logger.error("查询影像资料推送状态返回结果："+ss);
					    
					   logger.error("数据打印"+ss.getData());
					   
					   JSONArray jsonArray=JSONArray.fromObject(ss.getData().get("list"));
					   JSONObject json=  JSONObject.fromObject( jsonArray.get(0));
					   logger.error("json："+json);
					    if ("4".equals(json.get("docSts"))) {
					    	dd.setPushStatus("审核通过");
					    	ss.setCode("SUCCESS");
						}else {
							dd.setPushStatus("审核不通过");
							ss.setCode("FAIL");
							ss.setMsg(String.valueOf(ss.getData()));
							retStr=retStr+dd.getType()+",";
						}
					sgtongBusinfoService.update(dd);
					}
				 
			if (retStr==null) {
				
				ss.setCode(RespStatusEnum.SUCCESS.getCode());
				ss.setMsg("审核成功");
			}else {
				
				ss.setCode(RespStatusEnum.FAIL.getCode());
				ss.setMsg("查询上传状态失败,请先上传影像资料");
				
			}
			
		
		}else{
			
			ss.setCode(RespStatusEnum.FAIL.getCode());
			ss.setMsg("查询上传状态失败,请先上传影像资料");
			
		}
		
		} catch (Exception e) {
			ss.setCode(RespStatusEnum.FAIL.getCode());
			ss.setMsg("查询上传状态失败");
		}
		return ss;
		
	}


	/**
	 * 调进件批量申请接口（异步）【2101】
	 */
	@Override
	public RespStatus sgtLending(@RequestBody Map<String,Object> map) {
		RespStatus resp = new RespStatus();
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			String batNo = MapUtils.getString(map, "batNo");
			String orderNo = MapUtils.getString(map, "orderNo");
			maps.put("brNo", "0004");
			maps.put("batNo", batNo);
			maps.put("dataCnt", "1");
	
			SgtongBorrowerInformationDto dto1 = new SgtongBorrowerInformationDto();
			dto1.setOrderNo(orderNo);
			SgtongContractInformationDto dto2 = new SgtongContractInformationDto();
			dto2.setOrderNo(orderNo);
			SgtongMortgagorInformationDto dto3 = new SgtongMortgagorInformationDto();
			dto3.setOrderNo(orderNo);
	
			SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
			dto4.setOrderNo(orderNo);
	
			List<SgtongBorrowerInformationDto> sborrowerInformationDtos = sgtongBorrowerInformationService.search(dto1);
			List<SgtongContractInformationDto> scontractInformationDtos = sgtongContractInformationService.search(dto2);
			List<SgtongMortgagorInformationDto> smortgagorInformationDtos = sgtongMortgagorInformationService.search(dto3);
			List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);
	
			if (sborrowerInformationDtos.size() == 0 && scontractInformationDtos.size() == 0
					&& smortgagorInformationDtos.size() == 0 && smortgagorInformationDtos.size() == 0) {
	
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("资料信息不完整！");
				return resp;
			}
	
			SgtongBorrowerInformationDto borrowerInformationDto = sborrowerInformationDtos.get(0);
			SgtongContractInformationDto contractInformationDto = scontractInformationDtos.get(0);
			SgtongMortgagorInformationDto mortgagorInformationDto = smortgagorInformationDtos.get(0);
	
			Map<String, Object> first1 = new HashMap<String, Object>();
	
			DictionariesDto dictionariesDto = new DictionariesDto();
			dictionariesDto.setName(contractInformationDto.getAppArea());
		    List<String> dictionariesDtos1= dictionariesMapper.findDic(dictionariesDto);
		    System.out.println(contractInformationDto.getAppArea()+"===="+dictionariesDtos1.get(0));
			first1.put("appArea", dictionariesDtos1.get(0));
			first1.put("appUse", contractInformationDto.getAppUse());
			first1.put("birth", borrowerInformationDto.getBirth().replace("-", ""));
			first1.put("cardAmt", 0);
			first1.put("custName", borrowerInformationDto.getCustName());
			first1.put("custType", "0" + borrowerInformationDto.getCustType());
			first1.put("degree", borrowerInformationDto.getDegree());
			first1.put("edu", borrowerInformationDto.getEdu());
			first1.put("feeTotal", 0);
			first1.put("homeAddr", "暂缺");
			dictionariesDto.setName(borrowerInformationDto.getHomeArea());
		    List<String> dictionariesDtos2= dictionariesMapper.findDic(dictionariesDto);
			first1.put("homeArea", dictionariesDtos2.get(0));
			first1.put("homeSts", 9);
			first1.put("idNo", borrowerInformationDto.getIdNo());
			first1.put("idType", borrowerInformationDto.getIdType());
			first1.put("ifApp", mortgagorInformationDto.getIfApp());
			first1.put("ifCard", mortgagorInformationDto.getIfCard());
			first1.put("ifCarCred", mortgagorInformationDto.getIfCarCred());
			first1.put("ifCard", mortgagorInformationDto.getIfCard());
			first1.put("ifCar", mortgagorInformationDto.getIfCar());
			first1.put("ifId", mortgagorInformationDto.getIfId());
			first1.put("ifMort", mortgagorInformationDto.getIfMort());
			first1.put("ifPact", mortgagorInformationDto.getIfPact());
			first1.put("ifRoom", mortgagorInformationDto.getIfRoom());
			first1.put("income", borrowerInformationDto.getIncome());
			first1.put("lnRate", Double.valueOf(contractInformationDto.getLnRate())/100.0);//利率
			first1.put("marriage", borrowerInformationDto.getMarriage());
			first1.put("pactAmt", BigDecimal.valueOf(Double.valueOf(contractInformationDto.getPactAmt())).multiply((new BigDecimal(Double.toString(10000)))));//合同金额
			first1.put("pactNo", "KG"+orderNo);
			first1.put("payType", contractInformationDto.getPayType());
			first1.put("phoneNo", borrowerInformationDto.getPhoneNo());
			first1.put("prdtNo", "441");//产品号
			first1.put("projNo", "00040004");//信托项目编号
			first1.put("sex", borrowerInformationDto.getSex());
			first1.put("telNo", borrowerInformationDto.getTelNo());
			first1.put("termDay", contractInformationDto.getTermDay());
			first1.put("termMon", contractInformationDto.getTermMon());
			first1.put("vouAmt", 0);//期缴（保）费金额
			first1.put("vouType", contractInformationDto.getVouType());
			List<Map<String, Object>> listAc = new ArrayList<Map<String,Object>>();
			Map<String, Object> listAcMap = new HashMap<String, Object>();
			Map<String, Object> returnAcMap = new HashMap<String, Object>();
			ApplyLoanDto applyLoanDto = new ApplyLoanDto();
			applyLoanDto.setOrderNo(orderNo);
			System.out.println("======订单号："+orderNo);
			RespDataObject<Map<String, Object>> respData = orderApi.applyLoanProcessDetails(applyLoanDto);
			System.out.println(respData.getCode());
			System.out.println(respData.getData());
			Map<String,Object> aMap = respData.getData();
			Map<String,Object> applyMap = MapUtils.getMap(aMap, "loanDto");
			System.out.println(MapUtils.getString(applyMap, "bankName"));
			listAcMap.put("phoneNo", borrowerInformationDto.getPhoneNo());
			listAcMap.put("acName", MapUtils.getString(applyMap, "bankName"));//申请放款 放款卡户名
			if("企业".equals(MapUtils.getString(maps, "acType"))) {
				listAcMap.put("acType", 12);
			}else {
				listAcMap.put("acType", 11);//账户类型 10-个人贷记卡账户11-个人借记卡账户12-企业账户14-商户
			}
			listAcMap.put("acUse", 2);//账户用途 1-扣款账户2-放款账户
			listAcMap.put("acno", MapUtils.getString(applyMap, "bankAccount"));//账户号
			listAcMap.put("authentication", "002");
			listAcMap.put("bankCode", "002");//银行代码  《银行代码》账户类型为14-商户 银行代码填写为 001
			listAcMap.put("idNo", borrowerInformationDto.getIdNo());//证件号码
			listAcMap.put("idType", borrowerInformationDto.getIdType());//证件类型
			listAcMap.put("acAmt",  BigDecimal.valueOf(Double.valueOf(MapUtils.getString(applyMap, "loanAmount"))).multiply((new BigDecimal(Double.toString(10000)))));
			listAc.add(listAcMap);
			//回款信息
			returnAcMap=MapUtils.getMap(map, "returnAcMap");
			returnAcMap.put("idNo", borrowerInformationDto.getIdNo());
			returnAcMap.put("idType", borrowerInformationDto.getIdType());
			listAc.add(returnAcMap);
			first1.put("listAc",listAc);
			first1.put("listCom", new ArrayList<Map<String,Object>>());
			List<Map<String,Object>>  listGage = new ArrayList<Map<String,Object>>();
			if(1==contractInformationDto.getVouType()||2==contractInformationDto.getVouType()) {
				Map<String,Object> gageMap = new HashMap<String,Object>();
				gageMap.put("gcustName", mortgagorInformationDto.getGcustName());
				gageMap.put("gcustIdtype", mortgagorInformationDto.getGcustIdtype());
				gageMap.put("gcustIdno", mortgagorInformationDto.getGcustIdno());
				gageMap.put("gType", mortgagorInformationDto.getGtype());
				gageMap.put("gName", mortgagorInformationDto.getGname());
				gageMap.put("gValue", BigDecimal.valueOf(Double.valueOf(mortgagorInformationDto.getGvalue())).multiply((new BigDecimal(Double.toString(10000)))));//评估价值
				gageMap.put("gLicno", mortgagorInformationDto.getGlicno());
				gageMap.put("gLicType", mortgagorInformationDto.getGlicType());
				gageMap.put("gBegBal", mortgagorInformationDto.getGbegBal());
				gageMap.put("gSmType", mortgagorInformationDto.getGsmType());
				gageMap.put("gWorkType", mortgagorInformationDto.getGworkType());
				listGage.add(gageMap);
			}
			first1.put("listGage", listGage);//押品信息
			first1.put("listRel", new ArrayList<Map<String,Object>>());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(first1);
			maps.put("list", list);
			maps.put("type", 1);
			maps.put("txCode","2101");
			String jsonObject = JSONObject.fromObject(maps).toString();  
			logger.info("调进件批量申请接口（异步）【2101】进行实际的放款动作："+jsonObject);
			RespDataObject<Map<String, Object>> s = SGTWsHelper.interfaceCall(maps);// 
			logger.info("调进件批量申请接口（异步）【2101】进行实际的放款动作返回结果："+s.getCode()+"------" +s);
			resp.setCode(s.getCode());
			resp.setMsg(s.getMsg());
			return resp;
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}


	/**
	 * 2102查询放款结果接口
	 */
	@Override
	public RespStatus sgtLendingResult(@RequestBody Map<String,Object> map) {
		RespStatus resp = new RespStatus();
		try {
			String batNo = MapUtils.getString(map, "batNo");
			Map<String,Object> thMap = new HashMap<String,Object>();
			thMap.put("brNo", "0004");
			thMap.put("type", 2);
			thMap.put("txCode", "2102");
			thMap.put("batchNo", batNo);
			thMap.put("pactNo", "KG"+MapUtils.getString(map, "orderNo"));
			RespDataObject<Map<String, Object>> re = SGTWsHelper.interfaceCall(thMap);// 
			if(RespStatusEnum.SUCCESS.getCode().equals(re.getCode())||"0000".equals(re.getCode())) {
				SgtongBorrowerInformationDto oDto = new SgtongBorrowerInformationDto();
				oDto.setOrderNo(MapUtils.getString(map, "orderNo"));
				oDto = sgtongBorrowerInformationService.find(oDto);
				SgtongBorrowerInformationDto dto = new SgtongBorrowerInformationDto();
				dto.setOrderNo(MapUtils.getString(map, "orderNo"));
				dto.setLendingStatus("放款成功");
				if(oDto.getSgtLendingTime()==null) {
					dto.setSgtLendingTime(new Date());
				}
				sgtongBorrowerInformationService.insert(dto);
			}else {
				if(!(re.getMsg().contains("错误")||re.getMsg().contains("失败")||re.getMsg().contains("A100001")
						||re.getMsg().contains("A100002")||re.getMsg().contains("A100003")||re.getMsg().contains("A100004")
						||re.getMsg().contains("A100005")||re.getMsg().contains("A100006")||re.getMsg().contains("A100007")
						||re.getMsg().contains("A100008")||re.getMsg().contains("A100009")||re.getMsg().contains("A100010")
						||re.getMsg().contains("A100011")||re.getMsg().contains("A100012")||re.getMsg().contains("A100013")
						||re.getMsg().contains("A100014")||re.getMsg().contains("A100015")||re.getMsg().contains("A100016")
						||re.getMsg().contains("A100017")||re.getMsg().contains("A100018")||re.getMsg().contains("A100019")
						||re.getMsg().contains("A100020"))) {
					SgtongBorrowerInformationDto dto = new SgtongBorrowerInformationDto();
					dto.setOrderNo(MapUtils.getString(map, "orderNo"));
					dto.setLendingStatus("待放款");
					dto.setSgtLendingTime(new Date());
					sgtongBorrowerInformationService.insert(dto);
				}
			}
			resp.setCode(re.getCode());
			resp.setMsg(re.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, "查询2102接口异常");
		}
		return resp;
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


	@Override
	public RespStatus getInsertFile(@RequestBody Map<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println("录入pdf文件");
		System.out.println("客户姓名："+MapUtils.getString(map, "customerName"));
		RespStatus resp = new RespStatus();
		if (StringUtils.isEmpty(MapUtils.getString(map, "customerName"))) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("姓名为空");
			return resp;
		}
	if (StringUtils.isEmpty(MapUtils.getString(map, "idCardType"))) {
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg("证件类型为空");
		return resp;
	}
		if (StringUtils.isEmpty(MapUtils.getString(map, "idCardNumber"))) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("证件号码为空");
			return resp;
		}
		if (MapUtils.getString(map, "idCardNumber").length()!=18) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("身份证格式不正确");
			return resp;
		}
		PlatformDto platformDto = new PlatformDto();
		platformDto.setIdCardNumber(MapUtils.getString(map, "idCardNumber"));
		platformDto.setCustomerName(MapUtils.getString(map, "customerName"));
		platformDto.setIdCardType("CER");
		platformDto.setInsuranceFile(MapUtils.getString(map,"insuranceFile"));
		platformDto.setStatus("3");
		int i = 0;
		i = platformService.insertOne(platformDto);
		if (i!=0) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("成功");
		}
		return resp;
	}
	
}
