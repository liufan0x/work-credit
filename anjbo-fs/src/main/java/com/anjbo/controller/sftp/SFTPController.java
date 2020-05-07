package com.anjbo.controller.sftp;

import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.ImgToPdf;
import com.anjbo.utils.ZIPUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * 
 * 上传影响资料图片到华安
 * @author admin
 *
 */
@Controller
@RequestMapping("/fs/ftp")
public class SFTPController {
	    private static InputStream is; // 文件下载输入流  
	    private static FTPClient ftpClient;  
	    private static SFTPController sftpController;

	    @ResponseBody
	    @RequestMapping("/uploadNew")
	    public int uploadNew(HttpServletRequest request,@RequestBody Map<String, Object> imgs){
			int count=0;
			long start = System.currentTimeMillis();
			long end = 0;
			try {
				if(imgs==null){
					return -2;   //参数异常
				}
				ZIPUtil zipUtil = new ZIPUtil();

				String images = "";
				if(imgs.containsKey("imgs")){
					images = imgs.get("imgs")+"";
				}

				String orderNo = "";
				if(imgs.containsKey("orderNo")){
					orderNo = imgs.get("orderNo").toString();
				}
				String separator = File.separator;
				if(imgs.containsKey("separator")){
					separator = imgs.get("separator")+"";
				}
				Map<String,Object> pdfMap = null;
				if(imgs.containsKey("pdfImgs")){
					pdfMap = (Map<String,Object>)MapUtils.getObject(imgs,"pdfImgs");
				}

				if(StringUtils.isBlank(images)||StringUtils.isBlank(orderNo)){
					return -2;
				}
				String[] arr = images.split(";");

				//-------压缩zip包start----
				String filePath = request.getSession().getServletContext().getRealPath("img")+File.separator;
				LogFactory.getLog(SFTPController.class).info("-----路径地址 -----" + filePath);
				List<File> listFile = new ArrayList<File>();
				File tmp = null;
				String imgFilepath = "";
				String fileRootPath = request.getSession().getServletContext().getRealPath(File.separator);;
				fileRootPath = fileRootPath.replaceAll("/",File.separator);
				if(!fileRootPath.endsWith(File.separator)){
					fileRootPath = fileRootPath+File.separator;
				}
				for (String img:arr){
					imgFilepath = img.replace("_18.jpg", "_48.jpg").replace("http://fs.zxsf360.com/", "").replace("http://fs.anjbo.com/", "").replace("http://182.254.149.92:9206/", "").replace("http://182.254.149.92:2092/","").replace("http://fsnc.anjbo.com","");
					//img = zipUtil.susFileDir("img",img);
					tmp = new File(fileRootPath+imgFilepath);
					LogFactory.getLog(SFTPController.class).info("image图片路径============: " + fileRootPath+imgFilepath);
					listFile.add(tmp);
				}

				/********************生成PDF starrt*******************/
				Set<String> keys = pdfMap.keySet();
				String pdfName = "";
				List<String> pdf = null;
				for (String key:keys){
					pdfName = MapUtils.getString(pdfMap,key,"");
					String[] pdfarr = pdfName.split(";");
					pdf = new ArrayList<String>();
					for (String p:pdfarr){
						imgFilepath = p.replace("_18.jpg", "_48.jpg").replace("http://fs.zxsf360.com/", "").replace("http://fs.anjbo.com/", "").replace("http://182.254.149.92:9206/", "").replace("http://182.254.149.92:2092/","").replace("http://fsnc.anjbo.com","");;
						LogFactory.getLog(SFTPController.class).info("PDF图片路径============: " + fileRootPath+imgFilepath);
						//p = zipUtil.susFileDir("img",p);
						pdf.add(fileRootPath+imgFilepath);
					}
					ImgToPdf.getInstance().toPdf(pdf,filePath,key+".pdf");
					tmp = new File(filePath+key+".pdf");
					listFile.add(tmp);
				}
				String zipName = zipUtil.compressedFile(listFile, filePath, orderNo);

				/********************生成PDF end*********************/

				//-------压缩zip包end-----


				//-------上传ftp-------
				long ftpStart = System.currentTimeMillis();
				LogFactory.getLog(SFTPController.class).info("上传ftp开始 " + filePath+zipName);
				count = synchronizationImage(zipName,filePath);
				if(count==-3){
					zipName = zipUtil.compressedFile(listFile, filePath, orderNo);
					count = synchronizationImage(zipName,filePath);
					LogFactory.getLog(SFTPController.class).info("-----没有找到ZIP文件重新执行生成ZIP文件以及上传FTP-----");
				} else if(count==-1){
					count = synchronizationImage(zipName,filePath);
					LogFactory.getLog(SFTPController.class).info("-----上传FTP异常重新执行上传FTP-----");
				}
				long ftpEnd = System.currentTimeMillis();
				LogFactory.getLog(SFTPController.class).info("上传ftp结束 " + filePath+zipName+",共计耗时:"+(ftpEnd-ftpStart)+"ms");
				//-------删除本地zip-------
				File file = new File(filePath+zipName);  //本地文件
				if(file.exists()){
					file.delete();//删除压缩包
				}
				for(File f:listFile){
					if(f.exists()&&f.getName().endsWith("pdf")){
						f.delete();
					}
				}
			end = System.currentTimeMillis();
			} catch (Exception e) {
				count=-1;
				e.printStackTrace();
			}
			LogFactory.getLog(SFTPController.class).info("-----影像资料推送结束共计耗时:"+(end-start)+"ms-----");
			return count;
		}
	    /**
	     * 上传FTP文件
	     * @param request
	     * @param
	     * @return
	     */
	    @ResponseBody
		@RequestMapping("/upload")
		public int addUser(HttpServletRequest request,@RequestBody Map<String, Object> imgs){
	    	int count=0;
	    	ftpClient=new FTPClient();
	    	sftpController =new SFTPController();
			try {
				    if(imgs==null){
				    	return -2;   //参数异常
				    }
		            try {
		            	String filePath = request.getSession().getServletContext().getRealPath("img")+File.separator;
		            	LogFactory.getLog(SFTPController.class).info("-----路径地址 -----" + filePath);
						//-------压缩zip包start----
						String images = imgs.get("imgs").toString();
						String orderNo = imgs.get("orderNo").toString();
						String zfilename =  zipImage(images,orderNo,filePath);
						//-------压缩zip包end-----
						
						//-------上传ftp-------
						int ru = synchronizationImage(zfilename,filePath);
						while(ru==-1){
							ru=synchronizationImage(zfilename,filePath);
						}
//						//-------删除本地zip-------
						 File file = new File(filePath+zfilename);  //本地文件
						 if(file.exists()){
							 file.delete();//删除压缩包  
						 }
					} catch (Exception e) {
						e.printStackTrace();
					}
			} catch (Exception e) {
				 count=-1;
				 e.printStackTrace();
			}
			return count;
		}
	    
