package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class GbookTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "gbook";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		delete();
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		play();
	}
	
}
