package com.whalesj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalesj.common.pojo.EasyUiTreeNode;
import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.mapper.TbContentCategoryMapper;
import com.whalesj.pojo.TbContentCategory;
import com.whalesj.pojo.TbContentCategoryExample;
import com.whalesj.pojo.TbContentCategoryExample.Criteria;
import com.whalesj.service.ContentCategoryService;

/**
 * 内容管理系统(CMS)Service
 * @author wushijia
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper ;

	@Override
	public List<EasyUiTreeNode> getContentCatList(Long parentId) {
		//根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//将其转换
		List<EasyUiTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建EasyUiTreeNode
			EasyUiTreeNode node = new EasyUiTreeNode();
			//填充
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());//分类名称
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertCategory(Long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setStatus(1);//1正常  2删除
		contentCategory.setIsParent(false);
		//展现次序
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(new Date());
		contentCategory.setCreated(new Date());
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		//取返回的主键
		Long id = contentCategory.getId();
		//判断父节点的isparent属性
		//先查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			//更新
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		//将主键包i装
		return TaotaoResult.ok(id);
	}

}
