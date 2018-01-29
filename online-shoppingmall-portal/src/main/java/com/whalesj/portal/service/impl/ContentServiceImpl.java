package com.whalesj.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.common.utils.HttpClientUtil;
import com.whalesj.common.utils.JsonUtils;
import com.whalesj.pojo.TbContent;
import com.whalesj.portal.service.ContentService;
import com.whalesj.protal.pojo.AdNode;
/**
 * 轮播图的展示
 * @author wushijia
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	
	@Value("${REST_CONTENT_AD}")
	private String REST_CONTENT_AD;
	
	
	@Override
	public String getAdList() {
		//调用服务获得数据
		//url:http://localhost:8081/rest/content/89
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_CONTENT_AD);//返回一个json数据(TaotaoResult类型)
		//将该String转换位json数据
		TaotaoResult taotaoResult = TaotaoResult.formatToList(json, TbContent.class);//后面的表示的是List中的数据类型
		//取出data属性
		List<TbContent> contentList = (List<TbContent>) taotaoResult.getData();
		//将内容列表转换位AdNode列表
		List<AdNode> list = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(tbContent.getPic());//第一张图的url
			
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(tbContent.getPic2());//第二张图的url
			
			node.setAlt(tbContent.getSubTitle());
			node.setHref(tbContent.getUrl());
			
			list.add(node);
		}
		//将list转换为json
		String result = JsonUtils.objectToJson(list);
		return result;
	}

}
