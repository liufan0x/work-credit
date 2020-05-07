package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.risk.BaiduRiskListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.BaiduRiskListService;
import com.anjbo.utils.HttpUtil;
import com.jhlabs.math.BlackFunction;

@RequestMapping("/credit/risk/riskList/v")
@Controller
public class BaiduRiskListController extends BaseController {
	Logger log = Logger.getLogger(BaiduRiskListController.class);
	@Resource
	private BaiduRiskListService baiduRiskListService;
	/**
	 * 列表
	 * @param request
	 * @param response
	 * @param baiduRiskListDto
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/page")
	public RespPageData<BaiduRiskListDto> page(HttpServletRequest request,
			HttpServletResponse response,@RequestBody BaiduRiskListDto baiduRiskListDto) {
		
		RespPageData<BaiduRiskListDto> result= new RespPageData<BaiduRiskListDto>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			List<BaiduRiskListDto> riskList = baiduRiskListService.selectBaiduRiskList(baiduRiskListDto);
			result.setRows(riskList);
			result.setTotal(baiduRiskListService.selectBaiduRiskListCount(baiduRiskListDto));
			result.setCode(RespStatusEnum.SUCCESS.getCode());
	    	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("风险名单列表查询异常,错误信息为 ==>"+e.getMessage());
		}
		return result;
	}
	
	 /**获取总条数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRiskListCount")
    public RespDataObject<Integer> getRiskListCount(HttpServletRequest request,
			HttpServletResponse response,@RequestBody BaiduRiskListDto baiduRiskListDto){
    	RespDataObject<Integer> result = new RespDataObject<Integer>();
    	try {
			int count = baiduRiskListService.selectBaiduRiskListCount(baiduRiskListDto);
			result.setData(count);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("风险名单列表总数量查询异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
    
    /**
     * 详情
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail")
	public RespDataObject<Map<String, Object>> detail(HttpServletRequest request,HttpServletResponse response,@RequestBody BaiduRiskListDto baiduRiskListDto) {
    	RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	Map<String, Object> map=new HashMap<String, Object>();
    	try {
    		if(baiduRiskListDto.getId()==null||baiduRiskListDto.getId()==0){
    			result.setMsg("id不能为空");
    			return result;
    		}
    		baiduRiskListDto = baiduRiskListService.getBaiduRiskById(baiduRiskListDto.getId());
    		if(baiduRiskListDto.getBlackDetails()!=null&&!"".equals(baiduRiskListDto.getBlackDetails())){
    			baiduRiskListDto.setBlackDetails(baiduRiskListDto.getBlackDetails().replace("\n", "<br>"));
        		if(baiduRiskListDto.getBlackDetails().indexOf("<br>")==0){
        			baiduRiskListDto.setBlackDetails(baiduRiskListDto.getBlackDetails().substring(4));
        		}
    		}
    		map.put("baiduRiskListDto", baiduRiskListDto);
    		result.setData(map);
    		result.setCode(RespStatusEnum.SUCCESS.getCode());
        	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("风险名单详情查询异常,错误信息为 ==>"+e.getMessage());
		}
    	return result;
    }
    
    /**
     * 添加
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addRiskList")
	public @ResponseBody
	RespDataObject<Map<String, Object>> addBaiduRiskList(HttpServletRequest request,@RequestBody Map<String,Object> map) {
    	RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto userDto=getUserDto(request);  //获取用户信息
			BaiduRiskListDto baiduRiskListDto = new BaiduRiskListDto();
			baiduRiskListDto.setName(MapUtils.getString(map, "name"));
			baiduRiskListDto.setIdentity(MapUtils.getString(map, "identity"));
			baiduRiskListDto.setPhone(MapUtils.getString(map, "phone"));
			//查询百度风险名单
			JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "credit/third/api/baidu/risklist/search", map);
			System.out.println("第三方接口项目返回："+json.toString());
			if(json.get("retCode")!=null&&"0".equals(json.getString("retCode"))){
				JSONObject resultStr =  json.getJSONObject("result");
				if(resultStr!=null){
					String blackLevel = "".equals(resultStr.getString("blackLevel"))?"无":resultStr.getString("blackLevel");
					String blackReason = "".equals(resultStr.getString("blackReason"))?"无":resultStr.getString("blackReason");
					String blackDetails = "".equals(resultStr.getString("blackDetails"))?"无":resultStr.getString("blackDetails");
					baiduRiskListDto.setBlackLevel(blackLevel);
					baiduRiskListDto.setBlackReason(blackReason);
					baiduRiskListDto.setBlackDetails(blackDetails);
				}
			}else{
				result.setMsg(json.getString("retMsg"));
				return result;
			}
			baiduRiskListDto.setCreateUid(userDto==null?MapUtils.getString(map, "uid"):userDto.getUid());
			if(null!=MapUtils.getString(map, "orderNo")){
				baiduRiskListDto.setOrderNo(MapUtils.getString(map, "orderNo"));
			}
			BaiduRiskListDto baiduRiskListDtoTemp = new BaiduRiskListDto();
			baiduRiskListDtoTemp.setIdentity(MapUtils.getString(map, "identity"));
			int i = baiduRiskListService.selectBaiduRiskListCount(baiduRiskListDtoTemp);
			if(i>0){
				result.setMsg("身份证号不能重复");
				return result;
			}
			
			BaiduRiskListDto baiduRiskListDtoT = new BaiduRiskListDto();
			baiduRiskListDtoT.setPhone(MapUtils.getString(map, "phone"));
			int j = baiduRiskListService.selectBaiduRiskListCount(baiduRiskListDtoT);
			if(j>0){
				result.setMsg("手机号不能重复");
				return result;
			}
			
			baiduRiskListService.insertBaiduRiskList(baiduRiskListDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
	    	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加风险名单异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
    
    /**
     * 删除
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRiskListById")
	public RespStatus deleteMonitorArchiveById(HttpServletRequest request,@RequestBody BaiduRiskListDto baiduRiskListDto) {
    	RespStatus result = new RespStatus();
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	result.setCode(RespStatusEnum.FAIL.getCode());
		try {
			if(baiduRiskListDto.getId()==null||baiduRiskListDto.getId()==0){
    			result.setMsg("id不能为空");
    			return result;
    		}
			baiduRiskListService.deleteBaiduRiskById(baiduRiskListDto.getId());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
	    	result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除风险名单异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
}