	    /**
	     * 压缩图片为zip
	     * @param imags
	     * @param orderNo
	     * @return
	     */
	    private String zipImage(String imags,String orderNo,String filePath){
	        int ret = 0;
			String outputName = filePath;  //临时路径
			String zfilename = orderNo+".zip";  ///zip包名
			while(ret==0){
		    	ZipOutputStream out = null;
		    	try {
					File file = new File(outputName+zfilename);

					out = new ZipOutputStream(new FileOutputStream(file));

					String img[] = imags.split(";");

					LogFactory.getLog(SFTPController.class).info("-----生成ZIP文件start ----" + file.getAbsolutePath());

					filePath = filePath.substring(0,filePath.indexOf("img"));

					for (int i = 0; i < img.length; i++) {
						String images = img[i].replace("_18.jpg", "_48.jpg").replace("http://fs.zxsf360.com/", "").replace("http://fs.anjbo.com/", "").replace("http://182.254.149.92:9206/", "").replace("http://182.254.149.92:2092/","");
						String filename = img[i].substring(img[i].lastIndexOf("/")+1, img[i].length());  //目标文件
						LogFactory.getLog(SFTPController.class).info("image图片路径============: " + filePath+images);
						zipFilesToZipFile("", filename, new File(filePath+images), out);
					}
					out.finish();

					LogFactory.getLog(SFTPController.class).info("-----生成ZIP文件end -----" + file.getAbsolutePath());

					if (img.length == 0) {
						file.delete();
						LogFactory.getLog(SFTPController.class).info("------img is null，delete ZIP-----");
					}
					ret=1;

				} catch (Exception e) {
					e.printStackTrace();
					ret=0;
				} finally {
					try {
						if(null!=out) {
							out.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
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
					LogFactory.getLog(SFTPController.class).info("添加文件 " + file.getAbsolutePath() + " 到zip文件中!");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != fin) {
							fin.close();
						}
						if(null!=zouts) {
							zouts.close();
						}
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
			
		}
	   
	    /**
	     * 上传到ftp
	     * @param fileName
	     */
	    private int synchronizationImage(String fileName,String filePath){
	    	int count=0;
			FileInputStream in = null;
			FTPClient ftp = null;
	    	try {
	    		boolean result = false;
				File file = new File(filePath+fileName);  //本地文件
				if(!file.exists())return -3;
				in = new FileInputStream(file);
				ftp = new FTPClient();
	    		result = connectToTheServer("/download/anjbo",ftp);
	            // 判断服务器是否连接成功  
	            if (result) {  
		                // 获取文件操作目录下所有文件名称  
		                String[] remoteNames = ftp.listNames();
		                // 循环比对文件名称，判断是否含有当前要下载的文件名  
		                if(remoteNames!=null){
			                for (String remoteName: remoteNames) {  
			                    if (fileName.equals(remoteName)) {
									ftp.deleteFile(fileName);  //删除
			                    }  
			                }  
		                }
					ftp.storeFile(fileName, in);
	            }
	    	} catch (FileNotFoundException fe){
				count = -3;
			} catch (UnknownHostException ue){
				count = -1;
				System.out.println("=====UnknownHostException:解析FTP服务器ftp.sinosafe.com.cn异常======");
			}catch (Exception e) {
				e.printStackTrace();
				count=-1;
				if (e.getCause() != null)
				{
					e.getCause().printStackTrace();
				}
			}finally{
				logout(in,ftp);
			}
			return count;
	    }
	    //连接ftp old
	    public boolean connectToTheServer (String remotePath) {  
	        // 定义返回值  
	        boolean result = false;  
	        try {
	        	if(null==ftpClient){
					ftpClient = new  FTPClient();
				}
	        	// 连接至服务器，端口默认为21时，可直接通过URL连接  
				ftpClient.connect("ftp.sinosafe.com.cn", 21);
				String username = ConfigUtil.getStringValue("HUANG_FTP_USERNAME");
				String password = ConfigUtil.getStringValue("HUANG_FTP_PASSWORD");
				// 登录服务器  
				ftpClient.login(username,password);
	            // 判断返回码是否合法  
	            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
	                // 不合法时断开连接  
	                ftpClient.disconnect();  
	                // 结束程序  
	                return result;  
	            }  
	            // 设置文件操作目录  
	            result = ftpClient.changeWorkingDirectory(remotePath);  
	            // 设置文件类型，二进制  
	            result = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	            // 设置缓冲区大小  
	            ftpClient.setBufferSize(3072);  
	            // 设置字符编码  
	            ftpClient.setControlEncoding("UTF-8");
	            
	            ftpClient.setDataTimeout(60000*3);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setRemoteVerificationEnabled(false);
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }
		//连接ftp new
		public boolean connectToTheServer (String remotePath,FTPClient ftp) throws UnknownHostException,IOException{
			// 定义返回值
			boolean result = false;

				if(null==ftp){
					ftp = new  FTPClient();
				}
				// 连接至服务器，端口默认为21时，可直接通过URL连接
				ftp.connect("ftp.sinosafe.com.cn", 21);
				String username = ConfigUtil.getStringValue("HUANG_FTP_USERNAME");
				String password = ConfigUtil.getStringValue("HUANG_FTP_PASSWORD");
				// 登录服务器
				boolean isLogin = ftp.login(username,password);
				if(isLogin){
					System.out.println("====================登录FTP成功======================");
				}else{
					System.out.println("====================登录FTP失败======================");
				}
				// 判断返回码是否合法
				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					// 不合法时断开连接
					ftp.disconnect();
					// 结束程序
					System.out.println("====================断开连接======================");
					return result;
				}
				// 设置文件操作目录
				result = ftp.changeWorkingDirectory(remotePath);
				// 设置文件类型，二进制
				result = ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 设置缓冲区大小
				ftp.setBufferSize(4096);
				// 设置字符编码
				ftp.setControlEncoding("UTF-8");

				ftp.setDataTimeout(60000*3);
				ftp.enterLocalPassiveMode();
				ftp.setRemoteVerificationEnabled(false);

			return result;
		}

		//关闭 old
	    public void logout () {  
	        if (null != is) {  
	            try {  
	                // 关闭输入流  
	                is.close();
					is = null;
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        if (null != ftpClient) {  
	            try {
	                // 登出服务器
					ftpClient.logout();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                // 判断连接是否存在
	                if (ftpClient.isConnected()) {
	                    try {
	                        // 断开连接
	                        ftpClient.disconnect();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
				ftpClient = null;
	        }
	    }

		//关闭 new
		public void logout (FileInputStream in,FTPClient ftp) {
			if (null != in) {
				try {
					// 关闭输入流
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != ftp) {
				try {
					// 登出服务器
					ftp.logout();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					// 判断连接是否存在
					if (ftp.isConnected()) {
						try {
							// 断开连接
							ftp.disconnect();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				ftp = null;
			}
		}
}
