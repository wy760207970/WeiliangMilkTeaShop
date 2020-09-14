package com.weiliang.project.order.mapper;

import com.weiliang.project.order.domain.OrderItem;

import java.util.List;

/**
 * 订单商品 数据层
 */
public interface OrderItemMapper {

    public List<OrderItem> selectOrderItemList(String orderNo);

    public int insertOrderItem(OrderItem orderItem);

    public int deleteOrderItemByIds(Long[] orderItemIds);

}
