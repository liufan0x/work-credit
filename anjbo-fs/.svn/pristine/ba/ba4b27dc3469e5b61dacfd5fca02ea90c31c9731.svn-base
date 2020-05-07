package com.anjbo.controller.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.misc.BASE64Decoder;

import com.anjbo.common.Constants;

@Controller
@RequestMapping("/fs/base64Decode")
public class Base64DecodeController {
	
	/**
	 * 赎楼系统上传图片专用
	 * @param files
	 * @param request
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public Map<String, Object> upload(HttpServletRequest request,String imageByte,String photoPath,String serverName){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String path = request.getSession().getServletContext().getRealPath(File.separator+photoPath)+File.separator;
			String uid = new Date().getTime()+"";
			String imageName = uid+".jpg";
			byte[] photoimg = new BASE64Decoder().decodeBuffer(imageByte);  
            for (int i = 0; i < photoimg.length; ++i) {  
                if (photoimg[i] < 0) {  
                    // 调整异常数据  
                    photoimg[i] += 256;  
                }  
            }  
            // byte[] photoimg = Base64.decode(photo);//此处不能用Base64.decode（）方法解密，我调试时用此方法每次解密出的数据都比原数据大  所以用上面的函数进行解密，在网上直接拷贝的，花了好几个小时才找到这个错误（菜鸟不容易啊）  
            System.out.println("图片的大小：" + photoimg.length);  
            File file = new File(path,imageName);
            if (!file.getParentFile().exists()) {  
                file.getParentFile().mkdirs();
            }  
            FileOutputStream out = new FileOutputStream(file);  
            out.write(photoimg);  
            out.flush();
            out.close();
            
            String url = serverName + photoPath + imageName;
            map.put("url", url);
            map.put("status", 1);
    		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return map;  
	}
	
}


