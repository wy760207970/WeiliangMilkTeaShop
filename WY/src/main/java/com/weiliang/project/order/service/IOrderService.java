package com.weiliang.project.order.service;

import com.weiliang.project.order.domain.Order;
import com.weiliang.project.order.domain.OrderList;

import java.util.List;

/**
 * 订单 服务层
 */
public interface IOrderService {

    public List<OrderList> selectOrderList(OrderList orderList);

    public Order selectOrderById(Long orderId);

    public int insertOrder(Order order);

    public int updateOrder(Order order);

    public int deleteOrderByIds(Long[] orderIds);
}
