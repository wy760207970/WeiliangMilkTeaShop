package com.weiliang.project.statistics.controller;

import com.weiliang.common.utils.SecurityUtils;
import com.weiliang.common.utils.poi.ExcelUtil;
import com.weiliang.framework.aspectj.lang.annotation.Log;
import com.weiliang.framework.aspectj.lang.enums.BusinessType;
import com.weiliang.framework.web.controller.BaseController;
import com.weiliang.framework.web.domain.AjaxResult;
import com.weiliang.framework.web.page.TableDataInfo;
import com.weiliang.project.git.domain.Purchase;
import com.weiliang.project.statistics.domain.Turnover;
import com.weiliang.project.statistics.service.ITurnoverService;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.domain.SysUser;
import com.weiliang.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 库存 信息操作处理
 *
 */
@RestController
@RequestMapping("/turnover")
public class TurnoverController extends BaseController {
    @Autowired
    private ITurnoverService turnoverService;

    @Autowired
    private ISysUserService userService;
    /**
     * 获取营业额列表
     */
    @PreAuthorize("@ss.hasPermi('turnover:list')")
    @GetMapping("/list")
    public TableDataInfo list(Turnover turnover)
    {
        if (!SecurityUtils.getUsername().equals("admin")){
            SysUser sysUser = userService.selectUserByUserName(SecurityUtils.getUsername());
            SysDept sysDept = sysUser.getDept();
            turnover.setUnderShop(sysDept.getDeptName());
        }
        startPage();
        List<Turnover> list = null;
        List<Turnover> list1 = null;
        if (turnover.getTimeUtil() == 3){
            list = turnoverService.selectByMonth(turnover);
            list1 = turnoverService.selectByMonth(turnover);
        }else if (turnover.getTimeUtil() == 2){
           list = turnoverService.selectByWeek(turnover);
            list1 = turnoverService.selectByWeek(turnover);
        }else {
            list = turnoverService.selectByDay(turnover);
            list1 = turnoverService.selectByDay(turnover);
        }
        Collections.reverse(list1); // 倒序排列
        return getDataTable(list,list1);
    }

    @Log(title = "营业额管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('turnover:export')")
    @GetMapping("/export")
    public AjaxResult export(Turnover turnover)
    {
        List<Turnover> list = null;
        if (turnover.getTimeUtil() == 3){
            list = turnoverService.selectByMonth(turnover);
        }else if (turnover.getTimeUtil() == 2){
            list = turnoverService.selectByWeek(turnover);
        }else {
            list = turnoverService.selectByDay(turnover);
        }
        ExcelUtil<Turnover> util = new ExcelUtil<Turnover>(Turnover.class);
        return util.exportExcel(list, "营业额数据");
    }

}
