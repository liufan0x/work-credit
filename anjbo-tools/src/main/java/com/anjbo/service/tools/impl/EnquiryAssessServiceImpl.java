package com.anjbo.service.tools.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespStatus;
import com.anjbo.dao.tools.EnquiryAssessMapper;
import com.anjbo.service.tools.EnquiryAssessService;
import com.anjbo.service.tools.EnquiryService;


@Service
public class EnquiryAssessServiceImpl implements EnquiryAssessService {
	
	@Resource
	private EnquiryService enquiryService;
	
	@Resource
	private EnquiryAssessMapper enquiryAssessMapper;

	@Override
	public RespStatus tzcEnquiryAccess(EnquiryAssessDto assess)
			throws MortgageException {
		
		return enquiryService.applyAssessTZC(assess);
	}

	public RespStatus tzcWebReportApply(Map<String, String> params)
			throws MortgageException {
		
		return enquiryService.applyAssessReportTZC(params);
	}

	@Override
	public int addEnquiryAccess(EnquiryAssessDto assessDto) throws MortgageException {
		// 添加评估信息
		return enquiryAssessMapper.addEnquiryAccess(assessDto);
	}



	@Override
	public List<EnquiryAssessDto> findAlreadyassess(EnquiryAssessDto assess) {
		// TODO Auto-generated method stub
		return enquiryAssessMapper.findAlreadyassess(assess);
	}

	@Override
	public EnquiryAssessDto selEnquiryAssess(EnquiryAssessDto assessDto) {
		// TODO Auto-generated method stub
		return enquiryAssessMapper.selEnquiryAssess(assessDto);
	}

	public int updateAccessByApply(EnquiryAssessDto assess) {
		return this.enquiryAssessMapper.updateAccessByApply(assess);
	}

	@Override
	public List<EnquiryAssessDto> selEnquiryAssessAll(String uid, int pagesize,
			int pageindex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", uid);
		map.put("start", pageindex);
		map.put("count", pagesize);
		return enquiryAssessMapper.selEnquiryAssessAll(map);
	}

	public EnquiryAssessDto findById(String id) {
		return enquiryAssessMapper.findById(id);
	}

	public int updateStatus(String id, int status,String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("content", content);
		return this.enquiryAssessMapper.updateStatus(map);
	}

	public int findEnquiryIdBySerialid(String serialid) {
		return this.enquiryAssessMapper.findEnquiryIdBySerialid(serialid);
	}

	public String findIdByEnquiryId(int enquiryId) {
		return this.enquiryAssessMapper.findIdByEnquiryId(enquiryId);
	}

	@Override
	public EnquiryAssessDto selEnquirAssessDetail(String id)
			throws MortgageException {
		// TODO Auto-generated method stub
		EnquiryAssessDto dto = enquiryAssessMapper.selEnquirAssessDetail(id);
//		OrderDto rs = orderService.findByGoodId(id);
//		String name = dto.getPropertyName();
//		name += dto.getBuildingnum();
//		name += dto.getRoomnum();
//		dto.setPropertyName(name);
//		dto.setAmount(rs.getAmount());
//		dto.setOrderid(rs.getOrderId());
		return dto;
	}

	public int updateApplyTime(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		return this.enquiryAssessMapper.updateApplyTime(map);
	}

	public int updateApplyReportTime(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		return this.enquiryAssessMapper.updateApplyReportTime(map);
	}
	
	public int updateAssessIsRead(String id ,int isRead){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("isRead",isRead);
		return this.enquiryAssessMapper.updateAssessIsRead(map);
	}

	@Override
	public int selectAssessIsRead(String uid) {
		return enquiryAssessMapper.selectAssessIsRead(uid);
	}
	
	public EnquiryAssessDto findAssessByEnquiryId(int enquiryId){
		return enquiryAssessMapper.findAssessByEnquiryId(enquiryId);
	}

	@Override
	public int detailAssess(int enquiryId) {
		// TODO Auto-generated method stub
		return enquiryAssessMapper.detailAssess(enquiryId);
	}

	@Override
	public EnquiryAssessDto findBySerialid(String serialid) {
		return enquiryAssessMapper.findBySerialid(serialid);
	}
	
	@Override
	public Integer selectProgressIdByOrderNo(String orderNo) {
		Integer progressId =  enquiryAssessMapper.selectProgressIdByOrderNo(orderNo);
		return progressId==null ? 0 : progressId;
	}

	@Override
	public int updateEnquiryAccess(EnquiryAssessDto assess) {
		
		return this.enquiryAssessMapper.updateEnquiryAccess(assess);
	}

	@Override
	public List<EnquiryAssessDto> selEnquiryAssessByDistrict(String uid,
			String [] district, int pagesize, int pageindex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", uid);
		map.put("district", district);
		map.put("start", pageindex);
		map.put("count", pagesize);
		return enquiryAssessMapper.selEnquiryAssessByDistrict(map);
	}

	@Override
	public int delAssessById(String id) {
		
		return enquiryAssessMapper.delAssessById(id);
	}

	@Override
	public int updateIsClose(String id, int isClose) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isClose", isClose);
		return enquiryAssessMapper.updateIsClose(map);
	}

	@Override
	public int updateAssessByLimitApply(String id, int status, String content,
			double dealAmount, double loanAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("content", content);
		map.put("dealAmount", dealAmount);
		map.put("loanAmount", loanAmount);
		return enquiryAssessMapper.updateAssessByLimitApply(map);
	}
}
