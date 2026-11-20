package com.dfg.java_template.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceExceptionEnum {
    /**
     * 9-代表业务错误
     */
    SYSTEM_BUSY(900001, "service.system.busy"),
    MINI_CONFIG_ERROR(900002, "service.mini.config.error"),
    GET_PHONE_ERROR(900003, "service.get.phone.error"),
    GET_USER_INFO_ERROR(900004, "service.get.user.info.error"),
    ;
    private final Integer code;
    private final String message;

    public ServiceException toException() {
        return new ServiceException(this.code, this.message, null);
    }

    public ServiceException toException(Object[] args) {
        return new ServiceException(this.code, this.message, args);
    }
}
