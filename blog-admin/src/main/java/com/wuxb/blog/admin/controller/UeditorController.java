package com.wuxb.blog.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.util.FormdataParamsEncode;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	@RequestMapping("/index")
	public void index(@GetParam Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		if(map.get("action").equals("config")) {
			config();
		}
		else if(map.get("action").equals("listfile")) {
//			listfile();
		}
		else {
			httpServletResponse.setResponse(uploadfile());
		}
	}
	
	private void config() {
		httpServletResponse.location("/static/js/plugins/ueditor/ueditorConfig.json");
	}
	
	private Map<String, Object> uploadfile() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		FormdataParamsEncode formdata = new FormdataParamsEncode();
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		formdata.add("article", fileList.get(0));
		
		UploadFile uploadFile = new UploadFile();
		if(!uploadFile.send(formdata)) {
			resMap.put("state", uploadFile.getErrorMessage());
			return resMap;
		}
		List<Map<String, Object>> fileResultList = uploadFile.getFileResultList();
		Map<String, Object> file = fileResultList.get(0);
		
		resMap.put("state", "SUCCESS");
		resMap.put("original", file.get("name"));
		resMap.put("size", file.get("size"));
		String url = (String) file.get("url");
		resMap.put("type", url.substring(url.lastIndexOf(".")+1));
		resMap.put("title", url.substring(url.lastIndexOf("/")+1));
		resMap.put("url", url);
		return resMap;
	}
	
}
