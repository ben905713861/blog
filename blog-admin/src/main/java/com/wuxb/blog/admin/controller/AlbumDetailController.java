package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.Publisher;
import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/albumDetail")
public class AlbumDetailController {
	
	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");

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
		for(Map<String, Object> map : rows) {
			String path = (String) map.get("path");
			if(path.substring(0, 1).equals("/")) {
				path = FILE_SERVER_DOMAIN + path;
			}
			map.put("url", path);
			map.remove("path");
			
			String thumb_path = (String) map.get("thumb_path");
			if(!thumb_path.isEmpty() && thumb_path.substring(0, 1).equals("/")) {
				thumb_path = FILE_SERVER_DOMAIN + thumb_path;
			}
			map.put("thumbUrl", thumb_path);
			map.remove("thumb_path");
		}
		res.put("rows", rows);
		
		int total = Db.table("album_img")
			.where(where)
			.count();
		res.put("total", total);
		
		Map<String, Object> albumInfo = Db.table("album")
			.field("title,description")
			.where("album_id", search.get("album_id"))
			.find();
		res.put("albumInfo", albumInfo);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@PostParam Map<String, Object> postMap) throws SQLException {
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) postMap.get("filePathList");
		for(Map<String, Object> map : list) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("album_id", postMap.get("album_id"));
			data.put("name", map.get("name"));
			data.put("path", map.get("path"));
			data.put("thumb_path", map.get("thumb_path"));
			dataList.add(data);
		}
		
		Db.table("album_img").insertAll(dataList);
		
		publishHtml(postMap.get("album_id"));
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Map<String, Object> delete(@PostParam Map<String, Object> postMap) throws SQLException {
		 Map<String, Object> imgInfo = Db.table("album_img")
			.field("album_id,thumb_path,path")	
			.where("img_id", postMap.get("img_id"))
			.find();
		
		Db.table("album_img")
			.where("img_id", postMap.get("img_id"))
			.limit(1)
			.delete();
		
		UploadFile.delete((String) imgInfo.get("thumb_path"));
		UploadFile.delete((String) imgInfo.get("path"));
		
		if(imgInfo.get("album_id") != null) {
			publishHtml(imgInfo.get("album_id"));
		}
		return Tools.returnSucc();
	}
	
	private static void publishHtml(Object album_id) {
		Publisher publisher = new Publisher("album");
		publisher.addInputData(album_id);
		publisher.send();
	}
	
}
