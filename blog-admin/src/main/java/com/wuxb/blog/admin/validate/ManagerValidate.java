package com.wuxb.blog.admin.validate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.httpServer.Validate;

public class ManagerValidate extends Validate {

	protected static Map<String, Object> rulesMap = new LinkedHashMap<String, Object>();
	protected static Map<String, String> fieldNameList = new HashMap<String, String>();
	protected static Map<String, String> message = new HashMap<String, String>();
	protected static Map<String, Map<String, Object>> sceneList = new HashMap<String, Map<String, Object>>();
	
	static {
		rulesMap.put("password", "string|min:6|max:20");
		rulesMap.put("real_name", "string|max:20");
		rulesMap.put("email", "string|max:40");
		rulesMap.put("phone", "string|length:11");
		
		fieldNameList.put("password", "密码");
		fieldNameList.put("real_name", "真实姓名");
		fieldNameList.put("email", "邮箱");
		fieldNameList.put("phone", "手机");
		
		Map<String, Object> rulesMap_add = new LinkedHashMap<String, Object>();
		rulesMap_add.put("password", null);
		rulesMap_add.put("real_name", null);
		rulesMap_add.put("email", null);
		rulesMap_add.put("phone", null);
		sceneList.put("selfUpdate", rulesMap_add);
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
	
}
