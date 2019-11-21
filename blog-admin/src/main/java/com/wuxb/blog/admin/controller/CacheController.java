package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.Publisher;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/cache")
public class CacheController {
	
	@RequestMapping("/rebuild")
	public Map<String, Object> rebuild(@PostParam Map<String, Object> postMap) throws Exception {
		String type = (String) postMap.get("type");
		switch(type) {
			case "all":
				index();
				articleList();
				article();
				albumList();
				album();
				break;
			case "index":
				index();
				break;
			case "articleList":
				articleList();
				break;
			case "article":
				article();
				break;
			case "albumList":
				albumList();
				break;
			case "album":
				album();
				break;
			default:
				throw new Exception("type不支持");
		}
		return Tools.returnSucc();
	}
	
	private static void index() {
		new Publisher("index").send();
		new Publisher("gbook").send();
		new Publisher("articleSearch").send();
		new Publisher("about").send();
	}
	
	private static void articleList() throws SQLException {
		List<Object> type_ids = Db.table("article_type").column("type_id");
		type_ids.add(0);
		Publisher publisher = new Publisher("articleList");
		publisher.addInputData(type_ids);
		publisher.send();
	}
	
	private static void article() throws SQLException {
		List<Object> article_ids = Db.table("article").column("article_id");
		Publisher publisher = new Publisher("article");
		publisher.addInputData(article_ids);
		publisher.send();
	}
	
	private static void albumList() {
		new Publisher("albumList").send();
	}
	
	private static void album() throws SQLException {
		List<Object> album_ids = Db.table("album").column("album_id");
		Publisher publisher = new Publisher("album");
		publisher.addInputData(album_ids);
		publisher.send();
	}
	
}
