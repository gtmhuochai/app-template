package edu.jmu.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 权限实体类
 *
 * @author guantianmin
 * @date 2018/3/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * @Fields 权限名称，spring security 权限框架使用
     */
    private String name;

    /**
     * @Fields 权限显示名，用于界面按钮、菜单元素等显示
     */
    private String displayName;

    private List<User> users;

}