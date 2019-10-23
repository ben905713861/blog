package com.wuxb.blog.file.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Encrypt;
import com.wuxb.httpServer.util.Tools;

@RestController
public class IndexController {

	private static final String FILE_BASE_DIR = Config.get("http.staticSource.userUploadDir");
	
	private class ReturnFileInfo {
		String name;
		String path;
		long size;
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}
		@SuppressWarnings("unused")
		public String getPath() {
			return path;
		}
		@SuppressWarnings("unused")
		public long getSize() {
			return size;
		}
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Map<String, Object> index(@PostParam Map<String, Object> postMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		List<ReturnFileInfo> returnFileInfoList = new LinkedList<ReturnFileInfo>();
		List<String> errorList = new LinkedList<String>();
		
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		if(fileList == null) {
			return Tools.returnErr("文件列表不得为空");
		}
		for(FileInfo fileInfo : fileList) {
			Date date = new Date();
			//存放路径-相对路径
			String relativePath = "/" + fileInfo.key + "/" + new SimpleDateFormat("yyyy/MMdd").format(date) + "/" + Encrypt.md5(date.getTime()+""+System.currentTimeMillis()) +"."+ fileInfo.extname;
			//存放路径-绝对路径
			String newPath = FILE_BASE_DIR + relativePath;
			File newFile = new File(newPath);
			File newDir = newFile.getParentFile();
			if(!newDir.exists()) {
				if(!newDir.mkdirs()) {
					errorList.add("目标文件夹创建失败"+ newDir.getPath());
					continue;
				}
			}
			if(newFile.exists()) {
				errorList.add("文件已存在"+ newFile.getPath());
				continue;
			}
			File file = new File(fileInfo.path);
			//复制到新位置
			if(!file.renameTo(newFile)) {
				errorList.add("文件复制失败"+ newFile.getPath());
				continue;
			}
			ReturnFileInfo returnFileInfo = new ReturnFileInfo();
			returnFileInfo.name = fileInfo.filename;
			returnFileInfo.size = newFile.length();
			returnFileInfo.path = relativePath;
			returnFileInfoList.add(returnFileInfo);
		}
		Map<String, Object> resMap = Tools.returnSucc();
		resMap.put("fileInfoList", returnFileInfoList);
		resMap.put("errorList", errorList);
		return resMap;
	}
	
}
