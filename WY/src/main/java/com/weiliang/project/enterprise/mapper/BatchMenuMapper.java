package com.weiliang.project.enterprise.mapper;

import com.weiliang.project.enterprise.domain.BatchMenu;

import java.util.List;

/**
 * 配料菜单 数据层
 */
public interface BatchMenuMapper {
    /**
     * 查询配料菜单列表
     *
     * @param menu 配料菜单信息
     * @return 配料菜单集合
     */
    public List<BatchMenu> selectMenuList(BatchMenu menu);

    /**
     * 通过配料菜单ID查询配料菜单
     *
     * @param batchId 配料菜单ID
     * @return 配料菜单信息
     */
    public BatchMenu selectMenuById(Long batchId);

    /**
     * 新增配料菜单
     *
     * @param menu 配料菜单信息
     * @return 结果
     */
    public int insertMenu(BatchMenu menu);

    /**
     * 修改配料菜单
     *
     * @param menu 配料菜单信息
     * @return 结果
     */
    public int updateMenu(BatchMenu menu);

    /**
     * 批量删除配料菜单
     *
     * @param menuIds 需要删除的配料菜单ID
     * @return 结果
     */
    public int deleteMenuByIds(Long[] batchIds);

}
