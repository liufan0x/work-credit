package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.dao.huanTempMapper;
import com.anjbo.service.huanTempService;

@Service
public class huanTempServiceImpl implements huanTempService {

	@Resource huanTempMapper huanTempMapper;
	@Override
	public List<Map<String, Object>> findByAll() {
		// TODO Auto-generated method stub
		return huanTempMapper.findByAll();
	}
	@Override
	public List<Map<String, Object>> findByAllHuarong() {
		// TODO Auto-generated method stub
		return huanTempMapper.findByAllHuarong();
	}
	@Override
	public LendingDto findByLending(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByLending(orderNo);
	}
	@Override
	public int updOrderList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updOrderList(map);
	}
	@Override
	public int updFinanceLending(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updFinanceLending(map);
	}
	@Override
	public int updFinanceReceivable(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updFinanceReceivable(map);
	}
	@Override
	public DocumentsReturnDto finyByReturn(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.finyByReturn(orderNo);
	}
	@Override
	public int updateReturn(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updateReturn(map);
	}
	@Override
	public ReceivablePayDto findByPay(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByPay(orderNo);
	}
	@Override
	public int updatePay(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updatePay(map);
	}
	@Override
	public RebateDto findByRebate(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByRebate(orderNo);
	}
	@Override
	public int updateRebate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updateRebate(map);
	}
	@Override
	public OrderFlowDto findByFlow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByFlow(map);
	}
	@Override
	public int updateOrderFlow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updateOrderFlow(map);
	}
	@Override
	public List<String> selectOrderNoAll() {
		// TODO Auto-generated method stub
		return huanTempMapper.selectOrderNoAll();
	}
	@Override
	public List<OrderFlowDto> selectOrderFlow(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.selectOrderFlow(orderNo);
	}
	@Override
	public int updateFlow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updateFlow(map);
	}
	@Override
	public List<String> selectOrderNoAlls(String city) {
		// TODO Auto-generated method stub
		return huanTempMapper.selectOrderNoAlls(city);
	}
	@Override
	public String numTimeCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.numTimeCount(map);
	}
	@Override
	public int findByBack(String cityName) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByBack(cityName);
	}
	@Override
	public List<Map<String, Object>> fingByBackList(String cityName){
		return huanTempMapper.fingByBackList(cityName);
	}
	@Override
	public List<String> selectOrderNoAll2() {
		// TODO Auto-generated method stub
		return huanTempMapper.selectOrderNoAll2();
	}
	@Override
	public List<OrderFlowDto> selectOrderFlow2(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.selectOrderFlow2(orderNo);
	}
	@Override
	public int updateFlow2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return huanTempMapper.updateFlow2(map);
	}
	@Override
	public ReceivableForDto findByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return huanTempMapper.findByOrderNo(orderNo);
	}
	@Override
	public int addFlow(OrderFlowDto flowDto){
		huanTempMapper.deleteFlow(flowDto);
		return huanTempMapper.addFlow(flowDto);
	}
}
