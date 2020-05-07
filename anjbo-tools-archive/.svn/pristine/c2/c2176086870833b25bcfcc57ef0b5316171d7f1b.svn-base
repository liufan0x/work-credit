package com.anjbo.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.anjbo.chromejs.manager.ArchiveMonitor;
import com.anjbo.execute.ExecuteQueue;

@Controller
public class ArchiveController {
	private static final Log log = LogFactory.getLog(ArchiveController.class);
	@RequestMapping("/send")
	public void send(HttpServletRequest request,HttpServletResponse response){
		String jkid = request.getParameter("jkid");
		ArchiveMonitor.getInstance().timeChromeOut(jkid);//插件端监控预警
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String host = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		JSONObject  jsonObject = ExecuteQueue.getInstance().getAllNotCompleteDataToJson();
		jsonObject.put("host", host);
		try {
			response.getWriter().println("<body>");
			response.getWriter().println("<script>");
			response.getWriter().println("var div = document.createElement('div')");
			response.getWriter().println("document.body.appendChild(div)");
			response.getWriter().println("div.id='urlmanagerOne'");
			response.getWriter().println("div.innerText='" + jsonObject.toString() + "'");
			response.getWriter().println("</script>");
			response.getWriter().println("</body>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/receive")
	public void receive(HttpServletRequest request,HttpServletResponse response){
//		String jkid = request.getParameter("jkid");
//		ArchiveMonitorJob.timeAdminServer=0;//初始化请求后台服务时间
//		ArchiveMonitorJob.timeServerOut(jkid);//更新服务端访问时间
		String message = request.getParameter("message");
		response.setContentType("text/html;charset=UTF-8"); 
		String id = request.getParameter("id");
		if(StringUtils.isNotEmpty(message)){
			try {
					message=URLDecoder.decode(message,"UTF-8");
//					if(StringUtils.isNotEmpty(id)){
//						ArchiveMonitorJob.sendServer(message);//恢复短信预警
//					}
			} catch (Exception e) {
				log.error(message + " : message charset is unsupported ",e);
			}
		}
		ExecuteQueue.getInstance().changeStatusAndMsgById(id,  message);
		try {
			response.getWriter().println("<html>");
			response.getWriter().println("<body>");
			
			response.getWriter().println("<script>");
			response.getWriter().println("function closeWindow(){");
			response.getWriter().println("window.close();");
			response.getWriter().println("}");
			response.getWriter().println("setTimeout('closeWindow()',5000);");
			response.getWriter().println("</script>");
			
			response.getWriter().println("</body>");
			response.getWriter().println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
