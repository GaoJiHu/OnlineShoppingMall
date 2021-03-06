package com.whalesj.rest.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 描述商品类目及列表的pojo
 * @author wushijia
 *
 */
public class CatNode {
	@JsonProperty("u")
	private String url;//对应json树中的key  u
	
	@JsonProperty("n")
	private String name;
	
	@JsonProperty("i")
	private List items;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	
}
