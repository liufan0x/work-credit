package com.anjbo.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.contract.OrderContractDataDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.service.OrderContractDataService;
import com.anjbo.utils.IdcardUtils;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.NumberToCN;
import com.anjbo.utils.StringUtil;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
@Controller
@RequestMapping("/credit/common/contract/pdf/v")
public class ContractPdfController extends BaseController
{
	
	@Resource
	private OrderContractDataService orderContractDataService;
	
	@RequestMapping(value = "/showPDF", method = RequestMethod.POST)
	@ResponseBody
	public RespDataObject<String> pdfDownload(HttpServletRequest request,@RequestBody OrderContractDataDto orderContractDataDto) throws IOException
	{
		RespDataObject<String> resp = new RespDataObject<String>();
		String url = null;
		try {
			UserDto userDto = getUserDto(request); // 获取用户信息
			//OrderContractDataDto orderContractDataMysql = orderContractDataService.selectOrderContractDataDto(orderContractDataDto);
			//OrderBaseCustomerDto customer = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", orderContractDataDto, OrderBaseCustomerDto.class);
			Map<String,Object> data = orderContractDataDto.getData();
			String tblName = orderContractDataDto.getTblName();
			String orderNo = orderContractDataDto.getOrderNo();
			String fileName = orderNo+tblName+userDto.getUid();
			fileName = MD5Utils.MD5Encode(fileName);
			String path = request.getSession().getServletContext().getRealPath("/");
			String resourcePath = path+"/credit/common/pdf/modle/"+tblName+".pdf";
			String newPDFPath = path+"/credit/common/pdf/"+fileName+".pdf";
			//没有借款期限，后台添加打印
//			OrderBaseBorrowDto borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", orderContractDataDto, OrderBaseBorrowDto.class);
//			if(borrow!=null){
//				data.put("borrowingDays", borrow.getBorrowingDays());
//			}
			url = tbl_contract_bussiness_application(request,resourcePath,newPDFPath,fileName,data,tblName);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "预览失败");
			e.printStackTrace();
		}
		return RespHelper.setSuccessDataObject(resp, url);
	}
	
	/**
	 * 生成合同
	 * @param request
	 * @param resourcePath
	 * @param newPDFPath
	 * @param fileName
	 * @param data
	 * @return
	 */
	public String tbl_contract_bussiness_application(HttpServletRequest request,String resourcePath,String newPDFPath,String fileName,Map<String,Object> data,String tblName){
		String url ="";
		PdfDocument pdf;
		try {
			//光大信托合同属性
			data.put("1", MapUtils.getString(data, "borrowingDays",""));//借款期限
			data.put("11", 11);
			data.put("purpose", "短期周转");
			data.put("foreclosureType", "按日计息，利随本清");
			//出款银行
			if(StringUtil.isNotBlank(MapUtils.getString(data, "bankName",""))){
				data.put("bankName", MapUtils.getString(data, "bankName","")+"-"+MapUtils.getString(data, "bankSubName",""));
			}
			//回款银行
			if(StringUtil.isNotBlank(MapUtils.getString(data, "paymentBankName",""))){
				data.put("paymentBankName", MapUtils.getString(data, "paymentBankName","")+"-"+MapUtils.getString(data, "paymentBankSubName",""));
			}
			
			//借款金额（大写）
			if(data.containsKey("borrowingAmount")){
				Double amount = MapUtils.getDoubleValue(data, "borrowingAmount", 0.0);
				String borrowingAmount = Double.toString(amount);
				BigDecimal numberOfMoney = new BigDecimal(borrowingAmount);
				borrowingAmount = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				data.put("borrowingAmount", borrowingAmount.substring(0, borrowingAmount.indexOf("整")));
				//去掉末尾的元整
				data.put("upperBorrowingAmount", borrowingAmount.substring(0,borrowingAmount.indexOf("元")));
				data.put("upperBorrowingAmount1", borrowingAmount.substring(0,borrowingAmount.indexOf("元")));
				//小写的金额(元)
				String lowerBorrowingAmount = String.valueOf(amount).replace(".0", "");
				data.put("lowerBorrowingAmount", lowerBorrowingAmount);
				data.put("lowerBorrowingAmount1", lowerBorrowingAmount);
				//金额大写填入对应项
				if(lowerBorrowingAmount.length()>=7){
					data.put("baiw", lowerBorrowingAmount.substring(0, 1));
				}
				if(lowerBorrowingAmount.length()>=6){
					data.put("shiw", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-6, lowerBorrowingAmount.length()-5));
				}
				if(lowerBorrowingAmount.length()>=5){
					data.put("wan", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-5, lowerBorrowingAmount.length()-4));
				}
				if(lowerBorrowingAmount.length()>=4){
					data.put("qian", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-4, lowerBorrowingAmount.length()-3));
				}
				if(lowerBorrowingAmount.length()>=3){
					data.put("bai", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-3, lowerBorrowingAmount.length()-2));
				}
				if(lowerBorrowingAmount.length()>=2){
					data.put("shi", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-2, lowerBorrowingAmount.length()-1));
				}
				if(lowerBorrowingAmount.length()>=1){
					data.put("yuan", lowerBorrowingAmount.substring(lowerBorrowingAmount.length()-1, lowerBorrowingAmount.length()));
				}
				data.put("jiao", "0");
				data.put("fen", "0");
			}
			//借款合同，借款人为多个
			data.put("customerName1", MapUtils.getString(data, "customerName",""));
			data.put("customerName2", MapUtils.getString(data, "customerName",""));
			String customerNames="";
			String customerCardNumbers="";
			customerNames += MapUtils.getString(data, "customerName","");
			customerNames += StringUtil.isNotBlank(MapUtils.getString(data, "customerWifeName"))?"、"+MapUtils.getString(data, "customerWifeName",""):"";
			//customerCardNumbers += MapUtils.getString(data, "customerCardNumber","");
			customerCardNumbers += StringUtil.isNotBlank(MapUtils.getString(data, "customerWifeCardNumber"))?"、"+MapUtils.getString(data, "customerWifeCardNumber",""):"";
			//产权人
			if(data.containsKey("propertyPeopleDto")){
				JSONArray propertyPeople = JSONArray.fromObject(MapUtils.getObject(data, "propertyPeopleDto"));
				if(propertyPeople!=null&&propertyPeople.size()>0){
					for (int i = 0; i < propertyPeople.size(); i++) {
						JSONObject propertyPeoplei = propertyPeople.getJSONObject(i);
						if(propertyPeoplei.size()>0){
							if(!customerNames.contains(propertyPeoplei.getString("propertyName"))){
								customerNames += "、"+propertyPeoplei.getString("propertyName");
							}
							if(propertyPeoplei.containsKey("propertyCardNumber")&&!customerCardNumbers.contains(propertyPeoplei.getString("propertyCardNumber"))
									&&!MapUtils.getString(data, "customerCardNumber","").equals(propertyPeoplei.getString("propertyCardNumber"))){
								customerCardNumbers += "、"+propertyPeoplei.getString("propertyCardNumber");
							}
						}
					}
				}
			}
			//设置多个借款人
			if(data.containsKey("customerBorrowerDto")){
				JSONArray listBorrow = JSONArray.fromObject(MapUtils.getObject(data, "customerBorrowerDto"));
				if(listBorrow!=null&&listBorrow.size()>0){
					for (int i = 0; i < listBorrow.size(); i++) {
						JSONObject borrowi = listBorrow.getJSONObject(i);
						if(borrowi.size()>0){
							if(!customerNames.contains(borrowi.getString("borrowerName"))){
								customerNames += "、"+borrowi.getString("borrowerName");
							}
							if(borrowi.containsKey("borrowerCardNumber")&&!customerCardNumbers.contains(borrowi.getString("borrowerCardNumber"))
									&&!MapUtils.getString(data, "customerCardNumber","").equals(borrowi.getString("borrowerCardNumber"))){
								customerCardNumbers += "、"+borrowi.getString("borrowerCardNumber");
							}
							data.put("borrowerName"+i, borrowi.getString("borrowerName"));
							if(borrowi.containsKey("borrowerCardNumber")){
								data.put("borrowerCardNumber"+i, borrowi.getString("borrowerCardNumber"));
							}
							if(borrowi.containsKey("borrowerPhone")){
								data.put("borrowerPhone"+i, borrowi.getString("borrowerPhone"));
							}
							if(borrowi.containsKey("emailAddress")){
								data.put("emailAddress"+i, borrowi.getString("emailAddress"));
							}
							if(borrowi.containsKey("postalAddress")){
								data.put("postalAddress"+i, borrowi.getString("postalAddress"));
							}
						}
					}
				}
			}
			data.put("customerCardNumbers", customerCardNumbers);
			data.put("customerNames1", customerNames);
			data.put("customerNames2", customerNames);
			//业务申请属性
			//出生年月
			if(data.containsKey("customerCardNumber")){
				String birthBy = IdcardUtils.getBirthByIdCard(MapUtils.getString(data, "customerCardNumber"));
				if(StringUtil.isNotBlank(birthBy)){
					StringBuilder  sb = new StringBuilder (birthBy);  
					sb.insert(6, "-");
					sb.insert(4, "-");
					String birthday = sb.toString();
					data.put("birthdays", birthday);
					data.put("birthday", birthday.substring(0, 7));
				}
			}
			//承诺函属性
			//签署年月日
			String dateOfSigning = MapUtils.getString(data, "dateOfSigning");
			if(StringUtil.isNotBlank(dateOfSigning)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateSigning = sdf.parse(dateOfSigning);
				Calendar c = Calendar.getInstance();
				c.setTime(dateSigning);
				data.put("signYear", c.get(Calendar.YEAR));
				data.put("signMonth", c.get(Calendar.MONTH)+1);
				data.put("signDay", c.get(Calendar.DAY_OF_MONTH));
				data.put("dateOfSigningCN", c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日");
				//阿拉伯数字转汉字
				String year = String.valueOf(c.get(Calendar.YEAR));
				year  = year.substring(year.length()-1,year.length());
				data.put("signUpperYear", NumberToCN.formatInteger(Integer.parseInt(year)));
				data.put("signUpperMonth", NumberToCN.formatInteger(c.get(Calendar.MONTH)+1));
				data.put("signUpperDay", NumberToCN.formatInteger(c.get(Calendar.DAY_OF_MONTH)).replace("一十", "十"));
				data.put("signLowerYear", year);
				data.put("signLowerMonth", c.get(Calendar.MONTH)+1);
				data.put("signLowerDay", c.get(Calendar.DAY_OF_MONTH));
				data.put("signLowerYear1", year);
				data.put("signLowerMonth1", c.get(Calendar.MONTH)+1);
				data.put("signLowerDay1", c.get(Calendar.DAY_OF_MONTH));
				data.put("signLowerYear2", year);
				data.put("signLowerMonth2", c.get(Calendar.MONTH)+1);
				data.put("signLowerDay2", c.get(Calendar.DAY_OF_MONTH));
			}
			//结清日期
			String foreclosureTime = MapUtils.getString(data, "foreclosureTime");
			if(StringUtil.isNotBlank(foreclosureTime)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date foreclosureDate = sdf.parse(foreclosureTime);
				Calendar c = Calendar.getInstance();
				c.setTime(foreclosureDate);
				data.put("foreclosureYear", c.get(Calendar.YEAR));
				data.put("foreclosureMonth", c.get(Calendar.MONTH)+1);
				data.put("foreclosureDay", c.get(Calendar.DAY_OF_MONTH));
			}
			//除客户姓名外的其他借款人
			if(customerNames.indexOf("、")>0){
				data.put("propertyName", customerNames.substring(customerNames.indexOf("、")+1, customerNames.length()));
			}
			//
			//实际回款时间
			String payMentAmountDate = MapUtils.getString(data, "payMentAmountDate");
			if(StringUtil.isNotBlank(payMentAmountDate)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date payMentAmountTime = sdf.parse(payMentAmountDate);
				Calendar c = Calendar.getInstance();
				c.setTime(payMentAmountTime);
				data.put("payMentAmountYear", c.get(Calendar.YEAR));
				data.put("payMentAmountMonth", c.get(Calendar.MONTH)+1);
				data.put("payMentAmountDay", c.get(Calendar.DAY_OF_MONTH));
			}
			
			//多个合同编号
			String contractNo = MapUtils.getString(data, "contractNo","").replace(" ", "");
			data.put("borrowContractNo", "(201"+MapUtils.getString(data, "y","")+")年借字第"+contractNo+"号");
			data.put("contractNo", contractNo);
			data.put("contractNo2", contractNo);
			data.put("contractNo3", contractNo);
			//城市，区，去掉最后一个字
			String cityNameOfSigning = MapUtils.getString(data, "cityNameOfSigning","");
			if(cityNameOfSigning.length()>1){
				cityNameOfSigning = cityNameOfSigning.substring(0, cityNameOfSigning.length()-1);
				data.put("cityNameOfSigning", cityNameOfSigning);
			}
			String regionNameOfSigning = MapUtils.getString(data, "regionNameOfSigning","");
			if(regionNameOfSigning.length()>1){
				regionNameOfSigning = regionNameOfSigning.substring(0, regionNameOfSigning.length()-1);
				data.put("regionNameOfSigning", regionNameOfSigning);
			}
			//家庭住址
			String postalAddress = MapUtils.getString(data, "postalAddress","");
			if(StringUtil.isNotBlank(postalAddress)&&postalAddress.length()>12){
				data.put("postalAddresspr", postalAddress.substring(0, 12));
				data.put("postalAddressex", postalAddress.subSequence(12, postalAddress.length()));
			}else{
				data.put("postalAddresspr", postalAddress);
			}
			pdf = new PdfDocument(new PdfReader(resourcePath), new PdfWriter(newPDFPath));
			PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);  
			Map<String, PdfFormField> fields = form.getFormFields(); 
			PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			Iterator<Map.Entry<String, PdfFormField>> it = fields.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, PdfFormField> entry = it.next();
				System.out.println(entry.getKey());
				/*if("tbl_contract_trust_loan".equals(tblName)){//光大合同字体为12（小四）
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey(),"")).setFontSize(12).setBorderColor(Color.WHITE);
				}else*/ if(entry.getKey().contains(".1")){
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey().replace(".1", ""),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
				}else if(entry.getKey().contains(".2")){
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey().replace(".2", ""),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
				}else if(entry.getKey().contains(".3")){
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey().replace(".3", ""),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
				}else if(entry.getKey().contains(".4")){
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey().replace(".4", ""),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
				}else if(entry.getKey().contains(".5")){
					fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey().replace(".5", ""),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
				}else{
					if("tbl_contract_loan".equals(tblName)&&("customerNames1".equals(entry.getKey())
								||"y".equals(entry.getKey())
								||"contractNo".equals(entry.getKey())
								||"signUpperYear".equals(entry.getKey())
								||"signUpperMonth".equals(entry.getKey())
								||"signUpperDay".equals(entry.getKey()))){
							fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey(),"")).setFontSize(14).setBorderColor(Color.WHITE);
					}else if("tbl_contract_bussiness_application".equals(tblName)){
						fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey(),"")).setFontSize(8).setBorderColor(Color.WHITE);
					}else{
						fields.get(entry.getKey()).setFont(font).setValue(MapUtils.getString(data,entry.getKey(),"")).setFontSize((float) 10.5).setBorderColor(Color.WHITE);
					}
				}
			}
			form.flattenFields();//设置表单域不可编辑              
			pdf.close();
