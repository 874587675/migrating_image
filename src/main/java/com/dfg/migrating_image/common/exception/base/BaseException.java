package com.dfg.migrating_image.common.exception.base;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.dfg.migrating_image.common.util.message.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 基础异常
 *
 * @author zgc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认错误消息
     */
    private static String DEFAULT_MESSAGE = "系统异常";

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String message;

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(Integer code, String message, Object[] args) {
        this(null, code, args, message);
    }

    public BaseException(String message) {
        this(null, HttpStatus.HTTP_INTERNAL_ERROR, null, message);
    }

    @Override
    public String getMessage() {
        String result = null;
        if (!ObjectUtil.isEmpty(this.message)) {
            result = MessageUtils.message(this.message, this.args);
        }
        if (result == null) {
            result = DEFAULT_MESSAGE;
        }
        return result;
    }

}
