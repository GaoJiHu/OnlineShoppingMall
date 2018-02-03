package com.whalesj.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.sso.service.LogOutService;

/**
 * 安全退出接口
 * @author wushijia
 *
 */
@Controller
public class LogOutController {
	@Autowired
	private LogOutService logoutService;
	
	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token,String callback){
		try {
			TaotaoResult taotaoResult = logoutService.logout(token);
			//支持jsonp
			if(StringUtils.isNotBlank(callback)){
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return taotaoResult;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "退出登陆出现异常!");
		}
	}
}
