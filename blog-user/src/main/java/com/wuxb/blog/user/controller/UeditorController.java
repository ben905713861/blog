package com.wuxb.blog.user.controller;

import java.util.Map;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {

	private HttpServletResponse httpServletResponse;
	
	@RequestMapping("/index")
	public void index(@GetParam Map<String, Object> getMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		this.httpServletResponse = httpServletResponse;
		if(getMap.get("action").equals("config")) {
			config();
		}
	}
	
	private void config() throws Exception {
		httpServletResponse.setResponse("{}");
	}
	
}
