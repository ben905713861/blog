package com.wuxb.blog.publisher.logic;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wuxb.httpServer.util.Curl;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class IndexTemplate extends MyTemplate {

	public static void main(String[] args) throws IOException, TemplateException {
		System.out.println("start");
		Configuration configuration=new Configuration(Configuration.getVersion());
		
		TemplateLoader dd;
		configuration.setClassForTemplateLoading(MyTemplate.class, "/templates");
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("index.html");
		
		Map map = new HashMap();
		map.put("name", "张三 ");
		
		//6.创建Writer对象
//		Writer writer =new FileWriter(new File("C:/Users/Administrator/Desktop/result.html"));
		Writer writer = new StringWriter();
		//7.输出
		template.process(map, writer);
		System.out.println(writer.toString());
		//8.关闭Writer对象
		writer.close();
	}
	
	private static JSONObject curl(String path) {
		String json = Curl.simpleGet("http://127.0.0.1:8081"+ path);
		return JSONObject.parseObject(json);
	}
	
}
