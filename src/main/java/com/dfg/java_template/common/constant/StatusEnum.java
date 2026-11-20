package com.dfg.java_template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    DISABLE(0, "否"),
    ENABLE(1, "是");

    private final int code;
    private final String desc;
    
}
