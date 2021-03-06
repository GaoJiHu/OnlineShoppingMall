package com.whalesj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whalesj.common.pojo.EasyUiDateGridResult;
import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.common.utils.IDUtils;
import com.whalesj.common.utils.JsonUtils;
import com.whalesj.mapper.TbItemDescMapper;
import com.whalesj.mapper.TbItemMapper;
import com.whalesj.mapper.TbItemParamItemMapper;
import com.whalesj.mapper.TbItemParamMapper;
import com.whalesj.pojo.TbItem;
import com.whalesj.pojo.TbItemDesc;
import com.whalesj.pojo.TbItemExample;
import com.whalesj.pojo.TbItemParamItem;
import com.whalesj.pojo.TbItemParamItemExample;
import com.whalesj.pojo.TbItemParamItemExample.Criteria;
import com.whalesj.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItem(Long itemid) {
		TbItem item = itemMapper.selectByPrimaryKey(itemid);
		return item;
	}

	@Override
	public EasyUiDateGridResult getItemList(int page, int rows) {
		// 1、分页处理
		PageHelper.startPage(page, rows);
		// 2、执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);// 无条件查询
		// 3、处理结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);// 取分页信息
		EasyUiDateGridResult result = new EasyUiDateGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) {
		// 生成商品id
		long id = IDUtils.genItemId();
		// 补全商品属性
		item.setId(id);
		// 商品状态 1-正常，2-下架3-删除
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品
		itemMapper.insert(item);
		// 添加商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);// 商品id
		itemDesc.setItemDesc(desc);// 商品描述
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		itemDescMapper.insert(itemDesc);

		// 商品规格参数处理
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setItemId(id);
		paramItem.setParamData(itemParam);
		paramItem.setCreated(date);
		paramItem.setUpdated(date);

		itemParamItemMapper.insert(paramItem);

		return TaotaoResult.ok();// 处理成功返回该对象
	}

	@Override
	public String getItemParamHtml(Long itemId) {
		// 根据商品id查询规格参数列表
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.isEmpty()) {// 物值
			return "";
		}
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();// 取到规格参数
		// 将七转换为java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		// 表格生成
		StringBuffer sb = new StringBuffer();

		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
		List<Map> mapList2 = (List<Map>) map.get("params");
		for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

}
