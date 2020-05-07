package com.anjbo.service.huarong.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.huarong.FileApplyMapper;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.service.huarong.FileApplyService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.huarong.FromAnjboToHrKeyValue;
import com.anjbo.utils.huarong.JsonUtils;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;

/**
 * 
 * @ClassName: FileApplyServiceImpl
 * @Description: TODO(申请数据发送附件接口)
 * @author xufx
 * @date 2017年8月16日 下午2:03:28
 *
 */
@Service
public class FileApplyServiceImpl implements FileApplyService {

	@Resource
	private UtilBorrowIdService utilBorrowIdService;
	@Resource
	private FileApplyMapper fileApplyMapper;
	@Resource
	private LcAppointMapper lcAppointMapper;

	Logger log = Logger.getLogger(FileApplyServiceImpl.class);

	@Override
	public RespDataObject<String> fileApplySend(
			List<Map<String, Object>> parmlist) throws Exception {
		log.info("附件接口接收参数:"+parmlist+"Map0:"+parmlist.get(0));
		// Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		JSONArray jArray = JSONArray.fromObject(parmlist);
		// 此处等待压缩包，一次传递
		RespDataObject<String> resp = new RespDataObject<String>();
		Map keyValueMap = FromAnjboToHrKeyValue.KeyValue();
		
		String loanCooprCode="";
		String orderNo = "";
		
		// 筛选出已处理过的文件列表
		List<String> lstSuccess = new ArrayList<String>();
		for (Object object : jArray) {
			if (object != null) {
				JSONObject json = JSONObject.fromObject(object);
				lstSuccess.add(json.getString("orderNo"));
				orderNo=json.getString("orderNo");//为后面查询申请信息中的助贷商编码提供订单编号
				//loanCooprCode=json.getString("loanCooprCode");
				break;
			}
		}
		if(null!=lstSuccess && lstSuccess.size()>0){
			lstSuccess = fileApplyMapper.searchFilePaths(lstSuccess);
		}		

		Date date = new Date();
		long l = date.getTime();
		l = l / 1000;

		String filepath = "";
		String docIo = "";
		String sub_str = "";
		String typeId = "";
		ReturnParam param = null;
		String errorPictureUrl = "";
		String index = "";
		
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();// 上传成功文件list

		List<Map<String, Object>> PDFfileList = new ArrayList<Map<String, Object>>();// pdf文件list
		for (Object object : jArray) {// 筛选出PDF文件
			if (object != null) {
				JSONObject json = JSONObject.fromObject(object);
				if(lstSuccess.contains(json.getString("url"))){
					continue;
				}	
				
				Map<String, Object> map = new HashMap<String, Object>();
				typeId = json.getString("typeId");
				if (typeId.equals("10002") || typeId.equals("20002")) {
					map.put("url", json.getString("url"));
					map.put("orderNo", json.getString("orderNo"));
					map.put("index", json.getString("index"));
					map.put("typeId", typeId);
					PDFfileList.add(map);
				}
				log.info("申请附件信息中的图片地址:" + json.getString("url") + "PDF 图片个数"
						+ PDFfileList.size() + "图片索引" + json.getString("index"));
			}
		}

		
		Map lcMap=lcAppointMapper.getLcAppoint(orderNo);
		
		loanCooprCode=MapUtils.getString(lcMap, "loanCooprCode");
		
		
		
		// 对list进行重新按照index进行升序-从小到大
		if (null != PDFfileList && PDFfileList.size() > 0) {// 有PDF文件需要发送
			Collections.sort(PDFfileList, new Comparator<Map>() {
				public int compare(Map o1, Map o2) {
					int ret = 0;
					// 比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回-1/0/1
					ret = o1.get("index").toString()
							.compareTo(o2.get("index").toString());// 逆序的话就用o2.compareTo(o1)即可
					return ret;
				}
			});

			Map<String, Object> map = new HashMap<String, Object>();

			Map PDFmap = PDFfileList.get(0);

			docIo = getRspContentPDF(PDFfileList);
			if(docIo.equals("")) {//若出现read time out异常，docIo为空，再次请求一次
				docIo = getRspContentPDF(PDFfileList);
			}
			String applSeq = utilBorrowIdService.findApplSeqByOrderNo(PDFmap
					.get("orderNo").toString());
			map.put("applSeq", applSeq);
			map.put("docFileType",
					keyValueMap.get(PDFmap.get("typeId").toString()));// 此处需要转义为华融字段
			map.put("addtime", l);// 10位long 类型时间
			map.put("docIo", docIo);

			try {

				boolean flag = true;
				int i = 1;
				do {// 如果不成功，重复上传三次
					// log.info("附件发送报文map:"+map);
					param = SendHrHaEnCrypt.sendHrHaEnCryptMap("KG",
							JsonUtils.toJsonString(map), "fileApply.url");
					log.info("fileApply.url返回报文:" + param);
					if (param != null && param.getRetCode().equals("00000")) {
						flag = false;
					}
					i++;
				} while (flag && i < 4);

			} catch (Exception e) {
				resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
				resp.setMsg("调用华融发送附件接口失败");
				e.printStackTrace();
				return resp;
			}

			if (param != null && param.getRetCode().equals("00000")) {// 记录上传成功的附件，入库
				for (Map<String, Object> o : PDFfileList) {
					Map<String, Object> success_map = new HashMap<String, Object>();
					success_map.put("respStatus", param.getRetCode());
					success_map.put("orderId", o.get("orderNo"));
					success_map.put("docFileType", o.get("typeId"));
					success_map.put("filePath", o.get("url"));
					success_map.put("applSeq", applSeq);
					success_map.put("addtime", l);// 10位long 类型时间
					success_map.put("index", o.get("index"));
					fileList.add(success_map);
				}
			}
		}

		for (Object object : jArray) {
			if (object != null) {
				JSONObject json = JSONObject.fromObject(object);
				if(lstSuccess.contains(json.getString("url"))){
					continue;
				}	
				
				Map<String, Object> map = new HashMap<String, Object>();
				filepath = json.getString("url");
				// sub_str=filepath.substring(filepath.indexOf("img")+4);
				typeId = json.getString("typeId");
				if (typeId.equals("10002") || typeId.equals("20002")) {
					continue;
				}
				docIo = getRspContent(filepath, typeId);
				
				if(docIo.equals("")) {//若出现read time out异常，docIo为空，再次请求一次
					docIo = getRspContent(filepath, typeId);
				}
				
				orderNo = json.getString("orderNo");
				index = json.getString("index");
				map.put("applSeq",utilBorrowIdService.findApplSeqByOrderNo(orderNo));
				map.put("docFileType", keyValueMap.get(typeId));// 此处需要转义为华融字段
				map.put("addtime", l);// 10位long 类型时间
				// docIo=readFileByBytes(filepath);
				map.put("docIo", docIo);
				// log.info("fileApply.url请求报文:"+JsonUtils.toJsonString(map));
				try {

					boolean flag = true;
					int i = 1;
					do {// 如果不成功，重复上传三次
						log.info("附件发送报文图片地址:" + filepath);
						param = SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode,
								JsonUtils.toJsonString(map), "fileApply.url");
						log.info("fileApply.url返回报文:" + param);
						if (param != null && param.getRetCode().equals("00000")) {
							flag = false;
						}
						i++;
					} while (flag && i < 4);

					// 判断重复上传后的最后一次，如果不成功则记录在记录上传失败的图片地址字符串
					if (param == null) {
						errorPictureUrl = errorPictureUrl + json.get("url") + ",";
					} else if (!param.getRetCode().equals("00000")) {
						errorPictureUrl = errorPictureUrl + json.get("url") + ",";
					}
				} catch (Exception e) {
					resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
					resp.setMsg("调用华融发送附件接口失败");
					e.printStackTrace();
					return resp;
				}
				if (param != null && param.getRetCode().equals("00000")) {// 记录上传成功的附件，入库
					map.put("respStatus", param.getRetCode());
					map.put("orderId", orderNo);
					map.put("docFileType", typeId);
					map.put("filePath", filepath);
					map.put("index", index);
					fileList.add(map);
				}

			}
		}
		if (fileList.size() > 0) {
			fileApplyMapper.saveFileApply(fileList);
		}
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		if (!errorPictureUrl.equals("")) {
			resp.setMsg("存在图片上传失败");
			resp.setData(errorPictureUrl);
		} else {
			resp.setMsg("所有图片上传成功");
		}
		return resp;
	}
	
	public String readFileByBytes(String fileName) {
		/*
		 * WebApplicationContext WebApplicationContext =
		 * ContextLoaderListener.getCurrentWebApplicationContext(); String
		 * uploadDir =
		 * WebApplicationContext.getServletContext().getRealPath("/"); //
		 * uploadDir = "D:/"; fileName = uploadDir + fileName;
		 */
		File file = new File(fileName);
		InputStream in = null;
		byte[] data = null;
		/** 读取图片字节数组 */
		try {
			in = new FileInputStream(file);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 对字节数组Base64编码 */
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public String getRspContent(String http_requrl, String typeId) {

		String base_url = ConfigUtil.getStringValue(Constants.LINK_FS_URL,
				ConfigUtil.CONFIG_LINK);
		http_requrl = base_url + "/fs/Hr/fileStream?after_filepath="
				+ http_requrl + "&docFileType=" + typeId;
		HttpUtil httpUtils = new HttpUtil();
		try {
			return httpUtils.get(http_requrl);
		} catch (Exception e) {
			log.error("调用fs项目获取base64编码字符串失败", e);
			return "";
		}
	}

	public String getRspContentPDF(List list) {
		try {
			HttpUtil ht = new HttpUtil();
			JSONObject result = ht.getData(Constants.LINK_FS_URL,
					"/fs/Hr/filePDFStream", list);		
			return result.getString("pdf_base64");
			
		}catch (Exception e) {
			log.error("调用fs项目获取base64编码字符串失败", e);
			return "";
		}	
	}

	public static void main(String[] args) throws Exception {
		/*
		 * String
		 * http_requrl="http://192.168.1.219:8090/img/fc-img/1505204970535_48.jpg"
		 * ; //String base_url=
		 * ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY
		 * .ANJBO_FS_URL.toString(),ConfigUtil.CONFIG_URL);
		 * 
		 * String base_url="http://192.168.1.219:8090/";
		 * System.out.println("base_url"+base_url);
		 * http_requrl=base_url+"/fs/Hr/fileStream?after_filepath="
		 * +http_requrl+"&docFileType="+"10002"; HttpUtil httpUtils = new
		 * HttpUtil();
		 * 
		 * System.out.println(httpUtils.get(http_requrl));
		 */

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map map1 = new HashMap<String, String>();
		map1.put("url", "url" + 4);
		map1.put("type", "200" + 4);
		list.add(map1);

		Map map = new HashMap<String, String>();
		map.put("url", "url" + 3);
		map.put("type", "200" + 3);
		list.add(map);

		/*
		 * HttpUtil ht=new HttpUtil(); //JSONObject
		 * result1=ht.jsonPost("http://192.168.1.219:8090/fs/Hr/filePDFStream"
		 * ,list); JSONObject
		 * result1=ht.getData(Enums.GLOBAL_CONFIG_KEY.ANJBO_FS_URL.toString(),
		 * "/fs/Hr/filePDFStream", list);
		 * System.out.println(result1.toString());
		 */

		/*
		 * try { return httpUtils.get(http_requrl); } catch (Exception e) {
		 * log.info("获取base失败", e); return ""; }
		 */
	}
}
