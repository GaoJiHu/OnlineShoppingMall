package com.whalesj.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.portal.service.CartService;
import com.whalesj.protal.pojo.CartItem;

/**
 * 购物车Controller
 * @author wushijia
 *
 */
@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,Integer itemNum,HttpServletRequest request,HttpServletResponse response){
		TaotaoResult taotaoResult = cartService.addCart(itemId, itemNum, request, response);
		return "cart-success";
	}
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request,Model model){
		List<CartItem> list = cartService.getCartItems(request);
		model.addAttribute("cartList",list);
		return "cart";
	}
	@RequestMapping("/cart/upadte/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartItemNum(@PathVariable Long itemId,
			@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
		TaotaoResult result = cartService.updateCart(itemId, num, request, response);
		return result;
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request,HttpServletResponse response){
		TaotaoResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
