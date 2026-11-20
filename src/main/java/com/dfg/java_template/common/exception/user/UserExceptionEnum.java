package com.dfg.java_template.common.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionEnum {
    USER_PASSWORD_NOT_MATCH(200001, "user.password.not.match"),
    USER_PASSWORD_RETRY_LIMIT_EXCEED(200002, "user.password.retry.limit.exceed"),
    USER_IS_NOT_EXIST(200003, "user.is.not.exist"),
    USER_PASSWORD_UPDATE_FAIL(200004, "user.password.update.fail"),

    ;


    private final Integer code;
    private final String message;


    public UserException toException() {
        return new UserException(this.code, this.message, null);
    }

    public UserException toException(Object[] args) {
        return new UserException(this.code, this.message, args);
    }

}
