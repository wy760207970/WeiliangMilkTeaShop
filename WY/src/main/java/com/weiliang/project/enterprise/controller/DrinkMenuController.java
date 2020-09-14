package com.weiliang.project.enterprise.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.common.utils.poi.ExcelUtil;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.enterprise.domain.DrinkMenu;
import com.weiliang.project.enterprise.service.IDrinkMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 饮品菜单 信息操作处理
 *
 */
@RestController
@RequestMapping("/enterprise/drinkMenu")
public class DrinkMenuController extends BaseController {
    @Autowired
    private IDrinkMenuService menuService;

    /**
     * 获取饮品菜单列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrinkMenu menu)
    {
        startPage();
        List<DrinkMenu> list = menuService.selectMenuList(menu);
        return getDataTable(list);
    }

    /**
     * 根据饮品菜单编号获取饮品菜单信息
     */
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:query')")
    @GetMapping(value = { "/", "/{menuId}" })
    public AjaxResult getInfo(@PathVariable Long menuId)
    {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }


    @Log(title = "饮品菜单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:export')")
    @GetMapping("/export")
    public AjaxResult export(DrinkMenu menu)
    {
        List<DrinkMenu> list = menuService.selectMenuList(menu);
        ExcelUtil<DrinkMenu> util = new ExcelUtil<DrinkMenu>(DrinkMenu.class);
        return util.exportExcel(list, "饮品菜单数据");
    }

    /**
     * 新增饮品菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:add')")
    @Log(title = "饮品菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DrinkMenu menu)
    {
        menu.setCreateBy(SecurityUtils.getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改饮品菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:edit')")
    @Log(title = "饮品菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DrinkMenu menu)
    {
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除饮品菜单
     */
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:remove')")
    @Log(title = "饮品菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuIds}")
    public AjaxResult remove(@PathVariable Long[] menuIds)
    {
        return toAjax(menuService.deleteMenuByIds(menuIds));
    }

    @Log(title = "饮品菜单管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('enterprise:drinkMenu:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<DrinkMenu> util = new ExcelUtil<DrinkMenu>(DrinkMenu.class);
        List<DrinkMenu> menuList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = menuService.importMenu(menuList, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<DrinkMenu> util = new ExcelUtil<DrinkMenu>(DrinkMenu.class);
        return util.importTemplateExcel("饮品菜单数据");
    }
}
