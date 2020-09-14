package com.weiliang.project.order.domain;

import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单 order
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**订单ID**/
    private Long orderId;

    /**订单流水**/
    private String orderNo;

    /**订单总价**/
    private Double totalPrice;

    /**支付状态**/
    private String payStatus;

    /**支付方式**/
    private String payType;

    /**隶属店铺**/
    private String underShop;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUnderShop() {
        return underShop;
    }

    public void setUnderShop(String underShop) {
        this.underShop = underShop;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("orderNo", getOrderNo())
                .append("totalPrice", getTotalPrice())
                .append("payStatus", getPayStatus())
                .append("payType", getPayType())
                .append("underShop", getUnderShop())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
