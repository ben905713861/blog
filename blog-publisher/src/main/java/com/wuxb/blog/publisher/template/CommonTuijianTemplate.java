package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;

import compoment.MyTemplate;

public class CommonTuijianTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "common/tuijian";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		play();
	}
	
	public static void main(String[] args) {
		CommonTuijianTemplate commonAboutmeTemplate = new CommonTuijianTemplate();
		commonAboutmeTemplate.display(null);
	}
	
}
