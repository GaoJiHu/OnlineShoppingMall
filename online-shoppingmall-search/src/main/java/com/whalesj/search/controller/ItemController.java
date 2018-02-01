package com.whalesj.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.search.service.ItemService;


/**
 * 导入商品数据Controller
 * @author wushijia
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importItems(){
		try {
			TaotaoResult result = itemService.importItems();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "search服务端出错啦!");
		}
	}
}
