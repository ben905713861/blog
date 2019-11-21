package compoment;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuxb.httpServer.ClassReader;
import com.wuxb.httpServer.Redis;

import compoment.MyTemplate;
import redis.clients.jedis.Jedis;

public class Publisher {
	
	private static final String REDIS_KEY_NAME = "publisher";
	private static HashMap<String, Class<?>> templateName2class = new HashMap<String, Class<?>>();
	
	static {
		List<Class<?>> clazzs = ClassReader.getAllClass();
		for(Class<?> clazz : clazzs) {
			if(clazz == MyTemplate.class) {
				continue;
			}
			if(!MyTemplate.class.isAssignableFrom(clazz)) {
				continue;
			}
			try {
				Method method = clazz.getDeclaredMethod("setTemplate");
				method.setAccessible(true);
				String templateName = (String) method.invoke(clazz.getDeclaredConstructor().newInstance());
				templateName2class.put(templateName, clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Publisher is listening...");
		while(true) {
			Jedis jedis = Redis.getConn();
			List<String> messages = jedis.brpop(10, REDIS_KEY_NAME);
			jedis.close();
			if(messages.size() < 2) {
				continue;
			}
			if(!messages.get(0).equals(REDIS_KEY_NAME)) {
				continue;
			}
			//子线程进行渲染
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//解码
						JSONArray info = JSONObject.parseArray(messages.get(1));
						if(info.size() != 3) {
							throw new Exception("发布器队列数据格式错误");
						}
						String templateName = info.getString(0);
						JSONArray inputData = info.getJSONArray(1);
						JSONArray deleteData = info.getJSONArray(2);
						//执行对应模版渲染器
						if(!templateName2class.containsKey(templateName)) {
							throw new Exception("发布器不存在此模板的渲染类"+ templateName);
						}
						Class<?> clazz = templateName2class.get(templateName);
						Object newInstance = clazz.getDeclaredConstructor().newInstance();
						
						if(deleteData != null && deleteData.size() > 0) {
							clazz.getMethod("clear", JSONArray.class).invoke(newInstance, deleteData);
						}
						clazz.getMethod("display", JSONArray.class).invoke(newInstance, inputData);
						
						System.out.println(new Date() + " [" + templateName + "] display succ");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

}
