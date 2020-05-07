package com.anjbo.processor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.anjbo.controller.UserUtilController;
import com.anjbo.utils.SpringContextUtil;
/**
 * 服务器启动初始化
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午02:26:20
 */
public class Initializer extends HttpServlet{
	
	private Logger logger = Logger.getLogger(Initializer.class);
	
	private static final long serialVersionUID = -2734954638025743505L;

	@Override
	public void init() throws ServletException {
		UserUtilController userUtilController = (UserUtilController) SpringContextUtil.getBean("userUtilController");
		userUtilController.initUserList();
		logger.info("服务器启动初始化...");
	}
}
