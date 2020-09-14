package com.weiliang.project.enterprise.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.enterprise.domain.BatchMenu;
import com.weiliang.project.enterprise.service.IBatchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配料菜单 信息操作处理
 *
 */
@RestController
@RequestMapping("/enterprise/batchMenu")
public class BatchMenuController extends BaseController {
    @Autowired
    private IBatchMenuService menuService;

    /**
     * 获取配料菜单列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise:batchMenu:list')")
    @GetMapping("/list")
    public TableDataInfo list(BatchMenu menu)
    {
        startPage();
        List<BatchMenu> list = menuService.selectMenuList(menu);
        return getDataTable(list);
    }

    /**
     * 根据配料菜单编号获取配料菜单信息
     */
    @PreAuthorize("@ss.hasPermi('enterprise:batchMenu:query')")
    @GetMapping(value = { "/", "/{batchId}" })
    public AjaxResult getInfo(@PathVariable Long batchId)
    {
        return AjaxResult.success(menuService.selectMenuById(batchId));
    }

    /**
     * 新增配料菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:batchMenu:add')")
    @Log(title = "配料菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BatchMenu menu)
    {
        menu.setCreateBy(SecurityUtils.getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改配料菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:batchMenu:edit')")
    @Log(title = "配料菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BatchMenu menu)
    {
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除配料菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:batchMenu:remove')")
    @Log(title = "配料菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable Long[] batchIds)
    {
        return toAjax(menuService.deleteMenuByIds(batchIds));
    }

}
