package com.anjbo.controller;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.common.*;
import com.anjbo.service.CommonService;
import com.anjbo.utils.StringUtil;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping("/credit/common/base/v")
public class CommonController extends BaseController{

	@Resource
	private CommonService commonService;



	@ResponseBody
	@RequestMapping(value = "/initData") 
	public RespStatus initData(){
		try {
			commonService.initData();
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 获取银行，内包含支行
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectBankList") 
	public RespData<BankDto> selectBankList(){
		RespData<BankDto> resp = new RespData<BankDto>();
		commonService.initData();
		List<BankDto> bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		if(bankDtos == null){
			bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		}
		RespHelper.setSuccessData(resp, bankDtos);
		return resp;
	}
	/**
	 * 根据银行id查询银行附带支行
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/selectBank")
	public RespDataObject<BankDto> selectBank(@RequestBody Map<String,Object> map){
		RespDataObject<BankDto> result = new RespDataObject<BankDto>();
		Integer bankId = MapUtils.getInteger(map, "bankId", null);
		if(null==bankId){
			result.setMsg("请传银行id");
			return result;
		}
		List<BankDto> bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		BankDto tmp = null;
		for(BankDto b:bankDtos){
			if(b.getId()==bankId){
				tmp = b;
				break;
			}
		}
		RespHelper.setSuccessDataObject(result,tmp);
		return result;
	}
	/**
	 * 获取支行
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectSubBankListByBankId") 
	public RespData<SubBankDto> selectSubBankListByBankId(@RequestBody Map<String, Object> params){
		RespData<SubBankDto> resp = new RespData<SubBankDto>();
		commonService.initData();
		int bankId = 0;
		if(params.containsKey("bankId")){
			bankId = MapUtils.getIntValue(params, "bankId",0); 
		}else if(params.containsKey("foreclosureBankNameId")){
			bankId = MapUtils.getIntValue(params, "foreclosureBankNameId",0); 
		}else if(params.containsKey("licenseRevBank")){
			bankId = MapUtils.getIntValue(params, "licenseRevBank",0); 
		}else if(params.containsKey("oldLoanBankNameId")){
			bankId = MapUtils.getIntValue(params, "oldLoanBankNameId",0); 
		}else if(params.containsKey("loanBankNameId")){
			bankId = MapUtils.getIntValue(params, "loanBankNameId",0); 
		}else if(params.containsKey("rebateBankId")){
			bankId = MapUtils.getIntValue(params, "rebateBankId",0); 
		}else if(params.containsKey("bankNameId")){
			bankId = MapUtils.getIntValue(params, "bankNameId",0); 
		}else if(params.containsKey("paymentBankNameId")){
			bankId = MapUtils.getIntValue(params, "paymentBankNameId",0); 
		}else if(params.containsKey("lendingBankId")){
			bankId = MapUtils.getIntValue(params, "lendingBankId",0); 
		}else if(params.containsKey("mortgageBankId")){
			bankId = MapUtils.getIntValue(params, "mortgageBankId",0); 
		}else if(params.containsKey("paymentBankId")){
			bankId = MapUtils.getIntValue(params, "paymentBankId",0); 
		}else{
			return RespHelper.setSuccessData(resp, null);
		}

		List<BankDto> bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		if(bankDtos == null){
			bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		}

		RespHelper.setSuccessData(resp, null);
		for (BankDto bankDto : bankDtos) {
			if(bankDto.getId() == bankId){
				RespHelper.setSuccessData(resp, bankDto.getSubBankDtos());
			}
		}
		return resp;
	}


	/**
	 * 获取所有银行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectBankAll") 
	public RespData<BankDto> selectBankAll(){
		RespData<BankDto> resp = new RespData<BankDto>();
		List<BankDto> bankList=commonService.selectBankList();

		RespHelper.setSuccessData(resp, bankList);
		return resp;
	}
	/**
	 * 获取所有支行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSubBankAll") 
	public RespData<SubBankDto> selectSubBankAll(){
		RespData<SubBankDto> resp = new RespData<SubBankDto>();
		List<SubBankDto> subBankList=commonService.selectSubBankList();
		RespHelper.setSuccessData(resp, subBankList);
		return resp;
	}

	/**
	 * 获取所有字典表数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAllDict") 
	public RespData<DictDto> selectAllDict(){
		RespData<DictDto> resp = new RespData<DictDto>();
		try {
			List<DictDto> list = commonService.selectDictList();
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 选择字典
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/choiceDict")
	public RespData<DictDto> choiceDict(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<DictDto> resp = new RespData<DictDto>();
		List<DictDto> list = new ArrayList<DictDto>();
		try {
			String type = MapUtils.getString(params, "type");
			String pcode= MapUtils.getString(params, "pcode");
			commonService.initData();
			list = (List<DictDto>) RedisOperator.get(type);
			if(list == null){
				list = (List<DictDto>) RedisOperator.get(type);
			}

			if(pcode == null){
				List<DictDto> listTemp = new ArrayList<DictDto>();
				for (DictDto dictDto : list) {
					if(dictDto.getPcode()==null){
						listTemp.add(dictDto);
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}else if(pcode.equals("")){
				RespHelper.setSuccessData(resp, list);
			}else{
				List<DictDto> listTemp = new ArrayList<DictDto>();
				for (DictDto dictDto : list) {
					if(StringUtils.isNotEmpty(dictDto.getPcode())){
						if(dictDto.getPcode().equals(pcode)){
							listTemp.add(dictDto);
						}
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 选择字典
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/choiceDictRetureMap")
	public RespData<Map<String,Object>> choiceDictRetureMap(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<Map<String,Object>> resp = new RespData<Map<String,Object>>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			String type = MapUtils.getString(params, "type");
			String pcode= MapUtils.getString(params, "pcode");
			commonService.initData();
			List<DictDto> listData = (List<DictDto>) RedisOperator.get(type);
			if(listData == null){
				listData = (List<DictDto>) RedisOperator.get(type);
			}
			Map<String,Object> tmpMap = null;
			for(DictDto tmp:listData){
				tmpMap = new HashMap<String,Object>();
				tmpMap.put("id", tmp.getCode());
				tmpMap.put("code", tmp.getCode());
				tmpMap.put("pcode", tmp.getPcode());
				tmpMap.put("name", tmp.getName());
				tmpMap.put("type", tmp.getType());
				tmpMap.put("sort", tmp.getSort());
				list.add(tmpMap);
			}

			if(pcode == null){
				List<Map<String,Object>> listTemp = new ArrayList<Map<String,Object>>();
				for (DictDto dictDto : listData) {
					if(dictDto.getPcode()==null){
						tmpMap = new HashMap<String,Object>();
						tmpMap.put("id", dictDto.getCode());
						tmpMap.put("code", dictDto.getCode());
						tmpMap.put("pcode", dictDto.getPcode());
						tmpMap.put("name", dictDto.getName());
						tmpMap.put("type", dictDto.getType());
						tmpMap.put("sort", dictDto.getSort());
						listTemp.add(tmpMap);
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}else if(pcode.equals("")){
				RespHelper.setSuccessData(resp, list);
			}else{
				List<Map<String,Object>> listTemp = new ArrayList<Map<String,Object>>();
				for (DictDto dictDto : listData) {
					if(StringUtils.isNotEmpty(dictDto.getPcode())){
						if(dictDto.getPcode().equals(pcode)){
							tmpMap = new HashMap<String,Object>();
							tmpMap.put("id", dictDto.getCode());
							tmpMap.put("code", dictDto.getCode());
							tmpMap.put("pcode", dictDto.getPcode());
							tmpMap.put("name", dictDto.getName());
							tmpMap.put("type", dictDto.getType());
							tmpMap.put("sort", dictDto.getSort());
							listTemp.add(tmpMap);
						}
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 选择字典
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/choiceDictName")
	public RespData<Map<String, String>> choiceDictName(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		List<DictDto> list = new ArrayList<DictDto>();
		try {
			String type = MapUtils.getString(params, "type");
			String pcode= MapUtils.getString(params, "pcode");
			//选房产所在区域时的城市
			if(params.get("city")!=null){
				pcode = MapUtils.getString(params, "city");
			}else if(params.get("cityCode")!=null){
				pcode = MapUtils.getString(params, "cityCode");
			}else if(params.get("domicileProvinceId")!=null) {
				pcode = MapUtils.getString(params, "domicileProvinceId");
			}
			commonService.initData();
			list = (List<DictDto>) RedisOperator.get(type);
			if(list == null){
				list = (List<DictDto>) RedisOperator.get(type);
			}

			if(pcode == null){
				List<Map<String, String>> listTemp = new ArrayList<Map<String, String>>();
				for (DictDto dictDto : list) {
					if(dictDto.getPcode()==null){
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", dictDto.getName());
						map.put("name", dictDto.getName());
						listTemp.add(map);
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}else if(pcode.equals("")){
				List<Map<String, String>> listTemp = new ArrayList<Map<String, String>>();
				for (DictDto dictDto : list) {
					if(dictDto.getPcode()==null){
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", dictDto.getName());
						map.put("name", dictDto.getName());
						listTemp.add(map);
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}else{
				List<Map<String, String>> listTemp = new ArrayList<Map<String, String>>();
				for (DictDto dictDto : list) {
					if(StringUtils.isNotEmpty(dictDto.getPcode())){
						if(dictDto.getPcode().equals(pcode)){
							Map<String, String> map = new HashMap<String, String>();
							map.put("id", dictDto.getName());
							map.put("name", dictDto.getName());
							listTemp.add(map);
						}
					}
				}
				RespHelper.setSuccessData(resp, listTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 获取所有银行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selBankList") 
	public RespDataObject<List<Map<String,String>>> selBankList(){
		RespDataObject<List<Map<String,String>>> resp = new RespDataObject<List<Map<String,String>>>();
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		List<BankDto> bankList=commonService.selectBankList();
		Map<String,String> map =new HashMap<String, String>();
		for (BankDto bankDto : bankList) {
			map =new HashMap<String, String>();
			map.put("id", bankDto.getId()+"");
			map.put("name", bankDto.getName());
			listMap.add(map);
		}
		RespHelper.setSuccessDataObject(resp, listMap);
		return resp;
	}

	/**
	 * 获取所有银行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selSubBankListById") 
	public RespDataObject<List<Map<String,String>>> selSubBankListById(@RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String,String>>> resp = new RespDataObject<List<Map<String,String>>>();
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		Object obj = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			obj = entry.getValue();
			if (obj != null) {
				break;
			}
		}
		Integer bankId = Integer.parseInt(obj.toString());
		if(null==bankId){
			resp.setMsg("请传银行id");
			return resp;
		}
		List<BankDto> bankDtos = (List<BankDto>) RedisOperator.get("bankList");
		BankDto tmp = null;
		for(BankDto b:bankDtos){
			if(b.getId()==bankId){
				tmp = b;
				break;
			}
		}
		List<SubBankDto> subBankDtos = tmp.getSubBankDtos();
		Map<String,String> maps =new HashMap<String, String>();
		for (SubBankDto subBankDto : subBankDtos) {
			maps =new HashMap<String, String>();
			maps.put("id", subBankDto.getId()+"");
			maps.put("name", subBankDto.getName());
			listMap.add(maps);
		}
		RespHelper.setSuccessDataObject(resp, listMap);
		return resp;
	}

	/**
	 * 银行-管理后台用
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findBybank")
	public RespPageData<BankDto> findBybank(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		RespPageData<BankDto> resp = new RespPageData<BankDto>();
		try {
			List<BankDto>dictlist=commonService.findByBankList(params);
			int count=commonService.findByBankListCount(params);
			resp.setTotal(count);
			resp.setRows(dictlist);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 添加银行
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBank")
	public RespStatus addBank(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespStatus resp =new RespStatus();
		try {
			String ids=params.get("id")+"";
			if(null!=ids && ""!=ids && !"null".equals(ids)){
				commonService.updBank(params);
			}else{
				commonService.addBank(params);
			}
			resp=RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp=RespHelper.setFailRespStatus(resp, "添加银行失败，请稍后重试！");
		}
		return resp;
	}

	/**
	 * 支行-管理后台用
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findBybankSub")
	public RespPageData<SubBankDto> findBybankSub(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		RespPageData<SubBankDto> resp = new RespPageData<SubBankDto>();
		try {
			List<SubBankDto> subBanklist=commonService.findByBankSubList(params);
			int count=commonService.findByBankSubListCount(params);
			resp.setTotal(count);
			resp.setRows(subBanklist);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * 添加支行
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBankSub")
	public RespStatus addBankSub(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespStatus resp =new RespStatus();
		try {
			String ids=params.get("id")+"";
			if(null!=ids && ""!=ids && !"null".equals(ids)){
				commonService.updBankSub(params);
			}else{
				commonService.addBankSub(params);
			}
			resp=RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp=RespHelper.setFailRespStatus(resp, "保存支行失败，请稍后重试！");
		}
		return resp;
	}

	/**
	 * 查询字典-管理后台用
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findBydictList")
	public RespPageData<DictDto> findBydictList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		RespPageData<DictDto> resp = new RespPageData<DictDto>();
		try {
			List<DictDto>dictlist=commonService.findByDictList(params);
			int count=commonService.findByDictListCount(params);
			resp.setTotal(count);
			resp.setRows(dictlist);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 添加字典
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addDict")
	public RespStatus addDict(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespStatus resp =new RespStatus();
		try {
			String ids=params.get("id")+"";
			if(null!=ids && ""!=ids && !"null".equals(ids)){
				commonService.updDict(params);
			}else{
				commonService.addDict(params);
			}
			resp=RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp=RespHelper.setFailRespStatus(resp, "添加字典失败，请稍后重试！");
		}
		return resp;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selYnBankList") 
	public RespDataObject<List<Map<String,Object>>> selYnBankList(){
		RespDataObject<List<Map<String,Object>>> resp = new RespDataObject<List<Map<String,Object>>>();
		List<Map<String,Object>> listMap = commonService.findByYnBankAll();
		RespHelper.setSuccessDataObject(resp,listMap);
		return resp;
	}
	/**
	 * 获取所有云南信托城市
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAdministrationDivide") 
	public RespDataObject<List<Map<String,Object>>> selectAdministrationDivide(){
		RespDataObject<List<Map<String,Object>>> resp = new RespDataObject<List<Map<String,Object>>>();
		List<Map<String, Object>> listMap=commonService.selectAdministrationDivide();
		RespHelper.setSuccessDataObject(resp, listMap);
		return resp;
	}

	/**
	 * 根据名称查询云南信托城市信息
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectAdministrationDivideByName")
	public RespDataObject<Map<String,Object>> selectAdministrationDivideByName(@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		try{
			String name = MapUtils.getString(map,"name");
			if(StringUtil.isBlank(name)){
				RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			Map<String,Object> data = commonService.selectAdministrationDivideByName(map);
			RespHelper.setSuccessDataObject(result, data);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
		}
		return result;
	}
}
