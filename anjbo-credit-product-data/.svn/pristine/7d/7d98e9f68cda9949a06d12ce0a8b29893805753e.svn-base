package com.anjbo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ProductFlowBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.utils.CommonDataUtil;

@Controller
@RequestMapping("/credit/product/data/flow/base/v")
public class ProductFlowBaseController extends BaseController{
	
	private static final Logger log = Logger.getLogger(ProductFlowBaseController.class);
	
	@Resource
	private ProductFlowBaseService productFlowBaseService;
	@Resource
	private ProductListBaseService productListBaseService;
	
	//提供新增、修改、查询方法
	
	//修改方法 需要修改 tbl_xx_data的同时,插入tbl_xx_data_record
	@RequestMapping("save")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
			}
			String tblName = MapUtils.getString(map, "tblName");
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_flow";
			map.put("tblDataName", tblDataName);
			productFlowBaseService.insertProductFlowBase(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "更新"+MapUtils.getString(map, "tblName")+"异常");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	
	//查询方法 查询tbl_xx_data  通过orderNo 和 tblName 来查询
	@RequestMapping("list")
	@ResponseBody
	public RespDataObject<List<Map<String,Object>>> select(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String,Object>>> respDataObject = new RespDataObject<List<Map<String,Object>>>();
		try {
			String tblName = MapUtils.getString(map, "tblName");
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_flow";
			map.put("tblDataName", tblDataName);
			List<Map<String,Object>> list = productFlowBaseService.selectProductFlowBaseList(map);
			//查询productId
			ProductDataDto productDataDto = new  ProductDataDto();
			productDataDto.setOrderNo(MapUtils.getString(map, "orderNo"));
			productDataDto.setTblDataName(tblName.substring(0, tblName.indexOf("_",5))+"_list");
			Map<String,Object> maps = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
			if(CollectionUtils.isNotEmpty(list)&&MapUtils.isNotEmpty(maps)){
				int productId = 0;
				if(tblName.contains("tbl_sm")
						&&("0".equals(MapUtils.getString(map,"cityCode"))
						||StringUtils.isNotBlank(MapUtils.getString(map,"cityCode")))){
					productId = Integer.parseInt(MapUtils.getString(map, "cityCode")+MapUtils.getString(maps, "productCode"));
				} else {
					productId = Integer.parseInt(MapUtils.getString(maps, "cityCode")+MapUtils.getString(maps, "productCode"));
				}
				
				List<ProductProcessDto> productProcessDtoList = getProductProcessDto(productId);
//				List<UserDto> userList = getUserDtoList();
				for (Map<String, Object> listMap : list) {
					
					for (ProductProcessDto productProcessDto : productProcessDtoList) {
						if(MapUtils.getString(listMap, "currentProcessId").equals(productProcessDto.getProcessId())){
							listMap.put("currentProcessName", productProcessDto.getProcessName());
						}
						if(productProcessDto.getProcessId().equals(MapUtils.getString(listMap, "nextProcessId"))){
							listMap.put("nextProcessName", productProcessDto.getProcessName());
						}
					}
					
//					for (UserDto userDto : userList) {
//						if(userDto.getUid().equals(MapUtils.getString(listMap, "handleUid"))){
							listMap.put("handleName", CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(listMap, "handleUid")).getName());
//							break;
//						}
//					}
					
					if(MapUtils.getString(listMap, "currentProcessName").contains("关闭")||MapUtils.getString(listMap, "currentProcessName").contains("失败")){
						listMap.put("color", "red");
					}
				}
			}
			RespHelper.setSuccessDataObject(respDataObject, list);
			return respDataObject;
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(respDataObject, null, "");
			return respDataObject;
		}
	}
	
	/**
	 * 查询产品节点
	 * @param map
	 * cityCode
	 * productCode
	 * @return
	 */
	@RequestMapping("allList")
	@ResponseBody
	public RespData<ProductProcessDto> selectList(@RequestBody Map<String,Object> map){
		try {
			int productId = NumberUtils.toInt(MapUtils.getString(map, "cityCode")+MapUtils.getString(map, "productCode"));
			List<ProductProcessDto> list = getProductProcessDto(productId);
			return RespHelper.setSuccessData(new RespData<ProductProcessDto>(), list);
		} catch (Exception e) {
			log.info("查询"+MapUtils.getString(map, "cityCode")+MapUtils.getString(map, "productCode")+"节点异常",e);
			return RespHelper.setFailData(new RespData<ProductProcessDto>(), null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 可重新打开的流水
	 * @param map
	 * @return
	 */
	@RequestMapping("reopenFlow")
	@ResponseBody
	public RespData<Map<String,Object>> selectProductFlowBaseAll(@RequestBody Map<String,Object> map){
		try {
			List<Map<String,Object>> list = productFlowBaseService.selectProductFlowBaseAll();
			return RespHelper.setSuccessData(new RespData<Map<String,Object>>(), list);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.setFailData(new RespData<Map<String,Object>>(), null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
}
