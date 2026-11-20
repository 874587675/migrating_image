package com.dfg.java_template.common.exception;

import com.dfg.java_template.common.exception.base.BaseException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ServiceException extends BaseException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(code, message, null);
    }

    public ServiceException(Integer code, String message, Object[] args) {
        super("service", code, args, message);
    }

}
