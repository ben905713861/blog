package com.wuxb.blog.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;

@RestController
@RequestMapping("/album")
public class AlbumController {

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
	public List<Map<String, Object>> getDetail(@GetParam Map<String, Object> getMap) throws SQLException {
		List<Map<String, Object>> list = Db.table("album_img")
			.where("album_id", getMap.get("album_id"))
			.select();
		return list;
	}
	
}
