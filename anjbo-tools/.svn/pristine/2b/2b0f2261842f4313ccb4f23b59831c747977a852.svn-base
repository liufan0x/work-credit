package com.anjbo.common;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.anjbo.utils.AmsUtil;
/**
 * 异步发送邮件（用于批量）
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午02:44:44
 */
public class SendEmailWorker implements Callable<String> {

	private String title;
	private String content;
	private String email;

	public SendEmailWorker(String title, String content,String email) {
		this.title = title;
		this.content = content;
		this.email = email;
	}

	@Override
	public String call() throws Exception {
		 RespStatus status = AmsUtil.emailSend(title, email,content);
		 return status.getCode();
	}

	/**
	 * 异步发送邮件
	 * @author limh@anjbo.com
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param email 邮件地址
	 */
	public static void asyncSendEmail(ThreadPoolTaskExecutor poolTaskExecutor,String title, String content, String email) {
		SendEmailWorker sew = new SendEmailWorker(title, content,email);
		FutureTask<String> futureTask = new FutureTask<String>(sew);
		poolTaskExecutor.submit(futureTask);
	}
}
