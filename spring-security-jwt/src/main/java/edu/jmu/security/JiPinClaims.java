package edu.jmu.security;

import io.jsonwebtoken.impl.DefaultClaims;

/**
 * jwt Claims类
 *
 * @author guantianmin
 * @date 2018/6/3 16:10
 */
public class JiPinClaims extends DefaultClaims {
    public static final String AUTHORITIES = "authorities";
    public static final String USER_ID = "userIdd";
    public static final String TENANT_ID = "tenantId";
    public static final String LAST_PASSWORD_RESET_DATE = "lastPasswordResetDate";
    public static final String ENABLED = "enabled";
    public static final String USER_DETAILS = "userDetails";

    public String getAuthorities() {
        return this.getString(AUTHORITIES);
    }

    public Long getTenantId() {
        return Long.parseLong(this.getString(TENANT_ID));
    }
}
