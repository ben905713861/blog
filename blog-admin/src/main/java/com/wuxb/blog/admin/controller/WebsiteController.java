package com.wuxb.blog.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.Publisher;
import com.wuxb.blog.admin.component.UeditorFileDomainFilter;
import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/website")
public class WebsiteController {
	
	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");

	@RequestMapping("/getDetail")
	public Map<String, Object> getDetail() throws Exception {
		Map<String, Object> info = Db.table("website").find();
		
		String head_img_path = (String) info.get("head_img_path");
		info.put("head_img_url", FILE_SERVER_DOMAIN + head_img_path);
		
		String share_code_img_path = (String) info.get("share_code_img_path");
		info.put("share_code_img_url", FILE_SERVER_DOMAIN + share_code_img_path);
		
		String aboutme = (String) info.get("aboutme");
		info.replace("aboutme", UeditorFileDomainFilter.replay(aboutme));
		
		return info;
	}
	
	@RequestMapping("/upload_headImg")
	public Map<String, Object> upload_headImg(@PostParam Map<String, Object> postMap, HttpServletRequest httpServletRequest) throws Exception {
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		
		UploadFile uploadFile = new UploadFile();
		uploadFile.send("headImg", fileList.get(0));
		
		Map<String, Object> resMap = Tools.returnSucc();
		resMap.put("head_img_url", uploadFile.getUrl());
		resMap.put("head_img_path", uploadFile.getPath());
		return resMap;
	}
	
	@RequestMapping("/upload_shareCodeImg")
	public Map<String, Object> upload_shareCodeImg(@PostParam Map<String, Object> postMap, HttpServletRequest httpServletRequest) throws Exception {
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		
		UploadFile uploadFile = new UploadFile();
		uploadFile.send("shareCodeImg", fileList.get(0));
		
		Map<String, Object> resMap = Tools.returnSucc();
		resMap.put("share_code_img_url", uploadFile.getUrl());
		resMap.put("share_code_img_path", uploadFile.getPath());
		return resMap;
	}
	
	@RequestMapping("/update")
	public Map<String, Object> update(@PostParam Map<String, Object> postMap) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("website_name", postMap.get("website_name"));
		data.put("description", postMap.get("description"));
		data.put("head_img_path", postMap.get("head_img_path"));
		data.put("share_code_img_path", postMap.get("share_code_img_path"));
		//html富文本替换文件baseurl
		String aboutme = (String) postMap.get("aboutme");
		data.put("aboutme", UeditorFileDomainFilter.filter(aboutme));
		
		Db.table("website")
			.where("id", 1)
			.limit(1)
			.update(data);
		
		//静态html渲染
		new Publisher("about").send();
		new Publisher("common/aboutme").send();
		new Publisher("common/guanzhu").send();
		
		return Tools.returnSucc();
	}
	
}
