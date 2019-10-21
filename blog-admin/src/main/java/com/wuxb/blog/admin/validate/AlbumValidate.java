package com.wuxb.blog.admin.validate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.httpServer.Validate;

public class AlbumValidate extends Validate {

	protected static Map<String, Object> rulesMap = new LinkedHashMap<String, Object>();
	protected static Map<String, String> fieldNameList = new HashMap<String, String>();
	protected static Map<String, String> message = new HashMap<String, String>();
	protected static Map<String, Map<String, Object>> sceneList = new HashMap<String, Map<String, Object>>();
	
	static {
		rulesMap.put("album_id", "require|integer");
		rulesMap.put("title", "require|string|max:50");
		rulesMap.put("description", "string|max:255");
		rulesMap.put("is_recommend", "require|bool");
		rulesMap.put("thumb_path", "url");
		
		fieldNameList.put("title", "相册名");
		fieldNameList.put("description", "描述");
		fieldNameList.put("is_recommend", "是否推荐");
		fieldNameList.put("thumb_path", "缩略图");
		
		Map<String, Object> rulesMap_add = new LinkedHashMap<String, Object>();
			rulesMap_add.put("title", null);
			rulesMap_add.put("description", null);
			rulesMap_add.put("is_recommend", null);
			rulesMap_add.put("thumb_path", null);
		sceneList.put("add", rulesMap_add);
		Map<String, Object> rulesMap_update = new LinkedHashMap<String, Object>();
			rulesMap_update.put("album_id", null);
			rulesMap_update.put("title", null);
			rulesMap_update.put("description", null);
			rulesMap_update.put("is_recommend", null);
			rulesMap_update.put("thumb_path", null);
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
