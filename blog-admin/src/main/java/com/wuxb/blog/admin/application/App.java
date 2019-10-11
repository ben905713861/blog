package com.wuxb.blog.admin.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.blog.admin.validate.LoginValidate;
import com.wuxb.httpServer.Run;

public class App {

//	public static void main(String[] args) {
//		Run.main(args);
//	}
	
	public static void main(String[] args) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("name", "ben");
		dataMap.put("password", "123456");
		dataMap.put("vrfCode", "dcv4");
		
		LoginValidate loginValidate = new LoginValidate();
		if(!loginValidate.check(dataMap)) {
			System.out.println(loginValidate.getErrMessage());
			return;
		}
		System.out.println("ok");
	}

}
