package com.anjbo.controller.baidu;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.Enums;
import com.anjbo.controller.BaseController;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.common.ThirdConfigUtil;

@Controller
@RequestMapping("/credit/third/api/baidu/risklist")
public class BaiduRiskListController extends BaseController {
	static String urls = "https://jrws.baidu.com/risk/api/info/search?sp_no=1000000001&service_id=1001&datetime=1231231231231&reqid=123&sign_type=1&sign=dc0c2bdd3ce339dde027ec6422a23ef8&name=%E5%BC%A0%E4%B8%89&identity=410153199010101234&phone=18604837593";
	
	public static void main(String[] args) {
		try {
			String json = HttpUtil.get(urls);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static final String jrwsUrl = ThirdConfigUtil.getProperty("queryRiskList.url");
	static final String sp_no = ThirdConfigUtil.getProperty("queryRiskList.sp_no");
	static final String service_id = "1001";
	static final String sign_type = "1";
	static final String key =ThirdConfigUtil.getProperty("queryRiskList.key");
	@ResponseBody
	@RequestMapping("/search")
	public JSONObject search(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String,Object> map){
		logger.info("风险名单参数"+map);
		String name =MapUtils.getString(map, "name");
		String identity=MapUtils.getString(map, "identity");
		String phone=MapUtils.getString(map, "phone");
		if(identity==null){
			JSONObject json = new JSONObject();
			json.put("retCode", 5004);
			json.put("retMsg", "参数不合法");
			json.put("result", "");
			return json;
		}
		name = (name==null||"".equals(name))?"默认名字":name;
		phone = (phone==null||"".equals(phone))?"18617000206":phone;
		long datetime = new Date().getTime();
		int reqids = (int)(Math.random()*900)+100;
		String reqid = String.valueOf(reqids);
		String sign = "datetime="+datetime+"&identity="+identity+"&name="+name+"&phone="+phone+"&reqid="+reqid+"&service_id="+service_id+"&sign_type="+sign_type+"&sp_no="+sp_no+"&key="+key;
		sign = MD5Utils.MD5Encode(sign);
		String jsonStr = null;
		try {
			String url = jrwsUrl+"?datetime="+datetime+"&identity="+identity+"&name="+URLEncoder.encode(name,"UTF-8")+"&phone="+phone+"&reqid="+reqid+"&service_id="+service_id+"&sign_type="+sign_type+"&sp_no="+sp_no+"&sign="+sign;
			System.out.println("请求链接："+url);
			jsonStr = HttpUtil.get(url);
			System.out.println("返回数据："+jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//jsonStr = " {\"retCode\":0,\"retMsg\":\"OK\",\"result\":{\"blackLevel\":\"A\",\"blackReason\":\"C06BT020:C06BT021\",\"blackDetails\":{\"C06BT020\":[{\"duty\":\"2015-12\",\"performance\":\"M3+\",\"publishDate\":\"5\",\"case_no\":\"5\",\"disruptTypeName\":\"5\"}],\"C06BT021\":[{\"loan_date\":\"2015-12\",\"odu_level\":\"M3+\",\"odu_amount_level\":\"5\"}]}}}";
		JSONObject json = JSONObject.fromObject(jsonStr);
		if(json.getInt("retCode")==0){
			JSONObject result = json.getJSONObject("result");
			String blackLevel = result.getString("blackLevel");
			String blackReason = result.getString("blackReason");
			String details="";
			//风险名单原因
			if(blackReason!=null&&!"".equals(blackReason)){
				String[] str = blackReason.split(":");
				if(str.length>0){
					blackReason="";
					for (String string : str) {
						blackReason+=","+Enums.RISKLIST_ENUMS.getNameByCode(string);
					}
					blackReason = blackReason.substring(1);
				}
				//风险名单详情
				JSONObject blackDetails = result.getJSONObject("blackDetails");
				if(blackDetails!=null&&!"".equals(blackDetails)){
					//法院失信
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT020.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT020");
						JSONObject detail = jsonArry.getJSONObject(0);
						//执行义务
						String duty = detail.get("duty")==null?"":detail.get("duty").toString();
						//执行情况
						String performance  = detail.get("performance")==null?"":detail.get("performance").toString();
						//公开日期
						String publishDate   = detail.get("publishDate")==null?"":detail.get("publishDate").toString();
						//案件编号
						String case_no  = detail.get("case_no")==null?"":detail.get("case_no").toString();
						//违反条例
						String disruptTypeName =  detail.get("disruptTypeName")==null?"":detail.get("disruptTypeName").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT020.getName()+"\n 执行义务："+duty+"\n 执行情况:"+performance+"\n 公开日期："+publishDate+"\n 案件编号："+case_no+"\n 违反条例："+disruptTypeName+";";
					}
					//偷税漏税
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT021.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT021");
						JSONObject detail = jsonArry.getJSONObject(0);
						//税务主体
						String taxpayer_name = detail.get("taxpayer_name")==null?"":detail.get("taxpayer_name").toString();
						//纳税识别号
						String taxpayer_iden_num  = detail.get("taxpayer_iden_num")==null?"":detail.get("taxpayer_iden_num").toString();
						//公开机构
						String info_source   = detail.get("info_source")==null?"":detail.get("info_source").toString();
						//主要原因
						String major_fact  = detail.get("major_fact")==null?"":detail.get("major_fact").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT021.getName()+"\n 税务主体："+taxpayer_name+"\n 纳税识别号:"+taxpayer_iden_num+"\n 公开机构："+info_source+"\n 主要原因："+major_fact+";";
					}
					//股权冻结
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT022.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT022");
						JSONObject detail = jsonArry.getJSONObject(0);
						//执行法院
						String execute_court = detail.get("execute_court")==null?"":detail.get("execute_court").toString();
						//处罚编号
						String adjudicate_no  = detail.get("adjudicate_no")==null?"":detail.get("adjudicate_no").toString();
						//冻结金额
						String freeze_amount   = detail.get("freeze_amount")==null?"":detail.get("freeze_amount").toString();
						//处罚公开时间
						String pub_date  = detail.get("pub_date")==null?"":detail.get("pub_date").toString();
						//合作企业名称
						String corp_name = detail.get("corp_name")==null?"":detail.get("corp_name").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT022.getName()+"\n 执行法院："+execute_court+"\n 处罚编号:"+adjudicate_no+"\n 冻结金额："+freeze_amount+"\n 处罚公开时间："+pub_date+"\n 合作企业名称："+corp_name+";";
					}
					//无照经营
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT023.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT023");
						JSONObject detail = jsonArry.getJSONObject(0);
						//处罚编号
						String punishment_no = detail.get("punishment_no")==null?"":detail.get("punishment_no").toString();
						//执行原因
						String case_of_action  = detail.get("case_of_action")==null?"":detail.get("case_of_action").toString();
						//处罚部门
						String handle_org_name   =  detail.get("handle_org_name")==null?"":detail.get("handle_org_name").toString();
						//公示日期
						String pub_date  = detail.get("pub_date")==null?"":detail.get("pub_date").toString();
						//处罚内容
						String punishment_content = detail.get("punishment_content")==null?"":detail.get("punishment_content").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT023.getName()+"\n 处罚编号："+punishment_no+"\n 执行原因:"+case_of_action+"\n 处罚部门："+handle_org_name+"\n 公示日期："+pub_date+"\n 处罚内容："+punishment_content+";";
					}
					//法院被执行人
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT024.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT024");
						JSONObject detail = jsonArry.getJSONObject(0);
						//注册时间
						String reg_date = detail.get("reg_date")==null?"":detail.get("reg_date").toString();
						//案件编号
						String case_no  = detail.get("case_no")==null?"":detail.get("case_no").toString();
						//判决法院
						String court   =  detail.get("court")==null?"":detail.get("court").toString();
						//执行金额
						String exec_money  = detail.get("exec_money")==null?"":detail.get("exec_money").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT024.getName()+"\n 注册时间："+reg_date+"\n 案件编号:"+case_no+"\n 判决法院："+court+"\n 执行金额："+exec_money+";";
					}
					//行政处罚
					if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT025.getName())){
						JSONArray jsonArry =  blackDetails.getJSONArray("C06BT025");
						JSONObject detail = jsonArry.getJSONObject(0);
						//行政处罚决定书文号
						String cf_wsh = detail.get("cf_wsh")==null?"":detail.get("cf_wsh").toString();
						//处罚名称
						String cf_cfmc  = detail.get("cf_cfmc")==null?"":detail.get("cf_cfmc").toString();
						//处罚事由
						String cf_sy   = detail.get("cf_sy")==null?"":detail.get("cf_sy").toString();
						//处罚结果
						String cf_jg  = detail.get("cf_jg")==null?"":detail.get("cf_jg").toString();
						//处罚机关
						String cf_xzjg = detail.get("cf_xzjg")==null?"":detail.get("cf_xzjg").toString();
						//更新时间
						String reg_date = detail.get("reg_date")==null?"":detail.get("reg_date").toString();
						details+="\n"+Enums.RISKLIST_ENUMS.C06BT025.getName()+"\n 执行义务："+cf_wsh+"\n 执行情况:"+cf_cfmc+"\n 公开日期："+cf_sy+"\n 案件编号："+cf_jg+"\n 违反条例："+cf_xzjg+"\n 更新时间："+reg_date+";";
					}
					
				}
			}
			
			json = new JSONObject();
			json.put("retCode", 0);
			json.put("retMsg", "OK");
			result = new JSONObject();
			result.put("blackLevel", blackLevel);
			result.put("blackReason", blackReason);
			result.put("blackDetails", details);
			json.put("result", result);
		}
		System.out.println("接口返回："+json);
		return json;
	}
}
