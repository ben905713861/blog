package com.wuxb.blog.admin.config;

import java.util.Properties;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.util.Config;

public class KaptchaConfig {
	
	private static Config config;
	public static final String SESSION_KEY = Constants.KAPTCHA_SESSION_KEY;
	
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
		config = new Config(properties);
	}
	
	public static Config getConfig() {
		return config;
	}
	
}
