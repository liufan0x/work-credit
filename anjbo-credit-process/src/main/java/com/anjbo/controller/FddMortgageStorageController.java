package com.anjbo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.AfterLoanListDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FddMortgageStorageService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;

/**
 * 抵押品入库
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/process/fddMortgageStorage/v")
public class FddMortgageStorageController extends BaseController{

	
	
	@Resource FddMortgageStorageService fddMortgageStorageService;	

	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<FddMortgageStorageDto> detail(HttpServletRequest request,@RequestBody  FddMortgageStorageDto storageDto){
		 RespDataObject<FddMortgageStorageDto> resp=new RespDataObject<FddMortgageStorageDto>();
		 FddMortgageStorageDto mdto=fddMortgageStorageService.findByStorage(storageDto.getOrderNo());
		 if(mdto==null) {
			 mdto = new FddMortgageStorageDto();
		 }
		 if(mdto.getHousePropertyType()==null){//默认所在地区，房产名称，产权证类型，产权证号
			 mdto = new FddMortgageStorageDto();
			 mdto.setOrderNo(storageDto.getOrderNo());
			 OrderBaseHouseDto orderBaseHouseDto=new OrderBaseHouseDto();
			 orderBaseHouseDto.setOrderNo(storageDto.getOrderNo());
			 RespDataObject<OrderBaseHouseDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", orderBaseHouseDto,OrderBaseHouseDto.class);
			 OrderBaseHouseDto houseDto=  obj.getData();
			 if(null!=houseDto){
				 List<OrderBaseHousePropertyDto> hlist=houseDto.getOrderBaseHousePropertyDto();
				 if(hlist.size()>0){
					Object bean = hlist.get(0);
					String json = JsonUtil.BeanToJson(bean);
					Map<String, Object> m;
					try {
						m = JsonUtil.jsonToMap(json);
						mdto.setHousePropertyType(MapUtils.getString(m, "housePropertyType"));//房产证类型
						mdto.setHousePropertyNumber(MapUtils.getString(m, "housePropertyNumber"));//房产证号
						mdto.setHouseName(MapUtils.getString(m, "houseName"));//房产名称
						mdto.setRegion(MapUtils.getString(m, "houseRegion"));//城市
					} catch (JSONException e) {
						e.printStackTrace();
					}
				 }
			 }
		 }
		 if(null!=mdto.getCollateralUid()){
			 UserDto fddDto=CommonDataUtil.getUserDtoByUidAndMobile(mdto.getCollateralUid());
			 if(fddDto!=null){
				 mdto.setCollateralName(fddDto.getName());
			 }
		 }
		 resp.setData(mdto);
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
				HttpServletResponse response,@RequestBody FddMortgageStorageDto storageDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
				if(StringUtils.isBlank(storageDto.getOrderNo())){
					rd.setMsg("保存审核信息失败,订单编号不能为空!");
					return rd;
				}
				boolean isSubmit = isSubmit(storageDto.getOrderNo(),"fddMortgageStorage");
//				if(isSubmit){
//					rd.setMsg("该订单已经被提交");
//					return rd;
//				}
				boolean isWithdraw = isWithdraw(storageDto.getOrderNo(),"fddMortgageStorage");
				if(isWithdraw &&!isSubmit){
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("该订单已经被撤回");
					return rd;
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				storageDto.setCreateUid(dto.getUid());
				storageDto.setUpdateUid(dto.getUid());
				fddMortgageStorageService.addStorage(storageDto);
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				//订单列表
				OrderListDto listDto = new OrderListDto();
				orderFlowDto.setOrderNo(storageDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("fddMortgageStorage");
				orderFlowDto.setCurrentProcessName("抵押品入库");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//是否最后一期 结清
				AfterLoanListDto AfterLoanListDto = new AfterLoanListDto();
				AfterLoanListDto.setOrderNo(storageDto.getOrderNo());
				RespDataObject<AfterLoanListDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/afterLoanList/v/loanDetail", AfterLoanListDto,AfterLoanListDto.class);
				if(auditDto!=null && auditDto.getData().getRepaymentStatus()==7){ //已结清
					orderFlowDto.setNextProcessId("fddMortgageRelease");
					orderFlowDto.setNextProcessName("抵押品出库");
				}else{
					orderFlowDto.setNextProcessId("fddRepayment");
					orderFlowDto.setNextProcessName("还款");
				}
				listDto.setCurrentHandlerUid(dto.getUid());  //当前处理人
				listDto.setCurrentHandler(dto.getName());
//				FinalAuditDto finalAuditDto=new FinalAuditDto();
//				finalAuditDto.setOrderNo(storageDto.getOrderNo());
//				RespDataObject<FinalAuditDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
//				String paymentType=Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName(); //凭他项权利证放款
//				String paymentTypeName="";
//				if(null!=auditDto.getData()){
//					paymentTypeName=auditDto.getData().getPaymentType();
//				}
//				if(!paymentType.equals(paymentTypeName)){ 
//					orderFlowDto.setNextProcessId("charge");
//					orderFlowDto.setNextProcessName("收费");
//					//查询获取资金方选择的财务
//					HttpUtil http = new HttpUtil();
//					AllocationFundDto fund = new AllocationFundDto();
//					fund.setOrderNo(storageDto.getOrderNo());
//					List<AllocationFundDto> list = http.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fund,AllocationFundDto.class);
//					if(null!=list&&list.size()>0){
//						fund = list.get(0);
//					};
//					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(fund.getFinanceUid());
//					listDto.setCurrentHandlerUid(nextUser.getUid());//下一处理人财务
//					listDto.setCurrentHandler(nextUser.getName());
//				}else{
//					DistributionMemberDto memberDto = new DistributionMemberDto();//还款专员
//					memberDto.setOrderNo(storageDto.getOrderNo());
//					RespDataObject<DistributionMemberDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", memberDto,DistributionMemberDto.class);
//					DistributionMemberDto member=  mobj.getData();
//					UserDto nextUser = null;
//					if(member!=null && member.getForeclosureMemberUid()!=null){
//						nextUser=CommonDataUtil.getUserDtoByUidAndMobile(member.getForeclosureMemberUid());
//					}
//					orderFlowDto.setNextProcessId("applyLoan");
//					orderFlowDto.setNextProcessName("申请放款");
//					if(nextUser==null){
//						OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
//						orderBaseBorrowDto.setOrderNo(storageDto.getOrderNo());
//						RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
//						OrderBaseBorrowDto baseBorrowDto=  obj.getData();
//						nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
//					}
//					listDto.setCurrentHandlerUid(nextUser.getUid());//下一处理人
//					listDto.setCurrentHandler(nextUser.getName());
//				}
				//更新流程方法
				 if(!isSubmit(storageDto.getOrderNo(), "fddMortgageStorage")){		
					 goNextNode(orderFlowDto, listDto);
				 }else{
					 listDto.setOrderNo(storageDto.getOrderNo());
					 httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
				 }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
}
