package com.anjbo.controller.impl.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseCustomerBorrowerDto;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.bean.order.BaseHouseDto;
import com.anjbo.bean.order.BaseHousePropertyDto;
import com.anjbo.bean.order.BaseHousePropertypeopleDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.RiskEnquiryDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.IOrderApiController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.element.ForeclosureTypeMapper;
import com.anjbo.dao.order.BaseHousePropertyMapper;
import com.anjbo.dao.order.BaseHousePropertypeopleMapper;
import com.anjbo.dao.order.BaseListMapper;
import com.anjbo.service.element.DocumentsService;
import com.anjbo.service.element.PaymentTypeService;
import com.anjbo.service.finance.ApplyLoanService;
import com.anjbo.service.finance.ReceivableForService;
import com.anjbo.service.finance.StatementService;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseCustomerBorrowerService;
import com.anjbo.service.order.BaseCustomerGuaranteeService;
import com.anjbo.service.order.BaseCustomerService;
import com.anjbo.service.order.BaseHouseService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.BusinfoService;
import com.anjbo.service.order.BusinfoTypeService;
import com.anjbo.service.order.FlowService;
import com.anjbo.service.process.AuditManagerService;
import com.anjbo.service.risk.AuditFirstForeclosureService;
import com.anjbo.service.risk.AuditFirstPaymentService;
import com.anjbo.service.risk.AuditFirstService;
import com.anjbo.service.risk.CreditService;
import com.anjbo.service.risk.RiskEnquiryService;
import com.anjbo.utils.BeanToMapUtil;



@RestController
public class OrderApiController extends OrderBaseController implements IOrderApiController{	
	
	@Resource private BaseListService baseListService;
	@Resource private BaseBorrowService baseBorrowService;
	@Resource private BaseCustomerService baseCustomerService;
	@Resource private BaseHouseService baseHouseService;
	@Resource private BaseCustomerBorrowerService baseCustomerBorrowerService;
	@Resource private BaseCustomerGuaranteeService baseCustomerGuaranteeService;
	@Resource private CreditService creditService;
	@Resource private RiskEnquiryService riskEnquiryService;
	@Resource private DocumentsService documentsService;
	@Resource private ReceivableForService receivableForService;
	@Resource private FlowService  flowService;
	@Resource private UserApi userApi;
	@Resource private AuditFirstService auditFirstService;
	@Resource private AuditFirstForeclosureService auditFirstForeclosureService;  
	@Resource private AuditFirstPaymentService auditFirstPaymentService;
	@Resource private AuditManagerService auditManagerService;
    @Resource private BusinfoService businfoService;
    @Resource private BusinfoTypeService businfoTypeService;
    @Resource private BaseHousePropertyMapper baseHousePropertyMapper;
    @Resource private BaseHousePropertypeopleMapper baseHousePropertypeopleMapper;
    @Resource private ForeclosureTypeMapper foreclosureTypemapper;
    @Resource private PaymentTypeService paymentTypeService;
    @Resource private BaseListMapper baseListMapper;
    @Resource
	private ApplyLoanService applyLoanService;
    @Resource private StatementService statementService;
    @Resource
	private DataApi dataApi;

	@Override
	public BaseListDto findByOrderNo(@PathVariable("orderNo")  String orderNo) {
		BaseListDto dto = new BaseListDto();
		dto.setOrderNo(orderNo);
		return baseListService.find(dto);
	}


	@Override
	public BaseBorrowDto findBorrowByOrderNo(@PathVariable("orderNo")  String orderNo) {
		BaseBorrowDto dto = new BaseBorrowDto();
		dto.setOrderNo(orderNo);
		return baseBorrowService.find(dto);
	}

	@Override
	public BaseCustomerDto findCustomerByOrderNo(@PathVariable("orderNo")  String orderNo) {
		BaseCustomerDto dto = new BaseCustomerDto();
		dto.setOrderNo(orderNo);
		return baseCustomerService.find(dto);
	}

	@Override
	public BaseHouseDto findHouseByOrderNo(@PathVariable("orderNo")  String orderNo) {
		BaseHouseDto dto = new BaseHouseDto();
		dto.setOrderNo(orderNo);
		return baseHouseService.find(dto);
	}

