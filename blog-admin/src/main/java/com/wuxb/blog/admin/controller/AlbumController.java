package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.blog.admin.validate.AlbumValidate;
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
import com.wuxb.httpServer.util.FormdataParamsEncode;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/album")
public class AlbumController {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		HashMap<String, Object> where = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> search = (Map<String, Object>) getMap.get("search");
		//标题
		String title = (String) search.get("title");
		if(title != null && !title.isEmpty()) {
			where.put("title", new Object[] {"LIKE", "%"+title+"%"});
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
		
		List<Map<String, Object>> rows = Db.table("album")
			.where(where)
			.order("add_time", "DESC")
			.limit((long)getMap.get("offset"), (long)getMap.get("limit"))
			.select();
		for(Map<String, Object> row : rows) {
			String thumb_path = (String) row.get("thumb_path");
			row.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
		}
		res.put("rows", rows);
		
		int total = Db.table("album a")
			.where(where)
			.count();
		res.put("total", total);
		
		return res;
	}
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> info = Db.table("album")
			.where("album_id", getMap.get("album_id"))
			.find();
		String thumb_path = (String) info.get("thumb_path");
		info.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
		return info;
	}
	
	@RequestMapping(value="/uploadThumb", method=RequestMethod.POST)
	public Map<String, Object> uploadThumb(HttpServletRequest httpServletRequest) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		if(fileList.size() == 0) {
			return Tools.returnErr("上传文件不得为空");
		}
		FileInfo fileInfo = fileList.get(0);
		
		UploadFile uploadFile = new UploadFile();
		FormdataParamsEncode formdata = uploadFile.getFormdataObject();
		formdata.add("albumThumb", fileInfo);
		if(!uploadFile.send(formdata)) {
			return Tools.returnErr(uploadFile.getErrorMessage());
		}
		List<Map<String, Object>> fileResultList = uploadFile.getFileResultList();
		Map<String, Object> fileResult = fileResultList.get(0);
		
		resMap.put("status", true);
		resMap.put("thumb_path", fileResult.get("path"));
		resMap.put("thumb_url", fileResult.get("url"));
		return resMap;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new AlbumValidate();
		if(!validate.scene("add").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", postMap.get("title"));
		data.put("is_recommend", postMap.get("is_recommend"));
		data.put("description", postMap.get("description"));
		data.put("thumb_path", postMap.get("thumb_path"));
		data.put("add_time", Tools.time());
		Db.table("album").insert(data);
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(@PostParam Map<String, Object> postMap) throws SQLException {
		Validate validate = new AlbumValidate();
		if(!validate.scene("update").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", postMap.get("title"));
		data.put("is_recommend", postMap.get("is_recommend"));
		data.put("description", postMap.get("description"));
		data.put("thumb_path", postMap.get("thumb_path"));
		Db.table("album")
			.where("album_id", postMap.get("album_id"))
			.limit(1)
			.update(data);
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		try {
			Db.begin();
			Db.table("album_img")
				.where("album_id", postMap.get("album_id"))
				.delete();
			Db.table("album")
				.where("album_id", postMap.get("album_id"))
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