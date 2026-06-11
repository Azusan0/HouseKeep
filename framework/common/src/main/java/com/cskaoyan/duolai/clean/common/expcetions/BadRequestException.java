package com.cskaoyan.duolai.clean.common.expcetions;

import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

/**
 * 请求异常，
 * 使用场景：请求参数不合法，频繁请求
 *
 */
public class BadRequestException extends CommonException {

    public BadRequestException() {
        this(ErrorInfo.Msg.REQUEST_FAILD);
    }

    public BadRequestException(String message) {
        super(HTTP_BAD_REQUEST, message);
    }

    public BadRequestException(Throwable throwable, String message) {
        super(throwable, HTTP_BAD_REQUEST, message);
    }

    public BadRequestException(Throwable throwable) {
        super(throwable, HTTP_BAD_REQUEST, ErrorInfo.Msg.REQUEST_FAILD);
    }
}
