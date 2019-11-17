package com.wuxb.blog.user.application;

import java.util.Arrays;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.ResponseHeader;
import com.wuxb.httpServer.util.Config;

public class MyInterceptor implements Interceptor {

	private static final String[] USER_DOMAINS = Config.get("USER_DOMAINS").split(",");
	
	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		String origin = (String) httpServletRequest.getHeader().get("origin");
		
		ResponseHeader header = httpServletResponse.getHeader();
		if(!Arrays.asList(USER_DOMAINS).contains(origin)) {
			return;
		}
		header.set("Access-Control-Allow-Origin", origin);
		header.set("Access-Control-Allow-Methods", "POST,GET");
		header.set("Access-Control-Allow-Headers", "Content-Type,random,time");
		header.set("Access-Control-Max-Age", 3600);
	}

}
