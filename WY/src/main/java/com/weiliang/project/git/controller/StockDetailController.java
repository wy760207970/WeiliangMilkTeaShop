package com.weiliang.project.git.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.git.domain.StockDetail;
import com.weiliang.project.git.service.IStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存详情 信息操作处理
 *
 */
@RestController
@RequestMapping("/stockDetail")
public class StockDetailController extends BaseController {
    @Autowired
    private IStockDetailService stockDetailService;

    /**
     * 获取库存详情列表
     */
    @PreAuthorize("@ss.hasPermi('stockDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockDetail stockDetail)
    {
        startPage();
        List<StockDetail> list = stockDetailService.selectStockDetailByStockId(stockDetail);
        return getDataTable(list);
    }

    /**
     * 根据库存详情编号获取库存详情信息
     */
    @PreAuthorize("@ss.hasPermi('stockDetail:query')")
    @GetMapping(value = { "/", "/{stockDetailId}" })
    public AjaxResult getInfo(@PathVariable Long stockDetailId)
    {
        return AjaxResult.success(stockDetailService.selectStockDetailById(stockDetailId));
    }

    /**
     * 新增库存详情
     */
    @PreAuthorize("@ss.hasPermi('stockDetail:add')")
    @Log(title = "库存详情管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StockDetail stock)
    {
        stock.setCreateBy(SecurityUtils.getUsername());
        return toAjax(stockDetailService.insertStockDetail(stock));
    }

    /**
     * 修改库存详情
     */
    @PreAuthorize("@ss.hasPermi('stockDetail:edit')")
    @Log(title = "库存详情管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StockDetail stock)
    {
        stock.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(stockDetailService.updateStockDetail(stock));
    }

    /**
     * 删除库存详情
     */
    @PreAuthorize("@ss.hasPermi('stockDetail:remove')")
    @Log(title = "库存详情管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{stockDetailIds}")
    public AjaxResult remove(@PathVariable Long[] stockDetailIds)
    {
        return toAjax(stockDetailService.deleteStockDetailByIds(stockDetailIds));
    }

}
