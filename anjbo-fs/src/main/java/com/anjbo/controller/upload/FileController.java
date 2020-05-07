package com.anjbo.controller.upload;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;

/**
 * Created by Administrator on 2017/12/15.
 */
@Controller
@RequestMapping("/fs/file")
public class FileController {

    //上传
    @ResponseBody
    @RequestMapping("/upload")
    public RespDataObject<Map<String,Object>> upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse reponse){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        Map<String,Object> map = null;
        try{
            map = writeFile(file,request,reponse);
            result.setData(map);
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
            result.setCode(RespStatusEnum.SUCCESS.getCode());
        } catch (Exception e){
            result.setMsg(RespStatusEnum.FAIL.getMsg());
            result.setCode(RespStatusEnum.FAIL.getCode());
            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/uploads")
    public RespDataObject<List<Map<String,Object>>> uploads(@RequestParam MultipartFile[] file, HttpServletRequest request, HttpServletResponse reponse){
        RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
        Map<String,Object> map = null;
        try{
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            for(MultipartFile f:file){
                map = writeFile(f,request,reponse);
                list.add(map);
            }
            result.setData(list);
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
            result.setCode(RespStatusEnum.SUCCESS.getCode());
        } catch (Exception e){
            result.setMsg(RespStatusEnum.FAIL.getMsg());
            result.setCode(RespStatusEnum.FAIL.getCode());
            e.printStackTrace();
        }
        return result;
    }

    public String getFilePath(HttpServletRequest request,String data){
       String filePath = request.getSession().getServletContext().getRealPath("file")+File.separator+data+File.separator;
       return filePath;
    }

    private Map<String,Object> writeFile(MultipartFile file, HttpServletRequest request, HttpServletResponse reponse)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String data = format.format(new Date());
        String filePath = getFilePath(request,data);
        String name = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveFileName="";
        String fileName = request.getParameter("returnFileName");
        if(fileName!=null) {   
        	fileName = URLDecoder.decode(fileName, "utf-8").trim();
        	filePath+=uuid.replace("-","")+File.separator;
        	saveFileName=fileName+name.substring(name.lastIndexOf("."),name.length());
        }else {
        	saveFileName=uuid.replace("-","")+name.substring(name.lastIndexOf("."),name.length());
        }
        File writeFile = new File(filePath+saveFileName);
        if (!writeFile.getParentFile().exists()) {
            writeFile.getParentFile().mkdirs();
        }
        file.transferTo(writeFile);
        String url = request.getScheme() + "://" + Constants.UPLOAD_URL + request.getContextPath() +File.separator+ "file"+File.separator+data+File.separator;
        if(fileName!=null) { 
        	map.put("url",url+uuid.replace("-","")+File.separator+saveFileName);
        }else {
        	map.put("url",url+saveFileName);
        }
        map.put("name",name);
        return map;
    }

}
