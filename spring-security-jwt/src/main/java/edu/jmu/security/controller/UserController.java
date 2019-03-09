package edu.jmu.security.controller;

import edu.jmu.security.JwtTokenUtil;
import edu.jmu.security.mapper.UserMapper;
import edu.jmu.security.model.User;
import edu.jmu.security.service.UserService;
import edu.jmu.util.BasicResponse;
import edu.jmu.util.BusinessWrapper;
import edu.jmu.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 *
 * @author guantianmin
 * @date 2019/3/6 14:40
 */
@RestController
@RequestMapping(value = "/security")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/user")
    public BasicResponse<User> getUserInfo(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return BusinessWrapper.wrap(response -> {
            User user = userService.queryUser(username);
            ResponseUtil.set(response, 0, "查询用户信息成功", user);
        }, logger);
    }
}
