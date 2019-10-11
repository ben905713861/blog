package com.wuxb.blog.admin.validate;

import java.util.Map;
import java.util.Map.Entry;

public abstract class Validate {

	private Map<String, Object> rulesList;
	
	public Validate(Map<String, Object> rulesList) {
		this.rulesList = rulesList;
	}
	
	protected boolean check(Map<String, Object> dataMap) {
		for(Entry<String, Object> row : rulesList.entrySet()) {
			String field = row.getKey();
			Object value = row.getValue();
			if(value instanceof String) {
				String rule ;
			}
		}
		return false;
	}
	
}
