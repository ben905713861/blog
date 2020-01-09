package com.wuxb.blog.publisher.template;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;
import compoment.Page;

public class ArticleListTemplate extends MyTemplate {

	private static final int limit = 10;
	
	@Override
	protected String setTemplate() {
		return "articleList";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		for(Object row : inputData) {
			String type_id = (row instanceof Integer) ? String.valueOf((int) row) : (String) row;
			delete("/"+ type_id);
		}
	}

	@Override
	public void display(JSONArray inputData) {
		JSONArray articleTypeList = curlGetList("/article/getTypes");
		Map<String, String> type_id2type = new HashMap<String, String>();
		for(Object row : articleTypeList) {
			JSONObject articleType = (JSONObject) row;
			String type_id = articleType.getString("type_id");
			String type = articleType.getString("type");
			type_id2type.put(type_id, type);
		}
		type_id2type.put("0", "全部");
		
		for(Object row : inputData) {
			String type_id = (row instanceof Integer) ? String.valueOf((int) row) : (String) row;
			String type = type_id2type.get(type_id);
			data.put("articleType", type);
			
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
