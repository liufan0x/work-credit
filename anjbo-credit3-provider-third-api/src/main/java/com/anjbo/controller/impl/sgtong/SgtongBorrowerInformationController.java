/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.sgtong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.FormulaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.sgtong.ISgtongBorrowerInformationController;
import com.anjbo.dao.sgtong.DictionariesMapper;
import com.anjbo.service.sgtong.SgtongBorrowerInformationService;
import com.anjbo.service.sgtong.SgtongBusinfoService;
import com.anjbo.service.sgtong.SgtongContractInformationService;
import com.anjbo.service.sgtong.SgtongMortgagorInformationService;
import com.anjbo.utils.SingleUtils;
import com.anjbo.ws.sgt.SGTWsHelper;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anjbo.controller.BaseController;
import com.anjbo.common.RespHelper;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseCustomerBorrowerDto;
import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.bean.order.BaseHouseDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.RiskEnquiryDto;
import com.anjbo.bean.sgtong.DictionariesDto;
import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.bean.sgtong.SgtongContractInformationDto;
import com.anjbo.bean.sgtong.SgtongMortgagorInformationDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@RestController
public class SgtongBorrowerInformationController extends BaseController
		implements ISgtongBorrowerInformationController {

	@Resource
	private SgtongBorrowerInformationService sgtongBorrowerInformationService;
	@Resource
	private SgtongBusinfoService sgtongBusinfoService;
	@Resource
	private SgtongContractInformationService sgtongContractInformationService;
	@Resource
	private SgtongMortgagorInformationService sgtongMortgagorInformationService;
	@Resource
	private UserApi userApi;
	
	@Resource 
	private OrderApi orderApi;
	@Resource
	  private  DictionariesMapper dictionariesMapper;
	/**
	 * 分页查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespPageData<SgtongBorrowerInformationDto> page(@RequestBody SgtongBorrowerInformationDto dto) {
		RespPageData<SgtongBorrowerInformationDto> resp = new RespPageData<SgtongBorrowerInformationDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(sgtongBorrowerInformationService.search(dto));
			resp.setTotal(sgtongBorrowerInformationService.count(dto));
		} catch (Exception e) {
			logger.error("分页异常,参数：" + dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespData<SgtongBorrowerInformationDto> search(@RequestBody SgtongBorrowerInformationDto dto) {
		RespData<SgtongBorrowerInformationDto> resp = new RespData<SgtongBorrowerInformationDto>();
		try {
			return RespHelper.setSuccessData(resp, sgtongBorrowerInformationService.search(dto));
		} catch (Exception e) {
			logger.error("查询异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<SgtongBorrowerInformationDto>(),
					RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<SgtongBorrowerInformationDto> find(@RequestBody SgtongBorrowerInformationDto dto) {
		RespDataObject<SgtongBorrowerInformationDto> resp = new RespDataObject<SgtongBorrowerInformationDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, sgtongBorrowerInformationService.find(dto));
		} catch (Exception e) {
			logger.error("查找异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongBorrowerInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<SgtongBorrowerInformationDto> add(@RequestBody SgtongBorrowerInformationDto dto) {
		RespDataObject<SgtongBorrowerInformationDto> resp = new RespDataObject<SgtongBorrowerInformationDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, sgtongBorrowerInformationService.insert(dto));
		} catch (Exception e) {
			logger.error("新增异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongBorrowerInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus edit(@RequestBody SgtongBorrowerInformationDto dto) {
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			sgtongBorrowerInformationService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus delete(@RequestBody SgtongBorrowerInformationDto dto) {
		RespStatus resp = new RespStatus();
		try {
			sgtongBorrowerInformationService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("删除异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	
	
	@Override
	public RespDataObject<Map<String, Object>> texts(@RequestBody Map<String, Object> map) {
		int type =2;
		String txCode = "2102";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("brNo", Constants.SGT_WS_BRNO);
		maps.put("batchNo","20190118200633793");
		maps.put("pactNo", "KG20190118200633793");
		
		
		maps.put("type",type);
		maps.put("txCode",txCode);
		return SGTWsHelper.interfaceCall(maps);
		
	}

	/*
	 * 余额查询
	 **/
	@Override
	public RespDataObject<Map<String, Object>> text(@RequestBody Map<String, Object> map) {
		System.out.println("已经进来了120");
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		Map<String,Object> maps = new HashMap<String,Object>();
		try {
			String  brNo = MapUtils.getString(map,"brNo");
			String cardChno = MapUtils.getString(map,"cardChno");
			String acNo = MapUtils.getString(map,"acNo");
			if (StringUtils.isBlank(brNo)||StringUtils.isBlank(cardChno)||StringUtils.isBlank(acNo)) {
				RespHelper.setFailDataObject(result,null,"参数异常,请求为空!");
				return result;
			}
			maps.put("type",2);
			maps.put("txCode","5204");
			maps.put("brNo",Constants.SGT_WS_BRNO);
			maps.put("cardChno",Constants.SGT_WS_CARDCHNO);     //渠道编号
			maps.put("acNo",Constants.SGT_WS_ACNO);              //账户号
			result = SGTWsHelper.interfaceCall(maps);
			RespHelper.setSuccessDataObject(result,result.getData());
		} catch (Exception e) {
			// TODO: handle exception
			RespHelper.setFailDataObject(result,null,"查询信托专户余额异常!");
		}
		return result;
	}

	@Override
	public RespData<SgtongBorrowerInformationDto> pushBorrowerInformation(
			@RequestBody SgtongBorrowerInformationDto dto) {
		RespData<SgtongBorrowerInformationDto> resp = new RespData<SgtongBorrowerInformationDto>();

		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			String timestamp=String.valueOf(System.currentTimeMillis());

			maps.put("brNo", Constants.SGT_WS_BRNO);
			maps.put("batNo", "P"+timestamp);
			maps.put("dataCnt", "1");

			SgtongBorrowerInformationDto dto1 = new SgtongBorrowerInformationDto();
			dto1.setOrderNo(dto.getOrderNo());
			SgtongContractInformationDto dto2 = new SgtongContractInformationDto();
			dto2.setOrderNo(dto.getOrderNo());
			SgtongMortgagorInformationDto dto3 = new SgtongMortgagorInformationDto();
			dto3.setOrderNo(dto.getOrderNo());

			SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
			dto4.setOrderNo(dto.getOrderNo());

			List<SgtongBorrowerInformationDto> sborrowerInformationDtos = sgtongBorrowerInformationService.search(dto1);
			List<SgtongContractInformationDto> scontractInformationDtos = sgtongContractInformationService.search(dto2);
			List<SgtongMortgagorInformationDto> smortgagorInformationDtos = sgtongMortgagorInformationService.search(dto3);
			List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);

			if (sborrowerInformationDtos.size() == 0) {

				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("请先保存借款人信息,再推送！");
				return resp;
			}

			if ( scontractInformationDtos.size() == 0) {

				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("请先保存合同信息,再推送！");
				return resp;
			}

			if ( smortgagorInformationDtos.size() == 0 ) {

				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("请先保存抵押人信息,再推送！");
				return resp;
			}

			

			SgtongBorrowerInformationDto borrowerInformationDto = sborrowerInformationDtos.get(0);
			SgtongContractInformationDto contractInformationDto = scontractInformationDtos.get(0);
			SgtongMortgagorInformationDto mortgagorInformationDto = smortgagorInformationDtos.get(0);

			Map<String, Object> first1 = new HashMap<String, Object>();

			first1.put("prePactNo", "YSP"+timestamp);
			first1.put("custName", borrowerInformationDto.getCustName());
			first1.put("idType", borrowerInformationDto.getIdType());
			first1.put("idNo", borrowerInformationDto.getIdNo());
			first1.put("custType", "0" + borrowerInformationDto.getCustType());
			first1.put("sex", borrowerInformationDto.getSex());
			first1.put("birth", borrowerInformationDto.getBirth().replace("-", ""));
			first1.put("marriage", borrowerInformationDto.getMarriage());
			first1.put("children", borrowerInformationDto.getChildren());
			first1.put("edu", borrowerInformationDto.getEdu());
			first1.put("degree", borrowerInformationDto.getDegree());
			first1.put("telNo", borrowerInformationDto.getTelNo());
			first1.put("phoneNo", borrowerInformationDto.getPhoneNo());

			// first1.put("postCode",borrowerInformationDto.getp);
			// first1.put("postAddr",null);
			
			DictionariesDto dictionariesDto = new DictionariesDto();
			dictionariesDto.setName(borrowerInformationDto.getHomeArea());
		    List<String> dictionariesDtos= dictionariesMapper.findDic(dictionariesDto);
			first1.put("homeArea", dictionariesDtos.get(0));
			
			
			// first1.put("homeTel",borrowerInformationDto.geth);
			// first1.put("homeCode",null);
			// first1.put("homeAddr",null);
			// first1.put("homeSts","5");
			first1.put("income", borrowerInformationDto.getIncome());
			// first1.put("mateName",borrowerInformationDto.getrm);
			// first1.put("mateIdtype","0");
			// first1.put("mateIdno","");
			// first1.put("mateWork",null);
			// first1.put("mateTel",null);
			//listCom<开始>(共同借款人)
			
			List<Map<String, Object>> listCom = new ArrayList<Map<String, Object>>();

			 if(borrowerInformationDto.getComName()!=null && !"".equals(borrowerInformationDto.getComName())) {
				 Map<String, Object> listComM = new HashMap<String, Object>();
					listComM.put("comName", borrowerInformationDto.getComName());
					listComM.put("comIdtype", borrowerInformationDto.getComIdtype());
					listComM.put("comIdno", borrowerInformationDto.getComIdno());
					listComM.put("comTel", borrowerInformationDto.getComTel());
					listCom.add(listComM);
				
			}
			
			
			List<Map<String, Object>> listRel = new ArrayList<Map<String, Object>>();
           if(borrowerInformationDto.getRelName()!=null && !"".equals(borrowerInformationDto.getRelName())) {
			//listRel<开始>（借款关联人）
			Map<String, Object> first3 = new HashMap<String, Object>();
			first3.put("relName", borrowerInformationDto.getRelName());
			first3.put("relIdtype", borrowerInformationDto.getRelIdtype());
			first3.put("relIdno", borrowerInformationDto.getRelIdno());
			first3.put("relTel", borrowerInformationDto.getRelTel());
			listRel.add(first3);
           }
			
			first1.put("projNo", Constants.SGT_WS_PROJNO);
			first1.put("prdtNo",Constants.SGT_WS_PRDTNO);
			logger.error("prdtNo==="+ Constants.SGT_WS_PRDTNO);
			first1.put("pactAmt",BigDecimal.valueOf(Double.valueOf(contractInformationDto.getPactAmt())).multiply(new BigDecimal(Double.toString(10000))));
			first1.put("lnRate", BigDecimal.valueOf(Double.valueOf(contractInformationDto.getLnRate())).divide(new BigDecimal(Double.toString(100))));

			first1.put("rateType", contractInformationDto.getRateType());
			
			
			DictionariesDto dictionariesDto2 = new DictionariesDto();
			dictionariesDto2.setName(contractInformationDto.getAppArea());
		    List<String> dictionariesDtos2= dictionariesMapper.findDic(dictionariesDto2);
			first1.put("appArea", dictionariesDtos2.get(0));
			
			
			first1.put("appUse", contractInformationDto.getAppUse());
			first1.put("termMon", contractInformationDto.getTermMon());
			first1.put("termDay", contractInformationDto.getTermDay());
			first1.put("vouType", contractInformationDto.getVouType());
			// first1.put("endDate","20190326");
			first1.put("payType", contractInformationDto.getPayType());
			// first1.put("payDay",contractInformationDto.getp);

			//listGage<开始>（押品信息）
			List<Map<String, Object>> listGage = new ArrayList<Map<String, Object>>();
			Map<String, Object> first2 = new HashMap<String, Object>();
			first2.put("gcustName", mortgagorInformationDto.getGcustName());
			first2.put("gcustIdtype", mortgagorInformationDto.getGcustIdtype());
			first2.put("gcustIdno", mortgagorInformationDto.getGcustIdno());
			first2.put("gType", mortgagorInformationDto.getGtype());
			first2.put("gName", mortgagorInformationDto.getGname());
			// first1.put("gDesc",mortgagorInformationDto.get);
			first2.put("gValue",BigDecimal.valueOf(Double.valueOf(mortgagorInformationDto.getGvalue())).multiply(new BigDecimal(Double.toString(10000))));

			
			
			first2.put("gLicno", mortgagorInformationDto.getGlicno());
			first2.put("gLicType", mortgagorInformationDto.getGlicType());
			first2.put("gBegBal", mortgagorInformationDto.getGbegBal());
			first2.put("gSmType", mortgagorInformationDto.getGsmType());
			first2.put("gWorkType", mortgagorInformationDto.getGworkType());

			listGage.add(first2);
			
			
			first1.put("ifCar", mortgagorInformationDto.getIfCar());
			first1.put("ifCarCred", mortgagorInformationDto.getIfCarCred());
			first1.put("ifRoom", mortgagorInformationDto.getIfRoom());
			first1.put("ifMort", mortgagorInformationDto.getIfMort());
			first1.put("ifCard", mortgagorInformationDto.getIfCard());
			first1.put("cardAmt", mortgagorInformationDto.getCardAmt());
			first1.put("ifApp", mortgagorInformationDto.getIfApp());
			first1.put("ifId", mortgagorInformationDto.getIfId());
			first1.put("ifPact", mortgagorInformationDto.getIfPact());
			// first1.put("workSts",mortgagorInformationDto.get);

			
			if (listCom.size()>0) {
				first1.put("listCom",listCom);
			}
			if (listRel.size()>0) {
				first1.put("listRel",listRel);
			}
			
			
			
			first1.put("listGage",listGage);
			
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(first1);
		
			
			maps.put("list", list);
			maps.put("type", 1);
			maps.put("txCode","2001");
			String jsonObject = JSONObject.fromObject(maps).toString();  
			logger.error("上传陕国投借款,合同,抵押人信息资料名称请求参数："+jsonObject);
			RespDataObject<Map<String, Object>> s = SGTWsHelper.interfaceCall(maps);// 上传借款,合同,抵押人信息
			logger.error("上传陕国投借款,合同,抵押人信息资料名称返回结果："+s.getCode()+"------" +s);
			
			//更新相关sgt推送信息
			if ("SUCCESS".equals(s.getCode())) {
				SgtongBorrowerInformationDto dto12 = new SgtongBorrowerInformationDto();
				dto12.setId(borrowerInformationDto.getId());
				dto12.setBatchNo("P"+timestamp);
				dto12.setPrePactNo("YSP"+timestamp);
				sgtongBorrowerInformationService.update(dto12);
				RespData<SgtongBorrowerInformationDto> 	ss=	pushBusinfo(dto);
				s.setCode(ss.getCode());
				s.setMsg(ss.getMsg());
			}
			
			resp.setCode(s.getCode());
			resp.setMsg(s.getMsg());
			return resp;
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}
	
	/**
	 * 调进件批量申请接口（异步）【2101】
	 */
	@Override
	public RespStatus sgtLending(SgtongBorrowerInformationDto dto) {
		RespStatus resp = new RespStatus();
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			String timestamp=String.valueOf(System.currentTimeMillis());
	
			maps.put("brNo", Constants.SGT_WS_BRNO);
			maps.put("batNo", dto.getOrderNo());
			maps.put("dataCnt", "1");
	
			SgtongBorrowerInformationDto dto1 = new SgtongBorrowerInformationDto();
			dto1.setOrderNo(dto.getOrderNo());
			SgtongContractInformationDto dto2 = new SgtongContractInformationDto();
			dto2.setOrderNo(dto.getOrderNo());
			SgtongMortgagorInformationDto dto3 = new SgtongMortgagorInformationDto();
			dto3.setOrderNo(dto.getOrderNo());
	
			SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
			dto4.setOrderNo(dto.getOrderNo());
	
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
	
			first1.put("appArea", contractInformationDto.getAppArea());
			first1.put("appUse", contractInformationDto.getAppUse());
			first1.put("birth", borrowerInformationDto.getBirth().replace("-", ""));
			first1.put("cardAmt", 0);
			first1.put("custName", borrowerInformationDto.getCustName());
			first1.put("custType", "0" + borrowerInformationDto.getCustType());
			first1.put("degree", borrowerInformationDto.getDegree());
			first1.put("edu", borrowerInformationDto.getEdu());
			first1.put("feeTotal", 0);
			first1.put("homeAddr", "暂缺");
			first1.put("homeArea", borrowerInformationDto.getHomeArea());
			first1.put("homeSts", 9);
			first1.put("idNo", borrowerInformationDto.getIdNo());
			first1.put("idType", borrowerInformationDto.getIdType());
			first1.put("ifApp", mortgagorInformationDto.getIfApp());
			first1.put("ifCard", mortgagorInformationDto.getIfCard());
			first1.put("ifCarCred", mortgagorInformationDto.getIfCarCred());
			first1.put("ifCard", mortgagorInformationDto.getIfCard());
			first1.put("ifId", mortgagorInformationDto.getIfId());
			first1.put("ifMort", mortgagorInformationDto.getIfMort());
			first1.put("ifPact", mortgagorInformationDto.getIfPact());
			first1.put("ifRoom", mortgagorInformationDto.getIfRoom());
			first1.put("income", borrowerInformationDto.getIncome());
			
			first1.put("lnRate", BigDecimal.valueOf(Double.valueOf(contractInformationDto.getLnRate())).divide(new BigDecimal(Double.toString(100))));
			first1.put("marriage", borrowerInformationDto.getMarriage());
			first1.put("pactAmt",BigDecimal.valueOf(Double.valueOf(contractInformationDto.getPactAmt())).multiply(new BigDecimal(Double.toString(10000))));

			first1.put("pactNo", dto.getOrderNo());
			first1.put("payType", contractInformationDto.getPayType());
			first1.put("phoneNo", borrowerInformationDto.getPhoneNo());
			first1.put("prdtNo",  Constants.SGT_WS_PRDTNO);//产品号
			first1.put("projNo",  Constants.SGT_WS_PROJNO );//信托项目编号
			first1.put("sex", borrowerInformationDto.getSex());
			first1.put("telNo", borrowerInformationDto.getTelNo());
			first1.put("termDay", contractInformationDto.getTermDay());
			first1.put("termMon", contractInformationDto.getTermMon());
			first1.put("vouAmt", 0);//期缴（保）费金额
			first1.put("vouType", contractInformationDto.getVouType());
			List<Map<String, Object>> listAc = new ArrayList<Map<String,Object>>();
			Map<String, Object> listAcMap = new HashMap<String, Object>();
			ApplyLoanDto applyLoanDto = new ApplyLoanDto();
			applyLoanDto.setOrderNo(dto.getOrderNo());
			Map<String, Object> applyMap = orderApi.applyLoanProcessDetails(applyLoanDto).getData();
			listAcMap.put("phoneNo", borrowerInformationDto.getPhoneNo());
			listAcMap.put("acName", MapUtils.getString(applyMap, "bankName"));//申请放款 放款卡户名
			listAcMap.put("acType", MapUtils.getString(applyMap, "acType"));//账户类型 10-个人贷记卡账户11-个人借记卡账户12-企业账户14-商户
			listAcMap.put("acUse", 2);//账户用途 1-扣款账户2-放款账户
			listAcMap.put("acno", MapUtils.getString(applyMap, "bankAccount"));//账户号
			//listAcMap.put("authentication", value);
			listAcMap.put("bankCode", "004");//银行代码  《银行代码》账户类型为14-商户 银行代码填写为 001
			listAcMap.put("idNo", borrowerInformationDto.getIdNo());//证件号码
			listAcMap.put("idType", borrowerInformationDto.getIdType());//证件类型
			first1.put("listAc",listAc);
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
			logger.error("编辑异常,参数：" + dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}

	
	@Override
	public RespData<SgtongBorrowerInformationDto> pushBusinfo(@RequestBody SgtongBorrowerInformationDto dto) {
		
		RespData<SgtongBorrowerInformationDto> resp = new RespData<SgtongBorrowerInformationDto>();
		RespDataObject ss = null;
		try {

			SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
			dto4.setOrderNo(dto.getOrderNo());
			dto4.setIsDelete(2);
			dto4.setIsPlus(2);
			List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);

			if (sgtongBusinfoDtos.size()==0) {
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg("请上传新的影像资料！");
				return resp;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			String urls = "";
			for (SgtongBusinfoDto sgtongBusinfo : sgtongBusinfoDtos) {
				urls = urls + sgtongBusinfo.getUrl() + ",";
			}
			map.put("uploadFile", urls.substring(0, urls.length() - 1));
			
		
			ss = pushsgtongBusinfo(map); // 1上传影像资料文件
			logger.error("上传影像资料返回结果：" + ss);
			if (ss.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {

				ss = pushsgtongBusinfoName(sgtongBusinfoDtos);// 2上传影像资料名称

			}

			resp.setCode(ss.getCode());
			resp.setMsg(ss.getMsg());
			return resp;

		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public RespDataObject pushsgtongBusinfoName(List<SgtongBusinfoDto> list) {
		RespDataObject resp = new RespDataObject<>();
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			String timestamp=String.valueOf(System.currentTimeMillis());
			maps.put("brNo", Constants.SGT_WS_BRNO);
			maps.put("batNo", "P"+timestamp);
			maps.put("dataCnt", String.valueOf(list.size()));

			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

			for (SgtongBusinfoDto sgtongBusinfoDto : list) {
		
					
				
				Map<String, Object> first1 = new HashMap<String, Object>();
				// http://182.254.149.92:9206/img/fc-img/6dca5a91652d484f8fcbd330b3da446f_48.jpg
				String datas = sgtongBusinfoDto.getUrl();
				String docType = datas.substring(datas.lastIndexOf(".")+1, datas.length());
				String docName = datas.substring(datas.indexOf("/fc-img/")+8, datas.length());
				first1.put("transNo", "KG" + sgtongBusinfoDto.getOrderNo());
				first1.put("docType", getTypeCode(sgtongBusinfoDto.getTypeId()));
				first1.put("docName", docName);

				list2.add(first1);
			}
			maps.put("list", list2);
			maps.put("type",1);
			maps.put("txCode","2106");
			String jsonObject = JSONObject.fromObject(maps).toString();  
			logger.error("上传影像资料名称请求参数："+jsonObject);
			RespDataObject<Map<String, Object>> s= SGTWsHelper.interfaceCall(maps);
			logger.error("上传影像资料名称返回结果："+s);
			
			
			//推送成功后更新是否推送状态
			if (s.getMsg().indexOf("校验成功")>-1) {
				
				for (SgtongBusinfoDto sgtongBusinfoDto : list) {
				
					SgtongBusinfoDto dd=new SgtongBusinfoDto();
				    dd.setId(sgtongBusinfoDto.getId());
					dd.setBatchNo("P"+timestamp);
					dd.setPrePactNo("KG" + sgtongBusinfoDto.getOrderNo());
				    dd.setIsPlus(1);
				    dd.setPushStatus("待审核");
				sgtongBusinfoService.update(dd);
				}
				
			}
			resp.setCode(s.getCode());
			resp.setMsg(s.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("上传影像资料名称失败");

		}

		return resp;
	}

	@SuppressWarnings("rawtypes")
	public RespDataObject pushsgtongBusinfo(Map<String, Object> map) {
		HttpHeaders headers = new HttpHeaders();
		RespDataObject resp = new RespDataObject<>();
		try {
			logger.error("上传影像资料参数："+map);
			logger.error("上传影像资料参数URL："+Constants.LINK_ANJBO_FS_URL  + "/fs/sftp/showSftp");
			resp=	SingleUtils.getRestTemplate(120).postForObject(Constants.LINK_ANJBO_FS_URL  + "/fs/sftp/showSftp",
					SingleUtils.getHttpEntity(map, headers), RespDataObject.class);
		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("上传影像资料失败");

		}

		return resp;
	}

	@Override
	public RespDataObject<String> showSftp(@RequestBody Map<String, Object> map) {
		HttpHeaders headers = new HttpHeaders();
		RespDataObject resp = new RespDataObject<>();
		try {
			resp=	SingleUtils.getRestTemplate(120).postForObject("http://localhost:8082"  + "/anjbo-fs/fs/sftp/showSftp",
					SingleUtils.getHttpEntity(map, headers), RespDataObject.class);
		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("上传影像资料失败");

		}
		return resp;
		
		// TODO Auto-generated method stub
		/*Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("brNo",Constants.SGT_WS_BRNO);
		maps.put("projNo","00040004");
		maps.put("custName","苹果");
		maps.put("idType",0);
		maps.put("idNo","340101198108119852");
		maps.put("phoneNo","15018531908");
		maps.put("acType",1);
		maps.put("acno","4682037906128888");
		maps.put("bankCode","007");
		maps.put("acName","招商银行");
		maps.put("cardChn","CL0005");
		maps.put("type",1);
		maps.put("txCode","2011");
		SGTWsHelper.interfaceCall(maps);*/
	}

	@Override
	public RespDataObject<Map<String,Object>> searchPushStatus( @RequestBody SgtongBorrowerInformationDto dto) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String,Object> ret=new HashMap<String,Object>();
		String borrowstatus=null;
		String businfostatus=null;
		String pushstatus=null;
		try {
	
		
		SgtongBorrowerInformationDto dto1 = new SgtongBorrowerInformationDto();
		dto1.setOrderNo(dto.getOrderNo());
		
		SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
		dto4.setOrderNo(dto.getOrderNo());
		dto4.setIsPlus(1);
		dto4.setIsDelete(2);

		List<SgtongBorrowerInformationDto> sborrowerInformationDtos = sgtongBorrowerInformationService.search(dto1);
		List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);//获取已推送的影像资料
		
		logger.error("s1==="+sborrowerInformationDtos.size());
		logger.error("s2=="+sgtongBusinfoDtos.size());
		logger.error("s2=="+sborrowerInformationDtos.get(0).getBatchNo());
		logger.error(sborrowerInformationDtos.size() > 0 &&sgtongBusinfoDtos.size() > 0 
				&& sborrowerInformationDtos.get(0).getBatchNo()!=null);
		
		//推送过的查询状态,没有推送的直接返回为推送
		if ( sborrowerInformationDtos.size() > 0 
				&& sborrowerInformationDtos.get(0).getBatchNo()!=null
				) {
			borrowstatus=sborrowerInformationDtos.get(0).getPushStatus();//设置上一次推送状态
			
			if (sgtongBusinfoDtos.size()>0) {
				businfostatus=sgtongBusinfoDtos.get(0).getPushStatus();//设置上一次推送状态
			}
			
		
			if (sborrowerInformationDtos.get(0).getBatchNo()!=null  ) {
				ret.put("pushstatus","SUCCESS");	//如执行设置表示已经推送过资料
			}
			
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("type",2);
			maps.put("txCode","2002");
			maps.put("brNo", Constants.SGT_WS_BRNO);
			maps.put("batchNo",sborrowerInformationDtos.get(0).getBatchNo());
			maps.put("prePactNo", sborrowerInformationDtos.get(0).getPrePactNo());

			String jsonObject = JSONObject.fromObject(maps).toString();  
			logger.error("查询资料推送状态请求参数："+jsonObject);
			RespDataObject<Map<String, Object>> s=SGTWsHelper.interfaceCall(maps);//查询资料推送状态
			           // s.setCode("SUCCESS");  // 
		                //s.setMsg("成功");   
			logger.error("查询资料推送状态返回结果："+ s);
		
			SgtongBorrowerInformationDto dto12 = new SgtongBorrowerInformationDto();
			dto12.setId(sborrowerInformationDtos.get(0).getId());
			
			  if ("SUCCESS".equals(s.getCode())) {
				  dto12.setPushStatus("审核通过");
			    	
				}else {
					dto12.setPushStatus("审核不通过");
				}
			sgtongBorrowerInformationService.update(dto12);//更新资料推送状态
			
				
					RespDataObject<Map<String, Object>> ss=null;
					String retStr=" \n2)不通过的影像资料类型:";
					String  businfoDtoStatus= ""; //全部影像资料审核的总结果
					
					
					
				if (sgtongBusinfoDtos.size() > 0) {

					HashSet<String> batchNos = new HashSet<String>();// 找出上传全部批次
					for (SgtongBusinfoDto sgtongBusinfoDto2 : sgtongBusinfoDtos) {
						batchNos.add(sgtongBusinfoDto2.getBatchNo());
					}

					for (String batchNo : batchNos) {
					    
					    Map<String, Object> maps2 = new HashMap<String, Object>();
						maps2.put("type",2);
						maps2.put("txCode","2107");
						maps2.put("brNo", Constants.SGT_WS_BRNO);
						maps2.put("batNo",batchNo);
						maps2.put("transNo",sgtongBusinfoDtos.get(0).getPrePactNo());

						String jsonObject2 = JSONObject.fromObject(maps2).toString();  
						logger.error("查询影像资料推送状态请求参数："+jsonObject2);
					   ss=   SGTWsHelper.interfaceCall(maps2);//查询影像资料推送状态
						logger.error("查询影像资料推送状态返回结果："+ss);
					    
					   logger.error("数据打印"+ss.getData());
					   
					   JSONArray jsonArray=JSONArray.fromObject(ss.getData().get("list"));
						
							
						for (SgtongBusinfoDto sgtongBusinfoDto2 : sgtongBusinfoDtos) {//更新影像资料审核状态
							
							if (batchNo.equals(sgtongBusinfoDto2.getBatchNo())) {
								SgtongBusinfoDto dd=new SgtongBusinfoDto();
							    dd.setId(sgtongBusinfoDto2.getId());
						   
						   for (Object object : jsonArray) {
							   JSONObject json=  JSONObject.fromObject( object);
							 							   
							   if (sgtongBusinfoDto2.getTypeId()==getTypeId(String.valueOf(json.get("docType")))) {
								   logger.error("json："+json);

								   if ("4".equals(json.get("docSts"))) {
								    	dd.setPushStatus("审核通过");
								    	businfoDtoStatus=businfoDtoStatus+ ",SUCCESS";
								    	
								    	//ss.setCode("SUCCESS");
									}else {
										dd.setPushStatus("审核不通过");
										//ss.setCode("FAIL");
										businfoDtoStatus=businfoDtoStatus+ ",FAIL";
										retStr=retStr+getTypeName(getTypeId(String.valueOf(json.get("docType"))))+",";
									}
								   
								   sgtongBusinfoService.update(dd);
							    } 
							   
						     }
						  }
						}
				
						if (businfoDtoStatus.indexOf("FAIL")>-1) {
							ss.setCode("FAIL");
						}else {
							ss.setCode("SUCCESS");
						}
					}

				} else {
					ret.put("businfostatus", "未推送");
				}

		            // 设置影像资料的审核结果
		           if (ss!=null) {
				    	 ret.put("businfostatus",ss.getCode());
				    	 ret.put("businfostatusMsg",retStr);
					}
					
					// 设置资料推送审核结果
					ret.put("borrowstatus", s.getCode());
					
					if (s.getMsg()!=null && !"SUCCESS".equals(s.getCode())) {
						 JSONArray jsonArray=JSONArray.fromObject(s.getMsg());
						   JSONObject json=  JSONObject.fromObject( jsonArray.get(0));
						 ret.put("borrowstatusMsg","1)"+json.get("dealDesc"));
						 
					}else {
						
						ret.put("borrowstatusMsg",s.getMsg());
					}

		}else {
	    	   ret.put("code", "FAIL");
	    	   ret.put("msg", "未推送");
	    	   
	    	   if (sgtongBusinfoDtos.size()==0) {
	    		   ret.put("businfostatus", "未推送");
			}
		}
		
		
		return RespHelper.setSuccessDataObject(resp, ret);
		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询上传状态失败");
			
			if (borrowstatus!=null ) {
				if ("审核通过".equals(borrowstatus)) {
					ret.put("borrowstatus","SUCCESS");
				}else if ("审核不通过".equals(borrowstatus)) { 
					ret.put("borrowstatus","FAIL");
				}else {
					ret.put("borrowstatus","M");
				}
				
				resp.setCode("SUCCESS");
			}
			if (businfostatus!=null) {
				if ("审核通过".equals(businfostatus)) {
					ret.put("businfostatus","SUCCESS");
				}else if ("审核不通过".equals(businfostatus)) { 
					ret.put("businfostatus","FAIL");
				}else {
					ret.put("businfostatus","M");
				}
			}
			
			
			return RespHelper.setSuccessDataObject(resp, ret);
		}
	}

	public int getTypeId(String typdCode) {
		int typdId=0;
		if ("DKSQB".equals(typdCode)) {//贷款申请表
			typdId=70320;
		}
		if ("ZXBG".equals(typdCode)) {//征信报告
			typdId=70321;
		}
		if ("WPNL".equals(typdCode)) {//身份证复印件
			typdId=70322;
		}
		if ("JHZ".equals(typdCode)) {//结婚证复印件
			typdId=70323;
		}
		if ("DKHTDZ".equals(typdCode)) {//贷款合同
			typdId=70324;
		}
		if ("KKSQS".equals(typdCode)) {//代扣授权书
			typdId=70325;
		}
		if ("GRCZSQS".equals(typdCode)) {//征信授权书
			typdId=70326;
		}
//		if (typdId==70321) {//工商营业执照/购物发票/消费合同
//			ss="GSYYZZ";
//		}
		if ("RYHZ".equals(typdCode)) {//入押回执
			typdId=70328;
		}
		if ("TXQZ".equals(typdCode)) {//他项权证
			typdId=70329;
		}
		if ("FCZ".equals(typdCode)) {//房产证/不动产证
			typdId=70330;
		}
		if ("FWCC".equals(typdCode)) {//房屋查册记录
			typdId=70331;
		}
		if ("PGYJ".equals(typdCode)) {//评估依据
			typdId=70332;
		}
		
		return typdId;
	}
	
	public String getTypeCode(Integer typdId) {
		String ss="";
		if (typdId==70320) {//贷款申请表
			ss="DKSQB";
		}
		if (typdId==70321) {//征信报告
			ss="ZXBG";
		}
		if (typdId==70322) {//身份证复印件
			ss="WPNL";
		}
		if (typdId==70323) {//结婚证复印件
			ss="JHZ";
		}
		if (typdId==70324) {//贷款合同
			ss="DKHTDZ";
		}
		if (typdId==70325) {//代扣授权书
			ss="KKSQS";
		}
		if (typdId==70326) {//征信授权书
			ss="GRCZSQS";
		}
//		if (typdId==70321) {//工商营业执照/购物发票/消费合同
//			ss="GSYYZZ";
//		}
		if (typdId==70328) {//入押回执
			ss="RYHZ";
		}
		if (typdId==70329) {//他项权证
			ss="TXQZ";
		}
		if (typdId==70330) {//房产证/不动产证
			ss="FCZ";
		}
		if (typdId==70331) {//房屋查册记录
			ss="FWCC";
		}
		if (typdId==70332) {//评估依据
			ss="PGYJ";
		}
		
		return ss;
	}
	public String getTypeName(Integer typdId) {
		String ss="";
		if (typdId==70320) {//贷款申请表
			ss="贷款申请表";
		}
		if (typdId==70321) {//征信报告
			ss="征信报告";
		}
		if (typdId==70322) {//身份证复印件
			ss="身份证复印件";
		}
		if (typdId==70323) {//结婚证复印件
			ss="结婚证复印件";
		}
		if (typdId==70324) {//贷款合同
			ss="贷款合同";
		}
		if (typdId==70325) {//代扣授权书
			ss="代扣授权书";
		}
		if (typdId==70326) {//征信授权书
			ss="征信授权书";
		}
//		if (typdId==70321) {//工商营业执照/购物发票/消费合同
//			ss="GSYYZZ";
//		}
		if (typdId==70328) {//入押回执
			ss="入押回执";
		}
		if (typdId==70329) {//他项权证
			ss="他项权证";
		}
		if (typdId==70330) {//房产证/不动产证
			ss="房产证/不动产证";
		}
		if (typdId==70331) {//房屋查册记录
			ss="房屋查册记录";
		}
		if (typdId==70332) {//评估依据
			ss="评估依据";
		}
		
		return ss;
	}
	@Override
	public RespDataObject<Map<String, Object>> searchSgtInfo(@RequestBody Map<String, Object> dto) {
		RespDataObject<Map<String, Object>>ret=new RespDataObject<Map<String, Object>>();
		Map<String,Object>map=new HashMap<String,Object>();
		BaseCustomerBorrowerDto baseCustomerBorrowerDto=	orderApi.findCustomerBorrowerByOrderNo(String.valueOf(dto.get("orderNo")));
		BaseCustomerGuaranteeDto baseCustomerGuaranteeDto=orderApi.findCustomerGuaranteeByOrderNo(String.valueOf(dto.get("orderNo")));
		CreditDto  creditDto=orderApi.findOrderCredit(String.valueOf(dto.get("orderNo")));
		
		List<RiskEnquiryDto>  riskEnquiryDto=orderApi.findOrderRiskEnquiry(String.valueOf(dto.get("orderNo")));
		
		
		BaseHouseDto baseHouseDto=	orderApi.findHouseByOrderNo(String.valueOf(dto.get("orderNo")));
		List<Map<String, Object>> housePropertyPeople=	orderApi.findHousePropertyPeopleOrderNo(String.valueOf(dto.get("orderNo")));
		List<Map<String, Object>> houseProperty=	orderApi.findHousePropertyOrderNo(String.valueOf(dto.get("orderNo")));

		List<Map<String, Object>> businfolist=	orderApi.selectAllBusInfo(String.valueOf(dto.get("orderNo")));
		
		
		SgtongBusinfoDto businfodto=new SgtongBusinfoDto();
		        businfodto.setOrderNo(String.valueOf(dto.get("orderNo")));
		        businfodto.setIsDelete(2);
	      List<SgtongBusinfoDto>  businfolists=	sgtongBusinfoService.search(businfodto);
	      
	      if (businfolists.size()==0) {
	    		for (Map<String, Object> map2 : businfolist) {
	    			if(isStgBusinfo(Integer.valueOf(String.valueOf(map2.get("typeId"))))) {
	    			SgtongBusinfoDto businfo=new SgtongBusinfoDto();
	    			          businfo.setOrderNo(String.valueOf(map2.get("orderNo")));
	    			          businfo.setUrl(String.valueOf(map2.get("url")));
	    			         // businfo.setType(value);
	    			          businfo.setCreateUid(String.valueOf(map2.get("createUid")));
	    			          businfo.setTypeId(changTypId(Integer.valueOf(String.valueOf((map2.get("typeId"))))));
	    			          businfo.setIsPlus(2);
	    			          businfo.setIsDelete(2);
	    			          
	    			sgtongBusinfoService.insert(businfo);
	    			
	    			}
	    		}
	    		
		}
	    		  
	
		
		
		//map.put("baseBorrow", baseBorrowDto);
		map.put("baseHouse", baseHouseDto);
		map.put("housePropertyPeople", housePropertyPeople);
		map.put("baseCustomerBorrowerDto", baseCustomerBorrowerDto);
		map.put("baseCustomerGuaranteeDto", baseCustomerGuaranteeDto);
		
		map.put("houseProperty", houseProperty);
		map.put("creditDto", creditDto);
		if (riskEnquiryDto.size()>0) {
			map.put("riskEnquiryDto", riskEnquiryDto.get(0));
		}
	
		ret.setCode(RespStatusEnum.SUCCESS.getCode());
		ret.setMsg(RespStatusEnum.SUCCESS.getMsg());
		ret.setData(map);
		
		return ret;
	}

	private Integer changTypId(Integer oldTypeId) {
		int newtypeId = 0;
		if (oldTypeId==70001) {
			 newtypeId=70324;
		}
		if (oldTypeId==70005) {
			 newtypeId=70320;
		}
		if (oldTypeId==70051) {
			 newtypeId=70322;
		}
		if (oldTypeId==70053) {
			 newtypeId=70321;
		}
		if (oldTypeId==70054) {
			 newtypeId=70323;
		}
		if (oldTypeId==70101) {
			 newtypeId=70330;
		}
		if (oldTypeId==70103) {
			 newtypeId=70331;
		}
		return newtypeId;
		
	}

	private boolean isStgBusinfo(Integer typeId) {

		if (   typeId == 70001 || typeId == 70005 || typeId == 70051 || typeId == 70053 || typeId == 70054
				|| typeId ==70101 || typeId == 70103 ) {
				
            	 return true;
			}else {
				return false;
			}
		
		
	}

	@Override
	public RespDataObject<Map<String, Object>> searchPushStatus2(@RequestBody SgtongBorrowerInformationDto dto) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String,Object> ret=new HashMap<String,Object>();

		try {
	
		SgtongBorrowerInformationDto dto1 = new SgtongBorrowerInformationDto();
		dto1.setOrderNo(dto.getOrderNo());
		
		SgtongBusinfoDto dto4 = new SgtongBusinfoDto();
		dto4.setIsDelete(2);
		dto4.setIsPlus(1);
		dto4.setOrderNo(dto.getOrderNo());

		List<SgtongBorrowerInformationDto> sborrowerInformationDtos = sgtongBorrowerInformationService.search(dto1);
		List<SgtongBusinfoDto> sgtongBusinfoDtos = sgtongBusinfoService.search(dto4);
		
		
		
		
		if ( sborrowerInformationDtos.size() > 0 &&sgtongBusinfoDtos.size() > 0) {
		
			if (sborrowerInformationDtos.get(0).getBatchNo()!=null  ) {
				ret.put("pushstatus","SUCCESS");	
			}

	       }else if( sborrowerInformationDtos.size()>0 && sgtongBusinfoDtos.size() == 0) {
	    	   
	    	   ret.put("code", "FAIL");
	    	   ret.put("msg", "未推送");
	    	   
	       }
		
		else {
	    	   ret.put("code", "FAIL");
	    	   ret.put("msg", "未推送");
		}
		return RespHelper.setSuccessDataObject(resp, ret);
		
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询上传状态失败");
			return RespHelper.setSuccessDataObject(resp, ret);
		}
	}

}