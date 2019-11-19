package com.wuxb.blog.publisher.template;

import compoment.MyTemplate;

public class AboutTemplate extends MyTemplate {

	public static void main(String[] args) {
		AboutTemplate indexTemplate = new AboutTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "about";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		data.put("aboutInfo", curlGetMap("/about/index"));
		play();
	}
	
}