//			url = ConfigUtil.getStringValue(Constants.LINK_CMCREDIT,ConfigUtil.CONFIG_LINK_FILE);
			url+="/credit/common/pdf/"+fileName+".pdf";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}
	
	/**
	 * 光大信托合同
	 * @param request
	 * @param resourcePath
	 * @param newPDFPath
	 * @param fileName
	 * @param data
	 * @return
	 */
	public String tbl_contract_trust_loan(HttpServletRequest request,String resourcePath,String newPDFPath,String fileName,Map<String,Object> data,String tblName){
		String url = "";
		PdfDocument pdf;
		try {
			pdf = new PdfDocument(new PdfReader(resourcePath), new PdfWriter(newPDFPath));
			PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);  
			Map<String, PdfFormField> fields = form.getFormFields(); 
			PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			Calendar cal = Calendar.getInstance();
			Integer year = cal.get(Calendar.YEAR);   
	        Integer month = cal.get(Calendar.MONTH)+1; 
	        Integer day = cal.get(cal.DAY_OF_MONTH); 
			fields.get("contractNo").setValue(MapUtils.getString(data, "contractNo","").replace(" ", "")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("year").setValue(year.toString()).setFont(font).setFontSize(6).setBorderColor(Color.WHITE);
			fields.get("month").setValue(month.toString()).setFont(font).setFontSize(6).setBorderColor(Color.WHITE);
			fields.get("day").setValue(day.toString()).setFont(font).setFontSize(6).setBorderColor(Color.WHITE);
			String cityNameOfSigning = MapUtils.getString(data, "cityNameOfSigning","");
			if(cityNameOfSigning.length()>1){
				cityNameOfSigning = cityNameOfSigning.substring(0, cityNameOfSigning.length()-1);
			}
			fields.get("cityNameOfSigning").setFont(font).setValue(MapUtils.getString(data, "cityNameOfSigning","")).setFontSize(10).setBorderColor(Color.WHITE);
			fields.get("regionNameOfSigning").setFont(font).setValue(MapUtils.getString(data, "regionNameOfSigning","")).setFontSize(10).setBorderColor(Color.WHITE);
			//赋值借款人信息
			JSONArray listBorrow = JSONArray.fromObject(MapUtils.getObject(data, "customerBorrowerDto"));
			if(listBorrow!=null&&listBorrow.size()>0){
				for (int i = 0; i < listBorrow.size(); i++) {
					JSONObject borrowi = listBorrow.getJSONObject(i);
					fields.get("customerName"+(i+1)).setValue(borrowi.getString("borrowerName")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
					fields.get("customerCardNumber"+(i+1)).setValue(borrowi.getString("borrowerCardNumber")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
					if(borrowi.containsKey("borrowerPhone")){
						fields.get("phoneNumber"+(i+1)).setValue(borrowi.getString("borrowerPhone")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
					}
					if(borrowi.containsKey("emailAddress")){
						fields.get("emailAddress"+(i+1)).setValue(borrowi.getString("emailAddress")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
					}
					if(borrowi.containsKey("postalAddress")){
						fields.get("postalAddress"+(i+1)).setValue(borrowi.getString("postalAddress")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
					}
				}
			}
			fields.get("purpose").setValue("短期周转").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("1").setValue("1").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("11").setValue("11").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("foreclosureType").setValue("按日计息，利随本清").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("repayAmount").setValue(MapUtils.getString(data, "repayAmount","")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("bankNo").setValue(MapUtils.getString(data, "bankNo","")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("bankCardMaster").setValue(MapUtils.getString(data, "bankCardMaster","")).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("bankName").setFont(font).setValue(MapUtils.getString(data, "bankName","")+"-"+MapUtils.getString(data, "bankSubName","")).setFontSize(12).setBorderColor(Color.WHITE);
			Double amount = MapUtils.getDoubleValue(data, "borrowingAmount", 0.0);
			String borrowingAmount = Double.toString(amount);
			BigDecimal numberOfMoney = new BigDecimal(borrowingAmount);
			borrowingAmount = NumberToCN.number2CNMontrayUnit(numberOfMoney);
			fields.get("borrowingAmount").setValue(borrowingAmount).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			//填充表格里的金额
			String preBorrowingAmount = borrowingAmount.substring(0, borrowingAmount.indexOf("万")+1);
			String sufBorrowingAmount = borrowingAmount.substring(borrowingAmount.indexOf("万"),borrowingAmount.length());
			if(preBorrowingAmount.contains("佰")){
				fields.get("baiw").setValue(preBorrowingAmount.substring(preBorrowingAmount.indexOf("佰")-1,preBorrowingAmount.indexOf("佰"))).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}
			if(preBorrowingAmount.contains("拾")){
				fields.get("shiw").setValue(preBorrowingAmount.substring(preBorrowingAmount.indexOf("拾")-1,preBorrowingAmount.indexOf("拾"))).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}else if(preBorrowingAmount.contains("佰")){
				fields.get("shiw").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}
			if("拾".equals(preBorrowingAmount.substring(preBorrowingAmount.indexOf("万")-1,preBorrowingAmount.indexOf("万")))
					||"佰".equals(preBorrowingAmount.substring(preBorrowingAmount.indexOf("万")-1,preBorrowingAmount.indexOf("万")))){
				fields.get("wan").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}else{
				fields.get("wan").setValue(preBorrowingAmount.substring(preBorrowingAmount.indexOf("万")-1,preBorrowingAmount.indexOf("万"))).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}
			if(sufBorrowingAmount.contains("仟")){
				fields.get("qian").setValue(sufBorrowingAmount.substring(sufBorrowingAmount.indexOf("仟")-1,sufBorrowingAmount.indexOf("仟"))).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}else{
				fields.get("qian").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}
			if(sufBorrowingAmount.contains("佰")){
				fields.get("bai").setValue(sufBorrowingAmount.substring(sufBorrowingAmount.indexOf("佰")-1,sufBorrowingAmount.indexOf("佰"))).setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}else{
				fields.get("bai").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			}
			fields.get("shi").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("yuan").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("jiao").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			fields.get("fen").setValue("零").setFont(font).setFontSize(12).setBorderColor(Color.WHITE);
			form.flattenFields();//设置表单域不可编辑              
			pdf.close();
//			htmlToPdf(resourcePath,newPDFPath);
			url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort();
			url+="/pdf/"+fileName+".pdf";
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return url;
	}
	
	
	
//	public static void main(String [] args) throws Exception
//	{
//		htmlToPdf2();
//		System.out.println("ok!");
//	}
//	
//	public static void htmlToPdf2() throws Exception{
//		String templatePath = "C:\\Users\\admin\\Desktop\\test.pdf";
//		String newPDFPath = "C:\\Users\\admin\\Desktop\\123.pdf";;
//		PdfDocument pdf = new PdfDocument(new PdfReader(templatePath), new PdfWriter(newPDFPath));  
//		
//		PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);  
//		Map<String, PdfFormField> fields = form.getFormFields(); 
//		PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//		PdfTextFormField nameField = PdfTextFormField.createText(pdf, new Rectangle(80, 700, 30, 22), "name", "");
//		form.addField(nameField);
//		fields.get("name").setValue("李灿").setFont(font).setFontSize(12);
//
//		form.flattenFields();//设置表单域不可编辑              
//		pdf.close();  
//	}

}