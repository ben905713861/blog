package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.HtmlFilter;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		HashMap<String, Object> where = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> search = (Map<String, Object>) getMap.get("search");
		//发布时间
		String start_date = (String) search.get("start_date");
		String end_date = (String) search.get("end_date");
		if(start_date != null && end_date == null) {
			where.put("add_time", new Object[] {">=", Tools.dateStr2time(start_date)});
		} else if(start_date == null && end_date != null) {
			where.put("add_time", new Object[] {"<=", Tools.dateStr2time(end_date)+24*3600-1});
		} else if(start_date != null && end_date != null) {
			where.put("add_time", new Object[] {"BETWEEN", new Object[]{Tools.dateStr2time(start_date), Tools.dateStr2time(end_date)+24*3600-1}});
		}
		//ip
		String ip = (String) search.get("ip");
		if(ip != null && !ip.isEmpty()) {
			where.put("ip", Tools.ip2long(ip));
		}
		
		List<Map<String, Object>> rows = Db.table("comment")
			.where(where)
			.order("add_time", "DESC")
			.limit((long)getMap.get("offset"), (long)getMap.get("limit"))
			.select();
		for(Map<String, Object> row : rows) {
			String html = (String) row.get("content");
			html = HtmlFilter.stripTags(html);
			row.replace("content", html);
		}
		res.put("rows", rows);
		
		int total = Db.table("comment")
			.where(where)
			.count();
		res.put("total", total);
		
		return res;
	}
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("comment")
			.where("comment_id", getMap.get("comment_id"))
			.find();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		Db.table("comment")
			.where("comment_id", postMap.get("comment_id"))
			.limit(1)
			.delete();
		return Tools.returnSucc();
	}
	
}
