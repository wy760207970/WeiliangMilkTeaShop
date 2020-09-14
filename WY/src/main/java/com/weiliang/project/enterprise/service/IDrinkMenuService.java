package com.weiliang.project.enterprise.service;

import com.weiliang.project.enterprise.domain.DrinkMenu;

import java.util.List;

/**
 * 饮品菜单 服务层
 */
public interface IDrinkMenuService {
    /**
     * 查询饮品菜单列表
     *
     * @param menu 饮品菜单信息
     * @return 饮品菜单集合
     */
    public List<DrinkMenu> selectMenuList(DrinkMenu menu);

    /**
     * 通过饮品菜单ID查询饮品菜单
     *
     * @param menuId 饮品菜单ID
     * @return 饮品菜单信息
     */
    public DrinkMenu selectMenuById(Long menuId);

    /**
     * 新增饮品菜单
     *
     * @param menu 饮品菜单信息
     * @return 结果
     */
    public int insertMenu(DrinkMenu menu);

    /**
     * 修改饮品菜单
     *
     * @param menu 饮品菜单信息
     * @return 结果
     */
    public int updateMenu(DrinkMenu menu);

    /**
     * 批量删除饮品菜单
     *
     * @param menuIds 需要删除的饮品菜单ID
     * @return 结果
     */
    public int deleteMenuByIds(Long[] menuIds);

    /**
     * 导入饮品菜单数据
     *
     * @param menuList 饮品菜单列表
     * @param operName 操作用户
     * @return 结果
     */
    public String importMenu(List<DrinkMenu> menuList, String operName);
}
