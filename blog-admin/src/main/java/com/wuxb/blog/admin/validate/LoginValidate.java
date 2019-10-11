package com.wuxb.blog.admin.validate;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginValidate extends Validate {

	protected static Map<String, Object> rulesList = new LinkedHashMap<String, Object>();
	
	static {
		rulesList.put("name", "require|min:2|max:20");
		rulesList.put("password", "require|min:6|max:20");
		rulesList.put("vrfCode", "require|length:4");
	}
	
	public LoginValidate() {
		super(rulesList);
	}
	
//	public void ss() {
//		min("", "55");
//	}
	
}
