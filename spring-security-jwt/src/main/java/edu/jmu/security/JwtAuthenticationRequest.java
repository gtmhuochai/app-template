package edu.jmu.security;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Jwt认证请求对象，保存用户Post提交的账号密码
 *
 * @author guantianmin
 * @date 2018/3/15
 */
@Data
@ApiModel(value = "JwtAuthenticationRequest", description = "用于token生成")
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
