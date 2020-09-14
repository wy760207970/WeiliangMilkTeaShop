package com.weiliang.project.enterprise.domain;

import com.weiliang.framework.aspectj.lang.annotation.Excel;
import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 饮品菜单 drink_menu
 */
public class DrinkMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**饮品菜单ID**/
    @Excel(name = "饮品菜单编号", cellType = Excel.ColumnType.NUMERIC)
    private Long menuId;

    /**饮品名称**/
    @Excel(name = "饮品名称")
    private String menuName;

    /**饮品温度**/
    @Excel(name = "饮品温度")
    private String temp;

    /**饮品规格**/
    @Excel(name = "饮品规格")
    private String specs;

    /**饮品价格**/
    @Excel(name = "饮品价格")
    private String price;

    /**饮品类别**/
    @Excel(name = "饮品类别", readConverterExp = "0=推荐爆款,1=布丁,2=双皮奶,3=果茶,4=果汁,5=咖啡,6=牛奶,7=奶昔,8=奶茶")
    private String classify;

    /**显示顺序**/
    @Excel(name = "显示顺序", type = Excel.Type.IMPORT)
    private String orderNum;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", getMenuId())
                .append("menuName", getMenuName())
                .append("temp", getTemp())
                .append("specs", getSpecs())
                .append("price", getPrice())
                .append("classify", getClassify())
                .append("orderNum", getOrderNum())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
