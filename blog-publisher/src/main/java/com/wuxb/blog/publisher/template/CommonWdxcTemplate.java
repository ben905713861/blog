package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class CommonWdxcTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "common/wdxc";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		play();
	}
	
}
