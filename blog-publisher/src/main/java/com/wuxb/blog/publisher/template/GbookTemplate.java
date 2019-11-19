package com.wuxb.blog.publisher.template;

import compoment.MyTemplate;

public class GbookTemplate extends MyTemplate {

	public static void main(String[] args) {
		GbookTemplate indexTemplate = new GbookTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "gbook";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		play();
	}
	
}
