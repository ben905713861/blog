package com.wuxb.blog.admin.util;

import java.util.HashMap;
import java.util.Map;

public class Tools {

	public static Map<String, Object> returnErr(String msg) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", false);
		res.put("msg", msg);
		return res;
	}
	
	public static Map<String, Object> returnSucc() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", true);
		return res;
	}
	
}
