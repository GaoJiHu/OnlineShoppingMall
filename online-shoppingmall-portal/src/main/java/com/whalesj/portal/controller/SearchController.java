package com.whalesj.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whalesj.portal.service.SearchService;
import com.whalesj.protal.pojo.SearchResult;

/**
 * 商品查询
 * @author wushijia
 *
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String keyword,
						@RequestParam(defaultValue="1")Integer page,
						@RequestParam(defaultValue="60")Integer rows,Model model){
		//get乱码处理
		try {
			keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		SearchResult result = searchService.search(keyword, page, rows);
		//将参数传递给页面
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages",result.getPageCount());
		model.addAttribute("itemList",result.getItemList());
		model.addAttribute("page",result.getCurPage());
		
		//返回逻辑视图
		return "search";
	}
}
