package com.weiliang.project.git.service;

import com.weiliang.project.git.domain.Purchase;

import java.util.List;

/**
 * 采购清单 服务层
 */
public interface IPurchaseService {
    /**
     * 查询采购清单列表
     *
     * @param purchase 采购清单信息
     * @return 采购清单集合
     */
    public List<Purchase> selectPurchaseList(Purchase purchase);

    /**
     * 通过采购清单ID查询采购清单信息
     *
     * @param purchaseId 采购清单ID
     * @return 采购清单信息
     */
    public Purchase selectPurchaseById(Long purchaseId);

    /**
     * 新增采购清单
     *
     * @param purchase 采购清单信息
     * @return 结果
     */
    public int insertPurchase(Purchase purchase);

    /**
     * 修改采购清单
     *
     * @param purchase 采购清单信息
     * @return 结果
     */
    public int updatePurchase(Purchase purchase);

    /**
     * 批量删除采购清单
     *
     * @param purchaseIds 需要删除的采购清单ID
     * @return 结果
     */
    public int deletePurchaseByIds(Long[] purchaseIds);

    /**
     * 导入采购清单数据
     *
     * @param purchaseList 采购清单列表
     * @param operName 操作用户
     * @return 结果
     */
    public String importPurchase(List<Purchase> purchaseList, String operName);
}
