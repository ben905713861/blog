package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class ArticleSearchTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "articleSearch";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		delete();
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		play();
	}
	
}
