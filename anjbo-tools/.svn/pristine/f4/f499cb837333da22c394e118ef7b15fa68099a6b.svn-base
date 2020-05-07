package com.anjbo.controller.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.DegreeBuildingDto;
import com.anjbo.bean.tools.DegreeHouseNumDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.processor.DegreeHouseNumberProcessor;
import com.anjbo.utils.FileUtil;
import com.anjbo.utils.ImageUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/tools/housenumber")
public class DegreeHouseNumberController {
    @Resource
    private DegreeHouseNumberProcessor degreeHouseNumberProcessor;
    
    Logger log = Logger.getLogger("DegreeHouseNumberController");
   
    /**
	 * 生成验证码并获取第三方服务器的cookie
	 * @Title: authCode 
	 * @param request
	 * @return
	 * RespStatus
	 * @throws
	 */
    @RequestMapping(value = "/authCode")
	public @ResponseBody
	RespDataObject<Map<String,Object>> authCode(HttpServletRequest request,@RequestBody Map<String,Object> params) {
    	String urlPath = "http://szjzz.ga.sz.gov.cn/servlet/validateCodeServlet";
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getCode());
		try {
			//String cookie = MapUtils.getString(params, "_key");
			String url = "/code/degree/" + UUID.randomUUID().toString()
					+ ".jpg";
			String path = request.getSession().getServletContext()
					.getRealPath(url);
			String cookieValue = ImageUtil.saveToFile(
					urlPath, path);
			request.getSession().setAttribute("DEGREECOOKIEVALUE",
					cookieValue);
			log.info("生成验证码返回cookieValue=" + cookieValue);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("cookieValue", cookieValue);
			status.setData(map);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(url + "?" + Math.random());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

    /**
     * @param houseNumDto
     * @return 根据条件查询楼栋信息
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value = "/findBuilding")
    @ResponseBody
    public RespData<DegreeBuildingDto> findBuilding(HttpServletRequest request,@RequestBody final DegreeHouseNumDto houseNumDto){
    	RespData<DegreeBuildingDto> status = new RespData<DegreeBuildingDto>();
    	if(StringUtils.isEmpty(houseNumDto.getRegion())||
    			StringUtils.isEmpty(houseNumDto.getStreet())||
    			StringUtils.isEmpty(houseNumDto.getPropertyName())||
    			StringUtils.isEmpty(houseNumDto.getCookieValue())||
    			StringUtils.isEmpty(houseNumDto.getValidateCode())){
    		status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
    		status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
    		return status;
    	}
    	try {
    		/*String cookieValue = (String) request.getSession().getAttribute("DEGREECOOKIEVALUE");
    		houseNumDto.setCookieValue(cookieValue);*/
			return degreeHouseNumberProcessor.findBuilding(houseNumDto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.THIRD_FAIL.getMsg());
		return status;
    }

    /**
     * @param houseNumDto     区域编码
     * @return 根据条件查询房屋编码
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value = "/findHouseNumber")
    @ResponseBody
    public RespData<DegreeHouseNumDto> findHouseNumber(@RequestBody final DegreeHouseNumDto houseNumDto){
    	RespData<DegreeHouseNumDto> status = new RespData<DegreeHouseNumDto>();
    	if(StringUtils.isEmpty(houseNumDto.getRegion())||
    			StringUtils.isEmpty(houseNumDto.getStreet())||
    			StringUtils.isEmpty(houseNumDto.getBuildingId())||
    			StringUtils.isEmpty(houseNumDto.getCookieValue())||
    			StringUtils.isEmpty(houseNumDto.getValidateCode())){
    		status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
    		status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
    		return status;
    	}
    	if(houseNumDto.getRoomNum()==null){
    		houseNumDto.setRoomNum("");//查询全部
    	}
    	try {
			return degreeHouseNumberProcessor.findHouseNumber(houseNumDto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		status.setCode(RespStatusEnum.THIRD_FAIL.getCode());
		status.setMsg(RespStatusEnum.THIRD_FAIL.getMsg());
		return status;
       
    }
    /**
     * 初始化区域街道信息
     * 
     * @user Administrator
     * @date 2017-8-31 下午05:21:28 
     * @return
     */
    @RequestMapping(value = "/initRegionStreet")
    @ResponseBody
    public RespDataObject<JSONArray> initRegionStreet(){
    	String JsonContext = FileUtil.ReadFile("degree.json");
		JSONArray jsonArray = JSONArray.fromObject(JsonContext);
		RespDataObject<JSONArray> status = new RespDataObject<JSONArray>();
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		status.setData(jsonArray);
		return status;
    }
}
