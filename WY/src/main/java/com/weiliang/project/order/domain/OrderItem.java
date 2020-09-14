package com.weiliang.project.order.domain;

import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单商品 orderItem
 */
public class OrderItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**订单商品ID**/
    private Long orderItemId;

    /**订单流水**/
    private String orderNo;

    /**饮品名称**/
    private String drinkName;

    /**饮品温度**/
    private String temp;

    /**饮品规格**/
    private String specs;

    /**饮品单价**/
    private Double drinkPrice;

    /**订单数量**/
    private int orderCount;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Double getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(Double drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderItemId", getOrderItemId())
                .append("orderNo", getOrderNo())
                .append("drinkName", getDrinkName())
                .append("temp", getTemp())
                .append("specs", getSpecs())
                .append("drinkPrice", getDrinkPrice())
                .append("orderCount", getOrderCount())
                .toString();
    }
}
