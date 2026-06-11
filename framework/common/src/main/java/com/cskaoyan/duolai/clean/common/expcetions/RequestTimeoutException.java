package com.cskaoyan.duolai.clean.common.expcetions;

import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;

import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;

/**
 * 请求超时异常
 */
public class RequestTimeoutException extends CommonException {

    public RequestTimeoutException() {
        this(ErrorInfo.Msg.REQUEST_TIME_OUT);
    }

    public RequestTimeoutException(String message) {
        super(HTTP_CLIENT_TIMEOUT, message);
    }

    public RequestTimeoutException(Throwable throwable, String message) {
        super(throwable, HTTP_CLIENT_TIMEOUT, message);
    }

    public RequestTimeoutException(Throwable throwable) {
        super(throwable, HTTP_CLIENT_TIMEOUT, ErrorInfo.Msg.REQUEST_TIME_OUT);
    }

}
