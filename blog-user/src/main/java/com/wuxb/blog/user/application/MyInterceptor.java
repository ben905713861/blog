package com.wuxb.blog.user.application;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.ResponseHeader;
import com.wuxb.httpServer.util.Config;

public class MyInterceptor implements Interceptor {

	private static final String USER_DOMAIN = Config.get("USER_DOMAIN");
	
	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		ResponseHeader header = httpServletResponse.getHeader();
		header.set("Access-Control-Allow-Origin", USER_DOMAIN);
		header.set("Access-Control-Allow-Methods", "POST,GET");
		header.set("Access-Control-Allow-Headers", "Content-Type,random,time");
		header.set("Access-Control-Max-Age", 3600);
	}

}
