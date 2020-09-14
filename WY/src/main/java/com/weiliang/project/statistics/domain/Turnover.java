package com.weiliang.project.statistics.domain;

import com.weiliang.framework.aspectj.lang.annotation.Excel;
import com.weiliang.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Turnover extends BaseEntity {
    /**统计时间**/
    @Excel(name = "统计时间")
    private String statisticsTime;

    /**隶属店铺**/
    @Excel(name = "隶属店铺")
    private String underShop;

    /**营业额**/
    @Excel(name = "营业额")
    private Double money;

    private int timeUtil;

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public String getUnderShop() {
        return underShop;
    }

    public void setUnderShop(String underShop) {
        this.underShop = underShop;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(int timeUtil) {
        this.timeUtil = timeUtil;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("statisticsTime", getStatisticsTime())
                .append("underShop", getUnderShop())
                .append("money", getMoney())
                .append("timeUtil", getTimeUtil())
                .toString();
    }
}
