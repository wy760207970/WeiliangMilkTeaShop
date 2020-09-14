package com.weiliang.project.order.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.order.domain.OrderItem;
import com.weiliang.project.order.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单商品 信息操作处理
 *
 */
@RestController
@RequestMapping("/orderItem")
public class OrderItemController extends BaseController {
    @Autowired
    private IOrderItemService orderService;

    /**
     * 获取订单商品列表
     */
    @PreAuthorize("@ss.hasPermi('orderItem:list')")
    @GetMapping("/list/{orderNo}")
    public TableDataInfo list(@PathVariable String orderNo)
    {
        List<OrderItem> list = orderService.selectOrderItemList(orderNo);
        return getDataTable(list);
    }

    /**
     * 新增订单商品
     */
    @PreAuthorize("@ss.hasPermi('orderItem:add')")
    @Log(title = "订单商品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody OrderItem order)
    {
        return toAjax(orderService.insertOrderItem(order));
    }

    /**
     * 删除订单商品
     */
    @PreAuthorize("@ss.hasPermi('orderItem:remove')")
    @Log(title = "订单商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderItemIds}")
    public AjaxResult remove(@PathVariable Long[] orderItemIds)
    {
        return toAjax(orderService.deleteOrderItemByIds(orderItemIds));
    }

}
