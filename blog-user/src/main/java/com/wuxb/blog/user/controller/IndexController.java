package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.util.Config;

@RestController
@RequestMapping("/index")
public class IndexController {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/index")
	public Map<String, Object> index() throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> userInfo = Db.table("website")
			.field("website_name,description,head_img_path,share_code_img_path")
			.cache(10)
			.find();
		
		String head_img_path = (String) userInfo.get("head_img_path");
		userInfo.put("head_img_url", FILE_SERVER_DOMAIN + head_img_path);
		String share_code_img_path = (String) userInfo.get("share_code_img_path");
		userInfo.put("share_code_img_url", FILE_SERVER_DOMAIN + share_code_img_path);
		
		res.put("userInfo", userInfo);
		return res;
	}
	
}
