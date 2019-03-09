package edu.jmu.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.jmu.security.JwtAuthenticationRequest;
import edu.jmu.security.JwtTokenUtil;
import edu.jmu.security.mapper.LoginHistoryMapper;
import edu.jmu.security.model.LoginHistory;
import edu.jmu.security.model.User;
import edu.jmu.security.service.UserService;
import edu.jmu.util.BasicResponse;
import edu.jmu.util.BusinessWrapper;
import edu.jmu.util.ExceptionUtil;
import edu.jmu.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Objects;

/**
 * @author guantianmin
 * @date 2018/3/17
 */
@RestController
@RequestMapping(value = "/security")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;


    /**
     * @api {post} /auth jwt生成接口
     * @apiName getJWt
     * @apiGroup authority
     * @apiParam {String} username 平台用户帐户
     * @apiParam {String} password 密码
     * @apiParamExample {json} Request_Example:
     * post: /orgz/companys
     * {
     * "name":"普瑞特",
     * "shortName":"prt"
     * }
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifSx7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6IlJPTEVfTUVOVV9PUkRFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9NRU5VX0NPTlNJR05NRU5UIn1dLCJleHAiOjE1MjcyOTQ2NDksImlhdCI6MTUyNzI1ODY0OX0.yBvHcbjXuyoFuDjJEFif332RhlRTyGsjJvnGS_87ZNvAwuhaq4sQTukjqmzHdMh2ulSOdC7nOzny3s5BpG-9Kg"}
     */
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public BasicResponse<String> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, Throwable {
        return BusinessWrapper.wrap(objectBasicResponse -> {
            String username = authenticationRequest.getUsername();
            Integer result = authenticate(username, authenticationRequest.getPassword(), response);
            // result为null，立即返回认证错误信息
            if (result != null) {
                Integer code = 201;
                String msg = "获取token失败";
                switch (result) {
                    case 0:
                        code = 202;
                        msg = "账号禁用";
                        break;
                    case 1:
                        code = 203;
                        msg = "账号密码错误";
                        break;
                    default:
                }
                ResponseUtil.set(objectBasicResponse, code, msg);
            } else {
                User user = userService.queryUser(username);
                // Reload password post-security so we can generate the token
                final String token = jwtTokenUtil.generateToken(user);
                //nginx反代，设置远程ip地址到请求头
                String fromSource = "X-Real-IP";
                String ip = request.getHeader(fromSource);
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setUserId(user.getId());
                loginHistory.setLoginTime(Instant.now());
                loginHistory.setLoginIp(ip);
                loginHistoryMapper.addLoginHistory(loginHistory);
                ResponseUtil.set(objectBasicResponse, 0, "获取token成功", token);
            }
        }, logger);
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public BasicResponse<String> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        return BusinessWrapper.wrap(response -> {
            String authToken = request.getHeader(tokenHeader);
            final String token = authToken.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.queryUser(username);
            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
                String refreshedToken;
                try {
                    refreshedToken = jwtTokenUtil.refreshToken(token, user);
                    ResponseUtil.set(response, 0, "获取token成功", refreshedToken);
                } catch (JsonProcessingException e) {
                    logger.error(ExceptionUtil.getStackTrace(e));
                }
            } else {
                ResponseUtil.set(response, 0, "获取token失败");
            }
        }, logger);

    }

    @PostMapping("/logout")
    public BasicResponse<Boolean> logout(HttpServletRequest request) {
        return BusinessWrapper.wrap(response -> {
            String authToken = request.getHeader(tokenHeader);
            final String token = authToken.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            userService.evictUserByUsername(username);
            ResponseUtil.set(response, 0, "注销成功", true);
        }, logger);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private Integer authenticate(String username, String password, HttpServletResponse response) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return null;
        } catch (DisabledException e) {
            return 0;
            //throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            return 1;
            //throw new AuthenticationException("Bad credentials!", e);
        }
    }

}
