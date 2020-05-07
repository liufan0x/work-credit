package com.anjbo.controller.signature;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.signature.PlatformSignService;
import com.anjbo.service.signature.SignatureService;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.signature.ElectronSignatureAbutment;
import com.google.gson.Gson;
import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.SignType;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/29.
 */
@Controller
@RequestMapping("/credit/third/api/signature")
public class ElectronSignatureController extends BaseController{

    private Logger log = Logger.getLogger(getClass());
    @Resource
    private PlatformSignService platformSignService;
    @Resource
    private SignatureService signatureService;

    /**
     * 电子签章
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/stamp")
    public RespDataObject<Map<String,Object>> stamp(HttpServletRequest request, @RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Object cusList = MapUtils.getObject(map,"cusList");
            String signatureImg = MapUtils.getString(map,"signatureImg");
            if(null==cusList|| StringUtil.isBlank(signatureImg)){
                RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            } else if(!signatureImg.endsWith(".pdf")){
                RespHelper.setFailRespStatus(result, "请上传pdf文件");
                return result;
            }
            UserDto user = getUserDto(request);
            Gson gson = new Gson();
            List<Map<String,Object>> list = gson.fromJson(gson.toJson(cusList), List.class);
            ElectronSignatureAbutment signatureAbutment = getElectronSignatureAbutment(request, user,result,false);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                RespHelper.setFailDataObject(result,null,"电子签章初始化第三方接口失败!");
                return result;
            }
            signatureAbutment.stamp(result,list,signatureImg);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("用户电子签章失败",e);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/v/initSdk")
    public RespStatus initSdk(HttpServletRequest request){
        RespStatus result = new RespStatus();
        try{
            UserDto user = getUserDto(request);
            ElectronSignatureAbutment signatureAbutment = getElectronSignatureAbutment(request, user,result,true);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
                return result;
            }
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            log.error("电子签章初始化第三方异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/v/test")
    public String test(HttpServletRequest request){
        RespStatus respStatus = new RespStatus();
        /**
         * 程祺福 310101198110098231 C49CF1FA6FFA48F9A8D520634451932E
         */
        UserDto user = getUserDto(request);
        ElectronSignatureAbutment instance = getElectronSignatureAbutment(request,user,respStatus,false);
        /*
        PersonBean p = new PersonBean();
        p.setName("程祺福");
        p.setIdNo("310101198110098231");
        String t = platformSignService.addPersonAccount(p);
        */
        AddSealResult addSealResult = platformSignService.addPersonTemplateSeal("C49CF1FA6FFA48F9A8D520634451932E");
        ByteArrayOutputStream bo = null;
        RespDataObject<Map<String, Object>> upload = null;
        try {
           // byte[] bytes = BASE64.decodeBase64(addSealResult.getSealData());
            File file = new File("D:\\gd.pdf");
            FileInputStream fi = new FileInputStream(file);
            bo = new ByteArrayOutputStream();
            byte[] bt = new byte[1024];
            int len;
            while ((len=fi.read(bt))!=-1){
                bo.write(bt,0,len);
            }
            bo.flush();
            PosBean posBean  = new PosBean();
            posBean.setPosPage("5");
            posBean.setPosY(365);
            posBean.setPosX(175);

            FileDigestSignResult tmp = platformSignService.userPersonSignByStream(bo.toByteArray(), "C49CF1FA6FFA48F9A8D520634451932E", addSealResult.getSealData(), posBean, SignType.Single);
            posBean  = new PosBean();
            posBean.setPosPage("5");
            posBean.setPosY(365);
            posBean.setPosX(300);

            tmp = platformSignService.userPersonSignByStream(tmp.getStream(), "C49CF1FA6FFA48F9A8D520634451932E", addSealResult.getSealData(), posBean, SignType.Single);
            FileOutputStream fo = new FileOutputStream(new File("D:\\gdt.pdf"));
            byte[] stream = tmp.getStream();
            fo.write(stream);
            fo.flush();

            //upload = instance.upload(bo.toByteArray(),"gd.pdf");
            return "";
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != bo) {
                    bo.close();
                    bo = null;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null==upload?"":upload.getData().toString();
    }


    public ElectronSignatureAbutment getElectronSignatureAbutment(HttpServletRequest request, UserDto user, RespStatus respStatus,boolean isInit){
        ElectronSignatureAbutment instance = ElectronSignatureAbutment.getInstance();
        instance.initService(platformSignService,signatureService,user);
        String resultCode = instance.initSdk(isInit);
        respStatus.setCode(resultCode);
        return instance;
    }
    
    /**
     * 获取电子签章图片
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getImageUrl")
    public RespDataObject<Map<String,Object>> getImageUrl(HttpServletRequest request, @RequestBody Map<String,Object> params){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            UserDto user = new UserDto();
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            list.add(params);
            ElectronSignatureAbutment signatureAbutment = getElectronSignatureAbutment(request, user,result,false);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                RespHelper.setFailDataObject(result,null,"电子签章初始化第三方接口失败!");
                return result;
            }
            signatureAbutment.createAndReturnSignatureUrl(result, list);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("用户电子签章失败",e);
        }
        return result;
    }
}
