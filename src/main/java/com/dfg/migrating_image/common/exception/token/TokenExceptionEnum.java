package com.dfg.migrating_image.common.exception.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenExceptionEnum {
    TOKEN_INVALID(100001, "token.invalid"),
    TOKEN_REQUIRED(100002, "token.required"),
    TOKEN_REMOTE_LOGIN(100003, "token.remote.login"),
    TOKEN_EXPIRED(100004, "token.expired"),
    ;

    private final Integer code;
    private final String message;

    public TokenException toException() {
        return new TokenException(this.code, this.message, null);
    }

    public TokenException toException(Object[] args) {
        return new TokenException(this.code, this.message, args);
    }
}
