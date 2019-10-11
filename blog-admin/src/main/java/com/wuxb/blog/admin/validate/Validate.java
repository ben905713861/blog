package com.wuxb.blog.admin.validate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;


public abstract class Validate {

	private Map<String, String[]> rulesList = new LinkedHashMap<String, String[]>();
	private String errMessage;
	private static Map<String, String> msg = new HashMap<String, String>();
	
	static {
		msg.put("require", "不的为空");
		msg.put("min", "长度过小");
		msg.put("max", "长度过大");
		msg.put("between", "超出范围");
		msg.put("regex", "规则错误");
		msg.put("email", "不是邮箱");
		msg.put("mobile", "不是手机号");
	}
	
	public Validate(Map<String, Object> rulesList) {
		for(Entry<String, Object> row : rulesList.entrySet()) {
			String fieldName = row.getKey();
			Object valueObject = row.getValue();
			if(valueObject instanceof String) {
				String[] rules = ((String) valueObject).split("\\|");
				this.rulesList.put(fieldName, rules);
			} else if(valueObject instanceof String[]) {
				this.rulesList.put(fieldName, (String[]) valueObject);
			} else {
				System.err.println(fieldName +"的验证规则不合法");
			}
		}
	}
	
	public boolean check(Map<String, Object> dataMap) {
		for(Entry<String, String[]> row : rulesList.entrySet()) {
			String fieldName = row.getKey();
			Object data = dataMap.get(fieldName);
			for(String rule : row.getValue()) {
				int tempIndex = rule.indexOf(":");
				String methodName;
				String methodParam = null;
				if(tempIndex == -1) {
					methodName = rule;
				} else {
					methodName = rule.substring(0, tempIndex);
					methodParam = rule.substring(tempIndex + 1);
				}
				try {
					Method method = Validate.class.getDeclaredMethod(methodName);
					System.out.println(method.getParameterCount());
					Object checkRes;
					if(methodParam == null) {
						checkRes = method.invoke(null, data);
					} else {
						checkRes = method.invoke(null, data, methodParam);
					}
					if(!(boolean) checkRes) {
						errMessage = fieldName + msg.get(methodName);
						return false;
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	public String getErrMessage() {
		return errMessage;
	}
	
	private static void require() {
		System.err.println("rrr");
	}
	
	protected static boolean require(Object data) {
		System.err.println("ddd");
		if(data == null) {
			return false;
		}
		if(data instanceof String) {
			if(((String) data).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	protected static boolean min(String data, String _min) {
		int min = Integer.parseInt(_min);
		if(data.length() < min) {
			return false;
		}
		return true;
	}
	
	protected static boolean max(String data, String _max) {
		int max = Integer.parseInt(_max);
		if(data.length() > max) {
			return false;
		}
		return true;
	}
	
	protected static boolean between(Integer data, String length) {
		String[] temp = length.split(",");
		int min = Integer.parseInt(temp[0]);
		int max = Integer.parseInt(temp[1]);
		if(data < min) {
			return false;
		}
		if(data > max) {
			return false;
		}
		return true;
	}
	
	protected static boolean regex(String data, String regex) {
		return Pattern.matches(regex, data);
	}
	
	protected static boolean email(String email) {
		return Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$", email);
	}
	
	protected static boolean mobile(String mobile) {
		return Pattern.matches("^1[3-9]{1}{0-9}{9}$", mobile);
	}
	
	
	
}
