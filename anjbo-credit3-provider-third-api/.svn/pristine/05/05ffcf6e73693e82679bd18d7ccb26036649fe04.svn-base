package com.anjbo.utils.ccb;

import com.anjbo.bean.ccb.BusInfoTask;
import com.anjbo.bean.cm.CMBusInfoDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.service.ccb.BusInfoTaskService;
import com.anjbo.utils.StringUtil;
import com.anjbo.ws.ccb.CCBWsHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component("busInfoTaskRunner")
public class BusInfoTaskRunner implements Runnable {

	protected final Log logger = LogFactory.getLog(BusInfoTaskRunner.class);
	String BusInfoDir = ThirdApiConstants.CCB_BUS_INFO_DIR;
	String BusInfoZipDir = ThirdApiConstants.CCB_BUS_INFO_ZIPDIR;
	String rsync = ThirdApiConstants.CCB_RSYNC;

	private String code;
	private String orderNo;
	private String appNo;
	private String subBankId;

	private BusInfoTaskService busInfoTaskService;

	public void init(String code, String orderNo, String appNo, String subBankId,BusInfoTaskService busInfoTaskService) {
		this.code = code;
		this.orderNo = orderNo;
		this.appNo = appNo;
		this.subBankId = subBankId;
		this.busInfoTaskService = busInfoTaskService;
	}

