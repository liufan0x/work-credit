/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppFacesignDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.bean.risk.AuditOfficerDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.impl.order.BusinfoController;
import com.anjbo.controller.process.IAppFacesignController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.BusinfoService;
import com.anjbo.service.process.AppFacesignService;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.service.risk.AuditFinalService;
import com.anjbo.service.risk.AuditOfficerService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:33
 * @version 1.0
 */
@RestController
public class AppFacesignController extends OrderBaseController implements IAppFacesignController{

	@Resource private AppFacesignService appFacesignService;
	
	@Resource private BaseListService baseListService;
	
	@Resource private BaseBorrowService baseBorrowService;

	@Resource private AuditOfficerService auditOfficerService;
	
	@Resource private AuditFinalService auditFinalService;
	
	@Resource private AllocationFundService allocationFundService;
	
	@Resource private UserApi userApi;
	
	@Resource private BusinfoController businfoController;
	
	@Resource private BusinfoService businfoService;
	
	@Resource private ThirdApi thirdApi;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AppFacesignDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			
			if(baseListDto.getAuditSort()==1){
				//面签加影像资料上传校验
				//先面签则不校验面签资料
				if(baseListDto.getAuditSort()!=2&&!businfoService.faceBusinfoCheck(dto.getOrderNo(), baseListDto.getProductCode(),baseListDto.getAuditSort())){
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg("面签资料不完整");
					return resp;
				}
			}
			
			AppFacesignDto temp = new AppFacesignDto();
			temp.setOrderNo(dto.getOrderNo());
			temp= appFacesignService.find(temp);
			
			boolean isSGT = false;
			AllocationFundDto fundDto = new AllocationFundDto();
			fundDto.setOrderNo(dto.getOrderNo());
			List<AllocationFundDto> allocationFundDtos = allocationFundService.search(fundDto);
			for (AllocationFundDto fDto : allocationFundDtos) {
				if(47 == fDto.getFundId()) {
					isSGT = true;
				}
			}
			//如果是陕国投，必须绑定一个支付渠道才能提交面签
			if(isSGT) {
				if((dto.getReturnOne()==null||!dto.getReturnOne().contains("成功"))
						&&(dto.getReturnTwo()==null||!dto.getReturnTwo().contains("成功"))) {
					RespHelper.setFailRespStatus(resp, "未成功绑定支付渠道");
					return resp;
				}
			}
			
