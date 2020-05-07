/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.LoanPreparationDto;
import com.anjbo.bean.PreparationEditrecordDto;
import com.anjbo.bean.PreparationReplyrecordDto;
import com.anjbo.bean.UserDto;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.LoanPreparationMapper;
import com.anjbo.service.LoanPreparationService;
import com.anjbo.service.PreparationEditrecordService;
import com.anjbo.service.PreparationReplyrecordService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-25 16:16:13
 * @version 1.0
 */
@Service
public class LoanPreparationServiceImpl extends BaseServiceImpl<LoanPreparationDto>  implements LoanPreparationService {
	
	@Autowired private LoanPreparationMapper loanPreparationMapper;
	
	@Resource private PreparationEditrecordService preparationEditrecordService;

	@Resource private PreparationReplyrecordService preparationReplyrecordService;

	@Resource private UserApi userApi;

	@Resource private OrderApi orderApi;
	
	@Override
	public Map<String, Object> loanPreparationListMap(String orderNos) {
		List<LoanPreparationDto> list = loanPreparationMapper.loanPreparationListMap(orderNos);
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (LoanPreparationDto dto : list) {
			compareDate(map, dto);
		}
		return map;
	}
	
    public void compareDate(Map<String,Object> map,LoanPreparationDto obj){
        Integer status = MapUtils.getInteger(map,obj.getOrderNo(),-1);
        if(status==-1){
            map.put(obj.getOrderNo(),obj.getStatus());
            map.put("createTime",obj.getCreateTime().getTime());
            return;
        }
        Long date = MapUtils.getLong(map,"createTime",0L);
        if(obj.getCreateTime().getTime()>date){
            map.put(obj.getOrderNo(),obj.getStatus());
            map.put("createTime",obj.getCreateTime().getTime());
        }
    }
	
	@Override
	public List<LoanPreparationDto> search(LoanPreparationDto dto) {
		List<LoanPreparationDto> list = super.search(dto);
		
		List<PreparationEditrecordDto> preparationEditrecordDtos = preparationEditrecordService.search(null);
		
		List<PreparationReplyrecordDto> preparationReplyrecordDtos = preparationReplyrecordService.search(null);
		
		List<UserDto> userDtos = userApi.selectAllUserDto();
		
		Map<Integer, Object> sort = sort();
		
		for (LoanPreparationDto loanPreparationDto : list) {
			
			if(StringUtils.isNotEmpty(loanPreparationDto.getCreateUid())) {
				
				for (UserDto userDto : userDtos) {
					if(loanPreparationDto.getCreateUid().equals(userDto.getUid())) {
						loanPreparationDto.setDeptName(userDto.getDeptName());
					}
				}
				
				if(StringUtils.isNotEmpty(loanPreparationDto.getOrderNo())) {
					BaseListDto baseListDto = orderApi.findByOrderNo(loanPreparationDto.getOrderNo());
					if(baseListDto != null) {
						loanPreparationDto.setState(orderApi.findByOrderNo(loanPreparationDto.getOrderNo()).getState());
					}
				}
			}
			
			//拼接修改记录
			String  reportEditRecordStr = "";
			for (PreparationEditrecordDto preparationEditrecordDto : preparationEditrecordDtos) {
				if(loanPreparationDto.getId().equals(preparationEditrecordDto.getReportId())) {
					reportEditRecordStr += DateUtils.dateToString(preparationEditrecordDto.getCreateTime(), DateUtils.FMT_TYPE1) + ":" + preparationEditrecordDto.getColName();
					if(StringUtils.isNotBlank(preparationEditrecordDto.getEndVal())){
						reportEditRecordStr += "："+preparationEditrecordDto.getEndVal();
					}
					reportEditRecordStr += "<br/>";
				}
			}
			loanPreparationDto.setReportEditRecordStr(reportEditRecordStr);
			
			
			//拼接回复记录
			String reportReplyRecordStr = "";
			for (PreparationReplyrecordDto preparationReplyrecordDto : preparationReplyrecordDtos) {
				if(loanPreparationDto.getId().equals(preparationReplyrecordDto.getReportId())){
					reportReplyRecordStr += DateUtils.dateToString(preparationReplyrecordDto.getCreateTime(), DateUtils.FMT_TYPE1) +"："+preparationReplyrecordDto.getReplyContent()+"<br/>";
				}
			}
			loanPreparationDto.setReportReplyRecordStr(reportReplyRecordStr);
			if(sort.containsKey(loanPreparationDto.getId())) {
				loanPreparationDto.setSort(MapUtils.getString(sort, loanPreparationDto.getId()));
			}else {
				loanPreparationDto.setSort("暂未进入排队");
			}
		}
		return list;
	}
	
	
	private Map<Integer, Object> sort() {
		Map<Integer, Object> sort = new HashMap<Integer, Object>();
		LoanPreparationDto loanPreparationDto = new LoanPreparationDto();
		loanPreparationDto.setStatus(2);
		loanPreparationDto.setFinanceOutLoanTime(new Date());
		List<LoanPreparationDto> loanPreparationDtos = loanPreparationMapper.search(loanPreparationDto);
		int i = 1;
		int j = 1;
		for (LoanPreparationDto preparationDto : loanPreparationDtos) {
			System.out.println(preparationDto.getCustomerName()+":"+preparationDto.getFinanceOutLoanTime());
			if("1".equals(preparationDto.getTop()+"")) {
				sort.put(preparationDto.getId(), "优先排位：第"+i+"位");
				i++;
			}else {
				sort.put(preparationDto.getId(), "当前排位：第"+j+"位");
				j++;
			}
		}
		return sort;
	}
	
}
