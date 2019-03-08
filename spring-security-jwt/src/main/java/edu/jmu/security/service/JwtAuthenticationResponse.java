package edu.jmu.security.service;

import java.io.Serializable;

/**
 *
 * @author guantianmin
 * @date 2018/3/17
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
