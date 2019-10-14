package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@RequestMapping("/init")
	public List<Map<String, Object>> init() throws SQLException {
		return Db.table("article_type").select();
	}
	
	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		HashMap<String, Object> where = new HashMap<String, Object>();
		Map<String, Object> search = (Map<String, Object>) getMap.get("search");
		//分类
		Long type_id = (Long) search.get("type_id");
		if(type_id != null) {
			where.put("a.type_id", type_id);
		}
		//标题
		String title = (String) search.get("title");
		if(title != null && !title.isEmpty()) {
			where.put("a.title", new Object[] {"LIKE", "%"+title+"%"});
		}
		List<Map<String, Object>> rows = Db.table("article a")
			.join("article_type at", "at.type_id=a.type_id", "LEFT")
			.field("a.*,at.type")
			.where(where)
			.order("a.add_time", "DESC")
			.limit((long)getMap.get("offset"), (long)getMap.get("limit"))
			.select();
		res.put("rows", rows);
		int total = Db.table("article a")
			.where(where)
			.count();
		res.put("total", total);
		return res;
	}
	
}
