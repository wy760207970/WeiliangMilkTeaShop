package com.weiliang.project.git.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.common.utils.poi.ExcelUtil;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.git.domain.Stock;
import com.weiliang.project.git.service.IStockService;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.domain.SysUser;
import com.weiliang.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存 信息操作处理
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {
    @Autowired
    private IStockService stockService;

    @Autowired
    private ISysUserService userService;
    /**
     * 获取库存列表
     */
    @PreAuthorize("@ss.hasPermi('stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(Stock stock)
    {
        if (!SecurityUtils.getUsername().equals("admin")){
            SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
            SysDept sysDept = sysUser.getDept();
            stock.setUnderShop(sysDept.getDeptName());
        }
        startPage();
        List<Stock> list = stockService.selectStockList(stock);
        return getDataTable(list);
    }

    /**
     * 根据库存编号获取库存信息
     */
    @PreAuthorize("@ss.hasPermi('stock:query')")
    @GetMapping(value = { "/", "/{stockId}" })
    public AjaxResult getInfo(@PathVariable Long stockId)
    {
        return AjaxResult.success(stockService.selectStockById(stockId));
    }


    @Log(title = "库存管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('stock:export')")
    @GetMapping("/export")
    public AjaxResult export(Stock stock)
    {
        List<Stock> list = stockService.selectStockList(stock);
        ExcelUtil<Stock> util = new ExcelUtil<Stock>(Stock.class);
        return util.exportExcel(list, "库存数据");
    }

    /**
     * 新增库存
     */
    @PreAuthorize("@ss.hasPermi('stock:add')")
    @Log(title = "库存管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Stock stock)
    {
        SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
        SysDept sysDept = sysUser.getDept();
        if (sysDept!=null){
            stock.setUnderShop(sysDept.getDeptName());
        }
        stock.setCreateBy(SecurityUtils.getUsername());
        return toAjax(stockService.insertStock(stock));
    }

    /**
     * 修改库存
     */
    @PreAuthorize("@ss.hasPermi('stock:edit')")
    @Log(title = "库存管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Stock stock)
    {
        SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
        SysDept sysDept = sysUser.getDept();
        if (sysDept!=null){
            stock.setUnderShop(sysDept.getDeptName());
        }
        stock.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(stockService.updateStock(stock));
    }

    /**
     * 删除库存
     */
    @PreAuthorize("@ss.hasPermi('stock:remove')")
    @Log(title = "库存管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{stockIds}")
    public AjaxResult remove(@PathVariable Long[] stockIds)
    {
        return toAjax(stockService.deleteStockByIds(stockIds));
    }

}
