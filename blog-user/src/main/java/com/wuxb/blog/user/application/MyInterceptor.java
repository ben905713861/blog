package com.wuxb.blog.user.application;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.util.Config;

public class MyInterceptor implements Interceptor {

	private static final String USER_DOMAIN = Config.get("USER_DOMAIN");
	
	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		httpServletResponse.getHeader().set("Access-Control-Allow-Origin", USER_DOMAIN);
	}

}
