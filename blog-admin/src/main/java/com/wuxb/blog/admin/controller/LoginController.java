package com.wuxb.blog.admin.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.wuxb.blog.admin.config.KaptchaConfig;
import com.wuxb.blog.admin.validate.LoginValidate;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Validate;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Encrypt;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/getVrfCode")
	public byte[] getVrfCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		// 生产验证码字符串并保存到session中
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(KaptchaConfig.getConfig());
		String vrfCode = defaultKaptcha.createText();
		httpServletRequest.getSession().set(KaptchaConfig.SESSION_KEY, vrfCode);
		// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
		BufferedImage challenge = defaultKaptcha.createImage(vrfCode);
		ImageIO.write(challenge, "png", jpegOutputStream);
		httpServletResponse.getHeader().setContentType("image/png");
		return jpegOutputStream.toByteArray();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Map<String, Object> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PostParam Map<String, Object> postMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		Validate validate = new LoginValidate();
		if(!validate.check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		Map<String, Object> managerInfo = Db.table("manager")
			.field("manager_id,name,password")
			.where("name", postMap.get("name"))
			.find();
		if(managerInfo == null) {
			return Tools.returnErr("用户名不存在");
		}
		String password = (String) postMap.get("password");
		if(!managerInfo.get("password").equals(Encrypt.md5(password))) {
			return Tools.returnErr("密码错误");
		}
		//成功
		httpServletRequest.getSession().set("manager_id", managerInfo.get("manager_id"));
		res.put("status", true);
		res.put("name", managerInfo.get("name"));
		return res;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws SQLException {
		httpServletRequest.getSession().destory();
		return "[]";
	}
	
}
