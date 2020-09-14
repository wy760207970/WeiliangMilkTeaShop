package com.weiliang.project.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.weiliang.common.constant.UserConstants;
import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.service.ISysDeptService;

/**
 * 店铺信息
 *
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    /**
 * 获取店铺列表
 */
@PreAuthorize("@ss.hasPermi('system:dept:list')")
@GetMapping("/list")
public AjaxResult list(SysDept dept)
{
    List<SysDept> depts = deptService.selectDeptList(dept);
    return AjaxResult.success(depts);
}

    /**
     * 获取使用店铺列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:useList')")
    @GetMapping("/useList")
    public AjaxResult useList()
    {
        List<SysDept> depts = deptService.selectUseDeptList();
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增店铺
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "店铺管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return AjaxResult.error("店铺部门'" + dept.getDeptName() + "'失败，店铺名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改店铺
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "店铺管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除店铺
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "店铺管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        if (deptService.checkDeptExistUser(deptId))
        {
            return AjaxResult.error("店铺存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
