package edu.jmu.security.mapper;


import edu.jmu.security.model.Authority;
import edu.jmu.security.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guantianmin
 * @Date 2018-06-02
 */
@Mapper
public interface RoleMapper {

    /**
     * 添加角色
     *
     * @param role 添加信息
     * @return 添加是否成功
     */
    Boolean addRole(Role role);

    /**
     * 添加角色的权限集定义
     *
     * @param authorities 角色的权限集
     * @param roleId      角色id
     * @return 返回添加成功与否
     */
    Boolean addRoleAuthorities(@Param(value = "authorities") List<Authority> authorities, @Param(value = "roleId") Long roleId);

    /**
     * 添加角色的权限定义
     *
     * @param authorityId 角色的权限id
     * @param roleId      角色id
     * @return 返回添加成功与否
     */
    Boolean addRoleAuthority(@Param(value = "authorityId") Long authorityId, @Param(value = "roleId") Long roleId);

    /**
     * 更新角色
     *
     * @param role 更新信息
     * @return 更新是否成功
     */
    Boolean updateRole(Role role);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 删除是否成功
     */
    Boolean deleteRole(Long id);

    /**
     * 批量删除角色
     *
     * @param ids 角色id数组
     * @return 删除是否成功
     */
    Boolean deleteBatchRole(@Param(value = "ids") Long[] ids);

    /**
     * 删除角色权限定义
     *
     * @param id 角色id
     * @return 返回删除是否成功
     */
    Boolean deleteRoleAuthorities(Long id);

    /**
     * 删除角色的某个权限
     *
     * @param authorityId 权限id
     * @param roleId      角色id
     * @return 返回删除是否成功
     */
    Boolean deleteRoleAuthority(@Param(value = "authorityId") Long authorityId, @Param(value = "roleId") Long roleId);

    /**
     * 批量删除角色权限定义
     *
     * @param ids 角色id数组
     * @return 返回删除是否成功
     */
    Boolean deleteBatchRoleAuthorities(@Param(value = "ids") Long[] ids);

    /**
     * 查询所有角色
     *
     * @param id 角色id
     * @return 返回角色列表
     */
    List<Role> queryRoleById(@Param(value = "id") Long id);
}
