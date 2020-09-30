package com.wuxb.blog.admin.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.wuxb.httpServer.util.Config;

public class ElasticSearchConnection {

	public static final String INDEX = Config.get("elasticsearch.index");
	private static final String[] hosts = Config.get("elasticsearch.hosts").split(",");
	private static final RestHighLevelClient client;
	
	static {
		client = initClient();
	}
	
	private static RestHighLevelClient initClient() {
		List<HttpHost> httpHosts = new ArrayList<HttpHost>();
		for (String host : hosts) {
			String[] hp = host.split(":");
			httpHosts.add(new HttpHost(hp[0], Integer.valueOf(hp[1]), "http"));
		}
		return new RestHighLevelClient(RestClient.builder(httpHosts.toArray(new HttpHost[0])));
	}
	
	public static RestHighLevelClient getClient() {
		return client;
	}
	
}
