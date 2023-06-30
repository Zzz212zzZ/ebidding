package com.ebidding.common.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.core.instrument.util.StringUtils;


public class AuthFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // svc -> feign
        String userId = AuthContext.getUserId();
        String name = AuthContext.getName();
        String role = AuthContext.getRole();
        // feign -> svc
        if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(name) && !StringUtils.isBlank(role)) {
            requestTemplate.header(AuthConstant.X_JWT_ID_HEADER, userId);
            requestTemplate.header(AuthConstant.CLAIM_USER_NAME, name);
            requestTemplate.header(AuthConstant.X_JWT_ROLE_HEADER, role);
        }
    }
}