package com.wuxb.blog.publisher.logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class ArticleTemplate extends MyTemplate {

	public static void main(String[] args) {
		ArticleTemplate indexTemplate = new ArticleTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "article";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		
		JSONArray article_ids = curlGetList("/article/getAllArticleId");
		for(Object row : article_ids) {
			int article_id = (int) row;
			JSONObject articleInfo = curlGetMap("/article/getOne?article_id="+ article_id);
			
			data.put("articleInfo", articleInfo.getJSONObject("articleInfo"));
			data.put("nextArticleInfo", articleInfo.getJSONObject("nextArticleInfo"));
			data.put("lastArticleInfo", articleInfo.getJSONObject("lastArticleInfo"));
			play("/"+ article_id);
		}
	}
	
}
