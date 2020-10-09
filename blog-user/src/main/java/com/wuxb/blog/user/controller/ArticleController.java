package com.wuxb.blog.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import com.wuxb.blog.admin.component.ElasticSearchConnection;
import com.wuxb.blog.admin.component.UeditorFileDomainFilter;
import com.wuxb.httpServer.annotation.GetParam;
import com.wuxb.httpServer.annotation.PostParam;
import com.wuxb.httpServer.annotation.RequestMapping;
import com.wuxb.httpServer.annotation.RestController;
import com.wuxb.httpServer.db.Db;
import com.wuxb.httpServer.params.RequestMethod;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Tools;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private static final RestHighLevelClient esClient = ElasticSearchConnection.getClient();
	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	
	@RequestMapping("/getTypes")
	public List<Map<String, Object>> getTypes(@GetParam Map<String, Object> getMap) throws SQLException {
		List<Map<String, Object>> articleTypeList = Db.table("article_type at")
			.field("at.*,COUNT(a.article_id) article_num")
			.join("article a", "a.type_id=at.type_id", "LEFT")
			.group("at.type_id")
			.order("at.sort", "ASC")
			.cache(10)
			.select();
		return articleTypeList;
	}
	
	@RequestMapping("/getRecommend")
	public List<Map<String, Object>> getRecommend(@GetParam Map<String, Object> getMap) throws SQLException {
		List<Map<String, Object>> articleRecommendList = Db.table("article")
			.field("article_id,title")
			.where("is_recommend", 1)
			.order("add_time", "DESC")
			.limit(10)
			.cache(10)
			.select();
		return articleRecommendList;
	}
	
	
	@RequestMapping("/getList")
	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws IOException, SQLException {
		var highlightBuilder = new HighlightBuilder()
//			.boundaryMaxScan(10)
			.preTags("<span style=\"color:red\">")
			.postTags("</span>")
			.field("title").field("content"); 
		
		//过滤条件
		var boolQueryBuilder = new BoolQueryBuilder();
		var type_id = getMap.get("type_id");
		if(type_id != null) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("type_id", type_id));
		}
		var title = getMap.get("title");
		if(title != null) {
			String queryString = String.valueOf(title);
			//对特殊字符加反斜杠转义
			if(queryString.length() == 1) {
				queryString = QueryParser.escape(queryString);
			}
			boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString).field("title").field("content"));
		}
		
		//es搜索
		var searchSourceBuilder = new SearchSourceBuilder()
			.query(boolQueryBuilder)
			.highlighter(highlightBuilder);
		//分页查询
		long page = (Long) getMap.getOrDefault("page", 1l);
		long limit = (Long) getMap.getOrDefault("limit", 10l);
		long offset = (page - 1) * limit;
		searchSourceBuilder.from(Long.valueOf(offset).intValue())
			.size(Long.valueOf(limit).intValue());
		var searchRequest = new SearchRequest(ElasticSearchConnection.INDEX)
			.source(searchSourceBuilder);
		var response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		var hits = response.getHits();
		
		//查询总条数
		long count = hits.getTotalHits().value;
		
		//处理返回结果
		var searchHits = hits.getHits();
		var responseList = new ArrayList<Map<String, Object>>();
		for (SearchHit hit : searchHits) {
			var articleInfo = hit.getSourceAsMap();
			//主键
			articleInfo.put("article_id", hit.getId());
			//缩略图
			var thumb_path = (String) articleInfo.get("thumb_path");
			if(thumb_path == null || thumb_path.isEmpty()) {
				articleInfo.put("thumb_url", "images/default_thumb_264x176.jpg");
			} else {
				articleInfo.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
			}
			//没有关键词高亮的，直接截取正文前200字符显示
			if(hit.getHighlightFields().size() == 0) {
				var content = (String) articleInfo.get("content");
				String simpleContent;
				if(content.length() > 200) {
					simpleContent = content.substring(0, 200);
				} else {
					simpleContent = content;
				}
				articleInfo.put("content", simpleContent);
			} else {
				hit.getHighlightFields().forEach((key, value) -> {
					var description = new StringBuffer();
					for(var fragment : value.getFragments()) {
						description.append(fragment);
					}
					articleInfo.put(key, description.toString());
				});
			}
			responseList.add(articleInfo);
        }
		var res = new HashMap<String, Object>();
		res.put("list", responseList);
		res.put("count", count);
		
		String type;
		if(type_id != null) {
			type = (String) Db.table("article_type")
				.where("type_id", type_id)
				.limit(1)
				.value("type");
		} else {
			type = "全部";
		}
		res.put("type", type);
		return res;
	}
	
