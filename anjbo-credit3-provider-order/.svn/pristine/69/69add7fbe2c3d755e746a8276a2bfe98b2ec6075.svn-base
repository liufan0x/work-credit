/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.Constants;
import com.anjbo.common.SMSConstants;
import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.ToolsApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.order.FlowMapper;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.FlowService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-10 16:09:44
 * @version 1.0
 */
@Service
public class FlowServiceImpl extends BaseServiceImpl<FlowDto> implements FlowService {
	@Autowired
	private FlowMapper flowMapper;

	@Resource
	private BaseListService baseListService;

	@Resource
	private ThirdApi thirdApi;

	@Resource
	private UserApi userApi;
	
	@Resource
	private ToolsApi toolsApi;

	@Override
	public FlowDto insert(FlowDto dto) {
		dto = super.insert(dto);
		BaseListDto baseListDto = new BaseListDto();
		baseListDto.setOrderNo(dto.getOrderNo());
		baseListDto = baseListService.find(baseListDto);
		String uid = baseListDto.getCurrentHandlerUid();
		String state = baseListDto.getState();
		if (StringUtils.isNotEmpty(state)) {
			state = state.replaceAll("待", "");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("message", "您有1条新订单【客户姓名：" + baseListDto.getCustomerName() + "】需要进行[" + state + "]，请查看！");
			
			//PC  通知
			toolsApi.pushH5(map);
			
			//APP 通知
			thirdApi.pushText(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//发送短信
			if (Constants.BASE_SMS_PROCESS_HANDLE.contains(baseListDto.getProcessId())) {
				//发送短信给处理人
				UserDto userDto = userApi.findUserDtoByUid(uid);
				//李佳泽不发终审短信,潘慧芸不发初审终审短信
				if(!("auditFinal".equals(dto.getNextProcessId())&&"18603020023".equals(userDto.getMobile()))&&
						!(("auditFirst".equals(dto.getNextProcessId())||"auditFinal".equals(dto.getNextProcessId()))
								&&"15919918599".equals(userDto.getMobile()))) {
					AmsUtil.smsSend(userDto.getMobile(), Constants.BASE_AMS_IPWHITE, SMSConstants.SMS_PROCESS_HANDLE,
							baseListDto.getProductName(), baseListDto.getCustomerName(), baseListDto.getBorrowingAmount(),
							state);
				}
			}
			
			if (Constants.BASE_SMS_PROCESS_PASS.contains(dto.getCurrentProcessId())) {
				//发送短信给受理员
				if(StringUtils.isNotEmpty(baseListDto.getAcceptMemberUid())) {
					UserDto temp = userApi.findUserDtoByUid(baseListDto.getAcceptMemberUid());
					AmsUtil.smsSend(temp.getMobile(), Constants.BASE_AMS_IPWHITE, SMSConstants.SMS_PROCESS_PASS,
							baseListDto.getProductName(), baseListDto.getCustomerName(), baseListDto.getBorrowingAmount(),
							state);
				}
				//发送短信给渠道经理
				if(StringUtils.isNotEmpty(baseListDto.getChannelManagerUid())) {
					UserDto temp = userApi.findUserDtoByUid(baseListDto.getChannelManagerUid());
					//李佳泽不发终审短信,潘慧芸不发初审终审短信
					if(!("auditFinal".equals(dto.getNextProcessId())&&"18603020023".equals(temp.getMobile()))&&
							!(("auditFirst".equals(dto.getNextProcessId())||"auditFinal".equals(dto.getNextProcessId()))
									&&"15919918599".equals(temp.getMobile()))) {
						AmsUtil.smsSend(temp.getMobile(), Constants.BASE_AMS_IPWHITE, SMSConstants.SMS_PROCESS_PASS,
								baseListDto.getProductName(), baseListDto.getCustomerName(), baseListDto.getBorrowingAmount(),
								state);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public FlowDto selectEndOrderFlow(FlowDto orderFlowDto) {
		return flowMapper.selectEndOrderFlow(orderFlowDto);
	}

	@Override
	public String selectOrderNoByUid(String uid) {
		List<String> orderNoList = flowMapper.selectOrderNoByUid(uid);
		String orderNos = "";
		for (String orderNo : orderNoList) {
			if (orderNo.contains("k")) {
				orderNos += "'" + orderNo + "',";
			} else {
				orderNos += orderNo + ",";
			}
		}
		if (StringUtils.isNotEmpty(orderNos)) {
			orderNos = orderNos.substring(0, orderNos.length() - 1);
		}
		return orderNos;
	}

}
