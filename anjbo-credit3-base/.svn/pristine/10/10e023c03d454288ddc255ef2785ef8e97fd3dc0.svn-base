/**
 * 
 */
package com.anjbo.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Kevin Chang
 * 
 */
public class FreemarkerHelper {
	private static final Log log = LogFactory.getLog(FreemarkerHelper.class);
	private Configuration cfg;
	private static FreemarkerHelper ins;
	/**
	 * 
	 */
	private FreemarkerHelper() {
		// 创建一个Configuration实例
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		try {
			String path = Thread.currentThread().getClass().getResource("/").getFile().toString();
			cfg.setDirectoryForTemplateLoading(new File(path+"/ftl"));
			//cfg.setDirectoryForTemplateLoading(new File(System
					//.getProperty("web.root") + "/ftl"));
//			cfg.setDirectoryForTemplateLoading(new File("D:\\workspace\\anjbo-cm\\src\\main\\webapp\\WEB-INF\\ftl"));
		} catch (IOException e) {
			log.error("FreemarkerHelper init Error.",e);
		}
	}

	public static FreemarkerHelper getIns() {
		if (ins == null) {
			synchronized (FreemarkerHelper.class) {
				if (ins == null) {
					ins = new FreemarkerHelper();
				}
			}
		}
		return ins;
	}

	/**
	 * 加载模板，返回结果
	 * 
	 * @param template
	 * @param dataMap
	 */
	public String processTemplate(String template, Object dataMap) {
		String ret = "";
		Template t;
		StringWriter sw = null;
		try {
			t = cfg.getTemplate(template);
			sw = new StringWriter();
			t.process(dataMap, sw);
			log.info("Template:\n" + sw.toString());
			ret = sw.toString();
		} catch (Exception e) {
			log.error("processTemplate Exception.", e);
		} finally {
			if (null != sw) {
				try {
					sw.close();
				} catch (IOException e) {
				}
				sw = null;
			}
		}
		return ret;
	}
}
