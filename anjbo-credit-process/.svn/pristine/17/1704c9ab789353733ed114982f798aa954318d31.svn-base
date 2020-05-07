package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.FddMortgageReleaseDto;
import com.anjbo.bean.product.FddReleaseDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FddMortgageReleaseService;
import com.anjbo.service.ReleaseService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 抵押品出库
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/process/fddMortgageRelease/v")
public class FddMortgageReleaseController extends BaseController{

	@Resource FddMortgageReleaseService fddMortgageReleaseService;
	@Resource ReleaseService releaseService;
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<FddMortgageReleaseDto> detail(HttpServletRequest request,@RequestBody  FddMortgageReleaseDto releaseDto){
		 RespDataObject<FddMortgageReleaseDto> resp=new RespDataObject<FddMortgageReleaseDto>();
		 FddMortgageReleaseDto mdto=fddMortgageReleaseService.findByFddMortgageRelease(releaseDto.getOrderNo());
		 if(mdto==null){
			 mdto = new FddMortgageReleaseDto();
			 mdto.setOrderNo(releaseDto.getOrderNo());
			 OrderBaseHouseDto orderBaseHouseDto=new OrderBaseHouseDto();
			 orderBaseHouseDto.setOrderNo(releaseDto.getOrderNo());
			 RespDataObject<OrderBaseHouseDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", orderBaseHouseDto,OrderBaseHouseDto.class);
			 OrderBaseHouseDto houseDto=  obj.getData();
			 if(null!=houseDto){
				 List<OrderBaseHousePropertyDto> hlist=houseDto.getOrderBaseHousePropertyDto();
				 if(hlist.size()>0){
					 OrderBaseHousePropertyDto pdto=hlist.get(0);
					 mdto.setRegion(pdto.getHouseRegionName());//城市
					 mdto.setHousePropertyNumber(pdto.getHousePropertyNumber()); //房产证号
					 mdto.setHousePropertyType(pdto.getHousePropertyType());//房产证类型
					 mdto.setHouseName(pdto.getHouseName()); //房产名称
				 }
			 }
		 }
		 FddReleaseDto fddReleaseDto=releaseService.findByRelease(releaseDto.getOrderNo());
		 if(fddReleaseDto!=null){
			 UserDto fddDto=CommonDataUtil.getUserDtoByUidAndMobile(fddReleaseDto.getReleaseUid());
			 mdto.setReleaseUid(fddReleaseDto.getReleaseUid());
			 if(fddDto!=null){
				 mdto.setReleaseName(fddDto.getName());
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
				HttpServletResponse response,@RequestBody FddMortgageReleaseDto releaseDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
				if(StringUtils.isBlank(releaseDto.getOrderNo())){
					rd.setMsg("保存审核信息失败,订单编号不能为空!");
					return rd;
				}
				boolean isSubmit = isSubmit(releaseDto.getOrderNo(),"fddMortgageRelease");
//				if(isSubmit){
//					rd.setMsg("该订单已经被提交");
//					return rd;
//				}
				boolean isWithdraw = isWithdraw(releaseDto.getOrderNo(),"fddMortgageRelease");
				if(isWithdraw &&!isSubmit){
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("该订单已经被撤回");
					return rd;
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				releaseDto.setCreateUid(dto.getUid());
				releaseDto.setUpdateUid(dto.getUid());
				fddMortgageReleaseService.addFddMortgageRelease(releaseDto);
				FddReleaseDto  fddReleaseDto=new FddReleaseDto();
				fddReleaseDto.setReleaseUid(releaseDto.getReleaseUid());
				fddReleaseDto.setCreateUid(dto.getUid());
				fddReleaseDto.setUpdateUid(dto.getUid());
				fddReleaseDto.setOrderNo(releaseDto.getOrderNo());
				releaseService.addRelease(fddReleaseDto);
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				//订单列表
				OrderListDto listDto = new OrderListDto();
				orderFlowDto.setOrderNo(releaseDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("fddMortgageRelease");
				orderFlowDto.setCurrentProcessName("抵押品出库");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				
				orderFlowDto.setNextProcessId("release");
				orderFlowDto.setNextProcessName("解押");
				UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(releaseDto.getReleaseUid());
				listDto.setCurrentHandlerUid(nextUser.getUid());//下一处理人
				listDto.setCurrentHandler(nextUser.getName());
				//更新流程方法
				 //if(!isSubmit(releaseDto.getOrderNo(), "fddMortgageStorage")){		
					 goNextNode(orderFlowDto, listDto);
				 //}else{
					 listDto.setOrderNo(releaseDto.getOrderNo());
					 httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
				 //}
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
	   }
}
