package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class ArticleTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "article";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		for(Object row : inputData) {
			int type_id = (int) row;
			delete("/"+ type_id);
		}
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		
		for(Object temp : inputData) {
			String article_id = (String) temp;
			JSONObject articleInfo = curlGetMap("/article/getOne?article_id="+ article_id);
			data.put("articleInfo", articleInfo.getJSONObject("articleInfo"));
			data.put("nextArticleInfo", articleInfo.getJSONObject("nextArticleInfo"));
			data.put("lastArticleInfo", articleInfo.getJSONObject("lastArticleInfo"));
			play("/"+ article_id);
		}
	}
	
}
