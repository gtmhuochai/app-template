package edu.jmu.security.service;

import edu.jmu.security.mapper.UserMapper;
import edu.jmu.security.model.Role;
import edu.jmu.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理服务类
 *
 * @author guantianmin
 * @date 2018/6/11 15:30
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCacheService userCacheService;

    public Boolean addUser(User user) {
        return userMapper.addUser(user);
    }

    public Boolean updateUser(User user) {
        return userCacheService.updateUser(user);
    }

    /**
     * 添加用户权限
     * @param userId
     * @param roleId
     * @return
     */
    public Boolean addUserRole(Long userId,Long roleId){
        return userCacheService.addUserRole(userId,roleId);
    }

    /**
     * 删除用户权限
     * @param userId
     * @param roleId
     * @return
     */
    public Boolean deleteUserRole(Long userId,Long roleId){
        return userCacheService.deleteUserRole(userId,roleId);
    }

    /**
     * 根据用户名和公司id查询用户信息，并缓存到usersByUsername和usersById两个空间
     *
     * @param username
     * @return
     */
    @Cacheable(value = "usersByUsername", key = "#username")
    public User queryUser(String username) {
        User user = this.userMapper.findByUsername(username);
        if (user != null) {
            userCacheService.putUserIntoCacheById(user);
        }
        return user;
    }

    /**
     * 从usersById缓存空间中获取用户信息
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "usersById", key = "T(String).valueOf(#userId)")
    public User getUserFromCacheById(Long userId) {
        return null;
    }

    /**
     * 根据key(companyId-username)删除usersByUsername缓存空间中缓存值
     *
     * @param username
     */
    @CacheEvict(value = "usersByUsername", key = "#username")
    public void evictUserByUsername(String username) {
        return;
    }

    public List<User> queryUserById(Long id) {
        List<User> users = userMapper.queryUserById(id);
        for (User user : users) {
            String rolesName = concatRoleName(user.getRoles());
            user.setRolesString(rolesName);
        }
        return users;
    }

    /**
     * 删除用户以及用户角色
     *
     * @param userId 用户id
     * @return 返回删除是否成
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean deleteUser(Long userId) {
        return userCacheService.deleteUser(userId);
    }

    private String concatRoleName(List<Role> roles) {
        String rolesName = "";
        if (roles != null) {
            int length = roles.size();
            int i = 0;
            for (Role role : roles) {
                i++;
                String roleName = role.getName();
                if (roleName != null) {
                    rolesName = rolesName.concat(roleName);
                }
                if (i != length) {
                    rolesName = rolesName.concat(",");
                }
            }
        }
        return rolesName.equals("") ? null : rolesName;
    }

    /**
     * 根据手机号查找用户
     * @param mobile 手机号
     * @return 用户信息
     */
    public User findByMobileAndType(String mobile,Integer type){
        return userMapper.findByMobile(mobile,type);
    }

}
