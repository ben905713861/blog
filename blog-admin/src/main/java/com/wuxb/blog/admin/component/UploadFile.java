package com.wuxb.blog.admin.component;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wuxb.httpServer.util.Config;
import com.wuxb.httpServer.util.Curl;
import com.wuxb.httpServer.util.FormdataParamsEncode;


public class UploadFile {

	private static final String FILE_SERVER_UPLOAD_API = Config.get("FILE_SERVER_UPLOAD_API");
	private static final String FILE_SERVER_DOMAIN = Config.get("FILE_SERVER_DOMAIN");
	private List<Map<String, Object>> fileResultList;
	private String errorMessage;
	
	public FormdataParamsEncode getFormdataObject() {
		return new FormdataParamsEncode();
	}
	
	@SuppressWarnings("unchecked")
	public boolean send(FormdataParamsEncode formdata) {
		Curl curl = new Curl(FILE_SERVER_UPLOAD_API);
		curl.setMethod("POST");
		curl.setContentType(formdata.getContentType());
		curl.setBodyByte(formdata.toByteArray());
		curl.send();
		String json = curl.getResponseData();
		if(json == null || json.isEmpty()) {
			errorMessage = "FAIL 返回json为空";
			return false;
		}
		JSONObject jsonObject = JSONObject.parseObject(json);
		if(jsonObject.getBoolean("status") == false) {
			errorMessage = "FAIL 返回false";
			return false;
		}
		List<?> errorList = jsonObject.getObject("errorList", List.class);
		if(errorList.size() > 0) {
			errorMessage = "FAIL "+ errorList.toString();
			return false;
		}
		fileResultList = jsonObject.getObject("fileInfoList", List.class);
		if(fileResultList.size() == 0) {
			errorMessage = "FAIL fileInfoList为空";
			return false;
		}
		
		for(Map<String, Object> fileResult : fileResultList) {
			String url = FILE_SERVER_DOMAIN + fileResult.get("path");
			fileResult.put("url", url);
		}
		return true;
	}
	
	public List<Map<String, Object>> getFileResultList() {
		return fileResultList;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
