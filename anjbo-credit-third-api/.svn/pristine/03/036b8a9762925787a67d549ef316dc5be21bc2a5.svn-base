/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.utils.icbc.ParamUtil.java  <2017年10月20日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.utils.icbc;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;

import cn.com.icbc.CMS.commontools.TranslationTool;
import cn.com.icbc.CMS.commontools.XMLIO;
import cn.com.icbc.CMS.commontools.XpathOperater;

import com.anjbo.lang.ObjectBase;
import com.anjbo.utils.FileUtil;

/**
 * 获取工行param.xml
 * @Author KangLG
 * @Date 2017年10月20日 上午9:36:37
 * @version 1.0
 */
public class ParamUtil extends ObjectBase {	
	// 企业参数	
	private String name;     //企业名称
	private String bankcode; //银行编码
	private String cis;      //集团CIS号
	private String port;     //监听端口
	private String store;    //企业通讯层证书
	private String storepass;//企业通讯层证书口令
	private String cerfile;  //企业数据层公钥
	private String key;      //企业数据层私钥
	private String keypass;  //企业数据层私钥口令
	// 工行参数
	private String icbcIp;         //工行系统地址
	private String icbcPort;       //工行系统端口
	private String icbcTruststore; //工行通讯层证书
	private String icbcCerfile;    //工行数据层公钥
	// 其它参数
	private String anjboId;      //接口证书ID    示例：rp.y.4000
	private String anjboVersion; //接口版本
	
	private static final ParamUtil instance = new ParamUtil();
	public static ParamUtil getInstance(){
		return instance;
	}
	
	private ParamUtil() {
		XMLIO reader = new XMLIO();
		try {
			byte[]data = TranslationTool.readFile(FileUtil.getFilePath("icbc/param.xml"));
			reader.build(data);
		} catch (IOException e) {
			logger.error("无法读取参数配置文件 ：icbc/param.xml");	
			return;
		} catch (Exception e1) {
			logger.error("参数文件不正确，请检查配置文件是否正确：icbc/param.xml");
			return;
		}

		Document jdom = reader.getJdom();
		XpathOperater xo = new XpathOperater();
		try {
			xo.setDom(jdom);
			xo.setXpath("/paras/name");
			name = xo.getNodeValue();
			xo.setXpath("/paras/bankcode");
			bankcode = xo.getNodeValue();
			xo.setXpath("/paras/CIS");
			cis = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/port");
			port = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/commlevel/store");
			store = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/commlevel/storepass");
			storepass = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/cerfile");
			cerfile = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/key");
			key = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/keypass");
			keypass = xo.getNodeValue();
			// 工行参数
			xo.setXpath("/paras/ICBC/IP");
			icbcIp = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/port");
			icbcPort = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/commlevel/truststore");
			icbcTruststore = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/datalevel/cerfile");
			icbcCerfile = xo.getNodeValue();
			// 其它
			xo.setXpath("/paras/AnjboDefines/id");
			anjboId = xo.getNodeValue();
			xo.setXpath("/paras/AnjboDefines/version");
			anjboVersion = xo.getNodeValue();
		} catch (JDOMException e2) {
			logger.error("参数加载出错");
			e2.printStackTrace();
		}
	}

	/*
	 * getter
	 */
	/**企业名称*/
	public String getName() {
		return name;
	}
	/**银行编码*/
	public String getBankcode() {
		return bankcode;
	}
	/**集团CIS号*/
	public String getCis() {
		return cis;
	}
	/**监听端口*/
	public String getPort() {
		return port;
	}
	/**企业通讯层证书*/
	public String getStore() {
		return store;
	}
	/**企业通讯层证书口令*/
	public String getStorepass() {
		return storepass;
	}
	/**企业数据层公钥*/
	public String getCerfile() {
		return cerfile;
	}
	/**企业数据层私钥*/
	public String getKey() {
		return key;
	}
	/**企业数据层私钥口令*/
	public String getKeypass() {
		return keypass;
	}
	
	/**工行系统地址*/
	public String getIcbcIp() {
		return icbcIp;
	}
	/**工行系统端口*/
	public String getIcbcPort() {
		return icbcPort;
	}
	/**工行通讯层证书*/
	public String getIcbcTruststore() {
		return icbcTruststore;
	}
	/**工行数据层公钥*/
	public String getIcbcCerfile() {
		return icbcCerfile;
	}
	
	/**接口证书ID*/	
	public String getAnjboId() {
		return anjboId;
	}
	/**接口证书版本*/
	public String getAnjboVersion() {
		return anjboVersion;
	}

}
