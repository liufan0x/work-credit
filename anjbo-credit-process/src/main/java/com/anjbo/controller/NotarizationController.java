package com.anjbo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FacesignService;
import com.anjbo.service.NotarizationService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;

/**
 * 公证
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/notarization/v")
public class NotarizationController extends BaseController{

	@Resource NotarizationService notarizationService;
	
	@Resource FacesignService facesignService;
	
	@ResponseBody
	@RequestMapping(value = "/init") 
	public RespDataObject<NotarizationDto> init(HttpServletRequest request,@RequestBody  NotarizationDto notarizationDto){
		 RespDataObject<NotarizationDto> resp=new RespDataObject<NotarizationDto>();
//		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
//		 borrowDto.setOrderNo(notarizationDto.getOrderNo());
//		 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
//		 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
		 OrderListDto orderListDto=new OrderListDto();
		 orderListDto.setOrderNo(notarizationDto.getOrderNo());
		 RespDataObject<OrderListDto> listObj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto,OrderListDto.class);
		 orderListDto = listObj.getData();
		 
		 NotarizationDto dto= notarizationService.selectNotarization(notarizationDto);
		 if(dto==null || dto.getId()==null){
			 dto=new NotarizationDto();
			 if(null != orderListDto.getRelationOrderNo() && !"0".equals(orderListDto.getRelationOrderNo()) && !"".equals(orderListDto.getRelationOrderNo())){
				 dto.setOrderNo(orderListDto.getRelationOrderNo());
				 dto = notarizationService.selectNotarization(dto);
				 if(dto!=null)
				    dto.setOrderNo(notarizationDto.getOrderNo());
			 }
		 }else{
			 List<DictDto> list=getDictDtoByType("notaryoffice");
			 for(DictDto d:list){
				 if(dto.getNotarizationAddressCode()!=null && dto.getNotarizationAddressCode().equals(d.getCode())){
					 dto.setNotarizationAddress(d.getName());
				 }
			 }
		 }
		 if(dto==null || dto.getOrderNo()==null){
			 dto=new NotarizationDto();
			 dto.setOrderNo(notarizationDto.getOrderNo());
		 }
		 if(orderListDto!=null){
			 String code=orderListDto.getCityCode();
			 dto.setCode(code);
		 }
		 SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd");
		 Date notariTime=dto.getNotarizationTime();
		 Date estimaTime=dto.getEstimatedTime();
		 if(notariTime!=null)
		 dto.setNotarizationTimeStr(format.format(notariTime));
		 if(estimaTime!=null)
		 dto.setEstimatedTimeStr(format.format(estimaTime));
		 resp.setData(dto);
		 resp.setCode(RespStatusEnum.SUCCESS.getCode());
		 resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		 return resp;
	}
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<NotarizationDto> detail(HttpServletRequest request,@RequestBody  NotarizationDto notarizationDto){
		 RespDataObject<NotarizationDto> resp=new RespDataObject<NotarizationDto>();
//		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
//		 borrowDto.setOrderNo(notarizationDto.getOrderNo());
//		 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
//		 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
//		 if(baseBorrowDto!=null && baseBorrowDto.getIsChangLoan()==1){ //畅贷
//			String orderNo=getCreditOrderNo(notarizationDto.getOrderNo());
//			notarizationDto.setOrderNo(orderNo);
//		 }
		 NotarizationDto dto=notarizationService.selectNotarization(notarizationDto);
		 if(dto==null){
			 dto=new NotarizationDto();
			 dto.setOrderNo(notarizationDto.getOrderNo());
		 }
		 List<DictDto> list=getDictDtoByType("notaryoffice");
		 for(DictDto d:list){
			 if(dto!=null && dto.getNotarizationAddressCode()!=null && dto.getNotarizationAddressCode().equals(d.getCode())){
				 dto.setNotarizationAddress(d.getName());
			 }
		 }
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
		public @ResponseBody
		RespStatus add(HttpServletRequest request,
				HttpServletResponse response,@RequestBody NotarizationDto notarizationDto) {
			RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				 //判断当前节点
		    	if(StringUtils.isBlank(notarizationDto.getOrderNo())){
					rd.setMsg("保存缺少订单编号!");
					return rd;
				}
		    	OrderListDto listDto=new OrderListDto();
				listDto.setOrderNo(notarizationDto.getOrderNo());
				RespDataObject<OrderListDto> listobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto,OrderListDto.class);
				listDto=listobj.getData();
				boolean isSubmit = isSubmit(notarizationDto.getOrderNo(),"notarization");
				if(isSubmit){
					rd.setMsg("该订单已经被提交");
					return rd;
				}
				boolean isWithdraw = isWithdraw(notarizationDto.getOrderNo(),"notarization");
				if(listDto.getAuditSort()==1 && isWithdraw){
					rd.setMsg("该订单已经被撤回");
					return rd;
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				
				//公证加影像资料上传校验
				if("04".equals(listDto.getProductCode())&&!"不公证".equals(notarizationDto.getNotarizationType())){
					OrderBaseBorrowDto order = new OrderBaseBorrowDto();
					order.setOrderNo(notarizationDto.getOrderNo());
					RespStatus resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/businfo/v/notarizationBusinfoCheck",order);
					if(resp.getCode().equals(RespStatusEnum.FAIL.getCode())){
						return resp;
					}
				}
				
				notarizationDto.setCreateUid(dto.getUid());
				notarizationDto.setUpdateUid(dto.getUid());
				NotarizationDto ndto=notarizationService.selectNotarization(notarizationDto);
				if(ndto!=null){
					notarizationService.updateNotarizetion(notarizationDto);
				}else{
				    notarizationService.addNotarizetion(notarizationDto);
				}
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(notarizationDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("notarization");
					orderFlowDto.setCurrentProcessName("公证");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());

					OrderListDto orderListDto = new OrderListDto();
					OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
					orderBaseBorrowDto.setOrderNo(notarizationDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto=  obj.getData();
					if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷
						 if(null != baseBorrowDto && 3000 <= baseBorrowDto.getLoanAmount()  && listDto.getAuditSort()==1){  //借款金额大于1000万 走法务
								orderFlowDto.setNextProcessId("auditJustice");
								orderFlowDto.setNextProcessName("法务审批");
								OfficerAuditDto officerAuditDto=new OfficerAuditDto();
								officerAuditDto.setOrderNo(notarizationDto.getOrderNo());
								officerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail" ,officerAuditDto, OfficerAuditDto.class);
								if(officerAuditDto!=null && null!=officerAuditDto.getJusticeUid()){
									UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(officerAuditDto.getJusticeUid());
									orderListDto.setCurrentHandlerUid(nextUser.getUid()); 
									orderListDto.setCurrentHandler(nextUser.getName());
								}else{ //查询终审法务
									FinalAuditDto finalAuditDto=new FinalAuditDto();
									finalAuditDto.setOrderNo(notarizationDto.getOrderNo());
									finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail" ,finalAuditDto, FinalAuditDto.class);
									if(finalAuditDto!=null && (null !=finalAuditDto.getOfficerUid() && finalAuditDto.getOfficerUidType()==2)){
										UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(finalAuditDto.getOfficerUid());
										orderListDto.setCurrentHandlerUid(nextUser.getUid()); 
										orderListDto.setCurrentHandler(nextUser.getName());
									}else{
										rd.setCode(RespStatusEnum.FAIL.getCode());
										rd.setMsg("暂无法务处理人！");
										return rd;
									}
								}
						 }else if(listDto.getAuditSort()==1){
								orderFlowDto.setNextProcessId("fundDocking");
								orderFlowDto.setNextProcessName("资料推送");
								AllocationFundDto allocationFundDto =new AllocationFundDto();
								allocationFundDto.setOrderNo(notarizationDto.getOrderNo());
								List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail",allocationFundDto, AllocationFundDto.class);
								String nextUid = list==null?null:list.get(0).getCreateUid();
								UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(nextUid);
								orderListDto.setCurrentHandlerUid(nextUser.getUid()); //下一处理人分配资金
								orderListDto.setCurrentHandler(nextUser.getName());
//								OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
//								if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//									//发送短信
//									String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//									String ProductName="债务置换";
//									if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
//										ProductName=baseBorrowDto.getProductName();
//									}
//									//发送给操作人
//									AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资料推送");
//								}
						 }else{//分配订单
//								orderFlowDto.setNextProcessId("managerAudit");
//								orderFlowDto.setNextProcessName("分配订单");
//								UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(listDto.getDistributionOrderUid());
//								orderListDto.setCurrentHandlerUid(nextUser.getUid()); //下一处理人分配资金
//								orderListDto.setCurrentHandler(nextUser.getName());
								orderFlowDto.setNextProcessId("placeOrder");
								orderFlowDto.setNextProcessName("提单");
								String currentHandlerUid = StringUtil.isEmpty(baseBorrowDto.getAcceptMemberUid())?baseBorrowDto.getCreateUid():baseBorrowDto.getAcceptMemberUid();
								UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(currentHandlerUid);
								orderListDto.setCurrentHandlerUid(nextUser.getUid()); //下一处理人完善订单
								orderListDto.setCurrentHandler(nextUser.getName());
						 }
					}else{
						//面签处理人
						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getFacesignUid());
						orderFlowDto.setNextProcessId("facesign");
						orderFlowDto.setNextProcessName("面签");
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}
					orderListDto.setOrderNo(notarizationDto.getOrderNo());
					//填充列表预计出款
					goNextNode(orderFlowDto, orderListDto);
//				}
				orderListDto.setAppShowValue1(DateUtil.getDateByFmt(notarizationDto.getEstimatedTime(), DateUtil.FMT_TYPE2));
				orderListDto.setOrderNo(notarizationDto.getOrderNo());
				httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", orderListDto);
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
	
	  
	
}
