package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class AlbumTemplate extends MyTemplate {

	@Override
	protected String setTemplate() {
		return "album";
	}
	
	@Override
	public void clear(JSONArray inputData) {
		for(Object row : inputData) {
			int album_id = (int) row;
			delete("/"+ album_id);
		}
	}

	@Override
	public void display(JSONArray inputData) {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		
		for(Object row : inputData) {
			int album_id = (int) row;
			JSONObject info = curlGetMap("/album/getDetail?album_id="+ album_id);
			
			JSONObject albumInfo = info.getJSONObject("albumInfo");
			if(albumInfo == null || albumInfo.size() == 0) {
				delete("/"+ album_id);
				continue;
			}
			data.put("albumInfo",albumInfo);
			data.put("imgList", info.getJSONArray("imgList"));
			play("/"+ album_id);
		}
	}
	
}
