package com.dfg.java_template.framework.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginRoleEnum {
    FRONT_ROLE("FRONT", "前台用户"),
    BACK_ROLE("BACK", "后台用户");

    private final String code;
    private final String desc;

}
