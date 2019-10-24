package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.util.Config;

@RestController
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/index")
	public Map<String, Object> index() throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> userInfo = Db.table("user").find();
		res.put("userInfo", userInfo);
		
//		List<Map<String, Object>> articleList = Db.table("article")
//			.field("article_id,title,description,thumb_path")
//			.order("add_time", "DESC")
//			.limit(10)
//			.select();
//		for(Map<String, Object> map : articleList) {
//			String thumb_path = (String) map.get("thumb_path");
//			if(thumb_path == null || thumb_path.isEmpty()) {
//				thumb_path = "images/default_thumb_264x176.jpg";
//				map.replace("thumb_path", thumb_path);
//			}
//		}
//		res.put("articleList", articleList);
		
//		List<Map<String, Object>> articleTypeList = Db.table("article_type at")
//			.field("at.*,COUNT(a.article_id) article_num")
//			.join("article a", "a.type_id=at.type_id", "LEFT")
//			.group("at.type_id")
//			.order("at.sort", "ASC")
//			.select();
//		res.put("articleTypeList", articleTypeList);
		
//		List<Map<String, Object>> articleRecommendList = Db.table("article")
//			.field("article_id,title")
//			.where("is_recommend", 1)
//			.order("add_time", "DESC")
//			.limit(10)
//			.select();
//		res.put("articleRecommendList", articleRecommendList);
		
//		List<Map<String, Object>> albumRecommendList = Db.table("album")
//			.field("album_id,title,thumb_path")
//			.where("is_recommend", 1)
//			.order("add_time", "DESC")
//			.limit(6)
//			.select();
//		for(Map<String, Object> map : albumRecommendList) {
//			String thumb_path = (String) map.get("thumb_path");
//			if(thumb_path == null || thumb_path.isEmpty()) {
//				thumb_path = "images/default_thumb_264x176.jpg";
//				map.replace("thumb_path", thumb_path);
//			}
//		}
//		res.put("albumRecommendList", albumRecommendList);
		
		return res;
	}
	
}
