package com.whalesj.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.sso.service.LoginService;

/**
 * 用户登陆Controller
 * @author wushijia
 *
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		try {
			TaotaoResult taotaoResult = loginService.login(username, password, request, response);
			return taotaoResult;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "出错了！请重新登陆");
		}
	}
	
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		try {
			TaotaoResult taotaoResult = loginService.getUserByToken(token);
			if(StringUtils.isNotBlank(callback)){//如果callbask不为空
				//支持jsonp调用
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return taotaoResult;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "获取用户信息错误");
		}
	}
}
