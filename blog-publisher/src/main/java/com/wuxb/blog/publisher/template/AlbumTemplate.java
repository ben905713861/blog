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
			String album_id = (row instanceof Integer) ? String.valueOf((int) row) : (String) row;
			delete("/"+ album_id);
		}
	}

	@Override
	public void display(JSONArray inputData) {
		for(Object row : inputData) {
			String album_id = (row instanceof Integer) ? String.valueOf((int) row) : (String) row;
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
