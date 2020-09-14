package com.weiliang.project.git.service.impl;

import com.weiliang.project.git.domain.Stock;
import com.weiliang.project.git.mapper.StockMapper;
import com.weiliang.project.git.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存 服务层实现
 *
 */
@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询库存列表
     *
     * @param stock 库存信息
     * @return 库存集合
     */
    @Override
    public List<Stock> selectStockList(Stock stock) {
        return stockMapper.selectStockList(stock);
    }

    /**
     * 通过库存ID查询库存
     *
     * @param stockId 库存ID
     * @return 库存信息
     */
    @Override
    public Stock selectStockById(Long stockId) {
        return stockMapper.selectStockById(stockId);
    }

    /**
     * 新增库存
     *
     * @param stock 库存信息
     * @return 结果
     */
    @Override
    public int insertStock(Stock stock) {
        return stockMapper.insertStock(stock);
    }

    /**
     * 修改库存
     *
     * @param stock 库存信息
     * @return 结果
     */
    @Override
    public int updateStock(Stock stock) {
        return stockMapper.updateStock(stock);
    }

    /**
     * 批量删除库存
     *
     * @param stockIds 需要删除的库存ID
     * @return 结果
     */
    @Override
    public int deleteStockByIds(Long[] stockIds) {
        return stockMapper.deleteStockByIds(stockIds);
    }
}
