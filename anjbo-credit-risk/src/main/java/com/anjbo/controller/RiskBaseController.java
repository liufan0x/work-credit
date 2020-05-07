package com.anjbo.controller;

import com.anjbo.common.*;
import com.anjbo.service.RiskBaseService;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/credit/risk/base/v")
public class RiskBaseController extends BaseController{

	private static final Log log = LogFactory.getLog(RiskBaseController.class);

	@Resource
	private RiskBaseService riskBaseService;
	
	@ResponseBody
	@RequestMapping("/addImg")
	public RespData<Map<String,Object>> addImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespData<Map<String,Object>> result = new RespData<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!map.containsKey("imgType")){
				result.setMsg("请填写新增图片类型");
				return result;
			} else if(!map.containsKey("orderNo")){
				result.setMsg("新增图片缺少订单编号");
				return result;
			} else if(!map.containsKey("imgUrl")){
				result.setMsg("图片地址不能为空");
				return result;
			}
			List<Map<String,Object>> list = riskBaseService.insertImg(map);
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/delImg")
	public RespData<Map<String,Object>> delImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespData<Map<String,Object>> result = new RespData<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!map.containsKey("id")){
				result.setMsg("图片id不能为空");
				return result;
			} 
			List<Map<String,Object>> list = riskBaseService.deleteImgById(map);
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/orderIsBack")
	public RespDataObject<Boolean> orderIsBack(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Boolean> result = new RespDataObject<Boolean>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		result.setData(false);
		try{
			if(StringUtils.isBlank(MapUtils.getString(map, "orderNo", ""))
					||StringUtils.isBlank(MapUtils.getString(map, "processId",""))){
				return result;
			}
			boolean isBack = isBack(MapUtils.getString(map, "orderNo"),MapUtils.getString(map, "processId"));
			result.setData(isBack);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 华融申请信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyInformation")
	public RespStatus applyInformation(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

		} catch (Exception e){
			log.error("分配资金方保存华融申请信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融快鸽提单信息--业务信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgAppoint")
	public RespStatus kgAppoint(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

		} catch (Exception e){
			log.error("分配资金方保存华融快鸽提单业务信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融快鸽提单信息--借款人信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgIndiv")
	public RespStatus kgIndiv(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

		} catch (Exception e){
			log.error("分配资金方保存华融借款人信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融快鸽提单信息--审批信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgApproval")
	public RespStatus kgApproval(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

		} catch (Exception e){
			log.error("分配资金方保存华融审批信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融快鸽提单信息--房产信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgHouse")
	public RespStatus kgHouse(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{

		} catch (Exception e){
			log.error("分配资金方保存华融房产信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融快鸽提单信息--影像资料
	 * @param request
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgImage")
	public RespDataObject<String> kgImage(HttpServletRequest request, @RequestBody final List<Map<String,Object>> list){
		RespDataObject<String> result = new RespDataObject<String>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		try{
			if(null!=list&&list.size()>0){
				Map<String,Object> map = list.get(0);
				final  String orderNo = MapUtils.getString(map,"orderNo","");

				scheduledThreadPool.schedule(new Runnable() {
					public void run() {
						httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/hr/v/fileApply", list, RespDataObject.class);
					}
				}, 0, TimeUnit.SECONDS);
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("分配资金方保存华融影像信息异常,异常信息为:",e);
		} finally {
			if(null!=scheduledThreadPool){
				scheduledThreadPool.shutdown();
			}
		}
		return result;
	}
	/**
	 * 华融-放款信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgLoan")
	public RespStatus kgLoan(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
            map = nullStringToString(map);
			result =  httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/third/api/hr/v/lend", map);
		} catch (Exception e){
			log.error("分配资金方保存华融放款信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融-订单信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/allApply")
	public RespStatus allApply(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(map.containsKey("kgAppoint")){
				Object kgAppoint = MapUtils.getObject(map,"kgAppoint");
				Map<String,Object> tmp = null;
				if(kgAppoint instanceof Map){
					tmp = (Map<String,Object>)kgAppoint;
				}
				tmp = mappingBankName(tmp);
				if(null!=tmp){
					map.put("kgAppoint",tmp);
				}
			}
			System.out.println("接收数据为:"+map);
			map = nullStringForString(map);
			System.out.println("=========================================");
			System.out.println("格式转换之后:"+map);
			result =  httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/third/api/hr/v/apply", map);
			System.out.println("=============="+result+"==================");
		} catch (Exception e){
			log.error("分配资金方保存华融放款信息异常,异常信息为:",e);
		}
		return result;
	}
	public Map<String,Object> nullStringForString(Map<String,Object> map){
		if(MapUtils.isEmpty(map))return map;
		Map<String,Object> tmp = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Set<Map.Entry<String, Object>> entrySet = map.entrySet();
			String value = "";
			for(Map.Entry<String,Object> entry:entrySet){
				String key = entry.getKey();
				Object obj = map.get(key);
				if(obj instanceof Map){
					tmp = (Map<String,Object>)obj;
					Set<String> tmpKsys = tmp.keySet();
					for(String k:tmpKsys){
						value = MapUtils.getString(tmp,k,"");
						tmp.put(k,"null".equals(value)?"":value);
					}
					resultMap.put(key,tmp);
				} else {
					resultMap.put(key,obj);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			return map;
		}
		return resultMap;
	}
	public Map<String,Object> nullStringToString(Map<String,Object> map){
        if(MapUtils.isEmpty(map))return map;
        Map<String,Object> tmp = new HashMap<String,Object>();
	    try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            json = json.replaceAll("null","");
            tmp = gson.fromJson(json,Map.class);
        } catch (Exception e){
	        e.printStackTrace();
	        return map;
        }
        return tmp;
    }

	/**
	 * 华融-查询订单信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryApply")
	public RespDataObject<Map<String,Object>> queryApply(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map,"orderNo",""))){
				result.setMsg("查询订单信息缺少参数!");
				return result;
			}
			//result =  httpUtil.getRespDataObject(Enums.MODULAR_URL.CREDIT.toString(), "/credit/third/api/hr/queryApply", map, Map.class);
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT,"/credit/third/api/hr/v/queryApply",map);
			result = new Gson().fromJson(jsons.toString(), RespDataObject.class);
		} catch (Exception e){
			log.error("分配资金方查询华融订单信息异常,异常信息为:",e);
		}
		return result;
	}

	/**
	 * 华融-查询放款信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryLend")
	public RespDataObject<Map<String,Object>> queryLend(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map,"orderNo",""))){
				result.setMsg("查询订单信息缺少参数!");
				return result;
			}
			result =  httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/hr/v/queryLend", map, Map.class);
		} catch (Exception e){
			log.error("分配资金方查询华融放款信息异常,异常信息为:",e);
		}
		return result;
	}

	private Map<String,Object> mappingBankName(Map<String,Object> tmp){
		if(null==tmp||tmp.size()<=0)return tmp;

		List<Integer> bankList = new ArrayList<Integer>();
		List<Integer> suBankList = new ArrayList<Integer>();
		String yOriBank = "";
		String yOriSubBank = "";
		Integer oldLoanBankNameId = -1;
		Integer oldLoanBankSubNameId = -1;
		if(null!=MapUtils.getObject(tmp,"oldLoanBankNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"oldLoanBankNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"oldLoanBankNameId",""))){
			oldLoanBankNameId = MapUtils.getInteger(tmp,"oldLoanBankNameId",-1);
		}
		if(null!=MapUtils.getObject(tmp,"oldLoanBankSubNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"oldLoanBankSubNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"oldLoanBankSubNameId",""))){
			oldLoanBankSubNameId = MapUtils.getInteger(tmp,"oldLoanBankSubNameId",-1);
		}
		bankList.add(oldLoanBankNameId);
		suBankList.add(oldLoanBankSubNameId);

		String xLoanBank = "";
		String xLoanSubBank = "";
		Integer loanBankNameId = -1;
		Integer loanSubBankNameId = -1;
		if(null!=MapUtils.getObject(tmp,"loanBankNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"loanBankNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"loanBankNameId",""))){
			loanBankNameId = MapUtils.getInteger(tmp,"loanBankNameId",-1);
		}
		if(null!=MapUtils.getObject(tmp,"loanSubBankNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"loanSubBankNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"loanSubBankNameId",""))){
			loanSubBankNameId = MapUtils.getInteger(tmp,"loanSubBankNameId",-1);
		}

		bankList.add(loanBankNameId);
		suBankList.add(loanSubBankNameId);

		Integer bankNameId = -1;
		Integer bankSubNameId = -1;

		if(null!=MapUtils.getObject(tmp,"bankNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"bankNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"bankNameId",""))){
			bankNameId = MapUtils.getInteger(tmp,"bankNameId",-1);
		}
		if(null!=MapUtils.getObject(tmp,"bankSubNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"bankSubNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"bankSubNameId",""))){
			bankSubNameId = MapUtils.getInteger(tmp,"bankSubNameId",-1);
		}

		String fOpenBank = "";
		String fOpenSubBank = "";
		bankList.add(bankNameId);
		suBankList.add(bankSubNameId);

		Integer paymentBankNameId = -1;
		Integer paymentBankSubNameId = -1;
		String hOpenBank = "";
		String hOpenSubBank = "";

		if(null!=MapUtils.getObject(tmp,"paymentBankNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"paymentBankNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"paymentBankNameId",""))){
			paymentBankNameId = MapUtils.getInteger(tmp,"paymentBankNameId",-1);
		}
		if(null!=MapUtils.getObject(tmp,"paymentBankSubNameId")
				&&!"null".equals(MapUtils.getObject(tmp,"paymentBankSubNameId"))
				&&StringUtils.isNotBlank(MapUtils.getString(tmp,"paymentBankSubNameId",""))){
			paymentBankSubNameId = MapUtils.getInteger(tmp,"paymentBankSubNameId",-1);
		}

		bankList.add(paymentBankNameId);
		suBankList.add(paymentBankSubNameId);


		List<Map<Integer,Object>> banks = AllocationFundController.getBank(bankList,suBankList);

		for(Map<Integer,Object> m:banks){
			if(StringUtils.isBlank(hOpenBank)&&StringUtils.isNotBlank(MapUtils.getString(m,paymentBankNameId,""))){
				hOpenBank = MapUtils.getString(m,paymentBankNameId);
				continue;
			}
			if(StringUtils.isBlank(hOpenSubBank)&&StringUtils.isNotBlank(MapUtils.getString(m,paymentBankSubNameId,""))){
				hOpenSubBank = MapUtils.getString(m,paymentBankSubNameId);
				continue;
			}
			if(StringUtils.isBlank(fOpenBank)&&StringUtils.isNotBlank(MapUtils.getString(m,bankNameId,""))){
				fOpenBank = MapUtils.getString(m,bankNameId);
				continue;
			}
			if(StringUtils.isBlank(fOpenSubBank)&&StringUtils.isNotBlank(MapUtils.getString(m,bankSubNameId,""))){
				fOpenSubBank = MapUtils.getString(m,bankSubNameId);
				continue;
			}
			if(StringUtils.isBlank(xLoanBank)&&StringUtils.isNotBlank(MapUtils.getString(m,loanBankNameId,""))){
				xLoanBank = MapUtils.getString(m,loanBankNameId);
				continue;
			}
			if(StringUtils.isBlank(xLoanSubBank)&&StringUtils.isNotBlank(MapUtils.getString(m,loanSubBankNameId,""))){
				xLoanSubBank = MapUtils.getString(m,loanSubBankNameId);
				continue;
			}
			if(StringUtils.isBlank(yOriBank)&&StringUtils.isNotBlank(MapUtils.getString(m,oldLoanBankNameId,""))){
				yOriBank = MapUtils.getString(m,oldLoanBankNameId);
				continue;
			}
			if(StringUtils.isBlank(yOriSubBank)&&StringUtils.isNotBlank(MapUtils.getString(m,oldLoanBankSubNameId,""))){
				yOriSubBank = MapUtils.getString(m,oldLoanBankSubNameId);
			}
		}
		if(StringUtils.isNotBlank(hOpenBank)&&StringUtils.isNotBlank(hOpenSubBank)){
			hOpenBank = hOpenBank+"-"+hOpenSubBank;
		}
		if(StringUtils.isNotBlank(fOpenBank)&&StringUtils.isNotBlank(fOpenSubBank)){
			fOpenBank = fOpenBank+"-"+fOpenSubBank;
		}
		if(StringUtils.isNotBlank(xLoanBank)&&StringUtils.isNotBlank(xLoanSubBank)){
			xLoanBank = xLoanBank+"-"+xLoanSubBank;
		}
		if(StringUtils.isNotBlank(yOriBank)&&StringUtils.isNotBlank(yOriSubBank)){
			yOriBank = yOriBank+"-"+yOriSubBank;
		}
		if("是".equals(MapUtils.getString(tmp,"yIsBank",""))){
			tmp.put("yOriBank",yOriBank);
		} else {
			tmp.put("yOriBank",MapUtils.getString(tmp,"oldAddress"));
		}

		if("是".equals(MapUtils.getString(tmp,"xIsBank",""))){
			tmp.put("xLoanBank",xLoanBank);
		} else {
			tmp.put("xLoanBank",MapUtils.getString(tmp,"address"));
		}
		tmp.put("fOpenBank",fOpenBank);
		tmp.put("hOpenBank",hOpenBank);
		return tmp;
	}
}
