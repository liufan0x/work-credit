package com.anjbo.controller.impl.signature;

import com.anjbo.bean.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.signature.IElectronSignatureController;
import com.anjbo.service.signature.PlatformSignService;
import com.anjbo.service.signature.SignatureService;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.signature.ElectronSignatureAbutment;
import com.google.gson.Gson;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/29.
 */
@RestController
public class ElectronSignatureController extends BaseController implements IElectronSignatureController {

    @Resource
    private PlatformSignService platformSignService;
    @Resource
    private SignatureService signatureService;
    @Resource
    private UserApi userApi;
    /**
     * 电子签章
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String,Object>> stamp(@RequestBody Map<String,Object> map){
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
            UserDto user = userApi.getUserDto();
            Gson gson = new Gson();
            List<Map<String,Object>> list = gson.fromJson(gson.toJson(cusList), List.class);
            ElectronSignatureAbutment signatureAbutment = getElectronSignatureAbutment(request, user,result,false);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                RespHelper.setFailDataObject(result,null,"电子签章初始化第三方接口失败!");
                return result;
            }
            signatureAbutment.stamp(result,list,signatureImg);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null, RespStatusEnum.FAIL.getMsg());
            logger.error("用户电子签章失败",e);
        }
        return result;
    }
    @Override
    public RespStatus initSdk(){
        RespStatus result = new RespStatus();
        try{
            UserDto user = userApi.getUserDto();
            ElectronSignatureAbutment signatureAbutment = getElectronSignatureAbutment(request, user,result,true);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
                return result;
            }
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            logger.error("电子签章初始化第三方异常:",e);
        }
        return result;
    }


    public ElectronSignatureAbutment getElectronSignatureAbutment(HttpServletRequest request, UserDto user, RespStatus respStatus, boolean isInit){
        ElectronSignatureAbutment instance = ElectronSignatureAbutment.getInstance();
        instance.initService(platformSignService,signatureService,user);
        String resultCode = instance.initSdk(isInit);
        respStatus.setCode(resultCode);
        return instance;
    }
    
    /**
     * 获取电子签章图片
     * @return
     */
    @Override
    public RespDataObject<Map<String,Object>> getImageUrl(@RequestBody Map<String,Object> params){
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
            RespHelper.setFailDataObject(result,null, RespStatusEnum.FAIL.getMsg());
            logger.error("用户电子签章失败",e);
        }
        return result;
    }
}
