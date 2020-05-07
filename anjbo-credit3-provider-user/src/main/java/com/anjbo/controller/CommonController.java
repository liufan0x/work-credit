/*
 *Project: anjbo-credit3-user-provider
 *File: com.anjbo.controller.CommonController.java  <2017年12月8日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.UserDto;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SMSConstants;
import com.anjbo.service.UserService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CookieUtils;
import com.anjbo.utils.IpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.UidUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;

/**
 * @Author KangLG
 * @Date 2017年12月8日 下午2:43:14
 * @version 1.0
 */
@Controller("CommonController_USER")
public class CommonController extends BaseController {
	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@Resource
	private UserService userService;

	@RequestMapping("/auth")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		ServletOutputStream responseOutputStream = null;
		try {
			// 生产验证码字符串并保存到session中
			String createText = defaultKaptcha.createText();
			httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);

			// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
			captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
			httpServletResponse.setHeader("Cache-Control", "no-store");
			httpServletResponse.setHeader("Pragma", "no-cache");
			httpServletResponse.setDateHeader("Expires", 0);
			httpServletResponse.setContentType("image/jpeg");
			responseOutputStream = httpServletResponse.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);

		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} finally {
			if (null != responseOutputStream) {
				responseOutputStream.flush();
				responseOutputStream.close();
			}
		}
	}

	@ResponseBody
	@RequestMapping("/login")
	public RespDataObject<UserDto> login(@RequestBody Map<String, String> params) throws Exception {
		try {
			return userService.login(this.request, params);
		} catch (Exception e) {
			logger.error("PC登陆异常", e);
		}
		return new RespDataObject<UserDto>(null, RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
	}

	@ResponseBody
	@RequestMapping("/logout")
	public RespStatus logout() throws Exception {
		try {
			HttpSession session = request.getSession(false);
			session.removeAttribute(com.anjbo.common.Constants.LOGIN_USER_KEY);
		} catch (Exception e) {
			logger.error("PC登出异常", e);
		}
		return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
	}
	
	@ResponseBody
	@RequestMapping("/appLogin")
	public RespDataObject<UserDto> appLogin(@RequestBody Map<String, String> params) throws Exception {
		try {
			return userService.login(this.request, params);
		} catch (Exception e) {
			logger.error("app登陆异常", e);
		}
		return new RespDataObject<UserDto>(null, RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
	}
	
	@ResponseBody
	@RequestMapping("/appLogout")
	public RespStatus appLogout(HttpServletRequest request) throws Exception {
		try {
			String uid = request.getHeader("uid");
			String deviceId = request.getHeader("deviceId");
			UserDto userDto = new UserDto();
			userDto.setUid(uid);
			userDto.setToken("");
			userService.updateToken(userDto);
			RedisOperator.delete(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + uid + ":" + MD5Utils.MD5Encode(deviceId));
		} catch (Exception e) {
			logger.error("app登出异常", e);
		}
		return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
	}

	@ResponseBody
	@RequestMapping(value = "/sendSMS")
	public RespStatus sendSMS(HttpServletRequest request, @RequestBody Map<String, String> params) {
		String validateCode = MapUtils.getString(params, "validateCode", "");
		if (null == params || StringUtils.isEmpty(MapUtils.getString(params, "userMobile", "")) || StringUtils.isEmpty(validateCode)) {
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		} else if (CookieUtils.authCode(request.getSession(), validateCode)) {
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.VERIFYCODE_ERROR.getMsg());
		}
		try {
			String code = UidUtil.generateNo(4);
			AmsUtil.smsSend(params.get("userMobile"), IpUtil.getClientIp(request), SMSConstants.SMS_CODE, code);
			request.getSession().setAttribute("LOGIN_USER_CODE_SMS", code);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespHelper.failRespStatus();
	}

	@ResponseBody
	@RequestMapping("/test")
	public RespDataObject<UserDto> test() throws Exception {
		System.out.println(com.anjbo.common.Constants.BASE_AMS_IPWHITE);
		return new RespDataObject<UserDto>(null, RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
	}

}
