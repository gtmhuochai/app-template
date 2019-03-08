package edu.jmu.security.mapper;

import edu.jmu.security.model.Role;
import edu.jmu.security.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息数据访问类
 *
 * @author guantianmin
 * @date 2018/3/17
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回用户信息
     */
    User findByUsername(@Param(value = "username") String username);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @param type 用户类型
     * @return 返回用户信息
     */
    User findByMobile(@Param(value = "mobile") String mobile, @Param("type") Integer type);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 返回新增成功与否
     */
    Boolean addUser(User user);

    /**
     * 添加用户的角色集定义
     *
     * @param roles  角色集
     * @param userId 用户id
     * @return 返回添加成功与否
     */
    Boolean addUserRoles(@Param(value = "roles") List<Role> roles, @Param(value = "userId") Long userId);

    /**
     * 添加用户角色记录
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 返回添加成功与否
     */
    Boolean addUserRole(@Param(value = "userId") Long userId, @Param(value = "roleId") Long roleId);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 返回公司用户列表
     */
    List<User> queryUserById(@Param(value = "id") Long id);

    /**
     * 根据用户id和类型查询用户
     * @param type
     * @return
     */
    List<User> queryUserByIdAndType(@Param("type") Integer type);

    /**
     * 根据用户id和类型查询用户
     * @param type
     * @return
     */
    List<User> queryUserByRequirement(@Param("type") Integer type, @Param("keyword") String username);

    /**
     * 查看是否重复
     * @param name
     * @return
     */
    Boolean checkUnique(@Param("item") String item, @Param("name") String name);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 返回更新成功与否
     */
    Boolean updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 返回删除是否成功
     */
    Boolean deleteUser(Long id);

    /**
     * 根据用户id删除用户角色表
     *
     * @param userId 用户id
     * @return 返回删除成功与否
     */
    Boolean deleteUserRoles(Long userId);

    /**
     * 删除指定用户的某个角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 返回删除是否成功
     */
    Boolean deleteUserRole(@Param(value = "userId") Long userId, @Param(value = "roleId") Long roleId);

    /**
     * 单个查询用户
     * @param id
     * @return
     */
    User findById(@Param("id") Long id);

}
