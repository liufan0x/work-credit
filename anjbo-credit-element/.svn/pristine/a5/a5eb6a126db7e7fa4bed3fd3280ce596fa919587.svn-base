package com.anjbo.controller;

import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.DocumentsReturnService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.StringUtil;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * 要件退还
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/credit/element/return/v")
public class DocumentsReturnController extends BaseController{

	private static final Log log = LogFactory.getLog(DocumentsReturnController.class);
	@Resource
	private DocumentsReturnService documentsReturnService;
	
	@ResponseBody
	@RequestMapping("/detail")
	public RespDataObject<DocumentsReturnDto> detail(HttpServletRequest request,@RequestBody DocumentsReturnDto dto){
		RespDataObject<DocumentsReturnDto> result = new RespDataObject<DocumentsReturnDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(dto.getOrderNo())){
				result.setMsg("查询要件退回信息异常,查询参数不能为空");
				return result;
			}
			dto = documentsReturnService.detail(dto.getOrderNo());
			if(null!=dto) {
				UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(dto.getHandleUid());
				dto.setHandleName(user.getName());
			}
			result.setData(dto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载要件退还数据异常,方法名为detail,异常信息为:", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public RespStatus insert(HttpServletRequest request,@RequestBody DocumentsReturnDto dto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(dto.getOrderNo())){
				result.setMsg("要件退还操作异常,订单编号不能为空");
				return result;
			}
			if(StringUtils.isBlank(dto.getReturnImgUrl())){
				result.setMsg("截屏证明必传");
				return result;
			}
			String orderNo = dto.getOrderNo();
			boolean isSubmit = isSubmit(dto.getOrderNo(),"elementReturn");
//			if(isSubmit){
//				result.setMsg("该订单已经被提交");
//				return result;
//			}
//			boolean isWithdraw = isWithdraw(dto.getOrderNo(),"elementReturn");
//			if(isWithdraw){
//				result.setMsg("该订单已经被撤回");
//				return result;
//			}
			//判断是否返佣，返佣需要返佣专员
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			LendingHarvestDto  lendingHarvestDto= new LendingHarvestDto();
			lendingHarvestDto.setOrderNo(orderNo);
			lendingHarvestDto.setType(borrow.getPaymentMethod());//收利息
			Map<String,Object> harvestMap = new HashMap<String, Object>();
			Map<String,Object> m = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", lendingHarvestDto);
			if(m.get("data")!=null){
				Map<String,Object> data = MapUtils.getMap(m, "data");
				if(data.get("harvest")!=null){
					harvestMap = MapUtils.getMap(data, "harvest");
				}
			}
			ReceivablePayDto oo = new ReceivablePayDto();
			oo.setOrderNo(orderNo);
			oo = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/receivablePay/v/detail", oo, ReceivablePayDto.class);
			//有返佣
			if((null!=harvestMap.get("returnMoney")&&MapUtils.getInteger(harvestMap, "returnMoney")>0)
					||(oo!=null&&oo.getRebateMoney()!=null&&oo.getRebateMoney().doubleValue()>0)){
				if(StringUtil.isBlank(dto.getHandleUid())) {
					result.setMsg("请选择返佣专员");
					return result;
				}
			}
			boolean isRebate=false; //是否返佣
			String handlerUid=dto.getHandleUid();
			if(StringUtil.isNotBlank(handlerUid)){
				isRebate=true;
			}
			UserDto user = getUserDto(request);
			dto.setCreateUid(user.getUid());
			dto.setUpdateUid(user.getUid());
			if(isRebate){
				dto.setHandleUid(dto.getHandleUid());
			}else{
				dto.setHandleUid(user.getUid());
			}
			dto.setHandleName(user.getName());
			documentsReturnService.update(dto);
			
			OrderFlowDto next = new OrderFlowDto();
			OrderListDto order = new OrderListDto();
			order.setOrderNo(dto.getOrderNo());
			next.setOrderNo(dto.getOrderNo());
			next.setCurrentProcessId("elementReturn");
			next.setCurrentProcessName("要件退还");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			if(isRebate){
				next.setNextProcessId("rebate");
				next.setNextProcessName("返佣");
				order.setCurrentHandlerUid(handlerUid);
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(handlerUid);
				order.setCurrentHandler(userDto.getName());
			}else{
				next.setNextProcessId("wanjie");
				next.setNextProcessName("已完结");
				order.setCurrentHandlerUid("-");
				order.setCurrentHandler("-");
				order.setProcessId("wanjie");
				order.setState("已完结");
			}
			if(!isSubmit){
				result = goNextNode(next,order);
			}else{
				order.setOrderNo(dto.getOrderNo());
				result = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", order);
			}
		} catch (Exception e){
			log.error("保存要件退还信息异常,异常信息为:",e);
		}
		return result;
	}
	
}
