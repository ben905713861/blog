package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class IndexTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "index";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		delete();
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleBox", curlGetMap("/article/getList"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		play();
	}
	
}
