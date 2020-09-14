package com.weiliang.project.git.service.impl;

import com.weiliang.common.exception.CustomException;
import com.weiliang.common.utils.StringUtils;
import com.weiliang.project.git.domain.Purchase;
import com.weiliang.project.git.mapper.PurchaseMapper;
import com.weiliang.project.git.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购清单 服务层实现
 *
 */
@Service
public class PurchaseServiceImpl implements IPurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;

    /**
     * 查询采购清单列表
     *
     * @param purchase 采购清单信息
     * @return 采购清单集合
     */
    @Override
    public List<Purchase> selectPurchaseList(Purchase purchase) {
        return purchaseMapper.selectPurchaseList(purchase);
    }

    /**
     * 通过采购清单ID查询采购清单
     *
     * @param purchaseId 采购清单ID
     * @return 采购清单信息
     */
    @Override
    public Purchase selectPurchaseById(Long purchaseId) {
        return purchaseMapper.selectPurchaseById(purchaseId);
    }

    /**
     * 新增采购清单
     *
     * @param purchase 采购清单信息
     * @return 结果
     */
    @Override
    public int insertPurchase(Purchase purchase) {
        return purchaseMapper.insertPurchase(purchase);
    }

    /**
     * 修改采购清单
     *
     * @param purchase 采购清单信息
     * @return 结果
     */
    @Override
    public int updatePurchase(Purchase purchase) {
        return purchaseMapper.updatePurchase(purchase);
    }

    /**
     * 批量删除采购清单
     *
     * @param purchaseIds 需要删除的采购清单ID
     * @return 结果
     */
    @Override
    public int deletePurchaseByIds(Long[] purchaseIds) {
        return purchaseMapper.deletePurchaseByIds(purchaseIds);
    }

    /**
     * 导入采购清单数据
     *
     * @param purchaseList 采购清单数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importPurchase(List<Purchase> purchaseList, String operName)
    {
        if (StringUtils.isNull(purchaseList) || purchaseList.size() == 0)
        {
            throw new CustomException("导入采购清单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Purchase purchase : purchaseList)
        {
            try
            {
                purchase.setCreateBy(operName);
                this.insertPurchase(purchase);
                successNum++;
                successMsg.append("<br/>" + successNum + "、采购清单 " + purchase.getPurchaseName() + " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、采购清单 " + purchase.getPurchaseName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
