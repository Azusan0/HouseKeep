package com.cskaoyan.duolai.clean.common.expcetions;

import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;

/**
 * 权限校验被拒
 */
public class RequestForbiddenException extends CommonException{

    public RequestForbiddenException() {
        this(ErrorInfo.Msg.REQUEST_FORBIDDEN);
    }

    public RequestForbiddenException(String message) {
        super(HTTP_FORBIDDEN, message);
    }

    public RequestForbiddenException(Throwable throwable, String message) {
        super(throwable, HTTP_FORBIDDEN, message);
    }

    public RequestForbiddenException(Throwable throwable) {
        super(throwable, HTTP_FORBIDDEN, ErrorInfo.Msg.REQUEST_FORBIDDEN);
    }

}
