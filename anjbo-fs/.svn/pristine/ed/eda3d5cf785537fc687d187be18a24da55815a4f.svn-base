package com.anjbo.controller.upload;

import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.FileUtil;
import com.anjbo.utils.ImageMetaDataUtil;
import com.anjbo.utils.ImgUtil;
import com.anjbo.utils.JsonUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/fs/img")
public class ImgController {
	
	/**
	 * 赎楼系统上传图片专用
	 * @param files
	 * @param request
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public Map<String, Object> upload(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request,String path){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    String filePath = request.getSession().getServletContext().getRealPath("img"+File.separator+path)+File.separator;
			for(int i = 0;i<files.length;i++){
				String fileName = files[i].getOriginalFilename();
				String uuid = UUID.randomUUID().toString();
				String saveFileName48 = uuid.replace("-", "")+"_48"+ getExtention(fileName, 0);
				String saveFileName18 = uuid.replace("-", "")+"_18"+ getExtention(fileName, 0);
				if(!files[i].isEmpty()){
					File writeFile = new File(filePath+saveFileName48);
					if (!writeFile.getParentFile().exists()) {
						writeFile.getParentFile().mkdirs();
					}
					files[i].transferTo(writeFile);
					int isPs = 0;
					if(!fileName.endsWith(".pdf")) {
						isPs = ImageMetaDataUtil.isPs(writeFile);
					}
					String thumbnailUrl = createThumbFile(writeFile);
					map.put("thumbnail_url", filePath+thumbnailUrl);
					String url = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath() + File.separator +"img"+File.separator+path+File.separator;
					if(!fileName.endsWith(".pdf")) {
						productionAbbreviations(writeFile, filePath + saveFileName18);
					}
					map.put("thumbnail_url", url+saveFileName18);
					map.put("url", url+saveFileName48);
					map.put("isPs",isPs);
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return map;  
	}
	
	/**
	 * 赎楼系统上传图片专用
	 * @param files
	 * @param request
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appUpload")
	public RespDataObject<Map<String, Object>> appUpload(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request,String path){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    String filePath = request.getSession().getServletContext().getRealPath("img"+File.separator+path)+File.separator;
		    
			for(int i = 0;i<files.length;i++){
				String uuid = UUID.randomUUID().toString();
				String fileName = files[i].getOriginalFilename();
				String saveFileName48 = uuid.replace("-", "")+"_48"+ getExtention(fileName, 0);
				String saveFileName18 = uuid.replace("-", "")+"_18"+ getExtention(fileName, 0);
				if(!files[i].isEmpty()){
					File writeFile = new File(filePath+saveFileName48);
					if (!writeFile.getParentFile().exists()) {
						writeFile.getParentFile().mkdirs();
					}
					files[i].transferTo(writeFile);
					int isPs = 0;
					if(!fileName.endsWith(".pdf")) {
						isPs = ImageMetaDataUtil.isPs(writeFile);
					}
					String thumbnailUrl = createThumbFile(writeFile);
					map.put("thumbnail_url", filePath+thumbnailUrl);
					String url = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath() + File.separator +"img"+File.separator+path+File.separator;
					productionAbbreviations(writeFile, filePath+saveFileName18);
					map.put("thumbnail_url", url+saveFileName18);
					map.put("imgUrl", url+saveFileName48);
					map.put("isPs",isPs);
				}
			}
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			 e.printStackTrace();
			 RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;  
	}
	
	/**
	 * zhucaiCrawler爬虫系统上传图片专用
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/crawlerPictureUpload")
	public RespDataObject<Map<String, Object>> crawlerPictureUpload(@RequestParam("url") String url, @RequestParam("suffix") String suffix,
			@RequestParam("pictureSavePath") String pictureSavePath, HttpServletRequest request) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		FileOutputStream out = null;
		try {
		    String filePath = request.getSession().getServletContext().getRealPath(File.separator + pictureSavePath) + File.separator;
		    String uuid = UUID.randomUUID().toString();
		    String pictureName = uuid + suffix;
		    File file = new File(filePath,pictureName);
		    if (!file.getParentFile().exists()) {  
                file.getParentFile().mkdirs();
            }
		    byte[] b = FileUtil.httpConverBytes(url);
            out = new FileOutputStream(file); 
	        out.write(b); 
            String pictureUrl = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath()  + "/" 
            				  + pictureSavePath.replace(File.separator, "/") + "/" + pictureName;
            map.put("pictureUrl", pictureUrl);
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			 e.printStackTrace();
			 RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		} finally {
			 try {
				if(out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resp;  
	}
	
	/**
	 * zhucaiCrawler爬虫系统删除图片专用
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/crawlerPictureDelete")
	public RespDataObject<Map<String, Object>> crawlerPictureDelete(@RequestParam("url") String url, @RequestParam("pictureDeletePath") String pictureDeletePath, 
			HttpServletRequest request) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
		    String filePath = request.getSession().getServletContext().getRealPath(File.separator + pictureDeletePath) + File.separator + fileName;
		    File file = new File(filePath);
		    boolean flag = false;
		    if (file.exists() && file.isFile()) {  
		    	flag = file.delete();
            }
		    map.put("deleteStatus", flag);
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			 e.printStackTrace();
			 RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		} 
		return resp;  
	}
	
	private static String getExtention(String fileName, int i) {
		if(fileName != null){
			int pos = fileName.lastIndexOf(".") + i;
			if (pos > 0)
				return fileName.substring(pos);
			else
				return null;
		}
		return null;
	}
	
	private String createThumbFile(File file) {
		String mimetype = getMimeType(file);
		String thumbFileName = "";
		try {
			if (mimetype.endsWith("png") || mimetype.endsWith("jpeg")
					|| mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
				File thumbFile = new File(file.getAbsolutePath().replace(
						mimetype, "thumb." + mimetype));
				BufferedImage im = ImageIO.read(file);
				if (im != null) {
					//重设大小会改变原图片
					//BufferedImage thumb = Scalr.resize(im, 150);
					if (mimetype.endsWith("png")) {
						ImageIO.write(im, "PNG", thumbFile);
					} else if (mimetype.endsWith("jpeg")) {
						ImageIO.write(im, "jpg", thumbFile);
					} else if (mimetype.endsWith("jpg")) {
						ImageIO.write(im, "jpg", thumbFile);
					} else {
						ImageIO.write(im, "GIF", thumbFile);
					}
					thumbFileName = thumbFile.getName();
				}
			}
		} catch (IOException ex) {

		}
		return thumbFileName;
	}
	
	private String getMimeType(File file) {
		String mimetype = "";
		if (file.exists()) {
			if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
				mimetype = "png";// "image/png";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
				mimetype = "jpg";// "image/jpg";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
				mimetype = "jpeg";// "image/jpeg";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
				mimetype = "gif";// "image/gif";
			} else {
				javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
				mimetype = mtMap.getContentType(file);
			}
		}
		return mimetype;
	}
	
	private String getSuffix(String filename) {
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if (pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		return suffix;
	}
	
	private void productionAbbreviations(File fi,String url){
        try { 
            File fo = new File(url); //将要转换出的小图文件  
            int nw = 500;  
            /* 
            AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D 
            坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系 
            列平移、缩放、翻转、旋转和剪切来构造仿射变换。 
            */  
            AffineTransform transform = new AffineTransform();  
            BufferedImage bis = ImageIO.read(fi); //读取图片  
            int w = bis.getWidth();  
            int h = bis.getHeight();  
             //double scale = (double)w/h;  
            int nh = (nw*h)/w ;  
            double sx = (double)nw/w;  
            double sy = (double)nh/h;  
            transform.setToScale(sx,sy); //setToScale(double sx, double sy) 将此变换设置为缩放变换。  
            System.out.println(w + " " +h);  
            /* 
             * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 
             *  Raster 中 2D 坐标的线性映射。所使用的插值类型由构造方法通过 
             *  一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。 
            如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现 
            的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 
            呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像 
            必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 
            的 band 数。 
            */  
            AffineTransformOp ato = new AffineTransformOp(transform,null);  
            BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_BGR);  
            /* 
             * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 
             * 对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存 
             * 储的 Blue、Green 和 Red 三种颜色。 
            */  
            bid.getGraphics().drawImage(bis,0,0,nw,nh,null);//画图，ato.filter(bis,bid);不支持TYPE_INT_BGR
            //ato.filter(bis,bid);  
            ImageIO.write(bid,"jpeg",fo);  
        } catch(Exception e) {  
            e.printStackTrace();  
        } 
	}
	/**
	 * 上传图片做旋转及裁剪
	 * @param request
	 * @return
	 */
	@RequestMapping("saveImg")
	@ResponseBody
	public Map<String, Object> saveImg(HttpServletRequest request,String avatar_data,String path) {
		if(StringUtils.isEmpty(path)){
			path = "cut";
		}
		Map<String,Object> map = new HashMap<String, Object>();
		InputStream imageStream = null;
		try {
			// 上传图片
			// 创建一个多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				// 转换为多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 获取文件名
				Iterator<String> fileNames = multiRequest.getFileNames();
				while (fileNames.hasNext()) {
					MultipartFile file = multiRequest.getFile(fileNames.next());
					if (file != null) {
						// 获取文件名
						String originalFilename = file.getOriginalFilename();
						//重命名
						String stuffix = FilenameUtils.getExtension(originalFilename);
						String fileName = UUID.randomUUID().toString()+"."+stuffix;
						//定义存储路径
						String filePath = request.getSession().getServletContext().getRealPath("img"+File.separator+path)+File.separator;
						// 文件名非空，视为文件存在
						if (StringUtils.isNotBlank(originalFilename)) {
							imageStream = file.getInputStream();
							BufferedImage image = ImageIO.read(imageStream);
							Map<String, Object> jsonObj = JsonUtil.jsonToMap(avatar_data);
							int startX = Double.valueOf(jsonObj.get("x").toString()).intValue();
							int startY = Double.valueOf(jsonObj.get("y").toString()).intValue();
							int height = Double.valueOf(jsonObj.get("height").toString()).intValue();
							int width = Double.valueOf(jsonObj.get("width").toString()).intValue();
							int angel = Double.valueOf(jsonObj.get("rotate").toString()).intValue();
							BufferedImage img = ImgUtil.rotateSubImg(image, angel);
							BufferedImage subImage = ImgUtil.subImage(img,startX,startY, width, height);
							//判断文件路径或文件是否存在
							File dir = new File(filePath);
							if(!dir.exists()){
								dir.mkdir();
							}
							File target = new File(dir,fileName);
							if(target.exists()){
								target.delete();
							}
							//写出图片
							ImageIO.write(subImage, "PNG", target);//为保证图片清晰,以PNG格式写出,相应的,图片容量会变大
							String url = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath() + File.separator +"img"+File.separator+path+File.separator;
							map.put("imgUrl",url+fileName);
							map.put("state","success");
						}
					}
				}
			}
		} catch (Exception e) {
			map.put("state","fail");
			map.put("msg", "保存失败");
			e.printStackTrace();
		} finally {
			if (imageStream != null) {
				try {
					imageStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	/**
	 * 图片裁剪缩放并保存(评估系统上传图片压的缩处理)
	 * @param request
	 * @return
	 */
	@RequestMapping("zoomImgAndSave")
	@ResponseBody
	public Map<String, Object> zoomImgAndSave(HttpServletRequest request,String avatar_data,String path) {
		if(StringUtils.isEmpty(path)){
			path = "cut";
		}
		Map<String,Object> map = new HashMap<String, Object>();
		InputStream imageStream = null;
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> fileNames = multiRequest.getFileNames();
				while (fileNames.hasNext()) {
					MultipartFile file = multiRequest.getFile(fileNames.next());
					if (file != null) {
						String originalFilename = file.getOriginalFilename();
						String stuffix = FilenameUtils.getExtension(originalFilename);
						String fileName = UUID.randomUUID().toString()+"."+stuffix;
						//定义存储路径
						String filePath = request.getSession().getServletContext().getRealPath("img"+File.separator+path)+File.separator;
						// 文件名非空，视为文件存在
						if (StringUtils.isNotBlank(originalFilename)) {
							imageStream = file.getInputStream();
							BufferedImage image = ImageIO.read(imageStream);
							Map<String, Object> jsonObj = JsonUtil.jsonToMap(avatar_data);
							int startX = Double.valueOf(jsonObj.get("x").toString()).intValue();
							int startY = Double.valueOf(jsonObj.get("y").toString()).intValue();
							int height = Double.valueOf(jsonObj.get("height").toString()).intValue();
							int width = Double.valueOf(jsonObj.get("width").toString()).intValue();
							int angel = Double.valueOf(jsonObj.get("rotate").toString()).intValue();
							BufferedImage img = ImgUtil.rotateSubImg(image, angel);
							BufferedImage subImage = ImgUtil.subImage(img,startX,startY, width, height);
							if(width>1417||height>1417){//宽度或高度大于1417时,适当缩放
								int temp = width;
								if(temp<height){
									temp = height;
								}
								double zoom = 1417.0/temp;
								width = (int) (width * zoom);
								height = (int) (height * zoom);
							}
							BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
							Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
							graphics.drawImage(subImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
							//判断文件路径或文件是否存在
							File dir = new File(filePath);
							if(!dir.exists()){
								dir.mkdir();
							}
							File target = new File(dir,fileName);
							if(target.exists()){
								target.delete();
							}
							//写出图片
							ImageIO.write(bufferedImage, "jpg", target);
							String url = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath() + File.separator +"img"+File.separator+path+File.separator;
							map.put("imgUrl",url+fileName);
							map.put("state","success");
						}
					}
				}
			}
		} catch (Exception e) {
			map.put("state","fail");
			map.put("msg", "保存失败");
			e.printStackTrace();
		} finally {
			if (imageStream != null) {
				try {
					imageStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
}


