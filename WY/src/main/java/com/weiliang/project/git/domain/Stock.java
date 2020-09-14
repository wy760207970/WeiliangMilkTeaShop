package com.weiliang.project.git.domain;

import com.weiliang.framework.aspectj.lang.annotation.Excel;
import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 库存 stock
 */
public class Stock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**库存ID**/
    @Excel(name = "库存编号", cellType = Excel.ColumnType.NUMERIC)
    private Long stockId;

    /**库存名称**/
    @Excel(name = "库存名称")
    private String stockName;

    /**隶属店铺**/
    @Excel(name = "隶属店铺")
    private String underShop;

    /**最新库存量**/
    @Excel(name = "最新库存量")
    private Double stockNum;

    /**库存单位**/
    @Excel(name = "库存单位", readConverterExp = "0=千克,1=克,2=L,3=桶,4=罐")
    private String stockUnit;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getUnderShop() {
        return underShop;
    }

    public void setUnderShop(String underShop) {
        this.underShop = underShop;
    }

    public Double getStockNum() {
        return stockNum;
    }

    public void setStockNum(Double stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("stockId", getStockId())
                .append("stockName", getStockName())
                .append("underShop", getUnderShop())
                .append("stockNum", getStockNum())
                .append("stockUnit", getStockUnit())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