	@Override
	public void run() {
		BusInfoTask orderTask = busInfoTaskService.getByOrderNoAndCode(orderNo, code);
		if (orderTask == null) {
			orderTask = new BusInfoTask();
			orderTask.setOrderNo(orderNo);
			orderTask.setCode(code);
			orderTask.setUpdatetime(new Date());
			orderTask.setStatus(CCBEnums.Order_Task_Status.UNPROCESS.getStatus());
			busInfoTaskService.insert(orderTask);
		}else{
			if(CCBEnums.Order_Task_Status.PROCESSING.getStatus().equals(orderTask.getStatus())||CCBEnums.Order_Task_Status.PROCESSED.getStatus().equals(orderTask.getStatus())){
				return;
			}
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.UNPROCESS);
		}
		try {
			logger.info("[" + orderNo + "影像处理]开始---" + new Date());
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.PROCESSING);
			// zip
			logger.info("[" + orderNo + "影像处理]压缩目录开始---" + new Date());
			String zfilename = zipBusInfo(orderNo, appNo, code);
			orderTask.setName(zfilename);
			busInfoTaskService.update(orderTask);
			logger.info("[" + orderNo + "影像处理]压缩目录完成---" + new Date());
			// sh
			logger.info("[" + orderNo + "影像处理]同步数据开始---" + new Date());
			synchronizationBusinfo(appNo, code, zfilename);
			logger.info("[" + orderNo + "影像处理]同步数据结束---" + new Date());
			// tz
			logger.info("[" + orderNo + "影像处理]调用C007接口开始---" + new Date());
			HashMap<String, Object> info = new HashMap<String, Object>();
			info.put("name", zfilename);
			info.put("code", "001");
			info.put("appNo", appNo);
			RespDataObject<Map<String, Object>> respCCB = CCBWsHelper.postOp(CCBTranNoEnum.C007.getCode(), info,subBankId);
			logger.info("[" + orderNo + "影像处理]同步C007接口结束---" + new Date());
			orderTask.setResult(MapUtils.getString(respCCB.getData(), "RESULT"));
			orderTask.setRemark(MapUtils.getString(respCCB.getData(), "NOTE"));
			busInfoTaskService.update(orderTask);
			if ("FAIL".equals(respCCB.getCode())) {
				busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.FAILE);
				logger.error("调用远程命令错误-" + respCCB.getMsg());
				return;
			}
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.PROCESSED);
		} catch (IOException e) {
			// IO错误
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.FAILE);
			logger.error("IO错误", e);
		} catch (ParseException e) {
			// 压缩错误
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.FAILE);
			logger.error("压缩", e);
		} catch (InterruptedException e) {
			// 调用远程命令错误
			busInfoTaskService.updateStatus(orderTask.getId(), CCBEnums.Order_Task_Status.FAILE);
			logger.error("调用远程命令错误", e);
		}
	}

	private void synchronizationBusinfo(String appNo, String code, String zfilename)
			throws IOException, InterruptedException {
		String CMD = rsync + ' ' + BusInfoZipDir + File.separator + appNo + File.separator + zfilename;
		logger.info("CMD===:"+CMD);
		Process p;
		p = Runtime.getRuntime().exec(CMD);
		p.waitFor();

		// String ip = "192.168.1.15";
		// int port = 60688;
		// String user = "rsync";
		// String password = "VFz&uDs$@V#o%?Y$";
		// RemoteExecuteCommand rec = new RemoteExecuteCommand(ip,port, user,
		// password);
		// logger.info("rsync---["+CMD+']');
		// logger.info(rec.execute(CMD));
	}

	private String zipBusInfo(String orderNo, String appNo, String code) throws ParseException {
		ZipOutputStream out = null;
		String outputName = BusInfoZipDir + File.separator + appNo + File.separator;
		String zfilename = appNo + "_0.zip";
		if (!createFile(outputName + zfilename)) {
			String[] list = new File(BusInfoZipDir + File.separator + appNo + File.separator).list();
			int count = 0;
			DecimalFormat df = new DecimalFormat("000");
			if (list == null || list.length == 0)
				count = 0;
			else {
				Arrays.sort(list);
				count = df.parse(StringUtil.substringBetween(list[list.length - 1], "_", ".")).intValue() + 1;
			}
			zfilename = appNo + '_' + count + ".zip";
		} else {
			zfilename = appNo + "_0.zip";
		}
		File file = new File(outputName + zfilename);
		try {
			out = new ZipOutputStream(new FileOutputStream(file));
			CMBusInfoDto busDto = new CMBusInfoDto();
			busDto.setOrderNo(orderNo);
			busDto.setCode("001".equals(code) ? null : code);
			logger.info("zipBusInfo:----code:"+code);
			List<CMBusInfoDto> busInfos = new ArrayList<CMBusInfoDto>();
			//TODO 修改springcloud请求其他项目方式,目前orderApi未实现
			busInfos= null;/*new HttpUtil().getList(Constants.LINK_CMCREDIT.toString(),
						ReqMappingConstants.CREDIT_CM_GETBYORDERANDCODE, busDto, CMBusInfoDto.class);*/
			logger.info("zipBusInfo:----size:"+busInfos.size());
			LogFactory.getLog(BusInfoTaskRunner.class).info("生成ZIP文件开始 " + file.getAbsolutePath());
			for (Iterator<CMBusInfoDto> iterator = busInfos.iterator(); iterator.hasNext();) {
				CMBusInfoDto busInfoDto = iterator.next();
				logger.info("zipBusInfo:---list: orderNo="+busInfoDto.getOrderNo());
				String filename = "";
				if(busInfoDto.getUrl().startsWith("http://")){//新版影像资料采用fs上传，具体文件URL格式
					filename = BusInfoDir+ File.separator+busInfoDto.getOrderNo()+
					           File.separator+busInfoDto.getCode()+ File.separator+busInfoDto.getName();
					download(busInfoDto.getUrl(), new File(filename));
				}else{//兼容老版本文件采用本地请求地址格式
					filename = BusInfoDir + new String(
							Base64.decodeBase64(busInfoDto.getUrl().replace("/cm/common/v/getFile/", "")), "UTF-8");
				}
				String targetName = appNo + busInfoDto.getCode() + busInfoDto.getName() + '.' + busInfoDto.getExt();
				zipFilesToZipFile("", targetName, new File(filename), out);
			}
			out.flush();
			LogFactory.getLog(BusInfoTaskRunner.class).info("生成ZIP文件完成 " + file.getAbsolutePath());
			if (busInfos.size() == 0) {
				file.delete();
				LogFactory.getLog(BusInfoTaskRunner.class).info("查询影像资料为空，删除ZIP文件");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return zfilename;
	}

	private static void zipFilesToZipFile(String dirPath, String name, File file, ZipOutputStream zouts) {
		FileInputStream fin = null;
		ZipEntry entry = null;
		// 创建复制缓冲区
		byte[] buf = new byte[4096];
		int readByte = 0;
		if (file.isFile()) {
			try {
				// 创建一个文件输入流
				fin = new FileInputStream(file);
				// 创建一个ZipEntry
				entry = new ZipEntry(dirPath + name);
				// 存储信息到压缩文件
				zouts.putNextEntry(entry);
				// 复制字节到压缩文件
				while ((readByte = fin.read(buf)) != -1) {
					zouts.write(buf, 0, readByte);
				}
				zouts.closeEntry();
				fin.close();
				LogFactory.getLog(BusInfoTaskRunner.class).info("添加文件 " + file.getAbsolutePath() + " 到zip文件中!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			logger.debug("文件 " + descFileName + " 已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			logger.debug(descFileName + " 为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				logger.debug("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				logger.debug(descFileName + " 文件创建成功!");
				return true;
			} else {
				logger.debug(descFileName + " 文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(descFileName + " 文件创建失败!");
			return false;
		}

	}
	private static void download(String urlString, File file) throws IOException {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    // 输入流
	    InputStream is = con.getInputStream();
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    //创建目录
	    if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
	    // 输出的文件流
	    OutputStream os = new FileOutputStream(file);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	}   
	/**
	 * 同步上传影像资料
	 * @param orderNo
	 * @param busInfoUrl
	 */
	public String syncCCBBusInfo(String orderNo, String busInfoUrl){
		String imgPath = null;
		try {
			logger.info("[" + orderNo + "影像处理]下载文件开始---" + new Date());
			String path = BusInfoZipDir + orderNo + File.separator + new File(busInfoUrl).getName();
			logger.info("下载路径："+path);
			//download
			File file = new File(path);
			download(busInfoUrl,file);
			logger.info("[" + orderNo + "影像处理]下载文件结束---" + new Date());
			// sh
			logger.info("[" + orderNo + "影像处理]同步数据开始---" + new Date());
			synchronizationBusinfo(orderNo, "", file.getName());
			logger.info("[" + orderNo + "影像处理]同步数据结束---" + new Date());
			imgPath = path;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgPath;
	}
}
