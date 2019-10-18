package com.wuxb.blog.admin.controller;

import java.util.List;
import java.util.Map;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.params.FileInfo;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	@RequestMapping("/index")
	public void index(@GetParam Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		if(map.get("action").equals("config")) {
			config();
		}
		else if(map.get("action").equals("listfile")) {
//			listfile();
		}
		else {
			uploadfile();
		}
	}
	
	private void config() {
		httpServletResponse.location("/static/js/plugins/ueditor/ueditorConfig.json");
	}
	
	private void uploadfile() {
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
//		if(fileList.size()) {
//			
//		}
		System.out.println(fileList);
	}
	
}
