package com.wuxb.blog.admin.component;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.wuxb.httpServer.Redis;

public class Publisher {
	
	private static final String REDIS_KEY_NAME = "publisher";
	private String templateName;
	private JSONArray inputData = new JSONArray();
	private JSONArray deleteData = new JSONArray();
	
	public Publisher() {
		
	}
	
	public Publisher(String templateName) {
		setTemplateName(templateName);
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	
	public void addInputData(Object[] array) {
		for(Object object : array) {
			inputData.add(object);
		}
	}
	public void addInputData(List<Object> list) {
		addInputData(list.toArray());
	}
	public void addInputData(Object object) {
		inputData.add(object);
	}
	
	public void addDeleteData(Object[] array) {
		for(Object object : array) {
			deleteData.add(object);
		}
	}
	public void addDeleteData(List<Object> list) {
		addDeleteData(list.toArray());
	}
	public void addDeleteData(Object object) {
		deleteData.add(object);
	}
	
	public void send() {
		JSONArray data = new JSONArray();
		data.add(0, templateName);
		data.add(1, inputData);
		data.add(2, deleteData);
		Redis.getConn().lpush(REDIS_KEY_NAME, data.toString());
	}
	
}
