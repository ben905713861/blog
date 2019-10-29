package com.wuxb.blog.admin.component;

import com.wuxb.httpServer.util.Config;

public class UeditorFileDomainFilter {

	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	private static final String BOUNDARY = "%%%DOMAIN%%%";
	
	public static String filter(String content) {
		return content.replaceAll("(src|href)=\\\""+ FILE_SERVER_DOMAIN, "$1=\""+ BOUNDARY)
			.replaceAll("(src|href)=\\\"(http|https)://([\\w\\-\\.]+?)/static", "$1=\"/static");
	}
	
	public static String replay(String content) {
		return content.replaceAll("(src|href)=\\\""+ BOUNDARY, "$1=\""+ FILE_SERVER_DOMAIN);
	}
	
}
