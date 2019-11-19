package com.wuxb.blog.publisher.template;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;
import compoment.Page;

public class ArticleListTemplate extends MyTemplate {

	private static final int limit = 3;
	
	public static void main(String[] args) {
		ArticleListTemplate indexTemplate = new ArticleListTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "articleList";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("articleRecommendList", curlGetList("/article/getRecommend"));
		
		JSONArray articleTypeList = curlGetList("/article/getTypes");
		{
			var totalArticle_num = 0;
			for(Object row : articleTypeList) {
				JSONObject articleType = (JSONObject) row;
				totalArticle_num += articleType.getIntValue("article_num");
			}
			Map<String, Object> articleType = new JSONObject();
			articleType.put("type_id", 0);
			articleType.put("type", "全部");
			articleType.put("article_num", totalArticle_num);
			articleTypeList.add(0, articleType);
		}
		data.put("articleTypeList", articleTypeList);
		
		for(Object row : articleTypeList) {
			JSONObject articleType = (JSONObject) row;
			String type_id = articleType.getString("type_id");
			data.put("articleType", articleType.getString("type"));
			
			int page = 1;
			while(true) {
				JSONObject articleBox = curlGetMap("/article/getList?title=&type_id="+ (type_id.equals("0") ? "" : type_id) +"&page="+ page +"&limit="+ limit);
				if(articleBox == null) {
					break;
				}
				data.put("articleBox", articleBox);
				//分页
				Page _page = new Page(limit);
				_page.setThisPage(page);
				_page.setTotalCount(articleBox.getIntValue("count"));
				data.put("page", _page.toMap());
				
				play("/"+ type_id +"/"+ page);
				if(_page.isEndPage()) {
					break;
				}
				page ++;
			}
		}
	}
	
}
