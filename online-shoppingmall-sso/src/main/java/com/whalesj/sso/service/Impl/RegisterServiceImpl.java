package com.whalesj.sso.service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.mapper.TbUserMapper;
import com.whalesj.pojo.TbUser;
import com.whalesj.pojo.TbUserExample;
import com.whalesj.pojo.TbUserExample.Criteria;
import com.whalesj.sso.service.RegisterService;


/**
 * 注册服务
 * @author wushijia
 *
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public TaotaoResult checkData(String param, int type) {
		//根据数据类型查询
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1、2、3分别代表username、phone、email
		if(1 == type){
			criteria.andUsernameEqualTo(param);
		}else if(2 == type){
			criteria.andPhoneEqualTo(param);
		}else if(3 == type){
			criteria.andEmailEqualTo(param);
		}else{
			
		}
		List<TbUser> list = userMapper.selectByExample(example);
		//判断查询结果
		if(list == null || list.isEmpty()){
			return TaotaoResult.ok(true);
		}
		//有结果代表用户名等信息不可用
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		//校验数据，用户名密码不能为空
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return TaotaoResult.build(400, "用户名或密码不能为空");
		}
		//校验数据是否重复
		TaotaoResult taotaoResult = checkData(user.getUsername(),1);
		if(!(boolean) taotaoResult.getData()){
			return TaotaoResult.build(400, "该用户已存在");
		}
		//检验手机号是否重复
		if(user.getPhone() != null){
			taotaoResult = checkData(user.getPhone(),2);
			if(!(boolean) taotaoResult.getData()){
				return TaotaoResult.build(400, "该手机号已被注册");
			}
		}
		//校验邮箱
		if(user.getEmail() != null){
			taotaoResult = checkData(user.getEmail(),3);
			if(!(boolean) taotaoResult.getData()){
				return TaotaoResult.build(400, "该邮箱已被注册");
			}
		}
		//校验完成  进行插入
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//将密码进行MD5生成hash，是Spring中自带的
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

}
