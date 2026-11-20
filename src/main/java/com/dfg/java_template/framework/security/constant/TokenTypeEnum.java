package com.dfg.java_template.framework.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenTypeEnum {
    ACCESS_TOKEN(1, "access_token"),
    REFRESH_TOKEN(2, "refresh_token"),
    ;

    private final Integer code;
    private final String desc;
}
