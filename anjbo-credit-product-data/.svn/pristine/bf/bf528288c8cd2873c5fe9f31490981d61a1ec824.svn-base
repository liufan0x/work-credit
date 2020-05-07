package com.anjbo.controller;

import com.anjbo.bean.product.data.ProductBusinfoDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.FsService;
import com.anjbo.service.ProductBusinfoBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.ValidHelper;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;
@Controller
@RequestMapping("/credit/product/data/businfo/base")
public class ProductBusinfoBaseController extends BaseController {
	
	Logger log =Logger.getLogger(ProductBusinfoBaseController.class);
	
	@Resource
	private ProductBusinfoBaseService productBusinfoBaseService;
	
	@Resource
	private ProductListBaseService productListBaseService;
	
	@Resource
	private FsService fsService;
	
	@RequestMapping("select")
	@ResponseBody
	public RespDataObject<Map<String,Object>> select(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			UserDto user = getUserDto(request);
			if(user!=null){
				map.put("uid", user.getUid());
			}
			List<Map<String,Object>> data = productBusinfoBaseService.selectProductBusinfoBase(map);
			Map<String,Object> m = new HashMap<String, Object>();
			//设置是否要操作,是否可删除
			//查询订单列表节点
			String tblName = MapUtils.getString(map, "tblName");
			ProductDataDto productDataDto = new ProductDataDto();
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
			productDataDto.setOrderNo(MapUtils.getString(map, "orderNo"));
			productDataDto.setTblDataName(tblDataName);
			Map<String,Object> maps = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
			String createUid = MapUtils.getString(maps, "createUid");
			String channelManagerUid = MapUtils.getString(maps, "channelManagerUid");
			String uid = MapUtils.getString(map, "uid");
			
			String cityCode = MapUtils.getString(maps, "cityCode");
			String productCode = MapUtils.getString(maps, "productCode");
			int productId = 0;
			if(StringUtils.isNotBlank(cityCode)&&StringUtils.isNotBlank(productCode)){
				productId = Integer.parseInt(cityCode+productCode);
			}
			boolean flag = true;
			if(!tblName.equals("tbl_sm_")) {
				flag = compareProcessAround(productId, "auditSuccess", MapUtils.getString(maps, "processId"));
			} else {
				String processId = MapUtils.getString(maps,"processId");
				//已签约影像资料有渠道经理或拓展经理维护
				if(map.containsKey("source")
						&&"maintain".equals(MapUtils.getString(map,"source"))
						&&(Enums.RoleEnum.EXPANDMANAGER.getName().equals(user.getRoleName())
							||Enums.RoleEnum.CHANNEL_MANAGER.getName().equals(user.getRoleName()))){
					flag = false;
				}else if("agencyWaitSign".equals(processId)||"agencyWaitConfirm".equals(processId)) {
					flag = false;
				}
			}
			if(tblName.equals("tbl_sm_")&&!flag) {
				m.put("operate", true);
			} else if(
					((StringUtils.isNotBlank(createUid)&&createUid.equals(uid))
					||(StringUtils.isNotBlank(channelManagerUid)&&uid.equals(channelManagerUid)))
					&&!flag){
				m.put("operate", true);
			}else{
				m.put("operate", false);
			}

			m.put("businfo", data);
			RespHelper.setSuccessDataObject(resp, m);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, "查询订单影像资料异常");
		}
		return resp;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,@RequestBody ProductBusinfoDto productBusinfoDto){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				productBusinfoDto.setCreateUid(user.getUid());
			}
			String tblName = productBusinfoDto.getTblName();
			String processId = "";
			String source = "";
			if(tblName.contains("tbl_sm")) {
				if(tblName.indexOf("&")>1
						&&(tblName.length()-tblName.indexOf("&"))>1){
					processId = tblName.substring(tblName.lastIndexOf("_") + 1, tblName.indexOf("&"));
					source = tblName.substring(tblName.indexOf("&")+1,tblName.length());
				} else {
					processId = tblName.substring(tblName.lastIndexOf("_") + 1, tblName.length());
				}
				tblName = tblName.substring(0,tblName.lastIndexOf("_")+1);
			}
			ProductDataDto productDataDto = new ProductDataDto();
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
			productDataDto.setOrderNo(productBusinfoDto.getOrderNo());
			productDataDto.setTblDataName(tblDataName);
			Map<String,Object> maps = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
			String productCode = MapUtils.getString(maps, "productCode");
			boolean flag = false;
			//机构后台上传影像资料校验是否是当前节点操作
			if((StringUtils.isBlank(source)
					||"undefined".equals(source))
					&&productBusinfoDto.getTblName().contains("tbl_sm")
					&&!processId.equals(MapUtils.getString(maps,"processId"))){
				RespHelper.setFailRespStatus(respStatus,"当前流程已被操作,不能执行上传操作");
				return respStatus;
			}
			if("10000".equals(productCode)){//云按揭
				String cityCode = MapUtils.getString(maps, "cityCode");
				int productId = Integer.parseInt(cityCode+productCode);
				flag = compareProcessAround(productId,"managerAudit",MapUtils.getString(maps, "processId"));
			}
			int i = productBusinfoBaseService.insertProductBusinfoBase(productBusinfoDto,flag);
			if(i==0&&flag){
				RespHelper.setFailRespStatus(respStatus, "同步建行影像资料失败");
			}else{
				RespHelper.setSuccessRespStatus(respStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "录入订单影像资料异常");
		}
		return respStatus;
	}
	
	/**
	 * 上传照片到临时地址
	 * 
	 * @param file
	 *            要上传的图片文件列表
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/uploadPhoto")
	@ResponseBody
	public RespDataObject<Map> uploadPhoto(HttpServletRequest request, @RequestParam MultipartFile[] file,
			@RequestParam("infoType") String infoType, @RequestParam("orderNo") String orderNo,@RequestParam("index") int index,@RequestParam("productCode") String productCode) {
		RespDataObject<Map> resp = new RespDataObject<Map>();
		Map retMap = new HashMap();
		// 校验参数
		if (file == null || StringUtil.isEmpty(infoType)) {
			resp.setCode("ERROR");
			resp.setMsg("参数异常");
			return resp;
		}
		// 根据资料类型名称获取类型ID
		//Map infoTypeMap = orderBusinfoService.getInfoTypeByName(infoType);
		int infoTypeId = Integer.parseInt(infoType);
		List<String> listImagePath = new ArrayList<String>();
		Map<String,Object> map = new HashMap<String, Object>();
		// 上传到临时位置
		// 上传文件并得到url列表
		String imagePath = "";
		Map<String,Object> imgMap = null;
		Integer isPs;
		for (MultipartFile mf : file) {
			if (!mf.isEmpty()) {
				try {
					Map<String,Object> params = getOrderBaseInfo(orderNo);
					productCode = productCode!=null?productCode:MapUtils.getString(params, "productCode");
					//old
					//imagePath = fsService.uploadImageByFile(mf);
					imgMap = fsService.uploadImageByFileMap(mf);
					if(null==imgMap||imgMap.size()<=0){
						RespHelper.setFailDataObject(resp,Collections.EMPTY_MAP,RespStatusEnum.FAIL.getMsg());
						return resp;
					}
					imagePath = MapUtils.getString(imgMap,"url");
					isPs = MapUtils.getInteger(imgMap,"isPs");
					if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)){
						Map<String,Object> rMap = new HashMap<String, Object>();
						rMap.put("infoType", infoType);
						rMap.put("orderNo", orderNo);
						rMap.put("index", index);
						rMap.put("imagePath", imagePath);
						rMap.put("isPs", isPs);
						Map<String,Object> data = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/businfo/v/uploadPhoto", rMap);
						RespHelper.setSuccessDataObject(resp, MapUtils.getMap(data, "data"));
						return resp;
					}
					map.put("img", imagePath);
					boolean flag = false; 
					if (!StringUtil.isEmpty(imagePath)) {
						// 存储到数据库
						ProductBusinfoDto productBusinfoDto = new ProductBusinfoDto();
						productBusinfoDto.setCreateTime(new Date());
						productBusinfoDto.setUrls(imagePath);
						productBusinfoDto.setCreateUid(getUserDto(request).getUid());
						productBusinfoDto.setTypeId(infoTypeId);
						productBusinfoDto.setIndex(index);
						productBusinfoDto.setOrderNo(orderNo);
						productBusinfoDto.setIsPs(isPs);
						String tblName = productBusinfoDto.getTblName();
						if("10000".equals(productCode)){//云按揭
							tblName = "tbl_cm_";
							ProductDataDto productDataDto = new ProductDataDto();
							String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
							productDataDto.setOrderNo(productBusinfoDto.getOrderNo());
							productDataDto.setTblDataName(tblDataName);
							productBusinfoDto.setTblName(tblName);
							Map<String,Object> maps = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
							if("10000".equals(productCode)){//云按揭
								String cityCode = MapUtils.getString(maps, "cityCode");
								int productId = Integer.parseInt(cityCode+productCode);
								flag = compareProcessAround(productId,"managerAudit",MapUtils.getString(maps, "processId"));
							}
						}
						int id = productBusinfoBaseService.insertProductBusinfoBase(productBusinfoDto,flag);
						if(id==0){
							RespHelper.setFailDataObject(resp, null, "同步建行影像资料失败");
							return resp;
						}
						map.put("id", id);
					}
				}catch (IOException e) {
					log.error("uploadPhoto   上传图片出错-" + e.getMessage());
					listImagePath.add("");
					resp.setMsg("上传图片失败");
				} catch (Exception e){
					log.error("uploadPhoto   上传图片出错-" + e.getMessage());
					listImagePath.add("");
					resp.setMsg("上传图片失败");
				}
			}
		}
		retMap.put("imgData", map);
		RespHelper.setSuccessDataObject(resp, retMap);
		return resp;
	}
	
	@RequestMapping("move")
	@ResponseBody
	public RespStatus move(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			if(!ValidHelper.isNotEmptyByKeys(map, "businfoIds","toTypeId")){
				respStatus.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return respStatus;
			}
			String tblName = MapUtils.getString(map,"tblName");
			String source = MapUtils.getString(map,"source");
			if((StringUtils.isBlank(source)
					||"undefined".equals(source))
					&&tblName.contains("tbl_sm")){
				boolean isCan = isCanUpOrDelOrMove(map);
				if(!isCan){
					RespHelper.setFailRespStatus(respStatus,"当前节点已被操作,不允许执行移动操作!");
					return respStatus;
				}
			}

			Map<String,Object> params = getOrderBaseInfo(MapUtils.getString(map, "orderNo"));
			String productCode = map.get("productCode")!=null?MapUtils.getString(map, "productCode"):MapUtils.getString(params, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)){
				RespStatus respDa = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/businfo/v/move", map, Map.class);
				return respDa;
			}
			productBusinfoBaseService.move(map);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "移动订单影像资料异常");
		}
		return respStatus;
	}
	
	@RequestMapping("deleteIds")
	@ResponseBody
	public RespDataObject<Map<String, Object>> deleteIds(HttpServletRequest request,@RequestBody Map<String,Object> map){
		log.info(("删除订单影像资料;"+map.toString()));
		RespDataObject<Map<String, Object>> respStatus = new RespDataObject<Map<String, Object>>();
		try {
			if(!map.containsKey("businfoIds")&&!map.containsKey("ids")){
				RespHelper.setFailRespStatus(respStatus,"缺少参数ids或者businfoIds!");
				return respStatus;
			}
			if(map.get("productCode")!=null&&"04".equals(MapUtils.getString(map, "productCode"))){
				map.put("tblName", "tbl_cm_");
			}
			String tblName = MapUtils.getString(map,"tblName");
			String source = MapUtils.getString(map,"source");
			if((StringUtils.isBlank(source)
					||"undefined".equals(source))
					&&tblName!=null&&tblName.contains("tbl_sm")){
				boolean isCan = isCanUpOrDelOrMove(map);
				if(!isCan){
					RespHelper.setFailRespStatus(respStatus,"当前节点已被操作,不允许执行删除操作!");
					return respStatus;
				}
			}

			if(map.containsKey("ids")){
				String ids = MapUtils.getString(map, "ids");
				List<String> list = new ArrayList<String>();
				String[] idArr = ids.split(",");
				for (String string : idArr) {
					list.add(string);
				}
				map.put("businfoIds", list);
			}
			Map<String,Object> params = getOrderBaseInfo(MapUtils.getString(map, "orderNo"));
			String productCode = map.get("productCode")!=null?MapUtils.getString(map, "productCode"):MapUtils.getString(params, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)){
				Map<String,Object> data = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/businfo/v/deleteIds", map);
				if("SUCCESS".equals(MapUtils.getString(data, "code"))){
					RespHelper.setSuccessDataObject(respStatus, MapUtils.getMap(data, "data"));
					return respStatus;
				}else{
					RespHelper.setFailRespStatus(respStatus, MapUtils.getString(data, "msg"));
					return respStatus;
				}
			}

			productBusinfoBaseService.deleteImgByIds(map);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "删除订单影像资料异常");
		}
		return respStatus;
	}
	
	@ResponseBody 
	@RequestMapping(value="/lookOver")
	public RespDataObject<Map<String, Object>> lookOver(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> respDataObject = RespHelper.failDataObject(null);
		if(!ValidHelper.isNotEmptyByKeys(map, "typeId","orderNo")){
			respDataObject.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return respDataObject;
		}
		try{
			Map<String,Object> params = getOrderBaseInfo(MapUtils.getString(map, "orderNo"));
			String productCode = map.get("productCode")!=null?MapUtils.getString(map, "productCode"):MapUtils.getString(params, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)){//债务置换贷款
				Map<String,Object> data = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/businfo/v/lookOver", map);
				RespHelper.setSuccessDataObject(respDataObject, MapUtils.getMap(data, "data"));
				return respDataObject;
			}else{
				if("10000".equals(productCode)){
					map.put("tblName", "tbl_cm_");
					List<Map<String,Object>> list = productBusinfoBaseService.selectProductBusinfoBase(map);
					Map<String,Object> m = new HashMap<String, Object>();
					m.put("listMap", list);
					RespHelper.setSuccessDataObject(respDataObject, m);
				}
			}
		}catch (Exception e) {
			log.error("lookOver Exception  ->",e);
			RespHelper.setFailDataObject(respDataObject, null, "查看影像资料失败");
		}
		return respDataObject;
	}
	
	/**
	 * 机构校验当前是否能上传删除移动
	 * @param map
	 * @return
	 */
	public boolean isCanUpOrDelOrMove(Map<String,Object> map){
		String processId = MapUtils.getString(map,"processId");
		ProductDataDto productDataDto = new ProductDataDto();
		String tblDataName = MapUtils.getString(map,"tblName")+"list";
		productDataDto.setOrderNo(MapUtils.getString(map,"orderNo"));
		productDataDto.setTblDataName(tblDataName);
		Map<String,Object> maps = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
		if(!processId.equals(MapUtils.getString(maps,"processId"))){
			return false;
		}
		return true;
	}

	/**
	 * 校验影像资料必备的是否有全部上传
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/verificationImgage")
	public RespDataObject<Boolean> verificationImgage(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Boolean> result = new RespDataObject<Boolean>();
		try {
			RespHelper.setSuccessDataObject(result,productBusinfoBaseService.verificationImgage(map));
		} catch (Exception e){
			RespHelper.setFailDataObject(result,false,"校验影像资料必传信息异常");
			log.error("校验影像资料必传信息异常:",e);
		}
		return result;
	}
}
