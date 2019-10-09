package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;

@RestController
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/index")
	public Map<String, Object> index() throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> userInfo = Db.table("user").find();
		res.put("userInfo", userInfo);
		List<Map<String, Object>> articleList = Db.table("article")
			.field("article_id,title,description,thumb_path")
			.order("add_time", "DESC")
			.limit(10)
			.select();
		res.put("articleList", articleList);
		List<Map<String, Object>> articleTypeList = Db.table("article_type t")
			.field("t.*,COUNT(a.article_id) article_num")
			.join("article a", "a.type_id=t.type_id", "LEFT")
			.group("t.type_id")
			.select();
		res.put("articleTypeList", articleTypeList);
		List<Map<String, Object>> articleRecommendList = Db.table("article")
			.field("article_id,title")
			.where("is_recommend", 1)
			.order("add_time", "DESC")
			.limit(10)
			.select();
		res.put("articleRecommendList", articleRecommendList);
		List<Map<String, Object>> albumRecommendList = Db.table("album")
			.field("album_id,title,thumb_path")
			.where("is_recommend", 1)
			.order("add_time", "DESC")
			.limit(6)
			.select();
		res.put("albumRecommendList", albumRecommendList);
		return res;
	}
	
}
