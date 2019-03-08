package edu.jmu.security.service;

import edu.jmu.security.mapper.UserMapper;
import edu.jmu.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * 用户缓存实现类
 *
 * @author guantianmin
 * @date 2018/6/15 15:53
 */
@Service
public class UserCacheService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @CachePut(value = "usersById", key = "T(String).valueOf(#user.id)")
    public User putUserIntoCacheById(User user) {
        return user;
    }

    /**
     * 更新用户信息，根据key(companyId-username)删除usersByUsername缓存空间中的值
     *
     * @param user
     * @return
     */
    public Boolean updateUser(User user) {
        //删除缓存
        evictCache(user.getId());
        Boolean result = userMapper.updateUser(user);
        return result;
    }

    /**
     * 添加用户权限，根据key(companyId-username)删除usersByUsername缓存空间中的值
     * @param userId
     * @param roleId
     * @return
     */
    public Boolean addUserRole(Long userId,Long roleId){
        evictCache(userId);
        return userMapper.addUserRole(userId,roleId);
    }

    /**
     * 删除用户权限，根据key(companyId-username)删除usersByUsername缓存空间中的值
     * @param userId
     * @param roleId
     * @return
     */
    public Boolean deleteUserRole(Long userId, Long roleId) {
        evictCache(userId);
        return userMapper.deleteUserRole(userId,roleId);
    }

    /**
     * 删除用户信息，根据key(companyId-username)删除usersByUsername缓存空间中的值
     *
     * @param userId
     * @return
     */
    public Boolean deleteUser(Long userId) {
        evictCache(userId);
        //删除用户
        Boolean result = userMapper.deleteUser(userId);
        //删除用户已分配的角色
        userMapper.deleteUserRoles(userId);
        return result;
    }

    private void evictCache(Long userId) {
        //删除缓存
        if (userId != null) {
            User userFromCache = userService.getUserFromCacheById(userId);
            if (userFromCache != null) {
                userService.evictUserByUsername(userFromCache.getUsername());
            }
        }
    }
}
