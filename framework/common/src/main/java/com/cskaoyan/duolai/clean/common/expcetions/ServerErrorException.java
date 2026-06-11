package com.cskaoyan.duolai.clean.common.expcetions;

import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;

import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;

/**
 * 服务器异常
 */
public class ServerErrorException extends CommonException {

    public ServerErrorException() {
        this(ErrorInfo.Msg.PROCESS_FAILD);
    }

    public ServerErrorException(String message) {
        super(HTTP_SERVER_ERROR, message);
    }

    public ServerErrorException(Throwable throwable, String message) {
        super(throwable, HTTP_SERVER_ERROR, message);
    }

    public ServerErrorException(Throwable throwable) {
        super(throwable, HTTP_SERVER_ERROR, ErrorInfo.Msg.PROCESS_FAILD);
    }

}
