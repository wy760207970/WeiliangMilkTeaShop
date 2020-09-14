package com.weiliang.project.statistics.service.impl;

import com.weiliang.project.statistics.domain.Turnover;
import com.weiliang.project.statistics.mapper.TurnoverMapper;
import com.weiliang.project.statistics.service.ITurnoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoverServiceImpl implements ITurnoverService {
    @Autowired
    private TurnoverMapper turnoverMapper;

    @Override
    public List<Turnover> selectByDay(Turnover turnover) {
        return turnoverMapper.selectByDay(turnover);
    }

    @Override
    public List<Turnover> selectByWeek(Turnover turnover) {
        return turnoverMapper.selectByWeek(turnover);
    }

    @Override
    public List<Turnover> selectByMonth(Turnover turnover) {
        return turnoverMapper.selectByMonth(turnover);
    }
}
