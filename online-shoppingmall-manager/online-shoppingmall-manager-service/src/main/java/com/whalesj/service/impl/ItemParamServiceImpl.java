package com.whalesj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.mapper.TbItemParamMapper;
import com.whalesj.pojo.TbItemParam;
import com.whalesj.pojo.TbItemParamExample;
import com.whalesj.pojo.TbItemParamExample.Criteria;
import com.whalesj.service.ItemParamService;
/**
 * 商品规格参数模板
 * @author wushijia
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Override
	public TaotaoResult getParamByCid(Long cid) {
		//根据cid查询规格参数模板
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//执行查询
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){//如果有值，就将查询到的值返回
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult insertItemParam(Long cid, String paramData) {
		//创建pojo进行插入
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
