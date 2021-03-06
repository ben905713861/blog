package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;
import compoment.MyTemplate;

public class AboutTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "about";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		delete();
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("aboutInfo", curlGetMap("/about/index"));
		play();
	}

}
