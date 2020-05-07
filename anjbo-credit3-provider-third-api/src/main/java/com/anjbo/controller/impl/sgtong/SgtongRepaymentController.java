/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.sgtong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.TblFinanceAfterloanEqualInterestDto;
import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.ToolsApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.sgtong.ISgtongRepaymentController;
import com.anjbo.service.sgtong.SgtongBorrowerInformationService;
import com.anjbo.utils.StringUtil;
import com.anjbo.ws.sgt.SGTWsHelper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@RestController
public class SgtongRepaymentController extends BaseController implements ISgtongRepaymentController{

	@Resource private UserApi userApi;
	
	@Resource
	private OrderApi orderApi;
	
	@Resource
	private ToolsApi toolsApi;
	
	@Resource
	private SgtongBorrowerInformationService sgtongBorrowerInformationService;
	
	@Override
	public RespStatus tsYhkxx(@RequestBody Map<String, Object> map) {
		RespStatus resp = new RespStatus();
		RespDataObject<Map<String, Object>> respData = new RespDataObject<Map<String, Object>>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String orderNo = MapUtils.getString(map, "orderNo");
			TblFinanceAfterloanListDto afterloanListdto = new TblFinanceAfterloanListDto();
			afterloanListdto.setOrderNo(orderNo);
			RespDataObject<TblFinanceAfterloanListDto> respAfterloanListdto = toolsApi.findFinanceAfterloanList(afterloanListdto);
			afterloanListdto = respAfterloanListdto.getData();
			int  repaymentType = afterloanListdto.getRepaymentType();
			int count=0;
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if(repaymentType==1) {
				TblFinanceAfterloanFirstInterestDto dto = new TblFinanceAfterloanFirstInterestDto();
				dto.setOrderNo(orderNo);
				count = toolsApi.financeAfterloanFirstCount(dto).getData();
				List<TblFinanceAfterloanFirstInterestDto> list1 = toolsApi.searchFinanceAfterloanFirstInterestList(dto).getData();
				Map<String,Object> obj = new HashMap<String,Object>();
				for (int i=0;i<list1.size();i++) {
					TblFinanceAfterloanFirstInterestDto temp = list1.get(i);
					obj = new HashMap<String,Object>();
					obj.put("pactNo", "KG"+orderNo);
					obj.put("cnt", (i+1));
					obj.put("endDate", sdf.format(temp.getRepaymentDate()));
					obj.put("totalAmt", temp.getRepayAmount());
					obj.put("prcpAmt", temp.getRepayPrincipal());//当期本金
					obj.put("normInt", temp.getRepayInterest());
					list.add(obj);
				}
			}else if(repaymentType==2) {
				TblFinanceAfterloanEqualInterestDto dto = new TblFinanceAfterloanEqualInterestDto();
				dto.setOrderNo(orderNo);
				count = toolsApi.financeAfterloanEqualCount(dto).getData();
				List<TblFinanceAfterloanEqualInterestDto> list2 = toolsApi.searchFinanceAfterloanEqualInterestList(dto).getData();
				Map<String,Object> obj = new HashMap<String,Object>();
				for (int i=0;i<list2.size();i++) {
					TblFinanceAfterloanEqualInterestDto temp = list2.get(i);
					obj = new HashMap<String,Object>();
					obj.put("pactNo", "KG"+orderNo);
					obj.put("cnt", (i+1));
					obj.put("endDate", sdf.format(temp.getRepaymentDate()));
					obj.put("totalAmt", temp.getRepayAmount());
					obj.put("prcpAmt", temp.getRepayPrincipal());//当期本金
					obj.put("normInt", temp.getRepayInterest());
					list.add(obj);
				}
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("brNo", "0004");
			params.put("dataCnt", count);
			params.put("list", list);
			params.put("type",1);
			params.put("txCode","2201");
			String reqSerial = getNum();
			params.put("reqSerial", reqSerial);
			//保存流水号
			SgtongBorrowerInformationDto borrowerDto = new SgtongBorrowerInformationDto();
			borrowerDto.setOrderNo(orderNo);
			borrowerDto.setReqSerial(reqSerial);
			sgtongBorrowerInformationService.insert(borrowerDto);
			respData = SGTWsHelper.interfaceCall(params);
			System.out.println("2201返回："+respData.getCode());
			if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())||"0000".equals(respData.getCode())) {
				borrowerDto.setPushRepaymentStatus("处理中");
				sgtongBorrowerInformationService.insert(borrowerDto);
				RespHelper.setSuccessRespStatus(resp);
			}else {
				RespHelper.setFailRespStatus(resp, respData.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, respData.getMsg());
		}
		return resp;
	}

	/**
	 * 还款计划上传结果查询【2204】
	 */
	@Override
	public RespDataObject<Map<String, Object>> tsYhkxxResult(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			RespDataObject<Map<String, Object>>  respData = new RespDataObject<Map<String, Object>>();
			String orderNo = MapUtils.getString(map, "orderNo");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("brNo", "0004");
			params.put("type",2);
			//流水号
			SgtongBorrowerInformationDto borrowerDto = new SgtongBorrowerInformationDto();
			borrowerDto.setOrderNo(orderNo);
			borrowerDto = sgtongBorrowerInformationService.find(borrowerDto);
			params.put("reqSerial", borrowerDto.getReqSerial());
			params.put("txCode","2204");
			respData = SGTWsHelper.interfaceCall(params);
			if(StringUtil.isBlank(borrowerDto.getReqSerial())) {
				respData.setMsg("未推送还款计划");
			}
			if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())) {
				//更新推送还款计划状态
				borrowerDto.setPushRepaymentStatus("处理成功");
				sgtongBorrowerInformationService.insert(borrowerDto);
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("pushRepaymentStatus", borrowerDto.getPushRepaymentStatus());
				resp.setData(resultMap);
				RespHelper.setSuccessRespStatus(resp);
			}else {
				if(StringUtil.isNotBlank(borrowerDto.getReqSerial())) {
					borrowerDto.setPushRepaymentStatus("处理失败");
					sgtongBorrowerInformationService.insert(borrowerDto);
				}
				RespHelper.setFailRespStatus(resp, respData.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public String getNum() {                             //当前时分秒加上三位随机数
		int xterm  = (int) ((Math.random()*900)+100);
		SimpleDateFormat sfDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return sfDateFormat.format(new Date())+xterm;
	}
	
		
}