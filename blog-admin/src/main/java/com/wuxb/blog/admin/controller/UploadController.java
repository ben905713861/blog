package com.wuxb.blog.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxb.blog.admin.component.UploadFile;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/upload")
public class UploadController {

	private static final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;
	private static final String[] ALLOW_TYPE = new String[] {
		"article", "album", "albumThumb", "website"
	};
	private static final String[] ALLOW_EXTNAMES = new String[] {
		"jpg", "jpeg", "png", "gif", "xls", "xlsx", "doc", "docx"
	};
	
	@RequestMapping(value="/uploadOne", method=RequestMethod.POST)
	public Map<String, Object> uploadOne(@GetParam Map<String, Object> getMap, HttpServletRequest httpServletRequest) {
		String type = (String) getMap.get("type");
		//上传目录判断
		if(type == null || !Arrays.asList(ALLOW_TYPE).contains(type)) {
			return Tools.returnErr("type类型不合法");
		}
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		FileInfo fileInfo = fileList.get(0);
		
		//过滤
		if(new File(fileInfo.path).length() > MAX_UPLOAD_SIZE) {
			return Tools.returnErr("上传文件过大");
		}
		if(!Arrays.asList(ALLOW_EXTNAMES).contains(fileInfo.extname)) {
			return Tools.returnErr("上传文件格式不被允许");
		}
		
		//发送到文件服务器
		UploadFile uploadFile = new UploadFile();
		uploadFile.send(type, fileInfo);
		
		Map<String, Object> resMap = Tools.returnSucc();
		resMap.put("name", fileInfo.filename);
		resMap.put("url", uploadFile.getUrl());
		resMap.put("path", uploadFile.getPath());
		return resMap;
	}
	
	@RequestMapping(value="/uploadMore", method=RequestMethod.POST)
	public Map<String, Object> uploadMore(@GetParam Map<String, Object> getMap, HttpServletRequest httpServletRequest) {
		String type = (String) getMap.get("type");
		//上传目录判断
		if(type == null || !Arrays.asList(ALLOW_TYPE).contains(type)) {
			return Tools.returnErr("type类型不合法");
		}
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		ArrayList<Map<String, String>> fileResList = new ArrayList<Map<String, String>>();
		for(FileInfo fileInfo : fileList) {
			//过滤
			if(new File(fileInfo.path).length() > MAX_UPLOAD_SIZE) {
				return Tools.returnErr("上传文件过大");
			}
			if(!Arrays.asList(ALLOW_EXTNAMES).contains(fileInfo.extname)) {
				return Tools.returnErr("上传文件格式不被允许");
			}
			//发送到文件服务器
			UploadFile uploadFile = new UploadFile();
			uploadFile.send(type, fileInfo);
			
			HashMap<String, String> file = new HashMap<String, String>();
			file.put("name", fileInfo.filename);
			file.put("url", uploadFile.getUrl());
			file.put("path", uploadFile.getPath());
			fileResList.add(file);
		}
		
		Map<String, Object> resMap = Tools.returnSucc();
		resMap.put("fileResList", fileResList);
		return resMap;
	}
	
}
