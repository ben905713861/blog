package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.Publisher;
import com.wuxb.blog.admin.validate.ArticleTypeValidate;
import com.wuxb.httpServer.Validate;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		HashMap<String, Object> where = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> search = (Map<String, Object>) getMap.get("search");
		//标题
		String type = (String) search.get("type");
		if(type != null && !type.isEmpty()) {
			where.put("at.type", new Object[] {"LIKE", "%"+ type +"%"});
		}
		
		List<Map<String, Object>> rows = Db.table("article_type at")
			.join("article a", "a.type_id=at.type_id", "LEFT")
			.field("at.*,COUNT(a.article_id) article_num")
			.where(where)
			.group("at.type_id")
			.order("at.sort", "ASC")
			.limit((long)getMap.get("offset"), (long)getMap.get("limit"))
			.select();
		res.put("rows", rows);
		
		int total = Db.table("article_type at")
			.where(where)
			.count();
		res.put("total", total);
		return res;
	}
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("article_type")
			.where("type_id", getMap.get("type_id"))
			.find();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleTypeValidate();
		if(!validate.scene("add").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", postMap.get("type"));
		data.put("sort", postMap.get("sort"));
		int type_id = Db.table("article_type").insert(data);
		
		Publisher publisher = new Publisher("articleList");
		publisher.addInputData(type_id);
		publisher.send();
		
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleTypeValidate();
		if(!validate.scene("update").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		Object type_id = postMap.get("type_id");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", postMap.get("type"));
		data.put("sort", postMap.get("sort"));
		Db.table("article_type")
			.where("type_id", type_id)
			.limit(1)
			.update(data);
		
		Publisher publisher = new Publisher("articleList");
		publisher.addInputData(type_id);
		publisher.send();
		
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		int articleNum = Db.table("article").where("type_id", postMap.get("type_id")).count();
		if(articleNum > 0) {
			return Tools.returnErr("该分类下存在文章，请先删除分类下的文章在进行本操作");
		}
		
		Object type_id = postMap.get("type_id");
		Db.table("article_type")
			.where("type_id", type_id)
			.limit(1)
			.delete();
		
		Publisher publisher = new Publisher("articleList");
		publisher.addDeleteData(type_id);
		publisher.send();
		
		return Tools.returnSucc();
	}
	
}
