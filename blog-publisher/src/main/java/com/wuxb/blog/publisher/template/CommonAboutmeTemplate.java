package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class CommonAboutmeTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "common/aboutme";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		play();
	}
	
}
