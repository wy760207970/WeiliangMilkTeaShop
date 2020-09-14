package com.weiliang.project.order.service;

import com.weiliang.project.order.domain.OrderItem;

import java.util.List;

/**
 * 订单商品 服务层
 */
public interface IOrderItemService {

    public List<OrderItem> selectOrderItemList(String orderNo);

    public int insertOrderItem(OrderItem orderItem);

    public int deleteOrderItemByIds(Long[] orderItemIds);
}
