package com.anjbo.controller.yntrust;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.BASE64;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/9.
 */
@Controller
@RequestMapping("/fs/yntrust")
public class PlusImgControoler {
    /**
     * 返回图片的BASE64数据
     * @param request
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("/getImgBaseCode")
    public RespDataObject<List<Map<String,Object>>> getImgBaseCode(HttpServletRequest request, @RequestBody List<Map<String,Object>> list){
        RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
        try {

            String path = getRootPath(request);
            String isNull = changeBase64(path,list,true,false);
            if(StringUtil.isNotBlank(isNull)){
                isNull = isNull.substring(0,isNull.length()-1);
                RespHelper.setFailRespStatus(result,"该文件:"+isNull+"没有找到");
                return result;
            }
            RespHelper.setSuccessDataObject(result,list);
        } catch (Exception e){
            e.printStackTrace();
            RespHelper.setFailDataObject(result,null,RespStatusEnum.SYSTEM_ERROR.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getFileBytes")
    public RespDataObject<List<Map<String,Object>>> getImgBytes(HttpServletRequest request, @RequestBody List<Map<String,Object>> list){
        RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
        try {
            String path = getRootPath(request);
            String isNull = changeBase64(path,list,false,true);
            if(StringUtil.isNotBlank(isNull)){
                isNull = isNull.substring(0,isNull.length()-1);
                RespHelper.setFailRespStatus(result,"该文件:"+isNull+"没有找到");
                return result;
            }
            RespHelper.setSuccessDataObject(result,list);
        } catch (Exception e){
            e.printStackTrace();
            RespHelper.setFailDataObject(result,null,RespStatusEnum.SYSTEM_ERROR.getMsg());
        }
        return result;
    }

    public String changeBase64(String path,List<Map<String,Object>> list,boolean isBase64,boolean isByte)throws Exception{
        File file = null;
        FileInputStream fi = null;
        ByteArrayOutputStream output = null;
        byte[] b;
        int len;
        String img = "";
        String base64 = "";
        String isNull = "";
        String tmpImg = "";
        try {
            for (Map<String,Object> map:list){
                len = 0;
                b = new byte[1024];
                img = MapUtils.getString(map,"url");
                tmpImg = MapUtils.getString(map,"url");
                img = getImgPath(path,img);
                file = new File(img);
                if(!file.exists()){
                    isNull += tmpImg+",";
                   continue;
                }
                fi = new FileInputStream(file);
                output = new ByteArrayOutputStream();
                while ((len=fi.read(b))!=-1){
                    output.write(b,0,len);
                }
                output.flush();
                if(isBase64) {
                    base64 = BASE64.encodeBase64(output.toByteArray());
                    map.put("content", base64);
                }
                if(isByte) {
                    base64 = BASE64.encodeBase64(output.toByteArray());
                    map.put("bytearr", base64);
                }
            }
        } catch (Exception e){
            throw new Exception(e);
        } finally {
            try{
                if(null!=output){
                    output.close();
                    output = null;
                }
                if(null!=fi){
                    fi.close();
                    fi = null;
                }
            } catch (Exception e){
                throw new Exception(e);
            }
        }
        return isNull;
    }


    public String getRootPath(HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath(File.separator);
        path = path.replaceAll("/",File.separator);
        if(!path.endsWith(File.separator)){
            path = path+File.separator;
        }
        return path;
    }
    public String getImgPath(String path,String img){
        String url = subUrl(img);
        return path+url;
    }

    public String subUrl(String img){
        img = img.replace("/",File.separator);
        String url = img.replace("_18.jpg", "_48.jpg")
                .replace("http:"+File.separator+File.separator+"fs.zxsf360.com"+File.separator, "")
                .replace("http:"+File.separator+File.separator+"fs.anjbo.com"+File.separator, "")
                .replace("http:"+File.separator+File.separator+"182.254.149.92:9206"+File.separator, "")
                .replace("http:"+File.separator+File.separator+"182.254.149.92:2092"+File.separator,"")
                .replace("http:"+File.separator+File.separator+"fsnc.anjbo.com"+File.separator,"");
        return url;
    }

    public static void main(String[] args) {
        String url = "http://fs.anjbo.com/img/fc-img/5f58ab54937340bf8284a02816308504_18.png";
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> t = new HashMap<String,Object>();
        t.put("orderNo","2017070710381700000");
        t.put("type","A");
        t.put("url","http://fs.anjbo.com/img/fc-img/5f58ab54937340bf8284a02816308504_18.png");
        list.add(t);
        try {
            String json = new ObjectMapper().writeValueAsString(list);
            System.out.println(json);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
