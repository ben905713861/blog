package com.wuxb.blog.file.application;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.UserStaticSource;
import com.wuxb.httpServer.exception.HttpInterceptInterrupt;

public class MyInterceptor implements Interceptor {

	private static final String ALLOW_IP = "127.0.0.1";
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		checkPremission();
		//静态资源处理-用户上传的文件
		new UserStaticSource(httpServletRequest, httpServletResponse).action();
	}
	
	private void checkPremission() throws HttpInterceptInterrupt {
		if(httpServletRequest.getIp().equals(ALLOW_IP)) {
			return;
		}
		httpServletResponse.setResponseCode(403);
		throw new HttpInterceptInterrupt("没有权限访问");
	}
	
}
