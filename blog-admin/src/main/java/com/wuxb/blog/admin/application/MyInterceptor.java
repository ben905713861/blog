package com.wuxb.blog.admin.application;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.StaticSource;
import com.wuxb.httpServer.exception.HttpInterceptInterrupt;

public class MyInterceptor implements Interceptor {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		//静态资源处理
		new StaticSource(httpServletRequest, httpServletResponse).action();
		if(httpServletRequest.getPath().equals("/")) {
			httpServletResponse.location("/static/index.html");
			throw new HttpInterceptInterrupt();
		}
		//是否登录
		checkLogin();
		//是否有权限
//		checkPremission();
	}
	
	private void checkLogin() throws HttpInterceptInterrupt {
		String path = httpServletRequest.getPath();
		if(path.length() >= 6 && path.substring(0, 6).equals("/login")) {
			return;
		}
		Integer manager_id = (Integer) httpServletRequest.getSession().get("manager_id");
		if(manager_id == null) {
			httpServletResponse.setResponseCode(401);
			throw new HttpInterceptInterrupt("尚未登录");
		}
	}
	
//	private void checkPremission() throws HttpInterceptInterrupt {
//		httpServletResponse.setResponseCode(403);
//		throw new HttpInterceptInterrupt("没有权限访问");
//	}
	
}
