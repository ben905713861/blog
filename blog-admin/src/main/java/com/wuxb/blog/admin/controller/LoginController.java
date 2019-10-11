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
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.util.Encrypt;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static Config kaptchaConfig;
	
	static {
		Properties properties = new Properties();
		// 图片边框
		properties.setProperty("kaptcha.border", "no");
		// 边框颜色
//		properties.setProperty("kaptcha.border.color", "105,179,90");
		// 字体颜色
		properties.setProperty("kaptcha.textproducer.font.color", "red");
		// 图片宽
		properties.setProperty("kaptcha.image.width", "160");
		// 图片高
		properties.setProperty("kaptcha.image.height", "40");
		// 字体大小
		properties.setProperty("kaptcha.textproducer.font.size", "36");
		// session key
		properties.setProperty("kaptcha.session.key", "code");
		// 验证码长度
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		// 字体
//		properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		kaptchaConfig = new Config(properties);
	}
	
	@RequestMapping("/getVrfCode")
	public byte[] getVrfCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		// 生产验证码字符串并保存到session中
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(kaptchaConfig);
		String createText = defaultKaptcha.createText();
		httpServletRequest.getSession().set(Constants.KAPTCHA_SESSION_KEY, createText);
		// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
		BufferedImage challenge = defaultKaptcha.createImage(createText);
		ImageIO.write(challenge, "png", jpegOutputStream);
		httpServletResponse.getHeader().setContentType("image/png");
		return jpegOutputStream.toByteArray();
	}
	
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PostParam Map<String, Object> postMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		//验证码检验
		String real_vrfCode = (String) httpServletRequest.getSession().get(Constants.KAPTCHA_SESSION_KEY);
		String vrfCode = (String) postMap.get("vrfCode");
		if(!vrfCode.equals(real_vrfCode)) {
			res.put("status", false);
			res.put("msg", "验证码错误");
			return res;
		}
		Map<String, Object> managerInfo = Db.table("manager")
			.field("manager_id,password")
			.where("name", postMap.get("name"))
			.find();
		if(managerInfo == null) {
			res.put("status", false);
			res.put("msg", "用户名不存在");
			return res;
		}
		String password = (String) postMap.get("password");
		if(!managerInfo.get("password").equals(Encrypt.md5(password))) {
			res.put("status", false);
			res.put("msg", "密码错误");
			return res;
		}
		//成功
		res.put("status", true);
		return res;
	}
	
}
