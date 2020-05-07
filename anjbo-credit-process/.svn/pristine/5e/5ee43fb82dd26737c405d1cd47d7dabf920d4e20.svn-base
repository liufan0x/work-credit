package com.anjbo.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.DistributionMemberService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;

/**
 * 指派还款专员
 * 
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/distributionMember/v")
public class DistributionMemberController extends BaseController {

	@Resource
	DistributionMemberService distributionMemberService;

	/**
	 * 详情
	 * 
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<DistributionMemberDto> detail(
			HttpServletRequest request,
			@RequestBody DistributionMemberDto distributionMemberDto) {
		RespDataObject<DistributionMemberDto> resp = new RespDataObject<DistributionMemberDto>();
		DistributionMemberDto dto = distributionMemberService
				.selectDistributionMember(distributionMemberDto);
		if (dto == null) {
			dto = new DistributionMemberDto();
		}
		String foreclosureMemberUid = dto.getForeclosureMemberUid(); // 还款专员uid
		UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(foreclosureMemberUid);// 根据uid获取名称
		dto.setForeclosureMemberName(userDto.getName());
		resp.setData(dto);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}

	/**
	 * 添加
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody RespStatus add(HttpServletRequest request, HttpServletResponse response, @RequestBody DistributionMemberDto distributionMemberDto) {
		RespStatus rd = new RespStatus();
		rd.setCode(RespStatusEnum.SUCCESS.getCode());
		rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		
		try {
			// 判断当前节点
			if (!isSubmit(distributionMemberDto.getOrderNo(), "repaymentMember")) {
				UserDto dto = getUserDto(request); // 获取用户信息
				distributionMemberDto.setCreateUid(dto.getUid());
				distributionMemberDto.setUpdateUid(dto.getUid());
				distributionMemberDto.setDistributionTime(new Date());
				DistributionMemberDto disDto = distributionMemberService
						.selectDistributionMember(distributionMemberDto);
				if (disDto == null) {
					distributionMemberService
							.addDistributionMember(distributionMemberDto);
				} else {
					distributionMemberService
							.updateDistributionMember(distributionMemberDto);
				}
				// 流程表
				OrderFlowDto orderFlowDto = new OrderFlowDto();
				orderFlowDto.setOrderNo(distributionMemberDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("repaymentMember");
				orderFlowDto.setCurrentProcessName("指派还款专员");
				orderFlowDto.setNextProcessId("element");
				orderFlowDto.setNextProcessName("要件校验");
				orderFlowDto.setHandleUid(dto.getUid()); // 当前处理人
				orderFlowDto.setHandleName(dto.getName());
				// 订单列表
				OrderListDto listDto = new OrderListDto();
				// 查询要件管理员信息
				OrderBaseBorrowDto borrowDto = new OrderBaseBorrowDto();
				borrowDto.setOrderNo(distributionMemberDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj = httpUtil
						.getRespDataObject(Constants.LINK_CREDIT,
								"/credit/order/borrowother/v/queryBorrow",
								borrowDto, OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto = obj.getData();
				listDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
				listDto.setCurrentHandler(baseBorrowDto.getElementName());
				// 更新流程方法
				goNextNode(orderFlowDto, listDto);
				// ==============发送短信Start===================
				UserDto acceptMemberUser = CommonDataUtil.getUserDtoByUidAndMobile(distributionMemberDto
						.getForeclosureMemberUid()); // 还款专员UId
				String type="债务置换";
				if("03".equals(baseBorrowDto.getProductCode())){
					type="畅贷";
				}else if("04".equals(baseBorrowDto.getProductCode())) {
					type="房抵贷";
				}
				if (StringUtil.isNotEmpty(acceptMemberUser.getMobile())) {
					String ipWhite = ConfigUtil.getStringValue(
							Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
					String mobile = acceptMemberUser.getMobile();
					String cont = baseBorrowDto.getBorrowerName() + " "
							+ baseBorrowDto.getLoanAmount();
					AmsUtil.smsSend(mobile, ipWhite, Constants.SMS_DEBT_SUBSTITUTION,type, cont,"待要件校验");
					AmsUtil.smsSend(mobile, ipWhite, Constants.SMS_MEMBER, cont);
				}
				// ==============发送短信end===================
			} else {
				rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
				rd.setMsg("该订单已被处理，请刷新列表查看");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	
	/**
	 * 机构完善资料：还款专员：不影响正常流程节点
	 * @Author KangLG<2017年11月8日>
	 * @param request
	 * @param response
	 * @param distributionMemberDto
	 * @return
	 */
	@RequestMapping(value = "/addAgency")
	public @ResponseBody RespData<DistributionMemberDto> addAgency(HttpServletRequest request, HttpServletResponse response, @RequestBody DistributionMemberDto distributionMemberDto) {
		RespData<DistributionMemberDto> rd = new RespData<DistributionMemberDto>();
		rd.setCode(RespStatusEnum.FAIL.getCode());
		rd.setMsg(RespStatusEnum.FAIL.getMsg());		
		try {
			distributionMemberDto.setCreateUid(getUserDto(request).getUid());
			distributionMemberDto.setUpdateUid(getUserDto(request).getUid());
			distributionMemberDto.setDistributionTime(new Date());
			DistributionMemberDto disDto = distributionMemberService.selectDistributionMember(distributionMemberDto);
			if (disDto == null) {
				distributionMemberService.addDistributionMember(distributionMemberDto);
			} else {
				distributionMemberService.updateDistributionMember(distributionMemberDto);
			}
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return rd;
	}

	public static void main(String[] args) {
		String smsIpWhite = ConfigUtil.getStringValue(
				Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
		AmsUtil.smsSend("15112347841", smsIpWhite, Constants.SMSCOMEFROM_TEST,
				"易思");
	}
}
