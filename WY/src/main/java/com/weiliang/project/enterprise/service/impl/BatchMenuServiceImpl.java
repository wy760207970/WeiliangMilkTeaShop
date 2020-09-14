package com.weiliang.project.enterprise.service.impl;

import com.weiliang.project.enterprise.domain.BatchMenu;
import com.weiliang.project.enterprise.mapper.BatchMenuMapper;
import com.weiliang.project.enterprise.service.IBatchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配料菜单 服务层实现
 *
 */
@Service
public class BatchMenuServiceImpl implements IBatchMenuService {
    @Autowired
    private BatchMenuMapper batchMenuMapper;

    /**
     * 查询配料菜单列表
     *
     * @param menu 配料菜单信息
     * @return 配料菜单集合
     */
    @Override
    public List<BatchMenu> selectMenuList(BatchMenu menu) {
        return batchMenuMapper.selectMenuList(menu);
    }

    /**
     * 通过配料菜单ID查询配料菜单
     *
     * @param batchId 配料菜单ID
     * @return 配料菜单信息
     */
    @Override
    public BatchMenu selectMenuById(Long batchId) {
        return batchMenuMapper.selectMenuById(batchId);
    }

    /**
     * 新增配料菜单
     *
     * @param menu 配料菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(BatchMenu menu) {
        return batchMenuMapper.insertMenu(menu);
    }

    /**
     * 修改配料菜单
     *
     * @param menu 配料菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(BatchMenu menu) {
        return batchMenuMapper.updateMenu(menu);
    }

    /**
     * 批量删除配料菜单
     *
     * @param batchIds 需要删除的配料菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuByIds(Long[] batchIds) {
        return batchMenuMapper.deleteMenuByIds(batchIds);
    }
}
