package com.anjbo.service.baidu.impl;

import com.anjbo.bean.baidu.BaiduRiskVo;
import com.anjbo.common.Enums;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.service.baidu.BaiduBlacklistService;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.SingleUtils;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class BaiduBlacklistServiceImpl implements BaiduBlacklistService {
	
	private Log logger = LogFactory.getLog(this.getClass());	
	
    static final String service_id = "1001";
    static final String sign_type = "1";
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> blacklist(BaiduRiskVo baiduRiskVo) {
		logger.info("风险名单参数"+baiduRiskVo);
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(baiduRiskVo.getIdentity())){
        	map.put("retCode", 5004);
        	map.put("retMsg", "参数不合法");
        	map.put("result", "");
            return map;
        }
        long datetime = new Date().getTime();
        int reqids = (int)(Math.random()*900)+100;
        String reqid = String.valueOf(reqids);
        Map<String, Object> retMap = null;
        try {
            String sign = "datetime="+datetime+"&identity="+baiduRiskVo.getIdentity()+"&name="+URLEncoder.encode(baiduRiskVo.getName(),"UTF-8")+"&phone="+baiduRiskVo.getPhone()+"&reqid="+reqid+"&service_id="+service_id+"&sign_type="+sign_type+"&sp_no="+ThirdApiConstants.BAIDU_QUERYRISKLIST_SP_NO+"&key="+ThirdApiConstants.BAIDU_QUERYRISKLIST_KEY;
            String url = ThirdApiConstants.BAIDU_QUERYRISKLIST_URL+"?datetime="+datetime+"&identity="+baiduRiskVo.getIdentity()+"&name="+ URLEncoder.encode(baiduRiskVo.getName(),"UTF-8")+"&phone="+baiduRiskVo.getPhone()+"&reqid="+reqid+"&service_id="+service_id+"&sign_type="+sign_type+"&sp_no="+ThirdApiConstants.BAIDU_QUERYRISKLIST_SP_NO+"&sign="+MD5Utils.MD5Encode(sign);
            System.out.println("请求链接："+url);
            retMap = SingleUtils.getRestTemplate().getForObject(url,Map.class);
            System.out.println("返回数据："+retMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //jsonStr = " {\"retCode\":0,\"retMsg\":\"OK\",\"result\":{\"blackLevel\":\"A\",\"blackReason\":\"C06BT020:C06BT021\",\"blackDetails\":{\"C06BT020\":[{\"duty\":\"2015-12\",\"performance\":\"M3+\",\"publishDate\":\"5\",\"case_no\":\"5\",\"disruptTypeName\":\"5\"}],\"C06BT021\":[{\"loan_date\":\"2015-12\",\"odu_level\":\"M3+\",\"odu_amount_level\":\"5\"}]}}}";
        if(MapUtils.getIntValue(retMap, "retCode")==0){
        	Map<String, Object> result = MapUtils.getMap(retMap, "result");
            String blackLevel = MapUtils.getString(result, "blackLevel","无");
            String blackReason =  MapUtils.getString(result, "blackReason");
            String details="";
            //风险名单原因
            if(StringUtils.isNotEmpty(blackReason)){
                String[] str = blackReason.split(":");
                if(str.length>0){
                    blackReason="";
                    for (String string : str) {
                        blackReason+=","+ Enums.RISKLIST_ENUMS.getNameByCode(string);
                    }
                    blackReason = blackReason.substring(1);
                }
                //风险名单详情
                Map<String, Object> blackDetails = MapUtils.getMap(retMap, "blackDetails");
                if(blackDetails!=null && !blackDetails.isEmpty()){
                    //法院失信
                    if(blackReason.contains(Enums.RISKLIST_ENUMS.C06BT020.getName())){
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT020");
                        Map<String, Object> detail = tempList.get(0);
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
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT021");
                        Map<String, Object> detail = tempList.get(0);
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
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT022");
                        Map<String, Object> detail = tempList.get(0);
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
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT023");
                        Map<String, Object> detail = tempList.get(0);
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
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT024");
                        Map<String, Object> detail = tempList.get(0);
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
                    	List<Map<String, Object>> tempList = (List<Map<String, Object>>) MapUtils.getObject(blackDetails, "C06BT025");
                        Map<String, Object> detail = tempList.get(0);
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
            retMap = new HashMap<String, Object>();
            retMap.put("blackLevel", blackLevel);
            retMap.put("blackReason", blackReason);
            retMap.put("blackDetails", StringUtils.isEmpty(details)?"无":details);
        }
        System.out.println("接口返回："+retMap);
		return retMap;
	}

}
