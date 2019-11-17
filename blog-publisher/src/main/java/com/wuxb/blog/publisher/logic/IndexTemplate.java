package com.wuxb.blog.publisher.logic;

import compoment.MyTemplate;

public class IndexTemplate extends MyTemplate {

	public static void main(String[] args) {
		IndexTemplate indexTemplate = new IndexTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "index";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleBox", curlGetMap("/article/getList"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		play();
	}
	
}
