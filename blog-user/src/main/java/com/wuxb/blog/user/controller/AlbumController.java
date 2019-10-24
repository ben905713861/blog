package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.util.Config;

@RestController
@RequestMapping("/album")
public class AlbumController {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/getRecommend")
	public List<Map<String, Object>> getRecommend(@GetParam Map<String, Object> getMap) throws SQLException {
		List<Map<String, Object>> albumRecommendList = Db.table("album")
			.field("album_id,title,thumb_path")
			.where("is_recommend", 1)
			.order("add_time", "DESC")
			.limit(6)
			.select();
		for(Map<String, Object> map : albumRecommendList) {
			String thumb_path = (String) map.get("thumb_path");
			if(thumb_path == null || thumb_path.isEmpty()) {
				thumb_path = "images/default_thumb_264x176.jpg";
				map.replace("thumb_path", thumb_path);
			}
		}
		return albumRecommendList;
	}
	
	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, Object>> list = Db.table("album")
			.order("add_time", "DESC")
			.page((long)getMap.get("page"), (long)getMap.get("limit"))
			.select();
		res.put("list", list);
		int count = Db.table("album").count();
		res.put("count", count);
		return res;
	}
	
	@RequestMapping("/getDetail")
	public Map<String, Object> getDetail(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> albumInfo = Db.table("album")
			.where("album_id", getMap.get("album_id"))
			.find();
		res.put("albumInfo", albumInfo);
		
		List<Map<String, Object>> list = Db.table("album_img")
			.where("album_id", getMap.get("album_id"))
			.select();
		for(Map<String, Object> map : list) {
			String path = (String) map.get("path");
			map.put("url", FILE_SERVER_DOMAIN + path);
			map.remove("path");
		}
		res.put("imgList", list);
		
		return res;
	}
	
}
