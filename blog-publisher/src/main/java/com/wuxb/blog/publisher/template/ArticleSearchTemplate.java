package com.wuxb.blog.publisher.template;

import compoment.MyTemplate;

public class ArticleSearchTemplate extends MyTemplate {

	public static void main(String[] args) {
		ArticleSearchTemplate indexTemplate = new ArticleSearchTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "articleSearch";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		play();
	}
	
}
