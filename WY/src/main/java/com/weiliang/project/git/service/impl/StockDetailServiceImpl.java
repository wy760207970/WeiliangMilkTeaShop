package com.weiliang.project.git.service.impl;

import com.weiliang.project.git.domain.StockDetail;
import com.weiliang.project.git.mapper.StockDetailMapper;
import com.weiliang.project.git.service.IStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDetailServiceImpl implements IStockDetailService {
    @Autowired
    private StockDetailMapper stockDetailMapper;

    @Override
    public List<StockDetail> selectStockDetailByStockId(StockDetail stockDetail) {
        return stockDetailMapper.selectStockDetailByStockId(stockDetail);
    }

    @Override
    public StockDetail selectStockDetailById(Long stockDetailId) {
        return stockDetailMapper.selectStockDetailById(stockDetailId);
    }

    @Override
    public int deleteStockDetailByIds(Long[] stockDetailIds) {
        return stockDetailMapper.deleteStockDetailByIds(stockDetailIds);
    }

    @Override
    public int insertStockDetail(StockDetail stockDetail) {
        return stockDetailMapper.insertStockDetail(stockDetail);
    }

    @Override
    public int updateStockDetail(StockDetail stockDetail) {
        return stockDetailMapper.updateStockDetail(stockDetail);
    }
}
