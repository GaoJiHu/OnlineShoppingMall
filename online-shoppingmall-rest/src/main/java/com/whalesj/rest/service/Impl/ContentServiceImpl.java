package com.whalesj.rest.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalesj.mapper.TbContentMapper;
import com.whalesj.pojo.TbContent;
import com.whalesj.pojo.TbContentExample;
import com.whalesj.pojo.TbContentExample.Criteria;
import com.whalesj.rest.service.ContentService;

/**
 * 内容查询服务Service
 * @author wushijia
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public List<TbContent> getContentList(Long cid) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		return list;
	}

}
