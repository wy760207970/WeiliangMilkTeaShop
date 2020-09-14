package com.weiliang.project.git.mapper;

import com.weiliang.project.git.domain.Stock;

import java.util.List;

/**
 * 库存 数据层
 */
public interface StockMapper {
    /**
     * 查询库存列表
     *
     * @param stock 库存信息
     * @return 库存集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 通过库存ID查询库存
     *
     * @param stockId 库存ID
     * @return 库存信息
     */
    public Stock selectStockById(Long stockId);

    /**
     * 新增库存
     *
     * @param stock 库存信息
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改库存
     *
     * @param stock 库存信息
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 批量删除库存
     *
     * @param stockIds 需要删除的库存ID
     * @return 结果
     */
    public int deleteStockByIds(Long[] stockIds);

}
