package com.weiliang.project.order.service.impl;

import com.weiliang.project.order.domain.OrderItem;
import com.weiliang.project.order.mapper.OrderItemMapper;
import com.weiliang.project.order.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单商品 服务层实现
 *
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> selectOrderItemList(String orderNo) {
        return orderItemMapper.selectOrderItemList(orderNo);
    }

    @Override
    public int insertOrderItem(OrderItem orderItem) {
        return orderItemMapper.insertOrderItem(orderItem);
    }

    @Override
    public int deleteOrderItemByIds(Long[] orderItemIds) {
        return orderItemMapper.deleteOrderItemByIds(orderItemIds);
    }
}
