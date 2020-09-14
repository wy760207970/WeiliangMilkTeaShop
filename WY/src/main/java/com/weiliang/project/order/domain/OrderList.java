package com.weiliang.project.order.domain;

import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单列表
 */
public class OrderList extends BaseEntity {
    /**订单流水**/
    private String orderNo;

    /**订单内容**/
    private String content;

    /**订单总价**/
    private Double totalPrice;

    /**支付方式**/
    private String payType;

    /**支付状态**/
    private String payStatus;

    /**隶属店铺**/
    private String underShop;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
                .append("orderNo", getOrderNo())
                .append("content", getContent())
                .append("totalPrice", getTotalPrice())
                .append("payType", getPayType())
                .append("payStatus", getPayStatus())
                .append("underShop", getUnderShop())
                .append("createTime", getCreateTime())
                .toString();
    }
}
