package com.whalesj.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.utils.JsonUtils;
import com.whalesj.rest.pojo.ItemCatResult;
import com.whalesj.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")//设置返回类型为json
	@ResponseBody
	public String getItemCatList(String callback){
		ItemCatResult result = itemCatService.getItemCatList();
		if(StringUtils.isBlank(callback)){
			//将result转换为字符串
			String json = JsonUtils.objectToJson(result);
			return json;
		}
		//如果字符串不为空，要支持jsonp调用
		String json = JsonUtils.objectToJson(result);
		return callback + "("+json+");";
	}
}