//	@RequestMapping("/getList")
//	public Map<String, Object> getList(@GetParam Map<String, Object> getMap) throws SQLException {
//		Map<String, Object> res = new HashMap<String, Object>();
//		
//		Map<String, Object> where = new HashMap<String, Object>();
//		Object _title = getMap.get("title");
//		if(_title != null) {
//			String title;
//			if(_title instanceof String) {
//				title = (String) _title;
//			} else {
//				title = _title.toString();
//			}
//			if(title != null && !title.isBlank()) {
//				where.put("title", new Object[] {"LIKE", "%"+title.trim()+"%"});
//			}
//		}
//		Long type_id = (Long) getMap.get("type_id");
//		if(type_id != null) {
//			where.put("type_id", type_id);
//		}
//		
//		List<Map<String, Object>> list = Db.table("article")
//			.where(where)
//			.order("add_time", "DESC")
//			.page((long)getMap.getOrDefault("page", 1l), (long)getMap.getOrDefault("limit", 10l))
//			.cache(10)
//			.select();
//		for(Map<String, Object> map : list) {
//			String thumb_path = (String) map.get("thumb_path");
//			if(thumb_path == null || thumb_path.isEmpty()) {
////				map.put("thumb_url", "images/default_thumb_264x176.jpg");
//			} else {
//				map.put("thumb_url", FILE_SERVER_DOMAIN + thumb_path);
//			}
//		}
//		res.put("list", list);
//		
//		int count = Db.table("article")
//			.where(where)
//			.cache(10)
//			.count();
//		res.put("count", count);
//		
//		String type;
//		if(type_id != null) {
//			type = (String) Db.table("article_type")
//				.where("type_id", type_id)
//				.limit(1)
//				.value("type");
//		} else {
//			type = "全部";
//		}
//		res.put("type", type);
//		
//		return res;
//	}
	
	@RequestMapping("/getOne")
	public Map<String, Object> getOne(@GetParam Map<String, Object> getMap) throws SQLException {
		Map<String, Object> res = new HashMap<String, Object>();
		
		Map<String, Object> articleInfo = Db.table("article a")
			.field("a.*,t.type,c.content")
			.join("article_content c", "c.article_id=a.article_id", "LEFT")
			.join("article_type t", "t.type_id=a.type_id", "LEFT")
			.where("a.article_id", getMap.get("article_id"))
			.find();
		if(articleInfo == null) {
			return res;
		}
		//还原文件网址前缀
		String content = (String) articleInfo.get("content");
		articleInfo.replace("content", UeditorFileDomainFilter.replay(content));
		res.put("articleInfo", articleInfo);
		
		Map<String, Object> nextArticleInfo = Db.table("article")
			.field("article_id,title")
			.where("type_id", articleInfo.get("type_id"))
			.where("article_id", ">", getMap.get("article_id"))
			.order("article_id", "ASC")
			.find();
		res.put("nextArticleInfo", nextArticleInfo);
		
		Map<String, Object> lastArticleInfo = Db.table("article")
			.field("article_id,title")
			.where("type_id", articleInfo.get("type_id"))
			.where("article_id", "<", getMap.get("article_id"))
			.order("article_id", "DESC")
			.find();
		res.put("lastArticleInfo", lastArticleInfo);
		
		return res;
	}
	
	@RequestMapping("/getNum")
	public Map<String, Object> getLikeNum(@GetParam Map<String, Object> getMap) throws SQLException {
		return Db.table("article")
			.field("read_num,like_num")
			.where("article_id", getMap.get("article_id"))
			.find();
	}
	
	@RequestMapping(value="/like", method=RequestMethod.POST)
	public Map<String, Object> like(@PostParam Map<String, Object> postMap) throws SQLException {
		Db.table("article")
			.where("article_id", postMap.get("article_id"))
			.limit(1)
			.setInc("like_num", 1);
		return Tools.returnSucc();
	}
	
	@RequestMapping(value="/read", method=RequestMethod.POST)
	public Map<String, Object> read(@PostParam Map<String, Object> postMap) throws SQLException {
		Db.table("article")
			.where("article_id", postMap.get("article_id"))
			.limit(1)
			.setInc("read_num", 1);
		return Tools.returnSucc();
	}
	
}
