package com.dfg.migrating_image.framework.security.param;

public interface ValidUser {
    /**
     * 用户密码
     */
    String getPassword();

    /**
     * 用户名
     */
    String getUserName();
}
