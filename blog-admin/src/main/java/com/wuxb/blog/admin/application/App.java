package com.wuxb.blog.admin.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.wuxb.blog.admin.validate.LoginValidate;
import com.wuxb.httpServer.Run;
import com.wuxb.httpServer.Validate;

public class App {

	public static void main(String[] args) {
		Run.main(args);
	}
	
//	public static void main(String[] args) {
//		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
//		dataMap.put("name", "dh");
//		dataMap.put("password", "123456");
//		dataMap.put("vrf_code", "dcv4");
//		
//		Validate loginValidate = new LoginValidate();
//		if(!loginValidate.check(dataMap)) {
//			System.out.println(loginValidate.getError());
//			return;
//		}
//		System.out.println("ok");
//	}

}
