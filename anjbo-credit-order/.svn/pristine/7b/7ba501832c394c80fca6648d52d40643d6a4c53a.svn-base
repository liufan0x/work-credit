package com.anjbo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseBorrowRelationService;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.service.OrderBaseHouseService;
import com.anjbo.service.OrderBaseService;
import com.anjbo.utils.CommonDataUtil;

@Controller
@RequestMapping("/credit/order/house/v")
public class OrderBaseHouseController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseHouseController.class);

	@Resource
	private OrderBaseHouseService orderBaseHouseService;
	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderBaseService orderBaseService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseHouseService.saveOrderHouse(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存房产交易信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存房产交易信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto user = getUserDto(request);
			orderBaseHouseDto.setCreateUid(user.getUid());
			orderBaseHouseService.updateOrderHouse(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新房产交易信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新房产交易信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<OrderBaseHouseDto> query(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespDataObject<OrderBaseHouseDto> resp = new RespDataObject<OrderBaseHouseDto>();
		try {
			String orderNo = orderBaseHouseDto.getOrderNo();
//			orderNo = getLoanOrderNo(orderNo);
//			orderBaseHouseDto.setOrderNo(orderNo);
			OrderListDto orderListDto = orderBaseService.selectDetail(orderNo);
			OrderBaseHouseDto orderBaseHouse = new OrderBaseHouseDto();
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			if("03".equals(orderListDto.getProductCode())&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo())){
				orderBaseHouse = orderBaseHouseService
						.selectOrderHouseByOrderNo(orderListDto.getRelationOrderNo());
				//查询借款信息中的附加信息
				 orderBaseBorrowDto = orderBaseBorrowService
						.selectOrderBorrowByOrderNo(orderListDto.getRelationOrderNo());
			}else{
				orderBaseHouse = orderBaseHouseService
						.selectOrderHouseByOrderNo(orderNo);
				//查询借款信息中的附加信息
				 orderBaseBorrowDto = orderBaseBorrowService
						.selectOrderBorrowByOrderNo(orderNo);
			}
			orderBaseHouse = getOrderBaseHouseDto(orderBaseHouse,orderBaseBorrowDto);
			RespHelper.setSuccessDataObject(resp, orderBaseHouse);
		} catch (Exception e) {
			log.error("查询房产交易信息失败");
			RespHelper.setFailDataObject(resp, null, "查询房产交易信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 附加借款信息
	 * @param orderBaseHouseDto
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public OrderBaseHouseDto getOrderBaseHouseDto(OrderBaseHouseDto orderBaseHouseDto,OrderBaseBorrowDto orderBaseBorrowDto){
		if(orderBaseHouseDto!=null){
			orderBaseHouseDto.setIsOldLoanBank(orderBaseBorrowDto.getIsOldLoanBank());
			orderBaseHouseDto.setIsLoanBank(orderBaseBorrowDto.getIsLoanBank());
		}
		//获取银行支行名称
//		List<BankDto> bankList = getAllBankList();
//		List<SubBankDto> subBankList = getAllSubBankList();
		if((orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1)||(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1)){
			//查询银行名称
				if(orderBaseBorrowDto.getOldLoanBankNameId()!=null){
					orderBaseHouseDto.setOldLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanBankNameId()!=null){
					orderBaseHouseDto.setLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getOldLoanBankSubNameId()!=null){
					orderBaseHouseDto.setOldLoanBankSubName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getOldLoanBankSubNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanSubBankNameId()!=null){
					orderBaseHouseDto.setLoanSubBankName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getLoanSubBankNameId()).getName());
				}
			orderBaseHouseDto.setOldLoanBankManager(orderBaseBorrowDto.getOldLoanBankManager());
			orderBaseHouseDto.setOldLoanBankManagerPhone(orderBaseBorrowDto.getOldLoanBankManagerPhone());
			orderBaseHouseDto.setLoanBankNameManager(orderBaseBorrowDto.getLoanBankNameManager());
			orderBaseHouseDto.setLoanBankNameManagerPhone(orderBaseBorrowDto.getLoanBankNameManagerPhone());
		}
		if(orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==2){
			orderBaseHouseDto.setOldLoanBankName(orderBaseBorrowDto.getOldLoanBankName());
		}
		if(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==2){
			orderBaseHouseDto.setLoanBankName(orderBaseBorrowDto.getLoanBankName());
		}
		//获取所有城市
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		if(orderBaseHouseDto!=null&&orderBaseHouseDto.getOrderBaseHousePropertyDto()!=null){
			List<OrderBaseHousePropertyDto> housePropertyList = orderBaseHouseDto.getOrderBaseHousePropertyDto();
			List<OrderBaseHousePropertyDto> housePropertyListNew = new ArrayList<OrderBaseHousePropertyDto>();
			for (OrderBaseHousePropertyDto orderBaseHousePropertyDto : housePropertyList) {
				for (DictDto dictDto : dictList) {
					if(dictDto.getCode().equals(orderBaseHousePropertyDto.getCity())){
						orderBaseHouseDto.setCityName(dictDto.getName());
						orderBaseHousePropertyDto.setCityName(dictDto.getName());
					}
					if(dictDto.getCode().equals(orderBaseHousePropertyDto.getHouseRegion())&&dictDto.getPcode().equals(orderBaseHousePropertyDto.getCity())){
						orderBaseHousePropertyDto.setHouseRegionName(dictDto.getName());
					}
				}
				housePropertyListNew.add(orderBaseHousePropertyDto);
			}
			orderBaseHouseDto.setOrderBaseHousePropertyDto(housePropertyListNew);
		}
		
		return orderBaseHouseDto;
	}
	
	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	public String getLoanOrderNo(String orderNo){
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelationDto!=null && orderBaseBorrowRelationDto.getRelationOrderNo()!=null){
			return orderBaseBorrowRelationDto.getRelationOrderNo();
		}
		return orderNo;
	}
}
