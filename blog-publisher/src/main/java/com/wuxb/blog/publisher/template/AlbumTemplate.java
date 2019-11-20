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
			JSONObject albumInfo = curlGetMap("/album/getDetail?album_id="+ album_id);
			data.put("albumInfo", albumInfo.getJSONObject("albumInfo"));
			data.put("imgList", albumInfo.getJSONArray("imgList"));
			play("/"+ album_id);
		}
	}
	
}
