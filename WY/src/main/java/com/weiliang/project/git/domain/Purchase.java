package com.weiliang.project.git.domain;

import com.weiliang.framework.aspectj.lang.annotation.Excel;
import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 采购清单 purchase
 */
public class Purchase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**采购清单ID**/
    @Excel(name = "采购清单编号", cellType = Excel.ColumnType.NUMERIC)
    private Long purchaseId;

    /**采购原料名称**/
    @Excel(name = "采购原料名称")
    private String purchaseName;

    /**隶属店铺**/
    @Excel(name = "隶属店铺", type = Excel.Type.EXPORT)
    private String underShop;

    /**采购量**/
    @Excel(name = "采购量")
    private Double purchaseNum;

    /**单位**/
    @Excel(name = "单位", readConverterExp = "0=千克,1=克,2=L,3=桶,4=罐")
    private String purchaseUnit;

    /**单价**/
    @Excel(name = "单价")
    private Double unitPrice;

    /**总价**/
    @Excel(name = "总价")
    private Double totalPrice;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getUnderShop() {
        return underShop;
    }

    public void setUnderShop(String underShop) {
        this.underShop = underShop;
    }

    public Double getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Double purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("purchaseId", getPurchaseId())
                .append("purchaseName", getPurchaseName())
                .append("underShop", getUnderShop())
                .append("purchaseNum", getPurchaseNum())
                .append("purchaseUnit", getPurchaseUnit())
                .append("unitPrice", getUnitPrice())
                .append("totalPrice", getTotalPrice())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
