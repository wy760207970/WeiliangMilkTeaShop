package com.weiliang.project.statistics.mapper;

import com.weiliang.project.statistics.domain.Turnover;

import java.util.List;

/**
 * 营业额 数据层
 */
public interface TurnoverMapper {
    public List<Turnover> selectByDay(Turnover turnover);

    public List<Turnover> selectByWeek(Turnover turnover);

    public List<Turnover> selectByMonth(Turnover turnover);
}
