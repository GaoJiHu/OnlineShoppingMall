package com.whalesj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.EasyUiTreeNode;
import com.whalesj.mapper.TbItemCatMapper;
import com.whalesj.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUiTreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") Long parentId){//页面中传入的是id(实际上就是parentid)，但我们应该查parentid，因此需要用到该注解将id赋给它
		List<EasyUiTreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
}
