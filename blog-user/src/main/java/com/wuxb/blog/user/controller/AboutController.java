package com.wuxb.blog.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.wuxb.blog.admin.component.UeditorFileDomainFilter;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;

@RestController
@RequestMapping("/about")
public class AboutController {

	@RequestMapping("/index")
	public Map<String, Object> index() throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> userInfo = Db.table("website")
			.field("aboutme")
			.find();
		String aboutme = (String) userInfo.get("aboutme");
		
		res.put("aboutme", UeditorFileDomainFilter.replay(aboutme));
		return res;
	}
	
}
