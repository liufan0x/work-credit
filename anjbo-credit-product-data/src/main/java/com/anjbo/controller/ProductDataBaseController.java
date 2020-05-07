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

import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;

@Controller
@RequestMapping("/credit/product/data/base/v")
public class ProductDataBaseController extends BaseController{
	
	@Resource
	private ProductDataBaseService productDataBaseService;
	
	//提供新增、修改、查询方法
	
	//修改方法 需要修改 tbl_xx_data的同时,插入tbl_xx_data_record
	@RequestMapping("save")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,@RequestBody ProductDataDto productDataDto){
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				productDataDto.setCreateUid(user.getUid());
				productDataDto.setUpdateUid(user.getUid());
			}
			productDataDto.setCreateUid(productDataDto.getUpdateUid());
			UserDto u = CommonDataUtil.getUserDtoByUidAndMobile(productDataDto.getUpdateUid());
			if(productDataDto.getAddOrderType()==2){//普通用户提单.处理人设置为渠道经理，如果渠道经理为null，设置为刘俊龙来指派渠道经理
				productDataDto.setPreviousHandlerUid(u.getUid()!=null?u.getUid():productDataDto.getUpdateUid());
				productDataDto.setPreviousHandler(u.getName());
				String channelManagerUid = "";
				if(productDataDto.getData().get("channelManagerUid")==null){
					channelManagerUid = ConfigUtil.getStringValue(Constants.BASE_CM_CHANNELMANAGERUID,ConfigUtil.CONFIG_BASE);
					Map tMap = (Map)productDataDto.getOtherData().get("tbl_cm_list");
					tMap.put("state", "待指派渠道经理");
					tMap.put("processId", "repaymentChannelManager");
				}else{
					channelManagerUid = productDataDto.getData().get("channelManagerUid").toString();
				}
				UserDto channelManager = CommonDataUtil.getUserDtoByUidAndMobile(channelManagerUid);
				productDataDto.setCurrentHandlerUid(channelManagerUid);
				productDataDto.setCurrentHandler(channelManager.getName());
			}else{
				productDataDto.setPreviousHandlerUid(u.getUid()!=null?u.getUid():productDataDto.getUpdateUid());
				productDataDto.setPreviousHandler(u.getName());
				String channelManagerUid = "";
				if(productDataDto.getData().get("channelManagerUid")==null){//没有渠道经理，设置提单人为当前处理人
					channelManagerUid = productDataDto.getPreviousHandlerUid();
				}else{
					channelManagerUid = productDataDto.getData().get("channelManagerUid").toString();
				}
				UserDto channelManager = CommonDataUtil.getUserDtoByUidAndMobile(channelManagerUid);
				productDataDto.setCurrentHandlerUid(channelManager.getUid());
				productDataDto.setCurrentHandler(channelManager.getName());
			}
			//去掉data中的额外字段additional
		    Map data = productDataDto.getData();
		    if(MapUtils.isEmpty(data)){
		    	RespHelper.setFailRespStatus(resp, "保存数据不能为空");
				return resp;
		    }
			String result = productDataBaseService.updateProductDataBase(productDataDto);
			if("error".equals(result)){
				RespHelper.setFailRespStatus(resp, "请填写相关信息");
				return resp;
			}
			RespHelper.setSuccessRespStatus(resp);
			return resp;
		} catch (Exception e) {
			logger.error("更新"+productDataDto.getTblName()+"异常",e);
			RespHelper.setFailRespStatus(resp, "更新"+productDataDto.getTblName()+"异常");
			return resp;
		}
	}
	
	//查询方法 查询tbl_xx_data  通过orderNo 和 tblName 来查询
	@RequestMapping("select")
	@ResponseBody
	public RespDataObject<Map<String,Object>> select(HttpServletRequest request,@RequestBody ProductDataDto productDataDto){
		RespDataObject<Map<String,Object>> respDataObject = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> data = productDataBaseService.selectProductDataBase(productDataDto);
			RespHelper.setSuccessDataObject(respDataObject, data);
			return respDataObject;
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(respDataObject, null, "");
			return respDataObject;
		}
	}
	
	//查询方法 查询tbl_xx_data  通过orderNo 和 tblName 来查询
	@RequestMapping("selectList")
	@ResponseBody
	public RespDataObject<List<Map<String,Object>>> selectList(HttpServletRequest request,@RequestBody ProductDataDto productDataDto){
		RespDataObject<List<Map<String,Object>>> respDataObject = new RespDataObject<List<Map<String,Object>>>();
		try {
			List<Map<String,Object>> data = productDataBaseService.selectProductDataBaseList(productDataDto);
			RespHelper.setSuccessDataObject(respDataObject, data);
			return respDataObject;
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(respDataObject, null, "");
			return respDataObject;
		}
	}
	
	//查询流水方法 查询tbl_xx_flow，
}
