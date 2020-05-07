package com.anjbo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.contract.OrderContractCustomerBorrowerDto;
import com.anjbo.bean.contract.OrderContractDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderContractDataService;
import com.anjbo.utils.PinYin4JUtil;
import com.anjbo.utils.StringUtil;

@Controller
@RequestMapping("/credit/common/contract/data/v/")
public class OrderContractDataController extends BaseController {

	@Resource
	private OrderContractDataService orderContractDataService;
	
	/**
	 * 更新合同
	 * @param request
	 * @param orderContractDataDto
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,@RequestBody OrderContractDataDto orderContractDataDto){
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				orderContractDataDto.setCreateUid(user.getUid());
				orderContractDataDto.setUpdateUid(user.getUid());
			}
			Map data = orderContractDataDto.getData();
			if(MapUtils.isEmpty(data)){
				RespHelper.setFailRespStatus(resp, "保存数据不能为空");
				return resp;
			}
			if(StringUtil.isBlank(orderContractDataDto.getTblName())){
				RespHelper.setFailRespStatus(resp, "表名不能为空");
				return resp;
			}
			orderContractDataService.updateOrderContractData(orderContractDataDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, "保存失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询合同内容
	 * @param request
	 * @param orderContractDataDto
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<OrderContractDataDto> query(HttpServletRequest request,
			@RequestBody OrderContractDataDto orderContractDataDto) {
		RespDataObject<OrderContractDataDto> resp = new RespDataObject<OrderContractDataDto>();
		try {
			if(!"tbl_contract_trust_loan".equals(orderContractDataDto.getTblName())){
				orderContractDataDto.setTblName("tbl_contract_bussiness_application");
			}
			orderContractDataDto = orderContractDataService.selectOrderContractDataDto(orderContractDataDto);
			RespHelper.setSuccessDataObject(resp, orderContractDataDto);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询订单合同信息失败:"+orderContractDataDto.getTblName());
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 重置合同
	 * @param request
	 * @param orderContractDataDto
	 * @return
	 */
	@RequestMapping("reset")
	@ResponseBody
	public RespStatus reset(HttpServletRequest request,@RequestBody OrderContractDataDto orderContractDataDto){
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				orderContractDataDto.setCreateUid(user.getUid());
				orderContractDataDto.setUpdateUid(user.getUid());
			}
			orderContractDataService.insertOrderContractData(orderContractDataDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, "重置失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询共同借款人集合
	 * @return
	 */
	@RequestMapping("queryContractCustomerBorrow")
	@ResponseBody
	public RespData<OrderContractCustomerBorrowerDto> queryContractCustomerBorrow(HttpServletRequest request,@RequestBody OrderContractDataDto orderContractDataDto){
		RespData<OrderContractCustomerBorrowerDto> resp = new RespData<OrderContractCustomerBorrowerDto>();
		try {
			List<OrderContractCustomerBorrowerDto> customerBorrower = orderContractDataService.queryContractCustomerBorrow(orderContractDataDto);
			RespHelper.setSuccessData(resp, customerBorrower);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询合同编号
	 * @return
	 */
	@RequestMapping("queryContractNo")
	@ResponseBody
	public RespDataObject<String> queryContractNo(HttpServletRequest request,@RequestBody Map<String,Object> data){
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
			String py = PinYin4JUtil.getFirstSpell(MapUtils.getString(data, "creditBorrowerName",""));
			String py2 = PinYin4JUtil.getFirstSpell(MapUtils.getString(data, "customerName",""));
			py = StringUtil.isNotBlank(py)?py:py2;
			py = py.toUpperCase();
			String date = MapUtils.getString(data, "dateOfSigning");
			date = date.substring(date.length()-10, date.length());
			String dateDay = date.substring(date.length()-2, date.length());
			String dateMonth = date.substring(date.length()-5, date.length()-3);
			String contractNo = py+dateMonth+dateDay;
			RespHelper.setSuccessDataObject(resp, contractNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
