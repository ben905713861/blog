package com.wuxb.blog.admin.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.ImageCutter;
import com.wuxb.blog.admin.component.Publisher;
import com.wuxb.blog.admin.component.UeditorFileDomainFilter;
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
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.HtmlFilter;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/init")
	public List<Map<String, Object>> init() throws SQLException {
		return Db.table("article_type")
			.order("sort", "ASC")
			.select();
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
			where.put("a.add_time", new Object[] {">=", Tools.dateStr2time(start_date)});
		} else if(start_date == null && end_date != null) {
			where.put("a.add_time", new Object[] {"<=", Tools.dateStr2time(end_date)+24*3600-1});
		} else if(start_date != null && end_date != null) {
			where.put("a.add_time", new Object[] {"BETWEEN", new Object[]{Tools.dateStr2time(start_date), Tools.dateStr2time(end_date)+24*3600-1}});
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
		Map<String, Object> info = Db.table("article a")
			.join("article_content c", "c.article_id=a.article_id", "LEFT")
			.field("a.*,c.content")
			.where("a.article_id", getMap.get("article_id"))
			.find();
		String thumb_path = (String) info.get("thumb_path");
		info.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
		//还原文件网址前缀
		String content = (String) info.get("content");
		info.replace("content", UeditorFileDomainFilter.replay(content));
		return info;
	}
	
	@RequestMapping(value="/uploadThumb", method=RequestMethod.POST)
	public Map<String, Object> uploadThumb(HttpServletRequest httpServletRequest) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		if(fileList.size() == 0) {
			return Tools.returnErr("上传文件不得为空");
		}
		FileInfo fileInfo = fileList.get(0);
		
		//压缩缩略图
		try {
			ImageCutter imageCutter = new ImageCutter(fileInfo.path, fileInfo.extname);
			imageCutter.zoomImageByWidth(450);
			imageCutter.cutCenterImage(450, 300);
			//覆盖源文件
			imageCutter.writeFile(fileInfo.path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("缩略图压缩失败");
		}
		
		UploadFile uploadFile = new UploadFile();
		uploadFile.send("articleThumb", fileInfo);
		
		resMap.put("status", true);
		resMap.put("thumb_path", uploadFile.getPath());
		resMap.put("thumb_url", uploadFile.getUrl());
		return resMap;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleValidate();
		if(!validate.scene("add").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		try {
			Db.begin();
			
			Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", postMap.get("title"));
				data.put("type_id", postMap.get("type_id"));
				data.put("key_words", postMap.get("key_words"));
				data.put("is_recommend", postMap.get("is_recommend"));
				data.put("thumb_path", postMap.get("thumb_path"));
				data.put("add_time", Tools.time());
			
			String content = (String) postMap.get("content");
			if(((String) postMap.get("description")).isEmpty()) {
				//过滤html标签
				String description = HtmlFilter.stripTags(content);
				description = HtmlFilter.htmlspecialcharsDecode(description);
				int contentLen = description.length();
				description = description.substring(0, contentLen>255 ? 255 : contentLen);
				data.put("description", description);
			} else {
				String description = (String) postMap.get("description");
				description = HtmlFilter.stripTags(description);
				data.put("description", description);
			}
			int article_id = Db.table("article").insert(data);
			
			Map<String, Object> data2 = new HashMap<String, Object>();
			data2.put("article_id", article_id);
			//将正文中的上传文件url的网址前缀去掉
			data2.put("content", UeditorFileDomainFilter.filter(content));
			
			Db.table("article_content").insert(data2);
			Db.commit();
			
			publishHtml(article_id, postMap.get("type_id"));
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new ArticleValidate();
		if(!validate.scene("update").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		try {
			Db.begin();
			
			Object article_id = postMap.get("article_id");
			
			Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", postMap.get("title"));
				data.put("type_id", postMap.get("type_id"));
				data.put("key_words", postMap.get("key_words"));
				data.put("is_recommend", postMap.get("is_recommend"));
				data.put("thumb_path", postMap.get("thumb_path"));
			
			String content = (String) postMap.get("content");
			if(((String) postMap.get("description")).isEmpty()) {
				//过滤html标签
				String description = HtmlFilter.stripTags(content);
				description = HtmlFilter.htmlspecialcharsDecode(description);
				int contentLen = description.length();
				description = description.substring(0, contentLen>255 ? 255 : contentLen);
				data.put("description", description);
			} else {
				String description = (String) postMap.get("description");
				description = HtmlFilter.stripTags(description);
				data.put("description", description);
			}
			
			Db.table("article")
				.where("article_id", article_id)
				.limit(1)
				.update(data);
			
			Map<String, Object> data2 = new HashMap<String, Object>();
			//将正文中的上传文件url的网址前缀去掉
			data2.put("content", UeditorFileDomainFilter.filter(content));
			
			Db.table("article_content")
				.where("article_id", article_id)
				.limit(1)
				.update(data2);
			
			Db.commit();
			
			publishHtml(article_id, postMap.get("type_id"));
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		Object article_id = postMap.get("article_id");
		try {
			Db.begin();
			Object type_id = Db.table("article")
				.where("article_id", article_id)
				.value("type_id");
			Db.table("article_content")
				.where("article_id", article_id)
				.limit(1)
				.delete();
			Db.table("article")
				.where("article_id", article_id)
				.limit(1)
				.delete();
			Db.commit();
			
			publishHtml(article_id, type_id);
		} catch (SQLException e) {
			Db.rollback();
			throw new SQLException(e.getMessage());
		}
		return Tools.returnSucc();
	}
	
	private static void publishHtml(Object article_id, Object type_id) {
		{
			Publisher publisher = new Publisher("article");
			publisher.addInputData(article_id);
			publisher.send();
		}
		{
			Publisher publisher = new Publisher("articleList");
			publisher.addInputData(new Object[] {0, type_id});
			publisher.send();
		}
		new Publisher("common/fenlei").send();
		new Publisher("common/tuijian").send();
	}
	
}
