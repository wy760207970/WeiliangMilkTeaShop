package com.weiliang.project.enterprise.service.impl;

import com.weiliang.common.exception.CustomException;
import com.weiliang.common.utils.StringUtils;
import com.weiliang.project.enterprise.domain.DrinkMenu;
import com.weiliang.project.enterprise.mapper.DrinkMenuMapper;
import com.weiliang.project.enterprise.service.IDrinkMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 饮品菜单 服务层实现
 *
 */
@Service
public class DrinkMenuServiceImpl implements IDrinkMenuService {
    @Autowired
    private DrinkMenuMapper drinkMenuMapper;

    /**
     * 查询饮品菜单列表
     *
     * @param menu 饮品菜单信息
     * @return 饮品菜单集合
     */
    @Override
    public List<DrinkMenu> selectMenuList(DrinkMenu menu) {
        return drinkMenuMapper.selectMenuList(menu);
    }

    /**
     * 通过饮品菜单ID查询饮品菜单
     *
     * @param menuId 饮品菜单ID
     * @return 饮品菜单信息
     */
    @Override
    public DrinkMenu selectMenuById(Long menuId) {
        return drinkMenuMapper.selectMenuById(menuId);
    }

    /**
     * 新增饮品菜单
     *
     * @param menu 饮品菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(DrinkMenu menu) {
        return drinkMenuMapper.insertMenu(menu);
    }

    /**
     * 修改饮品菜单
     *
     * @param menu 饮品菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(DrinkMenu menu) {
        return drinkMenuMapper.updateMenu(menu);
    }

    /**
     * 批量删除饮品菜单
     *
     * @param menuIds 需要删除的饮品菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuByIds(Long[] menuIds) {
        return drinkMenuMapper.deleteMenuByIds(menuIds);
    }

    /**
     * 导入饮品菜单数据
     *
     * @param menuList 饮品菜单数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importMenu(List<DrinkMenu> menuList, String operName)
    {
        if (StringUtils.isNull(menuList) || menuList.size() == 0)
        {
            throw new CustomException("导入饮品菜单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (DrinkMenu menu : menuList)
        {
            try
            {
                menu.setCreateBy(operName);
                this.insertMenu(menu);
                successNum++;
                successMsg.append("<br/>" + successNum + "、饮品菜单 " + menu.getMenuName() + " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、饮品菜单 " + menu.getMenuName() + " 导入失败：";
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
