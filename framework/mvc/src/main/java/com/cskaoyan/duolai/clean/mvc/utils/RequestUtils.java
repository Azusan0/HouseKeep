package com.cskaoyan.duolai.clean.mvc.utils;

import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 请求工具
 */
public class RequestUtils {

    /**
     * 获取当前线程请求对象
     *
     * @return 请求对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = ServletRequestAttributesUtils.getServletRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
    }
}
