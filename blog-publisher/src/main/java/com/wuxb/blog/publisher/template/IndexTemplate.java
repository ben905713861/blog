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
		data.put("articleBox", curlGetMap("/article/getList"));
		play();
	}
	
}
