package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/articleComment")
public class ArticleCommentController {

	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = Db.table("article_comment")
			.where("article_id", getMap.get("article_id"))
			.order("add_time", "DESC")
			.page((long)getMap.getOrDefault("page", 1l), (long)getMap.getOrDefault("limit", 10l))
			.select();
		res.put("list", list);
		
		int count = Db.table("article_comment")
			.where("article_id", getMap.get("article_id"))
			.count();
		res.put("count", count);
		
		return res;
	}
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@PostParam Map<String, Object> postMap, HttpServletRequest httpServletRequest) throws SQLException {
		Map<String, Object> data = new HashMap<String, Object>();
			data.put("article_id", postMap.get("article_id"));
			data.put("name", postMap.get("name"));
			data.put("connection", postMap.get("connection"));
			data.put("content", postMap.get("content"));
			data.put("ip", Tools.ip2long(httpServletRequest.getIp()));
			data.put("add_time", Tools.time());
		Db.table("article_comment").insert(data);
		return Tools.returnSucc();
	}
	
}
