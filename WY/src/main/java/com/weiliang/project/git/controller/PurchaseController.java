package com.weiliang.project.git.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.common.utils.poi.ExcelUtil;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.enterprise.domain.DrinkMenu;
import com.weiliang.project.git.domain.Purchase;
import com.weiliang.project.git.service.IPurchaseService;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.domain.SysUser;
import com.weiliang.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 采购清单 信息操作处理
 *
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {
    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private ISysUserService userService;
    /**
     * 获取采购清单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(Purchase purchase)
    {
        if (!SecurityUtils.getUsername().equals("admin")){
            SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
            SysDept sysDept = sysUser.getDept();
            purchase.setUnderShop(sysDept.getDeptName());
        }
        startPage();
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        return getDataTable(list);
    }

    /**
     * 根据采购清单编号获取采购清单
     */
    @PreAuthorize("@ss.hasPermi('purchase:query')")
    @GetMapping(value = { "/", "/{purchaseId}" })
    public AjaxResult getInfo(@PathVariable Long purchaseId)
    {
        return AjaxResult.success(purchaseService.selectPurchaseById(purchaseId));
    }


    @Log(title = "采购清单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('purchase:export')")
    @GetMapping("/export")
    public AjaxResult export(Purchase purchase)
    {
        if (!SecurityUtils.getUsername().equals("admin")){
            SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
            SysDept sysDept = sysUser.getDept();
            purchase.setUnderShop(sysDept.getDeptName());
        }
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        return util.exportExcel(list, "采购数据");
    }

    /**
     * 新增采购清单
     */
    @PreAuthorize("@ss.hasPermi('purchase:add')")
    @Log(title = "采购清单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Purchase purchase)
    {
        SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
        SysDept sysDept = sysUser.getDept();
        if (sysDept!=null){
            purchase.setUnderShop(sysDept.getDeptName());
        }
        purchase.setCreateBy(SecurityUtils.getUsername());
        return toAjax(purchaseService.insertPurchase(purchase));
    }

    /**
     * 修改采购清单
     */
    @PreAuthorize("@ss.hasPermi('purchase:edit')")
    @Log(title = "采购清单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Purchase purchase)
    {
        SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
        SysDept sysDept = sysUser.getDept();
        if (sysDept!=null){
            purchase.setUnderShop(sysDept.getDeptName());
        }
        purchase.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(purchaseService.updatePurchase(purchase));
    }

    /**
     * 删除采购清单
     */
    @PreAuthorize("@ss.hasPermi('purchase:remove')")
    @Log(title = "采购清单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{purchaseIds}")
    public AjaxResult remove(@PathVariable Long[] purchaseIds)
    {
        return toAjax(purchaseService.deletePurchaseByIds(purchaseIds));
    }

    @Log(title = "采购清单管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('purchase:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        List<Purchase> purchaseList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = purchaseService.importPurchase(purchaseList, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        return util.importTemplateExcel("采购清单数据");
    }

}
