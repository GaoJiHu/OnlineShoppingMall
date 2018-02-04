package com.whalesj.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whalesj.common.pojo.TaotaoResult;
import com.whalesj.protal.pojo.CartItem;

public interface CartService {
	public TaotaoResult addCart(Long itemId,Integer itemNum,HttpServletRequest request ,HttpServletResponse response);
	public List<CartItem> getCartItems(HttpServletRequest request);
	public TaotaoResult updateCart(Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response);
	public TaotaoResult deleteCartItem(Long itemId,HttpServletRequest request,HttpServletResponse response);
}
