package com.weiliang.project.git.mapper;

import com.weiliang.project.git.domain.StockDetail;

import java.util.List;

/**
 * 库存详情 数据层
 *
 */
public interface StockDetailMapper {

    /**
     * 根据库存ID查询库存详情
     *
     * @return 库存详情数据集合信息
     */
    public List<StockDetail> selectStockDetailByStockId(StockDetail stockDetail);

    /**
     * 根据库存详情ID查询信息
     *
     * @param stockDetailId 库存详情ID
     * @return 字典数据
     */
    public StockDetail selectStockDetailById(Long stockDetailId);

    /**
     * 批量删除库存详情信息
     *
     * @param stockDetailIds 需要删除的库存详情ID
     * @return 结果
     */
    public int deleteStockDetailByIds(Long[] stockDetailIds);

    /**
     * 新增库存详情信息
     *
     * @param stockDetail 库存详情信息
     * @return 结果
     */
    public int insertStockDetail(StockDetail stockDetail);

    /**
     * 修改库存详情信息
     *
     * @param stockDetail 库存详情信息
     * @return 结果
     */
    public int updateStockDetail(StockDetail stockDetail);

}
