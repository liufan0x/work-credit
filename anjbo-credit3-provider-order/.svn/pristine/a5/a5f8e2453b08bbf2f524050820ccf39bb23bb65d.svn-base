/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.contract.FieldInputDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.order.IBusinfoController;
import com.anjbo.dao.order.BusinfoTypeMapper;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.BusinfoService;
import com.anjbo.utils.SingleUtils;




/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
@RestController
public class BusinfoController extends BaseController implements IBusinfoController{
	private Logger log = Logger.getLogger(getClass());
	@Resource private BusinfoService businfoService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseListService baseListService;
	
	@Resource private BusinfoTypeMapper businfoTypeMapper;
	 
	/* public File zipFile;
	 public BusinfoController(String pathName) {   
		 zipFile  = new File(pathName);     
	    } */ 

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BusinfoDto> page(@RequestBody BusinfoDto dto){
		RespPageData<BusinfoDto> resp = new RespPageData<BusinfoDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(businfoService.search(dto));
			resp.setTotal(businfoService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<BusinfoDto> search(@RequestBody BusinfoDto dto){ 
		RespData<BusinfoDto> resp = new RespData<BusinfoDto>();
		try {
			return RespHelper.setSuccessData(resp, businfoService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BusinfoDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BusinfoDto> find(@RequestBody BusinfoDto dto){ 
		RespDataObject<BusinfoDto> resp = new RespDataObject<BusinfoDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, businfoService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BusinfoDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BusinfoDto> add(@RequestBody BusinfoDto dto){ 
		RespDataObject<BusinfoDto> resp = new RespDataObject<BusinfoDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, businfoService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BusinfoDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BusinfoDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			businfoService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody BusinfoDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			businfoService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 面签影像资料校验
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public RespStatus faceBusinfoCheck(@RequestBody BaseListDto dto){
		RespStatus resp = new RespStatus();
		dto = baseListService.find(dto);
		if(dto.getAuditSort()==2||businfoService.faceBusinfoCheck(dto.getOrderNo(), dto.getProductCode(),dto.getAuditSort())){
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		}else{
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("面签资料不完整");
		}
		return resp;
	}

	/**
	   *影像资料一键下载
	   * */
	/*@SuppressWarnings("unchecked")
		@Override
		public RespDataObject<String> getBusinfoAndTypeNames(@RequestBody  Map<String, Object> map) {	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			 RespDataObject<String> osDataObject=SingleUtils.getRestTemplate(100000).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), RespDataObject.class);
			//return SingleUtils.getRestTemplate(120).postForObject("http://localhost:8082/" + "anjbo-fs/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), RespDataObject.class);
			//return SingleUtils.getRestTemplate(100000).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), RespDataObject.class);
		    System.out.println("已从下载处跳出");
			 return osDataObject;
		}*/
	
	/**
	   *影像资料一键下载
	   * */
	@SuppressWarnings("unchecked")
		@Override
		public RespDataObject<String> getBusinfoAndTypeNames(@RequestBody  Map<String, Object> map) {	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// Map<String,Object> osDataObject=(Map<String, Object>) SingleUtils.getRestTemplate(120).postForObject("http://localhost:8082/" + "anjbo-fs/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), Map.class);
	        // RespDataObject<String> osDataObject2=SingleUtils.getRestTemplate(120).postForObject("http://localhost:8082/" + "anjbo-fs/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), RespDataObject.class);
			RespDataObject<String> osDataObject2=SingleUtils.getRestTemplate(120).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), RespDataObject.class);
			/* Map<String,Object> osDataObject=(Map<String, Object>) SingleUtils.getRestTemplate(120).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/busin/businfoAndXi", SingleUtils.getHttpEntity(map,headers), Map.class);
			System.out.println("回来了一次");
			 RespDataObject<String> osDataObject2=SingleUtils.getRestTemplate(120).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/busin/businfoAndXi2", SingleUtils.getHttpEntity(osDataObject,headers), RespDataObject.class);*/
			 System.out.println("已从下载处跳出");
			 return osDataObject2;
		}
		
		    
}