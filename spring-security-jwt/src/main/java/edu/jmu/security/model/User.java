package edu.jmu.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.jmu.jackson.InstantSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 用户
 *
 * @author guantianmin
 * @date 2018/3/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer userType;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String realName;

    private String nickname;

    private Instant birthday;

    private Integer sex;

    private String email;

    private String url;

    private String mobile;

    private String avatar;

    private String signature;

    private Boolean enabled;

    @JsonSerialize(using = InstantSerializer.class)
    private Instant lastPasswordResetDate;

    private String loginName;

    @JsonSerialize(using = InstantSerializer.class)
    private Instant registerTime;

    private String disabledReason;

    private String lastLoginIp;

    private Integer status;

    private List<Role> roles;

    private String rolesString;

    private List<Authority> authorities;

}