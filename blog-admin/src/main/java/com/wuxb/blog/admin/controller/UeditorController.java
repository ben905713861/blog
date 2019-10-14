package com.wuxb.blog.admin.controller;

import java.util.Map;

import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.ResponseHeader;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {

	@RequestMapping("/index")
	public void index(@GetParam Map<String, Object> map, HttpServletResponse httpServletResponse) {
		if(map.get("action").equals("config")) {
			config(httpServletResponse);
		}
		else if(map.get("action").equals("listfile")) {
//			listfile();
		}
		else {
//			uploadfile();
		}
	}
	
	private void config(HttpServletResponse httpServletResponse) {
		httpServletResponse.location("/static/js/plugins/ueditor/ueditorConfig.json");
	}
	
}
