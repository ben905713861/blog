package com.wuxb.blog.admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.wuxb.blog.admin.validate.ManagerValidate;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.Session;
import com.wuxb.httpServer.Validate;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Encrypt;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/index")
	public String index() {
		
		return "index/index";
	}
	
	@RequestMapping("/getManagerInfo")
	public Map<String, Object> getManagerInfo(HttpServletRequest httpServletRequest) throws SQLException {
		Session session = httpServletRequest.getSession();
		Map<String, Object> managerInfo = Db.table("manager")
			.field("name,real_name,email,phone")
			.where("manager_id", session.get("manager_id"))
			.find();
		return managerInfo;
	}
	
	@RequestMapping(value="/updateManagerInfo", method=RequestMethod.POST)
	public Map<String, Object> updateManagerInfo(HttpServletRequest httpServletRequest, @PostParam Map<String, Object> postMap) throws SQLException {
		//检验
		Validate validate = new ManagerValidate();
		if(!validate.scene("selfUpdate").check(postMap)) {
			return Tools.returnErr(validate.getError());
		}
		//赋值
		Session session = httpServletRequest.getSession();
		Map<String, Object> data = new HashMap<String, Object>();
		String password = (String) postMap.get("password");
		if(!password.isEmpty()) {
			data.put("password", Encrypt.md5(password));
		}
		data.put("real_name", postMap.get("real_name"));
		data.put("phone", postMap.get("phone"));
		data.put("email", postMap.get("email"));
		Db.table("manager")
			.where("manager_id", session.get("manager_id"))
			.limit(1)
			.update(data);
		return Tools.returnSucc();
	}
	
}
