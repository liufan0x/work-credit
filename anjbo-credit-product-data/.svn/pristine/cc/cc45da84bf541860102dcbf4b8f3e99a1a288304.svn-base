package com.anjbo.controller;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ProductFileBaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/credit/product/data/file/base")
public class ProductFileBaseController extends BaseController {
	
	Logger logger = Logger.getLogger(ProductFileBaseController.class);

	@Resource
	private ProductFileBaseService productFileBaseService;

	@ResponseBody
	@RequestMapping("/v/uploadFile")
	public RespStatus uploadFile(HttpServletRequest request, @RequestBody List<Map<String,Object>> list){
		RespStatus result = new RespStatus();
		try{
			UserDto user = getUserDto(request);
			String createUid = "";
			if(null!=user){
				createUid = user.getUid();
			}
			String tblDataName = "";
			String tblName = "";
			String processId = "";
			String tmpTblName = "";
			for (Map<String,Object> m:list){
				m.put("createUid",createUid);
				if(StringUtils.isBlank(tblName)){
					tblName = MapUtils.getString(m,"tblName");
					if(m.containsKey("processId")){
						processId = MapUtils.getString(m,"processId");
					} else if(StringUtils.isNotBlank(tblName)
							&&(tblName.lastIndexOf("_")+1)<tblName.length()){
						String tmp = tblName.substring(0,tblName.lastIndexOf("_")+1);
						processId = tblName.substring(tblName.lastIndexOf("_")+1,tblName.length());
						tblName = tmp;
					}
					tblDataName = tblName+"file";
					tmpTblName = tblName+processId;
				}
				m.put("tblName",tmpTblName);
				m.put("tblDataName",tblDataName);
			}
			if(StringUtils.isBlank(tblName)){
				RespHelper.setFailRespStatus(result,"保存上传文件信息缺少参数!");
				return result;
			}
			productFileBaseService.batchFile(list);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			logger.error("保存上传文件信息异常:",e);
		}
		return result;
	}

	/**
	 *
	 * @param request
	 * @param map(key=tblName:表开头tbl_xx_,key=processId:当前节点,key=orderNo:订单节点)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/listFile")
	public RespDataObject<List<Map<String,Object>>> listFile(HttpServletRequest request, @RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
		try{
			if(StringUtils.isBlank(MapUtils.getString(map,"orderNo"))
					||StringUtils.isBlank(MapUtils.getString(map,"tblName"))){
				RespHelper.setFailRespStatus(result,"查询文件信息缺少参数!");
				return result;
			}
			String tblName = MapUtils.getString(map,"tblName");
			String processId = "";
			if(map.containsKey("processId")){
				processId = MapUtils.getString(map,"processId");
			} else if((tblName.lastIndexOf("_")+1)<tblName.length()){
				String tmp = tblName.substring(0,tblName.lastIndexOf("_")+1);
				processId = tblName.substring(tblName.lastIndexOf("_")+1,tblName.length());
				tblName = tmp;
			}

			String tblDataName = tblName+"file";
			tblName = tblName+processId;
			map.put("tblName",tblName);
			map.put("tblDataName",tblDataName);
			List<Map<String,Object>> list = productFileBaseService.listFile(map);
			RespHelper.setSuccessDataObject(result,list);
		} catch (Exception e){
			RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
			logger.error("查询保存的文件信息异常:",e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/v/deleteFileByIds")
	public RespStatus deleteFileByIds(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		try{
			if(!map.containsKey("ids")
					||!map.containsKey("tblName")){
				RespHelper.setFailRespStatus(result,"删除文件信息缺少参数!");
				return result;
			}
			String tblName = MapUtils.getString(map,"tblName");
			String tblDataName = tblName+"file";
			map.put("tblDataName",tblDataName);
			productFileBaseService.deleteFileById(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			logger.error("删除上传文件信息异常:",e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/v/deleteFileByOrderNoAndTablName")
	public RespStatus deleteFileByOrderNoAndTablName(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		try{
			if(StringUtils.isBlank(MapUtils.getString(map,"orderNo"))
					||StringUtils.isBlank(MapUtils.getString(map,"tblName"))){
				RespHelper.setFailRespStatus(result,"删除文件信息缺少参数!");
				return result;
			}
			String tblName = MapUtils.getString(map,"tblName");
			String tblDataName = tblName+"file";
			map.put("tblDataName",tblDataName);
			if(StringUtils.isNotBlank(MapUtils.getString(map,"processId"))){
				String processId = MapUtils.getString(map,"processId");
				tblName = tblName+processId;
				map.put("tblName",tblName);
			} else {
				map.remove("tblName");
			}
			productFileBaseService.deleteFileByOrderNoAndTblName(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
			logger.error("删除上传文件信息异常:",e);
		}
		return result;
	}
}
