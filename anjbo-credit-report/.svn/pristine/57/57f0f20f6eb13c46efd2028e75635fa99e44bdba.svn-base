package com.anjbo.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderBaseService;
import com.anjbo.utils.StringUtil;
/**
 * 资方看单（临时）
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/report/fund/v")
public class FundTempController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	
	@Resource private OrderBaseService orderBaseService;
	
	/**
	 * 资方流水
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectOrderFlowListRepeat")
	public  RespDataObject<Map<String,Object>> selectOrderFlowListRepeat(HttpServletRequest request,HttpServletResponse response, @RequestBody Map<String,Object> paramMap){
		RespDataObject<Map<String,Object>> respData = new RespDataObject<Map<String,Object>>();
		try {
			RespData<OrderFlowDto> resp = new RespData<OrderFlowDto>();
			String orderNo = MapUtils.getString(paramMap, "orderNo");
			resp = httpUtil.getRespData(Constants.LINK_SL, "/credit/order/flow/v/selectOrderFlowList", paramMap,OrderFlowDto.class);
			List<OrderFlowDto> orderFlowDtoList = new ArrayList<OrderFlowDto>();
//			OrderFlowDto orderFlowDto1 = new OrderFlowDto();
//			orderFlowDto1.setOrderNo(MapUtils.getString(paramMap, "orderNo"));
//			orderFlowDto1.setCurrentProcessId("notarization");
//			orderFlowDto1.setCurrentProcessName("公证");
//			orderFlowDto1.setHandleName(resp.getData().get(0).getHandleName());
//			orderFlowDto1.setHandleTime(resp.getData().get(0).getHandleTime());
//			orderFlowDtoList.add(orderFlowDto1);
			UserDto user = getUserDto(request);
			CustomerFundDto fund = orderBaseService.selectFundByUid(user.getUid());
			boolean riskShow = false;
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(orderNo);
			orderBaseBorrowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			if(fund!=null){
				log.info("资方id:"+fund.getId());
				fund.setCreateTime(null);
				fund.setUpdateTime(null);
				CustomerFundDto customerFundDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/fund/new/v/selectCustomerFundById", fund,CustomerFundDto.class);
				if(customerFundDto!=null){
					String typeId = customerFundDto.getAuths();  //资方影响分类id
					log.info("权限id："+typeId);
					if(typeId==null){
						riskShow = false;
					}else{
						if("01".equals(orderBaseBorrowDto.getProductCode())&&typeId.indexOf("1500")!=-1){
							riskShow = true;
						}else if("03".equals(orderBaseBorrowDto.getProductCode())&&typeId.indexOf("1501")!=-1){
							riskShow = true;
						}else if("02".equals(orderBaseBorrowDto.getProductCode())&&typeId.indexOf("1502")!=-1){
							riskShow = true;
						}else if("05".equals(orderBaseBorrowDto.getProductCode())&&typeId.indexOf("1503")!=-1){
							riskShow = true;
						}else if(fund.getId()==31){//华融
							riskShow = true;
						}
					}
				}else{
					if(fund.getId()==31){
						riskShow = true;
					}
				}
				log.info("资方id："+fund.getId()+"风控权限"+riskShow);
				//如果没有推送审批意见，设置为false
				if(fund.getId()!=31){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("orderNo", orderNo);
					map.put("fundId", fund.getId());
					Map<String,Object> first = orderBaseService.selectSendRiskInfo(map);
					if(first!=null&&first.get("finalRemark")!=null){
						riskShow = true;
					}else{
						riskShow = false;
					}
				}
			}
			for (OrderFlowDto orderFlowDto : resp.getData()) {
				if("isLendingHarvest".equals(orderFlowDto.getCurrentProcessId())
						||"lendingHarvest".equals(orderFlowDto.getCurrentProcessId())
						|| "lendingPay".equals(orderFlowDto.getCurrentProcessId())
						|| "pay".equals(orderFlowDto.getCurrentProcessId())
						|| "fundAduit".equals(orderFlowDto.getCurrentProcessId())
						|| "lendingInstructions".equals(orderFlowDto.getCurrentProcessId())){
					continue;
				}else if(fund!=null&&fund.getId()!=31
						&&("managerAudit".equals(orderFlowDto.getCurrentProcessId())
							||"fundDocking".equals(orderFlowDto.getCurrentProcessId())
							||"auditJustice".equals(orderFlowDto.getCurrentProcessId())
							||"isLendingHarvest".equals(orderFlowDto.getCurrentProcessId())
							||"lendingHarvest".equals(orderFlowDto.getCurrentProcessId())
							||"lendingPay".equals(orderFlowDto.getCurrentProcessId())
							||"isBackExpenses".equals(orderFlowDto.getCurrentProcessId())
//							||"receivableFor".equals(orderFlowDto.getCurrentProcessId())
//							||"receivableFor".equals(orderFlowDto.getCurrentProcessId())
							||"receivableForFirst".equals(orderFlowDto.getCurrentProcessId())
							||"receivableForEnd".equals(orderFlowDto.getCurrentProcessId())
							||"mortgage".equals(orderFlowDto.getCurrentProcessId())
							||"pay".equals(orderFlowDto.getCurrentProcessId())
							||"rebate".equals(orderFlowDto.getCurrentProcessId())
							||"lendingInstructions".equals(orderFlowDto.getCurrentProcessId())
							)){
					//富德财险放开回款节点
					if(!(fund!=null&&fund.getId()==38&&("receivableForFirst".equals(orderFlowDto.getCurrentProcessId())
							||"receivableForEnd".equals(orderFlowDto.getCurrentProcessId())))){
						continue;
					}
				}else if(fund!=null&&fund.getId()!=37&&fund.getId()!=38 && fund!=null&&fund.getId()!=31 && "receivableFor".equals(orderFlowDto.getCurrentProcessId())) {
					continue;
				}
				
				if(StringUtils.isNotEmpty(orderFlowDto.getBackReason())){
					continue;
				}
				if("receivableForEnd".equals(orderFlowDto.getCurrentProcessId())){
					orderFlowDto.setCurrentProcessId("receivableFor");
					orderFlowDto.setCurrentProcessName("回款");
				}
				orderFlowDtoList.add(orderFlowDto);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			for (int i = 0; i <orderFlowDtoList.size(); i++) {
				OrderFlowDto orderFlowDto=orderFlowDtoList.get(i);
				if (orderFlowDto.getCurrentProcessName().equals("复核审批")) {
					orderFlowDtoList.remove(orderFlowDto);
				}
			}
			map.put("flowList", orderFlowDtoList);
			map.put("riskShow", riskShow);
			RespHelper.setSuccessDataObject(respData, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常", e);
		}
		return respData;
	}
	
	@ResponseBody
	@RequestMapping("lending")
	public RespDataObject<LendingDto> lending(HttpServletRequest request,HttpServletResponse response, @RequestBody Map<String,Object> paramMap){
		RespDataObject<LendingDto> resp = new RespDataObject<LendingDto>();
		try {
			resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/detail", paramMap,LendingDto.class);
			Map<String, Object> tempresp = (Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/allocationfundaduit/v/init", paramMap);
			Map<String, Object> tempMap = MapUtils.getMap(tempresp, "data");
			tempMap = MapUtils.getMap(tempMap, "fundCompleteDto");
			LendingDto lendingDto = resp.getData();
			lendingDto.setLendingTime(new Date(MapUtils.getLongValue(tempMap, "lendingTime")));
			resp.setData(lendingDto);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常", e);
			return new RespDataObject<LendingDto>();
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("foreclosure")
	public  RespDataObject<ForeclosureDto> foreclosure(HttpServletRequest request,HttpServletResponse response, @RequestBody Map<String,Object> paramMap){
		RespDataObject<ForeclosureDto> resp = new RespDataObject<ForeclosureDto>();
		try {
			resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", paramMap,ForeclosureDto.class);
			Map<String, Object> tempresp = (Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/allocationfundaduit/v/init", paramMap);
			Map<String, Object> tempMap = MapUtils.getMap(tempresp, "data");
			tempMap = MapUtils.getMap(tempMap, "fundCompleteDto");
			ForeclosureDto foreclosureDto = resp.getData();
			foreclosureDto.setForeclosureTime(new Date(MapUtils.getLongValue(tempMap, "lendingTime")));
			resp.setData(foreclosureDto);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常", e);
			return new RespDataObject<ForeclosureDto>();
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("queryFund")
	public  RespDataObject<CustomerFundDto> queryFund(HttpServletRequest request,HttpServletResponse response, @RequestBody CustomerFundDto customerFundDto){
		RespDataObject<CustomerFundDto> resp = new RespDataObject<CustomerFundDto>();
		try {
			UserDto user = getUserDto(request);
			CustomerFundDto fund = orderBaseService.selectFundByUid(user.getUid());
			RespHelper.setSuccessDataObject(resp, fund);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常", e);
			return new RespDataObject<CustomerFundDto>();
		}
		return resp;
	}
	
	/*
	 * 要件管理
	 * */
	@ResponseBody
	@RequestMapping(value = "/eleAdmin")
	public RespPageData<Map<String,Object>> eleAdmin(HttpServletRequest request, @RequestBody Map<String,Object> param){
		 RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		 try {
			 UserDto user = getUserDto(request);
				CustomerFundDto fund = orderBaseService.selectFundByUid(user.getUid());
				if(fund!=null){
					param.put("fundId", fund.getId());
					log.info("资方id:"+fund.getId());
				}
				
				int count = orderBaseService.selectElementCount(param);     //要件管理的总条数
				 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	 		   if (count>0){
	 			   list =  orderBaseService.selectElementList(param);  //要件管理信息
	 			   List<Map<String, Object>> allFileList = orderBaseService.selectAllElementList(param);
	 			 for (Map<String, Object> map : list){
	                 StringBuffer sbRisk= new StringBuffer();
	                 StringBuffer sbPay= new StringBuffer();
	                 String stillFile = MapUtils.getString(map,"currentBoxElementSet");
	                 if (StringUtil.isNotEmpty(stillFile)){
	                     for(String file: stillFile.split(",")){
	                         for (Map<String, Object> allMap: allFileList){
	                             if (StringUtil.isNotEmpty(file)&&file.equals(MapUtils.getString(allMap,"id"))){
	                                 if ("1".equals(MapUtils.getString(allMap,"elementType"))){
	                                     if (sbPay.length()>0){
	                                         sbPay.append("、");
	                                     }
	                                     sbPay.append(MapUtils.getString(allMap,"cardType"));
	                                 }else{
	                                     if (sbRisk.length()>0){
	                                         sbRisk.append("、");
	                                     }
	                                     sbRisk.append(MapUtils.getString(allMap,"cardType"));
	                                 }
	                             }
	                         }
	                     }
	                 }
	                 if (sbRisk.length()>0)
	                     map.put("sbRisk",sbRisk.toString());
	                 if (sbPay.length()>0)
	                     map.put("sbPay",sbPay.toString());
	             }
	 		   }
	 		  result.setTotal(count);
	          result.setRows(list);
	          RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			  e.printStackTrace();
	          RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
		}
		    
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespPageData<OrderListDto> list(HttpServletRequest request, @RequestBody Map<String,Object> map) {
		RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
		try {
			UserDto user = getUserDto(request);
			CustomerFundDto fund = orderBaseService.selectFundByUid(user.getUid());
			if(fund!=null){
				map.put("fundId", fund.getId());
				log.info("资方id:"+fund.getId());
			}
//			if(!uid.equals("1512633198682")){
//				return null;
//			}
			
			List<OrderListDto> orderListDtos = orderBaseService.fundOrderList(map);
			boolean fl = false;
			boolean fl1 = false;
			for (OrderListDto orderListDto : orderListDtos) {
				if(orderListDto.getState().contains("退回")){
					String state = orderListDto.getState();
					int i = orderListDto.getState().indexOf("退回");
					state = "待" + state.substring(i+2,orderListDto.getState().length()-1).replace("【", "").replace("】", "");
					orderListDto.setState(state);
				}
				
				if(orderListDto.getState().contains("抵押")){
					fl = true;
				}
				
				if(orderListDto.getState().contains("资金审批")){
					orderListDto.setState("待指派还款专员");
				}else if(orderListDto.getState().contains("收利息")){
					orderListDto.setState("待放款");
				}else if(orderListDto.getState().contains("付利息")){
					orderListDto.setState("待放款");
				}else if(orderListDto.getState().contains("回款（尾期）")){
					orderListDto.setState("待要件退还");
				}else if(orderListDto.getState().contains("发放款指令")){
					orderListDto.setState("待放款");
				}else if(orderListDto.getState().contains("付费")){
					orderListDto.setState("待要件退还");
				}else if(orderListDto.getState().contains("回款")){
					if(fl){
						orderListDto.setState("待抵押");
					}else{
						orderListDto.setState("待要件退还");
					}
					
				}
				
				if(fund!=null&&fund.getId()!=31){
					if(orderListDto.getState().contains("分配订单")){
						orderListDto.setState("待分配资金");
					}else if(orderListDto.getState().contains("资料推送")){
						orderListDto.setState("待指派还款专员");
					}else if(orderListDto.getState().contains("核实后置费用")||orderListDto.getState().contains("收利息")||orderListDto.getState().contains("核实利息")){
						orderListDto.setState("待放款");
					}else if(orderListDto.getState().contains("扣回后置费用")){
						orderListDto.setState("待结清原贷款");
					}else if(orderListDto.getState().contains("回款")||orderListDto.getState().contains("抵押")||orderListDto.getState().contains("收罚息")){
						orderListDto.setState("待要件退还");
					}else if(orderListDto.getState().contains("返佣")){
						orderListDto.setState("已完结");
					}
				}
				
			}
			resp.setTotal(orderBaseService.fundOrderCount(map));
			resp.setRows(orderListDtos);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();	
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 资方查询借款信息
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/borrow")
	public RespDataObject<OrderBaseBorrowDto> borrow(HttpServletRequest request,HttpServletResponse response, @RequestBody Map<String,Object> paramMap){
		RespDataObject<OrderBaseBorrowDto> resp = new RespDataObject<OrderBaseBorrowDto>();
		try {
			String orderNo = MapUtils.getString(paramMap, "orderNo");
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("orderNo", orderNo);
			UserDto user = getUserDto(request);
			CustomerFundDto fund = orderBaseService.selectFundByUid(user.getUid());
			if(fund!=null){
				params.put("fundId", fund.getId());
				log.info("资方id:"+fund.getId());
			}
			OrderBaseBorrowDto borrow = orderBaseService.queryBorrow(params);
			RespHelper.setSuccessDataObject(resp, borrow);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常", e);
			return new RespDataObject<OrderBaseBorrowDto>();
		}
		return resp;
	}
	
}
