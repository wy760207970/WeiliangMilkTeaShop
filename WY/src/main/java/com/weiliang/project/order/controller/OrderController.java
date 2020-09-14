package com.weiliang.project.order.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.order.domain.Order;
import com.weiliang.project.order.domain.OrderList;
import com.weiliang.project.order.service.IOrderService;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.domain.SysUser;
import com.weiliang.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单 信息操作处理
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISysUserService userService;
    /**
     * 获取订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderList orderList)
    {
        if (!SecurityUtils.getUsername().equals("admin")){
            SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
            SysDept sysDept = sysUser.getDept();
            orderList.setUnderShop(sysDept.getDeptName());
        }
        startPage();
        List<OrderList> list = orderService.selectOrderList(orderList);
        return getDataTable(list);
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('order:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Order order)
    {
        SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
        SysDept sysDept = sysUser.getDept();
        if (sysDept!=null){
            order.setUnderShop(sysDept.getDeptName());
        }
        order.setCreateBy(SecurityUtils.getUsername());
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('order:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByIds(orderIds));
    }

}
