package com.wuxb.blog.file.application;

import com.wuxb.httpServer.HttpServletRequest;
import com.wuxb.httpServer.HttpServletResponse;
import com.wuxb.httpServer.Interceptor;
import com.wuxb.httpServer.UserStaticSource;

public class MyInterceptor implements Interceptor {

	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  throws Exception {
		//静态资源处理-用户上传的文件
		new UserStaticSource(httpServletRequest, httpServletResponse).action();
	}
	
}
