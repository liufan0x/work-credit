package com.anjbo.service.impl;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.vo.OrderReportLendingVo;
import com.anjbo.bean.vo.ChartVo;
import com.anjbo.common.DateUtil;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.service.OrderBaseService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.DateUtils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public List<OrderListDto> selectOrderList(OrderListDto orderListDto) {
		List<OrderListDto> list = orderBaseMapper.selectOrderList(orderListDto);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (OrderListDto orderListDtoTemp : list) {
			try {
				if(StringUtils.isNotEmpty(orderListDtoTemp.getPlanPaymentTime())){
					orderListDtoTemp.setDistancePaymentDay(""+DateUtil.betDays(new Date(),DateUtil.parse(orderListDtoTemp.getPlanPaymentTime(), DateUtil.FMT_TYPE1)));
				}
				if(orderListDtoTemp.getFinanceOutLoanTime()!=null){
					orderListDtoTemp.setFinanceOutLoanTimeStr(DateUtil.getDateByFmt(orderListDtoTemp.getFinanceOutLoanTime(), DateUtil.FMT_TYPE2));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public List<OrderListDto> selectOrderListApp(OrderListDto orderListDto) {
		List<OrderListDto> list = orderBaseMapper.selectOrderListApp(orderListDto);
		for (OrderListDto orderListDtoTemp : list) {
			try {
				if(StringUtils.isNotEmpty(orderListDtoTemp.getPlanPaymentTime())){
					orderListDtoTemp.setDistancePaymentDay(""+DateUtil.betDays(new Date(),DateUtil.parse(orderListDtoTemp.getPlanPaymentTime(), DateUtil.FMT_TYPE1)));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public int selectOrderCount(OrderListDto orderListDto) {
		return orderBaseMapper.selectOrderCount(orderListDto);
	}
	
	@Override
	public int selectlendingCount(OrderListDto orderListDto) {
		return orderBaseMapper.selectlendingCount(orderListDto);
	}
	
	@Override
	public int insertOrderList(OrderListDto orderListDto) {
		return orderBaseMapper.insertOrderList(orderListDto);
	}
	
	@Override
	public int updateOrderList(OrderListDto orderListDto) {
		return orderBaseMapper.updateOrderList(orderListDto);
	}

	@Override
	public OrderListDto selectDetail(String orderNo) {
		return orderBaseMapper.selectDetail(orderNo);
	}
	@Override
	public int submitAuditUpdateOrderList(OrderListDto orderListDto) {
		return orderBaseMapper.submitAuditUpdateOrderList(orderListDto);
	}

	@Override
	public List<OrderListDto> selectOrderAll(OrderListDto orderListDto) {
		// TODO Auto-generated method stub
		return orderBaseMapper.selectOrderAll(orderListDto);
	}

	@Override
	public int selectIsFace(String orderNo) {
		if(StringUtils.isBlank(orderNo))return 1;
		Integer isFace = orderBaseMapper.selectIsFace(orderNo);
		return null==isFace?1:isFace;
	}

	@Override
	public int updateIsFace(Map<String, Object> map) {
		return orderBaseMapper.updateIsFace(map);
	}

	@Override
	public ChartVo search4reportLendingChart(OrderListDto orderList, int lastMonth) {
		ChartVo vo = new ChartVo(lastMonth);
		try {
			orderList.setLendingTimeStart(DateUtils.dateToString(org.apache.commons.lang.time.DateUtils.addMonths(new Date(), -11), "yyyy-MM"));
			List<OrderReportLendingVo> list = orderBaseMapper.search4reportLendingChart(orderList);
			// 构建报表数据	
			if(null!=list && !list.isEmpty()){		
				for (int i = 0; i < lastMonth; i++) {
					for (OrderReportLendingVo bean : list) {
						if(vo.getNames()[i].equals(bean.getLendingTimeFmt().split("年")[1])){
							vo.getValues()[i] = bean.getReportSum();
							vo.getValues2()[i] = bean.getReportCount();
						}
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	@Override
	public List<OrderReportLendingVo> search4reportLending(OrderListDto orderList) {
		return orderBaseMapper.search4reportLending(orderList);
	}

	@Override
	public List<Map<String, Object>> findLoanStatisticMonth(String agencyId,
			String uids) {
		return orderBaseMapper.findLoanStatisticMonth(agencyId, uids);
	}

	@Override
	public Map<String, Object> findLoanStatisticTotal(String agencyId,
			String uids) {
		return orderBaseMapper.findLoanStatisticTotal(agencyId, uids);
	}

	@Override
	public List<Map<String, Object>> findLoanStatisticDetail(String agencyId,
			String uids, int year, int month) {
		return orderBaseMapper.findLoanStatisticDetail(agencyId, uids, year, month);
	}

	@Override
	public List<Map<String, Object>> findFinishStatisticMonth(String agencyId,
			String uids) {
		return orderBaseMapper.findFinishStatisticMonth(agencyId, uids);
	}

	@Override
	public Map<String, Object> findFinishStatisticTotal(String agencyId,
			String uids) {
		return orderBaseMapper.findFinishStatisticTotal(agencyId, uids);
	}

	@Override
	public List<Map<String, Object>> findFinishStatisticDetail(String agencyId,
			String uids, int year, int month) {
		return orderBaseMapper.findFinishStatisticDetail(agencyId, uids, year, month);
	}

	@Override
	public List<Map<String, Object>> findLoanByOrderNos(String orderNos) {
		return orderBaseMapper.findLoanByOrderNos(orderNos);
	}

	@Override
	public String findOrderNosByUids(String uids) {
		List<String> orderNoList = orderBaseMapper.findOrderNosByUids(uids);
		
		String orderNos = "";
		for (String orderNo : orderNoList) {
			orderNos += orderNo + ",";
		}
		if(StringUtils.isNotEmpty(orderNos)){
			orderNos = orderNos.substring(0, orderNos.length()-1);
		}
		
		return orderNos;
	}

	@Override
	public Map<String, Object> findLoanStatisticByMonthAndUids(
			String uids, int year, int month) {
		return orderBaseMapper.findLoanStatisticByMonthAndUids(uids, year, month);
	}

	@Override
	public List<Map<String, Object>> selectAllOrder(Map<String, Object> map) {
		List<Map<String, Object>> resultList=orderBaseMapper.selectAllOrder(map);
		for(Map<String, Object> o:resultList) {
			o.put("createTime", MapUtils.getString(o, "createTime"));
		}
		
		return resultList;
	}

	@Override
	public List<OrderListDto> selectAllOrderList() {
		
		return orderBaseMapper.selectAllOrderList();
	}

	@Override
	public List<Map<String, Object>> selectAbleRelationOrder(
			Map<String, Object> map) {
		return orderBaseMapper.selectAbleRelationOrder(map);
	}


	/**
	 * 获取合作机构放款之前回款之前放款金额
	 * @param map
	 * @return
	 */
	@Override
	public OrderListDto selectAgencyLoan(Map<String,Object> map){
		return orderBaseMapper.selectAgencyLoan(map);
	}

	@Override
	public Map<String, Object> selectNotarialUidByOrderNo(String orderNo) {
		return orderBaseMapper.selectNotarialUidByOrderNo(orderNo);
	}

	@Override
	public List<Map<String,Object>> selectUidOrderCount(Map<String, Object> map) {
		return orderBaseMapper.selectUidOrderCount(map);
	}
	
	@Override
	public OrderListDto selectOrderListByContractNo(OrderListDto orderListDto) {
		return orderBaseMapper.selectOrderListByContractNo(orderListDto);
	}

}
