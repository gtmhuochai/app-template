package edu.jmu.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色实体类
 *
 * @author guantianmin
 * @date 2018/6/2 9:47
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Boolean enabled;
    private String displayName;
    private List<Authority> authorityList;
}
