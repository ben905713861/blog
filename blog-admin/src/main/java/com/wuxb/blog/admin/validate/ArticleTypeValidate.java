package com.wuxb.blog.admin.validate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.httpServer.Validate;

public class ArticleTypeValidate extends Validate {

	protected static Map<String, Object> rulesMap = new LinkedHashMap<String, Object>();
	protected static Map<String, String> fieldNameList = new HashMap<String, String>();
	protected static Map<String, String> message = new HashMap<String, String>();
	protected static Map<String, Map<String, Object>> sceneList = new HashMap<String, Map<String, Object>>();
	
	static {
		rulesMap.put("type_id", "require|integer");
		rulesMap.put("type", "require|string|max:20");
		rulesMap.put("sort", "require|number|between:0,255");
		
		fieldNameList.put("type", "分类名");
		
		Map<String, Object> rulesMap_add = new LinkedHashMap<String, Object>();
			rulesMap_add.put("type", null);
			rulesMap_add.put("sort", null);
		sceneList.put("add", rulesMap_add);
		Map<String, Object> rulesMap_update = new LinkedHashMap<String, Object>();
			rulesMap_update.put("type_id", null);
			rulesMap_update.put("type", null);
			rulesMap_add.put("sort", null);
		sceneList.put("update", rulesMap_update);
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
