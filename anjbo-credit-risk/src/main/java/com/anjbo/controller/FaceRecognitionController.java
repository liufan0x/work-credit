package com.anjbo.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.product.FacesignRecognitionDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;

@Controller
@RequestMapping("/credit/risk/faceRecognition/v")
public class FaceRecognitionController extends BaseController{
	

	protected Log log = LogFactory.getLog(this.getClass());
	
	@ResponseBody
    @RequestMapping(value = "/appFaceRecognition")
    public RespDataObject<FacesignRecognitionDto> appFaceRecognition(HttpServletRequest request,@RequestBody FacesignRecognitionDto face){
        RespDataObject<FacesignRecognitionDto> resp = new RespDataObject<FacesignRecognitionDto>();
        try{
            if(StringUtils.isBlank(face.getCallbackkey())||StringUtils.isBlank(face.getExuid())){
                resp.setMsg("识别信息缺少参数!");
                return resp;
            }
            //调用人脸识别接口
            RespDataObject<JSONObject>  queryResult =  httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/baidu/face/query", face, JSONObject.class);
            String score = null;
            if(RespStatusEnum.SUCCESS.getCode().equals(queryResult.getCode())){
				JSONObject json = queryResult.getData();
            	if(null!=json&&json.containsKey("imageUrl")){
                    String imgUrl = queryResult.getData().getString("imageUrl");
                    face.setImageUrl(imgUrl);
                }
                if(null!=json&&json.containsKey("score")&&StringUtils.isNotBlank(json.getString("score"))){
                    score = json.getString("score");
                    BigDecimal bigDecimal = new BigDecimal(score);
                    int isSuccess = bigDecimal.doubleValue()>=70d?1:2;
                    face.setIsSuccess(isSuccess);
				} else{
                    face.setIsSuccess(2);
                }
            } else {
                face.setIsSuccess(2);
            }
            face.setScore(score);
            RespHelper.setSuccessDataObject(resp, face);
        } catch (Exception e){
            e.printStackTrace();
            RespHelper.setFailDataObject(resp, null,RespStatusEnum.FAIL.getMsg());
            log.error("人脸识别失败",e);
        }
        return resp;
    }
	
}
