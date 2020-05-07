/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.AuditDto;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.BaseHouseLendingDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppFacesignDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.finance.IAuditController;
import com.anjbo.service.finance.AuditService;
import com.anjbo.service.finance.StatementService;
import com.anjbo.service.order.BaseHouseLendingService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.AppFacesignService;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class AuditController extends OrderBaseController implements IAuditController{

	@Resource private AuditService auditService;
	
	@Resource private UserApi userApi;
	
	@Resource
	private AllocationFundService allocationFundService;
	
	@Resource private ThirdApi thirdApi;
	
	@Resource private AppFacesignService appFacesignService;
	
	@Resource private BaseHouseLendingService baseHouseLendingService;
	
	@Resource private StatementService statementService;
	
	@Resource private BaseListService baseListService;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			String orderNo = dto.getOrderNo();
			//云南发送放款指令
			BaseListDto baseDto = new BaseListDto();
			baseDto.setOrderNo(orderNo);
			baseDto = baseListService.find(baseDto);
			if(!"04".equals(baseDto.getProductCode())) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderNo", orderNo);
				RespStatus respStatus = thirdApi.confirmPayment(map);
				if("FAIL".equals(respStatus.getCode()) && !respStatus.getMsg().contains("已经发送过放款指令不能重复发送")){
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg(respStatus.getMsg());
					return resp;
			}
			}
			//陕国投房抵贷调用【2107】影像资料上传结果查询
			boolean isSGT = false;
			AllocationFundDto allocationFundDto = new AllocationFundDto();
			allocationFundDto.setOrderNo(dto.getOrderNo());
			List<AllocationFundDto> allocationFundDtos = allocationFundService.search(allocationFundDto);
			for (AllocationFundDto fundDto : allocationFundDtos) {
				FundDto fund = new FundDto();
				fund.setId(fundDto.getFundId());
				fund = userApi.findByFundId(fund);
				if("1000".equals(fund.getFundCode())) {
					isSGT = true;
				}
			}
			System.out.println("是否陕国投财务审核："+isSGT);
			if(isSGT) {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("orderNo", dto.getOrderNo());
				RespDataObject<Map<String, Object>> respData = thirdApi.searchSgtBusinfoStatus(params);
				if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())) {
					System.out.println("影响资料推送结果成功");
					Map<String,Object> map = new HashMap<String,Object>();
					String batNo = getNum();
					map.put("batNo", batNo);
					map.put("orderNo", dto.getOrderNo());
					AppFacesignDto fDto = new AppFacesignDto();
					fDto.setOrderNo(orderNo);
					fDto = appFacesignService.find(fDto);
					if("企业".equals(fDto.getAccountType())) {
						map.put("acType", 12);
					}else {
						map.put("acType", 11);//账户类型 10-个人贷记卡账户11-个人借记卡账户12-企业账户14-商户
					}
					//回款信息
					BaseHouseLendingDto lendingDto = new BaseHouseLendingDto();
					lendingDto.setOrderNo(orderNo);
					lendingDto = baseHouseLendingService.find(lendingDto);
					Map<String, Object> returnAcMap = new HashMap<String, Object>();
					returnAcMap.put("acUse", 1);
					returnAcMap.put("phoneNo", lendingDto.getPaymentPhoneNumber());
					returnAcMap.put("acName", lendingDto.getPaymentBankUserName());
					returnAcMap.put("acType", MapUtils.getString(map, "acType"));
					returnAcMap.put("acno", lendingDto.getPaymentBankAccount());
					returnAcMap.put("bankCode", "002");
					returnAcMap.put("authentication", "002");
//					returnAcMap.put("idNo", lendingDto.getpayment);
//					returnAcMap.put("idType", value);
					map.put("returnAcMap", returnAcMap);
					//如果批次号存在，则只查询结果
					StatementDto sDto = new StatementDto();
					sDto.setOrderNo(orderNo);
					sDto = statementService.find(sDto);
					if(sDto==null||StringUtil.isBlank(sDto.getSgtLendingBatNo())) {
						System.out.println("无批次号");
						RespStatus respStatus = thirdApi.sgtLending(map);//2101
						if(RespStatusEnum.SUCCESS.getCode().equals(respStatus.getCode())||"0000".equals(respStatus.getCode())) {
							//录入批次号
							//2102
							RespStatus re = thirdApi.sgtLendingResult(map);
							System.out.println("2102返回："+re.getCode());
							if(!RespStatusEnum.SUCCESS.getCode().equals(re.getCode())&&!"0000".equals(re.getCode())) {//放款失败
								System.out.println("放款失败");
								if(!(re.getMsg().contains("错误")||re.getMsg().contains("失败")||re.getMsg().contains("A100001")
										||re.getMsg().contains("A100002")||re.getMsg().contains("A100003")||re.getMsg().contains("A100004")
										||re.getMsg().contains("A100005")||re.getMsg().contains("A100006")||re.getMsg().contains("A100007")
										||re.getMsg().contains("A100008")||re.getMsg().contains("A100009")||re.getMsg().contains("A100010")
										||re.getMsg().contains("A100011")||re.getMsg().contains("A100012")||re.getMsg().contains("A100013")
										||re.getMsg().contains("A100014")||re.getMsg().contains("A100015")||re.getMsg().contains("A100016")
										||re.getMsg().contains("A100017")||re.getMsg().contains("A100018")||re.getMsg().contains("A100019")
										||re.getMsg().contains("A100020"))) {
									StatementDto temp = new StatementDto();
									temp.setOrderNo(orderNo);
									temp.setSgtLendingBatNo(batNo);
									statementService.update(temp);
									return RespHelper.setFailRespStatus(resp, "等待放款");
								}else {
									return RespHelper.setFailRespStatus(resp, respStatus.getMsg());
								}
							}else {
								System.out.println("放款成功");
							}
						}else {
							return RespHelper.setFailRespStatus(resp, respStatus.getMsg());
						}
					}else {
						System.out.println("有批次号"+sDto.getSgtLendingBatNo());
						map.put("batNo", sDto.getSgtLendingBatNo());
						//2102
						RespStatus re = thirdApi.sgtLendingResult(map);
						System.out.println("2102返回："+re.getCode());
						if(!RespStatusEnum.SUCCESS.getCode().equals(re.getCode())
								&&!"0000".equals(re.getCode())) {//放款成功
							System.out.println("2102返回："+re.getMsg());
							if(re.getMsg().contains("错误")||re.getMsg().contains("失败")||re.getMsg().contains("A100001")
									||re.getMsg().contains("A100002")||re.getMsg().contains("A100003")||re.getMsg().contains("A100004")
									||re.getMsg().contains("A100005")||re.getMsg().contains("A100006")||re.getMsg().contains("A100007")
									||re.getMsg().contains("A100008")||re.getMsg().contains("A100009")||re.getMsg().contains("A100010")
									||re.getMsg().contains("A100011")||re.getMsg().contains("A100012")||re.getMsg().contains("A100013")
									||re.getMsg().contains("A100014")||re.getMsg().contains("A100015")||re.getMsg().contains("A100016")
									||re.getMsg().contains("A100017")||re.getMsg().contains("A100018")||re.getMsg().contains("A100019")
									||re.getMsg().contains("A100020")) {
								System.out.println("更新批次号为空");
								StatementDto temp = new StatementDto();
								temp.setOrderNo(orderNo);
								temp.setSgtLendingBatNo("");
								statementService.update(temp);
							}
							return RespHelper.setFailRespStatus(resp, re.getMsg());
						}
					}
				}else {
					System.out.println("影响资料推送结果失败");
					return RespHelper.setFailRespStatus(resp, respData.getMsg());
				}
			}
			
			AuditDto temp = new AuditDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = auditService.find(temp);
			if(temp == null) {
				auditService.insert(dto);
			}else {
				auditService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("financialAudit");
			flowDto.setCurrentProcessName("财务审核");
			flowDto.setNextProcessId("lending");
			flowDto.setNextProcessName("放款");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());
			
			UserDto handlerUserDto = userApi.findUserDtoByUid(dto.getNextHandleUid());
			BaseListDto baseListDto = new BaseListDto();
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
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditDto> processDetails(@RequestBody AuditDto dto) {
		RespDataObject<AuditDto> resp = new RespDataObject<AuditDto>();
		try {
			
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
	public String getDate() {
		SimpleDateFormat sfDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return sfDateFormat.format(new Date());
	}
	
	public String getNum() {                             //当前时分秒加上三位随机数
		int xterm  = (int) ((Math.random()*900)+100);
		return getDate()+xterm;
	}
		
}