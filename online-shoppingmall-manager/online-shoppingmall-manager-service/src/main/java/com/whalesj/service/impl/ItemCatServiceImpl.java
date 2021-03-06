package com.whalesj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalesj.common.pojo.EasyUiTreeNode;
import com.whalesj.mapper.TbItemCatMapper;
import com.whalesj.pojo.TbItemCat;
import com.whalesj.pojo.TbItemCatExample;
import com.whalesj.pojo.TbItemCatExample.Criteria;
import com.whalesj.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUiTreeNode> getItemCatList(long parentId) {
		//根据parentId查询类目列表
		TbItemCatExample example = new TbItemCatExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//处理结果
		List<EasyUiTreeNode> result = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUiTreeNode node = new EasyUiTreeNode();//创建一个节点对象，每遍历一个
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			result.add(node);//添加进结果集合
		}
		return result;
	}

}
