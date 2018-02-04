package com.whalesj.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whalesj.pojo.TbUser;
import com.whalesj.portal.service.CartService;
import com.whalesj.portal.service.OrderService;
import com.whalesj.protal.pojo.CartItem;
import com.whalesj.protal.pojo.OrderInfo;





/**
 * 生成订单处理
 * @author wushijia
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(Model model,HttpServletRequest request){
		//取商品列表
		List<CartItem> list = cartService.getCartItems(request);
		model.addAttribute("cartList", list);
		return  "order-cart";
	}
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,Model model,HttpServletRequest request){//request用来取被拦截器拦截后设置的user对象
		TbUser user = (TbUser) request.getAttribute("user");
		//补全orderInfo属性
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务
		String orderId = orderService.createOrder(orderInfo);
		//将订单号传递给页面
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		DateTime date = dateTime.plusDays(3);
		model.addAttribute("date", date.toString("yyyy-MM-dd"));
		//返回逻辑属兔
		return "success";
	}
}
