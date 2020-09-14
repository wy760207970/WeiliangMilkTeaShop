package com.weiliang.project.statistics.service;

import com.weiliang.project.statistics.domain.Turnover;

import java.util.List;

public interface ITurnoverService {
    public List<Turnover> selectByDay(Turnover turnover);

    public List<Turnover> selectByWeek(Turnover turnover);

    public List<Turnover> selectByMonth(Turnover turnover);
}
