package com.wuxb.blog.admin.controller;

import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/index")
	public String index() {
		
		return "index/index";
	}
	
}
