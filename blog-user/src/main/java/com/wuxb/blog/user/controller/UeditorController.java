package com.wuxb.blog.user.controller;

import java.util.Map;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.util.Config;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {

	private static final String USER_DOMAIN = Config.get("USER_DOMAIN");
	private HttpServletResponse httpServletResponse;
	
	@RequestMapping("/index")
	public void index(@GetParam Map<String, Object> getMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		this.httpServletResponse = httpServletResponse;
		if(getMap.get("action").equals("config")) {
			config();
		}
	}
	
	private void config() {
		httpServletResponse.location(USER_DOMAIN +"/plugins/ueditor/ueditorConfig.json");
	}
	
	
}
