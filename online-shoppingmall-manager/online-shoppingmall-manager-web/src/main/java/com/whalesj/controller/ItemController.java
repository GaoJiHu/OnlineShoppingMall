package com.whalesj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.EasyUiDateGridResult;
import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.pojo.TbItem;
import com.whalesj.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem item = itemService.getItem(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUiDateGridResult getItemList(Integer page,Integer rows){
		EasyUiDateGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	@RequestMapping(value="/item/save",method=RequestMethod.POST)//注意此处是POST请求
	@ResponseBody
	public TaotaoResult createItem(TbItem item,String desc,String itemParams){
		TaotaoResult result = itemService.createItem(item, desc,itemParams);
		return result;
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model){
		String html = itemService.getItemParamHtml(itemId);
		model.addAttribute("html", html);
		return "item-param";
	}
}
