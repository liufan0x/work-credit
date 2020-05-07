/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.contract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.coyote.http11.filters.BufferedInputFilter;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.contract.ContractListRecordDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.contract.IContractListRecordController;
import com.anjbo.service.contract.ContractListRecordService;
import com.itextpdf.io.codec.Base64.OutputStream;
import com.itextpdf.kernel.pdf.annot.Pdf3DAnnotation;

import springfox.documentation.builders.RequestHandlerSelectors;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-09-20 20:08:34
 * @version 1.0
 */
@RestController
public class ContractListRecordController extends BaseController implements IContractListRecordController{

	@Resource private ContractListRecordService contractListRecordService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<ContractListRecordDto> page(@RequestBody ContractListRecordDto dto){
		RespPageData<ContractListRecordDto> resp = new RespPageData<ContractListRecordDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(contractListRecordService.search(dto));
			resp.setTotal(contractListRecordService.count(dto));
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
	public RespData<ContractListRecordDto> search(@RequestBody ContractListRecordDto dto){ 
		RespData<ContractListRecordDto> resp = new RespData<ContractListRecordDto>();
		try {
			return RespHelper.setSuccessData(resp, contractListRecordService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<ContractListRecordDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ContractListRecordDto> find(@RequestBody ContractListRecordDto dto){ 
		RespDataObject<ContractListRecordDto> resp = new RespDataObject<ContractListRecordDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, contractListRecordService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ContractListRecordDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ContractListRecordDto> add(@RequestBody ContractListRecordDto dto){ 
		RespDataObject<ContractListRecordDto> resp = new RespDataObject<ContractListRecordDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, contractListRecordService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ContractListRecordDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody ContractListRecordDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			contractListRecordService.update(dto);
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
	public RespStatus delete(@RequestBody ContractListRecordDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			contractListRecordService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
	@Override
	public RespDataObject<String> pathReturn(@RequestBody ContractListRecordDto dto) {
		// TODO Auto-generated method stub
		RespDataObject<String> resp = new RespDataObject<String>();
		String fileName = "C:\\Users\\Administrator\\Desktop";
		String urls  = dto.getFilePath();
		FileOutputStream out = null;
		InputStream ins = null;

		FileInputStream finputStream=null;
		ZipOutputStream zipOutputStream=null;
		
		int type = dto.getType();
		if (type==3) {     //文件下载
			String[] arr = urls.split(",");
			try {
				if (arr.length==1) {                      //只有一个pdf文件就不用打包
	    		    URL url = new URL(arr[0]);
	    		    String urlNames = arr[0].substring(arr[0].lastIndexOf(".")+1);
	    		    if(!"pdf".equals(urlNames)) {
	    		    	System.err.println("非pdf文件");
	    		    }
			        URLConnection con = url.openConnection();
	                out = new FileOutputStream(fileName+"/dfgdf."+urlNames);
	                ins = con.getInputStream();
	                byte[] b = new byte[ins.available()+1000];
	                int j=0;
	                while((j=ins.read(b))!=-1){
	                out.write(b, 0, j);
	                }
			 }
				File zipFile = new File(fileName+"/zipFile"+".zip");
		    	out = new FileOutputStream(zipFile);
		    	zipOutputStream = new ZipOutputStream(out);
		    	ZipEntry zipEntry=null;
		    	for (int i = 0; i < arr.length; i++) {
		    	/*	File files = new File(arr[i]);
		    		finputStream =new  FileInputStream(files);*/
		    		  URL url = new URL(arr[i]);
		    		  URLConnection con = url.openConnection();
		    		  ins = con.getInputStream();
		    		zipEntry = new ZipEntry(arr[i]);
		    		zipOutputStream.putNextEntry(zipEntry);
		    		int len;
		    		byte [] buffer = new byte[ins.available()+1000];
		    		while((len=ins.read(buffer))>0) {
		    			zipOutputStream.write(buffer,0,len);
		    		}
				}
		        System.err.println("已完结");	
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				   try {
					zipOutputStream.closeEntry();
				    zipOutputStream.close();
				    
					 ins.close();
					 out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			      
			}
			
		}
		return null;
	}
	
	


}