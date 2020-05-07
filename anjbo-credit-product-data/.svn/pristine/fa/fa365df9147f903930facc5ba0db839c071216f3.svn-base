package anjbo.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.ReqMappingConstants;
import com.anjbo.common.RespDataObject;
import com.anjbo.utils.HttpUtil;

public class TestDemo {

	public static void main(String[] args) {
		function008();
		//function024();
		//function005();
		//function027();
	
	}

	
	/**
	 * C008 申请评估 demo
	 */
	public static void function008(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo");
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		//0000001018
			iMap.put("ownerName", "陈志明");
			iMap.put("ownerCertificateType", "A");
			iMap.put("ownerCertificateNo", "421081196509050030");
			iMap.put("estateNo", "5000326747");
			iMap.put("custManagerMobile", "13560732804");
			iMap.put("estateType", "0");
			iMap.put("yearNo", "2016");
			iMap.put("actPrice",6399.00);
			iMap.put("remark", "备注");
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C008");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	/**
	 * C024 买卖双方信息 demo
	 */
	public static void function024(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo");
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			iMap.put("estateNo", "6541312");
			iMap.put("custManagerMobile", "18018776181");
			iMap.put("estateType", "0");
			iMap.put("yearNo", "2017");
					//卖方信息集合
					List<Map<String,Object>> selList=new ArrayList<Map<String,Object>>();
							Map<String,Object> sel=new HashMap<String,Object>();
							sel.put("name", "陈志明");
							sel.put("certificateNo", "152327199303217837");
							sel.put("certificateType", "A");
							sel.put("mobile", "13088833699");
							
							Map<String,Object> sel2=new HashMap<String,Object>();
							sel2.put("name", "陈志明");
							sel2.put("certificateNo", "152327199303216637");
							sel2.put("certificateType", "A");
							sel2.put("mobile", "13088833699");
							selList.add(sel2);
			iMap.put("selList",selList);
					//买方信息组
					List<Map<String,Object>> buyList=new ArrayList<Map<String,Object>>();
							Map<String,Object> buy=new HashMap<String,Object>();
							buy.put("name", "chenzhiming");
							buy.put("certificateNo", "152327199303212557");
							buy.put("certificateType", "A");
							buy.put("mobile", "13088832921");
							buyList.add(buy);
							
							Map<String,Object> buy2=new HashMap<String,Object>();
							buy2.put("name", "chenzhiming");
							buy2.put("certificateNo", "152327199303212337");
							buy2.put("certificateType", "A");
							buy2.put("mobile", "13088832921");
							buyList.add(buy2);
			iMap.put("buyList",buyList);
					
			iMap.put("actPrice",63002.00);
			iMap.put("appNo", "0000001007");
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C024");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);
		
		
	}
	
	/**
	 * C005 存量客户查询demo
	 */
	public static void function005(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			iMap.put("custName", "陈志明");
			iMap.put("certificateType", "A");
			iMap.put("certificateNo", "152327199303212337");

			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C005");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	 
	
	
	
	/**
	 * C025 预约抵押demo
	 */
	public static void function025(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("appNo", "0000001007");
			iMap.put("appointDate", "20170830");
			iMap.put("appointTime", "1");
			iMap.put("appointSite", "01");
			iMap.put("appointResult", "预约");

			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C025");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	
	
	/**
	 * C026 预约抵押demo
	 */
	public static void function026(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("appNo", "0000001009");
			iMap.put("estateNo", "20170830");
			iMap.put("yearNo", "1");
			iMap.put("index", "01");
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C026");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	
	
	/**
	 * C006 预约抵押demo
	 */
	public static void function006(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("custManagerMobile", "18018776181");
			
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C006");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	
	
	/**
	 * C001 客户信息demo
	 */
	public static void function001(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("custName", "陈志明");
			iMap.put("certificateType", "A");
			iMap.put("certificateNo", "152327199303212222");
			iMap.put("sex", "01");
			iMap.put("dateBirth", "19930321");
			iMap.put("marital", "10");
			iMap.put("education", "20");
			iMap.put("localLiveTime", "3");
			iMap.put("email", "98501025@qq.com");
			iMap.put("currAddress", "23qweqasd");
			iMap.put("zipCode", "029100");
			iMap.put("familyPhone", "13088832921");
			iMap.put("familySize", 4);
			iMap.put("mobile", "13088832921");
			iMap.put("workUnit", "快鸽");
			iMap.put("unitPhone", "07553333333");
			iMap.put("personIncome", 11111);
			iMap.put("custManagerMobile", "18018776181");
			iMap.put("nation", "01");
			iMap.put("live", "50");
			iMap.put("familyIncome", 22222);
			iMap.put("familyExpend", 10000);
			iMap.put("familyAssets", 55555);
			iMap.put("familyLiabilities", 1000);
			iMap.put("personPayments", 1000);
			iMap.put("children", "10");
			iMap.put("unitTime", "20160830");
			iMap.put("mainSources", "01");
			iMap.put("censusReg", "110000");
			iMap.put("agentName", "chen");
			iMap.put("agentMobile", "13088832921");
			
			
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C001");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	
	
	
	
	/**
	 * C002 贷款信息demo
	 */
	public static void function002(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("custName", "陈志明");
			iMap.put("certificateType", "A");
			iMap.put("certificateNo", "152327199303212222");
			iMap.put("productCode", "2022");
			iMap.put("loanAmount", 100000);
			iMap.put("loanLimit", "23");
			iMap.put("loanUsed", "76");
			iMap.put("firstPayment", 50000);
			iMap.put("repaymentWay", "311");
			iMap.put("totalAmount", 1000000);
			iMap.put("payeeCardNo", "6221504910002545236");
			iMap.put("payeeName", "czm");
			iMap.put("payeeBankName","中国银行");
			iMap.put("payeeIsOurBank", 1);
			iMap.put("houseCount", 1);
			iMap.put("houseType", "01");
			iMap.put("personIncome", 11111);
			iMap.put("assType", "3008");
			iMap.put("assCustName", "chenzm");
			iMap.put("assCertificateType", "A");
			iMap.put("assCertificateNo", "152327199332452112");
			iMap.put("agentName", "何渊");
			iMap.put("assessAppNo", "0000001014");
			iMap.put("agentMobile", "18018776181");
			iMap.put("custManagerMobile", "18018776181");
			
			
			
			
		//Appno=0000001007 0000001008 0000001009
		map.put("tranNo","C002");
		map.put("subBankId", "123456");
		map.put("obj", iMap);
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, map, Map.class);

	}
	
	
	/**
	 * C027影像资料demo
	 */
	public static void function027(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("comNo", "COM02");
			iMap.put("orderNo", "orderNo"); 
			iMap.put("bankNo", "442000086");	
			iMap.put("channelNo", "ACG");
			iMap.put("isn",getID());
		
			
			iMap.put("custManagerMobile", "18018776181");
			
			
		//Appno=0000001007 0000001008 0000001009
		map.put("orderNo","2017070717122200000");
		map.put("url", "http://fs.anjbo.com/img/fc-img/1499770442455.jpg");
		map.put("subBankId", "123456"); 
		RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,ReqMappingConstants.CREDIT_THIRD_API_BUSINFOSYNC, map, Map.class);

	}
	
	

	
	
	
	
	
	
	  public synchronized static String getID() {
		  long tmpID = 0;
	        long ltime = 0;
	        ltime = Long.valueOf(new SimpleDateFormat("yyMMddHHmmssSSS")
	                .format(new Date()).toString()).longValue() * 10000;
	        if (tmpID < ltime) {
	            tmpID = ltime;
	        } else {
	        	String id = String.valueOf(tmpID);
	        	String subid = id.substring(17,19);
	        	Integer flg = Integer.parseInt(subid);
	        	if(flg+1 > 99)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("getId方法休眠被打断W");
					}
	            tmpID = tmpID + 1;
	            ltime = tmpID;
	        }
	        return String.valueOf(ltime);
	    }
}
