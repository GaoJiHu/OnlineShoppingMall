package com.whalesj.sso.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.sso.component.JedisClient;
import com.whalesj.sso.service.LogOutService;


/**
 * 安全退出
 * @author wushijia
 *
 */
@Service
public class LogOutServiceImpl implements LogOutService {
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;

	@Override
	public TaotaoResult logout(String token) {
		jedisClient.del(REDIS_SESSION_KEY+":"+token);
		return TaotaoResult.ok();
	}

}
