package com.weiliang.project.git.domain;

import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 库存详情 stockDetail
 */
public class StockDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**库存详情ID**/
    private Long stockDetailId;

    /**库存ID**/
    private Long stockId;

    /**库存量**/
    private Double stockNum;

    /**备注**/
    private String remark;

    private Stock stock;

    public Long getStockDetailId() {
        return stockDetailId;
    }

    public void setStockDetailId(Long stockDetailId) {
        this.stockDetailId = stockDetailId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Double getStockNum() {
        return stockNum;
    }

    public void setStockNum(Double stockNum) {
        this.stockNum = stockNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("stockDetailId", getStockDetailId())
                .append("stockId", getStockId())
                .append("stockNum", getStockNum())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
