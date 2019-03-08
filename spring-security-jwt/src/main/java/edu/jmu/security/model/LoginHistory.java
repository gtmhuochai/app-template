package edu.jmu.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.jmu.jackson.InstantSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * 用户登录日志
 *
 * @author guantianmin
 * @date 2018/6/7 14:52
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long Id;
    private Long userId;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant loginTime;
    private String loginIp;
    private String remark;
    private User user;

}
