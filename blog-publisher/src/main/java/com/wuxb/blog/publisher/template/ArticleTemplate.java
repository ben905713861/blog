package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class ArticleTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "article";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		for(Object row : inputData) {
			int article_id = (int) row;
			delete("/"+ article_id);
		}
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		data.put("articleTypeList", curlGetList("/article/getTypes"));
		
		for(Object temp : inputData) {
			String article_id = (String) temp;
			JSONObject articleInfo = curlGetMap("/article/getOne?article_id="+ article_id);
			//查询结果为空，执行删除
			if(articleInfo == null || articleInfo.size() == 0) {
				delete("/"+ article_id);
				continue;
			}
			data.put("articleInfo", articleInfo.getJSONObject("articleInfo"));
			data.put("nextArticleInfo", articleInfo.getJSONObject("nextArticleInfo"));
			data.put("lastArticleInfo", articleInfo.getJSONObject("lastArticleInfo"));
			play("/"+ article_id);
		}
	}
	
}