	@Override
	public DocumentsDto findDocumentsByOrderNo(@PathVariable("orderNo")  String orderNo) {
		DocumentsDto dto = new DocumentsDto();
		dto.setOrderNo(orderNo);
		return documentsService.find(dto);
	}

	@Override
	public List<ReceivableForDto> findReceivableForByOrderNo(@PathVariable("orderNo") String orderNo) {
		ReceivableForDto dto = new ReceivableForDto();
		dto.setOrderNo(orderNo);
		return receivableForService.search(dto);
	}
	
	@Override
	public BaseListDto selectDetailByOrderNo(@RequestBody BaseListDto baseListDto) {
		return baseListService.selectDetail(baseListDto.getOrderNo());
	}
	
	@Override
	public List<Map<String, Object>> selectAllBusInfo(@PathVariable("orderNo") String orderNo) {
		BusinfoDto businfoDto = new BusinfoDto();
		businfoDto.setOrderNo(orderNo);
		List<BusinfoDto> businfoDtos = businfoService.search(businfoDto);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (BusinfoDto businfo : businfoDtos) {
			list.add(BeanToMapUtil.beanToMap(businfo));
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getParentBusInfoTypes(@RequestBody Map<String, Object> map) {
		return businfoTypeService.getParentBusInfoTypes(map);
	}
	
	@Override
	public List<Map<String, Object>> getSonType(@RequestBody Map<String, Object> map) {
		return businfoTypeService.getSonType(map);
	}


	@Override
	public List<Map<String, Object>> findHousePropertyOrderNo(@PathVariable("orderNo") String orderNo) {
		// TODO Auto-generated method stub  
		BaseHousePropertyDto dto = new BaseHousePropertyDto();
		dto.setOrderNo(orderNo);
		return baseHousePropertyMapper.findAll(dto);
	}


	@Override
	public List<Map<String, Object>> findHousePropertyPeopleOrderNo(@PathVariable("orderNo") String orderNo) {
		// TODO Auto-generated method stub
		BaseHousePropertypeopleDto dto =new BaseHousePropertypeopleDto();
		dto.setOrderNo(orderNo);
		return baseHousePropertypeopleMapper.findAll(dto);
	}


	@Override
	public ForeclosureTypeDto findForeclosureByOrderNo(@PathVariable("orderNo") String orderNo) {
		return foreclosureTypemapper.selects(orderNo);
	}


	@Override
	public PaymentTypeDto findPaymentByOrderNo(@PathVariable("orderNo") String orderNo) {
		// TODO Auto-generated method stub
		PaymentTypeDto paymentTypeDto = new PaymentTypeDto();
		paymentTypeDto.setOrderNo(orderNo);
		return paymentTypeService.find(paymentTypeDto);
	}


	@Override
	public BaseListDto findByListOrderNo(@PathVariable("orderNo") String orderNo) {
		// TODO Auto-generated method stub
		BaseListDto dto = new BaseListDto();
		dto.setOrderNo(orderNo);
		return baseListMapper.findOne(dto);
	}


	@Override
	public List<ReceivableForDto>  findReceivableForOrderNo(@PathVariable("orderNo") String orderNo) {
		ReceivableForDto dto = new ReceivableForDto();
		dto.setOrderNo(orderNo);
		return receivableForService.search(dto);
	}

	@Override
	public RespDataObject<Map<String, Object>> applyLoanProcessDetails(@RequestBody ApplyLoanDto dto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(dto.getOrderNo())) {
				return RespHelper.setFailDataObject(resp, null, "订单号不能为空");
			}
			dto = applyLoanService.find(dto);
			if(dto != null) {
				if(StringUtils.isNotEmpty(dto.getLendingBankId())) {
					BankDto bankDto = new BankDto();
					bankDto.setId(Integer.valueOf(dto.getLendingBankId()));
					bankDto = dataApi.getBankNameById(bankDto).getData();
					dto.setLendingBank(bankDto.getName());
				}
				if(StringUtils.isNotEmpty(dto.getLendingBankSubId())) {
					BankSubDto bankSubDto = new BankSubDto();
					bankSubDto.setId(Integer.valueOf(dto.getLendingBankSubId()));
					bankSubDto = dataApi.getSubBankNameById(bankSubDto).getData();
					dto.setLendingBankSub(bankSubDto.getName());
				}
			}
			map.put("loanDto", dto);
			
			return RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public BaseCustomerGuaranteeDto findCustomerGuaranteeByOrderNo(@PathVariable("orderNo") String orderNo) {
		BaseCustomerGuaranteeDto dto = new BaseCustomerGuaranteeDto();
		dto.setOrderNo(orderNo);
		return baseCustomerGuaranteeService.find(dto);
	}


	@Override
	public BaseCustomerBorrowerDto findCustomerBorrowerByOrderNo(@PathVariable("orderNo") String orderNo) {
		BaseCustomerBorrowerDto dto = new BaseCustomerBorrowerDto();
		dto.setOrderNo(orderNo);
		return baseCustomerBorrowerService.find(dto);
	}


	@Override
	public CreditDto findOrderCredit(@PathVariable("orderNo") String orderNo) {
		CreditDto dto=new CreditDto();
		dto.setOrderNo(orderNo);
		return creditService.find(dto);
	}


	@Override
	public List<RiskEnquiryDto> findOrderRiskEnquiry(@PathVariable("orderNo")String orderNo) {
		RiskEnquiryDto dto=new RiskEnquiryDto();
		dto.setOrderNo(orderNo);
		return riskEnquiryService.search(dto);
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public StatementDto findStatementDetails(@RequestBody StatementDto dto) {
		dto = statementService.find(dto);
		return dto;
	}


	/**
	 * 编辑
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus editOrderList(@RequestBody BaseListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseListService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 新增
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<FlowDto> addOrderFlow(@RequestBody FlowDto dto) {
		RespDataObject<FlowDto> resp = new RespDataObject<FlowDto>();
		try {
			if (dto == null || "".equals(dto.getOrderNo())
					|| "".equals(dto.getCurrentProcessId()) || "".equals(dto.getNextProcessId())
					|| "".equals(dto.getHandleUid())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			String uid = userApi.getUserDto().getUid();
			dto.setCreateUid(uid);
			dto.setUpdateUid(uid);
		 	dto = flowService.insert(dto);
		 	
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("新增异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FlowDto(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	@Override
	public RespData<FlowDto> selectOrderFlowList(@RequestBody FlowDto dto) {
		RespData<FlowDto> resp = new RespData<FlowDto>();
		try {
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			Integer productId = Integer.valueOf(baseListDto.getCityCode() + baseListDto.getProductCode());
			if (productId == null) {
				productId = 440301;
			}
			List<FlowDto> list = flowService.search(dto);
			List<UserDto> userDtos = userApi.selectAllUserDto();
			List<FlowDto> tempList = new ArrayList<FlowDto>();
			List<ProcessDto> processDtos = dataApi.findProcessDto(productId);
			boolean fl1 = false;
			boolean fl2 = false;
			for (int i = 0; i < list.size(); i++) {
				FlowDto orderFlow = list.get(i);
				for (ProcessDto processDto : processDtos) {
					if (orderFlow.getCurrentProcessId().equals(processDto.getProcessId())) {
						orderFlow.setCurrentProcessName(processDto.getProcessName());
					}
					if (orderFlow.getNextProcessId().equals(processDto.getProcessId())) {
						orderFlow.setNextProcessName(processDto.getProcessName());
					}
				}
				for (UserDto user : userDtos) {
					if (user.getUid().equals(orderFlow.getHandleUid())) {
						orderFlow.setHandleName(user.getName());
						break;
					}
				}
				if (!fl2) {
					tempList.add(orderFlow);
				} else {
					fl2 = false;
					continue;
				}
				fl1 = false;
				if (StringUtils.isNotEmpty(orderFlow.getBackReason())) {
					if (orderFlow.getIsNewWalkProcess() == 1) {
						Iterator<FlowDto> flowList = tempList.iterator();
						while (flowList.hasNext()) {
							FlowDto temp = flowList.next();
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								fl1 = true;
							}
							if (fl1) {
								flowList.remove();
							}
						}
					} else {
						for (FlowDto temp : tempList) {
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								temp = orderFlow;
								fl2 = true;
							}
						}
						tempList.remove(orderFlow);
					}
				}
			}
			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			logger.error("获取订单流水异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
}
