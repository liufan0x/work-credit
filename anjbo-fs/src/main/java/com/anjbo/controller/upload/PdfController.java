package com.anjbo.controller.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.ContractListDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.utils.ContractUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

@Controller
@RequestMapping("/fs/pdf")
public class PdfController {
	
	@ResponseBody
	@RequestMapping("/showPDF")
	public RespDataObject<String> showPDF(HttpServletRequest request, @RequestBody ContractListDto dto) {
		RespDataObject<String> resp = new RespDataObject<String>();

		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		Document document = null;
		FileInputStream fileInputStream = null;
		FileOutputStream outputStream = null;
		ZipOutputStream zipOutputStream = null;
		BufferedInputStream bufferedInputStream = null;

		String newUrl = null; // 记录处理后返回出去的地址
		String fileName = null; // 客户姓名
		int type = dto.getType();
		String urls = dto.getPaths(); /// credit/tools/template/test1.doc,/credit/tools/template/test2.doc
		// String urls
		// ="http://182.254.149.92:9206/img/fc-img/9a2489f878b9461f938c50275ad1998f_48.doc";
		// String scheme =request.getScheme() + "://" + Constants.UPLOAD_URL;
		// //http://127.0.0.1:8080
		String[] paths = urls.split(",");

		if (paths == null || paths.length == 0) {
			resp.setMsg("合同为空!");
			return resp;
		}
		String path = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/classes"; // D:\apache-tomcat-7.0.79\webapps\anjbo-fs\
		Map<String, String> param = new HashMap<String, String>();
		List<String> urList = new ArrayList<String>(); // 本地存放路径集合

		if (dto.getJsonOject() != null) {
			fileName = MapUtils.getString(dto.getJsonOject(), "1"); // 客户姓名
		}
		String filePath = "http://182.254.149.92:9206/";
		String fileName1 = "套打合同";
		String fileName2 = fileName + " " + fileName1;
		byte[] bty;
		String docName = null;
		if (paths.length > 1) {
			for (int i = 0; i < paths.length; i++) {
				if (dto.getJsonOject() != null) {
					param = dto.getJsonOject();
				}
				param.put("docName", paths[i]);
				bty = ContractUtils.doc2Pdf(param); // pdf文件的byte数组
				if (bty == null || bty.length == 0) {
					resp.setMsg("openOffice没有读取到文件");
					return resp;
				}

				File dir = new File(path + "/img/fc-img/");
				if (!dir.exists() && !dir.isDirectory()) { // 判断文件目录是否存在
															// "D://apache-tomcat-7.0.79/webapps/anjbo-fs/WEB-INF/classes/credit/tools/template/套打合同2.pdf"
					dir.mkdirs();
				} else {
					System.out.println("目录存在");
				}
				try {
					urList.add(path + "/img/fc-img/" + docName + ".pdf");
					file = new File(path + "/img/fc-img/" + docName + ".pdf"); // 循环将字节数据写入指定路径
					fos = new FileOutputStream(file);
					bos = new BufferedOutputStream(fos);
					bos.write(bty);
				} catch (Exception e) {
					e.printStackTrace();
					resp.setMsg("文件名、目录名或卷标语法不正确1。");
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}

			}

			if (type == 2 || type == 4) { // 预览或打印 多个模块进行拼接
				try {
					newUrl = path + "/img/fc-img/" + fileName2 + ".pdf";
					filePath = filePath + "img/fc-img/" + fileName2 + ".pdf";
					document = new Document();
					PdfCopy copy = new PdfCopy(document, new FileOutputStream(newUrl));
					document.open();
					for (int i = 0; i < urList.size(); i++) {
						String arr = urList.get(i);
						PdfReader reader = new PdfReader(arr);
						int num = reader.getNumberOfPages();
						for (int j = 1; j <= num; j++) {
							document.newPage();
							PdfImportedPage page = copy.getImportedPage(reader, j);
							copy.addPage(page);
						}
						File deleteFile = new File(arr);
						if (deleteFile.isFile() && deleteFile.exists()) {
							deleteFile.delete();
						}
					}
				} catch (Exception e) {
					resp.setMsg("文件名、目录名或卷标语法不正确2。");
					e.printStackTrace();
				} finally {
					document.close();
					System.out.println("已完成！！！");
				}
				resp.setData(filePath);
				return resp;
			} else if (type == 3) { // 下载 多文件进行打包压缩
				try {
					boolean flat = false;
					newUrl = path + "/img/fc-img/" + fileName2 + ".zip";
					filePath = filePath + "img/fc-img/" + fileName2 + ".zip";
					File zipFile = new File(newUrl);
					outputStream = new FileOutputStream(zipFile);
					zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
					for (int i = 0; i < urList.size(); i++) {
						// 创建ZIP实体，并添加进压缩包
						String zipName = urList.get(i);
						ZipEntry zipEntry = new ZipEntry(zipName);
						zipOutputStream.putNextEntry(zipEntry);
						// 读取待压缩的文件并写进压缩包里
						fileInputStream = new FileInputStream(zipName);
						byte[] buffer = new byte[fileInputStream.available() + 1000];
						bufferedInputStream = new BufferedInputStream(fileInputStream);
						int red = 0;
						while ((red = bufferedInputStream.read(buffer, 0, 1024 * 10)) != -1) {
							zipOutputStream.write(buffer, 0, red);
						}
						fileInputStream.close();

					}
				} catch (Exception e) {
					resp.setMsg("文件名、目录名或卷标语法不正确3。");
					e.printStackTrace();
				} finally {
					try {
						zipOutputStream.closeEntry();
						zipOutputStream.close();
						bufferedInputStream.close();
						// fileInputStream.close();
						outputStream.close();
						for (int i = 0; i < urList.size(); i++) {
							String zipName = urList.get(i);
							File deleteFile = new File(zipName);
							if (deleteFile.isFile() && deleteFile.exists()) {
								deleteFile.delete();
							}
						}
					} catch (IOException e) {
						resp.setMsg("文件名、目录名或卷标语法不正确4。");
						e.printStackTrace();
					}
				}

				resp.setData(filePath);
				return resp;
			}

		} else if (paths.length == 1) {
			// 只有一个模板的时候
			try {
				newUrl = path + "/img/fc-img/" + fileName2 + ".pdf";
				filePath = filePath + "img/fc-img/" + fileName2 + ".pdf";
				if (dto.getJsonOject() != null) {
					param = dto.getJsonOject();
				}
				param.put("docName", paths[0]);
				bty = ContractUtils.doc2Pdf(param); // 模板转pdf格式
				if (type == 2 || type == 3 || type == 4) { // 下载、预览或打印
					File dir = new File(path + "/img/fc-img/");
					if (!dir.exists() && !dir.isDirectory()) { // 判断文件目录是否存在
																// "D://apache-tomcat-7.0.79/webapps/anjbo-fs/WEB-INF/classes/credit/tools/template/套打合同2.pdf"
						dir.mkdirs();
					} else {
						System.out.println("目录存在");
					}
					file = new File(newUrl);
					fos = new FileOutputStream(file);
					bos = new BufferedOutputStream(fos);
					bos.write(bty);
				}

			} catch (Exception e) {
				// resp.setMsg("文件名、目录名或卷标语法不正确5。");
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			resp.setData(filePath);
			return resp;
		}
		return resp;
	}
}


