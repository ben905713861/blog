package com.wuxb.blog.admin.validate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.blog.admin.config.KaptchaConfig;
import com.wuxb.httpServer.Validate;
import com.wuxb.httpServer.util.HttpContextHolder;

public class LoginValidate extends Validate {

	protected static HashMap<String, Object> rulesMap = new LinkedHashMap<String, Object>();
	protected static Map<String, String> fieldNameList = new HashMap<String, String>();
	protected static Map<String, String> message = new HashMap<String, String>();
	protected static Map<String, Map<String, Object>> sceneList = new HashMap<String, Map<String, Object>>();
	
	static {
		rulesMap.put("name", "require|string|min:2|max:20");
		rulesMap.put("password", "require|string|min:6|max:20");
		rulesMap.put("vrf_code", "require|string|length:4|checkVrfCode");
		
		fieldNameList.put("name", "用户名");
		fieldNameList.put("password", "密码");
		fieldNameList.put("vrf_code", "图片验证码");
		
		message.put("vrf_code.checkVrfCode", "错误");
	}
	
	@Override
	protected Map<String, Object> setRulesMap() {
		return rulesMap;
	}
	
	@Override
	protected Map<String, String> setFieldNameList() {
		return fieldNameList;
	}

	@Override
	protected Map<String, String> setMessage() {
		return message;
	}
	
	@Override
	protected Map<String, Map<String, Object>> setSceneList() {
		return sceneList;
	}
	
	protected boolean checkVrfCode(Object data) {
		String _data = (String) data;
		String realVrfCode = (String) HttpContextHolder.getHttpServletRequest().getSession().get(KaptchaConfig.SESSION_KEY);
		if(realVrfCode == null || realVrfCode.isEmpty()) {
			return false;
		}
		if(!_data.equals(realVrfCode)) {
			return false;
		}
		return true;
	}

}
