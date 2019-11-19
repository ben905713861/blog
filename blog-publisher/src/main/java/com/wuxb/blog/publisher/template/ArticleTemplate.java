package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class ArticleTemplate extends MyTemplate {

	public static void main(String[] args) {
		ArticleTemplate indexTemplate = new ArticleTemplate();
		indexTemplate.display();
	}
	
	private int[] article_ids = new int[] {1};

	@Override
	protected String setTemplate() {
		return "article";
	}
	
	public void setArticleIds(int[] article_ids) {
		this.article_ids = article_ids;
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		
		for(int article_id : article_ids) {
			JSONObject articleInfo = curlGetMap("/article/getOne?article_id="+ article_id);
			data.put("articleInfo", articleInfo.getJSONObject("articleInfo"));
			data.put("nextArticleInfo", articleInfo.getJSONObject("nextArticleInfo"));
			data.put("lastArticleInfo", articleInfo.getJSONObject("lastArticleInfo"));
			play("/"+ article_id);
		}
	}
	
}
