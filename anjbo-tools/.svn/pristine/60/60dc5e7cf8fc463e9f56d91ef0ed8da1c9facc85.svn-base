package com.anjbo.processor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.anjbo.bean.tools.HouseAssess;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;

/**
 * 房产评估（查询过户价）处理类
 * @author limh
 * <div style="float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;">
 * <span style="width:100%;float:left;">查询结果如下：您的房产评估价格为 <font style="color:red;font-size:12px">21275</font>元/平方米，有效时间自2013年10月08日起，至下次调整之日止。</span>
 * </div>
 * <div style="float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;">
 * 您输入的信息有误，未查到数据。请确认身份证号及房产证号后再次查询，如有疑问请参阅温馨提示
 * </div>
 */
public class HouseAssessProcessor {
	private Logger log = Logger.getLogger(getClass());
	public final static String  url = "http://fj.szpgzx.com:8010/jgcx/index.jsp";
	public final static String  authCodeUrl = "http://fj.szpgzx.com:8010/jgcx/login/CertPicture.jpg";
	
	public RespDataObject<Double> getHouseAssessResult(HouseAssess houseAssess){
		RespDataObject<Double> rdo = new RespDataObject<Double>();
		StringBuffer sb = new StringBuffer(url);
		sb.append(houseAssess.getParams());
		try {
			String result = HttpUtil.get(sb.toString(),houseAssess.getCookieValue(),url);
			if(StringUtils.isEmpty(result)){
				rdo.setCode(RespStatusEnum.FAIL.getCode());
				rdo.setMsg("未查询到相关信息，请检查相关信息是否无误！");
				return rdo;
			}
			result = result.replace(" ","");
			result = result.replace("\n","");
			result = result.replace("\t","");
			//log.info("房产评估返回结果："+result);
			result = StringUtil.processLocation(result, "<divstyle=\"float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;\">(.*?)</div>");
			rdo.setCode(RespStatusEnum.SUCCESS.getCode());
			if(result.startsWith("<span")){
				result = StringUtil.processLocation(result,"<spanstyle\\=\"width:100%;float:left;\">(.*?)<\\/span>");
				String unitPrice = StringUtil.processLocation(result,"<fontstyle\\=\"color:red;font-size:12px\">(.*?)<\\/font>");
				rdo.setData(NumberUtils.toDouble(unitPrice));
			}else if(result.contains("验证码错误")||result.contains("您输入的信息有误")||
					result.contains("系统繁忙，请您稍候再试。")){
				rdo.setCode(RespStatusEnum.FAIL.getCode());
				rdo.setMsg(result);
			}else{
				rdo.setCode(RespStatusEnum.FAIL.getCode());
				rdo.setMsg("查询失败，请稍后再试！");
			}
			rdo.setMsg(result);
		} catch (Exception e) {
			rdo.setCode(RespStatusEnum.FAIL.getCode());
			rdo.setMsg("系统异常，请稍后再试！");
			e.printStackTrace();
		}
		return rdo;
	}
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div style=\"float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;\">");
		sb.append("   ");
		sb.append("您输入的信息有误，未查到数据。请确认身份证号及房产证号后再次查询，如有疑问请参阅温馨提示");
		sb.append("		");
		sb.append("		");
		sb.append("</div>");
		String result="<div style=\"float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;\">验证码错误，请正确输入的验证码信息。</div>";
		result = sb.toString();
		System.out.println(result);
		result = StringUtil.processLocation(result, "<div style=\"float:left;padding-left:2px;color:#9c9c9c;margin-top:10px;line-height:20px;width:400px;position:relative;\">(.*?)</div>");
		System.out.println(result);
		
	}
}
