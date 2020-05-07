package com.anjbo.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件发送
 * @Title JavaMailWithAttachment
 * @Description 
 * @author ch
 * @Date 2016-4-13 下午06:21:11
 */
public class EmailUtil {

	private MimeMessage message;
	private Session session;
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";

	private Properties properties = new Properties();

	/*
	 * 初始化方法
	 */
	private EmailUtil(boolean debug) {
		InputStream in = EmailUtil.class.getClassLoader()
				.getResourceAsStream("mailServer.properties");
		try {
			properties.load(in);
			this.mailHost = properties.getProperty("mail.smtp.host");
			this.sender_username = properties
					.getProperty("mail.sender.username");
			this.sender_password = properties
					.getProperty("mail.sender.password");
		} catch (IOException e) {
			e.printStackTrace();
		}

		session = Session.getInstance(properties);
		session.setDebug(debug);// 开启后有调试信息
		message = new MimeMessage(session);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUsers
	 *            收件人地址
	 * @param attachment
	 *            附件
	 */
	private void doSendHtmlEmail(String subject, String sendHtml,
			String[] receiveUsers, File[] attachment) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);

			// 收件人
			for (String receiveUser : receiveUsers) {
				InternetAddress to = new InternetAddress(receiveUser);
				message.setRecipient(Message.RecipientType.TO, to);

				// 邮件主题
				message.setSubject(subject);

				// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
				Multipart multipart = new MimeMultipart();

				// 添加邮件正文
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
				multipart.addBodyPart(contentPart);

				// 添加附件的内容
				if (attachment != null) {
					for (File file : attachment) {
						BodyPart attachmentBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(file);
						attachmentBodyPart.setDataHandler(new DataHandler(
								source));
						// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
						// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
						// sun.misc.BASE64Encoder enc = new
						// sun.misc.BASE64Encoder();
						// messageBodyPart.setFileName("=?GBK?B?" +
						// enc.encode(attachment.getName().getBytes()) + "?=");

						// MimeUtility.encodeWord可以避免文件名乱码。
						// 解决tomcat下中文命名,默认tomcat编码gb2312
						attachmentBodyPart.setFileName(MimeUtility.encodeWord(
								file.getName(), "gb2312", null));
						multipart.addBodyPart(attachmentBodyPart);
					}
				}

				// 将multipart对象放到message中
				message.setContent(multipart);
				// 保存邮件
				message.saveChanges();

				transport = session.getTransport("smtp");
				// smtp验证，就是你用来发邮件的邮箱用户名密码
				transport.connect(mailHost, sender_username, sender_password);
				// 发送
				transport.sendMessage(message, message.getAllRecipients());

				System.out.println(receiveUser + " send success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 发送邮件
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param email 发送人
	 */
	public static void sendEmial(String title,String content,String email){
		EmailUtil se = new EmailUtil(false);
		se.doSendHtmlEmail(title,content, new String[]{email},null);
	}
	/**
	 * 发送邮件（多人）
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param emails 发送人集合
	 */
	public static void sendEmial(String title,String content,String []emails){
		EmailUtil se = new EmailUtil(false);
		se.doSendHtmlEmail(title,content,emails,null);
	}
	/**
	 * 发送邮件（多人）（附件）
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param emails 发送人集合
	 * @param files 附件集合
	 */
	public static void sendEmial(String title,String content,String []emails,File[] files){
		EmailUtil se = new EmailUtil(false);
		se.doSendHtmlEmail(title,content,emails,files);
	}
	public static void main(String[] args) {
		File[] files = {new File("D:\\generatorConfig.xml")};
		sendEmial("开发部豆豆","皮皮思，我们走", new String[]{"2572279101@qq.com"},files);
	}
}
