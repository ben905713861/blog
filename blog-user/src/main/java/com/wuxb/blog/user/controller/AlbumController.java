package com.wuxb.blog.user.controller;

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
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/album")
public class AlbumController {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/getRecommend")
	public List<Map<String, Object>> getRecommend(@GetParam Map<String, Object> getMap) throws SQLException {
		List<Map<String, Object>> list = Db.table("album")
			.field("album_id,title,thumb_path")
			.where("is_recommend", 1)
			.order("add_time", "DESC")
			.limit(6)
			.cache(10)
			.select();
		for(Map<String, Object> map : list) {
			String thumb_path = (String) map.get("thumb_path");
			if(thumb_path == null || thumb_path.isEmpty()) {
//				map.put("thumb_url", "images/default_thumb_264x176.jpg");
			} else {
				map.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
			}
		}
		return list;
	}
	
	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = Db.table("album")
			.order("album_id", "DESC")
			.page((long)getMap.get("page"), (long)getMap.get("limit"))
			.cache(10)
			.select();
		for(Map<String, Object> map : list) {
			String thumb_path = (String) map.get("thumb_path");
			if(thumb_path == null || thumb_path.isEmpty()) {
//				map.put("thumb_url", "images/default_thumb_264x176.jpg");
			} else {
				map.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
			}
		}
		res.put("list", list);
		
		int count = Db.table("album")
			.cache(10)
			.count();
		res.put("count", count);
		return res;
	}
	
	@RequestMapping("/getDetail")
	public Map<String, Object> getDetail(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> albumInfo = Db.table("album")
			.where("album_id", getMap.get("album_id"))
			.find();
		res.put("albumInfo", albumInfo);
		
		List<Map<String, Object>> list = Db.table("album_img")
			.where("album_id", getMap.get("album_id"))
			.select();
		for(Map<String, Object> map : list) {
			String path = (String) map.get("path");
			map.put("url", FILE_SERVER_DOMAIN + path);
			map.remove("path");
			
			String thumbPath = (String) map.get("thumbPath");
			if(!thumbPath.isEmpty() && thumbPath.substring(0, 1).equals("/")) {
				thumbPath = FILE_SERVER_DOMAIN + thumbPath;
			}
			map.put("thumbUrl", thumbPath);
			map.remove("thumbPath");
		}
		res.put("imgList", list);
		
		return res;
	}
	
	@RequestMapping("/getLikeNum")
	public Map<String, Object> getLikeNum(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("album")
			.field("like_num")
			.where("album_id", getMap.get("album_id"))
			.find();
	}
	
	@RequestMapping(value="/like", method=RequestMethod.POST)
	public Map<String, Object> like(@PostParam Map<String, Object> postMap) throws SQLException {
		Db.table("album")
			.where("album_id", postMap.get("album_id"))
			.limit(1)
			.setInc("like_num", 1);
		return Tools.returnSucc();
	}
	
}
