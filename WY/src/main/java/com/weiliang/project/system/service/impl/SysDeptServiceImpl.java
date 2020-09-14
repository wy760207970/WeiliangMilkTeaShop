package com.weiliang.project.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiliang.common.constant.UserConstants;
import com.weiliang.common.utils.StringUtils;
import com.weiliang.framework.aspectj.lang.annotation.DataScope;
import com.weiliang.project.system.domain.SysDept;
import com.weiliang.project.system.mapper.SysDeptMapper;
import com.weiliang.project.system.service.ISysDeptService;

/**
 * 部门管理 服务实现
 *
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     *
     * @return 选中部门列表
     */
    @Override
    public List<SysDept> selectUseDeptList()
    {
        return deptMapper.selectUseDeptList();
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertDept(SysDept dept)
    {
        return deptMapper.insertDept(dept);
    }

    @Override
    public int updateDept(SysDept dept)
    {
        int result = deptMapper.updateDept(dept);
        return result;
    }

    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

}
