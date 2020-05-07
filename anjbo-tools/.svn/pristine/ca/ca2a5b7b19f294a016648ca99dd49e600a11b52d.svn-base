package com.anjbo.service.tools.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.tools.AssessReportDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.tools.AssessReportDtoMapper;
import com.anjbo.service.tools.AssessReportService;
import com.anjbo.utils.CheckUtil;
import com.anjbo.utils.StringUtil;

@Service
public class AssessReportServiceImpl implements AssessReportService{
	
	private Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AssessReportDtoMapper assessReportDtoMapper;
	
	@Override
	public AssessReportDto findAssessReportDto(String orderNo) {
		AssessReportDto assessReportDto = assessReportDtoMapper.findAssessReportDto(orderNo);
		return assessReportDto;
	}
	
	@Override
	@Transactional
	public RespDataObject<AssessReportDto> insertAssessReport(Map<String,Object> params) {
		RespDataObject<AssessReportDto> resp = new RespDataObject<AssessReportDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			resp.setData(null);
			if(params == null){
				resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			} 
			
			AssessReportDto assessReportDto = new AssessReportDto();
			assessReportDto.setLoanAmount(MapUtils.getDouble(params, "loanAmount"));
			assessReportDto.setLoanPercent(MapUtils.getInteger(params, "loanPercent"));
			assessReportDto.setAddress(MapUtils.getString(params, "address"));
			assessReportDto.setBankId(MapUtils.getInteger(params, "bankId"));
			assessReportDto.setBankName(MapUtils.getString(params, "bankName"));
			assessReportDto.setClientManager(MapUtils.getString(params, "clientManager"));
			assessReportDto.setClientManagerTel(MapUtils.getString(params, "clientManagerTel"));
			assessReportDto.setCompanyId(MapUtils.getInteger(params, "companyId"));
			assessReportDto.setCompanyName(MapUtils.getString(params, "companyName"));
			assessReportDto.setDevice(MapUtils.getString(params, "device"));
			assessReportDto.setDeviceId(MapUtils.getString(params, "deviceId"));
			assessReportDto.setEmail(MapUtils.getString(params, "email"));
			assessReportDto.setHouseCard(MapUtils.getString(params, "houseCard"));
			assessReportDto.setLoanType(MapUtils.getInteger(params, "loanType"));
			assessReportDto.setOrderNo(MapUtils.getString(params, "orderNo"));
			assessReportDto.setPhotographPerson(MapUtils.getString(params, "photographPerson"));
			assessReportDto.setPhotographPersonTel(MapUtils.getString(params, "photographPersonTel"));
			assessReportDto.setSubBankId(MapUtils.getInteger(params, "subBankId"));
			assessReportDto.setSubBankName(MapUtils.getString(params, "subBankName"));
			assessReportDto.setPropertyName(MapUtils.getString(params, "propertyName"));
			
			if(assessReportDto.getLoanAmount() < 1){
				resp.setMsg("贷款金额至少1万元");
				return resp;
			}
			
			if(assessReportDto.getLoanPercent() <= 0){
				resp.setMsg("贷款成数必须大于等于1成");
				return resp;
			}
			
			if(StringUtil.isEmpty(assessReportDto.getBankName())){
				resp.setMsg("按揭银行不能为空！");
				return resp;
			}

			if(StringUtil.isEmpty(assessReportDto.getSubBankName())){
				resp.setMsg("支行名称不能为空！");
				return resp;
			}

			if(StringUtil.isEmpty(assessReportDto.getPhotographPerson())){
				resp.setMsg("拍照联系人不能为空！");
				return resp;
			}

			if(StringUtil.isEmpty(assessReportDto.getPhotographPersonTel())){
				resp.setMsg("拍照联系人电话不能为空！");
				return resp;
			}


			if(StringUtil.isEmpty(assessReportDto.getClientManager())){
				resp.setMsg("客户经理不能为空！");
				return resp;
			}

			if(StringUtil.isEmpty(assessReportDto.getClientManagerTel())){
				resp.setMsg("客户经理电话不能为空！");
				return resp;
			}
			
			if(StringUtil.isEmpty(assessReportDto.getEmail())){
				resp.setMsg("联系邮箱不能为空！");
				return resp;
			}
			
			if(!CheckUtil.isEmail(assessReportDto.getEmail())){
				resp.setMsg("不正确的邮箱格式地址！");
				return resp;
			}
			
			if(StringUtil.isEmpty(assessReportDto.getAddress())){
				resp.setMsg("邮寄地址不能为空！");
				return resp;
			}
			
			AssessReportDto bean = assessReportDtoMapper.findAssessReportDto(assessReportDto.getOrderNo());
			if (bean == null) {
				assessReportDto.setProgressId(1);
				assessReportDtoMapper.insertAssessReport(assessReportDto);
			}else{
				
				if (bean.getProgressId() > 3) {
					resp.setMsg("修改失败，该评估已出报告！");
					return resp;
				}
				
				assessReportDtoMapper.updateAssessReport(assessReportDto);
			}
			
			resp.setData(assessReportDto);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增评估报告信息错误",e);
		}
		
		return resp;
	}

	@Override
	public int updateAssessReportProgressId(String orderNo, int progressId) {
		return assessReportDtoMapper.updateAssessReportProgressId(orderNo, progressId);
	}
	
}
