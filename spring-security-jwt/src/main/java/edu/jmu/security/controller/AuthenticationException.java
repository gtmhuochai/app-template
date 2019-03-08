package edu.jmu.security.controller;

/**
 * @author guantianmin
 * @date 2018/3/17
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