			if(temp == null) {
				appFacesignService.insert(dto);
			}else {
				appFacesignService.updateByOrderNo(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("facesign");
			flowDto.setCurrentProcessName("面签");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			
			String handlerUid = "";
			
			if(!"04".equals(baseListDto.getProductCode())){
				if(baseListDto.getAuditSort() == 1) {
					if(baseListDto.getBorrowingAmount() >= 3000) {
						AuditOfficerDto auditOfficerDto = new AuditOfficerDto();
						auditOfficerDto.setOrderNo(dto.getOrderNo());
						auditOfficerDto = auditOfficerService.find(auditOfficerDto);
						if(auditOfficerDto != null) {
							handlerUid = auditOfficerDto.getJusticeUid();
						}else {
							AuditFinalDto auditFinalDto = new AuditFinalDto();
							auditFinalDto.setOrderNo(dto.getOrderNo());
							auditFinalDto = auditFinalService.find(auditFinalDto);
							handlerUid = auditFinalDto.getOfficerUid();
						}
						flowDto.setNextProcessId("auditJustice");
						flowDto.setNextProcessName("法务审批");
					}else {
						//提放业务面签到要件校验
						if("05".equals(baseListDto.getProductCode())) {
							BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
							baseBorrowDto.setOrderNo(dto.getOrderNo());
							baseBorrowDto = baseBorrowService.find(baseBorrowDto);
							handlerUid = baseBorrowDto.getElementUid();
							flowDto.setNextProcessId("element");
							flowDto.setNextProcessName("要件校验");
						}else {
							AllocationFundDto allocationFundDto = new AllocationFundDto();
							allocationFundDto.setOrderNo(dto.getOrderNo());
							List<AllocationFundDto> list = allocationFundService.search(allocationFundDto);
							if(list != null && !list.isEmpty()) {
								handlerUid = list.get(0).getCreateUid();
							}
							flowDto.setNextProcessId("fundDocking");
							flowDto.setNextProcessName("资料推送");
						}
					}
				}else {
					handlerUid = baseListDto.getAcceptMemberUid();
					flowDto.setNextProcessId("placeOrder");
					flowDto.setNextProcessName("提单");
				}
			}else {
				handlerUid = baseListDto.getNotarialUid();
				flowDto.setNextProcessId("notarization");
				flowDto.setNextProcessName("公证");
			}

			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			goNextNode(flowDto, baseListDto);
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 建立签约关系，返回是否发送短信验证码 ifSend 01-已发送 02-未发送
	 * @param dto
	 * @return
	 */
	@Override
	public RespStatus signVerificationCode(@RequestBody AppFacesignDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("brNo", Constants.SGT_WS_BRNO);
			map.put("projNo", Constants.SGT_WS_PROJNO);
			map.put("custName", dto.getCustomerName());
			map.put("idType", 0);//身份证
			map.put("idNo", dto.getCertificateNo());
			map.put("phoneNo", dto.getMobile());
			map.put("acType", 1);//个人借记卡,3个人信用卡-暂时不支持
			map.put("acno", dto.getBankCardNo());//账户号
			map.put("bankCode", dto.getBankNameId());
			map.put("acName", dto.getBankName());
			//cvvNo.Cvn2码.信用卡使用验证码CVN2(即“后三码”).validDate.四位数，前两位为年份，后两位为月份
			if(dto.getqOne()!=null&&1==dto.getqOne()) {
				map.put("cardChn", "CL0001");//中金支付
			}else {
				map.put("cardChn", "CL0005");//宝付支付
			}
			map.put("type",1);
			map.put("txCode","2011");
			RespDataObject<Map<String, Object>>  object = thirdApi.interfaceCall(map);
			System.out.println("建立签约关系"+object.getCode());
			if(object.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
				Map<String,Object> m = object.getData();
				AppFacesignDto temp = new AppFacesignDto();
				temp.setOrderNo(dto.getOrderNo());
				System.out.println("签约流水号:"+m.get("serialNo"));
				if(m.containsKey("serialNo")&&m.get("serialNo")!=null) {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setSerialNoOne(MapUtils.getString(m, "serialNo"));
					}else {
						dto.setSerialNoTwo(MapUtils.getString(m, "serialNo"));
					}
					temp= appFacesignService.find(temp);
					if(temp == null) {
						appFacesignService.insert(dto);
					}else {
						appFacesignService.updateByOrderNo(dto);
					}
				}else {
					RespHelper.setFailRespStatus(resp, object.getMsg());
				}
				if(m.containsKey("ifSend")&&"01".equals(MapUtils.getString(m, "ifSend"))) {
					RespHelper.setSuccessRespStatus(resp);
				}else {
					RespHelper.setFailRespStatus(resp, object.getMsg());
				}
			}else {
				RespHelper.setFailRespStatus(resp, object.getMsg());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 签约，传短信验证码和签约流水号
	 * @param dto
	 * @return
	 */
	@Override
	public RespStatus sign(@RequestBody AppFacesignDto dto) {
		RespStatus resp = new RespStatus();
		RespHelper.setFailRespStatus(resp, "签约失败");
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("brNo", Constants.SGT_WS_BRNO);
			map.put("projNo", Constants.SGT_WS_PROJNO);
			map.put("custName", dto.getCustomerName());
			map.put("idType", 0);//身份证
			map.put("idNo", dto.getCertificateNo());
			map.put("phoneNo", dto.getMobile());
			map.put("acType", 1);//个人借记卡,3个人信用卡-暂时不支持
			map.put("acno", dto.getBankCardNo());//账户号
			map.put("bankCode", dto.getBankNameId());
			map.put("acName", dto.getBankName());
			//cvvNo.Cvn2码.信用卡使用验证码CVN2(即“后三码”).validDate.四位数，前两位为年份，后两位为月份
			if(dto.getqOne()!=null&&1==dto.getqOne()) {
				map.put("cardChn", "CL0001");//中金支付
			}else {
				map.put("cardChn", "CL0005");//宝付支付
			}
			AppFacesignDto temp = new AppFacesignDto();
			temp.setOrderNo(dto.getOrderNo());
			temp= appFacesignService.find(temp);
			if(dto.getqOne()!=null&&1==dto.getqOne()) {
				map.put("serialNo", temp.getSerialNoOne());
			}else {
				map.put("serialNo", temp.getSerialNoTwo());
			}
			map.put("msgCode", dto.getMsgCode());
			map.put("type",1);
			map.put("txCode","2012");
			RespDataObject<Map<String, Object>>  object = thirdApi.interfaceCall(map);
			if(object.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
				Map<String,Object> m = object.getData();
				if(m.containsKey("dealNo")&&
						("01".equals(MapUtils.getString(m, "dealNo"))
								||"9999".equals(MapUtils.getString(m, "dealNo"))
								||(object.getMsg()!=null&&object.getMsg().contains("重复鉴权")))) {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setReturnOne("中金支付 成功");
						resp.setMsg("中金支付 成功");
					}else {
						dto.setReturnTwo("宝付支付 成功");
						resp.setMsg("宝付支付 成功");
					}
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
				}else if(m.containsKey("dealNo")&&"03".equals(MapUtils.getString(m, "dealNo"))) {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setReturnOne("处理中");
					}else {
						dto.setReturnTwo("处理中");
					}
					resp.setMsg(MapUtils.getString(m, "dealDesc"));
					RespHelper.setFailRespStatus(resp,MapUtils.getString(m, "dealDesc"));
				}else {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setReturnOne("签约失败");
					}else {
						dto.setReturnTwo("签约失败");
					}
					RespHelper.setFailRespStatus(resp,object.getMsg());
				}
				appFacesignService.updateByOrderNo(dto);
			}else {
				if(object.getMsg()!=null&&object.getMsg().contains("重复鉴权")) {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setReturnOne("中金支付 成功");
						resp.setMsg("中金支付 成功");
					}else {
						dto.setReturnTwo("宝付支付 成功");
						resp.setMsg("宝付支付 成功");
					}
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
				}else {
					if(dto.getqOne()!=null&&1==dto.getqOne()) {
						dto.setReturnOne("签约失败");
					}else {
						dto.setReturnTwo("签约失败");
					}
					RespHelper.setFailRespStatus(resp,object.getMsg());
				}
				appFacesignService.updateByOrderNo(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AppFacesignDto> processDetails(@RequestBody AppFacesignDto dto) {
		RespDataObject<AppFacesignDto> resp = new RespDataObject<AppFacesignDto>();
		try {
			dto = appFacesignService.find(dto);
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}