package com.wuxb.blog.admin.validate;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginValidate extends Validate {

	protected static Map<String, Object> rules = new LinkedHashMap<String, Object>();
	
	static {
		rules.put("name", "require|min:2|max:20");
		rules.put("password", "require|min:6|max:20");
		rules.put("vrfCode", "require|length:4");
	}
	
	public LoginValidate() {
		super(rules);
	}
	
}
