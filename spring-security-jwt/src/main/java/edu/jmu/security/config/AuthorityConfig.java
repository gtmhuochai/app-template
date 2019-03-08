package edu.jmu.security.config;

import edu.jmu.security.JwtAuthenticationEntryPoint;
import edu.jmu.security.JwtTokenUtil;
import edu.jmu.security.service.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 权限bean实例配置
 *
 * @author guantianmin
 * @date 2018/5/24 16:27
 */
@Configuration
public class AuthorityConfig {

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean(value = "jwtUserDetailsService")
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

}
