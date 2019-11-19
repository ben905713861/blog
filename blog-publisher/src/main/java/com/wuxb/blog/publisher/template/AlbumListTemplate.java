package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;
import compoment.Page;

public class AlbumListTemplate extends MyTemplate {

	private static final int limit = 8;
	
	public static void main(String[] args) {
		AlbumListTemplate indexTemplate = new AlbumListTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "albumList";
	}

	@Override
	public void display() {
		int page = 1;
		while(true) {
			JSONObject albumBox = curlGetMap("/album/getList?page="+ page +"&limit="+ limit);
			if(albumBox == null) {
				break;
			}
			data.put("albumBox",  albumBox);
			//分页
			Page _page = new Page(limit);
			_page.setThisPage(page);
			_page.setTotalCount(albumBox.getIntValue("count"));
			data.put("page", _page.toMap());
			
			play("/"+ page);
			if(_page.isEndPage()) {
				break;
			}
			page ++;
		}
	}
	
}
