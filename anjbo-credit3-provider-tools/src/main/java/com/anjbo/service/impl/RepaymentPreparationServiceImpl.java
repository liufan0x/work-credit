/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.PreparationEditrecordDto;
import com.anjbo.bean.PreparationReplyrecordDto;
import com.anjbo.bean.RepaymentPreparationDto;
import com.anjbo.bean.UserDto;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.RepaymentPreparationMapper;
import com.anjbo.service.PreparationEditrecordService;
import com.anjbo.service.PreparationReplyrecordService;
import com.anjbo.service.RepaymentPreparationService;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-06-25 16:16:15
 * @version 1.0
 */
@Service
public class RepaymentPreparationServiceImpl extends BaseServiceImpl<RepaymentPreparationDto>
		implements RepaymentPreparationService {

	@Autowired
	private RepaymentPreparationMapper repaymentPreparationMapper;

	@Resource
	private PreparationEditrecordService preparationEditrecordService;

	@Resource
	private PreparationReplyrecordService preparationReplyrecordService;

	@Resource
	private UserApi userApi;

	@Resource
	private OrderApi orderApi;

	@Override
	public Map<String, Object> repaymentPreparationListMap(String orderNos) {
		List<RepaymentPreparationDto> list = repaymentPreparationMapper.repaymentPreparationListMap(orderNos);
		if (null == list || list.isEmpty()) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (RepaymentPreparationDto dto : list) {
			compareDate(map, dto);
		}
		return map;
	}
	
    public void compareDate(Map<String,Object> map,RepaymentPreparationDto obj){
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
	public List<RepaymentPreparationDto> search(RepaymentPreparationDto dto) {
		List<RepaymentPreparationDto> list = super.search(dto);

		List<PreparationEditrecordDto> preparationEditrecordDtos = preparationEditrecordService.search(null);

		List<PreparationReplyrecordDto> preparationReplyrecordDtos = preparationReplyrecordService.search(null);

		List<UserDto> userDtos = userApi.selectAllUserDto();

		for (RepaymentPreparationDto repaymentPreparationDto : list) {

			if (StringUtils.isNotEmpty(repaymentPreparationDto.getCreateUid())) {
				for (UserDto userDto : userDtos) {
					if (repaymentPreparationDto.getCreateUid().equals(userDto.getUid())) {
						repaymentPreparationDto.setDeptName(userDto.getDeptName());
					}
				}

			}

			if (StringUtils.isNotEmpty(repaymentPreparationDto.getOrderNo())) {
				BaseListDto baseListDto = orderApi.findByOrderNo(repaymentPreparationDto.getOrderNo());
				if (baseListDto != null) {
					repaymentPreparationDto
							.setState(orderApi.findByOrderNo(repaymentPreparationDto.getOrderNo()).getState());
				}
			}

			// 拼接修改记录
			String reportEditRecordStr = "";
			for (PreparationEditrecordDto preparationEditrecordDto : preparationEditrecordDtos) {
				if (repaymentPreparationDto.getId().equals(preparationEditrecordDto.getReportId())) {
					reportEditRecordStr += DateUtils.dateToString(preparationEditrecordDto.getCreateTime(),
							DateUtils.FMT_TYPE1) + ":" + preparationEditrecordDto.getColName();
					if (StringUtils.isNotBlank(preparationEditrecordDto.getEndVal())) {
						reportEditRecordStr += "：" + preparationEditrecordDto.getEndVal();
					}
					reportEditRecordStr += "<br/>";
				}
			}
			repaymentPreparationDto.setReportEditRecordStr(reportEditRecordStr);

			// 拼接回复记录
			String reportReplyRecordStr = "";
			for (PreparationReplyrecordDto preparationReplyrecordDto : preparationReplyrecordDtos) {
				if (repaymentPreparationDto.getId().equals(preparationReplyrecordDto.getReportId())) {
					reportReplyRecordStr += DateUtils.dateToString(preparationReplyrecordDto.getCreateTime(),
							DateUtils.FMT_TYPE1) + "：" + preparationReplyrecordDto.getReplyContent() + "<br/>";
				}
			}
			repaymentPreparationDto.setReportReplyRecordStr(reportReplyRecordStr);

		}
		return list;
	}

}
