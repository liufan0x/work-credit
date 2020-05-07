package com.anjbo.service.impl;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.common.*;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.service.OrderBaseService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author lic
 * @date 2017-6-5
 */
@Service
public class OrderBaseServiceImpl implements OrderBaseService {
	
	@Resource
	private OrderBaseMapper orderBaseMapper;
	
	@Override
	public int fundOrderCount(Map<String,Object> map) {
		if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==26){
			return orderBaseMapper.fundHAOrderCount(map);
		}else if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==31){
			return orderBaseMapper.fundOrderCount(map);
		}else{
			return orderBaseMapper.fundOtherOrderCount(map);
		}
	}
	
	@Override
	public List<OrderListDto> fundOrderList(Map<String,Object> map) {
		if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==26){
			return orderBaseMapper.fundHAOrderList(map);
		}else if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==31){
			return orderBaseMapper.fundOrderList(map);
		}else if (map.get("fundId")!=null&&(MapUtils.getInteger(map, "fundId")==37||MapUtils.getInteger(map, "fundId")==38)){         //新加
			return orderBaseMapper.fundOtherOrderList(map);
		}else{         //新加
			return orderBaseMapper.fundOtherOrderLists(map);
		}
	}

	@Override
	public CustomerFundDto selectFundByUid(String uid) {
		return orderBaseMapper.selectFundByUid(uid);
	}
	
	public RespPageData<StatisticsDto> queryBalance(RespPageData<StatisticsDto> result, Map<String,Object> params){
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		handTime(params);
		int count = orderBaseMapper.selectOrderCount(params);
		result.setTotal(count);
		if(count<=0){
			result.setRows(Collections.EMPTY_LIST);
			return result;
		}
		List<StatisticsDto> list = orderBaseMapper.selectOrderList(params);
		String orderNo = "";
		try {
			String bankName="";
			String sunBankName="";
			String oldbankName="";
			String oldsunBankName="";
			for (StatisticsDto orderListDtoTemp : list) {
				bankName = "";
				sunBankName = "";
				oldbankName = "";
				oldsunBankName = "";
				if(StringUtils.isBlank(orderNo)){
					orderNo = "'"+orderListDtoTemp.getOrderNo()+"'";
				} else {
					orderNo += ",'"+orderListDtoTemp.getOrderNo()+"'";
				}

				if(StringUtils.isNotEmpty(orderListDtoTemp.getPlanPaymentTime())){
					orderListDtoTemp.setDistancePaymentDay(""+ DateUtil.betDays(new Date(),DateUtil.parse(orderListDtoTemp.getPlanPaymentTime(), DateUtil.FMT_TYPE1)));
				}
//				if(null==orderListDtoTemp.getIsLoanBank()||2==orderListDtoTemp.getIsLoanBank()){
//					if(StringUtil.isBlank(orderListDtoTemp.getLoanBankName()))
//						orderListDtoTemp.setLoanBankName("-");
//				} else {
//					if(orderListDtoTemp.getLoanBankNameId()!=null ){
//						bankName = CommonDataUtil.getBankNameById(orderListDtoTemp.getLoanBankNameId()).getName();
//						bankName = (null==bankName||"null".equals(bankName))?"":bankName;
//					}
//					if(orderListDtoTemp.getLoanSubBankNameId()!=null ){
//						sunBankName = CommonDataUtil.getSubBankNameById(orderListDtoTemp.getLoanSubBankNameId()).getName();
//						sunBankName = (null==sunBankName||"null".equals(sunBankName))?"":sunBankName;
//					}
//					orderListDtoTemp.setLoanBankName(bankName+"-"+sunBankName);
//				}
//				if(null==orderListDtoTemp.getIsOldLoanBank()||2==orderListDtoTemp.getIsOldLoanBank()){
//					if(StringUtil.isBlank(orderListDtoTemp.getOldLoanBankName()))
//						orderListDtoTemp.setOldLoanBankName("-");
//				} else {
//					if(orderListDtoTemp.getOldLoanBankNameId()!=null ){
//						oldbankName = CommonDataUtil.getBankNameById(orderListDtoTemp.getOldLoanBankNameId()).getName();
//						oldbankName = (null==oldbankName||"null".equals(oldbankName))?"":oldbankName;
//					}
//					if(orderListDtoTemp.getOldLoanBankSubNameId()!=null ){
//						oldsunBankName = CommonDataUtil.getSubBankNameById(orderListDtoTemp.getLoanSubBankNameId()).getName();
//						oldsunBankName = (null==oldsunBankName||"null".equals(oldsunBankName))?"":oldsunBankName;
//					}
//					orderListDtoTemp.setOldLoanBankName(oldbankName+"-"+oldsunBankName);
//				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(orderNo)) {
			HttpUtil httpUtil = new HttpUtil();
			//获取全部资金方代号
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			RespDataObject<Map<String, Object>> riskList = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/getOrderNoMosaicFundCode", map, Map.class);

			if(RespStatusEnum.SUCCESS.getCode().equals(riskList.getCode())
					&&null!=riskList.getData()
					&&riskList.getData().size()>0){
				Map<String,Object> risk = riskList.getData();
				String fundCode = "";
				for (StatisticsDto orderListDtoTemp : list) {
					fundCode = MapUtils.getString(risk,orderListDtoTemp.getOrderNo(),"-");
					if(null==fundCode||"null".equals(fundCode)){
						fundCode = "-";
					}
					orderListDtoTemp.setFundCodes(fundCode);
				}
			}
		}
		//获取借款信息
		result.setRows(list);
		return result;
	}

	public RespPageData<StatisticsDto> managementExamination(RespPageData<StatisticsDto> result,Map<String,Object> params){
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		handTime(params);
		int count = orderBaseMapper.selectRiskCount(params);
		result.setTotal(count);
		if(count<=0){
			result.setRows(Collections.EMPTY_LIST);
			return result;
		}
		List<StatisticsDto> list = orderBaseMapper.selectRiskList(params);
		long nh = 1000 * 60 * 60;
		for (StatisticsDto order : list) {
			//计算终审处理时长（小时）
			if(order.getFirstAuditTime()!=null && order.getFinalAuditTime()!=null){
				Date audit = order.getFirstAuditTime(); //初审时间
				Date finalAudit = order.getFinalAuditTime();   //终审时间
				long sdateMillis = audit.getTime();
				long edateMillis = finalAudit.getTime();
				long bettweenMillis = edateMillis - sdateMillis;
				long hour = bettweenMillis / nh;//计算差多少小时
				order.setFinalAuditTimeStr(hour+"");
			}
			//计算初审处理时长
			if(order.getAuditTime()!=null && order.getFirstAuditTime()!=null){
				Date auditTime = order.getAuditTime();   //经理审批时间
				Date firstAudit = order.getFirstAuditTime();  //初审时间
				long sdateMillis = auditTime.getTime();
				long edateMillis = firstAudit.getTime();
				long bettweenMillis = edateMillis - sdateMillis;
				long hour = bettweenMillis / nh;//计算差多少小时
				order.setFirstAuditTimeStr(hour+"");
			}
		}
		result.setRows(list);
		return result;
	}

	public RespPageData<StatisticsDto> returnBack(RespPageData<StatisticsDto> result,Map<String,Object> params){
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		handTime(params);
		int count = orderBaseMapper.selectBackCount(params);
		result.setTotal(count);
		if(count<=0){
			result.setRows(Collections.EMPTY_LIST);
			return result;
		}
		List<StatisticsDto> list = orderBaseMapper.selectBackList(params);
		Map<String,Object> tmp = new HashMap<String,Object>();
		for (StatisticsDto order:list){
			order.setSource(CommonDataUtil.getUserDtoByUidAndMobile(order.getUpdateUid()).getName());
			tmp.put("handleTime",order.getBackTime());
			tmp.put("orderNo",order.getOrderNo());
			String beginHandleTimeStr = orderBaseMapper.selectHandTime(tmp);
			order.setBeginHandleTimeStr(beginHandleTimeStr);
		}
		result.setRows(list);
		return result;
	}

	public void handTime(Map<String,Object> paramMap){
		String startTime = MapUtils.getString(paramMap,"startTime","");
		String endTime = MapUtils.getString(paramMap,"endTime","");
		if("null".equals(startTime)&& "null".equals(endTime)){
			Integer days = MapUtils.getInteger(paramMap,"days",null);
			if(null!=days){
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				Date date= DateUtils.addDate(new Date(), days-1);
				startTime= dateFormat.format(new Date());
				endTime = dateFormat.format(date);
				paramMap.put("startTime",startTime);
				paramMap.put("endTime",endTime);
			}
		}
	}

	@Override
	public OrderBaseBorrowDto queryBorrow(Map<String,Object> map) {
		if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==26){
			return orderBaseMapper.queryHABorrow(map);
		}else if(map.get("fundId")!=null&&MapUtils.getInteger(map, "fundId")==31){
			return orderBaseMapper.queryHRBorrow(map);
		}else if(map.get("fundId")!=null&&(MapUtils.getInteger(map, "fundId")==37||MapUtils.getInteger(map, "fundId")==38)) {
			return orderBaseMapper.queryYNBorrow(map);
		}else{
			return orderBaseMapper.queryOtherBorrow(map);
		}
	}

	@Override
	public Map<String,Object> selectSendRiskInfo(Map<String,Object> map) {
		return orderBaseMapper.selectSendRiskInfo(map);
	}

	
	/*
	 * 要件管理信息
	 * */
	@Override
	public List<Map<String, Object>> selectElementList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return orderBaseMapper.selectElementList(param);
	}
    
	
	/*
	 * 要件管理条数统计
	 * */
	@Override
	public int selectElementCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return orderBaseMapper.selectElementCount(param);
	}

	@Override
	public List<Map<String, Object>> selectAllElementList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return orderBaseMapper.selectAllElementList(param);
	}
}
