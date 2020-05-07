package com.anjbo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.anjbo.common.Constants;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

/**
 * 由openoffice把doc转pdf<br>
 * 暂不支持docx转pdf <br>
 * 使用前请安装 openoffice并启动服务 可远程调用<br>
 * 命令:soffice -headless -accept="socket,host=192.168.1.8,port=8101;urp;" -nofirststartwizard
 */
public class ContractUtils {

	private static final Logger logger = Logger.getLogger(ContractUtils.class);
	
	private static final String OPENOFFICE_URL = "192.168.1.8";           // "127.0.0.1"  "8082"
	
	private static final String OPENOFFICE_PORT =  "8101";                 // "192.168.1.8" "8101"

	/**
	 * "docName" doc模板名 doc文件放到classpath: 下
	 */
	public static byte[] doc2Pdf(Map<String, String> param) {
		String docName = param.remove("docName");
		InputStream is = null;
    	//is = ContractUtils.class.getResourceAsStream("http://182.254.149.92:9206/img/fc-img"+"/"+ docName+ ".doc");
		//is = ContractUtils.class.getResourceAsStream("/credit/tools/template/"+ docName+ ".doc");
		try {
			URL url = new URL(docName);
			URLConnection connection = url.openConnection();
			is = connection.getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bdoc = fill(is, param);
		return toPdf(bdoc);
	}

	/**
	 * "docName" doc模板名 doc文件放到classpath: 下 "outName" 文件下载名
	 */
	public static void doc2PdfAndOut(Map<String, String> param, HttpServletResponse response) {
		String docName = param.remove("docName");
		String outName = param.remove("outName");
		InputStream is = ContractUtils.class.getResourceAsStream("/" + docName);
		byte[] bdoc = fill(is, param);
		responseWrite(toPdf(bdoc), outName, response);
	}

	/**
	 * 替换doc文件内变量 变量命名:${变量名}
	 */
	private static byte[] fill(InputStream docInputStream, Map<String, String> param) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			if (docInputStream == null) {
				logger.info("文件不存在:");
				return null;
			}
			HWPFDocument doc = new HWPFDocument(docInputStream);   //用来对word(doc格式)进行读写操作
			Set<Entry<String, String>> set = param.entrySet();
			Range range = doc.getRange();                          //用于获取存储在指定 key 中字符串的子字符串
			for (Entry<String, String> e : set) {
				range.replaceText("${" + e.getKey() + "}", e.getValue());
			}
			doc.write(baos);
		} catch (Exception e) {
			logger.error("数据填充错误", e);
		}
		return baos.toByteArray();
	}

	/**
	 * doc转pdf
	 */
	private static byte[] toPdf(byte[] b) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (b == null || b.length == 0) {
			logger.info("doc文件不存在");
			return null;
		}
		if (b != null && b.length > 0) {
			try {
				/**
				 * Constants.OPENOFFICE_URL openoffice服务url <br>
				 * Constants.OPENOFFICE_PORT openoffice服务端口
				 */
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFICE_URL,
						Integer.parseInt(OPENOFFICE_PORT));
				connection.connect();
				DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
				DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
				DocumentFormat inputFormat = formatReg.getFormatByFileExtension("doc"); // 源文件的格式
				DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf"); // 转成的格式
				converter.convert(new ByteArrayInputStream(b), inputFormat, baos, pdfFormat);
				connection.disconnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	public static void responseWrite(byte[] b, String outName, HttpServletResponse response) {
		try {
			if (b == null || b.length == 0) {
				logger.info("pdf文件不存在");
			}
			response.setContentType("application/pdf");
			response.setHeader("content-disposition", "attachment; filename="+new String((outName+".pdf").getBytes("UTF-8"),"ISO-8859-1"));
			response.addHeader("Content-Length", "" + b.length);
			ServletOutputStream sos = response.getOutputStream();
			sos.write(b);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
