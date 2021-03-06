package com.whalesj.rest.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalesj.mapper.TbItemCatMapper;
import com.whalesj.pojo.TbItemCat;
import com.whalesj.pojo.TbItemCatExample;
import com.whalesj.pojo.TbItemCatExample.Criteria;
import com.whalesj.rest.pojo.CatNode;
import com.whalesj.rest.pojo.ItemCatResult;
import com.whalesj.rest.service.ItemCatService;

/**
 * 得到商品类目列表
 * @author wushijia
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public ItemCatResult getItemCatList() {
		List list = getItemCatList(0l);
		//设置根节点
		ItemCatResult result = new ItemCatResult();
		result.setData(list);
		return result;
	}
	
	private List getItemCatList(Long parentId){
		//执行查询，根据parentid查
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		List resultList = new ArrayList();
		int index = 0;
		for (TbItemCat itemCat : list) {
			if(index >= 14){
				break;
			}
			//如果是父节点
			if(itemCat.getIsParent()){
				CatNode node = new CatNode();
				node.setUrl("/products/"+itemCat.getId()+".html");
				//如果当前节点为一级节点
				if(itemCat.getParentId() == 0){
					node.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
					//第一级节点不超过14个
					index++;
				}else{
					node.setName(itemCat.getName());
				}
				node.setItems(getItemCatList(itemCat.getId()));
				
				//将node添加到列表
				resultList.add(node);
			}else{
				//如果是叶子节点
				String item = "/products/"+itemCat.getId()+".html|" + itemCat.getName();
				resultList.add(item);
			}
			
		}
		return resultList;
	}
}
