package com.wuxb.blog.admin.component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.wuxb.httpServer.params.FileInfo;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Encrypt;


public class UploadFile {

	private static final String FILE_BASE_DIR = Config.get("FILE_BASE_DIR");
	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	private String relativePath;
	
	public void send(String type, FileInfo fileInfo) {
		Date date = new Date();
		relativePath = "/" + type + "/" + new SimpleDateFormat("yyyy/MMdd").format(date) + "/" + Encrypt.md5(date.getTime()+ "" +System.currentTimeMillis()) +"."+ fileInfo.extname;
		File newFile = new File(FILE_BASE_DIR + relativePath);
		System.out.println(FILE_BASE_DIR + relativePath);
		File newFilePath = newFile.getParentFile();
		if(!newFilePath.exists()) {
			newFilePath.mkdirs();
		}
		File originFile = new File(fileInfo.path);
		originFile.renameTo(newFile);
	}
	
	public String getPath() {
		return relativePath;
	}
	
	public String getUrl() {
		return FILE_SERVER_DOMAIN + relativePath;
	}
	
}
