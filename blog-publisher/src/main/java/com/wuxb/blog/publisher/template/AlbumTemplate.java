package com.wuxb.blog.publisher.template;

import com.alibaba.fastjson.JSONObject;

import compoment.MyTemplate;

public class AlbumTemplate extends MyTemplate {

	public static void main(String[] args) {
		AlbumTemplate indexTemplate = new AlbumTemplate();
		indexTemplate.display();
	}

	@Override
	protected String setTemplate() {
		return "album";
	}

	@Override
	public void display() {
		data.put("userInfo", curlGetMap("/index/index").get("userInfo"));
		data.put("albumRecommendList", curlGetList("/album/getRecommend"));
		
		int[] album_ids = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52};
		for(int album_id : album_ids) {
			JSONObject albumInfo = curlGetMap("/album/getDetail?album_id="+ album_id);
			data.put("albumInfo", albumInfo.getJSONObject("albumInfo"));
			data.put("imgList", albumInfo.getJSONArray("imgList"));
			play("/"+ album_id);
		}
	}
	
}
