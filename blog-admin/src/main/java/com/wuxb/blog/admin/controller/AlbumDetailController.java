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
import com.wuxb.httpServer.util.FormdataParamsEncode;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/albumDetail")
public class AlbumDetailController {

	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		HashMap<String, Object> where = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> search = (Map<String, Object>) getMap.get("search");
		where.put("album_id", search.get("album_id"));
		//标题
		String title = (String) search.get("name");
		if(title != null && !title.isEmpty()) {
			where.put("name", new Object[] {"LIKE", "%"+title+"%"});
		}
		
		List<Map<String, Object>> rows = Db.table("album_img")
			.where(where)
			.order("img_id", "DESC")
			.limit((long)getMap.get("offset"), (long)getMap.get("limit"))
			.select();
		res.put("rows", rows);
		int total = Db.table("album_img")
			.where(where)
			.count();
		res.put("total", total);
		return res;
	}
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("album")
			.where("album_id", getMap.get("album_id"))
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
		formdata.add("albumThumb", fileInfo);
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
	
	@RequestMapping("/update")
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
	
	@RequestMapping("/delete")
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
