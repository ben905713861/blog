package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.blog.admin.validate.ArticleValidate;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.Validate;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.util.FormdataParamsEncode;
import com.wuxb.httpServer.util.Tools;

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
		@SuppressWarnings("unchecked")
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
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("article a")
			.join("article_content c", "c.article_id=a.article_id", "LEFT")
			.field("a.*,c.content")
			.where("a.article_id", getMap.get("article_id"))
			.find();
	}
	
	@RequestMapping("/uploadThumb")
	public Map<String, Object> uploadThumb(HttpServletRequest httpServletRequest) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		if(fileList.size() == 0) {
			return Tools.returnErr("上传文件不得为空");
		}
		FileInfo fileInfo = fileList.get(0);
		
		UploadFile uploadFile = new UploadFile();
		FormdataParamsEncode formdata = uploadFile.getFormdataObject();
		formdata.add("articleThumb", fileInfo);
		if(!uploadFile.send(formdata)) {
			return Tools.returnErr(uploadFile.getErrorMessage());
		}
		List<Map<String, Object>> fileResultList = uploadFile.getFileResultList();
		Map<String, Object> fileResult = fileResultList.get(0);
		
		resMap.put("status", true);
		resMap.put("url", fileResult.get("url"));
		return resMap;
	}
	
	@RequestMapping("/add")
	public Map<String, Object> add(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleValidate();
		if(!validate.scene("add").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		try {
			Db.begin();
			
			Map<String, Object> data1 = new HashMap<String, Object>();
			data1.put("title", postMap.get("title"));
			data1.put("type_id", postMap.get("type_id"));
			data1.put("key_words", postMap.get("key_words"));
			data1.put("description", postMap.get("description"));
			data1.put("is_recommend", postMap.get("is_recommend"));
			data1.put("thumb_path", postMap.get("thumb_path"));
			data1.put("add_time", Tools.time());
			int article_id = Db.table("article").insert(data1);
			
			Map<String, Object> data2 = new HashMap<String, Object>();
			data2.put("article_id", article_id);
			data2.put("content", postMap.get("content"));
			Db.table("article_content").insert(data2);
			
			Db.commit();
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
	@RequestMapping("/update")
	public Map<String, Object> update(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleValidate();
		if(!validate.scene("update").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		try {
			Db.begin();
			
			Map<String, Object> data1 = new HashMap<String, Object>();
			data1.put("title", postMap.get("title"));
			data1.put("type_id", postMap.get("type_id"));
			data1.put("key_words", postMap.get("key_words"));
			data1.put("description", postMap.get("description"));
			data1.put("is_recommend", postMap.get("is_recommend"));
			data1.put("thumb_path", postMap.get("thumb_path"));
			Db.table("article")
				.where("article_id", postMap.get("article_id"))
				.limit(1)
				.update(data1);
			
			Map<String, Object> data2 = new HashMap<String, Object>();
			data2.put("content", postMap.get("content"));
			Db.table("article_content")
				.where("article_id", postMap.get("article_id"))
				.limit(1)
				.update(data2);
			
			Db.commit();
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
	@RequestMapping("/delete")
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		try {
			Db.begin();
			Db.table("article_content")
				.where("article_id", postMap.get("article_id"))
				.limit(1)
				.delete();
			Db.table("article")
				.where("article_id", postMap.get("article_id"))
				.limit(1)
				.delete();
			Db.commit();
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
}
