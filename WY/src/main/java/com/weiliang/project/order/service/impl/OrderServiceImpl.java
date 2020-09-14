package com.weiliang.project.order.service.impl;

import com.weiliang.project.order.domain.Order;
import com.weiliang.project.order.domain.OrderList;
import com.weiliang.project.order.mapper.OrderMapper;
import com.weiliang.project.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单 服务层实现
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderList> selectOrderList(OrderList orderList) {
        return orderMapper.selectOrderList(orderList);
    }

    @Override
    public Order selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrderByIds(Long[] orderIds) {
        return orderMapper.deleteOrderByIds(orderIds);
    }
}
