package com.anjbo.controller.hrFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.utils.ImgToPdf;
import com.anjbo.utils.JsonUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/fs/Hr")
public class HrFileController {
	static Logger log = Logger.getLogger(HrFileController.class);

	@ResponseBody
	@RequestMapping("/fileStream")
	public String fileString(HttpServletRequest request, String after_filepath, String docFileType) {
		log.error("after_filepath" + after_filepath);
		// String before_filePath =
		// request.getSession().getServletContext().getRealPath("img")+File.separator;

		String before_filePath = request.getSession().getServletContext().getRealPath(File.separator);

		System.out.println("filePath:" + before_filePath + after_filepath);

		// String FileName=after_filepath.substring(after_filepath.lastIndexOf("/")+1);

		// 此处需根据url确定获取图片位置，

		if (after_filepath.contains(".com")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf(".com") + 5);
		} else if (after_filepath.contains("fc-img")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf("img"));
		} else if (after_filepath.contains("fc-infos")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf("fc-infos"));
			// after_filepath=after_filepath.substring(after_filepath.indexOf("img"));
		}

		String lastfilepath = before_filePath + after_filepath;
		lastfilepath = lastfilepath.replace("/", File.separator);

		log.error("lastfilepath" + lastfilepath);

		System.out.println("lastfilepath" + lastfilepath);

		/*
		 * if(docFileType.equals("10002")||docFileType.equals("20002")) { //此处执行图片转PDF
		 * String
		 * PdfTempPath=lastfilepath.substring(0,lastfilepath.lastIndexOf(File.separator)
		 * +1);//PDF文件临时存放目录
		 * 
		 * System.out.println("执行PDF转码 PdfTempPath"+PdfTempPath);
		 * 
		 * return readPDFFileByBytes(lastfilepath,PdfTempPath); }
		 */

		System.out.println("执行图片转码");

		return readFileByBytes(lastfilepath);
	}

	@ResponseBody
	@RequestMapping("/filePDFStream")
	public String filePDFString(HttpServletRequest request, String after_filepath) {
		String before_filePath = request.getSession().getServletContext().getRealPath(File.separator);
		String lastfilepath = "";
		if (after_filepath.contains(".com")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf(".com") + 5);
		} else if (after_filepath.contains("fc-img")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf("img"));
		} else if (after_filepath.contains("fc-infos")) {
			after_filepath = after_filepath.substring(after_filepath.indexOf("fc-infos"));
		}
		lastfilepath = before_filePath + after_filepath;
		lastfilepath = lastfilepath.replace("/", File.separator);// 最终通过文件服务器上传的路径
		return readPDFFileByBytesList(lastfilepath);
	}

	public static String readFileByBytes(String filePath) {

		File file = new File(filePath);
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

	public static String readPDFFileByBytes(String filePath, String PdfTempPath) {
		ArrayList<String> imageUrllist = new ArrayList<String>();
		imageUrllist.add(filePath);
		String result = "";
		Date date = new Date();
		long l = date.getTime();

		String pdf_filename = String.valueOf(l) + ".pdf";
		log.info("pdf_filename" + pdf_filename);
		File file = ImgToPdf.getInstance().toPdf(imageUrllist, PdfTempPath, pdf_filename);

		result = readFileByBytes(file.getPath());

		// System.out.println("PDF result"+result);

		// file.delete();

		return result;
	}

	/*
	 * public static String readPDFFileByBytesList(List<Map<String,Object>> list) {
	 * ArrayList<String> imageUrllist = new ArrayList<String>(); String
	 * PdfTempPath=""; if(list!=null&&list.size()>0) { for(Map<String,Object>
	 * map:list) { imageUrllist.add(map.get("lastfilepath").toString());
	 * PdfTempPath=map.get("PdfTempPath").toString(); } }
	 * log.info("PdfTempPath"+PdfTempPath); String result=""; Date date=new Date();
	 * long l = date.getTime();
	 * 
	 * String pdf_filename=String.valueOf(l)+".pdf";
	 * log.info("pdf_filename"+pdf_filename +"imageUrllist"+imageUrllist); File file
	 * = ImgToPdf.getInstance().toPdf(imageUrllist,PdfTempPath,pdf_filename);
	 * log.info("file.getPath()"+file.getPath());
	 * result=readFileByBytes(file.getPath());
	 * 
	 * //System.out.println("PDF result"+result);
	 * 
	 * //file.delete();
	 * 
	 * return result; }
	 */

	public static String readPDFFileByBytesList(String lastfilepath) {
		log.info("new-lastfilepath" + lastfilepath);
		return readFileByBytes(lastfilepath);
	}

	public static void main(String[] args) throws Exception {
		String base64 = readFileByBytes("D:\\3c2140e98fd9482883300ebeff0aa818_48.pdf");
		System.out.println(base64);
		BufferedInputStream bin = null;
		FileOutputStream fout = null;
		BufferedOutputStream bout = null;
		try {
			BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(base64);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			// 创建从底层输入流中读取数据的缓冲输入流对象
			bin = new BufferedInputStream(bais);
			// 指定输出的文件http://m.nvzi91.cn/nxby/29355.html
			File file = new File("D:\\bb.pdf");
			fout = new FileOutputStream(file);
			bout = new BufferedOutputStream(fout);
			byte[] buffers = new byte[1024];
			int len = bin.read(buffers);
			while (len != -1) {
				bout.write(buffers, 0, len);
				len = bin.read(buffers);
			}
			// 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
			bout.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bin.close();
				fout.close();
				bout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
