package com.wuxb.blog.file.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Encrypt;

@RestController
public class IndexController {

	private static final String FILE_BASE_DIR = Config.get("http.staticSource.userUploadDir");
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public void index(@PostParam Map<String, Object> postMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
		List<FileInfo> fileList = httpServletRequest.getBody().getFileList();
		for(FileInfo fileInfo : fileList) {
			if(!Pattern.matches("^[\\w\\-]$", (String)postMap.get("type"))) {
				continue;
			}
			Date date = new Date();
			String newPath = FILE_BASE_DIR + "/" + postMap.get("type") + "/" + new SimpleDateFormat("yyyy/MMdd").format(date) + "/" + Encrypt.md5(date.getTime()+""+System.currentTimeMillis()) +"."+ fileInfo.extname;
			Files.move(Paths.get(fileInfo.path), Paths.get(newPath));
		}
	}
	
}
