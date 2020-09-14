package com.weiliang.project.order.mapper;

import com.weiliang.project.order.domain.Order;
import com.weiliang.project.order.domain.OrderList;

import java.util.List;

/**
 * 订单 数据层
 */
public interface OrderMapper {

    public List<OrderList> selectOrderList(OrderList orderList);

    public Order selectOrderById(Long orderId);

    public int insertOrder(Order order);

    public int updateOrder(Order order);

    public int deleteOrderByIds(Long[] orderIds);

}
