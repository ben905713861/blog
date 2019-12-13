package com.wuxb.blog.publisher.template;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class CommonFenleiTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "common/fenlei";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		
	}

	@Override
	public void display(JSONArray inputData) {
		JSONArray articleTypeList = curlGetList("/article/getTypes");
		{
			int totalArticle_num = 0;
			for(Object row : articleTypeList) {
				JSONObject articleType = (JSONObject) row;
				totalArticle_num += articleType.getIntValue("article_num");
			}
			Map<String, Object> articleType = new JSONObject();
			articleType.put("type_id", 0);
			articleType.put("type", "全部");
			articleType.put("article_num", totalArticle_num);
			articleTypeList.add(0, articleType);
		}
		data.put("articleTypeList", articleTypeList);
		
		play();
	}
	
}
