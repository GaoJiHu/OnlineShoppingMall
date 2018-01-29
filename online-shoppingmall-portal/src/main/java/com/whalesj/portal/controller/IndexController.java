package com.whalesj.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whalesj.portal.service.ContentService;

/**
 * 首页访问
 * @author wushijia
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("index")
	public String showIndex(Model model){
		//取轮播图内容
		String json = contentService.getAdList();
		//传递给页面
		model.addAttribute("ad1", json);
		return "index";
	}
}
