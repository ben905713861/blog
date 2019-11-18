package compoment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuxb.httpServer.util.Curl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class MyTemplate {
	
	private static final String TEMPLATE_DIR = "E:\\java\\blog\\frontend\\templates";
	private static final String HTML_DIR = "E:\\java\\blog\\frontend\\html";
	private static final String STATIC_DOMAIN = "http://static.blog.dev";
	private static final String USER_DOMAIN = "http://127.0.0.1:8081";
	private String templateName;
	private Template template;
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	public MyTemplate() {
		templateName = setTemplate();
		data.put("STATIC_DOMAIN", STATIC_DOMAIN);
		//模板引擎
		Configuration configuration = new Configuration(Configuration.getVersion());
		try {
			configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
			configuration.setDefaultEncoding("utf-8");
			template = configuration.getTemplate(templateName +".html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract String setTemplate();
	public abstract void display();
	
	protected void play() {
		play("");
	}
	
	protected void play(String suffix) {
		Writer writer = null;
		try {
			File file = new File(HTML_DIR + "/" + templateName + suffix +".html");
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			writer = new FileWriter(file);
			template.process(data, writer);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static JSONArray curlGetList(String path) {
		String json = curl(path);
		if(json == null || json.isEmpty()) {
			return null;
		}
		return JSONObject.parseArray(json);
	}
	
	protected static JSONObject curlGetMap(String path) {
		String json = curl(path);
		if(json == null || json.isEmpty()) {
			return null;
		}
		return JSONObject.parseObject(json);
	}
	
	private static String curl(String path) {
		return Curl.simpleGet(USER_DOMAIN + path);
	}
	
}
