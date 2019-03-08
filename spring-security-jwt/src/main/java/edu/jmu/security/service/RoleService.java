package edu.jmu.security.service;

import edu.jmu.security.mapper.AuthorityMapper;
import edu.jmu.security.mapper.RoleMapper;
import edu.jmu.security.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 角色服务类
 *
 * @author guantianmin
 * @date 2018/6/6 15:53
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 删除角色和角色的权限定义
     *
     * @param id 角色id
     * @return 返回删除是否成功
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean deleteRole(Long id) {
        //删除角色下的权限集
        roleMapper.deleteRoleAuthorities(id);
        //删除角色
        Boolean result = roleMapper.deleteRole(id);
        return result;
    }

    /**
     * 批量删除角色和角色的权限定义
     *
     * @param ids 角色id数组
     * @return 返回删除是否成功
     */
    public Boolean deleteRoles(Long[] ids) {
        if (ids != null && ids.length > 0) {
            roleMapper.deleteBatchRoleAuthorities(ids);
            Boolean result = roleMapper.deleteBatchRole(ids);

            return result;
        } else {
            return false;
        }
    }

}
