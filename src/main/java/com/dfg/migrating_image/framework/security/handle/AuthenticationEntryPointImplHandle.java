package com.dfg.migrating_image.framework.security.handle;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.dfg.migrating_image.common.util.servlet.ServletUtils;
import com.dfg.migrating_image.common.web.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serial;
import java.io.Serializable;

/**
 * 未认证处理类 返回未授权401
 *
 * @author zgc
 */
@Slf4j
@Component
public class AuthenticationEntryPointImplHandle implements AuthenticationEntryPoint, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error("\n认证失败\n路径: {} \n异常类型: {}\n原因: {}\n",
                request.getRequestURI(),
                e.getClass().getSimpleName(),
                e.getMessage());
        ServletUtils.renderString(response, JSON.toJSONString(new AjaxResult(HttpStatus.HTTP_UNAUTHORIZED, e.getMessage(), request.getRequestURI())));
    }
}
