package com.anjbo.sms.soap;
 /**
  * 本示例使用Axis1.4，项目需要引用的包为axis.jar,commons-discovery-0.2.jar,
  * jaxrpc.jar,wsdl4j-1.5.1.jar,mail.jar,commons-logging-1.0.4.jar
  * 6个包缺一不可。
  * 优易网关soap接口地址：http://113.105.65.138:7801/sms?wsdl
  * 更改为域名接入http://inter.smswang.net:7801/sms?wsdl
  */

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.anjbo.utils.ConfigUtil;

public class UegateSoap {
	
	//短信提交
	public String Submit(String spID,String password,String accessCode,String content,String mobileString){
		String result="";
		try {
			//soap接口地址，不能加后面的?wsdl
			String endpoint = ConfigUtil.getStringValue("soapURL");

			Service service = new Service();

			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(endpoint);

			call.setOperationName("Submit");

			call.addParameter("spID",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("password",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("accessCode",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("content",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("mobileString",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setUseSOAPAction(true);

			call.setSOAPActionURI("http://schemas.microsoft.com/clr/nsassem/com.softwee.smgw.soaps.Soap57Provider/soaps#Submit");

			result = (String) call.invoke("Submit", new Object[] { spID,
					password, accessCode, content, mobileString });


		}catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.toString());

		}
		
		return result;
	}

	//查询余额
	public String QueryMo(String spID,String password){
		String result="";
		try {
			//soap接口地址，不能加后面的?wsdl
			String endpoint = ConfigUtil.getStringValue("soapURL");

			Service service = new Service();

			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(endpoint);

			call.setOperationName("QueryMo");

			call.addParameter("spID",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("password",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setUseSOAPAction(true);

			call.setSOAPActionURI("http://schemas.microsoft.com/clr/nsassem/com.softwee.smgw.soaps.Soap57Provider/soaps#QueryMo");

			result = (String) call.invoke("QueryMo", new Object[] { spID,password});


		}catch (Exception e) {

			System.err.println(e.toString());

		}
		
		return result;
	}
	
	//状态报告
	public String QueryReport(String spID,String password){
		String result="";
		try {
			//soap接口地址，不能加后面的?wsdl
			String endpoint = ConfigUtil.getStringValue("soapURL");

			Service service = new Service();

			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(endpoint);

			call.setOperationName("Submit");

			call.addParameter("spID",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("password",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setUseSOAPAction(true);

			call.setSOAPActionURI("http://schemas.microsoft.com/clr/nsassem/com.softwee.smgw.soaps.Soap57Provider/soaps#QueryReport");

			result = (String) call.invoke("QueryReport", new Object[] { spID,password});


		}catch (Exception e) {

			System.err.println(e.toString());

		}
		
		return result;
	}
	
	//短信回复
	public String RetrieveAll(String spID,String password){
		String result="";
		try {
			//soap接口地址，不能加后面的?wsdl
			String endpoint = ConfigUtil.getStringValue("soapURL");

			Service service = new Service();

			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(endpoint);

			call.setOperationName("RetrieveAll");

			call.addParameter("spID",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("password",
					org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setUseSOAPAction(true);

			call.setSOAPActionURI("http://schemas.microsoft.com/clr/nsassem/com.softwee.smgw.soaps.Soap57Provider/soaps#RetrieveAll");

			result = (String) call.invoke("RetrieveAll", new Object[] { spID,password });


		}catch (Exception e) {

			System.err.println(e.toString());

		}
		
		return result;
	}
}


