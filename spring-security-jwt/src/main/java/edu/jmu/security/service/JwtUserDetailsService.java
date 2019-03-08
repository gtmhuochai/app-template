package edu.jmu.security.service;

import edu.jmu.security.JwtUserFactory;
import edu.jmu.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Jwt用户信息服务类
 *
 * @author guantianmin
 * @date 2018/3/17
 */
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.queryUser(username);
        if (user == null) {
            //查找为空，则清除缓存
            userService.evictUserByUsername(username);
            throw new UsernameNotFoundException(String.format("No userMapper found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
