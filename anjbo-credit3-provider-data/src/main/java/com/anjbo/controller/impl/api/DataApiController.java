package com.anjbo.controller.impl.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.bean.AuthorityDto;
import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.bean.RespBean;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.DataBaseController;
import com.anjbo.controller.api.IDataApiController;
import com.anjbo.service.AdministrationDivideService;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.BankService;
import com.anjbo.service.BankSubService;
import com.anjbo.service.DictService;
import com.anjbo.service.ProcessService;
import com.anjbo.service.ProductService;
import com.anjbo.stream.sender.DataSender;

@RestController
public class DataApiController extends DataBaseController implements IDataApiController{	
	
	@Resource
	private DictService dictService;
	
	
	@Resource
	private BankService bankService;
	
	@Resource 
	private AuthorityService authorityService;

	@Resource
	private BankSubService bankSubService;
	
	@Resource
	private ProcessService processService;

	@Resource
	private ProductService productService;
	
	@Resource 
    private AdministrationDivideService administrationDivideService;
	
	
	@Override
	public List<ProcessDto> findProcessDto(@PathVariable("productId") Integer productId) {
		try {
			ProcessDto processDto = new ProcessDto();
			processDto.setProductId(productId);;
			return processService.search(processDto);
		}catch (Exception e) {
			logger.error("获取productId:"+productId+"流程集合异常", e);
			return new ArrayList<ProcessDto>();
		}
	}
	
	@Override
	@RequestMapping(value = "/selectDictDtoListByType_{type}", method = { RequestMethod.POST })
	public List<DictDto> getDictDtoListByType(@PathVariable("type") String type){
		try {
			DictDto dto = new DictDto();
			dto.setType(type);
			return dictService.search(dto);
		} catch (Exception e) {
			logger.error("获取type:"+type+"字典集合异常", e);
			return new ArrayList<DictDto>();
		}
	}
	
	@Override
	public List<ProductDto> getProductList() {
		try {
			List<ProductDto> productDtos = productService.search(null);
			//获取产品，以及产品对应的节点集合
		    List<ProcessDto> processDtos = processService.search(null);
		    List<ProcessDto> temp = null;
			for (ProductDto product : productDtos) {
				temp = new ArrayList<ProcessDto>();
				for (ProcessDto processDto : processDtos) {
					if(product.getId() == processDto.getProductId()){
						temp.add(processDto);
					}
				}
				product.setProcessDtos(temp);
			}
			return productDtos;
		}catch (Exception e) {
			logger.error("获取产品集合异常", e);
			return new ArrayList<ProductDto>();
		}
	}
	
	@Override
	public RespData<BankDto> selectBankList() {
		RespData<BankDto> resp = new RespData<BankDto>();
		try {
			List<BankDto> list = bankService.search(null);
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			RespHelper.setFailData(resp, new ArrayList<BankDto>(), "获取银行集合异常");
		}
		return resp;
	}
	
	@Override
	public RespData<BankSubDto> selectSubBankListByBankId(@PathVariable("bankId") int bankId) {
		RespData<BankSubDto> resp = new RespData<BankSubDto>();
		try {
			BankSubDto bankSubDto = new BankSubDto();
			bankSubDto.setPid(bankId);
			List<BankSubDto> list = bankSubService.search(bankSubDto);
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			RespHelper.setFailData(resp, new ArrayList<BankSubDto>(), "获取银行集合异常");
		}
		return resp;
	}
	
	@Override
	public ProcessDto getNextProcess(@RequestBody Map<String, Object> params) {
		return processService.getNextProcess(params);
	}
	
	@Override
	public AuthorityDto findAuthority(@RequestBody AuthorityDto authorityDto) {
		return authorityService.find(authorityDto);
	}
	
	@Resource DataSender dataSender;
	
	@Override
	public RespBean<String> send() {
		for (int i = 0; i < 5; i++) {
			dataSender.sendAMS("生产："+i);
		}
		return new RespBean<String>("生产完成");
	}

	@Override
	public AdministrationDivideDto findAdministrationDivideDto(@RequestBody AdministrationDivideDto dto) {
		return administrationDivideService.find(dto);
	}
	
	/**
	 * 查找银行
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankDto> getBankNameById(@RequestBody BankDto dto){ 
		RespDataObject<BankDto> resp = new RespDataObject<BankDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankDto(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 查找支行
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankSubDto> getSubBankNameById(@RequestBody BankSubDto dto){ 
		RespDataObject<BankSubDto> resp = new RespDataObject<BankSubDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankSubService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankSubDto(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	public RespDataObject<Map<String, Object>> selectAuthorityByProductId(@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {

			Integer productId = MapUtils.getInteger(params, "productId",440301);
			List<ProcessDto> processDtos = findProcessDto(productId);
			
			String processIds = "";
			for (ProcessDto processDto : processDtos) {
				processIds += processDto.getId()+",";
			}
			if(StringUtils.isNotEmpty(processIds)){
				processIds = processIds.substring(0, processIds.length()-1);
			}
			List<AuthorityDto> authorityDtos = authorityService.selectAuthorityByProcessIds(processIds);
			Map<String, Object> retMap = new HashMap<String, Object>();
			for (AuthorityDto authorityDto : authorityDtos) {
				String processId = "";
				for (ProcessDto processDto : processDtos) {
					if(authorityDto.getProcessId().equals(processDto.getId())){
						processId = processDto.getProcessId();
						break;
					}
				}
				if(authorityDto.getName().contains("[A]")){
					retMap.put(processId+"[A]", authorityDto.getId());
				}else{
					retMap.put(processId+"[B]", authorityDto.getId());
				}
			}
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			logger.error("异常：", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@Override
	public DictDto getCityById(@RequestBody DictDto dto) {
		return dictService.findDto(dto);
	}

}
